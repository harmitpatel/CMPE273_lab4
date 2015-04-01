package hello;

import java.util.*;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;


public class Moderator {
	/*
	name": "John Smith",
	 "email": "John.Smith@Gmail.com",
	 "password": "secret"
	*/
       @Id
	   int id;

	  // @JsonIgnore
	  // String [] polls;
	  
	    @NotNull(message="Name should not be Null\n")
	   // @NotEmpty(message="Name cannot be Empty\n")
	    String name;
	 
	    @NotNull(message="Email should not Null\n")
	    @NotEmpty(message="Email cannot be Empty")
	   String  email;

	    @NotNull(message="Password should not Null\n")
	    @NotEmpty(message="Password cannot be Empty\n")
	    String password;
	    
	   String created_at;
	   
	   @JsonIgnore
	   ArrayList <String> pollslist = new ArrayList<String>();


	   public ArrayList<String> getPollslist() {
		return pollslist;
	}
	   public void setPollslist(ArrayList<String> pollslist) {
		this.pollslist = pollslist;
	}




    public Moderator() {
		//super();
	}
	/*public String[] getPolls() {
		return polls;
	}
	public void setPolls(String[] polls) {
		this.polls = polls;
	}*/
	public String getCreated_at() {
		
		 return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	
	}
	public Moderator(String name, String email, String password) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setName(String name) {
	      this.name= name;
	   }
	   public String getName() {
	      return name;
	   }

	   public void setEmail(String email) {
	      this.email = email;
	   }
	   public String getEmail() {
	      return email;
	   }

	   public void setPassword(String password) {
	      this.password = password;
	   }
	   public String getPassword() {
	      return password;
	   }

}
