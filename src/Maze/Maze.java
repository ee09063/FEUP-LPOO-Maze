package Maze;

import java.util.ArrayList;
import java.util.Random;

import UI.UI;
/**
 * 
 * Responsible for game and maze management.
 * Running this will allow console input.
 *
 */
public class Maze extends Maze_Build
{
    public static boolean gameOn = true;
   
    protected static Hero hero;
	public static ArrayList<Dragon> dragons = new ArrayList<Dragon>();
	public static Sword sword;
	public static Eagle eagle;
	protected static boolean edd;
	
	protected static boolean canMoveA;
	protected static boolean sleepPossibleA;
	protected static int nodA;
	
	private static boolean canMove;
	private static boolean sleepPossible;
	private static int nod;
	/**
	 * 
	 * Provides a clean default maze
	 *
	 */
	public static class MazeBuilder {
		private char[][] maze = {
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
		public char[][] getMaze() {return maze;}
	}
	
	public static void main(String[] args)///////////////////////////////////////////
	{
		UI.Maze_Creator();
		newGame();
		UI.printMaze();
		fullTurn();
	}///////////////////////////////////////////////////////////////////////////////////
	/**
	 * One full game turn
	 * The hero moves first, followed by the eagle and then the dragons
	 */
	public static void fullTurn()
	{
		while(gameOn)
		{	
			if(getHero().decision(UI.user_hero_input()))
			{
				checkCombat();
				eagleTurn();
				dragonTurn();
				checkCombat();
				setEdd(checkEdd());		
			}
			updateMaze();	
	    	UI.printMaze();
		}
	}
	
	/**
	 * Updates the position of the symbols in the maze.
	 * This way the actors only need to leave behind them an empty space (or wall, in the eagle's case)
	 * when moving and don't need to be worry about leaving another symbol behind if they have
	 * crossed with another actor.
	 */
	public static void updateMaze()
	{
		Maze.setSquare(getHero().getSymbol(), getHero().getX(), getHero().getY());
		
		if(Maze.getSword().getX() != 0 && Maze.getSword().getY() != 0)
			Maze.setSquare(getSword().getSymbol(), getSword().getX(), getSword().getY());
		
		for(int i = 0; i < getDragons().size(); i++)
		{
			Maze.setSquare(getDragons().get(i).getSymbol(), getDragons().get(i).getX(), getDragons().get(i).getY());
		}
		
		if(!eagle.isAlive() || (eagle.getX() != 0 && eagle.getY() != 0))
			Maze.setSquare(eagle.getSymbol(), eagle.getX(), eagle.getY());
	}
	/**
	 * Determines if hero becomes lunch or lives to fight another day
	 */
	public static void heroVsDragon(Dragon dragon)
	{
		if(getHero().isArmed())
		{
			Maze.setSquare(' ', dragon.getX(), dragon.getY());
			for(int i = 0; i < getDragons().size(); i++)
			{
				if(getDragons().get(i).equals(dragon))
				{
					getDragons().remove(i);
				}
			}
		}
		else if(!getHero().isArmed())
		{
			if(dragon.isAsleep()) return;
			//Maze.setSquare('T', hero.getX(), hero.getY());
			getHero().setSymbol('T');
			Maze.setSquare(dragon.getSymbol(), dragon.getX(), dragon.getY());
			gameOn = false;
			
		}
	}
	/**
	 * Determines how the dragons behave in their turn if they
	 * can fall asleep.
	 */
	public static void dragonCycleSleep()
	{
		Random random = new Random();
		for(int i = 0; i < getDragons().size(); i++)
		{
			int dragonSleep = random.nextInt(3);
			if(dragonSleep == 0 && !dragons.get(i).isAsleep())
			{
				getDragons().get(i).fallAsleep();
			}
			else if(dragonSleep == 0 && dragons.get(i).isAsleep())
			{
				getDragons().get(i).wakeUp();
			}
			getDragons().get(i).decision();
		}	
	}
	/**
	 * Determines how the dragons behave in their turn if they
	 * can't fall asleep.
	 */
	public static void dragonCycle()
	{
		for(int i = 0; i < getDragons().size(); i++)
		{
				getDragons().get(i).decision();
		}	
	}
	/**
	 * Initializes the necessary variables for a new game.
	 */
	public static void newGameVars()
	{
		size = getSizeA();
		setNod(getNodA());
		setSleepPossible(isSleepPossibleA());
		setCanMove(isCanMoveA());
		def = isDefA();
	
		if(!Maze.def)
		{
			maze = new char[Maze.getSize()][Maze.getSize()];
		}
		else if(Maze.def)
		{
			maze = new char[10][10];
			Maze.maze = (new MazeBuilder()).getMaze();
		}
		
		setHero(new Hero());
		
		maze[0][0] = 'X';
		
		gameOn = true;
		eagle = new Eagle(getHero());
		setSword(new Sword());
		getDragons().clear();
	}
	/**
	 * Responsible for the creation of a new game.
	 */
	public static void newGame()
	{
		newGameVars();
		
		visited_nodes = 1;
		backtrack_x[last_good_cell] = 1;		
		backtrack_y[last_good_cell] = 1;
		
		if(!Maze.isDef())
		{
			init_maze();
			maze_generator(1,1);
		}
		
		for(int i = 0; i < getNod(); i++)
		{
			Dragon d = new Dragon();
			getDragons().add(d);
			getDragons().get(i).random_placement();
		}
		
		getHero().random_placement();
		getSword().random_placement();
		
		if(!Maze.isDef())
			exit_random_placement();
	}
	/**
	 * Decides if combat is necessary or not
	 */
	public static void checkCombat()
	{
		for(int i = 0; i < getDragons().size(); i++)
		{
			if(getDragons().get(i).lookForHero())
			{
				heroVsDragon(getDragons().get(i));
			}
		}
	}
	/**
	 * Determines eagle behaviour in its turn
	 */
	public static void eagleTurn()
	{
		if(eagle.isLoose() && !eagle.isHasSword() && eagle.isAlive())
		{
			eagle.decision(getSword().getX(), getSword().getY());
		}
		else if(eagle.isLoose() && eagle.isHasSword() && eagle.isAlive())
		{
			eagle.decision(eagle.getXReturn(), eagle.getYReturn());
		}
	}
	
	public static void dragonTurn()
	{
			if(isSleepPossible())
			{
				dragonCycleSleep();
			}
			else if(!isSleepPossible())
			{
				dragonCycle();
			}
	}
	/**
	 * 
	 * Checks if all the dragons have been vanquished
	 * "Everybody's dead Dave" or "Every Dragon Dead"
	 * 
	 */
	public static boolean checkEdd()
	{
		if(getDragons().size() == 0)
			return true;
		return false;
	}
	
	public static boolean isWall(int x, int y)
	{
		if(maze[x][y] == 'X')
			return true;
		return false;
	}
	
	public static boolean isExit(int x, int y)
	{
		if(maze[x][y] == 'S')
			return true;
		return false;
	}
	
	public static boolean isSword(int x, int y) {
		if(x == getSword().getX() && y == getSword().getY())
			return true;
		return false;
	}
	
	public static void setSquare(char c, int x, int y)
	{
		maze[x][y] = c;
	}
	
	public static char getSquare(int x, int y)
	{
		return maze[x][y];
	}
	
	public static boolean isEmpty(int x, int y)
	{
		if(maze[x][y] == ' ')
			return true;
		return false;
	}
	public static boolean isHero(int x, int y)
	{
		if(x == getHero().getX() && y == getHero().getY())
			return true;
		return false;
	}
	public static boolean isEagle(int x, int y) {
		if(x == eagle.getX() && y == eagle.getY())
			return true;
		return false;
	}
	public static boolean isDragon(int x, int y)
	{
		for(int i = 0; i < getDragons().size(); i++)
			if(x == getDragons().get(i).getX() && y == getDragons().get(i).getY())
			return true;
		return false;
	}
	
	public static Dragon getADragon(int x, int y)
	{
		for(int i = 0; i < getDragons().size(); i++)
			if(getDragons().get(i).getX() == x && getDragons().get(i).getY() == y)
				return getDragons().get(i);
		return null;
	}
	
	public static void setDef(boolean d){
		def = d;
	}
	
	public static void setSize(int nextInt){
		size = nextInt;
	}
	
	public static int getSize() {
		return size;
	}
	
	public static Hero getHero() {
		return hero;
	}
	public static void setHero(Hero hero) {
		Maze.hero = hero;
	}
	public static Sword getSword() {
		return sword;
	}
	public static void setSword(Sword sword) {
		Maze.sword = sword;
	}
	public static ArrayList<Dragon> getDragons() {
		return dragons;
	}
	public static void setDragons(ArrayList<Dragon> dragons) {
		Maze.dragons = dragons;
	}
	public static int getNodA() {
		return nodA;
	}
	public static void setNodA(int nodA) {
		Maze.nodA = nodA;
	}
	public static boolean isSleepPossibleA() {
		return sleepPossibleA;
	}
	public static void setSleepPossibleA(boolean sleepPossibleA) {
		Maze.sleepPossibleA = sleepPossibleA;
	}
	public static boolean isEdd() {
		return edd;
	}
	public static void setEdd(boolean edd) {
		Maze.edd = edd;
	}
	public static boolean isCanMoveA() {
		return canMoveA;
	}
	public static void setCanMoveA(boolean canMoveA) {
		Maze.canMoveA = canMoveA;
	}
	public static boolean isCanMove() {
		return canMove;
	}
	public static void setCanMove(boolean canMove) {
		Maze.canMove = canMove;
	}
	public static boolean isSleepPossible() {
		return sleepPossible;
	}
	public static void setSleepPossible(boolean sleepPossible) {
		Maze.sleepPossible = sleepPossible;
	}
	public static int getNod() {
		return nod;
	}
	public static void setNod(int nod) {
		Maze.nod = nod;
	}
	
	

}//end class maze
