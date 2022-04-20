package JavaCVTest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.HighGui;
import org.opencv.imgproc.Imgproc;

public class TestLerp {

    public static void main(String[] args) throws IOException {

        File file = new File("dll/dllPath.txt"); // creates a new file instance
        FileReader fr = new FileReader(file); // reads the file
        BufferedReader br = new BufferedReader(fr); // creates a buffering character input stream

        System.load(br.readLine());

        Lerper lerper = new Lerper();

        double position = 100;
        double target = 400;

        int count = 0;

        Mat frame = null;

        while (true) {

            frame = new Mat(new Size(500, 500), 1);

            position = lerper.Lerp(position, target);

            Imgproc.circle(frame, new Point(position, frame.height() / 2), 25, new Scalar(255));
            Imgproc.circle(frame, new Point(target, frame.height() / 2), 5, new Scalar(255, 0, 0, 1));


            System.out.println(position);
            HighGui.imshow("Test", frame);
            HighGui.waitKey(1);
            count++;

            if (lerper.getResting()) {
                target = (int) (Math.random() * 500);
            }

        }

        // System.out.println("Count: " + count);

    }

}
