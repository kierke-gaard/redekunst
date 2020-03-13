(defproject fullstack "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/clojurescript "1.10.597"]
                 [reagent "0.8.1"]
                 [re-frame "0.12.0"]
                 [compojure "1.6.1"]
                 [yogthos/config "1.1.7"]
                 [ring "1.8.0"]
                 [org.clojure/core.async "1.0.567"]
                 [http-kit "2.3.0"]
                 [hiccup "1.0.5"]
                 [day8.re-frame/http-fx "0.1.6"]
                 [pneumatic-tubes "0.3.0"
                  :exclusions [com.cognitect/transit-cljs]]
                 [cheshire "5.10.0"]]

  :plugins [[lein-cljsbuild "1.1.7"]
            [lein-npm "0.6.2"]]

  :npm {:dependencies []
        :root "resources/public/js"}

  :min-lein-version "2.5.3"

  :source-paths ["src/clj" "src/cljs" "src/cljc"]

  :clean-targets ^{:protect false} ["resources/public/js/compiled"
                                    "target"
                                    "test/js"]

  :profiles
  {:dev
   {:dependencies [[binaryage/devtools "1.0.0"]
                   [day8.re-frame/re-frame-10x "0.3.3"]
                   [day8.re-frame/tracing "0.5.1"]
                   [figwheel-sidecar "0.5.19"]]

    :plugins      [[lein-figwheel "0.5.19"]
                   [lein-doo "0.1.11"]]
    :figwheel {:css-dirs ["resources/public/css"]
               :ring-handler fullstack.handler/handler
               :reload-clj-files {:clj true :cljc true}
               :server-port 3448}

    :main fullstack.dev-server
    :aot [fullstack.dev-server]
    }

   :uberjar {:source-paths ["env/prod/clj"]
             :dependencies [[day8.re-frame/tracing-stubs "0.5.3"]]
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
     :figwheel     {:on-jsload "fullstack.core/init"}
     :compiler     {:main                 fullstack.core
                    :output-to            "resources/public/js/compiled/app.js"
                    :output-dir           "resources/public/js/compiled/out"
                    :asset-path           "js/compiled/out"
                    :source-map-timestamp true
                    :preloads             [day8.re-frame-10x.preload]
                    :closure-defines      {"re_frame.trace.trace_enabled_QMARK_" true
                                           "day8.re_frame.tracing.trace_enabled_QMARK_" true}
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
