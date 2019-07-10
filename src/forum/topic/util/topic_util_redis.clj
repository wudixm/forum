(ns forum.topic.util.topic-util-redis
  (:require [clojure.java.jdbc :as jdbc]
            [clojure.data.json :as json]
            [forum.common.util.db :refer [wcar*]]
            [taoensso.carmine :as car]
            [clojure.data.json :as json]

            ))

(defn fill_topic [topic_obj]

  ;(hash-map "name" (:name topic)
  ;          "desc" (:description topic)
  ;          "id" (:id topic)
  ;          "user_id" (:user_id topic)
  ;          "username" (:name (user_info (:user_id topic)))
  ;          "like_count" (:like_count topic)
  ;          "comment_count" (:comment_count topic)
  ;"created_at" (let [diff (- (System/currentTimeMillis) (.getTime (:created_at topic)))
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
                                ))) topic )
        ]
    (println topic)
    (println topic_obj)
    (println (type topic_obj))
    topic_obj
    )
  )
(defn inc_topic_id []
  (wcar* (car/incr "topic:"))
  )
(defn create_topic [title content user_id]
  ;(let [stmt (jdbc/execute! db-spec ["insert into topic_info (name, description, user_id) values (?, ?, ?) ;" title content user_id] )
  ;      ])
  ;"success"
  (let [topic (hash-map "name" title "description" content "user_id" user_id)
        topic_id (inc_topic_id)
        ]
    (println (json/write-str topic))
    (println topic_id)
    ;(wcar* car/hmset* topic)

    (wcar* (car/hmset* (str "topic:" topic_id) topic))
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
