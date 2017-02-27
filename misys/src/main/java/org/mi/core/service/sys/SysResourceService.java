package org.mi.core.service.sys;


import java.util.List;

import org.mi.core.domain.security.SysResource;

import com.feinno.framework.common.service.EntityService;

/**
 * 系统资源表 Service
 *
 * Date: 2015-05-12 15:39:57
 *
 * @author Code Generator
 *
 */
public interface SysResourceService extends EntityService<SysResource> {

    public int updateStatusById(Long id, Integer status);

    /**
     * 批量修改排序值
     * @param ids
     * @param sortNums
     * @param parentIds
     */
    public void sort(Long[] ids, Long[] sortNums, Long[] parentIds);

    /**
     * 通过递归查找包含子资源
     * @param type
     * @param status
     * @return
     */
    public List<SysResource> queryByTypeAndStatus(String parentId, String type, Integer status);

    
    public int deleteResc(Long id);
    
    public List<SysResource> queryRescByPid(Long parentId,String title);
    
    public List<SysResource>  queryResc(Long parentId,String title);
    
    public void  updateResByPid(Long classify,Long parentId);
    
    public List<SysResource> queryResByType();

}
