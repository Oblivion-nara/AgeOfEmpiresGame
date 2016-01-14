package ageOfEmpires;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.JFrame;

import utils.InputHandler;

public class Main extends JFrame{
	
	private static final long serialVersionUID = 1L;
	public static int width,height;
	public static double zeroXCoord,zeroYCoord;
	static boolean running = false;
	
	static final double root2 = Math.sqrt(2);
	static Random gen = new Random();
	
	private int mapSize = 0;
	
	private Image gameMap;
	
	private InputHandler input;
	private Map map;
	private static GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];

	
	public static void main(String[] args) {
		Main main = new Main();
		main.run();
	}
	
	private void run(){
		initialise();
		while(mapSize == 0){
			mapSize();
		}
		int count = 0;
		while(running){
			System.out.println(count);
			count++;
			long beforeTime = System.currentTimeMillis();
			update();
			draw();
			long afterTime = System.currentTimeMillis();
			long diff = afterTime - beforeTime;
			long waitTime = diff / 45;
			try{
				if(waitTime > 0){
					Thread.sleep(waitTime);
				}
			}catch (Exception e){
				e.printStackTrace();
			}
		}
		dispose();
		
	}
	
	private void mapSize(){

		Graphics2D g2d = (Graphics2D)(getGraphics());
		Image offImage = createImage(width,height);
		Graphics offGraphics = offImage.getGraphics();
		offGraphics.setColor(Color.BLACK);
		offGraphics.fillRect(0, 0, width, height);
		offGraphics.setColor(Color.gray);
		Font j= new Font("Arial", Font.BOLD, 50);
		
		offGraphics.setFont(j);
		offGraphics.drawString("Choose the map size.", width/2-(int)(26*"Choose the map size.".length()/2.0), 150);
		offGraphics.fillRoundRect((width/2)-250, (height/4), 500, 200, 10000, 10000);
		offGraphics.fillRoundRect((width/2)-250, (2*height/4), 500, 200, 10000, 10000);
		offGraphics.fillRoundRect((width/2)-250, (3*height/4), 500, 200, 10000, 10000);
		offGraphics.setColor(Color.orange);
		offGraphics.drawString("small", width/2-(int)(26*"small".length()/2.0), height/4 + 100);
		offGraphics.drawString("medium", width/2-(int)(32*"medium".length()/2.0), 2*height/4 + 100);
		offGraphics.drawString("large", width/2-(int)(26*"large".length()/2.0), 3*height/4 + 100);

		
		if(input.isMouseDown(1)){
			if((new Rectangle(width/2 - 250,height/4,500,200)).contains(input.getMousePositionOnScreen())){
				mapSize = 1;
				offGraphics.setColor(Color.BLACK);
				offGraphics.fillRect(0, 0, width, height);
				offGraphics.setColor(Color.orange);
				offGraphics.drawString("Loading...", width/2-(400), height/2);
			}else if((new Rectangle(width/2 - 250,2*height/4,500,200)).contains(input.getMousePositionOnScreen())){
				mapSize = 2;
				offGraphics.setColor(Color.BLACK);
				offGraphics.fillRect(0, 0, width, height);
				offGraphics.setColor(Color.orange);
				offGraphics.drawString("Loading...", width/2-(400), height/2);
			}else if((new Rectangle(width/2 - 250,3*height/4,500,200)).contains(input.getMousePositionOnScreen())){
				mapSize = 3;
				offGraphics.setColor(Color.BLACK);
				offGraphics.fillRect(0, 0, width, height);
				offGraphics.setColor(Color.orange);
				offGraphics.drawString("Loading...", width/2-(400), height/2);
			}
		}
		
		g2d.drawImage(offImage,0,0,width,height,null);
		if(mapSize != 0){
			map = new Map(mapSize);
			int x = map.getXSize();
			int y = map.getYSize();
			gameMap = createImage(x, y);
			Graphics gameGraphics = gameMap.getGraphics();
			zeroXCoord = (Main.width/2)-(x/2);
			zeroYCoord = (Main.height/2)-(y/2);
			g2d.clearRect(0, 0, width, height);
			map.draw(gameGraphics);
		}
	}
	
	private void initialise(){
		running = true;
		setTitle("Age of Empires");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		width = Toolkit.getDefaultToolkit().getScreenSize().width;
		height = Toolkit.getDefaultToolkit().getScreenSize().height;
		setSize(width, height);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setUndecorated(true);
		device.setFullScreenWindow(this);
		setVisible(running);
		setResizable(false);
		zeroXCoord = 0;
		zeroYCoord = 0;
		input = new InputHandler(this);
		
	}
	
	public void moveUp(){
		if(zeroYCoord < 0){
		zeroYCoord += 1;
		}
	}
	public void moveDown(){
		if(zeroYCoord > height-map.getYSize()){
			zeroYCoord -= 1;
		}
	}
	public void moveRight(){
		if(zeroXCoord > width-map.getXSize()){
			zeroXCoord -= 1;
		}
	}
	public void moveLeft(){
		if(zeroXCoord < 0){
			zeroXCoord += 1;
		}
	}
	public void moveUR(){
		if(zeroYCoord >= 0){
			moveRight();
		}else if(zeroXCoord <= width-map.getXSize()){
			moveUp();
		}else{
			zeroYCoord += 1/1/root2;
			zeroXCoord -= 1/root2;
		}
	}
	public void moveUL(){
		if(zeroYCoord >= 0){
			moveLeft();
		}else if(zeroXCoord >= 0){
			moveUp();
		}else{
			zeroYCoord += 1/root2;
			zeroXCoord += 1/root2;
		}
	}
	public void moveDR(){
		if(zeroYCoord <= height-map.getYSize()){
			moveRight();
		}else if(zeroXCoord <= width-map.getXSize()){
			moveDown();
		}else{
			zeroYCoord -= 1/root2;
			zeroXCoord -= 1/root2;
		}
	}
	public void moveDL(){
		if(zeroYCoord <= height-map.getYSize()){
			moveLeft();
		}else if(zeroXCoord >= 0){
			moveDown();
		}else{
			zeroYCoord -= 1/root2;
			zeroXCoord += 1/root2;
		}
	}
	
	private void update(){
		
		if(input.isKeyDown(KeyEvent.VK_W)){
			if(input.isKeyDown(KeyEvent.VK_D)){
				moveUR();
			}else if(input.isKeyDown(KeyEvent.VK_A)){
				moveUL();
			}else{
				moveUp();
			}
		}else if(input.isKeyDown(KeyEvent.VK_S)){
			if(input.isKeyDown(KeyEvent.VK_D)){
				moveDR();
			}else if(input.isKeyDown(KeyEvent.VK_A)){
				moveDL();
			}else{
				moveDown();
			}
		}else if(input.isKeyDown(KeyEvent.VK_D)){
			moveRight();
		}else if(input.isKeyDown(KeyEvent.VK_A)){
			moveLeft();
		}
		
		if(input.isKeyDown(KeyEvent.VK_ESCAPE)){
			System.exit(0);
		}
		
	}
		
	private void draw(){

		Graphics2D g2d = (Graphics2D)(getGraphics());
		
		g2d.drawImage(gameMap, (int)zeroXCoord, (int)zeroYCoord, map.getXSize(), map.getYSize(), null);
		
	}
	
}
