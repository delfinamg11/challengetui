package tuichallenge.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import tuichallenge.DataTest;
import tuichallenge.configuration.MongoConfiguration;
import tuichallenge.error.ApiError;
import tuichallenge.exception.ElementNotFoundException;
import tuichallenge.service.QuoteService;
import tuichallenge.util.Util;

@WebMvcTest(controllers = QuoteController.class, excludeAutoConfiguration = MongoConfiguration.class)
class QuoteControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private QuoteService quoteService;

	@Test
	void getCorrectQuote() throws Exception {

		String id = DataTest.createQuote().getId();
		when(quoteService.findQuoteById(id)).thenReturn(DataTest.createQuoteDto());

		MockHttpServletResponse response = mockMvc.perform(get("/quotes/{id}", id)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();

		verify(quoteService, times(1)).findQuoteById(id);

		assertEquals(objectMapper.writeValueAsString(DataTest.createQuoteDto()), response.getContentAsString());

	}

	@Test
	void getQuoteNotExists() throws Exception {

		String id = DataTest.createQuote().getId();
		ApiError error = new ApiError(HttpStatus.NOT_FOUND.toString(),
				Util.getMessageBoundle().getString("exception.notfound"));
		when(quoteService.findQuoteById(id))
				.thenThrow(new ElementNotFoundException(Util.getMessageBoundle().getString("exception.notfound")));

		MockHttpServletResponse response = mockMvc
				.perform(get("/quotes/{id}", id).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound()).andReturn().getResponse();

		verify(quoteService, times(1)).findQuoteById(id);

		assertEquals(objectMapper.writeValueAsString(error), response.getContentAsString());

	}

	@Test
	void getQuoteByAuthor() throws Exception {
		String author = "Thomas Edison";
		when(quoteService.findQuotesByAuthor(author))
				.thenReturn(DataTest.createListQuoteDtoSameAuthorLessOrEqualThanProcessor());

		MockHttpServletResponse response = mockMvc.perform(get("/quotes/author/{author}", author))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn()
				.getResponse();

		verify(quoteService, times(1)).findQuotesByAuthor(author);

		assertEquals(objectMapper.writeValueAsString(DataTest.createListQuoteDtoSameAuthorLessOrEqualThanProcessor()),
				response.getContentAsString());

	}

	@Test
	void getQuoteByAuthorNotExists() throws Exception {
		String author = "Thomas Edison";

		when(quoteService.findQuotesByAuthor(author)).thenReturn(Collections.emptyList());

		MockHttpServletResponse response = mockMvc.perform(get("/quotes/author/{author}", author))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn()
				.getResponse();

		verify(quoteService, times(1)).findQuotesByAuthor(author);

		assertEquals("[]", response.getContentAsString());

	}

	@Test
	void getAllQuotes() throws Exception {
		when(quoteService.findAllQuotes())
				.thenReturn(DataTest.createListQuoteDtoSameAuthorLessOrEqualThanProcessor());

		MockHttpServletResponse response = mockMvc.perform(get("/quotes/all"))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn()
				.getResponse();

		verify(quoteService, times(1)).findAllQuotes();

		assertEquals(objectMapper.writeValueAsString(DataTest.createListQuoteDtoSameAuthorLessOrEqualThanProcessor()),
				response.getContentAsString());

	}

	@Test
	void getAllQuotesEmpty() throws Exception {
		
		when(quoteService.findAllQuotes())
		.thenReturn(Collections.emptyList());

		MockHttpServletResponse response = mockMvc.perform(get("/quotes/all"))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn()
				.getResponse();

		verify(quoteService, times(1)).findAllQuotes();

		assertEquals("[]", response.getContentAsString());

	}

	@Test
	void getAllQuotesError() throws Exception {
		ApiError error = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR.toString(),
				"There was an error processing your request");
		when(quoteService.findAllQuotes()).thenThrow(RuntimeException.class);

		MockHttpServletResponse response = mockMvc.perform(get("/quotes/all"))
				.andExpect(status().isInternalServerError())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();

		verify(quoteService, times(1)).findAllQuotes();

		assertEquals(objectMapper.writeValueAsString(error), response.getContentAsString());

	}

}
