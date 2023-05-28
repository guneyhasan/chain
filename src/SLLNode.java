public class SLLNode {
    private char data;
    private SLLNode link;
    public char getData() {
        return data;
    }

    public void setData(char data) {
        this.data = data;
    }

    public SLLNode getLink() {
        return link;
    }

    public void setLink(SLLNode link) {
        this.link = link;
    }

    public SLLNode(char dataToAdd) {
        data = dataToAdd;
        link = null;
    }
}
