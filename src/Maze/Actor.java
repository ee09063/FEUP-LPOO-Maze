package Maze;
/**
 * 
 * Responsible for the positions and symbol
 * of the actors -> Hero, Sword, Eagle and Dragon
 *
 */
public class Actor {
	protected  int x_position;
	protected  int y_position;
	protected  char symbol;
	Actor(){}
	
	Actor(char s)
	{
		symbol = s;
	}
	
	public int getX()
	{
		return x_position;
	}
	public int getY()
	{
		return y_position;
	}
	public char getSymbol()
	{
		return symbol;
	}
	public void setSymbol(char s)
	{
		symbol = s;
	}
}
