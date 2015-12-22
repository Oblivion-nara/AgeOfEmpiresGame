package ageOfEmpires;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.JFrame;

public class Main extends JFrame{
	
	private static final long serialVersionUID = 1L;
	public static int width,height;
	public static double zeroXCoord,zeroYCoord;
	static boolean running = false;
	
	private long updatetime;
	
	static final double root2 = Math.sqrt(2);
	static Random gen = new Random();
	
	private InputHandler input;
	private Map map;
	private static GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];

	
	public static void main(String[] args) {
		Main main = new Main();
		main.run();
	}
	
	private void run(){
		initialise();
		while(running){
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
	
	private void initialise(){
		running = true;
		setTitle("Age of Empires");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		width = Toolkit.getDefaultToolkit().getScreenSize().width;
		height = Toolkit.getDefaultToolkit().getScreenSize().height;
		setSize(width, height);
		System.out.println(width+","+height);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setUndecorated(true);
		device.setFullScreenWindow(this);
		setVisible(running);
		setResizable(false);
		input = new InputHandler(this);
		map = new Map();
		zeroXCoord = 0;
		zeroYCoord = 0;
		updatetime = System.currentTimeMillis() + 10;
		
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
		
		updatetime = System.currentTimeMillis() + 10;
		
	}
		
	private void draw(){

		Graphics g = getGraphics();
		Graphics2D g2d = (Graphics2D) g;
		Image offImage = createImage(map.getXSize(), map.getYSize());
		Graphics offGraphics = offImage.getGraphics();
		offGraphics.fillRect(0, 0, map.getXSize(), map.getYSize());
		map.draw(offGraphics);
		g2d.drawImage(offImage, (int)zeroXCoord, (int)zeroYCoord, map.getXSize(), map.getYSize(), null);
		
	}
	
}
