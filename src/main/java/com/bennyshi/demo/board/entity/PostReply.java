package com.bennyshi.demo.board.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import com.bennyshi.demo.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

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

    @NotNull(message = "id不能为空！")
    public int id;

    public Integer postId;

    private String content;

    public LocalDateTime createdDate;

    public LocalDateTime updateDate;

    public Long adminId;

    private Integer goodcount;

    private Integer badcount;

    public Integer state;


}
