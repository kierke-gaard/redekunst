(ns fullstack.mocked-service
  (:require [cheshire.core :as json]
            [fullstack.chat-bot :as chat-bot]))

(def reaction-lookup
  {"Hello" "Hello! how are you today?"
   "Hello!" "Hello, there!"
   "Bye" "Bye"
   "a" "b"
   "Who_is_the_best_developer" "Jan, for sure"
   "What_time_is_it" (.format (java.text.SimpleDateFormat. "hh:mm:ss") (new java.util.Date))})

(defn simple-reaction
  [sentence]
  (get reaction-lookup sentence "Not sure, how to respond."))

(defn response [sentence]
  (println "response sentence" sentence)
  {:status 200
   :headers {"Content-Type" "application/json"}
   :body (json/encode
          {"reaction"
          (simple-reaction sentence)})})



