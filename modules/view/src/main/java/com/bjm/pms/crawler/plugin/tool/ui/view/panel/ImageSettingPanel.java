/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.bjm.pms.crawler.plugin.tool.ui.view.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Iterator;

import javax.annotation.Resource;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.MouseInputAdapter;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bjm.pms.crawler.core.constants.Config;
import com.bjm.pms.crawler.plugin.tool.ui.bean.ImageSettingBean;
import com.bjm.pms.crawler.view.base.constant.Constant;
import com.bjm.pms.crawler.view.base.loader.ImageLoader;
import com.bjm.pms.crawler.view.base.loader.LanguageLoader;
import com.bjm.pms.crawler.view.base.utils.DateUtil;
import com.bjm.pms.crawler.view.core.event.CowSwingEvent;
import com.bjm.pms.crawler.view.main.CowSwingMainFrame;
import com.bjm.pms.crawler.view.ui.view.panel.AbstractContentPanel;
import com.bjm.pms.crawler.view.ui.view.panel.IPage;

/**
 * 图片预处理参数设置
 * 
 * @author DuanYong
 * @since 2012-12-16下午5:34:47
 * @version 1.0
 */
@Component("imageSettingPanel")
@Scope("prototype")
public class ImageSettingPanel extends AbstractContentPanel<ImageSettingBean> implements IPage{

	private static final long serialVersionUID = 1L;
	@Resource(name="cowSwingMainFrame")
	private CowSwingMainFrame cowSwingMainFrame;
	private JColorChooser jc = new JColorChooser(); 
	/** 中部演示区*/
	private JLayeredPane centerPanel;
	/** 中部滚动panel*/
	private JScrollPane centerScrollPane;
	/**水印图片panel*/
	private WaterImagePanel dragPicpanel;
	/**水印文字panel*/
	private WaterTextPanel dragTextpanel;
	/**剪切panel*/
	private CutPanel cutPanel;
	// 案例图片panel
	private ExampleImagePanel exampleImagePanel;
	@Resource(name="imageSettingBean")
    private ImageSettingBean imageSettingBean;
	
	private ImageSettingBean temp;
	
	
	/** 按比例 */
	private JRadioButton resizeByScaleButton;
	/** 按分辨率 */
	private JRadioButton resizeByPixelButton;
	/** 大小设置group */
	private ButtonGroup resizeButtonGroup;
	
	
	
	private JLabel resizeByPixelLabelWidth;
	private JSpinner widthSpinner;
	private JLabel resizeByPixelLabelHeight;
	private JSpinner heightSpinner;
	private JCheckBox equalScaleCehckBox;
	private JLabel resizeByScaleLabel;
	private JLabel resizeByScaleSignLabel;
	private JSpinner scaleSpinner;
	
	
	private JCheckBox saveFormatCehckBox;
	private JComboBox saveFormatCombo;
	private JLabel saveFormatQualityLabel;
	private JSpinner qualitySpinner;
	private JLabel saveFormatPathLabel;
	private JTextField savePathField;
	private JButton savePathBtn;
	//旋转相关
	private JCheckBox cirCehckBox;
	private JCheckBox cirTypeHorizontalButton;
	private JCheckBox cirTypeVerticalButton;
	private JLabel cirJSpinnerLabel;
	private JSpinner cirJSpinner;
	//裁剪相关
	private JCheckBox cutCehckBox;
	private JRadioButton cutTypeManualButton;
	private JRadioButton cutTypeAutoButton;
	private JLabel cutLabelWidth;
	private JSpinner cutWidthSpinner;
	private JLabel cutLabelHeight;
	private JSpinner cutHeightSpinner;
	//水印图片
	private JCheckBox waterImageCehckBox;
	private JTextField waterImagePathField;
	private JButton waterImagePathDirBtn;
	private JCheckBox waterImageCancelBg;
	private JCheckBox waterImageDiaphaneity;
	private JSlider diaphaneityJSlider;
	//水印文字
	private JCheckBox waterTextCehckBox;
	private JButton waterTextFontBtn;
	private JButton waterTextColorBtn;
	private JCheckBox waterTextDateBox;
	private JTextArea waterTextContentArea;
	private JCheckBox waterTextBgColorCehckBox;
	private JButton textBgColorBtn;
	private JCheckBox waterTextBorderCehckBox;
	private JButton textBorderBtn;
	private JCheckBox waterTextTimeBox;
	private JCheckBox waterTextDiaphaneityBox;
	private JSlider waterDiaphaneityJSlider;
	//亮度,对比度
	private JCheckBox imageLightenessCheckBox;
	private JSlider imageLightenessJSlider;
	private JCheckBox imageContrastCheckBox;
	private JSlider imageContrastBoxJSlider;
	//其他特效
	private JCheckBox blackAndwhiteCheckBox;
	private JCheckBox sharpCheckBox;
	private JCheckBox blurCheckBox;
	private JCheckBox slickCheckBox;
	private JCheckBox negativeCheckBox;
	private JCheckBox raisedCheckBox;
	
	
	
	/** 缩放方式：true为按比例,false为按分辨率 */
	private String resizeValue = Constant.NO;
	private String equalScaleValue = Constant.YES;
	private String saveFormatValue = Constant.YES;
	private String saveFormatType =  "JPEG";
	
	private String cirCehckValue = Constant.NO;
	private String cirCehckTypeHorizontalValue = Constant.NO;
	private String cirCehckTypeVerticalValue = Constant.NO;
	
	private String cutCehckValue = Constant.NO;
	private String cutCehckTypeValue = Constant.YES;
	private int cutPanelX = 0;
	private int cutPanelY = 0;
	
	private String waterImageCehckValue = Constant.NO;
	private String waterImageCancelBgValue = Constant.NO;
	private String waterImageDiaphaneityCehckValue = Constant.NO;
	private int waterImageX = 0;
	private int waterImageY = 0;
	
	private String waterTextCehckValue = Constant.NO;
	private String waterTextValue;
	private String waterTextBgColorCehckValue = Constant.NO;
	private Color waterTextColorValue = Color.BLUE;
	private Font waterTextFontValue = new Font("宋体",Font.PLAIN, 12);
	private Color waterTextBgColor = Color.YELLOW;
	private Color waterTextBorderColor = Color.RED;
	private String waterTextBorderCehckValue = Constant.NO;
	private String waterTextDateCehckValue = Constant.NO;
	private String waterTextDateValue = "";
	private String waterTextTimeCehckValue = Constant.NO;
	private String waterTextTimeValue = "";
	private String waterTextDiaphaneityCehckValue = Constant.NO;
	private int waterTextX = 0;
	private int waterTextY = 0;
	
	private String imageLightenessCheckValue = Constant.NO;
	private String imageContrastValue = Constant.NO;
	
	private String blackAndwhiteCheckValue = Constant.NO;
	private String sharpCheckValue = Constant.NO;
	private String blurCheckValue = Constant.NO;
	private String slickCheckValue = Constant.NO;
	private String negativeCheckValue = Constant.NO;
	private String raisedCheckValue = Constant.NO;
	
	public void init(){
		BorderLayout layout = new BorderLayout();
		this.setLayout(layout);
		
		if (getTopPane() != null) {
			this.add(getTopPane(), BorderLayout.NORTH);
		}
		if (getCenterPane() != null) {
			this.add(getCenterPane(), BorderLayout.CENTER);
		}
		if (getLeftPane() != null) {
			this.add(getLeftPane(), BorderLayout.WEST);
		}
		initCmpActionListener();
	}
	
	/**
	 * 初始化监听事件
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-12-17 下午10:24:23
	 * @return void
	 */
	protected void initCmpActionListener(){
		class ResizeBtnActionAdapter implements  ActionListener{
			@Override
			public void actionPerformed(ActionEvent e) {
				 if (resizeByScaleButton.isSelected()) {
					 	resizeValue = Constant.YES;
					 	setResizeCompPixelState(false);
					 	setResizeCompScaleState(true);
	                } else if (resizeByPixelButton.isSelected()) {
	                	resizeValue = Constant.NO;
	                	setResizeCompPixelState(true);
					 	setResizeCompScaleState(false);
	                }
				 repaintImage();
			}
		}
		resizeByPixelButton.addActionListener(new ResizeBtnActionAdapter());
		resizeByScaleButton.addActionListener(new ResizeBtnActionAdapter());
		
		class SpinnerChangeListener implements ChangeListener{
			@Override
			public void stateChanged(ChangeEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						repaintImage();
					}
				});
			}
		}
		SpinnerChangeListener spinnerChangeListener  = new SpinnerChangeListener();
		widthSpinner.addChangeListener(spinnerChangeListener);
		heightSpinner.addChangeListener(spinnerChangeListener);
		
		
		scaleSpinner.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						repaintImage();
					}
				});
			}
		});
		
		
		equalScaleCehckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(equalScaleCehckBox.isSelected()){
					equalScaleValue = Constant.YES;
				}else{
					equalScaleValue = Constant.NO;
				}
				resizeExampleImageByPixel();
			}
		});
		
		savePathBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				jfc.setDialogTitle("Open class File");
				int result = jfc.showOpenDialog(null);
				if (result == 1) {
					return; // 撤销则返回
				} else {
					File f = jfc.getSelectedFile();// f为选择到的目录
					savePathField.setText(f.getAbsolutePath());
				}
			}
		});
		
		saveFormatCehckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(saveFormatCehckBox.isSelected()){
					saveFormatValue = Constant.YES;
					setSaveFormatImageState(true);
				}else{
					saveFormatValue = Constant.NO;
					setSaveFormatImageState(false);
				}
				repaintImage();
			}
		});
		saveFormatCombo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveFormatType = saveFormatCombo.getSelectedItem().toString();
				repaintImage();
			}
		});
		
		qualitySpinner.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						repaintImage();
					}
				});
			}
		});
		
		
		
		//旋转
		cirCehckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(cirCehckBox.isSelected()){
					cirCehckValue = Constant.YES;
					setCirImageCompState(true);
				}else{
					cirCehckValue = Constant.NO;
					setCirImageCompState(false);
				}
				repaintImage();
			}
		});
		
		cirTypeHorizontalButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(cirTypeHorizontalButton.isSelected()){
					cirCehckTypeHorizontalValue = Constant.YES;
				}else{
					cirCehckTypeHorizontalValue = Constant.NO;
				}
				repaintImage();
			}
		});
		cirTypeVerticalButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(cirTypeVerticalButton.isSelected()){
					cirCehckTypeVerticalValue = Constant.YES;
				}else{
					cirCehckTypeVerticalValue = Constant.NO;
				}
				repaintImage();
			}
		});
		cirJSpinner.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						repaintImage();
					}
				});
			}
		});
		
		//裁剪
		cutCehckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(cutCehckBox.isSelected()){
					cutCehckValue = Constant.YES;
					setCutImageCompState(true);
				}else{
					cutCehckValue = Constant.NO;
					setCutImageCompState(false);
				}
				loadCutPanel();
			}
		});
		
		cutWidthSpinner.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						loadCutPanel();
					}
				});
			}
		});
		cutHeightSpinner.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						loadCutPanel();
					}
				});
			}
		});
		
		class CutCehckTypeActionAdapter implements  ActionListener{
			@Override
			public void actionPerformed(ActionEvent e) {
				 if (cutTypeManualButton.isSelected()) {
					 	cutCehckTypeValue = Constant.YES;
	                } else if (cutTypeAutoButton.isSelected()) {
	                	cutCehckTypeValue = Constant.NO;
	                }
			}
		}
		cutTypeManualButton.addActionListener(new CutCehckTypeActionAdapter());
		cutTypeAutoButton.addActionListener(new CutCehckTypeActionAdapter());
		//水印图片
		waterImageCehckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(waterImageCehckBox.isSelected()){
					waterImageCehckValue = Constant.YES;
					setWaterImageCompState(true);
				}else{
					waterImageCehckValue = Constant.NO;
					setWaterImageCompState(false);
				}
				loadWaterImage();
			}
		});
		waterImagePathDirBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				jfc.setDialogTitle(LanguageLoader.getString("ToolImage.imageSetWaterImagePathTitle"));
				int result = jfc.showOpenDialog(null);
				if (result == 1) {
					return; // 撤销则返回
				} else {
					File f = jfc.getSelectedFile();// f为选择到的目录
					waterImagePathField.setText(f.getAbsolutePath());
					loadWaterImage();
				}
			}
		});
		waterImageCancelBg.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(waterImageCancelBg.isSelected()){
					waterImageCancelBgValue = Constant.YES;
				}else{
					waterImageCancelBgValue = Constant.NO;
				}
				loadWaterImage();
			}
		});
		waterImageDiaphaneity.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(waterImageDiaphaneity.isSelected()){
					waterImageDiaphaneityCehckValue = Constant.YES;
					diaphaneityJSlider.setEnabled(true);
				}else{
					waterImageDiaphaneityCehckValue = Constant.NO;
					diaphaneityJSlider.setEnabled(false);
				}
				loadWaterImage();
			}
		});
		diaphaneityJSlider.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						loadWaterImage();
					}
				});
			}
		});
		//水印文字
		waterTextCehckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(waterTextCehckBox.isSelected()){
					waterTextCehckValue = Constant.YES;
					setWaterTextCompState(true);
				}else{
					waterTextCehckValue = Constant.NO;
					setWaterTextCompState(false);
				}
				loadWaterText();
			}
		});
		waterTextFontBtn.addActionListener(new ActionListener() {  
            public void actionPerformed(ActionEvent e) {  
                // 构造字体选择器，参数字体为预设值  
                FontChooser fontChooser = new FontChooser(waterTextFontValue);  
                // 打开一个字体选择器窗口，参数为父级所有者窗体。返回一个整型，代表设置字体时按下了确定或是取消，可参考MQFontChooser.APPROVE_OPTION和MQFontChooser.CANCEL_OPTION  
                int returnValue = fontChooser.showFontDialog(cowSwingMainFrame);  
                // 如果按下的是确定按钮  
                if (returnValue == FontChooser.APPROVE_OPTION) {  
                    // 获取选择的字体   
                    waterTextFontValue = fontChooser.getSelectFont();  
                }  
                loadWaterText();
            }  
        });  
		waterTextColorBtn.addActionListener(new ActionListener() {  
            public void actionPerformed(ActionEvent e) {  
            	waterTextColorValue = jc.showDialog(cowSwingMainFrame, "请选择字体颜色",waterTextColorValue);
                loadWaterText();
            }  
        });  
		textBgColorBtn.addActionListener(new ActionListener() {  
            public void actionPerformed(ActionEvent e) {  
            	waterTextBgColor = jc.showDialog(cowSwingMainFrame, "请选择字体的背景色",waterTextBgColor);
                loadWaterText();
            }  
        });  
		textBorderBtn.addActionListener(new ActionListener() {  
            public void actionPerformed(ActionEvent e) {  
            	waterTextBorderColor = jc.showDialog(cowSwingMainFrame, "请选择字体边框颜色",waterTextBorderColor);
                loadWaterText();
            }  
        });  
		waterTextContentArea.addCaretListener(new CaretListener(){
			@Override
			public void caretUpdate(CaretEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						waterTextValue = waterTextContentArea.getText();
						loadWaterText();
					}
				});
			}
		});
		waterTextBgColorCehckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(waterTextBgColorCehckBox.isSelected()){
					waterTextBgColorCehckValue = Constant.YES;
					textBgColorBtn.setEnabled(true);
				}else{
					waterTextBgColorCehckValue = Constant.NO;
					textBgColorBtn.setEnabled(false);
				}
				loadWaterText();
			}
		});
		waterTextBorderCehckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(waterTextBorderCehckBox.isSelected()){
					waterTextBorderCehckValue = Constant.YES;
					textBorderBtn.setEnabled(true);
				}else{
					waterTextBorderCehckValue = Constant.NO;
					textBorderBtn.setEnabled(false);
				}
				loadWaterText();
			}
		});
		waterTextDateBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(waterTextDateBox.isSelected()){
					waterTextDateValue = DateUtil.dateToStr(new Date(), "yyyy-MM-dd");
					waterTextDateCehckValue = Constant.YES;
				}else{
					waterTextDateValue =  "";
					waterTextDateCehckValue = Constant.NO;
				}
				loadWaterText();
			}
		});
		waterTextTimeBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(waterTextTimeBox.isSelected()){
					waterTextTimeValue =  DateUtil.dateToStr(new Date(), "HH:mm");
					waterTextTimeCehckValue = Constant.YES;
				}else{
					waterTextTimeValue = "";
					waterTextTimeCehckValue = Constant.NO;
				}
				loadWaterText();
			}
		});
		waterTextDiaphaneityBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(waterTextDiaphaneityBox.isSelected()){
					waterTextDiaphaneityCehckValue = Constant.YES;
					waterDiaphaneityJSlider.setEnabled(true);
				}else{
					waterTextDiaphaneityCehckValue = Constant.NO;
					waterDiaphaneityJSlider.setEnabled(false);
				}
				loadWaterText();
			}
		});
		
		waterDiaphaneityJSlider.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						loadWaterText();
					}
				});
			}
		});
		
		//亮度
		imageLightenessCheckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(imageLightenessCheckBox.isSelected()){
					imageLightenessCheckValue = Constant.YES;
					setImageLightenessCompState(true);
				}else{
					imageLightenessCheckValue = Constant.NO;
					setImageLightenessCompState(false);
				}
				repaintImage();
			}
		});
		imageLightenessJSlider.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						repaintImage();
					}
				});
			}
		});
		//对比度
		imageContrastCheckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(imageContrastCheckBox.isSelected()){
					imageContrastValue = Constant.YES;
					setImageContrastCompState(true);
				}else{
					imageContrastValue = Constant.NO;
					setImageContrastCompState(false);
				}
				repaintImage();
			}
		});
		imageContrastBoxJSlider.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						repaintImage();
					}
				});
			}
		});
		//其他特效
		blackAndwhiteCheckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(blackAndwhiteCheckBox.isSelected()){
					blackAndwhiteCheckValue = Constant.YES;
				}else{
					blackAndwhiteCheckValue = Constant.NO;
				}
				repaintImage();
			}
		});
		sharpCheckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(sharpCheckBox.isSelected()){
					sharpCheckValue = Constant.YES;
				}else{
					sharpCheckValue = Constant.NO;
				}
				repaintImage();
			}
		});
		blurCheckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(blurCheckBox.isSelected()){
					blurCheckValue = Constant.YES;
				}else{
					blurCheckValue = Constant.NO;
				}
				repaintImage();
			}
		});
		slickCheckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(slickCheckBox.isSelected()){
					slickCheckValue = Constant.YES;
				}else{
					slickCheckValue = Constant.NO;
				}
				repaintImage();
			}
		});
		negativeCheckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(negativeCheckBox.isSelected()){
					negativeCheckValue = Constant.YES;
				}else{
					negativeCheckValue = Constant.NO;
				}
				repaintImage();
			}
		});
		raisedCheckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(raisedCheckBox.isSelected()){
					raisedCheckValue = Constant.YES;
				}else{
					raisedCheckValue = Constant.NO;
				}
				repaintImage();
			}
		});
		
		// 鼠标事件处理
		class DragPicListener extends MouseInputAdapter {
			/** 坐标点 */
			Point point = new Point(0, 0);

            int oldX = 0;
            int oldY = 0;
			/**
			 * 当鼠标拖动时触发该事件。 记录下鼠标按下(开始拖动)的位置。
			 */
			public void mouseDragged(MouseEvent e) {
				if(oldX == 0){
					oldX = exampleImagePanel.getWidth() - dragPicpanel.getWidth();
				}
				if(oldY == 0){
					oldY = exampleImagePanel.getHeight() - dragPicpanel.getHeight();
				}
				// 转换坐标系统
				Point newPoint = SwingUtilities.convertPoint(dragPicpanel, e
						.getPoint(), dragPicpanel.getParent());
				// 设置标签的新位置
				int x = dragPicpanel.getX() + (newPoint.x - point.x);
				int y = dragPicpanel.getY() + (newPoint.y - point.y);
				if(x > oldX){
					x = oldX;
				}else if(x < 0){
					x = 0;
				}
				if(y > oldY){
					y = oldY;
				}else if(y < 0){
					y = 0;
				}
				dragPicpanel.setLocation(x, y);
				waterImageX = x;
				waterImageY = y;
				loadWaterImage();
				// 更改坐标点
				point = newPoint;
			}

			/**
			 * 当鼠标按下时触发该事件。 记录下鼠标按下(开始拖动)的位置。
			 */
			public void mousePressed(MouseEvent e) {
				// 得到当前坐标点
				point = SwingUtilities.convertPoint(dragPicpanel, e.getPoint(),
						dragPicpanel.getParent());
			}
		}
		// 增加标签的事件处理
		DragPicListener listener = new DragPicListener();
		dragPicpanel.addMouseListener(listener);
	    dragPicpanel.addMouseMotionListener(listener);
		
	    
	 // 鼠标事件处理
	 		class DragTextListener extends MouseInputAdapter {
	 			/** 坐标点 */
	 			Point point = new Point(0, 0);

	             int oldX = 0;
	             int oldY = 0;
	 			/**
	 			 * 当鼠标拖动时触发该事件。 记录下鼠标按下(开始拖动)的位置。
	 			 */
	 			public void mouseDragged(MouseEvent e) {
	 				oldX = exampleImagePanel.getWidth() - dragTextpanel.getWidth();
	 				oldY = exampleImagePanel.getHeight() - dragTextpanel.getHeight();
	 				// 转换坐标系统
	 				Point newPoint = SwingUtilities.convertPoint(dragTextpanel, e.getPoint(), dragTextpanel.getParent());
	 				
	 				// 设置标签的新位置
	 				int x = dragTextpanel.getX() + (newPoint.x - point.x);
	 				int y = dragTextpanel.getY() + (newPoint.y - point.y);
	 				if(x > oldX){
	 					x = oldX;
	 				}else if(x < 0){
	 					x = 0;
	 				}
	 				if(y > oldY){
	 					y = oldY;
	 				}else if(y < 0){
	 					y = 0;
	 				}
	 				
	 				dragTextpanel.setLocation(x, y);
	 				waterTextX = x;
	 				waterTextY = y;
	 				loadWaterText();
	 				// 更改坐标点
	 				point = newPoint;
	 			}

	 			/**
	 			 * 当鼠标按下时触发该事件。 记录下鼠标按下(开始拖动)的位置。
	 			 */
	 			public void mousePressed(MouseEvent e) {
	 				// 得到当前坐标点
	 				point = SwingUtilities.convertPoint(dragTextpanel, e.getPoint(),
	 						dragTextpanel.getParent());
	 			}
	 		}
	 		// 增加标签的事件处理
	 		DragTextListener textListener = new DragTextListener();
	 		dragTextpanel.addMouseListener(textListener);
	 		dragTextpanel.addMouseMotionListener(textListener);
	 		
	 		
	 	// 鼠标事件处理
	 		class DragCutPanelListener extends MouseInputAdapter {
	 			/** 坐标点 */
	 			Point point = new Point(0, 0);

	             int oldX = 0;
	             int oldY = 0;
	 			/**
	 			 * 当鼠标拖动时触发该事件。 记录下鼠标按下(开始拖动)的位置。
	 			 */
	 			public void mouseDragged(MouseEvent e) {
	 				oldX = exampleImagePanel.getWidth() - cutPanel.getWidth();
	 				oldY = exampleImagePanel.getHeight() - cutPanel.getHeight();
	 				// 转换坐标系统
	 				Point newPoint = SwingUtilities.convertPoint(cutPanel, e.getPoint(), dragTextpanel.getParent());
	 				
	 				// 设置标签的新位置
	 				int x = cutPanel.getX() + (newPoint.x - point.x);
	 				int y = cutPanel.getY() + (newPoint.y - point.y);
	 				if(x > oldX){
	 					x = oldX;
	 				}else if(x < 0){
	 					x = 0;
	 				}
	 				if(y > oldY){
	 					y = oldY;
	 				}else if(y < 0){
	 					y = 0;
	 				}
	 				
	 				cutPanel.setLocation(x, y);
	 				cutPanelX = x;
	 				cutPanelY = y;
	 				loadWaterText();
	 				// 更改坐标点
	 				point = newPoint;
	 			}

	 			/**
	 			 * 当鼠标按下时触发该事件。 记录下鼠标按下(开始拖动)的位置。
	 			 */
	 			public void mousePressed(MouseEvent e) {
	 				// 得到当前坐标点
	 				point = SwingUtilities.convertPoint(cutPanel, e.getPoint(),
	 						cutPanel.getParent());
	 			}
	 		}
	 		// 增加标签的事件处理
	 		DragCutPanelListener cutListener = new DragCutPanelListener();
	 		cutPanel.addMouseListener(cutListener);
	 		cutPanel.addMouseMotionListener(cutListener);
	 		
	}

	/**
	 * <p>
	 * 方法说明:
	 * </p>
	 * 
	 * @auther DuanYong
	 * @since 2012-12-16 下午5:40:30
	 * @return Component
	 */
	public JComponent getLeftPane() {
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		leftPanel.setBorder(new EtchedBorder());

		Box containerBox = new Box(BoxLayout.Y_AXIS);
		//旋转区域
		Box leftOneBox = new Box(BoxLayout.X_AXIS);
		//BorderFactory.createTitledBorder(new EtchedBorder(), LanguageLoader.getString("ToolImage.imageSetLeftCirCheckBox"));
		leftOneBox.setBorder(new EtchedBorder());
		
		cirCehckBox = new JCheckBox(LanguageLoader.getString("ToolImage.imageSetLeftCirCheckBox"));
		leftOneBox.add(cirCehckBox);

		cirTypeHorizontalButton = new JCheckBox(LanguageLoader.getString("ToolImage.imageSetLeftCirButtonHorizontal"));
		leftOneBox.add(cirTypeHorizontalButton);
		cirTypeVerticalButton = new JCheckBox(LanguageLoader.getString("ToolImage.imageSetLeftCirButtonVertical"));
		leftOneBox.add(cirTypeVerticalButton);

		cirJSpinnerLabel = new JLabel(LanguageLoader.getString("ToolImage.imageSetCirJSpinnerLabel"));
		leftOneBox.add(cirJSpinnerLabel); 
		
		// 起始宽度,最小值,最大值,增量
		SpinnerNumberModel cirModel = new SpinnerNumberModel(0, 0, 360, 45);
		cirJSpinner = new JSpinner(cirModel);
		cirJSpinner.setPreferredSize(new Dimension(75, 15));
		leftOneBox.add(cirJSpinner);

		containerBox.add(leftOneBox);
        //裁剪区域
		Box leftTwoBox = new Box(BoxLayout.X_AXIS);
		leftTwoBox.setBorder(new EtchedBorder());
		
		
		cutPanel = new CutPanel();
		//设置背景透明
		cutPanel.setOpaque(false);
		cutPanel.setBorder(BorderFactory.createLineBorder(Color.red));
		//cutPanel.setBounds(0, 0, 100, 100);
		centerPanel.add(cutPanel,JLayeredPane.DRAG_LAYER);

		// leftTwoBox.add(Box.createHorizontalStrut(50));
		cutCehckBox = new JCheckBox(LanguageLoader.getString("ToolImage.imageSetLeftCutCehckBox"));
		leftTwoBox.add(cutCehckBox);

		cutTypeManualButton = new JRadioButton(LanguageLoader.getString("ToolImage.imageSetLeftCutButtonManual"));
		leftTwoBox.add(cutTypeManualButton);
		cutTypeAutoButton = new JRadioButton(LanguageLoader.getString("ToolImage.imageSetLeftCutButtonAuto"));
		leftTwoBox.add(cutTypeAutoButton);
		ButtonGroup cutButtonGroup = new ButtonGroup();
		cutButtonGroup.add(cutTypeManualButton);
		cutButtonGroup.add(cutTypeAutoButton);

		cutLabelWidth = new JLabel(LanguageLoader.getString("ToolImage.imageSetLeftCutLabelWidth"));
		leftTwoBox.add(cutLabelWidth);

		// 起始宽度,最小值,最大值,增量
		SpinnerNumberModel widthModel = new SpinnerNumberModel(100, 0, 1000, 1);
		cutWidthSpinner = new JSpinner(widthModel);
		cutWidthSpinner.setPreferredSize(new Dimension(55, 15));
		leftTwoBox.add(cutWidthSpinner);
		cutLabelHeight = new JLabel(LanguageLoader.getString("ToolImage.imageSetLeftCutLabelHeight"));
		leftTwoBox.add(cutLabelHeight);

		// 起始宽度,最小值,最大值,增量
		SpinnerNumberModel heightModel = new SpinnerNumberModel(100, 0, 1000, 1);
		cutHeightSpinner = new JSpinner(heightModel);
		cutHeightSpinner.setPreferredSize(new Dimension(55, 15));
		leftTwoBox.add(cutHeightSpinner);

		containerBox.add(leftTwoBox);
		
		
		// 水印图片设置
		Box leftThreeBox = new Box(BoxLayout.Y_AXIS);
		leftThreeBox.setBorder(new EtchedBorder());

		Box leftThreeWaterImageBox = new Box(BoxLayout.X_AXIS);
		
		waterImageCehckBox = new JCheckBox(LanguageLoader.getString("ToolImage.imageSetWaterImageCehckBox"));
		leftThreeWaterImageBox.add(waterImageCehckBox);

		waterImagePathField = new JTextField();
		leftThreeWaterImageBox.add(waterImagePathField);

		waterImagePathDirBtn = new JButton(LanguageLoader.getString("ToolImage.imageSetSaveFormatPathBtn"),ImageLoader.getImageIcon("CrawlerResource.toolImageBrowse"));
		leftThreeWaterImageBox.add(waterImagePathDirBtn);

        //水印图片
		dragPicpanel = new WaterImagePanel();
		//设置背景透明
		dragPicpanel.setOpaque(false);
		centerPanel.add(dragPicpanel,JLayeredPane.MODAL_LAYER);

		leftThreeBox.add(leftThreeWaterImageBox);

		Box leftThreeItemBox = new Box(BoxLayout.X_AXIS);

		waterImageCancelBg = new JCheckBox(LanguageLoader.getString("ToolImage.imageSetWaterImageCancelBg"));
		leftThreeItemBox.add(waterImageCancelBg);

		waterImageDiaphaneity = new JCheckBox(LanguageLoader.getString("ToolImage.imageSetWaterImageDiaphaneity"));
		leftThreeItemBox.add(waterImageDiaphaneity);

		diaphaneityJSlider = new JSlider();
		diaphaneityJSlider.setOrientation(JSlider.HORIZONTAL);// 设置方向
		diaphaneityJSlider.setMinimum(0);// 设置最小值
		diaphaneityJSlider.setMaximum(100);// 设置最大值
		diaphaneityJSlider.setMajorTickSpacing(20);// 设置主标号间隔
		diaphaneityJSlider.setMinorTickSpacing(5);// 设置辅标号间隔
		diaphaneityJSlider.setPaintLabels(true);// Default:false显示标签
		diaphaneityJSlider.setPaintTicks(true);// Default:false显示标号
		diaphaneityJSlider.setPaintTrack(true);// Determines whether the track
												// is painted on
		// the slider
		diaphaneityJSlider.setValue(100);// 设置初始值

		leftThreeItemBox.add(diaphaneityJSlider);

		leftThreeBox.add(leftThreeItemBox);
		containerBox.add(leftThreeBox);

		// 水印文字设置
		Box leftFourBox = new Box(BoxLayout.Y_AXIS);
		leftFourBox.setBorder(new EtchedBorder());
		// 字体,颜色
		Box leftFourWaterTextBox = new Box(BoxLayout.X_AXIS);
		waterTextCehckBox = new JCheckBox(LanguageLoader.getString("ToolImage.imageSetWaterTextCehckBox"));
		leftFourWaterTextBox.add(waterTextCehckBox);
		// 字体
		waterTextFontBtn = new JButton(LanguageLoader.getString("ToolImage.imageSetWaterTextFontBtn"));
		leftFourWaterTextBox.add(waterTextFontBtn);
		// 颜色
		waterTextColorBtn = new JButton(LanguageLoader.getString("ToolImage.imageSetWaterTextColorBtn"));
		leftFourWaterTextBox.add(waterTextColorBtn);
		// 日期
		waterTextDateBox = new JCheckBox(LanguageLoader.getString("ToolImage.imageSetWaterTextDateBox"));
		leftFourWaterTextBox.add(waterTextDateBox);

		leftFourBox.add(leftFourWaterTextBox);
		// 文字内容
		Box leftFourWaterTextContentBox = new Box(BoxLayout.X_AXIS);
		waterTextContentArea = new JTextArea();
		waterTextContentArea.setRows(3);
		leftFourWaterTextContentBox.add(waterTextContentArea);

		leftFourBox.add(leftFourWaterTextContentBox);
		
		//水印文字
		dragTextpanel = new WaterTextPanel();
		//设置背景透明
		dragTextpanel.setOpaque(false);
		centerPanel.add(dragTextpanel,JLayeredPane.POPUP_LAYER);

		Box leftFourWaterTextBgColorBox = new Box(BoxLayout.X_AXIS);
		// 背景,背景颜色
		waterTextBgColorCehckBox = new JCheckBox(LanguageLoader.getString("ToolImage.imageSetWaterTextBgColorBox"));
		leftFourWaterTextBgColorBox.add(waterTextBgColorCehckBox);
		textBgColorBtn = new JButton(LanguageLoader.getString("ToolImage.imageSetWaterTextBgColorBtn"));
		leftFourWaterTextBgColorBox.add(textBgColorBtn);
		// 边框,边框颜色
		waterTextBorderCehckBox = new JCheckBox(LanguageLoader.getString("ToolImage.imageSetWaterTextBorderBox"));
		leftFourWaterTextBgColorBox.add(waterTextBorderCehckBox);
		textBorderBtn = new JButton(LanguageLoader.getString("ToolImage.imageSetWaterTextBorderBtn"));
		leftFourWaterTextBgColorBox.add(textBorderBtn);
		// 时间
		waterTextTimeBox = new JCheckBox(LanguageLoader.getString("ToolImage.imageSetWaterTextTimeBox"));
		leftFourWaterTextBgColorBox.add(waterTextTimeBox);

		leftFourBox.add(leftFourWaterTextBgColorBox);

		// 水印,透明度
		Box leftFourWaterDiaphaneityBox = new Box(BoxLayout.X_AXIS);
		waterTextDiaphaneityBox = new JCheckBox(LanguageLoader.getString("ToolImage.imageSetWaterTextDiaphaneity"));
		leftFourWaterDiaphaneityBox.add(waterTextDiaphaneityBox);

		waterDiaphaneityJSlider = new JSlider();
		waterDiaphaneityJSlider.setOrientation(JSlider.HORIZONTAL);// 设置方向
		waterDiaphaneityJSlider.setMinimum(0);// 设置最小值
		waterDiaphaneityJSlider.setMaximum(100);// 设置最大值
		waterDiaphaneityJSlider.setMajorTickSpacing(20);// 设置主标号间隔
		waterDiaphaneityJSlider.setMinorTickSpacing(5);// 设置辅标号间隔
		waterDiaphaneityJSlider.setPaintLabels(true);// Default:false显示标签
		waterDiaphaneityJSlider.setPaintTicks(true);// Default:false显示标号
		waterDiaphaneityJSlider.setPaintTrack(true);// Determines whether the
													// track is painted on
		// the slider
		waterDiaphaneityJSlider.setValue(100);// 设置初始值

		leftFourWaterDiaphaneityBox.add(waterDiaphaneityJSlider);

		leftFourBox.add(leftFourWaterDiaphaneityBox);

		containerBox.add(leftFourBox);

		// 亮度,对比度

		Box leftFiveBox = new Box(BoxLayout.Y_AXIS);
		leftFiveBox.setBorder(new EtchedBorder());

		Box leftFiveImageLightenessBox = new Box(BoxLayout.X_AXIS);
		imageLightenessCheckBox = new JCheckBox(LanguageLoader.getString("ToolImage.imageSetImageLighteness"));
		leftFiveImageLightenessBox.add(imageLightenessCheckBox);

		imageLightenessJSlider = new JSlider();
		imageLightenessJSlider.setOrientation(JSlider.HORIZONTAL);// 设置方向
		imageLightenessJSlider.setMinimum(0);// 设置最小值
		imageLightenessJSlider.setMaximum(100);// 设置最大值
		imageLightenessJSlider.setMajorTickSpacing(20);// 设置主标号间隔
		imageLightenessJSlider.setMinorTickSpacing(5);// 设置辅标号间隔
		imageLightenessJSlider.setPaintLabels(true);// Default:false显示标签
		imageLightenessJSlider.setPaintTicks(true);// Default:false显示标号
		imageLightenessJSlider.setPaintTrack(true);// Determines whether the
													// track is painted on
		// the slider
		imageLightenessJSlider.setValue(50);// 设置初始值

		leftFiveImageLightenessBox.add(imageLightenessJSlider);

		leftFiveBox.add(leftFiveImageLightenessBox);

		// 对比度
		Box leftFiveImageContrastBox = new Box(BoxLayout.X_AXIS);
		imageContrastCheckBox = new JCheckBox(LanguageLoader.getString("ToolImage.imageSetImageContrast"));
		leftFiveImageContrastBox.add(imageContrastCheckBox);

		imageContrastBoxJSlider = new JSlider();
		imageContrastBoxJSlider.setOrientation(JSlider.HORIZONTAL);// 设置方向
		imageContrastBoxJSlider.setMinimum(0);// 设置最小值
		imageContrastBoxJSlider.setMaximum(100);// 设置最大值
		imageContrastBoxJSlider.setMajorTickSpacing(20);// 设置主标号间隔
		imageContrastBoxJSlider.setMinorTickSpacing(5);// 设置辅标号间隔
		imageContrastBoxJSlider.setPaintLabels(true);// Default:false显示标签
		imageContrastBoxJSlider.setPaintTicks(true);// Default:false显示标号
		imageContrastBoxJSlider.setPaintTrack(true);// Determines whether the
													// track is painted on
		// the slider
		imageContrastBoxJSlider.setValue(37);// 设置初始值

		leftFiveImageContrastBox.add(imageContrastBoxJSlider);

		leftFiveBox.add(leftFiveImageContrastBox);

		containerBox.add(leftFiveBox);

		// 其他特效
		Box leftSixBox = new Box(BoxLayout.X_AXIS);
		leftSixBox.setBorder(new EtchedBorder());

		blackAndwhiteCheckBox = new JCheckBox(LanguageLoader.getString("ToolImage.imageSetImageBlackAndwhite"));
		leftSixBox.add(blackAndwhiteCheckBox);
	    sharpCheckBox = new JCheckBox(LanguageLoader.getString("ToolImage.imageSetImageSharp"));
		leftSixBox.add(sharpCheckBox);
		blurCheckBox = new JCheckBox(LanguageLoader.getString("ToolImage.imageSetImageBlur"));
		leftSixBox.add(blurCheckBox);
		slickCheckBox = new JCheckBox(LanguageLoader.getString("ToolImage.imageSetImageSlick"));
		leftSixBox.add(slickCheckBox);
		negativeCheckBox = new JCheckBox(LanguageLoader.getString("ToolImage.imageSetImageNegative"));
		leftSixBox.add(negativeCheckBox);
		raisedCheckBox = new JCheckBox(LanguageLoader.getString("ToolImage.imageSetImageRaised"));
		leftSixBox.add(raisedCheckBox);

		containerBox.add(leftSixBox);
		leftPanel.add(containerBox);
		return leftPanel;
	}

	/**
	 * <p>
	 * 方法说明:
	 * </p>
	 * 
	 * @auther DuanYong
	 * @since 2012-12-16 下午5:40:23
	 * @return Component
	 */
	public JComponent getCenterPane() {
		centerPanel = new JLayeredPane();
		centerPanel.setBorder(new EtchedBorder());
		
		
		exampleImagePanel = new ExampleImagePanel();
		// 增加标签到容器上,参数-1的作用是让这个背景图片面板保持在所有面板的最下面,相当于WEB中的z-index属性  
		centerPanel.add(exampleImagePanel,JLayeredPane.DEFAULT_LAYER);
		centerScrollPane = new JScrollPane(centerPanel);
		return centerScrollPane;
	}

	/**
	 * <p>
	 * 方法说明:
	 * </p>
	 * 
	 * @auther DuanYong
	 * @since 2012-12-16 下午5:40:17
	 * @return Component
	 */
	public JComponent getTopPane() {
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(2, 1));
		topPanel.setBorder(new EtchedBorder());

        //顶部box
		Box topOneBox = new Box(BoxLayout.X_AXIS);
		//按分辨率缩放button
		resizeByPixelButton = new JRadioButton();
		resizeByPixelButton.setText(LanguageLoader.getString("ToolImage.imageSetResizeByPixel"));
		topOneBox.add(resizeByPixelButton);
		//宽度
		resizeByPixelLabelWidth = new JLabel(LanguageLoader.getString("ToolImage.imageSetResizeByPixelLabelWidth"));
		topOneBox.add(resizeByPixelLabelWidth);

		// 起始宽度,最小值,最大值,增量
		SpinnerNumberModel widthModel = new SpinnerNumberModel(1000, 0, 1000, 1);
		widthSpinner = new JSpinner(widthModel);
		widthSpinner.setPreferredSize(new Dimension(55, 15));
		topOneBox.add(widthSpinner);
		//高度
		resizeByPixelLabelHeight = new JLabel(LanguageLoader.getString("ToolImage.imageSetResizeByPixelLabelHeight"));
		topOneBox.add(resizeByPixelLabelHeight);

		// 起始宽度,最小值,最大值,增量
		SpinnerNumberModel heightModel = new SpinnerNumberModel(1000, 0, 1000, 1);
		heightSpinner = new JSpinner(heightModel);
		heightSpinner.setPreferredSize(new Dimension(55, 15));
		topOneBox.add(heightSpinner);
        //是否等比例
		equalScaleCehckBox = new JCheckBox(LanguageLoader.getString("ToolImage.imageSetResizeEqualPixelCehckBox"));
		topOneBox.add(equalScaleCehckBox);
		//按比例
		resizeByScaleButton = new JRadioButton();
		resizeByScaleButton.setText(LanguageLoader.getString("ToolImage.imageSetResizeByScale"));
		topOneBox.add(resizeByScaleButton);

		resizeByScaleLabel = new JLabel(LanguageLoader.getString("ToolImage.imageSetResizeByScaleLabel"));
		topOneBox.add(resizeByScaleLabel);

		// 起始宽度,最小值,最大值,增量
		SpinnerNumberModel scaleModel = new SpinnerNumberModel(100, 0, 200, 1);
		scaleSpinner = new JSpinner(scaleModel);
		scaleSpinner.setPreferredSize(new Dimension(55, 15));
		topOneBox.add(scaleSpinner);
		resizeByScaleSignLabel = new JLabel(LanguageLoader.getString("ToolImage.imageSetResizeByScaleSignLabel"));
		topOneBox.add(resizeByScaleSignLabel);

		resizeButtonGroup = new ButtonGroup();
		resizeButtonGroup.add(resizeByScaleButton);
		resizeButtonGroup.add(resizeByPixelButton);
		
		topPanel.add(topOneBox);

		Box topTwoBox = new Box(BoxLayout.X_AXIS);

		saveFormatCehckBox = new JCheckBox(LanguageLoader.getString("ToolImage.imageSetSaveFormatLabel"));
		topTwoBox.add(saveFormatCehckBox);
		String[] data = new String[ImageSettingBean.FORMAT_TYPE_MAP.size()];
		int i = 0;
		for(Iterator<String> it = ImageSettingBean.FORMAT_TYPE_MAP.keySet().iterator();it.hasNext();){
			data[i] = it.next();
			i++;
		}
		saveFormatCombo = new JComboBox(data);
		saveFormatCombo.setSize(50, 20);
		// saveFormatCombo.setSelectedIndex(0);//设置默认索引为2
		// saveFormatCombo.addActionListener(this);
		topTwoBox.add(saveFormatCombo);

		saveFormatQualityLabel = new JLabel(LanguageLoader.getString("ToolImage.imageSetSaveFormatQualityLabel"));
		topTwoBox.add(saveFormatQualityLabel);

		// 起始宽度,最小值,最大值,增量
		SpinnerNumberModel qualityModel = new SpinnerNumberModel(100, 1, 100, 1);
		qualitySpinner = new JSpinner(qualityModel);
		qualitySpinner.setPreferredSize(new Dimension(55, 15));
		topTwoBox.add(qualitySpinner);

		saveFormatPathLabel = new JLabel(LanguageLoader.getString("ToolImage.imageSetSaveFormatPathLabel"));
		topTwoBox.add(saveFormatPathLabel);

		savePathField = new JTextField();
		topTwoBox.add(savePathField);

		savePathBtn = new JButton(LanguageLoader.getString("ToolImage.imageSetSaveFormatPathBtn"),ImageLoader.getImageIcon("CrawlerResource.toolImageBrowse"));
		topTwoBox.add(savePathBtn);

		

		topPanel.add(topTwoBox);
		return topPanel;
	}
	public void disableSavePath(){
		saveFormatPathLabel.setEnabled(false);
		savePathField.setEnabled(false);
		savePathBtn.setEnabled(false);
	}
	/**
	 * 设置按分辨率缩放方式相关组件状态
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-12-17 下午10:17:33
	 * @param b
	 * @return void
	 */
	private void setResizeCompPixelState(boolean b){
		resizeByPixelLabelWidth.setEnabled(b);
	 	widthSpinner.setEnabled(b);
	 	resizeByPixelLabelHeight.setEnabled(b);
	 	heightSpinner.setEnabled(b);
	 	equalScaleCehckBox.setEnabled(b);
	}
	/**
	 * 设置按百分比相关组件状态
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-12-17 下午10:18:09
	 * @param b
	 * @return void
	 */
	private void setResizeCompScaleState(boolean b){
		resizeByScaleLabel.setEnabled(b);
    	scaleSpinner.setEnabled(b);
    	resizeByScaleSignLabel.setEnabled(b);
	}
	/**
	 * 设置图片格式化相关组件状态
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-12-18 上午10:00:12
	 * @param b
	 * @return void
	 */
	private void setSaveFormatImageState(boolean b){
		saveFormatCombo.setEnabled(b);
		saveFormatQualityLabel.setEnabled(b);
		qualitySpinner.setEnabled(b);
	}
	/**
	 * 设置旋转图片相关组件状态
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-12-18 下午9:19:28
	 * @param b
	 * @return void
	 */
	private void setCirImageCompState(boolean b){
		cirTypeHorizontalButton.setEnabled(b);
		cirTypeVerticalButton.setEnabled(b);
		cirJSpinner.setEnabled(b);
		
	}
	/**
	 * 设置裁剪图片相关组件状态
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-12-18 下午10:33:39
	 * @param b
	 * @return void
	 */
	private void setCutImageCompState(boolean b){
		cutTypeManualButton.setEnabled(b);
		cutTypeAutoButton.setEnabled(b);
		cutLabelWidth.setEnabled(b);
		cutWidthSpinner.setEnabled(b);
		cutLabelHeight.setEnabled(b);
		cutHeightSpinner.setEnabled(b);
	}
	/**
	 * 设置水印图片相关组件状态
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-12-18 下午11:04:03
	 * @param b
	 * @return void
	 */
	private void setWaterImageCompState(boolean b){
		waterImagePathField.setEnabled(b);
		waterImagePathDirBtn.setEnabled(b);
		waterImageCancelBg.setEnabled(b);
		waterImageDiaphaneity.setEnabled(b);
		if(Constant.YES.equals(waterImageDiaphaneityCehckValue) && b){
			diaphaneityJSlider.setEnabled(true);
		}else{
			diaphaneityJSlider.setEnabled(false);
		}
	}
	/**
	 * 设置水印文字相关组件状态
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-12-19 下午11:40:44
	 * @param b
	 * @return void
	 */
	private void setWaterTextCompState(boolean b){
		waterTextFontBtn.setEnabled(b);
		waterTextColorBtn.setEnabled(b);
		waterTextDateBox.setEnabled(b);
		waterTextContentArea.setEnabled(b);
		waterTextBgColorCehckBox.setEnabled(b);
		if(Constant.YES.equals(waterTextBgColorCehckValue) && b){
			textBgColorBtn.setEnabled(true);
		}else{
			textBgColorBtn.setEnabled(false);
		}
		waterTextBorderCehckBox.setEnabled(b);
		if(Constant.YES.equals(waterTextBorderCehckValue) && b){
			textBorderBtn.setEnabled(true);
		}else{
			textBorderBtn.setEnabled(false);
		}
		waterTextTimeBox.setEnabled(b);
		waterTextDiaphaneityBox.setEnabled(b);
		if(Constant.YES.equals(waterTextDiaphaneityCehckValue) && b){
			waterDiaphaneityJSlider.setEnabled(true);
		}else{
			waterDiaphaneityJSlider.setEnabled(false);
		}
	}
	/**
	 * 按宽,高调整案例图片大小
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-12-23 下午12:53:32
	 * @return void
	 */
	private void resizeExampleImageByPixel(){
		repaintImage();
		if(0 != temp.getResizeByPixelWidth()){
			widthSpinner.setValue(temp.getResizeByPixelWidth());
		}else{
			widthSpinner.setValue(exampleImagePanel.getBufferedImage().getWidth());
		}
		if(0 != temp.getResizeByPixelHeight()){
			heightSpinner.setValue(temp.getResizeByPixelHeight());
		}else{
			heightSpinner.setValue(exampleImagePanel.getBufferedImage().getHeight());
		}
	}
	/**
	 * 检查是否设置保存路径
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-12-24 下午10:02:01
	 * @return void
	 */
	private void checkSavePath(){
		if(StringUtils.isBlank(savePathField.getText())){
			JOptionPane.showMessageDialog(null, LanguageLoader.getString("ToolImage.imageSetSavePathIsBlank"), LanguageLoader.getString("Common.alertTitle"), JOptionPane.CLOSED_OPTION);
			return;
		}
	}
	/**
	 * 设置图片亮度相关组件状态
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-12-20 下午8:30:13
	 * @param b
	 * @return void
	 */
	private void setImageLightenessCompState(boolean b){
		imageLightenessJSlider.setEnabled(b);
	}
	/**
	 * 设置图片对比度相关组件状态
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-12-20 下午8:32:46
	 * @param b
	 * @return void
	 */
	private void setImageContrastCompState(boolean b){
		imageContrastBoxJSlider.setEnabled(b);
	}
    /**
     * 调整案例图片大小
     * <p>方法说明:</p>
     * @auther DuanYong
     * @since 2012-12-23 下午4:57:04
     * @param icon
     * @return void
     */
	private void repaintImage() {
		exampleImagePanel.repaintImage(getData());
		centerPanel.doLayout();
		centerScrollPane.doLayout();
	}
	private void repaintImage(ImageSettingBean temp) {
		exampleImagePanel.repaintImage(temp);
		centerPanel.doLayout();
		centerScrollPane.doLayout();
	}
	
	/**
	 * 加载水印图片
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-12-21 下午9:33:30
	 * @param waterImagePath
	 * @return void
	 */
	private void loadWaterImage(){
		dragPicpanel.repaintWaterImage(getData());
	}
	private void loadWaterImage(ImageSettingBean temp){
		dragPicpanel.repaintWaterImage(temp);
	}

	private void loadWaterText(){
		dragTextpanel.repaintWaterText(getData());
	}
	private void loadWaterText(ImageSettingBean temp){
		dragTextpanel.repaintWaterText(temp);
	}
	private void loadCutPanel(){
		cutPanel.repaintCutImage(getData());
	}
	private void loadCutPanel(ImageSettingBean temp){
		cutPanel.repaintCutImage(temp);
	}
	/**
	 * 设置保存路径
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-12-25 下午9:08:32
	 * @param path
	 * @return void
	 */
    private void setSavePath(String path){
    	File file = new File(path);
    	boolean mkdirSuccess = true;
    	if(!file.exists()){
    		mkdirSuccess = file.mkdirs();
    	}
    	if(mkdirSuccess){
    		savePathField.setText(path);
    	}else{
    		savePathField.setText("");
    		checkSavePath();
    	}
    }
	public String getPageName() {
		return LanguageLoader.getString("ToolImage.imageSetting");
	}

	

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.javacoo.crawler.event.CrawlerListener#update(org.javacoo.crawler.
	 * event.CrawlerEvent)
	 */
	@Override
	public void update(CowSwingEvent event) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.ui.view.panel.AbstractContentPanel#populateData()
	 */
	@Override
	protected ImageSettingBean populateData() {
		
		imageSettingBean.setResizeValue(resizeValue);
		
		imageSettingBean.setResizeByPixelWidth(Integer.valueOf(widthSpinner.getValue().toString()));
		
		imageSettingBean.setResizeByPixelHeight(Integer.valueOf(heightSpinner.getValue().toString()));
		
		imageSettingBean.setEqualScaleValue(equalScaleValue);
		
		imageSettingBean.setResizeByScaleValue(Integer.valueOf(scaleSpinner.getValue().toString()));
		
		
		
		imageSettingBean.setSaveFormatType(saveFormatType);
		imageSettingBean.setSaveFormatValue(saveFormatValue);
		
		imageSettingBean.setSaveImageQualityValue(Integer.valueOf(qualitySpinner.getValue().toString()));
		
		imageSettingBean.setSavePath(savePathField.getText());
		
		imageSettingBean.setCirCehckValue(cirCehckValue);
		
		
		imageSettingBean.setCirCehckTypeHorizontalValue(cirCehckTypeHorizontalValue);
		
		imageSettingBean.setCirCehckTypeVerticalValue(cirCehckTypeVerticalValue);
		
		imageSettingBean.setCirValue(Integer.valueOf(cirJSpinner.getValue().toString()));
		
		imageSettingBean.setCutCehckValue(cutCehckValue);
		
		imageSettingBean.setCutCehckTypeValue(cutCehckTypeValue);
		
		imageSettingBean.setCutPanelX(cutPanelX);
		
		imageSettingBean.setCutPanelY(cutPanelY);
		
		imageSettingBean.setCutWidth(Integer.valueOf(cutWidthSpinner.getValue().toString()));
		
		imageSettingBean.setCutHeight(Integer.valueOf(cutHeightSpinner.getValue().toString()));
		
		imageSettingBean.setWaterImageCehckValue(waterImageCehckValue);
		
		imageSettingBean.setWaterImagePath(waterImagePathField.getText());
		imageSettingBean.setWaterImageCancelBgValue(waterImageCancelBgValue);
		
		imageSettingBean.setWaterImageDiaphaneityCehckValue(waterImageDiaphaneityCehckValue);
		
		imageSettingBean.setWaterImageDiaphaneityValue(diaphaneityJSlider.getValue());
		
		imageSettingBean.setWaterImageX(waterImageX);
		imageSettingBean.setWaterImageY(waterImageY);
		
		
		
		imageSettingBean.setWaterTextCehckValue(waterTextCehckValue);
		
		imageSettingBean.setWaterTextFontValue(waterTextFontValue);
		
		imageSettingBean.setWaterTextColorValue(waterTextColorValue);
		
		imageSettingBean.setWaterText(StringUtils.split(waterTextValue, "\r\n"));
		imageSettingBean.setWaterTextDate(waterTextDateValue+" "+waterTextTimeValue);
		imageSettingBean.setWaterTextBgColorCehckValue(waterTextBgColorCehckValue);
		
		imageSettingBean.setWaterTextBgColor(waterTextBgColor);
		
		imageSettingBean.setWaterTextBorderCehckValue(waterTextBorderCehckValue);
		
		imageSettingBean.setWaterTextBorderColor(waterTextBorderColor);
		
		imageSettingBean.setWaterTextDateCehckValue(waterTextDateCehckValue);
		
		imageSettingBean.setWaterTextTimeCehckValue(waterTextTimeCehckValue);
		
		imageSettingBean.setWaterTextDiaphaneityCehckValue(waterTextDiaphaneityCehckValue);
		imageSettingBean.setWaterTextDiaphaneityValue(waterDiaphaneityJSlider.getValue());
		
		imageSettingBean.setWaterTextX(waterTextX);
		imageSettingBean.setWaterTextY(waterTextY);
		
		imageSettingBean.setImageLightenessCheckValue(imageLightenessCheckValue);
		
		imageSettingBean.setImageLighteness(imageLightenessJSlider.getValue());
		
		imageSettingBean.setImageContrastValue(imageContrastValue);
		
		imageSettingBean.setImageContrast(imageContrastBoxJSlider.getValue());
		
		imageSettingBean.setBlackAndwhiteCheckValue(blackAndwhiteCheckValue);
		
		imageSettingBean.setSharpCheckValue(sharpCheckValue);
		
		imageSettingBean.setBlurCheckValue(blurCheckValue);
		
		imageSettingBean.setSlickCheckValue(slickCheckValue);
		
		imageSettingBean.setNegativeCheckValue(negativeCheckValue);
		
		imageSettingBean.setRaisedCheckValue(raisedCheckValue);
        
		return imageSettingBean;
	}

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.ui.view.panel.AbstractContentPanel#fillComponentData(java.lang.Object)
	 */
	@Override
	public void fillComponentData(ImageSettingBean t) {
		imageSettingBean = t;
		temp = new ImageSettingBean();
		try {
			BeanUtils.copyProperties(temp, t);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		exampleImagePanel.initExampleImagePanel(temp);
		if(Constant.YES.equals(temp.getResizeValue())){
			resizeByScaleButton.setSelected(true);
			resizeValue = Constant.YES;
			setResizeCompPixelState(false);
		 	setResizeCompScaleState(true);
		}else{
			resizeByPixelButton.setSelected(true);
			resizeValue = Constant.NO;
			setResizeCompPixelState(true);
		 	setResizeCompScaleState(false);
		}
		if(Constant.YES.equals(temp.getEqualScaleValue())){
			equalScaleCehckBox.setSelected(true);
			equalScaleValue = Constant.YES;
		}else{
			equalScaleCehckBox.setSelected(false);
			equalScaleValue = Constant.NO;
		}
		if(0 != temp.getResizeByPixelWidth()){
			widthSpinner.setValue(temp.getResizeByPixelWidth());
		}
		if(0 != temp.getResizeByPixelHeight()){
			heightSpinner.setValue(temp.getResizeByPixelHeight());
		}
		if(0 != temp.getResizeByScaleValue()){
			scaleSpinner.setValue(temp.getResizeByScaleValue());
		}
		repaintImage(temp);
		if(Constant.YES.equals(temp.getSaveFormatValue())){
			saveFormatCehckBox.setSelected(true);
			saveFormatValue = Constant.YES;
			setSaveFormatImageState(true);
		}else{
			saveFormatCehckBox.setSelected(false);
			saveFormatValue = Constant.NO;
			setSaveFormatImageState(false);
		}
		if(StringUtils.isNotBlank(temp.getSaveFormatType())){
			saveFormatCombo.setSelectedItem(temp.getSaveFormatType());
		}
		if(0 != temp.getSaveImageQualityValue()){
			qualitySpinner.setValue(temp.getSaveImageQualityValue());
		}
		repaintImage(temp);
		if(StringUtils.isNotBlank(temp.getSavePath())){
			setSavePath(temp.getSavePath());
		}else{
			setSavePath(Constant.SYSTEM_ROOT_PATH +Constant.getSlash()+ Config.CRAWLER_CONFIG_MAP.get(Config.CRAWLER_CONFIG_KEY_CORE).get(Config.CRAWLER_CONFIG_KEY_CORE_RES_SAVE_ROOT_PATH));
		}
		//旋转相关
		if(Constant.YES.equals(temp.getCirCehckValue())){
			cirCehckBox.setSelected(true);
			cirCehckValue = Constant.YES;
			setCirImageCompState(true);
			cirJSpinner.setValue(temp.getCirValue());
			repaintImage(temp);
		}else{
			cirCehckBox.setSelected(false);
			cirCehckValue = Constant.NO;
			setCirImageCompState(false);
		}
		if(Constant.YES.equals(temp.getCirCehckTypeHorizontalValue())){
			cirTypeHorizontalButton.setSelected(true);
			cirCehckTypeHorizontalValue = Constant.YES;
		}else{
			cirTypeHorizontalButton.setSelected(false);
			cirCehckTypeHorizontalValue = Constant.NO;
		}
		
		if(Constant.YES.equals(temp.getCirCehckTypeVerticalValue())){
			cirTypeVerticalButton.setSelected(true);
			cirCehckTypeVerticalValue = Constant.YES;
		}else{
			cirTypeVerticalButton.setSelected(false);
			cirCehckTypeVerticalValue = Constant.NO;
		}
		//裁剪相关
		if(Constant.YES.equals(temp.getCutCehckValue())){
			cutCehckBox.setSelected(true);
			cutCehckValue = Constant.YES;
			setCutImageCompState(true);
			cutPanel.setLocation(temp.getCutPanelX(), temp.getCutPanelY());
			cutPanelX = temp.getCutPanelX();
			cutPanelY = temp.getCutPanelY();
			loadCutPanel(temp);
		}else{
			cutCehckBox.setSelected(false);
			cutCehckValue = Constant.NO;
			setCutImageCompState(false);
		}
		if(Constant.YES.equals(temp.getCutCehckTypeValue())){
			cutTypeManualButton.setSelected(true);
			cutCehckTypeValue = Constant.YES;
		}else{
			cutTypeAutoButton.setSelected(false);
			cutCehckTypeValue = Constant.NO;
		}
		//水印图片
		if(Constant.YES.equals(temp.getWaterImageCehckValue())){
			waterImageCehckBox.setSelected(true);
			waterImageCehckValue = Constant.YES;
			setWaterImageCompState(true);
			waterImagePathField.setText(temp.getWaterImagePath());
			dragPicpanel.setLocation(temp.getWaterImageX(), temp.getWaterImageY());
			waterImageX = temp.getWaterImageX();
			waterImageY = temp.getWaterImageY();
			loadWaterImage(temp);
		}else{
			waterImageCehckBox.setSelected(false);
			waterImageCehckValue = Constant.NO;
			setWaterImageCompState(false);
		}
		if(Constant.YES.equals(temp.getWaterImageCancelBgValue())){
			waterImageCancelBg.setSelected(true);
			waterImageCancelBgValue = Constant.YES;
		}else{
			waterImageCancelBg.setSelected(false);
			waterImageCancelBgValue = Constant.NO;
		}
		if(Constant.YES.equals(temp.getWaterImageDiaphaneityCehckValue())){
			waterImageDiaphaneity.setSelected(true);
			waterImageDiaphaneityCehckValue = Constant.YES;
			diaphaneityJSlider.setEnabled(true);
		}else{
			waterImageDiaphaneity.setSelected(false);
			waterImageDiaphaneityCehckValue = Constant.NO;
			diaphaneityJSlider.setEnabled(false);
		}
		//水印文字
		if(Constant.YES.equals(temp.getWaterTextCehckValue())){
			waterTextCehckBox.setSelected(true);
			waterTextCehckValue = Constant.YES;
			setWaterTextCompState(true);
			if(null != temp.getWaterText()){
				StringBuilder text = new StringBuilder();
				for(String str : temp.getWaterText()){
					text.append(str).append("\r\n");
				}
				waterTextContentArea.setText(text.toString());
			}
			dragTextpanel.setLocation(temp.getWaterTextX(), temp.getWaterTextY());
			waterTextX = temp.getWaterTextX();
			waterTextY = temp.getWaterTextY();
			loadWaterText(temp);
		}else{
			waterTextCehckBox.setSelected(false);
			waterTextCehckValue = Constant.NO;
			setWaterTextCompState(false);
		}
		if(Constant.YES.equals(temp.getWaterTextBgColorCehckValue())){
			waterTextBgColorCehckBox.setSelected(true);
			waterTextBgColorCehckValue = Constant.YES;
		}else{
			waterTextBgColorCehckBox.setSelected(false);
			waterTextBgColorCehckValue = Constant.NO;
		}
		if(Constant.YES.equals(temp.getWaterTextBorderCehckValue())){
			waterTextBorderCehckBox.setSelected(true);
			waterTextBorderCehckValue = Constant.YES;
		}else{
			waterTextBorderCehckBox.setSelected(false);
			waterTextBorderCehckValue = Constant.NO;
		}
		if(Constant.YES.equals(temp.getWaterTextDateCehckValue())){
			waterTextDateBox.setSelected(true);
			waterTextDateCehckValue = Constant.YES;
			waterTextDateValue = DateUtil.dateToStr(new Date(), "yyyy-MM-dd");
		}else{
			waterTextDateBox.setSelected(false);
			waterTextDateCehckValue = Constant.NO;
		}
		if(Constant.YES.equals(temp.getWaterTextTimeCehckValue())){
			waterTextTimeBox.setSelected(true);
			waterTextTimeCehckValue = Constant.YES;
			waterTextTimeValue =  DateUtil.dateToStr(new Date(), "HH:mm");
		}else{
			waterTextTimeBox.setSelected(false);
			waterTextTimeCehckValue = Constant.NO;
		}
		if(Constant.YES.equals(temp.getWaterTextDiaphaneityCehckValue())){
			waterTextDiaphaneityBox.setSelected(true);
			waterDiaphaneityJSlider.setEnabled(true);
			waterTextDiaphaneityCehckValue = Constant.YES;
		}else{
			waterTextDiaphaneityBox.setSelected(false);
			waterDiaphaneityJSlider.setEnabled(false);
			waterTextDiaphaneityCehckValue = Constant.NO;
		}
		if(0 != temp.getWaterTextDiaphaneityValue()){
			waterDiaphaneityJSlider.setValue(temp.getWaterTextDiaphaneityValue());
		}
		//亮度
		if(Constant.YES.equals(temp.getImageLightenessCheckValue())){
			imageLightenessCheckBox.setSelected(true);
			imageLightenessCheckValue = Constant.YES;
			setImageLightenessCompState(true);
		}else{
			imageLightenessCheckBox.setSelected(false);
			imageLightenessCheckValue = Constant.NO;
			setImageLightenessCompState(false);
		}
		if(0 != temp.getImageLighteness()){
			imageLightenessJSlider.setValue(temp.getImageLighteness());
		}
		//对比度
		if(Constant.YES.equals(temp.getImageContrastValue())){
			imageContrastCheckBox.setSelected(true);
			imageContrastValue = Constant.YES;
			setImageContrastCompState(true);
		}else{
			imageContrastCheckBox.setSelected(false);
			imageContrastValue = Constant.NO;
			setImageContrastCompState(false);
		}
		if(0 != temp.getImageContrast()){
			imageContrastBoxJSlider.setValue(temp.getImageContrast());
		}
		//其他特效
		if(Constant.YES.equals(temp.getBlackAndwhiteCheckValue())){
			blackAndwhiteCheckBox.setSelected(true);
			blackAndwhiteCheckValue = Constant.YES;
		}else{
			blackAndwhiteCheckBox.setSelected(false);
			blackAndwhiteCheckValue = Constant.NO;
		}
		if(Constant.YES.equals(temp.getSharpCheckValue())){
			sharpCheckBox.setSelected(true);
			sharpCheckValue = Constant.YES;
		}else{
			sharpCheckBox.setSelected(false);
			sharpCheckValue = Constant.NO;
		}
		if(Constant.YES.equals(temp.getSlickCheckValue())){
			slickCheckBox.setSelected(true);
			sharpCheckValue = Constant.YES;
		}else{
			slickCheckBox.setSelected(false);
			sharpCheckValue = Constant.NO;
		}
		if(Constant.YES.equals(temp.getNegativeCheckValue())){
			negativeCheckBox.setSelected(true);
			negativeCheckValue = Constant.YES;
		}else{
			negativeCheckBox.setSelected(false);
			negativeCheckValue = Constant.NO;
		}
		if(Constant.YES.equals(temp.getRaisedCheckValue())){
			raisedCheckBox.setSelected(true);
			raisedCheckValue = Constant.YES;
		}else{
			raisedCheckBox.setSelected(false);
			raisedCheckValue = Constant.NO;
		}
		resizeExampleImageByPixel();
	}

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.ui.view.panel.IListPage#getPageId()
	 */
	@Override
	public String getPageId() {
		return this.getClass().getName();
	}

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.ui.view.panel.IListPage#disposePage()
	 */
	@Override
	public void disposePage() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.ui.view.panel.IListPage#isDefaultPage()
	 */
	@Override
	public boolean isDefaultPage() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.ui.view.panel.IListPage#setDefaultPage(boolean)
	 */
	@Override
	public void setDefaultPage(boolean bool) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.ui.view.panel.IListPage#showData(int, int)
	 */
	@Override
	public void showData(int pageNumber, int pageSize) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.javacoo.crawler.ui.view.panel.AbstractContentPanel#initComponents()
	 */
	@Override
	protected void initComponents() {
		// TODO Auto-generated method stub
		
	}

	public ImageSettingBean getImageSettingBean() {
		getData();
		return imageSettingBean;
	}
     
}
