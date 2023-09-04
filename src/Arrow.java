import java.awt.event.KeyEvent;

public class Arrow {
    private double positionX;
    private double positionY;
    private double arrowHeight;

    private boolean isArrowExists;

    public Arrow() {
        this.positionX = 0;
        this.positionY = 0;
        this.isArrowExists = false;
        this.arrowHeight = 0;
    }

    public void shootArrow(double positionX){
        if (StdDraw.isKeyPressed(KeyEvent.VK_SPACE) && !isArrowExists()){
            setArrowExists(true);
            setPositionX(positionX);
        }
    }
    public void arrowMovement(){
        if (isArrowExists()){
            if (getArrowHeight() + Environment.SCALE_Y/20 <= Environment.SCALE_Y){
                setArrowHeight(getArrowHeight() + Environment.SCALE_Y/30);
            }
            else {
                setArrowHeight(0);
                setArrowExists(false);
            }
        }
    }
    public void drawArrow(){
        if (isArrowExists()) {
            StdDraw.picture(getPositionX(), getArrowHeight() / 2, "arrow.png", 0.2, getArrowHeight());
        }
    }

    public double getPositionX() {
        return positionX;
    }

    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }

    public boolean isArrowExists() {
        return isArrowExists;
    }

    public void setArrowExists(boolean arrowExists) {
        isArrowExists = arrowExists;
    }

    public double getArrowHeight() {
        return arrowHeight;
    }

    public void setArrowHeight(double arrowHeight) {
        this.arrowHeight = arrowHeight;
    }
}
