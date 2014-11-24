/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.cowswing.plugin.core.ui.view.panel;
import java.awt.FlowLayout;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.apache.commons.lang.StringUtils;
import org.javacoo.cowswing.base.constant.Constant;
import org.javacoo.cowswing.base.loader.ImageLoader;
import org.springframework.stereotype.Component;

/**
 * 广告
 *@author DuanYong
 *@since 2013-2-28下午10:14:53
 *@version 1.0
 */
@Component("advertisementPanel")
public class AdvertisementPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	private JPanel containerPanel;
	private String currImagePath;
	
	public AdvertisementPanel(){
		super();
		init();
	}
	private void init() {
		FlowLayout layout = new FlowLayout(FlowLayout.LEFT ,0, 0);
		this.setLayout(layout);
		containerPanel = new JPanel();
		this.add(containerPanel);
		repaintImage();
	}
	public void setImagePath(String imagePath) {
		this.currImagePath = imagePath;
		repaintImage();
	}
	public String getImagePath(){
		return this.currImagePath;
	}
	
	private void repaintImage(){
		try {
			containerPanel.removeAll();
			ImageIcon tempImage = null;
			if(StringUtils.isBlank(this.currImagePath)){
				tempImage = ImageLoader.getImageIcon("CrawlerResource.defaultAd");
			}else{
				if (this.currImagePath.startsWith(Constant.HTTP) || this.currImagePath.startsWith(Constant.HTTPS)) {
					tempImage = new ImageIcon(new URL(this.currImagePath));
				}else{
					tempImage = new ImageIcon(this.currImagePath);
				}
			}
			final ImageIcon image = tempImage;
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					containerPanel.add(new JLabel(image));
					containerPanel.repaint();
					containerPanel.doLayout();
				}
			});
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

}
