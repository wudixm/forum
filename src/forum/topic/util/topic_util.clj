(ns forum.topic.util.topic-util
  (:require [clojure.java.jdbc :as jdbc]
            [clojure.data.json :as json]
            [forum.user.util.util :refer [user_info]]
            ))

(def db-spec
  {:classname   "com.mysql.jdbc.Driver"
   :subprotocol "mysql"
   ;:subname     "//127.0.0.1:3306/mm"
   ;:subname           "//172.26.158.219:3306/mm"
   ;:subname           "//172.17.0.1:3306/mm"
   :subname           "//127.0.0.1:13308/forum"
   ;:subname           "//localhost:3306/mm"
   :user        "dev"
   :password    "devdevdev"
   ;:user        "root"
   ;:password    "rootroot"
   :useSSL false
   })
(defn fill_topic [topic_seqs]
  ; desc, id, user_name, 时间点，点赞数，评论数
  (let [f (first topic_seqs)]
    (println (:created_at f))
    (println (type (:created_at f)))
    (println (.getTime (:created_at f)))
    (println (System/currentTimeMillis ))

    )
  (map #(hash-map "name" (:name %)
                  "desc" (:description %)
                  "id" (:id %)
                  "username" (:name (user_info (:user_id %)))
                  "like_count" (:like_count %)
                  "comment_count" (:comment_count %)
                  ; "created_at" (subs (str (:created_at %)) 0 10)
                  "created_at" (let [diff (- (System/currentTimeMillis ) (.getTime (:created_at %)))
                                     diff_minutes (/ diff (* 60 1000))
                                     diff_seconds (/ diff 1000)
                                     diff_str (if (< diff_seconds 59)
                                                "less than a minute"
                                                (if (< diff_seconds (* 59 60))
                                                  (str diff_minutes " minutes ago")
                                                  (if (< diff_seconds (* 24 3600))
                                                    (str (int (/ diff_minutes 60))  " hours ago")
                                                    (if (< diff_seconds (* 30 24 60 60))
                                                      (str (int (/ diff_minutes 24 60)) " days ago")
                                                      (str (int (/ diff_minutes 30 24 60 )) " months ago")
                                                      )
                                                    )
                                                  )
                                                )

                                     ; times (.format (java.text.SimpleDateFormat. "MM/dd/yyyy" ) (java.util.Date. diff))
                                     ]
                                 (println (str "created_at = " (java.util.Date. (.getTime (:created_at %)))))
                                 (println (str "当前时间是" (System/currentTimeMillis) " date=" (java.util.Date.)))
                                 (println diff)
                                 ; (println (type minutes))
                                 ; (println minutes)
                                 ; (println (str (float minutes)  " 分钟前"))
                                 ; (println times)
                                 diff_str
                                 )
                  ) topic_seqs)
  )

(defn all_topic [seqs length]

  (let [stmt (jdbc/query db-spec ["select * from topic_info where id > ? limit ?" seqs length] )
        topics (fill_topic stmt)
        ]
    ; (println topics)
    ; (println (json/write-str topics))
    (json/write-str topics)
    ;(println (type topics)) clojure.lang.LazySeq
    )
  )
(all_topic 0 1)
