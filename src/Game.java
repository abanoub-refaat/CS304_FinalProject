import java.awt.geom.Point2D;

public class Game {
    private GameBoard game;
    private Rabbit rabbit;
    private boolean isRunning = false;
    private int level;
    private int missHit;
    mainMusic = new Music("sounds//Clement Panchout _ Journey _ 2017.wav", true);

    public void startGame(){
        game =new GameBoard(2,2);
        isRunning=true;
        while(isRunning){
            game.update(level);
            if (rabbit.isHit(rabbit.getPosition())){
                game.setScore(game.score++);
                missHit=0;
            } else{
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
