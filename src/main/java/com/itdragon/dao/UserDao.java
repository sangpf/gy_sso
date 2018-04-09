package com.itdragon.dao;

import com.itdragon.pojo.User;
import com.itdragon.pojo.UserQuery;
import com.itdragon.pojo.UserVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *
 * @author sang
 *
 */
@Mapper
public interface UserDao {
	//查询集合
	public List<UserVo> getUserList(UserQuery userQuery);

	//查询总记录数
	public int getUserListCount(UserQuery userQuery);

	//查询总记录数
	public int getUserCount(User entity);
	//添加
	public void addUser(User entity);
	
	//删除
	public void deleteUserByKey(Integer id);
	//删除 批量
	public void deleteUserByKeys(Integer[] ids); //List<Integer>  ids
	//修改
	public void updateUserByKey(User entity);

	//根据 id查询
	public User getUserByKey(Integer id);
}
