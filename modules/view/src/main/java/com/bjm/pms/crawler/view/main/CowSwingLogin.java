/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.bjm.pms.crawler.view.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.net.InetAddress;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.CollectionUtils;

import com.bjm.pms.crawler.view.base.constant.Constant;
import com.bjm.pms.crawler.view.base.utils.FileUtils;
import com.bjm.pms.crawler.view.base.utils.HttpProxySetUtil;
import com.bjm.pms.crawler.view.base.utils.ImageManager;
import com.bjm.pms.crawler.view.core.cache.UserCacheManager;
import com.bjm.pms.crawler.view.core.context.CowSwingContextData;
import com.bjm.pms.crawler.view.core.runcycle.ICowSwingRunCycle;
import com.bjm.pms.crawler.view.core.runcycle.support.CowSwingRunCycleManager;
import com.bjm.pms.crawler.view.ui.style.LookAndFeelSelector;
import com.bjm.pms.crawler.view.ui.widget.JFilterTextField;
import com.bjm.pms.crawler.view.ui.widget.UrlLabel;
import com.bjm.pms.crawler.webservice.manager.ManagerService;
import com.bjm.pms.crawler.webservice.manager.beans.UserBean;
import com.bjm.pms.crawler.webservice.util.WebServiceUtil;
import com.sun.awt.AWTUtilities;

/**
 * 丑牛爬虫登陆界面
 * <p>
 * 说明:
 * </p>
 * 丑牛爬虫登陆界面
 * 
 * @author DuanYong
 * @since 2013-4-12 下午10:08:08
 * @version 1.0
 */
public class CowSwingLogin extends JFrame {
	private static final long serialVersionUID = 1L;
	protected static final Log logger = LogFactory.getLog(CowSwingLogin.class);
	/** 登陆窗口宽 */
	private int width = 332;
	/** 登陆窗口高 */
	private int height = 344;
	private ImageIcon buttonIcon;
	private ImageIcon buttonRoverIcon;
	private ImageIcon buttonPressedIcon;
	private ImageIcon logoIcon;
	private ImageIcon logoRoverIcon;
	private JLabel logoLabel;
	private JButton btnLogin;
	private JPanel inputPane;
	private JPanel topPane;
	private JLabel errorLabel;
	private JLabel netSetLabel;
	private JLabel exitLabel;
	private JFilterTextField userNameTextField;
	private JTextField passwordTextField;
	private JCheckBox rememberMe;
	private MouseAdapter moveWindowListener;
	private static CowSwingLogin ui;
	private static FileLock lock = null;
	/** 用户缓存管理类 */
	protected UserCacheManager userCacheManager = UserCacheManager.getInstance();
	/** 柜员号集合 */
	protected List<Object> userIdList;
	/** 最后一次登录用户ID */
	protected String lastUserId = "";
	private static String lockFile = Constant.SYSTEM_ROOT_PATH+"/lock.lock";

	public CowSwingLogin() {
		initListener();
		initComponents();
		initStyle(inputPane);
		registerListener();
		initPosition();
		CowSwingContextData.getInstance().setContextData(Constant.CONTEXT_DATA_KEY_WEBSERVICE, WebServiceUtil.getInstance().getWsCtx().getBean("managerClient"));
	}

	private void initPosition() {
		// 取得屏幕大小
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension oursize = getSize();
		Dimension screensize = kit.getScreenSize();
		// 窗口居中显示
		int x = (screensize.width - oursize.width) / 2;
		int y = (screensize.height - oursize.height) / 2;
		x = Math.max(0, x);
		y = Math.max(0, y);
		setLocation(x, y);
	}

	/**
	 * 初始化组件
	 * <p>
	 * 方法说明:</>
	 * <li></li>
	 * 
	 * @author DuanYong
	 * @since 2013-4-20 下午4:27:55
	 * @version 1.0
	 * @exception
	 */
	private void initComponents() {
		getPropertyValues();
		setTitle("丑牛迷你采集器");

		setIconImage(ImageManager.getImageByShortName("crawler_logo.png"));
		buttonIcon = ImageManager.getImageIconByShortName("login_button.png");
		buttonRoverIcon = ImageManager
				.getImageIconByShortName("login_button_rover.png");
		buttonPressedIcon = ImageManager
				.getImageIconByShortName("login_button_pressed.png");
		logoIcon = ImageManager.getImageIconByShortName("login_logo.png");
		logoRoverIcon = ImageManager
				.getImageIconByShortName("login_logo_rover.png");
		logoLabel = createDraggableLabel(logoIcon);
		btnLogin = new JButton();
		inputPane = new JPanel() {
			private static final long serialVersionUID = 1L;
			private TexturePaint paint;

			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				paint = createTexturePaint(ImageManager
						.getImageByShortName("login_background.png"));
				Graphics2D g2d = (Graphics2D) g;
				g2d.setPaint(paint);
				g2d.fillRect(0, 0, getWidth(), getHeight());
			}
		};
		setDefaultCloseOperation(2);
		setUndecorated(true);
		AWTUtilities.setWindowOpaque(this, false);
		JPanel centerPane = new JPanel(new BorderLayout());
		centerPane.add(btnLogin, "South");
		setContentPane(centerPane);
		setSize(width, height);
		btnLogin.setBorder(null);
		btnLogin.setMargin(null);
		btnLogin.setOpaque(false);
		btnLogin.setIcon(buttonIcon);
		btnLogin.setRolloverEnabled(true);
		btnLogin.setRolloverIcon(buttonRoverIcon);
		btnLogin.setPressedIcon(buttonPressedIcon);
		btnLogin.setContentAreaFilled(false);
		btnLogin.setRequestFocusEnabled(false);

		topPane = new JPanel(new BorderLayout());
		logoLabel.setOpaque(false);
		topPane.setOpaque(false);

		topPane.add(logoLabel, "Center");
		topPane.add(createDraggableLabel(ImageManager
				.getImageIconByShortName("login_left_top.png")), "West");
		topPane.add(createDraggableLabel(ImageManager
				.getImageIconByShortName("login_right_top.png")), "East");
		centerPane.add(createDraggableLabel(ImageManager
				.getImageIconByShortName("login_left.png")), "West");
		centerPane.add(createDraggableLabel(ImageManager
				.getImageIconByShortName("login_right.png")), "East");
		centerPane.add(topPane, "North");
		inputPane.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		inputPane.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
		inputPane.setPreferredSize(new Dimension(100, 30));

		inputPane.add(createInputLabel("用户名:"));
		userNameTextField = new JFilterTextField(userIdList);
		userNameTextField.setText(lastUserId);
		userNameTextField.setPreferredSize(new Dimension(120, 26));
		inputPane.add(userNameTextField);
		inputPane.add(createInputLabel("密   码:"));
		passwordTextField = new JTextField("123456");
		passwordTextField.setPreferredSize(new Dimension(120, 26));
		inputPane.add(passwordTextField);
		inputPane.add(createInputLabel("语   言:"));
		String[] data = new String[] { "zh_CN" };
		JComboBox languageComboBox = new JComboBox(data);
		languageComboBox.setPreferredSize(new Dimension(120, 26));
		inputPane.add(languageComboBox);
		inputPane.add(new JLabel());
		rememberMe = new JCheckBox("记住我");
		rememberMe.setOpaque(false);
		inputPane.add(rememberMe);
		netSetLabel = createInputLabel("网络设置");
		netSetLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		inputPane.add(netSetLabel);
		JLabel regLabel = new UrlLabel("注册新账号", "http://www.javacoo.com/register.jspx");
		JLabel forgetPasswordLabel = new UrlLabel("忘记密码", "http://www.javacoo.com/member/forgot_password.jspx");
		exitLabel = createInputLabel("退出");
		exitLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 1));
		panel.setOpaque(false);
		panel.add(regLabel);
		panel.add(forgetPasswordLabel);
		panel.add(exitLabel);
		inputPane.add(panel);
		errorLabel = createInputLabel("");
		inputPane.add(errorLabel);
		centerPane.add(inputPane, "Center");
		
	}
	private void initStyle(JPanel panel){
		Component[] components = panel.getComponents();
		for(Component component : components){
			if(component instanceof JPanel){
				JPanel tempPanel = (JPanel)component;
				initStyle(tempPanel);
			}
			setupComponent(component);
		}
	}

	/**
	 * 初始化全局Listener
	 * <p>
	 * 方法说明:</>
	 * <li>初始化MouseAdapter</li>
	 * 
	 * @author DuanYong
	 * @since 2013-4-20 下午4:24:10
	 * @version 1.0
	 * @exception
	 */
	private void initListener() {
		moveWindowListener = new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				lastPoint = e.getLocationOnScreen();
			}

			public void mouseDragged(MouseEvent e) {
				Point point = e.getLocationOnScreen();
				int offsetX = point.x - lastPoint.x;
				int offsetY = point.y - lastPoint.y;
				Rectangle bounds = getBounds();
				bounds.x += offsetX;
				bounds.y += offsetY;
				setBounds(bounds);
				lastPoint = point;
			}

			public void mouseEntered(MouseEvent e) {
				if (e.getSource() == logoLabel)
					logoLabel.setIcon(logoRoverIcon);
			}

			public void mouseExited(MouseEvent e) {
				if (e.getSource() == logoLabel)
					logoLabel.setIcon(logoIcon);
			}

			private Point lastPoint;

		};
		
	}

	/**
	 * 注册Listener
	 * <p>
	 * 方法说明:</>
	 * <li></li>
	 * 
	 * @author DuanYong
	 * @since 2013-4-20 下午4:28:13
	 * @version 1.0
	 * @exception
	 */
	private void registerListener() {
		logoLabel.addMouseListener(moveWindowListener);
		logoLabel.addMouseMotionListener(moveWindowListener);
		topPane.addMouseListener(moveWindowListener);
		topPane.addMouseMotionListener(moveWindowListener);
		inputPane.addMouseListener(moveWindowListener);
		inputPane.addMouseMotionListener(moveWindowListener);
		btnLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(login()){
					ui.setVisible(false);
					JSplash.getInstance().splashOn();
					JSplash.getInstance().increaseProgress(0, "丑牛正在初始化,请稍候...");
					start();
				}
			}
		});
		netSetLabel.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				NetProxySettingDialog.getInstance().init(ui, Constant.OPTION_TYPE_ADD, "网络代理设置");
				NetProxySettingDialog.getInstance().setVisible(true);
			}
		});
		exitLabel.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				ui.dispose();
			}
		});
	}
	private boolean login(){
		String errorMsg = "";
        try {
        	if(Boolean.valueOf(userCacheManager.getValue(Constant.NET_PROXY_SETTING_ISUSED))){
        		HttpProxySetUtil.setHttpProxy(userCacheManager.getValue(Constant.NET_PROXY_SETTING_HOST), userCacheManager.getValue(Constant.NET_PROXY_SETTING_PORT), userCacheManager.getValue(Constant.NET_PROXY_SETTING_DOMAIN), userCacheManager.getValue(Constant.NET_PROXY_SETTING_USERNAME), userCacheManager.getValue(Constant.NET_PROXY_SETTING_PASSWORD));
        	}
        	UserBean userBean = new UserBean();
            userBean.setUsername(userNameTextField.getText());
            userBean.setPassword(passwordTextField.getText());
			userBean.setLastLoginIp(InetAddress.getLocalHost().getHostAddress());
			ManagerService service = (ManagerService)CowSwingContextData.getInstance().getContextDataByKey(Constant.CONTEXT_DATA_KEY_WEBSERVICE);
			userBean = service.login(userBean);
			if(userBean.getIsLoginSuccess()){
				CowSwingContextData.getInstance().setContextData(Constant.CONTEXT_DATA_KEY_USERANME, userBean);
				if(rememberMe.isSelected()){
					// 记录柜员号到本地属性配置文件
					saveUserIdToLocalProperty(userBean.getUsername());
				}
				return true;
			}else{
				errorMsg = userBean.getErrorMsg();
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			int result = JOptionPane.showConfirmDialog(null, "连接主机失败,是否离线运行?", "系统提示",JOptionPane.YES_NO_OPTION); 
			if(result == 0){
				return true;
			}
			errorMsg = "连接主机失败,请检查网络配置!";
			return false;
		}finally{
			errorLabel.setText(errorMsg);
		}
	}
	/**
	 * 取得属性文件中柜员号信息
	 * <p>
	 * 方法说明:
	 * </p>
	 * <li>获取当前用户配置文件中记录的已经成功登陆的柜员号并组装到userIdList中</li> 
	 * <li>获取当前用户配置文件中记录的最后一次成功登陆的柜员号并组装到lastUserId中</li>
	 * <li>获取当前用户配置文件中记录的最后一次成功登陆的登录方式并组装到lastLoginMode中</li>
	 * @author DuanYong
	 * @since 2013-4-10 下午3:31:22
	 * @version 1.0
	 * @exception
	 */
	private void getPropertyValues() {
		if (logger.isInfoEnabled()) {
			logger.info("读取文件数据...");
		}
		String userIds = userCacheManager
				.getValue(Constant.FILE_PROPERTY_KEY_USER_IDS);
		userIdList = new ArrayList<Object>();
		if (StringUtils.isNotBlank(userIds)) {
			if (userIds.contains(",")) {
				for (String userId : userIds.split(",")) {
					userIdList.add(userId);
				}
			} else {
				userIdList.add(userIds);
			}
		}
		lastUserId = userCacheManager.getValue(Constant.FILE_PROPERTY_KEY_LAST_USER_ID);
		if (logger.isInfoEnabled()) {
			logger.info("读取文件数据...完成");
		}
	}

	/**
	 * 保存柜员号到本地属性配置文件
	 * <p>
	 * 方法说明:
	 * </p>
	 * <li>保存柜员号到已经登录柜员号以逗号分隔</li> 
	 * <li>保存柜员号到最后一次</li>
	 * @author DuanYong
	 * @since 2013-4-12 上午10:20:22
	 * @version 1.0
	 * @exception
	 * @param userCode
	 */
	private void saveUserIdToLocalProperty(String userId) {
		if (!userIdList.contains(userId)) {
			String tempUserId = userId;
			if (!userIdList.isEmpty()) {
				tempUserId = userId + ",";
			}
			if (null != userCacheManager
					.getValue(Constant.FILE_PROPERTY_KEY_USER_IDS)) {
				tempUserId = tempUserId
						+ userCacheManager
								.getValue(Constant.FILE_PROPERTY_KEY_USER_IDS);
			}
			userCacheManager.setValue(Constant.FILE_PROPERTY_KEY_USER_IDS,
					tempUserId);
			userIdList.add(userId);
		}
		if (!lastUserId.equals(userId)) {
			userCacheManager.setValue(Constant.FILE_PROPERTY_KEY_LAST_USER_ID,
					userId);
			lastUserId = userId;
		}
	}

	/**
	 * 登陆
	 * <p>
	 * 方法说明:</>
	 * <li></li>
	 * 
	 * @author DuanYong
	 * @since 2013-4-20 下午4:48:07
	 * @version 1.0
	 * @exception
	 */
	private void start() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				LookAndFeelSelector.setLookAndFeel("OfficeBlue2007");
				com.bjm.pms.crawler.view.ui.style.ColorDefinitions.initColors();
				// 开始运行周期
				ICowSwingRunCycle cowSwingRunCycle = new CowSwingRunCycleManager();
				cowSwingRunCycle.start();
				ui.dispose();
				JSplash.getInstance().splashOff();
			}
		});
	}


	/**
	 * 构建可拖动的label
	 * <p>
	 * 方法说明:</>
	 * <li></li>
	 * 
	 * @author DuanYong
	 * @since 2013-4-20 下午4:29:31
	 * @version 1.0
	 * @exception
	 * @param icon
	 * @return
	 */
	private JLabel createDraggableLabel(ImageIcon icon) {
		JLabel label = new JLabel(icon);
		label.addMouseListener(moveWindowListener);
		label.addMouseMotionListener(moveWindowListener);
		return label;
	}

	/**
	 * 构建输入框label
	 * <p>
	 * 方法说明:</>
	 * <li></li>
	 * 
	 * @author DuanYong
	 * @since 2013-4-20 下午4:29:56
	 * @version 1.0
	 * @exception
	 * @param text
	 * @return
	 */
	private JLabel createInputLabel(String text) {
		JLabel label = new JLabel(text);
		return label;
	}

	private void setupComponent(Component c) {
		c.setFont(new Font("微软雅黑", 1, 14));
		c.setForeground(new Color(37, 81, 54));
	}

	public static TexturePaint createTexturePaint(Image image) {
		int imageWidth = image.getWidth(null);
		int imageHeight = image.getHeight(null);
		BufferedImage bi = new BufferedImage(imageWidth, imageHeight, 2);
		Graphics2D g2d = bi.createGraphics();
		g2d.drawImage(image, 0, 0, null);
		g2d.dispose();
		return new TexturePaint(bi,
				new Rectangle(0, 0, imageWidth, imageHeight));
	}

	public static void main(String args[]) {
		if(!isLocking()){
			checkUpdate();
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					ui = new CowSwingLogin();
					ui.setVisible(true);
				}
			});
		}
	}
	/**
	 * 检查系统是否已经启动
	 * <p>方法说明:</>
	 * <li></li>
	 * @author DuanYong
	 * @since 2013-10-8 下午11:33:47
	 * @version 1.0
	 * @exception 
	 * @return
	 */
	public static boolean isLocking() {
		try {
			File flagFile = new File(lockFile);
			if (!flagFile.exists()){
				flagFile.createNewFile();
			}
			lock = new FileOutputStream(lockFile).getChannel().tryLock();

			if (lock == null){
				return true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}
	/**
	 * 版本更新检查
	 * <p>方法说明:</>
	 * <li></li>
	 * @author DuanYong
	 * @since 2014-1-7 上午10:58:09
	 * @version 1.0
	 * @exception
	 */
	private static void checkUpdate(){
		if(FileUtils.checkFileIsexists(Constant.UPDATE_FILE)){
			List<File> fileList = new ArrayList<File>();
			FileUtils.getAllFiles(new File(Constant.UPDATE_DIR),fileList);
			String newFilePath = "";
			if(!CollectionUtils.isEmpty(fileList)){
				for(File f : fileList){
					if(Constant.UPDATE_FILE_NAME.equals(FilenameUtils.getName(f.getAbsolutePath())) || StringUtils.isBlank(f.getAbsolutePath()) || FilenameUtils.getName(f.getAbsolutePath()).contains(Constant.UPDATE_FILE_NAME_PRE)){
						continue;
					}
					newFilePath = f.getAbsolutePath().replace(Constant.PACKAGE_UPDATE + Constant.SYSTEM_SEPARATOR, "");
					logger.info("====更新文件："+f.getAbsolutePath());
					logger.info("====更新后文件路径："+newFilePath);
					FileUtils.deleteFile(newFilePath);
					FileUtils.createFile(f,newFilePath,false);
					FileUtils.deleteFile(f.getAbsolutePath(),true);
				}
				//删除标志文件
				//FileUtils.deleteFile(Constant.UPDATE_FILE);
				FileUtils.deleteAllFile(Constant.UPDATE_DIR);
			}
		}
	}
}
