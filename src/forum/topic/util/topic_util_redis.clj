(ns forum.topic.util.topic-util-redis
  (:require [clojure.java.jdbc :as jdbc]
            [clojure.data.json :as json]
            [forum.common.util.db :refer [wcar*]]
            [taoensso.carmine :as car]
            [clojure.data.json :as json]

            ))

(defn all_topic [seqs length]
  )
(defn get_topic_by_id [_id]
  )
(defn create_topic [title content user_id]
  ;(let [stmt (jdbc/execute! db-spec ["insert into topic_info (name, description, user_id) values (?, ?, ?) ;" title content user_id] )
  ;      ])
  ;"success"

  )
; (all_topic 0 1)
;(get_topic_by_id 24)
(wcar* (car/get "test"))
(wcar* (car/ping))

(wcar* (car/hset "Hkey" "mapKey" "{\"a\":1}"))
(def a (wcar* (car/hget "Hkey" "mapKey" )))
(println a)
(println (type a))
(def b (json/read-object a))
(println b)
(println (type b))
