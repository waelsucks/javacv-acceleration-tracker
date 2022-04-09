package JavaCVTest;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.highgui.HighGui;
import org.opencv.videoio.VideoCapture;

public class App {

    public static void main(String[] args) {

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        DeepNeuralNetworkProcessor processor = new DeepNeuralNetworkProcessor();
        List<DnnObject> detectObject = new ArrayList<>();

        VideoCapture capture = new VideoCapture(0);

        Mat frame = new Mat();
        capture.read(frame);

        while (capture.read(frame)) {

            detectObject = processor.getObjectsInFrame(frame, false);

            try {

                DnnObject person = detectObject.stream().filter(o -> o.getObjectName().equals("person"))
                        .findFirst()
                        .get();

                Person.getInstance("Player").setPersonRect(new Rect(person.getLeftBottom(), person.getRightTop()));
                Person.getInstance("Player").show(frame);

                if (Person.getInstance("Player").getIsMoving()) {
                    System.out.println("Player is moving");
                } else {
                    System.out.println();
                }

                HighGui.imshow("Boop", frame);
                HighGui.waitKey(1);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

}
