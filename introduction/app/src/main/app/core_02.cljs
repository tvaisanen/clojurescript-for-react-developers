(ns app.core
  (:require [helix.core :refer [defnc $]]
            [helix.dom :as d]
            [app.router :as router]
            [app.nav :as nav]
            [app.utils :as utils]))

(def routes
  [{:to "/movies"
    :label "movies"
    :element (d/div "movies")}
   {:to "/people"
    :label "people"
    :element (d/div "people")}])

(defnc Layout []
  (d/div
   ($ nav/Navbar {:navlinks routes})
   ($ router/RouteContent)))

(defnc App []
  ($ router/WrapRouter
     {:routes [{:path "/"
                :element ($ Layout)
                :children (for [{:keys [to element]} routes]
                            {:path to
                             :element element})}]}))

(defn ^:dev/after-load init
  "This function is used in the `index.html`
  to load the application."
  []
  (utils/render App))
