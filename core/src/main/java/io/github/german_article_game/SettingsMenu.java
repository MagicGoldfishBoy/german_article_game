package io.github.german_article_game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox.CheckBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.ScreenUtils;

import dev.lyze.flexbox.FlexBox;
import io.github.orioncraftmc.meditate.YogaNode;
import io.github.orioncraftmc.meditate.enums.YogaAlign;
import io.github.orioncraftmc.meditate.enums.YogaEdge;
import io.github.orioncraftmc.meditate.enums.YogaFlexDirection;
import io.github.orioncraftmc.meditate.enums.YogaJustify;
import io.github.orioncraftmc.meditate.enums.YogaWrap;

public class SettingsMenu implements Screen {

    final Main game;

    FlexBox settingsMenuFlexbox;

    TextureAtlas atlas;

    Skin CurrentSkin;

    YogaNode backNode;
    TextButton backButton;

    YogaNode includeEnglishTranslationCheckBoxNode;
    CheckBox includeEnglishTranslationCheckBox;

    YogaNode isDebugCheckBoxNode;
    CheckBox isDebugCheckBox;

    YogaNode difficultySelectBoxNode;
    SelectBox<Config.Difficulty> difficultySelectBox;


    public SettingsMenu(Main game) {
        this.game = game;

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(game.stage);
        Gdx.input.setInputProcessor(multiplexer);

        CurrentSkin = game.CurrentSkin;

        atlas = new TextureAtlas("ui/uiskin.atlas");
            CurrentSkin.addRegions(atlas);

        settingsMenuFlexbox = new FlexBox();
        settingsMenuFlexbox.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        settingsMenuFlexbox.getRoot()
            .setFlexDirection(YogaFlexDirection.COLUMN)  
            .setWrap(YogaWrap.NO_WRAP)
            .setAlignItems(YogaAlign.FLEX_START)              
            .setJustifyContent(YogaJustify.FLEX_START);   
        game.stage.addActor(settingsMenuFlexbox);


        TextButtonStyle defaultStyle = new TextButtonStyle();
            defaultStyle.font = game.buttonFont;
            defaultStyle.fontColor = Color.WHITE;
            defaultStyle.up = CurrentSkin.newDrawable("button-normal");
            defaultStyle.over = CurrentSkin.newDrawable("button-normal-over");
            defaultStyle.down = CurrentSkin.newDrawable("button-normal-pressed");

        backButton = new TextButton("Back", defaultStyle);
        backButton.getLabel().setFontScale(1);

        backNode = settingsMenuFlexbox.add(backButton)
            .setWidthPercent(15)
            .setHeightPercent(10)
            .setMarginPercent(YogaEdge.BOTTOM, 2);

        backButton.addListener(backListener);
        backButton.setTouchable(Touchable.enabled);


        Drawable checkOff = CurrentSkin.newDrawable("checkbox");
        checkOff.setMinWidth(32);
        checkOff.setMinHeight(32);

        Drawable checkOn = CurrentSkin.newDrawable("checkbox-selected");
        checkOn.setMinWidth(32);
        checkOn.setMinHeight(32);

        CheckBoxStyle defaultCheckBoxStyle = new CheckBoxStyle();
            defaultCheckBoxStyle.font = game.buttonFont;
            defaultCheckBoxStyle.fontColor = Color.SKY;
            defaultCheckBoxStyle.checkboxOff = CurrentSkin.newDrawable(checkOff);
            defaultCheckBoxStyle.checkboxOn = CurrentSkin.newDrawable(checkOn);


        includeEnglishTranslationCheckBox = new CheckBox("Include English Translation", defaultCheckBoxStyle);
        includeEnglishTranslationCheckBox.getLabel().setFontScale(0.75f);

        includeEnglishTranslationCheckBoxNode = settingsMenuFlexbox.add(includeEnglishTranslationCheckBox)
            .setAlignContent(YogaAlign.CENTER)
            .setWidthPercent(5)
            .setHeightPercent(5)
            .setMarginPercent(YogaEdge.LEFT, 2)
            .setMarginPercent(YogaEdge.BOTTOM, 2);

        includeEnglishTranslationCheckBox.addListener(includeEnglishListener);

        includeEnglishTranslationCheckBox.setChecked(Config.includeEnglishTranslation);


        isDebugCheckBox = new CheckBox("Debug Mode", defaultCheckBoxStyle);
        isDebugCheckBox.getLabel().setFontScale(0.75f);
        
        isDebugCheckBoxNode = settingsMenuFlexbox.add(isDebugCheckBox)
            .setAlignContent(YogaAlign.CENTER)
            .setWidthPercent(5)
            .setHeightPercent(5)
            .setMarginPercent(YogaEdge.LEFT, 2)
            .setMarginPercent(YogaEdge.BOTTOM, 2);

        isDebugCheckBox.addListener(isDebugListener);
        isDebugCheckBox.setChecked(Config.isDebugMode);


        difficultySelectBox = new SelectBox<>(CurrentSkin);
        difficultySelectBox.setItems(Config.Difficulty.values());
        
        
        difficultySelectBoxNode = settingsMenuFlexbox.add(difficultySelectBox)
            .setAlignContent(YogaAlign.CENTER)
            .setWidthPercent(5)
            .setHeightPercent(5)
            .setMarginPercent(YogaEdge.LEFT, 2)
            .setMarginPercent(YogaEdge.BOTTOM, 2);

        difficultySelectBox.addListener(difficultySelectBoxChangeListener);
        difficultySelectBox.setSelected(Config.difficulty);
    }

    ClickListener backListener = new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            dispose();
            game.setScreen(new MainMenu(game));
        }
    };
    ClickListener includeEnglishListener = new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            Config.includeEnglishTranslation = !Config.includeEnglishTranslation;
            includeEnglishTranslationCheckBox.setChecked(Config.includeEnglishTranslation);
            Config config = new Config();
            config.createNewConfigFile();
        }
    };
    ClickListener isDebugListener = new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            Config.isDebugMode = !Config.isDebugMode;
            isDebugCheckBox.setChecked(Config.isDebugMode);
            Config config = new Config();
            config.createNewConfigFile();
        }
    };
    ChangeListener difficultySelectBoxChangeListener = new ChangeListener() {
        @Override
        public void changed(ChangeEvent event, com.badlogic.gdx.scenes.scene2d.Actor actor) {
            Config.Difficulty selectedDifficulty = difficultySelectBox.getSelected();
            System.out.println("Selected from box: " + selectedDifficulty);
            Config config = new Config();
            Config.difficulty = selectedDifficulty;
            difficultySelectBox.setSelected(Config.difficulty);
            config.createNewConfigFile();
        }
    };

    private void updateCheckBoxStyle(int width, int height) {
        float size = Math.min(width, height) * 0.04f; // 4% of the smaller dimension

        Drawable checkOff = CurrentSkin.newDrawable("checkbox");
        checkOff.setMinWidth(size);
        checkOff.setMinHeight(size);

        Drawable checkOn = CurrentSkin.newDrawable("checkbox-selected");
        checkOn.setMinWidth(size);
        checkOn.setMinHeight(size);

        CheckBoxStyle style = includeEnglishTranslationCheckBox.getStyle();
        style.checkboxOff = checkOff;
        style.checkboxOn = checkOn;

        includeEnglishTranslationCheckBox.setStyle(style); // forces layout refresh
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.ROYAL);

        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
        game.batch.begin();
        //game.titleFont.draw(game.batch, "What Article Do I Use Again?", 2.5f, 4.25f);
        game.batch.end();

        game.stage.getViewport().apply();  // re-apply stage viewport before drawing
        game.stage.act(delta);
        game.stage.draw();
    }

    @Override
    public void resize(int width, int height) {

        if(width <= 0 || height <= 0) return;

        backButton.getLabel().setFontScale(width * 0.0005f, height * 0.0005f);

        includeEnglishTranslationCheckBox.getLabel().setFontScale(width * 0.0004f, height * 0.0004f);

        isDebugCheckBox.getLabel().setFontScale(width * 0.0004f, height * 0.0004f);


        updateCheckBoxStyle(width, height);

        game.stage.getViewport().update(width, height, true);        
        game.viewport.update(width, height, true);

        settingsMenuFlexbox.setSize(width, height);
        settingsMenuFlexbox.invalidate();
        settingsMenuFlexbox.layout();
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
        settingsMenuFlexbox.clear();
    }
}
