(ns fullstack.tubes
  "Namesspace to setup a websocket connection between client and server. Here just a single connection is setup, called tube."
  (:require [re-frame.core :as re-frame]
            [pneumatic-tubes.core :as tubes]
            [fullstack.config :as config]))

(defn on-receive [event]
  #_(.log js/console "received from server:" (str event))
  (re-frame/dispatch event))

;; better retrieve from config or env
(def websocket-url
  #_"ws://localhost:3448/ws"
  config/websocket-url)

(print "websocket-url" config/websocket-url)

(def tube (tubes/tube
           websocket-url
           on-receive))


;; function to send events to server
(defn dispatch-to-server [event]
  (tubes/dispatch tube event))

#_(def send-to-server (re-frame/after
                     (fn [_ event] (dispatch-to-server event))))

(tubes/create! tube)


;; Effect handler for handing over events to server
(re-frame/reg-fx :tubes
                 (fn [event]
                   (print "tube" tube "event" event)
                   (tubes/dispatch tube event)))

;; Event to forward events to server
(re-frame/reg-event-fx
 ::tubes
 (fn [{db :db} [_ event]]
   (print "dispatch event to server")
   {:tubes event}))
