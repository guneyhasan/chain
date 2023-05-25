public class MLLNode {
    private int chainElement;
    private MLLNode down;
    private MLLNode right;

    public int getChainElement() {
        return chainElement;
    }

    public void setChainElement(int chainElement) {
        this.chainElement = chainElement;
    }

    public MLLNode getDown() {
        return down;
    }

    public MLLNode getRight() {
        return right;
    }

    public void setDown(MLLNode down) {
        this.down = down;
    }

    public void setRight(MLLNode right) {
        this.right = right;
    }

    public MLLNode(int chainToAdd) {
        chainElement = chainToAdd;
        down = null;
        right = null;

    }
}
