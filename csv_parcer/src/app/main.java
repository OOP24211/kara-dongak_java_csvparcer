package app;

import app.io.CsvWriter;
import app.text.BufferTokenizer;
import app.text.WordStats;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class main {
    public static void main(String[] args) {
        final String inputPath = "input.txt";
        final String outputPath = "output.csv";
        final int BUFFER_SIZE = 1024;

        BufferTokenizer tokenizer = new BufferTokenizer();
        WordStats stats = new WordStats();

        try (Reader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputPath), StandardCharsets.UTF_8))) {
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
            System.out.println("The file cannot be opened!");
            return;
        }
        System.out.println("The file has been opened successfully");

        try (Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputPath), StandardCharsets.UTF_8))) {
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
