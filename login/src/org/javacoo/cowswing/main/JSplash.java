/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package org.javacoo.cowswing.main;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;

import org.javacoo.cowswing.base.utils.ImageManager;
import org.javacoo.cowswing.ui.util.SwingUtils;
import org.javacoo.cowswing.ui.widget.JSplashLabel;

/**
 * 
 * <p>
 * 说明:
 * </p>
 * <li></li>
 * 
 * @author DuanYong
 * @since 2013-10-8 上午10:05:48
 * @version 1.0
 */
public class JSplash extends JWindow implements Splash {
	private static final long serialVersionUID = 1L;

	/**
	 * Progress bar to use in the splash screen.
	 */
	private JProgressBar m_progress = null;

	/**
	 * Check for whether to use the progress bar or not.
	 */
	private boolean m_progressBar = false;

	/**
	 * Check for whether to use progress bar messages or not.
	 */
	private boolean m_progressBarMessages = false;

	/**
	 * Check for whether to use precentage values or not.
	 */
	private boolean m_progressBarPercent = false;

	private int total_progress = 0;
	
	private static JSplash instance = new JSplash();

	// public JSplash(URL url,
	// boolean progress, boolean messages, boolean percent,
	// String versionString, Font versionStringFont, Color versionStringColor)
	
	public static JSplash getInstance(){
		return instance;
	}
	private JSplash() {
		super();

		// URL url =
		// JSplash.class.getClassLoader().getResource("resources/icons/splash_logo.png");

		ImageIcon icon = ImageManager
				.getImageIconByShortName("splash_logo.png");
		m_progressBar = true;
		m_progressBarMessages = true;
		m_progressBarPercent = false;

		this.setAlwaysOnTop(false);

		// build a panel with a black line for border,
		// and set it as the content pane
		//
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		setContentPane(panel);

		// build a label and set it's icon
		//

		JSplashLabel label = new JSplashLabel(icon, null, null, null);

		// build a progress bar
		//
		if (m_progressBar) {
			m_progress = new JProgressBar();
			// m_progress.setIndeterminate(false);
			// m_progress.setStringPainted( true );
			if (m_progressBarMessages || m_progressBarPercent) {
				m_progress.setStringPainted(true);
			} else {
				m_progress.setStringPainted(false);
			}

			if (m_progressBarMessages && !m_progressBarPercent) {
				m_progress.setString("");
			}
			m_progress.setMaximum(100);
			m_progress.setMinimum(0);
			m_progress.setValue(0);
		}

		// add the components to the panel
		//
		getContentPane().add(label, BorderLayout.CENTER);

		if (m_progressBar) {
			getContentPane().add(m_progress, BorderLayout.SOUTH);
		}

		// validate, and display the components
		pack();

		// center on screen
		SwingUtils.centerOnScreen(this);

		// hide the panel for now...
		setVisible(false);
	}

	/**
	 * Displays the splash screen
	 */
	public void splashOn() {
		setVisible(true);
	}

	/**
	 * Hides and disposes the splash screen
	 */
	public void splashOff() {
		setVisible(false);
		dispose();
	}

	/**
	 * Sets the progress indicator (values: 0 - 100).
	 * <p>
	 * 
	 * @param value
	 *            The progress indicator value.
	 */
	private void setProgress(int value) {
		if (m_progressBar && value >= 0 && value <= 100) {
			m_progress.setValue(value);
		}
	}

	/**
	 * Sets the progress indicator (values: 0 - 100) and a label to print inside
	 * the progress bar.
	 * <p>
	 * 
	 * @param value
	 *            The progress indicator value.
	 * @param msg
	 *            The message to print.
	 */
	private void setProgress(int value, String msg) {
		setProgress(value);

		if (m_progressBarMessages && !m_progressBarPercent && msg != null) {
			m_progress.setString(msg);
		}
	}

	public void increaseProgress(int value) {
		total_progress += value;
		setProgress(total_progress);
	}

	public void increaseProgress(int value, String message) {
		System.out.println("value="+value+"message="+message);
		total_progress += value;
		setProgress(total_progress, message);
		//m_progress.updateUI();
	}

	public void resetProgress() {
		total_progress = 0;
		setProgress(total_progress);
	}
}
