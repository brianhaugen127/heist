package heist;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class gridMap {
	
	public int[][] map;
	
	public gridMap(String mapTitle) throws IOException {
		
		map = new int[15][20];
		File file = new File(mapTitle);
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		int c = 0;
		for(int i = 0; i <15; i++) {
			for(int j = 0; j<20; j++) {
				c = br.read();
				char character = (char) c;
				
				if(character == '\n') break;
				else{
					map[i][j] = Character.getNumericValue(c);
				}
			}
		}
		br.close();
	}
}
