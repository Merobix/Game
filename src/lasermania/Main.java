package lasermania;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import lasermania.lasers.VMLaser;
import lasermania.lasers.Laser;
import lasermania.lasers.HMLaser;
import lasermania.spawner.MusicSpawner;
import lasermania.spawner.ScoreSpawner;
import lasermania.spawner.ServerSpawner;
import lasermania.spawner.Spawner;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;


public class Main extends Application {

    private Stage primary;
    private Scene main;

    private Canvas canvas;
    private GraphicsContext gc;

    private TextField field1;
    private TextField field2;
    private ImageView title;

    private final boolean DEBUG = true;

    private Mode mode;
    private int fpsCounter;
    private int fps;
    private long prevTime;
    private long timeCount; //for fps calc

    private int totalFrames;

    private int spawnRate;
    private int spawnAmount;
    private int score;
    private int level;
    private Spawner spawner;

    private Server server;
    private Client client;

    private SoundMaker sounds;
    private GameLoop gameLoop;

    private Player player;
    private Player2 player2;
    private ArrayList<Laser> lasers;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primary = primaryStage;

        canvas = new Canvas(640, 480);
        gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, 640, 480);

        Pane menu = new Pane();

        main = new Scene(menu, 640, 480);

        ImageView back = new ImageView(getClass().getResource("media/backround.png").toString());
        title = new ImageView(getClass().getResource("media/Title.png").toString());
        ImageView button1 = new ImageView(getClass().getResource("media/Button.png").toString());
        ImageView button2 = new ImageView(getClass().getResource("media/bp.png").toString());
        ImageView button3 = new ImageView(getClass().getResource("media/host.png").toString());
        ImageView button4 = new ImageView(getClass().getResource("media/join.png").toString());
        field1 = new TextField();
        field2 = new TextField();

        title.setLayoutX(80);
        title.setLayoutY(20);
        button1.setLayoutX(220);
        button1.setLayoutY(140);
        button2.setLayoutX(220);
        button2.setLayoutY(240);
        button3.setLayoutX(100);
        button3.setLayoutY(340);
        button4.setLayoutX(340);
        button4.setLayoutY(340);
        field1.setLayoutX(100);
        field1.setLayoutY(400);
        field1.setMinWidth(200);
        field1.setText("Port");
        field2.setLayoutX(340);
        field2.setLayoutY(400);
        field2.setMinWidth(200);
        field2.setText("IP:Port");

        button1.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            mode = Mode.SCORE;
            gameStart();
            event.consume();
        });

        button2.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            mode = Mode.MUSIC;
            gameStart();
            event.consume();
        });

        button3.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            mode = Mode.HOST;
            checkMode();
            event.consume();
        });

        button4.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            mode = Mode.CLIENT;
            checkMode();
            event.consume();
        });

        field1.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            field1.clear();
            event.consume();
        });

        field2.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            field2.clear();
            event.consume();
        });

        menu.getChildren().addAll(back, button1, button2, button3, button4, field1, field2, title);

        title.requestFocus();

        sounds = new SoundMaker();
        sounds.initializeSounds();

        primaryStage.setTitle("Laser Mania");
        primaryStage.setScene(main);

        primaryStage.show();
    }

    private void checkMode() {

        if (mode == Mode.HOST) {
            try {
                server = new Server(Integer.parseInt(field1.getCharacters().toString()));
                Thread st = new Thread(server);
                st.setDaemon(true);
                st.start();
                gameStart();
            } catch (Exception e) {
                field1.setText("Invalid Port");
                title.requestFocus();
                return;
            }
        }
        else if (mode == Mode.CLIENT) {
            try {
                String ip = field2.getCharacters().toString();
                String[] split = ip.split(":");
                client = new Client(split[0], Integer.parseInt(split[1]), player2, this);
                Thread ct = new Thread(client);
                ct.setDaemon(true);
                ct.start();
            } catch (Exception e) {
                field2.setText("Invalid Input");
                title.requestFocus();
            }
        }
    }

    public void failedConnect() {
        field2.setText("Can't reach server");
        title.requestFocus();
    }

    public void gameStart() {

        Pane gamePane = new Pane();
        Scene gameScene = new Scene(gamePane, 640, 480);
        gameScene.setCursor(Cursor.NONE);
        gamePane.getChildren().add(canvas);
        setKeys(gameScene);
        gc.setFont(Font.font(12));

        primary.setScene(gameScene);

        totalFrames = 0;
        score = 0;

        gameLoop = new GameLoop();
        gameLoop.start();
    }

    public void gameOver() {

        player2.setActive(false);

        gameLoop.stop();

        if (mode == Mode.MUSIC)
            sounds.stopBP();

        if (mode == Mode.MUSIC || mode == Mode.SCORE)
            sounds.playDog();

        Pane pane = new Pane();
        Scene gOScene = new Scene(pane, 640, 480);

        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, 640, 480);

        gc.setFill(Color.WHITE);
        gc.setFont(Font.font(40));

        if (mode == Mode.SCORE || mode == Mode.MUSIC)
            gc.fillText("GAME OVER", 210, 200);
        else if (player.isDead())
            gc.fillText("You lost.", 230, 200);
        else
            gc.fillText("You won.", 230, 200);

        gc.setFont(Font.font(20));

        if (mode == Mode.SCORE)
            gc.fillText("Score:   " + score, 280, 240);

        ImageView retry = new ImageView(getClass().getResource("media/Retry.png").toString());
        ImageView menu = new ImageView(getClass().getResource("media/menu.png").toString());

        retry.setLayoutX(220);
        retry.setLayoutY(280);
        menu.setLayoutX(220);
        menu.setLayoutY(360);

        retry.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            sounds.stopDog();
            gameStart();
            event.consume();
        });

        menu.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            sounds.stopDog();
            primary.setScene(main);
            event.consume();
        });


        pane.getChildren().addAll(canvas, menu);

        if (mode == Mode.SCORE || mode == Mode.MUSIC)
            pane.getChildren().add(retry);

        primary.setScene(gOScene);
    }

    private class GameLoop extends AnimationTimer {

        public GameLoop() {
            player = new Player();
            lasers = new ArrayList<Laser>();

            switch (mode) {

                case SCORE:
                    spawnRate = 90;    //1.5 seconds
                    spawnAmount = 1;
                    level = 1;
                    spawner = new ScoreSpawner(player, sounds);
                    break;

                case MUSIC:
                    spawner = new MusicSpawner(player);
                    sounds.playBP();
                    break;

                case HOST:
                    spawnRate = 90;
                    player2 = new Player2();
                    server.setPlayers(player, player2);
                    spawner = new ServerSpawner(player, player2, sounds);
                    break;

                case CLIENT:
                    spawnRate = 90;
                    player2 = new Player2();
                    client.setPlayers(player, player2);
                    player2.setActive(true);

                    synchronized (client) {
                        client.notify();
                    }

                    spawner = new ServerSpawner(player, player2, sounds);
                    break;
            }
        }

        public void handle(long currentNanoTime) {

            // FPS calc
            if (DEBUG) {
                if (timeCount >= 1000000000) {
                    timeCount = 0;
                    fps = fpsCounter;
                    fpsCounter = 0;
                } else {
                    timeCount += currentNanoTime - prevTime;
                    fpsCounter++;
                }

                prevTime = currentNanoTime;
            }

            totalFrames++;

            update();

            if (player.isDead())
                gameOver();
            else if (player2 != null)
                if (player2.isActive() && player2.isDead())
                    gameOver();

            draw();
        }


        private void update() {

            int pX = player.getX();
            int pY = player.getY();
            int pR = player.getR();

            spawner.spawnLasers(totalFrames, spawnRate, lasers, level);

            //increase spawn difficulty for scoreMode
            if (mode == Mode.SCORE)
                if (totalFrames <= 3000) {
                    if (totalFrames % 600 == 0) {

                        if (spawnRate > 60)
                            spawnRate -= 10;

                        level++;

                        if (level == 5)
                            lasers.add(new VMLaser(320, 15, 60));

                        if (level == 6)
                            lasers.add(new HMLaser(320, 15, 60));

                        if (spawnAmount < 3)
                            spawnAmount++;
                    }
                }

            //hit detection + update
            player.hit(false);

            Iterator<Laser> it = lasers.iterator();

            while (it.hasNext()) {
                Laser l = it.next();
                if (l.isDamaging()) {

                    switch (l.getKind()) {

                        case V:
                            if (pY < l.getY() + l.getW() && pY + 2 * pR > l.getY() - l.getW())
                                player.hit(true);
                            break;

                        case H:
                            if (pX < l.getX() + l.getW() && pX + 2 * pR > l.getX() - l.getW())
                                player.hit(true);
                            break;

                        case C:
                            if (Math.sqrt(Math.pow(pX + pR - l.getX(), 2) + Math.pow(pY + pR - l.getY(), 2)) <= pR + l.getW())
                                player.hit(true);
                            break;
                    }
                }

                l.update();

                if (l.isOver()) {
                    it.remove();
                    score++;
                }
            }

            player.update();

        }

        private void draw() {

            // background image clears canvas
            gc.setFill(Color.BLACK);
            gc.fillRect(0, 0, 640, 480);

            for (Laser l : lasers)
                l.draw(gc);

            if (mode == Mode.HOST || mode == Mode.CLIENT)
                if (player2.isActive())
                    player2.draw(gc);
                else
                    totalFrames = 0;

            player.draw(gc);

            if (mode == Mode.SCORE) {
                gc.setFill(Color.WHITE);
                gc.fillText(Integer.toString(fps) + " FPS", 10, 20);
                gc.fillText("Score: " + score, 560, 20);
                gc.fillText("Time: " + totalFrames / 60, 560, 40);
                gc.setFont(Font.font(20));
                gc.fillText("Level: " + level, 280, 30);
                gc.setFont(Font.font(12));
                //gc.fillText("Speed:  " + String.format("%.2g",(double) 60 /  spawnRate) + " waves / sec", 250, 20);
            }

        }
    }


    private void setKeys(Scene scene) {
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case W:
                    player.setUp(true);
                    break;
                case S:
                    player.setDown(true);
                    break;
                case A:
                    player.setLeft(true);
                    break;
                case D:
                    player.setRight(true);
                    break;
                case E:
                    player.setSlow(true);
                    break;
                case UP:
                    player.setUp(true);
                    break;
                case DOWN:
                    player.setDown(true);
                    break;
                case LEFT:
                    player.setLeft(true);
                    break;
                case RIGHT:
                    player.setRight(true);
                    break;
            }
        });

        scene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case W:
                    player.setUp(false);
                    break;
                case S:
                    player.setDown(false);
                    break;
                case A:
                    player.setLeft(false);
                    break;
                case D:
                    player.setRight(false);
                    break;
                case E:
                    player.setSlow(false);
                    break;
                case UP:
                    player.setUp(false);
                    break;
                case DOWN:
                    player.setDown(false);
                    break;
                case LEFT:
                    player.setLeft(false);
                    break;
                case RIGHT:
                    player.setRight(false);
                    break;
            }
        });
    }
}
