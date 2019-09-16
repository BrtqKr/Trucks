public class InnerQueue extends Queue implements Cloneable
{
    private boolean isExpress;
    private int massTotal;

    public InnerQueue(Node head, Node tail)
    {
        isExpress=false;
        this.head=head;
        this.tail=tail;
        this.elementsCounter=0;
        this.massTotal=0;
    }

    @Override
    public Object clone()throws CloneNotSupportedException
    {
        return new InnerQueue((Node)this.head.clone(),(Node)this.tail.clone());
    }

    public void step()
    {
        if(head!=null)
        {
            Truck t1=head.getTruck();
            if(t1.getMass()==1)
            {
                pushQueue();
            }
            else
            {
                int mass1=t1.getMass();
                t1.setMass(--mass1);
            }
        }
        if(massTotal>0)massTotal--;
    }
    @Override
    public void insert(Node node)
    {
        super.insert(node);
        this.massTotal+=node.getTruck().getMass();
    }

    @Override
    public int generateID()
    {
        throw new UnsupportedOperationException();
    }

    public boolean getExpressStatus()
    {
        return this.isExpress;
    }

    public void setExpress(boolean isExpress)
    {
        this.isExpress=isExpress;
    }

    public int getMassTotal()
    {
        return this.massTotal;
    }

}
