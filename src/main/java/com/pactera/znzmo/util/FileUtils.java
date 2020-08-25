package com.pactera.znzmo.util;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件处理工具类.
 * <pre>
 * <b>依赖jar包：</b>
 * javax.servlet-api-3.0.1.jar
 * spring-web-4.1.3.RELEASE.jar
 * spring-mock-2.0.8.jar
 * </pre>
 * @author wangchaojie　2018年1月10日
 */

public class FileUtils {

	public static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

    /**
     * 上传文件
     * @param request 请求对象
     * @param file 控制层获取到的文件
     * @param uploadSubPath 文件的路径
     * @return 上传后的文件信息map
     *
     */
	public static Map<String, Object> uploadFile(HttpServletRequest request, MultipartFile file, String uploadSubPath) {
          Map<String, Object> jsonMap = new HashMap<String, Object>();
          try {
        	  if(file.isEmpty()){
                  jsonMap.put("success", false);
                  jsonMap.put("errorCode", "fileEmpty");
                  jsonMap.put("error", "上传文件为空");
                  return jsonMap;
              }

              //获取文件名
              String fileName = file.getOriginalFilename();

              //获取文件后缀
              String fileSuffix = fileName.substring(fileName.lastIndexOf("."), fileName.length());

              // 获取当前当前配置保存物理路径
              if (StringUtils.isEmpty(uploadSubPath)) {
                  uploadSubPath = "/default";
              }

              //文件大小
              long size = file.getSize();
              DecimalFormat df = new DecimalFormat("0.00");
  			  Double doubleSize = Double.valueOf(String.valueOf(size))/1024/1024;
  			  String sizes = "";
  			  if(doubleSize > 1024){
  				  doubleSize = doubleSize/1024;
  				  sizes = df.format(doubleSize) + "G";
  			  }else {
  				  sizes = df.format(doubleSize) + "M";
          	  }
              
  			  //图片尺寸
  			  InputStream is = file.getInputStream();
  			  BufferedImage sourceImg = ImageIO.read(is);
  			  String pictureSize = "";
  			  if(sourceImg != null) {
  				pictureSize = sourceImg.getHeight() + "*" + sourceImg.getWidth();
  			  }
              
              //创建相对路径
              String serverRealPath =  FileUtils.getRealPath(request);
//              SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//              String dirRelativePath = uploadSubPath + "/"  + sdf.format(new Date());
              String dirRelativePath = uploadSubPath;
              String dirRealPath = serverRealPath + dirRelativePath;
             
              //创建文件夹
              File uploadDir = new File(dirRealPath);
              if(!uploadDir.exists()){
                  uploadDir.mkdirs();
              }

              //文件重命名
              String fileEnd = System.currentTimeMillis() + "";
              String uploadFileName = fileEnd + fileSuffix;

              //创建文件
              File uploadFile = new File(dirRealPath + "/" + uploadFileName);
              //上传
              file.transferTo(uploadFile);

              //文件的相对路径
              String relativeUrl =   dirRelativePath + "/" + uploadFileName;
              String domain = getAccessDomain(request);
              String accessUrl = domain + relativeUrl;
              
              jsonMap.put("success", true);
              jsonMap.put("url", relativeUrl);
              //绝对路径
              jsonMap.put("accessUrl", accessUrl);
              //上传前文件名称
              jsonMap.put("realName", fileName);
              jsonMap.put("realPath", uploadFile.getPath());
              jsonMap.put("fileName", uploadFileName);
              jsonMap.put("file", uploadFile);
              jsonMap.put("sizes", sizes);
              jsonMap.put("pictureSize", pictureSize);
              jsonMap.put("fileSuffix", fileSuffix);
          } catch (Exception e) {
              jsonMap.put("success", false);
              jsonMap.put("error",e.getMessage());
              logger.error("上传文件异常",e);
          }
          return jsonMap;
      }

	/**
	  *
	  * 检查上传文件大小是否超限.
	  * <pre>
	  * FileUtils.isFileExceedSize(null,12) = true;
	  * FileUtils.isFileExceedSize(File,0()) = false;
	  * </pre>
	  * @param file 文件对象
	  * @param limitSize 限制文件大小(单位:MB)
	  * @return true 超限； false 没有超限
	  *
	 */
	public static boolean isFileExceedSize(File file,long limitSize){
		boolean isValidSize = true;
		if(file == null){
			return isValidSize;
		}
		long fileSize = file.length();
		if(limitSize > 0){
			if(fileSize > 0 && fileSize <= (limitSize * 1024 * 1024)){
				isValidSize = false;
			}
		}else{
			isValidSize = false;
		}
		return isValidSize;
	}

   /**
    * 下载文件
    * @param filePath  文件全路径包含文件名及文件格式
    * @param fileName  完整文件名及格式
    */
    public static void downloadFile(String filePath, String fileName, HttpServletRequest request,HttpServletResponse response) throws Exception {
       File f = new File(filePath);
       if (!f.exists()) {
           response.setContentType("text/html;charset=utf-8");
           response.getWriter().print("<html><head><title>文件不存在</title></head><body>文件不存在！</body></html>");
           return;
       }
       BufferedInputStream bis = null;
       BufferedOutputStream bos = null;

       response.setContentType("application/vnd.ms-excel;charset=utf-8");
//       response.setContentType("application/octet-stream");
       boolean isMSIE = isMSBrowser(request);
       if (isMSIE) {
        //IE浏览器的乱码问题解决
            fileName = URLEncoder.encode(fileName, "UTF-8");
       } else {
        //万能乱码问题解决
            fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
       }
       response.setHeader("content-disposition", "attachment;filename=\"" + fileName + "\"");
//       response.setHeader("filename", fileName);
//       response.setHeader("Access-Control-Expose-Headers", "filename");

       try {
           bis = new BufferedInputStream(new FileInputStream(filePath));
           bos = new BufferedOutputStream(response.getOutputStream());
           byte[] buff = new byte[2048];
           int bytesRead;
           while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
               bos.write(buff, 0, bytesRead);
           }
       } catch (IOException e) {
           throw e;
       } finally {
           if (bis != null){
               bis.close();
           }
           if (bos != null){
               bos.close();
           }

       }
   }

	private static String[] IEBrowserSignals = {"MSIE", "Trident", "Edge"};

    public static boolean isMSBrowser(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
	    for (String signal : IEBrowserSignals) {
	        if (userAgent.contains(signal)) {
	            return true;
	        }
	    }
	    return false;
	}


    /**
      * 从网络上下载文件到本地.</br>
      * PS：url请求超时设置3秒.
      * @param srcUrl 网络文件url
      * @param localFileName 存储到本地的文件名
      * @param localPath 存储到本地的目录
      * @throws IOException
      *
     */
    public static void downloadFile(String srcUrl, String localFileName, String localPath) throws IOException{
         InputStream inputStream =null;

    	 FileOutputStream fos = null;

    	 try {
    	        URL u = new URL(srcUrl);
    	        HttpURLConnection conn = (HttpURLConnection)u.openConnection();
    	        //设置超时间为3秒
    	        conn.setConnectTimeout(3*1000);
    	        //防止屏蔽程序抓取而返回403错误
    	        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

    	        //得到输入流
    	        inputStream = conn.getInputStream();
    	        //获取自己数组
    	        byte[] getData = readInputStream(inputStream);

    	        //文件保存位置
    	        File saveDir = new File(localPath);
    	        if(!saveDir.exists()){
    	            saveDir.mkdir();
    	        }
    	        File file = new File(saveDir+"/"+localFileName);
    	        fos = new FileOutputStream(file);
    	        fos.write(getData);

         } catch (IOException e) {
             logger.error("下载文件异常",e);
         } finally {
        	 if(fos!=null){
                 fos.close();
             }
             if(inputStream!=null){
                 inputStream.close();
             }
         }
    }

    /**
     * 从输入流中获取字节数组
     * @param inputStream 输入流
     * @return byte[]
     * @throws IOException
     */
    private static byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }

    /**
	 * @Title: getBytesFromFile
	 * @Description: 获取文件字节流
	 * @param
	 * @return
	 * @throws IOException byte[]
	 * @author LiuGuiChao
	 * @date 2018年12月4日 下午3:27:03
	*/
	public static byte[] getBytesFromFile(String fileUrl,HttpServletRequest request) throws IOException {
		String domain = UIHelper.getWebRealPath(request);
		String accessUrl = domain + fileUrl;
		File file = new File(accessUrl);
		InputStream is = new FileInputStream(file);
       	try {
			// 获取文件大小
			long length = file.length();
			if (length > Integer.MAX_VALUE) {
				// 文件太大，无法读取
				throw new IOException("File is to large " + file.getName());
			}
			// 创建一个数据来保存文件数据
			byte[] bytes = new byte[(int) length];
			// 读取数据到byte数组中
			int offset = 0;
			int numRead = 0;
			while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
				offset += numRead;
			}
			// 确保所有数据均被读取
			if (offset < bytes.length) {
				throw new IOException("Could not completely read file " + file.getName());
			}
			return bytes;
		}catch (Exception e){
			logger.error(e.getMessage(),e);
			return null;
		}finally {
			is.close();
		}
		// Close the input stream and return bytes


	}

    /**
      * 文件拷贝
      * @param srcFile 源文件
      * @param destFile 拷贝后的文件
     */
    public static void copyFile(File srcFile, File destFile) {
		createDir(destFile);
		int length = 1024;
		FileInputStream in = null;
		FileOutputStream out = null;
		try {
			in = new FileInputStream(srcFile);
			out = new FileOutputStream(destFile);
			FileChannel inC = in.getChannel();
			FileChannel outC = out.getChannel();
			ByteBuffer b = null;
			while (true) {
				if (inC.position() == inC.size()) {
					inC.close();
					outC.close();
					break;
				}
				if ((inC.size() - inC.position()) < length) {
					length = (int) (inC.size() - inC.position());
				}
				b = ByteBuffer.allocateDirect(length);
				inC.read(b);
				b.flip();
				outC.write(b);
				outC.force(false);
			}
		}catch(Exception e) {
			logger.error(e.getMessage(),e);
		}finally {
			if(in != null) {
				try {
					in.close();
				} catch (IOException e) {
					logger.error(e.getMessage(),e);
				}
			}
			if(out != null) {
				try {
					out.close();
				} catch (IOException e) {
					logger.error(e.getMessage(),e);
				}
			}
		}
    }

    private static void createDir(File file) {
        file = file.getParentFile();
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * 获取上传物理路径
     * @return
     */
    public static String getRealPath(HttpServletRequest request){
    	String realPath = request.getSession().getServletContext().getRealPath("");//获得项目的物理路径
		logger.info("项目的物理路径"+realPath);
    	String mode = Constants.UPLOAD_MODE;
    	if("nas".equals(mode)){
    		if(StringUtils.isNotEmpty(Constants.UPLOAD_NAS_DIR)){
    			realPath = Constants.UPLOAD_NAS_DIR;
    		}
    	}
    	return realPath;
    }

    /**
     * 获取文件访问域名
     * @return
     */
    public static String getAccessDomain(HttpServletRequest request){
    	String accessDomain = UIHelper.getWebRoot(request);
    	String mode = Constants.UPLOAD_MODE;
    	if("nas".equals(mode)){
    		String domain = UIHelper.getWebDomain(request);
    		accessDomain = domain + Constants.UPLOAD_NAS_ACCESS;
    	}
    	return accessDomain;
    }

    /***
     *
      * @Title: FileUtils.java
      * @Description: 上传到项目指定路径下
      * @param: MultipartFile file
      * @return: Map<String, Object>
      * @throws: Exception JSONArray
      * @author: Yang
      * @date: 2019年3月22日
     */
    public static Map<String, Object> fileUploadToLocal(MultipartFile file) {
    	Map<String, Object> jsonMap = new HashMap<String, Object>();
    	File upload = null;
		try {
			//获取当前路径
			String url = Constants.UPLOAD_FOLDER;
			File path = new File(url);

	        if(!path.exists()) path = new File("");
	        upload = new File(path.getAbsolutePath());
	        if(!upload.exists()) upload.mkdirs();
	        String realPath = upload + "/";

	    	//获取文件名
	        String fileName = file.getOriginalFilename();

	        //获取文件后缀
	        String fileSuffix = fileName.substring(fileName.lastIndexOf("."), fileName.length());

	        //文件重命名
            String fileEnd = System.currentTimeMillis() + "";
            String uploadFileName = fileEnd + fileSuffix;

	        //创建文件
	        String uploadFileStr = realPath + "/" + uploadFileName;
	        File uploadFile = new File(uploadFileStr);
			if(!uploadFile.exists()){
				uploadFile.getParentFile().mkdir();
			    //创建文件
			    boolean createNewFile = uploadFile.createNewFile();
			    if(!createNewFile) {
			    	 jsonMap.put("success", false);
				     jsonMap.put("error","创建文件失败！");
				     return jsonMap;
			    }
			}

			//上传
			file.transferTo(uploadFile);

	        jsonMap.put("success", true);
	        //上传前文件名称
	        jsonMap.put("accessName", fileName);
	        //绝对路径
	        jsonMap.put("accessUrl", uploadFile.getPath());
	        //虚拟路径
	        jsonMap.put("realPath", Constants.ACCESS_PATH.replace("**", "")+uploadFileName);
	    } catch (IOException e) {
	        jsonMap.put("success", false);
	        jsonMap.put("error",e.getMessage());
	        logger.error("上传文件异常",e);
	    }
		return jsonMap;
    }

    /**
	 * @throws IOException
     * @throws IllegalStateException
     * @Description: 转换File文件
	*/
	public static File getFile(MultipartFile multipartFile) throws IllegalStateException, IOException {
		//文件上传前的名称
    	String fileName = multipartFile.getOriginalFilename();
        // 获取文件后缀
        String prefix=fileName.substring(fileName.lastIndexOf("."));
        // 用uuid作为文件名，防止生成的临时文件重复
        final File excelFile = File.createTempFile(UUIDUtils.create(), prefix);
        // MultipartFile to File
        multipartFile.transferTo(excelFile);
		return excelFile;
	}
	
}
