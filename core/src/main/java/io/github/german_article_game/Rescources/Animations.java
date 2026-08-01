package io.github.german_article_game.Rescources;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

public class Animations {

    public static TextureAtlas atlas = new TextureAtlas("animations/peopleskin.atlas");
    
    public static final Animation<AtlasRegion> playerAnimation =
    new Animation<>(1.5f / 30f, atlas.findRegions("player-normal"), PlayMode.LOOP);

    public static final Animation<AtlasRegion> katzeAnimation =
    new Animation<>(1.5f / 30f, atlas.findRegions("katze-normal"), PlayMode.LOOP);
    
}
