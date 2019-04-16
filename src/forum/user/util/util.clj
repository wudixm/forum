(ns forum.user.util.util
  (:require [clojure.java.jdbc :as jdbc]
            [clojure.data.json :as json]
            ))

(def db-spec
  {:classname   "com.mysql.jdbc.Driver"
   :subprotocol "mysql"
   :subname           "//127.0.0.1:13308/forum"
   :user        "dev"
   :password    "devdevdev"
   :useSSL false
   })

(defn user_info [_id]

  (let [stmt (jdbc/query db-spec ["select * from user_obs where id = ?" _id] )]
    (first stmt)
    )
  )
; (user_info 1)
