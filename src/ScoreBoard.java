

import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ScoreBoard {

    private List<Score> scores;

    public ScoreBoard() {
        scores = new ArrayList<Score>();
        try (FileReader file = new FileReader("Scores.txt");
                BufferedReader reader = new BufferedReader(file)) {
            String name = reader.readLine();
            if (name == null)
                return;
            String scoreString = reader.readLine();
            int score = Integer.parseInt(scoreString);
            scores.add(new Score(name, score));
            name = reader.readLine();
            while (name != null) {
                scoreString = reader.readLine();
                score = Integer.parseInt(scoreString);
                scores.add(new Score(name, score));
                name = reader.readLine();
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public void addScore(Score s) {
        scores.add(s);
        try (FileWriter fw = new FileWriter("Scores.txt", true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw)) {
            out.println(s.getName());
            out.println(s.getScore());
        } catch (IOException e) {
        }
    }

    public List<Score> getList() {
        return scores;
    }

    public int checkDuplicate(String name){
        for(Score a : this.scores){
            if(a.getName().equals(name)){
                return a.getScore();
            }
        }
        return -1;
    }

    public void sort() {
        int k = scores.size();
        while (k > 1) {
            int i = 0;
            while (i < k - 1) {
                if (scores.get(i).getScore() < scores.get(i + 1).getScore()) {
                    Score a = scores.get(i);
                    Score b = scores.get(i + 1);
                    scores.set(i, b);
                    scores.set(i + 1, a);
                }
                i++;
            }
            k--;
        }
    }

}