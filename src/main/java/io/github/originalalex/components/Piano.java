package io.github.originalalex.components;

import io.github.originalalex.music.Chord;
import io.github.originalalex.music.Note;
import io.github.originalalex.music.Piece;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;

import javax.sound.midi.MidiSystem;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Piano extends HBox {

    private Map<Integer, String> order;
    private Player player;
    private boolean playChords;

    private static final Color RECTANGLE_HIGHLIGHT = Color.rgb(255,228,181); // light yellow
    private Chord currentChord;
    private Piece piece;

    public Piano(int numberOfOctaves) {
        initVariables();
        createPiano(numberOfOctaves);
        setPadding(new Insets(20, 5, 5, 5));
    }

    public Piano() {
        this(4);
    }

    private void createPiano(int numberOfOctaves) {
        for (int i = 0; i < numberOfOctaves; i++) {
            for (int j = 0; j < 7; j++) {
                Key key;
                if (j == 0 || j == 1 || j == 3 || j == 4 || j == 5) { // has a sharp
                    key = new Key(true, i, order.get(j));
                } else {
                    key = new Key(false, i, order.get(j));
                }

                key.setOnMouseClicked(e -> {
                    if (playChords) {
                        if (key.getStyle().contains("background-color: yellow")) {
                            currentChord.removeWithNode(key);
                            key.setBackgroundColor("white");
                        } else {
                            currentChord.addNote(new Note(key, false, key));
                            key.setBackgroundColor("yellow");
                        }
                    } else {
                        player.getManagedPlayer().finish();
                        Thread th = new Thread(() -> {
                            player.play(key.getNoteToPlay());
                        });
                        th.start();
                    }
                });
                if (key.hasBlackNote()) {
                    Rectangle r = key.getBlackNote();
                    r.setOnMouseClicked(e -> {
                        if (playChords) {
                            Color color = (Color) r.getFill();
                            if (color.equals(RECTANGLE_HIGHLIGHT)) {
                                currentChord.removeWithNode(r);
                                r.setFill(Color.BLACK);
                            } else {
                                r.setFill(RECTANGLE_HIGHLIGHT);
                                currentChord.addNote(new Note(key, true, r));
                            }
                        } else {
                            player.getManagedPlayer().finish();
                            Thread th = new Thread(() -> {
                                player.play(key.getNoteToPlay() + "#");
                            });
                            th.start();
                        }
                        e.consume();
                    });
                }
                getChildren().add(key);
            }
        }
    }

    private void initVariables() {
        player = new Player();
        currentChord = new Chord();
        playChords = false;
        piece = new Piece();
        initializeMap();
    }

    private void initializeMap() {
        order = new HashMap<>();
        char[] arr = "CDEFGAB".toCharArray();
        for (int i = 0; i < arr.length; i++) {
            order.put(i, String.valueOf(arr[i]));
        }
    }

    public void setChordMode(boolean mode) {
        playChords = mode;
        if (mode) {
            currentChord = new Chord();
        }
    }

    public void playChord() {
        player.getManagedPlayer().finish();
        player.play(currentChord.convertToPlayable());
    }

    public void addChordToPiece() {
        if (!currentChord.getNotes().isEmpty()) {
            List<Note> notes = currentChord.getNotes();
            for (Note note : notes) {
                Node pressed = note.getNodePressed();
                if (pressed instanceof Rectangle) {
                    ((Rectangle) pressed).setFill(Color.BLACK);
                } else {
                    ((Key) pressed).setBackgroundColor("white");
                }
            }
            piece.addChord(currentChord);
            currentChord = new Chord();
        }
    }

    public void playPiece() {
        String pieceAsString = piece.getStringToPlay();
        player.getManagedPlayer().finish();
        player.play(pieceAsString);
    }

    public Piece getPiece() {
        return this.piece;
    }

}
