import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.ArrayList;

public class QuestFunction {
    public boolean getRequest(ArrayList<Quest> arrayQuests, int questId, String answer){
        Quest quest =  arrayQuests.get(questId);
        if (quest.trueAnswer.equals(answer)){
            return true;
        }else return false;
    }

    public void getQuest(Message message, Quest quest){
        Sending sending = new Sending();
        sending.sendMsg(message,
                quest.situation + "\n" +
                        quest.choise1 + "\n" +
                        quest.choise2 + "\n" +
                        quest.choise3 + "\n");
    }
}
