package MazeTest;

import Maze.Eagle;
import Maze.Hero;
import Maze.Maze;
import Maze.Sword;
/**
 * 
 * Provides a clean Maze for testing purposes
 *
 */
public class TestMaze {
	private char[][] maze = {
			{'X','X','X','X','X','X','X','X','X','X'},
			{'X',' ','X',' ',' ',' ','X','X',' ','X'},
			{'X',' ','X','X',' ','X',' ','X',' ','X'},
			{'X',' ','X','X',' ','X',' ','X',' ','X'},
			{'X',' ','X','X',' ','X',' ','X',' ','X'},
			{'X',' ',' ',' ',' ',' ',' ','X',' ','S'},
			{'X',' ','X','X',' ','X',' ','X',' ','X'},
			{'X',' ','X','X',' ','X',' ','X',' ','X'},
			{'X',' ','X','X',' ',' ',' ','X',' ','X'},
			{'X','X','X','X','X','X','X','X','X','X'}};
	public char[][] getMaze() {return maze;}
	
	/**
	 * Resets the variables before a test
	 */
	public static void clean()
	{
		Maze.setHero(new Hero());
		Maze.setSword(new Sword());
		Maze.eagle = new Eagle(Maze.getHero());
		Maze.maze = (new TestMaze()).getMaze();
		Maze.getHero().setX(0); Maze.getHero().setY(0);
		Maze.getSword().setX(0); Maze.getSword().setY(0);
		Maze.eagle.setX(0); Maze.eagle.setY(0);
		Maze.getDragons().clear();
	//	Maze.eagle.setSymbol('G');
		Maze.eagle.setHasSword(false);
		Maze.getHero().setUnarmed(); Maze.setEdd(false);	
		Maze.setCanMove(true);
		//checkModel();
	}
}