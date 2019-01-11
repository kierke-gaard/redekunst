(ns fullstack.server
  (:require [fullstack.handler :refer [handler]]
            [figwheel-sidecar.repl-api
             :refer [start-figwheel! stop-figwheel! cljs-repl]]
            [config.core :refer [env]]
            [ring.adapter.jetty :refer [run-jetty]])
  (:gen-class))

 (defn -main [& args]
   (let [port (Integer/parseInt (or (env :port) "3000"))]
     (run-jetty handler {:port port :join? false})))

(defn start-dev []
  (start-figwheel!))

(defn stop-dev []
  (stop-figwheel!))
