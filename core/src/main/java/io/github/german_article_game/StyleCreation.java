package io.github.german_article_game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox.CheckBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class StyleCreation {

    Skin CurrentSkin;

    TextButtonStyle buttonStyle;

    CheckBoxStyle checkBoxStyle;

    LabelStyle defaultLabelStyle;

    public StyleCreation(Skin CurrentSkin) {
        this.CurrentSkin = CurrentSkin;

        buttonStyle = new TextButtonStyle();
            buttonStyle.font = FontGeneration.buttonFont;
            buttonStyle.fontColor = FontGeneration.buttonFont.getColor();
            buttonStyle.up = CurrentSkin.newDrawable("button-normal");
            buttonStyle.over = CurrentSkin.newDrawable("button-normal-over");
            buttonStyle.down = CurrentSkin.newDrawable("button-normal-pressed");

        Drawable checkOff = CurrentSkin.newDrawable("checkbox");
        checkOff.setMinWidth(32);
        checkOff.setMinHeight(32);

        Drawable checkOn = CurrentSkin.newDrawable("checkbox-selected");
        checkOn.setMinWidth(32);
        checkOn.setMinHeight(32);

        checkBoxStyle = new CheckBoxStyle();
            checkBoxStyle.font = FontGeneration.buttonFont;
            checkBoxStyle.fontColor = Color.SKY;
            checkBoxStyle.checkboxOff = CurrentSkin.newDrawable(checkOff);
            checkBoxStyle.checkboxOn = CurrentSkin.newDrawable(checkOn);

        defaultLabelStyle = new LabelStyle();
            defaultLabelStyle.font = FontGeneration.buttonFont;
            defaultLabelStyle.fontColor = Color.SKY;
    }
    
}
