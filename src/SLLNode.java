public class SLLNode {
    private Object data;
    private SLLNode link;
    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public SLLNode getLink() {
        return link;
    }

    public void setLink(SLLNode link) {
        this.link = link;
    }

    public SLLNode(Object dataToAdd) {
        data = dataToAdd;
        link = null;
    }
}
