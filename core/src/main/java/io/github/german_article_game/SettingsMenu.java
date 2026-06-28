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

public class SettingsMenu implements Screen {

    final Main game;

    FlexBox settingsMenuFlexbox;

    TextureAtlas atlas;

    Skin CurrentSkin;

    YogaNode backNode;
    TextButton backButton;

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
            .setWrap(YogaWrap.WRAP);
        game.stage.addActor(settingsMenuFlexbox);


        TextButtonStyle defaultStyle = new TextButtonStyle();
            defaultStyle.font = CurrentSkin.getFont("default-font");
            defaultStyle.fontColor = Color.BROWN;
            defaultStyle.up = CurrentSkin.newDrawable("button-normal");
            defaultStyle.over = CurrentSkin.newDrawable("button-normal-over");
            defaultStyle.down = CurrentSkin.newDrawable("button-normal-pressed");

        backButton = new TextButton("Back", defaultStyle);
        backButton.getLabel().setFontScale(1.5f);

        backNode = settingsMenuFlexbox.add(backButton)
            .setFlexDirection(YogaFlexDirection.COLUMN)
            .setBorder(YogaEdge.ALL, 25)
            .setMargin(YogaEdge.LEFT, 50)
            .setMargin(YogaEdge.TOP, 50)
            .setWidth(150)
            .setHeight(75);

        backButton.addListener(backListener);
        backButton.setTouchable(Touchable.enabled);
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
        //game.titleFont.draw(game.batch, "What Article Do I Use Again?", 2.5f, 4.25f);
        game.batch.end();

        game.stage.getViewport().apply();  // re-apply stage viewport before drawing
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
