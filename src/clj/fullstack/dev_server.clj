(ns fullstack.dev-server
  (:require [fullstack.handler :refer [handler]]
            [fullstack.config :as config]
            [figwheel-sidecar.repl-api
             :refer [start-figwheel! stop-figwheel! cljs-repl]])
  (:gen-class))

(defn start-dev []
  (start-figwheel!))

(defn stop-dev []
  (stop-figwheel!))
