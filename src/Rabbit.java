import java.awt.geom.Point2D;

public class Rabbit {
    private Point2D position;
    private boolean isVisible;


    public Rabbit(Point2D position, boolean isVisible) {
        this.position = position;
        this.isVisible = isVisible;
    }

    public Point2D getPosition() { return position; }
    public void setPosition(Point2D position) { this.position = position; }
    public boolean isVisible() { return isVisible; }

    public void show(){
        isVisible = true;
    }

    public void hide(){
        isVisible = false;
    }

    public boolean isHit(Point2D clickedPoint){
        return position.equals(clickedPoint);
    }
}
