(ns app.movies
  (:require [app.http :as http]
            [helix.core :refer [defnc $]]
            [helix.dom :as d]
            [helix.hooks :as hooks]
            ["react" :as react]))

(defnc MovieList []
  (let [[movies set-movies] (react/useState [])]

    (hooks/use-effect
     :once
     (-> (http/fetch "https://swapi.dev/api/films")
         (.then (fn [data]
                  (set-movies (:results data))))))

    (d/ul
     (for [film movies]
       (d/li
        (d/strong (:title film))
        (d/p " by " (:director film)))))))
