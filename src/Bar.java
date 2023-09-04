import java.awt.*;

public class Bar {
    private double startTime;
    private double currentLength;
    private double startLength;
    private Color barColor;

    public Bar(double startTime){
        this.startTime = startTime;
        this.currentLength = Environment.SCALE_X;
        this.startLength = Environment.SCALE_X;
        this.barColor = new Color(255,255,0);

    }

    public void barMovement(double curTime){
        if (curTime - getStartTime() > Environment.TOTAL_GAME_DURATION){
            setCurrentLength(0);
        }
        else{
            setCurrentLength((getStartLength()/Environment.TOTAL_GAME_DURATION) * (Environment.TOTAL_GAME_DURATION - (curTime - getStartTime())));
            setBarColor(new Color(255, (int) ((255.0/Environment.TOTAL_GAME_DURATION) * (Environment.TOTAL_GAME_DURATION - (curTime - getStartTime()))),0));
        }

    }
    public void drawBar(){
        StdDraw.setPenColor(getBarColor());
        Color a = getBarColor();
        StdDraw.filledRectangle(getCurrentLength()/2,-0.5,getCurrentLength()/2,0.25);
    }

    public double getCurrentLength() {
        return currentLength;
    }

    public void setCurrentLength(double currentLength) {
        this.currentLength = currentLength;
    }

    public Color getBarColor() {
        return barColor;
    }

    public void setBarColor(Color barColor) {
        this.barColor = barColor;
    }

    public double getStartTime() {
        return startTime;
    }

    public void setStartTime(double startTime) {
        this.startTime = startTime;
    }

    public double getStartLength() {
        return startLength;
    }
}
