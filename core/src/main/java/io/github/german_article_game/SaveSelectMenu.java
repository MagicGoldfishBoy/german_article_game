package io.github.german_article_game;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;

import dev.lyze.flexbox.FlexBox;
import io.github.orioncraftmc.meditate.enums.YogaAlign;
import io.github.orioncraftmc.meditate.enums.YogaEdge;
import io.github.orioncraftmc.meditate.enums.YogaFlexDirection;
import io.github.orioncraftmc.meditate.enums.YogaJustify;
import io.github.orioncraftmc.meditate.enums.YogaOverflow;
import io.github.orioncraftmc.meditate.enums.YogaPositionType;
import io.github.orioncraftmc.meditate.enums.YogaWrap;

public class SaveSelectMenu implements Screen {

    final Main game;

    FlexBox saveSelectFlexbox;

    FlexBox optionColumnFlexbox;

    TextureAtlas atlas;

    Skin CurrentSkin;

    TextButtonStyle TextButtonStyle;

    TextButton backButton;

    TextButton newGameButton;

    List<String> saveList = SaveDataManager.retrieveSaveFileNames();
    SelectBox<String> saveSelectBox;

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
        saveSelectFlexbox.setFillParent(true); 
        saveSelectFlexbox.getRoot()
            .setPositionType(YogaPositionType.ABSOLUTE)
            .setMarginPercent(YogaEdge.LEFT, 40)
            .setMarginPercent(YogaEdge.TOP, 10)
            .setFlexDirection(YogaFlexDirection.ROW)  
            .setWrap(YogaWrap.WRAP)
            .setAlignItems(YogaAlign.FLEX_START)              
            .setJustifyContent(YogaJustify.FLEX_START);  
        game.stage.addActor(saveSelectFlexbox);

        optionColumnFlexbox = new FlexBox();
        optionColumnFlexbox.setFillParent(true);
        optionColumnFlexbox.getRoot()
            .setPositionType(YogaPositionType.ABSOLUTE)
            .setOverflow(YogaOverflow.SCROLL)
            .setWidthPercent(100)
            .setFlexDirection(YogaFlexDirection.COLUMN)
            .setWrap(YogaWrap.NO_WRAP)
            .setAlignContent(YogaAlign.STRETCH)
            .setAlignItems(YogaAlign.CENTER)
            .setPositionPercent(YogaEdge.RIGHT, 50)
            .setMarginPercent(YogaEdge.TOP, 2);
        game.stage.addActor(optionColumnFlexbox);

        TextButtonStyle defaultStyle = new StyleCreation(CurrentSkin).buttonStyle;

        backButton = new TextButton("Back", defaultStyle);
        backButton.getLabel().setFontScale(Config.buttonFontScale);

        optionColumnFlexbox.add(backButton)
            .setWidthPercent(StyleCreation.sizeTextButton(backButton))
            .setHeightPercent(10)
            .setMarginPercent(YogaEdge.BOTTOM, 2);

        backButton.addListener(backListener);


        newGameButton = new TextButton("New Game", defaultStyle);
        newGameButton.getLabel().setFontScale(Config.buttonFontScale);

        optionColumnFlexbox.add(newGameButton)
            .setWidthPercent(StyleCreation.sizeTextButton(newGameButton))
            .setHeight(10)
            .setMarginPercent(YogaEdge.BOTTOM, 2);

        newGameButton.addListener(newGameClickListener);

        for (String i : saveList) {
            Table table = new Table();
            Label name_label = new Label(i, CurrentSkin);
            TextButton button = new TextButton("Load", CurrentSkin);
            button.setWidth(10);
            table.add(name_label);
            table.row();
            table.add(button);
            table.padRight(25);
            table.padBottom(25);
            table.setColor(Color.WHITE);
            saveSelectFlexbox.add(table);  
        }
    }

    ClickListener backListener = new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            dispose();
            game.setScreen(new MainMenu(game));
        }
    };

    ClickListener newGameClickListener = new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            System.out.println("New Game creation coming soon!");
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
        saveSelectFlexbox.layout();    
        optionColumnFlexbox.layout();
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
        optionColumnFlexbox.clear();
    }
    
}
