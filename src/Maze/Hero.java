package Maze;

import java.util.Random;
/**
 * The protagonist of our tale.
 */
public class Hero extends Actor
{
	protected boolean isArmed;
	protected boolean hasEagle;
	protected static String upKey = "w";
	protected static String downKey = "s";
	protected static String leftKey = "a";
	protected static String rightKey = "d";
	protected static String releaseKey = "r";
	/**
	 * Class constructor.
	 */
	public Hero()
	{
		super('H');
		setArmed(false);
		setHasEagle(true);
	}
	/**
	 * Randomly places the hero on the maze.
	 */
	protected void random_placement()
	{
		int Size = 0;
		if(Maze.isDef())
			Size = 10;
		else if(!Maze.isDef())
			Size = Maze.getSize();
		
		Random random = new Random();
		
		int hero_x_random = random.nextInt(Size);
		int hero_y_random = random.nextInt(Size);
		
		if(Maze.isEmpty(hero_x_random, hero_y_random))
	    {
	    	x_position = hero_x_random;
	    	y_position = hero_y_random;
		    Maze.setSquare(symbol, hero_x_random, hero_y_random);  
	    }
	   else random_placement();
	}
	/**
	 * Using user input, decides what direction to move in
	 * or releases the eagle.
	 */
	public boolean decision(String move)
	{
		if(move.equals(releaseKey) && this.isHasEagle() && !this.isArmed()){ //release eagle
			this.releaseEagle();
			return true;
		}
		else if(move.equals(upKey)){
			if(!move(-1, 0))
				return false;
			else return true;
		}
		else if(move.equals(downKey)){
			if(!move(1, 0))
				return false;
			else return true;
		}
		else if(move.equals(leftKey)){
			if(!move(0, -1))
				return false;
			else return true;
		}
		else if(move.equals(rightKey)){
			if(!move(0, 1))
				return false;
			else return true;
		}
	    return false;
	}
	/**
	 * Releases the eagle.
	 */
	private void releaseEagle()
	{
		this.setHasEagle(false);
		Maze.eagle.setLoose();
		this.setSymbol('H');
	}
	/**
	 * Responsible for the movement of the hero.
	 */
	public boolean move(int x_offset, int y_offset)
	{
			if(!Maze.isDef()){
				if(getX() + x_offset < 0 || getY() + y_offset < 0 || getX() + x_offset > Maze.size*2 || getY() + y_offset > Maze.size*2)
					return false;}
			else if(Maze.isDef()){
				if(getX() + x_offset < 0 || getY() + y_offset < 0 || getX() + x_offset > 9 || getY() + y_offset > 9)
					return false;}
			if (Maze.isWall(x_position + x_offset, y_position + y_offset)
			 || (Maze.isEagle(x_position + x_offset, y_position + y_offset) && Maze.eagle.getLb() == 'X')
			 || Maze.isDragon(x_position + x_offset, y_position + y_offset)
			 || (Maze.isExit(x_position + x_offset, y_position + y_offset) && !Maze.isEdd()))
			{
				return false;
			}
			else{
				Maze.setSquare(' ', this.getX(), this.getY());
				this.setX(this.getX() + x_offset);
				this.setY(this.getY() + y_offset);
				if(this.getX() == Maze.getSword().getX() && this.getY() == Maze.getSword().getY())
				{
					Maze.getSword().setX(0);
					Maze.getSword().setY(0);
					this.setArmed();
				}
				if(Maze.isExit(x_position, y_position)){
					this.setSymbol('V');
					Maze.gameOn = false;
				}
				return true;
			}
	}
	/**
	 * Arms the hero.
	 */
	public void setArmed()
	{
		this.isArmed = true;
		this.setSymbol('A');
		Maze.setSquare(this.getSymbol(), this.getX(), this.getY());
	}
	/**
	 * Disarms the hero.
	 */
	public void setUnarmed()
	{
		this.setArmed(false);
		this.setSymbol('H');
		Maze.setSquare(this.getSymbol(), this.getX(), this.getY());
	}
	public void setX(int x)
	{
		x_position = x;
	}
	public void setY(int y)
	{
		y_position = y;
	}
	public boolean isArmed() 
	{
		return isArmed;
	}
	public static String getUpKey() {
		return upKey;
	}
	public static void setUpKey(String upKeyA) {
		upKey = upKeyA;
	}
	public static String getDownKey() {
		return downKey;
	}
	public static void setDownKey(String downKeyA) {
		downKey = downKeyA;
	}
	public static String getLeftKey() {
		return leftKey;
	}
	public static void setLeftKey(String leftKeyA) {
		leftKey = leftKeyA;
	}
	public static String getRightKey() {
		return rightKey;
	}
	public static void setRightKey(String rightKeyA) {
		rightKey = rightKeyA;
	}
	public static void setReleaseKey(String release) {
		releaseKey = release;	
	}
	public boolean isHasEagle() {
		return hasEagle;
	}
	public void setHasEagle(boolean hasEagle) {
		this.hasEagle = hasEagle;
	}
	public void setArmed(boolean isArmed) {
		this.isArmed = isArmed;
	}
}