package videotube.aws;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

public class Video_list {

	public ResultSet populate_video_list()
	{
		ResultSet rs;
		try{
			
		RDS_connect rds=new RDS_connect(); //create rds_connect instance
		System.out.println("after rds connect");
		//Statement st=rds.createConnectionAndStatement();
		System.out.println("after connect statement");
		rs=rds.run_select_query_rs("SELECT video_id,name from video order by upload_time desc"); //call run_select_query_rs
		if(rs==null)
			System.out.println("populate video list function : rs is null");
		System.out.println("rs val :"+rs);
		return rs;
		
		}
		catch(Exception e)
		{
			System.out.println(e);
			return null;
		}
		finally
		{
			
		}
		
		
		
		/*
		HashMap hm=new HashMap();
	
		try
		{
			while(rs.next())
	        {
				System.out.println(rs.getString("name").toString());
	                hm.put(rs.getString("video_id"),rs.getString("name"));
	        }
		}
		catch(Exception e)
		{
			return null;
		}
		
		return hm; */
	}
	
	
	
	/*public static void main(String args[])
	{
		Video_list v=new Video_list();
		System.out.println(v.populate_video_list().toString());
	}*/
	
	
}
