package tuichallenge.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "quotes")
public class Quote {

	@Id
	private String id;

	private String author;

	private String content;

	private List<String> tags;

	private String authorSlug;

	private String dateAdded;

	private String dateModified;
}
