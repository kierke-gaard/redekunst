(ns fullstack.core
  (:require
   [reagent.core :as reagent]
   [re-frame.core :as re-frame]
   [fullstack.events :as events]
   [fullstack.views :as views]))


(def debug?
  ^boolean goog.DEBUG)


(defn dev-setup []
  (when debug?
    (enable-console-print!)
    (println "dev mode")))

(defn mount-root []
  (re-frame/clear-subscription-cache!)
  (re-frame/dispatch-sync [::events/initialize])
  (reagent/render [views/main-panel]
                  (.getElementById js/document "app")))

(defn ^:export init []
  (dev-setup)
  (mount-root))

(mount-root)
