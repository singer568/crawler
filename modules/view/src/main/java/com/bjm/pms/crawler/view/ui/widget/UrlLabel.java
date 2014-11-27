/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.bjm.pms.crawler.view.ui.widget;

import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

/**
 * 超链接label
 * <p>
 * 说明:
 * </p>
 * <li></li>
 * 
 * @author DuanYong
 * @since 2013-10-26 下午5:37:36
 * @version 1.0
 */
public class UrlLabel extends JLabel {
	private static final long serialVersionUID = 1L;
	private String text, url;
	private boolean isSupported;

	public UrlLabel(String text, String url) {
		this.text = text;
		this.url = url;
		try {
			this.isSupported = Desktop.isDesktopSupported()
					&& Desktop.getDesktop().isSupported(Desktop.Action.BROWSE);
		} catch (Exception e) {
			this.isSupported = false;
		}
		setText(false);
		addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				setText(isSupported);
				if (isSupported)
					setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			public void mouseExited(MouseEvent e) {
				setText(false);
			}

			public void mouseClicked(MouseEvent e) {
				try {
					Desktop.getDesktop().browse(
							new java.net.URI(UrlLabel.this.url));
				} catch (Exception ex) {
				}
			}
		});
	}

	private void setText(boolean b) {
		if (!b){
			//setText("<html><font color=blue><u>" + text);
			setText(text);
		}else{
			setText("<html><font color=red><u>" + text);
		}
	}
}
