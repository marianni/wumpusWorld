package application;

import java.util.List;

import javafx.scene.layout.Pane;

/**
 * GamePane je pane , ktora sluzi na vykreslovanie hracich policok.
 * @author Marianni
 *
 */

public class GamePane extends Pane {

	public GamePane(int width, int height) {
		super();
		setWidth(width);
		setHeight(height);				
	}

	public void repaint(List<List<Policko>> policka, Player player) {
		getChildren().clear();

		// repaint policka
		for (List<Policko> row : policka) {
			for (Policko p : row) {
				p.paint(this, player);
			}
		}
	}

}
