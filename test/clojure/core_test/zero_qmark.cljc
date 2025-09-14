(ns clojure.core-test.zero-qmark
  (:require [clojure.test :as t :refer [deftest testing is are]]
            [clojure.core-test.number-range :as r]
            [clojure.core-test.portability #?(:cljs :refer-macros :default :refer) [when-var-exists]]))

(when-var-exists clojure.core/zero?
  (deftest test-zero?
    (are [expected x] (= expected (zero? x))
      true  0
      false 1
      false -1
      false r/min-int
      false r/max-int
      true  0.0
      false 1.0
      false -1.0
      false r/min-double
      false r/max-double
      false ##Inf
      false ##-Inf
      false ##NaN
      true  0N
      false 1N
      false -1N
      #?@(:cljs []
          :default
          [true  0/2
           false 1/2
           false -1/2])
      true  0.0M
      false 1.0M
      false -1.0M)

    #?@(:jank []
        :default (do
          (is #?@(:cljs [(= false (zero? nil))]
                  :default [(thrown? Exception (zero? nil))]))
          (is #?@(:cljs [(= false (zero? false))]
                  :default [(thrown? Exception (zero? false))]))
          (is #?@(:cljs [(= false (zero? true))]
                  :default [(thrown? Exception (zero? true))]))))))
