package tuichallenge.dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import tuichallenge.model.Quote;

@Data
@NoArgsConstructor
public class QuoteDto {

	private String author;

	private String content;

	private List<String> tags;

	private String authorSlug;

	private String dateAdded;

	private String dateModified;

	public QuoteDto(Quote quote) {
		this.setAuthor(quote.getAuthor());
		this.setAuthorSlug(quote.getAuthorSlug());
		this.setContent(quote.getContent());
		this.setDateAdded(quote.getDateAdded());
		this.setDateModified(quote.getDateModified());
		this.setTags(quote.getTags());
	}
}
