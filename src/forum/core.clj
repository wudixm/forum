(ns forum.core
  (require
    [org.httpkit.server :refer [run-server]]
    [compojure.core :refer :all]                            ; defroutes
    [compojure.route :as route]                             ; route/resources
    [clojure.string :as str]
    [forum.user.login :refer :all]
    [forum.topic.controller :refer :all]
    [forum.topic.util.topic-util :refer :all]
    [ring.middleware.params :refer :all]

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
  [req]
  (println req)
  (println (type req))
  (let [seqs (get (:query-params req) "seq" 0)
        length (get (:query-params req) "length" 20)
        kind (get (:query-params req) "kind" "all") ]     ;kind 有3 种，all 全部, pinned（置顶），normal(非置顶)
    (println (str "seq =" seqs))
    (println (str "length =" length))
    (println (str "kind = " kind))
    (all_topic seqs length)
    ;(if (= kind "all")
    ;  (all_topic seqs length)
    ;  (if (= kind "pinned")
    ;    ()
    ;    ))
    )
  )
(defn wrap-resp [resp]
    (let [new_resp (hash-map :body resp :status 200 :headers {"Content-Type" "text/text"})] 
     (println new_resp)
     new_resp
      )
  )
(defn topic_info [req]
  (let [topic_id (get (:query-params req) "topic_id" 0)]
    (println (str "topic_info called " topic_id))
    (println topic_id)
    (def result (get_topic_by_id topic_id) )
    (println result)
    result
    )
  
  )
(defroutes myapp
  (GET "/" [] "Hello World11111")
  (GET "/all_post" req (wrap-resp (all_post req)))
  (GET "/topic" req (wrap-resp (topic_info req)))
  (POST "/" req (mytest req))
  (POST "/register" req (register req))            ;登录注册先不需要，只能发帖，回复帖子
  (POST "/post/topic" req (create req))
  (route/resources "/")
  )



(defn -main []
  (println "start server at port 8081")
  (run-server (wrap-params myapp ) {:port 8081})
  )

(defn stop [server]
  (server :timeout 10)
  )
