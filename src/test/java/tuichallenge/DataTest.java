package tuichallenge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tuichallenge.dto.QuoteDto;
import tuichallenge.model.Quote;

public class DataTest {

	public static QuoteDto createQuoteDto() {
		return new QuoteDto("Thomas Edison", "Hell, there are no rules here-- we're trying to accomplish something.",
				Arrays.asList("Success", "Motivational"), "thomas-edison", 70, "2023-04-14", "2023-04-14");
	}

	public static Quote createQuote() {
		return new Quote("An5NAXPrbN", "Thomas Edison",
				"Hell, there are no rules here-- we're trying to accomplish something.",
				Arrays.asList("Success", "Motivational"), "thomas-edison", 70, "2023-04-14", "2023-04-14");
	}

	public static List<Quote> createListQuoteSameAuthorLessOrEqualThanProcessor() {
		List<Quote> quotes = new ArrayList<Quote>();
		String content = "something ";
		String id = "An5NAXPrb";
		for (int i = 0; i < 8; i++) {
			quotes.add(new Quote(id + i, "Thomas Edison", content + "new" + i, Arrays.asList("Success", "Motivational"),
					"thomas-edison", 70, "2023-04-14", "2023-04-14"));
		}
		return quotes;
	}

	public static List<QuoteDto> createListQuoteDtoSameAuthorLessOrEqualThanProcessor() {
		List<QuoteDto> quotes = new ArrayList<QuoteDto>();
		String content = "something ";
		for (int i = 0; i < 8; i++) {
			quotes.add(new QuoteDto("Thomas Edison", content + "new" + i, Arrays.asList("Success", "Motivational"),
					"thomas-edison", 70, "2023-04-14", "2023-04-14"));
		}
		return quotes;
	}
	
	
	public static List<Quote> createListQuoteSameAuthorGreaterThanProcessor() {
		List<Quote> quotes = new ArrayList<Quote>();
		String content = "something ";
		String id = "An5NAXPrb";
		for (int i = 0; i < 200; i++) {
			quotes.add(new Quote(id + i, "Thomas Edison", content + "new" + i, Arrays.asList("Success", "Motivational"),
					"thomas-edison", 70, "2023-04-14", "2023-04-14"));
		}
		return quotes;
	}

	public static List<QuoteDto> createListQuoteDtoSameAuthorrGreaterThanProcessor() {
		List<QuoteDto> quotes = new ArrayList<QuoteDto>();
		String content = "something ";
		for (int i = 0; i < 200; i++) {
			quotes.add(new QuoteDto("Thomas Edison", content + "new" + i, Arrays.asList("Success", "Motivational"),
					"thomas-edison", 70, "2023-04-14", "2023-04-14"));
		}
		return quotes;
	}
	
	
	public static List<Quote> createListQuoteLessOrEqualThanProcessor() {
		List<Quote> quotes = new ArrayList<Quote>();
		String content = "something ";
		String id = "An5NAXPrb";
		for (int i = 0; i < 8; i++) {
			quotes.add(new Quote(id + i, "Thomas Edison", content + "new" + i, Arrays.asList("Success", "Motivational"),
					"thomas-edison", 70, "2023-04-14", "2023-04-14"));
		}
		return quotes;
	}

	public static List<QuoteDto> createListQuoteDtoLessOrEqualThanProcessor() {
		List<QuoteDto> quotes = new ArrayList<QuoteDto>();
		String content = "something ";
		for (int i = 0; i < 8; i++) {
			quotes.add(new QuoteDto("Thomas Edison", content + "new" + i, Arrays.asList("Success", "Motivational"),
					"thomas-edison", 70, "2023-04-14", "2023-04-14"));
		}
		return quotes;
	}
	
	
	public static List<Quote> createListQuoteGreaterThanProcessor() {
		List<Quote> quotes = new ArrayList<Quote>();
		String content = "something ";
		String id = "An5NAXPrb";
		for (int i = 0; i < 200; i++) {
			quotes.add(new Quote(id + i, "Thomas Edison", content + "new" + i, Arrays.asList("Success", "Motivational"),
					"thomas-edison", 70, "2023-04-14", "2023-04-14"));
		}
		return quotes;
	}

	public static List<QuoteDto> createListQuoteDtoGreaterThanProcessor() {
		List<QuoteDto> quotes = new ArrayList<QuoteDto>();
		String content = "something ";
		for (int i = 0; i < 200; i++) {
			quotes.add(new QuoteDto("Thomas Edison", content + "new" + i, Arrays.asList("Success", "Motivational"),
					"thomas-edison", 70, "2023-04-14", "2023-04-14"));
		}
		return quotes;
	}
	
	
	public static List<Quote> createListQuoteSameAuthorGreaterThanProcessorToCreate() {
		List<Quote> quotes = new ArrayList<Quote>();
		String content = "something ";
		for (int i = 0; i < 200; i++) {
			quotes.add(new Quote(null, "Thomas Edison", content + "new" + i, Arrays.asList("Success", "Motivational"),
					"thomas-edison", 70, "2023-04-14", "2023-04-14"));
		}
		return quotes;
	}
	
	public static Quote createQuoteToCreate() {
		return new Quote(null, "Thomas Edison",
				"Hell, there are no rules here-- we're trying to accomplish something.",
				Arrays.asList("Success", "Motivational"), "thomas-edison", 70, "2023-04-14", "2023-04-14");
	}
	
	
	

}
