package model;
import java.util.Random;
import utils.AbstractModel;

public class Game extends AbstractModel {	
	/**
	 * this class represent a game
	 */
	private Element [][] gate;
	private int nbLines;
	private int nbColumns;
	private Coordinate emptyPosition ;
	private String screenMessage;
	/**
	 * 
	 * @param nbLines
	 * @param nbColumns
	 */
	public Game(int nbLines,int nbColumns) {
		this.gate = new Element[nbLines][nbColumns];
		this.nbLines = nbLines;
		this.nbColumns = nbColumns;
		this.emptyPosition = new Coordinate(nbLines-1,nbColumns-1);
		screenMessage = "jouez";
		}
	/**
	 * @return the gate
	 */
	public Element[][] getGate() {
		return gate;
	}

	/**
	 * @param gate the gate to set
	 */
	public void setGate(Element[][] gate) {
		this.gate = gate;
	}
	
	/**
	 * @return the nbLines
	 */
	public int getnbLines() {
		return nbLines;
	}
	/**
	 * @param nbLines
	 */
	public void setnbLines(int nbLines) {
		this.nbLines = nbLines;
	}
	/**
	 * @return the nbColumns
	 */
	public int getnbColumns() {
		return nbColumns;
	}
	/**
	 * @param nbLines
	 */
	public void setnbColumns(int nbColumns) {
		this.nbColumns = nbColumns;
	}
	/**
	 * @return the screenMessage
	 */
	public String getScreenMessage() {
		return screenMessage;
	}
	/**
	 * 
	 * @return the coordinate of empty case
	 */
	public Coordinate getEmptyPosition() {
		return emptyPosition;
	}
	/**
	 * 
	 * @param emptyPosition
	 * set the coordinate of empty case
	 */
	public void setEmptyPosition(Coordinate emptyPosition) {
		this.emptyPosition = emptyPosition;
	}
	/**
	 * 
	 * @return true if the game is resolved
	 */
	public boolean isResolved() {
		for(int i = 0 ; i < this.nbLines ; i++) {
			for(int j = 0 ; j < this.nbColumns ; j ++) {
				if(i == this.nbLines-1 && j == nbColumns-1) return true ;
				if(!this.gate[i][j].isWellPlaced(this)) 
				{
					return false;
				}
			}
		}
		return true;
	}
	/**
	 * 
	 * @param value
	 * @return the coordinate of the case which contains value
	 */
	public Coordinate getCoordinate(int value) {
		for(int i=0;i<this.nbLines;i++) {
			for(int j=0;j<this.nbColumns;j++) {
				if(this.gate[i][j].getValue() == value)
					return new Coordinate(i,j);
			}
		}
		return null;
	}
	/**
	 * 
	 * @param value
	 * move value if it's possible
	 */
	public void play(int value) {
		if(value == -1)
			screenMessage = "La case est vide";
		else {
			int direction = this.isMovable(value);
			if(this.canBePlayed(direction)&& direction != -1 ) {
				fireChange(value);
				int x = this.emptyPosition.getX();
				int y = this.emptyPosition.getY();
				switch(direction) {
				case 1 :
					this.gate[x][y].setValue(this.gate[x+1][y].getValue());
					this.gate[x+1][y].setValue(-1); 
					this.setEmptyPosition(new Coordinate(x+1,y));
					break;
				case 2 :
					this.gate[x][y].setValue(this.gate[x-1][y].getValue());
					this.gate[x-1][y].setValue(-1); 
					this.setEmptyPosition(new Coordinate(x-1,y));
					break;
				case 3 :
					this.gate[x][y].setValue(this.gate[x][y+1].getValue());
					this.gate[x][y+1].setValue(-1); 
					this.setEmptyPosition(new Coordinate(x,y+1));
					break;
				case 4 :
					this.gate[x][y].setValue(this.gate[x][y-1].getValue());
					this.gate[x][y-1].setValue(-1); 
					this.setEmptyPosition(new Coordinate(x,y-1));
					break;
				}
			}
			else {
				screenMessage = "Element indeplaçcable";
			}
		}
		fireChange();
	}
	/**
	 * @retrun true if we can play in direction else return false
	 */
	public boolean canBePlayed(int direction) {
		switch (direction) {
		case 1 :
			if(this.emptyPosition.getX()+1 < this.nbLines ) return true ;
			else return false ;

		case 2 :
			if(this.emptyPosition.getX()-1 >= 0 ) return true ;
			else return false ;
		case 3 :
			if(this.emptyPosition.getY()+1 < this.nbColumns ) return true ;
			else return false ;
		case 4 :
			if(this.emptyPosition.getY()-1 >= 0 ) return true ;
			else return false ;
		default : return false ;
		}
			
	}
	/**
	 * @retrun -1 if the case which contains value can't be moved else return the direction that we can move the case
	 */
	public int isMovable(int value) {
		int x = this.emptyPosition.getX();
		int y = this.emptyPosition.getY();
		if(x+1<this.nbLines && this.gate[x+1][y].getValue() == value) return 1 ;
		else if(x-1>=0 && this.gate[x-1][y].getValue() == value) return 2 ;
		else if(y-1>=0 && this.gate[x][y-1].getValue() == value) return 4 ;
		else if(y+1<this.nbColumns && this.gate[x][y+1].getValue() == value) return 3 ;
		else return -1 ;
	}
	/**
	 * 
	 * @param nbMove
	 * initialise the game on playing nbMoves
	 */
	public void initialiseGame(int nbMove) {
		int moveAp = 0;
		this.emptyPosition = new Coordinate(this.nbLines-1,this.nbColumns-1);
		this.gate = new Element[this.nbLines][this.nbColumns];
		for(int i = 0 ; i < this.nbLines ; i++) {
			for(int j = 0 ; j < this.nbColumns ; j ++) {
				this.gate[i][j] = new Element(new Coordinate(i,j),i*this.nbColumns+1+j);
		
			}
		}
		this.gate[this.nbLines-1][this.nbColumns-1].setValue(-1);
		int value = 1 ;
		while(moveAp < nbMove || this.isResolved() ) {
			value = 1 + new Random().nextInt(this.nbColumns*this.nbLines-1);
			if(this.canBePlayed(this.isMovable(value))) {
				this.play(value);
				moveAp ++ ;
				}
		}
	}
	/**
	 * @return the value of the case situated up the empty case if exists else return -1
	 */
	public  int getUpValue() {
		if(this.emptyPosition.getX()-1 >=0)
			return this.gate[this.emptyPosition.getX()-1][this.emptyPosition.getY()].getValue();
		else return -1 ;
	}
	/**
	 * @return the value of the case situated down the empty case if exists else return -1
	 */
	public  int getDownValue() {
		if(this.emptyPosition.getX()+1 < this.getnbLines())
			return this.gate[this.emptyPosition.getX()+1][this.emptyPosition.getY()].getValue();
		else return -1 ;
	}
	/**
	 * @return the value of the case situated left the empty case if exists else return -1
	 */
	public  int getLeftValue() {
		if(this.emptyPosition.getY()-1 >=0)
			return this.gate[this.emptyPosition.getX()][this.emptyPosition.getY()-1].getValue();
		else return -1 ;
	}
	/**
	 * @return the value of the case situated left the empty case if exists else return -1
	 */
	public  int getRightValue() {
		if(this.emptyPosition.getY()+1 <this.nbColumns)
			 return this.gate[this.emptyPosition.getX()][this.emptyPosition.getY()+1].getValue();
		else return -1 ;
	}
}
