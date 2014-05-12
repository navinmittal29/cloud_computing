package videotube.aws;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class RDS_connect {
	
	private Connection connect = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	private String DB_END_POINT = "admindb.cerrepx79qfp.us-west-2.rds.amazonaws.com";
	private final String DB_USER_NAME = "admindb";
	private final String DB_PWD = "admin123";
	private final String DB_NAME = "admin123";
	private final int DB_PORT = 3306;
	
	
	public void createConnectionAndStatement()
	{
		try{
			// This will load the MySQL driver, each DB has its own driver
			//Class.forName("com.mysql.jdbc.Driver").newInstance();
			  Class.forName("com.mysql.jdbc.Driver").newInstance();
			// Setup the connection with the DB
			connect = DriverManager
					.getConnection("jdbc:mysql://"+DB_END_POINT+":"+DB_PORT+"/"+DB_NAME,DB_USER_NAME,DB_PWD);

			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();
			
			//return statement;
		} catch (Exception e) {
			e.printStackTrace();
			close();
			
			//return null;
		}
		finally {
			//close();
		}
	}
	
	public int run_select_query(String query)
	{
		int count=0;
		try {
			createConnectionAndStatement();
			ResultSet rs=statement.executeQuery(query);
			
			while(rs.next())
            {
                count=rs.getInt(1);          
            }
			
			System.out.println(count);
			
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} finally {
			//close();
		}
		
		return count;

	}
	
	public ResultSet run_select_query_rs(String query)
	{
		//ResultSet temp=null;
		try
		{
			System.out.println("Running run_select_query_rs");
			System.out.println("creating connection and statement");
			createConnectionAndStatement();
			System.out.println("running query : "+query);
			resultSet= statement.executeQuery(query);
			return resultSet;
		}
		catch(Exception e)
		{
			System.out.println("inside exception");
			System.out.println(e);
			return null;
		}
		finally {
			//close();
		}
		
		
	}
	
	public void run_insert_query(String query)
	{
		try {
			createConnectionAndStatement();
			statement.executeUpdate(query);
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			close();
		}

	}
	
	// You need to close the resultSet
		private void close() {
			try {
				if (resultSet != null) {
					resultSet.close();
				}

				if (statement != null) {
					statement.close();
				}

				if (connect != null) {
					connect.close();
				}
			} catch (Exception e) {

			}
		}
		
		/*
		public static void main(String args[])
		{
			RDS_connect rds=new RDS_connect();
			Statement st=rds.createConnectionAndStatement();
			String userid="gdm";
			String password="gdm";
			int res=rds.run_select_query(st, "SELECT count(1) from user where user_id='"+userid+"' and password='"+password+"'");
			System.out.println(res);
		}
		*/

}

