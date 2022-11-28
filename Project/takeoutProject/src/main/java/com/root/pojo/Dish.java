package com.root.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Options;

/**
 * 菜品管理
 * @TableName dish
 */
@TableName(value ="dish")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dish implements Serializable {
    /**
     * 主键
     */
//    @Options(useGeneratedKeys = true,keyProperty = "id")//此处标在方法上表示的是插入对象时返回主键id
    @TableId
    private Long id;

    /**
     * 菜品名称
     */
    private String name;

    /**
     * 菜品分类id
     */
    private Long categoryId;

    /**
     * 菜品价格
     */
    private BigDecimal price;

    /**
     * 商品码
     */
    private String code;

    /**
     * 图片
     */
    private String image;

    /**
     * 描述信息
     */
    private String description;

    /**
     * 0 停售 1 起售
     */
    private Integer status;

    /**
     * 顺序
     */
    private Integer sort;

    //当前菜属于的菜品
    @TableField(exist = false)
    private String categoryName;

    /**
     * 创建时间
     */
    //此注解的作用是指明那些数据需要进行数据填充
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    //在插入和更新时需要自动填充
    @TableField(fill=FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    /**
     * 修改人
     */
    @TableField(fill=FieldFill.INSERT_UPDATE)
    private Long updateUser;

    /**
     * 是否删除
     */
    //逻辑删除 不是真正的从磁盘中删除 而是标志性删除
    //0表示未删除 1表示删除
    @TableLogic
    private Integer isDeleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}