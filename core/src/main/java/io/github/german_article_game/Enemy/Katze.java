package io.github.german_article_game.Enemy;

import io.github.german_article_game.Main;

public class Katze extends EnemyNoun {

    public Katze(Main game) {
        super(game, "Katze");
        this.gender = Gender.FEMALE;
    }
    
}
