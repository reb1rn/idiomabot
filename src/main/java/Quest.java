public class Quest {

    public String situation;
    public String choise1;
    public String choise2;
    public String choise3;
    public String trueAnswer;
    public String idiomaMeaning;

    public Quest(String situation, String choise1, String choise2, String choise3, String trueAnswer, String idiomaMeaning) {
        this.situation = situation;
        this.choise1 = choise1;
        this.choise2 = choise2;
        this.choise3 = choise3;
        this.trueAnswer = trueAnswer;
        this.idiomaMeaning = idiomaMeaning;
    }
    @Override
    public String toString() {
        return situation + "\n" +
                choise1 + "\n" +
                choise2 + "\n" +
                choise3 + "\n" +
                trueAnswer ;
    }
}
