package com.pactera.znzmo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
* @ClassName：PropertiesUtils
* @Description：TODO(这里用一句话描述这个类的作用)
* @author LiuGuiChao
* @date 2019年10月8日 下午6:13:41
* @version 1.0.0
*/
@Component
public class PropConfigUtil {

   private static final Logger logger = LoggerFactory.getLogger(PropConfigUtil.class);
   private static Properties props;
   static{
       loadProps();
   }

   synchronized static private void loadProps(){
       logger.info("开始加载properties文件内容.......");
       props = new Properties();
       InputStream in = null;
       try {
           // 要加载的属性文件
           in = PropConfigUtil.class.getResourceAsStream("/application.yml");
           props.load(in);
       } catch (FileNotFoundException e) {
           logger.error("jdbc.properties文件未找到");
       } catch (IOException e) {
           logger.error("出现IOException");
       } finally {
           try {
               if(null != in) {
                   in.close();
               }
           } catch (IOException e) {
               logger.error("vas.properties文件流关闭出现异常");
           }
       }
       logger.info("加载properties文件内容完成...........");
       logger.info("properties文件内容：" + props);
   }

   public static String getProperty(String key){
       if(null == props) {
           loadProps();
       }
       return props.getProperty(key);
   }

   public static String getProperty(String key, String defaultValue) {
       if(null == props) {
           loadProps();
       }
       return props.getProperty(key, defaultValue);
   }
}
