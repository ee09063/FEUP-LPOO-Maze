package GUI;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JPanel;

import Maze.Maze;
/**
 *
 * Class responsible for drawing the maze in the GUI.
 */
public class myPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	protected static int side;
	protected static Image heroIcon;
	protected static Image heroArmedIcon;
	protected static Image dragonIcon;
	protected static Image dragonSleepingIcon;
	protected static Image dragonTopSwordIcon;
	protected static Image dragonTopSwordSleepingIcon;
	protected static Image swordIcon;
	protected static Image eagleIcon;
	protected static Image whiteWall;
	protected static Image blackWall;
	protected static Image blackWallC;
	protected static Image blackWallI;
	protected static Image blackWallO;
	protected static Image tombStone;
	protected static Image victoryIcon;
	protected static Image exitIcon;
	protected static String path = System.getProperty("user.dir");//"C:/Users/Aleister/workspace/MazeGame_v6/Icons/";
	protected static String theme = "Zelda";
	/**
	 * Class constructor
	 */
	public myPanel(int side)
	{
		myPanel.side = side;
		loadIcons();
	}
	/**
	 * Loads the images for the icons and resizes them
	 */
	public static void loadIcons()
	{
		String fullPath = path + "/Icons/" + theme ;
		Image img = Toolkit.getDefaultToolkit().getImage(fullPath + "/heroIcon.jpg");
		heroIcon = img.getScaledInstance(side, side, Image.SCALE_DEFAULT);
		
		img = Toolkit.getDefaultToolkit().getImage(fullPath+"/dragonIcon2.jpg");
		dragonIcon = img.getScaledInstance(side, side, Image.SCALE_DEFAULT);
		
		img = Toolkit.getDefaultToolkit().getImage(fullPath+"/dragonIconSleeping.jpg");
		dragonSleepingIcon = img.getScaledInstance(side, side, Image.SCALE_DEFAULT);
		
		img = Toolkit.getDefaultToolkit().getImage(fullPath+"/dragonTopSword.png");
		dragonTopSwordIcon = img.getScaledInstance(side, side, Image.SCALE_DEFAULT);
		
		img = Toolkit.getDefaultToolkit().getImage(fullPath+"/dragonTopSwordSleeping.png");
		dragonTopSwordSleepingIcon = img.getScaledInstance(side, side, Image.SCALE_DEFAULT);
		
		img = Toolkit.getDefaultToolkit().getImage(fullPath+"/armedHeroIcon.jpg");
		heroArmedIcon = img.getScaledInstance(side, side, Image.SCALE_DEFAULT);
		
		img = Toolkit.getDefaultToolkit().getImage(fullPath+"/swordIcon.jpg");
		swordIcon = img.getScaledInstance(side, side, Image.SCALE_DEFAULT);
		
		img = Toolkit.getDefaultToolkit().getImage(fullPath+"/floorBlack3.png");
		blackWall = img.getScaledInstance(side, side, Image.SCALE_DEFAULT);
		
		img = Toolkit.getDefaultToolkit().getImage(fullPath+"/floorCWall.png");
		blackWallC = img.getScaledInstance(side, side, Image.SCALE_DEFAULT);
		
		img = Toolkit.getDefaultToolkit().getImage(fullPath+"/floorIWall.png");
		blackWallI = img.getScaledInstance(side, side, Image.SCALE_DEFAULT);
		
		img = Toolkit.getDefaultToolkit().getImage(fullPath+"/floorOWall.png");
		blackWallO = img.getScaledInstance(side, side, Image.SCALE_DEFAULT);
		
		img = Toolkit.getDefaultToolkit().getImage(fullPath+"/floorWhite2.png");
		whiteWall = img.getScaledInstance(side, side, Image.SCALE_DEFAULT);
		
		img = Toolkit.getDefaultToolkit().getImage(fullPath+"/eagleIcon2.jpg");
		eagleIcon = img.getScaledInstance(side, side, Image.SCALE_DEFAULT);
		
		img = Toolkit.getDefaultToolkit().getImage(fullPath+"/tombstone.png");
		tombStone = img.getScaledInstance(side, side, Image.SCALE_DEFAULT);
		
		img = Toolkit.getDefaultToolkit().getImage(fullPath+"/Tri3.gif");
		victoryIcon = img.getScaledInstance(side, side, Image.SCALE_DEFAULT);
		
		img = Toolkit.getDefaultToolkit().getImage(fullPath+"/exit.jpg");
		exitIcon = img.getScaledInstance(side, side, Image.SCALE_DEFAULT);
	}
	
	public Dimension getPreferredSize()
	{
		return new Dimension(MazeGUI.boardSize,MazeGUI.boardSize);
	}
	/**
	 * Function responsible for drawing the Maze
	 */
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		int Size = 0;
		
		if(Maze.isDef())
		{
			Size = 10;
		}
		else if(!Maze.isDef())
		{
			Size = Maze.getSize();
		}
			
		for(int y = 0; y < Size; y++)
		{
			for(int x = 0; x < Size ; x++)
			{
				if(Maze.getSquare(x,y) == 'X')
				{
					if(x<Size-1){
					if((!Maze.isWall(x+1, y) && !Maze.isEagle(x+1,y)) || (Maze.isEagle(x+1, y) && Maze.eagle.getLb() != 'X'))
						g.drawImage(blackWallC, y*side, x*side, this);
					else 
						g.drawImage(blackWallI, y*side, x*side, this);
					}
					else g.drawImage(blackWallO, y*side, x*side, this);
				}
				if(Maze.getSquare(x, y) == ' ')
				{
					g.drawImage(whiteWall, y*side, x*side, this);
				}
				if(Maze.getSquare(x, y) == 'H')
				{
					g.drawImage(heroIcon, y*side, x*side, this);
				}
				if(Maze.getSquare(x, y) == 'A')
				{
					g.drawImage(heroArmedIcon, y*side, x*side, this);
				}
				if(Maze.getSquare(x, y) == 'D')
				{
					g.drawImage(dragonIcon, y*side, x*side, this);
				}
				if(Maze.getSquare(x, y) == 'E')
				{
					g.drawImage(swordIcon, y*side, x*side, this);
				}
				if(Maze.getSquare(x, y) == 'G')
				{
					g.drawImage(eagleIcon, y*side, x*side, this);
				}
				if(Maze.getSquare(x, y) == 'F')
				{
					g.drawImage(dragonTopSwordIcon, y*side, x*side, this);
				}
				if(Maze.getSquare(x, y) == 'T')
				{
					g.drawImage(tombStone, y*side, x*side, this);
				}
				if(Maze.getSquare(x, y) == 'V')
				{
					g.drawImage(victoryIcon, y*side, x*side, this);
				}
				if(Maze.getSquare(x, y) == 'd')
				{
					g.drawImage(dragonSleepingIcon, y*side, x*side, this);
				}
				if(Maze.getSquare(x, y) == 'f')
				{
					g.drawImage(dragonTopSwordSleepingIcon, y*side, x*side, this);
				}
				if(Maze.getSquare(x, y) == 'S' )
				{
					g.drawImage(exitIcon, y*side, x*side, this);
				}
			}
		}
	}

	public String getPath()
	{
		return path;
	}
	
	public void setPath(String s)
	{
		path = s;
	}
	public String getTheme()
	{
		return theme;
	}
	
	public void setTheme(String s)
	{
		theme = s;
	}
}
