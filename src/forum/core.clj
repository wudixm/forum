(ns forum.core
  (require
    [org.httpkit.server :refer [run-server]]
    [compojure.core :refer :all]                            ; defroutes
    [compojure.route :as route]                             ; route/resources
    [clojure.string :as str]
    [forum.user.login :refer :all]
    [forum.user.util.util :refer :all]
    [forum.topic.controller :refer :all]
    [ring.middleware.params :refer :all]
    [ring.util.response :refer [response]]
    [ring.middleware.session :refer :all]
    [ring.middleware.cookies :refer :all]
    [taoensso.carmine :as car :refer (wcar)]

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
  (let [email (get (:form-params req) "email" 0)
        pass (get (:form-params req) "password" 0)
        ]
    (register_user email pass)
    (let [resp (assoc (response "注册成功!") :session (hash-map "username" email))]
      (println resp)
      resp
      )
    )
  ; (username_password_parse req)
  )
(defn mytest [req]
  (println req)
  (let [old-session (:session req)
        ]
    (println "old -session")
    (println old-session)
    ;(assoc (response "you are not login ") :session "testsession" :cookies (hash-map "session_id" (hash-map "value" "sessionid")))
    (assoc (response "you are not login ") :session (str "testsession"))
    ;(-> (response "You are now logged in")
    ;    (assoc :session new-session))
    )
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
        kind (get (:query-params req) "kind" "all")]        ;kind 有3 种，all 全部, pinned（置顶），normal(非置顶)
    (println (str "seq =" seqs))
    (println (str "length =" length))
    (println (str "kind = " kind))
    (all_topics seqs length)
    ;(if (= kind "all")
    ;  (all_topic seqs length)
    ;  (if (= kind "pinned")
    ;    ()
    ;    ))
    )
  )
(defn wrap-resp [resp req]
  (let [session (:session req)
        new_resp (hash-map :body resp :status 200 :headers {"Content-Type" "text/text"} :session session)
        ]
    (println "in method wrap-resp")
    (println new_resp)
    new_resp
    )
  )
(defn session_info [req]
  (println "in method session_info")
  (println req)
  (let [key_name (get (:query-params req) "key_name" "username")
        session (:session req)]
    (println session)
    (println key_name)
    (println (get session key_name))
    (get session key_name)
    )
  )
(defn topic_info [req]
  (let [topic_id (get (:query-params req) "topic_id" 0)]
    (println (str "topic_info called " topic_id))
    (println topic_id)
    (def result (get_topic_by_id topic_id))
    (println result)
    result
    )
  )
(defn login [req]
  (println "in method login")
  (println req)
  (let [email (get (:query-params req) "email" 0)
        pass (get (:query-params req) "password" 0)
        result (login_user email pass)
        ]
    (println (str "email " email))
    (println (str "pass " pass))
    (if (nil? result)
      (let [resp (response "not found the user")]
        resp
        )
      (let [resp (assoc (response "find the user") :session (hash-map "username" (get result :email) "user_id" (get result :id)))]
        (println result)
        (println (get result :id))
        resp
        )
      )
    ))
(defn logout [req]
  (println "in method logout ")
  (println req)
  (let [resp (assoc (response "log out success") :session nil)]
    (println "resp is ")
    (println resp)
    resp
    )
  )
(defn new_topic [req]
  (let [title (get (:form-params req) "title" "")
        content (get (:form-params req) "content" "")
        session (:session req)
        user_id (get session "user_id")
        ]
    (println title)
    (println content)
    (println session)
    (println user_id)
    (println ())
    (create_topic title content user_id)
    (response "create topic success~")
    )
  )
(defn rand-str [req]
  (let [ran (rand-int 10)]
    (if (> ran 5)
      (str "success " ran)
      (str "successnot_ " ran)
      )
    )
  )
(defroutes myapp
  (GET "/" [] "Hello World11111")
  (GET "/mytest" req (mytest req))
  (GET "/all_post" req (wrap-resp (all_post req) req))
  (GET "/topic" req (wrap-resp (topic_info req) req))
  (GET "/metrics" req (response (rand-str req)))
  (GET "/session_info" req (wrap-resp (session_info req) req))
  (POST "/" req (mytest req))
  (POST "/register" req (register req))
  (POST "/new_topic" req (new_topic req))
  (GET "/login" req (login req))
  (GET "/logout" req (logout req))
  (POST "/post/topic" req (create req))
  (route/resources "/")
  )



(defn -main []
  (println "start server at port 8081")
  (run-server (wrap-params (wrap-cookies (wrap-session myapp {:cookie-attrs {:max-age 3600}}))) {:port 8081})
  )

(defn stop [server]
  (server :timeout 10)
  )
(login_user "fdsafdsaf@aaa.com" 1 )
