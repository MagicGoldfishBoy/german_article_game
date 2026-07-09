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
    static DirectedGraph<Pattern> patternGraph = new DirectedGraph<>();

    public enum Pattern {
        CENTER, CHARGE, SIDE_TO_SIDE, FIGURE_EIGHT, OVAL, CIRCLE, HOURGLASS
    }

    enum Direction { LEFT, RIGHT, UP, DOWN }

    static Pattern current = Pattern.CENTER;
    static Collection<Edge<Pattern>> options;
    private static Direction direction = Direction.RIGHT;

    // Time accumulator for the currently active pattern (radians for trig patterns)
    private static float angle = 0f;

    // The "anchor" point a shape is drawn around, captured once when a pattern starts
    private static float centerX = 0f;
    private static float centerY = 0f;

    public EnemyMovePatterns(Main game) {
        for (Pattern p : Pattern.values()) {
            patternGraph.addVertex(p);
        }
        patternGraph.addEdge(Pattern.CENTER, Pattern.CHARGE, 2f);
        // TODO: add the rest of your edges here so every pattern can reach others
        options = patternGraph.getEdges(current);
    }

    public static float leftAndRight(Entity entity, Main game) {
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

    /**
     * Rolls a weighted random edge out of `current` and advances the state machine.
     * Call this once, whenever you decide it's time to pick a new pattern
     * (e.g. the current pattern finished, or on a timer) — NOT every frame.
     */
    public static void random(Entity entity) {
        if (options == null || options.isEmpty()) {
            options = patternGraph.getEdges(current);
            return;
        }

        float total = 0f; // local, not static — this was the earlier bug
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

        // Actually advance the state machine
        current = next;
        options = patternGraph.getEdges(current);

        // Reset per-pattern state so the new shape starts fresh
        angle = 0f;
        centerX = entity.x;
        centerY = entity.y;
    }

    /**
     * Moves entity around a circle of the given radius at the given angular speed.
     * radius: how big the circle is (world units)
     * speed: radians per second (try ~1f-3f to start)
     */
    public static void circle(Entity entity, float radius, float speed) {
        float dt = Gdx.graphics.getDeltaTime();
        angle += speed * dt;

        entity.x = centerX + MathUtils.cos(angle) * radius;
        entity.y = centerY + MathUtils.sin(angle) * radius;
    }

    /**
     * Like circle(), but different x/y radii — makes an ellipse instead of a perfect circle.
     */
    public static void oval(Entity entity, float radiusX, float radiusY, float speed) {
        float dt = Gdx.graphics.getDeltaTime();
        angle += speed * dt;

        entity.x = centerX + MathUtils.cos(angle) * radiusX;
        entity.y = centerY + MathUtils.sin(angle) * radiusY;
    }

    /**
     * Classic figure-eight (lemniscate-style) using a Lissajous curve:
     * x oscillates once per cycle, y oscillates twice per cycle -> traces an "8" on its side.
     */
    public static void figureEight(Entity entity, float radius, float speed) {
        float dt = Gdx.graphics.getDeltaTime();
        angle += speed * dt;

        entity.x = centerX + MathUtils.sin(angle) * radius;
        entity.y = centerY + MathUtils.sin(angle * 2f) * (radius * 0.5f);
    }

    /**
     * Hourglass shape: two triangles meeting at a point in the middle.
     * We trace it as a bowtie using a scaled sine relationship, similar to figure-eight
     * but with a sharper "pinch" in the middle by using tan-like scaling on x.
     * radius: half-height of the hourglass
     * width: half-width of the top/bottom triangles
     */
    public static void hourglass(Entity entity, float width, float radius, float speed) {
        float dt = Gdx.graphics.getDeltaTime();
        angle += speed * dt;

        // y sweeps up and down linearly (triangle wave) for the "hourglass" vertical motion
        float t = (angle % MathUtils.PI2) / MathUtils.PI2; // 0..1 over one cycle
        float yWave = (t < 0.5f) ? (t * 4f - 1f) : (3f - t * 4f); // triangle wave, -1..1..-1
        // x pinches toward 0 as y approaches the middle (like an hourglass silhouette)
        float pinch = Math.abs(yWave); // 0 in the middle, 1 at top/bottom
        float xWave = MathUtils.sin(angle * 2f) * pinch;

        entity.x = centerX + xWave * width;
        entity.y = centerY + yWave * radius;
    }

    public static void testPattern(Entity entity) {
        Path<Pattern> path = Path.with(Pattern.CENTER, Pattern.CHARGE, Pattern.CIRCLE);
    }
}