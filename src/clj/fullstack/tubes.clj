(ns fullstack.tubes
  (:require [pneumatic-tubes.core :refer [receiver transmitter dispatch]]
            [pneumatic-tubes.httpkit :refer [websocket-handler]]
            [clojure.core.async :refer [go]]))

(def tx (transmitter))
(def dispatch-to (partial dispatch tx))

(def websocket-routes
  (receiver
   {:fullstack.client/test-event
    (fn [tube [_ arg1 arg2]]
      (go (println "test-event")
          ;;do stuff
          #_(dispatch-to tube [:fullstack.client/response "done"]))
      tube)}))

(defn tube-handler []
  (websocket-handler websocket-routes))
