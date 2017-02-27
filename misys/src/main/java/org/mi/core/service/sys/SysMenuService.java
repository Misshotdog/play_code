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
 * Date: 2015-05-12 15:39:06
 *
 * @author Code Generator
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

    /**
     * 通过ID删除， 并删除子菜单
     * @param id
     * @return
     */
    public int deleteTree(long id);

    /**
     * 批量修改排序值
     * @param ids
     * @param sortNums
     * @param parentIds
     */
    public void sort(Long[] ids, Long[] sortNums, Long[] parentIds);
    
    public PageInfo<SysMenu> getInfoPage(PageInfo<SysMenu> pageInfo,
			Map<String, Object> conditions, Map<String, Boolean> orderBy);
    
    
    public List<SysMenu> getMenuList();
    
    public int getMenuCount(Long parentId);

}
