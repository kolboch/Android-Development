package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.FlappyGame;
import com.mygdx.game.sprites.Bird;
import com.mygdx.game.sprites.Tube;

/**
 * Created by Karol on 2017-05-28.
 */

public class PlayState extends State {

    private static final String LOG_PLAY_STATE = PlayState.class.getSimpleName();
    private static final int TUBE_SPACING = 125;
    private static final int TUBE_COUNT = 4;
    private static final int CAMERA_X_OFFSET = 80;
    private static final int GROUND_Y_OFFSET = -50;

    private Bird bird;
    private int birdStartX = 50;
    private int birdStartY = 150;
    private Texture background;
    private Texture ground;
    private Vector2 groundPosition1, groundPosition2;
    private Array<Tube> tubes;

    public PlayState(GameStateManager gameStateManager) {
        super(gameStateManager);
        bird = new Bird(birdStartX, birdStartY);
        camera.setToOrtho(false, FlappyGame.WIDTH / 2, FlappyGame.HEIGHT / 2);
        background = new Texture("bg.png");
        ground = new Texture("ground.png");
        groundPosition1 = new Vector2(camera.position.x - camera.viewportWidth / 2, GROUND_Y_OFFSET);
        groundPosition2 = new Vector2(camera.position.x - camera.viewportWidth / 2 + ground.getWidth(), GROUND_Y_OFFSET);
        tubes = new Array<Tube>();
        for (int i = 1; i <= TUBE_COUNT; i++) {
            tubes.add(new Tube(i * (TUBE_SPACING + Tube.TUBE_WIDTH)));
        }
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
            bird.jump();
        }
    }

    @Override
    public void update(float delta) {
        handleInput();
        bird.update(delta);
        camera.position.x = bird.getPosition().x + CAMERA_X_OFFSET;
        for(Tube tube : tubes){
            if (camera.position.x - camera.viewportWidth / 2 > tube.getPositionTopTube().x + tube.getTopTube().getWidth()) {
                tube.reposition(tube.getPositionTopTube().x + (Tube.TUBE_WIDTH + TUBE_SPACING) * TUBE_COUNT);
            }
            if (tube.collides(bird.getBounds())) {
                gameStateManager.set(new MenuState(gameStateManager));
                break;
            }
        }
        updateGround();
        checkGroundCollision();
        camera.update();
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        spriteBatch.draw(background, camera.position.x - camera.viewportWidth / 2, 0);
        spriteBatch.draw(bird.getTexture(), bird.getPosition().x, bird.getPosition().y);
        for (Tube tube : tubes) {
            spriteBatch.draw(tube.getTopTube(), tube.getPositionTopTube().x, tube.getPositionTopTube().y);
            spriteBatch.draw(tube.getBottomTube(), tube.getPositionBottomTube().x, tube.getPositionBottomTube().y);
        }
        spriteBatch.draw(ground, groundPosition1.x, groundPosition1.y);
        spriteBatch.draw(ground, groundPosition2.x, groundPosition2.y);
        spriteBatch.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        bird.dispose();
        ground.dispose();
        for (Tube t : tubes) {
            t.dispose();
        }
        Gdx.app.log(LOG_PLAY_STATE, "Disposing resources.");
    }

    private void updateGround(){
        if(camera.position.x - camera.viewportWidth / 2 > groundPosition1.x + ground.getWidth()){
            Vector2 temp = new Vector2(groundPosition1.x + ground.getWidth() * 2, groundPosition1.y);
            groundPosition1 = groundPosition2;
            groundPosition2 = temp;
        }
    }

    private void checkGroundCollision(){
        if(bird.getPosition().y <= ground.getHeight() + GROUND_Y_OFFSET){
            gameStateManager.set(new PlayState(gameStateManager));
        }
    }
}
