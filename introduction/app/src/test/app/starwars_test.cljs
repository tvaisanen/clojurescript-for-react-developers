(ns app.starwars-test
  (:require [cljs.test :refer [testing is]]
            [helix.core :refer [$]]
            [devcards.core :as dc :include-macros true]
            [app.starwars :as sw]))

(dc/deftest filter-by-name
  (testing "filter by name works as expected"
    (is (= [{:name "Luke"}]
           (sw/filter-by-name "uke" [{:name "Luke"} {:name "Chewbacca"} {:name "C-3PO"}])))))


(dc/defcard FOO
  ($ sw/PeopleFiltering))
