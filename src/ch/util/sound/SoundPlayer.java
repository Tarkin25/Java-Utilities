package ch.util.sound;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import java.util.ArrayList;
import java.util.List;

public class SoundPlayer {

    public void play(final int ms, Note... notes) throws LineUnavailableException {
        List<Thread> threads = new ArrayList<>();

        for (Note note : notes) {
            Thread thread = new Thread(()-> {
                try {
                    final AudioFormat af =
                            new AudioFormat(Note.SAMPLE_RATE, 8, 1, true, true);
                    SourceDataLine line = AudioSystem.getSourceDataLine(af);
                    line.open(af, Note.SAMPLE_RATE);
                    line.start();

                    play(line, note, ms);

                    line.drain();
                    line.close();
                } catch (LineUnavailableException e) {}
            });
            thread.start();
            threads.add(thread);
        }

        for(Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void play(SourceDataLine line, Note note, int ms) {
        ms = Math.min(ms, Note.SECONDS * 1000);
        int length = Note.SAMPLE_RATE * ms / 1000;
        int count = line.write(note.data(), 0, length);
    }

}
