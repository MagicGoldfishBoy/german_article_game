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
    
    public static final BulletCollisionFilter BULLET_COLLISION_FILTER = new BulletCollisionFilter();

    public Bullet(Main game) {
        this.game = game;
        this.animation = bulletAnimation;
        bboxX = 15;
        bboxY = 15;
        bboxWidth = 100;
        bboxHeight = 100;
        item = new Item<>(this);
        x = Gdx.graphics.getWidth() / 2;
        y = Gdx.graphics.getHeight() / 2;
        game.world.add(item, x + bboxX, y + bboxY, bboxWidth, bboxHeight);
    }

    @Override
    public void act(float delta) {

        x += delta * deltaX;
        y += delta * deltaY;
        
        //handle collisions

        Result result = game.world.move(item, x, y, BULLET_COLLISION_FILTER);   

        for (int i = 0; i < result.projectedCollisions.size(); i++) {
            Collision collision = result.projectedCollisions.get(i);
            if (collision.other.userData instanceof Entity) {

                game.entities.removeValue(this, true);
                if (item != null) {
                    game.world.remove(item);
                    item = null;
                }
                
                // Entity enemy = (Entity) collision.other.userData;
                // if (!enemy.isDying()) {
                //     //enemy is not dead yet: kill it
                //     enemy.die();
                //     hurtSound.play();
                // } else {
                //     //push the enemy
                //     enemy.deltaX += deltaX * BULLET_PUSH_MAGNITUDE;
                //     enemy.deltaY += deltaY * BULLET_PUSH_MAGNITUDE;
                // }
            }
        }
        
        //update position based on collisions
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

