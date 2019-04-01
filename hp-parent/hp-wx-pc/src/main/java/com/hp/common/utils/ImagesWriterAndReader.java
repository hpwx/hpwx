package com.hp.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import com.amazonaws.services.s3.AmazonS3URI;

@Component
public class ImagesWriterAndReader {

  private static final Logger LOG = LoggerFactory.getLogger(ImagesWriterAndReader.class);

  private static String UPLOAD_PATH;


  private static AwsS3 awsS3;

  @Autowired
  public void setAwsS3(AwsS3 awsS3) {
    this.awsS3 = awsS3;
  }

  @Value("${uploadpath}")
  public void setUploadPath(String uploadPath) {
    ImagesWriterAndReader.UPLOAD_PATH = uploadPath;
  }

  private static boolean checkSuffix(String imgPath) {
    Boolean flag = false;
    // 图片格式
    String[] FILETYPES = new String[] {".jpg", ".bmp", ".jpeg", ".png", ".gif", ".JPG", ".BMP",
        ".JPEG", ".PNG", ".GIF"};
    if (!StringUtils.isBlank(imgPath)) {
      for (int i = 0; i < FILETYPES.length; i++) {
        String fileType = FILETYPES[i];
        if (imgPath.endsWith(fileType)) {
          flag = true;
          break;
        }
      }
    }

    return flag;
  }


  public static R uploadImage(MultipartFile file) throws Exception {

    Map<String, Object> map = new HashMap<>();

    if (file.isEmpty()) {
      return R.error("上传文件为空！");
    } else {
      // 保存时的文件名
      DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
      Calendar calendar = Calendar.getInstance();
      String dateName = df.format(calendar.getTime()) + file.getOriginalFilename();
      System.out.println(dateName);
      if (!checkSuffix(file.getOriginalFilename())) {
        return R.error("图片格式不正确");
      }

      LOG.info("获取流信息：" + file.getInputStream().read());
      InputStream inputStream = file.getInputStream();

      LOG.info("图片存储地址：" + UPLOAD_PATH);
      Path directory = Paths.get(UPLOAD_PATH);

      LOG.info("获取路径：" + directory.getParent());


      if (!Files.exists(directory)) {
        Files.createDirectories(directory);
      }
      long copy = Files.copy(inputStream, directory.resolve(dateName));

      return R.ok(dateName);
    }
  }



  public static R uploadAwsImage(MultipartFile file) {

    Map<String, Object> map = new HashMap<>();
    try {


      if (file.isEmpty()) {
        return R.error("上传文件为空！");
      } else {
        // 保存时的文件名
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar calendar = Calendar.getInstance();
        String dateName = df.format(calendar.getTime()) + file.getOriginalFilename();
        System.out.println(dateName);
        if (!checkSuffix(file.getOriginalFilename())) {
          return R.error("图片格式不正确");
        }
        LOG.info("========== 开始上传======");
        AmazonS3URI s3url = new AmazonS3URI(
            "s3://hpi-cn-local-business-css-wechat/css-wechat-520-activity/itg/images/" + dateName);

        LOG.info("=======bucket: " + s3url.getBucket());
        LOG.info("=======region: " + s3url.getRegion());


        String imageurl = null;
        try {
          imageurl = awsS3.uploadAndGetUrl(s3url, file.getInputStream());
        } catch (IOException e) {
          e.printStackTrace();
        }
        LOG.info("=======图片地址 ： ========" + imageurl);
        map.put("url", imageurl);

      }
    } catch (Exception ex) {

      LOG.info("22", ex);

    }
    return R.ok(map);


  }



}
