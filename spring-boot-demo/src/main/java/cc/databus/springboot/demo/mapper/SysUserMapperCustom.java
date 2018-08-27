package cc.databus.springboot.demo.mapper;

import cc.databus.springboot.demo.pojo.SysUser;
import cc.databus.springboot.demo.utils.MyMapper;

import java.util.List;

public interface SysUserMapperCustom extends MyMapper<SysUser> {
    List<SysUser> queryUserSimplyInfoById(String id);
}