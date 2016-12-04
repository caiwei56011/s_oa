package com.jsoft.oa.core.action;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.opensymphony.xwork2.ActionSupport;
/**
 * 生成二维码控制器
 * @author jack
 * @email wujunwei6@163.com
 * @date 2016年9月1日 下午5:06:38 
 * @version V1.0
 */
public class BarcodeAction extends ActionSupport {
	private static final long serialVersionUID = 7727162071140103532L;
	/** 定义生成二维码图片的宽度 */
	private static final int WIDTH = 200;
	/** 定义生成二维码图片的高度 */
	private static final int HEIGHT = 200;
	/** 定义中间LOGO图片的宽度 */
	private static final int LOGO_WIDTH = 50;
	/** 定义中间LOGO图片的高度 */
	private static final int LOGO_HEIGHT = 50;
	/** 定义二维码图片中的内容 */
	private String url;

	@Override
	public String execute() throws Exception {
		
		/** 获取响应对象 */
		HttpServletResponse response = ServletActionContext.getResponse();
		/** 设置响应的内容类型 */
		response.setContentType("images/png");
		
		if (url == null || "".equals(url)){
			url = "http://www.baidu.com";
		}
		
		/** 定义Map集合封装生成二维码需要全局配置信息 */
		Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
		/** 设置二维码图片中的内容编码  */
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		/** 设置二维码图片的上下左右的边距  */
		hints.put(EncodeHintType.MARGIN, 1);
		/** 设置二维码图片的纠错级别为最高 */
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		
		/** 
		 * 创建二维码字节转换对象 
		 * 第一个参数：二维码图片中的内容
		 * 第二个参数：生成二维码的格式器
		 * 第三个参数：生成二维码图片的宽度
		 * 第四个参数：生成二维码图片的高度
		 * 第五个参数：生成二维码图片需要的全局配置信息
		 * */
		BitMatrix matrix = new MultiFormatWriter()
				.encode(url, BarcodeFormat.QR_CODE, WIDTH, HEIGHT, hints);
		
		/** 获取二维码图片的宽度 */
		int matrix_width = matrix.getWidth();
		/** 获取二维码图片的高度 */
		int matrix_height = matrix.getHeight();
		
		/** 创建缓冲流图片(空白) */
		BufferedImage image = new BufferedImage(matrix_width, matrix_height, BufferedImage.TYPE_INT_RGB);
		/** 把matrix中的图片转换到image图片 */
		for (int x = 0; x < matrix_width; x++){
			for (int y = 0; y < matrix_height; y++){
				/** 获取一点上面的颜色 true: 黑色  false : 白色 */
				int rgb = matrix.get(x, y) ? 0x8B008B : 0xffffff;
				image.setRGB(x, y, rgb);
			}
		}
		
		/** 读取logo */
		BufferedImage logo = ImageIO.read(new File("D:\\green3.jpg"));
		/** 获取画笔 */
		Graphics2D g = (Graphics2D)image.getGraphics();
		/** 在二维码图片中间放logo */
		g.drawImage(logo, (matrix_width - LOGO_WIDTH) / 2, (matrix_height - LOGO_HEIGHT) / 2, LOGO_WIDTH, LOGO_HEIGHT, null);
		
		/** 设置画笔颜色 */
		g.setColor(Color.WHITE);
		/** 设置画笔的粗细 */
		g.setStroke(new BasicStroke(3.0f));
		/** 设置消除锯齿 */
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		/** 绘制圆角矩形 */
		g.drawRoundRect((matrix_width - LOGO_WIDTH) / 2, (matrix_height - LOGO_HEIGHT) / 2, 
				LOGO_WIDTH, LOGO_HEIGHT, 10, 10);
		
		/** 向浏览器输出二维码 */
		//MatrixToImageWriter.writeToStream(matrix, "png", response.getOutputStream());
		ImageIO.write(image, "png", response.getOutputStream());
		return NONE;
	}

	/** setter and getter method */
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

}
