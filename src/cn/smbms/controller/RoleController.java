package cn.smbms.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.mysql.jdbc.StringUtils;

import cn.smbms.pojo.Role;
import cn.smbms.pojo.User;
import cn.smbms.service.role.RoleService;
import cn.smbms.tools.Constants;

@Controller
@RequestMapping("/sys/role")
public class RoleController {
	
	private Logger logger = Logger.getLogger(RoleController.class);
	
	@Resource
	private RoleService roleService;
	
	
	@RequestMapping(value="/list.html")
	public String getRoleList(Model model){
		List<Role> roleList = null;
		try {
			roleList = roleService.getRoleList();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("roleList", roleList);
		return "rolelist";
	}
	
	@RequestMapping(value="/add.html",method=RequestMethod.GET)
	public String addRole(@ModelAttribute("role") Role role){
		return "roleadd";
	}
	
	@RequestMapping(value="/addsave.html",method=RequestMethod.POST)
	public String addRoleSave(Role role,HttpSession session){
		role.setCreatedBy(((User)session.getAttribute(Constants.USER_SESSION)).getId());
		role.setCreationDate(new Date());
		try {
			if(roleService.add(role)){
				return "redirect:/sys/role/list.html";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "roleadd";
	}
	
	@RequestMapping(value="/modify/{id}",method=RequestMethod.GET)
	public String getUserById(@PathVariable String id,Model model,HttpServletRequest request){
		Role role = new Role();
		try {
			role = roleService.getRoleById(Integer.parseInt(id));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute(role);
		return "rolemodify";
	}
	
	@RequestMapping(value="/modifysave.html",method=RequestMethod.POST)
	public String modifyUserSave(Role role,HttpSession session){
		role.setModifyBy(((User)session.getAttribute(Constants.USER_SESSION)).getId());
		role.setModifyDate(new Date());
		try {
			if(roleService.modify(role)){
				return "redirect:/sys/role/list.html";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "rolemodify";
	}
	@RequestMapping(value="/delrole.json",method=RequestMethod.GET)
	@ResponseBody
	public Object deluser(@RequestParam String id){
		HashMap<String, String> resultMap = new HashMap<String, String>();
		if(StringUtils.isNullOrEmpty(id)){
			resultMap.put("delResult", "notexist");
		}else{
			try {
				if(roleService.deleteRoleById(Integer.parseInt(id)))
					resultMap.put("delResult", "true");
				else
					resultMap.put("delResult", "false");
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return JSONArray.toJSONString(resultMap);
	}
	
	@RequestMapping(value="/rcexist.json")
	@ResponseBody
	public Object roleCodeIsExit(@RequestParam String roleCode){
		HashMap<String, String> resultMap = new HashMap<String, String>();
		if(StringUtils.isNullOrEmpty(roleCode)){
			resultMap.put("roleCode", "exist");
		}else{
			int count = 0;
			try {
				count = roleService.roleCodeIsExist(roleCode);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(count == 0)
				resultMap.put("roleCode", "noexist");
			else
				resultMap.put("roleCode", "exist");
		}
		return JSONArray.toJSONString(resultMap);
	}

}
