(ns fullstack.subs
  (:require [re-frame.core :as re-frame]))


;; Queries:
(re-frame/reg-sub
 :sentence
 :sentence)

(re-frame/reg-sub
 :analysis
 ;; The keyword below is actually a shortcut for this function
 ;; (fn [db] (:analysis db)
 :analysis)
