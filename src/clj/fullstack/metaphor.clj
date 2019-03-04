(ns fullstack.metaphor
  (:require [org.httpkit.client :as http]
            [cheshire.core :as json]))


(def test-url
  "http://localhost:3448/metaphor-analysis")

(def julia-server-url
  "http://localhost:8000")

(defn replace-spaces
  [sentence]
  (clojure.string/replace sentence " " "%20"))

(defn analysis
  [service-url sentence]
  (let [request-url (str service-url "/"
                         (replace-spaces sentence))
        response @(http/get request-url)]
    response))





;;sample usage
#_(analysis service-url "how%20are%20you%20today")
#_(def r (analysis julia-server-url "Ignorance is bliss"))
#_(def j (-> r
           :body
           json/parse-string))
;;(:opts :body :headers :status)

