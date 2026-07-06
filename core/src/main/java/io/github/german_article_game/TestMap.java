package io.github.german_article_game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.SnapshotArray;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.World;

import io.github.german_article_game.Enemy.EnemyTest;
import io.github.german_article_game.Player.PlayerCollisionFilter;

public class TestMap implements Screen {
	public static SpriteBatch spriteBatch;
	public static TextureAtlas textureAtlas;
	public static OrthographicCamera camera;
	public static SnapshotArray<Entity> entities;
	public static World<Entity> world;
    public static ShapeRenderer shapeRenderer;
	public static final float ENEMY_DELAY = .5f;
	public float enemyTimer;
	public static final Vector2 vector2 = new Vector2();

    final Main game;
    public static Player player;
    public static EnemyTest enemy;

    public TestMap(Main game) {
        this.game = game;
    }

    @Override
    public void show() {
        entities = game.entities;
        world = game.world;
        shapeRenderer = game.shapeRenderer;
        player = new Player(game);
        enemy = new EnemyTest(game);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.ROYAL);

        Main.gameplayViewport.apply();
        //game.viewport.apply();
        Main.batch.setProjectionMatrix(game.viewport.getCamera().combined);

        player.act(delta);

        enemy.act(delta);

        Main.batch.begin();

        player.draw();

        if (enemy.hp > 0) {
            enemy.draw(); 
        } 
        
        
        for (Entity i : entities) {
            i.act(delta);
            i.draw();
        };

        Main.batch.end();

        if (Config.isDebugMode) {
            shapeRenderer.begin(ShapeType.Line);
            shapeRenderer.setColor(Color.BLACK);
            player.drawDebugHitbox(shapeRenderer, world);
            enemy.drawDebugHitbox(shapeRenderer, world);
            for (Entity i : entities) {
                i.drawDebugHitbox(shapeRenderer, world);
            };
            shapeRenderer.end();
        }

        game.stage.act(delta);
        game.stage.draw();
    }

    @Override
    public void resize(int width, int height) {

        if(width <= 0 || height <= 0) return;

        game.stage.getViewport().update(width, height, true);        
        game.viewport.update(width, height, true); 
        Main.gameplayViewport.update(width, height); 
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        game.stage.clear();
    }
    
}
