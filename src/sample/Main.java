package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class Main extends Application {

    private Canvas canvas;
    private GraphicsContext gc;
    private Pane root;

    private int fpsCounter;
    private int fps;
    private long prevTime;
    private long timeCount; //for fps calc

    private Player player;
    private Laser laser;
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
        laser = new Laser(100, 0 , 16, false);

        prevTime = System.nanoTime();

        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {

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

                // background image clears canvas
                gc.clearRect(0, 0, 640, 480);
                gc.setFill(Color.WHITE);
                gc.fillText(Integer.toString(fps), 10, 20);

                update();
                draw();
            }
        }.start();

        primaryStage.show();
    }


    private void update() {

        player.update();
        laser.update();

    }

    private void draw() {

        player.draw(gc);
        laser.draw(gc);

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
