package com.hp.common.utils;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@Component
public class ImageExtUtils {

    private static String imageuploadpath;

    @Value("${imageuploadpath}")
    public void setImageuploadpath(String imageuploadpath) {
        ImageExtUtils.imageuploadpath = imageuploadpath;
    }

    private static boolean checkSuffix(String imgPath) {
        Boolean flag =false;
        //图片格式
        String[] FILETYPES = new String[]{
                ".jpg", ".bmp", ".jpeg", ".png", ".gif",
                ".JPG", ".BMP", ".JPEG", ".PNG", ".GIF"
        };
        if(!StringUtils.isBlank(imgPath)){
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


    public static R uploadImage(MultipartFile file) {

        Map<String,Object> map = new HashMap<>();

        if (file.isEmpty()) {
            return R.error("上传文件为空！");
        }else {
            //保存时的文件名
            DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
            Calendar calendar = Calendar.getInstance();
            String dateName = df.format(calendar.getTime())+file.getOriginalFilename();
            System.out.println(dateName);
            if(!checkSuffix(file.getOriginalFilename())){
                return R.error("图片格式不正确");
            }
            File upload = null;
            /* 获取跟目录 */
            try {
                File path = new File(ResourceUtils.getURL("classpath:").getPath());
                if(!path.exists()) path = new File("");
                System.out.println("path:"+path.getAbsolutePath());
                //如果上传目录为/static/images/upload/，则可以如下获取：
                upload = new File(path.getAbsolutePath(),"static/hpwx/uploadImages/");
                if(!upload.exists()) upload.mkdirs();
                System.out.println("upload url:"+upload.getAbsolutePath());

            }catch (IllegalStateException | IOException e){
                e.printStackTrace();
                return R.error("服务器未响应！");
            }

            File newFile = new File(upload,dateName);

            System.out.println("文件的绝对路径:"+newFile);

            //MultipartFile的方法直接写文件
            try {
                //上传文件
                file.transferTo(newFile);
                //数据库存储的相对路径
//                String projectPath = servletContext.getContextPath();
//                String url = "http://localhost:5000/hpwxpc/hpwx/uploadImages/"+dateName;
                String url = imageuploadpath +dateName;
                System.out.println("相对路径:"+url);
//                //文件名与文件URL存入数据库表
                map.put("url",url);

                return R.ok(map);

            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
                return R.error("服务器未响应！");
            }

        }
    }

}
