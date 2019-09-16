import java.io.Serializable;

class Node implements Serializable {
    private Truck truck;
    private Node previous;
    private Node next;

    Node(Truck truck, Node previous, Node next) {
        this.truck = truck;
        this.previous = previous;
        this.next = next;
    }

    Truck getTruck() {
        return truck;
    }

    Node getPrevious() {
        return previous;
    }

    Node getNext() {
        return next;
    }

    void setPrevious(Node previous) {
        this.previous = previous;
    }

    void setNext(Node next) {
        this.next = next;
    }

}
