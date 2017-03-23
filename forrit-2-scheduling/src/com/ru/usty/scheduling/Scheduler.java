package com.ru.usty.scheduling;

import java.util.Stack;
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
			/**
			 * Add your policy specific initialization code here (if needed)
			 */
			break;
		case SRT:	//Shortest remaining time
			System.out.println("Starting new scheduling task: Shortest remaining time");
			/**
			 * Add your policy specific initialization code here (if needed)
			 */
			break;
		case HRRN:	//Highest response ratio next
			System.out.println("Starting new scheduling task: Highest response ratio next");
			/**
			 * Add your policy specific initialization code here (if needed)
			 */
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
		
	}
}
