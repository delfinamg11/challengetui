package tuichallenge.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import tuichallenge.DataTest;
import tuichallenge.dto.QuoteDto;
import tuichallenge.exception.ElementNotFoundException;
import tuichallenge.model.Quote;
import tuichallenge.repository.QuoteRepository;
import tuichallenge.util.Util;

@ExtendWith(MockitoExtension.class)
class QuoteServiceImplTest {

	private QuoteRepository quoteRepository;

	private QuoteServiceImpl quoteServiceImpl;

	@BeforeEach
	void setUp() {
		quoteRepository = Mockito.mock(QuoteRepository.class);
		quoteServiceImpl = new QuoteServiceImpl(quoteRepository, 8);
	}

	@Test
	void testGetQuoteByIdNotExist() {
		when(quoteRepository.findById(any())).thenReturn(Optional.empty());
		
		ElementNotFoundException exception=assertThrows(ElementNotFoundException.class, () ->quoteServiceImpl.findQuoteById(any()));
		
		verify(quoteRepository,times(1)).findById(any());
		assertEquals(Util.getMessageBoundle().getString("exception.notfound"), exception.getMessage());
		
	}

	@Test
	void testGetQuoteByIdExist() throws ElementNotFoundException {
		Quote createdQuote = DataTest.createQuote();
		when(quoteRepository.findById(createdQuote.getId())).thenReturn(Optional.of(createdQuote));

		QuoteDto quoteDto = quoteServiceImpl.findQuoteById(createdQuote.getId());

		verify(quoteRepository, times(1)).findById(createdQuote.getId());
		assertEquals(DataTest.createQuoteDto(), quoteDto);
	}

	@Test
	void testGetQuoteByAuthorLessThanProcessors() {
		String author = "Thomas Edison";
		when(quoteRepository.countByAuthor(author)).thenReturn(8L);
		when(quoteRepository.findByAuthor(author))
				.thenReturn(DataTest.createListQuoteSameAuthorLessOrEqualThanProcessor());

		List<QuoteDto> quotes = quoteServiceImpl.findQuotesByAuthor(author);

		verify(quoteRepository, times(1)).countByAuthor(author);
		verify(quoteRepository, times(1)).findByAuthor(author);
		verify(quoteRepository, never()).findAllByAuthor(any(), any());

		assertEquals(DataTest.createListQuoteDtoSameAuthorLessOrEqualThanProcessor(), quotes);

	}

	@Test
	void testGetQuoteByAuthorNotExists() {
		String author = "Thomas Edison";
		when(quoteRepository.countByAuthor(author)).thenReturn(0L);

		List<QuoteDto> quotes = quoteServiceImpl.findQuotesByAuthor(author);

		verify(quoteRepository, times(1)).countByAuthor(author);
		verify(quoteRepository, never()).findByAuthor(author);
		verify(quoteRepository, never()).findAllByAuthor(any(), any());

		assertEquals(0, quotes.size());

	}

	@Test
	void testGetQuoteByAuthorGreaterThanProcessors() {
		String author = "Thomas Edison";
		List<Quote> quotes = DataTest.createListQuoteSameAuthorGreaterThanProcessor();
		when(quoteRepository.countByAuthor(author)).thenReturn(200L);

		when(quoteRepository.findAllByAuthor(author, PageRequest.of(0, 26))).thenReturn(quotes.subList(0, 25));
		when(quoteRepository.findAllByAuthor(author, PageRequest.of(1, 26))).thenReturn(quotes.subList(25, 51));
		when(quoteRepository.findAllByAuthor(author, PageRequest.of(2, 26))).thenReturn(quotes.subList(51, 77));
		when(quoteRepository.findAllByAuthor(author, PageRequest.of(3, 26))).thenReturn(quotes.subList(77, 103));
		when(quoteRepository.findAllByAuthor(author, PageRequest.of(4, 26))).thenReturn(quotes.subList(103, 129));
		when(quoteRepository.findAllByAuthor(author, PageRequest.of(5, 26))).thenReturn(quotes.subList(129, 155));
		when(quoteRepository.findAllByAuthor(author, PageRequest.of(6, 26))).thenReturn(quotes.subList(155, 181));
		when(quoteRepository.findAllByAuthor(author, PageRequest.of(7, 26))).thenReturn(quotes.subList(181, 200));

		List<QuoteDto> quotesDto = quoteServiceImpl.findQuotesByAuthor(author);

		verify(quoteRepository, times(1)).countByAuthor(author);
		verify(quoteRepository, never()).findByAuthor(author);
		verify(quoteRepository, times(8)).findAllByAuthor(any(), any());

		assertEquals(DataTest.createListQuoteDtoSameAuthorrGreaterThanProcessor(), quotesDto);

	}

	@Test
	void testGetQuoteGreaterThanProcessors() {

		List<Quote> quotes = DataTest.createListQuoteGreaterThanProcessor();
		when(quoteRepository.count()).thenReturn(200L);

		when(quoteRepository.findAll(PageRequest.of(0, 26))).thenReturn(new PageImpl<Quote>(quotes.subList(0, 25)));
		when(quoteRepository.findAll(PageRequest.of(1, 26))).thenReturn(new PageImpl<Quote>(quotes.subList(25, 51)));
		when(quoteRepository.findAll(PageRequest.of(2, 26))).thenReturn(new PageImpl<Quote>(quotes.subList(51, 77)));
		when(quoteRepository.findAll(PageRequest.of(3, 26))).thenReturn(new PageImpl<Quote>(quotes.subList(77, 103)));
		when(quoteRepository.findAll(PageRequest.of(4, 26))).thenReturn(new PageImpl<Quote>(quotes.subList(103, 129)));
		when(quoteRepository.findAll(PageRequest.of(5, 26))).thenReturn(new PageImpl<Quote>(quotes.subList(129, 155)));
		when(quoteRepository.findAll(PageRequest.of(6, 26))).thenReturn(new PageImpl<Quote>(quotes.subList(155, 181)));
		when(quoteRepository.findAll(PageRequest.of(7, 26))).thenReturn(new PageImpl<Quote>(quotes.subList(181, 200)));

		List<QuoteDto> quotesDto = quoteServiceImpl.findAllQuotes();

		verify(quoteRepository, times(1)).count();
		verify(quoteRepository, never()).findAll();
		verify(quoteRepository, times(8)).findAll(any(PageRequest.class));

		assertEquals(DataTest.createListQuoteDtoGreaterThanProcessor(), quotesDto);

	}

	@Test
	void testGetAllQuotesEmpty() {
		when(quoteRepository.count()).thenReturn(0L);

		List<QuoteDto> quotes = quoteServiceImpl.findAllQuotes();

		verify(quoteRepository, times(1)).count();
		verify(quoteRepository, never()).findAll();
		verify(quoteRepository, never()).findAll(any(PageRequest.class));

		assertEquals(0, quotes.size());

	}

}
