package io.github.german_article_game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;

import dev.lyze.flexbox.FlexBox;
import io.github.orioncraftmc.meditate.YogaNode;
import io.github.orioncraftmc.meditate.enums.YogaAlign;
import io.github.orioncraftmc.meditate.enums.YogaEdge;
import io.github.orioncraftmc.meditate.enums.YogaFlexDirection;
import io.github.orioncraftmc.meditate.enums.YogaJustify;
import io.github.orioncraftmc.meditate.enums.YogaWrap;

/** First screen of the application. Displayed after the application is created. */
public class MainMenu implements Screen {

    final Main game;

    FlexBox mainMenuFlexbox;

    TextureAtlas atlas;

    Skin CurrentSkin;

    YogaNode playNode;
    TextButton playButton;

    YogaNode settingsNode;
    TextButton settingsButton;

    YogaNode exitNode;
    TextButton exitButton;

    Float buttonFontScale = Config.buttonFontScale;

    public MainMenu(Main game) {
        
        this.game = game;

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(game.stage);
        Gdx.input.setInputProcessor(multiplexer);

        CurrentSkin = game.CurrentSkin;

        atlas = new TextureAtlas("ui/uiskin.atlas");
            CurrentSkin.addRegions(atlas);


        mainMenuFlexbox = new FlexBox();
        mainMenuFlexbox.setSize(game.stage.getWidth(), game.stage.getHeight());
        mainMenuFlexbox.getRoot()
            .setFlexDirection(YogaFlexDirection.COLUMN)  
            .setWrap(YogaWrap.NO_WRAP)
            .setAlignItems(YogaAlign.CENTER)              
            .setJustifyContent(YogaJustify.CENTER);   
        game.stage.addActor(mainMenuFlexbox);

        TextButtonStyle defaultStyle = new TextButtonStyle();
            defaultStyle.font = game.buttonFont;
            defaultStyle.fontColor = Color.WHITE;
            defaultStyle.up = CurrentSkin.newDrawable("button-normal");
            defaultStyle.over = CurrentSkin.newDrawable("button-normal-over");
            defaultStyle.down = CurrentSkin.newDrawable("button-normal-pressed");


        playButton = new TextButton("Play", defaultStyle);
        playButton.getLabel().setFontScale(buttonFontScale);

        mainMenuFlexbox.add(playButton)
            .setWidthPercent(25)
            .setHeightPercent(10)
            .setMarginPercent(YogaEdge.VERTICAL, 2);

        playButton.addListener(playListener);
        playButton.setTouchable(Touchable.enabled);


        settingsButton = new TextButton("Settings", defaultStyle);
        settingsButton.getLabel().setFontScale(buttonFontScale);

        mainMenuFlexbox.add(settingsButton)
            .setWidthPercent(25)
            .setHeightPercent(10)
            .setMarginPercent(YogaEdge.BOTTOM, 2);

        settingsButton.addListener(settingsListener);
        settingsButton.setTouchable(Touchable.enabled);
                

        exitButton = new TextButton("Exit", defaultStyle);
        exitButton.getLabel().setFontScale(buttonFontScale);

        mainMenuFlexbox.add(exitButton)
            .setWidthPercent(25)
            .setHeightPercent(10)
            .setMarginPercent(YogaEdge.BOTTOM, 2);


        exitButton.addListener(exitListener);
        exitButton.setTouchable(Touchable.enabled);
    }

    ClickListener playListener = new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            dispose();
            game.setScreen(new SaveSelectMenu(game));
        }
    };

    ClickListener settingsListener = new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            dispose();
            game.setScreen(new SettingsMenu(game));
        }
    };

    ClickListener exitListener = new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            Gdx.app.exit();
        }
    };
    
    @Override
    public void show() {
        // Prepare your screen here.
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.ROYAL);

        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
        game.batch.begin();
        game.titleFont.draw(game.batch, "What Article Do I Use Again?", 175f, 400f);
        game.batch.end();

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
        // This screen can't be paused, but I had to put this here because the screen interface demands it
    }

    @Override
    public void resume() {
        // See above
    }

    @Override
    public void hide() {
        // This method is called when another screen replaces this one.
    }

    @Override
    public void dispose() {
        game.stage.clear();
        mainMenuFlexbox.clear();
        mainMenuFlexbox.remove();
    }
}