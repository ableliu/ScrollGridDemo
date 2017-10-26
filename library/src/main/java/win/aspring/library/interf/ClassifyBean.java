package win.aspring.library.interf;

/**
 * ============================================
 * 文件名：ClassifyBean.java
 * 作者：Admin
 * 日期：2017-10-26 15:04
 * 更新：2017-10-26 15:04
 * 描述：分类的实体类都要实现这个接口
 * 版本：1.0
 * 版权：Copyright （C） 2017 河南商宇科技有限公司
 * ============================================
 */
public interface ClassifyBean {
    /**
     * 获取分类显示的名称
     */
    String getName();

    /**
     * 获取显示的图片路径
     */
    String getImagePath();
}
