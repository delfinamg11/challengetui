package tuichallenge.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Document(collection = "quotes")
@AllArgsConstructor
@NoArgsConstructor
public class Quote {

	@Id
	private String id;

	private String author;

	private String content;

	private List<String> tags;

	private String authorSlug;
	
	private Integer length;

	private String dateAdded;

	private String dateModified;
}
