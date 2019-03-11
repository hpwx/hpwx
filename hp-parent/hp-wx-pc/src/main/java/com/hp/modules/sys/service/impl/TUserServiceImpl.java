package com.hp.modules.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hp.common.utils.PageUtils;
import com.hp.common.utils.Query;
import com.hp.modules.sys.dao.TSubjectDao;
import com.hp.modules.sys.dao.TUserDao;
import com.hp.modules.sys.entity.TSubject;
import com.hp.modules.sys.entity.TUser;
import com.hp.modules.sys.service.TSubjectService;
import com.hp.modules.sys.service.TUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("tUser")
public class TUserServiceImpl extends ServiceImpl<TUserDao, TUser> implements TUserService {

    @Override
    public PageUtils getAll(Map<String, Object> params) {
        Page<TUser> page = this.selectPage(new Query<TUser>(params).getPage(), new EntityWrapper<TUser>().eq("deleted", 0));

        return new PageUtils(page);
    }

    @Override
    @Transactional
    public void save(TUser tUser) {
        tUser.setCreateTime(new Date());
        this.insert(tUser);
    }


    @Override
    @Transactional
    public void update(TUser tUser) {

        this.updateById(tUser);

    }

    @Override
    public void deleteBatch(Long[] userId) {
        this.deleteBatchIds(Arrays.asList(userId));
    }



}
