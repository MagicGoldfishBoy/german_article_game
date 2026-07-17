package io.github.german_article_game.Bullet;

import io.github.german_article_game.Main;

public class PlayerSpreadingBullet extends PlayerBullet {

    public PlayerSpreadingBullet(Main game) {
        super(game);
    }

    @Override
    public void onSpawn(BulletManager manager) {
        Bullet bullet = manager.bulletPool.obtain();

        bullet.init(x, y, deltaX, deltaY);
        Bullet bullet_2 = manager.bulletPool.obtain();
        bullet_2.init(x, y, deltaX + 100, deltaY);
        Bullet bullet_3 = manager.bulletPool.obtain();
        bullet_3.init(x, y, deltaX - 100, deltaY);
        manager.activeBullets.add(bullet_2);
        manager.activeBullets.add(bullet_3);
    }
    
}
