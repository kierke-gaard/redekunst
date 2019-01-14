(ns fullstack.server
  (:require [org.httpkit.server :as http]
            [fullstack.handler :refer [handler]]
            [config.core :refer [env]]
            ;;[fullstack.config :as config]
            )
  (:gen-class))

(defn -main [& args]
  (let [port (Integer/parseInt (or (env :port) "3448"))]
    (println "Starting http-kit server on port" port "...")
    (http/run-server handler
                     {:port port
                      :join? false})
    (println "Server running")))
