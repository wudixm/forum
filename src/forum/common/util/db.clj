(ns forum.common.util.db
  (:require [clojure.java.jdbc :as jdbc]
            [clojure.data.json :as json]
            ))

(def db-spec
  {:classname   "com.mysql.jdbc.Driver"
   :subprotocol "mysql"
   ; :subname           "//127.0.0.1:13308/forum"
   ; :subname           "//172.17.0.1:3306/forum"
   :user        "dev"
   ; :password    "devdev"
   :subname           "//172.26.158.219:3306/forum"
   :password    "devdevdev"
   :useSSL false
   })
