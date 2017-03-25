package com.ru.usty.scheduling;

import java.util.Stack;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import com.ru.usty.scheduling.process.ProcessExecution;

public class Scheduler {

	ProcessExecution processExecution;
	Policy policy;
	int quantum;
	Stack<Integer> stk;
	Timer timer = new Timer();
	Timer timer2 = new Timer();
	int stkAt;
	boolean timerSet;
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
			
			timer.cancel();
			stk = new Stack<Integer> ();
			
			break;
		case RR:	//Round robin
			System.out.println("Starting new scheduling task: Round robin, quantum = " + quantum);
			
	        timer.cancel();
	        timer = new Timer();
			timerSet = false;
			
			stk = new Stack<Integer>();
			stkAt = 0;
			
			break;
		case SPN:	//Shortest process next
			System.out.println("Starting new scheduling task: Shortest process next");
			
			timer.cancel();
			queue = new LinkedList<Integer> ();

			break;
		case SRT:	//Shortest remaining time
			System.out.println("Starting new scheduling task: Shortest remaining time");
			
			queue = new LinkedList<Integer> ();
			
			break;
		case HRRN:	//Highest response ratio next
			System.out.println("Starting new scheduling task: Highest response ratio next");
			
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
		
		if(this.policy.equals(Policy.FCFS))
		{
			stk.push(processID);
			if(!stk.empty())
			{
				processExecution.switchToProcess(stk.elementAt(0));
			}
		}
		
		if(this.policy.equals(Policy.RR))
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
			// Since the queue is empty we can add a process to it without checking execution and service times and start the process right away
			if(queue.isEmpty())
			{
				queue.add(processID);
				processExecution.switchToProcess(queue.element());
			}
			else
			{
				// if the queue is not empty we check if the new process has a better service time than the one that is currently running
				if(processExecution.getProcessInfo(processID).totalServiceTime < (processExecution.getProcessInfo(queue.element()).totalServiceTime - processExecution.getProcessInfo(queue.element()).elapsedExecutionTime))
				{
					// if so we make the new process the first element in the queue
					queue.addFirst(processID);
				}
				else
				{
					// if the process hasn't got a better service time than the one currently running we need to find out where to put it
					// we check if the queue has more than one value, if not we add it to the back of the queue
					// else we iterate through the queue and place the new one at an appropriate location in it
					if(queue.size() > 1)
					{
						boolean added = false;
						
						// checking if the new process has a shorter service time than current processes in the queue
						for(int i = 1; i < queue.size(); i++)
						{
							// with the current processes in queue we compare the service time minus execution time with the new process service time since the new process has an execution time of zero
							if(processExecution.getProcessInfo(processID).totalServiceTime < (processExecution.getProcessInfo(queue.get(i)).totalServiceTime - processExecution.getProcessInfo(queue.get(i)).elapsedExecutionTime))
							{
								queue.add(i, processID);
								added = true;
								break;
							}
						}
						
						// making sure we add to the back of the list if process wasn't added to queue in for-loop
						if(!added)
						{
							queue.add(processID);
						}
					}
					else
					{
						queue.add(processID);
					}
				}
				// we start the process that is first in the queue
				processExecution.switchToProcess(queue.element());
			}
		}
		
		if(this.policy.equals(Policy.HRRN))
		{
			// ATTN: There is no need to sort processes when they come in, they are at ratio 1 when they arrive
			
			if(queue.isEmpty())
			{
				queue.add(processID);
				processExecution.switchToProcess(queue.element());
			}
			else
			{
				// since the queue isn't empty we don't need to start up a process (we assume there's already a process running)
				queue.add(processID);
			}
		}
	}

	/**
	 * DO NOT CHANGE DEFINITION OF OPERATION
	 */
	public void processFinished(int processID) {
		
		if(this.policy.equals(Policy.FCFS))
		{
			stk.remove(0);
			if(!stk.empty())
			{
				processExecution.switchToProcess(stk.elementAt(0));
			}
		}
		
		if(this.policy.equals(Policy.RR))
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
			if(!queue.isEmpty())
			{
				processExecution.switchToProcess(queue.element());
			}
		}
		
		if(this.policy.equals(Policy.HRRN))
		{
			queue.remove();
			
			// after finishing and removing the previously running process we want to find the next one to run
			// we find that by finding how long a process has been waiting to start and the expected running time
			if(!queue.isEmpty())
			{
				if(queue.size() > 1)
				{
					// because every new process added starts with 1 in ratio
					double highestRatioFound = 1.0;
					
					for(int i = 0; i < queue.size(); i++)
					{
						double tempRatio = (processExecution.getProcessInfo(queue.get(i)).elapsedWaitingTime + processExecution.getProcessInfo(queue.get(i)).totalServiceTime) / processExecution.getProcessInfo(queue.get(i)).totalServiceTime;
						if(tempRatio > highestRatioFound)
						{
							highestRatioFound = tempRatio;
							queue.add(0, queue.remove(i)); // we do it like this so we both add to the front and remove the process from its previous place in the queue
						}
					}
				}
				processExecution.switchToProcess(queue.element());
			}
		}
	}
}
