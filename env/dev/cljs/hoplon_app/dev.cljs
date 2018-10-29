(ns ^:figwheel-no-load hoplon-app.dev
  (:require
    [hoplon-app.core :as core]
    [devtools.core :as devtools]
	[figwheel.client :as figwheel :include-macros true]))

(devtools/install!)
(enable-console-print!)
(figwheel/watch-and-reload
	:websocket-url "ws://localhost:3449/figwheel-ws"
	:jsload-callback #'core/mount-root)

(core/init!)
