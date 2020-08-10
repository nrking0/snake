
class Marker{

    private int xPos;
    private int yPos;

    public Marker(int x, int y){
        this.xPos=x;
        this.yPos=y;
    }

    public int getxPos(){
        return this.xPos;
    }

    public int getyPos(){
        return yPos;
    }

    public void setxPos(int x){
        this.xPos= x;
    }

    public void setyPos(int y){
        this.yPos= y;
    }
}