package io.github.german_article_game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.dongbat.jbump.Collision;
import com.dongbat.jbump.CollisionFilter;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.Rect;
import com.dongbat.jbump.Response;
import com.dongbat.jbump.Response.Result;
import com.dongbat.jbump.util.MathUtils;

public class Bullet extends Entity {

  static TextureAtlas atlas = new TextureAtlas("animations/bullets.atlas");
    public static final Animation<AtlasRegion> bulletAnimation =
        new Animation<>(1.5f / 30f, atlas.findRegions("bullet-normal"), PlayMode.LOOP);
        
    public Float direction;
    public static final float BULLET_START_DISTANCE = 120f;
    public static final float BULLET_SPEED = 1200f;
    public static final float BULLET_DELAY = .025f;
    public float bulletTimer;
    public static final float SPEED = 200f;
    final Main game;
    public static final Vector2 vector2 = new Vector2();
    public static final Vector3 vector3 = new Vector3();
        private float width;
        private float height;
    
    public static final BulletCollisionFilter BULLET_COLLISION_FILTER = new BulletCollisionFilter();

    public Bullet(Main game) {
        this.game = game;
        this.animation = bulletAnimation;
        bboxX = 15;
        bboxY = 15;
        bboxWidth = 100;
        bboxHeight = 100;
        item = new Item<>(this);
        width = bulletAnimation.getKeyFrames()[0].getRegionWidth();
        height = bulletAnimation.getKeyFrames()[0].getRegionHeight();
        // x = Gdx.graphics.getWidth() / 2;
        // y = Gdx.graphics.getHeight() / 2;
        game.world.add(item, x + bboxX, y + bboxY, bboxWidth, bboxHeight);
    }

@Override
public void act(float delta) {
    x += delta * deltaX;
    y += delta * deltaY;

    // move THIS bullet's own item, not a new one
    Result result = game.world.move(item, x + bboxX, y + bboxY, BULLET_COLLISION_FILTER);

    vector3.set(Gdx.input.getX(), Gdx.input.getY(), 0);
    vector2.set(vector3.x, vector3.y);
    vector2.sub(x + width / 2, y + height / 2);

    for (int i = 0; i < result.projectedCollisions.size(); i++) {
        Collision collision = result.projectedCollisions.get(i);
        if (collision.other.userData instanceof Entity) {

            game.entities.removeValue(this, true);
            if (item != null) {
                game.world.remove(item);
                item = null;
            }
            bulletTimer = BULLET_DELAY;

            // NOW create the bounce-back bullet, fresh, after removing this one
            Bullet bounced = new Bullet(game);

            vector2.set(BULLET_START_DISTANCE, 0);
            bounced.x = x + width / 2 - bounced.bboxWidth / 2 + vector2.x;
            bounced.y = y + height / 2 - bounced.bboxHeight / 2 + vector2.y;

            vector2.set(BULLET_SPEED, 0);
            bounced.deltaX = vector2.x;
            bounced.deltaY = vector2.y;

            game.entities.add(bounced);
            game.world.update(bounced.item, bounced.x + bounced.bboxX, bounced.y + bounced.bboxY);

            break; // this bullet is gone, stop processing its other collisions
        }
    }

    Rect rect = game.world.getRect(item);
    if (rect != null) {
        x = rect.x;
        y = rect.y;
    }

        
        //if outside view
        // if (x < camera.position.x - camera.viewportWidth / 2 || x > camera.position.x + camera.viewportWidth / 2 ||
        //         y < camera.position.y - camera.viewportHeight / 2 || y > camera.position.y + camera.viewportHeight / 2) {
        //     //destroy the entity
        //     entities.removeValue(this, true);
        //     if (item != null) {
        //         world.remove(item);
        //         item = null;
        //     }
        // }
    }

    
    public static class BulletCollisionFilter implements CollisionFilter {
        @Override
        public Response filter(Item item, Item other) {
            if (other.userData instanceof Entity) return Response.cross;
            else return null;
        }
    }

}

