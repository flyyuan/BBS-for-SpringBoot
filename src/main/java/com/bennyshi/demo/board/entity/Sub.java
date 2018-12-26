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
@TableName("board_sub")
public class Sub extends BaseEntity {

    public Long id;

    private Integer boardParentId;

    private String name;

    public Long adminId;

    public LocalDateTime createdDate;

    public LocalDateTime updateDate;

    public Integer state;


}
