import java.awt.geom.Point2D;

public class Game {
    private GameBoard game;
    private Rabbit rabbit;
    private boolean isRunning = false;
    private int level;
    private int missHit;

    public void startGame(int row , int column){
        game =new GameBoard(row,column);
    static Music mainMusic = new Music ("sounds\\main.wav",true) , lose = new Music("sounds\\lose.wav",false) , win = new Music("sounds\\win.wav",false) , hit = new Music("sounds\\hit.wav",false);
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
