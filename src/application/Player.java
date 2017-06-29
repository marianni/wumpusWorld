package application;

import java.util.List;

import javafx.scene.input.KeyCode;

/**
 * Trieda spracovava celu logiku hraca, jeho pohyb v danom smere, strelbu sipom (skontroluje zabitie wumpusa),
 * zbieranie a pocitanie zlata, ci hrac zije, v pripade , ze je blizko wumpus tak citi smrad, v pripade diery citi vanok.
 * hrac sa otaca a pohybuje sipkami, sip vystreluje medzernikom.
 * @author Marianni
 *
 */

public class Player {
	private int x;
	private int y;
	private int speed;
	private int pocetZozbieranehoZlata = 0;
	private boolean playerDead;
	private boolean citiVanok;
	private boolean citiSmrad;
	public PlayerOrientation orientation;
	private boolean shooted;
	private boolean wumpusKilled;

	public boolean isPlayerDead() {
		return playerDead;
	}

	public int getPocetZlata() {
		return pocetZozbieranehoZlata;
	}

	public void setPocetZlata(int pocetZlata) {
		this.pocetZozbieranehoZlata = pocetZlata;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public Player(int x, int y) {
		super();
		this.x = x;
		this.y = y;
		speed = 1;
		playerDead = false;
		orientation = PlayerOrientation.NORTH;
		shooted = false;
		wumpusKilled =false;
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

	public void keyReleased() {
		speed = 0;
	}

	private boolean playerDies(List<List<Policko>> policka) {
		if (policka.get(y).get(x).getTypes().contains(TypPolicka.WUMPUS)
				|| policka.get(y).get(x).getTypes().contains(TypPolicka.HOLE)) {
			return true;
		}
		return false;
	}

	private void movePlayer(List<List<Policko>> policka, KeyCode keyCode) {
		System.out.println(x + "," + y);
		if (keyCode.equals(KeyCode.LEFT)) {
			if (orientation.equals(PlayerOrientation.WEST) && x > 0) {
				x = x - speed;
			} else {
				orientation = PlayerOrientation.WEST;
			}
		}

		if (keyCode.equals(KeyCode.UP)) {
			if (orientation.equals(PlayerOrientation.NORTH) && y > 0) {
				y = y - speed;
			} else {
				orientation = PlayerOrientation.NORTH;
			}
		}

		if (keyCode.equals(KeyCode.RIGHT)) {
			if (orientation.equals(PlayerOrientation.EAST) && x < policka.get(0).size() - 1) {
				x = x + speed;
			} else {
				orientation = PlayerOrientation.EAST;
			}
		}

		if (keyCode.equals(KeyCode.DOWN)) {
			if (orientation.equals(PlayerOrientation.SOUTH) && y < policka.size() - 1) {
				y = y + speed;
			} else {
				orientation = PlayerOrientation.SOUTH;
			}
		}
	}

	private void processPlayerLogic(List<List<Policko>> policka) {
		policka.get(y).get(x).setVisible(true);

		if (policka.get(y).get(x).getTypes().contains(TypPolicka.GOLD)) {
			System.out.println("zlato mam");
			pocetZozbieranehoZlata++;
			policka.get(y).get(x).getTypes().remove(TypPolicka.GOLD);
		}
		System.out.println(pocetZozbieranehoZlata);

		if (policka.get(y).get(x).getTypes().contains(TypPolicka.BREEZE)) {
			citiVanok = true;
			System.out.println("Citim vanok,moze byt niekde diera");
		} else {
			citiVanok = false;
		}

		if (policka.get(y).get(x).getTypes().contains(TypPolicka.STENCH)) {
			citiSmrad = true;
			System.out.println("Citim smrad, wumpus je blizko");
		} else {
			citiSmrad = false;
		}

	}

	public void update(List<List<Policko>> policka, KeyCode keyCode) {
		handleShoot(policka,keyCode);
		movePlayer(policka, keyCode);
		if (playerDies(policka)) {
			System.out.println("PLAYER DIED");
			playerDead = true;
			return;
		}
		processPlayerLogic(policka);
	}

	public void start(List<List<Policko>> policka) {
		processPlayerLogic(policka);
	}

	public PlayerOrientation getOrientation() {
		return orientation;
	}

	public boolean citiVanok() {
		return citiVanok;
	}

	public boolean citiSmrad() {
		return citiSmrad;
	}

	public boolean isWumpusKilled() {
		return wumpusKilled;
	}

	public int getRemainingGold(List<List<Policko>> policka) {
		int pocetZostavajucehoZlata = 0;
		for (List<Policko> rows : policka) {
			for (Policko p : rows) {
				if (p.getTypes().contains(TypPolicka.GOLD)) {
					pocetZostavajucehoZlata++;
				}
			}
		}
		return pocetZostavajucehoZlata;
	}
	
	public boolean getShooted() {
		return shooted;
	}

	private void handleShoot(List<List<Policko>> policka,KeyCode keyCode) {
		if (!shooted && keyCode.equals(KeyCode.SPACE)) {
			shooted = true;
			for (List<Policko> rows : policka) {
				for (Policko p : rows) {
					if(orientation.equals(PlayerOrientation.NORTH)){
						if(p.getX()==x && p.getY()<y && p.getTypes().contains(TypPolicka.WUMPUS)){
							handleWumpusKilled(policka, p);
							return;
						}
					}
					
					if(orientation.equals(PlayerOrientation.SOUTH)){
						if(p.getX()==x && p.getY()>y && p.getTypes().contains(TypPolicka.WUMPUS)){
							handleWumpusKilled(policka, p);
							return;
						}
					}
					
					if(orientation.equals(PlayerOrientation.WEST)){
						if(p.getX()<x && p.getY() == y && p.getTypes().contains(TypPolicka.WUMPUS)){
							handleWumpusKilled(policka, p);
							return;
						}
					}
					
					if(orientation.equals(PlayerOrientation.EAST)){
						if(p.getX()>x && p.getY() == y && p.getTypes().contains(TypPolicka.WUMPUS)){
							handleWumpusKilled(policka, p);
							return;
						}
					}
				}
			}
		}
	}
	
	private void handleWumpusKilled(List<List<Policko>> policka, Policko wumpusPolicko){
		wumpusKilled = true;
		wumpusPolicko.getTypes().remove(TypPolicka.WUMPUS);
		wumpusPolicko.getTypes().add(TypPolicka.BLOOD);
		wumpusPolicko.setVisible(true);
		
		for (List<Policko> rows : policka) {
			for (Policko p : rows) {
				p.getTypes().remove(TypPolicka.STENCH);
			}
		}
	}

}
