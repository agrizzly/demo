package com.grizzly.permission.service;

import com.grizzly.permission.entity.RoleResource;
import com.baomidou.mybatisplus.extension.service.IService;
import com.grizzly.utils.result.R;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author grizzly
 * @since 2021-01-12
 */
public interface RoleResourceService extends IService<RoleResource> {

    R resource2role(String rid, String[] resourceids);

}
