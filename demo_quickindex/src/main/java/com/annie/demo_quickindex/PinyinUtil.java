package com.annie.demo_quickindex;

import android.text.TextUtils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 获取汉字的拼音,本质是读取xml，所以该方法不应该被频繁调用，会销毁一定资源
 */
public class PinyinUtil {
    public static String getPinyin(String chinese) {
         if (TextUtils.isEmpty(chinese)) return null;

        //主要是设置字母的大小写，以及是否需要音调
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.UPPERCASE); //设置拼音为大写字母
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);//设置拼音不带有音调

        //由于只能对单个汉字获取拼音，需要将字符串转为字符数组，然后将每个字符的拼音拼接起来
        char[] chars = chinese.toCharArray();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            //1.判断是否是空格,需要过滤掉空格
            if (Character.isWhitespace(c)) continue; //结束本次遍历,继续下次遍历
            // 2/判断是否是中文
            //@##大圣归来##@O(∩_∩)O~
            //由于中文占2个字节，一个字节的范围是-128~127,所以中文肯定大于127
            if (c>127) {
                //说明可能是中文,使用pinyin4j转化为拼音
                try {
                    //由于多音字的存在，所以返回的数组，单:{dan,shan}
                    String[] pinyinArr = PinyinHelper.toHanyuPinyinStringArray(c, format);
                    //目前只能暂且取第0个，对于真的是多音字，那么也就无能为力了。因为
                    //此处需要强大的数据库支持
                    if (pinyinArr != null) {
                        builder.append(pinyinArr[0]);
                    }
                } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                    //如果是非法中文就抛出异常，那么我们选择忽略
                    badHanyuPinyinOutputFormatCombination.printStackTrace();
                }
            }else {
                //肯定不是中文，一般是英文字母和键盘能直接输入的字符，
                //对于该情况我们选择直接拼接
                builder.append(c);
            }
        }
        return builder.toString();
    }
}
