package io.github.german_article_game.Enemy;

import java.util.Collection;
import com.badlogic.gdx.math.MathUtils;
import com.dongbat.jbump.Item;
import com.github.tommyettinger.gand.DirectedGraph;
import com.github.tommyettinger.gand.Edge;

import io.github.german_article_game.Entity;
import io.github.german_article_game.Main;

public class EnemyMovePatterns {

    static DirectedGraph<Pattern> patternGraph = new DirectedGraph<>();

    public enum Pattern {
        CENTER,
        CHARGE,
        SIDE_TO_SIDE,
        FIGURE_EIGHT,
        OVAL,
        CIRCLE,
        HOURGLASS
    }

    enum Direction {
        LEFT,
        RIGHT,
        UP,
        DOWN
    }

    static Pattern current = Pattern.CENTER;
    
    static Collection<Edge<Pattern>> options; 
    static float totalWeight = 0f;
    private static Direction direction = Direction.RIGHT;

    public EnemyMovePatterns(Main game) {

        for (Pattern p : Pattern.values()) {
            patternGraph.addVertex(p);
        }
        patternGraph.addEdge(Pattern.CENTER, Pattern.CHARGE, 2f);

        
        
        options = patternGraph.getEdges(current);
        
        // System.out.println("Pattern Graph Components:" + patternGraph.internals().getNode(Pattern.CHARGE));
        // System.out.flush();
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
            case LEFT:
                return 180f;
            case RIGHT:
                return 0f;
            default:
                return 0f;
        }
    }
    public static void random(Entity entity) {
        if (options == null) {
            options = patternGraph.getEdges(Pattern.CENTER);
            System.out.println(options);
            return;
        }
        for (Edge<Pattern> c : options) totalWeight += c.getWeight();

        float roll = MathUtils.random(totalWeight);

        Pattern next = current;
        for (Edge<Pattern> c : options) {
            roll -= c.getWeight();
            if (roll <= 0f) {
                next = c.getB();
                break;
            }
        }

        System.out.println(next.getClass());
    
    }
    public static void testPattern(Entity entity) {

      //  System.out.println(Pattern.CENTER.name());

    }
}
