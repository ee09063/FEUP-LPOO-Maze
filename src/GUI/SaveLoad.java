package GUI;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;

import Maze.Eagle;
import Maze.Hero;
import Maze.Maze;
import Maze.Dragon;
import Maze.Sword;
/**
 * 
 * Responsible for saving and loading the game.
 *
 */
public class SaveLoad{
	static ArrayList<Integer> variables = new ArrayList<Integer>();
	static ArrayList<Integer> positions = new ArrayList<Integer>();
	/**
	 * Function responsible for saving the game
	 * The game is saved on a .txt file.
	 * If the player tries to create a savegame and the name already exists, 
	 * the older save will the overwritten
	 */
	public static void saveGame(File file)
	{
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(file.getName() + ".txt", "UTF-8");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		int psize = 0;
		if(!Maze.isDef()){
			psize = Maze.getSize();
		}
		else if(Maze.isDef()){
			psize = 10;
		}
		for(int i = 0; i < psize; i++)
		{
			for(int k = 0; k < psize; k++)
			{
			writer.print(Maze.maze[i][k]);
				if(k == psize-1)
				{
					writer.println("");
				}
			}
		}
		
		printVars(writer);
		
		writer.close();
		MazeGUI.boardGame.requestFocusInWindow();
	}
	/**
	 * Loads a savegame in the form of a text file - .txt extension
	 */
	public static void loadGame(File file) throws FileNotFoundException
	{
		int mazeSize = mazeSize(file);
		String filename = file.getName();
		Scanner s = null;
		variables.clear();
		positions.clear();
		Maze.setHero(new Hero());
		Maze.eagle = new Eagle(Maze.getHero());
		Maze.setSword(new Sword());
		Maze.maze = new char[mazeSize][mazeSize];
		Maze.setSize(mazeSize);
		//Maze.setSizeA(mazeSize);
		myPanel.side = MazeGUI.boardSize/mazeSize;
		myPanel.loadIcons();
		try
		{
			s = new Scanner(new BufferedReader(new FileReader(filename)));
			String str;
			//READS THE MAZE
			for(int i = 0; i < mazeSize; i++)
			 {
				 str = s.nextLine();
				 char[] myChar = str.toCharArray();
				 for(int k = 0; k < myChar.length; k++)
				 {
					 Maze.setSquare(myChar[k],i,k);
				 }
			 }
			//READS VARIABLES
			str = s.nextLine();
			Scanner scanner = new Scanner(str);
			scanner.useDelimiter(",");
			while(scanner.hasNextInt())
			{
				variables.add(scanner.nextInt());
			}
			scanner.close();
			loadVariables();
			//READS POSITIONS
			str = s.nextLine();
			scanner = new Scanner(str);
			scanner.useDelimiter(",");
			while(scanner.hasNextInt())
			{
				positions.add(scanner.nextInt());
			}
			loadPositions();
			scanner.close();
			//now for the eagle
			str = s.nextLine();
			scanner = new Scanner(str);
			if(str.charAt(0) == 'B')
				Maze.eagle.setLb(' ');
			else if (str.charAt(0)!= 'B')
				Maze.eagle.setLb(str.charAt(0));
			scanner.close();
			s.close();
			//since the eagle can hide a dragons, these load last
			loadDragons(mazeSize);
			MazeGUI.boardGame.repaint();
		}
		finally
		{
			if(s != null)
				s.close();
		}
	}
	/**
	 * Reads the size of a maze from a text file.
	 */
	public static int mazeSize(File file) throws FileNotFoundException
	{
		String filename = file.getName();
		Scanner s = null;
		try
		{
			 s = new Scanner(new BufferedReader(new FileReader(filename)));
			 String str = s.nextLine();
			 return str.length();
		}
		finally
		{
			if(s != null)
				s.close();
		}
	}
	/**
	 * Prepares the Dragons, loaded from a savegame.
	 */
	private static void loadDragons(int mazeSize) 
	{
		Maze.getDragons().clear();
		Dragon d = null;
		if(Maze.eagle.getLb() == 'D' || Maze.eagle.getLb() == 'd')
		{
			d = new Dragon();
			Maze.getDragons().add(d);
			if(Maze.eagle.getLb() == 'd')
				Maze.getDragons().get(Maze.getDragons().size()-1).setSymbol('d');
			Maze.getDragons().get(Maze.getDragons().size()-1).setX(Maze.eagle.getX());
			Maze.getDragons().get(Maze.getDragons().size()-1).setY(Maze.eagle.getY());
		}
		
		for(int x = 0; x < mazeSize; x++)
			for(int y = 0; y < mazeSize; y++)
			{
				if(Maze.getSquare(x, y) == 'D' || Maze.getSquare(x, y) == 'd' || Maze.getSquare(x, y) == 'F' || Maze.getSquare(x, y) == 'f')
				{
					d = new Dragon();
					Maze.getDragons().add(d);
					if(Maze.getSquare(x, y) == 'D')
					{
						Maze.getDragons().get(Maze.getDragons().size()-1).setAsleep(false);
					}
					else if(Maze.getSquare(x, y) == 'd')
					{
						Maze.getDragons().get(Maze.getDragons().size()-1).setSymbol('d');
						Maze.getDragons().get(Maze.getDragons().size()-1).setAsleep(true);
					}
					else if(Maze.getSquare(x, y) == 'F')
					{
						Maze.getDragons().get(Maze.getDragons().size()-1).setSymbol('F');
						Maze.getDragons().get(Maze.getDragons().size()-1).setAsleep(false);
					}
					else if(Maze.getSquare(x, y) == 'f')
					{
						Maze.getDragons().get(Maze.getDragons().size()-1).setSymbol('f');
						Maze.getDragons().get(Maze.getDragons().size()-1).setAsleep(true);
					}
					Maze.getDragons().get(Maze.getDragons().size()-1).setX(x);
					Maze.getDragons().get(Maze.getDragons().size()-1).setY(y);
				}
			}
		Maze.setNod(Maze.getDragons().size());
	}
	/**
	 * Prepares the positions for the actors, loaded from a savegame.
	 */
	private static void loadPositions()
	{
		Maze.getHero().setX(positions.get(0));
		Maze.getHero().setY(positions.get(1));
		
		Maze.eagle.setX(positions.get(2));
		Maze.eagle.setY(positions.get(3));
		Maze.eagle.setXReturn(positions.get(4));
		Maze.eagle.setYReturn(positions.get(5));
		
		Maze.getSword().setX(positions.get(6));
		Maze.getSword().setY(positions.get(7));
	}
	/**
	 * Prepares the game variables, loaded from a savegame .txt file.
	 */
	private static void loadVariables()
	{
		if(variables.get(0) == 1){Maze.setCanMove(true); }
		else if(variables.get(0) == 0){Maze.setCanMove(false); }
		
		if(variables.get(1) == 1){Maze.setSleepPossible(true); }
		else if(variables.get(1) == 0){Maze.setSleepPossible(false);}
		
		if(variables.get(2) == 1){Maze.setDef(true);}
		else if(variables.get(2) == 0){Maze.setDef(false);}
		
		if(variables.get(3) == 1){Maze.gameOn = true;}
		else if(variables.get(3) == 0){Maze.gameOn = false;}
		
		if(variables.get(4) == 1){Maze.getHero().setHasEagle(true);}
		else if(variables.get(4) == 0){Maze.getHero().setHasEagle(false);}
		
		if(variables.get(5) == 1){Maze.eagle.setHasSword(true);}
		else if(variables.get(5) == 0){Maze.eagle.setHasSword(false);}
		
		if(variables.get(6) == 1){Maze.eagle.setAlive();}
		else if(variables.get(6) == 0){Maze.eagle.setDead();}
		
		if(variables.get(7) == 1){Maze.eagle.setLoose();}
		else if(variables.get(7) == 0){Maze.eagle.setLoose(false);}
		
		if(variables.get(8) == 1){Maze.setEdd(true);}
		else if(variables.get(8) == 0){Maze.setEdd(false);}
		
		if(variables.get(9) == 1){Maze.getHero().setArmed(true); Maze.getHero().setSymbol('A');}
		else if(variables.get(9) == 0){Maze.getHero().setArmed(false); Maze.getHero().setSymbol('H');}
	}
	/**
	 * Prints the variables to a text file.
	 */
	public static void printVars(PrintWriter writer)
	{
		if(Maze.isCanMove() == true){ writer.print("" + 1);}
		else if(Maze.isCanMove() == false){writer.print("" + 0);}
		writer.print(",");
		if(Maze.isSleepPossible() == true){ writer.print("" + 1);}
		else if(Maze.isSleepPossible() == false){writer.print("" + 0);}
		writer.print(",");
		if(Maze.isDef() == true){ writer.print("" + 1);}
		else if(Maze.isDef() == false){writer.print("" + 0);}
		writer.print(",");
		if(Maze.gameOn == true){ writer.print("" + 1);}
		else if(Maze.gameOn == false){writer.print("" + 0);}
		writer.print(",");
		if(Maze.getHero().isHasEagle() == true){ writer.print("" + 1);}
		else if(Maze.getHero().isHasEagle() == false){writer.print("" + 0);}
		writer.print(",");
		if(Maze.eagle.isHasSword() == true){ writer.print("" + 1);}
		else if(Maze.eagle.isHasSword() == false){writer.print("" + 0);}
		writer.print(",");
		if(Maze.eagle.isAlive() == true){ writer.print("" + 1);}
		else if(Maze.eagle.isAlive() == false){writer.print("" + 0);}
		writer.print(",");
		if(Maze.eagle.isLoose() == true){ writer.print("" + 1);}
		else if(Maze.eagle.isLoose() == false){writer.print("" + 0);}
		writer.print(",");
		if(Maze.isEdd() == true){ writer.print("" + 1);}
		else if(Maze.isEdd() == false){writer.print("" + 0);}
		writer.print(",");
		if(Maze.getHero().isArmed() == true){ writer.print("" + 1);}
		else if(Maze.getHero().isArmed() == false){writer.print("" + 0);}
		writer.print('\n');
	
		writer.print("" + Maze.getHero().getX() + "," + Maze.getHero().getY() + "," +
				     Maze.eagle.getX() + "," + Maze.eagle.getY() + "," + Maze.eagle.getXReturn() + "," + Maze.eagle.getYReturn() + "," +
					 Maze.getSword().getX() + "," + Maze.getSword().getY() + '\n');
		
		if(Maze.getADragon(Maze.eagle.getX(), Maze.eagle.getY()) != null)
		{
			writer.print("" + Maze.getADragon(Maze.eagle.getX(), Maze.eagle.getY()).getSymbol() + '\n');
		}
		else if(Maze.isHero(Maze.eagle.getX(), Maze.eagle.getY()))
		{
			writer.print("" + Maze.getHero().getSymbol() + '\n');
		}
		else if(Maze.eagle.getLb() == ' ')
			writer.print("" + 'B' + '\n');
		else if(Maze.eagle.getLb() != ' ')
			writer.print("" + Maze.eagle.getLb() + '\n');
		
	}
	
}