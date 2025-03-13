package com.Planwar.obj;

import com.Planwar.GameWin;
import com.Planwar.utils.GameUtils;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class PlaneObjTest {
    private PlaneObj planeObj;
    private GameWin gameWin;
    private BufferedImage testImage;

    @Before
    public void setUp() {
        // Initialize test environment
        gameWin = new GameWin();
        testImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        planeObj = new PlaneObj(testImage, 37, 41, 290, 550, 0, gameWin);

        // Initialize collections in GameUtils to avoid NullPointerException
        GameUtils.enemy1ObjList = new ArrayList<>();
        GameUtils.enemy2ObjList = new ArrayList<>();
        GameUtils.enemy2BulletObjList = new ArrayList<>();
        GameUtils.littleBoss1BulletList = new ArrayList<>();
        GameUtils.littleBoss2BulletList = new ArrayList<>();
        GameUtils.bossBulletList = new ArrayList<>();
        GameUtils.giftObjList = new ArrayList<>();
        GameUtils.removeList = new ArrayList<>();
        GameUtils.explodeObjList = new ArrayList<>();
    }

    @Test
    public void testPlaneInitialization() {
        System.out.println("Test: Plane Initialization");
        System.out.println("Initial Plane Attributes:");
        System.out.println("X: " + planeObj.getX());
        System.out.println("Y: " + planeObj.getY());
        System.out.println("Width: " + planeObj.getWidth());
        System.out.println("Height: " + planeObj.getHeight());
        System.out.println("Speed: " + planeObj.getSpeed());

        // Test plane initialization attributes
        assertEquals(290, planeObj.getX());
        assertEquals(550, planeObj.getY());
        assertEquals(37, planeObj.getWidth());
        assertEquals(41, planeObj.getHeight());
        assertEquals(0, planeObj.getSpeed(), 0.001);

        System.out.println("Plane Initialization Test Passed ✓\n");
    }

    @Test
    public void testPlaneMovement() {
        System.out.println("Test: Plane Movement");
        System.out.println("Initial Position: (" + planeObj.getX() + ", " + planeObj.getY() + ")");

        // Test plane position setting
        planeObj.setX(100);
        planeObj.setY(200);

        System.out.println("New Position: (" + planeObj.getX() + ", " + planeObj.getY() + ")");

        assertEquals(100, planeObj.getX());
        assertEquals(200, planeObj.getY());

        System.out.println("Plane Movement Test Passed ✓\n");
    }

    @Test
    public void testCollisionWithEnemy() {
        System.out.println("Test: Collision with Enemy");
        // Initialize lives
        GameWin.playerLives = 5;
        int initialLives = GameWin.playerLives;
        System.out.println("Initial Lives: " + initialLives);

        // Create an enemy aircraft overlapping with player plane
        Enemy1Obj enemy = new Enemy1Obj(testImage, 30, 30, planeObj.getX(), planeObj.getY(), 0, gameWin);
        GameUtils.enemy1ObjList.add(enemy);

        // Call paintSelf method to trigger collision detection
        planeObj.paintSelf(testImage.getGraphics());

        System.out.println("Lives after Collision: " + GameWin.playerLives);
        System.out.println("Enemy in Remove List: " + GameUtils.removeList.contains(enemy));

        // Verify lives decreased
        assertEquals(initialLives - 1, GameWin.playerLives);
        // Verify enemy was removed
        assertTrue(GameUtils.removeList.contains(enemy));

        System.out.println("Collision Test Passed ✓\n");
    }

    @Test
    public void testCollectGift() {
        System.out.println("Test: Collecting Gift");
        // Record initial times value
        int initialTimes = PlaneObj.times;
        System.out.println("Initial Gift Collection Times: " + initialTimes);

        // Create a supply item overlapping with player plane
        GiftObj gift = new GiftObj(testImage, 30, 30, planeObj.getX(), planeObj.getY(), 0, gameWin);
        GameUtils.giftObjList.add(gift);

        // Call paintSelf method to trigger collision detection
        planeObj.paintSelf(testImage.getGraphics());

        System.out.println("Gift Collection Times after Test: " + PlaneObj.times);
        System.out.println("Gift in Remove List: " + GameUtils.removeList.contains(gift));

        // Verify times increased
        assertEquals(initialTimes + 1, PlaneObj.times);
        // Verify gift was removed
        assertTrue(GameUtils.removeList.contains(gift));

        System.out.println("Gift Collection Test Passed ✓\n");
    }

    @Test
    public void testGameOverWhenNoLives() {
        System.out.println("Test: Game Over Scenario");
        // Set lives to 1
        GameWin.playerLives = 1;
        GameWin.state = 1; // In-game state
        System.out.println("Initial Lives: " + GameWin.playerLives);
        System.out.println("Initial Game State: " + GameWin.state);

        // Create an enemy aircraft overlapping with player plane
        Enemy1Obj enemy = new Enemy1Obj(testImage, 30, 30, planeObj.getX(), planeObj.getY(), 0, gameWin);
        GameUtils.enemy1ObjList.add(enemy);

        // Call paintSelf method to trigger collision detection
        planeObj.paintSelf(testImage.getGraphics());

        System.out.println("Lives after Collision: " + GameWin.playerLives);
        System.out.println("Game State after No Lives: " + GameWin.state);

        // Verify lives reduced to 0
        assertEquals(0, GameWin.playerLives);
        // Verify game state changed to game over (3)
        assertEquals(3, GameWin.state);

        System.out.println("Game Over Test Passed ✓\n");
    }

    @Test
    public void testGetRecReturnsRectangle() {
        System.out.println("Test: Collision Rectangle");
        // Test if collision detection rectangle is correct
        Rectangle rect = planeObj.getRec();

        System.out.println("Rectangle Details:");
        System.out.println("X: " + rect.x);
        System.out.println("Y: " + rect.y);
        System.out.println("Width: " + rect.width);
        System.out.println("Height: " + rect.height);

        assertNotNull(rect);
        assertEquals(planeObj.getX(), rect.x);
        assertEquals(planeObj.getY(), rect.y);
        assertEquals(planeObj.getWidth(), rect.width);
        assertEquals(planeObj.getHeight(), rect.height);

        System.out.println("Rectangle Test Passed ✓\n");
    }
}