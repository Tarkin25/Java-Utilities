package ch.util.sound.planning;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;
import java.util.ArrayList;

public class Player extends ArrayList<SoundByteSequence> {

    public void play() {
        try{
            AudioFormat audioFormat = new AudioFormat(16 * 1024, 16, 1, true, false);

            SourceDataLine sourceDataLine = AudioSystem.getSourceDataLine(audioFormat);

            sourceDataLine.open(audioFormat);
            sourceDataLine.start();

            for(SoundByteSequence soundByteSequence : this) {
                for(byte[] bytes : soundByteSequence.soundBytes) {
                    sourceDataLine.write(bytes, 0, bytes.length);
                }
            }

            sourceDataLine.drain();
            sourceDataLine.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
