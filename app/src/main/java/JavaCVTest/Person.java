package JavaCVTest;

import java.util.LinkedList;

import org.opencv.core.Mat;
import org.opencv.core.Rect;

public class Person {

    // Singleton instance
    private static Person instance = null;

    private Rect personRect;
    private String name;

    private LinkedList<Rect> lastRects;
    private double averageMovement = 0;

    private boolean isMoving;

    // Private constructor
    private Person() {
    }

    // Getter for the singleton instance
    public static Person getInstance(String name) {

        if (instance == null) {
            instance = new Person();
            instance.name = name;
            instance.lastRects = new LinkedList<>();
            instance.isMoving = false;
        }

        return instance;
    }

    public void show(Mat frame) {

        if (personRect != null) {

            try {

                averageMovement = 0;
                int index = 0;

                for (Rect rect : lastRects) {

                    averageMovement += rect.width;

                    index++;
                }

                averageMovement /= index;
                averageMovement /= lastRects.getLast().width;

                if (averageMovement >= 0.60 && averageMovement <= 1.40) {
                    instance.isMoving = false;
                } else {
                    instance.isMoving = true;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public void setPersonRect(Rect personRect) {

        this.personRect = personRect;

        if (lastRects.size() > 30) {
            lastRects.removeLast();
        }

        lastRects.addFirst(personRect);

    }

    public Rect getPersonRect() {
        return personRect;
    }

    public String getName() {
        return name;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean isMoving) {
        this.isMoving = isMoving;
    }

    public LinkedList<Rect> getLastRects() {
        return lastRects;
    }

    // Get average movement
    public double getAverageMovement() {
        return averageMovement;
    }

    public boolean getIsMoving() {
        return isMoving;
    }

    /***
     * Interpolating method
     * 
     * @param start start of the interval
     * @param end   end of the interval
     * @param count count of output interpolated numbers
     * @return array of interpolated number with specified count
     */
    public static double[] interpolate(double start, double end, int count) {
        if (count < 2) {
            throw new IllegalArgumentException("interpolate: illegal count!");
        }
        double[] array = new double[count + 1];

        for (int i = 0; i <= count; ++i) {
            array[i] = start + i * (end - start) / count;
        }
        return array;
    }

}
