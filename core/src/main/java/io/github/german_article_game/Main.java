package io.github.german_article_game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.viewport.FitViewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {

	public SpriteBatch batch;
	public BitmapFont font;
	public BitmapFont titleFont;
	public FitViewport viewport;
    FreeTypeFontGenerator generator;
    @Override
    public void create() {

		batch = new SpriteBatch();

		font = new BitmapFont();
        generator = new FreeTypeFontGenerator(Gdx.files.internal("ui/BricolageGrotesque-VariableFont_opsz,wdth,wght.ttf"));
        titleFont = generator.generateFont(new FreeTypeFontGenerator.FreeTypeFontParameter() {{
            size = 24;
        }});

		viewport = new FitViewport(8, 5);
		
		font.setUseIntegerPositions(false);
		font.getData().setScale(viewport.getWorldHeight() / Gdx.graphics.getHeight());

        titleFont.setUseIntegerPositions(false);
        titleFont.getData().setScale(viewport.getWorldHeight() / Gdx.graphics.getHeight());

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