package tuichallenge.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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

	private ExecutorService executorService;

	private Integer numberProcessors;

	public QuoteServiceImpl() {
		numberProcessors = Runtime.getRuntime().availableProcessors();
		executorService = Executors.newFixedThreadPool(numberProcessors);
	}

	public QuoteServiceImpl(QuoteRepository quoteRepository, Integer numberProcessors) {
		this.numberProcessors = numberProcessors;
		executorService = Executors.newFixedThreadPool(numberProcessors);
		this.quoteRepository = quoteRepository;
	}

	@Override
	public QuoteDto findQuoteById(String id) throws ElementNotFoundException {
		return new QuoteDto(quoteRepository.findById(id)
				.orElseThrow(() -> new ElementNotFoundException("Quote could not be found")));
	}

	@Override
	public List<QuoteDto> findQuotesByAuthor(String author) {
		Long total = quoteRepository.countByAuthor(author);
		if (total == 0) {
			return Collections.emptyList();
		} else if (total <= numberProcessors) {
			return quoteRepository.findByAuthor(author).stream().map(QuoteDto::new).collect(Collectors.toList());
		} else {
			return getQuotesAllByParts((a, b) -> createFutureFindAllByAuthor(author, a, b), total.intValue());
		}
	}

	@Override
	public List<QuoteDto> findAllQuotes() {
		Long total = quoteRepository.count();
		if (total == 0) {
			return Collections.emptyList();
		} else if (total <= numberProcessors) {
			return quoteRepository.findAll().stream().map(QuoteDto::new).collect(Collectors.toList());
		} else {
			return getQuotesAllByParts((a, b) -> createFutureFindAll(a, b), total.intValue());
		}
	}

	/**
	 * Make parallel call to database depending on the number of processor available
	 * 
	 * @param createFuture
	 * @param total
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	private List<QuoteDto> getQuotesAllByParts(
			BiFunction<Integer, Integer, CompletableFuture<Stream<QuoteDto>>> createFuture, Integer total) {

		List<CompletableFuture<Stream<QuoteDto>>> listFuture = new ArrayList<CompletableFuture<Stream<QuoteDto>>>();

		// page size depending of processor and number of preocessor needed
		Integer pageSize = (total / numberProcessors) + 1;
		Integer pageNumber = (total / pageSize) + 1;
		if (total % pageSize == 0) {
			pageNumber--;
		}

		for (int i = 0; i < pageNumber; i++) {
			listFuture.add(createFuture.apply(i, pageSize));
		}

		return listFuture.stream().flatMap(a -> a.join()).collect(Collectors.toList());

	}

	/**
	 * Create a future that finds all quotes by page and size
	 * 
	 * @param page
	 * @param pageSize
	 * @return
	 */
	private CompletableFuture<Stream<QuoteDto>> createFutureFindAll(Integer page, Integer pageSize) {
		return CompletableFuture.supplyAsync(
				() -> quoteRepository.findAll(PageRequest.of(page, pageSize)).get().map(QuoteDto::new),
				executorService);
	}

	/**
	 * Create a future that finds all quotes by author, page and size
	 * 
	 * @param author
	 * @param page
	 * @param pageSize
	 * @return
	 */
	private CompletableFuture<Stream<QuoteDto>> createFutureFindAllByAuthor(String author, Integer page,
			Integer pageSize) {
		return CompletableFuture.supplyAsync(() -> quoteRepository
				.findAllByAuthor(author, PageRequest.of(page, pageSize)).stream().map(QuoteDto::new), executorService);
	}
}
