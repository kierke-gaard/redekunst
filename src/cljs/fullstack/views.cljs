(ns fullstack.views
  (:require
   [re-frame.core :as re-frame]
   [fullstack.subs :as subs]
   ))

;; FRAME ====================================
;; :suche.core/search-open
;; woco.frame/create-frame
(defn panel [{:keys [:id :x :y :w :h :title]}]
  [:div.optimist__window {:id id
                          :style {:position "absolute"
                                  :left x
                                  :top y
                                  :width w
                                  :height h
                                  :border "1px solid black"}}
   [:div.window__header
    [:div.window__title title]]])


;; FRAMES ==========================================

(def frames [{:id 0, :title "Pick Data", :x 50, :y 100, :w 200, :h 200}
             {:id 1, :title "Display Data", :x 50, :y 400, :w 200, :h 200}])

;; SPA VIEW ========================================

(defn main-panel []
  (let [name (re-frame/subscribe [::subs/name])]
    [:div
     [:div.optimist
      [:div.optimist__header
       [:div.optimist__productbar
        [:div.optimist__userbar
         [:div.optimist__name 
          "Application"]]]]]
     [:div.optimist__workspace {:style {:height "100vh"
                                        :width "100vw"
                                        :overflow :auto}
                                :on-drag-over #(.preventDefault %)
                                :on-drag-enter #(.preventDefault %)
                                :on-drop #(.preventDefault %)}
      (for [f frames]
        ^{:key (:id f)}
        [panel f])]]))
