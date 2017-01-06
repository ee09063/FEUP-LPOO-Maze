package GUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import Maze.Hero;
import Maze.Maze;

/**
 * 
 * Class responsible for the creation of the General Options and 
 * Key Binding dialogs.
 *
 */
public class Options extends JPanel {
	private static final long serialVersionUID = 1L;

	/**
     * 
     */
     private static JPanel create2ColPane(JRadioButton[] defaultMaze,
    		 				   JRadioButton[] dragonMovement,
    		 				   JRadioButton[] dragonSleep,
    		 				   JTextField mazeSize,
    		 				   JTextField nod,
    		 				   JButton showButton){
    	 
        JLabel defMaze = new JLabel("Play with the defaut Maze Layout?");
        JLabel draMove = new JLabel("Can the Dragons Move?");
        JLabel draSleep = new JLabel("Can the Dragons Fall Asleep?");
        JLabel size = new JLabel("Maze Size (def: 11 - Odd number 5-19)");
        JLabel number = new JLabel("Number of Dragons (def: 1)");
        

        JPanel grid1 = new JPanel(new GridLayout(0, 2));
        grid1.add(defaultMaze[0]);
        grid1.add(defaultMaze[1]);
        JPanel grid2 = new JPanel(new GridLayout(0, 2));
        grid2.add(dragonMovement[0]);
        grid2.add(dragonMovement[1]);
        JPanel grid3 = new JPanel(new GridLayout(0, 2));
        grid3.add(dragonSleep[0]);
        grid3.add(dragonSleep[1]);
        JPanel grid4 = new JPanel(new GridLayout());
        grid4.add(mazeSize);
        JPanel grid5 = new JPanel(new GridLayout());
        grid5.add(nod);

        JPanel box = new JPanel();
        box.setLayout(new BoxLayout(box, BoxLayout.PAGE_AXIS));
        
        box.add(defMaze);
        grid1.setAlignmentX(0.0f);
        box.add(grid1);
        
        box.add(draMove);
        grid2.setAlignmentX(0.0f);
        box.add(grid2);
        
        box.add(draSleep);
        grid3.setAlignmentX(0.0f);
        box.add(grid3);
        
        box.add(size);
        grid4.setAlignmentX(0.0f);
        box.add(grid4);
        
        box.add(number);
        grid4.setAlignmentX(0.0f);
        box.add(grid5);
   
        JPanel pane = new JPanel(new BorderLayout());
        pane.add(box, BorderLayout.PAGE_START);
        pane.add(showButton, BorderLayout.PAGE_END);

        return pane;
    }
     /**
      * Creates the General Options dialog, which allows the player to change
      * the game's variables -> Default Maze, Dragon Movement, Sleeping Dragons,
      * Number of Dragons and Maze Size.
      * 
      * The changes take effect only when a new game starts.
      * 
      */

    public static JPanel createOptionTabOne() {
        JButton confirmation = null;
        
        JRadioButton[] defaultMaze = new JRadioButton[2];
        JRadioButton[] dragonMovement = new JRadioButton[2];
        JRadioButton[] dragonSleep = new JRadioButton[2];
        final JTextField mazeSize = new JTextField("11");
        final JTextField nod = new JTextField("1");
        
        final ButtonGroup defMaze = new ButtonGroup();
        final ButtonGroup draMove = new ButtonGroup();
        final ButtonGroup draSleep = new ButtonGroup();

        final String defMazeYes = "defMazeYes";
        final String defMazeNo = "defMazeNo";
        final String draMoveYes = "draMoveYes";
        final String draMoveNo = "draMoveNo";
        final String draSleepYes = "draSleepYes";
        final String draSleepNo = "draSleepNo";

        defaultMaze[0] = new JRadioButton("Yes");
        defaultMaze[0].setActionCommand(defMazeYes);

        defaultMaze[1] = new JRadioButton("No");
        defaultMaze[1].setActionCommand(defMazeNo);

        dragonMovement[0] = new JRadioButton("Yes");
        dragonMovement[0].setActionCommand(draMoveYes);

        dragonMovement[1] = new JRadioButton("No");
        dragonMovement[1].setActionCommand(draMoveNo);

        dragonSleep[0] = new JRadioButton("Yes");
        dragonSleep[0].setActionCommand(draSleepYes);

        dragonSleep[1] = new JRadioButton("No");
        dragonSleep[1].setActionCommand(draSleepNo);
        
        if(Maze.isDefA() == true)
        	defaultMaze[0].setSelected(true);
        else if(Maze.isDefA() == false)
        	defaultMaze[1].setSelected(true);
        if(Maze.isCanMoveA() == true)
        	dragonMovement[0].setSelected(true);
        else if(Maze.isCanMoveA() == false)
        	dragonMovement[1].setSelected(true);
        if(Maze.isSleepPossibleA() == true)
        	dragonSleep[0].setSelected(true);
        else if(Maze.isSleepPossibleA() == false)
        	dragonSleep[1].setSelected(true);

        defMaze.add(defaultMaze[0]); defMaze.add(defaultMaze[1]);
        draMove.add(dragonMovement[0]); draMove.add(dragonMovement[1]);
        draSleep.add(dragonSleep[0]); draSleep.add(dragonSleep[1]);

        confirmation = new JButton("OK");
        confirmation.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String command1 = defMaze.getSelection().getActionCommand();
                String command3 = draSleep.getSelection().getActionCommand();
                String command2 = draMove.getSelection().getActionCommand();

                if (command1 == defMazeYes)
                {
                	//System.out.println("DEFAULT YES");
                	Maze.setDefA(true);
                }
                else if (command1 == defMazeNo)
                {
                	//System.out.println("DEFAULT NO");
                	Maze.setDefA(false);
                }
                if (command2 == draMoveYes)
                {
                   //System.out.println("DRAGON MOVEMENT YES");
                	Maze.setCanMoveA(true);
                }
                else  if (command2 == draMoveNo)
                {
                  // System.out.println("DRAGON MOVEMENT NO");
                	Maze.setCanMoveA(false);
                }
                if (command3 == draSleepYes)
                {
                	//System.out.println("DRAGON SLEEP YES");
                	Maze.setSleepPossibleA(true);
                }
                else if (command3 == draSleepNo)
                {
                	//System.out.println("DRAGON SLEEP NO");
                	Maze.setSleepPossibleA(false);
                }
                int size;
                try
                {
                	size = Integer.parseInt(mazeSize.getText());
                	if(size % 2 == 0 || size < 5 || size > 19)
                		Maze.setSizeA(11);
                	else if(size % 2 != 0)
                		Maze.setSizeA(size);
                	
                }
                catch (NumberFormatException e1) { 
                	//System.out.println("SIZE FIELD EMPTY");
                	Maze.setSizeA(11);
            	 }
                int num;
                try
                {
                	num = Integer.parseInt(nod.getText());
                	Maze.setNodA(num);
                }
                catch(NumberFormatException e1)
                {
                	Maze.setNodA(1);
                }
                MazeGUI.dialog1.setVisible(false);
                MazeGUI.boardGame.setFocusable(true);
                MazeGUI.boardGame.requestFocusInWindow();

            }
        });

        return create2ColPane(defaultMaze, dragonMovement, dragonSleep, mazeSize, nod, confirmation);
    }
    /**
     * 
     * Creates the Key Bindings Dialog, which allows the player to change the keys
     * for movement and releasing the eagle.
     * 
     * The changes take effect after confirming the dialog.
     *
     */
    public static JPanel createOptionTabTwo()
    {
    	final JTextField moveKeyUp = new JTextField("w");
    	final JTextField moveKeyDown = new JTextField("s");
    	final JTextField moveKeyLeft = new JTextField("a");
    	final JTextField moveKeyRight = new JTextField("d");
    	final JTextField moveKeyRelease = new JTextField("r");
    	JButton confirmation = new JButton("OK");
    	confirmation.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
            	String moveUp = moveKeyUp.getText();
            	if(moveUp.length() != 0)
            		Hero.setUpKey(moveUp.substring(0,1));
            	else
            		Hero.setUpKey("w");
            		
            	String moveDown = moveKeyDown.getText();
            	if(moveDown.length() != 0)
            		Hero.setDownKey(moveDown.substring(0,1));
            	else
            		Hero.setDownKey("s");
            	
            	String moveLeft = moveKeyLeft.getText();
            	if(moveLeft.length() != 0)
            		Hero.setLeftKey(moveLeft.substring(0,1));
            	else
            		Hero.setLeftKey("a");
            	
            	String moveRight = moveKeyRight.getText();
            	if(moveRight.length() != 0)
            		Hero.setRightKey(moveRight.substring(0,1));
            	else
            		Hero.setRightKey("d");
            	
            	String moveRelease = moveKeyRelease.getText();
            	if(moveRelease.length() != 0)
            		Hero.setReleaseKey(moveRelease.substring(0,1));
            	else
            		Hero.setReleaseKey("r");
            	
            	MazeGUI.dialog2.setVisible(false);
            	MazeGUI.boardGame.setFocusable(true);
            	MazeGUI.boardGame.requestFocusInWindow();
            }
            });
    	ArrayList<JTextField> movement = new ArrayList<JTextField>();
    	movement.add(moveKeyUp);
    	movement.add(moveKeyDown);
    	movement.add(moveKeyLeft);
    	movement.add(moveKeyRight);
    	movement.add(moveKeyRelease);
    	return createPane(movement, confirmation);
    }

    private static JPanel createPane(ArrayList<JTextField> movement, JButton confirmation)
    {
		JLabel up = new JLabel("Movement Key UP");
		JLabel down = new JLabel("Movement Key DOWN");
		JLabel left = new JLabel("Movement Key LEFT");
		JLabel right = new JLabel("Movement Key RIGHT");
		JLabel release = new JLabel("Movement Key RELEASE");
		
		JPanel grid1 = new JPanel(new GridLayout());
        grid1.add(movement.get(0));
        JPanel grid2 = new JPanel(new GridLayout());
        grid2.add(movement.get(1));
        JPanel grid3 = new JPanel(new GridLayout());
        grid3.add(movement.get(2));
        JPanel grid4 = new JPanel(new GridLayout());
        grid4.add(movement.get(3));
        JPanel grid5 = new JPanel(new GridLayout());
        grid5.add(movement.get(4));
        
        JPanel box = new JPanel();
        box.setLayout(new BoxLayout(box, BoxLayout.PAGE_AXIS));
        
        box.add(up);
        grid1.setAlignmentX(0.0f);
        box.add(grid1);
        
        box.add(down);
        grid2.setAlignmentX(0.0f);
        box.add(grid2);
        
        box.add(left);
        grid3.setAlignmentX(0.0f);
        box.add(grid3);
        
        box.add(right);
        grid4.setAlignmentX(0.0f);
        box.add(grid4);
   
        box.add(release);
        grid4.setAlignmentX(0.0f);
        box.add(grid5);
        
        JPanel pane = new JPanel(new BorderLayout());
        pane.add(box, BorderLayout.PAGE_START);
        pane.add(confirmation, BorderLayout.PAGE_END);

        return pane;
	}
}
