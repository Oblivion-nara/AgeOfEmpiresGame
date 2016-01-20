package ageOfEmpires;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;

import utils.NoiseGenerator;
import utils.MathHelper;
import utils.ResourceLoader;

public class Map{

	private Point[][] mapPics;   //which mapSprite to use, as a point
	private terrain[][] map;
	private Image[][] mapSprites;
	private double[][] noise;
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
			xSize = MathHelper.ceiling(Main.width,160)*160;
			ySize = MathHelper.ceiling(Main.height,160)*160;
			break;
		case 2:
			xSize = MathHelper.ceiling(3*MathHelper.ceiling(Main.width,2),160)*160;
			ySize = MathHelper.ceiling(3*MathHelper.ceiling(Main.height,2),160)*160;
			break;
		case 3:
			xSize = MathHelper.ceiling(2*Main.width,160)*160;
			ySize = MathHelper.ceiling(2*Main.height,160)*160;
			break;
		}
		
		/*map = new terrain[xSize/10][ySize/10];
		mapPics = new Point[xSize][ySize];
		
		mapSprites = ResourceLoader.getPlayerSprites("background/landSprites.png", 16, 16);
		
		
		for (int i = 0; i < mapPics.length; i++) {
			for (int j = 0; j < mapPics[0].length; j++) {
				mapPics[i][j] = new Point(0,Main.gen.nextInt(4));
			}
		}*/
		
		NoiseGenerator noiseGen = new NoiseGenerator(Main.gen.nextLong());
		noise = noiseGen.getPerlinNoise(xSize, ySize, 4, 5);
		
	}
	
	public int getXSize(){
		return xSize;
	}
	public int getYSize(){
		return ySize;
	}
	
	public void draw(Graphics2D g2){
		
//		Graphics2D g2 = (Graphics2D)g;
		/*for (int i = 0; i < mapPics.length; i++) {
			for (int j = 0; j < mapPics[0].length; j++) {
					g2.drawImage(mapSprites[mapPics[i][j].x][mapPics[i][j].y], i*16, j*16, null);
				
			}
		}*/
		g2.setColor(Color.BLACK);
		g2.fillRect(0, 0, xSize, ySize);
		
		
		
		
		
		g2.setColor(Color.blue);
		int y = 0;
		int width = 10;
		int x = Main.gen.nextInt(xSize);
		do{
			
			int ychange = Main.gen.nextInt(20)+1;
			int xchange = (int)(Main.gen.nextGaussian()*20);
			int wchange = (int)(Main.gen.nextGaussian()*20);
			
			if(width + wchange < 5){ 
				width = 5;
				wchange = 0;
			}
			
			if(y+ychange > ySize){
				y = ySize;
				ychange = 0;
			}
			
			if(x+xchange < 30){
				x = 30;
				xchange = 0;
			}else if(x+xchange > xSize - 30){
				x = xSize - 30;
				xchange = 0;
			}
			
			double xstep = xchange/ychange;
			double wstep = width/ychange;
			
			for (int i = y; i < y+ychange; i++) {
				g2.drawLine(x-width, i, x+width, i);
				x += xstep;
				width += wstep; 
			}
			y += ychange;
		}while(y <= ySize);
		
//		for(int i = 0; i < noise.length; i++){
//			for(int j = 0; j < noise[0].length; j++){
//				if(noise[i][j] < 0.53 && noise[i][j] > 0.47){
//					g2.setColor(Color.blue);
//				}else{
//					g2.setColor(Color.green);
//				}
//				g2.fillRect(i*12, j*12, 12, 12);;
//			}
//		}
	}
}















