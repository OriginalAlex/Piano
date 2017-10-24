package io.github.originalalex.backend;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToggleButton;
import io.github.originalalex.components.Piano;
import io.github.originalalex.util.NodeUtils;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.stage.DirectoryChooser;
import org.jfugue.midi.MidiFileManager;
import org.jfugue.pattern.Pattern;

import java.io.File;
import java.io.IOException;

public class Controller {

    @FXML
    private BorderPane root;

    @FXML
    private JFXToggleButton chord;

    private Piano piano;

    @FXML
    public void initialize() {
        this.piano = new Piano(4);
        root.setCenter(getFullContainer());
    }

    private VBox getFullContainer() {
        VBox fullContainer = new VBox();
        HBox container = center(piano);
        Region space = new Region(), space2 = new Region();
        JFXButton playButton = new JFXButton("Play Piece");
        JFXButton saveButton = new JFXButton("Save Piece");
        HBox buttonContainer = center(playButton);
        buttonContainer.getChildren().addAll(saveButton, space2);
        fullContainer.getChildren().addAll(container, space, buttonContainer);
        NodeUtils.makeNodeAlwaysGrowHBox(space2);
        NodeUtils.makeNodeAlwaysGrowVBox(space);
        addEvents(playButton, saveButton);
        return fullContainer;
    }

    private void addEvents(JFXButton play, JFXButton save) {
        play.setOnAction(e -> {
            piano.playPiece();
        });

        save.setOnAction(e -> {
            DirectoryChooser chooser = new DirectoryChooser();
            chooser.setTitle("Folder for MIDI file");
            File directory = chooser.showDialog(root.getScene().getWindow());
            if (directory != null) {
                File file = new File(directory.getAbsolutePath() + "\\piece.mid");
                try {
                    MidiFileManager.savePatternToMidi(new Pattern(piano.getPiece().getStringToPlay()), file);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    private HBox center(Node node) {
        HBox container = new HBox();
        Region surroundLeft = new Region(), surroundRight = new Region();
        container.getChildren().addAll(surroundLeft, node, surroundRight);
        NodeUtils.makeNodeAlwaysGrowHBox(surroundLeft, surroundRight);
        return container;
    }

    @FXML
    public void changeChordMode() {
        piano.setChordMode(chord.isSelected());
    }

    @FXML
    public void playChord() {
        Thread th = new Thread(() -> {
            piano.playChord();
        });
        th.start();
    }

    @FXML
    public void addChord() {
        piano.addChordToPiece();
    }

    @FXML
    public void playPiece() {
        piano.playPiece();
    }

}
