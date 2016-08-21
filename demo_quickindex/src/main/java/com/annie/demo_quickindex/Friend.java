package com.annie.demo_quickindex;

/**
 * Created by ma on 2016/7/15.
 */
public class Friend implements Comparable<Friend>{
    private String name;
    private String pinyin;

    //因为当获取到名字的时候,直接就可以获取拼音,所以可以在这里将拼音存储在一个变量中
    public Friend(String name) {
        this.name = name;
        this.pinyin = PinyinUtil.getPinyin(this.name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.pinyin = PinyinUtil.getPinyin(this.name);
    }
    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }


    //因为比较的方法会被频繁调用,每次比较都要重新调用工具类中的getPinyin方法,获取当前自己的拼音和比较的拼音
    //很没必要,且浪费资源,所以需要优化
    @Override
    public int compareTo(Friend another) {
        /*String pinyin = PinyinUtil.getPinyin(this.getName()); //当前自己的拼音
        String anotherPinyin = PinyinUtil.getPinyin(another.getName()); //比较对象的拼音
        return pinyin.compareTo(anotherPinyin);*/

        //优化后的方案
        return pinyin.compareTo(another.getPinyin());
    }
}
