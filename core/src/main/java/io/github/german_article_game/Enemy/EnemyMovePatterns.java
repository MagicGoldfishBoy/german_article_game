package io.github.german_article_game.Enemy;

import com.badlogic.gdx.Gdx;
import com.dongbat.jbump.Item;

import io.github.german_article_game.Entity;
import io.github.german_article_game.Main;

public class EnemyMovePatterns {

    enum Direction {
        LEFT,
        RIGHT,
        UP,
        DOWN
    }

   private static Direction direction = Direction.RIGHT;

   public static float leftAndRight(Entity entity, Main game) {
        Item<Entity> item = entity.item;

        if (item.userData.x >= game.gameplayViewport.getMinWorldWidth() - item.userData.bboxWidth) {
            direction = Direction.LEFT;
        }
        if (item.userData.x <= game.gameplayViewport.getScreenX()) {
            direction = Direction.RIGHT;
        }

        switch (direction) {
            case LEFT:
                return 180f;
            case RIGHT:
                return 0f;
            default:
                return 0f;
        }
   }
    
}
