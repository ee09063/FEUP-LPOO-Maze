package GUI;

import java.awt.EventQueue;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileNotFoundException;

import Maze.Maze;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;
/**
 *
 * Class responsible for the creation of the Graphic Player Interface.
 * Running this class will allow the player to play using the GUI.
 */
public class MazeGUI implements KeyListener, MouseListener{

	protected static final String IOUtils = null;
	protected static JFrame frame;
	protected static myPanel boardGame;
	protected static int testSize = 11;
	protected static int boardSize = 600;
	protected static int squareSide;
	protected static MazeGUI window;
	protected static JMenuBar menubar;
	protected static JMenu fileBar; 
	protected static JMenu optionsBar;
	protected static JMenu customOptions;
	protected static JDialog dialog1;
	protected static JDialog dialog2;
	
	private static WindowListener closeWindow = new WindowAdapter() {
        public void windowClosing(WindowEvent e) {
        	boardGame.setFocusable(true);
        	boardGame.requestFocusInWindow();
            e.getWindow().dispose();
        }
    };
    
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
			    	defaultSettings();
					window = new MazeGUI();
					MazeGUI.frame.pack();
					MazeGUI.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Sets the default settings for the game.
	 */
	public static void defaultSettings()
	{
    	Maze.setDefA(true);
    	Maze.setSizeA(10);
    	Maze.setNodA(1);
    	Maze.setCanMoveA(true);
    	Maze.setSleepPossibleA(false);
    	Maze.newGame();
	}
	
	public MazeGUI() {
		initialize();
	}
	/**
	 * Initialization function
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(0, 0, boardSize, boardSize);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		if(Maze.isDef())
			squareSide = boardSize / 10;
		else if(!Maze.isDef())
			squareSide = boardSize / Maze.getSizeA();
		
		prepareMenuBar();
		
		boardGame = new myPanel(squareSide);
		boardGame.setFocusable(true);
		boardGame.addKeyListener(this);
		boardGame.addMouseListener(this);
		boardGame.requestFocusInWindow();
		frame.getContentPane().add(boardGame, BorderLayout.CENTER);
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		String move = "" + e.getKeyChar();
		if(Maze.gameOn && !CustomMaze.editingMode)
		{
	  		if(Maze.getHero().decision(move))//if the hero doesn't move, no one else does
	  		{
	  			Maze.checkCombat();
	  			Maze.eagleTurn();
		   		Maze.dragonTurn();
		    	Maze.checkCombat();
		   		Maze.setEdd(Maze.checkEdd());
		   		if(!Maze.gameOn)
		   		{
		   			Maze.updateMaze();
		   			boardGame.repaint();
		   			gameOverScreen();
		   			Maze.newGame();
		   			boardGame.requestFocusInWindow();		
		   		}
		   		Maze.updateMaze();
				boardGame.repaint();
	  		}
		}
	}
	/**
	 * Game Over Screen, shows up when the player dies or leaves the Maze.
	 */
	public void gameOverScreen()
	{
		int n = JOptionPane.showConfirmDialog(frame, "GAME OVER\n" + "RESTART?", "RESTART?", JOptionPane.YES_NO_OPTION);
		if(n == JOptionPane.YES_OPTION){
			if(Maze.isDefA())
				myPanel.side = boardSize / 10;
			else if(!Maze.isDefA())
				myPanel.side = boardSize / (Maze.getSizeA());
			myPanel.loadIcons();}
		else if(n == JOptionPane.NO_OPTION)
		{
			System.exit(0);
		}
	
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX(), y = e.getY();
		int yMaze = x/myPanel.side, xMaze = y/myPanel.side;
		CustomMaze.addActor(xMaze, yMaze);
	}
	/**
	 * Prepares the various dropdown Menus -> File, Options and Custom Maze Options
	 */
	public void prepareMenuBar()
	{
		menubar = new JMenuBar();
		fileBar = new JMenu("File");
		optionsBar = new JMenu("Options");
		customOptions = new JMenu("Custom Maze Options");
		
		JMenuItem newGame = new JMenuItem("New Game");
		newGame.setToolTipText("Start a new game");
		newGame.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				int n = JOptionPane.showConfirmDialog(frame, "START NEW GAME?", "NEW GAME", JOptionPane.YES_NO_OPTION);
				if(n == JOptionPane.YES_OPTION)
				{
					customOptions.setEnabled(false);
					CustomMaze.editingMode = false;
					customOptions.setEnabled(false);
					Maze.newGame();
					if(Maze.isDef())
						myPanel.side = boardSize / 10;
					else if(!Maze.isDef())
						myPanel.side = boardSize / (Maze.getSizeA());
					myPanel.loadIcons();
					boardGame.requestFocusInWindow();
					boardGame.repaint();
				}
			}
		});
		
		JMenuItem customMaze = new JMenuItem("Custom Maze");
		customMaze.setToolTipText("Create a Maze");
		customMaze.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				customOptions.setEnabled(true);
				CustomMaze.customMazeSize();
			}
		});
		
		JMenuItem exit = new JMenuItem("Exit");
		exit.setToolTipText("Exit application");
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				int n = JOptionPane.showConfirmDialog(frame, "LEAVE GAME?", "EXIT?", JOptionPane.YES_NO_OPTION);
				if(n == JOptionPane.YES_OPTION)
				{
					System.exit(0);
				}
			}
		});
		
		JMenuItem save = new JMenuItem("Save");
		save.setToolTipText("Save game");
		save.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				JFileChooser f = new JFileChooser();
				f.setCurrentDirectory(new File(System.getProperty("user.dir")));
				int returnVal = f.showOpenDialog(frame);
				if(returnVal == JFileChooser.APPROVE_OPTION)
				{
					File file = f.getSelectedFile();
					SaveLoad.saveGame(file);
				}
			}
		});
		
		JMenuItem load = new JMenuItem("Load");
		load.setToolTipText("Load game");
		load.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				JFileChooser f = new JFileChooser();
				f.setCurrentDirectory(new File(System.getProperty("user.dir")));
				FileNameExtensionFilter filter = new FileNameExtensionFilter("TXT", "txt");
				f.setFileFilter(filter);
				int returnVal = f.showOpenDialog(frame);
				if(returnVal == JFileChooser.APPROVE_OPTION)
				{
					File file = f.getSelectedFile();
					try {
						SaveLoad.loadGame(file);
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					}
					
				}
			}
		});
		
		JMenuItem generalOptions = new JMenuItem("General Options");
		generalOptions.setToolTipText("Game Options");
		generalOptions.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				boardGame.setFocusable(false);
				JPanel tabOne = Options.createOptionTabOne();
		        Border padding = BorderFactory.createEmptyBorder(20,20,5,20);
		        tabOne.setBorder(padding);
		       
		        dialog1= new JDialog(frame);
		        dialog1.addWindowListener(closeWindow);
		        dialog1.setBounds(200, 200, 300, 300);
		        dialog1.getContentPane().setLayout(new BorderLayout());
		        dialog1.getContentPane().add(tabOne, BorderLayout.CENTER);
		        
		        dialog1.setVisible(true);
			}
		});
		
		JMenuItem keyBindings = new JMenuItem("Key Bindings");
		keyBindings.setToolTipText("Key Bindings");
		keyBindings.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				boardGame.setFocusable(false);
				JPanel tabTwo = Options.createOptionTabTwo();
		        Border padding = BorderFactory.createEmptyBorder(20,20,5,20);
		        tabTwo.setBorder(padding);
		       
		        dialog2= new JDialog(frame);
		        dialog2.addWindowListener(closeWindow);
		        dialog2.setBounds(200, 200, 300, 300);
		        dialog2.getContentPane().setLayout(new BorderLayout());
		        dialog2.getContentPane().add(tabTwo, BorderLayout.CENTER);
		        
		        dialog2.setVisible(true);
			}
		});
		
		JMenuItem addPath = new JMenuItem("Add Path");
		addPath.setToolTipText("Click a square to add an Empty Space");
		addPath.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				CustomMaze.addPathMode();
			}
		});
		JMenuItem addWall = new JMenuItem("Add Wall");
		addWall.setToolTipText("Click a square to add a Wall");
		addWall.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				CustomMaze.addWallMode();
			}
		});
		JMenuItem addHero = new JMenuItem("Add Hero");
		addHero.setToolTipText("Click a square to add the Hero");
		addHero.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				CustomMaze.addHeroMode();
			}
		});
		JMenuItem addDragon = new JMenuItem("Add Dragon");
		addDragon.setToolTipText("Click a square to add a Dragon");
		addDragon.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				CustomMaze.addDragonMode();
			}
		});
		JMenuItem addSword = new JMenuItem("Add Sword");
		addSword.setToolTipText("Click a square to add the Sword");
		addSword.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				CustomMaze.addSwordMode();
			}
		});
		JMenuItem addExit = new JMenuItem("Add Exit");
		addPath.setToolTipText("Click a square to add the Exit");
		addExit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				CustomMaze.addExitMode();
			}
		});
		JMenuItem startCustomGame = new JMenuItem("Start Custom Game");
		startCustomGame.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				CustomMaze.startCustomGame();
			}
		});
		
		fileBar.add(newGame);
		fileBar.add(customMaze);
		fileBar.add(save);
		fileBar.add(load);
		fileBar.add(exit);
		
		optionsBar.add(generalOptions);
		optionsBar.add(keyBindings);
		
		customOptions.add(addPath);
		customOptions.add(addWall);
		customOptions.add(addHero);
		customOptions.add(addDragon);
		customOptions.add(addSword);
		customOptions.add(addExit);
		customOptions.add(startCustomGame);
		customOptions.setEnabled(false);
		
		menubar.add(fileBar);
		menubar.add(optionsBar);
		menubar.add(customOptions);
		frame.setJMenuBar(menubar);
		
	}
	
	@Override
	public void keyReleased(KeyEvent arg0) {
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent arg0) {}

	@Override
	public void mouseReleased(MouseEvent arg0) {}
}