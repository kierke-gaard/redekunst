(ns fullstack.mocked-service
  (:require [cheshire.core :as json]))

(def response-body-json-as-string
  (json/encode {"reaction"
                "Hello, my friend! How are you today?"}))

(defn response [sentence]
  {:status 200
   :headers {"Content-Type" "application/json"}
   :body response-body-json-as-string})



