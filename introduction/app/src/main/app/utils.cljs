(ns app.utils
  (:require [helix.core :refer [$]]
            ["react" :as react]
            ["react-dom/client" :as rdom]))

(defn app-container []
  (js/document.getElementById "app"))

(defonce root (atom nil))

(defn react-root []
  (when-not @root
    (reset! root (rdom/createRoot (app-container))))
  @root)

(defn render
  [App]
  (.render (react-root)
           ($ react/StrictMode
              ($ App))))
