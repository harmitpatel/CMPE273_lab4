package myvote;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Repository;

import entities.Moderator;
import entities.Poll;

@Repository
@SpringBootApplication
public class Utility {

	@Autowired
	private ModeratorRepo moderatorRespository;
	@Autowired
	private PollRepo pollRepository;
	ArrayList<Poll> Ex_poll = new ArrayList<Poll>();

	public Moderator search_moderator_by_id(String id) {

		Moderator mod = moderatorRespository.findById(id);
		return mod;
	}

	public Moderator update_moderator_by_id(String id, String new_email,
			String new_password, String new_name) {
		Moderator mod = moderatorRespository.findById(id);
		if (mod != null) {
			if (!new_email.equalsIgnoreCase("")) {
				mod.setEmail(new_email);
			}
			if (!new_name.equalsIgnoreCase("")) {
				mod.setName(new_name);
			}
			if (!new_password.equalsIgnoreCase("")) {
				mod.setPassword(new_password);
			}

			moderatorRespository.save(mod);
			return mod;
		}
		return null;
	}

	public Poll search_poll_by_id(String id) {

		Poll poll = pollRepository.findById(id);
		return poll;
	}

	public void update_result(Poll poll, int choice) {

	}

	public void addPoll(Poll poll) {
		pollRepository.save(poll);
	}

	public void addModerator(Moderator moderator) {
		moderatorRespository.save(moderator);
	}

	public Moderator getModerator(String moderatorId) {
		Moderator mod = moderatorRespository.findById(moderatorId);
		return mod;
	}

	public void removePoll(String pollId) {
		pollRepository.delete(pollId);
	}

	public void vote(Poll p) {
		pollRepository.save(p);
	}

	public ArrayList get_moderator_polls(Moderator moderator) {
		return pollRepository.findByModerator(moderator);

	}

	public String getISO_Date() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.ms'Z'");
		String nowAsISO = df.format(new Date());
		return nowAsISO;
	}
	
	public ArrayList pollLookUp(){
		
		List<Poll> poll = pollRepository.findAll();
		for(int i=0; i<poll.size();i++){
				if(getISO_Date().compareTo(poll.get(i).getExpired_at()) > 0 ){
					System.out.println("Condition Satified!!!");
					System.out.println("Expired Poll: "+poll.get(i).getId());
					System.out.println("Current Date: "+getISO_Date());
					System.out.println("Date of Poll Expiration: "+poll.get(i).getExpired_at());
					Ex_poll.add(poll.get(i));
				}
			//}
		}
		return Ex_poll;
	}
	
}
