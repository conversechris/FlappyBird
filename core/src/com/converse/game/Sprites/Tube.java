package com.converse.game.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

/**
 * Author: Christopher Converse
 *
 * Class Name: Tube
 * Purpose: Obstacles in game
 * Description: Contains constructor and methods regarding handling
 * of Tube objects in FlappyBird game. This includes repositioning them
 * when out of bounds, and collision checking when a bird flies into one
 */
public class Tube {
    public static final int TUBE_WIDTH = 52; // Width of tubes
    private static final int FLUCT = 130; // The randomness of high/lowness of tubes
    private static final int TUBE_GAP = 90; // Space between each tube object
    private static final int LOWEST_OPENING = 120; // Lowest a tube can be from the
                                                    // bottom

    private Rectangle boundsTop, boundsBot; // Two rectangles needed
    private Texture topTube, botTube; // Top/Bot tube textures
    private Vector2 posTopTube, posBotTube; // Position of tubes
    private Random rand; // Random number generator
    private boolean flownThrough = false; // Keep track of when bird flies through

    /**
     * Method Name: Tube
     * Purpose: Constructor
     * Parameters: x - x position of tube
     * Description: Creation of a Tube object
     */
    public Tube(float x) {
        topTube = new Texture("topTube.png");
        botTube = new Texture("botTube.png");
        rand = new Random();

        posTopTube = new Vector2(x, rand.nextInt(FLUCT) + TUBE_GAP + LOWEST_OPENING);
        posBotTube = new Vector2(x, posTopTube.y - TUBE_GAP - botTube.getHeight());

        boundsTop = new Rectangle(posTopTube.x, posTopTube.y, topTube.getWidth(), topTube.getHeight());
        boundsBot = new Rectangle(posBotTube.x, posBotTube.y, botTube.getWidth(), botTube.getHeight());
    }

    /**
     * Method Name: reposition
     * Purpose: Move tubes from off screen
     * Parameters: x - X position to set it to
     * Description: Called when a tube falls off from the game cam
     */
    public void reposition(float x) {
        posTopTube.set(x, rand.nextInt(FLUCT) + TUBE_GAP + LOWEST_OPENING);
        posBotTube.set(x, posTopTube.y - TUBE_GAP - botTube.getHeight());

        boundsTop.setPosition(posTopTube.x, posTopTube.y);
        boundsBot.setPosition(posBotTube.x, posBotTube.y);
    }

    /**
     * Method Name: collides
     * Purpose: Check for collision
     * Parameters: player - The bird object
     * Description: Returns a bool indicating collision occurring or not
     */
    public boolean collides(Rectangle player) {
        return player.overlaps(boundsTop) || player.overlaps(boundsBot);
    }

    /**
     * Clear memory allocation
     */
    public void dispose() {
        topTube.dispose();
        botTube.dispose();
    }

    /**
     * Getter methods for various fields of a Tube object
     *
     */

    public void setFlownThrough(boolean bool) {
        flownThrough = bool;
    }

    public boolean getFlownThrough() {
        return flownThrough;
    }

    public Texture getTopTube() {
        return topTube;
    }

    public Texture getBotTube() {
        return botTube;
    }

    public Vector2 getPosBotTube() {
        return posBotTube;
    }

    public Vector2 getPosTopTube() {
        return posTopTube;
    }

    public Rectangle getBoundsTop() { return boundsTop; }
}
