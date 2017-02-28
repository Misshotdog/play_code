package org.mi.core.service.sys;


import java.util.List;
import java.util.Map;

import org.mi.core.domain.security.SysMenu;
import org.mi.core.dto.MenuView;

import com.feinno.framework.common.dao.support.PageInfo;
import com.feinno.framework.common.service.EntityService;

/**
 * 系统菜单表 Service
 *
 *
 * @author miss_hotdog
 *
 */
public interface SysMenuService extends EntityService<SysMenu> {

    public int updateStatusById(Long id, Integer status);

    /**
     * 通过用户名获取菜单
     * @param userName 用户名
     * @return
     */
    List<MenuView> queryAuthorisedMenus(String userName);


    /**
     * 通过角色获取菜单
     * @param roleId 角色ID
     * @return
     */
    List<MenuView> queryAuthorisedMenus(Long roleId,Long classify);

}
