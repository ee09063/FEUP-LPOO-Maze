package MazeTest;

import static org.junit.Assert.*;

import org.junit.Test;

import Maze.Dragon;
import Maze.Maze;

/**
 * 
 * Tests if the Eagle is working correctly
 *
 */
public class TestEagle {
	
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
		 * Tests if the eagle picks up the sword
		 */
		@Test
		public void eagleToSword()
		{
			TestMaze.clean();
			Maze.eagle.setX(8); Maze.eagle.setY(1);
			Maze.getSword().setX(8); Maze.getSword().setY(4);
			for(int i = 0; i < 3; i++)
			{
				assertTrue(Maze.eagle.move(0,1));	
			}
			assertEquals(0, Maze.getSword().getX());
			assertEquals(0, Maze.getSword().getY());
			assertTrue(Maze.eagle.isHasSword());
		}
		/**
		 * Tests if the eagle drops the sword
		 */
		@Test
		public void eagleFromSword()
		{
			TestMaze.clean();
			Maze.eagle.setX(8); Maze.eagle.setY(1);
			Maze.eagle.setXReturn(8); Maze.eagle.setYReturn(1);
			Maze.getSword().setX(8); Maze.getSword().setY(4);
			for(int i = 0; i < 3; i++)
			{
				assertTrue(Maze.eagle.move(0, 1));	
			}
			assertEquals(0, Maze.getSword().getX());
			assertEquals(0, Maze.getSword().getY());
			assertTrue(Maze.eagle.isHasSword());
			for(int i = 0; i < 3; i++)
			{
				assertTrue(Maze.eagle.move(0, -1));
			}
			assertEquals(8, Maze.getSword().getX());
			assertEquals(1, Maze.getSword().getY());
			Maze.updateMaze();
			assertEquals('E', Maze.getSquare(8, 1));
		}
		/**
		 * Tests if the eagle gets killed by a dragon on top of the sword
		 */
		@Test
		public void eagleGetsKilled()
		{
			TestMaze.clean();
			Maze.eagle.setX(8); Maze.eagle.setY(1);
			Maze.eagle.setXReturn(8); Maze.eagle.setYReturn(1);
			Maze.getDragons().add(new Dragon());
			Maze.getDragons().get(0).setX(7); Maze.getDragons().get(0).setY(4);
			Maze.getSword().setX(8); Maze.getSword().setY(4);
			assertTrue(Maze.getDragons().get(0).move(1,0));
			for(int i = 0; i < 3; i++)
			{
				assertTrue(Maze.eagle.move(0, 1));	
			}
			assertFalse(Maze.eagle.isAlive());
		}
		/**
		 * Tests if the eagle gets killed by a dragon adjacent to the sword
		 */
		@Test
		public void eagleGetsKilledTwo()
		{
			TestMaze.clean();
			Maze.eagle.setX(8); Maze.eagle.setY(1);
			Maze.eagle.setXReturn(8); Maze.eagle.setYReturn(1);
			Maze.getDragons().add(new Dragon());
			Maze.getDragons().get(0).setX(7); Maze.getDragons().get(0).setY(4);
			Maze.getSword().setX(8); Maze.getSword().setY(4);
	
			for(int i = 0; i < 3; i++)
			{
				assertTrue(Maze.eagle.move(0, 1));	
			}
			assertFalse(Maze.eagle.isAlive());
			
		}
		/**
		 * Tests if the eagle leaves behind the correct character
		 */
		@Test
		public void eagleCrossesHero()
		{
			TestMaze.clean();
			Maze.eagle.setX(5); Maze.eagle.setY(3);
			Maze.eagle.setXReturn(5); Maze.eagle.setYReturn(3);
			Maze.getHero().setX(4); Maze.getHero().setY(4);
			assertTrue(Maze.eagle.move(0, 1));
			assertTrue(Maze.getHero().move(1,0));
			assertEquals(Maze.getHero().getX(), Maze.eagle.getX());
			assertEquals(Maze.getHero().getY(), Maze.eagle.getY());
			
			assertTrue(Maze.eagle.move(0, 1));
			assertTrue(Maze.getHero().move(1,0));
			assertNotEquals(Maze.getHero().getX(), Maze.eagle.getX());
			assertNotEquals(Maze.getHero().getY(), Maze.eagle.getY());
			assertEquals(' ', Maze.getSquare(5, 4));
			Maze.updateMaze();
			assertEquals('H', Maze.getSquare(6, 4));
			assertEquals('G', Maze.getSquare(5, 5));
		}
		/**
		 * Tests if the eagle leaves behind the correct character
		 */
		@Test
		public void eagleCrossesDragon()
		{
			TestMaze.clean();
			Maze.eagle.setX(5); Maze.eagle.setY(3);
			Maze.eagle.setXReturn(5); Maze.eagle.setYReturn(3);
			Maze.getDragons().add(new Dragon());
			Maze.getDragons().get(0).setX(4); Maze.getDragons().get(0).setY(4);
			
			assertTrue(Maze.eagle.move(0, 1));
			assertTrue(Maze.getDragons().get(0).move(1,0));
			assertEquals(Maze.getDragons().get(0).getX(), Maze.eagle.getX());
			assertEquals(Maze.getDragons().get(0).getY(), Maze.eagle.getY());
			
			assertTrue(Maze.eagle.move(0, 1));
			assertTrue(Maze.getDragons().get(0).move(1,0));
			assertNotEquals(Maze.getDragons().get(0).getX(), Maze.eagle.getX());
			assertNotEquals(Maze.getDragons().get(0).getY(), Maze.eagle.getY());
			
			assertEquals(' ', Maze.getSquare(5, 4));
			Maze.updateMaze();
			assertEquals('D', Maze.getSquare(6, 4));
			assertEquals('G', Maze.getSquare(5, 5));
		}
		/**
		 * Tests if the eagle leaves behind the correct character
		 */
		@Test
		public void eagleCrossesSleepingDragon()
		{
			TestMaze.clean();
			Maze.eagle.setX(5); Maze.eagle.setY(3);
			Maze.eagle.setXReturn(5); Maze.eagle.setYReturn(3);
			Maze.getDragons().add(new Dragon());
			Maze.getDragons().get(0).setX(4); Maze.getDragons().get(0).setY(4);
			
			assertTrue(Maze.getDragons().get(0).move(1,0));
			Maze.getDragons().get(0).fallAsleep();
			
			assertTrue(Maze.eagle.move(0, 1));
			
			assertEquals(Maze.getDragons().get(0).getX(), Maze.eagle.getX());
			assertEquals(Maze.getDragons().get(0).getY(), Maze.eagle.getY());
			
			assertTrue(Maze.eagle.move(0, 1));
			
			assertNotEquals(Maze.getDragons().get(0).getY(), Maze.eagle.getY());
			Maze.updateMaze();
			assertEquals('d', Maze.getSquare(5, 4));
			assertEquals('G', Maze.getSquare(5, 5));
		}

		@Test
		public void eagleDropsSwordOnDragon()
		{
			TestMaze.clean();
			Maze.eagle.setX(8); Maze.eagle.setY(1);
			Maze.eagle.setXReturn(8); Maze.eagle.setYReturn(1);
			Maze.getSword().setX(8); Maze.getSword().setY(4);
			Maze.getDragons().add(new Dragon());
			Maze.getDragons().get(0).setX(7); Maze.getDragons().get(0).setY(1);
			for(int i = 0; i < 3; i++)
			{
				assertTrue(Maze.eagle.move(0, 1));	
			}
			assertEquals(0, Maze.getSword().getX());
			assertEquals(0, Maze.getSword().getY());
			
			assertTrue(Maze.getDragons().get(0).move(1,0));
			
			for(int i = 0; i < 3; i++)
			{
				assertTrue(Maze.eagle.move(0, -1));
			}
			Maze.updateMaze();
			assertEquals(8, Maze.getSword().getX());
			assertEquals(1, Maze.getSword().getY());
			assertEquals('F', Maze.getSquare(8, 1));
			
			assertTrue(Maze.getDragons().get(0).move(-1,0));
			Maze.updateMaze();
			assertEquals('E', Maze.getSquare(8, 1));
			assertEquals('D', Maze.getSquare(7, 1));
		}
}
