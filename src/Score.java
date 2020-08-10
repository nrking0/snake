public class Score {
    
    private int score;
    private String name;

    public Score(String name, int score){
        this.name = name;
        this.score = score;
    }

    public int getScore(){
        return this.score;
    }

    public String getName(){
        return this.name;
    }

    public void setScore(int n){
        this.score = n;
    }

}