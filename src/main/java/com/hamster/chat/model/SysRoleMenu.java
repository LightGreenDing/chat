package com.hamster.chat.model;

import com.hamster.chat.base.BaseEntity;

public class SysRoleMenu extends BaseEntity {
    /**
     * 
     */
    private Long id;

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 菜单ID
     */
    private Long menuId;

    /**
     * 
     * @return id 
     */
    public Long getId() {
        return id;
    }

    /**
     * 
     * @param id 
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 角色ID
     * @return role_id 角色ID
     */
    public Long getRoleId() {
        return roleId;
    }

    /**
     * 角色ID
     * @param roleId 角色ID
     */
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    /**
     * 菜单ID
     * @return menu_id 菜单ID
     */
    public Long getMenuId() {
        return menuId;
    }

    /**
     * 菜单ID
     * @param menuId 菜单ID
     */
    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }
}