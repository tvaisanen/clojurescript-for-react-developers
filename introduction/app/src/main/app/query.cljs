(ns app.query
  "Lightweight CLJS wrapper for React Query"
  (:require ["@tanstack/react-query" :as react-query]))

;; define the query client
(def query-client-provider
  (react-query/QueryClientProvider.))

(defonce query-client (react-query/QueryClient.))

(defn use-query
  "create clojure wrapper for useQuery"
  [query-key query-fn]
  (let [result (react-query/useQuery
                ;; useQuery is expecting a JS object
                ;; instead of CLJS map
                #js {:queryFn  query-fn
                     :queryKey (into-array query-key)})]
    {:data result.data
     :loading? result.isLoading}))
