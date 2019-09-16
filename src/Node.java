public class Node
{
    private Truck truck;
    private Node previous;
    private Node next;

    public Node(Truck truck, Node previous, Node next)
    {
        this.truck = truck;
        this.previous=previous;
        this.next=next;
    }

    public Truck getTruck()
    {
        return truck;
    }
    public Node getPrevious()
    {
        return previous;
    }
    public Node getNext()
    {
        return next;
    }

    public void setPrevious(Node previous)
    {
        this.previous=previous;
    }
    public void setNext(Node next)
    {
        this.next=next;
    }
}
