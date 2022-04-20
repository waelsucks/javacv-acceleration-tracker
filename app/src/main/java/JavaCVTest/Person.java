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

    // Private constructor
    private Person() {
    }

    // Getter for the singleton instance
    public static Person getInstance(String name) {

        if (instance == null) {
            instance = new Person();
            instance.name = name;
            instance.lastRects = new LinkedList<>();
        }

        return instance;
    }

    public void show(Mat frame) {

        if (personRect != null) {

            try {

                averageMovement = 0;
                int index = 0;

                for (Rect rect : lastRects) {

                    averageMovement += rect.area();

                    index++;
                }

                averageMovement /= index;
                averageMovement /= lastRects.getLast().area();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public void setPersonRect(Rect personRect) {

        this.personRect = personRect;

        if (lastRects.size() > 15) {
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


    public LinkedList<Rect> getLastRects() {
        return lastRects;
    }

    // Get average movement
    public double getAverageMovement() {
        return averageMovement;
    }

}
