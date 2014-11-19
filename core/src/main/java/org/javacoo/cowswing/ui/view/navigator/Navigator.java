
package org.javacoo.cowswing.ui.view.navigator;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

import org.apache.commons.collections.CollectionUtils;
import org.javacoo.cowswing.base.loader.LanguageLoader;
import org.javacoo.cowswing.ui.widget.CurtainPane;
import org.springframework.stereotype.Component;



/**
 * 导航
 *@author DuanYong
 *@since 2012-11-5下午2:39:24
 *@version 1.0
 */
@Component("navigator")
public class Navigator {

	private JPanel cutainPane;
	private CurtainPane cp;
	public JPanel getCurtainPanelView() {
		if (cutainPane == null) {
			cutainPane = new JPanel(new BorderLayout());
			cp = getCurtainPane();
			//show the first layer. 
			cutainPane.add(cp, BorderLayout.CENTER);

			JCheckBox box = getAnimationCheckBox(cp);
			cutainPane.add(box, BorderLayout.SOUTH);
		}

		return cutainPane;
	}
	/**
	 * 加载插件导航栏
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-12-13 下午9:56:25
	 * @param navigatorList
	 * @return void
	 */
	public void loadPluginNavigator(List<NavigatorItem> navigatorList){
		if(CollectionUtils.isNotEmpty(navigatorList)){
			for(NavigatorItem item : navigatorList){
				cp.addPane(item.getTitle(), item.getIcon(), item.getComp());
			}
		}
		cp.setSelectedPane(0);
	}
	
	private CurtainPane getCurtainPane() {
		CurtainPane fp = new CurtainPane();
		fp.setAnimated(true);
		return fp;
	}
	
	private JCheckBox getAnimationCheckBox(final CurtainPane cp) {
		final JCheckBox box = new JCheckBox(LanguageLoader.getString("CrawlerMainFrame.enableAnimation"));
		box.setSelected(true);
		box.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cp.setAnimated(box.isSelected());
			}
		});
		return box;
	}

}
