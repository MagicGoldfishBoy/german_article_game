package io.github.german_article_game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.SnapshotArray;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.World;

public class WorldMap implements Screen {
	public static SpriteBatch spriteBatch;
	public static TextureAtlas textureAtlas;
	public static ExtendViewport viewport;
	public static OrthographicCamera camera;
	public static SnapshotArray<Entity> entities;
	public static World<Entity> world;
	public static final float ENEMY_DELAY = .5f;
	public float enemyTimer;
	public static final Vector2 vector2 = new Vector2();

    final Main game;
    public static Player player;

    public WorldMap(Main game) {
        this.game = game;
    }

    @Override
    public void show() {
        entities = new SnapshotArray<>();
        world = new World<>();
        player = new Player();
        world.add(new Item<Entity>(player), 0, 0, 10, 10);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.ROYAL);

        game.viewport.apply();
        Main.batch.setProjectionMatrix(game.viewport.getCamera().combined);

        player.act(delta);

        Main.batch.begin();
        player.draw();
        Main.batch.end();

        game.stage.getViewport().apply();
        game.stage.act(delta);
        game.stage.draw();
    }

    @Override
    public void resize(int width, int height) {

        if(width <= 0 || height <= 0) return;

        game.stage.getViewport().update(width, height, true);        
        game.viewport.update(width, height, true);  
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
