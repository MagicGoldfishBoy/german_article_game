package io.github.german_article_game;

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

public class Bullet extends Entity {

    static TextureAtlas atlas = new TextureAtlas("animations/bullets.atlas");
    public static final Animation<AtlasRegion> bulletAnimation =
        new Animation<>(1.5f / 30f, atlas.findRegions("bullet-normal"), PlayMode.LOOP);

    public static final float BULLET_SPEED = 1000f;
    public static final BulletCollisionFilter BULLET_COLLISION_FILTER = new BulletCollisionFilter();

    final Main game;
    private float width;
    private float height;

    public Bullet(Main game) {
        this.game = game;
        this.animation = bulletAnimation;
        bboxX = 0;
        bboxY = 0;
        bboxWidth = bulletAnimation.getKeyFrames()[0].getRegionWidth();
        bboxHeight = bulletAnimation.getKeyFrames()[0].getRegionHeight();

        width = bulletAnimation.getKeyFrames()[0].getRegionWidth();
        height = bulletAnimation.getKeyFrames()[0].getRegionHeight();

        deltaX = 0f;
        deltaY = BULLET_SPEED;

        item = new Item<>(this);
        game.world.add(item, x + bboxX, y + bboxY, bboxWidth, bboxHeight);
    }

    @Override
    public void act(float delta) {
        x += delta * deltaX;
        y += delta * deltaY;

        Result result = game.world.move(item, x + bboxX, y + bboxY, BULLET_COLLISION_FILTER);

        for (int i = 0; i < result.projectedCollisions.size(); i++) {
            Collision collision = result.projectedCollisions.get(i);
            if (collision.other.userData instanceof Entity) {

                game.entities.removeValue(this, true);
                if (item != null) {
                    game.world.remove(item);
                    item = null;
                }
                return;
            }
        }

        Rect rect = game.world.getRect(item);
        if (rect != null) {
            x = rect.x - bboxX;
            y = rect.y - bboxY;
        }

        if (y > Gdx.graphics.getHeight() + height) {
            game.entities.removeValue(this, true);
            if (item != null) {
                game.world.remove(item);
                item = null;
            }
        }
    }

    public static class BulletCollisionFilter implements CollisionFilter {
        @Override
        public Response filter(Item item, Item other) {
            if (other.userData instanceof Entity) return Response.cross;
            else return null;
        }
    }
}