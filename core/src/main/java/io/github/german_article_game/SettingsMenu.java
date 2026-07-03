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
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox.CheckBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;

import dev.lyze.flexbox.FlexBox;
import io.github.orioncraftmc.meditate.YogaNode;
import io.github.orioncraftmc.meditate.enums.YogaAlign;
import io.github.orioncraftmc.meditate.enums.YogaDirection;
import io.github.orioncraftmc.meditate.enums.YogaEdge;
import io.github.orioncraftmc.meditate.enums.YogaFlexDirection;
import io.github.orioncraftmc.meditate.enums.YogaJustify;
import io.github.orioncraftmc.meditate.enums.YogaWrap;

public class SettingsMenu implements Screen {

    final Main game;

    Float buttonFontScale = Config.buttonFontScale;

    FlexBox settingsMenuFlexbox;
    FlexBox difficultyRow;
    FlexBox volumeRow; 

    TextureAtlas atlas;

    Skin CurrentSkin;

    YogaNode backNode;
    TextButton backButton;

    YogaNode includeEnglishTranslationCheckBoxNode;
    CheckBox includeEnglishTranslationCheckBox;

    YogaNode isDebugCheckBoxNode;
    CheckBox isDebugCheckBox;

    YogaNode difficultySelectLabelNode;
    Label difficultySelectLabel;

    YogaNode difficultySelectBoxNode;
    SelectBox<Config.Difficulty> difficultySelectBox;

    Label volumeLabel;

    YogaNode volumeSliderNode;
    Slider volumeSlider;


    public SettingsMenu(Main game) {
        this.game = game;

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(game.stage);
        Gdx.input.setInputProcessor(multiplexer);

        CurrentSkin = game.CurrentSkin;

        atlas = new TextureAtlas("ui/uiskin.atlas");
            CurrentSkin.addRegions(atlas);

        settingsMenuFlexbox = new FlexBox();
        settingsMenuFlexbox.setSize(game.stage.getWidth(), game.stage.getHeight());
        settingsMenuFlexbox.getRoot()
            .setMarginPercent(YogaEdge.LEFT, 2)
            .setFlexDirection(YogaFlexDirection.COLUMN)  
            .setWrap(YogaWrap.WRAP)
            .setAlignItems(YogaAlign.FLEX_START)              
            .setJustifyContent(YogaJustify.FLEX_START);   
        game.stage.addActor(settingsMenuFlexbox);


        FlexBox difficultyRow = new FlexBox();
        settingsMenuFlexbox.setSize(game.stage.getWidth(), game.stage.getHeight());
        difficultyRow.getRoot()
            .setFlexDirection(YogaFlexDirection.ROW)
            .setWrap(YogaWrap.WRAP)
            .setAlignItems(YogaAlign.CENTER)
            .setJustifyContent(YogaJustify.FLEX_START)
            .setMarginPercent(YogaEdge.TOP, 1.5f);

        FlexBox volumeRow = new FlexBox();
        settingsMenuFlexbox.setSize(game.stage.getWidth(), game.stage.getHeight());
        volumeRow.getRoot()
            .setFlexDirection(YogaFlexDirection.ROW)
            .setWrap(YogaWrap.WRAP)
            .setAlignItems(YogaAlign.CENTER)
            .setMarginPercent(YogaEdge.TOP, 1.5f);


        TextButtonStyle defaultStyle = new TextButtonStyle();
            defaultStyle.font = game.buttonFont;
            defaultStyle.fontColor = Color.WHITE;
            defaultStyle.up = CurrentSkin.newDrawable("button-normal");
            defaultStyle.over = CurrentSkin.newDrawable("button-normal-over");
            defaultStyle.down = CurrentSkin.newDrawable("button-normal-pressed");

        backButton = new TextButton("Back", defaultStyle);
        backButton.getLabel().setFontScale(buttonFontScale);

        settingsMenuFlexbox.add(backButton)
            .setWidthPercent(15)
            .setHeightPercent(10)
            .setMarginPercent(YogaEdge.VERTICAL, 2)
            .setMarginPercent(YogaEdge.HORIZONTAL, 2)
        ;

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
        includeEnglishTranslationCheckBox.getLabel().setFontScale(buttonFontScale);

            settingsMenuFlexbox.add(includeEnglishTranslationCheckBox)
            .setAlignContent(YogaAlign.CENTER)
            .setWidthPercent(5)
            .setHeightPercent(5)
            .setMarginPercent(YogaEdge.LEFT, 2.5f)
            .setMarginPercent(YogaEdge.BOTTOM, 2);

        includeEnglishTranslationCheckBox.addListener(includeEnglishListener);

        includeEnglishTranslationCheckBox.setChecked(Config.includeEnglishTranslation);


        isDebugCheckBox = new CheckBox("Debug Mode", defaultCheckBoxStyle);
        isDebugCheckBox.getLabel().setFontScale(buttonFontScale);
        
        settingsMenuFlexbox.add(isDebugCheckBox)
            .setAlignContent(YogaAlign.CENTER)
            .setWidthPercent(5)
            .setHeightPercent(5)
            .setMarginPercent(YogaEdge.LEFT, 2.5f)
            .setMarginPercent(YogaEdge.BOTTOM, 2);

        isDebugCheckBox.addListener(isDebugListener);
        isDebugCheckBox.setChecked(Config.isDebugMode);


        difficultySelectLabel = new Label("Difficulty:", new Label.LabelStyle(game.buttonFont, Color.SKY));
        difficultySelectLabel.getStyle().font.getData().setScale(buttonFontScale);

        difficultyRow.add(difficultySelectLabel)
            .setMarginPercent(YogaEdge.RIGHT, 2f)
            .setMarginPercent(YogaEdge.LEFT, 9.5f)
            .setMarginPercent(YogaEdge.BOTTOM, 5f);
        

        difficultySelectBox = new SelectBox<>(CurrentSkin);
            difficultySelectBox.setItems(Config.Difficulty.values());
            difficultySelectBox.setName("Difficulty");

        difficultyRow.add(difficultySelectBox)
            .setWidthPercent(25)
            .setHeightPercent(100);

        difficultySelectBox.setAlignment(1);

        difficultySelectBox.addListener(difficultySelectBoxChangeListener);
        difficultySelectBox.setSelected(Config.difficulty);


        volumeLabel = new Label("Volume:", new Label.LabelStyle(game.buttonFont, Color.SKY));
        volumeLabel.setFontScale(buttonFontScale);

        volumeSlider = new Slider(0f, 1f, 0.1f, false, CurrentSkin);

        volumeRow.add(volumeLabel)
            .setMarginPercent(YogaEdge.RIGHT, 2f)
            .setMarginPercent(YogaEdge.LEFT, 3.5f);

        volumeRow.add(volumeSlider)
            .setWidthPercent(50)
            .setHeightPercent(100);
        

        volumeSlider.addListener(volumeSliderChangeListener);
        volumeSlider.setValue(Config.volume);

        settingsMenuFlexbox.invalidate();
        settingsMenuFlexbox.layout();

        if (difficultySelectBox.getScrollPane() != null) {
            difficultySelectBox.getScrollPane().setWidth(difficultySelectBox.getWidth());
            difficultySelectBox.getScrollPane().invalidateHierarchy();
            difficultySelectBox.getScrollPane().invalidate();
        }

        settingsMenuFlexbox.add(difficultyRow)
            .setWidthPercent(40)
            .setHeightPercent(6);

        settingsMenuFlexbox.add(volumeRow)
            .setWidthPercent(100)
            .setHeightPercent(6);

        difficultyRow.invalidate();

        volumeRow.invalidate();
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
            config.createNewConfigFile();
        }
    };

    ChangeListener volumeSliderChangeListener = new ChangeListener() {
        @Override
        public void changed(ChangeEvent event, com.badlogic.gdx.scenes.scene2d.Actor actor) {
            float selectedVolume = volumeSlider.getValue();
            System.out.println("Selected volume: " + selectedVolume);
            Config config = new Config();
            Config.volume = selectedVolume;
            config.createNewConfigFile();
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
        settingsMenuFlexbox.clear();
    }
}
