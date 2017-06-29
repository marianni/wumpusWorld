package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Trieda spracovava nacitavanie levelu zo suboru.
 * @author Marianni
 *
 */

public class Level {
	
	private Player player;
	private List<List<Policko>> policka;
	
	public Level(String filename) {
		super();
		loadFromFile(filename);
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public List<List<Policko>> getPolicka() {
		return policka;
	}
	
	public void setPolicka(List<List<Policko>> policka) {
		this.policka = policka;
	}
	
	@SuppressWarnings("resource")
	private void loadFromFile(String filename) {
		policka = new ArrayList<>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(filename)));
			String line;
			int x = -1;
			int y = -1;

			
			while ((line = br.readLine()) != null) {
				y++;
				x = -1;
				List<Policko> row = new ArrayList<>();
				String[] split = line.split("\\|");

				for (String polickoString : split) {
					polickoString = polickoString.replaceAll(" ", "");
					x++;
					Set<TypPolicka> types = new HashSet<>();
					polickoString = polickoString.trim();

					for (int i = 0; i < polickoString.length(); i++) {
						if (polickoString.charAt(i) == 'b') {
							types.add(TypPolicka.BREEZE);
						}
						if (polickoString.charAt(i) == 'h') {
							types.add(TypPolicka.HOLE);
						}
						if (polickoString.charAt(i) == 'g') {
							types.add(TypPolicka.GOLD);
						}
						if (polickoString.charAt(i) == 's') {
							types.add(TypPolicka.STENCH);
						}
						if (polickoString.charAt(i) == 'w') {
							types.add(TypPolicka.WUMPUS);
						}
						if (polickoString.charAt(i) == 'p') {
							player = new Player(x, y);
						}
					}
					Policko policko = new Policko(x, y, types);
					row.add(policko);

				}
				policka.add(row); // pridam riadok do 2D struktury
			}						
		} catch (IOException e) {
			System.out.println(e.toString());
			System.out.println("file does not exist");
		}
	}

	
	
	
	
	
	
	

}
