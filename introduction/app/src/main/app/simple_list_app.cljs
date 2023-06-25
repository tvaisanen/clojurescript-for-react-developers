(ns app.simple-list-app
  (:require [helix.core :refer [defnc $]]
            [helix.dom :as d]
            ["react" :as react]
            ["react-dom/client" :as rdom]))

(defnc App []
  (d/div "This is a React component."))

(defn app-container []
  (js/document.getElementById "app"))

(defonce root (atom nil))

(defn react-root []
  (when-not @root
    (reset! root (rdom/createRoot (app-container))))
  @root)

(defn ^:dev/after-load init
  []
  (.render (react-root)
           ($ react/StrictMode ($ App))))
