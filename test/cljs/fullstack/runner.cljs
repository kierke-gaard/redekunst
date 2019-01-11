(ns fullstack.runner
    (:require [doo.runner :refer-macros [doo-tests]]
              [fullstack.core-test]))

(doo-tests 'fullstack.core-test)
