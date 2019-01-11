(ns fullstack.views
  (:require
   [re-frame.core :as re-frame]
   [fullstack.subs :as subs]
   [fullstack.tubes :as tubes]))

;; FRAME ====================================
;; :suche.core/search-open
;; woco.frame/create-frame
(defn panel [{:keys [:id :x :y :w :h :title :content]}]
  [:div.optimist__window {:id id
                          :style {:position "absolute"
                                  :left x
                                  :top y
                                  :width w
                                  :height h
                                  :border "1px solid black"}}
   [:div.window__header
    [:div.window__title title]]
   [:div.windows__body
    [content]]])

(defn pick-frame []
  [:form
   [:div.row.optimist__form__row
    [:div.col-4
     [:label.optimist__form__label.input--w12
      {:for "input-select"} "Problem Type"]]
    [:div.col-8
     [:label.optimist__form__label.input--w12
      {:for "input-select"} "chosen Type"]]]
   [:dib.row.optimist__form__row
    [:div.col-4g
     [:label.optimist__form__label.input--w12
      {:for "input-select"} "Model Type"]]
    [:div.col-8
     [:label.optimist__form__label.input--w12
      {:for "input-select"} "chosen Type"]]]

   [:div.optimist__form__row.optimist__form__actions
    [:div.col-4
     [:a.optimist__button.optimist__button--primary
      {:href ""
       :on-click (fn [e]
                   (.preventDefault e)
                   (print "clicked")
                   #_(re-frame/dispatch [::tubes/tubes [:tubes.log "I'm here"]])
                   (tubes/dispatch-to-server [:tubes.log "Message to log"]))}
      "Ok"]]]])


(defn display-frame []
  (pick-frame))

;; FRAMES ==========================================

(def frames [{:id 0, :title "Pick Data", :content pick-frame
              :x 50, :y 100, :w 350, :h 300}
             #_{:id 1, :title "Display Data", :content display-frame
              :x 50, :y 400, :w 300, :h 230}])

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
