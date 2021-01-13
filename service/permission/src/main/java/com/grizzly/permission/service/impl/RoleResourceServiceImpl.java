package com.grizzly.permission.service.impl;

import com.grizzly.permission.entity.RoleResource;
import com.grizzly.permission.mapper.RoleResourceMapper;
import com.grizzly.permission.service.RoleResourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.grizzly.utils.result.R;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author grizzly
 * @since 2021-01-12
 */
@Service
public class RoleResourceServiceImpl extends ServiceImpl<RoleResourceMapper, RoleResource> implements RoleResourceService {

    @Override
    public R resource2role(String rid, String[] resourceids) {

        List<RoleResource> role_resource = new ArrayList<>();
        for (String rsid : resourceids){
            role_resource.add(new RoleResource(rid,rsid));
        }
        this.saveBatch(role_resource);
        return R.ok();

    }

}
