;; core is used often in clojure similarly as index.js in Javascript
(ns app.core)

(defn start
  "Shadow CLJS will run this code everytime on after load."
  []
  (prn "app start"))

(defn stop
  "Shadow CLJS will run this code everytime before load."
  []
  (prn "app stop"))

(defn init
  "This function is used in index.html to load the application."
  []
  (js/console.log "Browser loaded the code"))
