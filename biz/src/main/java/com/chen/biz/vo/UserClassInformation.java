package com.chen.biz.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author danger
 * @date 2021/5/14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserClassInformation {
    @TableId(value = "user_id")
    private Long userId;
    @TableField(value = "username")
    private String username;
    @TableField(value = "real_name")
    private String realName;
    @TableField(value = "student_id")
    private String studentId;
}
