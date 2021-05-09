package com.chen.biz.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author danger
 * @date 2021/5/9
 */
@TableName("user_ranking")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRanking {
    @TableField("user_id")
    private Long userId;
    @TableField("username")
    private String username;
    @TableField("pass_rate")
    private Double passRate;
    @TableField("pass_order")
    private Integer passOrder;
    @TableField("img")
    private String img;
}
