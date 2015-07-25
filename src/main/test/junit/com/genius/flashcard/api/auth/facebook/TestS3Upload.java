package junit.com.genius.flashcard.api.auth.facebook;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AbortMultipartUploadRequest;
import com.amazonaws.services.s3.model.CompleteMultipartUploadRequest;
import com.amazonaws.services.s3.model.InitiateMultipartUploadRequest;
import com.amazonaws.services.s3.model.InitiateMultipartUploadResult;
import com.amazonaws.services.s3.model.PartETag;
import com.amazonaws.services.s3.model.UploadPartRequest;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;

public class TestS3Upload {
	String accessKey;
	String secretKey;
	String bucketName;

	@Before
	public void init() throws NoSuchAlgorithmException, NoSuchProviderException {
	}

	@Test
	public void testS3Simple() throws InterruptedException {
		// String existingBucketName = "*** Provide existing bucket name ***";
		String keyName = "doc.json";
		String filePath = "c:\\doc.json";

		TransferManager tm = new TransferManager(new BasicAWSCredentials(accessKey, secretKey));
		System.out.println("Hello");
		// TransferManager processes all transfers asynchronously,
		// so this call will return immediately.
		Upload upload = tm.upload(bucketName, keyName, new File(filePath));
		System.out.println("Hello2");

		try {
			// Or you can block and wait for the upload to finish
			upload.waitForCompletion();
			System.out.println("Upload complete.");
		} catch (AmazonClientException amazonClientException) {
			System.out.println("Unable to upload file, upload was aborted.");
			amazonClientException.printStackTrace();
		}
	}

	// @Test
	public void testS3() {
		String keyName = "doc.json";

		AWSCredentials cred = new BasicAWSCredentials(accessKey, secretKey);
		AmazonS3 s3Client = new AmazonS3Client(cred);

		// Create a list of UploadPartResponse objects. You get one of these for
		// each part upload.
		List<PartETag> partETags = new ArrayList<PartETag>();

		// Step 1: Initialize.
		InitiateMultipartUploadRequest initRequest = new InitiateMultipartUploadRequest(bucketName, keyName);
		InitiateMultipartUploadResult initResponse = s3Client.initiateMultipartUpload(initRequest);

		File file = new File("c:\\doc.json");
		long contentLength = file.length();
		long partSize = 5 * 1024 * 1024; // Set part size to 5 MB.

		try {
			// Step 2: Upload parts.
			long filePosition = 0;
			for (int i = 1; filePosition < contentLength; i++) {
				// Last part can be less than 5 MB. Adjust part size.
				partSize = Math.min(partSize, (contentLength - filePosition));

				// Create request to upload a part.
				UploadPartRequest uploadRequest = new UploadPartRequest().withBucketName(bucketName).withKey(keyName)
						.withUploadId(initResponse.getUploadId()).withPartNumber(i).withFileOffset(filePosition)
						.withFile(file).withPartSize(partSize);

				// Upload part and add response to our list.
				partETags.add(s3Client.uploadPart(uploadRequest).getPartETag());

				filePosition += partSize;
			}

			// Step 3: Complete.
			CompleteMultipartUploadRequest compRequest = new CompleteMultipartUploadRequest(bucketName, keyName,
					initResponse.getUploadId(), partETags);

			s3Client.completeMultipartUpload(compRequest);
		} catch (Exception e) {
			s3Client.abortMultipartUpload(
					new AbortMultipartUploadRequest(bucketName, keyName, initResponse.getUploadId()));
		}
	}
}
