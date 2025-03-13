package com.Planwar.obj;

import com.Planwar.GameWin;
import com.Planwar.utils.GameUtils;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ShellObjTest {

    // Test image
    private Image testImage;

    // Test GameWin instance
    private GameWin gameWin;

    // Test Graphics
    private Graphics graphics;

    // Bullet object being tested
    private ShellObj shell;

    @Before
    public void setUp() {
        System.out.println("==========================================");
        System.out.println("Setting up ShellObj test environment");

        // Create test image
        testImage = new BufferedImage(14, 29, BufferedImage.TYPE_INT_ARGB);

        // Create test GameWin
        gameWin = new GameWin();

        // Create test Graphics
        BufferedImage bi = new BufferedImage(600, 800, BufferedImage.TYPE_INT_ARGB);
        graphics = bi.getGraphics();

        // Clear related lists to ensure clean test environment
        GameUtils.shellObjList.clear();
        GameUtils.removeList.clear();

        // Create test bullet
        shell = new ShellObj(testImage, 14, 29, 300, 500, 5, gameWin);
        System.out.println("Created test bullet: Position(" + shell.getX() + ", " + shell.getY() + "), Speed: " + shell.getSpeed());

        // Add bullet to the list
        GameUtils.shellObjList.add(shell);
    }

    @After
    public void tearDown() {
        // Clean up test environment
        GameUtils.shellObjList.clear();
        GameUtils.removeList.clear();
        System.out.println("ShellObj test environment cleanup complete");
        System.out.println("==========================================");
    }

    @Test
    public void testShellInitialization() {
        System.out.println("Test: Bullet initialization");

        assertEquals("Bullet width should be 14", 14, shell.getWidth());
        assertEquals("Bullet height should be 29", 29, shell.getHeight());
        assertEquals("Bullet X coordinate should be 300", 300, shell.getX());
        assertEquals("Bullet Y coordinate should be 500", 500, shell.getY());
        assertEquals("Bullet speed should be 5", 5, shell.getSpeed(), 0.001);

        System.out.println("Bullet initialization test passed ✓");
    }

    @Test
    public void testShellMovement() {
        System.out.println("Test: Bullet movement");

        int initialY = shell.getY();
        System.out.println("Initial Y coordinate: " + initialY);

        // Call paintSelf method to trigger movement
        shell.paintSelf(graphics);

        int expectedY = initialY - (int)shell.getSpeed();
        System.out.println("Expected Y coordinate: " + expectedY + ", Actual Y coordinate: " + shell.getY());

        assertEquals("Bullet should move up by speed units", expectedY, shell.getY());

        System.out.println("Bullet movement test passed ✓");
    }

    @Test
    public void testShellRemovalAtBoundary() {
        System.out.println("Test: Bullet removal at boundary");

        // Place bullet above the screen top
        shell.setY(-10);
        System.out.println("Set bullet Y coordinate to: " + shell.getY() + " (above screen top)");

        // Call paintSelf method to trigger boundary detection
        shell.paintSelf(graphics);

        // Check if bullet was added to removeList
        boolean isInRemoveList = GameUtils.removeList.contains(shell);
        System.out.println("Is bullet in remove list: " + isInRemoveList);

        assertTrue("Bullet should be added to remove list", isInRemoveList);

        System.out.println("Bullet boundary removal test passed ✓");
    }

    @Test
    public void testShellRectangle() {
        System.out.println("Test: Bullet collision rectangle");

        Rectangle rec = shell.getRec();
        System.out.println("Collision rectangle details:");
        System.out.println("X: " + rec.x);
        System.out.println("Y: " + rec.y);
        System.out.println("Width: " + rec.width);
        System.out.println("Height: " + rec.height);

        assertEquals("Rectangle X coordinate should match bullet X coordinate", shell.getX(), rec.x);
        assertEquals("Rectangle Y coordinate should match bullet Y coordinate", shell.getY(), rec.y);
        assertEquals("Rectangle width should match bullet width", shell.getWidth(), rec.width);
        assertEquals("Rectangle height should match bullet height", shell.getHeight(), rec.height);

        System.out.println("Bullet collision rectangle test passed ✓");
    }

    @Test
    public void testShellCollisionWithEnemy() {
        System.out.println("Test: Bullet collision with enemy");

        // Create an enemy overlapping with bullet position
        Enemy1Obj enemy = new Enemy1Obj(testImage, 32, 24, shell.getX(), shell.getY(), 5, gameWin);
        GameUtils.enemy1ObjList.add(enemy);
        GameUtils.gameObjList.add(enemy);

        System.out.println("Created enemy, position(" + enemy.getX() + ", " + enemy.getY() + ") overlapping with bullet");

        // Call enemy's paintSelf method to trigger collision detection
        enemy.paintSelf(graphics);

        // Check if bullet and enemy were added to removeList
        boolean isShellRemoved = GameUtils.removeList.contains(shell);
        boolean isEnemyRemoved = GameUtils.removeList.contains(enemy);

        System.out.println("Is bullet removed: " + isShellRemoved);
        System.out.println("Is enemy removed: " + isEnemyRemoved);

        assertTrue("Bullet should be added to remove list", isShellRemoved);
        assertTrue("Enemy should be added to remove list", isEnemyRemoved);

        // Check if score increased
        System.out.println("Score: " + GameWin.score + " (should be 1)");
        assertEquals("Defeating enemy should earn 1 point", 1, GameWin.score);

        // Cleanup
        GameUtils.enemy1ObjList.clear();
        System.out.println("Bullet collision with enemy test passed ✓");
    }

    @Test
    public void testMultipleShellMovements() {
        System.out.println("Test: Multiple bullet movements");

        int initialY = shell.getY();
        System.out.println("Initial Y coordinate: " + initialY);

        // Simulate bullet moving 3 times consecutively
        for (int i = 1; i <= 3; i++) {
            shell.paintSelf(graphics);
            int expectedY = initialY - i * (int)shell.getSpeed();
            System.out.println("Movement " + i + " - Expected Y coordinate: " + expectedY + ", Actual Y coordinate: " + shell.getY());
            assertEquals("Bullet position incorrect after movement " + i, expectedY, shell.getY());
        }

        System.out.println("Multiple bullet movements test passed ✓");
    }
}