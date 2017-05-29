package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Karol on 2017-05-29.
 */

public class Animation {

    private Array<TextureRegion> frames;
    private float maxFrameTime;
    private float currentFrameTime;
    private int frameCount;
    private int currentFrame;

    public Animation(TextureRegion region, int frameCount, float cycleTime) {
        frames = new Array<TextureRegion>();
        int frameWidth = region.getRegionWidth() / frameCount;
        int frameHeight = region.getRegionHeight();
        for (int i = 0; i < frameCount; i++) {
            frames.add(new TextureRegion(region, i * frameWidth, 0, frameWidth, frameHeight));
        }
        this.frameCount = frameCount;
        this.maxFrameTime = cycleTime / frameCount;
        this.currentFrame = 0;
    }

    public void update(float delta) {
        currentFrameTime += delta;
        if (currentFrameTime > maxFrameTime) {
            currentFrame++;
            currentFrame %= frameCount;
            currentFrameTime -= maxFrameTime;
        }
    }

    public TextureRegion getFrame() {
        return frames.get(currentFrame);
    }

    public void dispose() {
        for (TextureRegion textureRegion : frames) {
            textureRegion.getTexture().dispose();
        }
    }

}
