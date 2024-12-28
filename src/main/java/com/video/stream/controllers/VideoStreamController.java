package com.video.stream.controllers;


import com.video.stream.services.StreamFromS3Service;
import com.video.stream.utils.VerifyToken;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import java.io.ByteArrayInputStream;
import java.io.InputStream;


@RestController
@RequestMapping("/video")
@AllArgsConstructor
public class VideoStreamController {
    VerifyToken verifyToken;
    StreamFromS3Service streamFromS3Service;
    @GetMapping("/stream/{fileName}")
    public ResponseEntity<StreamingResponseBody> streamVideo(@PathVariable String fileName,

                                                             @RequestHeader(value = "Range", required = false) String rangeHeader) throws Exception {
        //verify the token
        //  verifyToken.getProfileFroToken(token);

        //verify if user has active subscription

        //try to search if user has started watching from mongodb

        //if user has started watching check for last stream byte

        //else create new session for watches

        String range = rangeHeader != null ? rangeHeader.replace("bytes=", "") : null;
        ResponseBytes<GetObjectResponse> responseBytes = streamFromS3Service.getObject(fileName, range);
        GetObjectResponse responseMetadata = responseBytes.response();

        // Set headers for partial content if requested
        long contentLength = responseMetadata.contentLength();
        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-Type", responseMetadata.contentType());
        headers.add("Content-Type", "video/mp4");
        headers.add("Accept-Ranges", "bytes");

        long start = 0;
        long end = contentLength - 1;
        if (rangeHeader != null) {
            String[] ranges = rangeHeader.substring(6).split("-");
            start = Long.parseLong(ranges[0]);
            if (ranges.length > 1) {
                end = Long.parseLong(ranges[1]);
            }

            long content_range = end-start +1;
            System.out.println(String.format("bytes %d-%d/%d", start, end, contentLength));
            headers.add("Content-Range", String.format("bytes %d-%d/%d", start, end, contentLength));
            headers.setContentLength(content_range);
            //now request specific range from s3
            String s3RangeHeader = String.format("bytes=%d-%d", start, end);
            var videofrag = streamFromS3Service.getObject(fileName, s3RangeHeader);

            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                    .headers(headers)
                    .body(outputStream -> {
                        InputStream inputStream = new ByteArrayInputStream(videofrag.asByteArray());
                        byte[] buffer = new byte[8192]; //1024
                        int bytesRead;
                        while ((bytesRead = inputStream.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, bytesRead);
                        }
                        inputStream.close();
                    });
        } else {
            headers.setContentLength(contentLength);
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(outputStream -> {
                        InputStream inputStream = new ByteArrayInputStream(responseBytes.asByteArray());
                        byte[] buffer = new byte[1024];
                        int bytesRead;
                        while ((bytesRead = inputStream.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, bytesRead);
                        }
                        inputStream.close();
                    });
        }




    }
}




//@RestController
//@RequestMapping("/video")
//@AllArgsConstructor
//public class VideoStreamController {
//    StreamFromS3Service streamFromS3Service;
//    @GetMapping("/stream/{fileName}")
//    public ResponseEntity<StreamingResponseBody> streamVideo(@PathVariable String fileName,
//                                                             @RequestHeader(value = "Range", required = false) String rangeHeader){
//
//        String range = rangeHeader != null ? rangeHeader.replace("bytes=", "") : null;
//        ResponseBytes<GetObjectResponse> responseBytes = streamFromS3Service.getObject(fileName, range);
//        GetObjectResponse responseMetadata = responseBytes.response();
//
//        // Set headers for partial content if requested
//        long contentLength = responseMetadata.contentLength();
//        HttpHeaders headers = new HttpHeaders();
////        headers.add("Content-Type", responseMetadata.contentType());
//        headers.add("Content-Type", "video/mp4");
//        headers.add("Accept-Ranges", "bytes");
//        System.out.println("range header is "+ rangeHeader);
//        System.out.println("range is " +range);
//        if (rangeHeader != null) {
//            String contentRange = String.format("bytes %s/%d", range, contentLength);
//            System.out.println("Content range is "+ contentRange);
//            headers.add("Content-Range", contentRange);
//            headers.setContentLength(contentLength);
//            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
//                    .headers(headers)
//                    .body(outputStream -> {
//                        InputStream inputStream = new ByteArrayInputStream(responseBytes.asByteArray());
//                        byte[] buffer = new byte[1024];
//                        int bytesRead;
//                        while ((bytesRead = inputStream.read(buffer)) != -1) {
//                            outputStream.write(buffer, 0, bytesRead);
//                        }
//                        inputStream.close();
//                    });
//        } else {
//            headers.setContentLength(contentLength);
//            return ResponseEntity.ok()
//                    .headers(headers)
//                    .body(outputStream -> {
//                        InputStream inputStream = new ByteArrayInputStream(responseBytes.asByteArray());
//                        byte[] buffer = new byte[1024];
//                        int bytesRead;
//                        while ((bytesRead = inputStream.read(buffer)) != -1) {
//                            outputStream.write(buffer, 0, bytesRead);
//                        }
//                        inputStream.close();
//                    });
//        }
//
//
//
//
//    }
//}
