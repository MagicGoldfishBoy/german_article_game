package io.github.german_article_game.Bullet;

import com.badlogic.gdx.Gdx;
import com.dongbat.jbump.Collision;
import com.dongbat.jbump.Rect;
import com.dongbat.jbump.Response.Result;

import io.github.german_article_game.Entity;
import io.github.german_article_game.Main;
import io.github.german_article_game.Player;
import io.github.german_article_game.Enemy.Enemy;
import io.github.german_article_game.Enemy.EnemyNoun;

public class NounBullet extends EnemyBullet {

    enum Gender {
        MALE,
        FEMALE,
        NEUTER
    }

    public Gender gender;

    public EnemyNoun shooter;

    public NounBullet(Main game) {
        super(game);
    }

    @Override
    public void act(float delta) {
        if (!alive || item == null) {
            return;
        }
        x += delta * deltaX;
        y += delta * deltaY;
        
        Result result = game.world.move(item, x + bboxX, y + bboxY, bulletCollisionFilter);

        for (int i = 0; i < result.projectedCollisions.size(); i++) {
            Collision collision = result.projectedCollisions.get(i);

    
            if (collision.other.userData instanceof Player) {

                Player player = (Player) collision.other.userData;
                if (this.gender.toString() == shooter.gender.toString()) {
                    player.heal(bulletStrength / 2);
                }
                else {
                    player.takeDamage(bulletStrength);   
                }
                
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