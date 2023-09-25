package tuichallenge.service;

import java.util.List;

import tuichallenge.dto.QuoteDto;
import tuichallenge.exception.ElementNotFoundException;

public interface QuoteService {

	/**
	 * Find quote by id
	 * 
	 * @param id
	 * @return
	 * @throws ElementNotFoundException
	 */
	QuoteDto findQuoteById(String id) throws ElementNotFoundException;

	/**
	 * find quotes by author
	 * 
	 * @param author
	 * @return
	 */
	List<QuoteDto> findQuotesByAuthor(String author);

	/**
	 * Find all quotes
	 * 
	 * @return
	 */
	List<QuoteDto> findAllQuotes();

}
