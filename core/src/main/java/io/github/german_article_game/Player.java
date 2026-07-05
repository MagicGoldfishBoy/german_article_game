package io.github.german_article_game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

public class Player extends Entity {

    static TextureAtlas atlas = new TextureAtlas("ui/uiskin.atlas");

    public static final Animation<AtlasRegion> tank = new Animation<>(1 / 30f, atlas.findRegions("square"), PlayMode.LOOP);

    @Override
    public void act(float delta) {

        boolean left = Gdx.input.isKeyPressed(Config.leftKey);
        boolean right = Gdx.input.isKeyPressed(Config.rightKey);
        boolean up = Gdx.input.isKeyPressed(Config.upKey);
        boolean down = Gdx.input.isKeyPressed(Config.downKey);
        boolean fire = Gdx.input.isKeyJustPressed(Config.fireKey);

        float direction = 0f;

            if (left || right || up || down) {
                if (left || right) {
                    if (left) {
                        direction = 180f;
                        if (up) direction = 135f;
                        else if (down) direction = 225f;
                    } else {
                        direction = 0f;
                        if (up) direction = 45f;
                        else if (down) direction = 315f;
                    }
                } else {
                    if (up) direction = 90f;
                    else if (down) direction = 270f;
                }
            
        }
    }
}
