import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBLoad {
	private Connection conn = null;

	public DBLoad(Connection conn){
		this.conn = conn;
	}
	
	public int addMovieToDatabse(Movie movie) throws SQLException{
    	int id=-1;
    	
    	String sql = "INSERT INTO now_playing_movies (title, original_title, description) VALUES (?,?,?)";
		
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, movie.getTitle());
		ps.setString(2, movie.getOriginalTitle());
		ps.setString(3, movie.getDescription());
			
		int rowsAffected = ps.executeUpdate();
			
		if (rowsAffected == 0) {
			System.out.print("Unable to add movie at this time.");
				
		} else {
					
			String sql1 = "SELECT M.movie_id FROM now_playing_movies M WHERE M.title = ?";
						
			PreparedStatement ps1 = conn.prepareStatement(sql1);
			ps1.setString(1, movie.getTitle());
			ResultSet result1 = ps1.executeQuery();
						
			while (result1.next()) {
				id = result1.getInt(1);		
			}
					
			if (id == -1){
				System.out.println("Issue retrieving movie id from database.");
			}
								
		}
					

		return id;
    }
    
    public int addDirectorToDatabase(Director director) throws SQLException{
    	
    	int id=-1;
    	
    	String sql = "INSERT INTO director (name, imdb_link) VALUES (?,?)";
		
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, director.getName());
		ps.setString(2, director.getIMDB_url());
		
		int rowsAffected = ps.executeUpdate();
		
			if (rowsAffected == 0) {
				System.out.println("Unable to add director at this time.");
			
			} else {
				
				String sql1 = "SELECT D.director_id FROM director D WHERE D.name = ?";
					
				PreparedStatement ps1 = conn.prepareStatement(sql1);
				ps1.setString(1, director.getName());
				ResultSet result1 = ps1.executeQuery();
					
				while (result1.next()) {
					id = result1.getInt(1);	
				}
				
				if (id == -1){
					System.out.println("Issue retrieving director id from database.");
				}
							
			}
				

    	
    	return id;
    }
    
    public void associateMovieAndDirector(int movieID, int directorID) throws SQLException{
    
    	String sql = "INSERT INTO movie_director (movie_id, director_id) VALUES (?,?)";
		
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, movieID);
		ps.setInt(2, directorID);
		
		int rowsAffected = ps.executeUpdate();
		
		if (rowsAffected == 0) {
			System.out.println("Unable to associate movie and director at this time.");
		}
    	
    }
	
}
