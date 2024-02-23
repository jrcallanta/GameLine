package game.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Formatter {
    private int maxWidth;
    public Formatter (int maxWidth) {
        this.maxWidth = maxWidth;
    }

    public int getMaxWidth() {
        return maxWidth;
    }

    public void setMaxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
    }

    public void print(String s) {
        System.out.println(this.limitTextToWidth(s, this.maxWidth, 0, 0));
    }

    public static String limitLineToWidth(String s, int width) {
        StringBuilder sb = new StringBuilder();
        int marker = 0;
        int end = Math.min(marker + width, s.length());
        while (marker < end && end <= s.length()) {
            while (
                    end < s.length()
                            && s.charAt(end - 1) != ' '
                            && marker < end - 1
            ) end--;

            sb
                    .append(s, marker, end)
                    .append("\n");

            marker = end;
            end = Math.min(marker + width, s.length());
        }
        return sb.toString();
    }
    public static String limitTextToWidth(String s, int width, int firstIndent, int indent) {
        StringBuilder sb = new StringBuilder();
        List<String> groups = new ArrayList<>();

        Arrays
                .stream(s.split("\n"))
                .forEachOrdered(part -> {
                    groups.addAll(Arrays
                            .asList(limitLineToWidth(part, width)
                                    .split("\n")
                            ));
                });

        for (int i = 0; i < groups.size(); i++) {
            sb.append(" ".repeat(i == 0  ? firstIndent : indent))
                    .append(groups.get(i).trim())
                    .append("\n");
        }

        return sb.toString().stripTrailing();
    }
}
