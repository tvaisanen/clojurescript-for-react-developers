(ns app.starwars-test
  (:require [cljs.test :refer [testing is]]
            [helix.core :refer [$]]
            [devcards.core :as dc :include-macros true]
            [app.starwars :as sw]))

(dc/defcard people
  [{:name "Luke"} {:name "Chewbacca"} {:name "C-3PO"}])

(dc/deftest filter-by-name
  (let [people [{:name "Luke"} {:name "Chewbacca"} {:name "C-3PO"}]]
    (testing "filter by name works as expected"
      (is (= [{:name "Luke"}]
             (sw/filter-by-name "uke" people))))
    (testing "filter by name works case insensitive"
      (is (= [{:name "Luke"}]
             (sw/filter-by-name "luke" people))))))

(dc/defcard PeopleWithFiltering
  ($ sw/PeopleFiltering))
