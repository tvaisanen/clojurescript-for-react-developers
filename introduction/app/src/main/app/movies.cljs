(ns app.movies
  (:require [app.http :as http]
            [app.query :as query]
            [clojure.string :as str]
            [cljs-bean.core :refer [bean]]
            [helix.core :refer [defnc $]]
            [helix.dom :as d]
            ["react" :as react]
            ["react-router-dom" :refer [useNavigate
                                        useParams]]))

(def url  "https://swapi.dev/api/films")

(defn movie-detail-url [id]
  (str url "/" id "/"))

(defnc MovieList []
  (let [navigate (useNavigate)
        {:keys [data]} (query/use-query {:query-key [url]
                                         :query-fn #(http/fetch url)})]
    (d/ul
     (for [film (:results data)]
       (let [resource-id (last (str/split (:url film) #"/"))]
         (d/li {:key (:id film)
                :on-click #(navigate (str "/movies/" resource-id))}
               (d/strong (:title film))
               (d/p " by " (:director film))))))))

(defnc MovieDetail []
  (let [params (bean (useParams))
        movie-detail-url (movie-detail-url (:id params))
        query-params {:query-key [movie-detail-url]
                      :query-fn #(http/fetch movie-detail-url)}
        {movie :data} (query/use-query query-params) ]
    (d/pre (str movie))))
