package io.github.german_article_game.Enemy;

import io.github.german_article_game.Main;

public abstract class EnemyNoun extends Enemy {

    public enum Gender {
        MALE,
        FEMALE,
        NEUTER
    }

    public Gender gender;

    public EnemyNoun(Main game, String germanName) {
        super(game, germanName);
    }

    public String addArticle() {
        switch (gender) {
            case MALE:
                return "Der" + this.germanName;
            case FEMALE:
                return "Die" + this.germanName;
            default:
                return "Das" + this.germanName;
        }
    }
    
}
