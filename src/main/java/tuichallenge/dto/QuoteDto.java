package tuichallenge.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tuichallenge.model.Quote;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuoteDto {

	private String author;

	private String content;

	private List<String> tags;

	private String authorSlug;

	private Integer length;

	private String dateAdded;

	private String dateModified;

	public QuoteDto(Quote quote) {
		this.setAuthor(quote.getAuthor());
		this.setAuthorSlug(quote.getAuthorSlug());
		this.setContent(quote.getContent());
		this.setDateAdded(quote.getDateAdded());
		this.setDateModified(quote.getDateModified());
		this.setLength(quote.getLength());
		this.setTags(quote.getTags());
	}
}
