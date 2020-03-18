(ns fullstack.server
  (:require [org.httpkit.server :as http]
            [fullstack.handler :refer [handler]]
            [config.core :refer [env]]
            [fullstack.config :as config])
  (:gen-class))

(defn -main [& args]
  (println "Starting http-kit server on port" config/port "...")
  (http/run-server handler
                   {:port config/port
                    :join? false})
  (println "Server running"))
