import java.util.ArrayList;

public class Ball {
    private final double MIN_POSSIBLE_RADIUS = Environment.SCALE_Y*0.0175*3;
    private int ballLevel;
    private double vX;
    private double vY;
    private double positionX;
    private double startX;
    private double positionY;
    private double startY;
    private double ballRadius;
    private double maxHeight;


    public Ball(int ballLevel, double vX, double vY, double positionX, double positionY) {
        this.ballLevel = ballLevel;
        this.vX = vX;
        this.vY = vY;
        this.positionX = positionX;
        this.startX = positionX;
        this.positionY = positionY;
        this.startY = positionY;
        this.ballRadius = MIN_POSSIBLE_RADIUS * ballLevel;
        if (ballLevel == 1){
            this.maxHeight = Player.playerHeight * 1.7;
        }
        if (ballLevel == 2){
            this.maxHeight = Player.playerHeight * 2.8;
        }
        if (ballLevel == 3){
            this.maxHeight = Player.playerHeight * 3.9;
        }

    }
    public void ballMovement(double time){
        setPositionX(getPositionX() + getvX()*time);
        if (getPositionX()>Environment.SCALE_X-ballRadius/2){
            setPositionX(Environment.SCALE_X-ballRadius/2);
            setvX(-getvX());
        }
        if (getPositionX()<ballRadius/2){
            setPositionX(ballRadius/2);
            setvX(-getvX());
        }

        setvY(getvY() - Environment.GRAVITIONAL_CONSTANT*time);
        setPositionY(getPositionY() + getvY()*time);
        if (getPositionY() + getvY()*time - ballRadius/2 < 0.0){
            setPositionY(ballRadius/2);
            setvY(Math.sqrt(2*maxHeight*Environment.GRAVITIONAL_CONSTANT));
        }



        if (getPositionY()<ballRadius/2 && (getvY()<0)){
            setPositionY(ballRadius/2);
        }



    }
    public void drawBall(){
        StdDraw.picture(getPositionX(),getPositionY(),"ball.png",getBallRadius(),getBallRadius());
    }
    public ArrayList<Ball> ballSplitter(){
        ArrayList<Ball> resLst = new ArrayList<>();
        if (getBallLevel() > 1){
            resLst.add(new Ball(getBallLevel()-1,getvX(),Math.abs(getvY()),getPositionX(),getPositionY()));
            resLst.add(new Ball(getBallLevel()-1,-getvX(),Math.abs(getvY()),getPositionX(),getPositionY()));
            return resLst;
        }
        return new ArrayList<>();
    }

    public int getBallLevel() {
        return ballLevel;
    }

    public void setBallLevel(int ballLevel) {
        this.ballLevel = ballLevel;
    }

    public double getvX() {
        return vX;
    }

    public void setvX(double vX) {
        this.vX = vX;
    }

    public double getvY() {
        return vY;
    }

    public void setvY(double vY) {
        this.vY = vY;
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

    public double getBallRadius() {
        return ballRadius;
    }

    public void setBallRadius(double ballRadius) {
        this.ballRadius = ballRadius;
    }

}
