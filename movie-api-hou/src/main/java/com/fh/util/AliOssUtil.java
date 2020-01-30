package com.fh.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;

import java.io.InputStream;
import java.util.UUID;

public class AliOssUtil {

   private static final String ENDPOINT="http://oss-cn-beijing.aliyuncs.com";
   private static final String ACCESSKEYID="LTAI4FeUvwWqYhDqJKsjSqqk";
   private static final String ACCESSKEYSCIRET="ImA5CyUTXbZWsnq1D9K1HeOKy0pBJ4";
   private static final String BUCKETNAME="mjh-0130";
   private static final String BEFORENAME="http://mjh-0130.oss-cn-beijing.aliyuncs.com";


   public static String uploadimage(InputStream inputStream,String filename,String foldername){


       String substring = filename.substring(filename.lastIndexOf("."));
       String objectName=foldername+"/"+ UUID.randomUUID().toString()+substring;
       OSS oss=new OSSClientBuilder().build(ENDPOINT,ACCESSKEYID,ACCESSKEYSCIRET);
       oss.putObject(BUCKETNAME,objectName,inputStream);
       oss.shutdown();
      return BEFORENAME+"/"+objectName;

   }


   public static void deleteimage(String filename){

       OSS oss=new OSSClientBuilder().build(ENDPOINT,ACCESSKEYID,ACCESSKEYSCIRET);
       String abc=filename.substring(filename.indexOf("images"));
       oss.deleteObject(BUCKETNAME,abc);
       oss.shutdown();
   }




}
