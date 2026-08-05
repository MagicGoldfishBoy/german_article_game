package io.github.german_article_game;

import java.util.List;
import java.util.stream.Stream;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

import dev.lyze.flexbox.FlexBox;
import io.github.german_article_game.Enemy.Enemy;
import io.github.german_article_game.Enemy.EnemyNoun;
import io.github.german_article_game.Enemy.EnemyTest;
import io.github.german_article_game.Enemy.Katze;
import io.github.orioncraftmc.meditate.enums.YogaAlign;
import io.github.orioncraftmc.meditate.enums.YogaEdge;
import io.github.orioncraftmc.meditate.enums.YogaFlexDirection;
import io.github.orioncraftmc.meditate.enums.YogaJustify;
import io.github.orioncraftmc.meditate.enums.YogaPositionType;
import io.github.orioncraftmc.meditate.enums.YogaWrap;

public class TestModeMenu implements Screen {

    final Main game;

    TextureAtlas atlas;

    Skin CurrentSkin;

    FlexBox backButtonFlexbox;

    TextButtonStyle textButtonStyle;

    TextButton backButton;

    FlexBox optionsFlexbox;

    SelectBox<Enemy> enemySelectBox;

    Enemy selectedEnemy;

    public TestModeMenu(Main game) {

        this.game = game;
        
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(game.stage);
        Gdx.input.setInputProcessor(multiplexer);

        CurrentSkin = game.CurrentSkin;

        atlas = new TextureAtlas("ui/uiskin.atlas");
            CurrentSkin.addRegions(atlas);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void show() {

        TextButtonStyle defaultStyle = new StyleCreation(CurrentSkin).buttonStyle;

        backButtonFlexbox = new FlexBox();
        backButtonFlexbox.setSize(game.stage.getWidth(), game.stage.getHeight());
        backButtonFlexbox.setFillParent(true);
        backButtonFlexbox.getRoot()
            .setPositionType(YogaPositionType.ABSOLUTE)
            .setMarginPercent(YogaEdge.LEFT, 5)
            .setMarginPercent(YogaEdge.TOP, 5)
            .setFlexDirection(YogaFlexDirection.ROW)  
            .setWrap(YogaWrap.WRAP)
            .setAlignItems(YogaAlign.FLEX_START)              
            .setJustifyContent(YogaJustify.FLEX_START);  
        game.stage.addActor(backButtonFlexbox);

        backButton = new TextButton("Back", defaultStyle);
        backButton.getLabel().setFontScale(Config.buttonFontScale);

        backButtonFlexbox.add(backButton)
            .setWidthPercent(StyleCreation.sizeTextButton(backButton))
            .setHeightPercent(10)
            .setMarginPercent(YogaEdge.BOTTOM, 2);

        backButton.addListener(backButtonClickListener);


        optionsFlexbox = new FlexBox();
        optionsFlexbox.setSize(game.stage.getWidth(), game.stage.getHeight());
        optionsFlexbox.setFillParent(true);
        optionsFlexbox.getRoot()
            .setPositionType(YogaPositionType.ABSOLUTE)
            .setMarginPercent(YogaEdge.LEFT, 5)
            .setMarginPercent(YogaEdge.TOP, 20)
            .setFlexDirection(YogaFlexDirection.COLUMN)  
            .setWrap(YogaWrap.WRAP)
            .setAlignItems(YogaAlign.FLEX_START)              
            .setJustifyContent(YogaJustify.FLEX_START);
        game.stage.addActor(optionsFlexbox);
        
        if (game.allEnemyList != null) {
            enemySelectBox = new SelectBox<>(CurrentSkin);
            Array<Enemy> items = new Array<>();
            System.out.println("All Enemy List: " + game.allEnemyList);
            game.allEnemyList.forEach(items::add);
            enemySelectBox.setItems(items);
            enemySelectBox.setName("Select Enemy");
            optionsFlexbox.add(enemySelectBox)
                .setWidthPercent(30)
                .setHeightPercent(10)
                .setMarginPercent(YogaEdge.BOTTOM, 2);

            enemySelectBox.addListener(enemySelectBoxChangeListener);
        }
    }

    ClickListener backButtonClickListener = new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            dispose();
            game.setScreen(new SaveSelectMenu(game));
        }
    };

    ChangeListener enemySelectBoxChangeListener = new ChangeListener() {
        @Override
        public void changed(ChangeEvent event, com.badlogic.gdx.scenes.scene2d.Actor actor) {
            selectedEnemy = enemySelectBox.getSelected();
            System.out.println(selectedEnemy);
        }
    };

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.ROYAL);

        game.viewport.apply();
        Main.batch.setProjectionMatrix(game.viewport.getCamera().combined);
        Main.batch.begin();
        Main.batch.end();

        game.stage.getViewport().apply();
        game.stage.act(delta);
        game.stage.draw();
    }

    @Override
    public void resize(int width, int height) {

        if(width <= 0 || height <= 0) return;

        game.stage.getViewport().update(width, height, true);   
        backButtonFlexbox.layout();    
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
        backButtonFlexbox.clear();
    }
    
}
