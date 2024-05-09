package barScheduling;

import java.util.concurrent.atomic.AtomicBoolean;

import barScheduling.DrinkOrder.Drink;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.*;

/*
 Barman Thread class.
 */

public class Barman extends Thread {
	
	private CountDownLatch startSignal;
	private BlockingQueue<DrinkOrder> orderQueue;
	private LinkedList<DrinkOrder> linkedList;
	public static FileWriter fileWw;
	private long throughput, startTime, endTime;
	private int count;
	DrinkOrder nextOrder;
	
	Barman(CountDownLatch startSignal,int schedAlg) {
		if (schedAlg==0)
			this.orderQueue = new LinkedBlockingQueue<>();
		//FIX below
		else this.orderQueue = new PriorityBlockingQueue<>();
		
	    this.startSignal=startSignal;
	}

	public synchronized void placeDrinkOrder(DrinkOrder order) throws InterruptedException {
        orderQueue.put(order); // All orders added to the copy orderQueue
    }

	public synchronized void queueSorter(){
		linkedList = new LinkedList<>(orderQueue); // List of all drink orders from the order queue

		Comparator<DrinkOrder> customComparator = (od1, od2) -> { // Comparator object which compares two DrinkOrders
			int od1Time = od1.getExecutionTime();				  // Gets execution time of each DrinkOrder
			int od2Time = od2.getExecutionTime();

			int od1ID = Integer.valueOf(od1.toString().charAt(0)); // Gets the patron ID from the first character in the DrinkOrder
			int od2ID = Integer.valueOf(od1.toString().charAt(0));

			if (od1Time != od2Time){							  // Compares the execution time
				return Integer.compare(od1Time, od2Time);
			} else {
				return Integer.compare(od1ID, od2ID);
			}
		};

		PriorityQueue<DrinkOrder> priQueue = new PriorityQueue<>(customComparator); // Priority queue using comparator above
		priQueue.addAll(linkedList);  // Adds all DrinkOrder objects from the linked list into the priority queue 
		// Custom comparator sorts priority queue accordingly

		orderQueue.clear(); // empty order queue
		while (!priQueue.isEmpty()){ // add items from priority queue to order queue
			orderQueue.add(priQueue.poll());
		}

		//if (!orderQueue.isEmpty())
		//	System.out.println("\nSorted queue : " + orderQueue + "\n");
	}

	// Method to write data to fileWw
	public void writeToFile(String data) throws IOException { 
	    synchronized (fileWw) {
	    	fileWw.write(data);
	    }
	}

	public void run() {
		try {
			startSignal.countDown(); //barman ready
			startSignal.await(); //check latch - don't start until told to do so

			/* THROUGHPUT : Andre making orders while there are orders in the queue
			 * Count the number of orders in the queue and divide it by the time taken for while loop to run
			 * write to "throughput.txt" file
			*/

			/////////////////////////////////////////////////////////////
			startTime = System.currentTimeMillis();
			/////////////////////////////////////////////////////////////

			while (orderQueue == null || orderQueue.isEmpty()) {
				// Wait until orderQueueF is not null and not empty
				queueSorter();
				Thread.sleep(1); // Add a small delay to avoid busy-waiting
			}

			while(true) {
				count++; // increments for each order
				queueSorter();
				nextOrder = orderQueue.take();
				System.out.println("---Barman preparing order for patron "+ nextOrder.toString());
				sleep(nextOrder.getExecutionTime()); //processing order
				System.out.println("---Barman has made order for patron "+ nextOrder.toString());
				nextOrder.orderDone();
				if (orderQueue.isEmpty())
					break;
			}

			/////////////////////////////////////////////////////////////
			endTime = System.currentTimeMillis();
			/////////////////////////////////////////////////////////////

			throughput = count/(endTime-startTime);

			writeToFile("\r" + String.valueOf(throughput));
				
		} catch (InterruptedException | IOException e1) {
			System.out.println("---Barman is packing up ");
		}
	}
}





