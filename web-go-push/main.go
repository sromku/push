package sromku

import (
	"net/http"
	"server"
)

func init() {
	http.HandleFunc("/server/", handle)
	http.HandleFunc("/favicon.ico", donothing)
}

func handle(w http.ResponseWriter, r *http.Request) {
	server.PushIt(w, r)
}

func donothing(w http.ResponseWriter, r *http.Request) {
}
