package repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import model.Message;


@Repository
public interface MessageRepository extends CrudRepository<Message, Long>{
	
	@Query("SELECT m FROM Message m WHERE LOWER(m.username) = LOWER(:username) ORDER BY m.id DESC")
    public List<Message> find(@Param("username") String username);

}