package io.github.german_article_game.Enemy;

import com.dongbat.jbump.CollisionFilter;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.Response;

import io.github.german_article_game.Entity;
import io.github.german_article_game.Main;
import io.github.german_article_game.Bullet.Bullet;

public abstract class Enemy extends Entity {

    final Main game;

    public Bullet bulletType;

    public String germanName;

    public String englishName;

    public static float speed;

    Bullet bullet;

    public Float onStageTime;

    public Enemy (Main game, String germanName) {

        this.game = game;
        this.germanName = germanName;
        game.allEnemyList.add(this);
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

    public void act(float delta) {
        animationTime += delta;
        if (!game.isPaused) {
            onStageTime += delta;  
        }
        
    }

    @Override
    public String toString() {
        return this.germanName;
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
