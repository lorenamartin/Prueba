package com.codeoftheweb.salvo.controllers;
import com.codeoftheweb.salvo.models.*;
import com.codeoftheweb.salvo.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class SalvoController {

    @Autowired
    private GamePlayerRepository gamePlayerRepo;

    @Autowired
    private GameRepository gameRepo;

    @Autowired
    private SalvoRepository salvoRepo;

    @Autowired
    private PlayerRepository playerRepo;

    @Autowired
    private ScoreRepository scoreRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;



@RequestMapping("/game_view/{gamePlayerId}")
public Map<String, Object> getGamePlayers(@PathVariable Long gamePlayerId) {
    GamePlayer gamePlayer = gamePlayerRepo.findById(gamePlayerId).orElse(null);
    return this.game_viewDTO(gamePlayer);
}

@RequestMapping("/games") /*objeto JSON con información sobre el usuario actual, si se loguea y existe*/
public Map<String, Object> getGames(Authentication authentication){
    Map<String, Object> map = new HashMap<>();
    if(isGuest(authentication)){
        map.put("player", "guest");
    }else{
        map.put("player", playerRepo.findByUserName(authentication.getName()).playersDTO());
    }

    map.put("games", gameRepo.findAll().stream().map(Game::gamesDTO).collect(Collectors.toList()));


        return map;
}

  private Map<String, Object> game_viewDTO(GamePlayer gamePlayer){
      Map<String, Object> dto = new LinkedHashMap<>();
      dto.put("gameID", gamePlayer.getGame().getId());
      dto.put("creationDate", gamePlayer.getGame().getCreationDate());
      dto.put("gamePlayers", gamePlayer.getGame().getGamePlayers().stream().map(GamePlayer::gamePlayersDTO).collect(Collectors.toList()));
      dto.put("ships", gamePlayer.getShips().stream().map(Ship::shipDTO));
      dto.put("salvoes", gamePlayer.getGame().getGamePlayers().stream().flatMap(gp -> gp.getSalvoes().stream().map(Salvo::salvoDTO)));
      return dto;
}

@RequestMapping("/getPlayers")
    public List<Map<String, Object>> getPlayers(){
    return playerRepo.findAll().stream().map(Player::stadisticDTO).collect(Collectors.toList());
}


    private boolean isGuest(Authentication authentication) {
        return authentication == null || authentication instanceof AnonymousAuthenticationToken;
    }

@RequestMapping(path = "/players", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>>createUser(@RequestParam String username, @RequestParam String password) {
    ResponseEntity<Map<String, Object>> response;

    if (username.isEmpty() || password.isEmpty()) {
            response= new ResponseEntity<>(makeMap("error", "No data"), HttpStatus.FORBIDDEN);
        }else {
        Player player = playerRepo.findByUserName(username);
        if (player != null) {
            response = new ResponseEntity<>(makeMap("error", "Username already exists"), HttpStatus.CONFLICT);
        } else {
            Player newPlayer = playerRepo.save(new Player(username, passwordEncoder.encode(password)));
            response = new ResponseEntity<>(makeMap("player", newPlayer.getUserName()), HttpStatus.CREATED);
          }
        }
    return response;

}


    private Map<String, Object> makeMap(String key, Object value) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);
        return map;
    }



@RequestMapping(path = "/games" , method=RequestMethod.POST)
    public ResponseEntity<Map<String, Object>>creategame(Authentication authentication){
    ResponseEntity<Map<String, Object>> response;
    if(isGuest(authentication)){
        response = new ResponseEntity<>(makeMap("error", "not logged in"), HttpStatus.UNAUTHORIZED);
    }else{
        Game game = new Game(LocalDateTime.now());
        gameRepo.save(game);
        Player player = playerRepo.findByUserName(authentication.getName());
        GamePlayer gamePlayer = new GamePlayer(player, game, LocalDateTime.now());
        gamePlayerRepo.save(gamePlayer);
        response = new ResponseEntity<>(makeMap("gamePlayerId", gamePlayer.getId()), HttpStatus.CREATED);
    }
    return response;
}




@PostMapping(path= "/games/{gameId}/players")
public ResponseEntity<Map<String, Object>> joingame(Authentication authentication, @PathVariable long gameId) {
    ResponseEntity<Map<String, Object>> response;
    Game game = gameRepo.findById(gameId).orElse(null);
    Player player = playerRepo.findByUserName(authentication.getName());
    if (isGuest(authentication)) {
        response = new ResponseEntity<>(makeMap("error", "not logged in"), HttpStatus.UNAUTHORIZED);
    } else if (game == null) {
        response = new ResponseEntity<>(makeMap("error", "that game does not exist"), HttpStatus.BAD_REQUEST);
    } else if (game.getGamePlayers().stream().anyMatch(gp -> gp.getPlayer().getId() == player.getId())) {
        response = new ResponseEntity<>(makeMap("error", "the game is full"), HttpStatus.FORBIDDEN);
    } else if (game.getGamePlayers().size() > 1) {
        response = new ResponseEntity<>(makeMap("error", "the game is full"), HttpStatus.FORBIDDEN);
    }else{
    GamePlayer gamePlayer = new GamePlayer(player, game, LocalDateTime.now());
    gamePlayerRepo.save(gamePlayer);
    response = new ResponseEntity<>(makeMap("gamePlayerId", gamePlayer.getId()), HttpStatus.CREATED);
    }
 return response;
         }

@PostMapping("/games/players/{gpId}/ships")
public ResponseEntity<Map<String, Object>> addShips (Authentication authentication,@RequestBody List<Ship> ships, @PathVariable long gpId) {
        ResponseEntity<Map<String, Object>> response;
        Player player = playerRepo.findByUserName(authentication.getName());
        GamePlayer gamePlayer = gamePlayerRepo.findById(gpId).orElse(null);
        if (isGuest(authentication)) {
            response = new ResponseEntity<>(makeMap("error", "you must be logged"), HttpStatus.UNAUTHORIZED);
        } else if (gamePlayer == null) {
            response = new ResponseEntity<>(makeMap("error", "that game does not exist"), HttpStatus.BAD_REQUEST);
        } else if (gamePlayer.getPlayer().getId() != player.getId()){
            response = new ResponseEntity<>(makeMap("error", "that player is not participating in that game"), HttpStatus.NOT_FOUND);
        } else if (gamePlayer.getShips().size() > 0){
            response = new ResponseEntity<>(makeMap("error", "you've already placed ships"), HttpStatus.NOT_FOUND);
        } else if (ships.size() != 5) {
                response = new ResponseEntity<>(makeMap("error", "you need 5 ships to play"), HttpStatus.FORBIDDEN);
        } else {
            ships.stream().forEach(ship -> gamePlayer.addShip(ship));
            gamePlayerRepo.save(gamePlayer);
            response = new ResponseEntity<>(makeMap("success", "the ship have been placed"), HttpStatus.CREATED);
        }
        return response;
    }


@PostMapping(path = "/games/players/{gamePlayerId}/salvoes")/*endpoint para ubicar los salvos*/
public ResponseEntity<Map<String, Object> > addSalvoes(Authentication authentication, @PathVariable long gpId, @RequestBody List<String>shots) {
    ResponseEntity<Map<String, Object>> response;
    Player player = playerRepo.findByUserName(authentication.getName());
    GamePlayer gamePlayer = gamePlayerRepo.findById(gpId).orElse(null);
    if (isGuest(authentication)) {
        response = new ResponseEntity<>(makeMap("error", "not logged in"), HttpStatus.UNAUTHORIZED);
    } else if (gamePlayer == null) {
        response = new ResponseEntity<>(makeMap("error", "that game does not exist"), HttpStatus.NOT_FOUND);
    } else if (gamePlayer.getPlayer().getId() != player.getId()) {
        response = new ResponseEntity<>(makeMap("error", "that player is not participating in that game"), HttpStatus.NOT_FOUND);
    }else if (gamePlayer.getShips().size() > 0) {
        response = new ResponseEntity<>(makeMap("error", "you've already placed ships"), HttpStatus.NOT_FOUND);
    }else if (shots.size() > 5) {
        response = new ResponseEntity<>(makeMap("error", "you need 5 ships to play"), HttpStatus.FORBIDDEN);
    }else {
        /*crear los salvos*/
        int turn = gamePlayer.getSalvoes().size() + 1;

        Salvo newSalvo = new Salvo(turn, shots);
        gamePlayer.addSalvo(newSalvo);
        gamePlayerRepo.save(gamePlayer);
        response = new ResponseEntity<>(makeMap("success", "the salvo have been placed"), HttpStatus.CREATED);
    }
   return response;
}

}




