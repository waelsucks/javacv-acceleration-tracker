package JavaCVTest;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.changestream.FullDocument;

import org.bson.Document;

public class App {

    public static void main(String[] args) {

        MongoClient mongoClient = MongoClients.create(
                "mongodb+srv://whaam:B-oop123@project2022.yskak.mongodb.net/myFirstDatabase?retryWrites=true&w=majority");

        System.out.println("Connected to MongoDB");

        MongoDatabase database = mongoClient.getDatabase("configuration");
        MongoCollection<Document> collection = database.getCollection("setup");

        var watchCursor = collection.watch()
                .fullDocument(FullDocument.UPDATE_LOOKUP);

        while (true) {

            var doc = watchCursor.first();

            if ((boolean) doc.getFullDocument().get("status")) {

                GameController gameController = new GameController(mongoClient, (String) doc.getFullDocument().get("username"));
                gameController.start();
                System.out.println(doc.getFullDocument().get("username") + " has joined the game!");

                collection.findOneAndUpdate(new Document("name", "setup"),
                        new Document("$set", new Document("status", false)));

            } else {

                // gameController.stopGame();

            }

        }

    }

}
