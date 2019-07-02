package ch.util.sound;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class Sequence extends Playable {

    private double[] frequencies;

    public Sequence(double... frequencies) {
        this.frequencies = frequencies;
    }

    public Sequence(int... keyIndexes) {
        frequencies = new double[keyIndexes.length];

        for(int i=0;i<frequencies.length;i++) {
            frequencies[i] = getFrequency(keyIndexes[i]);
        }
    }

    public void play(int millis) {
        try{
            AudioFormat audioFormat = new AudioFormat(16*1024, 16, 1, true, false);

            SourceDataLine sourceDataLine = AudioSystem.getSourceDataLine(audioFormat);

            sourceDataLine.open(audioFormat);
            sourceDataLine.start();

            for(double frequency : frequencies) {
                byte[] soundBytes = generateBytes(frequency, millis);

                sourceDataLine.write(soundBytes, 0, soundBytes.length);
            }

            sourceDataLine.drain();
            sourceDataLine.close();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
