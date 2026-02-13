package app.text;

import java.util.*;

public class WordStats {
    private final Map<String, Integer> wordCount = new HashMap<>();
    private long totalWords = 0;

    public void addWord(String rawWord){
        if(rawWord == null) return;

        String cleaned = TextNormalizer.cleanWord(rawWord);
        if(cleaned.isEmpty()) return;

        String normalized = TextNormalizer.toLowerRuEn(cleaned);

        wordCount.merge(normalized, 1, Integer::sum);
        totalWords++;
    }

    public List<Map.Entry<String, Integer>> sortedByFreqDesc() {
        List<Map.Entry<String, Integer>> list = new ArrayList<>(wordCount.entrySet());

        list.sort((a, b) -> Integer.compare(b.getValue(), a.getValue()));

        return list;
    }

    public long getTotalWords() {
        return totalWords;
    }
}
