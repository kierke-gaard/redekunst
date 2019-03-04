(ns fullstack.handler
  (:require [compojure.core :refer [GET defroutes]]
            [compojure.route :refer [resources]]
            [ring.util.response :refer [resource-response]]
            [ring.middleware.reload :refer [wrap-reload]]
            ;;[ring.middleware.cors :refer [wrap-cors]]
            [fullstack.tubes :refer [tube-handler]]))


(defn app [sentence]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (clojure.string/join \newline
                              ["Analyzing the sentence"
                               (str "\"" sentence "\"")
                               "It turns out, that it contains a metaphor!"])})

(defroutes routes
  (GET "/" [] (resource-response "index.html" {:root "public"}))
  (GET "/ws" [] (tube-handler))
  ;; sample usage
  ;; http://localhost:3448/metaphor-analysis/how%20are%20you%20today
  (GET "/metaphor-analysis/:sentence" [sentence]
       (app sentence))
  (resources "/"))

(def handler routes) ;; (-> #'routes (wrap-cors identity))

(def dev-handler (-> handler
                     wrap-reload))
