package cc.databus.springboot.demo.service.impl;

import cc.databus.springboot.demo.mapper.SysUserMapper;
import cc.databus.springboot.demo.mapper.SysUserMapperCustom;
import cc.databus.springboot.demo.pojo.SysUser;
import cc.databus.springboot.demo.service.UserService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private SysUserMapperCustom userMapperCustom;

    @Override
    public void saveUser(SysUser sysUser) {
        userMapper.insert(sysUser);
    }

    @Override
    public void updateUser(SysUser sysUser) {
        userMapper.updateByPrimaryKeySelective(sysUser);
    }

    @Override
    public void deleteUser(String userId) {
        userMapper.deleteByPrimaryKey(userId);
    }

    @Override
    public List<SysUser> queryUserList(SysUser sysUser) {
        return null;
    }

    @Override
    public List<SysUser> queryUserListPaged(SysUser user, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        Example example = new Example(SysUser.class);
        Example.Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmptyOrWhitespace(user.getNickname())) {
            criteria.andLike("nickname", "%" + user.getNickname() +"%");
        }
        example.orderBy("id").desc();
        return userMapper.selectByExample(example);
    }

    @Override
    public List<SysUser> queryUserByIdCustom(String userId) {
        return userMapperCustom.queryUserSimplyInfoById(userId);
    }

    @Override
    public void saveUserTransactional(SysUser user) {

    }
}
