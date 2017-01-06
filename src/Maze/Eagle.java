package Maze;
/**
 * 
 * The sidekick of our hero.
 *
 */
public class Eagle extends Actor
{
	protected boolean isAlive;
	protected boolean hasSword;
	protected boolean isLoose;
	protected int xReturn;
	protected int yReturn;
	protected char lb;
	/**
	 * Class constructor.
	 */
	public Eagle(Hero hero)
	{
		this.symbol = 'G';
		this.setLoose(false);
		this.setHasSword(false);
		this.setAlive();
		setLb('H');
	}
	/**
	 * Checks if any action is necessary on current position,
	 * such as picking up the sword, dropping it, equipping the hero
	 * or dying.
	 */
	protected boolean checkTarget(int x, int y)
	{
		if(x == Maze.getSword().getX() && y == Maze.getSword().getY()) //carries sword
		{
			if((Maze.isDragon(x,y) || Maze.isDragon(x+1, y) || Maze.isDragon(x-1, y) //if there is a dragon there the eagle dies
			|| Maze.isDragon(x,y+1) || Maze.isDragon(x, y-1)) && !this.isHasSword())
			{
				this.setDead();
				return false;
			}
			else
			{
				setHasSword(true);
				Maze.getSword().x_position = 0; // this is how the sword gets thrown off the maze
				Maze.getSword().y_position = 0;
				return true;
			}
		}
		else if(xReturn == getX() && yReturn == getY()) //the initial position
		{
			if(Maze.isDragon(x,y) && this.isHasSword())
			{
				this.setDead();
				Maze.getSword().setX(x);
				Maze.getSword().setY(y);
				Maze.getADragon(x, y).setSymbol('F');
				return false;
			}
			else if(Maze.isHero(getX(), getY())) // if the hero is at the inicial position he picks up the sword
			{
				this.setDead();
				Maze.sword.x_position = 0;
				Maze.sword.y_position = 0;
				Maze.getHero().setArmed();
				Maze.getHero().setSymbol('A');
				return true;
			}
			else //drops the sword
			{
				this.setSymbol('E');
				Maze.getSword().setX(this.getX());
				Maze.getSword().setY(this.getY());
				this.setDead();
				return true;
			}
		}
		return true;
	}
	/**
	 * Decides what direction the eagle moves in.
	 */
	public void decision(int x, int y) // the eagle starts by moving diagonally until it can move in a straight line
	{
		if(y == getY())
		{
			if(x > getX())
			{
				this.move(1,0);
			}
			else
			{
				this.move(-1,0);
			}
		}
		else if(x == getX())
		{
			if(y > getY())
			{
				this.move(0,1);
			}
			else
			{
				this.move(0,-1);
			}
		}
		else if(x > getX() && y > getY())
		{
			this.move(1,1);
		}
		else if(x < getX() && y < getY())
		{
			this.move(-1,-1);
		}
		else if(x > getX() && y < getY())
		{
			this.move(1,-1);	
		}
		else if(x < getX() && y > getY())
		{
			this.move(-1,1);
		}
	}
	/**
	 * Responsible for the movement of the eagle.
	 */
	public boolean move(int x_offset, int y_offset)
	{	
		Maze.setSquare(getLb(), this.getX(), this.getY());
		this.x_position += x_offset;
		this.y_position += y_offset;
		if(Maze.getSquare(getX(), getY()) == 'X')
			setLb(Maze.getSquare(getX(), getY()));
		else
			setLb(' ');
		if(!checkTarget(getX(), getY())) return true;
		Maze.setSquare(this.getSymbol(), getX(), getY());
		return true;
	}
	/**
	 * Kills the eagle.
	 */
	public void setDead()
	{
		this.isAlive = false;
		this.setX(0);
		this.setY(0);
		this.setSymbol('X');
	}
	/**
	 * Sets the eagle loose.
	 */
	public void setLoose()
	{
		isLoose = true;
		this.setX(Maze.getHero().getX());
		this.setY(Maze.getHero().getY());
		this.setXReturn(Maze.getHero().getX());
		this.setYReturn(Maze.getHero().getY());
	}
	public void setX(int x) {
		this.x_position = x;
	}
	
	public void setY(int y)
	{
		this.y_position = y;
	}

	public void setXReturn(int x) {

		this.xReturn = x;
	}

	public void setYReturn(int y) {
		this.yReturn = y;
	}

	public int getXReturn() {
		return xReturn;
	}

	public int getYReturn() {
		return yReturn;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive() {
		this.isAlive = true;
	}
	public boolean isHasSword() {
		return hasSword;
	}
	public void setHasSword(boolean hasSword) {
		this.hasSword = hasSword;
	}
	public boolean isLoose() {
		return isLoose;
	}
	public void setLoose(boolean isLoose) {
		this.isLoose = isLoose;
	}
	public char getLb() {
		return lb;
	}
	public void setLb(char lb) {
		this.lb = lb;
	}
	
}
