package JavaCVTest;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;
import org.opencv.highgui.HighGui;

public class App {

    public static void main(String[] args) {

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        DeepNeuralNetworkProcessor processor = new DeepNeuralNetworkProcessor();
        List<DnnObject> detectObject = new ArrayList<>();

        VideoCapture capture = new VideoCapture();
        // capture.open("files/car.mp4");
        capture.open(args[0]);
        Mat frame = new Mat();

        // todo: Change source of images to a camera

        while (capture.read(frame)) {

            Imgproc.resize(frame, frame, new Size(frame.width() / 2, frame.height() / 2));

            detectObject = processor.getObjectsInFrame(frame, false);

            for (DnnObject obj : detectObject) {
                Imgproc.rectangle(frame, obj.getLeftBottom(), obj.getRightTop(), new Scalar(255, 0, 0), 1);
                Imgproc.putText(frame, obj.getObjectName(), obj.getLeftBottom(),
                        Imgproc.FONT_HERSHEY_DUPLEX, 1.5, new Scalar(255, 255, 0));
            }

            HighGui.imshow("Display", frame);
            HighGui.waitKey(1);
        }

    }

}
