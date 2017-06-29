package application;

import java.util.Set;

import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * tato trieda sluzi na vykreslenie jedneho policka, podla jeho typu a vykresli ho na danu poziciu v GamePane.
 * kontroluje sa tu viditelnost policka, a teda ci sa na policku nachadza hrac
 * trieda tiez spracovava orientaciu hraca v priestore
 * @author Marianni
 *
 */

class Policko {
	private boolean visible;
	private int x;
	private int y;
	private Set<TypPolicka> types;

	public Policko(int x, int y, Set<TypPolicka> types) {
		super();
		this.x = x;
		this.y = y;
		this.types = types;
		this.visible = false;
	}

	public void paint(final GamePane pane, Player player) {
		if (!isVisible()) {
			Rectangle r = new Rectangle(x * Main.POLICKO_WIDTH, y * Main.POLICKO_HEIGHT, Main.POLICKO_WIDTH,
					Main.POLICKO_HEIGHT);
		
			r.setFill(Color.BLACK);
			r.setStroke(Color.WHITE);
			pane.getChildren().add(r);
			return;
		}
		
		ImageView img = new ImageView(Main.IMG_WALL);
		img.setX(x * Main.POLICKO_WIDTH);
		img.setY(y * Main.POLICKO_HEIGHT);
		pane.getChildren().add(img);

		if (types.contains(TypPolicka.BREEZE)) {
			ImageView breeze = new ImageView(Main.IMG_BREEZE);
			breeze.setX(x * Main.POLICKO_WIDTH);
			breeze.setY(y * Main.POLICKO_HEIGHT);
			pane.getChildren().add(breeze);
		}

		if (types.contains(TypPolicka.STENCH)) {
				ImageView stench = new ImageView(Main.IMG_STENCH);
				stench.setX(x * Main.POLICKO_WIDTH);
				stench.setY(y * Main.POLICKO_HEIGHT);
				pane.getChildren().add(stench);
		}

		if (types.contains(TypPolicka.GOLD)) {
				ImageView gold = new ImageView(Main.IMG_GOLD);
				gold.setX(x * Main.POLICKO_WIDTH);
				gold.setY(y * Main.POLICKO_HEIGHT);
				pane.getChildren().add(gold);
		}

		

		if (types.contains(TypPolicka.HOLE)) {
			ImageView hole = new ImageView(Main.IMG_HOLE);
			hole.setX(x * Main.POLICKO_WIDTH);
			hole.setY(y * Main.POLICKO_HEIGHT);
			pane.getChildren().add(hole);
		}

		if (types.contains(TypPolicka.WUMPUS)) {
			ImageView wumpus = new ImageView(Main.IMG_WUMPUS);
			wumpus.setX(x * Main.POLICKO_WIDTH);
			wumpus.setY(y * Main.POLICKO_HEIGHT);
			pane.getChildren().add(wumpus);
		}
		
		if (types.contains(TypPolicka.BLOOD)) {
			ImageView blood = new ImageView(Main.IMG_BLOOD);
			blood.setX(x * Main.POLICKO_WIDTH);
			blood.setY(y * Main.POLICKO_HEIGHT);
			pane.getChildren().add(blood);
		}
		
		if (player.getX() == x && player.getY() == y) {
			ImageView playerImage = null;
			switch (player.getOrientation()) {
			case NORTH:
				playerImage = new ImageView(Main.IMG_PLAYER_NORTH);
				break;
			case SOUTH:
				playerImage = new ImageView(Main.IMG_PLAYER_SOUTH);
				break;
			case EAST:
				playerImage = new ImageView(Main.IMG_PLAYER_EAST);
				break;
			case WEST:
				playerImage = new ImageView(Main.IMG_PLAYER_WEST);
				break;
			default:
				break;
			}			
			playerImage.setX(x * Main.POLICKO_WIDTH);
			playerImage.setY(y * Main.POLICKO_HEIGHT);
			pane.getChildren().add(playerImage);
		}

	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Set<TypPolicka> getTypes() {
		return types;
	}

	public void setTypes(Set<TypPolicka> types) {
		this.types = types;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	// 
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Policko other = (Policko) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

}