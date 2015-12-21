package ageOfEmpires;

public class Map {

	private terrain[][] map;
	private int xSize,ySize;
	
	private enum terrain{
		GRASS,WOODLAND
	}
	
	public Map(){
		
		int map = Main.gen.nextInt(5);
		switch (map){
		case 0:
			break;
		case 1:
			break;
		case 2:
			break;
		case 3:
			break;
		case 4:
			break;
		}
		
	}
	
	public int getXSize(){
		return xSize;
	}
	public int getYSize(){
		return ySize;
	}
	
}
