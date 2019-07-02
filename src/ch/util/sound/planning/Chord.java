package ch.util.sound.planning;

public class Chord extends SoundByteSequence {

    public Chord(int millis, double... frequencies) {
        soundBytes = new byte[frequencies.length][];

        for(int i=0;i<frequencies.length;i++) {
            soundBytes[i] = generateBytes(frequencies[i], millis);
        }
    }

    public Chord(int millis, int... keyIndexes) {
        soundBytes = new byte[keyIndexes.length][];

        for(int i=0;i<keyIndexes.length;i++) {
            soundBytes[i] = generateBytes(getFrequency(keyIndexes[i]), millis);
        }
    }

}
