package io.github.german_article_game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.dongbat.jbump.Item;

public abstract class Entity {
    public Animation<AtlasRegion> animation;
    public float animationTime;
    public float x;
    public float y;
    public float bboxX;
    public float bboxY;
    public float bboxWidth;
    public float bboxHeight;
    public float rotation;
    public float deltaX;
    public float deltaY;
    public boolean flipX;
    public boolean flipY;
    public float gravityX;
    public float gravityY;
    public Item<Entity> item;
    
    public abstract void act(float delta);
    
    public void draw() {
        if (animation != null) {
            AtlasRegion region = animation.getKeyFrame(animationTime);
            Main.batch.draw(region, x, y, region.getRegionWidth() / 2f, region.getRegionHeight() / 2f, region.getRegionWidth(), region.getRegionHeight(), flipX ? -1 : 1, flipY ? -1 : 1, rotation);
        }
    }
}
