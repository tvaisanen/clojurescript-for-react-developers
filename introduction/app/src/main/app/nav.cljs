(ns app.nav
  (:require [helix.core :refer [defnc $]]
            [helix.dom :as d]
            [app.router :as router]))

(defnc Navbar [{:keys [navlinks]}]
  (d/nav
   (for [{:keys [to label]} navlinks]
     ($ router/Link {:key to
                     :to to
                     :label label}))))
