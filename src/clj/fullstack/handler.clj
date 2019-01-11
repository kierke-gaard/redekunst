(ns fullstack.handler
  (:require [compojure.core :refer [GET defroutes]]
            [compojure.route :refer [resources]]
            [ring.util.response :refer [resource-response]]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.middleware.cors :refer [wrap-cors]]
            [fullstack.tubes :refer [tube-handler]]))

(defroutes routes
  (GET "/" [] (resource-response "index.html" {:root "public"}))
  (GET "/ws" [] (tube-handler))
  (resources "/"))

(def handler routes) ;; (-> #'routes (wrap-cors identity))

(def dev-handler (-> handler
                     wrap-reload))

