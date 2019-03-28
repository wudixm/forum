(ns forum.core
  (require
    [org.httpkit.server :refer [run-server]]
    [compojure.core :refer :all]                            ; defroutes
    [compojure.route :as route]                             ; route/resources
    [clojure.string :as str]
    [forum.user.login :refer :all]
    [forum.topic.controller :refer :all]

    )
  )

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))

(defn handler [request]
  {:status  200
   :headers {"Content-Type" "text/html"}
   :body    "Hello World"})

(defn register [req]
  (println req)
  (username_password_parse req)
  )
(defn mytest [req]
  (println req)
  "test success"
  )

(defn create [req]
  (let [info (parse_content_from_req req)
        name (get info :name)
        content (get info :content)
        user_id (get info :user_id)]
    (println name)
    (println content)
    (println user_id)
    )

  )
(defn all_post
  "获取所有帖子，包括置顶的"
  []

  )

(defroutes myapp
           (GET "/" [] "Hello World11111")
           (GET "/all_post" req (all_post))
           (POST "/" req (mytest req))
           (POST "/register" req (register req))            ;登录注册先不需要，只能发帖，回复帖子
           (POST "/post/topic" req (create req))
           (route/resources "/")
           )



(defn -main []
  (println "start server at port 8081")
  (run-server myapp {:port 8081})
  )

(defn stop [server]
  (server :timeout 10)
  )

