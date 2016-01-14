package ageOfEmpires;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;

import utils.ResourceLoader;

public class Map{

	private Point[][] mapPics;   //which mapSprite to use, as a point
	private terrain[][] map;
	private Image[][] mapSprites;
	private int xSize,ySize;
	private int minix;
	private double ratio;
	
	private enum terrain{
		GRASS,DESERT
	}
	
	public Map(int mapsize){
		
		createMap(mapsize);
	
	}

	private void createMap(double mapsize){
		
		xSize = (int)((3*mapsize/2) * 10 * 16 * (Main.width/160));
		ySize = (int)((3*mapsize/2) * 10 * 16 * (Main.height/160));
		map = new terrain[xSize/10][ySize/10];
		mapPics = new Point[xSize][ySize];
		
		mapSprites = ResourceLoader.getPlayerSprites("background/landSprites.png", 16, 16);
		
		for (int i = 0; i < map.length; i++) {			// xcoord
			for (int j = 0; j < map[0].length; j++) {	// ycoord
				
				int type = Main.gen.nextInt(2);
				
				switch (type){
				
				case 0:
					map[i][j] = terrain.GRASS;
					if(Main.gen.nextDouble() < 0.01){
						mapPics[i][j] = new Point(0,Main.gen.nextInt(4));
					}else{
						mapPics[i][j] = new Point(0,Main.gen.nextInt(2)+1);
					}
					break;
				case 1:
					map[i][j] = terrain.DESERT;
					if(Main.gen.nextDouble() < 0.01){
						mapPics[i][j] = new Point(3,Main.gen.nextInt(4));
					}else{
						mapPics[i][j] = new Point(3,Main.gen.nextInt(2)+1);
					}
					break;
				}
				
			}
		}
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				
				switch(map[i][j]){
				case GRASS:
					for (int i2 = 0; i2 < 10; i2++) {
						for (int j2 = 0; j2 < 10; j2++) {
							if(Main.gen.nextDouble() < 0.01){
								mapPics[i*10+i2][j*10+j2] = new Point(0,Main.gen.nextInt(4));
							}else{
								mapPics[i*10+i2][j*10+j2] = new Point(0,Main.gen.nextInt(2)+1);
							}
						}
					}
					if(i == map.length-1){
						if(j == map[0].length-1){
						}else if(map[i][j+1] != terrain.GRASS){
							for(int x = 0; x < 10; x++){
								// top row x changes
								mapPics[i*10+x][j*10+9] = new Point(1,Main.gen.nextInt(2)+2);
							}
						}
						// else do nothing
					}else if(j == map[0].length-1){
						if(map[i+1][j] != terrain.GRASS){
							for(int x = 0; x < 10; x++){
								// last column y changes
								mapPics[i*10+9][j*10+x] = new Point(1,Main.gen.nextInt(2));
							}
						}
						// else do nothing
					}else{
						if(map[i][j+1] != terrain.GRASS){
							for(int x = 0; x < 9; x++){
								mapPics[i*10+x][j*10+9] = new Point(1,Main.gen.nextInt(2)+2);
							}
						}
						if(map[i+1][j] != terrain.GRASS){
							for(int x = 0; x < 9; x++){
								mapPics[i*10+9][j*10+x] = new Point(1,Main.gen.nextInt(2));
							}
						}
					}
					
					break;
				case DESERT:
					for (int j2 = 0; j2 < 10; j2++) {
						for (int k = 0; k < 10; k++) {
							if(Main.gen.nextDouble() < 0.01){
								mapPics[i*10+j2][j*10+k] = new Point(3,Main.gen.nextInt(4));
							}else{
								mapPics[i*10+j2][j*10+k] = new Point(3,Main.gen.nextInt(2)+1);
							}
						}
					}
//					if(i == map.length-1){
//						if(j == map[0].length-1){
//						}else{
//							for(int x = 0; x < 9; x++){
//								mapPics[i*10+x][j*10+9] = new Point(3,Main.gen.nextInt(2)+1);
//							}
//						}
//					}else if(j == map[0].length-1){
//						for(int x = 0; x < 9; x++){
//							mapPics[i*10+x][j*10+9] = new Point(3,Main.gen.nextInt(2)+2);
//						}
//					}else{
//						if(map[i][j+1] != terrain.DESERT){
//							for(int x = 0; x < 9; x++){
//								mapPics[i*10+x][j*10+9] = new Point(2,Main.gen.nextInt(2)+2);
//							}
//						}
//						if(map[i+1][j] != terrain.DESERT){
//							for(int x = 0; x < 9; x++){
//								mapPics[i*10+9][j*10+x] = new Point(2,Main.gen.nextInt(2));
//							}
//						}
//					}
					break;
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
					g2.drawImage(mapSprites[mapPics[i][j].x][mapPics[i][j].y], i*16, j*16, null);
				
			}
		}
	}
}
