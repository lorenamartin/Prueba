package com.codeoftheweb.salvo.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Entity
public class Salvo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="gamePlayer_id")
    private GamePlayer gamePlayer;
    private int turn;

    @ElementCollection
    @Column(name="location")
    private List<String> locations = new ArrayList<>();

    public Salvo(){}

    public Salvo(int turn,  List<String> locations){
        setTurn(turn);
        setLocations(locations);
    }


    public long getId() { return id;}

    public void setId(long id) { this.id = id;}

    public GamePlayer getGamePlayer() { return gamePlayer;}

    public void setGamePlayer(GamePlayer gamePlayer) { this.gamePlayer = gamePlayer;}

    public int getTurn() { return turn;}

    public void setTurn(int turn) { this.turn = turn;}

    public List<String> getLocations() {
        return locations;
    }

    public void setLocations(List<String> locations) {
        this.locations = locations;
    }

    public Map<String, Object> salvoDTO(){
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("player",this.getGamePlayer().getPlayer().getId());
        dto.put("locations", this.getLocations());
        dto.put("turn", this.getTurn());
        return dto;
    }


}
