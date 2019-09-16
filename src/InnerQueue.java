import java.io.Serializable;

public class InnerQueue extends Queue implements Serializable {
    private boolean isExpress;
    private int massTotal;

    InnerQueue(Node head, Node tail) {
        isExpress = false;
        this.head = head;
        this.tail = tail;
        this.elementsCounter = 0;
        this.massTotal = 0;
    }

    void step() {
        if (head != null) {
            Truck t1 = head.getTruck();
            if (t1.getMass() == 1) {
                pushQueue();
            } else {
                int mass1 = t1.getMass();
                t1.setMass(--mass1);
            }
            massTotal--;
        }
    }

    @Override
    public void insert(Node node) {
        super.insert(node);
        this.massTotal += node.getTruck().getMass();
    }

    @Override
    public int generateID() {
        throw new UnsupportedOperationException();
    }

    boolean getExpressStatus() {
        return this.isExpress;
    }

    void setExpress(boolean isExpress) {
        this.isExpress = isExpress;
    }

    int getMassTotal() {
        return this.massTotal;
    }

    void setMassTotal(int mass) {
        this.massTotal = mass;
    }

}
