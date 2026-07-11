package io.github.german_article_game.Enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.TimeUtils;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.Rect;
import com.dongbat.jbump.Response.Result;
import com.dongbat.jbump.util.MathUtils;

import io.github.german_article_game.Main;
import io.github.german_article_game.Bullet.Bullet;
import io.github.german_article_game.Bullet.BulletManager;
import io.github.german_article_game.Bullet.Enemy_Bullet;
import io.github.german_article_game.Bullet.Player_Bullet;
import io.github.german_article_game.Player.PlayerCollisionFilter;

public class EnemyTest extends Enemy {

    static TextureAtlas atlas = new TextureAtlas("animations/peopleskin.atlas");
    public static final Animation<AtlasRegion> enemyAnimation =
        new Animation<>(1.5f / 30f, atlas.findRegions("player-normal"), PlayMode.LOOP);

    final Main game;
    public EnemyMovePatterns movePatterns;

    public Bullet bulletType;

    public String germanName;

    public String englishName;

    public Float onStageTime; 

    public static final float bulletDelay = 0.15f;

    public float bulletTimer;

    BulletManager bulletManager;

    public EnemyTest(Main game) {
        super(game);
        this.game = game;                      
        this.movePatterns = new EnemyMovePatterns(this.game); 

        this.bulletManager = game.enemyBulletManager;
    }

    public void init(float x, float y, float deltaX, float deltaY) {
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
        onStageTime = 0f;
        if (item == null) {
            item = new Item<>(this);
        }
        game.world.add(item, x + bboxX, y + bboxY, bboxWidth, bboxHeight);
    }

    @SuppressWarnings("unused")
    @Override
    public void act(float delta) {
        Float direction = 0f;
        if (!game.world.hasItem(item)) {
            return;
        }
        if (!game.isPaused) {
            
        

        //direction = movePatterns.leftAndRight(this, game);
        //this.movePatterns.dispatch(this, game);
        //
        if (onStageTime < 5f) {
           this.movePatterns.oval(this, 150, 50, 10);      
        }
        if (onStageTime >= 5f) {
           this.movePatterns.circle(this, 100, 10); 
        }
             
        //EnemyMovePatterns.dispatch(this, game);
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

        if (bulletTimer > 0) {
            bulletTimer -= delta;
            if (bulletTimer <= 0f) {
                bulletTimer = bulletDelay;
                fireBullet();
            }
        }

        if (bulletTimer == 0) {
            fireBullet();
        }

        if (bulletTimer == 0.10f) {
            fireBullet();
        }



        animationTime += delta;
        onStageTime += delta;}
    }

    public void fireBullet() {
        // bulletTimer = bulletDelay;
        // Bullet bullet = new Enemy_Bullet(game);
        // bullet.init(x + bboxWidth / 2.5f, y + bboxHeight, 0f, -Enemy_Bullet.bulletSpeed);
        // game.entities.add(bullet);
        bulletTimer = bulletDelay;
        game.enemyBulletManager.spawnBullet(
        x + bboxWidth / 2.5f, y + bboxHeight, 0f, -Enemy_Bullet.bulletSpeed
    );
    }
    
}
