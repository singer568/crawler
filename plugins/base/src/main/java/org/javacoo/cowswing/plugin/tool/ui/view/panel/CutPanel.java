/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.cowswing.plugin.tool.ui.view.panel;

import javax.swing.JPanel;

import org.javacoo.cowswing.base.constant.Constant;
import org.javacoo.cowswing.plugin.tool.ui.bean.ImageSettingBean;


import java.awt.Graphics;

/**
 * 剪切panel
 *@author DuanYong
 *@since 2012-12-27下午9:46:47
 *@version 1.0
 */
public class CutPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	/**参数bean*/
	private ImageSettingBean imageSettingBean;
	private boolean visible = true;
	
    /**
     * 初始化
     * <p>方法说明:</p>
     * @auther DuanYong
     * @since 2012-12-26 下午5:55:02
     * @param imageSettingBean
     * @return void
     */
    private void initCutPanel(){
    	if(null == this.imageSettingBean || !visible){
    		this.setBounds(0, 0, 100,100);
    		this.setVisible(true);
    		this.getParent().repaint();
    	}
    } 
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(Constant.YES.equals(this.imageSettingBean.getCutCehckValue())){
			this.setBounds(this.imageSettingBean.getCutPanelX(),this.imageSettingBean.getCutPanelY(), imageSettingBean.getCutWidth(),imageSettingBean.getCutHeight());
			visible = true;
		}else{
			this.setVisible(false);
			visible = false;
		}
	}

	/**
	 * 根据参数重新绘制图片
	 * <p>方法说明:</p>
	 * @auther DuanYong
	 * @since 2012-12-26 下午5:53:06
	 * @param imageSettingBean
	 * @return void
	 */
	public void repaintCutImage(ImageSettingBean imageSettingBean){
		initCutPanel();
		this.imageSettingBean = imageSettingBean;
		this.repaint();
		System.out.println("000000");
	}
	
}
