(ns fullstack.config)

(def host
  "localhost")

(def port
  "In dev mode figwheel provides the server with a path not specified here. When in dev mode it has to be the same as the figwheel server port as specified in project.clj. For prod mode it can be configured here."
  3448)

(defn url [scheme path]
  (str scheme "://" host ":" port "/" path))

(def websocket-url
  (url "ws" "ws"))
