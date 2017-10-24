package io.github.originalalex.music;

import java.util.ArrayList;
import java.util.List;

public class Piece {

    private List<Chord> sequence;

    public Piece() {
        this.sequence = new ArrayList<>();
    }

    public Piece(List<Chord> chords) {
        this.sequence = chords;
    }

    public Piece(Chord... chords) {
        this();
        for (Chord c : chords) {
            sequence.add(c);
        }
    }

    public void addChord(Chord chord) {
        sequence.add(chord);
    }

    public String getStringToPlay() {
        String s = sequence.stream()
                .map(chord -> chord.convertToPlayable())
                .reduce("", (a, chord) -> a + chord + " ");
        return (s.length() != 0) ? s.substring(0, s.length()-1) : "";
    }

}
