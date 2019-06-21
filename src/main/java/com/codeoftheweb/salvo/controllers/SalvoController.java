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
    public ResponseEntity<Map<String, Object>> createUser(@RequestParam String username, @RequestParam String password) {
        if (username.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>(makeMap("error", "No data"), HttpStatus.FORBIDDEN);
        }
        Player player= playerRepo.findByUserName(username);
        if (player != null) {
            return new ResponseEntity<>(makeMap("error", "Username already exists"), HttpStatus.CONFLICT);
        }
        Player newPlayer = playerRepo.save(new Player(username, passwordEncoder.encode(password)));
        return new ResponseEntity<>(makeMap("player", newPlayer.getUserName()), HttpStatus.CREATED);
    }

    private Map<String, Object> makeMap(String key, Object value) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);
        return map;
    }
holi

}
