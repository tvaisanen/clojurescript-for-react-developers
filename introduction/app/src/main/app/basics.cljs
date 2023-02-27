(ns app.basics)
;; clojure lingo [js , clj] 
;; array = vector
;; object = map
;; explain list ...js doesnt have it,
;; what can we do with it and to it
;; explain namespace 

(comment

  ;; Quick primer on clojure

  ;; define variables

  (def number-variable 1)
  ;; number-variable
  ;; => 1

  (def string-variable "variable")
  ;; string-variable
  ;; => "variable"

  (def map-variable {:a 1 :b 2})
  ;; map-variable
  ;; => {:a 1, :b 2}

  ;; get values from a map
  (get map-variable :a)
  ;; => 1
  ;; get values from a map with a default
  (get map-variable :c :not-found)
  ;; => :not-found

  ;; use the key as a function to get the value
  (:a map-variable)
  ;; => 1
  ;; or use the map as a function to get the value
  (map-variable :a)
  ;; => 1

  ;; add value
  (assoc map-variable :c "c")
  ;; => {:a 1, :b 2, :c "c"}

  ;; remove variable
  (dissoc map-variable :b)
  ;; => {:a 1}

  ;; vector variable
  (def vector-variable [1 2 3])

  (get vector-variable 0)
  ;; => 1

  ;; mapping over a vector
  (map inc vector-variable)
  ;; => (2 3 4)

  ;; filtering
  (filter odd? vector-variable)
  ;; => (1 3)

  ;; combinining map and filter
  (->> vector-variable
       (map inc)
       (filter even?))
  ;; => (2 4)

  ;; defining functions

  (defn sum [a b] (+ a b))
  sum
  ;; => #object[app$core$sum]
  (+ 1 2)
  ;; => 3
  (sum 1 2)
  ;; => 3

  ;; https://cljs.info/cheatsheet/

       ;; make sure that we have a connection to the browser
  (js/alert 1)

       ;; using the prefix `js/` allows us to access all the
       ;; javascript functionts and API's

       ;; for example let's get the window location
  js/document.location
       ;; => #object[Location http://localhost:3000/]

       ;; console log
  (js/console.log 123)

       ;; Interacting with the DOM from the editor
       ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

       ;; lets create an input element
  (def el (js/document.createElement "input"))
       ;; => [#object[HTMLInputElement [object HTMLInputElement]]]

  (set! (.-id el) "input")
       ;; => "input"

  (.appendChild (js/document.getElementById "app") el)
       ;; => #object[HTMLInputElement [object HTMLInputElement]]

       ;; at this point an input element should have appeared in your browser

       ;; lets update the value of that input
  (set! (.-value el) "some value from the browser")

       ;; edit the input value in the browser

       ;; lets use browsers getElementById to read that value
  (.. (js/document.getElementById "input") -value)
       ;; => "some edited value from the browser"

       ;; Some notes on the differences between CLJS and JS

  ;; Objects and maps
  ;;;;;;;;;;;;;;;;;;;;;;;

  (def object {:some "value"})

  (type object)
  ;; => cljs.core/PersistentArrayMap

  ;; If we need to create an argument object to JS functions,
  ;; the values need to be transformed into JS
  (clj->js object)
;; => {"some" "value"}
  (type (clj->js object))
;; => #object[Object]

  ;; or we can create it with a `#js` reader notation
  (do #js {:some "value"})
  ;; => {"some" "value"}
  (type #js {:some "value"})
  ;; => #object[Object]

  ;; when using `#js` the value is compiled directly to javascript
  ;; object and there's no runtime costs for the transformation

  ;; there's also a function to do this `clj->js`,
  (clj->js object)
  ;; => {"some" "value"}
  (type (clj->js object))
  ;; => #object[Object]

  ;; it is good to be aware that `clj->js` is recursive and
  ;; if you have a large datastructure it can cause performance issues

  ;; If you need to create javascript objects dynamically
  ;; `js-obj` can also be used
  (js-obj :some "value")
;; => {":some" "value"}
  (type (js-obj :some "value"))
;; => #object[Object]


  ;; Arrays
  ;;;;;;;;;;;;;;

  (def a [1 2 3])
  ;; similarly for arrays

  (type a)
  ;; => cljs.core/PersistentVector
  ;; clojure vector to js array

  (into-array a)
  ;; => [1 2 3]

  (type (into-array a))
  ;; => #object[Array]

  (clj->js a)
  ;; => [1 2 3]

  (type (clj->js a))
  ;; => #object[Array]

  ;; empty js array
  (array)
  ;; => []
  (type (array))
  ;; => #object[Array]
  (array 1 2)
   ;; => [1 2]

  ;; JS array with pre-allocated memory
  (make-array 10)
  ;; => [nil nil nil nil nil nil nil nil nil nil]

  ;; and lastly, use the `js->clj` to transform from javascript to clojure

  (js->clj #js {:a 2})
  ;; => {"a" 2}

  (js->clj #js {:a 2} :keywordize-keys true)
  ;; => {:a 2}

  )
