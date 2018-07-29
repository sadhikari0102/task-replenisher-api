package com.walmart.replenisher.utils;

import java.util.Comparator;

import com.walmart.replenisher.entity.Task;

public class TaskComparator implements Comparator<Task> {

	@Override
	public int compare(Task o1, Task o2) {
		int n1 = getStatusNumber(o1.getStatus());
		int n2 = getStatusNumber(o2.getStatus());
		if(n1 == n2)
			return o1.getPriority() - o2.getPriority();
		return n1 - n2;
	}
	
	public int getStatusNumber(String status) {
		switch(status) {
		case "CREATED" :
			return 1;
		case "ASSIGNED" :
			return 2;
		case "STARTED" :
			return 3;
		case "FINISHED" :
			return 4;
		default :
			return 5;
		}
	}

}
