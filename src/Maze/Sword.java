package Maze;

import java.util.Random;
/**
 * 
 * Slices bread like a charm
 *
 */
public class Sword extends Actor
{
	/**
	 * Class constructor
	 */
	public Sword()
	{
		super('E');
	}
	/**
	 * Randomly places the sword in the maze
	 */
	protected void random_placement()
	{
		int Size = 0;
		if(Maze.isDef())
			Size = 10;
		else if(!Maze.isDef())
			Size = Maze.getSize();
		
		Random random = new Random();
		int sword_x_random = random.nextInt(Size);
		int sword_y_random = random.nextInt(Size);
	    if(Maze.maze[sword_x_random][sword_y_random] == ' ')
	    {
	    	x_position = sword_x_random;
		    y_position = sword_y_random;
		    Maze.maze[sword_x_random][sword_y_random] = symbol;
	    }
	    else random_placement();
	}

	@Override
	public int getX() {
		return x_position;
	}

	@Override
	public int getY() {
		return y_position;
	}

	public void setX(int x) {
		// TODO Auto-generated method stub
		this.x_position = x;
	}

	public void setY(int y) {
		// TODO Auto-generated method stub
		this.y_position = y;
	}

}
