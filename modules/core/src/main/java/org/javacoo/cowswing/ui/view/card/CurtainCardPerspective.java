

package org.javacoo.cowswing.ui.view.card;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import org.javacoo.cowswing.ui.view.navigator.Navigator;
import org.javacoo.cowswing.ui.view.navigator.NavigatorItem;
import org.javacoo.cowswing.ui.view.panel.IPage;
import org.javacoo.cowswing.ui.view.panel.PageContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("curtainCardPerspective")
public class CurtainCardPerspective {
	/**页面容器*/
    private PageContainer pageContainer;
    /**导航栏*/
    private Navigator navigator;
	/**主面板*/
	private JPanel thisPerspective;
	/**分隔面板*/
	private static JSplitPane splitPanel;
	@Autowired
	public CurtainCardPerspective(PageContainer pageContainer,Navigator navigator){
		this.pageContainer = pageContainer;
		this.navigator = navigator;
		if(thisPerspective == null){
			thisPerspective = new JPanel(new BorderLayout());
			thisPerspective.add(getSplitPanel());
		}
	}
	
	public JPanel getThisPerspective() {
		return thisPerspective;
	}

	public void setThisPerspective(JPanel thisPerspective) {
		this.thisPerspective = thisPerspective;
	}

	
	private JSplitPane getSplitPanel(){
		if(splitPanel == null){
			splitPanel = new JSplitPane();
			splitPanel.setOrientation(JSplitPane.HORIZONTAL_SPLIT );
			//splitPanel.setAutoscrolls(true);
			splitPanel.setDividerLocation(0.35);
			splitPanel.setOneTouchExpandable(true);
			splitPanel.setLeftComponent(navigator.getCurtainPanelView());
			splitPanel.setRightComponent(pageContainer);		
		}
		
		return splitPanel;
	}
	
	public void setDefaultComponent(IPage cardComponet){
		pageContainer.setDefaultPage(cardComponet);
	}
	
	public void loadPluginNavigator(List<NavigatorItem> navigatorList){
		navigator.loadPluginNavigator(navigatorList);
	}
	
	
}
