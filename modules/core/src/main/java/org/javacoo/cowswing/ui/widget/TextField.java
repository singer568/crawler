package org.javacoo.cowswing.ui.widget;

import java.util.List;

import javax.swing.JTextField;

import org.apache.commons.collections.CollectionUtils;

/**
 *@author DuanYong
 *@since 2012-11-17下午5:18:46
 *@version 1.0
 */
public class TextField extends JTextField{
	private static final long serialVersionUID = 1L;
	private List<String> verifierNameList;
	public TextField(){
	}
	private void init(){
		if(CollectionUtils.isNotEmpty(verifierNameList)){
			for(String name : verifierNameList){
			
			}
		}
	}
	public List<String> getVerifierNameList() {
		return verifierNameList;
	}
	public void setVerifierNameList(List<String> verifierNameList) {
		this.verifierNameList = verifierNameList;
	}
	

}
