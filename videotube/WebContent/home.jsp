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
    private AmazonDynamoDB dynamo;
       
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
        dynamo = new AmazonDynamoDBClient(credentialsProvider);
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

    <title>Videotube Home</title>

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
                  <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#">VideoTube</a>
        </div>
       
      </div><!-- /.container -->
    </div><!-- /.navbar -->

    <div class="container">

      <div class="row row-offcanvas row-offcanvas-right">

       
	
	<div class="starter-template">
        <h1>Conversations</h1>
        <p class="lead">
        <form action="${pageContext.request.contextPath}/Upload_video" method="post" enctype="multipart/form-data">
			<input type="file" name="fileurl" size="50" value=""/>
			<input type="submit" value="Upload Video" />
			</form></p>
      </div>

	  
<!--   
	  <div class="media">
  <a class="pull-left" href="#">
    <div class="thumbnail">  <img class="media-object" src="1517869_10202176329544090_622724350_o.jpg" alt="..." width=150 height=100 name="test1"
	onclick=picture_click(this.name)> </div>
  </a>
  <div class="media-body">
    <h4 class="media-heading">Video name</h4>
    Description
  </div>
 </div>
 
 <div class="media">
  <a class="pull-left" href="#">
    <div class="thumbnail">  <img class="media-object" src="59.jpg" alt="..." width=150 height=100> </div>
  </a>
  <div class="media-body">
    <h4 class="media-heading">Video name</h4>
    Description
  </div>
</div>
 -->

<br> 
<script type='text/javascript' src='https://d3hx87d66haprz.cloudfront.net/jwplayer.js'></script>

<!-- Replace RTMP-DISTRIBUTION-DOMAIN-NAME with the domain name of your 
RTMP distribution, for example, s5678.cloudfront.net (begins with "s").

Replace VIDEO-FILE-NAME with the name of your .mp4 or .flv video file, 
including the .mp4 or .flv filename extension. For example, if you uploaded 
my-vacation.mp4, enter my-vacation.mp4.
-->
<body>
<%
System.out.println("before resultset in home.jsp");
ResultSet rs =(ResultSet)request.getAttribute("video_list");
System.out.println("after resultset in home.jsp");
/*
for (Map.Entry<String, String> entry : map.entrySet()) {
    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
	System.out.println("rtmp://syg5n0bh5mq6w.cloudfront.net/cfx/st/"+entry.getValue());
*/
%>
<form action="Reply" method="post" name="reply_post_form">
<input type="hidden" name="video_id" />
<%
try
{
	while(rs.next())
    {
		System.out.println(rs.getString("name").toString());
            

%>

<div id='<%=rs.getString("video_id")%>'></div>
<script type="text/javascript">
   jwplayer('<%=rs.getString("video_id")%>').setup({
      file: "rtmp://syg5n0bh5mq6w.cloudfront.net/cfx/st/<%=rs.getString("name")%>",
      width: "350",
      height: "240"
   });
   
</script>

<div id="video_file_name">
<%=rs.getString("name")%>
<input type="submit" id="<%=rs.getString("video_id")%>" value="Post reply" onclick="{document.reply_post_form.video_id.value=this.id;}"></input>
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
