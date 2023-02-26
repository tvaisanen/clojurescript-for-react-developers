;; core is used often in clojure similarly as index.js in Javascript
(ns app.core
  (:require [helix.core :refer [defnc $]]
            [helix.dom :as d]
            [app.starwars :as sw]
            ["react" :as react]
            ["react-dom/client" :as rdom]))

;; `defnc` macro creates a react component
(defnc App []
  (d/div "This is a React component."))

(defn app-container []
  (js/document.getElementById "app"))

(defonce root (atom nil))

(defn react-root []
  (when-not @root
    ;; createRoot should be done only once so
    ;; let's store the reference to `root`
    ;; if not already exist
    (reset! root (rdom/createRoot (app-container))))
  @root)

;; add ^:dev/after-load to re-mount the app on file change
;; OR update the shadow-cljs.edn config file to run
;; app.core.init on after-load
(defn ^:dev/after-load init
  "This function is used in the `index.html`
  to load the application."
  []
  (.render (react-root)
           ;; `$` is a macro to make a React
           ;; element out of the given component
           ($ react/StrictMode ($ sw/StarwarsApp))))
