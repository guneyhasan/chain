public class Node {
    private Object data;
    private Node link;

    private int Score;
    private String Name;

    public int getScore() {
        return Score;
    }

    public void setScore(int score) {
        Score = score;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Node(Object dataToAdd){
        data=dataToAdd;
        link=null;
    }

    public Node(int score,String name){
        this.Score=score;
        this.Name=name;
        link=null;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Node getLink() {
        return link;
    }

    public void setLink(Node link) {
        this.link = link;
    }
}
