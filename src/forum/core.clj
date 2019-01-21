(ns forum.core
  (require
    [org.httpkit.server :refer [run-server]]
    )
  )

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))

(defn handler [request]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body "Hello World"})



(defn -main []
  (println "start server at port 8080")
  (run-server handler {:port 8080}))
