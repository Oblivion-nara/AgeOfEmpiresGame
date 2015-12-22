package ageOfEmpires;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;

public class Map {

	private Point[][] mapPics;
	private terrain[][] map;
	private Image[][] mapImages;
	private int xSize,ySize;
	
	private enum terrain{
		GRASS,DESERT
	}
	
	public Map(){
		
		createMap();
	
	}

	private void createMap(){
		
		xSize = (Main.gen.nextInt(101)*10)+Main.width;
		ySize = (Main.gen.nextInt(101)*10)+Main.height;
		System.out.println(xSize+","+ySize);
		map = new terrain[xSize/10][ySize/10];
		mapPics = new Point[xSize][ySize];
		
		mapImages = ResourceLoader.getPlayerSprites("background/landSprites.png", 16, 16);
		
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				
				int type = Main.gen.nextInt(2);
				
				switch (type){
				
				case 0:
					map[i][j] = terrain.GRASS;
					break;
				case 1:
					map[i][j] = terrain.DESERT;
					break;
				
				}
				
			}
		}
	
		
		for (int i = 0; i < mapPics.length; i++) {
			for (int j = 0; j < mapPics[0].length; j++) {
				if(Main.gen.nextDouble() < 0.01){
					mapPics[i][j] = new Point(3,Main.gen.nextInt(4));
				}else{
					mapPics[i][j] = new Point(3,Main.gen.nextInt(2)+1);
				}
			}
		}
		
	}
	
	public int getXSize(){
		return xSize;
	}
	public int getYSize(){
		return ySize;
	}
	
	public void draw(Graphics g){
		
		Graphics2D g2 = (Graphics2D)g;
		
		for (int i = 0; i < mapPics.length; i++) {
			for (int j = 0; j < mapPics[0].length; j++) {
						int x = i*16;
						int y = j*16;
				if(((x)+Main.zeroXCoord >= -16 && x <= (Main.width+16)-Main.zeroXCoord)
						&&(((y)+Main.zeroYCoord >= -16)&& y <=(Main.height+16)-Main.zeroYCoord )){
					g2.drawImage(mapImages[mapPics[i][j].x][mapPics[i][j].y], i*16, j*16, null);
				}
			}
		}
		
	}
}
