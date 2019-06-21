package com.codeoftheweb.salvo.models;

import com.codeoftheweb.salvo.models.Player;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.*;

@Entity
public class Ship {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private ShipType type;
    @ElementCollection
    @Column(name="location")
    private List<String> locations = new ArrayList<>();
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="gamePlayer_id")
    private GamePlayer gamePlayer;



    public Ship(){}

    public Ship(ShipType type, List<String> locations){
        this.type = type;
        this.locations = locations;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ShipType getType() {
        return type;
    }

    public void setType(ShipType type) {
        this.type = type;
    }


    public GamePlayer getGamePlayer() {
        return gamePlayer;
    }

    public void setGamePlayer(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
    }


    public List<String> getlocations() {
        return locations;
    }

    public void setList(List<String> locations) {
        this.locations = locations;
    }

    public Map<String, Object> shipDTO(){
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("locations", this.getlocations());
        dto.put("type", this.getType());
        return dto;
    }

}
