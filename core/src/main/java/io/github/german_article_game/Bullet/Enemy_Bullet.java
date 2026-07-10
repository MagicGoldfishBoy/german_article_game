package io.github.german_article_game.Bullet;

import com.badlogic.gdx.Gdx;
import com.dongbat.jbump.Collision;
import com.dongbat.jbump.CollisionFilter;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.Rect;
import com.dongbat.jbump.Response;
import com.dongbat.jbump.Response.Result;

import io.github.german_article_game.Entity;
import io.github.german_article_game.Main;
import io.github.german_article_game.Player;
import io.github.german_article_game.Enemy.Enemy;

public class Enemy_Bullet extends Bullet {

    private float width;
    private float height;

    public static final float bulletSpeed = 100f;

    public static final EnemyBulletCollisionFilter bulletCollisionFilter = new EnemyBulletCollisionFilter();

    public Enemy_Bullet(Main game) {
        super(game);
        deltaY = -bulletSpeed;
    }

    @Override
    public void act(float delta) {
        x += delta * deltaX;
        y += delta * deltaY;

        //Result result = game.world.move(item, x + bboxX, -(y + bboxY), BULLET_COLLISION_FILTER); <-Not intended behaviour, but worth exploring later
        
        Result result = game.world.move(item, x + bboxX, y + bboxY, bulletCollisionFilter);

        for (int i = 0; i < result.projectedCollisions.size(); i++) {
            Collision collision = result.projectedCollisions.get(i);

            

            if (collision.other.userData instanceof Player) {

                Player player = (Player) collision.other.userData;
                player.takeDamage(bulletStrength);

                game.entities.removeValue(this, true);
                if (item != null) {

                    destroyBullet();
                }

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
        if (y < 0) {
            destroyBullet();
        }
        if (x > Gdx.graphics.getWidth() + width) {
            destroyBullet();
            System.out.println("bullet Destroyed");
        }
        if (x < 0) {
            destroyBullet();
        }
    }

    public static class EnemyBulletCollisionFilter implements CollisionFilter {
        @Override
        public Response filter(Item item, Item other) {
            if (other.userData instanceof Player) return Response.cross;
            else return null;
        }
    }

    private void destroyBullet() {
        game.entities.removeValue(this, true);
        if (item != null) {
            game.world.remove(item);
            item = null;
        }
    }
    
}
