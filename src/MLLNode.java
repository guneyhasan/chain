public class MLLNode {
    private char chainElement;
    private MLLNode down;
    private MLLNode right;

    public char getChainElement() {
        return chainElement;
    }

    public void setChainElement(char chainElement) {
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

    public MLLNode(char chainToAdd) {
        chainElement = chainToAdd;
        down = null;
        right = null;

    }
}
