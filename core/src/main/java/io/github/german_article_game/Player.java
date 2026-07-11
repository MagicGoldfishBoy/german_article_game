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

import io.github.german_article_game.Bullet.Bullet;
import io.github.german_article_game.Bullet.BulletManager;
import io.github.german_article_game.Bullet.Player_Bullet;

public class Player extends Entity {
    static TextureAtlas atlas = new TextureAtlas("animations/peopleskin.atlas");
    public static final Animation<AtlasRegion> playerAnimation =
        new Animation<>(1.5f / 30f, atlas.findRegions("player-normal"), PlayMode.LOOP);

    public static final float SPEED = 200f;
    Player_Bullet bullet;

    final Main game;
    BulletManager bulletManager;

    public Player(Main game) {
        this.game = game;   // store the real instance passed in
        this.animation = playerAnimation;
        this.x = 100;
        this.y = 100;
        this.bboxWidth = 15;
        this.bboxHeight = 40;
        this.bboxX = (animation.getKeyFrame(1).getRegionWidth() - this.bboxWidth) / 2;
        this.bboxY = (animation.getKeyFrame(1).getRegionHeight() - this.bboxHeight) / 2;
        this.hp = 100;
        item = new Item<>(this);
        this.game.world.add(item, x + bboxX, y + bboxY, bboxWidth, bboxHeight);

        this.bulletManager = game.bulletManager;
    }
    public void takeDamage(Integer damage) {
        System.out.println(this.hp);
        if (this.hp - damage > 0) {
            this.hp -= damage;
        }
        else {
            System.out.println("Dead");
            this.hp = 100;
           // this.die();
        }
        System.out.println(this.hp);
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
            
            bulletManager.spawnBullet(x + animation.getKeyFrame(1).getRegionWidth() / 2.5f, y + bboxHeight, 0f, Bullet.bulletSpeed);
            // Bullet bullet = new Player_Bullet(game);
            // bullet.x = x + animation.getKeyFrame(1).getRegionWidth() / 2.5f;
            // bullet.y = y + bboxHeight;
            // game.entities.add(bullet);
            // game.world.update(bullet.item, bullet.x + bullet.bboxX, bullet.y + bullet.bboxY);
        }

        if (direction != null) {
            x += MathUtils.cosDeg(direction) * SPEED * delta;
            y += MathUtils.sinDeg(direction) * SPEED * delta;
        }

        Result result = game.world.move(item, x + bboxX, y + bboxY, PlayerCollisionFilter.instance);
        Rect rect = game.world.getRect(item);
        if (rect != null) {
            x = rect.x - bboxX;
            y = rect.y - bboxY;
        }

        animationTime += delta;
        }

        public static class PlayerCollisionFilter implements CollisionFilter {
            public static final PlayerCollisionFilter instance = new PlayerCollisionFilter();

            @Override
            public Response filter(Item item, Item other) {
                if (other.userData instanceof Entity) return Response.cross;
                else return null;
            }
        }
}