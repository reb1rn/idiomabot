import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.telegram.telegrambots.meta.api.objects.Chat;

import java.awt.datatransfer.Clipboard;
import java.util.ArrayList;

import static com.mongodb.client.model.Filters.eq;

public class User implements Comparable {
    public String userFirstName;
    public int userRecord;
    public  String id;

    public User(String userFirstName, int userRecord,String id) {
        this.userFirstName = userFirstName;
        this.userRecord = userRecord;
        this.id=id;
    }

    public int getUserRecord() {
        return userRecord;
    }

    @Override
    public int compareTo(Object o) {
        int compare=((User)o).getUserRecord();
        return compare-this.userRecord;
    }
}