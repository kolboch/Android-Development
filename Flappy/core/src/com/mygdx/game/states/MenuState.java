package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.FlappyGame;

/**
 * Created by Karol on 2017-05-27.
 */

public class MenuState extends State {

    private static final String LOG_TAG_MENU_STATE = MenuState.class.getSimpleName();
    private Texture background;
    private Texture playButton;

    public MenuState(GameStateManager gameStateManager) {
        super(gameStateManager);
        background = new Texture("bg.png");
        playButton = new Texture("playbtn.png");
    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()) {
            gameStateManager.set(new PlayState(gameStateManager));
        }
    }

    @Override
    public void update(float delta) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.begin();
        spriteBatch.draw(background, 0, 0, FlappyGame.WIDTH, FlappyGame.HEIGHT);
        spriteBatch.draw(playButton, FlappyGame.WIDTH / 2 - playButton.getWidth() / 2, FlappyGame.HEIGHT / 2 - playButton.getHeight() / 2);
        spriteBatch.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        playButton.dispose();
        Gdx.app.log(LOG_TAG_MENU_STATE, "Disposing resources.");
    }
}
