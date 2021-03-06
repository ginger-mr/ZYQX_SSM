package com.ginger.wlfl.web;

import com.ginger.wlfl.pojo.Permission;
import com.ginger.wlfl.pojo.Role;
import com.ginger.wlfl.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    IRoleService roleService;

    /**
     * 查询所有角色
     *
     * @return
     */
    @RequestMapping("/findAll.do")
    public ModelAndView findAll() {
        ModelAndView mav = new ModelAndView();
        List<Role> roleList = roleService.findAll();
        mav.addObject("roleList", roleList);
        mav.setViewName("role-list");
        return mav;
    }

    /**
     * 保存角色
     *
     * @param role
     * @return
     */
    @RequestMapping("/save.do")
    public String saveRole(Role role) {
        roleService.saveRole(role);
        return "redirect:findAll.do";
    }

    /**
     * 根据id查询角色
     */
    @RequestMapping("/findById.do")
    public ModelAndView findById(@RequestParam(name = "id") String roleId) {
        ModelAndView mav = new ModelAndView();
        Role role = roleService.findById(roleId);
        mav.addObject("role", role);
        mav.setViewName("role-show");
        return mav;
    }


    /**
     * 根据角色id查询出该角色中没有的资源权限
     *
     * @param roleId
     * @return
     */
    @RequestMapping("/findByIdAndOtherPermission.do")
    public ModelAndView findByIdAndOtherPermission(@RequestParam(name = "id") String roleId) {
        ModelAndView mav = new ModelAndView();
        Role role = roleService.findById(roleId);
        List<Permission> permissionList = roleService.findByIdAndOtherPermission(roleId);
        mav.addObject("role", role);
        mav.addObject("permissionList", permissionList);
        mav.setViewName("role-permission-add");
        return mav;
    }

    /**
     * 添加资源权限到角色
     * @param roleId
     * @param ids
     * @return
     */
    @RequestMapping("/addPermissionToRole.do")
    public String addPermissionToRole(String roleId, String[] ids) {
        roleService.addPermissionToRole(roleId, ids);
        return "redirect:findAll.do";
    }

}
