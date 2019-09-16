public class Truck implements Cloneable
{
    private int mass;
    private int id;
    private boolean insideSQ;

    public Truck(int mass, int id)
    {
        this.mass=mass;
        this.id=id;
        this.insideSQ=false;//assumption: every truck starts on waiting queue
    }

    @Override
    public Object clone()throws CloneNotSupportedException
    {
        return new Truck(getMass(),getId());
    }


    public void setId(int id)
    {
        this.id=id;
    }
    public void setMass(int mass)
    {
        this.mass=mass;
    }
    public void setInsideSQ()
    {
        this.insideSQ=true;
    }

    public int getId()
    {
        return this.id;
    }

    public int getMass()
    {
        return this.mass;
    }

    public boolean inSQ()
    {
        return this.insideSQ;
    }


}
