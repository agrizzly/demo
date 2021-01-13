package com.grizzly.permission.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author grizzly
 * @since 2021-01-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@ApiModel(value="RoleResource对象", description="")
public class RoleResource implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "rid", type = IdType.ASSIGN_UUID)
    private String rid;

    private String rsid;
}
