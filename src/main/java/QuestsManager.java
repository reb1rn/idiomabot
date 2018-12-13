import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.telegram.telegrambots.meta.api.objects.Chat;

import java.util.ArrayList;

public class QuestsManager{
    private static QuestsManager sInstance;

    private static final String DB_HOST = "ds113169.mlab.com";
    private static final int DB_PORT = 13169;
    private static final String DB_NAME = "heroku_8f7sgf4c";
    private static final String DB_USER = "admin919";
    private static final String DB_PASSWORD = "admin919_passs";

    private static final String DB_URL = "mongodb://" + DB_USER + ":" + DB_PASSWORD + "@" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME;

    private MongoCollection<Document> mQuestsCollection;

    private QuestsManager() {
        MongoClientURI clientURI = new MongoClientURI(DB_URL);
        MongoClient client = new MongoClient(clientURI);
        MongoDatabase db = client.getDatabase(DB_NAME);
        mQuestsCollection = db.getCollection("quests");
    }

    public static QuestsManager getInstance() {
        if (sInstance == null) {
            sInstance = new QuestsManager();
        }
        return sInstance;
    }

    public void addQuest(String quest) {
        System.out.println(quest);
        String[] questArray = quest.split("\\*");
        int index = 0;
        for (String qustSubInfo: questArray){
            questArray[index] = qustSubInfo.trim();
            questArray[0]="❓"+qustSubInfo.trim();
            index++;
        }
        long id;

        if (mQuestsCollection.count() == 0){
            id = 0;
        }else id = mQuestsCollection.count();

        Document newUser = new Document("id", id)
                .append("situation", questArray[0])
                .append("choice1", questArray[1])
                .append("choice2", questArray[2])
                .append("choice3", questArray[3])
                .append("trueAnswer", questArray[4])
                .append("idiomaMeaning", questArray[5]);
        mQuestsCollection.insertOne(newUser);
    }
    public Quest getQuest(long questId){
        Document query = new Document("id", questId);
        Document someQuest = mQuestsCollection.find(query).first();
        Quest quest = new Quest(
                someQuest.getString("situation"),
                someQuest.getString("choice1"),
                someQuest.getString("choice2"),
                someQuest.getString("choice3"),
                someQuest.getString("trueAnswer"),
                someQuest.getString("idiomaMeaning")
        );
        return quest;
    }
    public ArrayList<Quest> getArrayListQuests(){
        ArrayList<Quest> arrayListQuests = new ArrayList<Quest>();
        for (Document currentQuest : mQuestsCollection.find()) {
            arrayListQuests.add(getQuest(currentQuest.getLong("id")));
        }
        return arrayListQuests;
    }

}