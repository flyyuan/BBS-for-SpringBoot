package com.bennyshi.demo.board.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import com.bennyshi.demo.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 
 * </p>
 *
 * @author jobob
 * @since 2018-12-22
 */
@Component
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("board_parent")
public class Parent extends BaseEntity {

//    private static final long serialVersionUID = 1L;

    @NotNull(message = "id不能为空！")
    public int id;

    @NotEmpty(message = "父论坛名不能为空！")
    private String name;

//    @NotNull(message = "管理员ID不能为空！")
    public Long adminId;

    public LocalDateTime createdDate;

    public LocalDateTime updateDate;

    public Integer state;


}
