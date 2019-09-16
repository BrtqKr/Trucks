import jline.console.ConsoleReader;

import java.io.IOException;

public class Main {

    public static void main(String[] args)throws CloneNotSupportedException
    {
        try
        {
            //test queue parameters
            Queue waitingQueue=new Queue();

            InnerQueue q1=new InnerQueue(null,null);
            InnerQueue q2=new InnerQueue(null,null);

            SwapQueue sq=new SwapQueue(q1,q2);


            SwapQueue sq2=new SwapQueue(null,null);//DELETE AFTER TESTS


            ConsoleReader linereader = new ConsoleReader(System.in, System.out);

            System.out.println("Truck queue simulation has started, type help to display available commands...");
            String command;
            while(!(command=linereader.readLine(">")).equals("exit"))
            {
                try
                {
                    if (!command.isEmpty()) {
                        String[] order = command.trim().split("\\s");
                        //System.out.println("command: "+command+" order: "+order[0]+" argument: "+order[1]);
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
                                    System.out.println("\nQ1: "+sq.getQ1().getExpressStatus()+" Q2: "+sq.getQ2().getExpressStatus());
                                    System.out.println("\nQ1: "+sq.getQ1().getMassTotal()+" Q2: "+sq.getQ2().getMassTotal());

                                    break;
                                case "step":
                                    sq.step();
                                    if(waitingQueue.getElementsCounter()>0)
                                    {
                                        if(!(sq.getQ1().getElementsCounter()==5 && sq.getQ2().getElementsCounter()==5))
                                        {
                                            InnerQueue queue1=sq.getQ1();
                                            InnerQueue queue2=sq.getQ2();

                                            Node insertNode=waitingQueue.getHead();
                                            waitingQueue.pushQueue();
                                            insertNode.getTruck().setInsideSQ();

                                            if(queue1.getElementsCounter()==5) queue2.insert(insertNode);
                                            else if(queue2.getElementsCounter()==5) queue1.insert(insertNode);
                                            else if(queue1.isEmpty() && queue2.isEmpty())
                                            {
                                                queue1.insert(insertNode);
                                            }
                                            else if(queue1.isEmpty() && queue2.getElementsCounter()==1)
                                            {
                                                queue1.insert(insertNode);
                                                sq.resolveExpress();
                                            }
                                            else if(queue2.isEmpty() && queue1.getElementsCounter()==1)
                                            {
                                                queue2.insert(insertNode);
                                                sq.resolveExpress();
                                            }
                                            else if(queue1.getExpressStatus() && queue1.getHead().getTruck().getMass()>=queue2.getMassTotal())
                                            {
                                                queue1.setExpress(false);
                                                queue2.setExpress(true);
                                            }
                                            else if(queue2.getExpressStatus() && queue2.getHead().getTruck().getMass()>queue1.getMassTotal())
                                            {
                                                queue2.setExpress(false);
                                                queue1.setExpress(true);
                                            }
                                            else if(queue1.getMassTotal()>queue2.getMassTotal())queue2.insert(insertNode);
                                            else if(queue1.getMassTotal()<=queue2.getMassTotal())queue1.insert(insertNode);

                                            if(waitingQueue.getElementsCounter()>0)waitingQueue.getHead().setNext(null);
                                        }
                                    }
                                    break;
                                case "clone":
                                    Object object=sq.clone();
                                    sq2=(SwapQueue)object;
                                    System.out.println("Queue cloning successful");
                                    break;
                                case "clonePrint":
                                    System.out.println("Cloned queue: \n");
                                    sq2.printQueue();
                                    break;
                                case "cloneStep":
                                    sq2.step();
                                    break;
                                default:
                                    System.out.println("Invalid command, type help to display available commands");
                            }
                        }
                        else if (order.length >= 2)
                        {
                            switch (order[0])
                            {
                                case "arrive":
                                    int mass=Integer.parseInt(order[1]);
                                    Integer arrivedID=waitingQueue.generateID();
                                    Truck arrived=new Truck(mass,arrivedID);
                                    Node arrivedNode=new Node(arrived,null,null);
                                    waitingQueue.insert(arrivedNode);
                                    System.out.println("Truck with mass: "+mass+" arrived\n"+"ID received: "+arrivedID);
                                    break;
                                case "estimated":
                                    Integer estID=Integer.valueOf(order[1]);
                                    Node estNode;
                                    if(waitingQueue.getTrucks().containsKey(estID))
                                    {

                                    }
                                default:
                                    System.out.println("Invalid command, type help to display available commands");


                            }
                        }
                        else System.out.println("Invalid command, type help to display available commands");

                    }
                }
                catch (NumberFormatException ex)
                {
                    System.out.print(ex.getMessage()+"\n");
                }

            }


        }
        catch (IOException ex)
        {
            System.out.println(ex.getMessage());
        }

    }
}
