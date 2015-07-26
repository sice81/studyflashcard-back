package com.genius.flashcard.api.v1.cardpacks.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;

@Service
public class S3SendService {
	@Value("${s3.accessKey}")
	String accessKey;

	@Value("${s3.secretKey}")
	String secretKey;

	@Value("${s3.bucketName}")
	String bucketName;

	public void send(String doc, String keyName) throws Exception {
		TransferManager tm = new TransferManager(new BasicAWSCredentials(accessKey, secretKey));

		InputStream is = null;

		try {
			is = new ByteArrayInputStream(doc.getBytes("utf-8"));

			ObjectMetadata meta = new ObjectMetadata();
			meta.setHeader("Content-Type", "application/json");
			Upload upload = tm.upload(bucketName, keyName, is, meta);
			upload.waitForCompletion();
			System.out.println("Upload complete.");
		} catch (AmazonClientException amazonClientException) {
			System.out.println("Unable to upload file, upload was aborted.");
			amazonClientException.printStackTrace();
		} finally {
			if (is != null) {
				is.close();
			}
		}
	}

	public String get(String keyName) throws Exception {
		String result = null;
		AmazonS3 s3client = new AmazonS3Client(new BasicAWSCredentials(accessKey, secretKey));

		S3Object obj = s3client.getObject(bucketName, keyName);
		InputStream is = obj.getObjectContent();

		result = StreamUtils.copyToString(is, Charset.forName("UTF-8"));
		return result;
	}

	public String getSignedUrl(String keyName) {
		String result = null;
		AmazonS3 s3client = new AmazonS3Client(new BasicAWSCredentials(accessKey, secretKey));

		try {
			java.util.Date expiration = new java.util.Date();
			long milliSeconds = expiration.getTime();
			milliSeconds += 1000 * 60 * 10; // Add 10 min.
			expiration.setTime(milliSeconds);

			GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, keyName);
			generatePresignedUrlRequest.setMethod(HttpMethod.GET);
			generatePresignedUrlRequest.setExpiration(expiration);

			URL url = s3client.generatePresignedUrl(generatePresignedUrlRequest);

			result = url.toString();
		} catch (AmazonServiceException exception) {
			System.out.println("Caught an AmazonServiceException, " + "which means your request made it "
					+ "to Amazon S3, but was rejected with an error response " + "for some reason.");
			System.out.println("Error Message: " + exception.getMessage());
			System.out.println("HTTP  Code: " + exception.getStatusCode());
			System.out.println("AWS Error Code:" + exception.getErrorCode());
			System.out.println("Error Type:    " + exception.getErrorType());
			System.out.println("Request ID:    " + exception.getRequestId());
		} catch (AmazonClientException ace) {
			System.out.println("Caught an AmazonClientException, " + "which means the client encountered "
					+ "an internal error while trying to communicate" + " with S3, "
					+ "such as not being able to access the network.");
			System.out.println("Error Message: " + ace.getMessage());
		}

		return result;
	}
}
