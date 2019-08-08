(ns forum.topic.controller
  (require [forum.common.util.req :refer [get_req_body]]
           [forum.topic.util.topic-util-redis :as dao]
           ))

(defn get_topic_by_id [_id]
  (dao/get_topic_by_id _id)
  )

(defn all_topics [seq length]
  (dao/all_topic seq length)
  )

(defn create_topic [title content user_id]
  (dao/create_topic title content user_id)
  )

(defn parse_content_from_req [req]

  (println req)
  (let [body (get_req_body req)]
    (println "in method username_password_parse")
    (println "body is ")
    (println body)

    ; body 的默认格式是username=111&pass=1
    )
  (hash-map "name" "topic name" "content" "this is the topic content " "user_id" 123)
  )
