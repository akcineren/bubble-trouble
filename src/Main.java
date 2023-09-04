public class Main {
    public static void main(String[] args) {
        Environment env = new Environment("arrow.png","background.png","ball.png","bar.png","game_screen.png","player_back.png");
        env.initializeCanvas();
        env.gameLoop();
    }
}