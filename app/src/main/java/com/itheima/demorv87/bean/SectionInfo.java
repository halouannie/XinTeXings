package com.itheima.demorv87.bean;

import com.chad.library.adapter.base.entity.SectionEntity;

/**
 * Created by Administrator on 2016/7/21.
 */
public class SectionInfo extends SectionEntity<Category> {
    public SectionInfo(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public SectionInfo(Category category) {
        super(category);
    }
}
