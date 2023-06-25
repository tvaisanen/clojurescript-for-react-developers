;; core is used often in clojure similarly as index.js in Javascript
(ns app.core
  (:require [helix.core :refer [defnc $]]
            [helix.dom :as d]
            [app.utils :as utils]
            [app.starwars :as sw]
            ["react" :as react]
            ["react-dom/client" :as rdom]))

;; `defnc` macro creates a react component
(defnc App []
  (d/div "This is a React component."))
;; => #object[app$core$App_render ""]

($ App)
;; =>

(defn ^:dev/after-load init
  "This function is used in the `index.html`
  to load the application."
  []
  (utils/render ($ sw/StarwarsApp)))
