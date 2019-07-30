(defproject fullstack "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.10.238"]
                 [reagent "0.7.0"]
                 [re-frame "0.10.5"]
                 [ns-tracker "0.3.1"]
                 [compojure "1.5.0"]
                 [yogthos/config "0.8"]
                 [ring "1.4.0"]
                 ;;added manually after template dependencies
                 [org.clojure/core.async "0.3.465"]
                 [http-kit "2.3.0"]
                 [hiccup "1.0.5"]
                 [day8.re-frame/http-fx "0.1.3"]
                 [pneumatic-tubes "0.3.0"
                  :exclusions [com.cognitect/transit-cljs]]
                 [cheshire "5.8.1"]
                 #_[cljsjs/react-select "1.2.1-1"]]

  :plugins [[lein-cljsbuild "1.1.7"]
            [lein-npm "0.6.2"]]

  :npm {:dependencies []
        :root "resources/public/js"}

  :min-lein-version "2.5.3"

  :source-paths ["src/clj" "src/cljs" "src/cljc"]

  ;;:test-path ["test/clj"]

  :clean-targets ^{:protect false} ["resources/public/js/compiled"
                                    "target"
                                    "test/js"]

  :profiles
  {:dev
   {:dependencies [[binaryage/devtools "0.9.10"]
                   [day8.re-frame/re-frame-10x "0.3.3"]
                   [day8.re-frame/tracing "0.5.1"]
                   [figwheel-sidecar "0.5.13"]]

    :plugins      [[lein-figwheel "0.5.16"]
                   [lein-doo "0.1.8"]]
    :figwheel {:css-dirs ["resources/public/css"]
               :ring-handler fullstack.handler/handler
               :reload-clj-files {:clj true :cljc true}
               :server-port 3448}

    ;; :source-paths ["profile/dev/clj"]
    :main fullstack.dev-server
    :aot [fullstack.dev-server]}
   ;;:prod { :dependencies [[day8.re-frame/tracing-stubs "0.5.1"]]}
   :uberjar {:source-paths ["env/prod/clj"]
             :dependencies [[day8.re-frame/tracing-stubs "0.5.1"]]
             :omit-source  true
             :main         fullstack.server
             :aot          [fullstack.server]
             :uberjar-name "fullstack.jar"
             :prep-tasks   ["clean" ["cljsbuild" "once" "min"] ["npm" "install"] "compile"]}
   }

  :cljsbuild
  {:builds
   [{:id           "dev"
     :source-paths ["src/cljs" "src/cljc"]
     :figwheel     {:on-jsload "fullstack.core/mount-root"}
     :compiler     {:main                 fullstack.core
                    :output-to            "resources/public/js/compiled/app.js"
                    :output-dir           "resources/public/js/compiled/out"
                    :asset-path           "js/compiled/out"
                    :source-map-timestamp true
                    :preloads             [devtools.preload
                                           day8.re-frame-10x.preload]
                    :closure-defines      {"re_frame.trace.trace_enabled_QMARK_" true
                                           "day8.re_frame.tracing.trace_enabled_QMARK_" true}
                    :external-config      {:devtools/config {:features-to-install :all}}
                    }}

    {:id           "min"
     :source-paths ["src/cljs" "src/cljc"]
     :jar true
     :compiler     {:main            fullstack.core
                    :output-to       "resources/public/js/compiled/app.js"
                    :output-dir      "resources/public/js/compiled/prod"
                    :asset-path      "js/compiled/prod"
                    :optimizations   :none
                    :closure-defines {goog.DEBUG false}
                    :pretty-print    false}}

    {:id           "test"
     :source-paths ["src/cljs" "src/cljc" "test/cljs"]
     :compiler     {:main          fullstack.runner
                    :output-to     "resources/public/js/compiled/test.js"
                    :output-dir    "resources/public/js/compiled/test/out"
                    :optimizations :none}}
    ]}

  :main fullstack.server

  :aot [fullstack.server]
  
  :uberjar-name "fullstack.jar"

  :prep-task ["clean"
              ["cljsbuild" "once" "min"]
              ["npm" "install"] ;; activate only in case of npm dependencies
              "compile"])
