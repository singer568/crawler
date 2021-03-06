package com.bjm.pms.crawler.plugin.core.service.beans;

import java.util.List;

import com.bjm.pms.crawler.view.base.service.beans.ExtendFieldsBean;

/**
 * 扩展字段值对象
 *@author DuanYong
 *@since 2012-11-15上午9:36:08
 *@version 1.0
 */
public class RuleFieldsBean {
	/**扩展字段集合*/
	private List<ExtendFieldsBean> extendFields;

	public List<ExtendFieldsBean> getExtendFields() {
		return extendFields;
	}

	public void setExtendFields(List<ExtendFieldsBean> extendFields) {
		this.extendFields = extendFields;
	}
}
