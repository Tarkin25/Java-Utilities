package ch.util.strings;

public class StringUtils {

    public static String replaceLast(String s, String regex, String replacement) {
        String reverse = new StringBuffer(s).reverse().toString();

        reverse = reverse.replaceFirst(regex, replacement);

        return new StringBuffer(reverse).reverse().toString();
    }

}
