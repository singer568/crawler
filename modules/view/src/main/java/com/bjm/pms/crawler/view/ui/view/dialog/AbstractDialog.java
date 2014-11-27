package com.bjm.pms.crawler.view.ui.view.dialog;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.AbstractButton;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.LayoutStyle;
import javax.swing.WindowConstants;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.bjm.pms.crawler.view.base.loader.LanguageLoader;
import com.bjm.pms.crawler.view.core.event.CowSwingEvent;
import com.bjm.pms.crawler.view.core.event.CowSwingListener;
import com.bjm.pms.crawler.view.core.event.CowSwingObserver;

/**
 * 基类对话框
 * @author DuanYong
 * @since 2012-11-7下午8:59:30
 * @version 1.0
 */
public abstract class AbstractDialog extends JDialog implements CowSwingListener {
	private static final long serialVersionUID = 1L;
	protected Logger logger = Logger.getLogger(this.getClass());
    private boolean hasInit = false;

	/** buttonPanel. */
	private JPanel buttonPanel;
	private JSeparator buttonSeparator;
	private JButton finishButton;
	private JButton cancelButton;
	private JButton helpButton;

	/** Whether created the buttons or not. */
	private boolean showFinishButton = true;
	private boolean showCancelButton = true;
	private boolean showHelpButton = false;

	/** centerPane. */
	protected JComponent centerPane;

	private Hashtable<String, AbstractButton> buttonMap;
	protected Vector<AbstractButton> buttonVector;

	private static int BUTTON_PREFERRED_SIZE = 73;

	protected String errorMessage = "";
	

	// 窗体高度大小
	private static final int FRAME_WIDTH = 750;
	private static final int FRAME_HEIGHT = 650;
    
	public AbstractDialog() {
		this(FRAME_WIDTH, FRAME_HEIGHT, true);
		this.setPreferredSize(getPreferredSize());
	}
	public AbstractDialog(int width, int height,boolean modal) {
		setLayout(new BorderLayout());
		buttonMap = new Hashtable<String, AbstractButton>();
		buttonVector = new Vector<AbstractButton>();
		setSize(new Dimension(width, height));
		setModalityType(modal ? ModalityType.APPLICATION_MODAL
				: ModalityType.MODELESS);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}
	/**
	 * 初始化
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-12-2 下午9:29:21
	 * @param owner
	 * @param type
	 * @param title
	 * @return void
	 */
	public void init(Component owner,String type,String title){
		initComponents();
		setTitle(title);
		initData(type);
		initState();
		setLocationRelativeTo(owner);
		// 添加监听事件
		CowSwingObserver.getInstance().addCrawlerListener(this);
	}
    
	protected void initState() {

	}
    /**
     * 初始化数据
     * <p>方法说明:</p>
     * @auther DuanYong
     * @since 2012-11-20 上午10:27:54
     * @return void
     */
	protected void initData(String type) {

	}

	public void initComponents() {
        if(!hasInit){
        	setButtonPanel(getButtonPanel());
    		setCenterPane(getCenterPane());
    		hasInit = true;
        }
	}

	

	public void setCenterPane(Component contentPanel) {
		add(contentPanel, BorderLayout.CENTER);
	}

	public void setButtonPanel(JPanel buttonPanel) {
		add(buttonPanel, BorderLayout.PAGE_END);
	}



	protected JComponent getCenterPane() {
		if (centerPane == null) {
			centerPane = new JPanel();
		}
		return centerPane;
	}



	private JPanel getButtonPanel() {
		if (buttonPanel == null) {
			buttonPanel = new JPanel();
			buttonSeparator = new JSeparator();
		}
		// refresh layout.
		try {
			layoutButtonPanel();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return buttonPanel;
	}

	protected void layoutButtonPanel() {
		if (showHelpButton) {
			getHelpButton();
		}
		if (showCancelButton) {
			getCancelButton();
		}
		if (showFinishButton) {
			getFinishButton();
		}

		// Computing the preferred size.
		for (int i = buttonVector.size() - 1; i >= 0; i--) {
			Dimension size = buttonVector.elementAt(i).getPreferredSize();
			if (size.width > BUTTON_PREFERRED_SIZE) {
				BUTTON_PREFERRED_SIZE = size.width;
			}
		}

		GroupLayout buttonPanelLayout = new GroupLayout(buttonPanel);
		buttonPanel.setLayout(buttonPanelLayout);
		GroupLayout.ParallelGroup hGroup = buttonPanelLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING);

		hGroup.addComponent(buttonSeparator, GroupLayout.DEFAULT_SIZE, 589,
				Short.MAX_VALUE);

		GroupLayout.SequentialGroup hGroupSeq = buttonPanelLayout
				.createSequentialGroup();
		hGroupSeq.addContainerGap(429, Short.MAX_VALUE);
		for (int i = buttonVector.size() - 1; i >= 0; i--) {
			hGroupSeq.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED);
			hGroupSeq.addComponent(buttonVector.get(i),
					GroupLayout.PREFERRED_SIZE, BUTTON_PREFERRED_SIZE,
					GroupLayout.PREFERRED_SIZE);
		}
		hGroupSeq.addContainerGap();

		hGroup.addGroup(GroupLayout.Alignment.TRAILING, hGroupSeq);
		buttonPanelLayout.setHorizontalGroup(hGroup);

		GroupLayout.ParallelGroup vGroup = buttonPanelLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING);

		GroupLayout.SequentialGroup vGroupSeq = buttonPanelLayout
				.createSequentialGroup();
		vGroupSeq.addComponent(buttonSeparator, GroupLayout.PREFERRED_SIZE, 10,
				GroupLayout.PREFERRED_SIZE);
		vGroupSeq.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED,
				GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE);

		GroupLayout.ParallelGroup vGroupPar = buttonPanelLayout
				.createParallelGroup(GroupLayout.Alignment.BASELINE);

		for (int i = buttonVector.size() - 1; i >= 0; i--) {
			vGroupPar.addComponent(buttonVector.get(i));
		}
		vGroupSeq.addGroup(vGroupPar);
		vGroupSeq.addContainerGap();
		vGroup.addGroup(vGroupSeq);

		buttonPanelLayout.setVerticalGroup(vGroup);
	}

	public JButton getHelpButton() {
		if (helpButton == null) {
			helpButton = new JButton();
			helpButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					helpButtonActionPerformed(event);
				}
			});

			helpButton.setText(LanguageLoader.getString("Common.help"));
			putButton(helpButton);
		}
		return helpButton;
	}

	public JButton getCancelButton() {
		if (cancelButton == null) {
			cancelButton = new JButton();
			cancelButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					cancelButtonActionPerformed(event);
				}
			});

			cancelButton.setText(LanguageLoader.getString("Common.cancel"));
			cancelButton.setToolTipText("Cancel this task.");
			putButton(cancelButton);
		}
		return cancelButton;
	}

	public JButton getFinishButton() {
		if (finishButton == null) {
			finishButton = new JButton();
			finishButton.setText(LanguageLoader.getString("Common.finish"));
			finishButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					finish(event);
				}
			});
			putButton(finishButton);
		}
		return finishButton;
	}

	public void putButton(AbstractButton button) {
		buttonMap.put(button.getText(), button);
		buttonVector.add(button);

	}

	/** abstract. */
	protected abstract void finishButtonActionPerformed(ActionEvent event);
	
	protected void finish(ActionEvent event){
		if(StringUtils.isNotBlank(errorMsg())){
			JOptionPane.showMessageDialog(null, LanguageLoader.getString("Common.alertMsg")+errorMsg(), LanguageLoader.getString("Common.alertTitle"), JOptionPane.CLOSED_OPTION);
			return;
		}
		finishButtonActionPerformed(event);
	}
	protected String errorMsg(){
		return "";
	}

	protected void helpButtonActionPerformed(ActionEvent event) {
		this.dispose();
	}

	protected void cancelButtonActionPerformed(ActionEvent event) {
		this.dispose();
	}

	public void dispose(){
		super.dispose();
		// 删除监听事件
		CowSwingObserver.getInstance().removeCrawlerListener(this);
		centerPane = null;
	}
	@Override
	public void update(CowSwingEvent event) {
		
	}
	

}
