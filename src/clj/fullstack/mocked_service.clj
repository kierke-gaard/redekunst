(ns fullstack.mocked-service
  (:require [cheshire.core :as json]))

(def response-body-json-as-string
  (json/encode {"score" 1.0,
                "data" {"paraphrase" ["unpleasant",
                                      "dishonorable",
                                      "unwanted",
                                      "inopportune",
                                      "SpecialBookSources/ 9780595239832",
                                      nil,
                                      "wJeffrey: Boam",
                                      nil,
                                      "Special:BookSources/9781475997132",
                                      nil,
                                      "w:Gail Bowen",
                                      "Special:BookSources/9781551995366",
                                      nil],
                        "url" "/wiki/a_bad_penny_always_turns_up#English",
                        "sentiment" "Nothing",
                        "sentence" "a bad penny always turns up",
                        "explanation" "Nothing",
                        "type" "PROVERB",
                        "alternative_forms" ["a bad penny always comes back",
                                             "the bad penny always comes back",
                                             "the bad penny always turns up",
                                             "bad penny"],
                        "metanet" "Nothing",
                        "frames" "Nothing"}}))

(defn response [sentence]
  {:status 200
   :headers {"Content-Type" "application/json"}
   :body response-body-json-as-string})



