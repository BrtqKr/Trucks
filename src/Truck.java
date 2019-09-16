import java.io.Serializable;

class Truck implements Serializable {
    private int mass;
    private int id;

    Truck(int mass, int id) {
        this.mass = mass;
        this.id = id;
    }


    void setId(int id) {
        this.id = id;
    }

    void setMass(int mass) {
        this.mass = mass;
    }

    int getId() {
        return this.id;
    }

    int getMass() {
        return this.mass;
    }

}
