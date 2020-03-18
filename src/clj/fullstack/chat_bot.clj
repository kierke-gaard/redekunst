(ns fullstack.chat-bot
  (:require [fullstack.config :as config]
            [org.httpkit.client :as http]
            [cheshire.core :as json]))

(defn replace-chars
  [sentence]
  (clojure.string/replace sentence #"[ ,;:.]" "%20"))


(defn reaction
  [sentence]
  (let [request-url (str (config/service-url)
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
        (or (:reaction $)
            (throw (ex-info "No reaction in body")))
        (str $))))
