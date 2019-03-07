(ns fullstack.metaphor
  (:require [fullstack.config :as config]
            [org.httpkit.client :as http]
            [cheshire.core :as json]))

(defn replace-chars
  [sentence]
  (clojure.string/replace sentence #"[ ,;:.]" "%20"))


(defn analysis
  [sentence]
  (let [request-url (str config/metaphor-server-url
                         "/"
                         (replace-chars sentence))
        response @(http/get request-url)]
    (as->
        response $
        (if (= (:status $) 200) $
            (throw (ex-info "Server error" $)))
        (or (:body $)
            (throw (ex-info "No or empty body" {:response response})))
        (json/decode $ true)
        (or (:score $)
            (throw (ex-info "No score in body")))
        (str "Metaphor with probability " $))))

