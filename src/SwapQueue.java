import java.io.Serializable;

class SwapQueue implements Serializable {
    private InnerQueue q1;
    private InnerQueue q2;
    //private HashMap<Integer, Node> swapTrucks;

    SwapQueue(InnerQueue q1, InnerQueue q2) {
        this.q1 = q1;
        this.q2 = q2;
        //swapTrucks = new HashMap<>();
    }

    private void swap(Node node1, Node node2) {
        Truck t1 = node1.getTruck();
        Truck t2 = node2.getTruck();

        int id1 = t1.getId();
        int mass1 = t1.getMass();

        t1.setId(t2.getId());
        t1.setMass(t2.getMass());

        t2.setId(id1);
        t2.setMass(mass1);
    }

    void optimise() {

        Node tmp1 = q1.getHead().getPrevious();
        Node tmp2 = q2.getHead().getPrevious();

        while (tmp1 != null && tmp2 != null) {
            Truck truck1 = tmp1.getTruck();
            Truck truck2 = tmp2.getTruck();
            int diff = Math.abs(truck1.getMass() - truck2.getMass());
            if (q1.getExpressStatus()) {
                if (truck1.getMass() > truck2.getMass()) {
                    q1.setMassTotal(q1.getMassTotal() - diff);
                    q2.setMassTotal(q2.getMassTotal() + diff);
                    swap(tmp1, tmp2);
                }

            } else if (truck1.getMass() < truck2.getMass()) {
                q1.setMassTotal(q1.getMassTotal() + diff);
                q2.setMassTotal(q2.getMassTotal() - diff);
                swap(tmp1, tmp2);
            }

            tmp1 = tmp1.getPrevious();
            tmp2 = tmp2.getPrevious();
        }
    }

    void step() {
        if (q1.getHead() != null && q2.getHead() != null) optimise();
        q1.step();
        q2.step();

    }

    void printQueue() {
        Node tmp = q1.getHead();
        Node tmp2 = q2.getHead();
        System.out.println("Inner Queues:\n");
        System.out.print("\t");
        System.out.print("<- ");
        while (tmp != null) {
            System.out.printf("id: %-2d m: %-5d", tmp.getTruck().getId(), tmp.getTruck().getMass());
            tmp = tmp.getPrevious();
        }
        System.out.println("\n");
        System.out.print("\t");
        System.out.print("<- ");
        while (tmp2 != null) {
            System.out.printf("id: %-2d m: %-5d", tmp2.getTruck().getId(), tmp2.getTruck().getMass());
            tmp2 = tmp2.getPrevious();
        }
    }

    void resolveExpress() {
        Truck truck1 = this.q1.getHead().getTruck();
        Truck truck2 = this.q2.getHead().getTruck();

        if (truck1.getMass() <= truck2.getMass()) q1.setExpress(true);
        else q2.setExpress(true);
    }

    InnerQueue getQ1() {
        return this.q1;
    }

    InnerQueue getQ2() {
        return this.q2;
    }

    Node getByID(int id) {

        Node tmp = q1.getTail();
        for (int i = 0; i < q1.getElementsCounter(); i++) {
            if (tmp.getTruck().getId() == id) return tmp;
            tmp = tmp.getNext();
        }
        tmp = q2.getTail();
        for (int i = 0; i < q2.getElementsCounter(); i++) {
            if (tmp.getTruck().getId() == id) return tmp;
            tmp = tmp.getNext();
        }
        return null;
    }
}
