(ns forum.common.util.db
  (:require [clojure.java.jdbc :as jdbc]
            [clojure.data.json :as json]
            [taoensso.carmine :as car :refer (wcar)])
  (:import (java.net InetAddress)))

(def local_mac
  {:classname   "com.mysql.jdbc.Driver"
   :subprotocol "mysql"
   :subname     "//127.0.0.1:13308/forum"
   ; :subname           "//172.17.0.1:3306/forum"
   :user        "dev"
   :password    "devdevdev"
   :useSSL      false
   })
(def remote_ali
  {:classname   "com.mysql.jdbc.Driver"
   :subprotocol "mysql"
   ; :subname           "//127.0.0.1:13308/forum"
   ; :subname           "//172.17.0.1:3306/forum"
   :user        "dev"
   ; :password    "devdev"
   :subname     "//172.26.158.219:3306/forum"
   :password    "devdevdev"
   :useSSL      false
   })
(defn is_local_mac []
  (let [host_str (.toString (InetAddress/getLocalHost))]
    (println host_str)                                      ; hoytMacBook-Pro.local/192.168.202.210
    (println "host_str")
    (if (or (> (.indexOf host_str "hoytMacBook") -1 ) (> (.indexOf host_str "iz8vbcmllue4daadc7mfm8z") -1))
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


; (def redis-spec
  ; (if (is_local_mac)
    ; ; 定义两种server-conn 给不同环境redis 用
    ; )
  ; )
(def server1-conn
  (if (is_local_mac)
    {:pool {} :spec {:uri "redis://127.0.0.1:6379/1"}}
    ;{:pool {} :spec {:uri "redis://172.26.158.219:6379/1"}}
    {:pool {} :spec {:uri "redis://172.17.0.1:6379/1"}}
    )
  )
(defmacro wcar* [& body] `(car/wcar server1-conn ~@body))
;(println db-spec)
;(println server1-conn)
; (wcar* (car/set "test" 2))
;(wcar* (car/get "test"))
