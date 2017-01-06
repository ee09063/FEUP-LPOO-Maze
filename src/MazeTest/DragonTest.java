package MazeTest;

import static org.junit.Assert.*;

import org.junit.Test;

import Maze.Dragon;
import Maze.Maze;
/**
 * 
 * This class tests if the dragon is functioning properly
 *
 */
public class DragonTest
{	
	protected static char[][] maze = {
		{'X','X','X','X','X','X','X','X','X','X'},
		{'X',' ',' ',' ',' ',' ',' ',' ',' ','X'},
		{'X',' ','X','X',' ','X',' ','X',' ','X'},
		{'X',' ','X','X',' ','X',' ','X',' ','X'},
		{'X',' ','X','X',' ','X',' ','X',' ','X'},
		{'X',' ',' ',' ',' ',' ',' ','X',' ','S'},
		{'X',' ','X','X',' ','X',' ','X',' ','X'},
		{'X',' ','X','X',' ','X',' ','X',' ','X'},
		{'X',' ','X','X',' ',' ',' ',' ',' ','X'},
		{'X','X','X','X','X','X','X','X','X','X'}};;
		
		public void checkModel() {
			System.out.println("" + Maze.getHero().getX() + Maze.getHero().getY() + Maze.getSword().getX() + Maze.getSword().getY());
			System.out.println(Maze.getDragons().size());
		}
		/**
		 * A dragon is placed on a dead end and can only move UP
		 */
		@Test
		public void testDragonMoveUp()
		{
			TestMaze.clean();
			Maze.getDragons().add(new Dragon());
			Maze.getDragons().get(0).setX(8);
			Maze.getDragons().get(0).setY(1);
			assertFalse(Maze.getDragons().get(0).move(1,0));
			assertFalse(Maze.getDragons().get(0).move(0,1));
			assertFalse(Maze.getDragons().get(0).move(0,-1));
			assertTrue(Maze.getDragons().get(0).move(-1,0));
		}
		/**
		 * A dragon is placed on a dead end and can only move Down
		 */
		@Test
		public void testDragonMoveDown()
		{
			TestMaze.clean();
			Maze.getDragons().add(new Dragon());
			Maze.getDragons().get(0).setX(1);
			Maze.getDragons().get(0).setY(1);
			
			assertFalse(Maze.getDragons().get(0).move(0,1));
			assertFalse(Maze.getDragons().get(0).move(0,-1));
			assertFalse(Maze.getDragons().get(0).move(-1,0));
			assertTrue(Maze.getDragons().get(0).move(1,0));
		}
		/**
		 * A dragon is placed on a dead end and can only move Left
		 */
		@Test
		public void testDragonMoveLeft()
		{
			TestMaze.clean();
			Maze.getDragons().add(new Dragon());
			Maze.getDragons().get(0).setX(1);
			Maze.getDragons().get(0).setY(5);
			assertFalse(Maze.getDragons().get(0).move(1,0));
			assertFalse(Maze.getDragons().get(0).move(0,1));
			assertFalse(Maze.getDragons().get(0).move(-1,0));
			assertTrue(Maze.getDragons().get(0).move(0,-1));
		}
		/**
		 * A dragon is placed on a dead end and can only move Right
		 */
		@Test
		public void testDragonMoveRight()
		{
			TestMaze.clean();
			Maze.getDragons().add(new Dragon());
			Maze.getDragons().get(0).setX(1);
			Maze.getDragons().get(0).setY(3);
			assertFalse(Maze.getDragons().get(0).move(1,0));
			
			assertFalse(Maze.getDragons().get(0).move(0,-1));
			assertFalse(Maze.getDragons().get(0).move(-1,0));
			assertTrue(Maze.getDragons().get(0).move(0,1));
		}
		/**
		 * Tests if the dragon correctly moves on top of the sword
		 */
		@Test
		public void testDragonTopSword()
		{
			TestMaze.clean();
			Maze.getDragons().add(new Dragon());
			Maze.getDragons().get(0).setX(8);
			Maze.getDragons().get(0).setY(1);
			Maze.getSword().setX(6);
			Maze.getSword().setY(1);
			assertTrue(Maze.getDragons().get(0).move(-1,0));
			assertTrue(Maze.getDragons().get(0).move(-1,0));
			assertEquals('F', Maze.getDragons().get(0).getSymbol());
			assertEquals(Maze.getSword().getX(), Maze.getDragons().get(0).getX());
			assertEquals(Maze.getSword().getY(), Maze.getDragons().get(0).getY());
		}
		/**
		 * Tests several Dragons
		 */
		//@Ignore
		@Test
		public void testMoreDragons()
		{
			TestMaze.clean();
			Maze.getDragons().add(new Dragon());
			Maze.getDragons().add(new Dragon());
			assertEquals(2, Maze.getDragons().size());
			Maze.getDragons().get(0).setX(1); Maze.getDragons().get(0).setY(1);
			Maze.getDragons().get(1).setX(2); Maze.getDragons().get(1).setY(1);
			Maze.getSword().setX(3); Maze.getSword().setY(1);
			assertFalse(Maze.getDragons().get(0).move(1,0));
			assertFalse(Maze.getDragons().get(1).move(-1,0));
			assertTrue(Maze.getDragons().get(1).move(1, 0));
			Maze.updateMaze();
			assertEquals('F',Maze.getDragons().get(1).getSymbol());
			assertEquals('F',Maze.getSquare(Maze.getDragons().get(1).getX(), Maze.getDragons().get(1).getY()));
		}
		
		/**
		 * Tests if the Hero gets killed when approaching a dragon while unarmed
		 */
		@Test
		public void testDragonKillsHero()
		{
			TestMaze.clean();
			Maze.getHero().setX(1);//hero starting position
			Maze.getHero().setY(1);
			Maze.getHero().setUnarmed();
			Dragon d = new Dragon();
			Maze.getDragons().add(d);
			assertEquals(1,Maze.getDragons().size());
			Maze.getDragons().get(0).setX(3);
			Maze.getDragons().get(0).setY(1);
			assertTrue(Maze.getDragons().get(0).move(-1,0));
			Maze.checkCombat();
			assertEquals(1,Maze.getDragons().size());
			assertFalse(Maze.gameOn);
		}
		
		//@Ignore
		/**
		 * Tests if a dragon is killed if the hero approaches it while armed
		 */
		@Test
		public void testDragonGetsKilled()
		{
			TestMaze.clean();
			Maze.getHero().setX(1);
			Maze.getHero().setY(1);
			Maze.getSword().setX(2);
			Maze.getSword().setY(1);
			Dragon d = new Dragon();
			Maze.getDragons().add(d);
			assertEquals(1,Maze.getDragons().size());
			Maze.getDragons().get(0).setX(4);
			Maze.getDragons().get(0).setY(1);
			assertTrue(Maze.getHero().move(1,0));
			assertTrue(Maze.getDragons().get(0).move(-1,0));
			Maze.checkCombat();
			assertEquals(0,Maze.getDragons().size());
			assertTrue(Maze.checkEdd());
		}
		/**
		 * Tests if the dragon falls asleep/asleep on top of the sword correctly
		 */
		@Test
		public void testDragonTopSwordSleep()
		{
			TestMaze.clean();
			Maze.getDragons().add(new Dragon());
			Maze.getDragons().get(0).setX(8);
			Maze.getDragons().get(0).setY(1);
			Maze.getSword().setX(6);
			Maze.getSword().setY(1);
			
			Maze.getDragons().get(0).fallAsleep();
			assertEquals('d', Maze.getDragons().get(0).getSymbol());
			assertEquals('d', Maze.getSquare(8, 1));
			Maze.getDragons().get(0).wakeUp();
			assertEquals('D', Maze.getDragons().get(0).getSymbol());
			assertEquals('D', Maze.getSquare(8, 1));
			
			assertTrue(Maze.getDragons().get(0).move(-1,0));
			assertTrue(Maze.getDragons().get(0).move(-1,0));
			assertEquals('F', Maze.getDragons().get(0).getSymbol());
			assertEquals(Maze.getSword().getX(), Maze.getDragons().get(0).getX());
			assertEquals(Maze.getSword().getY(), Maze.getDragons().get(0).getY());
			
			Maze.getDragons().get(0).fallAsleep();
			assertEquals('f', Maze.getDragons().get(0).getSymbol());
			assertEquals('f', Maze.getSquare(6, 1));
			assertFalse(Maze.getDragons().get(0).move(1,0));
			
			Maze.getDragons().get(0).wakeUp();
			assertEquals('F', Maze.getDragons().get(0).getSymbol());
			assertEquals('F', Maze.getSquare(6, 1));
			assertTrue(Maze.getDragons().get(0).move(1,0));
			Maze.updateMaze();
			assertEquals('D', Maze.getDragons().get(0).getSymbol());
			assertEquals('D', Maze.getSquare(7, 1));
			assertEquals('E', Maze.getSquare(6, 1));		
		}	
}
