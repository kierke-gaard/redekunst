(ns fullstack.handler
  (:require [compojure.core :refer [GET defroutes]]
            [compojure.route :refer [resources]]
            [ring.util.response :refer [resource-response]]
            [ring.middleware.reload :refer [wrap-reload]]
            ;;[ring.middleware.cors :refer [wrap-cors]]
            [fullstack.tubes :refer [tube-handler]]
            [fullstack.mocked-service :as mocked-service]))

(defroutes routes
  (GET "/" [] (resource-response "index.html" {:root "public"}))
  (GET "/ws" [] (tube-handler))
  (GET "/metaphor-analysis-mocked/:sentence"
       [sentence]
       (mocked-service/response sentence))
  (resources "/"))

(def handler routes) ;; (-> #'routes (wrap-cors identity))

(def dev-handler (-> handler
                     wrap-reload))
