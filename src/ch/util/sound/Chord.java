package ch.util.sound;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Chord extends Playable {

  private double[] frequencies;

  public Chord(double... frequencies) {
    this.frequencies = frequencies;
  }

  public Chord(int... keyIndexes) {
    frequencies = new double[keyIndexes.length];

    for (int i = 0; i < frequencies.length; i++) {
      frequencies[i] = getFrequency(keyIndexes[i]);
    }
  }

  public void play(int millis) {
      try{
          final CyclicBarrier barrier = new CyclicBarrier(frequencies.length);

          for(double frequency : frequencies) {
        new Thread(
                () -> {
                  try {
                    barrier.await();

                    AudioFormat audioFormat = new AudioFormat(16 * 1024, 16, 1, true, false);

                    SourceDataLine sourceDataLine = AudioSystem.getSourceDataLine(audioFormat);

                    sourceDataLine.open(audioFormat);
                    sourceDataLine.start();

                    byte[] soundBytes = generateBytes(frequency, millis);

                    sourceDataLine.write(soundBytes, 0, soundBytes.length);
                    sourceDataLine.drain();
                    sourceDataLine.close();
                  } catch (InterruptedException
                      | BrokenBarrierException
                      | LineUnavailableException e) {
                  }
                })
            .start();
          }

          barrier.await();
          barrier.await();
      } catch (InterruptedException | BrokenBarrierException e) {
          e.printStackTrace();
      }
  }
}
