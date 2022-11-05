package controler;

import model.Coordinate;
import model.Game;
public abstract class AbstractControler {
	protected Game game ;
	protected int nbMoveCreation ;
	public AbstractControler(Game game , int nbMoveCreation) {
		this.nbMoveCreation = nbMoveCreation;
		this.game = game ;
		this.game.initialiseGame(nbMoveCreation);
	}
	/**
	 * 
	 * @return the gate of the game as gate of integers
	 */
	public abstract int[][] getGate();
	/**
	 * 
	 * @return the nbMoveCreation
	 */
	public abstract int getnbMoveCreation();
	/**
	 * 
	 * @param nbMove
	 */
	public abstract void setnbMoveCreation(int nbMove);
	/**
	 * 
	 * @return
	 */
	public abstract int getnbLines() ;
	/**
	 * @return the nbColumns
	 */
	public abstract int getnbColumns();
	/**
	 * 
	 * @param nbLines
	 */
	public abstract void setnbLines(int nbLines);
	/**
	 * 
	 * @param nbColumns
	 */
	public abstract void setnbColumns(int nbColumns);
	/**
	 * 
	 * @param value the value of the element 
	 */
	public abstract void play(int value);
	/**
	 * 
	 * @return true if the controller is resolved
	 */
	public abstract boolean isResolved();
	/**
	 * 
	 * @param value the value of the element 
	 * @param direction the direction of the move
	 * @return true if the move can be play
	 */
	public abstract boolean canBePlayed(int direction ) ;
	/**
	 * reset the game
	 */
	public abstract void reset();
	/**
	 * 
	 * @return 1 if the value is movable else 0
	 */
	public abstract Coordinate getEmptyPosition();
	/**
	 * 
	 * @param value
	 * @return the coordinate of value in gate
	 */
	public abstract Coordinate getCoordinate(int value);
	/**
	 * 
	 * @return the value situated up the empty case
	 */
	public abstract int getUpValue();
	/**
	 * 
	 * @return the value situated down the empty case
	 */
	public abstract int getDownValue();
	/**
	 * 
	 * @return the value situated left the empty case
	 */
	public abstract int getLeftValue();
	/**
	 * 
	 * @return the value situated right the empty case
	 */
	public abstract int getRightValue();
	/**
	 * 
	 * @return the creenMessage
	 */
	public abstract String getScreenMessage() ;
	
}
