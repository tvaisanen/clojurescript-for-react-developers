(ns app.components.list-with-details
  (:require [helix.core :refer [defnc $]]
            [helix.dom :as d]
            [helix.hooks :as hooks]))

(defnc ListWithDetails
  [{:keys [people on-click]}]
  ;; define state for selecting the detail
  ;; hooks/use-state is a wrapper for React.useState
  ;; we could also use React.useState here and it would work
  (let [[selected set-selected] (hooks/use-state nil)]
    (d/div
     (d/h1 "Starwars People")
     (d/ul
      ;; iterate over people and create <li>{name}</li> for each
      (for [{:keys [name] :as person} people]
        (d/li {:key name
               ;; on click detail, set the clicked person as selected
               :on-click #(set-selected person)} name)))
     ;; if person selected show the details
     (when selected
       (d/div
        (:details selected))))))
