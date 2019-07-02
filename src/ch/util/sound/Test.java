package ch.util.sound;

import ch.util.sound.planning.Note;
import ch.util.sound.planning.Player;
import ch.util.sound.planning.Chord;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;

import java.util.concurrent.CyclicBarrier;

import static ch.util.sound.planning.Note.*;

public class Test {

    public static void main(String[] args) throws Exception {

//        Note note = new Note(500, C4);
//        Chord chord = new Chord(500, C4, E4, G4);
//
//        Player player = new Player();
//
//        player.add(note);
//        player.add(chord);
//        player.add(note);
//
//        player.play();

        AudioFormat audioFormat = new AudioFormat(16 * 1024, 16, 1, true, false);

        final SourceDataLine sourceDataLine = AudioSystem.getSourceDataLine(audioFormat);

        sourceDataLine.open(audioFormat);
        sourceDataLine.start();

        final CyclicBarrier barrier = new CyclicBarrier(2);

        new Thread(()-> {
            try{
                barrier.await();

                byte[] bytes = new Note(500, C4).soundBytes[0];

                sourceDataLine.write(bytes, 0, bytes.length);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

//        new Thread(()-> {
//            try{
//                barrier.await();
//
//                byte[] bytes = new Note(500, F4).soundBytes[0];
//
//                sourceDataLine.write(bytes, 0, bytes.length);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }).start();

        barrier.await();

        sourceDataLine.drain();
        sourceDataLine.close();
    }
}
