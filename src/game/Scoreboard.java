package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Scoreboard {
    final int MAX_WIDTH = 58;
    static private Score latest;
    final private HashMap<String, List<Score>> scoreboard;


    public Scoreboard () {
        this.scoreboard = new HashMap<>();
        latest = null;
    }

    public List<Score> addScore (String gameName, Score score) {
        List<Score> scores = scoreboard.getOrDefault(gameName, new ArrayList<>());
        scores.add(score);
        scoreboard.put(gameName, scores);
        latest = score;
        return scores;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String gameName : this.scoreboard.keySet()) {
            sb
            .append(printPretty(gameName))
            .append("\n\n");
        }
        return sb.toString();
    }

    public void print() {
        System.out.println(this);
    }

    private String printPretty(String gameName) {
        System.out.println();
        int width = Math.max(gameName.length(), MAX_WIDTH);
        StringBuilder sb = new StringBuilder();
        List<Score> scores = scoreboard.get(gameName);

        sb
        .append(String.format("|%" + width/2 + "s%-" + width/2 + "s|\n",
                gameName.substring(0,gameName.length()/2),
                gameName.substring(gameName.length()/2)

        ))
        .append(String.format("|%" + width + "s|\n", ""));

        if (scores == null) {
            sb
            .append(String.format("|%" + width/2 + "s%-" + width/2 + "s|\n", "No Scores"))
            .append(String.format("|%" + width + "s|\n", ""));
            return sb.toString();
        }

        scores.sort(Comparable::compareTo);
        for(int i = 0; i < scores.size(); i++) {
            String s = String.format("%2d.%s", i+1, scores.get(i));
            sb
            .append(String.format("|%" + width/2 + "s%-" + width/2 + "s|",
                    s.substring(0, s.length()/2),
                    s.substring(s.length()/2)

            ))
            .append(scores.get(i) == latest ? "  <--\n" : "\n" );
        }
        System.out.printf("|%" + width + "s|\n","");

        return sb.toString();
    }
}
