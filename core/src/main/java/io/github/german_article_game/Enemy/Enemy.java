package io.github.german_article_game.Enemy;

import com.dongbat.jbump.CollisionFilter;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.Response;

import io.github.german_article_game.Entity;
import io.github.german_article_game.Main;
import io.github.german_article_game.Player;
import io.github.german_article_game.Bullet.Bullet;
import io.github.german_article_game.Player.PlayerCollisionFilter;

public abstract class Enemy extends Entity {

    final Main game;

    public Bullet bulletType;

    public String germanName;

    public String englishName;

    public static float speed
    ;
    Bullet bullet;

    public Enemy (Main game) {

        this.game = game;
    }

    public void takeDamage(Integer damage) {
        System.out.println(this.hp);
        if (this.hp - damage > 0) {
            this.hp -= damage;
        }
        else {
            this.die();
        }
        System.out.println(this.hp);
    }

    public void die () {
        game.entities.removeValue(this, true);
        if (item != null) {
            this.animation = null;
            game.world.remove(item);
            item = null;
        }
    }

    public static class enemyCollisionFilter implements CollisionFilter {
            public static final enemyCollisionFilter instance = new enemyCollisionFilter();

            @Override
            public Response filter(Item item, Item other) {
                if (other.userData instanceof Bullet) return Response.cross;
                else return null;
            }
    }
    
}
