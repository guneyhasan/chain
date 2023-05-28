public class DLLNode {
    private int Score;
    private String Names;
    private DLLNode next_link;
    private DLLNode previous_link;

    public DLLNode getNext_link() {
        return next_link;
    }

    public void setNext_link(DLLNode next_link) {
        this.next_link = next_link;
    }

    public DLLNode getPrevious_link() {
        return previous_link;
    }

    public void setPrevious_link(DLLNode previous_link) {
        this.previous_link = previous_link;
    }

    public int getScore() {
        return Score;
    }

    public void setScore(int scoredata) {
        Score = scoredata;
    }

    public String getNames() {
        return Names;
    }

    public void setNames(String namesdata) {
        Names = namesdata;
    }

    public DLLNode(int ScoretoADD,String NamestoADD) {
        Score=ScoretoADD;
        Names=NamestoADD;
        next_link = null;
        previous_link = null;
    }
}
