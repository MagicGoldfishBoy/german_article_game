package io.github.german_article_game.Enemy;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.Rect;
import com.dongbat.jbump.Response.Result;

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
        this.y = 100;
        this.bboxWidth = 25;
        this.bboxHeight = 45;
        this.bboxX = 0;
        this.bboxY = 0;
        this.hp = 100;
        item = new Item<>(this);
        game.world.add(item, x + bboxX, y + bboxY, bboxWidth, bboxHeight);

    }

    @Override
    public void act(float delta) {
        if (!game.world.hasItem(item)) {
            return;
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
