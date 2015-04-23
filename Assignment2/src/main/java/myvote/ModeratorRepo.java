package myvote;

import org.springframework.data.mongodb.repository.MongoRepository; 
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import entities.Moderator;

//@RepositoryRestResource(collectionResourceRel = "moderators", path = "moderators")
public interface ModeratorRepo extends MongoRepository<Moderator, String> {
	
	Moderator findById(String id);
	
}
