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
		zeroYCoord += 1;
	}
	public void moveDown(){
		zeroYCoord -= 1;
	}
	public void moveRight(){
		zeroXCoord -= 1;
	}
	public void moveLeft(){
		zeroXCoord += 1;
	}
	public void moveUR(){
		zeroYCoord += 1/1/root2;
		zeroXCoord -= 1/root2;
	}
	public void moveUL(){
		zeroYCoord += 1/root2;
		zeroXCoord += 1/root2;
	}
	public void moveDR(){
		zeroYCoord -= 1/root2;
		zeroXCoord -= 1/root2;
	}
	public void moveDL(){
		zeroYCoord -= 1/root2;
		zeroXCoord += 1/root2;
	}
	
	private void update(){
		
		if(updatetime <= System.currentTimeMillis()){
			
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
			}else
			
			updatetime = System.currentTimeMillis() + 10;
			
		}
		
	}

	private void draw(){

		Graphics g = getGraphics();
		Graphics2D g2d = (Graphics2D) g;
		Image offImage = createImage(map.getXSize(), map.getYSize());
		Graphics offGraphics = offImage.getGraphics();
		offGraphics.setColor(Color.BLUE);
		offGraphics.fillRect((int)-zeroXCoord, (int)-zeroYCoord, width, height);
		offGraphics.setColor(Color.orange);
		offGraphics.drawRect(0, 0, width+1, height+1);
		offGraphics.setColor(Color.black);
		offGraphics.fillRect(width/2-10, height/2-10, 20, 20);
		g2d.drawImage(offImage, (int)zeroXCoord, (int)zeroYCoord, width, height, null);
		
	}
	
}
