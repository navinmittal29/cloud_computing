package videotube.aws;

	import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

	import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

	import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.*;
import org.apache.commons.fileupload.servlet.*;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.output.*;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.CanonicalGrantee;
import com.amazonaws.services.s3.model.EmailAddressGrantee;
import com.amazonaws.services.s3.model.GroupGrantee;
import com.amazonaws.services.s3.model.Permission;
import com.amazonaws.services.s3.model.PutObjectRequest;

	/**
	 * Servlet implementation class Upload_video
	 */
	public class Upload_video extends HttpServlet {
		private static final long serialVersionUID = 1L;

		private static String bucketName = "myawss3";
		private static String keyName,category;
		File tempFile;
		/**
		 * @see HttpServlet#HttpServlet()
		 */
		public Upload_video() {
			super();
			// TODO Auto-generated constructor stub
		}

		/**
		 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
		 */
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		}


		/**
		 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
		 */
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
		{
			
			try {
//				ServletContext servletContext = this.getServletConfig().getServletContext();
				
				List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
				for (FileItem item : items) {
					if (!item.isFormField()) {
						// Process form file field (input type="file").
						//convId= genConvId();
						
						category = item.getFieldName();
						
						keyName = item.getName();
						System.out.println("New broadcast Msg keyname:"+keyName);
						InputStream is = item.getInputStream();
						// ... (do your job here)
						tempFile = new File(keyName);
						OutputStream outputStream = new FileOutputStream(tempFile);

						int read = 0;
						byte[] bytes = new byte[1024];

						while ((read = is.read(bytes)) != -1) {
							outputStream.write(bytes, 0, read);
						}
					}
				}
				
				RDS_connect rds=new RDS_connect();
								
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				Date date = new Date();
				System.out.println(dateFormat.format(date));
				
				String[] str = category.split("#");
				
				
				if (str[0].equals("reply_url")){
					int video_id= Integer.parseInt(str[1]);
					
					System.out.println("INSERT into Reply" + video_id);
					rds.run_insert_query("INSERT into reply(name,video_id) values('"+keyName+"','"+video_id+"')");
				}else{
					System.out.println("INSERT into video(name,upload_time) values('"+keyName+"','"+dateFormat.format(date)+"')");
					rds.run_insert_query("INSERT into video(name,upload_time) values('"+keyName+"','"+dateFormat.format(date)+"')");
				}
				SNSAPI sns = new SNSAPI(keyName);
				sns.close();
				
				} catch (Exception e) {
					System.out.println(e);
				}
			

			
			///////////////////////////////////////////////////

			AWSCredentialsProvider credentialsProvider = new ClasspathPropertiesFileCredentialsProvider();
			AmazonS3 s3client = new AmazonS3Client(credentialsProvider);
	        try {
	            System.out.println("Uploading a new object to S3 from a file\n");
	            
	            AccessControlList acl = new AccessControlList();
	            acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);             	
	            s3client.putObject(new PutObjectRequest(bucketName, keyName, tempFile).withAccessControlList(acl));
	            
	            
	            
	            
	         } catch (AmazonServiceException ase) {
	            System.out.println("Caught an AmazonServiceException, which " +
	            		"means your request made it " +
	                    "to Amazon S3, but was rejected with an error response" +
	                    " for some reason.");
	            System.out.println("Error Message:    " + ase.getMessage());
	            System.out.println("HTTP Status Code: " + ase.getStatusCode());
	            System.out.println("AWS Error Code:   " + ase.getErrorCode());
	            System.out.println("Error Type:       " + ase.getErrorType());
	            System.out.println("Request ID:       " + ase.getRequestId());
	        } catch (AmazonClientException ace) {
	            System.out.println("Caught an AmazonClientException, which " +
	            		"means the client encountered " +
	                    "an internal error while trying to " +
	                    "communicate with S3, " +
	                    "such as not being able to access the network.");
	            System.out.println("Error Message: " + ace.getMessage());
	        }
	        
	        RequestDispatcher dispatcher = getServletContext().getNamedDispatcher("Login");
	        dispatcher.forward(request, response);

	        //response.sendRedirect(request.getContextPath() + "/home.jsp");
			
		}
		
		

	}



