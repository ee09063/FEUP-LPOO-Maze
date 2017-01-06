package Maze;

import java.util.Random;
/**
 * 
 * The villains of our tale.
 *
 */
public class Dragon extends Actor
{
	protected boolean isAsleep;
	/**
	 * Class constructor.
	 */
	public Dragon()
	{
		super('D');
		setAsleep(false);
	}
	/**
	 * Randomly places the dragon on the maze.
	 */
	protected void random_placement()
	{
		int Size = 0;
		
		if(Maze.isDef())
			Size = 10;
		else if(!Maze.isDef())
			Size = Maze.getSize();
			
		Random random = new Random();
		int dragon_x_random = random.nextInt(Size);
		int dragon_y_random = random.nextInt(Size);
	    if(Maze.isEmpty(dragon_x_random, dragon_y_random))
	    {
	    	x_position = dragon_x_random;
		    y_position = dragon_y_random;
		    Maze.setSquare(symbol, dragon_x_random, dragon_y_random);
	    }
	    else random_placement();
	}
	/**
	 * Selects a random direction for the dragon to move in.
	 * If the Dragon cannot move in that direction, it will select another one.
	 */
	protected void decision()
	{
		//tries to move outside the maze, for custom Mazes
		if(!Maze.isDef() && (getX()-1 < 0 || getY()-1 < 0 || getX()+1 > Maze.getSize()-1 || getY()+1 > Maze.getSize()-1))
			return;
		//cornered, in which case the dragon cannot move, even on top of the hero. Kills are adjacent only
		if((Maze.isWall(getX()-1, getY()) || Maze.isDragon(getX()-1, getY()) || Maze.isExit(getX()-1, getY()) || Maze.isHero(getX()-1,  getY()))
				&& (Maze.isWall(getX()+1, getY()) || Maze.isDragon(getX()+1, getY()) || Maze.isExit(getX()+1, getY())|| Maze.isHero(getX()+1,  getY()))
				&& (Maze.isWall(getX(), getY()-1) || Maze.isDragon(getX(), getY()-1) || Maze.isExit(getX(), getY()-1)|| Maze.isHero(getX(),  getY()-1))
				&& (Maze.isWall(getX(), getY()+1) || Maze.isDragon(getX(), getY()+1) || Maze.isExit(getX(), getY()+1)|| Maze.isHero(getX(),  getY()+1)))
			return;
		//if asleep, does nothing
		if(this.isAsleep())
			return;
		//if he can't move, does nothing
		if(!Maze.isCanMove())
			return;
		else 
		{
			Random random = new Random();
			int dragon_move = random.nextInt(4)+1;
			switch(dragon_move){
				case 1: //up
					if(!move(-1,0))
						this.decision();
					break;
				case 2://down
					if(!move(1,0))
						this.decision();
					break;
				case 3://left
					if(!move(0,-1))
						this.decision();
					break;
				case 4://right
					if(!move(0,1))
						this.decision();
					break;
			}
		}
	}
	/**
	 * Created for test purposes.
	 * Allows the dragon to be controlled manually.
	 */
	public void manualMove(String move)
	{
		if(move.equals("w")){
			move(-1, 0);
		}
		else if(move.equals("s")){
			move(1, 0);
		}
		else if(move.equals("a")){
			move(0, -1);
		}
		else if(move.equals("d")){
			move(0, 1);
		}
	}
	/**
	 * Checks for the hero in adjacent positions.
	 */
	protected boolean lookForHero()
	{
		if(Maze.isHero(x_position-1, y_position)
				|| Maze.isHero(x_position+1, y_position)
				|| Maze.isHero(x_position, y_position-1)
				|| Maze.isHero(x_position, y_position+1))
			return true;
		return false;
	}
	/**
	 * Responsible for the movement of the actor.
	 */
	public boolean move(int x_offset, int y_offset)
	{
		if(!Maze.isDef()){
			if(getX() + x_offset < 0 || getY() + y_offset < 0 || getX() + x_offset > Maze.size-1 || getY() + y_offset > Maze.size-1)
				return false;}
		else if(Maze.isDef()){
			if(getX() + x_offset < 0 || getY() + y_offset < 0 || getX() + x_offset > 9 || getY() + y_offset > 9)
				return false;}
		if(this.isAsleep())
			return false;
		if(!Maze.isCanMove())
			return false;
		if(Maze.isHero(x_position + x_offset, y_position + y_offset)
		 ||Maze.isWall(x_position + x_offset, y_position + y_offset)
		 ||Maze.isExit(x_position + x_offset, y_position + y_offset)
		 ||Maze.isDragon(x_position + x_offset, y_position + y_offset)
		 ||(Maze.isEagle(x_position + x_offset, y_position + y_offset) && Maze.eagle.getLb() == 'X'))//cannot move
		{
			return false;
		}
		else
		{
			Maze.setSquare(' ', x_position, y_position);
			
			if(this.getX()+x_offset == Maze.getSword().getX() && this.getY()+y_offset == Maze.getSword().getY())
			{
				this.setSymbol('F');
				
			}
			else if(Maze.getSword().getX() == this.getX() && Maze.getSword().getY() == this.getY())
			{
				this.setSymbol('D');
			}
			x_position += x_offset;
			y_position += y_offset;
			return true;
		}
	}
	/**
	 * Makes the dragon fall asleep.
	 */
	public void fallAsleep()
	{
		this.setAsleep(true);
		if(this.getX() == Maze.getSword().getX() && this.getY() == Maze.getSword().getY())
			this.setSymbol('f');
		else 
			this.setSymbol('d');
		Maze.setSquare(this.getSymbol(), this.getX(), this.getY());
	}
	/**
	 * Wakes the dragon up.
	 */
	public void wakeUp()
	{
		this.setAsleep(false);
		if(this.getX() == Maze.getSword().getX() && this.getY() == Maze.getSword().getY())
			this.setSymbol('F');
		else 
			this.setSymbol('D');
		Maze.setSquare(this.getSymbol(), this.getX(), this.getY());
	}

	@Override
	public boolean equals(Object d)
	{
		if(d instanceof Dragon)
			return this.getX() == ((Dragon)d).getX() && this.getY() == ((Dragon)d).getY();
		return false;
	}

	public void setX(int x)
	{
		this.x_position = x;
	}
	
	public void setY(int y)
	{
		this.y_position = y;
	}

	public boolean isAsleep() {
		return isAsleep;
	}

	public void setAsleep(boolean isAsleep) {
		this.isAsleep = isAsleep;
	}

	}
