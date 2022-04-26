package JavaCVTest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.changestream.FullDocument;

import org.bson.Document;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

public class App {

    public static void main(String[] args) throws IOException {

        // MongoClient mongoClient = MongoClients.create(
        // "mongodb+srv://whaam:B-oop123@project2022.yskak.mongodb.net/myFirstDatabase?retryWrites=true&w=majority");

        // System.out.println("Connected to MongoDB");

        // MongoDatabase database = mongoClient.getDatabase("configuration");
        // MongoCollection<Document> collection = database.getCollection("setup");

        // var watchCursor = collection.watch()
        // .fullDocument(FullDocument.UPDATE_LOOKUP);

        // while (true) {

        // var doc = watchCursor.first();

        // if ((boolean) doc.getFullDocument().get("status")) {

        // GameController gameController = new GameController(mongoClient, (String)
        // doc.getFullDocument().get("username"));
        // gameController.start();
        // System.out.println(doc.getFullDocument().get("username") + " has joined the
        // game!");

        // collection.findOneAndUpdate(new Document("name", "setup"),
        // new Document("$set", new Document("status", false)));

        // } else {

        // // gameController.stopGame();

        // }

        // }

        // TESTING AVERAGE MOVEMENT

        File file = new File("dll/dllPath.txt"); // creates a new file instance
        FileReader fr = new FileReader(file); // reads the file
        BufferedReader br = new BufferedReader(fr); // creates a buffering character input stream

        System.load(br.readLine());

        DeepNeuralNetworkProcessor processor = new DeepNeuralNetworkProcessor();
        VideoCapture camera = new VideoCapture(0);
        Mat frame = new Mat();

        // Music music = new Music();

        Person player = new Person("Player");

        while (camera.read(frame)) {

            // Imgproc.resize(frame, frame, new Size(300, 300));
            // Imgproc.resize(frame, frame, new Size(800, 800));

            List<DnnObject> detectObject = processor.getObjectsInFrame(frame, false);

            try {

                DnnObject person = detectObject.stream().filter(o -> o.getObjectName().equals("person"))
                        .findFirst()
                        .get();

                player.addRect(new Rect(person.getLeftBottom(), person.getRightTop()));
                player.updateMovement();
                player.showGauge();

                // Imgproc.rectangle(frame, person.getLeftBottom(), person.getRightTop(), new Scalar(255));
                // Imgproc.putText(frame, String.format("%.0f", player.getLerpPercent()), person.getRightTop(),
                //         50, 1, new Scalar(255));
                HighGui.imshow("VIEW", frame);
                // HighGui.waitKey(1);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

}
