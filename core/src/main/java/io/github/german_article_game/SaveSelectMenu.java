package io.github.german_article_game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;

import dev.lyze.flexbox.FlexBox;
import io.github.orioncraftmc.meditate.enums.YogaAlign;
import io.github.orioncraftmc.meditate.enums.YogaEdge;
import io.github.orioncraftmc.meditate.enums.YogaFlexDirection;
import io.github.orioncraftmc.meditate.enums.YogaJustify;
import io.github.orioncraftmc.meditate.enums.YogaWrap;

public class SaveSelectMenu implements Screen {

    final Main game;

    FlexBox saveSelectFlexbox;

    TextureAtlas atlas;

    Skin CurrentSkin;

    TextButton backButton;

    public SaveSelectMenu(Main game) {
        this.game = game;

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(game.stage);
        Gdx.input.setInputProcessor(multiplexer);

        CurrentSkin = game.CurrentSkin;

        atlas = new TextureAtlas("ui/uiskin.atlas");
            CurrentSkin.addRegions(atlas);

        saveSelectFlexbox = new FlexBox();
        saveSelectFlexbox.setSize(game.stage.getWidth(), game.stage.getHeight());
        saveSelectFlexbox.getRoot()
            .setMarginPercent(YogaEdge.LEFT, 2)
            .setFlexDirection(YogaFlexDirection.COLUMN)  
            .setWrap(YogaWrap.WRAP)
            .setAlignItems(YogaAlign.FLEX_START)              
            .setJustifyContent(YogaJustify.FLEX_START);  
        game.stage.addActor(saveSelectFlexbox);

        TextButtonStyle defaultStyle = new TextButtonStyle();
            defaultStyle.font = game.buttonFont;
            defaultStyle.fontColor = Color.WHITE;
            defaultStyle.up = CurrentSkin.newDrawable("button-normal");
            defaultStyle.over = CurrentSkin.newDrawable("button-normal-over");
            defaultStyle.down = CurrentSkin.newDrawable("button-normal-pressed");

        backButton = new TextButton("Back", defaultStyle);
        backButton.getLabel().setFontScale(Config.buttonFontScale);

        saveSelectFlexbox.add(backButton)
            .setWidthPercent(25)
            .setHeightPercent(10)
            .setMarginPercent(YogaEdge.BOTTOM, 2);

        backButton.addListener(backListener);
    }

    ClickListener backListener = new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            dispose();
            game.setScreen(new MainMenu(game));
        }
    };

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.ROYAL);

        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
        game.batch.begin();
        game.batch.end();

        game.stage.getViewport().apply();
        game.stage.act(delta);
        game.stage.draw();

    }

    @Override
    public void resize(int width, int height) {

        if(width <= 0 || height <= 0) return;

        game.stage.getViewport().update(width, height, true);       

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
        saveSelectFlexbox.clear();
    }
    
}
