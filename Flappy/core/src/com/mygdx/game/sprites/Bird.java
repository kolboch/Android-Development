package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Karol on 2017-05-28.
 */

public class Bird {

    private static final int GRAVITY = -15;
    private static final int MOVEMENT_X = 100;
    private static final int JUMP_VELOCITY_Y = 300;
    private Vector3 position;
    private Vector3 velocity;
    private Texture bird;
    private Rectangle bounds;

    public Bird(int x, int y) {
        position = new Vector3(x, y, 0);
        velocity = new Vector3(0, 0, 0);
        bird = new Texture("bird.png");
        bounds = new Rectangle(x, y, bird.getWidth(), bird.getHeight());
    }

    public void update(float delta) {
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

    public Texture getTexture() {
        return bird;
    }

    public void jump() {
        velocity.y = JUMP_VELOCITY_Y;
    }

    public void dispose() {
        bird.dispose();
    }

    public Rectangle getBounds() {
        return bounds;
    }
}