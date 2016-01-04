package lasermania;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Philip on 23.12.2015.
 */
public class Server implements Runnable{

    private ServerSocket ss;
    private Player p;
    private Player2 p2;

    public Server(int port) throws Exception {
        ss = new ServerSocket(port);
    }

    @Override
    public void run() {
        try {
            Socket s = ss.accept();
            p2.setActive(true);

            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(s.getInputStream());

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
            s.close();
            ss.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setPlayers(Player p, Player2 p2) {
        this.p = p;
        this.p2 = p2;
    }
}
