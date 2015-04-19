package SMSVoting;

import org.springframework.data.mongodb.repository.MongoRepository; 
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import beans.Moderator;

//@RepositoryRestResource(collectionResourceRel = "moderators", path = "moderators")
public interface ModRepo extends MongoRepository<Moderator, String> {
	
	Moderator findById(String id);
	
}
