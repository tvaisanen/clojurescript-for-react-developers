(ns app.starwars-test
  (:require [cljs.test :refer [deftest testing is]]
            [app.starwars :as sw]))

(deftest test-example
  (testing "assertion works"
    (is (= 0 1))))

(deftest filter-by-name
  (testing "filter by name works as expected"
    (let [people [{:name "Luke"} {:name "Chewbacca"} {:name "C-3PO"}]
          expected [{:name "Luke"}]]
    (is (= expected
           (sw/filter-by-name "uke" people))))))
