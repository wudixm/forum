(ns forum.topic.util.topic-util-redis
  (:require [clojure.java.jdbc :as jdbc]
            [clojure.data.json :as json]
            [forum.common.util.db :refer [wcar*]]
            [taoensso.carmine :as car]
            [clojure.data.json :as json]

            ))
(def score_per_like 432)
(defmacro now_time_stamp
  []
  `(quot (System/currentTimeMillis) 1000)
  )

(defn fill_topic [topic_obj]

  ;(hash-map "name" (:name topic)
  ;          "desc" (:description topic)
  ;          "id" (:id topic)
  ;          "user_id" (:user_id topic)
  ;          "username" (:name (user_info (:user_id topic)))
  ;          "like_count" (:like_count topic)
  ;          "comment_count" (:comment_count topic)
  ;"created_at" (let [diff (- (System/currentTimeMillis) (.getTime (:created_at topic)))
  (hash-map "name")
  )
(defn all_topic [seqs length]
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
        within-one-hour (wcar* (car/zrangebyscore "score:" (- (now_time_stamp) 3600) "+inf"))
        ]

    ; 判断是否已经超时1 小时
    (println timestamp)
    (println an_hour_ago_timestamp)

    (if (some #(= topic_id_str %) within-one-hour)
      (do
        (wcar* (car/hincrby topic_id_str "like_count" 1))
        (wcar* (car/zincrby "score:" topic_id_str score_per_like))
        (wcar* (car/sadd (str "liked:" topic_id) user_id))
        )
      (do ))

    )
  )

(defn inc_topic_id []
  "自增话题id"
  (wcar* (car/incr "topic:"))
  )

(defn create_topic [title content user_id]
  (let [topic (hash-map "name" title
                        "description" content
                        "user_id" user_id
                        "like_count" 0
                        "comment_count" 0)
        timestamp (now_time_stamp)
        topic_id (inc_topic_id)
        topic_id_str (str "topic:" topic_id)
        expire_time 3600
        ]
    (println (json/write-str topic))
    (println topic_id)
    ; 点过赞的，这个要过期expire
    (wcar* (car/sadd (str "liked:" topic_id) user_id))
    (wcar* (car/expire (str "liked:" topic_id) expire_time))

    ; hash 存值
    (assoc topic "timestamp" timestamp)
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


;(get_topic_by_id 11)
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
