package videotube.aws;


//import java.beans.Statement;
import java.sql.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Login extends HttpServlet {
	
	 
	 protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException 
	 {
		 System.out.println("doget");
		
			 Video_list vl=new Video_list(); //create object of video_list
			 System.out.println("after vl");
			 ResultSet rs=vl.populate_video_list(); //populate video list
			 System.out.println("after rs");
			 req.setAttribute("video_list",rs);
			 RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/home.jsp");
		     dispatcher.forward(req, res);       

	 }
	 
	 
	 protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException 
	 {
		 System.out.println("dopost");
		
			 Video_list vl=new Video_list();
			 ResultSet rs=vl.populate_video_list();
			 req.setAttribute("video_list",rs);
			 RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/home.jsp");
		     dispatcher.forward(req, res);       

	 }
	
	
	public int check_login(String userid, String password)
	{
		
		try
		{
		RDS_connect rds=new RDS_connect();
		rds.createConnectionAndStatement();
		return rds.run_select_query("SELECT count(1) from user where user_id='"+userid+"' and password='"+password+"'");
		}
		catch(Exception e)
		{
			System.out.println("Inside exception block");
			System.out.println(e);
			return 0;
		}
		/*if(rds.run_select_query(st, "SELECT count(1) from user where user_id='"+userid+"' and password='"+password+"'")==1)
			{
			System.out.println("valid");
			}
		else
			System.out.println("invalid");
			*/
	}
	
	/*public static void main(String args[])
	{
		Login l=new Login();
		l.check_login("gdm","gdm");
	}*/
	
	

}

