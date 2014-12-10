package com.bjm.pms.crawler.plugin.tool.ui.view.panel;

import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.commons.lang.StringUtils;

import com.bjm.pms.crawler.plugin.tool.ui.DealWithImage;
import com.bjm.pms.crawler.plugin.tool.ui.action.AddImageAction;
import com.bjm.pms.crawler.plugin.tool.ui.action.AddImageDirAction;
import com.bjm.pms.crawler.plugin.tool.ui.action.DealWithImageAction;
import com.bjm.pms.crawler.plugin.tool.ui.action.DeleteAllImageAction;
import com.bjm.pms.crawler.plugin.tool.ui.action.DeleteImageAction;
import com.bjm.pms.crawler.plugin.tool.ui.action.ShowImageSettingAction;
import com.bjm.pms.crawler.plugin.tool.ui.bean.ImageBean;
import com.bjm.pms.crawler.plugin.tool.ui.bean.ImageSettingBean;
import com.bjm.pms.crawler.plugin.tool.ui.model.ImageTabelModel;
import com.bjm.pms.crawler.view.base.loader.LanguageLoader;
import com.bjm.pms.crawler.view.core.event.CowSwingEvent;
import com.bjm.pms.crawler.view.core.event.CowSwingEventType;
import com.bjm.pms.crawler.view.main.CowSwingMainFrame;
import com.bjm.pms.crawler.view.ui.util.ColumnResizer;
import com.bjm.pms.crawler.view.ui.view.dialog.RunnableProgress;
import com.bjm.pms.crawler.view.ui.view.dialog.WaitingDialog;
import com.bjm.pms.crawler.view.ui.view.dialog.WaitingProgressDialog;
import com.bjm.pms.crawler.view.ui.view.panel.AbstractListPage;

/**
 * 待处理图片列表panel
 *@author DuanYong
 *@since 2012-12-14下午10:45:53
 *@version 1.0
 */
@org.springframework.stereotype.Component("imageListPanel")
public class ImageListPanel extends AbstractListPage implements ListSelectionListener {
	private static final long serialVersionUID = 1L;
	/**
	 * 添加图片按钮
	 */
	private JButton addButton;
	/**
	 * 添加图片目录按钮
	 */
	private JButton addDirButton;
	/**
	 * 删除图片按钮
	 */
	private JButton deleteButton;
	/**
	 * 图片按钮
	 */
	private JButton deleteAllButton;
	/**
	 * 属性设置按钮
	 */
	private JButton settingButton;
	/**
	 * 处理图片
	 */
	private JButton dealWithButton;

	/**
	 * 内容Table
	 */
	private JTable imageTable;
	/**
	 * 删除图片Action
	 */
	@Resource(name="deleteImageAction")
	private DeleteImageAction deleteImageAction;
	/**
	 * 清空图片Action
	 */
	@Resource(name="deleteAllImageAction")
	private DeleteAllImageAction deleteAllImageAction;
	/**
	 * 图片属性设置Action
	 */
	@Resource(name="showImageSettingAction")
	private ShowImageSettingAction showImageSettingAction;
	/**
	 * 执行处理图片Action
	 */
	@Resource(name="dealWithImageAction")
	private DealWithImageAction dealWithImageAction;
	
	/**
	 * 添加图片Action
	 */
	@Resource(name="addImageAction")
	private AddImageAction addImageAction;
	/**
	 * 添加图片目录Action
	 */
	@Resource(name="addImageDirAction")
	private AddImageDirAction addImageDirAction;
	
	@Resource(name="cowSwingMainFrame")
	private CowSwingMainFrame cowSwingMainFrame;
	@Resource(name="imageSettingPanel")
	private ImageSettingPanel imageSettingPanel;
	
	public ImageListPanel() {
		super();
	}
	
	

	@Override
	protected JComponent getTopPane() {
		super.getTopPane();
		buttonBar.add(getAddButton());
		buttonBar.add(getAddDirButton());
		buttonBar.add(getDeleteButton());
		buttonBar.add(getDeleteAllButton());
		buttonBar.add(getSettingButton());
		buttonBar.add(getDealWithButton());
		return buttonBar;
	}

	@Override
	protected JComponent getCenterPane() {
		return new JScrollPane(getImageTable());
	}

	private JButton getDeleteButton() {
		if (deleteButton == null) {
			deleteButton = new JButton(deleteImageAction);
		}
		return deleteButton;
	}
	private JButton getDeleteAllButton() {
		if (deleteAllButton == null) {
			deleteAllButton = new JButton(deleteAllImageAction);
		}
		return deleteAllButton;
	}
	private JButton getSettingButton() {
		if (settingButton == null) {
			showImageSettingAction.setImageSettingPanel(imageSettingPanel);
			settingButton = new JButton(showImageSettingAction);
		}
		return settingButton;
	}
	private JButton getDealWithButton() {
		if (dealWithButton == null) {
			dealWithImageAction.setImageSettingPanel(imageSettingPanel);
			dealWithButton = new JButton(dealWithImageAction);
		}
		return dealWithButton;
	}

	private JButton getAddButton() {
		if (addButton == null) {
			addButton = new JButton(addImageAction);
		}
		return addButton;
	}
	
	private JButton getAddDirButton() {
		if (addDirButton == null) {
			addDirButton = new JButton(addImageDirAction);
		}
		return addDirButton;
	}

	
	public JTable getImageTable() {
		if (imageTable == null) {
			imageTable = new JTable();
			final ImageTabelModel dataModel = new ImageTabelModel(
					getColumnNames());
			imageTable.setModel(dataModel);
			imageTable.setPreferredScrollableViewportSize(new Dimension(
					500, 70));
			imageTable.setFillsViewportHeight(true);
			imageTable.setRowHeight(93);
			for(int i = 0 ;i<dataModel.getColumnCount();i++){
				imageTable.getColumnModel().getColumn(i).setPreferredWidth(100);
			}
			imageTable.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						@Override
						public void valueChanged(ListSelectionEvent e) {
							SwingUtilities.invokeLater(new Runnable() {
								public void run() {
									if (imageTable.getSelectedRow() != -1) {
										deleteButton.setEnabled(true);
									} else {
										deleteButton.setEnabled(false);
									}
								}
							});
						}
					});

			imageTable.setAutoCreateRowSorter(true);
		}

		return imageTable;
	}

	private List<String> getColumnNames() {
		List<String> columnNames = new ArrayList<String>();
		columnNames.add(LanguageLoader.getString("ToolImage.listTitle"));
		columnNames.add(LanguageLoader.getString("ToolImage.listType"));
		columnNames.add(LanguageLoader.getString("ToolImage.listSize"));
		columnNames.add(LanguageLoader.getString("ToolImage.listPath"));
		return columnNames;
	}

	public void showData(int startIndex,int pageSize) {
		loadData(getData(startIndex,pageSize));
	}

	public List<ImageBean> getData(int startIndex,int pageSize) {
		
		return new ArrayList<ImageBean>();
	}
	
	public void loadData(List<ImageBean> data){
		final JTable tempTable = getImageTable();
		final ImageTabelModel dataModel = (ImageTabelModel) tempTable.getModel();
		dataModel.setData(data);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				ColumnResizer.adjustColumnPerferredWidths(tempTable);
				//开启线程加载图片
				Thread currThread = new Thread(dataModel);
				currThread.start();
				Thread waitingThread = new Thread(new WaitingDialog(cowSwingMainFrame,currThread,LanguageLoader.getString("ViewDetail.loadImageList")));
				waitingThread.start();
			}
		});
	}
	
	public void changeState(){
		ImageTabelModel dataModel = (ImageTabelModel)getImageTable().getModel();
		if (dataModel.getData().size() > 0) {
			showImageSettingAction.setEnabled(true);
			dealWithImageAction.setEnabled(true);
			deleteAllImageAction.setEnabled(true);
		} else {
			showImageSettingAction.setEnabled(false);
			dealWithImageAction.setEnabled(false);
			deleteAllImageAction.setEnabled(false);
		}
	}
	/**
	 * 处理图片
	 * <p>方法说明:</p>
	 * <li></li>
	 * @auther DuanYong
	 * @since 2013-1-26 上午10:42:46
	 * @param data
	 * @param imageSettingBean
	 * @return void
	 */
	public void dealWith(ImageSettingBean imageSettingBean){
		if(StringUtils.isBlank(imageSettingBean.getSavePath())){
			JOptionPane.showMessageDialog(null, LanguageLoader.getString("ToolImage.imageDealWithNoConfig"), LanguageLoader.getString("ToolImage.imageDealWithNoConfigTitle"), JOptionPane.CLOSED_OPTION);
		}else{
			ImageTabelModel dataModel = (ImageTabelModel)getImageTable().getModel();
			if(dataModel.getData().size() > 0){
				final RunnableProgress dealWithImage = new DealWithImage(dataModel.getData(),imageSettingBean);
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						//开启线程处理图片
						Thread currThread = new Thread(dealWithImage);
						currThread.start();
						Thread waitingThread = new Thread(new WaitingProgressDialog(cowSwingMainFrame,currThread,LanguageLoader.getString("ToolImage.imageListDealWithing")));
						waitingThread.start();
					}
				});
			}
		}
	}
	
	public void deleteByName(String name){
		
	}

	@Override
	public void update(CowSwingEvent event) {
		logger.info("ImageListPanel---响应事件");
		if (event.getEventType().equals(CowSwingEventType.LoadImagesChangeEvent) || event.getEventType().equals(CowSwingEventType.DeleteImagesChangeEvent)) {
			initData();
		}
	}

	@Override
	public String getPageName() {
		return LanguageLoader.getString("ToolImage.imageList");
	}

	@Override
	public void disposePage() {
		super.disposePage();
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		logger.info("ListSelectionEvent---响应事件");
	}
	
	protected Component getBottomPane() {
		return null;
	}
    
	

}
