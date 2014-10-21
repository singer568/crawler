package org.javacoo.cowswing.ui.listener;


import javax.swing.JComponent;
import javax.swing.JTextField;

import org.apache.commons.lang.StringUtils;
import org.javacoo.cowswing.base.loader.LanguageLoader;

/**
 * 整数校验类
 * 
 * @author DuanYong
 * @since 2012-11-16上午11:02:13
 * @version 1.0
 */
public class IntegerVerifier extends AbstractVerifier {
	/** 是否允许空值,默认false */
	private boolean blankOk = false;
	/** 最小值 */
	int minValue = Integer.MIN_VALUE;
	/** 最大值 */
	int maxValue = Integer.MAX_VALUE;

	public IntegerVerifier(VerifierListener alistener, boolean blankok,
			int min, int max) {
		super(alistener);
		blankOk = blankok;
		minValue = min;
		maxValue = max;
	}
	@Override
	protected boolean doVerify(JComponent jComponent) {
		JTextField thefield = (JTextField) jComponent;
		String input = thefield.getText();
		int number;
		input = input.trim(); 
		if (StringUtils.isBlank(input) && blankOk) {
			return true; 
		} else if (StringUtils.isBlank(input) && !blankOk) {
			setMessage(LanguageLoader.getString("ErrorMsg.NoEmpty"));
			return false; 
		}

		try {
			number = Integer.parseInt(input);
		} catch (NumberFormatException e) {
			setMessage(LanguageLoader.getString("ErrorMsg.MustNumber"));
			//thefield.setToolTipText(LanguageLoader.getString("ErrorMsg.MustNumber"));
			return false;
		}
		/*
		 * 检查数据范围
		 */
		if (number < minValue || number > maxValue) {
			setMessage(LanguageLoader.getString("ErrorMsg.NumberRange") + minValue
					+ " - " + maxValue);
			return false;
		}
		thefield.setText("" + number); 
		return true; 
	}
	
	
}
