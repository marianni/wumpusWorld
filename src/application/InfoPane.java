package application;

import java.util.List;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * InfoPane je pane, ktora sluzi na zobrazovanie informacii o hre napr. kolko zlata je v hre a kolko hrac vyzbieral,zobrazuje cislo levelu, 
 * ci je wumpus zabity a ci hrac vycerpal strelu na wumpusa.
 * @author Marianni
 *
 */
public class InfoPane extends Pane {
	private Text pocetZlataText;
	private Text strelyText;
	private Text wumpustext;
	private Text levelText;
	private Text playerText;

	public InfoPane(int width, int height) {
		super();
		setWidth(width);
		setHeight(height);
	}

	public void repaint(Player player, List<List<Policko>> policka, int level, boolean gameWin) {
		getChildren().clear();
		if (gameWin) {
			getChildren().add(generujText(30, 30, "VYHRAL SI VSETKY LEVELY!"));
			return;
		}

		levelText = generujText(27, 60, "Level: " + level);
		
		int allGoldCount = player.getPocetZlata()
				+ player.getRemainingGold(policka);
		pocetZlataText = generujText(45, 90, "Pocet zlata: " + player.getPocetZlata() + "/" + allGoldCount);

		int volnaStrela = player.getShooted() ? 0 : 1;
		strelyText = generujText(45, 120, "Volna strela:  " + volnaStrela);
		wumpustext = generujText(62, 150, "Wumpus zabity: " + player.isWumpusKilled());

		if (player.isPlayerDead()) {
			playerText = generujText(50, 190, "PREHRAL SI!");
			getChildren().add(playerText);
		}

		getChildren().add(levelText);
		getChildren().add(pocetZlataText);
		getChildren().add(strelyText);
		getChildren().add(wumpustext);
	}

	public Text generujText(double x, double y, String text) {
		Text t = new Text(text);
		t.setX(x);
		t.setY(y);
		t.setScaleX(2);
		t.setScaleY(2);
		t.setFill(Color.BLACK);
		return t;
	}
}
