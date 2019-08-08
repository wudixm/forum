(ns forum.topic.util.topic-util-redis
  (:require [clojure.java.jdbc :as jdbc]
            [clojure.data.json :as json]
            [forum.common.util.db :refer [wcar*]]
            [taoensso.carmine :as car]
            [clojure.data.json :as json]
            [forum.user.util.util :refer [user_info]]

            ))
(def score_per_like 432)
(defmacro now_time_stamp
  []
  `(quot (System/currentTimeMillis) 1000)
  )
(defn add_unit_to_time [time_seconds]
  (let [diff (- (System/currentTimeMillis) (* 1000 time_seconds))
        a (do println diff)
        diff_minutes (int (/ diff (* 60 1000)))
        diff_seconds (int (/ diff 1000))
        diff_str (if (< diff_seconds 59)
                   "less than a minute"
                   (if (< diff_seconds (* 59 60))
                     (str diff_minutes " minutes ago")
                     (if (< diff_seconds (* 24 3600))
                       (str (int (/ diff_minutes 60)) " hours ago")
                       (if (< diff_seconds (* 30 24 60 60))
                         (str (int (/ diff_minutes 24 60)) " days ago")
                         (str (int (/ diff_minutes 30 24 60)) " months ago")
                         )
                       )
                     )
                   )
        ; times (.format (java.text.SimpleDateFormat. "MM/dd/yyyy" ) (java.util.Date. diff))
        ]
    ;(println (str "created_at = " time_seconds))
    ;(println (str "当前时间是" (System/currentTimeMillis) " date=" (java.util.Date.)))
    ;(println diff)
    ;(println diff_minutes)
    ;(println diff_seconds)
    diff_str
    )
  )
(defn fill_topic [topic_id_str]
  (let [topic (wcar* (car/hgetall topic_id_str))
        topic_obj (apply hash-map topic)
        created_at_st (add_unit_to_time (Integer/parseInt (get topic_obj "timestamp")))
        username (get (user_info (get topic_obj "user_id") ) :name)
        result (assoc topic_obj "created_at" created_at_st "username" username "desc" (get topic_obj "description"))
        ]
    (println topic_id_str)
    ; topic_obj
    ;{"like_count" "0",
    ; "user_id" "11174736",
    ; "timestamp" "1565233068",
    ; "name" "title3",
    ; "comment_count" "0",
    ; "description" "content3"}
    (println result)
    (dissoc result "description")
    )

  ;(hash-map "name" (:name topic)
  ;          "desc" (:description topic)
  ;          "id" (:id topic)
  ;          "user_id" (:user_id topic)
  ;          "username" (:name (user_info (:user_id topic)))
  ;          "like_count" (:like_count topic)
  ;          "comment_count" (:comment_count topic)
  ;"created_at" (let [diff (- (System/currentTimeMillis) (.getTime (:created_at topic)))

  )
(defn all_pinned_topics []
  (let [pinned_ids (wcar* (car/lrange "pinned_ids:" 0 -1))]
    (map (fn [x] (str "topic:" x)) pinned_ids)
    )
  )
(defn all_topic [seqs length]
  (let [pinned (all_pinned_topics)
        topics (wcar* (car/zrangebyscore "score:" (- (now_time_stamp) 3600) "+inf" "limit" seqs length))]
    (json/write-str (hash-map "pinned" (map fill_topic pinned) "latest" (map fill_topic topics)))
    )
  )
(defn get_topic_by_id [_id]
  (let [topic (wcar* (car/hgetall (str "topic:" _id)))
        topic_obj (reduce (fn [x y]
                            (if (string? x)
                              (hash-map x y)
                              (if (map? x)
                                [x y]
                                (assoc (first x) (last x) y)
                                ))) topic)
        ]
    (println topic)
    (println topic_obj)
    (println (type topic_obj))
    topic_obj
    )
  )
(defn like_topic
  "点赞话题"
  [user_id topic_id]
  (let [topic_id_str (str "topic:" topic_id)
        timestamp (now_time_stamp)
        an_hour_ago_timestamp (- (now_time_stamp) 3600)
        within-one-hour (some #(= topic_id_str %) (wcar* (car/zrangebyscore "score:" (- (now_time_stamp) 3600) "+inf")))
        is_liked (if within-one-hour (= 1 (wcar* (car/sismember (str "liked:" topic_id) user_id))) false)
        ]

    ; 判断是否已经超时1 小时
    ; 还要判断是否已经点过赞
    (println timestamp)
    (println an_hour_ago_timestamp)

    (if (and within-one-hour (not is_liked))
      (do
        (wcar* (car/multi))
        (try
          (println "before inc like count")
          (wcar* (car/hincrby topic_id_str "like_count" 1))
          (println "before inc score count")
          (wcar* (car/zincrby "score:" score_per_like topic_id_str))
          (println "before add user_id to liked:topic_id")
          (wcar* (car/sadd (str "liked:" topic_id) user_id))
          (println "after all ")
          (wcar* (car/exec))
          (catch Throwable e
            (println "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%")
            ;(.printStackTrace e)
            ;(println (str "exception e " (.getMessage e)))
            (wcar* (car/discard))
            )
          (finally (println "finally print")))
        )
      (do
        (if (not within-one-hour)
          "帖子不存在，超过1 小时"
          "您已经点过赞了"
          )
        )
      )

    )
  )

(defn inc_topic_id []
  "自增话题id"
  (wcar* (car/incr "topic:"))
  )

(defn create_topic [title content user_id]
  (let [timestamp (now_time_stamp)
        topic (hash-map "name" title
                        "description" content
                        "user_id" user_id
                        "like_count" 0
                        "comment_count" 0
                        "timestamp" timestamp
                        )
        topic_id (inc_topic_id)
        topic_id_str (str "topic:" topic_id)
        expire_time 3600
        ]
    (println (json/write-str topic))
    (println topic_id)
    ; 点过赞的，这个要过期expire
    (wcar* (car/sadd (str "liked:" topic_id) user_id))
    (wcar* (car/expire (str "liked:" topic_id) expire_time))

    (println topic)
    (wcar* (car/hmset* topic_id_str topic))

    (println topic_id_str)
    ; 积分表，可以按照积分查询
    (wcar* (car/zadd "score:" (+ timestamp score_per_like) topic_id_str))

    (println timestamp)
    ; 时间表，可以按照时间查
    (wcar* (car/zadd "time:" timestamp topic_id_str))
    )
  )


;(get_topic_by_id 11
;(create_topic "1" "2" 3)
; (all_topic 0 1)
;(get_topic_by_id 24)
;(wcar* (car/get "test"))
;(wcar* (car/ping))

;(wcar* (car/hset "Hkey" "mapKey" "{\"a\":1}"))
;(def a (wcar* (car/hget "Hkey" "mapKey" )))
;(println a)
;(println (type a))
;(def b (json/read-json a))
;(println b)
;(println (type b))
;(println (:a b))
;(wcar* (car/hgetall "topic:8"))
