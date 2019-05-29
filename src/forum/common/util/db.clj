(ns forum.common.util.db
  (:require [clojure.java.jdbc :as jdbc]
            [clojure.data.json :as json]
            )
  (:import (java.net InetAddress)))

(def local_mac
  {:classname   "com.mysql.jdbc.Driver"
   :subprotocol "mysql"
   :subname           "//127.0.0.1:13308/forum"
   ; :subname           "//172.17.0.1:3306/forum"
   :user        "dev"
   :password    "devdevdev"
   :useSSL false
   })
(def remote_ali
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
(defn is_local_mac []
  (let [host_str (.toString (InetAddress/getLocalHost))]
    (println host_str)
    (println "host_str")
    (if (> (.indexOf host_str "hoytMacBook") -1)

      true
      false
      )
    )
  )
(def db-spec
  (if (is_local_mac)
    local_mac
    remote_ali
    )
  )
(println db-spec)
