package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;


public class Main extends Application {

    private Canvas canvas;
    private GraphicsContext gc;
    private Pane root;


    private Random rand;

    private int fpsCounter;
    private int fps;
    private long prevTime;
    private long timeCount; //for fps calc
    private int totalFrames;

    private long spawnRate;
    private int spawnAmount;
    private int score;
    private GameLoop gameLoop;

    private Player player;
    private ArrayList<Laser> lasers;
    private boolean running;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        root = new Pane();
        Scene scene = new Scene(root, 640, 480);

        canvas = new Canvas(640,480);
        gc = canvas.getGraphicsContext2D();
        scene.setFill(Color.BLACK);

        root.getChildren().add(canvas);

        primaryStage.setTitle("Game");
        primaryStage.setScene(scene);

        setKeys(scene);

        player = new Player();
        lasers = new ArrayList<Laser>();
        spawnRate = 120;    //2 seconds
        spawnAmount = 1;
        rand = new Random();

        gameLoop = new GameLoop();
        gameLoop.start();

        primaryStage.show();
    }

    private class GameLoop extends AnimationTimer {
        public void handle(long currentNanoTime)
        {

            // FPS calc
            if (timeCount > 1000000000) {
                timeCount = 0;
                fps = fpsCounter;
                fpsCounter = 0;
            }
            else {
                timeCount += currentNanoTime - prevTime;
                fpsCounter++;
            }

            prevTime = currentNanoTime;
            totalFrames++;

            // background image clears canvas
            gc.clearRect(0, 0, 640, 480);
            gc.setFill(Color.WHITE);
            gc.fillText(Integer.toString(fps), 10, 20);
            gc.fillText("Score: " + Integer.toString(score), 560, 40);

            update();

            if (player.isDead)
                gameOver();
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

            int[] pos = {rand.nextInt(160)};

            for (int i = 0; i < spawnAmount; i++) {

                int r = rand.nextInt(5);

                if (r <= 2)
                    lasers.add(new Laser(pX - 80 + rand.nextInt(160) + 10, 0, 8, 1));
                else if (r <= 4)
                    lasers.add(new Laser(0, pY - 50 + rand.nextInt(101), 10, 8));
                //else
                    //lasers.add(new Laser(pX - 80 + rand.nextInt(160), pY - 50 + rand.nextInt(101), 50, 3));
            }
        }

        //increase spawn Rate
        if (totalFrames <= 1800) {
            if (totalFrames % 600 == 0) {
                spawnRate *= 0.65;
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
                    if (Math.sqrt((pX - l.getX()) * (pX - l.getX())) + ((pY - l.getY()) * (pY - l.getY())) <= pR + l.getW())
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

    private void gameOver() {

        gc.clearRect(0, 0, 640, 480);
        gc.setFill(Color.WHITE);
        gc.fillText("GAME OVER", 280, 200);
        gc.fillText("Score:     " + score, 280, 300);

        gameLoop.stop();
    }

    private void draw() {

        for (Laser l : lasers)
            l.draw(gc);

        player.draw(gc);

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
