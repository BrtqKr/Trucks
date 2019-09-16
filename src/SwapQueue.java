public class SwapQueue
{
    private InnerQueue q1;
    private InnerQueue q2;

    public SwapQueue(InnerQueue q1, InnerQueue q2)
    {
        this.q1=q1;
        this.q2=q2;
    }

    public void swap(Node node1, Node node2)
    {
        Truck t1=node1.getTruck();
        Truck t2=node2.getTruck();

        int id1=t1.getId();
        int mass1=t1.getMass();

        t1.setId(t2.getId());
        t1.setMass(t2.getMass());

        t2.setId(id1);
        t2.setMass(mass1);
    }

    public void optimise()
    {

        Node tmp1=q1.getHead().getPrevious();
        Node tmp2=q2.getHead().getPrevious();

        while(tmp1!=null && tmp2!=null)
        {
            Truck truck1=tmp1.getTruck();
            Truck truck2=tmp2.getTruck();
            if(q1.getExpressStatus())
            {
                if(truck1.getMass()>truck2.getMass()) swap(tmp1, tmp2);

            }
            else if(truck1.getMass()<truck2.getMass()) swap(tmp1, tmp2);

            tmp1=tmp1.getPrevious();
            tmp2=tmp2.getPrevious();
        }
    }

    public void step()
    {
        if(q1.getHead()!=null && q2.getHead()!=null) optimise();
        q1.step();
        q2.step();
    }

    public void printQueue()
    {
        Node tmp=this.q1.getHead();
        Node tmp2=this.q2.getHead();
        System.out.println("Inner Queues:\n");
        System.out.print("\t");
        System.out.print("<- ");
        while(tmp!=null)
        {
            System.out.printf("id: %-2d m: %-5d",tmp.getTruck().getId(),tmp.getTruck().getMass());
            tmp=tmp.getPrevious();
        }
        System.out.println("\n");
        System.out.print("\t");
        System.out.print("<- ");
        while(tmp2!=null)
        {
            System.out.printf("id: %-2d m: %-5d",tmp2.getTruck().getId(),tmp2.getTruck().getMass());
            tmp2=tmp2.getPrevious();
        }
    }
    public void resolveExpress()
    {
        Truck truck1=this.q1.getHead().getTruck();
        Truck truck2=this.q2.getHead().getTruck();

        if(truck1.getMass()<=truck2.getMass())q1.setExpress(true);
        else q2.setExpress(true);
    }

    public InnerQueue getQ1()
    {
        return this.q1;
    }

    public InnerQueue getQ2()
    {
        return this.q2;
    }
}
