package app.text;

import java.util.ArrayList;
import java.util.List;

public class BufferTokenizer {
    private String leftover = "";

    public List<String> consume(String chunk) {
        List<String> words = new ArrayList<>();
        StringBuilder currentWord = new StringBuilder(leftover);

        for (int i = 0; i < chunk.length(); i++) {
            char c = chunk.charAt(i);
            if (Character.isWhitespace(c) || isPunctuation(c)) {
                if (!currentWord.isEmpty()) {
                    String cleaned = TextNormalizer.cleanWord(currentWord.toString());
                    if (!cleaned.isEmpty()) {
                        words.add(cleaned);
                    }
                    currentWord.setLength(0);
                }
            }
            else {
                currentWord.append(c);
            }
        }
        leftover = currentWord.toString();

        return words;
    }

    public List<String> finish(){
        List<String> words = new ArrayList<>();

        if (leftover != null && !leftover.isEmpty()) {
            String cleaned = TextNormalizer.cleanWord(leftover);
            if(!cleaned.isEmpty()){
                words.add(cleaned);
            }
            leftover = "";
        }
        return words;
    }

    private boolean isPunctuation(char c){
        return !Character.isLetterOrDigit(c);
    }
}
