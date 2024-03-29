package game.scoring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Scoreboard {
    final int MAX_WIDTH = 48;
    static private Score latest;
    final private HashMap<String, List<Score>> scoreboard;


    public Scoreboard () {
        this.scoreboard = new HashMap<>();
        latest = null;
    }

    public List<Score> addScore (String gameName, Score score) {
        List<Score> scores = this.scoreboard.getOrDefault(gameName, new ArrayList<>());
        scores.add(score);
        this.scoreboard.put(gameName, scores);
        latest = score;
        return scores;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String gameName : this.scoreboard.keySet()) {
            sb
            .append(pretty(gameName))
            .append("\n\n");
        }
        return sb.toString();
    }

    public void printAll() {
        System.out.println(this);
    }

    public void printByGame(String gameName) {
        System.out.println(pretty(gameName));
    }

    private String pretty(String gameName) {
        int width = Math.max(gameName.length(), MAX_WIDTH);
        StringBuilder sb = new StringBuilder();
        List<Score> scores = this.scoreboard.get(gameName);

        String lined = String.format("*%" + width + "s*\n", "").replace(" ", "-");
        sb
        .append("\n")
        .append(lined)
        .append(String.format("|%" + width/2 + "s%-" + width/2 + "s|\n",
                gameName.substring(0,gameName.length()/2),
                gameName.substring(gameName.length()/2)

        ))
        .append(lined);



        if (scores == null) {
            String noScores = "No Scores";
            sb
            .append(String.format("|%" + width/2 + "s%-" + width/2 + "s|\n",
                    noScores.substring(0, noScores.length()/2),
                    noScores.substring(noScores.length()/2)
            ))
            .append(lined);
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
        sb.append(lined);

        return sb.toString();
    }
}
