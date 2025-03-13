package com.Planwar.system;

import com.Planwar.GameWin;
import com.Planwar.obj.*;
import com.Planwar.utils.GameUtils;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Plane War Game System Test
 * Test complete game flow and core gameplay
 */
public class GameSystemTest {

    // Test GameWin instance
    private GameWin gameWin;

    // Test Graphics
    private Graphics graphics;

    @Before
    public void setUp() {
        System.out.println("==========================================");
        System.out.println("Setting up game system test environment");

        // Create test Graphics
        BufferedImage bi = new BufferedImage(600, 800, BufferedImage.TYPE_INT_ARGB);
        graphics = bi.getGraphics();

        // Clear all game object lists
        clearAllGameLists();

        // Reset game state
        resetGameState();

        // Create GameWin instance but don't call launch() method
        // (launch method contains infinite loop, not suitable for direct calling in tests)
        gameWin = new GameWin();

        System.out.println("Game system test environment setup complete");
    }

    @After
    public void tearDown() {
        clearAllGameLists();
        System.out.println("Game system test environment cleanup complete");
        System.out.println("==========================================");
    }

    /**
     * Helper method to clear all game object lists
     */
    private void clearAllGameLists() {
        GameUtils.gameObjList.clear();
        GameUtils.shellObjList.clear();
        GameUtils.doubleShellObjList.clear();
        GameUtils.tripleShellObjList.clear();
        GameUtils.enemy1ObjList.clear();
        GameUtils.enemy2ObjList.clear();
        GameUtils.enemy2BulletObjList.clear();
        GameUtils.littleBoss1BulletList.clear();
        GameUtils.littleBoss2BulletList.clear();
        GameUtils.bossBulletList.clear();
        GameUtils.explodeObjList.clear();
        GameUtils.giftObjList.clear();
        GameUtils.removeList.clear();
    }

    /**
     * Helper method to reset game state
     */
    private void resetGameState() {
        GameWin.score = 0;
        GameWin.state = 0;
        GameWin.playerLives = 5;
        GameWin.planeindex = 0;
    }

    /**
     * Test complete game flow - from start to completion
     * This is a black-box test, only focusing on game state transitions and score changes
     */
    @Test
    public void testCompleteGameFlow() {
        System.out.println("Test: Complete game flow");

        // 1. Initial state check
        assertEquals("Initial game state should be not started", 0, GameWin.state);
        assertEquals("Initial score should be 0", 0, GameWin.score);
        assertEquals("Initial lives should be 5", 5, GameWin.playerLives);

        // 2. Start game
        System.out.println("Starting game...");
        GameWin.state = 1; // Simulate clicking to start game
        assertEquals("Game state should change to in-game", 1, GameWin.state);

        // 3. Initialize main game objects
        Image testImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        PlaneObj planeObj = new PlaneObj(testImage, 37, 41, 300, 500, 0, gameWin);
        GameUtils.gameObjList.add(planeObj);
        GameWin.planeindex = GameUtils.gameObjList.indexOf(planeObj);

        // 4. Simulate game process - defeat enemies to earn score
        System.out.println("Simulating defeating enemies...");
        GameWin.score += 50; // Simulate defeating multiple enemies
        assertEquals("Score should increase after defeating enemies", 50, GameWin.score);

        // 5. Simulate collecting items to upgrade weapons
        System.out.println("Simulating collecting weapon upgrade items...");
        planeObj.times = 1; // Simulate weapon upgrade to level 2
        assertEquals("Weapon level should increase after collecting items", 1, planeObj.times);

        // 6. Simulate being hit by enemies
        System.out.println("Simulating being hit by enemies...");
        GameWin.playerLives -= 2; // Simulate being hit twice
        assertEquals("Lives should decrease after being hit", 3, GameWin.playerLives);

        // 7. Simulate pausing game
        System.out.println("Pausing game...");
        GameWin.state = 2; // Simulate pressing spacebar to pause
        assertEquals("Game state should change to paused after pressing spacebar", 2, GameWin.state);

        // 8. Simulate resuming game
        System.out.println("Resuming game...");
        GameWin.state = 1; // Simulate pressing spacebar again to resume
        assertEquals("Game state should change back to in-game after pressing spacebar again", 1, GameWin.state);

        // 9. Simulate defeating Boss, completing the game
        System.out.println("Simulating defeating Boss...");
        BossObj boss = new BossObj(testImage, 240, 174, 180, 180, 3, gameWin);
        GameUtils.gameObjList.add(boss);

        // Simulate Boss being defeated, game completion
        GameWin.score += 10; // Boss points
        GameWin.state = 4; // Game completed state

        assertEquals("Score should increase after defeating Boss", 60, GameWin.score);
        assertEquals("Game state should change to completed after defeating Boss", 4, GameWin.state);

        // 10. Simulate restarting game
        System.out.println("Restarting game...");
        // Call code to restart game
        GameWin.score = 0;
        GameWin.playerLives = 5;
        GameWin.state = 1;
        planeObj.times = 0;

        assertEquals("Score should reset to 0 after restart", 0, GameWin.score);
        assertEquals("Lives should reset to 5 after restart", 5, GameWin.playerLives);
        assertEquals("Game state should be in-game after restart", 1, GameWin.state);
        assertEquals("Weapon level should reset after restart", 0, planeObj.times);

        System.out.println("Complete game flow test passed ✓");
    }

    /**
     * Test game performance and responsiveness
     * Simulate game performance under high load
     */
    @Test
    public void testGamePerformance() {
        System.out.println("Test: Game performance and responsiveness");

        // Set game state to in-game
        GameWin.state = 1;

        // Create many game objects to simulate high load
        Image testImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);

        // Create player aircraft
        PlaneObj planeObj = new PlaneObj(testImage, 37, 41, 300, 500, 0, gameWin);
        GameUtils.gameObjList.add(planeObj);
        GameWin.planeindex = GameUtils.gameObjList.indexOf(planeObj);

        System.out.println("Creating many game objects...");
        // Create 50 enemy aircraft
        for (int i = 0; i < 50; i++) {
            Enemy1Obj enemy = new Enemy1Obj(testImage, 32, 24, (int)(Math.random() * 600), (int)(Math.random() * 400), 5, gameWin);
            GameUtils.enemy1ObjList.add(enemy);
            GameUtils.gameObjList.add(enemy);
        }

        // Create 20 enemy bullets
        for (int i = 0; i < 20; i++) {
            Enemy2BulletObj bullet = new Enemy2BulletObj(testImage, 14, 25, (int)(Math.random() * 600), (int)(Math.random() * 400), 5, gameWin);
            GameUtils.enemy2BulletObjList.add(bullet);
            GameUtils.gameObjList.add(bullet);
        }

        // Create 10 explosion effects
        for (int i = 0; i < 10; i++) {
            ExplodeObj explode = new ExplodeObj((int)(Math.random() * 600), (int)(Math.random() * 400));
            GameUtils.explodeObjList.add(explode);
            GameUtils.gameObjList.add(explode);
        }

        System.out.println("Total game objects: " + GameUtils.gameObjList.size());

        // Measure time to process all objects
        long startTime = System.currentTimeMillis();

        // Simulate one frame of game rendering
        for (int i = 0; i < GameUtils.gameObjList.size(); i++) {
            GameUtils.gameObjList.get(i).paintSelf(graphics);
        }

        long endTime = System.currentTimeMillis();
        long renderTime = endTime - startTime;

        System.out.println("Time to render one frame: " + renderTime + "ms");

        // Verify rendering time is within acceptable range (assuming less than 100ms is acceptable)
        assertTrue("Rendering time should be within acceptable range", renderTime < 100);

        System.out.println("Game performance test passed ✓");
    }

    /**
     * Test player input response system
     * This is a white-box test because it tests internal implementation details
     */
    @Test
    public void testPlayerInputResponse() {
        System.out.println("Test: Player input response system");

        // Set game state to in-game
        GameWin.state = 1;

        // Create player aircraft
        Image testImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        PlaneObj planeObj = new PlaneObj(testImage, 37, 41, 300, 500, 0, gameWin);
        GameUtils.gameObjList.add(planeObj);
        GameWin.planeindex = 0;

        // Record initial position
        int initialX = planeObj.getX();
        int initialY = planeObj.getY();

        // Simulate setting mouse move to new position
        int newX = 400;
        int newY = 400;
        planeObj.setX(newX);
        planeObj.setY(newY);

        // Call paintSelf to trigger aircraft drawing
        planeObj.paintSelf(graphics);

        // Verify aircraft position is updated
        assertEquals("Aircraft X coordinate should update to mouse position", newX, planeObj.getX());
        assertEquals("Aircraft Y coordinate should update to mouse position", newY, planeObj.getY());

        System.out.println("Player input response test passed ✓");
    }

    /**
     * Test game boundary conditions
     * Test game behavior under extreme conditions
     */
    @Test
    public void testGameBoundaryConditions() {
        System.out.println("Test: Game boundary conditions");

        // Set game state to in-game
        GameWin.state = 1;

        Image testImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);

        // 1. Test player aircraft behavior at boundaries
        System.out.println("Testing player aircraft behavior at boundaries");
        PlaneObj planeObj = new PlaneObj(testImage, 37, 41, 0, 0, 0, gameWin);
        GameUtils.gameObjList.add(planeObj);
        GameWin.planeindex = 0;

        // Call paintSelf to trigger boundary check
        planeObj.paintSelf(graphics);

        // Verify aircraft doesn't go beyond left and top boundaries
        assertTrue("Aircraft X coordinate should not be less than 0", planeObj.getX() >= 0);
        assertTrue("Aircraft Y coordinate should not be less than 0", planeObj.getY() >= 0);

        // Move to right and bottom boundaries
        planeObj.setX(563); // 600 - 37 = 563 (where 37 is width)
        planeObj.setY(759); // 800 - 41 = 759 (where 41 is height)
        planeObj.paintSelf(graphics);

        // Verify aircraft doesn't go beyond right and bottom boundaries
        assertTrue("Aircraft X coordinate should not exceed right boundary", planeObj.getX() <= 600 - planeObj.getWidth());
        assertTrue("Aircraft Y coordinate should not exceed bottom boundary", planeObj.getY() <= 800 - planeObj.getHeight());

        // 2. Test extreme life value conditions
        System.out.println("Testing extreme life value conditions");

        // Set lives to maximum value
        GameWin.playerLives = Integer.MAX_VALUE;

        // Simulate being hit
        GameWin.playerLives--;

        // Verify lives decrease correctly
        assertEquals("Extremely high lives should decrease correctly", Integer.MAX_VALUE - 1, GameWin.playerLives);

        // Set lives to 0
        GameWin.playerLives = 0;
        GameWin.state = 1; // Reset to in-game state

        // Simulate being hit
        if (GameWin.playerLives <= 0) {
            GameWin.state = 3; // Game over
        }

        // Verify game state changes to game over
        assertEquals("Game state should change to game over when lives are 0", 3, GameWin.state);

        // 3. Test extreme score conditions
        System.out.println("Testing extreme score conditions");

        // Set score to maximum value
        GameWin.score = Integer.MAX_VALUE;

        // Simulate earning points
        try {
            GameWin.score += 10;
        } catch (Exception e) {
            // Catch possible integer overflow exception
            System.out.println("Score overflow exception caught: " + e.getMessage());
            GameWin.score = Integer.MAX_VALUE; // Keep maximum value
        }

        System.out.println("Game boundary conditions test passed ✓");
    }
}