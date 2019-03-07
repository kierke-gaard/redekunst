(ns fullstack.events
  (:require
   [re-frame.core :as re-frame]
   [fullstack.db :as db]))


;; Define event-handlers
(re-frame/reg-event-db
 ::initialize
 (fn [_ _]
   {:sentence "When looking outside the windows, I have to say that it is raining cats and dogs."
    :analysis "There is figure of speech."}))

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
