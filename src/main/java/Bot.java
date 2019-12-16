import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import java.util.ArrayList;

public class Bot extends TelegramLongPollingBot {
    QuestFunction questFunction = new QuestFunction();
    ArrayList<Quest> arrayListQuests = QuestsManager.getInstance().getArrayListQuests();

    @Override
    public void onUpdateReceived(Update update) {

        boolean joke=true;
        Sending sending = new Sending();
        String message_text = update.getMessage().getText();
        int points = UsersManager.getInstance().getPointsUserByChat(update.getMessage().getChat());
        int currentlyQuest = UsersManager.getInstance().getСurrentlyQuest(update.getMessage().getChat());
        ArrayList<User> userArrayList = UsersManager.getInstance().getLiderBoard();
        int i = 0;
        int b=0 ;

        if (message_text.equals("Leaderboard")) {
            sending.sendMsg(update.getMessage(), "\tLeaderoard\uD83D\uDC51" +
                    "\n\t ⬇️⬇️⬇️");
        }
        for (User user : userArrayList) {
            if (message_text.equals("Leaderboard")) {
                sending.sendMsg(update.getMessage(), "\n" + user.userRecord + " - " + user.userFirstName);
            }
            i++;
            System.out.println(UsersManager.getInstance().getCurrentlyId(update.getMessage().getChat()));
            if (i < 4) {
                UsersManager.getInstance().UpdateOnPro(user.id, true);
                System.out.println(UsersManager.getInstance().getCurrentlypro(update.getMessage().getChat()));
            } else {
                UsersManager.getInstance().UpdateOnPro(user.id, false);
            }
        }

        //  sending.sendMsg(update.getMessage(),  UsersManager.getInstance().getLiderBoard().toString());


//        String user = UsersManager.getInstance().getLeaders(update.getMessage().getChat());


//sending.sendMsg(update.getMessage(),"Сонце ти просто монстер б можеш бавитися");
            //          System.out.println(user);

       // if (UsersManager.getInstance().getCurrentlyId(update.getMessage().getChat()).equals("382514487")&&b==0) {
         //   sending.sendMsg(update.getMessage(), "Спочатку м\uD83D\uDC45\uD83D\uDC45\uD83D\uDC45, потiм квест\uD83D\uDE09");

        //}




                if (message_text.contains("addone") && UsersManager.getInstance().getCurrentlypro(update.getMessage().getChat()) == true) {
                    String questInfo = message_text.replace("addone", "");
                    QuestsManager.getInstance().addQuest(questInfo);

                }
                else if (message_text.equals("/startgame")) {


                    points = UsersManager.getInstance().updatePoints(update.getMessage().getChat(), 0);
                    currentlyQuest = UsersManager.getInstance().updateСurrentlyQuest(update.getMessage().getChat(), 0);
                    if ((currentlyQuest < (arrayListQuests.size() - 1)) && currentlyQuest != -1) {
                        questFunction.getQuest(update.getMessage(), arrayListQuests.get(currentlyQuest));
                    }
                    sending.sendKeyboard(update.getMessage());
                } else if ((message_text.equals("a") || message_text.equals("c") || message_text.equals("b")) && currentlyQuest != -1) {
                    boolean request = questFunction.getRequest(arrayListQuests, currentlyQuest, message_text);
                    if (request == true) {
                        points = UsersManager.getInstance().updatePoints(update.getMessage().getChat(), ++points);
                        sending.sendMsg(update.getMessage(), "points➕1️⃣ " +
                                "\n Current points:" + points);
                    } else {
                        sending.sendMsg(update.getMessage(), "Uncorrect answer\uD83D\uDED1 " +
                                "\npoints:" + points);
                    }
                    currentlyQuest = UsersManager.getInstance().updateСurrentlyQuest(update.getMessage().getChat(), ++currentlyQuest);
                    if (currentlyQuest <= (arrayListQuests.size() - 1) && currentlyQuest != -1) {
                        questFunction.getQuest(update.getMessage(), arrayListQuests.get(currentlyQuest));
                    }
                    if (arrayListQuests.size() == currentlyQuest) {
                        UsersManager.getInstance().updateСurrentlyQuest(update.getMessage().getChat(), -1);
                        switch (points) {
                            case 0:
                                sending.sendMsg(update.getMessage(), "Your rank is WTF\uD83D\uDE2D");
                                break;
                            case 1:
                                sending.sendMsg(update.getMessage(), "Your rank is IMAO\uD83D\uDE22");
                                break;
                            case 2:
                                sending.sendMsg(update.getMessage(), "Your rank is God,NO!!!!\uD83E\uDD2C");
                                break;
                            case 3:
                                sending.sendMsg(update.getMessage(), "Your rank is Amateur\uD83D\uDE35");
                                break;
                            case 4:
                                sending.sendMsg(update.getMessage(), "Your rank is Noob\uD83E\uDD2A");
                                break;
                            case 5:
                                sending.sendMsg(update.getMessage(), "Your rank is SkilledNoob\uD83D\uDE32");
                                break;
                            case 6:
                                sending.sendMsg(update.getMessage(), "Your rank is BestNoobEver\uD83E\uDD24");
                                break;
                            case 7:
                                sending.sendMsg(update.getMessage(), "Your rank is Idiomakiller 1.0\uD83D\uDE0D");
                                break;
                            case 8:
                                sending.sendMsg(update.getMessage(), "Your rank is IdiomaKille r2.0\uD83D\uDE0E");
                                break;
                            case 9:
                                sending.sendMsg(update.getMessage(), "Your rank is IdiomaHuntePr0\uD83E\uDDD0");
                                break;
                            case 10:
                                sending.sendMsg(update.getMessage(), "Your rank is EnglishVeryPro\uD83E\uDD11");
                                break;
                        }
                        sending.idiomasMeanings(update.getMessage(), arrayListQuests);
                    }
                } else if (message_text.equals("/start")) {
                    sending.gameInfo(update.getMessage());
                }
                else if (message_text.equals("Leaderboard")) {
                    sending.sendMsg(update.getMessage(), "\t⬆️⬆️⬆️");
                } else if (message_text.equals("addQuest") && UsersManager.getInstance().getCurrentlypro(update.getMessage().getChat()) == true) {
                    // sending.sendMsg(update.getMessage(),alerts.instruction());


                    // sending.sendMsg(update.getMessage(),alerts.example());
                    //  sending.example(update.getMessage());
                    sending.sendMsg(update.getMessage(), "Follow this instruction to create new question❓" +
                            "\nWrite in one Message:" +
                            "\n⬇️⬇️⬇️" +
                            "\naddone" +
                            "\n The question(don't forget about ... in the place for answer" +
                            "\n* a) your variant" +
                            "\n* b) your variant" +
                            "\n* c) your variant" +
                            "\n*correct optoin(a,b or c)" +
                            "\n* correct idiomas' meaning ");
                    sending.sendMsg(update.getMessage(), "For example\uD83D\uDCC4:" +
                            "\n⬇️⬇️⬇️" +
                            "\naddone" +
                            "\n  Recently he ... with idea." +
                            "\n * a)come up" +
                            "\n * b)get down" +
                            "\n * c)interested in" +
                            "\n *a" +
                            "\n * come up - have an idea ,something new etc.");
                } else if (message_text.equals("addQuest") && UsersManager.getInstance().getCurrentlypro(update.getMessage().getChat()) == false && message_text.contains("addone") && UsersManager.getInstance().getCurrentlypro(update.getMessage().getChat()) == false) {
                    sending.sendMsg(update.getMessage(), "You need to be  in top-3 players\uD83D\uDD25\uD83D\uDD25\uD83D\uDD25 to add new quest." +
                            "\n Use button Leaderboard\uD83D\uDD35 to check the leadearboard list. ");
                } //else if (message_text.equals("/start") || message_text.equals("/startgame") && UsersManager.getInstance().getCurrentlyId(update.getMessage().getChat()).equals("424596510")) {
                //sending.sendMsg(update.getMessage(), "Спочатку 30 балiв, а потiм бот \uD83D\uDE05\uD83D\uDE05\uD83D\uDE05");
//
                //          }

                else {
                    sending.sendMsg(update.getMessage(), "Error❗️❗️❗️" +
                            "\n Incorrect input or Quest is finished‼️" +
                            " \nIf you want to restart game use command /startgame");
                }


        }



    @Override
    public String getBotUsername() {
            return "IdiomaHunter_bot";
        }

    @Override
    public String getBotToken() {
            return "1015891578:AAEgrYQMc8RZBJbQZDHtgYqLmrb4Sou9c4s";
        }
}
