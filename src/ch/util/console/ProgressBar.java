package ch.util.console;

import ch.util.json.JsonParseException;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.Ansi.Color;
import org.fusesource.jansi.AnsiConsole;

public class ProgressBar {
    
    private final int BAR_LENGTH;
    
    private final double MAX_PROGRESS;
    
    private double progress;
    
    private final Color color;
    
    private boolean reset;
    
    public ProgressBar(int bar_length, double max_progress, String barColor) {
        AnsiConsole.systemInstall();
        
        this.BAR_LENGTH = bar_length;
        this.MAX_PROGRESS = max_progress;
        
        switch(barColor.toLowerCase()) {
        case "black":
            color = Color.BLACK;
            break;
        case "blue":
            color = Color.BLUE;
            break;
        case "cyan":
            color = Color.CYAN;
            break;
        case "green":
            color = Color.GREEN;
            break;
        case "magenta":
            color = Color.MAGENTA;
            break;
        case "red":
            color = Color.RED;
            break;
        case "white":
            color = Color.WHITE;
            break;
        case "yellow":
            color = Color.YELLOW;
            break;
        default:
            color = Color.DEFAULT;
        }
    }
    
    public ProgressBar(int bar_length, double max_progress) {
        this(bar_length, max_progress, "default");
    }
    
    public void setProgress(double progress) {
        if(progress > MAX_PROGRESS) {
            throw new IllegalArgumentException();
        } else {
            this.progress = progress;
        }
    }
    
    public void setReset(boolean b) {
        reset = b;
    }
    
    public String toString() {
        String progressBar = "";
        
        double percentage = progress * 100 / MAX_PROGRESS;
        
        double bars = percentage / 100 * BAR_LENGTH;
        
        progressBar += "\r[";
        
        for(int i=0;i<(int)(bars);i++) {
            progressBar += "=";
        }
        
        if(bars % 1.0 >= 0.5) {
            progressBar += "-";
            bars++;
        }
        
        for(int i=0;i<(BAR_LENGTH-bars);i++) {
            progressBar += " ";
        }
        
        progressBar += "] "+(int)percentage+" % ("+(int)progress+"/"+(int)MAX_PROGRESS+")";
        
        if(reset) {
            return Ansi.ansi().fg(color).a(progressBar).reset().toString();
        } else {
            return Ansi.ansi().fg(color).a(progressBar).toString();
        }
    }
    
    public static void main(String[] args) {
        
        ProgressBar pb = new ProgressBar(90, 137);
        
        for(int i=1;i<=137;i++) {
            pb.setProgress(i);
            System.out.print(pb);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
    }

}
