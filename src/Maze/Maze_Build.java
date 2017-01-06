package Maze;

import java.util.Random;
/**
 * 
 * Responsible for the creation of a random Maze.
 * The maze is square and of odd size.
 */
public class Maze_Build
{
	protected static int MAX = 61;
	protected static int CELL = 900;
	protected static char WALL = 'X';
	protected static char PATH = ' ';
	protected static int[] backtrack_x = new int[CELL];
	protected static int[] backtrack_y = new int[CELL];
	protected static int visited_nodes = 1;
	protected static int last_good_cell;
	protected static boolean lets_dig = false;
	
	protected static int sizeA;
	protected static boolean defA;
	
	protected static int size;
	protected static boolean def = true;
	
	protected static int xExit = 5;
	protected static int yExit = 9;
	
	public static char[][] mazeA = {
		{'X','X','X','X','X','X','X','X','X','X'},
		{'X',' ',' ',' ',' ',' ',' ',' ',' ','X'},
		{'X',' ','X','X',' ','X',' ','X',' ','X'},
		{'X',' ','X','X',' ','X',' ','X',' ','X'},
		{'X',' ','X','X',' ','X',' ','X',' ','X'},
		{'X',' ',' ',' ',' ',' ',' ','X',' ','S'},
		{'X',' ','X','X',' ','X',' ','X',' ','X'},
		{'X',' ','X','X',' ','X',' ','X',' ','X'},
		{'X',' ','X','X',' ',' ',' ',' ',' ','X'},
		{'X','X','X','X','X','X','X','X','X','X'}};
		
		public static char[][] maze = {};	
	/**
	 * Randomly generates the maze
	 */
	public static void maze_generator(int x_cur, int y_cur)
	{
		int sizeM = (getSize()-1)/2;
		if(visited_nodes < sizeM*sizeM)
		{
			int valid_neighbours = -1;
			int[] neighbour_x = new int[4]; //four possible neighbours
			int[] neighbour_y = new int[4];
			int[] step = new int[4]; //four possible directions
		
			int x_next = 0;
			int y_next = 0;
		
			if(x_cur - 2 > 0 && is_closed(x_cur - 2, y_cur))  // up
	        {
	            valid_neighbours++;
	            neighbour_x[valid_neighbours]=x_cur - 2;;
	            neighbour_y[valid_neighbours]=y_cur;
	            step[valid_neighbours]=1;
	        }
			if(x_cur + 2 < sizeM * 2 + 1 && is_closed(x_cur + 2, y_cur))  // down
	        {
	        	valid_neighbours++;
	            neighbour_x[valid_neighbours]=x_cur+2;;
	            neighbour_y[valid_neighbours]=y_cur;
	            step[valid_neighbours]=4;
	        }
			if(y_cur - 2 > 0 && is_closed(x_cur, y_cur - 2))  // left
	        {
				valid_neighbours++;
	            neighbour_x[valid_neighbours]=x_cur;;
	            neighbour_y[valid_neighbours]=y_cur-2;
	            step[valid_neighbours]=2;
	        }
	 
	        if(y_cur + 2 <sizeM * 2 + 1 && is_closed(x_cur, y_cur + 2))  // right
	        {
	        	valid_neighbours++;
	            neighbour_x[valid_neighbours]=x_cur;
	            neighbour_y[valid_neighbours]=y_cur+2;
	            step[valid_neighbours]=3;
	 
	        }
	        if(valid_neighbours == -1) //there are no viable neighbours
	        {
	            // backtrack
	            x_next = backtrack_x[last_good_cell];
	            y_next = backtrack_y[last_good_cell];
	            last_good_cell--;
	        }
	        if(valid_neighbours!=-1)
	        {
	        	Random random = new Random();
	        	int ex_dir = random.nextInt(valid_neighbours+1); //excavation direction
	            x_next = neighbour_x[ex_dir];
	            y_next = neighbour_y[ex_dir];
	            last_good_cell++;
	            backtrack_x[last_good_cell] = x_next;
	            backtrack_y[last_good_cell] = y_next;
	 
	            int rstep = step[ex_dir];
	 
	            if(rstep == 1)
	                maze[x_next+1][y_next] = PATH;
	            else if(rstep == 2)
	                maze[x_next][y_next + 1] = PATH;
	            else if(rstep == 3)
	                maze[x_next][y_next - 1] = PATH;
	            else if(rstep == 4)
	                maze[x_next - 1][y_next] = PATH;
	            visited_nodes++;
	        }
	        maze_generator(x_next, y_next);
		}
	}
	/**
	 * Checks if a cell has been visited or not.
	 */
	protected static boolean is_closed(int x, int y)
	{
	     if(maze[x - 1][y]  == WALL && maze[x][y - 1] == WALL && maze[x][y + 1] == WALL && maze[x + 1][y] == WALL)
	        return true;
	     return false;
	}
	/**
	 * Initializes the maze.
	 */
	public static void init_maze()
	{	
	     for(int a = 0; a < getSizeA(); a++)
	     {
	         for(int b = 0; b < getSizeA(); b++)
	         {
	             if(a % 2 == 0 || b % 2 == 0)
	            	 maze[a][b] = WALL;
	             else
	            	 maze[a][b] = PATH;
	         }
	     }
	}
	/**
	 * Places the exit on a random outside wall.
	 */
	protected static void exit_random_placement()
	{
		if(Maze.isDef())
			Maze.setSize(4); 
		
		Random random = new Random();
		int exit = random.nextInt(getSize());
		int wall = random.nextInt(4);
		if(wall == 0)//up
		{
			setxExit(0);
			setyExit(exit);
			if(maze[1][exit] != 'X')
				maze[0][exit] = 'S';
			else exit_random_placement();
		}
		if(wall == 1)//right
		{
			setyExit(0);
			setxExit(exit);
			if(maze[exit][getSize()-1] != 'X')
				maze[exit][getSize()-1] = 'S';
			else exit_random_placement();
		}
		if(wall == 2)//down
		{
			if(isDef()) setxExit(9);
			else if(!isDef()) setxExit(Maze.getSize()-1);
			setyExit(exit);
			if(maze[getSize()-1][exit] != 'X')
				maze[getSize()-1][exit] = 'S';
			else exit_random_placement();
		}
		if(wall == 3)//left
		{
			if(isDef()) setyExit(9);
			else if(!isDef()) setyExit(Maze.getSize()-1);
			setxExit(exit);
			if(maze[exit][1] != 'X')
				maze[exit][0] = 'S';
			else exit_random_placement();
		}
	}

	public static int getSize() {
		return size;
	}

	public static void setSize(int size) {
		Maze_Build.size = size;
	}
	public static boolean isDef() {
		return def;
	}
	public static void setDef(boolean def) {
		Maze_Build.def = def;
	}
	public static boolean isDefA() {
		return defA;
	}
	public static void setDefA(boolean defA) {
		Maze_Build.defA = defA;
	}
	public static int getSizeA() {
		return sizeA;
	}
	public static void setSizeA(int sizeA) {
		Maze_Build.sizeA = sizeA;
	}
	public static int getxExit() {
		return xExit;
	}
	public static void setxExit(int xExit) {
		Maze_Build.xExit = xExit;
	}
	public static int getyExit() {
		return yExit;
	}
	public static void setyExit(int yExit) {
		Maze_Build.yExit = yExit;
	}
}
