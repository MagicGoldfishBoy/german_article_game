package io.github.german_article_game.Enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.Rect;
import com.dongbat.jbump.Response.Result;
import com.dongbat.jbump.util.MathUtils;

import io.github.german_article_game.Bullet;
import io.github.german_article_game.Main;
import io.github.german_article_game.Player.PlayerCollisionFilter;

public class EnemyTest extends Enemy {

    static TextureAtlas atlas = new TextureAtlas("animations/peopleskin.atlas");
    public static final Animation<AtlasRegion> enemyAnimation =
        new Animation<>(1.5f / 30f, atlas.findRegions("player-normal"), PlayMode.LOOP);

    final Main game;

    public Bullet bulletType;

    public String germanName;

    public String englishName;

    public EnemyTest(Main game) {
        super(game);

        this.game = game;
        this.germanName = "Feind";
        this.englishName = "Enemy";
        this.animation = enemyAnimation;
        this.x = 100;
        this.y = 300;
        this.bboxWidth = 25;
        this.bboxHeight = 45;
        this.bboxX = 0;
        this.bboxY = 0;
        this.hp = 100;
        EnemyTest.speed = 100f;
        item = new Item<>(this);
        game.world.add(item, x + bboxX, y + bboxY, bboxWidth, bboxHeight);

    }

    @SuppressWarnings("unused")
    @Override
    public void act(float delta) {
        Float direction = 0f;
        if (!game.world.hasItem(item)) {
            return;
        }

       // direction = EnemyMovePatterns.leftAndRight(this, game);
        // EnemyMovePatterns.testPattern(this);
        // EnemyMovePatterns.circle(this, 100, 10);
        EnemyMovePatterns.oval(this, 100, 50, 10);        
        if (direction != null) {
            x += MathUtils.cosDeg(direction) * speed * delta;
            y += MathUtils.sinDeg(direction) * speed * delta;
        }
        Result result = game.world.move(item, x + bboxX, y + bboxY, enemyCollisionFilter.instance);
        Rect rect = game.world.getRect(item);
        if (rect != null) {
            x = rect.x - bboxX;
            y = rect.y - bboxY;
        }


        animationTime += delta;
    }
    
}
