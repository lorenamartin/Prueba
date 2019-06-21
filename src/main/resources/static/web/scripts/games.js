
let gamesData




const loadData = () => {
	fetch('/api/games')
	.then(response => response.json())
	.then(json => {
		gamesData = json.games
		changeDateFormat()
		gamesTable()
		leaderTable(leaderboard(), document.getElementById('leaderboard'))
	})
	.catch(error => console.log(error))
}

loadData()

const changeDateFormat = () => {
    for (var i in gamesData){
        var newDate = new Date(gamesData[i].creationDate).toLocaleString();
        gamesData[i].creationDate = newDate
    }
}

const gamesTable = () => {
	let table = document.getElementById('games-table')
	let head = document.createElement('THEAD')
	let row = document.createElement('TR')
	let cell1 = document.createElement('TH')
	cell1.innerText = '#'
	row.appendChild(cell1)
	let cell2 = document.createElement('TH')
	cell2.innerText = 'created'
	row.appendChild(cell2)
	let cell3 = document.createElement('TH')
	cell3.innerText = 'players'
	cell3.colSpan = 2
	row.appendChild(cell3)
	head.appendChild(row)

	table.appendChild(head)


	let body = document.createElement('TBODY')
	gamesData.forEach(game => {
		let row = document.createElement('TR')
		for(item in game){
			
			if(typeof game[item] == 'object'){
				if(game[item].length == 1){
					let cell = document.createElement('TD')
					let cell2 = document.createElement('TD')
					cell.innerText = game[item][0].player.email
					cell2.innerText = 'waiting...'
					row.appendChild(cell)
					row.appendChild(cell2)
				} else{
					game[item].forEach(gamePlayer => {
						let cell = document.createElement('TD')
						cell.innerText = gamePlayer.player.email
						row.appendChild(cell)
					})
				}
				
			} else{
				let cell = document.createElement('TD')
				cell.innerText = game[item]
				row.appendChild(cell)
			}
			
		}

		body.appendChild(row)

	})

	table.appendChild(body)
}

const leaderboard = () => {
	let leaderboard = []
	let aux = []

	gamesData.forEach(game => {
		game.gamePlayers.forEach(gamePlayer => {
			if(aux.indexOf(gamePlayer.player.id) == -1){
				aux.push(gamePlayer.player.id)
				let obj = {}
				obj.id = gamePlayer.player.id
				obj.email = gamePlayer.player.email
				obj.score = gamePlayer.score
				obj.won = gamePlayer.score == 3 ? 1 : 0
				obj.lost = gamePlayer.score == 0 ? 1 : 0
				obj.tied = gamePlayer.score == 1 ? 1 : 0
				leaderboard.push(obj)
			} else{
				leaderboard.forEach(player => {
					if(player.id == gamePlayer.player.id){
						player.score += gamePlayer.score
						player.won += gamePlayer.score == 3 ? 1 : 0
						player.lost += gamePlayer.score == 0 ? 1 : 0
						player.tied += gamePlayer.score == 1 ? 1 : 0
					}
				})
			}
		})
	})
	return leaderboard
}

const leaderTable = (leaders, table) => {
	let head = document.createElement('THEAD')
	let row = document.createElement('TR')
	for (key in leaders[0]){
		let cell = document.createElement('TH')
		cell.innerText = key
		row.appendChild(cell)
	}
	head.appendChild(row)
	
    let body = document.createElement('TBODY')
    leaders.forEach(leader => {
		let row = document.createElement('TR')
		for(item in leader){
			let cell = document.createElement('TD')
			cell.innerText = leader[item]
			row.appendChild(cell)
		}
		body.appendChild(row)
	})

	table.appendChild(head)
	table.appendChild(body)
}