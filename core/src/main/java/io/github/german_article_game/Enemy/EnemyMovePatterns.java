package io.github.german_article_game.Enemy;

import java.util.Collection;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.dongbat.jbump.Item;
import com.github.tommyettinger.gand.DirectedGraph;
import com.github.tommyettinger.gand.Edge;
import com.github.tommyettinger.gand.Path;
import io.github.german_article_game.Entity;
import io.github.german_article_game.Main;

public class EnemyMovePatterns {
    // The graph itself IS shared — every enemy uses the same rules for what
    // can transition to what. Only per-enemy PROGRESS through the graph is instance state.
    private static final DirectedGraph<Pattern> patternGraph = new DirectedGraph<>();
    private static boolean graphInitialized = false;

    public enum Pattern {
        CENTER, CHARGE, SIDE_TO_SIDE, FIGURE_EIGHT, OVAL, CIRCLE, HOURGLASS
    }

    enum Direction { LEFT, RIGHT, UP, DOWN }

    // --- Instance state: each enemy gets its own copy of all of this ---
    private Pattern current = Pattern.SIDE_TO_SIDE;
    private Collection<Edge<Pattern>> options;
    private Direction direction = Direction.RIGHT;

    private float angle = 0f;
    private float centerX = Main.gameplayViewport.getMinWorldWidth() / 2;
    private float centerY = Main.gameplayViewport.getMinWorldHeight() / 1.25f;

    private static final float CIRCLE_RADIUS = 3f;
    private static final float CIRCLE_SPEED = 2f;

    private static final float OVAL_RADIUS_X = 4f;
    private static final float OVAL_RADIUS_Y = 2f;
    private static final float OVAL_SPEED = 2f;

    private static final float FIGURE_EIGHT_RADIUS = 3f;
    private static final float FIGURE_EIGHT_SPEED = 2f;

    private static final float HOURGLASS_WIDTH = 3f;
    private static final float HOURGLASS_HEIGHT = 3f;
    private static final float HOURGLASS_SPEED = 2f;

    public EnemyMovePatterns(Main game) {
        initGraphOnce();
        options = patternGraph.getEdges(current);
    }

    // The graph's vertices/edges only need to be built ONE time
    private static synchronized void initGraphOnce() {
        if (graphInitialized) return;
        for (Pattern p : Pattern.values()) {
            patternGraph.addVertex(p);
        }
        patternGraph.addEdge(Pattern.CENTER, Pattern.CHARGE, 2f);
        // TODO: add the other edges here
        graphInitialized = true;
    }

    public float leftAndRight(Entity entity, Main game) {
        Item<Entity> item = entity.item;
        if (item.userData.x >= game.gameplayViewport.getMinWorldWidth() - item.userData.bboxWidth) {
            direction = Direction.LEFT;
        }
        if (item.userData.x <= game.gameplayViewport.getScreenX()) {
            direction = Direction.RIGHT;
        }
        switch (direction) {
            case LEFT: return 180f;
            case RIGHT: return 0f;
            default: return 0f;
        }
    }

    public void random(Entity entity) {
        if (options == null || options.isEmpty()) {
            options = patternGraph.getEdges(current);
            return;
        }

        float total = 0f;
        for (Edge<Pattern> c : options) total += c.getWeight();

        float roll = MathUtils.random(total);
        Pattern next = current;
        for (Edge<Pattern> c : options) {
            roll -= c.getWeight();
            if (roll <= 0f) {
                next = c.getB();
                break;
            }
        }

        System.out.println(next);

        current = next;
        options = patternGraph.getEdges(current);

        angle = 0f;
        centerX = entity.x;
        centerY = entity.y;
    }

    public void circle(Entity entity, float radius, float speed) {
        float dt = Gdx.graphics.getDeltaTime();
        angle += speed * dt;
        entity.x = centerX + MathUtils.cos(angle) * radius;
        entity.y = centerY + MathUtils.sin(angle) * radius;
    }

    public void oval(Entity entity, float radiusX, float radiusY, float speed) {
        float dt = Gdx.graphics.getDeltaTime();
        angle += speed * dt;
        entity.x = centerX + MathUtils.cos(angle) * radiusX;
        entity.y = centerY + MathUtils.sin(angle) * radiusY;
    }

    public void figureEight(Entity entity, float radius, float speed) {
        float dt = Gdx.graphics.getDeltaTime();
        angle += speed * dt;
        entity.x = centerX + MathUtils.sin(angle) * radius;
        entity.y = centerY + MathUtils.sin(angle * 2f) * (radius * 0.5f);
    }

    public void hourglass(Entity entity, float width, float radius, float speed) {
        float dt = Gdx.graphics.getDeltaTime();
        angle += speed * dt;
        float t = (angle % MathUtils.PI2) / MathUtils.PI2;
        float yWave = (t < 0.5f) ? (t * 4f - 1f) : (3f - t * 4f);
        float pinch = Math.abs(yWave);
        float xWave = MathUtils.sin(angle * 2f) * pinch;
        entity.x = centerX + xWave * width;
        entity.y = centerY + yWave * radius;
    }

    public void dispatch(Entity entity, Main game) {
        switch (current) {
            case CENTER:
                // TODO
                break;
            case CHARGE:
                // TODO
                break;
            case SIDE_TO_SIDE:
                leftAndRight(entity, game);
                break;
            case CIRCLE:
                circle(entity, CIRCLE_RADIUS, CIRCLE_SPEED);
                break;
            case OVAL:
                oval(entity, OVAL_RADIUS_X, OVAL_RADIUS_Y, OVAL_SPEED);
                break;
            case FIGURE_EIGHT:
                figureEight(entity, FIGURE_EIGHT_RADIUS, FIGURE_EIGHT_SPEED);
                break;
            case HOURGLASS:
                hourglass(entity, HOURGLASS_WIDTH, HOURGLASS_HEIGHT, HOURGLASS_SPEED);
                break;
        }
    }

    public void testPattern(Entity entity) {
        Path<Pattern> path = Path.with(Pattern.CENTER, Pattern.CHARGE, Pattern.CIRCLE);
    }
}