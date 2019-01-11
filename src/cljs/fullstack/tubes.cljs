(ns fullstack.tubes
  (:require [re-frame.core :as re-frame]
            [pneumatic-tubes.core :as tubes]))

(defn on-receive [event]
  #_(.log js/console "received from server:" (str event))
  (re-frame/dispatch event))

;; better retrieve from config or env
(def websocket-url
  "ws://localhost:3448")

(def tube (tubes/tube
           websocket-url
           on-receive))

(defn dispatch-to-server [event]
  (tubes/dispatch tube event))

(def send-to-server (re-frame/after
                     (fn [_ event] (dispatch-to-server event))))

(tubes/create! tube)

(re-frame/reg-fx :tubes
                 (fn [event]
                   (tubes/dispatch tube event)))

