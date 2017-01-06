package UI;
import java.util.Scanner;

import Maze.Maze;
/**
 * 
 * Responsible for asking for user input and printing the maze
 *
 */
public class UI
{
	
	public static Scanner s = new Scanner(System.in);
	/**
	 * When starting a game using a console, asks for player input to initialize the game variables
	 */
	public static void Maze_Creator()
	{
		System.out.println("DEFAULT MAZE? Y/N");
		String d = s.next();
		if(d.equals("Y"))
	    {
	    	Maze.setDefA(true);
	    }
	    else if(d.equals("N")) 
	    {
	    	Maze.setDefA(false);
	    	System.out.println("MAZE CREATOR");
			System.out.println("INPUT  (5 ~ 19): ");
			int size = s.nextInt();
			if(size % 2 == 0 || size < 5 || size > 19)
        		Maze.setSizeA(11);
			else if(size % 2 != 0)
        		Maze.setSizeA(size);
        	
	    }
	    System.out.println("SLEEP POSSIBLE? Y/N");
	    String sleep = s.next();
	    if(sleep.equals("Y"))
	    {
	    	Maze.setSleepPossibleA(true);
	    }
	    else if(sleep.equals("N"))
	    {
	    	Maze.setSleepPossibleA(false);
	    }
	    System.out.println("DRAGONS CAN MOVE? Y/N");
	    String canmove = s.next();
	    if(canmove.equals("Y"))
	    {
	    	Maze.setCanMoveA(true);
	    }
	    else if(canmove.equals("N"))
	    {
	    	Maze.setCanMoveA(false);
	    }
	    System.out.println("NUMBER OF DRAGONS");
	    Maze.setNodA(s.nextInt()); 
	}
	/**
	 * Asks user for hero direction
	 */
	public static String user_hero_input()
	{
		System.out.println("INPUT COMMAND:");
		String move = s.next();
		return move;
	}
	/**
	 * Prints the Maze to the console
	 */
	public static void printMaze()
	{
		 int psize = 0;
		 if(Maze.isDef())
			 psize = 10;
		 else if(!Maze.isDef())
			 psize = Maze.getSize();
	     for(int a = 0; a < psize; a++)
	     {
	         for(int b = 0; b < psize; b++)
	         {
	             System.out.print(Maze.maze[a][b]);
	         }
	         System.out.print("\n");
	     }
	}
}
