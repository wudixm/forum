(ns forum.topic.controller
  (require [forum.common.util.req :refer [get_req_body]])
  )


(defn create_topic [user, topic_content, topic_name]

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