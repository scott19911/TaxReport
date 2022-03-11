package com.example.taxreports.util;

import java.io.IOException;
import java.io.InputStream;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.apache.log4j.Logger;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.waiters.WaiterResponse;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.waiters.S3Waiter;

public class S3Util {
	private static final Logger log = Logger.getLogger(S3Util.class);
	private static final String BUCKET = "taxrepo";
	
	public static void uploadFile(String fileName, InputStream inputStream) 
			throws AwsServiceException, SdkClientException, IOException {
		Region region = Region.EU_CENTRAL_1;

		S3Client client = S3Client.builder().region(region).build();

		PutObjectRequest request = PutObjectRequest.builder()
											.bucket(BUCKET)
											.key(fileName)
											.acl("public-read")
											.build();
		client.putObject(request, 
				RequestBody.fromInputStream(inputStream, inputStream.available()));
		
		S3Waiter waiter = client.waiter();
		HeadObjectRequest waitRequest = HeadObjectRequest.builder()
											.bucket(BUCKET)
											.key(fileName)
											.build();
		
		WaiterResponse<HeadObjectResponse> waitResponse = waiter.waitUntilObjectExists(waitRequest);
		waitResponse.matched().response().ifPresent(response -> {
			// run custom logics when the file exists on S3
		});
	}
	public static void deleteFile (String object_key){

		final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.EU_CENTRAL_1).build();

		try {
			s3.deleteObject(BUCKET, object_key);
			log.info("Delete file = " + object_key);

		} catch (AmazonServiceException e) {
			log.error(e);
		}
	}

}
