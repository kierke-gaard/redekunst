(ns fullstack.tubes
  (:require [pneumatic-tubes.core :refer [receiver transmitter dispatch]]
            [pneumatic-tubes.httpkit :refer [websocket-handler]]
            [clojure.core.async :refer [go]]
            [fullstack.metaphor :as metaphor]))

(def tx (transmitter))
(def dispatch-to (partial dispatch tx))

(defn local-time []
  (.format (java.time.format.DateTimeFormatter/ofPattern
            "YYYY-MM-dd_hh.mm.ss")
           (java.time.LocalDateTime/now)))

(defn log [message]
  (println message)
  #_(spit "log__" message))

(def websocket-routes
  (receiver
   {:tubes.log
    (fn [tube [_ message]]
      (log message)
      (dispatch-to tube [:fullstack.events/server-response (str "Server received:" message)])
      tube)
    :metaphor-analysis
    (fn [tube [_ sentence]]
      (let [analysis (metaphor/analysis sentence)]
        (log (str ":metaphor-analysis of sentence: "
                  sentence
                  "\n  result: " analysis))        
        (dispatch-to tube [:fullstack.events/analysis-change
                           analysis])
        tube))}))

(defn tube-handler []
  (websocket-handler websocket-routes))
