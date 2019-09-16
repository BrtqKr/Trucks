class Stepper {
    void step(SwapQueue sq, Queue waitingQueue) {
        sq.step();
        if (waitingQueue.getElementsCounter() > 0) {
            if (!(sq.getQ1().getElementsCounter() == 5 && sq.getQ2().getElementsCounter() == 5)) {
                InnerQueue queue1 = sq.getQ1();
                InnerQueue queue2 = sq.getQ2();

                Node insertNode = waitingQueue.getHead();
                waitingQueue.pushQueue();

                if (queue1.getElementsCounter() == 5) {
                    sq.optimise();
                    queue2.insert(insertNode);
                } else if (queue2.getElementsCounter() == 5) {
                    sq.optimise();
                    queue1.insert(insertNode);
                } else if (queue1.isEmpty() && queue2.isEmpty()) {
                    queue1.insert(insertNode);
                } else if (queue1.isEmpty() && queue2.getElementsCounter() == 1) {
                    queue1.insert(insertNode);
                    sq.resolveExpress();
                } else if (queue2.isEmpty() && queue1.getElementsCounter() == 1) {
                    queue2.insert(insertNode);
                    sq.resolveExpress();
                } else if (queue1.getExpressStatus() && queue1.getHead().getTruck().getMass() >= queue2.getMassTotal()) {
                    queue1.setExpress(false);
                    queue2.setExpress(true);
                } else if (queue2.getExpressStatus() && queue2.getHead().getTruck().getMass() > queue1.getMassTotal()) {
                    queue2.setExpress(false);
                    queue1.setExpress(true);
                } else if (queue1.getMassTotal() > queue2.getMassTotal()) {
                    sq.optimise();
                    queue2.insert(insertNode);
                } else if (queue1.getMassTotal() <= queue2.getMassTotal()) {
                    sq.optimise();
                    queue1.insert(insertNode);
                }

                if (waitingQueue.getElementsCounter() > 0)
                    waitingQueue.getHead().setNext(null);
            }
        }
    }
}
