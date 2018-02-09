import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;


import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.ObjectMapper;


public class CurrentMovies {
	
	
	// TODO: Please replace database details below with your values
	
	// Local DB Connection Details
	private static String db = "jdbc:postgresql://localhost:5432/db";
	private static String username = "user";
	private static String password = "";

	
    // URLs
    private static String nowPlayingMoviesURL= "https://api.themoviedb.org/3/movie/now_playing?api_key=bbb0e77b94b09193e6f32d5fac7a3b9c&region=GR";

     // Objects
    static ArrayList<Movie> movieList = new ArrayList<>();
    
    
    
    public static ArrayList<String> getAllMovieIDs(ArrayList<String> movieIdList) throws UnirestException{
    	
    	// Get IDs of all movies now playing in Greece from page 1 of API
    	HttpResponse<JsonNode> moviesResponse = Unirest.get(nowPlayingMoviesURL).asJson();
    	JSONObject obj = moviesResponse.getBody().getObject();		
    	JSONArray results = obj.getJSONArray("results");
    				
    	// extract all IDs from the object
    	for(int i=0; i < results.length(); i++){
    		JSONObject jObj = results.getJSONObject(i);
    	    String id = jObj.optString("id");
    	    movieIdList.add(id);
    	 }
    				
    	return movieIdList;
    }
    
    public static Movie getMovieData (String movieID) throws UnirestException{
    	Movie movie;
    	ArrayList<String> directorIdList = new ArrayList<>();

		// Access movie data via API
		String movieByIdURL = "https://api.themoviedb.org/3/movie/" + movieID + "?api_key=bbb0e77b94b09193e6f32d5fac7a3b9c";
		
		// Save response to movie object
		HttpResponse<Movie> movieResponse = Unirest.get(movieByIdURL).asObject(Movie.class);
		movie = movieResponse.getBody();
		
		try{
			Thread.sleep(1000);
		} catch(Exception e){
			
		}
	
		// Access credits data via API
		String creditsByIdURL = "https://api.themoviedb.org/3/movie/" + movieID + "/credits?api_key=bbb0e77b94b09193e6f32d5fac7a3b9c";
		HttpResponse<JsonNode> creditsResponse = Unirest.get(creditsByIdURL).asJson();
		JSONObject creditsObject = creditsResponse.getBody().getObject();		
		JSONArray crewResults = creditsObject.getJSONArray("crew");
		
		// extract all director IDs from the object
		for(int i=0; i < crewResults.length(); i++){
            JSONObject crewObject = crewResults.getJSONObject(i);
            String department = crewObject.optString("department");
            String job = crewObject.optString("job");
            
            if (department.equals("Directing") && job.equals("Director")){
            	String id = crewObject.optString("id");
                directorIdList.add(id);
            } 
        }
		
		// For every director, get data
		for (String directorID: directorIdList){
			// Access director data via API
			String directorByIdURL = "https://api.themoviedb.org/3/person/" + directorID + "?api_key=bbb0e77b94b09193e6f32d5fac7a3b9c";
			
			// Save response to movie object
			HttpResponse<Director> directorResponse = Unirest.get(directorByIdURL).asObject(Director.class);
			Director director = directorResponse.getBody();
			movie.addDirector(director);		
			
		}
		
		directorIdList.clear();
    	
    	return movie;
    }
    
    public static void getMoviesAndDirectors(){
    	ArrayList<String> movieIdList = new ArrayList<>();
    	
    	
    	try {
			
			// Code section below comes from 
			// https://github.com/Kong/unirest-java#creating-request
			Unirest.setObjectMapper(new ObjectMapper() {
			    private com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper
			                = new com.fasterxml.jackson.databind.ObjectMapper();
			    
			    public <T> T readValue(String value, Class<T> valueType) {
			        try {
			            return jacksonObjectMapper.readValue(value, valueType);
			        } catch (IOException e) {
			            throw new RuntimeException(e);
			        }
			    }

			    public String writeValue(Object value) {
			        try {
			            return jacksonObjectMapper.writeValueAsString(value);
			        } catch (JsonProcessingException e) {
			            throw new RuntimeException(e);
			        }
			    }
			});
			
			
			movieIdList = getAllMovieIDs(movieIdList);
			
			// For every movie id, get movie data
			for (String movieID: movieIdList){
				Movie movie = getMovieData(movieID);
				
				// Add movie object to list
				movieList.add(movie);
				
			}
			
			
		} catch (UnirestException e) {
			e.printStackTrace();
		}
    	
   	
    	
    }

    
    
    public static void addMoviesAndDirectorsToDatabase(){
    	// Connect to PostgreSQL DB
    	DBConnection database = new DBConnection(db, username, password);
    	Connection conn = database.getConnection();
    			
    	if (conn==null){
    		System.out.println("No database connection established. Please check configuration.");
    		System.exit(1);
    	}
    	
    	
	    try {
	    	
	    	DBLoad db = new DBLoad(conn);
	    	
	    	int movieID = -1;
	    	int directorID = -1;
	    	
	    	for (Movie m: movieList){
				
				movieID = db.addMovieToDatabse(m);
				
				for (Director d: m.getDirectors()){
					
					directorID = db.addDirectorToDatabase(d);
					
					if (movieID != -1 || directorID != -1){
						db.associateMovieAndDirector(movieID, directorID);
					
					} else {
						System.out.println("Issue retreiving movie and director id's.");
						System.out.println("Records cannot be associated.");
					}
				}
				
				
			}
	    	
		   } catch (SQLException e){
		    	System.out.println(e);
		   } finally {
				try {
					 
				     if (conn != null) {
				          conn.close();
				        }
				     
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					System.out.println("\nAll data has been loaded. Please check database.");
				}
			}
		
		
    }
    
    public static void main(String[] args) throws SQLException {
    	
    	System.out.println("Starting data gathering from API.. ");
		
    	// extract data from API and save into POJOs
		getMoviesAndDirectors();
			
		System.out.println("\nData gathering complete. Adding to database.. ");
		
		if (!movieList.isEmpty()){
			
			// load POJO data into DB
			addMoviesAndDirectorsToDatabase();
					
		} else {
			System.out.println("No movies could be gathered from API.");
		}
		
		
	
			
	}
	
	
}
