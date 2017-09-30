package com.converse.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.converse.game.Screens.menuScreen;

/**
 * Author: Christopher Converse
 */
public class FlappyBird extends Game {

    public static final int WIDTH = 480;
    public static final int HEIGHT = 800;
	public SpriteBatch batch;
	private Music music;


	@Override
	public void create () {
		batch = new SpriteBatch();
		music = Gdx.audio.newMusic(Gdx.files.internal("music2.mp3"));
		music.setLooping(true);
		music.setVolume(0.05f);
		music.play();
		setScreen(new menuScreen(this));
		Gdx.gl.glClearColor(1,0, 0, 1);
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		music.dispose();
		batch.dispose();
	}
}
