
import java.util.ArrayList;
import java.util.List;

public class Board {

    private List<Marker> list;
    private Marker initialMarker;
    private Marker enemyMarker;
    private int width;
    private int height;
    

    public Board(int width, int height){
        this.width = width;
        this.height = height;
        list = new ArrayList<Marker>();
        initialMarker = new Marker((int) ((Math.random()*width)-1), (int)((Math.random()*height)-1));
        list.add(initialMarker);
        do{
            enemyMarker = new Marker((int) ((Math.random()*width)-1), (int)((Math.random()*height)-1));
        } while(enemyMarker.getxPos()== initialMarker.getxPos() || enemyMarker.getyPos()== initialMarker.getyPos());
        
    }

    public List<Marker> getList(){
        return list;
    }

    public void move(String state, boolean hit){
       switch(state){
           case "LEFT":
            Marker newA = new Marker(list.get(0).getxPos()-1,list.get(0).getyPos());
            list.add(0, newA);
            if(!hit) list.remove(list.size()-1);
           break;
           case "RIGHT":
            Marker newB = new Marker(list.get(0).getxPos()+1,list.get(0).getyPos());
            list.add(0, newB);
            if(!hit) list.remove(list.size()-1);
           break;
           case "UP":
            Marker newC = new Marker(list.get(0).getxPos(),list.get(0).getyPos()-1);
            list.add(0, newC);
            if(!hit) list.remove(list.size()-1);
           break;
           case "DOWN":
           Marker newD = new Marker(list.get(0).getxPos(),list.get(0).getyPos()+1);
           list.add(0, newD);
           if(!hit) list.remove(list.size()-1);
           break;
       }

    }

    public boolean checkRules(){
        if(list.get(0).getxPos() < 0 || list.get(0).getxPos() >= this.width || list.get(0).getyPos() < 0 || list.get(0).getyPos() >= this.height){
            return true;
        } 
        for(int i = 1; i < list.size(); i++){
            if(list.get(0).getxPos() ==list.get(i).getxPos() && list.get(0).getyPos() == list.get(i).getyPos()){
                return true;
            }
        }
        return false;
    }

    public void setEnemyMarker(){
        enemyMarker = new Marker((int) ((Math.random()*width)-1), (int)((Math.random()*height)-1));
        for(Marker a: list){
            if(enemyMarker.getxPos()== a.getxPos()){
                setEnemyMarker();
            }else if(enemyMarker.getyPos()== a.getyPos()){
                setEnemyMarker();
            }
        }
    }

    public Marker getEnemyMarker(){
        return enemyMarker;
    }
}