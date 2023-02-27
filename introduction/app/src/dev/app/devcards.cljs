(ns app.devcards
  (:require [devcards.core :as dc :include-macros true]
            ;; load the namespaces with devcards
            [app.starwars-test]))

(defn ^:dev/after-load start! []
  (dc/start-devcard-ui!))

(defn init! [] (start!))

(init!)
