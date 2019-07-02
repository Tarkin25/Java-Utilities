package ch.util.sound.planning;

public abstract class SoundByteSequence {

  public byte[][] soundBytes;

  protected static byte[] generateBytes(double frequency, int millis) {
    byte[] bytes = new byte[16 * 1024 * millis / 1000];

    for(int i=0;i<bytes.length;i++) {
      double angle = i / (16*1024 / frequency) * 2.0 * Math.PI;

      bytes[i] = (byte)(Math.sin(angle) * 127);
    }

    return bytes;
  }

  protected static double getFrequency(int keyIndex) {
    double exponent = (keyIndex - 49) / 12.0;

    double frequency = Math.pow(2.0, exponent) * 440;

    return frequency;
  }
}
