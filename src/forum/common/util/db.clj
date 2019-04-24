(ns forum.common.util.db
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
