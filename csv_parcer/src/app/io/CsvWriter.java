package app.io;

import java.io.IOException;
import java.io.Writer;
import java.util.Locale;

public final class CsvWriter {
    private CsvWriter() {}

    public static void writeHeader(Writer out) throws IOException{
        out.write("Слово,Частота,Частота(%)\n");
    }

    public static void writeRow(Writer out, String word, int freq, long totalWords) throws IOException {
        if (totalWords == 0) return;

        double percent = (freq * 100.0) / totalWords;

        String percentStr = String.format(Locale.US, "%.6f", percent);

        out.write(word);
        out.write(",");
        out.write(Integer.toString(freq));
        out.write(",");
        out.write(percentStr);
        out.write("%\n");
    }
}
