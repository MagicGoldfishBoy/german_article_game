package io.github.german_article_game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.SnapshotArray;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.dongbat.jbump.World;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {

	public static SpriteBatch batch;
	public BitmapFont font;
	public BitmapFont titleFont = FontGeneration.titleFont;
	public BitmapFont buttonFont = FontGeneration.buttonFont;
    public static FitViewport gameplayViewport;
    public StretchViewport viewport;
    FreeTypeFontGenerator generator;
    Skin CurrentSkin;
    Stage stage;
    World world;
    SnapshotArray entities;

    @Override
    public void create() {

        //viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        gameplayViewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        viewport = new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        CurrentSkin = new Skin(Gdx.files.internal("uiskin.json"));

        stage = new Stage(viewport);

		batch = new SpriteBatch();

		font = new BitmapFont();

        FontGeneration.createFontGeneration();

        world = new World<>();
        entities = new SnapshotArray<>();

		buttonFont = FontGeneration.buttonFont;
        titleFont = FontGeneration.titleFont;

		font.setUseIntegerPositions(false);
		font.getData().setScale(viewport.getWorldHeight() / Gdx.graphics.getHeight());

        titleFont.setUseIntegerPositions(false);
        titleFont.getData().setScale((viewport.getWorldHeight() / Gdx.graphics.getHeight()) / 4);

        buttonFont.setUseIntegerPositions(false);

        Config config = new Config();
        config.loadAllSettings();

        SaveDataManager.createSaveGameBin();
        //SaveDataManager.retrieveSaveFileNames();
        //SaveDataManager.appendSaveGameBin("bill");

        setScreen(new MainMenu(this));
    }

    public void render() {
		super.render();
	}

	public void dispose() {
		batch.dispose();
		font.dispose();
	}
}