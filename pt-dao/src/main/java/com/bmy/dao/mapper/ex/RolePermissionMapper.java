package com.bmy.dao.mapper.ex;

import com.bmy.dao.domain.Permission;
import com.bmy.dao.domain.ex.RolePermission;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @InterfaceName RolePermissionMapper
 * @Description TODO
 * @Author potato
 * @Date 2020/12/22 下午11:44
 **/
@Repository
public interface RolePermissionMapper extends Mapper<RolePermission> {

    @Select("select *\n" +
            "from role_permission\n" +
            "left join permission\n" +
            "on permission.id = role_permission.pid\n" +
            "where rid = #{rid}")
    public List<Permission> selectPermissions(Long rid);


}
