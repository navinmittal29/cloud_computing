package videotube.aws;


//import java.beans.Statement;
import java.sql.*;
import java.util.List;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class Reply extends HttpServlet {
	
	 
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException 
	 {
		 System.out.println("reply dopost");
		System.out.println(req.getParameter("video_id"));
		String video_id=req.getParameter("video_id");
		 

		
		
		RDS_connect rds=new RDS_connect();
		//rds.run_insert_query("INSERT into reply(video_id,name) values('"+video_id+"','Default')");
		ResultSet rs=rds.run_select_query_rs("SELECT reply_id,name,video_id from reply where video_id='"+video_id+"'");
		
		 req.setAttribute("video_id",video_id);
		 req.setAttribute("reply_list",rs);
		
		rds=new RDS_connect();
		rs=rds.run_select_query_rs("SELECT name from video where video_id='"+video_id+"'");
		
		String video_name="";
		try
		{
			while(rs.next())
			{
				video_name=rs.getString("name");
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		req.setAttribute("video_name",video_name); 
		
		 RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Reply_page.jsp?video_id="+video_id);
	     dispatcher.forward(req, res);  
	     
	 }
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException 
	 {
		 System.out.println("reply doget");
			System.out.println(req.getParameter("video_id"));
			String video_id=req.getParameter("video_id");
			 
			RDS_connect rds=new RDS_connect();
			rds.createConnectionAndStatement();
			ResultSet rs=rds.run_select_query_rs("SELECT reply_id,name,video_id from reply where video_id='"+video_id+"'");
			
			 req.setAttribute("video_id",video_id);
			 req.setAttribute("reply_list",rs);
			
			 RDS_connect rds1=new RDS_connect();
			rds1.createConnectionAndStatement();
			ResultSet rs1=rds.run_select_query_rs("SELECT name from video where video_id='"+video_id+"'");
			
			String video_name="";
			try
			{
				while(rs1.next())
				{
					video_name=rs1.getString("name");
				}
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
			
			req.setAttribute("video_name",video_name); 
			
			 RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Reply_page.jsp?video_id="+video_id);
		     dispatcher.forward(req, res);  
	     
	 }
	
	
	
	
	/*public static void main(String args[])
	{
		Login l=new Login();
		l.check_login("gdm","gdm");
	}*/
	
	

}

