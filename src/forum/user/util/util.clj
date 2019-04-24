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
; (user_info 1)
; (register_user "wxmswy" 123)
