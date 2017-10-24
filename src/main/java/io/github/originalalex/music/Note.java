package io.github.originalalex.music;

import io.github.originalalex.components.Key;
import javafx.scene.Node;

public class Note {

    private Node key;
    private Node actualNote;
    private String playableString;

    public Note(Key key, boolean isBlackNote, Node actualNote) { // actual note is the button pressed (ie. black rectangle/white hbox)
        this.playableString = (isBlackNote) ? key.getNoteToPlay() + "#" : key.getNoteToPlay();
        this.key = key;
        this.actualNote = actualNote;
    }

    public Node getNode() {
        return this.key;
    }

    public String getPlayableString() {
        return this.playableString;
    }

    public Node getNodePressed() {
        return this.actualNote;
    }

}
