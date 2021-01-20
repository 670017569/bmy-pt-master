package com.bmy.wx.service;

import cn.hutool.core.lang.Snowflake;
import com.bmy.core.constant.Response;
import com.bmy.core.exception.BadRequestException;
import com.bmy.dao.domain.UserInfo;
import com.bmy.dao.domain.ex.DynamicPic;
import com.bmy.dao.dto.OssFileDTO;
import com.bmy.dao.mapper.ex.DynamicPicMapper;
import com.bmy.dao.service.DynamicPraiseService;
import com.bmy.dao.service.DynamicService;
import com.bmy.dao.service.FollowService;
import com.bmy.dao.service.UserInfoService;
import com.bmy.dao.dto.DynamicDTO;
import com.bmy.dao.domain.Dynamic;
import com.bmy.dao.mapper.DynamicMapper;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DynamicServiceImpl implements DynamicService {

    @Resource
    private DynamicMapper dynamicMapper;

    @Resource
    private DynamicPicMapper dynamicPicMapper;

    @Resource
    private Snowflake snowflake;

    @Resource
    private DynamicPraiseService dynamicPraiseService;

    @Resource
    private UserInfoService userInfoService;

    @Resource
    private FollowService followService;

    /**
     * 查询所有动态
     * @return
     */
    public List<Dynamic> selectAll(Long uid){
        List<Dynamic> dynamics = dynamicMapper.selectAll();
        for (Dynamic dynamic : dynamics){
            dynamic.setIsFollowed(followService.setIsFollowed(uid,dynamic.getUid()));
            dynamic.setBePraised(dynamicPraiseService.setIsPraised(uid,dynamic.getId()));
        }
        return dynamics;
    }

    /**
     * 发布一条新动态
     * @param dynamicVo
     * @return
     */
    public Dynamic pubDynamic(DynamicDTO dynamicVo, UserInfo userInfo){
        try {
            //生成一个id
            Long id = snowflake.nextId();
            Dynamic dynamic = Dynamic.builder()
                    .id(id)
                    .uid(userInfo.getId())
                    .content(dynamicVo.getContent())
                    .region(dynamicVo.getRegion())
                    .build();
            List<OssFileDTO> pics = dynamicVo.getPics();
            //将动态插入表中
            int res = dynamicMapper.insertSelective(dynamic);
            if (res == 1){
                for (OssFileDTO pic : pics){
                    DynamicPic dynamicPic = DynamicPic.builder().id(pic.getId()).dynId(id).url(pic.getUrl()).build();
                    //将图片和动态的id插入关系表中
                    dynamicPicMapper.insertSelective(dynamicPic);
                }
            }else {
                return null;
            }
            //插入成功后userinfo中的动态字段+1
            userInfo.setDynamics(userInfo.getDynamics()+1);
            userInfoService.updateUserInfo(userInfo);
            return dynamicMapper.selectByPrimaryKey(id);
        }catch (DuplicateKeyException e){
            return null;
        }
    }

    /**
     * 根据uid查询动态
     * @param uid
     * @return
     */
    public List<Dynamic> selectByUid(Long uid) {
        List<Dynamic> dynamics = dynamicMapper.selectByUid(uid);
        for (Dynamic dynamic : dynamics){
            dynamic.setIsFollowed(followService.setIsFollowed(uid,dynamic.getUid()));
            dynamic.setBePraised(dynamicPraiseService.setIsPraised(uid,dynamic.getId()));
        }
        return dynamics;
    }

    /**
     * 根据id删除动态
     * @param id
     * @param userInfo
     * @return
     */
    @Override
    public boolean deleteById(Long id,UserInfo userInfo) {
        //根据dynId查询该动态，并判断该动态的uid是否与userInfo中的uid相同，若不同则返回非法参数
        Dynamic dynamic = dynamicMapper.selectByPrimaryKey(id);
        //若相同则继续执行删除
        if (!dynamic.getUid().equals(userInfo.getId())){
            throw new BadRequestException(Response.ILLEGAL_PARAMS);
        }
        Example example = new Example(Dynamic.class);
        example.and()
                .andEqualTo("id",id);
        Dynamic record = Dynamic.builder().deleted(1).build();
        int res = dynamicMapper.updateByExampleSelective(record,example);
        userInfo.setDynamics(userInfo.getDynamics()-1);
        userInfoService.updateUserInfo(userInfo);
        return 1 == res;
    }

    @Override
    public Dynamic selectByDynId(Long dynId,UserInfo userInfo) {
        Dynamic dynamic = dynamicMapper.selectByPrimaryKey(dynId);
        dynamic.setIsFollowed(followService.setIsFollowed(userInfo.getId(),dynamic.getUid()));
        dynamic.setBePraised(dynamicPraiseService.setIsPraised(userInfo.getId(),dynamic.getId()));
        return dynamic;
    }

}
