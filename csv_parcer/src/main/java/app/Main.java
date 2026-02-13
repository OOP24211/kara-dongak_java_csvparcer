package app;

import app.io.CsvWriter;
import app.text.BufferTokenizer;
import app.text.WordStats;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class Main {

    private static final String INPUT_PATH = "input.txt";
    private static final String OUTPUT_PATH = "output.csv";
    private static final int BUFFER_SIZE = 1024;

    public static void main(String[] args) throws Exception {

        BufferTokenizer tokenizer = new BufferTokenizer();
        WordStats stats = new WordStats();

        try (Reader reader = new BufferedReader(new InputStreamReader(new FileInputStream(INPUT_PATH), StandardCharsets.UTF_8))) {
            char[] buf = new char[BUFFER_SIZE];
            int read;

            while ((read = reader.read(buf)) != -1) {
                String chunk = new String(buf, 0, read);

                List<String> words = tokenizer.consume(chunk);
                for (String w : words) {
                    stats.addWord(w);
                }
            }
            for (String w : tokenizer.finish()) {
                stats.addWord(w);
            }
        } catch (IOException e) {
            System.out.println("The file cannot be opened!" + e.getMessage());
            return;
        }
        System.out.println("The file has been opened successfully");

        try (Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(OUTPUT_PATH), StandardCharsets.UTF_8))) {
            CsvWriter.writeHeader(out);

            long total = stats.getTotalWords();
            for (Map.Entry<String, Integer> entry : stats.sortedByFreqDesc()) {
                CsvWriter.writeRow(out, entry.getKey(), entry.getValue(), total);
            }
        } catch (IOException e) {
            System.out.println("Cannot write output.csv: " + e.getMessage());
        }
    }
}
