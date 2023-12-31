package tuichallenge.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import tuichallenge.model.Quote;

@Repository
public interface QuoteRepository extends MongoRepository<Quote, String> {

	List<Quote> findByAuthor(String author);

	List<Quote> findAllByAuthor(String author, Pageable page);

	long countByAuthor(String author);
	
	List<Quote> findAll();

}
