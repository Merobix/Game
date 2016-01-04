package lasermania;

import javafx.application.Platform;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by Philip on 24.12.2015.
 */
public class Client implements Runnable {

    private Player p;
    private Player2 p2;
    private Socket client;
    private String host;
    private int port;
    private Main main;

    public Client(String host, int port, Player2 p2, Main main) {
        this.host = host;
        this.port = port;
        this.p2 = p2;
        this.main = main;
    }

    @Override
    public void run() {
        try {
            client = new Socket(host, port);
            Platform.runLater(() -> main.gameStart());
        } catch (IOException e) {
            Platform.runLater(() -> main.failedConnect());
            return;
        }

        try {

            synchronized (this) {
                wait();
            }

            ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(client.getInputStream());

            while (true) {
                oos.reset();
                oos.writeObject(p);
                Player info = (Player) ois.readObject();

                p2.setX(info.getX());
                p2.setY(info.getY());
                p2.setDead(info.isDead());

                if (p.isDead() || p2.isDead()) {
                    oos.reset();
                    oos.writeObject(p);
                    break;
                }
            }

            oos.close();
            ois.close();
            client.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setPlayers(Player p, Player2 p2) {
        this.p = p;
        this.p2 = p2;
    }
}
