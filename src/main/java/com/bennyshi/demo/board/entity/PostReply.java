package com.bennyshi.demo.board.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import com.bennyshi.demo.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author jobob
 * @since 2018-12-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("board_post_reply")
public class PostReply extends BaseEntity {

    private int id;

    private Integer postId;

    private String content;

    public LocalDateTime createdDate;

    public LocalDateTime updateDate;

    private String adminId;

    private Integer goodcount;

    private Integer badcount;

    public Integer state;


}
