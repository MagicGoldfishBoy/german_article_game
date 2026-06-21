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
import io.github.orioncraftmc.meditate.enums.YogaEdge;
import io.github.orioncraftmc.meditate.enums.YogaFlexDirection;
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

    public MainMenu(Main game) {
        
        this.game = game;

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(game.stage);
        Gdx.input.setInputProcessor(multiplexer);

        CurrentSkin = game.CurrentSkin;

        atlas = new TextureAtlas("ui/uiskin.atlas");
            CurrentSkin.addRegions(atlas);


        mainMenuFlexbox = new FlexBox();
        mainMenuFlexbox.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        mainMenuFlexbox.getRoot()
            .setFlexDirection(YogaFlexDirection.ROW)
            .setWrap(YogaWrap.WRAP);
        game.stage.addActor(mainMenuFlexbox);

        TextButtonStyle defaultStyle = new TextButtonStyle();
            defaultStyle.font = CurrentSkin.getFont("default-font");
            defaultStyle.fontColor = Color.BROWN;
            defaultStyle.up = CurrentSkin.newDrawable("button-normal");
            defaultStyle.over = CurrentSkin.newDrawable("button-normal-over");
            defaultStyle.down = CurrentSkin.newDrawable("button-normal-pressed");


        playButton = new TextButton("Play", defaultStyle);
        playButton.getLabel().setFontScale(1.5f);

        playNode = mainMenuFlexbox.add(playButton)
            .setFlexDirection(YogaFlexDirection.ROW)
            .setBorder(YogaEdge.ALL, 25)
            .setMargin(YogaEdge.LEFT, 50)
            .setMargin(YogaEdge.TOP, 350)
            .setWidth(150)
            .setHeight(75);

        playButton.addListener(playListener);
        playButton.setTouchable(Touchable.enabled);


        settingsButton = new TextButton("Settings", defaultStyle);
        settingsButton.getLabel().setFontScale(1.5f);

        settingsNode = mainMenuFlexbox.add(settingsButton)
            .setFlexDirection(YogaFlexDirection.ROW)
            .setBorder(YogaEdge.ALL, 25)
            .setMargin(YogaEdge.LEFT, 50)
            .setMargin(YogaEdge.TOP, 350)
            .setWidth(150)
            .setHeight(75);

        settingsButton.addListener(settingsListener);
        settingsButton.setTouchable(Touchable.enabled);
        

        exitButton = new TextButton("Exit", defaultStyle);
        exitButton.getLabel().setFontScale(1.5f);

        exitNode = mainMenuFlexbox.add(exitButton)
            .setFlexDirection(YogaFlexDirection.ROW)
            .setBorder(YogaEdge.ALL, 25)
            .setMargin(YogaEdge.LEFT, 50)
            .setMargin(YogaEdge.TOP, 350)
            .setWidth(150)
            .setHeight(75);

        exitButton.addListener(exitListener);
        exitButton.setTouchable(Touchable.enabled);
    }

    ClickListener playListener = new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            System.out.println("Play button clicked");
        }
    };

    ClickListener settingsListener = new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            System.out.println("Settings button clicked");
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
    game.titleFont.draw(game.batch, "What Article Do I Use Again?", 2.5f, 4.25f);
    game.batch.end();

    game.stage.getViewport().apply();  // re-apply stage viewport before drawing
    game.stage.act(delta);
    game.stage.draw();
}

    @Override
    public void resize(int width, int height) {
        // If the window is minimized on a desktop (LWJGL3) platform, width and height are 0, which causes problems.
        // In that case, we don't resize anything, and wait for the window to be a normal size before updating.
        if(width <= 0 || height <= 0) return;

        // Resize your screen here. The parameters represent the new window size.
        game.stage.getViewport().update(width, height, true);        
        game.viewport.update(width, height, true);

    }

    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void hide() {
        // This method is called when another screen replaces this one.
    }

    @Override
    public void dispose() {
        game.stage.clear();
    }
}