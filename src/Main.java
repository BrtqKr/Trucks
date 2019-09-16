import jline.console.ConsoleReader;

import java.io.*;

public class Main {

    public static void main(String[] args) {
        try {
            Queue waitingQueue = new Queue();

            InnerQueue q1 = new InnerQueue(null, null);
            InnerQueue q2 = new InnerQueue(null, null);

            SwapQueue sq = new SwapQueue(q1, q2);

            ConsoleReader lineReader = new ConsoleReader(System.in, System.out);

            System.out.println("Truck queue simulation has started, type help to display available commands...");
            String command;
            while (!(command = lineReader.readLine(">")).equals("exit")) {
                try {
                    if (!command.isEmpty()) {
                        String[] order = command.trim().split("\\s");
                        if (order.length == 1) {
                            switch (order[0]) {
                                case "help":
                                    System.out.println("Available commands:\n\tarrive [mass] -> truckID\n\tstatus -> [InnerQueue Status]\n\tstep\n\testimated [truckID] -> time\n\texit");
                                    break;
                                case "status":
                                    System.out.println("Waiting Queue:\n");
                                    System.out.print("\t");
                                    waitingQueue.printQueue();
                                    sq.printQueue();
                                    System.out.println("\nQ1: " + sq.getQ1().getExpressStatus() + " Q2: " + sq.getQ2().getExpressStatus());
                                    System.out.println("\nQ1: " + sq.getQ1().getMassTotal() + " Q2: " + sq.getQ2().getMassTotal());

                                    break;
                                case "step":
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
                                    break;
                                default:
                                    System.out.println("Invalid command, type help to display available commands");
                            }
                        } else if (order.length >= 2) {
                            switch (order[0]) {
                                case "arrive":
                                    int mass = Integer.parseInt(order[1]);
                                    int arrivedID = waitingQueue.generateID();
                                    Truck arrived = new Truck(mass, arrivedID);
                                    Node arrivedNode = new Node(arrived, null, null);
                                    waitingQueue.insert(arrivedNode);
                                    System.out.println("Truck with mass: " + mass + " arrived\n" + "ID received: " + arrivedID);
                                    break;
                                case "estimated":
                                    int estID = Integer.valueOf(order[1]);
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
                                            break;
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

                                default:
                                    System.out.println("Invalid command, type help to display available commands");


                            }
                        } else System.out.println("Invalid command, type help to display available commands");

                    }
                } catch (ClassNotFoundException | NumberFormatException ex) {
                    System.out.print(ex.getMessage() + "\n");
                }

            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }
}
