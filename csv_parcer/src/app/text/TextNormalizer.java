package app.text;

import org.w3c.dom.Text;

import java.util.Locale;

public class TextNormalizer {
    private static final Locale RU = new Locale("ru", "RU");

    private TextNormalizer() {}

    public static String toLowerRuEn(String s){
        if (s == null || s.isEmpty()) return s;
        return s.toLowerCase(RU);
    }

    public static String cleanWord(String word){
        if(word == null || word.isEmpty()) return "";

        StringBuilder sb = new StringBuilder(word.length());
        for (int i = 0; i < word.length(); i++){
            char ch = word.charAt(i);

            if(Character.isLetterOrDigit(ch)){
                sb.append(ch);
            }
        }
        return sb.toString();
    }
}
