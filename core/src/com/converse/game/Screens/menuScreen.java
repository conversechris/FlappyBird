package com.converse.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.converse.game.FlappyBird;

/**
 * Author: Christopher Converse
 *
 * Class Name: menuScreen
 * Purpose: Screen displayed to user upon loading game
 * Description: Simple screen showing title and play button.
 */

public class menuScreen implements Screen {
    private FlappyBird game;
    private Texture background; // Background image
    private Texture playButton; // Play button image
    private OrthographicCamera cam; //Cam of world

    /**
     * Method Name: menuScreen
     * Purpose: Constructor
     * Parameters: game - FlappyBird game from startup
     * Description: Initialize a new menuScreen
     *
     */
    public menuScreen(FlappyBird game) {
        this.game = game;
        background = new Texture("bg.png");
        playButton = new Texture("playButton.png");
        cam = new OrthographicCamera();
        cam.setToOrtho(false, FlappyBird.WIDTH / 2, FlappyBird.HEIGHT / 2);
    }

    @Override
    public void show() {

    }

    /**
     * Method Name: render
     * Purpose: Called everytime to update changes to screen
     * Parameters: delta - Time from last frame to current gtram
     * Description: Simply shows screen, waiting for user input to begin game
     */
    @Override
    public void render(float delta) {
        handleInput();
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(cam.combined);
        game.batch.begin();
        game.batch.draw(background, 0, 0);
        game.batch.draw(playButton, cam.position.x - (playButton.getWidth() / 2),
                cam.position.y);
        game.batch.end();
    }

    /**
     * Method Name: handleInput
     * Purpose: Check for handling
     * Parameters: void
     * Description: Check for user touching screen or spacebar being pressed
     */
    public void handleInput() {
        if (Gdx.input.justTouched() ||
                Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            game.setScreen(new playScreen(game));
        }
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
     * Clear memory alloaction
     */
    @Override
    public void dispose() {
        background.dispose();
        playButton.dispose();

    }
}
