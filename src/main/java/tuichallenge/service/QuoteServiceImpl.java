package tuichallenge.service;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import tuichallenge.dto.QuoteDto;
import tuichallenge.exception.ElementNotFoundException;
import tuichallenge.repository.QuoteRepository;

@Service
@Slf4j
public class QuoteServiceImpl implements QuoteService {

	@Autowired
	private QuoteRepository quoteRepository;

	@Override
	public QuoteDto findQuoteById(String id) throws ElementNotFoundException {
		return new QuoteDto(quoteRepository.findById(id)
				.orElseThrow(() -> new ElementNotFoundException("Quote could not be found")));
	}

	@Override
	public List<QuoteDto> findQuoteByAuthor(String author) {
		return quoteRepository.findByAuthor(author).stream().map(QuoteDto::new).collect(Collectors.toList());

	}

	@Override
	public List<QuoteDto> findAllQuotes() {
		return quoteRepository.findAll().stream().map(QuoteDto::new).collect(Collectors.toList());
	}

}
