(ns app.starwars
  (:require [helix.core :refer [defnc $]]
            [helix.dom :as d]
            [helix.hooks :as hooks]
            [clojure.pprint :refer [pprint]]
            [clojure.string :as str]
            ["@tanstack/react-query" :as react-query]))

;; utilities

(defn filter-by-name [search people]
  (filter (fn [person]
            (str/includes? (:name person)
                           search))
          people))

;; Example # 1
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(def people [{:name "Luke" :details "Luke's story"}
             {:name "Chewbacca" :details "Chwebacca's story"}
             {:name "C-3PO" :details "C-3PO's story"}])

(defnc People
  []
  ;; define state for selecting the detail
  (let [[selected set-selected] (hooks/use-state nil)]
    (d/div
     (d/h1 "Starwars People")
     (d/ul
      ;; iterate over people and create <li>{name}</li> for each
      (for [{:keys [name] :as person} people]
        (d/li {:key      name
               ;; on click detail, set the clicked person as selected
               :on-click #(set-selected person)} name)))
     ;; if person selected show the details
     (when selected
       (d/div
        (:details selected))))))

;; Example # 2
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

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

(defnc PeopleFromAPI
  []
  (let [[people set-people]     (hooks/use-state [])
        [selected set-selected] (hooks/use-state nil)]

    ;; React.useEffect
    (hooks/use-effect
     ;; run this hook only once
     :once
     (fn [] ;; fetch the people
       (-> (fetch-people)
           ;; and set-people with the result data
           (.then set-people))))
    (d/div
     (d/h1 "Starwars People")
     (d/ul
      (for [{:keys [name] :as person} people]
        (d/li {:key      name
               :on-click #(set-selected person)} name)))
     (when selected
       (d/pre
        ;; think this as JSON.stringify(selected)
        (with-out-str (pprint selected)))))))

;; Example # 2
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn use-query
  "create clojure wrapper for useQuery"
  [query-key query-fn]
  (let [result (react-query/useQuery
                ;; useQuery is expecting a JS object
                ;; instead of CLJS map
                #js {:queryFn  query-fn
                     :queryKey (into-array query-key)})]
    {:data result.data
     :loading? result.isLoading}))


(defnc PeopleWithReactQuery
  []
  (let [[selected set-selected] (hooks/use-state nil)
        ;; use react-query to handle the query state
        {people :data loading? :loading?} (use-query ["people"] fetch-people)]
    (if loading?
      (d/div "Loading...")
      (d/div
       (d/h1 "Starwars People")
       (d/ul
        (for [{:keys [name] :as person} people]
          (d/li {:key      name
                 :on-click #(set-selected person)} name)))
       (when selected
         (d/pre
          (with-out-str
            (pprint selected))))))))

;; define the query client
(defonce query-client (react-query/QueryClient. ))

;; wrap the component with QueryClientProvider
(defnc WrapQueryClient [{:keys [children]}]
  ($ react-query/QueryClientProvider {:client query-client}
     children))

;; and it's a wrap
(defnc StarwarsApp []
  ($ WrapQueryClient
     ($ PeopleWithReactQuery)))

;; With filter
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defnc PeopleFiltering
  []
  ;; define state for selecting the detail
  (let [[selected set-selected] (hooks/use-state nil)
        [filter set-filter] (hooks/use-state "")]
    (d/div
     (d/h1 "Starwars People")
     (d/input {:on-change (fn [e] (set-filter e.target.value))})
     (d/ul
      ;; iterate over people and create <li>{name}</li> for each
      (for [{:keys [name] :as person} (filter-by-name filter people)]
        (d/li {:key      name
               ;; on click detail, set the clicked person as selected
               :on-click #(set-selected person)} name)))
     ;; if person selected show the details
     (when selected
       (d/div
        (:details selected))))))
