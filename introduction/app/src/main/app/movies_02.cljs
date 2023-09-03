(ns app.movies
  (:require [app.http :as http]
            [app.query :as query]
            [helix.core :refer [defnc $]]
            [helix.dom :as d]
            ["react" :as react]))

(def url  "https://swapi.dev/api/films")

(defnc MovieList []
  (let [{:keys [data]} (query/use-query {:query-key [url]
                                         :query-fn #(http/fetch url)})]
    (d/ul
     (for [film (:results data)]
       (d/li
        (d/strong (:title film))
        (d/p " by " (:director film)))))))
