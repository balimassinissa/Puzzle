package model;

public class Coordinate {
	/**
	 * this class represent a coordinate on the gate
	 */
	private int x;
	private int y;
	/**
	 * 
	 * @param x
	 * @param y
	 */
	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}
	/**
	 * 
	 * @return x coordinate
	 */
	public int getX() {
		return x;
	}
	/**
	 * 
	 * @param x
	 */
	public void setX(int x) {
		this.x = x;
	}
	/**
	 * 
	 * @return y coordinate
	 */
	public int getY() {
		return y;
	}
	/**
	 * 
	 * @param y
	 */
	public void setY(int y) {
		this.y = y;
	}
	/**
	 * 
	 * @param nbLines
	 * @param nbColumns
	 * @return true if the coordinate is on the gate nblines*nbcolumns, else return false
	 */
	public boolean isInTheGate(int nbLines,int nbColumns) {
		return(this.x>=0 && this.x<nbLines && this.y>=0 && this.y< nbColumns );
	}
	
}
