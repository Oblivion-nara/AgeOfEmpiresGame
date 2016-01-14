package ageOfEmpires;

import java.awt.Color;
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
	
	private enum terrain{
		GRASS,DESERT
	}
	
	public Map(int mapsize){
		
		createMap(mapsize);
	
	}

	private void createMap(int mapsize){
		
		switch (mapsize){
		case 1:
			ySize = 1080;
			xSize = 1760;
			break;
		case 2:
			ySize = 1440;
			xSize = 2080;
			break;
		case 3:
			ySize = 1760;
			xSize = 2400;
			break;
		}
		
		map = new terrain[xSize/10][ySize/10];
		mapPics = new Point[xSize][ySize];
		
		mapSprites = ResourceLoader.getPlayerSprites("background/landSprites.png", 16, 16);
		
		
		for (int i = 0; i < mapPics.length; i++) {
			for (int j = 0; j < mapPics[0].length; j++) {
				mapPics[i][j] = new Point(0,Main.gen.nextInt(4));
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
//		for (int i = 0; i < mapPics.length; i++) {
//			for (int j = 0; j < mapPics[0].length; j++) {
//					g2.drawImage(mapSprites[mapPics[i][j].x][mapPics[i][j].y], i*16, j*16, null);
//				
//			}
//		}
		g2.setColor(Color.blue);
		int y = 0;
		int width = 10;
		int x = Main.gen.nextInt(xSize);
		do{
			
			int ychange = Main.gen.nextInt(10);
			x += (int)(Main.gen.nextGaussian()*5);
			width += (int)(Main.gen.nextGaussian()*5);
			if(width < 5) width = 5;
			if(y+ychange > ySize) y = ySize;
			if(x < 30){
				x = 30;
			}else if(x < xSize - 30){
				x = xSize - 30;
			}
			for (int i = y; i < y+ychange; i++) {
				g2.drawLine(x-width, i, x+width, i);
			}
			
		}while(y <= ySize);
		
	}
}















