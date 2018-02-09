import java.io.Serializable;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Movie implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9114285076757165001L;
	private String title;
	private String originalTitle;
	private String description;
	private ArrayList<Director> directors;
	
	
	
	public Movie (){
		//default
		this.directors = new ArrayList<>();
	}
	
	public Movie (@JsonProperty("title")String title, 
				@JsonProperty("original_title")String originalTitle, 
				@JsonProperty("overview")String description) {
		this.title = title;
		this.originalTitle = originalTitle;
		this.description = description;
		this.directors = new ArrayList<>();
	}
	
	public String getTitle(){
		return this.title;
	}
	
	public String getOriginalTitle(){
		return this.originalTitle;
	}
	
	public String getDescription(){
		return this.description;
	}
	
	public void setTitle (@JsonProperty("title")String title){
		this.title = title;
	}
	
	public void setOriginalTitle (@JsonProperty("original_title")String originalTitle){
		this.originalTitle = originalTitle;
	}
	
	public void setDescription (@JsonProperty("overview")String description){
		this.description = description;
	}
	
	public void addDirector(Director director){
		this.directors.add(director);
	}

	public ArrayList<Director> getDirectors(){
		return this.directors;
	}
	
	public String toString() {
		return this.title;
	}

}
