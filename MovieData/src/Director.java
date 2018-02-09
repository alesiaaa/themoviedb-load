import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Director implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3208371682645444486L;
	private String name;
	private String IMDB_url;
	
	public Director (){
		// default
	}
	
	public Director (@JsonProperty("name")String name, @JsonProperty("imdb_id")String imdb_id){
		this.name = name;
		
		if (!imdb_id.equals("")){
			this.IMDB_url = "http://www.imdb.com/name/" + imdb_id;
		} else {
			this.IMDB_url = "";
		}
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getIMDB_url(){
		return this.IMDB_url;
	}
	
	public void setName (@JsonProperty("name")String name){
		this.name = name;
	}
	
	public void setIMDBurl (@JsonProperty("imdb_id")String imdb_id){
		if (!imdb_id.equals("")){
			this.IMDB_url = "http://www.imdb.com/name/" + imdb_id;
		} else {
			this.IMDB_url = "";
		}
	}
	
	public String toString() {
		return this.name;
	}

}
