import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class Sending extends Bot {
    Bot bot = new Bot();
    public  void sendMsg(Message message, String s){
        SendMessage sendMessage = new SendMessage();
//        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(s);
        try {
            execute(sendMessage);
        }catch (TelegramApiException e){
            e.printStackTrace();
        }
    }
    public void example(Message message){
        String ex="For example:" +
                "\naddone" +
                "\n 11 Recently he ... with idea." +
                "\n * a)come up" +
                "\n * b)get down" +
                "\n * c)interested in" +
                "\n *a" +
                "\n * come up - have an idea ,something new etc.";
        sendMsg(message,ex);
    }
   // public void  instruction(Message message){
     //   String inst=
       // sendMsg(message,inst);
    //}
    public void idiomasMeanings(Message message, ArrayList<Quest> questArrayList){
        String meanings = "\t Idiomas list\uD83D\uDE0D\uD83D\uDE0D\uD83D\uDE0D" ;
        for (Quest iQuest : questArrayList){
            meanings = meanings + "\n" + iQuest.idiomaMeaning;
        }
        sendMsg(message, meanings);
    }


    public void gameInfo(Message message) {
        String  info = message.getFrom().getFirstName() + ", welcome to the IdiomaHunter!✋" +
                "\t\n The aim of IdiomaHunter is check your knowladge of phrasal verbs\uD83D\uDE2E." +
                "\n You will be given different situations," +
                "\n in which you must choose the most suitable idiom\uD83D\uDE43." +
                "\t\nChoose variant a , b or c ." +
                "\n \uD83E\uDD24 EX: Harry is   going on holiday" +
                "\n a)looking forward to \t b)come across" +
                "\n c)facinated by " +
                "\nCorrect answer is a)looking forward to , because it means :hoping to go \uD83D\uDE0A " +
                "\n(P.S you will be given the meaning for correct idioma after the test )" +
                "\t\nYou have only 1 chance to tick the correct option." +
                "\nFor each correct option you will recieve 1 point , " +
                "\nand after the game you will recieve IdiomaHunters' rank." +
                "\n If you are in top-3 players , you could  add your own question" +
                "\n (It will appear after bot restart)." +
                "\n To check y"  +
                "\nTo start use command /startgame." +
                "\nGood luck!!!";
        sendMsg(message, info);
    }

    public void sendKeyboard(Message myMessage){
        SendMessage message = new SendMessage()
                .setChatId(myMessage.getChatId())
                .setText("Your keyboard ⬇");
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add("/startgame");
        row.add("Leaderboard");
        row.add("addQuest");
        keyboard.add(row);

        row = new KeyboardRow();
        row.add("a");
        row.add("b");
        row.add("c");
        keyboard.add(row);
        keyboardMarkup.setKeyboard(keyboard);
        message.setReplyMarkup(keyboardMarkup);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
