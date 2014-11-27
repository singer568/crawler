/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.bjm.pms.crawler.view.ui.view.panel;

import java.awt.BorderLayout;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bjm.pms.crawler.view.base.loader.LanguageLoader;
import com.bjm.pms.crawler.view.core.event.CowSwingEvent;
import com.bjm.pms.crawler.view.ui.widget.SingleRowTabbedPane;


/**
 * 系统TabPanel
 * <p>
 * 说明:
 * </p>
 * <li></li>
 * 
 * @author DuanYong
 * @since 2013-4-25 下午2:38:39
 * @version 1.0
 */
@Component("systemTabPanel")
public class SystemTabPanel extends JPanel implements IPage {
	private static final long serialVersionUID = 1L;
	private SingleRowTabbedPane tabbedPane;
    @Autowired
	public SystemTabPanel(List<ITabPanel> tabPanelList) {
		setLayout(new BorderLayout());
		tabbedPane = new SingleRowTabbedPane(
				SingleRowTabbedPane.FOUR_BUTTONS, SwingConstants.LEFT);
		tabbedPane.setTabPlacement(JTabbedPane.BOTTOM);
		Collections.sort(tabPanelList,new Comparator<ITabPanel>(){ 
	        public int compare(ITabPanel tabl, ITabPanel tab2) {  
	            if(null!=tabl&&null!=tab2) {    
	                if(tabl.getTabPanelIndex() > tab2.getTabPanelIndex()){  
	                    return 1;  
	                }else {  
	                    return 0;  
	                }  
	            }  
	            return 0;  
	        }   
	    });
		for (ITabPanel panel : tabPanelList) {
			tabbedPane.addTab(panel.getTanPanelName(), panel.getPanel());
			panel.init();
		}
		tabbedPane.setSelectedIndex(0);
		add(tabbedPane, BorderLayout.CENTER);
	}
	
	/**
	 * @return the tabbedPane
	 */
	public SingleRowTabbedPane getTabbedPane() {
		return tabbedPane;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.javacoo.crawler.ui.view.panel.IPage#getPageId()
	 */
	@Override
	public String getPageId() {
		return this.getClass().getName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.javacoo.crawler.ui.view.panel.IPage#getPageName()
	 */
	@Override
	public String getPageName() {
		return LanguageLoader.getString("Home.systemWelcome");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.javacoo.crawler.ui.view.panel.IPage#disposePage()
	 */
	@Override
	public void disposePage() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.javacoo.crawler.ui.view.panel.IPage#isDefaultPage()
	 */
	@Override
	public boolean isDefaultPage() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.javacoo.crawler.ui.view.panel.IPage#setDefaultPage(boolean)
	 */
	@Override
	public void setDefaultPage(boolean bool) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.javacoo.crawler.ui.view.panel.IPage#showData(int, int)
	 */
	@Override
	public void showData(int pageNumber, int pageSize) {
		// TODO Auto-generated method stub

	}
	/* (non-Javadoc)
	 * @see org.javacoo.cowswing.ui.view.panel.IPage#init()
	 */
	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

}
