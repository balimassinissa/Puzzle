package model;

import java.util.HashMap;
import java.util.Map;
public class Element {
	/**
	 * this class represent an element on the gate
	 */
	private Coordinate cor;
	private int value ;
	/**
	 * @param cor
	 * @param value
	 */
	public Element(Coordinate cor, int value) {
		this.cor = cor;
		this.value = value;
	}
	/**
	 * @return the coordinate of element
	 */
	public Coordinate getCor() {
		return cor;
	}
	/**
	 * @param cor the cor to set
	 */
	public void setCor(Coordinate cor) {
		this.cor = cor;
	}
	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(int value) {
		this.value = value;
	}
	/**
	 * 
	 * @param game
	 * @return the map of the boxes next to the element
	 */
	public Map<Element,Integer> getNexTo(Game game){
		Map<Element,Integer> res = new HashMap<Element,Integer>();
		int lines = game.getnbLines();
		int columns = game.getnbColumns();
		if(this.getCor().getX()+1 < lines && game.getGate()[this.getCor().getX()+1][this.getCor().getY()].getValue() != -1  )//value ==-1 ==> empty box
			res.put(game.getGate()[this.getCor().getX()+1][this.getCor().getY()], 1);//1 means the box below
		if(this.getCor().getX()-1 >= 0 && game.getGate()[this.getCor().getX()-1][this.getCor().getY()].getValue() != -1   )//value ==-1 ==> empty box
			res.put(game.getGate()[this.getCor().getX()-1][this.getCor().getY()], 2);//1 means the box above
		if(this.getCor().getY()+1 < columns && game.getGate()[this.getCor().getX()][this.getCor().getY()+1].getValue() != -1  )//value ==-1 ==> empty box
			res.put(game.getGate()[this.getCor().getX()][this.getCor().getY()+1], 3);//1 means the box on the right side 
		if(this.getCor().getY()-1 >= columns && game.getGate()[this.getCor().getX()][this.getCor().getY()-1].getValue() != -1 )//value ==-1 ==> empty box
			res.put(game.getGate()[this.getCor().getX()][this.getCor().getY()-1], 4);//1 means the box below
		return res;
	}
	
	/**
	 * 
	 * @param game
	 * @return true if the element is well placed in the gride
	 */
	public boolean isWellPlaced(Game game) {
		int columns = game.getnbColumns();
		boolean wellPlaced = true ;
		for (Map.Entry<Element,Integer> mapentry : this.getNexTo(game).entrySet()) {
	           if(mapentry.getValue() == 1 && mapentry.getKey().value != this.value+columns) wellPlaced = false ;
	           else if(mapentry.getValue() == 2 && mapentry.getKey().value != this.value-columns) wellPlaced = false ;
	           else if(mapentry.getValue() == 3 && mapentry.getKey().value != this.value+1) wellPlaced = false ;
	           else if(mapentry.getValue() == 4 && mapentry.getKey().value != this.value-1) wellPlaced = false ;
	      
	        }
		return wellPlaced;
	}
}
