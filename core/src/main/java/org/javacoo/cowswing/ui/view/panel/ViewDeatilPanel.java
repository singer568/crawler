/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.cowswing.ui.view.panel;

import java.awt.BorderLayout;

import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 显示内容详细
 *@author DuanYong
 *@since 2012-12-5下午2:54:07
 *@version 1.0
 */
@Component("viewDeatilPanel")
@Scope("prototype")
public class ViewDeatilPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	private JEditorPane containerPanel;
    public ViewDeatilPanel(){
    	super();
    	init();
    }
    private void init(){
    	BorderLayout layout = new BorderLayout();
		this.setLayout(layout);
		containerPanel = new JEditorPane();
		containerPanel.setEditable(false);
		containerPanel.setContentType("text/html");
		JScrollPane topScrollPane = new JScrollPane(containerPanel);
		this.add(topScrollPane, BorderLayout.CENTER);
    }
   
    /**
     * 显示内容
     * <p>方法说明:</p>
     * @auther DuanYong
     * @since 2012-12-5 下午3:11:26
     * @param content
     * @return void
     */
    public void showContent(String content){
    	containerPanel.setText(content);
    }
}
