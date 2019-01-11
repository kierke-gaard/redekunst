(ns fullstack.tubes
  (:require [pneumatic-tubes.core :refer [receiver transmitter dispatch]]
            [pneumatic-tubes.httpkit :refer [websocket-handler]]
            [clojure.core.async :refer [go]]))

(def tx (transmitter))
(def dispatch-to (partial dispatch tx))

(defn local-time []
  (.format (java.time.format.DateTimeFormatter/ofPattern
            "YYYY-MM-dd_hh.mm.ss")
           (java.time.LocalDateTime/now)))

(defn log [message]
  (println message)
  (spit "log__" message))

(def websocket-routes
  (receiver
   {:tubes.log
    (fn [tube [_ message]]
      (log message)
      (dispatch-to tube [:fullstack.events/server-response "done"])
      tube)}))

(defn tube-handler []
  (websocket-handler websocket-routes))
