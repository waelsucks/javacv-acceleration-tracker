package JavaCVTest;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;

import org.bson.Document;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.highgui.HighGui;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

public class GameController extends Thread {

    private VideoCapture camera;
    private DeepNeuralNetworkProcessor processor;
    private boolean isGameRunning = false;
    private Game game;
    private MongoClient mongoClient;
    private String username;

    public GameController(MongoClient mongoClient, String string) {

        processor = new DeepNeuralNetworkProcessor();
        camera = new VideoCapture(0);
        this.mongoClient = mongoClient;
        this.username = string;

    }

    @Override
    public void run() {

        Mat frame = new Mat();
        isGameRunning = true;

        game = new Game();
        game.startGame();

        Music music = new Music();

        while (isGameRunning) {

            camera.read(frame);

            List<DnnObject> detectObject = processor.getObjectsInFrame(frame, false);

            try {

                DnnObject person = detectObject.stream().filter(o -> o.getObjectName().equals("person"))
                        .findFirst()
                        .get();

                Person.getInstance("Player").setPersonRect(new Rect(person.getLeftBottom(), person.getRightTop()));
                Person.getInstance("Player").show(frame);

                // ------------------------------------------
                // GAME LOGIC

                if (Person.getInstance("Player").isMoving() && !music.getMp().getRunningStatus() ) {
                    game.addMistake();
                } else if (!Person.getInstance("Player").isMoving() && music.getMp().getRunningStatus()) {
                    game.addMistake();
                } else {
                    game.setMistakesCount(0);
                }

                if (game.getMistakesCount() >= 10) {
                    stopGame();
                    music.getMp().stopClip();
                    music.getMp().setRunning(false);
                    music.getMp().setPlaying(false);
                }

                // ------------------------------------------

                HighGui.imshow("Player", frame);
                HighGui.waitKey(1);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    public void stopGame() {

        isGameRunning = false;

        // HighGui.destroyWindow("Player");

        game.endGame();
        System.out.println(game.calculateScore());

        mongoClient.getDatabase("stats").getCollection("games")
                .insertOne(new Document("score", game.calculateScore()).append("player", username));

    }

}
