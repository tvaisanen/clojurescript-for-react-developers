(ns app.query
  "Lightweight CLJS wrapper for React Query"
  (:require [helix.core :refer [defnc $]]
   ["@tanstack/react-query" :as react-query]))

(defonce query-client (react-query/QueryClient.))

(defn use-query
  "create clojure wrapper for useQuery"
  [{:keys [query-key query-fn]}]
  (let [result (react-query/useQuery
                #js {:queryFn  query-fn
                     :queryKey (into-array query-key)})]
    (js/console.log result)
    {:data result.data
     :loading? result.isLoading}))

(defnc WrapQueryClientProvider [{:keys [children]}]
  ($ react-query/QueryClientProvider {:client query-client}
     children))
