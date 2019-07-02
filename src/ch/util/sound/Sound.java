package ch.util.sound;

public class Sound extends Playable {

    private Playable[] playables;

    public Sound(Playable... playables) {
        this.playables = playables;
    }

    public void play(int millis) {
        for(Playable playable : playables) {
            playable.play(millis);
        }
    }

}
