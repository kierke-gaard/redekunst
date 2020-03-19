(ns fullstack.config)

(def host
  "localhost")

(defn url [scheme path port]
  (str scheme "://" host ":" port "/" path))


(def port
  "In dev mode figwheel provides the server with a path not specified here. When in dev mode it has to be the same as the figwheel server port as specified in project.clj. For prod mode it can be configured here."
  80)

(defn websocket-url []
  (url "ws" "ws" port))

(defn service-url []
  (str "http://localhost:" port "/mocked-service"))
