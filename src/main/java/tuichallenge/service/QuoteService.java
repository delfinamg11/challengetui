package tuichallenge.service;

import java.util.List;

import tuichallenge.dto.QuoteDto;
import tuichallenge.exception.ElementNotFoundException;

public interface QuoteService {

	QuoteDto findQuoteById(String id) throws ElementNotFoundException;

	List<QuoteDto> findQuoteByAuthor(String author);

	List<QuoteDto> findAllQuotes();

}
