package SMSVoting;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import beans.Poll;

@Component
public class ExPollScheduler
{

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	SimpleProducer producer;
	@Autowired
	SMSUtil utility;
	Poll poll;

    @Scheduled(fixedRate = 300000) //5 minutes 300000 miliseconds
    public void mailScheduler() 
    {
    	
    	ArrayList<Poll> expired_Poll = new ArrayList<Poll>();
        producer= new SimpleProducer();
        expired_Poll = utility.pollLookUp();  
        System.out.println("List of Expired Polls: "+expired_Poll);
        
        if(!expired_Poll.isEmpty())
        {
        	for(int i=0; i<expired_Poll.size();i++)
        	{	
        		if(expired_Poll.get(i).isFlag() != true)
				{
        			
        			producer.kafka("cmpe273-new-topic", expired_Poll.get(i).getModerator().getEmail() + ":010124634:Poll Result [" + expired_Poll.get(i).getChoice()[0] + "=" + expired_Poll.get(i).getResults()[0] + " , " + expired_Poll.get(i).getChoice()[1] + " = " + expired_Poll.get(i).getResults()[1]);
        			System.out.println("Condition Satified!!!");
        			poll = expired_Poll.get(i);
        			poll.setFlag(true);
        			utility.UpdatePoll(poll);
        			System.out.println();
					
				}
        	}	
        }
    }  
}

