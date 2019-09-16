public class Node implements Cloneable
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

    @Override
    public Object clone()throws CloneNotSupportedException
    {
        return new Node((Truck)this.truck.clone(),(Node)this.previous.clone(),(Node)this.next.clone());
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
    public void setTruck(Truck truck)
    {
        this.truck=truck;
    }
}
