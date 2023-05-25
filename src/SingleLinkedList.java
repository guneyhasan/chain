public class SingleLinkedList {
    private SLLNode head;

    public SLLNode getHead() {
        return head;
    }

    public void add(Object dataToAdd){
        if(head == null){
            SLLNode newnode = new SLLNode(dataToAdd);
            head = newnode;
        }
        else{
            SLLNode temp = head;
            while(temp.getLink() != null)
                temp = temp.getLink();
            SLLNode newnode = new SLLNode(dataToAdd);
            temp.setLink(newnode);
        }
    }
    public void display(){
        if(head == null){
            System.out.println("Linked list is empty");
        }
        else{
            SLLNode temp = head;
            while(temp != null){
                System.out.println(temp.getData());
                temp = temp.getLink();
            }
        }
    }
    public int size(){
        int count = 0;
        if(head == null)
            System.out.println("Linked list is empty");
        else{
            SLLNode temp = head;
            while (temp != null){
                count++;
                temp = temp.getLink();
            }
        }
        return count;
    }
}
