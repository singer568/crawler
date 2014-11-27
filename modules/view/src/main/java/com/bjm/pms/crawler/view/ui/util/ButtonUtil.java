/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.bjm.pms.crawler.view.ui.util;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * 按钮工具类
 * @author DuanYong
 * @since 2012-12-8下午10:13:25
 * @version 1.0
 */
public class ButtonUtil {
	public static JButton makeNavigationButton(String imgLocation,
			String actionCommand, String toolTipText, String altText,
			ActionListener listener) {
		// Look for the image.

		// Create and initialize the button.
		JButton button = new JButton();
		button.setActionCommand(actionCommand);
		button.setToolTipText(toolTipText);
		button.addActionListener(listener);
		if (altText != null) {
			button.setText(altText);
		}
		if (imgLocation != null) {
			// button.setIconTextGap(10);
			URL imageURL = ButtonUtil.class.getClassLoader().getResource(
					imgLocation);
			if (imageURL != null) {
				button.setIcon(new ImageIcon(imageURL, altText));
				button.setSize(new Dimension(1, 1));

				// setOpaque(false);// image found
				button.setBorderPainted(false);
				// button.setContentAreaFilled(false);
				// button.setRolloverEnabled(true);
				button.setVerticalTextPosition(JButton.BOTTOM);
				button.setHorizontalTextPosition(JButton.CENTER);
				button.setIconTextGap(0);

			} else { // no image found
				System.err.println("Resource not found: " + imgLocation);
			}

		}

		return button;
	}
}
