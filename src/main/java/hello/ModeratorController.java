package hello;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod.*;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@EnableWebMvcSecurity
@RequestMapping(value="/api/v1")
@RestController
public class ModeratorController extends WebSecurityConfigurerAdapter {
	
	
	Moderator moder = new Moderator();
	Polls Polls = new Polls();
	
	ArrayList <Moderator> str_list = new ArrayList<Moderator>();
	ArrayList <Polls> str_list_1 = new ArrayList<Polls>();

	private static final AtomicLong counter = new AtomicLong(123455);
	private SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
	
     int [] tmp_result = new int[2];
     int [] res = new int[2];
     int [] finalresult= new int[2];
	 String [] str_choice = new String[2];
    
	 protected void configure(HttpSecurity http) throws Exception {
                http
                .httpBasic().and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST,"/api/v1/moderators").permitAll()
                .antMatchers("/api/v1/polls/**").permitAll()
                .antMatchers("/api/v1/moderators/**").fullyAuthenticated().anyRequest().hasRole("USER");
            }

         @Autowired
            public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
                auth
                    .inMemoryAuthentication()
                        .withUser("foo").password("bar").roles("USER");
            }
    
    
    
    
	@RequestMapping(value = "/moderators", method = RequestMethod.POST)
	
	public ResponseEntity <Moderator> moderator(@Valid @RequestBody Moderator moder) {
		
		String date = new Date().toString();	
		moder.setCreated_at(formater.format(new Date()));
		moder.setId((int)counter.incrementAndGet());
		str_list.add(moder);
		
		return new ResponseEntity<Moderator>(moder,HttpStatus.CREATED);
		
	   }

	@RequestMapping(value = "/moderators/{id}", method = RequestMethod.GET)
		public ResponseEntity <Moderator> Viewmoderator(@PathVariable int id) {
	
		int identifier_id = 0;
		
		System.out.println("size is"+str_list.size());
		
		for(int i=0;i<str_list.size();i++)
		{
			if(id == str_list.get(i).getId())
			{
				identifier_id=i;
			}
		}
		
		return new ResponseEntity<Moderator>(str_list.get(identifier_id),HttpStatus.OK);
	   
	}
	
	@RequestMapping(value = "/moderators/{id}", method = RequestMethod.PUT)
	 public ResponseEntity <Moderator> updatemoderator(@Valid @RequestBody Moderator moder,@PathVariable int id) {
		
         int identifier_id = 0;
		
         String email = moder.getEmail();
		 String password= moder.getPassword();
		
		System.out.println("size is"+str_list.size());
		for(int i=0;i<str_list.size();i++)
		{
			if(id == str_list.get(i).getId())
			{
			
				identifier_id=i;
				str_list.get(i).setEmail(email);
				str_list.get(i).setPassword(password);
				
			}
			
		}
		return new ResponseEntity<Moderator>(str_list.get(identifier_id),HttpStatus.OK);
	   }
	
    @RequestMapping(value = "/moderators/{moderator_id}/polls", method = RequestMethod.POST)

	public ResponseEntity <Polls> createPoll(@Valid @RequestBody Polls Polls,@PathVariable int moderator_id) {
		
    	Polls.setId(Integer.toString((int) counter.incrementAndGet(),36));
    	
    	str_list_1.add(Polls);
    
    	
		for(int i=0;i<str_list.size();i++)
		{
			if(moderator_id == str_list.get(i).getId())
			{
				str_list.get(i).getPollslist().add(Polls);
		
			}
			
		}
	
		return new ResponseEntity<Polls>(Polls,HttpStatus.CREATED);
		
	   }
   
  

	@RequestMapping(value = "/polls/{poll_id}", method = RequestMethod.GET)
		
	    public ResponseEntity <Polls> viewPollsWithoughResult(@PathVariable String poll_id) {
	
		int identifier_id = 0;
		
		System.out.println("size is"+str_list_1.size());
		
		for(int i=0; i<str_list_1.size(); i++)
		{
			if(poll_id.equals(str_list_1.get(i).getId()))
			{
				identifier_id=i;
			}
		}
		
		return new ResponseEntity<Polls>(str_list_1.get(identifier_id),HttpStatus.OK);
	}

	@RequestMapping(value = "/moderators/{moderator_id}/polls/{poll_id}", method = RequestMethod.GET)
	public ResponseEntity viewPollWithResult(@PathVariable int moderator_id,@PathVariable String poll_id) {

    int identifier_id = 0;
	System.out.println("size is"+str_list.size());
	
	for(int i=0;i<str_list.size();i++)
	{
		if(moderator_id == str_list.get(i).getId())
		{
		
			for(int j=0;j<str_list_1.size();j++)
			{
				if(poll_id.equals(str_list_1.get(j).getId()))	
				{
					return new ResponseEntity(str_list.get(i).getPollslist().get(j),HttpStatus.OK);
				}
			}
		}	
	
	}
	
     return new ResponseEntity("View Polls is not sucessfull",HttpStatus.OK);
}

	@RequestMapping(value = "/moderators/{moderator_id}/polls", method = RequestMethod.GET)
	public ResponseEntity listAllPolls(@PathVariable int moderator_id) {

    int identifier_id = 0;
	System.out.println("size is"+str_list.size());
	
	for(int i=0;i<str_list.size();i++)
	{
		if(moderator_id == str_list.get(i).getId())
		{
		
					return new ResponseEntity(str_list.get(i).getPollslist(),HttpStatus.OK);
		}	
	}
     return new ResponseEntity("View Polls is not sucessfull",HttpStatus.OK);
}

	
	
	@RequestMapping(value = "/moderators/{moderator_id}/polls/{poll_id}", method = RequestMethod.DELETE)
	public ResponseEntity deletePoll(@PathVariable int moderator_id,@PathVariable String poll_id) {

    int identifier_id = 0;
	System.out.println("size is"+str_list.size());
	
	for(int i=0;i<str_list.size();i++)
	{
		if(moderator_id == str_list.get(i).getId())
		{
		
			for(int j=0;j<str_list_1.size();j++)
			{
				if(poll_id.equals(str_list_1.get(j).getId()))	
				{
					str_list_1.remove(j);
					return new ResponseEntity(str_list.get(i).getPollslist(),HttpStatus.NO_CONTENT);
				}
			}
		}	
	
	}
	
     return new ResponseEntity("Delete Polls is not sucessfull",HttpStatus.OK);
}

	
	
	 @RequestMapping(value = "/polls/{poll_id}", method = RequestMethod.PUT)
	 public ResponseEntity voteAPoll(@PathVariable String poll_id,@RequestParam(value="str_choice")int choice_index) 
	 {
		 for(int i=0;i<str_list_1.size();i++)
		    {
			  if(poll_id.equals(str_list_1.get(i).getId()))
			  {
			   
				  	if(choice_index == 0)
				  		{
				  		
				  		tmp_result=str_list_1.get(i).getResult();
				  		tmp_result[choice_index]=tmp_result[choice_index]+1;	
				  		str_list_1.get(i).setResult(tmp_result);
				  	 	return new ResponseEntity(HttpStatus.NO_CONTENT);
				  		
				  		}
				  	else if(choice_index==1)
		            {
				  		 tmp_result=str_list_1.get(i).getResult();
				  		 tmp_result[choice_index]=tmp_result[choice_index]+1;
						 str_list_1.get(i).setResult(tmp_result);
						 return new ResponseEntity(HttpStatus.NO_CONTENT);
		            }
				  	
    		    }
            }
		    	 
           	return new ResponseEntity("Not able to vote",HttpStatus.NO_CONTENT);
		
	   }

     @ExceptionHandler(MethodArgumentNotValidException.class)
     @ResponseBody
     public ResponseEntity handleBadInput(MethodArgumentNotValidException e)
     {
         String errors="";
         for(FieldError obj: e.getBindingResult().getFieldErrors())
             {
                 errors+=obj.getDefaultMessage();
             }    
         return new ResponseEntity(errors,HttpStatus.BAD_REQUEST);
     }
}
