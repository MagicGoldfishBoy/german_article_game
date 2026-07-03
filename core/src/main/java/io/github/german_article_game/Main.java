package io.github.german_article_game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {

	public SpriteBatch batch;
	public BitmapFont font;
	public BitmapFont titleFont;
	public BitmapFont buttonFont;
	public FitViewport viewport;
    FreeTypeFontGenerator generator;
    Skin CurrentSkin;
    Stage stage;
    

    @Override
    public void create() {

        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        CurrentSkin = new Skin(Gdx.files.internal("uiskin.json"));

        stage = new Stage(viewport);

		batch = new SpriteBatch();

		font = new BitmapFont();

        
        generator = new FreeTypeFontGenerator(Gdx.files.internal("ui/BricolageGrotesque-VariableFont_opsz,wdth,wght.ttf"));
        titleFont = generator.generateFont(new FreeTypeFontGenerator.FreeTypeFontParameter() {{
            size = 96;
        }});

        generator = new FreeTypeFontGenerator(Gdx.files.internal("ui/Geist-VariableFont_wght.ttf"));

        buttonFont = generator.generateFont(new FreeTypeFontGenerator.FreeTypeFontParameter() {{
            size = 75;
        }});
		
		font.setUseIntegerPositions(false);
		font.getData().setScale(viewport.getWorldHeight() / Gdx.graphics.getHeight());

        titleFont.setUseIntegerPositions(false);
        titleFont.getData().setScale((viewport.getWorldHeight() / Gdx.graphics.getHeight()) / 4);

        buttonFont.setUseIntegerPositions(false);

        Config config = new Config();
        config.loadAllSettings();

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