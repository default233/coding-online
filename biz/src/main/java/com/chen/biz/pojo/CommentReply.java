package com.chen.biz.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author danger
 * @date 2021/5/9
 */
@TableName("comment_reply")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentReply {
    @TableId(value = "comment_reply_id")
    private Long commentReplyId;
    @TableField(value = "comment_id")
    private Long commentId;
    @TableField(value = "user_id")
    private Long userId;
    @TableField(value = "content")
    private String content;
    @TableField(value = "username")
    private String username;
    @TableField(value = "img")
    private String img;
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    @TableLogic
    @TableField(value = "is_delete", fill = FieldFill.INSERT)
    private Boolean isDelete;
    @TableField("description")
    private String description;
}
