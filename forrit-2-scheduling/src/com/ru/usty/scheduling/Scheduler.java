package com.ru.usty.scheduling;

import java.util.Stack;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import com.ru.usty.scheduling.process.ProcessExecution;

public class Scheduler {

	ProcessExecution processExecution;
	Policy policy;
	int quantum;
	Stack<Integer> stk = new Stack();
	Timer timer = new Timer();
	Timer timer2 = new Timer();
	int stkAt;
	boolean timerSet;
	
	//Queue<Integer> queue;
	LinkedList<Integer> queue;



	/**
	 * DO NOT CHANGE DEFINITION OF OPERATION
	 */
	public Scheduler(ProcessExecution processExecution) {
		this.processExecution = processExecution;

		/**
		 * Add general initialization code here (if needed)
		 */
	}

	/**
	 * DO NOT CHANGE DEFINITION OF OPERATION
	 */
	public void startScheduling(Policy policy, int quantum) {

		this.policy = policy;
		this.quantum = quantum;

		/**
		 * Add general initialization code here (if needed)
		 */

		switch(policy) {
		case FCFS:	//First-come-first-served
			System.out.println("Starting new scheduling task: First-come-first-served");
				
			break;
		case RR:	//Round robin
			System.out.println("Starting new scheduling task: Round robin, quantum = " + quantum);
			
	        timer.cancel();
	        timer = new Timer();
			timerSet = false;
			
			stk = new Stack();
			stkAt = 0;
			
			break;
		case SPN:	//Shortest process next
			System.out.println("Starting new scheduling task: Shortest process next");
			
			timer.cancel(); // making sure the timer from Round Robin isn't affecting things
			queue = new LinkedList<Integer> ();

			break;
		case SRT:	//Shortest remaining time
			System.out.println("Starting new scheduling task: Shortest remaining time");
			
			timer.cancel(); // making sure the timer from Round Robin isn't affecting things
			queue = new LinkedList<Integer> ();
			
			break;
		case HRRN:	//Highest response ratio next
			System.out.println("Starting new scheduling task: Highest response ratio next");
			
			timer.cancel(); // making sure the timer from Round Robin isn't affecting things
			queue = new LinkedList<Integer> ();
			
			break;
		case FB:	//Feedback
			System.out.println("Starting new scheduling task: Feedback, quantum = " + quantum);
			/**
			 * Add your policy specific initialization code here (if needed)
			 */
			break;
		}

		/**
		 * Add general scheduling or initialization code here (if needed)
		 */

	}

	/**
	 * DO NOT CHANGE DEFINITION OF OPERATION
	 */
	public void processAdded(int processID) {
		
		if(this.policy.equals(policy.FCFS))
		{
			stk.push(processID);
			if(!stk.empty())
			{
				processExecution.switchToProcess(stk.elementAt(0));
			}
		}
		
		if(this.policy.equals(policy.RR))
		{
			stk.push(processID);
			if(timerSet == false)
			{
				timerSet = true;
				timer.scheduleAtFixedRate(new TimerTask() {
					@Override
					public void run() {
						if(stkAt+1 >= stk.size())
							stkAt = 0;
						else if(stk.elementAt(stkAt) == 0)
						{
						 while(stk.elementAt(stkAt) == 0)
							{
								if(stk.size() == 0)
									break;
								else if(stkAt+1 >= stk.size())
								{
									stkAt = 0;
									break;
								}
								else
									stkAt++;	
							}
						}
						else
							stkAt++;
						
					processExecution.switchToProcess(stk.indexOf(stkAt));
					}
				}, 230, quantum);
				
			}
		}
		
		if(this.policy.equals(Policy.SPN))
		{
			// It is not necessary to do anything special if the queue has less than two values since with SPN
			// it starts with the first process received and then continues onto the next one
			if(queue.size() < 2)
			{
				queue.add(processID);
			}
			// When there are two or more values we need to know the processing time (totalServiceTime)
			else
			{
				boolean added = false;
				
				// We go through the queue and find the appropriate location and we make sure we don't mess with the first value in the queue
				for(int i = 1; i < queue.size(); i++)
				{
					if(processExecution.getProcessInfo(processID).totalServiceTime < processExecution.getProcessInfo(queue.get(i)).totalServiceTime)
					{
						// when we find the location we squeeze the value in the place where the compared processID was in
						// and push the previous value one back
						queue.add(i, processID);
						added = true;
						break;
					}
				}
				
				// If the value wasn't added into the queue we add it to the back of it
				if(!added)
				{
					queue.add(processID);
				}
			}
			
			// The queue always starts on the first element in the list
			if(!queue.isEmpty())
			{
				processExecution.switchToProcess(queue.element());
			}
		}
		
		if(this.policy.equals(Policy.SRT))
		{
			// Since the queue is empty we can add a process to it without checking execution and service times
			//if(queue.isEmpty())
			if(queue.size() < 1)
			{
				System.out.println(queue.isEmpty());
				queue.add(processID);
			}
			
			//if(!queue.isEmpty())
			else
			{
				System.out.println("First element is: " + queue.element());
				System.out.println("processID being added: " + processID);
				if(processExecution.getProcessInfo(processID).totalServiceTime < (processExecution.getProcessInfo(queue.element()).totalServiceTime - processExecution.getProcessInfo(queue.element()).elapsedExecutionTime))
				{
					System.out.println("adding first");
					queue.addFirst(processID);
					System.out.println(queue.getFirst());
				}
				else
				{
					queue.addLast(processID);
					//queue.add(1, processID);
					/*boolean added = false;
					for(int i = 1; i < queue.size(); i++)
					{
						if(processExecution.getProcessInfo(processID).totalServiceTime < (processExecution.getProcessInfo(queue.get(i)).totalServiceTime - processExecution.getProcessInfo(queue.get(i)).elapsedExecutionTime))
						{
							queue.add(i, processID);
							break;
						}
					}
					
					if(!added)
					{
						queue.addLast(processID);
					}*/
					//System.out.println("adding last");
					//queue.add(processID);
				}
				//boolean added = false;
				
				// we check if the new process has a shorter service time than current processes in the queue 
				/*for(int i = 0; i < queue.size(); i++)
				{
					System.out.println("Added process serviceTime: " + processExecution.getProcessInfo(processID).totalServiceTime);
					System.out.println("Added process executionTime: " + processExecution.getProcessInfo(processID).elapsedExecutionTime);
					//System.out.println("Queued process service minus execution: " + (processExecution.getProcessInfo(queue.get(i)).totalServiceTime - processExecution.getProcessInfo(queue.get(i)).elapsedExecutionTime));
					System.out.println("ServiceTime for process in queue: " + processExecution.getProcessInfo(queue.get(i)).totalServiceTime);
					System.out.println("ExecutionTime for process in queue: " + processExecution.getProcessInfo(queue.get(i)).elapsedExecutionTime);
					
					// with the current processes in queue we compare the service time minus execution time with the new process service time
					/*if(processExecution.getProcessInfo(processID).totalServiceTime < (processExecution.getProcessInfo(queue.get(i)).totalServiceTime - processExecution.getProcessInfo(queue.get(i)).elapsedExecutionTime))
					{
						
						queue.add(i, processID);
						added = true;
						break;
					}*/
					
				//}
				
				// making sure we add to the back of the list if process wasn't added to queue in for-loop
				/*if(!added)
				{
					queue.add(processID);
				}*/
				
				// we start the process that is first in the queue
				processExecution.switchToProcess(queue.element());
			}
			
		}
		
		if(this.policy.equals(Policy.HRRN))
		{
			/*long currentProcessRatio = (processExecution.getProcessInfo(processID).elapsedWaitingTime + processExecution.getProcessInfo(processID).totalServiceTime) / processExecution.getProcessInfo(processID).totalServiceTime;
			long anotherProcessRatio;
			
			if(queue.size() < 2)
			{
				queue.add(processID);
			}
			else
			{
				
			}*/
			
			queue.add(processID);
			
			processExecution.switchToProcess(queue.element());
			
			if(queue.size() > 2)
			{
				int nextProcessLocation = 0;
				double currentHighestRatio = 0.00;
				
				for(int i = 1; i < queue.size(); i++)
				{
					double tempRatio = (processExecution.getProcessInfo(queue.get(i)).elapsedWaitingTime + processExecution.getProcessInfo(queue.get(i)).totalServiceTime) / processExecution.getProcessInfo(queue.get(i)).totalServiceTime;
					System.out.println("Queue size: " + queue.size());
					System.out.println("Checking location " + i + " in queue: Has processID :" + queue.get(i));
					System.out.println("currentHighestRatio: " + currentHighestRatio);
					System.out.println("tempRatio: " + tempRatio);
					
					if(tempRatio > currentHighestRatio)
					{
						System.out.println("Checked ratios and assigning them");
						currentHighestRatio = tempRatio;
						nextProcessLocation = i;
					}
				}
				
				if(currentHighestRatio > 1.00)
				{
					System.out.println("New highest ratio, reordering queue");
					queue.add(1, queue.remove(nextProcessLocation));
					System.out.println("Queue size after re-ordering: " + queue.size());
				}
				

			}
		}
	}

	/**
	 * DO NOT CHANGE DEFINITION OF OPERATION
	 */
	public void processFinished(int processID) {
		
		if(this.policy.equals(policy.FCFS))
		{
			stk.remove(0);
			if(!stk.empty())
			{
				processExecution.switchToProcess(stk.elementAt(0));
			}
		}
		
		if(this.policy.equals(policy.RR))
		{
			stk.set(stkAt, 0);
		}
		
		if(this.policy.equals(Policy.SPN))
		{
			queue.remove();
			if(!queue.isEmpty())
			{
				processExecution.switchToProcess(queue.element());
			}
		}
		
		if(this.policy.equals(Policy.SRT))
		{
			queue.remove();
			//System.out.println("removed: " + removed);
			if(!queue.isEmpty())
			{
				//System.out.println("First element after removing is: " + queue.element());
				processExecution.switchToProcess(queue.element());
			}
		}
		
		if(this.policy.equals(Policy.HRRN))
		{
			queue.remove();
			if(!queue.isEmpty())
			{
				processExecution.switchToProcess(queue.element());
			}
		}
	}
}
