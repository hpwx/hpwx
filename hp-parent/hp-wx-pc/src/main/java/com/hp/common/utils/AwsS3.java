package com.hp.common.utils;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.AmazonS3URI;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

@Component
public class AwsS3 {

  private Logger log = LoggerFactory.getLogger(AwsS3.class);

  @Value("${aws.s3.region:}")
  private String region;

  @Value("${aws.s3.credentials.type:none}")
  private String type;

  @Value("${aws.s3.credentials.accessKey:}")
  private String accessKey;

  @Value("${aws.s3.credentials.secretKey:}")
  private String secretKey;

  private AmazonS3 amazonS3;

  @PostConstruct
  public void init() {
    try {
      AmazonS3ClientBuilder builder = AmazonS3ClientBuilder.standard();
      if (StringUtils.hasText(region)) {
        builder.setRegion(region);
      }
      AWSCredentialsProvider credentialsProvider = null;
      if ("BASIC".equalsIgnoreCase(type)) {
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        credentialsProvider = new AWSStaticCredentialsProvider(credentials);
        builder.setCredentials(credentialsProvider);
      }
      // build AmazonS3
      amazonS3 = builder.build();
    } catch (Exception e) {
      log.error("AmazonS3 client build failed on region[{}]:{}", region, e.getMessage());
    }
  }

  /**
   * 獲取S3對象的InputStream
   * 
   * @param amazonS3URI
   * @return
   */
  public InputStream getInputStream(AmazonS3URI amazonS3URI) {
    S3Object object =
        amazonS3.getObject(new GetObjectRequest(amazonS3URI.getBucket(), amazonS3URI.getKey()));
    return object.getObjectContent();
  }

  /**
   * 上傳至S3
   * 
   * @param amazonS3URI
   * @param file
   */
  public void upload(AmazonS3URI amazonS3URI, File file) {
    PutObjectRequest o = new PutObjectRequest(amazonS3URI.getBucket(), amazonS3URI.getKey(), file);
    amazonS3.putObject(o);
  }

  /**
   * 上傳至S3
   * 
   * @param amazonS3URI
   * @param file
   */
  public void upload(AmazonS3URI amazonS3URI, InputStream ins) {
    PutObjectRequest o =
        new PutObjectRequest(amazonS3URI.getBucket(), amazonS3URI.getKey(), ins, null);
    amazonS3.putObject(o);
  }


  /**
   * 上傳至S3,返回URl
   * 
   * @param amazonS3URI
   * @param file
   */
  public String uploadAndGetUrl(AmazonS3URI amazonS3URI, File file) {
    PutObjectRequest o = new PutObjectRequest(amazonS3URI.getBucket(), amazonS3URI.getKey(), file)
        .withCannedAcl(CannedAccessControlList.PublicRead);
    amazonS3.putObject(o);
    return getPulicUrl(amazonS3URI);
  }


  /**
   * 上傳至S3
   * 
   * @param amazonS3URI
   * @param file
   */
  public String uploadAndGetUrl(AmazonS3URI amazonS3URI, InputStream ins) {
    PutObjectRequest o =
        new PutObjectRequest(amazonS3URI.getBucket(), amazonS3URI.getKey(), ins, null)
            .withCannedAcl(CannedAccessControlList.PublicRead);

    amazonS3.putObject(o);
    return getPulicUrl(amazonS3URI);
  }

  /**
   * 获取S3公开URL
   * 
   * @param amazonS3URI
   * @return
   */
  private String getPulicUrl(AmazonS3URI amazonS3URI){
    // GeneratePresignedUrlRequest urlRequest =
    // new GeneratePresignedUrlRequest(amazonS3URI.getBucket(), amazonS3URI.getKey());
    // Calendar ca = Calendar.getInstance();
    // ca.add(Calendar.DATE, 5);
    // // 设置过期时间
    // urlRequest.setExpiration(ca.getTime());
    // 生成公用的url
    URL url = amazonS3.getUrl(amazonS3URI.getBucket(), amazonS3URI.getKey());
    if (url == null) {
      return null;
    }
    return url.toString();
  }

}

