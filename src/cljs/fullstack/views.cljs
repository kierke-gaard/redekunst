(ns fullstack.views
  (:require
   [re-frame.core :as re-frame]
   [fullstack.subs :as subs]
   [fullstack.tubes :as tubes]
   [fullstack.events :as events]
   #_[cljsjs.react-select]))

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

(defn input-frame []
  [:form
   [:div.row.optimist__form__row
    [:div.col-8
     [:div.text-input
      [:input {:type "text"
               :value @(re-frame/subscribe [:sentence])
               :on-change #(re-frame/dispatch
                            [::events/sentence-change
                             (-> % .-target .-value)])}]]]]
   [:div.optimist__form__row.optimist__form__actions
    [:div.col-4
     [:a.optimist__button.optimist__button--primary  
      {:href ""
       :on-click (fn [e]
                   (.preventDefault e)
                   (print "clicked")
                   (tubes/dispatch-to-server
                    [:tubes.log @(re-frame/subscribe [:sentence])]))}
      "Detect"]]]])

(defn output-frame []
  [:form
   [:div.row.optimist__form__row
    [:div.col-8
     [:div.text-input
      [:input {:type "text"
               :default-value @(re-frame/subscribe [:analysis])}]]]]
   [:div.optimist__form__row.optimist__form__actions
    [:div.col-4
     [:a.optimist__button.optimist__button--primary  
      {:href ""
       :on-click (fn [e]
                   (.preventDefault e)
                   (print "clicked")
                   (tubes/dispatch-to-server [:tubes.log "Message to log"]))}
      "That's right"]
     [:div.col-4
      [:a.optimist__button.optimist__button--primary  
       {:href ""
        :on-click (fn [e]
                    (.preventDefault e)
                    (print "clicked")
                    (tubes/dispatch-to-server [:tubes.log "Message to log"]))}
       "Incorrect"]]]]])

(def frames [{:id 0, :title "Type your sentence here ...",
              :content input-frame :x 50, :y 100, :w 400, :h 200}
             {:id 1, :title "... and we tell you if there is figure of speech",
              :content output-frame :x 500, :y 100, :w 400, :h 200}])

(defn main-panel []
  [:div
   [:div.optimist
    [:div.optimist__header
     [:div.optimist__productbar
      [:div.optimist__userbar
       [:div.optimist__name 
        "Mercury - an innovative Figure of Speech Detection Service"]]]]]
   [:div.optimist__workspace {:style {:height "100vh"
                                      :width "100vw"
                                      :overflow :auto}
                              :on-drag-over #(.preventDefault %)
                              :on-drag-enter #(.preventDefault %)
                              :on-drop #(.preventDefault %)}
    (for [f frames]
      ^{:key (:id f)}
      [panel f])]])
