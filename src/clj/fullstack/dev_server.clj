(ns fullstack.dev-server
  (:require [fullstack.handler :refer [handler]]
            [figwheel-sidecar.repl-api
             :refer [start-figwheel! stop-figwheel! cljs-repl]])
  (:gen-class))

(defn start-dev []
  (start-figwheel!))

(defn stop-dev []
  (stop-figwheel!))
