package com.mygdx.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Karol on 2017-05-28.
 */

public class Bird {

    private static final int GRAVITY = -15;
    private static final int MOVEMENT_X = 100;
    private static final int JUMP_VELOCITY_Y = 300;
    private static final float SOUNDS_VOLUME = 0.5f;
    private Vector3 position;
    private Vector3 velocity;
    private Rectangle bounds;
    private Animation birdAnimation;
    private Sound jumpSound;

    public Bird(int x, int y) {
        position = new Vector3(x, y, 0);
        velocity = new Vector3(0, 0, 0);
        TextureRegion region = new TextureRegion(new Texture("birdanimation.png"));
        birdAnimation = new Animation(region, 3, 0.7f);
        jumpSound = Gdx.audio.newSound(Gdx.files.internal("sfx_wing.ogg"));
        TextureRegion bird = birdAnimation.getFrame();
        bounds = new Rectangle(x, y, bird.getRegionWidth(), bird.getRegionHeight());
    }

    public void update(float delta) {
        birdAnimation.update(delta);
        if (position.y > 0) {
            velocity.add(0, GRAVITY, 0);
            velocity.x = MOVEMENT_X;
        }
        velocity.scl(delta);
        position.add(velocity.x, velocity.y, 0);
        velocity.scl(1 / delta);
        if (position.y < 0) {
            position.y = 0;
            velocity.x = 0;
        }
        bounds.setPosition(position.x, position.y);
    }

    public Vector3 getPosition() {
        return position;
    }

    public TextureRegion getTexture() {
        return birdAnimation.getFrame();
    }

    public void jump() {
        velocity.y = JUMP_VELOCITY_Y;
        jumpSound.play(SOUNDS_VOLUME);
    }

    public void dispose() {
        birdAnimation.dispose();
        jumpSound.dispose();
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
