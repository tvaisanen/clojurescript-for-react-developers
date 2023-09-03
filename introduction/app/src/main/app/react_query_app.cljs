(ns app.react-query-app
  (:require [helix.core :refer [defnc $]]
            [helix.dom :as d]
            [app.utils :as utils]
            [app.query :refer [query-client-provider
                               query-client
                               use-query]]
            ["react" :as react]
            ["react-dom/client" :as rdom]
            [helix.hooks :as hooks]
            [clojure.pprint :refer [pprint]]
            ["@tanstack/react-query" :as react-query]))

(defn fetch-people
  "
  Just a wrapper for

  fetch(URL)
     .then((response) => response.json())
     .then((data) => console.log(data))"
  []
  (-> (js/fetch "https://swapi.dev/api/people")
      (.then (fn [response] (.json response)))
      (.then (fn [data]
               (js->clj data.results
                        :keywordize-keys true)))))


;; TODO make this working

(defnc PeopleWithReactQuery
  []
  (let [[selected set-selected] (hooks/use-state nil)
        ;; use react-query to handle the query state
        {people :data
         loading? :loading?} (use-query ["people"] fetch-people)]
    (if loading?
      (d/div "Loading...")
      (d/div
       (d/h1 "Starwars People")
       (d/ul
        (for [{:keys [name] :as person} people]
          (d/li {:key
                 name
                 :on-click #(set-selected person)} name)))
       (when selected
         (d/pre
          (with-out-str
            (pprint selected))))))))

(defnc WrapQueryClient [{:keys [children]}]
  ($ query-client-provider {:client query-client}
     children))

;; and it's a wrap
(defnc QueryApp []
  ($ WrapQueryClient
     ($ PeopleWithReactQuery)))

(defn ^:dev/after-load init
  []
  (utils/render ($ QueryApp)))
