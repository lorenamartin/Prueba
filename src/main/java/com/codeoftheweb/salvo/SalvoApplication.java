package com.codeoftheweb.salvo;

import com.codeoftheweb.salvo.models.*;
import com.codeoftheweb.salvo.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import static com.codeoftheweb.salvo.models.ShipType.*;

@SpringBootApplication
public class SalvoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalvoApplication.class, args);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Bean
	public CommandLineRunner initData(PlayerRepository playerRepository, GameRepository gameRepository, GamePlayerRepository gamePlayerRepository, ShipRepository shipRepository, SalvoRepository salvoRepository, ScoreRepository scoreRepository) {
		return (args) -> {
			// save a couple of players

			Player player1 = new Player("j.bauer@ctu.gov", passwordEncoder().encode("24"));
			Player player2 = new Player( "c.obrian@ctu.gov", passwordEncoder().encode( "42"));
			Player player3 = new Player("kim_bauer@gmail.com", passwordEncoder().encode("kb"));
			Player player4 = new Player("t.almeida@ctu.gov", passwordEncoder().encode("mole"));

			Game game1 = new Game(LocalDateTime.now());
			Game game2 = new Game(LocalDateTime.now().minusHours(1));
			Game game3 = new Game(LocalDateTime.now().minusHours(2));
			Game game4 = new Game(LocalDateTime.now().minusHours(3));
			Game game5 = new Game(LocalDateTime.now().minusHours(4));
			Game game6 = new Game(LocalDateTime.now().minusHours(5));
			Game game7 = new Game(LocalDateTime.now().minusHours(6));
			Game game8 = new Game(LocalDateTime.now().minusHours(7));




			GamePlayer gamePlayer1 = new GamePlayer(player1, game1, LocalDateTime.now());
			GamePlayer gamePlayer2 = new GamePlayer(player2, game1, LocalDateTime.now());
			GamePlayer gamePlayer3 = new GamePlayer(player1, game2, LocalDateTime.now().minusHours(1));
			GamePlayer gamePlayer4 = new GamePlayer(player2, game2, LocalDateTime.now().minusHours(1));
			GamePlayer gamePlayer5 = new GamePlayer(player2, game3, LocalDateTime.now().minusHours(2));
			GamePlayer gamePlayer6 = new GamePlayer(player4, game3, LocalDateTime.now().minusHours(2));
			GamePlayer gamePlayer7 = new GamePlayer(player2, game4, LocalDateTime.now().minusHours(3));
			GamePlayer gamePlayer8 = new GamePlayer(player1, game4, LocalDateTime.now().minusHours(3));
			GamePlayer gamePlayer9 = new GamePlayer(player1, game5, LocalDateTime.now().minusHours(4));
			GamePlayer gamePlayer10 = new GamePlayer(player2, game5, LocalDateTime.now().minusHours(4));
			GamePlayer gamePlayer11 = new GamePlayer(player1, game6, LocalDateTime.now().minusHours(5));
			GamePlayer gamePlayer12 = new GamePlayer(player2, game6, LocalDateTime.now().minusHours(5));
			GamePlayer gamePlayer13 = new GamePlayer(player2, game7, LocalDateTime.now().minusHours(6));
			GamePlayer gamePlayer14 = new GamePlayer(player4, game7, LocalDateTime.now().minusHours(6));
			GamePlayer gamePlayer15 = new GamePlayer(player2, game8, LocalDateTime.now().minusHours(7));
			GamePlayer gamePlayer16 = new GamePlayer(player1, game8, LocalDateTime.now().minusHours(7));



			Ship ship1 = new Ship(DESTROYER, new ArrayList<>(Arrays.asList("H2","H3","H4")));
			Ship ship2 = new Ship(SUBMARINE, new ArrayList<>(Arrays.asList("E1","F1","G1")));
			Ship ship3 = new Ship(PATROL_BOAT, new ArrayList<>(Arrays.asList("B4","B5")));
			Ship ship4 = new Ship(DESTROYER, new ArrayList<>(Arrays.asList("B5","C5","D5")));
			Ship ship5 = new Ship(PATROL_BOAT, new ArrayList<>(Arrays.asList("F1","F2")));
			Ship ship6 = new Ship(DESTROYER, new ArrayList<>(Arrays.asList("B5","C5","D5")));
			Ship ship7 = new Ship(PATROL_BOAT, new ArrayList<>(Arrays.asList("C6","C7")));
			Ship ship8 = new Ship(SUBMARINE, new ArrayList<>(Arrays.asList("A2","A3","A4")));
			Ship ship9 = new Ship(PATROL_BOAT, new ArrayList<>(Arrays.asList("G6","H6")));
			Ship ship10 = new Ship(DESTROYER, new ArrayList<>(Arrays.asList("B5","C5","D5")));
			Ship ship11 = new Ship(PATROL_BOAT, new ArrayList<>(Arrays.asList("C6","C7")));
			Ship ship12 = new Ship(SUBMARINE, new ArrayList<>(Arrays.asList("A2","A3","A4")));
			Ship ship13 = new Ship(PATROL_BOAT, new ArrayList<>(Arrays.asList("G6","H6")));
			Ship ship14 = new Ship(DESTROYER, new ArrayList<>(Arrays.asList("B5","C5","D5")));
			Ship ship15 = new Ship(PATROL_BOAT, new ArrayList<>(Arrays.asList("C6","C7")));
			Ship ship16 = new Ship(SUBMARINE, new ArrayList<>(Arrays.asList("A2","A3","A4")));
			Ship ship17 = new Ship(PATROL_BOAT, new ArrayList<>(Arrays.asList("G6","H6")));
			Ship ship18 = new Ship(DESTROYER, new ArrayList<>(Arrays.asList("B5","C5","D5")));
			Ship ship19 = new Ship(PATROL_BOAT, new ArrayList<>(Arrays.asList("C6","C7")));
			Ship ship20 = new Ship(SUBMARINE, new ArrayList<>(Arrays.asList("A2","A3","A4")));
			Ship ship21 = new Ship(PATROL_BOAT, new ArrayList<>(Arrays.asList("G6","H6")));
			Ship ship22 = new Ship(DESTROYER, new ArrayList<>(Arrays.asList("B5","C5","D5")));
			Ship ship23 = new Ship(PATROL_BOAT, new ArrayList<>(Arrays.asList("C6","C7")));
			Ship ship24 = new Ship(DESTROYER, new ArrayList<>(Arrays.asList("B5","C5","D5")));
			Ship ship25 = new Ship(PATROL_BOAT, new ArrayList<>(Arrays.asList("C6","C7")));
			Ship ship26 = new Ship(SUBMARINE, new ArrayList<>(Arrays.asList("A2","A3","A4")));
			Ship ship27 = new Ship(PATROL_BOAT, new ArrayList<>(Arrays.asList("G6","H6")));


			Salvo salvo1 = new Salvo(1, new ArrayList<>(Arrays.asList("B5", "C5", "F1")));
			Salvo salvo2 = new Salvo(2, new ArrayList<>(Arrays.asList("F2", "D5")));
			Salvo salvo3 = new Salvo(1, new ArrayList<>(Arrays.asList("A2", "A4", "G6")));
			Salvo salvo4 = new Salvo(2, new ArrayList<>(Arrays.asList("A3", "H6")));
			Salvo salvo5 = new Salvo(1, new ArrayList<>(Arrays.asList("G6", "H6", "A4")));
			Salvo salvo6 = new Salvo(2, new ArrayList<>(Arrays.asList("A2", "A3", "D8")));
			Salvo salvo7 = new Salvo(1, new ArrayList<>(Arrays.asList("A3", "A4", "F7")));
			Salvo salvo8 = new Salvo(2, new ArrayList<>(Arrays.asList("A2", "G6", "H6")));
			Salvo salvo9 = new Salvo(1, new ArrayList<>(Arrays.asList("A1", "A2", "A3")));
			Salvo salvo10 = new Salvo(2, new ArrayList<>(Arrays.asList("G6", "G7", "G8")));
			Salvo salvo11 = new Salvo(3, new ArrayList<>(Arrays.asList("","")));
			Salvo salvo12 = new Salvo(1, new ArrayList<>(Arrays.asList("B4", "B5", "B6")));
			Salvo salvo13 = new Salvo(2, new ArrayList<>(Arrays.asList("E1", "H3", "A2")));
			Salvo salvo14 = new Salvo(1, new ArrayList<>(Arrays.asList("B5", "D5", "C7")));
			Salvo salvo15 = new Salvo(2, new ArrayList<>(Arrays.asList("C5", "C6" )));
			Salvo salvo16 = new Salvo(1, new ArrayList<>(Arrays.asList("H1", "H2", "H3")));
			Salvo salvo17 = new Salvo(2, new ArrayList<>(Arrays.asList("E1", "F2", "G3")));
			Salvo salvo18 = new Salvo(1, new ArrayList<>(Arrays.asList("B5", "C6", "H1")));
			Salvo salvo19 = new Salvo(2, new ArrayList<>(Arrays.asList("C5", "C7", "D5")));
			Salvo salvo20 = new Salvo(1, new ArrayList<>(Arrays.asList("B5", "B6", "C7")));
			Salvo salvo21 = new Salvo(2, new ArrayList<>(Arrays.asList("C6", "D6", "E6")));
			Salvo salvo22 = new Salvo(3, new ArrayList<>(Arrays.asList("H1", "H8")));

			Score score1 = new Score(0.0, game1, player1, LocalDateTime.now() );
			Score score2 = new Score(1.0, game2, player1, LocalDateTime.now() );
			Score score3 = new Score(0.5, game3, player2, LocalDateTime.now() );
			Score score4 = new Score(0.0, game4, player2, LocalDateTime.now() );


			gamePlayer1.addShip(ship1);
			gamePlayer1.addShip(ship2);
			gamePlayer2.addShip(ship3);
			gamePlayer2.addShip(ship4);

			gamePlayer3.addShip(ship5);
			gamePlayer3.addShip(ship6);
			gamePlayer4.addShip(ship7);
			gamePlayer4.addShip(ship8);

			gamePlayer5.addShip(ship9);
			gamePlayer5.addShip(ship10);
			gamePlayer6.addShip(ship11);
			gamePlayer6.addShip(ship12);

			gamePlayer7.addShip(ship13);
			gamePlayer7.addShip(ship14);
			gamePlayer8.addShip(ship15);
			gamePlayer8.addShip(ship16);

			gamePlayer9.addShip(ship17);
			gamePlayer9.addShip(ship18);
			gamePlayer10.addShip(ship19);
			gamePlayer10.addShip(ship20);

			gamePlayer11.addShip(ship21);
			gamePlayer11.addShip(ship22);
			gamePlayer12.addShip(ship23);
			gamePlayer12.addShip(ship24);

			gamePlayer13.addShip(ship25);
			gamePlayer13.addShip(ship26);
			gamePlayer14.addShip(ship27);
			/*gamePlayer14.addShip(ship1);

			gamePlayer15.addShip(ship2);
			gamePlayer15.addShip(ship3);
			gamePlayer16.addShip(ship4);
			gamePlayer16.addShip(ship5);*/

			gamePlayer1.addSalvo(salvo1);
			gamePlayer1.addSalvo(salvo12);

			gamePlayer1.addSalvo(salvo2);
			gamePlayer1.addSalvo(salvo13);

			gamePlayer2.addSalvo(salvo3);
			gamePlayer2.addSalvo(salvo14);

			gamePlayer2.addSalvo(salvo4);
			gamePlayer2.addSalvo(salvo15);

			gamePlayer3.addSalvo(salvo5);
			gamePlayer3.addSalvo(salvo16);

			gamePlayer3.addSalvo(salvo6);
			gamePlayer3.addSalvo(salvo17);

			gamePlayer4.addSalvo(salvo7);
			gamePlayer4.addSalvo(salvo18);

			gamePlayer4.addSalvo(salvo8);
			gamePlayer4.addSalvo(salvo19);

			gamePlayer5.addSalvo(salvo9);
			gamePlayer5.addSalvo(salvo20);

			gamePlayer6.addSalvo(salvo10);
			gamePlayer6.addSalvo(salvo21);

			gamePlayer6.addSalvo(salvo11);
			gamePlayer6.addSalvo(salvo22);





			// save a couple of players
			playerRepository.save(player1);
			playerRepository.save(player2);
			playerRepository.save(player3);
			playerRepository.save(player4);


			// save a couple of games
			gameRepository.save(game1);
			gameRepository.save(game2);
			gameRepository.save(game3);
			gameRepository.save(game4);
			gameRepository.save(game5);
			gameRepository.save(game6);
			gameRepository.save(game7);
			gameRepository.save(game8);


			// save a couple of gamePlayers
			gamePlayerRepository.save(gamePlayer1);
			gamePlayerRepository.save(gamePlayer2);
			gamePlayerRepository.save(gamePlayer3);
			gamePlayerRepository.save(gamePlayer4);
			gamePlayerRepository.save(gamePlayer5);
			gamePlayerRepository.save(gamePlayer6);
			gamePlayerRepository.save(gamePlayer7);
			gamePlayerRepository.save(gamePlayer8);
			gamePlayerRepository.save(gamePlayer9);
			gamePlayerRepository.save(gamePlayer10);
			gamePlayerRepository.save(gamePlayer11);
			gamePlayerRepository.save(gamePlayer12);
			gamePlayerRepository.save(gamePlayer13);
			gamePlayerRepository.save(gamePlayer14);
			gamePlayerRepository.save(gamePlayer15);
			gamePlayerRepository.save(gamePlayer16);

			salvoRepository.save(salvo1);
			salvoRepository.save(salvo2);
			salvoRepository.save(salvo3);
			salvoRepository.save(salvo4);
			salvoRepository.save(salvo5);
			salvoRepository.save(salvo6);
			salvoRepository.save(salvo7);
			salvoRepository.save(salvo8);
			salvoRepository.save(salvo9);
			salvoRepository.save(salvo10);
			salvoRepository.save(salvo11);
			salvoRepository.save(salvo12);
			salvoRepository.save(salvo13);
			salvoRepository.save(salvo14);
			salvoRepository.save(salvo15);
			salvoRepository.save(salvo16);
			salvoRepository.save(salvo17);
			salvoRepository.save(salvo18);
			salvoRepository.save(salvo19);
			salvoRepository.save(salvo20);
			salvoRepository.save(salvo21);
			salvoRepository.save(salvo22);


            scoreRepository.save(score1);
			scoreRepository.save(score2);
			scoreRepository.save(score3);
			scoreRepository.save(score4);



		};
	}
}

