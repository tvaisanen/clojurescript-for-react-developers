(ns app.router
  (:require [helix.core :refer [defnc $]]
            ["react-router-dom" :refer [createBrowserRouter
                                        RouterProvider
                                        NavLink
                                        Outlet]]))

(defn make-router
  "Create react-router/BrowserRouter from given routes."
  [routes]
  (createBrowserRouter (clj->js routes)))

(defnc WrapRouter
  "Make routes and wrap in a RouterProvider"
  [{:keys [routes]}]
  ($ RouterProvider
     {:router (make-router routes)}))

(defnc Link
  "Wrapper react-router/NavLink"
  [{:keys [to label] :as props}]

  ($ NavLink {:to to
              & props}
     label))

(defnc RouteContent
  "Render the route content."
  []
  ($ Outlet))
