package io.github.german_article_game.Bullet;

import java.util.function.Supplier;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

import io.github.german_article_game.Main;

public class BulletManager {
    private final Array<Bullet> activeBullets = new Array<>();
    private final Pool<Bullet> bulletPool;

    public BulletManager(Main game, Supplier<Bullet> bulletFactory) {
        this.bulletPool = new Pool<Bullet>() {
            @Override
            protected Bullet newObject() {
                return bulletFactory.get();
            }
        };
    }

    public void spawnBullet(float x, float y, float deltaX, float deltaY) {
        Bullet bullet = bulletPool.obtain();
        bullet.init(x, y, deltaX, deltaY);
        activeBullets.add(bullet);
    }

    public void updateAndRender(float delta, SpriteBatch batch) {
        for (int i = activeBullets.size - 1; i >= 0; i--) {
            Bullet bullet = activeBullets.get(i);
            bullet.act(delta);
            if (!bullet.alive) {
                activeBullets.removeIndex(i);
                bulletPool.free(bullet);
            } else {
                bullet.draw();
            }
        }
    }

    public void dispose() {
        activeBullets.clear();
        bulletPool.clear();
    }
}