public class MultiLinkedList {
    private MLLNode head;
    public MultiLinkedList() {
        head = null;
    }
    public void addChain(SingleLinkedList chainToAdd){
        if(head == null){
            String forCasting=chainToAdd.getHead().getData().toString();
            MLLNode newNode = new MLLNode(Integer.parseInt(forCasting));
            head = newNode;
            MLLNode temp = head;
            SLLNode SLLtemp = chainToAdd.getHead().getLink();
            while(SLLtemp != null) {
                String SLLtempForCasting=SLLtemp.getData().toString();
                newNode = new MLLNode(Integer.parseInt(SLLtempForCasting));
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

            String newNODEforCasting=chainToAdd.getHead().getData().toString();
            MLLNode newNode = new MLLNode(Integer.parseInt(newNODEforCasting));
            temp.setDown(newNode);
            temp = temp.getDown();
            SLLNode SLLtemp = chainToAdd.getHead().getLink();
            while(SLLtemp != null) {
                String SLLTEMPforCastinG=SLLtemp.getData().toString();
                newNode = new MLLNode(Integer.parseInt(SLLTEMPforCastinG));
                while (temp.getRight() != null)
                    temp = temp.getRight();
                temp.setRight(newNode);
                SLLtemp = SLLtemp.getLink();
            }
        }
    }
    public String display(){
        String output = "";
        if (head != null){
            MLLNode temp = head;
            while (temp != null){
                MLLNode temp2 = temp;
                while(temp2.getRight() != null) {
                    output += temp2.getChainElement() + " + ";
                    temp2 = temp2.getRight();
                }
                if(temp.getDown() != null)
                    output += temp2.getChainElement() + "\n+\n";
                else
                    output += temp2.getChainElement();
                temp = temp.getDown();
            }
        }
        return output;
    }

    public MLLNode getHead(){
        return head;
    }
    //public int size(){}
}
