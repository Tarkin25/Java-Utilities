package ch.util.sound;

public enum Note {

    REST, C1, C1$, D1, D1$, E1, F1, F1$, G1, G1$, A1, A1$, B1,
    C2, C2$, D2, D2$, E2, F2, F2$, G2, G2$,
    A3, A3$, B3, C3, C3$, D3, D3$, E3, F3, F3$, G3, G3$,
    A4, A4$, B4, C4, C4$, D4, D4$, E4, F4, F4$, G4, G4$,
    A5, A5$, B5, C5;

    public static final int SAMPLE_RATE = 16 * 1024; // ~16KHz
    public static final int SECONDS = 2;
    private byte[] sin = new byte[SECONDS * SAMPLE_RATE];

    Note() {
        int n = this.ordinal();
        if (n > 0) {
            double exp = ((double) n - 1) / 12d;
            double f = 130.81 * Math.pow(2d, exp);
            for (int i = 0; i < sin.length; i++) {
                double period = (double)SAMPLE_RATE / f;
                double angle = 2.0 * Math.PI * i / period;
                sin[i] = (byte)(Math.sin(angle) * 127f);
            }
        }
    }

    public byte[] data() {
        return sin;
    }

}
