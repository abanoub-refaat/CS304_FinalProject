
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.*;
public class GameBoard {
    private ArrayList<Rabbit> rabbits;
    private String backGround;
    private int score;
    private int timeRemaining;
    private int health;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.*;

public class GameBoard {
    private ArrayList<Rabbit> rabbits;
    private String background;
    public int score;
    public int timeRemaining;
    public int health;
    
    public GameBoard(int row , int column) {
        this.rabbits = new ArrayList<>();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                Point2D p = new Point(i,j);
                rabbits.add(new Rabbit(p,false));
            }
        }
<<<<<<< HEAD
        this.backGround = "active";
        this.score = 0;
        this.timeRemaining = 100;
        this.health = 5 ;
    }
=======

        this.background = "active";
        this.score = 0;
        this.timeRemaining = 100;
        this.health = 3 ;
    }

>>>>>>> b84ff79fd8e2e3a029dfcd9cfc2e23db70cf284b
    public void update(int level) {
        for (Rabbit rabbits : rabbits) {
            if (Math.random()*level < 0.1)
                rabbits.show();
            else
                rabbits.hide();
        }

    }
    
    public boolean CheckWin(int score , int timeRemaining ,int health) {
        return (score >=1 && timeRemaining== 0 && health!=0);
    }

    public boolean CheckLose(int score , int timeRemaining ,int health) {
        return (score ==0 && timeRemaining== 0) || health==0;
    }


    public String DisplayBackground(int score, int timeRemaining, int health) {

        if (CheckWin(score, timeRemaining, health)) {
            return "win";
        }
        else if (CheckLose(score, timeRemaining, health)) {
            return "lose";
        }
        else {
            return "active";
        }
    }

>>>>>>> b84ff79fd8e2e3a029dfcd9cfc2e23db70cf284b
    public void setScore(int score) {
        this.score = score;
    }

    public void setTimeRemaining(int timeRemaining) {
        this.timeRemaining = timeRemaining;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
