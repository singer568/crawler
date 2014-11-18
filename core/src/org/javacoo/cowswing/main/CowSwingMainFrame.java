package org.javacoo.cowswing.main;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;
import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.javacoo.cowswing.base.constant.Config;
import org.javacoo.cowswing.base.constant.Constant;
import org.javacoo.cowswing.base.loader.ImageLoader;
import org.javacoo.cowswing.base.loader.LanguageLoader;
import org.javacoo.cowswing.core.context.CowSwingContextData;
import org.javacoo.cowswing.core.event.CowSwingEvent;
import org.javacoo.cowswing.core.event.CowSwingEventType;
import org.javacoo.cowswing.core.event.CowSwingListener;
import org.javacoo.cowswing.core.event.CowSwingObserver;
import org.javacoo.cowswing.plugin.core.ui.view.panel.AdvertisementPanel;
import org.javacoo.cowswing.ui.plugin.UiPlugin;
import org.javacoo.cowswing.ui.view.bar.AppMenuBar;
import org.javacoo.cowswing.ui.view.bar.AppToolBar;
import org.javacoo.cowswing.ui.view.card.CurtainCardPerspective;
import org.javacoo.cowswing.ui.view.panel.SystemTabPanel;
import org.javacoo.cowswing.ui.widget.MainFrame;
import org.jdesktop.swingx.JXStatusBar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * 丑牛GUI主界面
 * 
 * @author javacoo
 * @since 2012-03-14
 */
@Component("cowSwingMainFrame")
public class CowSwingMainFrame extends MainFrame implements Runnable,CowSwingListener{
	private static final long serialVersionUID = 1L;
	protected Logger logger = Logger.getLogger(this.getClass());
	/** 菜单栏 */ 
	@Resource(name="appMenuBar") 
	private AppMenuBar appMenuBar;
	/** 工具栏 */
	@Resource(name="appToolBar")
	private AppToolBar appToolBar;
	private JPanel centerPanel;
	private JPanel mainPanel;
	/** The application statusBar.*/
	private JXStatusBar statusBar;
	
	/** The left status bar. */
	private JLabel leftVersionInfoLabel;
	private JMenuBar leftMenubar = new JMenuBar();


	/** The center status bar. */
	private JLabel centerStatusBar;

	/** The right status bar. */
	private JLabel rightStatusBar;
	
	private SystemTray sysTray;// 当前操作系统的托盘对象  
    private TrayIcon trayIcon;// 当前对象的托盘   
    private static int count = 1; //记录消息闪动的次数  
    private boolean flag = false; //是否有新消息  
    private boolean hasTrayIcon = false; //是否已经加载到系统托盘
    private String msg = "";//消息
    
    @Resource(name="systemTabPanel")
    private SystemTabPanel systemTabPanel;
    @Resource(name="curtainCardPerspective")
    private CurtainCardPerspective curtainCardPerspective;
    @Resource(name="advertisementPanel")
    private AdvertisementPanel advertisementPanel;
    
    /**插件*/
	@Autowired
	private List<UiPlugin> uiPlugins;
	private CowSwingMainFrame(){
		super();
	}
	/**
	 * 初始化
	 * <p>方法说明:</p>
	 * <li></li>
	 * @auther DuanYong
	 * @since 2013-2-27 下午2:41:28
	 * @return void
	 */
	public void init() {
		ininFrameConfig();
		initPlugin(); 
		this.setJMenuBar(appMenuBar);
		this.setContentPane(getContentPanel());
		this.createTrayIcon();// 创建托盘对象  
		setVisible(true);
		toFront();
		visibleAfter();
	}
	/**
	 * 初始化插件
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-12-13 下午5:04:54
	 * @return void
	 */
	private void initPlugin(){
		if(CollectionUtils.isNotEmpty(uiPlugins)){
			Collections.sort(uiPlugins);
			boolean hasSetDefault = false;
			for(UiPlugin plugin : uiPlugins){
				appMenuBar.loadPluginMenuBar(plugin.getMenuBarList());
				appToolBar.loadPluginToolBar(plugin.getToolBarList());
				curtainCardPerspective.loadPluginNavigator(plugin.getNavigatorItemList());
				if(!hasSetDefault){
					if(null != plugin.getDefaultPage()){
						plugin.getDefaultPage().init();
						curtainCardPerspective.setDefaultComponent(plugin.getDefaultPage());
						hasSetDefault = true;
					}
				}
			}
		}
	}
	 /**  
     * 添加托盘的方法  
     */ 
    public void addTrayIcon(){ 
        setVisible(false);// 使得当前的窗口隐藏  
    	if(!hasTrayIcon){
    		 try{  
    	            sysTray.add(trayIcon);// 将托盘添加到操作系统的托盘  
    	            hasTrayIcon = true;
    	            new Thread(this).start();  
    	        } catch (AWTException e1){  
    	            e1.printStackTrace();  
    	        }  
    	}
    }  
    /**
     * 显示界面后处理
     * <p>方法说明:</p>
     * <li></li>
     * @auther DuanYong
     * @since 2013-3-9 上午10:59:15
     * @return void
     */
    private void visibleAfter(){
    	leftVersionInfoLabel.setText(LanguageLoader.getString("CrawlerMainFrame.version") + Config.CRAWLER_CONFIG_MAP.get(Config.CRAWLER_CONFIG_KEY_INIT).get(Config.CRAWLER_CONFIG_KEY_INIT_VERSION));
    	playMusic(Config.CRAWLER_CONFIG_MAP.get(Config.CRAWLER_CONFIG_KEY_INIT).get(Config.CRAWLER_CONFIG_KEY_INIT_SHOW_MUSIC_NAME_WELCOME));
    }
    /**  
     * 创建系统托盘的对象 步骤: 1,获得当前操作系统的托盘对象 2,创建弹出菜单popupMenu 3,创建托盘图标icon  
     * 4,创建系统的托盘对象trayIcon  
     */ 
    private void createTrayIcon()  
    {  
        sysTray = SystemTray.getSystemTray();// 获得当前操作系统的托盘对象
        PopupMenu popupMenu = new PopupMenu();// 弹出菜单  
        MenuItem mi = new MenuItem(LanguageLoader.getString("CrawlerMainFrame.Show"));  
        MenuItem exit = new MenuItem(LanguageLoader.getString("CrawlerMainFrame.Exit"));  
        popupMenu.add(mi);  
        popupMenu.add(exit);  
        // 为弹出菜单项添加事件  
        mi.addActionListener(new ActionListener() {  
        	public void actionPerformed(ActionEvent e){  
                setVisible(true);  
                setExtendedState(JFrame.NORMAL);
                //sysTray.remove(trayIcon);  
                msg = ""; // 清空内容
                flag = false;  //消息打开了  
                count = 0; 
            }  
        });  
        exit.addActionListener(new ActionListener() {  
        	public void actionPerformed(ActionEvent e){  
                System.exit(0);  
            }  
        });  
        trayIcon = new TrayIcon(ImageLoader.getImage("CrawlerResource.logo"), LanguageLoader.getString("CrawlerMainFrame.title"), popupMenu); 
        trayIcon.addMouseListener(new MouseAdapter() {
        	public void mouseClicked(MouseEvent e) {
        		// 鼠标左键单击，打开窗体
        		if (e.getButton() == MouseEvent.BUTTON1){
        			setVisible(true);  
                    setExtendedState(JFrame.NORMAL);
                    //sysTray.remove(trayIcon); 
        		} 
        		if(e.getClickCount()== 2 && flag){
        			trayIcon.displayMessage(LanguageLoader.getString("CrawlerMainFrame.showMessageTitle"), msg, TrayIcon.MessageType.INFO);   
        			flag = false;  //消息打开了  
                    count = 0;
        		}
        	}
        });
    }  
	private Container getContentPanel(){
		/** create mainPanel. */
		mainPanel = new JPanel(new BorderLayout());
		
		/** set toolBar. */
		Box northBox = Box.createVerticalBox();
		appToolBar.setAlignmentX(LEFT_ALIGNMENT);
		northBox.add(appToolBar);
		//如果显示广告
		if(Boolean.valueOf(Config.CRAWLER_CONFIG_MAP.get(Config.CRAWLER_CONFIG_KEY_INIT).get(Config.CRAWLER_CONFIG_KEY_INIT_SHOW_AD))){
			advertisementPanel.setAlignmentX(LEFT_ALIGNMENT);
			northBox.add(advertisementPanel);
		}
        mainPanel.add(northBox, BorderLayout.NORTH);
        
        /** set center panel. */
        setCenterPanel(getCenterPanel());
        
        /** set statusBar. */
        mainPanel.add(getStatusBar(), BorderLayout.SOUTH);
        CowSwingObserver.getInstance().addCrawlerListener(this);
		return mainPanel;	
	}
	public void setCenterPanel(Container centerPane){
		mainPanel.add(centerPane, BorderLayout.CENTER);
	}
	public JPanel getCenterPanel(){
		if(centerPanel == null){
			centerPanel = curtainCardPerspective.getThisPerspective();
			curtainCardPerspective.setDefaultComponent(systemTabPanel);
		}
		return centerPanel;
	}
	public JXStatusBar getStatusBar() {
		if (statusBar == null) {
			statusBar = new JXStatusBar();
			JXStatusBar.Constraint c = new JXStatusBar.Constraint(JXStatusBar.Constraint.ResizeBehavior.FILL);
			statusBar.add(getLeftStatusBar(), c);
			c = new JXStatusBar.Constraint(JXStatusBar.Constraint.ResizeBehavior.FILL);
			statusBar.add(getCenterStatusBar(), c);
			c = new JXStatusBar.Constraint(JXStatusBar.Constraint.ResizeBehavior.FILL);
			statusBar.add(getRightStatusBar(), c);
		}
		return statusBar;
	}
	/**
	 * Gets the left status bar.
	 * 
	 * @return the left status bar
	 */
	private JMenuBar getLeftStatusBar() {
		if (leftVersionInfoLabel == null) {
			leftVersionInfoLabel = new JLabel(" ");
			leftMenubar.add(leftVersionInfoLabel);
		}
		return leftMenubar;
	}
	/**
	 * Sets the left status bar.
	 * @param String
	 * @param String
	 * 
	 */
	public void setLeftStatusBar(String text, String toolTip) {
		leftVersionInfoLabel.setText(text);
		getLeftStatusBar().setToolTipText(toolTip);
	}
	
	/**
	 * Gets the right status bar.
	 * 
	 * @return the right status bar
	 */
	private JLabel getRightStatusBar() {
		if (rightStatusBar == null) {
			rightStatusBar = new JLabel(" ");
		}
		return rightStatusBar;
	}
	
	/**
	 * Sets the right status bar.
	 * @param String
	 * @param String
	 * 
	 */
	public void setRightStatusBar(String text, String toolTip) {
		getRightStatusBar().setText(text);
		getRightStatusBar().setToolTipText(toolTip);
	}
	
	/**
	 * Gets the center status bar.
	 * 
	 * @return the center status bar
	 */
	private JLabel getCenterStatusBar() {
		if (centerStatusBar == null) {
			centerStatusBar = new JLabel(" ");
		}
		return centerStatusBar;
	}
	
	/**
	 * Sets the center status bar.
	 * @param String
	 * @param String
	 * 
	 */
	public void setCenterStatusBar(String text, String toolTip) {
		getCenterStatusBar().setText(text);
		getCenterStatusBar().setToolTipText(toolTip);
	}
	/**
	 * 初始化主界面配置
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-11-5 上午10:31:49
	 * @return void
	 */
	private void ininFrameConfig() {
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
		setResizable(true); 
		// 添加窗口事件
        this.addWindowListener(new WindowAdapter() {  
        	//将托盘添加到操作系统的托盘  
            public void windowIconified(WindowEvent e){  
                addTrayIcon();  
            }  
        });   
	}
	// 关闭窗体  
	public void closeMainFrame(){  
		Toolkit.getDefaultToolkit().beep();
        int result = JOptionPane.showConfirmDialog(this, LanguageLoader.getString("CrawlerMainFrame.windowClose"), LanguageLoader.getString("CrawlerMainFrame.windowCloseTitle"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,null);  
        if(result == JOptionPane.YES_OPTION){
        	this.dispose();
        }else if(result == JOptionPane.NO_OPTION){
        	return;
        }      
    }  
	//重写这个方法  
    @Override  
    protected void processWindowEvent(WindowEvent e) {  
        if (e.getID() == WindowEvent.WINDOW_CLOSING){
        	Toolkit.getDefaultToolkit().beep();
            int result = JOptionPane.showConfirmDialog(this, LanguageLoader.getString("CrawlerMainFrame.windowClose"), LanguageLoader.getString("CrawlerMainFrame.windowCloseTitle"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,null);  
            if(result == JOptionPane.YES_OPTION){
            	this.dispose();
            }else{
            	return;//直接返回，阻止默认动作，阻止窗口关闭  
            }  	
        }
        super.processWindowEvent(e); //该语句会执行窗口事件的默认动作(如：隐藏)  
    } 
	/**
	 * 退出系统
	 */
	public void dispose() {
		logger.info("退出系统");
		CowSwingObserver.getInstance().notifyEvents(
				new CowSwingEvent(this,
						CowSwingEventType.SystemExitChangeEvent));
        super.dispose();
        //停止运行
		CowSwingContextData.getInstance().getCowSwingRunCycle().end();
        System.exit(0);
    }
	public void update(CowSwingEvent event) {
		if (event.getEventType().isAlso(CowSwingEventType.RuleTableChangeEvent)) {
			if(null != event.getEventObject() && StringUtils.isNotBlank(event.getEventObject().toString())){
				logger.info("CrawlerMainFrame---响应事件");
				flag = true; 
				msg = event.getEventObject().toString();
				getRightStatusBar().setText(msg);
			}
		}else if(event.getEventType().equals(CowSwingEventType.RuleHistoryTableAddEvent)){
			msg = event.getEventObject().toString();
			getRightStatusBar().setText(msg);
		}else if(event.getEventType().equals(CowSwingEventType.ValidDataChangeEvent)){
			msg = event.getEventObject().toString();
			getRightStatusBar().setText(msg);
		}else if(event.getEventType().equals(CowSwingEventType.CacheDataChangeEvent)){
			msg = event.getEventObject().toString();
			getRightStatusBar().setText(msg);
		}else if(event.getEventType().equals(CowSwingEventType.CacheConfigChangeEvent)){
			msg = event.getEventObject().toString();
			getRightStatusBar().setText(msg);
		}else if(event.getEventType().equals(CowSwingEventType.SaveFinishedEvent) 
				|| event.getEventType().equals(CowSwingEventType.FtpFinishedEvent)
				|| event.getEventType().equals(CowSwingEventType.DealWithImageFinishedEvent)){
			if(StringUtils.isNotBlank(event.getEventInfo())){
				msg = event.getEventInfo();
			}else if(null != event.getEventObject()){
				msg = event.getEventObject().toString();
			}
			getRightStatusBar().setText(msg);
		}
		
	}
	/**
     * 线程控制闪动、替换图片  
     */
	@Override
	public void run() {
		 while (true){  
			 if(flag){ // 有新消息  
	                try {  
	                    if(count == 1){  
	                    	//播放音乐
	                    	if(Boolean.valueOf(Config.CRAWLER_CONFIG_MAP.get(Config.CRAWLER_CONFIG_KEY_INIT).get(Config.CRAWLER_CONFIG_KEY_INIT_SHOW_MUSIC))){
	                    		playMusic(Config.CRAWLER_CONFIG_MAP.get(Config.CRAWLER_CONFIG_KEY_INIT).get(Config.CRAWLER_CONFIG_KEY_INIT_SHOW_MUSIC_NAME_MSG));
	                    	}
	                    }  
	                    this.trayIcon.setImage(ImageLoader.getImage("CrawlerResource.logo_yellow"));  
	    	            try {  
	    	                Thread.sleep(300);  
	    	            }catch (InterruptedException e){  
	    	                e.printStackTrace();  
	    	            }  
	    	            this.trayIcon.setImage(ImageLoader.getImage("CrawlerResource.logo"));  
	    	            try {  
	    	                Thread.sleep(300);  
	    	            }catch (InterruptedException e){  
	    	                e.printStackTrace();  
	    	            }  
	                } catch (Exception e) {  
	                    e.printStackTrace();  
	                }  
	                count++;  
	            }else{ // 无消息或是消息已经打开过  
	            	this.trayIcon.setImage(ImageLoader.getImage("CrawlerResource.logo"));  
	            }  
	            
	        }  
	}
	
	private void playMusic(String musicName){
		// 播放消息提示音   
        try {  
            AudioClip p = Applet.newAudioClip(new URL("file:"+Constant.PACKGE_SOUNDS+musicName));  
            p.play();  
        } catch (MalformedURLException e) {  
            e.printStackTrace();  
        } 
	}
}
