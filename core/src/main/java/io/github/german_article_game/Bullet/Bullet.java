package io.github.german_article_game.Bullet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.dongbat.jbump.Collision;
import com.dongbat.jbump.CollisionFilter;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.Rect;
import com.dongbat.jbump.Response;
import com.dongbat.jbump.Response.Result;

import io.github.german_article_game.Entity;
import io.github.german_article_game.Main;
import io.github.german_article_game.Enemy.Enemy;

public class Bullet extends Entity {

    static TextureAtlas atlas = new TextureAtlas("animations/bullets.atlas");
    public static final Animation<AtlasRegion> bulletAnimation =
        new Animation<>(1.5f / 30f, atlas.findRegions("bullet-normal"), PlayMode.LOOP);

    public static final float bulletSpeed = 1000f;
    public static final BulletCollisionFilter bulletCollissionFilter = new BulletCollisionFilter();
    public static Integer bulletStrength;

    final Main game;
    protected float width;
    protected float height;
    public boolean alive = false;

    public Bullet(Main game) {
        this.game = game;
        this.animation = bulletAnimation;
        bulletStrength = 10;
        bboxX = 0;
        bboxY = 0;
        bboxWidth = bulletAnimation.getKeyFrames()[0].getRegionWidth();
        bboxHeight = bulletAnimation.getKeyFrames()[0].getRegionHeight();

        width = bulletAnimation.getKeyFrames()[0].getRegionWidth();
        height = bulletAnimation.getKeyFrames()[0].getRegionHeight();

        deltaX = 0f;
        deltaY = bulletSpeed;
    }

    public void init(float x, float y, float deltaX, float deltaY) {
        this.x = x;
        this.y = y;
        this.alive = true;
        this.deltaX = deltaX;
        this.deltaY = deltaY;

        if (item == null) {
            item = new Item<>(this);
        }
        game.world.add(item, x + bboxX, y + bboxY, bboxWidth, bboxHeight);
    }

    public void onSpawn(BulletManager manager) {
        this.alive = true;
    }

    @Override
    public void act(float delta) {
        if (!alive || item == null) {
            return;
        }
        x += delta * deltaX;
        y += delta * deltaY;

        Result result = game.world.move(item, x + bboxX, y + bboxY, bulletCollissionFilter);

        for (int i = 0; i < result.projectedCollisions.size(); i++) {
            Collision collision = result.projectedCollisions.get(i);

            Enemy enemy = (Enemy) collision.other.userData;

            if (collision.other.userData instanceof Enemy) {
                enemy.takeDamage(bulletStrength);

                destroyBullet();

                return;
            }

            if (collision.other.userData instanceof Entity) {

                destroyBullet();

                return;
            }
        }

        Rect rect = game.world.getRect(item);
        if (rect != null) {
            x = rect.x - bboxX;
            y = rect.y - bboxY;
        }

        if (y > Gdx.graphics.getHeight() + height) {
            destroyBullet();
        }
    }

    protected void destroyBullet() {
        alive = false;
        if (item != null) {
            game.world.remove(item);
            item = null;
        }
    }

    public void reset() {
        this.x = 0;
        this.y = 0;
        this.deltaX = 0;
        this.deltaY = 0;
        this.alive = false;
    }

    public static class BulletCollisionFilter implements CollisionFilter {
        @Override
        public Response filter(Item item, Item other) {
            if (other.userData instanceof Enemy) return Response.cross;
            else return null;
        }
    }
}