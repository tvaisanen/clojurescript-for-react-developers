(ns app.react-router-app
  (:require [helix.core :refer [fnc defnc $ <>]]
            [helix.dom :as d]
            [app.utils :as utils]
            ["react" :as react]
            ["react-dom/client" :as rdom]
            ["react-router-dom" :refer [Navigate
                                        createBrowserRouter
                                        RouterProvider]]))

(defnc Root []
  (<>
   (d/nav
    (d/a {:href "/"} "landing")
    (d/a {:href "/people"} "people"))
   (d/div {:id "container"})))

(def router
  (createBrowserRouter
   (clj->js [{:path "/react-router-app.html"
              :element ($ Navigate {:to "/"})}
             {:path "/"
              :element ($ Root)
              :children [{:path "/people"
                          :element (fnc []
                                        (d/div "people"))}]}])))

(defn ^:dev/after-load init
  []
  (utils/render
   ($ RouterProvider {:router router})))
