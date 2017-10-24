package io.github.originalalex.music;

import javafx.scene.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Chord {

    private List<Note> notes;

    public Chord() {
        this.notes = new ArrayList<>();
    }

    public void addNote(Note n) {
        notes.add(n);
    }

    public void removeNote(Note n) {
        notes.remove(n);
    }

    public void removeWithNode(Node n) {
        notes = notes.stream()
                .filter(note -> !note.getNode().equals(n))
                .collect(Collectors.toList());
    }

    public String convertToPlayable() {
        String s = notes
                .stream()
                .map(note -> note.getPlayableString())
                .reduce("", (a, note) -> a + note + "+");
        return s.substring(0, s.length()-1); // ignore the last "+"
    }

    public List<Note> getNotes() {
        return this.notes;
    }

}
