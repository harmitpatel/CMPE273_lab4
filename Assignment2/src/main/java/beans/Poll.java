package beans;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
@Document
public class Poll {
	 @JsonView(View.WithoutResult.class)
	private String id;
	 @JsonView(View.WithoutResult.class)
	 @NotBlank
	private String question;
	 @JsonView(View.WithoutResult.class)
	@NotBlank
	private String started_at;
	 @JsonView(View.WithoutResult.class)
	@NotBlank
	private String expired_at;
	 @JsonView(View.WithoutResult.class)
	@NotNull
	private String[] choice;
	 
	private Integer[] results;
	@JsonIgnore
	private Moderator moderator;
	
	@JsonIgnore
	private boolean flag;
	
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	
	public String[] getChoice() {
		return choice;
	}
	public void setChoice(String[] choice) {
		this.choice = choice;
	}
	public Integer[] getResults() {
		return results;
	}
	public void setResults(Integer[] results) {
		this.results = results;
	}
	public String getStarted_at() {
		return started_at;
	}
	public void setStarted_at(String started_at) {
		this.started_at = started_at;
	}
	public String getExpired_at() {
		return expired_at;
	}
	public void setExpired_at(String expired_at) {
		this.expired_at = expired_at;
	}
	
	public Moderator getModerator() {
		return moderator;
	}
	public void setModerator(Moderator moderator) {
		this.moderator = moderator;
	}
	public Poll(String id,String question,String started_at,String expired_at,String[] choice){
		Integer[] result=create_dynamic_array(choice.length);
		this.id=id;
		this.question=question;
		this.started_at=started_at;
		this.expired_at=expired_at;
		this.choice=choice;
		this.results=result;
		
	}
	public Poll(){
		
	}
	public Poll(String id,String question,String started_at,String expired_at,String[] choice,Integer[] result){
		this.id=id;
		this.question=question;
		this.started_at=started_at;
		this.expired_at=expired_at;
		this.choice=choice;
		this.results=result;
		
	}
	
	private Integer[] create_dynamic_array(int max){
		
		Integer[] result=new Integer[max];
		
		for(int i=0;i<max;i++){
			
			result[i]=0;
		}
		
		return result;
	}

}
