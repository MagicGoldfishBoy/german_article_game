package io.github.german_article_game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.Rect;
import com.dongbat.jbump.World;

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

    public void drawDebugHitbox(ShapeRenderer shapeRenderer, World<Entity> world) {
    if (item == null) return;
    Rect rect = world.getRect(item);
    if (rect != null) {
        shapeRenderer.rect(rect.x, rect.y, rect.w, rect.h);
    }
}
}
