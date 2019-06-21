package com.codeoftheweb.salvo.models;
import com.codeoftheweb.salvo.models.Game;
import com.codeoftheweb.salvo.models.GamePlayer;
/*import com.fasterxml.jackson.annotation.JsonIgnore;*/
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import java.util.*;

import static java.util.stream.Collectors.toList;


@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String userName;
    private String password;



    public Player() {
    }

    public Player(String userName, String password) {

        this.userName = userName;
        this.password = password;
    }

    public Set<GamePlayer> getGamePlayers() {
        return gamePlayers;
    } /*Borrar*/

    public Set<Score> getScores() {
        return scores;
    }

    @OneToMany(mappedBy="player", fetch=FetchType.EAGER)
    Set<GamePlayer> gamePlayers;

    @OneToMany(mappedBy="player", fetch=FetchType.EAGER)
    Set<Score> scores;


    public void addGamePlayer(GamePlayer gamePlayer) {
        gamePlayer.setPlayer(this);
        gamePlayers.add(gamePlayer);
    }

    public Score getScore(Game game) {
        return getScores().stream().filter(s -> s.getGame().getId() == game.getId()).findFirst().orElse(null);
    }

    /*public void addScore(Score score) {
        score.setScore(this);
        scores.add(score);
    }****************** no sé si esto va o no????************** */

    public List<Game> getGames() {
        return gamePlayers.stream().map(sub -> sub.getGame()).collect(toList());
    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public Map<String, Object> playersDTO(){
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("playerID", this.getId());
        dto.put("email", this.getUserName());
        return dto;
    }

    public Map<String, Object> stadisticDTO(){
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id",this.getId());
        dto.put("email",this.getUserName());
        double total = this.getScores().stream().mapToDouble(Score :: getScore).sum();
        double  won = this.getScores().stream().filter(score -> score.getScore()==1).count();
        double  lost = this.getScores().stream().filter(score -> score.getScore()==0).count();
        double  tied = this.getScores().stream().filter(score -> score.getScore()==0.5).count();
        dto.put("score",total);
        dto.put("score",won);
        dto.put("score",lost);
        dto.put("score",tied);
        return dto;
    }
    /*public String toString() {
        return firstName + " " + lastName;
    }*/
}
