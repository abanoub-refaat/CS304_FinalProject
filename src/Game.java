import javax.media.opengl.GL;
import java.awt.geom.Point2D;

public class Game {
    private GameBoard game;
    private Rabbit rabbit;
    private boolean isRunning = false;
    private int level;
    private int missHit;

    public void startGame(){
        game =new GameBoard(2,2);
//        rabbit=new Rabbit();//what should take??
        isRunning=true;
        while(isRunning){
            game.update(level);//level not initialize yet!!
            if (rabbit.isHit(rabbit.getPosition())){
                game.setScore(game.score++);
                //handel timer (need timer class)
                missHit=0;
            }
            else{
                missHit++;
                if(missHit==3){
                    game.setHealth(game.health--);
                    if (game.health==0) {
                        endGame();
                    }
                }
            }
        }
    }
    public void endGame(){
        isRunning=false;
    }




}
