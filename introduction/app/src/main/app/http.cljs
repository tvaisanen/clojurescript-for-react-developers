(ns app.http)

(defn fetch
  "Browser fetch wrapper

  Same as:

  fetch(URL)
     .then((response) => response.json())
     .then((data) => console.log(data))"
  ([url] (fetch url {}))
  ([url opts]
   (-> (js/fetch url (clj->js opts))
       (.then (fn [response]
                (.json response)))
       (.then (fn [data]
                (js->clj data :keywordize-keys true))))))
