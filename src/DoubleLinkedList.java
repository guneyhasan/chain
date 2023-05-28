public class DoubleLinkedList {
    private DLLNode head;

    public DLLNode getHead(){
        return head;
    }

    public void add(String name,int score){
        if(head==null){
            DLLNode newnode=new DLLNode(score,name);
            head=newnode;
        }

        else if((Integer) score>(Integer)head.getScore()){
            DLLNode newnode=new DLLNode(score,name);
            newnode.setNext_link(head);
            newnode.setPrevious_link(null);
            head.setPrevious_link(newnode);
            head=newnode;
        }

        else if((Integer) score==(Integer)head.getScore()){
            DLLNode newnode=new DLLNode(score,name);
            newnode.setNext_link(head.getNext_link());
            newnode.setPrevious_link(head);
            head.setNext_link(newnode);
        }

        else{
            DLLNode temp=head;
            DLLNode previous=null;
            while(temp!=null&&(Integer)score <=(Integer)temp.getScore()){
                previous=temp;
                temp=temp.getNext_link();
            }
            if(temp==null){
                DLLNode newnode=new DLLNode(score, name);
                newnode.setNext_link(temp);
                newnode.setPrevious_link(previous);
                previous.setNext_link(newnode);
            }

            else{
                DLLNode newnode=new DLLNode(score,name);
                newnode.setPrevious_link(previous);
                newnode.setNext_link(previous.getNext_link());
                previous.setNext_link(newnode);

            }
        }

        /*
        if(size()>12){
            Node temp=head;
            Node previous=null;
            while(temp.getLink()!=null){
                previous=temp;
                temp=temp.getLink();
            }

            deleteHighScore(temp.getName(),temp.getScore());
        }
        */
    }
    public void displayScore(){
        if(head==null){
            System.out.println("linked list is empty");
        }
        else{
            DLLNode temp=head;
            while(temp!=null){
                System.out.println(temp.getNames()+" "+temp.getScore());
                temp=temp.getNext_link();
            }
        }
    }

    public int size(){
        int count=0;
        if(head==null){
            System.out.println("linked list is empty");
        }

        else{
            DLLNode temp=head;
            while(temp!=null){
                count++;
                temp=temp.getNext_link();
            }
        }
        return count;
    }

    public Object getName (int indexyouwant){
        DLLNode check=head;
        for(int i=0;i<indexyouwant-1;i++){
            check=check.getNext_link();
        }
        Object retdata=check.getNames();
        return retdata;
    }

    public Object getScore (int indexyouwant){
        DLLNode check=head;
        for(int i=0;i<indexyouwant-1;i++){
            check=check.getNext_link();
        }
        Object retdata=check.getScore();
        return retdata;
    }

}
