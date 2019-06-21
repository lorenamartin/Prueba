
var gamesData
var actualPlayer
var opponent

//get the gamePlayer id from the query parameter on the url
var gpId = paramObj(window.location.href).gp

fetch("/api/game_view/"+gpId)
.then(function(response){
	return response.json()
})
.then(function(json){
	
  gamesData = json
  
  WhoIsWho()//shows who is the player related to the gamePlayer and who is the opponent
  
  //the loadGrid() function loads the grid for ships with gridstack.js
  //first check if the player has already placed ships 
  if(gamesData.ships.length > 0){
    //if true, the grid is initialized in static mode, that is, the ships can't be moved
    loadGrid(true)
  } else{
    //On the contrary, the grid is initialized in dynamic mode, allowing the user to move the ships
    loadGrid(false)
  }


  createGrid(11, $(".grid-salvoes"), 'salvoes') //loads the grid for salvoes without gridstack.js
  setSalvoes() //loads the salvoes

})
.catch(function(error){
	console.log(error)
})

function WhoIsWho(){
  for(i = 0; i < gamesData.gamePlayers.length; i++){
    if(gamesData.gamePlayers[i].id == gpId){
      actualPlayer = gamesData.gamePlayers[i].player
    } else{
      opponent = gamesData.gamePlayers[i].player
    }
  }

  let logger = document.getElementById("logger")
  let wrapper = document.createElement('DIV')
  let p1 = document.createElement('P')
  p1.innerHTML = `Hi ${actualPlayer.email}!`
  let p2 = document.createElement('P')
  p2.innerHTML = `your opponent is: ${opponent.email}`
  wrapper.appendChild(p1)
  wrapper.appendChild(p2)
  logger.appendChild(wrapper)
}

function paramObj(search) {
  var obj = {};
  var reg = /(?:[?&]([^?&#=]+)(?:=([^&#]*))?)(?:#.*)?/g;

  search.replace(reg, function(match, param, val) {
    obj[decodeURIComponent(param)] = val === undefined ? "" : decodeURIComponent(val);
  });

  return obj;
}