(ns forum.common.util.req)


(defn get_req_body [req]
  (slurp (:body req))
  )
