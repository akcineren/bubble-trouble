import java.awt.event.KeyEvent;

public class Player {
    private final double vX;
    private final String playerbackPngName;
    public static double playerHeight;
    private final double playerWidth;
    private final double yCoordinate;
    private double xCoordinate;



    public Player(double vX, String playerbackPngName, double playerHeight, double xCoordinate) {
        this.vX = vX;
        this.playerbackPngName = playerbackPngName;
        Player.playerHeight = playerHeight;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = playerHeight/2;
        this.playerWidth = (playerHeight*27)/37;
    }
    public void playerMove(){
        if (StdDraw.isKeyPressed(KeyEvent.VK_RIGHT) && (xCoordinate + vX + playerWidth/2 < Environment.SCALE_X)){
            setxCoordinate(xCoordinate + vX);
        }
        if (StdDraw.isKeyPressed(KeyEvent.VK_LEFT) && (xCoordinate - vX - playerWidth/2 > 0.0)){
            setxCoordinate(xCoordinate - vX);
        }
    }
    public void drawPlayer(){
        StdDraw.picture(xCoordinate,yCoordinate,playerbackPngName,playerWidth,playerHeight);
    }

    public double getvX() {
        return vX;
    }

    public double getxCoordinate() {
        return xCoordinate;
    }

    public void setxCoordinate(double xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public static double getPlayerHeight() {
        return playerHeight;
    }

    public static void setPlayerHeight(double playerHeight) {
        Player.playerHeight = playerHeight;
    }

    public double getPlayerWidth() {
        return playerWidth;
    }
}
