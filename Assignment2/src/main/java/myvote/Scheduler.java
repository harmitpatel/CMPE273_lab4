package myvote;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


import entities.Poll;

@Component
public class Scheduler {

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	SimpleProducer producer;
	@Autowired
	Utility utility;
	Poll poll;

    @Scheduled(fixedRate = 300000) //5 minutes 300000 miliseconds
    public void mailScheduler() {
    	
    	ArrayList<Poll> Ex_poll = new ArrayList<Poll>();
        producer= new SimpleProducer();
        Ex_poll = utility.pollLookUp();  
        System.out.println("List of Expired Polls: "+Ex_poll);
        
        if(!Ex_poll.isEmpty()){
        	for(Poll p : Ex_poll){	
        		producer.kafka("cmpe273-topic", "madhurab31@gmail.com:010125505:htd");
        	}	
        }
    }   
}
