package org.mi.core.service.impl.sys;



import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.mi.core.dao.sys.SysMenuDao;
import org.mi.core.dao.sys.SysMenuDaoCustom;
import org.mi.core.domain.security.SysMenu;
import org.mi.core.domain.security.SysResource;
import org.mi.core.dto.MenuView;
import org.mi.core.service.sys.SysMenuService;
import org.mi.security.cache.SecurityCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.feinno.framework.common.dao.support.PageInfo;
import com.feinno.framework.common.exception.BusinessException;
import com.feinno.framework.common.service.EntityServiceImpl;

@Service("sysMenuService")
public class SysMenuServiceImpl extends EntityServiceImpl<SysMenu, SysMenuDao> implements SysMenuService {

    @Autowired
    private SecurityCache securityCache;
    @Autowired
    private SysMenuDaoCustom sysMenuDaoCustom;

    @Override
    public void save(SysMenu o) throws BusinessException {
        super.save(o);
        securityCache.clearAllMenu();
    }

    @Override
    public void update(SysMenu o) throws BusinessException {
        super.update(o);
        securityCache.clearAllMenu();
    }

    @Override
    @Transactional
    public int updateStatusById(Long id, Integer status){
        return super.getEntityDao().updateStatusById(id, status);
    }


    /**
     * 组装 MenuView
     * @param list 查询(角色 - 资源) 关联的二级菜单
     * @return
     */
    private List<MenuView> queryAuthorisedMenus(List<Object> list){
        Map<Long, LinkedList<MenuView>> menuViewMap = new HashMap<Long, LinkedList<MenuView>>();

        //查询一级菜单
        Map<String, Object> searchMap = new HashMap<String, Object>();
        Map<String, Boolean> sortMap = new HashMap<String, Boolean>();
        searchMap.put("EQ_parentId", 0);
        searchMap.put("EQ_status", 1);
        sortMap.put("sortNum", true);
        List<SysMenu> menuList = super.query(searchMap, sortMap);

        Iterator<Object> ie = list.iterator();
        //组装二级菜单的展示对象
        while (ie.hasNext()) {
            Object[] obj = (Object[]) ie.next();
            SysResource resource = (SysResource) obj[0];
            SysMenu menu = (SysMenu) obj[1];
            if(menuViewMap.containsKey(menu.getParentId())==false) {
                menuViewMap.put(menu.getParentId(), new LinkedList<MenuView>());
            }
            MenuView subMenuView = new MenuView(menu.getId(), menu.getTitle(),
                    menu.getIcon());
            subMenuView.getAttributes().put("url",
                    resource.getResString());
            subMenuView.getAttributes().put("firstSpeel", menu.getTitleFirstSpell());
            menuViewMap.get(menu.getParentId()).add(subMenuView);
        }

        //组装一级菜单的展示对象
        List<MenuView> menuViews = new LinkedList<MenuView>();
        for(SysMenu menu : menuList){
            if(menuViewMap.containsKey(menu.getId())){
                MenuView menuView = new MenuView(menu.getId(), menu.getTitle(),
                        menu.getIcon());
                menuView.getAttributes().put("showMode", menu.getShowMode()!=null ? String.valueOf(menu.getShowMode()) : "2");
                menuView.setChildren(menuViewMap.get(menu.getId()));

                menuViews.add(menuView);
            }
        }
        return menuViews;
    }

    @Override
    public List<MenuView> queryAuthorisedMenus(String userName) {
        return queryAuthorisedMenus(super.getEntityDao().queryAuthorisedMenusByUserName(userName));
    }

    @Override
    public List<MenuView> queryAuthorisedMenus(Long roleId,Long classify) {
        List<MenuView> list = securityCache.getMenu(roleId);
        //if(list == null && (list = queryAuthorisedMenus(super.getEntityDao().queryAuthorisedMenusByRole(roleId,classify)))!=null){
        List<Object> qlist=super.getEntityDao().queryAuthorisedMenusByRole(roleId,classify);
        if((list = queryAuthorisedMenus(qlist))!=null){
            securityCache.putMenu(roleId, list);
        }
        return list;
    }

    @Override
    @Transactional
    public int deleteTree(long id) {
        int count = super.getEntityDao().deleteTree(id);
        securityCache.clearAllMenu();
        return count;
    }

    @Override
    @Transactional
    public void sort(Long[] ids, Long[] sortNums, Long[] parentIds) {
        for(int j=0; j<ids.length; j++){
            super.getEntityDao().sort(ids[j], sortNums[j], parentIds[j]);
        }
        securityCache.clearAllMenu();
    }

	public PageInfo<SysMenu> getInfoPage(PageInfo<SysMenu> pageInfo, Map<String, Object> conditions,
			Map<String, Boolean> orderBy) {
		return sysMenuDaoCustom.getInfoPage(pageInfo, conditions, orderBy);
	}

	public List<SysMenu> getMenuList() {
		return sysMenuDaoCustom.getMenuList();
	}

	public int getMenuCount(Long parentId) {
		return this.getEntityDao().getMenuCount(parentId);
	}


}
