package com.zhihuishu.util;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.huaban.analysis.jieba.JiebaSegmenter;


public class AI_QA_UTIL {
	//分词
	private static JiebaSegmenter segmenter = new JiebaSegmenter();
	//去字符表达式[\\s] [12]|[123]|[1234]|[12345]
	//private final  String reg = "[\f\n\r\\v]|[/:;()$&@\".,?？《》，。；：、‘’“”【】｛｝（）——￥！!'\\[\\]{}#%^*+=_\\\\~|<>€£¥•\\-]";
	private static final String regnum = "[123]|[1234]|[12345]|[123456]|[1234567]|[\\s]";
	
	/**
	 * 是否符号
	 * @param a
	 * @return
	 */
	private static boolean symbol(int a){
		 return	a<=31 || (a>=33 && a<=47) || (a>=58 && a<=64) || (a>=91 && a<=96) || (a>=123 && a<=126) || a==65509 ||
		   (a>=8208 && a<=8231) || (a>=12289 && a<=12319) || (a>=65097 && a<=65131) || (a>=65281 && a<=65295) || 
		   (a>=65306 && a<=65312) || (a>=65371 && a<=65381);
	}
	/**
	 * 是否数字
	 * @param a
	 * @return
	 */
	private static boolean num(int a){
		 return	a>=48 && a<= 57;
	}
	/**
	 * 是否字母
	 * @param a
	 * @return
	 */
	private static boolean abc(int a){
		 return	(a>=65 && a<=90) || (a>=97 && a<=122) ;
	}
	private static boolean is(char a){
		return  a=='的' || a == '吗' || a=='啊' || a=='和' || a == '及' || a=='等'  || a=='吧';
	}
	private static class FloatNumber {
		public float value=0;
		public String str="";
		public String str1="";
		public String target1="";
		public boolean end=true;
	}
	
	/**
	 * 相似度
	 * @param str 当前字符串
	 * @param target 和对比字符串
	 * @param bfb 相似度设置
	 * @return
	 */
	public static float similarity(String str,String target,double bfb){
		if(str==null  ){return 0;}
		FloatNumber s = overlap(str);
		if(s!=null && s.value<bfb && target!=null && s.end){
			s =distance(s,str(target));
			if(s.value<bfb && s.end){
				s = cosine(s);
			}
		}
		return s==null?0:s.value;
	}
	/**
	 * 相似度
	 * @param str 当前字符串
	 * @param target 和对比字符串
	 * @return
	 */
	public static float similarity(String str,String target){
		if(str==null  ){return 0;}
		FloatNumber s = overlap(str);
		if(s!=null && s.value<99 && target!=null && s.end){
			s =distance(s,str(target));
			if(s.value<99 && s.end){
				s = cosine(s);
			}
		}
		return s==null?0:s.value;
	}
	
	
	private static String str(String str){
		char a;String s="";
		for (int i = 0; i < str.length(); i++) {
			a = str.charAt(i);
			if( symbol(a) || a==' '){ continue;}/*是符号 是空隔*/
			if(!is(a)){s += a;}/*非语气词*/
		}
		return s;
	}
	/**
	 * 字符串算法
	 * @param str
	 * @return
	 */
	private static FloatNumber overlap(String str){
		FloatNumber f= new FloatNumber();
		if(str!=null  ){
			int strLen = str.length(),len=0;
			Set<Character> str2 = new HashSet<Character>();
			char a,b=0;
			int c1=0,e2=0,e=0,e1=0,g=0,k=0,mc1=0;
			for (int i = 0; i < strLen; i++) {
				a = str.charAt(i);
				if( symbol(a) ){ continue;}/*是符号*/
				if( a==' '){ k++; continue;}/*是空隔*/
				if(a == b && !num(a) ){c1 ++;}else{mc1 = c1>mc1 ? c1 : mc1; c1=0;}/*不是数字 相同相连的最大数*/
				if(num(a)){e++;e2++;}else{e1 = e>e1?e:e1; e=0;}/*是数字 连续最大数*/
				if( abc(a) ){g++;}/*是字母*/
				b = a; len++;
				if(!is(a)){f.str += a;}/*非语气词*/
				str2.add(a);/*去重*/
			}
			int i = str2.size();/*去重后长度*/
			/*如果总长度小于4 并去重后长度小于3 直接相似度100*/
			if(len<4 || (i<4 && (g<5||e2<5))){
				f.value=100;f.end=false;
			}else{
				float e5  = ((float) g/(len)) *100f ;/*字母占总长度百分比*/
				float e3=((float) e2/len)*100f;/*数字占总长度百分比*/
				mc1 = c1>mc1 ? c1 : mc1; c1=0;/*非数字连续出现最大相同次数*/
				e1 = e>e1?e:e1; e=0;/*数字连续出现最大相同次数*/
				f.value = ((float) (len-i)/len*100f);/*f.value = ((float) (len-i)*2/(len+i)*100f);*/
				if(e5>95){/*英文字母占95%按纯字母算语句（英语）*/
					float e4 =((float)g/(k==0?1:k));/*平均每个单词长度*/
					if(e4<3 || e4>10){f.value=100;}/*平均单词长度小于3时或大于10*/
				}
				if(e3>90 && e2>3){
					f.value = e3 > f.value ? e3 : f.value;/*数字占比大于90*/
				}
				if(e1>2 && (len-g-e2)>2){/*连续出现数字大于2并总长度减数字长度减字母长度大于2*/
					f.str = f.str.replaceAll(regnum, "");
				}
			}
		}
		return f;
	}
	/**
	 * 计算两个字符串相似度（距离算法）
	 * @return 
	 */
	private static FloatNumber distance(FloatNumber obj,String target){
		try {
		int n = obj.str.length();
		int m = target.length();
		if (n == 0 || m == 0) { obj.end=false; return obj;}
		int d[][] = new int[n + 1][m + 1]; // 矩阵
		int i,j,temp,fn=0,fm=0; 
		char ch1,ch2,upch1=0,upch2=0; 
		d[0][0] = 0;
		for (i = 1; i <= n; i++) {
			ch1 = obj.str.charAt(i - 1);
			if(ch1 == upch1){ fn++; continue; } 
			obj.str1 += ch1;
			upch1=ch1; 
			d[i-fn][0] = i-fn;
			fm=0;
			upch2=0;
			obj.target1="";
			for (j = 1; j <= m; j++) {
				ch2 = target.charAt(j - 1);
				if(ch2 == upch2){ fm++; continue; } 
				obj.target1 += ch2;
				upch2=ch2; 
				d[0][j-fm] = j-fm;
				temp = (ch1 == ch2 || ch1 == ch2 + 32 || ch1 + 32 == ch2)?0:1;
				d[i-fn][j-fm] = Math.min(Math.min(d[i-fn - 1][j-fm] + 1, d[i-fn][j-fm - 1] + 1), d[i-fn - 1][j -fm- 1] + temp);
			}
		}
		float f = (1 - (float) d[n-fn][m-fm] / Math.max(n-fn, m-fm)) * 100F;
		obj.value = f>obj.value?f:obj.value;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}
	/**
	 * 计算两个字符串相似度（分词 余弦相似度算法）
	 * @param obj
	 * @return
	 */
	private static FloatNumber cosine(FloatNumber obj){
		try {
		List<String> wrod1 = segmenter.sentenceProcess(obj.str1);
		List<String> wrod2 = segmenter.sentenceProcess(obj.target1);
		Set<String> word = new HashSet<String>();
		word.addAll(wrod1);
		word.addAll(wrod2);
		Iterator<String> it =word.iterator();
		String s;
		double ab=0,a=0,b=0;
		while(it.hasNext()){
			s = it.next();
			double i=0,j=0;
			for(String w1 : wrod1){
				if(w1.equals(s)){i++;}
			}
			for(String w2 : wrod2){
				if(w2.equals(s)){j++;}
			}
			ab += i*j;
			a += i*i;
			b += j*j;
		}
		float f = (float) (ab/(Math.sqrt(a)*Math.sqrt(b))*100);
		obj.value =  f > obj.value ? f : obj.value;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}
}
