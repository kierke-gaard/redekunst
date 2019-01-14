(ns fullstack.config
  (:require [config.core :refer [env]]))

(def host
  (or (env :fullstack-host)
      "localhost"))

(def port
  "In dev mode figwheel provides the server with a path not specified here"
  (or (env :fullstack-port)
      3448))

(defn url [scheme path]
  (str scheme "://" host ":" port "/" path))
