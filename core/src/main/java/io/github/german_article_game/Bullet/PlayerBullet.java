package io.github.german_article_game.Bullet;

import com.badlogic.gdx.Gdx;
import com.dongbat.jbump.Collision;
import com.dongbat.jbump.Rect;
import com.dongbat.jbump.Response.Result;

import io.github.german_article_game.Entity;
import io.github.german_article_game.Main;
import io.github.german_article_game.Enemy.Enemy;

public class PlayerBullet extends Bullet {

    private float width;
    private float height;

    public PlayerBullet(Main game) {
        
        super(game);

        bboxWidth = bulletAnimation.getKeyFrames()[0].getRegionWidth();
        bboxHeight = bulletAnimation.getKeyFrames()[0].getRegionHeight();

        width = bulletAnimation.getKeyFrames()[0].getRegionWidth();
        height = bulletAnimation.getKeyFrames()[0].getRegionHeight();
    }

    @Override
    public void act(float delta) {
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
        if (y < 0) {
            destroyBullet();
        }
        if (x > Gdx.graphics.getWidth() + width) {
            destroyBullet();
        }
        if (x < 0) {
            destroyBullet();
        }
    }
    
}
