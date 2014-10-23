package org.javacoo.cowswing.ui.listener;


import java.util.regex.Pattern;

import javax.swing.JComponent;
import javax.swing.JTextField;

import org.apache.commons.lang.StringUtils;
import org.javacoo.cowswing.base.loader.LanguageLoader;

/**
 * 文本校验类
 * 
 * @author DuanYong
 * @since 2012-11-16上午11:02:13
 * @version 1.0
 */
public class TextVerifier extends AbstractVerifier {
	/** 是否允许空值,默认false */
	private boolean blankOk = false;
	/**正则表达式*/
	private String regex = "";

	public TextVerifier(VerifierListener alistener, boolean blankok) {
		super(alistener);
		blankOk = blankok;
	}
	@Override
	protected boolean doVerify(JComponent jComponent) {
		JTextField thefield = (JTextField) jComponent;
		String input = thefield.getText();
		input = input.trim(); 
		if (StringUtils.isBlank(input) && blankOk) {
			return true; 
		} else if (StringUtils.isBlank(input) && !blankOk) {
			setMessage(LanguageLoader.getString("ErrorMsg.NoEmpty"));
			return false; 
		}
		return true; 
	}
	
    private boolean check(){
    	if(StringUtils.isNotBlank(this.regex)){
    		Pattern pattern = Pattern.compile(this.regex);
    	}
    	
    	return true;
    }
	
	
}
