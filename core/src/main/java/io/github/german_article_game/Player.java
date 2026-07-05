package io.github.german_article_game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.MathUtils;

public class Player extends Entity {
    static TextureAtlas atlas = new TextureAtlas("animations/peopleskin.atlas");
    public static final Animation<AtlasRegion> playerAnimation =
        new Animation<>(1.5f / 30f, atlas.findRegions("player-normal"), PlayMode.LOOP);

    public static final float SPEED = 200f;

    public Player() {
        this.animation = playerAnimation;
        this.x = 100;
        this.y = 100;
        this.bboxWidth = 10;
        this.bboxHeight = 10;
        this.bboxX = 0;
        this.bboxY = 0;
    }

    @Override
    public void act(float delta) {
        boolean left = Gdx.input.isKeyPressed(Config.leftKey);
        boolean right = Gdx.input.isKeyPressed(Config.rightKey);
        boolean up = Gdx.input.isKeyPressed(Config.upKey);
        boolean down = Gdx.input.isKeyPressed(Config.downKey);
        boolean fire = Gdx.input.isKeyJustPressed(Config.fireKey);

        Float direction = null; // null = not moving

        if (left || right || up || down) {
            if (left || right) {
                if (left) {
                    direction = 180f;
                    if (up) direction = 135f;
                    else if (down) direction = 225f;
                } else {
                    direction = 0f;
                    if (up) direction = 45f;
                    else if (down) direction = 315f;
                }
            } else {
                if (up) direction = 90f;
                else if (down) direction = 270f;
            }
        }

        if (direction != null) {
            x += MathUtils.cosDeg(direction) * SPEED * delta;
            y += MathUtils.sinDeg(direction) * SPEED * delta;
        }

        animationTime += delta;
    }
}