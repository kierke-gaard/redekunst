(ns fullstack.views
  (:require
   [re-frame.core :as re-frame]
   [fullstack.subs :as subs]
   [fullstack.tubes :as tubes]
   [fullstack.events :as events]
   #_[cljsjs.react-select]))

(defn panel [{:keys [:id :x :y :w :h :title :content]}]
  [:div.fullstack__window {:id id
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
   [:div.row.fullstack__form__row
    [:div.col-11
     [:div.text-input
      [:input {:type "text"
               :value @(re-frame/subscribe [:sentence])
               :on-change #(re-frame/dispatch
                            [::events/sentence-change
                             (-> % .-target .-value)])}]]]]
   [:div.fullstack__form__row.fullstack__form__actions
    [:div.col-4
     [:a.fullstack__button.fullstack__button--primary  
      {:href ""
       :on-click (fn [e]
                   (.preventDefault e)
                   (tubes/dispatch-to-server
                    [:metaphor-analysis
                     @(re-frame/subscribe [:sentence])]))}
      "Detect"]]]])

(defn output-frame []
  [:form
   [:div.row.fullstack__form__row
    [:div.col-11
     [:div.text-input
      [:input {:type "text"
               :value @(re-frame/subscribe [:analysis])
               :on-change #(re-frame/dispatch
                            [::events/analysis-change (-> % .-target .-value)])}]]]]
   [:div.fullstack__form__row.fullstack__form__actions
    [:div.col-4
     [:a.fullstack__button.fullstack__button--primary  
      {:href ""
       :on-click (fn [e]
                   (.preventDefault e)
                   (print "clicked")
                   (tubes/dispatch-to-server [:tubes.log "Message to log"]))}
      "That's right"]
     [:div.col-4
      [:a.fullstack__button.fullstack__button--primary  
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
   [:div.fullstack
    [:div.fullstack__header
     [:div.fullstack__productbar
      [:div.fullstack__userbar
       [:div.fullstack__name 
        "Mercury - an innovative Figure of Speech Detection Service"]]]]]
   [:div.fullstack__workspace {:style {:height "100vh"
                                      :width "100vw"
                                      :overflow :auto}
                              :on-drag-over #(.preventDefault %)
                              :on-drag-enter #(.preventDefault %)
                              :on-drop #(.preventDefault %)}
    (for [f frames]
      ^{:key (:id f)}
      [panel f])]])
