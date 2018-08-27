package cc.databus.springboot.demo.service;

import cc.databus.springboot.demo.pojo.SysUser;

import java.util.List;

public interface UserService {
    void saveUser(SysUser sysUser);
    void updateUser(SysUser sysUser);
    void deleteUser(String userId);
    List<SysUser> queryUserList(SysUser sysUser);
    List<SysUser> queryUserListPaged(SysUser user, Integer page, Integer pageSize);
    SysUser queryUserByIdCustom(String userId);
    void saveUserTransactional(SysUser user);
}
