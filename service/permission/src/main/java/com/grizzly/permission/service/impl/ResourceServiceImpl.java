package com.grizzly.permission.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.grizzly.permission.entity.Resource;
import com.grizzly.permission.entity.RoleResource;
import com.grizzly.permission.mapper.ResourceMapper;
import com.grizzly.permission.service.ResourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.grizzly.utils.result.R;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements ResourceService {

    @Override
    public R allresource() {

        Resource root = new Resource();
        List<Resource> resources = baseMapper.selectList(null);
        for (Resource resource : resources){
            if ("0".equals(resource.getId())){
                resource.setLevel(1);
                resource.setChildren(getChildren(resource, resources));
                root = resource;
            }
        }
        return R.ok().data("resources",root);
    }




    public List<Resource> getChildren(Resource root, List<Resource> resources){
        List<Resource> children = new ArrayList<>();
        for (Resource resource : resources){
            if (!StringUtils.isEmpty(resource.getPid()) &&  resource.getPid().equals(root.getId())){
                resource.setLevel(root.getLevel() + 1);
                resource.setChildren(getChildren(resource, resources));
                children.add(resource);
            }
        }
        return children;
    }


    @Override
    public R delete(String id) {

        List<String> idList = new ArrayList<>();
        List<Resource> resources = baseMapper.selectList(null);
        getChildrenId(id,idList,resources);
        idList.add(id);
        System.out.println(idList);
//        baseMapper.deleteBatchIds(idList);
        return R.ok();
    }

    private void getChildrenId(String id, List<String> idList, List<Resource> resources) {
        for (Resource resource : resources){
            if (!StringUtils.isEmpty(resource.getPid()) && resource.getPid().equals(id)){
                idList.add(resource.getId());
                getChildrenId(resource.getId(),idList,resources);
            }
        }
    }


}
