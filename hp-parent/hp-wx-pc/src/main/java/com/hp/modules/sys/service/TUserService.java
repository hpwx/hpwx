package com.hp.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.hp.common.utils.PageUtils;
import com.hp.modules.sys.entity.TSubject;
import com.hp.modules.sys.entity.TUser;

import java.util.List;
import java.util.Map;

public interface TUserService extends IService<TUser> {

    PageUtils getAll(Map<String, Object> params);

    void save(TUser tUser);

    void update(TUser tUser);

    void deleteBatch(Long[] ids);


}
