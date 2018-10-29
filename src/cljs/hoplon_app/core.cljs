(ns hoplon-app.core
	(:require
		[cljs.pprint :refer [pprint]]
		[goog.object :as gobj]
		[hoplon.core
		 :as h
		 :include-macros true]
		[javelin.core
		 :refer [cell]
		 :refer-macros [cell= dosync]]
		[oops.core :refer [oget]]))


(extend-type js/Event IDeref
	(-deref [o]
		(some-> o
				(oget "target")
				(oget "value"))))

(h/defelem todo-list [{:keys [title]} _]
		 (let [todo-items (cell [])
			   new-item   (cell "")]
			 (h/div
				 (h/h3 (or title "TODO"))
				 (h/ul
					 (h/loop-tpl :bindings [todo todo-items]
							   (h/li todo)))
				 (h/input :type "text"
						:value new-item
						:change #(reset! new-item @%))
				 (h/button :click #(dosync
									 (swap! todo-items conj @new-item)
									 (reset! new-item ""))
						 (h/text "Add #~{(inc (count todo-items))}")))))



#_(h/defelem home []
		   (h/div
			   :id "app"
			   (h/h3 "Welcome to Hoplon 3")
			   (h/ul
				   (h/for-tpl [i todo-items]
							  (h/li i)))

			   (todo-list)))

(defn mount-root []
	(js/jQuery #(.replaceWith (js/jQuery "#app") (todo-list))))

(defn init! []
	(mount-root))
