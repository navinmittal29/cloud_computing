package videotube.aws;

import java.io.IOException;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.sns.AmazonSNSAsync;
import com.amazonaws.services.sns.AmazonSNSAsyncClient;
import com.amazonaws.services.sns.model.CreateTopicRequest;

public class SNSAPI{
	private final AmazonSNSAsync sns;
	private String topicArn;

	private boolean snsClosed;

	public SNSAPI(String video_title) {
		try {
			this.sns = new AmazonSNSAsyncClient(new PropertiesCredentials(
					SNSAPI.class.getResourceAsStream("/AwsCredentials.properties")));
			
	        String topicArn = this.sns.createTopic("new").getTopicArn();
	        //String subscriptionArn = this.sns.subscribe(
	        //     topicArn, "email", "gdm284@nyu.edu").getSubscriptionArn();
	        
	        String message = "Hello, \n\nThe new video is availble on VideoTube Channel. \nName : " + video_title;
	        
	        
	        this.sns.publish(topicArn, message,"New Video Available");
	        //this.sns.unsubscribe(subscriptionArn);
	        
	        //this.sns.deleteTopic(topicArn);
			
			this.snsClosed = true;
			System.out.println("Successfully sent Email notification");
		} catch (IOException ioe) {
			throw new RuntimeException("Could not instantiate SNS session", ioe);
		}
	}
	
	public void setTopicName(String topicName) {
		try {
			topicArn = sns.createTopic(new CreateTopicRequest(topicName)).getTopicArn();
			snsClosed = false;
		} catch (Exception e) {
			System.out.println("Could not set topic name to: " + topicName);
			snsClosed = true;
		}
	}

	

	protected boolean checkEntryConditions() {
		return !snsClosed;
	}

	public String getTopicArn() {
		return topicArn;
	}

	public void close() {
		if (snsClosed) return;
		snsClosed = true;
		sns.shutdown();
		((AmazonSNSAsyncClient) sns).getExecutorService().shutdownNow();
	}

	public boolean requiresLayout() {
		return false;
	}

}