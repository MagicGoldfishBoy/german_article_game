package io.github.german_article_game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.MathUtils;
import com.dongbat.jbump.CollisionFilter;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.Rect;
import com.dongbat.jbump.Response;
import com.dongbat.jbump.Response.Result;

public class Player extends Entity {
    static TextureAtlas atlas = new TextureAtlas("animations/peopleskin.atlas");
    public static final Animation<AtlasRegion> playerAnimation =
        new Animation<>(1.5f / 30f, atlas.findRegions("player-normal"), PlayMode.LOOP);

    public static final float SPEED = 200f;
    Bullet bullet;
    final Main game;

    public Player(Main game) {
        this.game = game;
        this.animation = playerAnimation;
        this.x = 100;
        this.y = 100;
        this.bboxWidth = 10;
        this.bboxHeight = 10;
        this.bboxX = 0;
        this.bboxY = 0;
        item = new Item<>(this);
        game.world.add(item, x + bboxX, y + bboxY, bboxWidth, bboxHeight);
    }

    @Override
    public void act(float delta) {
        boolean left = Gdx.input.isKeyPressed(Config.leftKey);
        boolean right = Gdx.input.isKeyPressed(Config.rightKey);
        boolean up = Gdx.input.isKeyPressed(Config.upKey);
        boolean down = Gdx.input.isKeyPressed(Config.downKey);
        boolean fire = Gdx.input.isKeyJustPressed(Config.fireKey);

        Float direction = null;

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

        if (fire) {
            Bullet b = new Bullet(game);
            b.x = x + bboxWidth / 2f - b.bboxWidth / 2f;
            b.y = y + bboxHeight / 2f - b.bboxHeight / 2f;
            b.direction = direction != null ? direction : 0f;

            game.entities.add(b);
        }

        if (direction != null) {
            x += MathUtils.cosDeg(direction) * SPEED * delta;
            y += MathUtils.sinDeg(direction) * SPEED * delta;
        }

        Result result = game.world.move(item, x + bboxX, y + bboxY, new PlayerCollisionFilter());
        Rect rect = game.world.getRect(item);
        if (rect != null) {
            x = rect.x - bboxX;
            y = rect.y - bboxY;
        }

        animationTime += delta;
        }

    public static class PlayerCollisionFilter implements CollisionFilter {
        @Override
        public Response filter(Item item, Item other) {
            if (other.userData instanceof Entity) return Response.cross;
            else return null;
        }
    }
}