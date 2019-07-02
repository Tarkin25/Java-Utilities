package ch.util.sound;

import javax.sound.sampled.LineUnavailableException;

public class Test {

    public static void main(String[] args) throws LineUnavailableException {

        SoundPlayer player = new SoundPlayer();

        player.play(5000, Note.C4, Note.F4$);
    }



}
