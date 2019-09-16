import jline.console.ConsoleReader;

import java.io.IOException;

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
                                    break;
                                case "step":
                                    Stepper stepper = new Stepper();
                                    stepper.step(sq, waitingQueue);
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
                                    Estimator estimator = new Estimator();
                                    if (estimator.estimate(estID, sq, waitingQueue) == -1) break;
                                    break;
                                default:
                                    System.out.println("Invalid command, type help to display available commands or check for typos");


                            }
                        } else
                            System.out.println("Invalid command: wrong command length, type help to display available commands");

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