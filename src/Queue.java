import java.util.HashMap;

public class Queue
{
    protected Node head;
    protected Node tail;
    protected int elementsCounter;

    private Integer id=0;
    private HashMap<Integer, Node> trucks = new HashMap<>();

    public boolean isEmpty()
    {
        return head==null;
    }
    public void insert(Node node)
    {
        if(isEmpty())
        {
            this.head=node;
            this.tail=node;
            node.setNext(null);
            node.setPrevious(null);
            trucks.put(node.getTruck().getId(),node);
        }
        else
        {
            node.setNext(this.tail);
            this.tail.setPrevious(node);
            node.setPrevious(null);
            this.tail=node;
            trucks.put(node.getTruck().getId(),node);
        }
        this.elementsCounter++;
    }
    public void pushQueue()
    {
        if(this.head!=null)
        {
            if(this.getElementsCounter()==1)
            {
                this.head.setPrevious(null);
                trucks.remove(this.head.getTruck().getId());
                this.head=null;
                elementsCounter--;
            }
            else
            {
                Node newHead=this.head.getPrevious();
                this.head.setPrevious(null);
                trucks.remove(this.head.getTruck().getId());
                newHead.setNext(null);
                this.head=newHead;
                elementsCounter--;
            }

        }
    }
    public void estimate(Integer id)
    {
        Node estNode=this.trucks.get(id);
        estNode=estNode.getNext();
        int time;
        while(estNode!=null)
        {

        }
    }

    public int generateID()
    {
        return this.id++;
    }
    public HashMap<Integer, Node> getTrucks()
    {
        return this.trucks;
    }
    public Node getHead()
    {
        return this.head;
    }
    public Node getTail()
    {
        return this.tail;
    }
    public int getElementsCounter()
    {
        return this.elementsCounter;
    }
    public void setHead(Node head)
    {
        this.head=head;
    }
    public void setTail(Node tail)
    {
        this.tail=tail;
    }

    public void printQueue()
    {
        Node tmp=this.head;
        System.out.print("<- ");
        while(tmp!=null)
        {
            System.out.printf("id: %-2d m: %-5d",tmp.getTruck().getId(),tmp.getTruck().getMass());
            tmp=tmp.getPrevious();
        }
        System.out.println("\n\n");
    }
}
