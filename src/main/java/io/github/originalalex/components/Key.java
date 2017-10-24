package io.github.originalalex.components;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Key extends HBox {

    private String noteToPlay;
    private boolean hasBlackNote;
    private Rectangle blackNote;

    public Key(int width, int height, boolean hasBlackNote, int octave, String note) {
        this.hasBlackNote = hasBlackNote;
        this.noteToPlay = note + (octave+3);
        setBackgroundColor("white");
        setMaxSize(width, height);
        setMinSize(width, height);
        if (hasBlackNote) {
            Rectangle blackNote = blackNote(height, width);
            Region filler = filler();
            getChildren().addAll(filler, blackNote);
            setHgrow(filler, Priority.ALWAYS);
            setHgrow(blackNote, Priority.NEVER);
            this.blackNote = blackNote;
        }
    }

    public Key(boolean hasBlackNote, int octave, String noteNum) {
        this(30, 140, hasBlackNote, octave, noteNum);
    }

    private Rectangle blackNote(int height, int width) {
       return new Rectangle(width * 0.4, height * 0.55, Color.BLACK);
    }

    private Region filler() {
        return new Region();
    }

    public void setBackgroundColor(String color) {
        setStyle("-fx-background-color: " + color + ";" +
                "-fx-border-color: black;");
    }

    public boolean hasBlackNote() {
        return this.hasBlackNote;
    }

    public Rectangle getBlackNote() {
        return this.blackNote;
    }

    public String getNoteToPlay() {
        return this.noteToPlay;
    }

}
