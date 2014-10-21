package org.javacoo.cowswing.ui.view.dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.annotation.Resource;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import org.javacoo.cowswing.base.loader.LanguageLoader;
import org.javacoo.cowswing.ui.view.panel.ViewDeatilPanel;
import org.javacoo.cowswing.ui.view.panel.ViewImagePanel;
import org.springframework.stereotype.Component;


/**
 * 查看详细
 * 
 * @author javacoo
 * @since 2012-03-14
 */
@Component("viewDialog")
public class ViewDialog extends JDialog{
	private static final long serialVersionUID = 1L;
	// 窗体高度大小
	private static final int FRAME_WIDTH = 650;
	private static final int FRAME_HEIGHT = 550;
	
	@Resource(name="viewDeatilPanel")
	private ViewDeatilPanel viewDeatilPanel;
	
	@Resource(name="viewImagePanel")
	private ViewImagePanel viewImagePanel;
	
	/**
	 * 内容对话框
	 */
//	@Resource(name="contentDialog")
//	private ContentDialog contentDialog;
	
	private JComponent centerPane;
	
	private Dimension dimensions = Toolkit.getDefaultToolkit().getScreenSize();

	private int left = (dimensions.width - FRAME_WIDTH) / 2;
	private int top = (dimensions.height - FRAME_HEIGHT) / 2;
	public ViewDialog(){
		setLayout(new BorderLayout());
		//setLocationRelativeTo(contentDialog);
		setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		setLocation(left, top);
		setTitle(LanguageLoader.getString("ContentList.contentDetail"));
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setPreferredSize(getPreferredSize());
	}
	public void setViewSize(int width,int height){
		setSize(new Dimension(width, height));
	}
	public void showContent(String content){
		if(!(centerPane instanceof ViewDeatilPanel)){
			if(null != centerPane){
				this.remove(centerPane);
			}
			centerPane = viewDeatilPanel;
			add(centerPane);
		}
		viewDeatilPanel.showContent(content);
	}
	public void showImage(String imagePath){
		if(!(centerPane instanceof ViewImagePanel)){
			if(null != centerPane){
				this.remove(centerPane);
			}
			centerPane = viewImagePanel;
			add(centerPane);
		}
		viewImagePanel.showImage(imagePath);
		final ViewDialog viewDialog = this;
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Thread currThread = new Thread(viewImagePanel);
				currThread.start();
				Thread waitingThread = new Thread(new WaitingDialog(viewDialog,currThread,LanguageLoader.getString("ViewDetail.loadImage")));
				waitingThread.start();
			}
		});
	}
	
}
