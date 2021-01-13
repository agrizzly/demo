package com.grizzly.permission.service;

import com.grizzly.permission.entity.Resource;
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
public interface ResourceService extends IService<Resource> {

    R allresource();

    R delete(String id);

}
