package com.bennyshi.demo.board.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.awt.peer.LabelPeer;
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
@TableName("board_post")
public class Post extends BaseEntity {

    private int id;

    private String title;

    private String content;

    private Integer goodcount;

    private Integer badcount;

    public Integer parId;

    public Integer subId;

    public Long adminId;

    public LocalDateTime createdDate;

    public LocalDateTime updateDate;

    public Integer state;


}
