package entities;

import javax.validation.constraints.NotNull;

import myvote.Validation;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
public class Moderator{
	private String id;
	
	
	@NotBlank(groups={Validation.ValidateAll.class})
	private String name;
	
	@NotNull(groups={Validation.ValidateAll.class,Validation.ValidateFields.class})
	@NotBlank(groups={Validation.ValidateAll.class,Validation.ValidateFields.class})
	@Length(min=7,groups={Validation.ValidateAll.class,Validation.ValidateFields.class})
	private String email;
	@NotNull(groups={Validation.ValidateAll.class,Validation.ValidateFields.class})
	@NotBlank(groups={Validation.ValidateAll.class,Validation.ValidateFields.class})
	@Length(min=5,groups={Validation.ValidateAll.class,Validation.ValidateFields.class})
	private String password;
	private String created_at;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Moderator(String id, String name,String email,String password,String created_at){
		this.id=id;
		this.name=name;
		this.email=email;
		this.password=password;		
		this.created_at=created_at;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	public Moderator(){
		
		
	}
	

}
