
var app = new Vue({
  el: '#app',
  data: {
   title1:'GAMES',
   games: {},
  }})


var url

url="/api/games"

fetch(url)
  .then(function (response) {
        if (response.ok) {
            return response.json();
        }

        throw new Error(response.statusText);
  })
  .then(function (value) {
        app.games = value;
  })
  .catch(function (error) {
         console.log("Request failed: " + error.message);
  });

function login(evt) {
	evt.preventDefault(evt);
	var form = evt.target.form;
	$.post("/api/login", {
		username: form["username"].value,
		password: form["password"].value
	});
}

function logout(evt) {
	evt.preventDefault();
	$.post("/api/logout");
}

function signin(evt) {
	evt.preventDefault(evt);
	var form = evt.target.form;
	$.post("/api/players", {
		username: form["username"].value,
		password: form["password"].value
	});
}