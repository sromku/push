package server

import (
	"appengine"
	"appengine/urlfetch"
	"encoding/json"
	"github.com/alexjlockwood/gcm"
	"net/http"
)

type FormData struct {
	Message string
	APIKey  string
	RegId   string
}

type Result struct {
	Message string
}

func PushIt(w http.ResponseWriter, r *http.Request) error {
	c := appengine.NewContext(r)
	req := &FormData{}
	json.NewDecoder(r.Body).Decode(&req)

	client := urlfetch.Client(c)
	sender := &gcm.Sender{ApiKey: req.APIKey, Http: client}

	// Create the message to be sent.
	data := map[string]interface{}{"data": req.Message}
	regIDs := []string{req.RegId}
	msg := gcm.NewMessage(data, regIDs...)

	// Send the message and receive the response after at most two retries.
	_, err := sender.Send(msg, 2)
	if err != nil {
		result := &Result{Message: "Failed to send message: " + err.Error()}
		return json.NewEncoder(w).Encode(result)
	}
	result := &Result{Message: "Successfully sent"}
	return json.NewEncoder(w).Encode(result)
}
