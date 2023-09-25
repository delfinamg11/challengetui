package tuichallenge.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import tuichallenge.DataTest;
import tuichallenge.error.ApiError;
import tuichallenge.model.Quote;
import tuichallenge.repository.QuoteRepository;
import tuichallenge.util.Util;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureDataMongo
class IntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private QuoteRepository quoteRepository;

	@BeforeEach
	void setUp() {
		quoteRepository.deleteAll();
		DataTest.createListQuoteSameAuthorGreaterThanProcessorToCreate().forEach(e -> quoteRepository.save(e));
	}

	@Test
	void getQuoteNotExists() throws Exception {

		String id = DataTest.createQuote().getId();
		ApiError error = new ApiError(HttpStatus.NOT_FOUND.toString(),
				Util.getMessageBoundle().getString("exception.notfound"));

		MockHttpServletResponse response = mockMvc
				.perform(get("/quotes/{id}", id).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound()).andReturn().getResponse();

		assertEquals(objectMapper.writeValueAsString(error), response.getContentAsString());

	}

	@Test
	void getCorrectQuote() throws Exception {

		quoteRepository.deleteAll();
		Quote quote = quoteRepository.save(DataTest.createQuoteToCreate());

		MockHttpServletResponse response = mockMvc.perform(get("/quotes/{id}", quote.getId()))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn()
				.getResponse();

		assertEquals(objectMapper.writeValueAsString(DataTest.createQuoteDto()), response.getContentAsString());

	}

	@Test
	void getQuoteByAuthor() throws Exception {
		String author = "Thomas Edison";

		MockHttpServletResponse response = mockMvc.perform(get("/quotes/author/{author}", author))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn()
				.getResponse();

		assertEquals(objectMapper.writeValueAsString(DataTest.createListQuoteDtoSameAuthorrGreaterThanProcessor()),
				response.getContentAsString());

	}

	@Test
	void getQuoteByAuthorNotExists() throws Exception {
		String author = "Thomas";

		MockHttpServletResponse response = mockMvc.perform(get("/quotes/author/{author}", author))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn()
				.getResponse();

		assertEquals("[]", response.getContentAsString());

	}

	@Test
	void getAllQuotes() throws Exception {

		MockHttpServletResponse response = mockMvc.perform(get("/quotes/all")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();

		assertEquals(objectMapper.writeValueAsString(DataTest.createListQuoteDtoSameAuthorrGreaterThanProcessor()),
				response.getContentAsString());

	}

	@Test
	void getAllQuotesEmpty() throws Exception {

		quoteRepository.deleteAll();

		MockHttpServletResponse response = mockMvc.perform(get("/quotes/all")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();

		assertEquals("[]", response.getContentAsString());

	}

}
