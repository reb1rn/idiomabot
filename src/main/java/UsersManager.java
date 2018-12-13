import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.telegram.telegrambots.meta.api.objects.Chat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static com.mongodb.client.model.Filters.eq;

public class UsersManager {
    private static UsersManager sInstance;

    /*
     * Database constants
     */
    private static final String DB_HOST = "ds113169.mlab.com";
    private static final int DB_PORT = 13169;
    private static final String DB_NAME = "heroku_8f7sgf4c";
    private static final String DB_USER = "admin919";
    private static final String DB_PASSWORD = "admin919_passs";

    private static final String DB_URL = "mongodb://" + DB_USER + ":" + DB_PASSWORD + "@" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME;

    private MongoCollection<Document> mUsersCollection;
    private MongoCollection<Document> mProductsCollection;
  public   String rank;
    private UsersManager() {
        // Більше інформації по роботі з MongoDB: https://www.baeldung.com/java-mongodb
        MongoClientURI clientURI = new MongoClientURI(DB_URL);
        MongoClient client = new MongoClient(clientURI);
        MongoDatabase db = client.getDatabase(DB_NAME);
        mUsersCollection = db.getCollection("users");
    }

    public static UsersManager getInstance() {
        if (sInstance == null) {
            sInstance = new UsersManager();
        }

        return sInstance;
    }



    public ArrayList<User> getLiderBoard(){
        ArrayList<User> lidersBoard = new ArrayList<>();
        for (Document user: mUsersCollection.find()){
            User createdUser = new User(user.getString("firstName"), user.getInteger("record"),user.getString("id"));
            lidersBoard.add(createdUser);
        }
        lidersBoard.sort(User::compareTo);
        return lidersBoard;
    }

    public int getPointsUserByChat(Chat chat) {
        Document query = new Document("id", chat.getId().toString());

        Document user = mUsersCollection.find(query).first();

        if (user == null) {
            Document newUser = new Document("id", chat.getId().toString())
                    .append("firstName", chat.getFirstName())
                    .append("lastName", chat.getLastName())
                    .append("points", 0)
                    .append("record",0)
                    .append("is_pro", false)
                    .append("currentlyQuest", 0)
                    .append("rank",rank)
                    .append("username", chat.getUserName());
            mUsersCollection.insertOne(newUser);
            return 0;
        }
        int userPoints = user.getInteger("points");
        return userPoints;

    }
    public int updatePoints(Chat chat, int points) {
        mUsersCollection.updateOne(eq("id", chat.getId().toString()), new Document("$set", new Document("points", points)));

        Document query = new Document("id", chat.getId().toString());
        Document user = mUsersCollection.find(query).first();
        int record = user.getInteger("record");

        if(user.getInteger("points") >= record) {
            mUsersCollection.updateOne(eq("id", chat.getId().toString()), new Document("$set", new Document("record", user.getInteger("points"))));
        }

        return  user.getInteger("points");
    }
 public  void UpdateOnPro(String id,boolean pro){
     mUsersCollection.updateOne(eq("id", id),new Document("$set", new Document("is_pro", pro)));

 }
public  boolean getCurrentlypro(Chat chat){
    Document query = new Document("id", chat.getId().toString());
    Document user = mUsersCollection.find(query).first();
        return  user.getBoolean("is_pro");
}
public String getCurrentlyId(Chat chat){
        Document query = new Document("id",chat.getId().toString());
        Document user = mUsersCollection.find(query).first();
        return  user.getString("id");
}
    public int getСurrentlyQuest(Chat chat){
        Document query = new Document("id", chat.getId().toString());
        Document user = mUsersCollection.find(query).first();
        return  user.getInteger("currentlyQuest");
    }
    public int updateСurrentlyQuest(Chat chat, int currentlyQuest) {
        mUsersCollection.updateOne(eq("id", chat.getId().toString()), new Document("$set", new Document("currentlyQuest", currentlyQuest)));

        Document query = new Document("id", chat.getId().toString());
        Document user = mUsersCollection.find(query).first();
        return  user.getInteger("currentlyQuest");
    }
}