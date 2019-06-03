(ns forum.topic.util.topic-util
  (:require [clojure.java.jdbc :as jdbc]
            [clojure.data.json :as json]
            [forum.common.util.db :refer :all]
            [forum.user.util.util :refer [user_info]]
            ))

(defn fill_one_topic [topic]
  (hash-map "name" (:name topic)
            "desc" (:description topic)
            "id" (:id topic)
            "user_id" (:user_id topic)
            "username" (:name (user_info (:user_id topic)))
            "like_count" (:like_count topic)
            "comment_count" (:comment_count topic)
            ; "created_at" (subs (str (:created_at topic)) 0 10)
            "created_at" (let [diff (- (System/currentTimeMillis) (.getTime (:created_at topic)))
                               diff_minutes (/ diff (* 60 1000))
                               diff_seconds (/ diff 1000)
                               diff_str (if (< diff_seconds 59)
                                          "less than a minute"
                                          (if (< diff_seconds (* 59 60))
                                            (str diff_minutes " minutes ago")
                                            (if (< diff_seconds (* 24 3600))
                                              (str (int (/ diff_minutes 60)) " hours ago")
                                              (if (< diff_seconds (* 30 24 60 60))
                                                (str (int (/ diff_minutes 24 60)) " days ago")
                                                (str (int (/ diff_minutes 30 24 60)) " months ago")
                                                )
                                              )
                                            )
                                          )

                               ; times (.format (java.text.SimpleDateFormat. "MM/dd/yyyy" ) (java.util.Date. diff))
                               ]
                           (println (str "created_at = " (java.util.Date. (.getTime (:created_at topic)))))
                           (println (str "当前时间是" (System/currentTimeMillis) " date=" (java.util.Date.)))
                           (println diff)
                           diff_str
                           )
            )
  )
(defn fill_topic [topic_seqs]
  ; desc, id, user_name, 时间点，点赞数，评论数
  (if (< (count topic_seqs) 1)
    (hash-map )
    (map fill_one_topic topic_seqs)
    )

  )

(defn all_topic [seqs length]

  (let [pinned_stmt (jdbc/query db-spec ["select * from topic_info where id > ? and pinned = 1 limit ? " seqs length])
        pinned_topics (fill_topic pinned_stmt)
        latest_sql (if (zero? seqs)
                     "select * from topic_info where id > ? and pinned != 1 order by id desc limit ? "
                     "select * from topic_info where id < ? and pinned != 1 order by id desc limit ? "
                     )
        latest_stmt (jdbc/query db-spec [latest_sql seqs length])
        latest_topics (fill_topic latest_stmt)
        ]
    ; (println topics)
    ; (println (json/write-str topics))

    (json/write-str (hash-map "pinned" pinned_topics "latest" latest_topics))
    ;(println (type topics)) clojure.lang.LazySeq
    )
  )
(defn get_topic_by_id [_id]
  (let [stmt (jdbc/query db-spec ["select * from topic_info where id = ? " _id])
        topics (fill_one_topic (first stmt) )
        comments "fdadfsa"
        ]
    (json/write-str topics)
    )
  )
(defn create_topic [title content user_id]
  (let [stmt (jdbc/execute! db-spec ["insert into topic_info (name, description, user_id) values (?, ?, ?) ;" title content user_id] )
        ])
  "success"
  )
; (all_topic 0 1)
;(get_topic_by_id 24)
