package com.converse.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.converse.game.FlappyBird;
import com.converse.game.Sprites.Bird;
import com.converse.game.Sprites.Tube;

/**
 * Author: Christopher Converse
 *
 * Class Name: playScreen
 * Purpose: Screen displayed when game is playing; after the user sees the menu
 * Description: Game continues to run until user exits out of the application. The goal of the
 * game is to weave the bird through the tubes. If at any point a tube is hit, the game will stop,
 * communicating to the user that a tube was hit. The user can then start a new match by either
 * left clicking on the mouse or by hitting space. Both keys can also be used to jump with the
 * bird.
 *
 *
 *
 */

public class playScreen implements Screen {

    private FlappyBird game;
    private Texture background; // Background of the game
    private OrthographicCamera cam; // Camera of the game world
    private long startTime; // Timer; used when collision occurs
    private long endTime; // Compared with startTime to see how much time has passed in order to
                                // create a new playScreen and start a new game
    private boolean collisionOccurred = false; // Hold when collisions occur in game

    private static final int GROUND_Y_OFFSET = -45; // Offset to place ground
    private static final int TUBE_COUNT = 5; // Amount of tubes that will be used in game
    private static final int TUBE_SPACING = 125; // Space between tubes
    private static final int PLAY_DELAY = 1000; // Delay after collision occurs

    private Vector2 groundPos1, groundPos2; // Two grounds with two different positions to place
    private Texture ground; // Graphic of what ground will look like
    private Array<Tube> tubes; // Array to hold tubes to weave through
    private Bird bird; // Bird object controlled by user
    private Sound splat; // Sound made when a collision occurs in the game
    private Sound success; // Sound made when successful passing between tubes


    /**
     * Method name: playScreen
     * Purpose: Constructor for a new screen to play the game on
     * Parameters: game - FlappyBird game from startup
     * Description: Initialization of many different objects needed such as bird, ground, tubes,
     * background.
     *
     */
    public playScreen(FlappyBird game) {
        this.game = game;
        cam = new OrthographicCamera();
        cam.setToOrtho(false, FlappyBird.WIDTH / 2, FlappyBird.HEIGHT / 2);

        // Set background image and ground images
        background = new Texture("bg.png");
        ground = new Texture("ground.png");

        // Set sounds for collisions and non collisions
        splat = Gdx.audio.newSound(Gdx.files.internal("splat.mp3"));
        success = Gdx.audio.newSound(Gdx.files.internal("success.wav"));

        // Create new objects to be used in game
        bird = new Bird(40, 300);
        groundPos1 = new Vector2(cam.position.x - cam.viewportWidth / 2, GROUND_Y_OFFSET);
        groundPos2 = new Vector2((cam.position.x - cam.viewportWidth / 2) + ground.getWidth(), GROUND_Y_OFFSET);
        tubes = new Array<Tube>();

        for (int i = 2; i <= TUBE_COUNT + 1; i++) {
            tubes.add(new Tube(i * (TUBE_SPACING + Tube.TUBE_WIDTH)));
        }
    }

    /**
     * Name: Update
     * Purpose: Check multiple objects and update positions of them if necessary as the game
     * continues
     * Parameters: dt - delta time; the time between the last frame and current frame
     * Description: Checks for whether a collision has occurred or not. If a collisision hasn't
     * occurred. If a collision hasn't occurred, positions of the cam and objects as well as input
     * checking is done. If a collision has occurred, set a delay until the next game can be started.
     *
     */
    public void update(float dt) {

        // Collision not occurred
        if (!collisionOccurred) {

            // Input handling
            if (Gdx.input.justTouched() || Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
                bird.bounce();

            if (cam.position.x - cam.viewportWidth / 2 > groundPos1.x + ground.getWidth())
                groundPos1.add(ground.getWidth() * 2, 0);

            if (cam.position.x - cam.viewportWidth / 2 > groundPos2.x + ground.getWidth())
                groundPos2.add(ground.getWidth() * 2, 0);

            bird.update(dt);

            cam.position.x = bird.getPosition().x + 80;

            //  Check each tube for collision, repositioning, or success of passing through
            for (int i = 0; i < tubes.size; i++) {
                Tube tube = tubes.get(i);

                if (cam.position.x - (cam.viewportWidth / 2) > tube.getPosTopTube().x + tube.getTopTube().getWidth()) {
                    tube.reposition(tube.getPosTopTube().x + (Tube.TUBE_WIDTH + TUBE_SPACING) * TUBE_COUNT);
                    tube.setFlownThrough(false);
                }

                if (tube.collides(bird.getBounds())) {
                    collisionOccurred = true;
                    startTime = System.currentTimeMillis();
                    splat.play(0.1f);
                }

                if (!tube.getFlownThrough() && (bird.getBounds().getX() > tube.getBoundsTop().getX())) {
                    tube.setFlownThrough(true);
                    success.play(1.0f);
                }
            }

            // Collision with the ground
            if (bird.getPosition().y <= ground.getHeight() + GROUND_Y_OFFSET) {
                collisionOccurred = true;
                startTime = System.currentTimeMillis();
                splat.play(0.1f);
            }

            // Collision with the ceiling of the cam
            if (bird.getPosition().y > FlappyBird.HEIGHT / 2) {
                bird.getPosition().y = FlappyBird.HEIGHT / 2;
                collisionOccurred = true;
                startTime = System.currentTimeMillis();
                splat.play(0.1f);
            }
        }

        // A collision has occurred
        else {
            endTime = System.currentTimeMillis();
            long difference = endTime - startTime;

            if (difference > PLAY_DELAY && (Gdx.input.justTouched() || Gdx.input.isKeyJustPressed(Input.Keys.SPACE))) {
                startTime = 0;
                collisionOccurred = false;
                dispose();
                game.setScreen(new playScreen(game));
            }
        }
    }


    /**
     * Name: render
     * Purpose: Updatre object positions and show that the user each frame
     * Parameters: dt - delta time; time between last frame and current frame
     * Description: Calls update method to make any necessary changes to object locations and
     * collision checking. Then draw that to the screen so the user can see the changes each frame.
     *
     */
    @Override
    public void render(float dt) {

        update(dt);

        game.batch.setProjectionMatrix(cam.combined);
        game.batch.begin();
        game.batch.draw(background, cam.position.x - (cam.viewportWidth / 2), 0);
        game.batch.draw(bird.getBird(), bird.getPosition().x, bird.getPosition().y);

        for (int i = 0; i < tubes.size; i++) {
            Tube tube = tubes.get(i);
            game.batch.draw(tube.getTopTube(), tube.getPosTopTube().x, tube.getPosTopTube().y);
            game.batch.draw(tube.getBotTube(), tube.getPosBotTube().x, tube.getPosBotTube().y);
        }

        game.batch.draw(ground, groundPos1.x, groundPos1.y);
        game.batch.draw(ground, groundPos2.x, groundPos2.y);

        game.batch.end();
        cam.update();
    }

    // IGNORE
    private void updateGround() {
        if (cam.position.x - cam.viewportWidth / 2 > groundPos1.x + ground.getWidth())
            groundPos1.add(ground.getWidth() * 2, 0);

        if (cam.position.x - cam.viewportWidth / 2 > groundPos2.x + ground.getWidth())
            groundPos2.add(ground.getWidth() * 2, 0);
    }


    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    /**
     * Clear memory allocation
     */
    @Override
    public void dispose() {
        bird.dispose();
        for (Tube tube : tubes)
            tube.dispose();
        ground.dispose();
    }
}
