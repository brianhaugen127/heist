package heist;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class gridMap {
	
	public Tile[][] tiles;
	public gridMap(String mapTitle) throws IOException {
		
		tiles = new Tile[15][20];
		File file = new File(mapTitle);
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		int c = 0;
		for(int i = 0; i <15; i++) {
			int j = 0;
			for(; j<21; j++) {
				c = br.read();
				char character = (char) c;
				if(character == '\n') {
					break;
				}
				else{
					tiles[i][j] = new Tile((float)40*j+20, (float)40*i+20, Character.getNumericValue(character));
				}
			}
		}
		br.close();
	}
}
