package com.bjm.pms.crawler.core.util.generator;

import java.text.SimpleDateFormat;

import org.apache.commons.lang.RandomStringUtils;

import com.bjm.pms.crawler.core.constants.Constants;


/**
 * 唯一标示生成器接口默认实现类
 * @author javacoo
 * @since 2011-11-14
 */
public class DefaultGenerator implements Generator{
	SimpleDateFormat dt = new SimpleDateFormat(Constants.GENERATOR_FORMAT);
	public String next() {
		return dt.format(new java.util.Date()) + (int)(Math.random()*100)+RandomStringUtils.random(4, Num62.N36_CHARS);
	}
    public static void main(String[] args){
    	DefaultGenerator generator = new DefaultGenerator();
    	System.out.println(generator.next());
    	System.out.println(generator.next());
    	System.out.println(generator.next());
    }
}
