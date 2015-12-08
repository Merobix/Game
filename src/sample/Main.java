package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioPermission;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;


public class Main extends Application {

    private Stage primary;
    private Scene menuScene;

    private Canvas canvas;
    private GraphicsContext gc;

    private Random rand;
    private boolean debug = true;

    private int fpsCounter;
    private int fps;
    private long prevTime;
    private long timeCount; //for fps calc
    private int totalFrames;

    private final int SPAWN_INTERVAL = 100;
    private long spawnRate;
    private int spawnAmount;
    private int score;
    private int level;

    private GameLoop gameLoop;

    private Player player;
    private ArrayList<Laser> lasers;
    private boolean running;

    public static Clip laser;
    public static AudioInputStream ais;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        //InputStream song = this.getClass().getClassLoader().getResourceAsStream("/Game/laser.mp3");
        //System.out.println(song);
        //AudioInputStream ais = AudioSystem.getAudioInputStream(song);
        //laser = AudioSystem.getClip();
        //Main.ais = ais;

        primary = primaryStage;

        canvas = new Canvas(640,480);
        gc = canvas.getGraphicsContext2D();

        Pane menu = new Pane();

        Scene scene = new Scene(menu, 640, 480);
        Button b1 = new Button();
        Button b2 = new Button();

        b1.setText("Score Mode");
        b2.setText("Music Mode");

        b1.setPrefSize(100, 50);
        b1.setLayoutX(270);
        b1.setLayoutY(100);
        b1.setOnAction(event -> gameStart(true));

        b2.setPrefSize(100, 50);
        b2.setLayoutX(270);
        b2.setLayoutY(200);
        b2.setOnAction(event -> gameStart(false));

        menu.getChildren().addAll(canvas, b1);

        primaryStage.setTitle("Game");
        primaryStage.setScene(scene);

        //AudioClip music = new AudioClip("file:music.mp3");
        //music.setVolume(0.05);
        //music.play();

        primaryStage.show();
    }

    private void gameStart(boolean scoreMode) {
        totalFrames = 0;
        score = 0;

        Pane gamePane = new Pane();
        Scene gameScene = new Scene(gamePane, 640, 480);
        setKeys(gameScene);

        gamePane.getChildren().add(canvas);

        primary.setScene(gameScene);

        gameLoop = new GameLoop(scoreMode);
        gameLoop.start();
    }

    private class GameLoop extends AnimationTimer {

        private boolean scoreMode;

        public GameLoop(boolean scoreMode){
            player = new Player();
            lasers = new ArrayList<Laser>();
            rand = new Random();
            this.scoreMode = scoreMode;

            if (scoreMode) {
                spawnRate = 90;    //1.5 seconds
                spawnAmount = 1;
                level = 1;
            }
        }

        public void handle(long currentNanoTime) {

            // FPS calc
            if (debug) {
                if (timeCount > 1000000000) {
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

            // background image clears canvas
            gc.setFill(Color.BLACK);
            gc.fillRect(0, 0, 640, 480);
            gc.setFill(Color.WHITE);
            gc.fillText(Integer.toString(fps), 10, 20);
            gc.fillText("Score: " + Integer.toString(score), 560, 40);

            update();

            if (player.isDead)
                gameOver(scoreMode);
            else
                draw();
        }
    }


    private void update() {

        int pX = player.getX();
        int pY = player.getY();
        int pR = player.getR();

        //spawn lasers
        if (totalFrames % spawnRate == 0) {
            int r = rand.nextInt(6);
            switch (level) {

                case 1:
                    if (r < 3)
                        lasers.add(new Laser(pX + pR, 0, 10, 1, true));
                    else
                        lasers.add(new Laser(0, pY + pR, 10, 2, true));
                    break;

                case 2:
                    if (r < 2) {
                        int temp = pX - 40 + rand.nextInt(80);
                        lasers.add(new Laser(temp, 0, 10, 1, true));

                        temp = temp < pX ? temp + 20 + rand.nextInt(40 + (pX - temp - 20)) : temp - 20 - rand.nextInt(40 + (temp - pX - 20));

                        lasers.add(new Laser(temp, 0, 10, 1, false));
                    }

                    else if (r < 4) {
                        int temp = pY - 40 + rand.nextInt(80);
                        lasers.add(new Laser(0, temp, 10, 2, true));

                        temp = temp < pY ? temp + 20 + rand.nextInt(40 + (pY - temp - 20)) : temp - 20 - rand.nextInt(40 - (temp - pY - 20));

                        lasers.add(new Laser(0, temp, 10, 2, false));
                    }

                    else {
                        lasers.add(new Laser(pX - 40 + rand.nextInt(80), 0, 10, 1, true));
                        lasers.add(new Laser(0, pY - 40 + rand.nextInt(80), 10, 2, false));
                    }
                    break;

                case 6:
                case 5:
                case 4:
                    lasers.add(new Laser(pX, pY, 40, 3, false));

                case 3:
                    if (r < 3) {
                        int temp = pX - 80 + rand.nextInt(160);
                        lasers.add(new Laser(temp, 0, 10, 1, true));

                        temp = temp < pX ? temp + 20 + rand.nextInt(80 + (pX - temp - 20)) : temp - 20 - rand.nextInt(80 + (temp - pX - 20));

                        lasers.add(new Laser(temp, 0, 10, 1, false));

                        lasers.add(new Laser(0, pY - 80 + rand.nextInt(160), 10, 2, false));
                    }
                    else {
                        int temp = pY - 80 + rand.nextInt(160);
                        lasers.add(new Laser(0, temp, 10, 2, true));

                        temp = temp < pY ? temp + 20 + rand.nextInt(80 + (pY - temp - 20)) : temp - 20 - rand.nextInt(80 - (temp - pY - 20));

                        lasers.add(new Laser(0, temp, 10, 2, false));

                        lasers.add(new Laser(pX - 80 + rand.nextInt(160), 0, 10, 1, false));
                    }
            }
        }

        //increase spawn Rate
        if (totalFrames <= 2400) {
            if (totalFrames % 600 == 0) {
                spawnRate -= 15;

                if (level <= 6)
                    level++;

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

                int kind = l.getLKind();

                if (kind == 2) {
                    if (pY < l.getY() + l.getW() && pY + 2 * pR > l.getY() - l.getW())
                        player.hit(true);
                }
                else if (kind == 1) {
                    if (pX < l.getX() + l.getW() && pX + 2 * pR > l.getX() - l.getW())
                        player.hit(true);
                }
                else
                    if (Math.sqrt(Math.pow(pX + pR - l.getX(), 2) + Math.pow(pY + pR - l.getY(), 2)) <= pR + l.getW())
                        player.hit(true);
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

        for (Laser l : lasers)
            l.draw(gc);

        player.draw(gc);
        gc.setFill(Color.WHITE);
        gc.fillText("Level: " + level, 560, 60);
        gc.fillText("Speed:  " + String.format("%.2g",(double) 60 /  spawnRate) + " waves / sec", 250, 20);


    }

    private void gameOver(boolean scoreMode) {

        gameLoop.stop();

        Pane pane = new Pane();
        Scene gOScene = new Scene(pane, 640, 480);

        //AudioClip dogSong = new AudioClip("file:dog.mp3");
        //dogSong.setVolume(0.2);
        //dogSong.play();

        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, 640, 480);
        gc.setFill(Color.WHITE);
        gc.fillText("GAME OVER", 280, 200);
        gc.fillText("Score:     " + score, 280, 240);

        Button retry = new Button();
        retry.setText("Retry");
        retry.setPrefSize(100, 50);
        retry.setLayoutX(265);
        retry.setLayoutY(300);
        retry.setOnAction(event -> {
            //dogSong.stop();
            gameStart(scoreMode);
        });

        pane.getChildren().addAll(canvas, retry);
        primary.setScene(gOScene);
    }

    private void setKeys(Scene scene) {
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case W: player.setUp(true);
                    break;
                case S: player.setDown(true);
                    break;
                case A: player.setLeft(true);
                    break;
                case D: player.setRight(true);
                    break;
            }
        });

        scene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case W: player.setUp(false);
                    break;
                case S: player.setDown(false);
                    break;
                case A: player.setLeft(false);
                    break;
                case D: player.setRight(false);
                    break;
            }
        });
    }
}
