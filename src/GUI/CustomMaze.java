package GUI;

import javax.swing.JOptionPane;

import Maze.Dragon;
import Maze.Hero;
import Maze.Maze;
import Maze.Maze_Build;
import Maze.Sword;

/**
 *
 * Class responsible for the creation of a custom Maze.
 * 
 */
	public class CustomMaze
	{
		protected static boolean addPath;
		protected static boolean addWall;
		protected static boolean addHero;
		protected static boolean addSword;
		protected static boolean addDragon;
		protected static boolean addExit;
		protected static boolean alreadyAHero;
		protected static boolean alreadyASword;
		protected static boolean alreadyAnExit;
		protected static boolean visited[][];
		protected static int mazeSize;
		protected static boolean editingMode;
	
	/**
	 * Creates a maze and fills it with walls.
	 */
	public static void customMazeSize()//before customizing a maze, the player needs to choose the size
	{
		String s = (String)JOptionPane.showInputDialog(MazeGUI.frame,"Size of Maze (Odd Number 5-19):\n", "MAZE SIZE", JOptionPane.PLAIN_MESSAGE, null, null, "11");
	
		if ((s != null) && (s.length() > 0))
		{
			mazeSize = Integer.parseInt(s);
			if(mazeSize % 2 == 0 || mazeSize < 5 || mazeSize > 19)
				mazeSize = 11;	   
			customGameVariables();
		}
		MazeGUI.boardGame.requestFocusInWindow();
		MazeGUI.boardGame.repaint();
	}
	/**
	 * Prepares the variables for a custom maze.
	 */
	private static void customGameVariables()
	{
		 editingMode = true;//if editing mode is true, the key listener has no effect
		 Maze.getHero().setX(-1); Maze.getHero().setY(-1);
		 Maze.getSword().setX(-1); Maze.getSword().setY(-1);
		 Maze.getDragons().clear();
		 MazeGUI.customOptions.setEnabled(true);
		 
		 alreadyAHero = false;
		 alreadyASword = false;
		 alreadyAnExit = false;
		 Maze.setDef(false);
		 Maze.setDefA(false);
		 Maze.setSizeA(mazeSize);
		 Maze.setSize(Maze.getSize());
		 Maze.maze = new char[mazeSize][mazeSize];
		 Maze.setSize(mazeSize);
		 myPanel.side = MazeGUI.boardSize/(mazeSize);
		 myPanel.loadIcons();
		 for(int x = 0; x < mazeSize; x++)
		  for(int y = 0; y < mazeSize; y++)
		   Maze.maze[x][y] = 'X';
		
	}
	/**
	 * Starts a custom game, first checking if the maze is viable.
	 * If not presents a dialog.
	 */
	public static void startCustomGame()
	{
		if(checkViable())
		{
			MazeGUI.customOptions.setEnabled(false);
			MazeGUI.boardGame.requestFocusInWindow();
			editingMode = false;
			return;
		}
		JOptionPane.showMessageDialog(MazeGUI.frame, "The Maze you created is not viable");
	}
	/**
	 * Checks if all the actors exist in the maze and if the maze is viable.
	 */
	private static boolean checkViable()
	{
		initializeVisited();//for the backtracking
		if(checkForAllActors()){
			if(checkMaze(Maze.getHero().getX(),Maze.getHero().getY())){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Initializes a matrix used for the backtracking algorithm.
	 */
	public static void initializeVisited()
	{
		visited = new boolean[mazeSize][mazeSize];
		for(int i = 0; i < mazeSize; i++)
			for(int j = 0; j < mazeSize; j++)
				visited[i][j] = false;
	}
	/**
	 * Checks if the hero can reach the exit, not counting with dragons
	 * using a backtracking algorithm. 
	 */
	private static boolean checkMaze(int x, int y)
	{
		if(Maze.getSquare(x, y) == 'S')
			return true;
		if(Maze.getSquare(x, y) == 'X' || visited[x][y])
			return false;
		visited[x][y] = true;
		return checkMaze(x+1,y) || checkMaze(x-1,y) || checkMaze(x,y+1) || checkMaze(x,y-1);
	}
	/**
	 * Checks if all the actors are placed in the maze.
	 * 
	 */
	private static boolean checkForAllActors() {
		if(heroExists() && dragonsExist() && swordExists() && exitExists()){
			return true;
		}
		return false;
	}
	/**
	 * Responsible for adding an actor to the Maze.
	 * The player first selects which actor to add and clicks on a square.
	 */
	public static void addActor(int xMaze, int yMaze)
	{
		if(editingMode)
		{
			if(addPath)//adding a path or a wall over a dragon allows the player to delete that dragon
			{
				if(Maze.isDragon(xMaze, yMaze))
				{
					Maze.setSquare(' ', xMaze, yMaze);
					for(int i = 0; i < Maze.dragons.size(); i++)
					{
						if(Maze.dragons.get(i) == Maze.getADragon(xMaze, yMaze))
							Maze.dragons.remove(i);	
					}
				}
				else if(!checkCollision(xMaze,yMaze))
					Maze.setSquare(' ', xMaze, yMaze);
			}
			else if(addWall)
			{
				if(Maze.isDragon(xMaze, yMaze))
				{
					Maze.setSquare('X', xMaze, yMaze);
					for(int i = 0; i < Maze.dragons.size(); i++)
					{
						if(Maze.dragons.get(i) == Maze.getADragon(xMaze, yMaze))
							Maze.dragons.remove(i);	
					}
				}
				else if(!checkCollision(xMaze,yMaze))
				Maze.setSquare('X', xMaze, yMaze);
			}
			else if(addHero && !checkCollision(xMaze,yMaze))
			{
				if(!alreadyAHero)
				{
					Maze.setHero(new Hero());
					Maze.getHero().setX(xMaze);
					Maze.getHero().setY(yMaze);
					Maze.setSquare('H', xMaze, yMaze);
					alreadyAHero = true;
				}
				else if(alreadyAHero)//in case there's already a hero, the old one needs to be replaced
				{
					Maze.setSquare(' ', Maze.getHero().getX(), Maze.getHero().getY());
					Maze.setHero(new Hero());
					Maze.getHero().setX(xMaze);
					Maze.getHero().setY(yMaze);
					Maze.setSquare('H', xMaze, yMaze);
				}
			}
			else if(addDragon && !checkCollision(xMaze,yMaze)) //dragons cannot be added over other actors
			{
				Dragon d = null;
				if(Maze.getADragon(xMaze, yMaze) == null)
				{
					d = new Dragon();
					d.setX(xMaze); d.setY(yMaze);
					Maze.setSquare('D', xMaze, yMaze);
					Maze.getDragons().add(d);
				}
			}
			else if(addSword && !checkCollision(xMaze,yMaze))//the sword cannot be added over other actors
			{
				if(!alreadyASword)
				{
					Maze.setSword(new Sword());
					Maze.getSword().setX(xMaze); Maze.getSword().setY(yMaze);
					Maze.setSquare('E', xMaze, yMaze);
					alreadyASword = true;
				}
				if(alreadyASword)///in case there's already a sword, the old one needs to be replaced
				{
					Maze.setSquare(' ', Maze.getSword().getX(), Maze.getSword().getY());
					Maze.setSword(new Sword());
					Maze.getSword().setX(xMaze); Maze.getSword().setY(yMaze);
					Maze.setSquare('E', xMaze, yMaze);	
				}
			}
			else if(addExit && !checkCollision(xMaze,yMaze))
			{
				if(!alreadyAnExit)
				{
					Maze.setSquare('S', xMaze, yMaze);
					Maze_Build.setxExit(xMaze);
					Maze_Build.setyExit(yMaze);
					alreadyAnExit = true;
				}
				else if(alreadyAnExit)
				{
					Maze.setSquare('X', Maze_Build.getxExit(), Maze_Build.getyExit());
					Maze.setSquare('S', xMaze, yMaze);
					Maze_Build.setxExit(xMaze);
					Maze_Build.setyExit(yMaze);
					alreadyAnExit = true;
				}
			}
		}
		MazeGUI.boardGame.repaint();
	}
	/**
	 * Checks if there is already an hero, dragon or sword to prevent collision
	 * when adding a new actor.
	 * 
	 */
	public static boolean checkCollision(int x, int y)
	{
		if(Maze.isHero(x, y) || Maze.isSword(x, y) || Maze.isDragon(x, y))
			return true;
		return false;
	}
	/**
	 * Checks if there's already an exit in the maze.
	 * 
	 */
	private static boolean exitExists()
	{
		for(int i = 0; i < Maze.getSize(); i++)
			for(int j = 0; j < Maze.getSize(); j++)
				if(Maze.isExit(i,j))
					return true;
		return false;
	}
	/**
	 * Checks if there's already a sword in the maze.
	 * 
	 */
	private static boolean swordExists()
	{
		for(int i = 0; i < Maze.getSize(); i++)
			for(int j = 0; j < Maze.getSize(); j++)
				if(Maze.isSword(i,j))
					return true;
		return false;
	}
	/**
	 * Checks if there is at least a dragon in the maze.
	 */
	private static boolean dragonsExist() {
		if(Maze.getDragons().size() == 0)
			return false;
		return true;
	}
	/**
	 * Checks if there is already a hero in the maze.	
	 */
	private static boolean heroExists()
	{
		for(int i = 0; i < Maze.getSize(); i++)
			for(int j = 0; j < Maze.getSize(); j++)
				if(Maze.isHero(i,j))
					return true;
		return false;
	}
	/**
	 * With the addDragon variable is true, when the player clicks on the Maze,
	 * a dragon will be added.
	 */
	public static void addDragonMode()
	{
		addDragon = true;
		addPath = addWall = addHero = addExit = addSword = false;
	}
	/**
	 * With the addPath variable is true, when the player clicks on the Maze,
	 * an empty space will be added.
	 */
	public static void addPathMode()
	{
		addPath = true;
		addDragon = addWall = addHero = addExit = addSword = false;
	}
	/**
	 * With the addWall variable is true, when the player clicks on the Maze,
	 * a wall will be added.
	 */
	public static void addWallMode()
	{
		addWall = true;
		addPath = addDragon = addHero = addExit  = addSword = false;
	}
	/**
	 * With the addHero variable is true, when the player clicks on the Maze,
	 * the hero will be added.
	 */
	public static void addHeroMode()
	{
		addHero = true;
		addPath = addWall = addDragon = addExit = addSword = false;
	}
	/**
	 * With the addSword variable is true, when the player clicks on the Maze,
	 * the sword will be added.
	 */
	public static void addSwordMode()
	{
		addSword = true;
		addPath = addWall = addHero = addExit = addDragon = false;
	}
	/**
	 * With the addExit variable is true, when the player clicks on the Maze,
	 * the Exit will be added.
	 */
	public static void addExitMode()
	{
		addExit = true;
		addPath = addWall = addHero = addDragon = addSword = false;
	}
}