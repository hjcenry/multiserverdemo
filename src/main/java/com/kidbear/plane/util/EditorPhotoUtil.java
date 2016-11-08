package com.kidbear.plane.util;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class EditorPhotoUtil {
	/**
	 * 
	 * 方法描述. 获取inputFilePath的后缀名，如："e:/test.pptx"的后缀名为："pptx"
	 * 
	 * @param inputFilePath
	 * @return
	 */
	public static String getPostfix(String inputFilePath) {
		return inputFilePath.substring(inputFilePath.lastIndexOf(".") + 1);
	}

	public static void savePhoto(Integer x, Integer y, Integer w, Integer h, String srcpath, String targetpath,
			Integer photoWidth, Integer photoHeight) {
		BufferedImage subImage = null;
		try {
			BufferedImage bufferedImage = ImageIO.read(new File(srcpath));
			// 获得原始图片的像素
			Integer width = bufferedImage.getWidth();
			Integer height = bufferedImage.getHeight();
			x = x * width / photoWidth;
			y = y * height / photoHeight;
			w = w * width / photoWidth;
			h = h * height / photoHeight;
			// 截取图片
			subImage = bufferedImage.getSubimage(x, y, w, h);
			// 压缩保存图片
			String imgType = getPostfix(srcpath);
			// 图片压缩后的分辨率
			int widths = 80;
			int heights = 80;
			BufferedImage newImage = new BufferedImage(widths, heights, subImage.getType());
			Graphics g = newImage.getGraphics();
			g.drawImage(subImage, 0, 0, widths, heights, null);
			g.dispose();
			ImageIO.write(newImage, imgType, new File(targetpath));

		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	/**
	 * 压缩图片方法
	 * 
	 * @param oldFile
	 *            将要压缩的图片
	 * @param width
	 *            压缩宽
	 * @param height
	 *            压缩高
	 * @param quality
	 *            压缩清晰度 <b>建议为1.0</b>
	 * @param smallIcon
	 *            压缩图片后,添加的扩展名（在图片后缀名前添加）
	 * @param percentage
	 *            是否等比压缩 若true宽高比率将将自动调整
	 * @author slzs
	 * @return 如果处理正确返回压缩后的文件名 null则参数可能有误
	 */
	public static String doCompress(File file, int width, int height, float quality, boolean percentage, String path) {
		if (file != null && width > 0 && height > 0) {
			Image srcFile = null;
			String newImage = null;
			String newFileName = null;
			try {
				/* 读取图片信息 */
				srcFile = ImageIO.read(file);
				int new_w = width;
				int new_h = height;
				if (percentage) {
					// 为等比缩放计算输出的图片宽度及高度
					double rate1 = ((double) srcFile.getWidth(null)) / (double) width + 0.1;
					double rate2 = ((double) srcFile.getHeight(null)) / (double) height + 0.1;
					double rate = rate1 > rate2 ? rate1 : rate2;
					new_w = (int) (((double) srcFile.getWidth(null)) / rate);
					new_h = (int) (((double) srcFile.getHeight(null)) / rate);
				}
				/* 宽高设定 */
				BufferedImage tag = new BufferedImage(new_w, new_h, BufferedImage.TYPE_INT_RGB);
				tag.getGraphics().drawImage(srcFile, 0, 0, new_w, new_h, null);
				
				String fileName = file.getName();
				String fileType = fileName.substring(fileName.lastIndexOf("."), fileName.length());
				newFileName = UUID.randomUUID().toString() + fileType;
				newImage = path + File.separator + newFileName;

				/* 压缩之后临时存放位置 */
				FileOutputStream out = new FileOutputStream(newImage);
				JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
				JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(tag);
				/* 压缩质量 */
				jep.setQuality(quality, true);
				encoder.encode(tag, jep);
				out.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				srcFile.flush();
			}
			return newFileName;
		} else {
			return null;
		}
	}
	
	public static void main(String[] args) {
		
		File file = new File("e://2.jpg");
		String path = "e:";
		String string = doCompress(file, 70, 70, 1, true, path);
		System.out.println(string);
		/*try {
		 //String url = "http://img.sc115.com/uploads/sc/png/140315/01.png";
		//String path = "f://2.jpg";
			HttpClient.download(url, path);
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	
	public static String SaveImgFile(HttpServletRequest request,String fileInput) throws IOException
    {   
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile imgFile = multipartRequest.getFile(fileInput);
		
		String fileName = UUID.randomUUID().toString()+".jpg";
		String path = request.getSession().getServletContext().getRealPath("images")+
				File.separator+"activity"+File.separator+fileName;

		File file = new File(path);
		if(!file.exists()){  
			//file.mkdirs();  
        }
		if(imgFile.getSize()==0){                
            return null;
        }
		InputStream stream = imgFile.getInputStream();
		try{
           FileOutputStream fs=new FileOutputStream(file);
           byte[] buffer =new byte[1024*1024];
           int bytesum = 0;
           int byteread = 0; 
           
           while ((byteread=stream.read(buffer))!=-1)
           {
              bytesum+=byteread;
              fs.write(buffer,0,byteread);
              fs.flush();
           } 
	        fs.close();
	        stream.close();  
		}catch (IOException e){
			e.printStackTrace();
		}finally{
	        stream.close();  
		}
        return fileName;
    }       
	

}
