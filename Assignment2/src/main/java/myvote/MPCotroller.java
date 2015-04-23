package myvote;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import entities.Moderator;
import entities.Poll;
import entities.View;

@RestController
@RequestMapping("/api/v1")
@EnableWebSecurity
public class MPCotroller {

	ArrayList moderator_list = new ArrayList();
	ArrayList poll_list = new ArrayList();

	@Autowired
	Utility utility;

	private final AtomicInteger moderator_counter = new AtomicInteger();
	private final AtomicInteger poll_counter = new AtomicInteger();

	@RequestMapping(value = "/moderators/{moderatorId}", method = { RequestMethod.GET })
	public @ResponseBody ResponseEntity<Moderator> getModerator(
			@PathVariable String moderatorId) throws Exception {
		System.out.println("get Moderator" + moderatorId);
		Moderator mod = utility.getModerator(moderatorId);
		return new ResponseEntity<Moderator>(mod, HttpStatus.OK);
	}

	@RequestMapping(value = "/moderators", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Moderator> createModerator(
			@RequestBody @Validated({ Validation.ValidateAll.class }) Moderator moderator) {
		// System.out.println("Counter is" + moderator_counter);
		String count_string = String.valueOf(moderator_counter
				.incrementAndGet());
		Moderator m = new Moderator(count_string, moderator.getName(),
				moderator.getEmail(), moderator.getPassword(),
				utility.getISO_Date());
		utility.addModerator(m);
		return new ResponseEntity<Moderator>(m, HttpStatus.CREATED);

	}

	@RequestMapping(value = "/moderators/{moderatorId}", method = { RequestMethod.PUT })
	@ResponseStatus(value = HttpStatus.CREATED)
	public ResponseEntity<Moderator> updateModerator(
			@PathVariable String moderatorId,
			@RequestBody @Validated({ Validation.ValidateFields.class }) Moderator moderator) {
		System.out.println("Updating Moderator " + moderatorId);
		if (moderatorId != null) {
			Moderator mod = utility.update_moderator_by_id(moderatorId,
					moderator.getEmail(), moderator.getPassword(),
					moderator.getName());
			if (mod != null)
				return new ResponseEntity<Moderator>(mod, HttpStatus.OK);
		}

		return new ResponseEntity<Moderator>(HttpStatus.BAD_REQUEST);

	}

	@JsonView(View.WithoutResult.class)
	@RequestMapping(value = "/moderators/{moderatorId}/polls", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Poll> createPoll(@PathVariable String moderatorId,
			@RequestBody @Valid Poll poll) {
		Poll p = null;
		Moderator m = utility.search_moderator_by_id(moderatorId);
		if (m != null) {
			p = new Poll("POLL" + poll_counter.incrementAndGet(),
					poll.getQuestion(), poll.getStarted_at(),
					poll.getExpired_at(), poll.getChoice());
			// mapping pole to moderator
			p.setModerator(m);
			System.out.println("Added POLL");
			utility.addPoll(p);
			return new ResponseEntity<Poll>(p, HttpStatus.CREATED);
		}

		return new ResponseEntity<Poll>(p, HttpStatus.BAD_REQUEST);

	}

	@JsonView(View.WithoutResult.class)
	@RequestMapping(value = "/polls/{pollId}", method = { RequestMethod.GET })
	public @ResponseBody ResponseEntity<Poll> getPoll_without_result(
			@PathVariable String pollId) throws Exception {
		if (!pollId.equals("")) {
			Poll p = utility.search_poll_by_id(pollId);
			if (p != null)
				return new ResponseEntity<Poll>(p, HttpStatus.OK);
		}

		return new ResponseEntity<Poll>(HttpStatus.BAD_REQUEST);

	}

	@RequestMapping(value = "/moderators/{moderatorId}/polls/{pollId}", method = { RequestMethod.GET })
	public @ResponseBody ResponseEntity<Poll> getPoll_with_result(
			@PathVariable String moderatorId, @PathVariable String pollId)
			throws Exception {
		Moderator mod = utility.search_moderator_by_id(moderatorId);
		if (mod != null) {
			if (!pollId.equals("")) {
				Poll p = utility.search_poll_by_id(pollId);
				if (p != null && p.getModerator().getId().equals(mod.getId()))
					return new ResponseEntity<Poll>(p, HttpStatus.OK);
			}
		}
		return new ResponseEntity<Poll>(HttpStatus.BAD_REQUEST);

	}

	@RequestMapping(value = "/moderators/{moderatorId}/polls", method = { RequestMethod.GET })
	public @ResponseBody ResponseEntity<ArrayList<Poll>> getList_Polls(
			@PathVariable String moderatorId) throws Exception {
		Moderator m = utility.search_moderator_by_id(moderatorId);
		if (m != null) {

			ArrayList<Poll> pollList = utility.get_moderator_polls(m);
			if (pollList != null) {
				return new ResponseEntity<ArrayList<Poll>>(pollList,
						HttpStatus.OK);
			}

		}
		return new ResponseEntity<ArrayList<Poll>>(HttpStatus.BAD_REQUEST);

	}

	@RequestMapping(value = "/moderators/{moderatorId}/polls/{pollId}", method = { RequestMethod.DELETE })
	public @ResponseBody ResponseEntity<String> delete_Polls(
			@PathVariable String moderatorId, @PathVariable String pollId)
			throws Exception {

		if (!moderatorId.equals("")) {
			Moderator m = utility.search_moderator_by_id(moderatorId);
			if (m != null) {

				if (!pollId.equals("")) {
					Poll p = utility.search_poll_by_id(pollId);
					if (p != null && p.getModerator().getId().equals(m.getId())) {
						utility.removePoll(pollId);
						return new ResponseEntity<String>("Record Deleted!!",
								HttpStatus.NO_CONTENT);
					} else {
						return new ResponseEntity<String>(
								"You dont have access to Delete this record!!",
								HttpStatus.NO_CONTENT);
					}
				}
			}
		}
		return new ResponseEntity<String>("No Record Found/Deleted!!",
				HttpStatus.NO_CONTENT);

	}

	@RequestMapping(value = "/polls/{pollId}", method = { RequestMethod.PUT })
	public ResponseEntity<String> vote(@PathVariable String pollId,
			@RequestParam Integer choice) {
		if (!pollId.equals("")) {
			Poll p = utility.search_poll_by_id(pollId);
			if (p != null && choice < p.getResults().length) {
				p.getResults()[choice]++;
				utility.vote(p);
				return new ResponseEntity<String>("Voting Completed!!",
						HttpStatus.NO_CONTENT);
			}
		}
		return new ResponseEntity<String>("No Voting don!!",
				HttpStatus.NO_CONTENT);

	}

}
