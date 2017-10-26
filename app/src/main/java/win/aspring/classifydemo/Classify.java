package win.aspring.classifydemo;

import win.aspring.library.interf.ClassifyBean;

/**
 * ============================================
 * 文件名：Classify.java
 * 作者：Admin
 * 日期：2017-10-26 15:58
 * 更新：2017-10-26 15:58
 * 描述：分类实体类
 * 版本：1.0
 * ============================================
 */
public class Classify implements ClassifyBean {
    private String name;
    private String imagePath;

    public void setName(String name) {
        this.name = name;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getImagePath() {
        return imagePath;
    }
}
