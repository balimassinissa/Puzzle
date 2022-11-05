package controler;

import model.Coordinate;
import model.Game;


public class GameControler extends AbstractControler {

	public GameControler(Game game,int nbMoveCreation) {
		super(game,nbMoveCreation);
	}
	@Override
	public int[][] getGate() {
		int [][] res = new int[this.getnbLines()][this.getnbColumns()]; 
		for(int i = 0 ; i < this.getnbLines();i++) {
			for(int j = 0 ; j<this.getnbColumns();j++) {
				res[i][j] = this.game.getGate()[i][j].getValue();
			}
		}
		return res ;
	}
	public int getnbLines() {
		return this.game.getnbLines();
	}
	public void setnbLines(int nbLines) {
		this.game.setnbLines(nbLines);
	}
	public int getnbColumns() {
		return this.game.getnbColumns();
	}
	public void setnbColumns(int nbColumns) {
		this.game.setnbColumns(nbColumns);
	}
	public int getnbMoveCreation() {
		 return this.nbMoveCreation;
	 }
	public void setnbMoveCreation(int nbMove) {
		this.nbMoveCreation = nbMove;
	}
	@Override
	public void play(int value) {
		
		this.game.play(value);

	}
	@Override
	public boolean isResolved() {
		return this.game.isResolved();
	}
	@Override
	public boolean canBePlayed(int direction) {
		return this.game.canBePlayed(direction);
	}
	public void reset() {
		this.game.initialiseGame(this.getnbMoveCreation());
	}
	public Coordinate getEmptyPosition() {
		return this.game.getEmptyPosition();
	}
	public Coordinate getCoordinate(int value) {
		return game.getCoordinate(value);
	}
	public  int getUpValue() {
		return this.game.getUpValue();
	}
	public  int getDownValue() {
		return this.game.getDownValue();
	}
	public  int getLeftValue() {
		return this.game.getLeftValue();
	}
	public  int getRightValue() {
		return this.game.getRightValue();
	}
	@Override
	public String getScreenMessage() {
		return game.getScreenMessage();
	}


}
