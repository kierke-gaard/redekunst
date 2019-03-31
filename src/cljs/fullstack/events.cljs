(ns fullstack.events
  (:require
   [re-frame.core :as re-frame]
   [fullstack.db :as db]))


;; Define event-handlers
(re-frame/reg-event-db
 ::initialize
 (fn [_ _]
   {:sentence "Hello!"
    :analysis "Not chatty yet :( "}))

(re-frame/reg-event-db
 ::sentence-change
 (fn [db [_ new-sentence]]
   (assoc db :sentence new-sentence)))

(re-frame/reg-event-db
 ::analysis-change
 (fn [db [_ new-analysis]]
   (assoc db :analysis new-analysis)))

(re-frame/reg-event-fx
 ::server-response
 (fn [_ [_ response]]
   (print response)))

;; comments for extending service to speech: new events for recording/ speech-ebents
;;   ::start-recording
;; ::stop-recording -> ::react-on-speech

#_(re-frame/reg-event-db
 ::react-on-speech
 (fn [db [_ speech-seq]]
    (let [speech-to-text-result (analysis-web-servive speech-seq)]
      (assoc db :sentence #_:latest-speech-as-text speech-to-text-result))))
