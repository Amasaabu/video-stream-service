package com.video.stream.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
@Component
@AllArgsConstructor
public class StreamFromS3Service {
    private S3Client initialize() {
        Region region = Region.US_EAST_2;
        return S3Client.builder().region(region).build();
    }

    public ResponseBytes<GetObjectResponse> getObject(String key, String range) {
        var s3 = initialize();
        var bucket = "video-streaming-bucket-8";
        var requestBuilder = GetObjectRequest.builder().bucket(bucket).key("video/"+key);
        if (range!=null) {
            requestBuilder.range(range);
        }
        var request= requestBuilder.build();
        return s3.getObjectAsBytes(request);
    }
}
