(ns forum.user.login
  (:require [clojure.string :as str])
  (:require [forum.common.util.req :refer [get_req_body]])
  )
(defn username_password_parse [req]
  (println req)
  (let [body (get_req_body req)]
    (println "in method username_password_parse")
    (println "body is ")
    (println body)

    ; 默认格式是username=111&pass=1
    (let [username (nth (str/split (first (str/split body #"&")) #"=") 1)
          pwd (nth (str/split (nth (str/split body #"&") 1) #"=") 1)]
      (println username)
      (println pwd)
      (if (or (nil? username) (= username "") (nil? pwd) (= pwd ""))
        "input user name or pass is nil"
        (hash-map "username" username "password" pwd))))
  )
