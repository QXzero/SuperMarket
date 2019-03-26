package cn.smbms.dao.role;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import cn.smbms.pojo.Role;
import cn.smbms.pojo.User;

public interface RoleMapper {
	
	
	/**
	 * 获取角色列表
	 * @return
	 * @throws Exception
	 */
	public List<Role> getRoleList()throws Exception;
	
	/**
	 * 增加角色信息
	 * @param role
	 * @return
	 * @throws Exception
	 */
	public int add(Role role)throws Exception;
	
	/**
	 * 通过Id删除role
	 * @param delId
	 * @return
	 * @throws Exception
	 */
	public int deleteRoleById(@Param("id")Integer delId)throws Exception; 
	
	/**
	 * 修改角色信息
	 * @param role
	 * @return
	 * @throws Exception
	 */
	public int modify(Role role)throws Exception;
	
	
	/**
	 * 通过id获取role
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Role getRoleById(@Param("id")Integer id)throws Exception; 
	
	/**
	 * 根据roleCode ，进行角色编码的唯一性验证
	 * @param roleCode
	 * @return
	 * @throws Exception
	 */
	public int roleCodeIsExist(@Param("roleCode")String roleCode)throws Exception;
	
}
