package com.bmy.dao.mapper.ex;

import com.bmy.dao.domain.Role;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *@InterfaceName UserRoleMapper
 *@Description TODO
 *@Author potato
 *@Date 2020/12/22 下午11:42
**/
@Repository
public interface UserRoleMapper {

    @Select("select * \n" +
            "from user_role\n" +
            "left join role\n" +
            "on role.id = user_role.rid\n" +
            "where uid = #{uid}")
    public List<Role> selectRolesByUid(Long uid);

}
