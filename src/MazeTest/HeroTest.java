package MazeTest;

import static org.junit.Assert.*;

import org.junit.Test;

import Maze.Dragon;
import Maze.Hero;
import Maze.Maze_Build;
import Maze.Maze;

/**
 * 
 * Tests if the Hero is working correctly
 *
 */
public class HeroTest
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
		
		//@Ignore
		/**
		 * The Hero is placed on a dead end and can only move UP
		 */
		@Test
		public void testHeroOneMoveUp()
		{
			TestMaze.clean();
			Hero hero = new Hero();
			hero.setX(8);//hero starting position
			hero.setY(1);
			Maze_Build.setDef(true);
			
			assertFalse(hero.move(1,0));
			assertEquals(8, hero.getX());
			assertEquals(1, hero.getY());
			
			assertFalse(hero.move(0,1));
			assertEquals(8, hero.getX());
			assertEquals(1, hero.getY());
			
			assertFalse(hero.move(0,-1));
			assertEquals(8, hero.getX());
			assertEquals(1, hero.getY());
			
			assertTrue(hero.move(-1,0));
			assertEquals(7, hero.getX());
			assertEquals(1, hero.getY());		
		}
		
		/**
		 * The Hero is placed on a dead end and can only move DOWN
		 */
		@Test
		public void testHeroOneMoveDown()
		{
			TestMaze.clean();
			Hero hero = new Hero();
			hero.setX(1);//hero starting position
			hero.setY(1);
			Maze_Build.setDef(true);
			
			assertFalse(hero.move(0,1));
			assertEquals(1, hero.getX());
			assertEquals(1, hero.getY());
			
			assertFalse(hero.move(0,-1));
			assertEquals(1, hero.getX());
			assertEquals(1, hero.getY());
			
			assertFalse(hero.move(-1,0));
			assertEquals(1, hero.getX());
			assertEquals(1, hero.getY());	
			
			assertTrue(hero.move(1,0));
			assertEquals(2, hero.getX());
			assertEquals(1, hero.getY());
		}
		
		/**
		 * The Hero is placed on a dead end and can only move LEFT
		 */
		@Test
		public void testHeroOneMoveLeft()
		{
			TestMaze.clean();
			Hero hero = new Hero();
			hero.setX(1);//hero starting position
			hero.setY(5);
			Maze_Build.setDef(true);
			
			assertFalse(hero.move(0,1));
			assertEquals(1, hero.getX());
			assertEquals(5, hero.getY());
			
			assertFalse(hero.move(-1,0));
			assertEquals(1, hero.getX());
			assertEquals(5, hero.getY());	
			
			assertFalse(hero.move(1,0));
			assertEquals(1, hero.getX());
			assertEquals(5, hero.getY());
			
			assertTrue(hero.move(0,-1));
			assertEquals(1, hero.getX());
			assertEquals(4, hero.getY());
		}
		
		/**
		 * The Hero is placed on a dead end and can only move RIGHT
		 */
		@Test
		public void testHeroOneMoveRight()
		{
			TestMaze.clean();
			Hero hero = new Hero();
			hero.setX(1);//hero starting position
			hero.setY(3);
			Maze_Build.setDef(true);
			
			assertFalse(hero.move(-1,0));
			assertEquals(1, hero.getX());
			assertEquals(3, hero.getY());	
			
			assertFalse(hero.move(1,0));
			assertEquals(1, hero.getX());
			assertEquals(3, hero.getY());
			
			assertFalse(hero.move(0,-1));
			assertEquals(1, hero.getX());
			assertEquals(3, hero.getY());
			
			assertTrue(hero.move(0,1));
			assertEquals(1, hero.getX());
			assertEquals(4, hero.getY());
		}
		
		
		//@Ignore
		/**
		 * Tests if the Hero picks up the Sword
		 */
		@Test
		public void testHeroGetSword()
		{
			TestMaze.clean();
			Maze.getHero().setX(8);//hero starting position
			Maze.getHero().setY(1);
			Maze.getSword().setX(7);
			Maze.getSword().setY(1);
			
			assertTrue(Maze.getHero().move(-1,0));
			assertEquals(Maze.getSword().getX(), 0); //sword gets thrown off the maze
			assertEquals(Maze.getSword().getY(), 0);
			assertEquals('A', Maze.getHero().getSymbol());
			assertTrue(Maze.getHero().isArmed());
		}
		
		//@Ignore
		/**
		 * Tests if the Hero gets killed when approaching a dragon while unarmed
		 */
		@Test
		public void testHeroGetsKilled()
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
			assertTrue(Maze.getHero().move(1,0));
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
			Maze.getDragons().get(0).setX(4);
			Maze.getDragons().get(0).setY(1);
			assertTrue(Maze.getHero().move(1,0));
			assertTrue(Maze.getHero().move(1,0));
			Maze.checkCombat();
			assertEquals(0,Maze.getDragons().size());
			assertTrue(Maze.checkEdd());
		}
		
		/**
		 * Tests if the Hero gets killed when approaching a dragon while unarmed
		 */
		@Test
		public void testHeroAndSleepingDragon()
		{
			TestMaze.clean();
			Maze.getHero().setX(1);//hero starting position
			Maze.getHero().setY(1);
			Maze.getHero().setUnarmed();
			Dragon d = new Dragon();
			Maze.getDragons().add(d);
			Maze.getDragons().get(0).setX(3);
			Maze.getDragons().get(0).setY(1);
			Maze.getDragons().get(0).fallAsleep();
			assertEquals(Maze.getDragons().get(0).getSymbol(), 'd');
			assertTrue(Maze.getHero().move(1,0));
			Maze.checkCombat();
			assertEquals(1,Maze.getDragons().size());
			assertTrue(Maze.gameOn);
		}
		
		//@Ignore
		/**
		 * The Hero cannot move towards the exit if there are still dragons alive
		 */
		@Test
		public void exitNoEdd()
		{
			TestMaze.clean();
			Maze.getHero().setX(5);
			Maze.getHero().setY(8);
			Maze.setEdd(false);
			Maze.getHero().setUnarmed();
			assertFalse(Maze.getHero().move(0,1));
		}
		
		//@Ignore
		/**
		 * Conditions for victory are:
		 * Hero picks up Sword
		 * Hero kills all dragons
		 * Hero reaches exit
		 */
		@Test
		public void Victory()
		{
			TestMaze.clean();
			Maze.getHero().setX(8); Maze.getHero().setY(8);
			Maze.getSword().setX(7); Maze.getSword().setY(8);
			Maze.getDragons().add(new Dragon());
			Maze.getDragons().get(0).setX(5); Maze.getDragons().get(0).setY(8);
			assertTrue(Maze.getHero().move(-1, 0));
			assertTrue(Maze.getHero().isArmed());
			assertTrue(Maze.getHero().move(-1, 0));
			Maze.checkCombat();
			assertEquals(0,Maze.getDragons().size());
			assertTrue(Maze.checkEdd());
			Maze.setEdd(Maze.checkEdd());
			assertTrue(Maze.getHero().move(-1, 0));
			assertTrue(Maze.getHero().move(0,1));
			assertFalse(Maze.gameOn);
		}

}
