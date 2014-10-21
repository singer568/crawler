/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.cowswing.ui.util;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.ConvolveOp;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.Kernel;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.ImageIcon;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.javacoo.cowswing.base.constant.Constant;

/**
 * 图片处理工具类：<br>
 * 功能：缩放图像、切割图像、图像类型转换、彩色转黑白、文字水印、图片水印等
 * 
 * @author DuanYong
 * @since 2012-12-11下午2:55:29
 * @version 1.0
 */
public class ImageUtils {
	/**
	 * 几种常见的图片格式
	 */
	public static String IMAGE_TYPE_GIF = "gif";// 图形交换格式
	public static String IMAGE_TYPE_JPG = "jpg";// 联合照片专家组
	public static String IMAGE_TYPE_JPEG = "jpeg";// 联合照片专家组
	public static String IMAGE_TYPE_BMP = "bmp";// 英文Bitmap（位图）的简写，它是Windows操作系统中的标准图像文件格式
	public static String IMAGE_TYPE_PNG = "png";// 可移植网络图形
	public static String IMAGE_TYPE_PSD = "psd";// Photoshop的专用格式Photoshop
	
	private static float contrast = 1.5f; // default value;  
	private static float brightness = 1.0f; // default value; 

	/**
	 * 程序入口：用于测试
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// 1-缩放图像：
		// 方法一：按比例缩放
		ImageUtils.scaleByRate("e:/abc.jpg", "e:/abc_scale.jpg", 2, true);// 测试OK
		// 方法二：按高度和宽度缩放
		ImageUtils.scaleByWH("e:/abc.jpg", "e:/abc_scale2.jpg", 500, 300, true);// 测试OK

		// 2-切割图像：
		// 方法一：按指定起点坐标和宽高切割
		ImageUtils.cutByXYWH("e:/abc.jpg", "e:/abc_cut.jpg", 0, 0, 400, 400);// 测试OK
		// 方法二：指定切片的行数和列数
		ImageUtils.cutByRowCol("e:/abc.jpg", "e:/", 2, 2);// 测试OK
		// 方法三：指定切片的宽度和高度
		ImageUtils.cutByWH("e:/abc.jpg", "e:/", 300, 300);// 测试OK

		// 3-图像类型转换：
		ImageUtils.convert("e:/abc.jpg", "GIF", "e:/abc_convert.gif");// 测试OK

		// 4-彩色转黑白：
		ImageUtils.gray("e:/abc.jpg", "e:/abc_gray.jpg");// 测试OK

		// 5-给图片添加文字水印：
		// 方法一：
		ImageUtils.pressText("我是水印文字", "e:/abc.jpg", "e:/abc_pressText.jpg",
				"宋体", Font.BOLD, Color.white, 80, 0, 0, 0.5f);// 测试OK
		// 方法二：
		ImageUtils.pressText2("我也是水印文字", "e:/abc.jpg", "e:/abc_pressText2.jpg",
				"黑体", 36, Color.white, 80, 0, 0, 0.5f);// 测试OK

		// 6-给图片添加图片水印：
		ImageUtils.pressImage("e:/abc2.jpg", "e:/abc.jpg",
				"e:/abc_pressImage.jpg", 0, 0, 0.5f);// 测试OK
	}

	/**
	 * 缩放图像（按比例缩放）
	 * 
	 * @param srcImageFile
	 *            源图像文件地址
	 * @param result
	 *            缩放后的图像地址
	 * @param scale
	 *            缩放比例
	 * @param flag
	 *            缩放选择:true 放大; false 缩小;
	 */
	public final static void scaleByRate(String srcImageFile, String result,
			int scale, boolean flag) {
		try {
			BufferedImage src = ImageIO.read(new File(srcImageFile)); // 读入文件
			int width = src.getWidth(); // 得到源图宽
			int height = src.getHeight(); // 得到源图长
			if (flag) {// 放大
				width = width * scale;
				height = height * scale;
			} else {// 缩小
				width = width / scale;
				height = height / scale;
			}
			Image image = src.getScaledInstance(width, height,
					Image.SCALE_DEFAULT);
			BufferedImage tag = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics g = tag.getGraphics();
			g.drawImage(image, 0, 0, null); // 绘制缩小后的图
			g.dispose();
			ImageIO.write(tag, "JPEG", new File(result));// 输出到文件流
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 缩放图像（按比例缩放）
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-12-23 下午2:57:31
	 * @param srcImageFile 路径
	 * @param scale 缩放比例
	 * @return void
	 */
	public final static BufferedImage scaleByScale(String srcImageFile,int scale){
		try {
			BufferedImage bi = getBufferedImageByImagePath(srcImageFile);
			int width = bi.getWidth() *  scale / 100 ;
			int height = bi.getHeight() * scale / 100;
			
			//构建图片对象  
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); 
            //绘制缩小后的图  
            image.getGraphics().drawImage(bi, 0, 0, width, height, null);   
			return image;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public final static void saveAsImage(BufferedImage bufferedImage,String savePath,String formatName){
		File file  = null;
		try {
			file = new File(savePath);
			if(file.exists()){
				file.delete();
			}
			file.canRead();
			file.canWrite();
			// 输出到文件流
			ImageIO.write(bufferedImage, formatName, file);
			bufferedImage.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			file = null;
			bufferedImage = null;
		}
	}
	/**
	 * 根据图片地址取得图片BufferedImage
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-12-23 下午9:01:49
	 * @param srcImageFile
	 * @throws IOException
	 * @return BufferedImage
	 */
	public final static BufferedImage getBufferedImageByImagePath(String srcImageFile) throws IOException{
		BufferedImage bi = null;
		if(srcImageFile.startsWith(Constant.HTTP) || srcImageFile.startsWith(Constant.HTTPS)){
			URL url = new URL(srcImageFile);
			bi = ImageIO.read(url.openStream());
		}else{
			bi = ImageIO.read(new File(srcImageFile));
		}
		return bi;
	}
	/**
	 * 根据图片质量调整图片
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-12-25 下午8:44:45
	 * @param srcImageFile
	 * @param savePath
	 * @param quality
	 * @return BufferedImage
	 */
	public final static BufferedImage scaleByQuality(BufferedImage bufferedImage,String fullSaveImagePath,int quality){
		try {
			ImageWriter writer = null;
			ImageTypeSpecifier type = ImageTypeSpecifier.createFromRenderedImage(bufferedImage);
			Iterator iter = ImageIO.getImageWriters(type, "jpg");
			if (iter.hasNext()) {
				writer = (ImageWriter)iter.next();
				IIOImage iioImage = new IIOImage(bufferedImage, null, null);
				ImageWriteParam param = writer.getDefaultWriteParam();
		
				param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
				param.setCompressionQuality(quality / 100f);
				ImageOutputStream outPutStream = ImageIO.createImageOutputStream(new FileOutputStream(fullSaveImagePath));
				try {
					writer.setOutput(outPutStream);
					writer.write(null, iioImage, param);
				} catch (IOException e) {
					e.printStackTrace();
				} 
				return getBufferedImageByImagePath(fullSaveImagePath);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public final static BufferedImage backGround(Graphics g,BufferedImage bufferedImage){
		Graphics2D g2 =  bufferedImage.createGraphics();
		bufferedImage = g2.getDeviceConfiguration().createCompatibleImage(bufferedImage.getWidth(), bufferedImage.getHeight(), Transparency.TRANSLUCENT);
		
		g2.dispose();
		return bufferedImage;
	}
	public final static void waterText(BufferedImage bufferedImage,String[] pressTextArray,String dateText,Font font, Color fontColor,boolean hasBg,Color bgColor,boolean hasBorder,Color borderColor, int x, int y){
		Graphics2D g =  bufferedImage.createGraphics();
		int width = bufferedImage.getWidth();
		int height = bufferedImage.getHeight();
		//设置背景颜色
		if(hasBg){
			AlphaComposite acBg = AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 1f);
			g.setComposite(acBg);
			g.setColor(bgColor);
			g.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
		}
		
		
		// 创建AlphaComposite对象，并设定透明度
	    AlphaComposite acFont = AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 1f);
	    g.setComposite(acFont);
	    g.setColor(fontColor);
		g.setFont(font);
		//文字高度
		int textHeight = 0;
		if(null != pressTextArray && pressTextArray.length > 0){
			for(int i=0;i<pressTextArray.length;i++){
				// 在指定坐标绘制水印文字
				g.drawString(pressTextArray[i], (bufferedImage.getWidth() - (getLength(pressTextArray[i]) * font.getSize()))
						/ 2 + x, y + i * font.getSize());
			}
			textHeight = pressTextArray.length * font.getSize();
		}
		if(StringUtils.isNotBlank(dateText)){
			g.drawString(dateText, (bufferedImage.getWidth() - (getLength(dateText) * font.getSize()))
					/ 2 + x, y + textHeight);
		}
		//画一个背景色为..的长方形
		if(hasBorder){
			  AlphaComposite acBorder = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);
			  g.setComposite(acBorder);
			  g.setColor(borderColor);
			  g.drawRect(0, 0, width - 1, height - 1);
			  g.drawRect(1, 1, width - 1, height - 1);
			  g.drawRect(0, 0, width-2, height- 2);  
		}
	   // g.setColor(Color.CYAN);
	   // g.fill3DRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(),false);
		
		g.dispose();
	}
	/**
	 * 设置透明度
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-12-29 下午10:04:52
	 * @param g
	 * @param alpha
	 * @return void
	 */
	public final static void opacity(Graphics g,int alpha){
		Graphics2D g2 = (Graphics2D)g;
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,alpha / 100f));
	}
	/**
	 * 旋转图片
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-12-29 下午5:06:22
	 * @param g Graphics对象
	 * @param bufferedImage 图片
	 * @param xScale 水平翻转比例的标志。-1表示需要进行水平翻转
	 * @param yScale 垂直翻转比例的标志。-1表示需要进行垂直翻转
	 * @param roteAngle 旋转角度
	 * @return BufferedImage
	 */
	public final static BufferedImage gyral(Graphics g,BufferedImage bufferedImage,int xScale,int yScale,int roteAngle){
		try {
			int width = bufferedImage.getWidth();
			int height = bufferedImage.getHeight();
			
            drawTransImage(g,bufferedImage,xScale,yScale,width,height,0,roteAngle);
            return bufferedImage;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public final static BufferedImage gyral(BufferedImage bufferedImage,int xScale,int yScale,int roteAngle){
		try {
			int width = bufferedImage.getWidth();
			int height = bufferedImage.getHeight();
			//构建图片对象  
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            drawTransImage(image.getGraphics(),bufferedImage,xScale,yScale,width,height,0,roteAngle);
            return image;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private final static void drawTransImage(Graphics g,Image img,int xScale,int yScale,int drawx,int drawy,int zoom,int roteAngle)
    {
        int x = 0 ;
        int y = 0 ;
        int w = img.getWidth(null) ;
        int h = img.getHeight(null) ;
        int zoomw = getZoomSize(w,zoom) ;
        int zoomh = getZoomSize(h,zoom) ;
        int xPos = 0 ;
        int yPos = 0 ;
        if (xScale == -1)
              xPos = -zoomw ;
        if (yScale == -1)
              yPos = -zoomh ;
        Graphics2D g2 = (Graphics2D)g;
        //转换坐标原点。这步不要也成，但是将当前位置转换为坐标原点后，可以节省好多计算步骤，非常好用。
         //不过记得用完了以后，一定要把原点转换回来，要不然其他地方就乱了
        //g2.translate(drawx,drawy);
        if (roteAngle != 0)
              g2.rotate(Math.toRadians(xScale * yScale * roteAngle),zoomw >> 1,zoomh >> 1);
              //上面的m_nFlipXScale * m_nFlipYScale需要特殊说明一下：因为实际使用中，可能遇到各种组合的情况，比如
                //先flipX或者flipY以后然后再旋转，这时候，图片的旋转方向就会出现错误，加上这段代码可以保证无论使用哪种组合
                //操作方式，都保证在旋转图片的时候是按照顺时针的方向进行旋转。
        if (xScale == -1)
              g2.scale(-1,1);//第一个值表示水平，-1表示等宽水平翻转，Math.abs(m_nFlipXScale)的值越大，出来的图片就越宽
        if (yScale == -1)
              g2.scale(1,-1);//第二个值表示垂直，-1表示等高垂直翻转，Math.abs(m_nFlipYScale)的值越大，出来的图片就越高
        //显示图片
        g2.drawImage(img,xPos,yPos,xPos + zoomw,yPos + zoomh,x,y,w,h,null) ;
        g2.dispose();
        //g2.translate(-drawx,-drawy);        
    }
	
	private static final int getZoomSize(int sourceSize,int zoomLevel)
    {
        if (zoomLevel == 0)
              return sourceSize ;
        else
        if (zoomLevel < 0)
            return sourceSize / (Math.abs(zoomLevel) + 1) ;
        else
            return sourceSize * (zoomLevel + 1) ;
      }  

	/**
	 * 缩放图像（按高度和宽度缩放）
	 * 
	 * @param srcImageFile
	 *            源图像文件地址
	 * @param result
	 *            缩放后的图像地址
	 * @param height
	 *            缩放后的高度
	 * @param width
	 *            缩放后的宽度
	 * @param bb
	 *            比例不对时是否需要补白：true为补白; false为不补白;
	 */
	public final static void scaleByWH(String srcImageFile, String result,
			int height, int width, boolean bb) {
		try {
			double ratio = 0.0; // 缩放比例
			File f = new File(srcImageFile);
			BufferedImage bi = ImageIO.read(f);
			Image itemp = bi.getScaledInstance(width, height, bi.SCALE_SMOOTH);
			// 计算比例
			if ((bi.getHeight() > height) || (bi.getWidth() > width)) {
				if (bi.getHeight() > bi.getWidth()) {
					ratio = (new Integer(height)).doubleValue()
							/ bi.getHeight();
				} else {
					ratio = (new Integer(width)).doubleValue() / bi.getWidth();
				}
				AffineTransformOp op = new AffineTransformOp(
						AffineTransform.getScaleInstance(ratio, ratio), null);
				itemp = op.filter(bi, null);
			}
			if (bb) {// 补白
				BufferedImage image = new BufferedImage(width, height,
						BufferedImage.TYPE_INT_RGB);
				Graphics2D g = image.createGraphics();
				g.setColor(Color.white);
				g.fillRect(0, 0, width, height);
				if (width == itemp.getWidth(null))
					g.drawImage(itemp, 0, (height - itemp.getHeight(null)) / 2,
							itemp.getWidth(null), itemp.getHeight(null),
							Color.white, null);
				else
					g.drawImage(itemp, (width - itemp.getWidth(null)) / 2, 0,
							itemp.getWidth(null), itemp.getHeight(null),
							Color.white, null);
				g.dispose();
				itemp = image;
			}
			ImageIO.write((BufferedImage) itemp, "JPEG", new File(result));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 缩放图像（按高度和宽度缩放）
	 * <p>
	 * 方法说明:
	 * </p>
	 * 
	 * @auther DuanYong
	 * @since 2012-12-11 下午3:26:58
	 * @param srcImageFile
	 *            源图像文件地址
	 * @param destWidth
	 *            缩放后的高度
	 * @param destHeight
	 *            缩放后的宽度
	 * @param equalRate
	 *            等比例       
	 * @return BufferedImage 图片
	 */
	public final static BufferedImage scaleByWH(String srcImageFile,
			int destWidth, int destHeight, boolean equalRate) {
		try {
			BufferedImage bi = getBufferedImageByImagePath(srcImageFile);
			//Image itemp = bi.getScaledInstance(destWidth, destHeight,bi.SCALE_SMOOTH);
			// 计算比例
			if(equalRate){
				if ( bi.getWidth() >= bi.getHeight()) {  
					destHeight = (int)Math.round((bi.getHeight() * destWidth * 1.0 / bi.getWidth()));  
	            }else{   
	            	destWidth = (int)Math.round((bi.getWidth() * destHeight * 1.0 / bi.getHeight()));  
	            }
			}
			//构建图片对象  
            BufferedImage image = new BufferedImage(destWidth, destHeight, BufferedImage.TYPE_INT_RGB); 
            //绘制缩小后的图  
            image.getGraphics().drawImage(bi, 0, 0, destWidth, destHeight, null);   
            image.getGraphics().dispose();
			return image;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	

	/**
	 * 图像切割(按指定起点坐标和宽高切割)
	 * 
	 * @param srcImageFile
	 *            源图像地址
	 * @param result
	 *            切片后的图像地址
	 * @param x
	 *            目标切片起点坐标X
	 * @param y
	 *            目标切片起点坐标Y
	 * @param width
	 *            目标切片宽度
	 * @param height
	 *            目标切片高度
	 */
	public final static void cutByXYWH(String srcImageFile, String result,
			int x, int y, int width, int height) {
		try {
			// 读取源图像
			BufferedImage bi = ImageIO.read(new File(srcImageFile));
			int srcWidth = bi.getHeight(); // 源图宽度
			int srcHeight = bi.getWidth(); // 源图高度
			if (srcWidth > 0 && srcHeight > 0) {
				Image image = bi.getScaledInstance(srcWidth, srcHeight,
						Image.SCALE_DEFAULT);
				// 四个参数分别为图像起点坐标和宽高
				// 即: CropImageFilter(int x,int y,int width,int height)
				ImageFilter cropFilter = new CropImageFilter(x, y, width,
						height);
				Image img = Toolkit.getDefaultToolkit().createImage(
						new FilteredImageSource(image.getSource(), cropFilter));
				BufferedImage tag = new BufferedImage(width, height,
						BufferedImage.TYPE_INT_RGB);
				
				
				Graphics g = tag.getGraphics();
				g.drawImage(img, 0, 0, width, height, null); // 绘制切割后的图
				g.dispose();
				// 输出为文件
				ImageIO.write(tag, "JPEG", new File(result));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 图像切割(按指定起点坐标和宽高切割)
	 * 
	 * @param bi
	 *            源图像
	 * @param result
	 *            切片后的图像地址
	 * @param x
	 *            目标切片起点坐标X
	 * @param y
	 *            目标切片起点坐标Y
	 * @param width
	 *            目标切片宽度
	 * @param height
	 *            目标切片高度
	 */
	public final static BufferedImage cutByXYWH(BufferedImage bi,
			int x, int y, int width, int height) {
		try {
			// 读取
			int srcWidth = bi.getWidth(); // 源图宽度
			int srcHeight = bi.getHeight(); // 源图高度
			if (srcWidth > 0 && srcHeight > 0) {
				Image image = bi.getScaledInstance(srcWidth, srcHeight,
						Image.SCALE_DEFAULT);
				if(x > srcWidth || x + width > srcWidth){
					x = 0;
					width = srcWidth;
				}
				if(y > srcHeight || y + height > srcHeight){
					y = 0;
					height = srcHeight;
				}
				// 四个参数分别为图像起点坐标和宽高
				// 即: CropImageFilter(int x,int y,int width,int height)
				ImageFilter cropFilter = new CropImageFilter(x, y, width,
						height);
				Image img = Toolkit.getDefaultToolkit().createImage(
						new FilteredImageSource(image.getSource(), cropFilter));
				BufferedImage tag = new BufferedImage(width, height,
						BufferedImage.TYPE_INT_RGB);
				Graphics g = tag.getGraphics();
				g.drawImage(img, 0, 0, width, height, null); // 绘制切割后的图
				g.dispose();
				// 输出为文件
				return tag;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 图像切割（指定切片的行数和列数）
	 * 
	 * @param srcImageFile
	 *            源图像地址
	 * @param descDir
	 *            切片目标文件夹
	 * @param rows
	 *            目标切片行数。默认2，必须是范围 [1, 20] 之内
	 * @param cols
	 *            目标切片列数。默认2，必须是范围 [1, 20] 之内
	 */
	public final static void cutByRowCol(String srcImageFile, String descDir,
			int rows, int cols) {
		try {
			if (rows <= 0 || rows > 20)
				rows = 2; // 切片行数
			if (cols <= 0 || cols > 20)
				cols = 2; // 切片列数
			// 读取源图像
			BufferedImage bi = ImageIO.read(new File(srcImageFile));
			int srcWidth = bi.getHeight(); // 源图宽度
			int srcHeight = bi.getWidth(); // 源图高度
			if (srcWidth > 0 && srcHeight > 0) {
				Image img;
				ImageFilter cropFilter;
				Image image = bi.getScaledInstance(srcWidth, srcHeight,
						Image.SCALE_DEFAULT);
				int destWidth = srcWidth; // 每张切片的宽度
				int destHeight = srcHeight; // 每张切片的高度
				// 计算切片的宽度和高度
				if (srcWidth % cols == 0) {
					destWidth = srcWidth / cols;
				} else {
					destWidth = (int) Math.floor(srcWidth / cols) + 1;
				}
				if (srcHeight % rows == 0) {
					destHeight = srcHeight / rows;
				} else {
					destHeight = (int) Math.floor(srcWidth / rows) + 1;
				}
				// 循环建立切片
				// 改进的想法:是否可用多线程加快切割速度
				for (int i = 0; i < rows; i++) {
					for (int j = 0; j < cols; j++) {
						// 四个参数分别为图像起点坐标和宽高
						// 即: CropImageFilter(int x,int y,int width,int height)
						cropFilter = new CropImageFilter(j * destWidth, i
								* destHeight, destWidth, destHeight);
						img = Toolkit.getDefaultToolkit().createImage(
								new FilteredImageSource(image.getSource(),
										cropFilter));
						BufferedImage tag = new BufferedImage(destWidth,
								destHeight, BufferedImage.TYPE_INT_RGB);
						Graphics g = tag.getGraphics();
						g.drawImage(img, 0, 0, null); // 绘制缩小后的图
						g.dispose();
						// 输出为文件
						ImageIO.write(tag, "JPEG", new File(descDir + "_r" + i
								+ "_c" + j + ".jpg"));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 图像切割（指定切片的宽度和高度）
	 * 
	 * @param srcImageFile
	 *            源图像地址
	 * @param descDir
	 *            切片目标文件夹
	 * @param destWidth
	 *            目标切片宽度。默认200
	 * @param destHeight
	 *            目标切片高度。默认150
	 */
	public final static void cutByWH(String srcImageFile, String descDir,
			int destWidth, int destHeight) {
		try {
			if (destWidth <= 0)
				destWidth = 200; // 切片宽度
			if (destHeight <= 0)
				destHeight = 150; // 切片高度
			// 读取源图像
			BufferedImage bi = ImageIO.read(new File(srcImageFile));
			int srcWidth = bi.getHeight(); // 源图宽度
			int srcHeight = bi.getWidth(); // 源图高度
			if (srcWidth > destWidth && srcHeight > destHeight) {
				Image img;
				ImageFilter cropFilter;
				Image image = bi.getScaledInstance(srcWidth, srcHeight,
						Image.SCALE_DEFAULT);
				int cols = 0; // 切片横向数量
				int rows = 0; // 切片纵向数量
				// 计算切片的横向和纵向数量
				if (srcWidth % destWidth == 0) {
					cols = srcWidth / destWidth;
				} else {
					cols = (int) Math.floor(srcWidth / destWidth) + 1;
				}
				if (srcHeight % destHeight == 0) {
					rows = srcHeight / destHeight;
				} else {
					rows = (int) Math.floor(srcHeight / destHeight) + 1;
				}
				// 循环建立切片
				// 改进的想法:是否可用多线程加快切割速度
				for (int i = 0; i < rows; i++) {
					for (int j = 0; j < cols; j++) {
						// 四个参数分别为图像起点坐标和宽高
						// 即: CropImageFilter(int x,int y,int width,int height)
						cropFilter = new CropImageFilter(j * destWidth, i
								* destHeight, destWidth, destHeight);
						img = Toolkit.getDefaultToolkit().createImage(
								new FilteredImageSource(image.getSource(),
										cropFilter));
						BufferedImage tag = new BufferedImage(destWidth,
								destHeight, BufferedImage.TYPE_INT_RGB);
						Graphics g = tag.getGraphics();
						g.drawImage(img, 0, 0, null); // 绘制缩小后的图
						g.dispose();
						// 输出为文件
						ImageIO.write(tag, "JPEG", new File(descDir + "_r" + i
								+ "_c" + j + ".jpg"));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 图像类型转换：GIF->JPG、GIF->PNG、PNG->JPG、PNG->GIF(X)、BMP->PNG
	 * 
	 * @param srcImageFile
	 *            源图像地址
	 * @param formatName
	 *            包含格式非正式名称的 String：如JPG、JPEG、GIF等
	 * @param destImageFile
	 *            目标图像地址
	 */
	public final static void convert(String srcImageFile, String formatName,
			String destImageFile) {
		try {
			File f = new File(srcImageFile);
			f.canRead();
			f.canWrite();
			BufferedImage src = ImageIO.read(f);
			ImageIO.write(src, formatName, new File(destImageFile));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 彩色转为黑白
	 * 
	 * @param srcImageFile
	 *            源图像地址
	 * @param destImageFile
	 *            目标图像地址
	 */
	public final static void gray(String srcImageFile, String destImageFile) {
		try {
			BufferedImage src = ImageIO.read(new File(srcImageFile));
			ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
			ColorConvertOp op = new ColorConvertOp(cs, null);
			src = op.filter(src, null);
			ImageIO.write(src, "JPEG", new File(destImageFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * 给图片添加文字水印
	 * 
	 * @param pressText
	 *            水印文字
	 * @param srcImageFile
	 *            源图像地址
	 * @param destImageFile
	 *            目标图像地址
	 * @param fontName
	 *            水印的字体名称
	 * @param fontStyle
	 *            水印的字体样式
	 * @param color
	 *            水印的字体颜色
	 * @param fontSize
	 *            水印的字体大小
	 * @param x
	 *            修正值
	 * @param y
	 *            修正值
	 * @param alpha
	 *            透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
	 */
	public final static void pressText(String pressText, String srcImageFile,
			String destImageFile, String fontName, int fontStyle, Color color,
			int fontSize, int x, int y, float alpha) {
		try {
			File img = new File(srcImageFile);
			Image src = ImageIO.read(img);
			int width = src.getWidth(null);
			int height = src.getHeight(null);
			BufferedImage image = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D g = image.createGraphics();
			g.drawImage(src, 0, 0, width, height, null);
			g.setColor(color);
			g.setFont(new Font(fontName, fontStyle, fontSize));
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
					alpha));
			// 在指定坐标绘制水印文字
			g.drawString(pressText, (width - (getLength(pressText) * fontSize))
					/ 2 + x, (height - fontSize) / 2 + y);
			g.dispose();
			ImageIO.write((BufferedImage) image, "JPEG",
					new File(destImageFile));// 输出到文件流
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 给图片添加文字水印
	 * 
	 * @param pressText
	 *            水印文字
	 * @param srcImageFile
	 *            源图像地址
	 * @param destImageFile
	 *            目标图像地址
	 * @param fontName
	 *            字体名称
	 * @param fontStyle
	 *            字体样式
	 * @param color
	 *            字体颜色
	 * @param fontSize
	 *            字体大小
	 * @param x
	 *            修正值
	 * @param y
	 *            修正值
	 * @param alpha
	 *            透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
	 */
	public final static void pressText2(String pressText, String srcImageFile,
			String destImageFile, String fontName, int fontStyle, Color color,
			int fontSize, int x, int y, float alpha) {
		try {
			File img = new File(srcImageFile);
			Image src = ImageIO.read(img);
			int width = src.getWidth(null);
			int height = src.getHeight(null);
			BufferedImage image = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D g = image.createGraphics();
			g.drawImage(src, 0, 0, width, height, null);
			g.setColor(color);
			g.setFont(new Font(fontName, fontStyle, fontSize));
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
					alpha));
			// 在指定坐标绘制水印文字
			g.drawString(pressText, (width - (getLength(pressText) * fontSize))
					/ 2 + x, (height - fontSize) / 2 + y);
			g.dispose();
			ImageIO.write((BufferedImage) image, "JPEG",
					new File(destImageFile));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 给图片添加文字水印
	 * 
	 * @param pressText
	 *            水印文字
	 * @param srcImageFile
	 *            源图像地址
	 * @param destImageFile
	 *            目标图像地址
	 * @param fontName
	 *            字体名称
	 * @param fontStyle
	 *            字体样式
	 * @param color
	 *            字体颜色
	 * @param fontSize
	 *            字体大小
	 * @param x
	 *            修正值
	 * @param y
	 *            修正值
	 * @param alpha
	 *            透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
	 */
	public final static BufferedImage pressText(String pressText, BufferedImage srcImage, String fontName, int fontStyle, Color color,
			int fontSize, int x, int y, float alpha) {
		try {
			int width = srcImage.getWidth(null);
			int height = srcImage.getHeight(null);
			BufferedImage image = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D g = image.createGraphics();
			g.drawImage(srcImage, 0, 0, width, height, null);
			g.setColor(color);
			g.setFont(new Font(fontName, fontStyle, fontSize));
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
					alpha));
			// 在指定坐标绘制水印文字
			g.drawString(pressText, (width - (getLength(pressText) * fontSize))
					/ 2 + x, (height - fontSize) / 2 + y);
			g.dispose();
			return image;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 给图片添加图片水印
	 * 
	 * @param pressImg
	 *            水印图片
	 * @param srcImageFile
	 *            源图像地址
	 * @param destImageFile
	 *            目标图像地址
	 * @param x
	 *            修正值。 默认在中间
	 * @param y
	 *            修正值。 默认在中间
	 * @param alpha
	 *            透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
	 */
	public final static void pressImage(String pressImg, String srcImageFile,
			String destImageFile, int x, int y, float alpha) {
		try {
			File img = new File(srcImageFile);
			Image src = ImageIO.read(img);
			int wideth = src.getWidth(null);
			int height = src.getHeight(null);
			BufferedImage image = new BufferedImage(wideth, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D g = image.createGraphics();
			g.drawImage(src, 0, 0, wideth, height, null);
			// 水印文件
			Image src_biao = ImageIO.read(new File(pressImg));
			int wideth_biao = src_biao.getWidth(null);
			int height_biao = src_biao.getHeight(null);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
					alpha));
			g.drawImage(src_biao, (wideth - wideth_biao) / 2,
					(height - height_biao) / 2, wideth_biao, height_biao, null);
			// 水印文件结束
			g.dispose();
			ImageIO.write((BufferedImage) image, "JPEG",
					new File(destImageFile));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 给图片添加图片水印
	 * 
	 * @param pressImg
	 *            水印图片
	 * @param srcImageFile
	 *            源图像地址
	 * @param destImageFile
	 *            目标图像地址
	 * @param x
	 *            修正值。 默认在中间
	 * @param y
	 *            修正值。 默认在中间
	 * @param alpha
	 *            透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
	 */
	public final static BufferedImage pressImage(BufferedImage pressImg, BufferedImage srcImage, int x, int y, float alpha) {
		try {
			int wideth = srcImage.getWidth(null);
			int height = srcImage.getHeight(null);
			BufferedImage image = new BufferedImage(wideth, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D g = image.createGraphics();
			g.drawImage(srcImage, 0, 0, wideth, height, null);
			// 水印文件
			int wideth_biao = pressImg.getWidth(null);
			int height_biao = pressImg.getHeight(null);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
					alpha));
			if(wideth_biao < wideth && height_biao < height){
				if(x > wideth || x + wideth_biao > wideth){
					x = wideth - wideth_biao;
				}
				if(y > height || y + height_biao > height){
					y = height - height_biao;
				}
				g.drawImage(pressImg, x,y, wideth_biao, height_biao, null);
				// 水印文件结束
				g.dispose();
			}
			return image;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 计算text的长度（一个中文算两个字符）
	 * 
	 * @param text
	 * @return
	 */
	public final static int getLength(String text) {
		int textLength = text.length();
		int length = textLength;
		for (int i = 0; i < textLength; i++) {
		    if (String.valueOf(text.charAt(i)).getBytes().length > 1) {
		        length++;
		    }
		}
		return (length % 2 == 0) ? length / 2 : length / 2 + 1;
	}
	/**
	 * 亮度,对比度设置
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2013-1-3 下午8:51:51
	 * @param bufferedImage
	 * @param contrast 对比度
	 * @param brightness 亮度
	 * @return BufferedImage
	 */
	public final static BufferedImage lightenessContrast(BufferedImage bufferedImage,int contrast,int brightness){
		int width = bufferedImage.getWidth();  
        int height = bufferedImage.getHeight();
        float contrastFloat = contrast / 25f;
        float brightnessFloat = brightness / 50f;
        
        int[] inPixels = new int[width*height];  
        int[] outPixels = new int[width*height];  
        bufferedImage.getRGB( 0, 0, width, height, inPixels, 0, width );  
          
        // calculate RED, GREEN, BLUE means of pixel  
        int index = 0;  
        int[] rgbmeans = new int[3];  
        double redSum = 0, greenSum = 0, blueSum = 0;  
        double total = height * width;  
        for(int row=0; row<height; row++) {  
            int ta = 0, tr = 0, tg = 0, tb = 0;  
            for(int col=0; col<width; col++) {  
                index = row * width + col;  
                ta = (inPixels[index] >> 24) & 0xff;  
                tr = (inPixels[index] >> 16) & 0xff;  
                tg = (inPixels[index] >> 8) & 0xff;  
                tb = inPixels[index] & 0xff;  
                redSum += tr;  
                greenSum += tg;  
                blueSum +=tb;  
            }  
        }  
          
        rgbmeans[0] = (int)(redSum / total);  
        rgbmeans[1] = (int)(greenSum / total);  
        rgbmeans[2] = (int)(blueSum / total);  
          
        // adjust contrast and brightness algorithm, here  
        for(int row=0; row<height; row++) {  
            int ta = 0, tr = 0, tg = 0, tb = 0;  
            for(int col=0; col<width; col++) {  
                index = row * width + col;  
                ta = (inPixels[index] >> 24) & 0xff;  
                tr = (inPixels[index] >> 16) & 0xff;  
                tg = (inPixels[index] >> 8) & 0xff;  
                tb = inPixels[index] & 0xff;  
                  
                // remove means  
                tr -=rgbmeans[0];  
                tg -=rgbmeans[1];  
                tb -=rgbmeans[2];  
                  
                // adjust contrast now !!!  
                tr = (int)(tr * contrastFloat);  
                tg = (int)(tg * contrastFloat);  
                tb = (int)(tb * contrastFloat);  
                  
                // adjust brightness  
                tr += (int)(rgbmeans[0] * brightnessFloat);  
                tg += (int)(rgbmeans[1] * brightnessFloat);  
                tb += (int)(rgbmeans[2] * brightnessFloat);  
                outPixels[index] = (ta << 24) | (clamp(tr) << 16) | (clamp(tg) << 8) | clamp(tb);  
            }  
        }  
        bufferedImage.setRGB(0, 0, width, height, outPixels, 0, width);
		return bufferedImage;
	}
	private final static int clamp(int value) {  
        return value > 255 ? 255 :(value < 0 ? 0 : value);  
    }
	/**
	 * 图片黑白
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2013-1-3 下午9:24:17
	 * @param bufferedImage
	 * @return BufferedImage
	 */
	public final static BufferedImage gray(BufferedImage bufferedImage) {
		ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
		ColorConvertOp op = new ColorConvertOp(cs, null);
		bufferedImage = op.filter(bufferedImage, null);
		return bufferedImage;
	}

	public final static BufferedImage sharpen(BufferedImage bufferedImage) {
		if(null != bufferedImage){
			float[] sharpenData = { -1.0f, -1.0f, -1.0f, -1.0f, 9.0f, -1.0f, -1.0f,-1.0f, -1.0f };
			Kernel kernel = new Kernel(3, 3, sharpenData);
			ConvolveOp imageOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null); // 创建卷积变换操作对象
			BufferedImage filteredBufImage = new BufferedImage(bufferedImage.getWidth(),bufferedImage.getHeight(),BufferedImage.TYPE_3BYTE_BGR); //过滤后的缓冲区图像
			imageOp.filter(bufferedImage, filteredBufImage);//过滤图像，目标图像在filteredBufImage
			bufferedImage = filteredBufImage;
		}
		return bufferedImage;
	}

	public final static BufferedImage blur(BufferedImage bufferedImage) {
		if (null != bufferedImage) {
			float[] sharpenData = { 0.0625f, 0.125f, 0.0625f, 0.125f, 0.025f,
					0.125f, 0.0625f, 0.125f, 0.0625f };
			Kernel kernel = new Kernel(3, 3, sharpenData);
			ConvolveOp imageOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP,null); // 创建卷积变换操作对象
			BufferedImage filteredBufImage = new BufferedImage(bufferedImage.getWidth(),bufferedImage.getHeight(),BufferedImage.TYPE_3BYTE_BGR); //过滤后的缓冲区图像
			imageOp.filter(bufferedImage, filteredBufImage);//过滤图像，目标图像在filteredBufImage
			bufferedImage = filteredBufImage;
		}
		return bufferedImage;
	}
}
