<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="com.amazonaws.*" %>
<%@ page import="com.amazonaws.auth.*" %>
<%@ page import="com.amazonaws.services.ec2.*" %>
<%@ page import="com.amazonaws.services.ec2.model.*" %>
<%@ page import="com.amazonaws.services.s3.*" %>
<%@ page import="com.amazonaws.services.s3.model.*" %>
<%@ page import="com.amazonaws.services.dynamodbv2.*" %>
<%@ page import="com.amazonaws.services.dynamodbv2.model.*" %>
<%@ page import="java.net.URL" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.sql.*" %>

<%! // Share the client objects across threads to
    // avoid creating new clients for each web request
    private AmazonEC2         ec2;
    private AmazonS3           s3;
    
       
 %>
 
 <%
    /*
     * AWS Elastic Beanstalk checks your application's health by periodically
     * sending an HTTP HEAD request to a resource in your application. By
     * default, this is the root or default resource in your application,
     * but can be configured for each environment.
     *
     * Here, we report success as long as the app server is up, but skip
     * generating the whole page since this is a HEAD request only. You
     * can employ more sophisticated health checks in your application.
     */
     
     /*
     if(request.getAttribute("user_session").equals("enabled"))
    	 {
    	 System.out.println(request.getAttribute("user_session"));
    	 return;
    	 }
 */
 
 
     
    if (request.getMethod().equals("HEAD")) return;
%>

<%
    if (ec2 == null) {
        AWSCredentialsProvider credentialsProvider = new ClasspathPropertiesFileCredentialsProvider();
        System.out.println(credentialsProvider.toString());
        ec2    = new AmazonEC2Client(credentialsProvider);
        s3     = new AmazonS3Client(credentialsProvider);
        
    }


System.out.println(s3.toString());


%>



<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="shortcut icon" href="../../assets/ico/favicon.ico">

    <title>Reply</title>

    <!-- Bootstrap core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="css/offcanvas.css" rel="stylesheet">

    <!-- Just for debugging purposes. Don't actually copy this line! -->
    <!--[if lt IE 9]><script src="../../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>

  <body>
    <div class="navbar navbar-fixed-top navbar-inverse" role="navigation">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#">Videotube</a>
        </div>
        <div class="collapse navbar-collapse">
          <ul class="nav navbar-nav">
          </ul>
        </div><!-- /.nav-collapse -->
      </div><!-- /.container -->
    </div><!-- /.navbar -->

    <div class="container">

      <div class="row row-offcanvas row-offcanvas-right">

        <div class="col-xs-12 col-sm-9">
          <p class="pull-right visible-xs">
            <button type="button" class="btn btn-primary btn-xs" data-toggle="offcanvas">Toggle nav</button>
          </p>
    
	
	<div class="starter-template">
        <h1>Replies for Video : <%=request.getAttribute("video_name").toString() %></h1>
        <p class="lead">
        <form action="${pageContext.request.contextPath}/Upload_video" method="post" enctype="multipart/form-data">
			<input type="file" name="reply_url#<%=request.getAttribute("video_id").toString()%>" size="50" value=""/>
			<input type="submit" value="Reply to video" name="submit_button">
			<input type="hidden" name="video_id" value="<%=request.getAttribute("video_id").toString()%>"/>
			<input type="hidden" name="btn_click_value" value="reply_to_video">
			</form></p>
      </div>

	  

<br> 
<script type='text/javascript' src='https://dfxsnak521st3.cloudfront.net/jwplayer.js'></script>

<!-- Replace RTMP-DISTRIBUTION-DOMAIN-NAME with the domain name of your 
RTMP distribution, for example, s5678.cloudfront.net (begins with "s").

Replace VIDEO-FILE-NAME with the name of your .mp4 or .flv video file, 
including the .mp4 or .flv filename extension. For example, if you uploaded 
my-vacation.mp4, enter my-vacation.mp4.
-->
<body>
<%
ResultSet rs =(ResultSet)request.getAttribute("reply_list");
if(rs==null)
	System.out.println("rs is null");
/*
for (Map.Entry<String, String> entry : map.entrySet()) {
    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
	System.out.println("rtmp://s25e2m8xns0cmv.cloudfront.net/cfx/st/"+entry.getValue());
*/
%>


<%
try
{
	while(rs.next())
    {
		System.out.println(rs.getString("name").toString());
            

%>

<div id='<%=rs.getString("reply_id")%>'></div>
<script type="text/javascript">
   jwplayer('<%=rs.getString("reply_id")%>').setup({
      file: "rtmp://s25e2m8xns0cmv.cloudfront.net/cfx/st/<%=rs.getString("name")%>",
      width: "350",
      height: "240"
   });
   
</script>

<div id="video_file_name">
<%=rs.getString("name")%>
</div>

<br>
<%
    }
}
catch(Exception e)
{
	System.out.println(e);
}
%>

</form>
</body>




<hr>

	  
	  
	  
    </div><!--/.container-->



    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="css/offcanvas.js"></script>
  </body>
</html>

<script language="javascript">
function post_reply_click($id)
{
	
	request.getRequestDispatcher("Reply");
}
</script>
