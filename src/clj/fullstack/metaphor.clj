(ns fullstack.metaphor
  (:require [org.httpkit.client :as http]))


(def service-url
  "http://localhost:3448/metaphor-analysis")

(defn analysis [service-url sentence]
  (let [request-url (str service-url "/" sentence)
        response (:body @(http/get request-url))]
    response))

;;sample usage
#_(analysis service-url "how%20are%20you%20today")
