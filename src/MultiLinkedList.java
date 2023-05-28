public class MultiLinkedList {
    private MLLNode head;
    public MultiLinkedList() {
        head = null;
    }
    public void addChain(SingleLinkedList chainToAdd){
        if(head == null){
            MLLNode newNode = new MLLNode(chainToAdd.getHead().getData());
            head = newNode;
            MLLNode temp = head;
            SLLNode SLLtemp = chainToAdd.getHead().getLink();
            while(SLLtemp != null) {
                newNode = new MLLNode(SLLtemp.getData());
                while (temp.getRight() != null)
                    temp = temp.getRight();
                temp.setRight(newNode);
                SLLtemp = SLLtemp.getLink();
            }
        }
        else {
            MLLNode temp = head;
            while (temp.getDown() != null)
                temp = temp.getDown();
            MLLNode newNode = new MLLNode(chainToAdd.getHead().getData());
            temp.setDown(newNode); temp = temp.getDown();
            SLLNode SLLtemp = chainToAdd.getHead().getLink();
            while(SLLtemp != null) {
                newNode = new MLLNode(SLLtemp.getData());
                while (temp.getRight() != null)
                    temp = temp.getRight();
                temp.setRight(newNode);
                SLLtemp = SLLtemp.getLink();
            }
        }
    }
    public void display(enigma.console.Console console){
        if (head != null){
            MLLNode temp = head;
            int tempWhileCounter=0;
            while (temp != null){
                console.getTextWindow().setCursorPosition(38,9+tempWhileCounter);
                MLLNode temp2 = temp;
                int temp2WhileCounter=1;
                while(temp2.getRight() != null){
                    console.getTextWindow().output( temp2.getChainElement()+" + ");
                    console.getTextWindow().setCursorPosition(38+temp2WhileCounter+3,9+tempWhileCounter);
                    temp2 = temp2.getRight();
                    temp2WhileCounter+=4;
                }
                console.getTextWindow().setCursorPosition(38+temp2WhileCounter-1,9+tempWhileCounter);
                console.getTextWindow().output(temp2.getChainElement());

                if(temp.getDown() != null) {

                    console.getTextWindow().setCursorPosition(38,9 + tempWhileCounter + 1);
                    console.getTextWindow().output('+');
                }
                temp = temp.getDown();
                tempWhileCounter+=2;
            }
        }
    }
}
