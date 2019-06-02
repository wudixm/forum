(ns forum.topic.controller
  (require [forum.common.util.req :refer [get_req_body]]
           [forum.topic.util.topic-util :refer [all_topic]]
           )
  )


(defn all_topics [seq length]
  (all_topic seq length)
  )
(defn delete_topic
  "delete"
  [topic_id]

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
;(all_topics 1 1 )
