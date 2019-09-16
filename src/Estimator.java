import java.io.*;

class Estimator {
    int estimate(int estID, SwapQueue sq, Queue waitingQueue) throws IOException, ClassNotFoundException {
        int timer = 0;

        //deep copy SwapQueue
        SwapQueue sq2;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(sq);
        oos.flush();
        oos.close();
        bos.close();
        byte[] byteData = bos.toByteArray();
        ByteArrayInputStream bais = new ByteArrayInputStream(byteData);
        sq2 = (SwapQueue) new ObjectInputStream(bais).readObject();

        //deep copy WaitingQueue
        Queue waitingQueue2;
        bos = new ByteArrayOutputStream();
        oos = new ObjectOutputStream(bos);
        oos.writeObject(waitingQueue);
        oos.flush();
        oos.close();
        bos.close();
        byteData = bos.toByteArray();
        bais = new ByteArrayInputStream(byteData);
        waitingQueue2 = (Queue) new ObjectInputStream(bais).readObject();


        if (sq2.getByID(estID) == null) {
            if (waitingQueue2.getTrucks().get(estID) == null) {
                System.out.println("Element with a given ID could not be found");
                return -1;
            }
        }

        while (sq2.getQ1().getHead().getTruck().getId() != estID && sq2.getQ2().getHead().getTruck().getId() != estID) {

            if (waitingQueue2.getElementsCounter() > 0) {
                if (!(sq2.getQ1().getElementsCounter() == 5 && sq2.getQ2().getElementsCounter() == 5)) {
                    InnerQueue queue1 = sq2.getQ1();
                    InnerQueue queue2 = sq2.getQ2();

                    Node insertNode = waitingQueue2.getHead();
                    waitingQueue2.pushQueue();
                    sq2.optimise();
                    if (queue1.getElementsCounter() == 5) {
                        sq2.optimise();
                        queue2.insert(insertNode);
                    } else if (queue2.getElementsCounter() == 5) {
                        sq2.optimise();
                        queue1.insert(insertNode);
                    } else if (queue1.isEmpty() && queue2.isEmpty()) {
                        queue1.insert(insertNode);
                    } else if (queue1.isEmpty() && queue2.getElementsCounter() == 1) {
                        queue1.insert(insertNode);
                        sq2.resolveExpress();
                    } else if (queue2.isEmpty() && queue1.getElementsCounter() == 1) {
                        queue2.insert(insertNode);
                        sq2.resolveExpress();
                    } else if (queue1.getExpressStatus() && queue1.getHead().getTruck().getMass() >= queue2.getMassTotal()) {
                        queue1.setExpress(false);
                        queue2.setExpress(true);
                    } else if (queue2.getExpressStatus() && queue2.getHead().getTruck().getMass() > queue1.getMassTotal()) {
                        queue2.setExpress(false);
                        queue1.setExpress(true);
                    } else if (queue1.getMassTotal() > queue2.getMassTotal()) {
                        sq2.optimise();
                        queue2.insert(insertNode);
                    } else if (queue1.getMassTotal() <= queue2.getMassTotal()) {
                        sq2.optimise();
                        queue1.insert(insertNode);
                    }

                    if (waitingQueue2.getElementsCounter() > 0)
                        waitingQueue2.getHead().setNext(null);
                }
            }
            sq2.step();
            timer++;
        }
        timer += sq2.getByID(estID).getTruck().getMass();
        System.out.println("\nEstimated time: " + timer + "steps");
        return 0;

    }
}
