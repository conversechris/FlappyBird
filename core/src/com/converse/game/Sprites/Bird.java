package com.converse.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.converse.game.FlappyBird;

/**
 * Author: Christopher Converse
 *
 * Class Name: Bird
 * Purpose: Bird object in FlappyBird game
 * Description: Holds all methods for handling birds, including updating
 * velocity, position, and collisions.
 */
public class Bird {

    private static final int GRAVITY = -15; // Change in y position
    private static final int MOVEMENT = 180; // X movement across screen

    private Texture bird; // Image of bird
    private Vector2 position; // Keep track of XY position
    private Vector2 velocity; // Keep track of speed the bird is moving
    private Rectangle bounds; // For collision checking
    private Sound flap; // Sound made each flap

    /**
     * Method Name: Bird
     * Purpose: Constructor
     * Parameters: x/y - x and y coordinates of where bird begins on screen
     * Description: Create a new bird object to be displayed onto the screen
     */
    public Bird(int x, int y) {
        bird = new Texture("bird.png");
        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);
        bounds = new Rectangle(x, y, bird.getWidth(), bird.getHeight());
        flap = Gdx.audio.newSound(Gdx.files.internal("flap.ogg"));
    }

    /**
     * Method Name: update
     * Purpose: Handle position and velocity of bird
     * Parameters: dt - time from last frame and current frame
     * Description: Make changes to the velocity and position of the bird
     * from the last frame
     */
    public void update(float dt) {
        if (position.y > 0)
            velocity.add(0, GRAVITY);

        velocity.scl(dt);
        position.add(MOVEMENT * dt, velocity.y);

        if (position.y < 0)
            position.y = 0;


        if (position.y > FlappyBird.HEIGHT)
            position.y = FlappyBird.HEIGHT;

        velocity.scl(1/dt);

        bounds.setPosition(position.x, position.y);
    }

    /**
     * Method Name: bounce
     * Purpose: Action from user to happen from tapping/spacebar
     * Parameters: None
     * Description: Called when user makes the bird 'fly'
     */
    public void bounce() {
        velocity.y = 250;
        flap.play(0.07f);
    }

    /**
     * Method Name: getPosition
     * Purpose: Returns position from Bird object
     *
     */
    public Vector2 getPosition() {
        return position;
    }

    /**
     * Method Name: getBird
     * Purpose: Returns the bird object texture
     *
     */
    public Texture getBird() {
        return bird;
    }

    /**
     * Method Name: getBounds
     * Purpose: Return Rectangle the encompasses the bird
     *
     */
    public Rectangle getBounds() { return bounds; }

    /**
     * Clear memory allocation
     */
    public void dispose() {
        bird.dispose();
        flap.dispose();
    }

}
