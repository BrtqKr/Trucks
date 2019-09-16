import java.io.Serializable;
import java.util.HashMap;

public class Queue implements Serializable {
    Node head;
    Node tail;
    int elementsCounter;

    private Integer id = 0;
    private HashMap<Integer, Node> trucks = new HashMap<>();

    boolean isEmpty() {
        return head == null;
    }

    public void insert(Node node) {
        if (isEmpty()) {
            this.head = node;
            this.tail = node;
            node.setNext(null);
            node.setPrevious(null);
            trucks.put(node.getTruck().getId(), node);
        } else {
            node.setNext(this.tail);
            this.tail.setPrevious(node);
            node.setPrevious(null);
            this.tail = node;
            trucks.put(node.getTruck().getId(), node);
        }
        this.elementsCounter++;
    }

    void pushQueue() {
        if (this.head != null) {
            if (this.getElementsCounter() == 1) {
                this.head.setPrevious(null);
                trucks.remove(this.head.getTruck().getId());
                this.head = null;
                elementsCounter--;
            } else {
                Node newHead = this.head.getPrevious();
                this.head.setPrevious(null);
                trucks.remove(this.head.getTruck().getId());
                newHead.setNext(null);
                this.head = newHead;
                elementsCounter--;
            }

        }
    }

    public int generateID() {
        return this.id++;
    }

    HashMap<Integer, Node> getTrucks() {
        return this.trucks;
    }

    Node getHead() {
        return this.head;
    }

    Node getTail() {
        return this.tail;
    }

    int getElementsCounter() {
        return this.elementsCounter;
    }

    void printQueue() {
        Node tmp = this.head;
        System.out.print("<- ");
        while (tmp != null) {
            System.out.printf("id: %-2d m: %-5d", tmp.getTruck().getId(), tmp.getTruck().getMass());
            tmp = tmp.getPrevious();
        }
        System.out.println("\n\n");
    }
}
