package ch.util.console;

import java.util.Properties;

public class Args {

    private Properties properties = new Properties();

    public Args(String... args) {
        for(int i=0;i<args.length;i+=2) {
            try{
                properties.setProperty(args[i], args[i+1]);
            } catch (ArrayIndexOutOfBoundsException e) {}
        }
    }

    public Boolean getBoolean(String key) {
        return Boolean.parseBoolean(properties.getProperty(key));
    }

    public Short getShort(String key) {
        return Short.parseShort(properties.getProperty(key));
    }

    public Byte getByte(String key) {
        return Byte.parseByte(properties.getProperty(key));
    }

    public Integer getInt(String key) {
        return Integer.parseInt(properties.getProperty(key));
    }

    public Float getFloat(String key) {
        return Float.parseFloat(properties.getProperty(key));
    }

    public Double getDouble(String key) {
        return Double.parseDouble(properties.getProperty(key));
    }

    public Long getLong(String key) {
        return Long.parseLong(properties.getProperty(key));
    }

    public Character getChar(String key) {
        return properties.getProperty(key).charAt(0);
    }

    public String getString(String key) {
        return properties.getProperty(key);
    }

  public static void main(String[] args) {
        Args arguments = new Args("-l", "5", "-h", "localhost");

    System.out.println("Length: "+arguments.getShort("-l"));
    System.out.println("Hostname: "+arguments.getString("-h"));
  }
}
