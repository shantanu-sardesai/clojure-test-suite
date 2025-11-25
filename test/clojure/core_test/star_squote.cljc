(ns clojure.core-test.star-squote
  (:require [clojure.test :as t :refer [are deftest is]]
            [clojure.core-test.number-range :as r]
            [clojure.core-test.portability #?(:cljs :refer-macros :default :refer) [when-var-exists]]))

(when-var-exists *'
  (deftest test-*'
    (are [prod cand er] (= prod (*' cand er))
      0 0 0
      0 1 0
      0 0 1
      1 1 1
      5 1 5
      5 5 1
      25 5 5
      -1 1 -1
      -1 -1 1
      1 -1 -1
      0 -1 0
      0 0 -1
      (inc r/min-int) r/max-int -1
      (inc r/min-int) -1 r/max-int

      0.0 0.0 0.0
      0.0 1.0 0.0
      0.0 0.0 1.0
      1.0 1.0 1.0
      5.0 1.0 5.0
      5.0 5.0 1.0
      25.0 5.0 5.0
      -1.0 1.0 -1.0
      -1.0 -1.0 1.0
      1.0 -1.0 -1.0
      0.0 -1.0 0.0
      0.0 0.0 -1.0

      0.0 0.0 0
      0.0 1.0 0
      0.0 0.0 1
      1.0 1.0 1
      5.0 1.0 5
      5.0 5.0 1
      25.0 5.0 5
      -1.0 1.0 -1
      -1.0 -1.0 1
      1.0 -1.0 -1
      0.0 -1.0 0
      0.0 0.0 -1

      0.0 0 0.0
      0.0 1 0.0
      0.0 0 1.0
      1.0 1 1.0
      5.0 1 5.0
      5.0 5 1.0
      25.0 5 5.0
      -1.0 1 -1.0
      -1.0 -1 1.0
      1.0 -1 -1.0
      0.0 -1 0.0
      0.0 0 -1.0

      0 0 1N
      0 0N 1
      0 0N 1N
      1 1N 1
      1 1 1N
      1 1N 1N
      5 1 5N
      5 1N 5
      5 1N 5N)

    #?(:jank []
       :default [(is (thrown? #?(:cljs :default :clj Exception :cljr Exception) (*' 1 nil)))
                 (is (thrown? #?(:cljs :default :clj Exception :cljr Exception) (*' nil 1)))])

    (is (#?@(:jank [cpp/jank.runtime.is_big_integer] :default [instance? clojure.lang.BigInt]) (*' 0 1N)))
    (is (#?@(:jank [cpp/jank.runtime.is_big_integer] :default [instance? clojure.lang.BigInt]) (*' 0N 1)))
    (is (#?@(:jank [cpp/jank.runtime.is_big_integer] :default [instance? clojure.lang.BigInt]) (*' 0N 1N)))
    (is (#?@(:jank [cpp/jank.runtime.is_big_integer] :default [instance? clojure.lang.BigInt]) (*' 1N 1)))
    (is (#?@(:jank [cpp/jank.runtime.is_big_integer] :default [instance? clojure.lang.BigInt]) (*' 1 1N)))
    (is (#?@(:jank [cpp/jank.runtime.is_big_integer] :default [instance? clojure.lang.BigInt]) (*' 1N 1N)))
    (is (#?@(:jank [cpp/jank.runtime.is_big_integer] :default [instance? clojure.lang.BigInt]) (*' 1 5N)))
    (is (#?@(:jank [cpp/jank.runtime.is_big_integer] :default [instance? clojure.lang.BigInt]) (*' 1N 5)))
    (is (#?@(:jank [cpp/jank.runtime.is_big_integer] :default [instance? clojure.lang.BigInt]) (*' 1N 5N)))

    (is (#?@(:jank [cpp/jank.runtime.is_big_integer] :default [instance? clojure.lang.BigInt]) (*' -1 r/min-int)))
    (is (#?@(:jank [cpp/jank.runtime.is_big_integer] :default [instance? clojure.lang.BigInt]) (*' r/min-int -1)))
    #?(:jank nil ;; Currently `long` hasn't been ported in jank.
       :default (is (#?@(:jank [cpp/jank.runtime.is_big_integer] :default [instance? clojure.lang.BigInt]) (*' (long (/ r/min-int 2)) 3))))
    #?(:jank nil
       :default (is (#?@(:jank [cpp/jank.runtime.is_big_integer] :default [instance? clojure.lang.BigInt]) (*' 3 (long (/ r/min-int 2))))))))
