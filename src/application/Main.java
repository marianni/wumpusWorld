package application;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * "Manazment" celej hry, v tejto triede sa spracovavaju klavesove eventy, nacitavaju sa levely, 
 * v pripade prehry hraca sa spracovava odkryvanie policok,
 * trieda kontroluje , ci hrac vyhral
 * @author Marianni
 *
 */

public class Main extends Application {

	public static final int SCENE_WIDTH = 900;
	public static final int SCENE_HEIGHT = 600;

	public static final int GAME_PANE_WIDTH = 600;
	public static final int GAME_PANE_HEIGHT = 600;

	public static final int INFO_PANE_WIDTH = 250;
	public static final int INFO_PANE_HEIGHT = 600;

	public static final int ROWS = 6;
	public static final int COLS = 6;
	public static final int POLICKO_HEIGHT = GAME_PANE_HEIGHT / ROWS;
	public static final int POLICKO_WIDTH = GAME_PANE_WIDTH / COLS;
	public static final int TIK = 100;

	public static final Image IMG_BREEZE = new Image("images/breeze.png");
	public static final Image IMG_STENCH = new Image("images/stench.png");
	public static final Image IMG_GOLD = new Image("images/gold.png");
	public static final Image IMG_HOLE = new Image("images/hole.png");
	public static final Image IMG_BLOOD = new Image("images/blood.png");

	public static final Image IMG_PLAYER_NORTH = new Image("images/player_north.png");
	public static final Image IMG_PLAYER_EAST = new Image("images/player_east.png");
	public static final Image IMG_PLAYER_SOUTH = new Image("images/player_south.png");
	public static final Image IMG_PLAYER_WEST = new Image("images/player_west.png");

	public static final Image IMG_WUMPUS = new Image("images/wumpus.png");
	public static final Image IMG_WALL = new Image("images/wall.jpg");

	private List<Level> levely;
	private int  level = 0;
	private List<List<Policko>> policka;
	private Player player;
	private Timeline animation;
	private boolean gameOver = false;
	private boolean gameWin = false;
	private GamePane gamePane;
	private InfoPane infoPane;	

	@Override
	public void start(Stage primaryStage) throws Exception {
		nacitajLevely();
		gamePane = new GamePane(GAME_PANE_WIDTH, GAME_PANE_HEIGHT);
		infoPane = new InfoPane(INFO_PANE_WIDTH, INFO_PANE_HEIGHT);
		HBox mainPane = new HBox();
		mainPane.getChildren().addAll(gamePane, infoPane);
		Scene scene = new Scene(mainPane, SCENE_WIDTH, SCENE_HEIGHT);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		policka = levely.get(level).getPolicka();
		player = levely.get(level).getPlayer();

		this.player.start(policka);
		gamePane.repaint(policka, player);
		infoPane.repaint(player, policka, level, false);

		scene.setOnKeyPressed(event -> {
			if (gameOver || gameWin) {
				return;
			}
			this.player.update(policka, event.getCode());
			gamePane.repaint(policka, player);
			if (this.player.isPlayerDead()) {
				gameOver = true;
			}
			infoPane.repaint(player, policka, level, false);
			event.consume();
		});

		animation = new Timeline(new KeyFrame(Duration.millis(TIK), e -> {
			handleLevelWin();
			if (gameWin) {
				showAll();
				gamePane.repaint(policka, player);
				infoPane.repaint(player, policka, level, true);
				animation.stop();
			}
			
			if (gameOver) {				
				showAll();
				gamePane.repaint(policka, player);
				infoPane.repaint(player, policka, level, false);
				animation.stop();
				return;
			}
		}));

		animation.setCycleCount(Timeline.INDEFINITE);
		animation.play();

		primaryStage.setScene(scene);
		primaryStage.show();
	}
	

	private void handleLevelWin(){
		if (player.isWumpusKilled() && player.getRemainingGold(policka) == 0){
			if (level == levely.size()-1){
				// KONIEC CELEJ HRY
				gameWin = true;
				return;
			}
			level++;
			policka = levely.get(level).getPolicka();
			player =  levely.get(level).getPlayer();
			return;
		}
	}

	private void showAll() {
		for (List<Policko> rows : policka) {
			for (Policko p : rows) {
				p.setVisible(true);
			}
		}
	}
	
	private void nacitajLevely() {
		levely = new ArrayList<>();
		File folder = new File("src/levely/");

		for (File fileEntry : folder.listFiles()) {
			Level level = new Level(fileEntry.getPath());
			levely.add(level);
		}
	}


	public static void main(String[] args) {
		launch(args);
	}

}
