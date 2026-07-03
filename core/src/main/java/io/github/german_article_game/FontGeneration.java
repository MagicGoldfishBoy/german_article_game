package io.github.german_article_game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class FontGeneration {

	public SpriteBatch batch;
	public BitmapFont font;
	public static BitmapFont titleFont;
	public static BitmapFont buttonFont;
	public FitViewport viewport;
    FreeTypeFontGenerator generator;
    

    public static void createFontGeneration() {
        
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("ui/BricolageGrotesque-VariableFont_opsz,wdth,wght.ttf"));
        FontGeneration.titleFont = generator.generateFont(new FreeTypeFontGenerator.FreeTypeFontParameter() {{
            size = 96;
        }});

        generator = new FreeTypeFontGenerator(Gdx.files.internal("ui/Geist-VariableFont_wght.ttf"));

        FontGeneration.buttonFont = generator.generateFont(new FreeTypeFontGenerator.FreeTypeFontParameter() {{
            size = 75;
        }});
    }
    
}
