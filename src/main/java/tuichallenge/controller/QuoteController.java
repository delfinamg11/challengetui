package tuichallenge.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tuichallenge.dto.QuoteDto;
import tuichallenge.exception.ElementNotFoundException;
import tuichallenge.service.QuoteService;

@RestController
@RequestMapping("/quotes")
public class QuoteController {

	@Autowired
	private QuoteService quoteService;

	@GetMapping(value = "/{id}")
	public ResponseEntity<QuoteDto> getQuoteById(@PathVariable String id) throws ElementNotFoundException {

		return ResponseEntity.ok(quoteService.findQuoteById(id));
	}

	@GetMapping(value = "/author/{author}")
	public ResponseEntity<List<QuoteDto>> getQuoteByAuthor(@PathVariable String author) {
		return ResponseEntity.ok(quoteService.findQuoteByAuthor(author));
	}

	@GetMapping(value = "/all")
	public ResponseEntity<List<QuoteDto>> getAll()  {
		return ResponseEntity.ok(quoteService.findAllQuotes());
	}

}
