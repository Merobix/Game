package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;


public class Main extends Application {

    private Stage primary;

    private Canvas canvas;
    private GraphicsContext gc;

    private Random rand;
    private final boolean DEBUG = true;

    private int fpsCounter;
    private int fps;
    private long prevTime;
    private long timeCount; //for fps calc

    private int totalFrames;

    private long spawnRate;
    private int spawnAmount;
    private int score;
    private int level;

    private SoundMaker sounds;
    private GameLoop gameLoop;

    private Player player;
    private ArrayList<Laser> lasers;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        primary = primaryStage;

        canvas = new Canvas(640,480);
        gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, 640, 480);

        Pane menu = new Pane();

        Scene scene = new Scene(menu, 640, 480);

        ImageView title = new ImageView(getClass().getResource("Title.png").toString());
        ImageView button = new ImageView(getClass().getResource("Button.png").toString());
        title.setLayoutX(80);
        title.setLayoutY(20);
        button.setLayoutX(220);
        button.setLayoutY(140);

        button.addEventHandler(MouseEvent.MOUSE_CLICKED, event ->  {
                gameStart(true);
                event.consume();
        });

        menu.getChildren().addAll(canvas, button, title);

        sounds = new SoundMaker();
        sounds.initializeSounds();

        primaryStage.setTitle("Laser Mania");
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private void gameStart(boolean scoreMode) {
        totalFrames = 0;
        score = 0;

        Pane gamePane = new Pane();
        Scene gameScene = new Scene(gamePane, 640, 480);
        gameScene.setCursor(Cursor.NONE);
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
            if (DEBUG) {
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
            gc.fillText(Integer.toString(fps) + " FPS", 10, 20);
            gc.fillText("Score: " + score, 560, 40);
            gc.fillText("Time: " + totalFrames / 60, 560, 80);

            update();

            if (player.isDead())
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

            sounds.playLaser();

            int r = rand.nextInt(6);
            switch (level) {

                case 1:
                    if (r < 3)
                        lasers.add(new HLaser(pX + pR, 10, 38));
                    else
                        lasers.add(new VLaser(pY + pR, 10, 38));
                    break;

                case 2:
                    if (r < 2) {
                        int temp = pX - 40 + rand.nextInt(80);
                        lasers.add(new HLaser(temp, 10, 38));

                        temp = temp < pX ? temp + 20 + rand.nextInt(40 + (pX - temp - 20)) : temp - 20 - rand.nextInt(40 + (temp - pX - 20));

                        lasers.add(new HLaser(temp, 10, 38));
                    }

                    else if (r < 4) {
                        int temp = pY - 40 + rand.nextInt(80);
                        lasers.add(new VLaser(temp, 10, 38));

                        temp = temp < pY ? temp + 20 + rand.nextInt(40 + (pY - temp - 20)) : temp - 20 - rand.nextInt(40 - (temp - pY - 20));

                        lasers.add(new VLaser(temp, 10, 38));
                    }

                    else {
                        lasers.add(new HLaser(pX - 40 + rand.nextInt(80), 10, 38));
                        lasers.add(new VLaser(pY - 40 + rand.nextInt(80), 10, 38));
                    }
                    break;

                case 6:
                case 5:
                case 4:
                    lasers.add(new CLaser(pX, pY, 40, 38));

                case 3:
                    if (r < 3) {
                        int temp = pX - 80 + rand.nextInt(160);
                        lasers.add(new HLaser(temp, 10, 38));

                        temp = temp < pX ? temp + 20 + rand.nextInt(80 + (pX - temp - 20)) : temp - 20 - rand.nextInt(80 + (temp - pX - 20));

                        lasers.add(new HLaser(temp, 10, 38));

                        lasers.add(new VLaser(pY - 80 + rand.nextInt(160), 10, 38));
                    }
                    else {
                        int temp = pY - 80 + rand.nextInt(160);
                        lasers.add(new VLaser(temp, 10, 38));

                        temp = temp < pY ? temp + 20 + rand.nextInt(80 + (pY - temp - 20)) : temp - 20 - rand.nextInt(80 - (temp - pY - 20));

                        lasers.add(new VLaser(temp, 10, 38));

                        lasers.add(new HLaser(pX - 80 + rand.nextInt(160), 10, 38));
                    }
            }
        }

        //increase spawn difficulty
        if (totalFrames <= 3000) {
            if (totalFrames % 600 == 0) {

                if (spawnRate > 60)
                    spawnRate -= 10;

                level++;

                if (level == 5)
                    lasers.add(new HMLaser(320, 15, 60));

                if (level == 6)
                    lasers.add(new VMLaser(320, 15, 60));

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

        sounds.playDog();

        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, 640, 480);
        gc.setFill(Color.WHITE);
        gc.fillText("GAME OVER", 280, 200);
        gc.fillText("Score:     " + score, 280, 240);

        ImageView retry = new ImageView(getClass().getResource("Retry.png").toString());
        retry.addEventHandler(MouseEvent.MOUSE_CLICKED, event ->  {
                sounds.stopDog();
                gameStart(true);
                event.consume();
        });
        retry.setLayoutX(220);
        retry.setLayoutY(300);


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
                case E: player.setSlow(true);
                    break;
                case UP: player.setUp(true);
                    break;
                case DOWN: player.setDown(true);
                    break;
                case LEFT: player.setLeft(true);
                    break;
                case RIGHT: player.setRight(true);
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
                case E: player.setSlow(false);
                    break;
                case UP: player.setUp(false);
                    break;
                case DOWN: player.setDown(false);
                    break;
                case LEFT: player.setLeft(false);
                    break;
                case RIGHT: player.setRight(false);
                    break;
            }
        });
    }
}
