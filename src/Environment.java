import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import static javax.swing.text.StyleConstants.Bold;

public class Environment {
    private final int CANVAS_WIDTH = 800;
    private final int CANVAS_HEIGHT = 500;
    public static final int TOTAL_GAME_DURATION = 40000;
    private final int PAUSE_DURATION = 15;

    public static final double SCALE_X = 16.0;
    public static final double SCALE_Y = 9.0;
    public static final double GRAVITIONAL_CONSTANT = 0.000003 * Environment.SCALE_Y;
    private String arrowPngName;
    private String backgroundPngName;
    private String ballPngName;
    private String barPngName;
    private String gamescreenPngName;
    private String playerbackPngName;

    public Environment(String arrowPngName, String backgroundPngName, String ballPngName, String barPngName, String gamescreenPngName, String playerbackPngName) {
        this.arrowPngName = arrowPngName;
        this.backgroundPngName = backgroundPngName;
        this.ballPngName = ballPngName;
        this.barPngName = barPngName;
        this.gamescreenPngName = gamescreenPngName;
        this.playerbackPngName = playerbackPngName;
    }
    public void gameRestarter(int ballListSize){
        StdDraw.picture(SCALE_X/2,SCALE_Y/2.18,gamescreenPngName,SCALE_X/3.8,SCALE_Y/4);
        StdDraw.setFont(new Font("Helvetica", Font.BOLD, 30));
        StdDraw.setPenColor(StdDraw.BLACK);
        if (ballListSize == 0){
            StdDraw.text(SCALE_X/2,SCALE_Y/2,"You Won!");
        }
        else {
            StdDraw.text(SCALE_X/2,SCALE_Y/2,"Game Over!");
        }

        StdDraw.setFont(new Font("Helvetica", Font.ITALIC, 15));
        StdDraw.text(SCALE_X/2,SCALE_Y/2.3,"To Replay Click “Y”");
        StdDraw.text(SCALE_X/2,SCALE_Y/2.6,"To Quit Click “N”");
        StdDraw.show();
        while (true) {
            if (StdDraw.isKeyPressed(KeyEvent.VK_Y)) {
                gameLoop();
                break;
            }
            if (StdDraw.isKeyPressed(KeyEvent.VK_N)){
                System.exit(1);
                break;
            }
        }
    }

    public void gameLoop(){
        Player player = new Player((SCALE_X*3.5)/(6000/PAUSE_DURATION),playerbackPngName,SCALE_Y/8,SCALE_X/2);
        Ball trialBall = new Ball(1,Environment.SCALE_X/6000,0,Environment.SCALE_X/2,Environment.SCALE_Y-2);
        Ball trialBall2 = new Ball(3,-Environment.SCALE_X/6000,0,Environment.SCALE_X/2,Environment.SCALE_Y-2);
        Ball trialBall3 = new Ball(2,Environment.SCALE_X/6000,0,Environment.SCALE_X/2,Environment.SCALE_Y-2);
        Bar bar = new Bar(System.currentTimeMillis());
        Arrow arrow = new Arrow();
        ArrayList<Ball> ballList = new ArrayList<>();
        ballList.add(trialBall);
        ballList.add(trialBall2);
        ballList.add(trialBall3);

        while (true){

            double startTime = System.currentTimeMillis();
            StdDraw.clear();
            drawBackground();
            arrowMoveAndDraw(arrow,player.getxCoordinate());
            playerMovement(player);
            double endTime = System.currentTimeMillis();
            double timeDelta = endTime - startTime;
            bar.barMovement(endTime);
            bar.drawBar();
            ballArrowIntersections(ballList,arrow);
            ballsMoves(ballList,timeDelta);;
            StdDraw.show();

            if (ballList.size() == 0 || ballPlayerIntersections(ballList,player) || bar.getCurrentLength() == 0){
                gameRestarter(ballList.size());
                break;
            }
            StdDraw.pause(PAUSE_DURATION);


        }



    }

    public void initializeCanvas() {
        StdDraw.setCanvasSize(CANVAS_WIDTH,CANVAS_HEIGHT);
        StdDraw.setXscale(0.0,SCALE_X);
        StdDraw.setYscale(-1.0,SCALE_Y);
        StdDraw.enableDoubleBuffering();


    }
    public void drawBackground(){
        StdDraw.picture(SCALE_X/2,SCALE_Y/2,backgroundPngName,SCALE_X,SCALE_Y);
        StdDraw.picture(SCALE_X/2,-1.0/2,barPngName,SCALE_X,1.0);
    }
    public void playerMovement(Player player){
        player.playerMove();
        player.drawPlayer();
    }
    public void arrowMoveAndDraw(Arrow arrow, double positionX){
        arrow.shootArrow(positionX);
        arrow.arrowMovement();
        arrow.drawArrow();

    }
    public void ballsMoves(ArrayList<Ball> ballList, double timeDelta){
        for (Ball ball: ballList){
            ball.ballMovement(timeDelta);
            ball.drawBall();
        }
    }

    public void ballArrowIntersections(ArrayList<Ball> ballList, Arrow arrow){
        if (arrow.isArrowExists()){
            ArrayList<Ball> tempLst = new ArrayList<>();
            for (Ball ball: ballList){
                double xDiff = ball.getPositionX() - arrow.getPositionX();
                double yDiff = ball.getPositionY() - arrow.getPositionY()*2;
                double distBetweenTipAndCenter = Math.sqrt(xDiff*xDiff + yDiff*yDiff);
                double rightestSideOfBall = ball.getPositionX() + ball.getBallRadius()/2;
                double leftestSideOfBall = ball.getPositionX() - ball.getBallRadius()/2;
                boolean isRightIntersects = (ball.getPositionX()<arrow.getPositionX() && rightestSideOfBall>arrow.getPositionX() && arrow.getArrowHeight()>ball.getPositionY());
                boolean isLeftIntersects = (ball.getPositionX()>arrow.getPositionX() && leftestSideOfBall<arrow.getPositionX() && arrow.getArrowHeight()>ball.getPositionY());
                boolean isTipIntersects = distBetweenTipAndCenter < ball.getBallRadius()/2;
                if (isTipIntersects || isRightIntersects || isLeftIntersects){
                    tempLst.addAll(ball.ballSplitter());
                    ballList.remove(ball);
                    arrow.setArrowExists(false);
                    arrow.setArrowHeight(0);
                    break;
                }
            }
            ballList.addAll(tempLst);
        }
    }

    public boolean ballPlayerIntersections(ArrayList<Ball> ballList, Player player){
        double leftestSideOfThePlayer = player.getxCoordinate() - player.getPlayerWidth()/2;
        double rightestSideOfThePlayer = player.getxCoordinate() + player.getPlayerWidth()/2;

        double leftTipX = leftestSideOfThePlayer;
        double rightTipX = rightestSideOfThePlayer;
        double leftTipY = Player.playerHeight;
        double rightTipY = Player.playerHeight;

        for(Ball ball : ballList){
            boolean isTopIntersects = (ball.getPositionY() - ball.getBallRadius()/2 < Player.playerHeight) && (ball.getPositionX() > rightestSideOfThePlayer && ball.getPositionX() < leftestSideOfThePlayer);
            boolean isLeftIntersects = (ball.getPositionX() < player.getxCoordinate()) && (ball.getPositionX() + ball.getBallRadius()/2 > leftestSideOfThePlayer) && (ball.getPositionY() < Player.playerHeight);
            boolean isRightIntersects = (ball.getPositionX() > player.getxCoordinate()) && (ball.getPositionX() - ball.getBallRadius()/2 < rightestSideOfThePlayer) && (ball.getPositionY() < Player.playerHeight);
            boolean isLeftTipIntersects = (Math.sqrt((leftTipX-ball.getPositionX()) * (leftTipX-ball.getPositionX()) + (leftTipY-ball.getPositionY()) * (leftTipY-ball.getPositionY())) < ball.getBallRadius()/2);
            boolean isRightTipIntersects = (Math.sqrt((rightTipX-ball.getPositionX()) * (rightTipX-ball.getPositionX()) + (rightTipY-ball.getPositionY()) * (rightTipY-ball.getPositionY())) < ball.getBallRadius()/2);

            if (isTopIntersects || isLeftIntersects || isRightIntersects || isLeftTipIntersects || isRightTipIntersects){
                return true;

            }
        }
        return false;
    }

}
