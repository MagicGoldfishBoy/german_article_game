package io.github.german_article_game.Bullet;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.dongbat.jbump.World;

import io.github.german_article_game.Main;

public class BulletManager {
    private final Array<Bullet> activeBullets = new Array<>();
    private final Main game;

    public BulletManager(Main game) {
        this.game = game;
    }

    private final Pool<Bullet> bulletPool = new Pool<Bullet>() {
        @Override
        protected Bullet newObject() {
            return new Bullet(game);
        }
    };

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
}
