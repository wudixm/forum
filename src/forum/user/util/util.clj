(ns forum.user.util.util
  (:require [clojure.java.jdbc :as jdbc]
            [clojure.data.json :as json]
            [forum.common.util.db :refer :all]
            ))


(defn user_info [_id]

  (let [stmt (jdbc/query db-spec ["select * from user_obs where id = ?" _id] )]
    (first stmt)
    )
  )
(defn register_user [email pass]

  (let [stmt (jdbc/execute! db-spec ["insert into user_obs (email, password) values (?, ?) ;" email pass] )]
    )

  )

(defn login_user [email pass]

  (let [stmt (jdbc/query db-spec ["select email, password from user_obs where email = ? and password = ? ;" email pass] )
        fs (first stmt)
        ]
    (println (get fs :email))
    fs
    )

  )

(defn test_spec [db-s sqlstr ]

  (let [stmt (jdbc/query db-s [sqlstr ] )
        fs (first stmt)
        ]
    (println fs)
    (println (type fs))
    fs
    )

  )
; (user_info 1)
; (register_user "wxmswy" 123)
; (login_user "wxm66668888@163.com" 1)
