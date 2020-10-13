package com.zhihuishu.util;

import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class StringHelper {


	/**
	 * 转换字符串为Set集合，自动去空格
	 * @param info
	 * @return
	 */
	public static final Set<String> toSet(String info) {
		if(StringUtils.isBlank(info)) return null ;
		String [] array = StringUtils.split(info ,",") ;
		if(array == null || array.length <= 0) return null ;
		Set<String> set = new HashSet<String>() ;
		for(String m : array) {
			if(StringUtils.isBlank(m)) continue ;
			set.add(StringUtils.trim(m)) ;
		}
		return set ;
	}
	
	/**
	 * 将字符串数组拼接为字符串
	 * @param strings
	 * @return
	 */
	public static final String join(String ... strings) {
		return join(',' ,strings) ;
	}

	/**
	 * 使用指定分隔符将字符串数组拼接为字符串
	 * @param placeholder
	 * @param strings
	 * @return
	 */
	public static final String join(char placeholder ,String ... strings) {
		if(strings == null || strings.length == 0) return null ;
		StringBuffer sb = new StringBuffer() ;
		for(String m : strings) {
			sb.append(m).append(placeholder) ;
		}
		return sb.substring(0 ,sb.length() - 1) ;
	}
	
	/**
	 * 集合转换为数组
	 * @param strings
	 * @return
	 */
	public static final String [] toArray(Collection<String> strings) {
		if(strings == null || strings.isEmpty()) return null ;
		String [] array = new String [strings.size()] ;
		int index = 0 ;
		for(String m : strings) {
			array[index ++] = m ;
		}
		return array ;
	}
	
	/**
	 * 生成UUID字符串(32位)
	 * @return
	 */
    public static final String uuid(){   
        return UUID.randomUUID().toString().replaceAll("-", "");   
    }  
	
	public static void main(String[] args) {
		
		System.out.println(join("15618950100" ,"15618950101" ,"15618950102"));
		
	}
	
}
