/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.bjm.pms.crawler.view.ui.view.panel;

import java.awt.BorderLayout;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bjm.pms.crawler.view.base.constant.Constant;

/**
 * 显示图片
 *  
 * @author DuanYong
 * @since 2012-12-11下午4:24:54
 * @version 1.0
 */
@Component("viewImagePanel")
@Scope("prototype")
public class ViewImagePanel extends JPanel implements Runnable{
	private static final long serialVersionUID = 1L;
	private JPanel containerPanel;
    private Map<String,ImageIcon> imagesCache;
    private String currImagePath;
	public ViewImagePanel() {
		super();
		init();
	} 
	private void init() {
		BorderLayout layout = new BorderLayout();
		this.setLayout(layout);
		containerPanel = new JPanel();
		JScrollPane topScrollPane = new JScrollPane(containerPanel);
		this.add(topScrollPane, BorderLayout.CENTER);
		imagesCache = new ConcurrentHashMap<String,ImageIcon>();
	}

	/**
	 * 显示图片
	 * <p>
	 * 方法说明:
	 * </p>
	 * 
	 * @auther DuanYong
	 * @since 2012-12-11 下午4:28:22
	 * @param content
	 * @return void
	 */
	public void showImage(String imagePath) {
		this.currImagePath = imagePath;
	}
	
	private void initImagesCache(){
		try {
			containerPanel.removeAll();
			if(!getImagesCache().containsKey(this.currImagePath)){
				ImageIcon image = null;
				if (this.currImagePath.startsWith(Constant.HTTP) || this.currImagePath.startsWith(Constant.HTTPS)) {
					image = new ImageIcon(new URL(this.currImagePath));
				}else{
					image = new ImageIcon(this.currImagePath);
				}
				getImagesCache().put(this.currImagePath, image);
			}
			final String path = this.currImagePath;
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					containerPanel.add(new JLabel(getImagesCache().get(path)));
					containerPanel.repaint();
					containerPanel.doLayout();
				}
			});
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
    
	public Map<String, ImageIcon> getImagesCache() {
		return imagesCache;
	}

	public void setImagesCache(Map<String, ImageIcon> imagesCache) {
		this.imagesCache = imagesCache;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		initImagesCache();
	}
	
	
}
