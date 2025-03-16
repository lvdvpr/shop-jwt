package com.apple.shop.item;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class S3Service {

    @Value("${spring.cloud.aws.s3.bucket}")     // application.properties파일에 있던 변수를 가져와서 bucket에 저장이 됨
    private String bucket;
    private final S3Presigner s3Presigner;

    String createPresignedUrl(String path) {
        var putObjectRequest = PutObjectRequest.builder()
                .bucket(bucket)     // 올릴 버킷명
                .key(path)      // 저장할 경로
                .build();
        var preSignRequest = PutObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(3))   // URL유효기간
                .putObjectRequest(putObjectRequest)
                .build();
        return s3Presigner.presignPutObject(preSignRequest).url().toString();   // PresignedURL 발급해줌
    }

}