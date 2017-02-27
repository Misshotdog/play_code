package org.mi.core.dao.sys;

import java.util.List;

import org.mi.core.domain.security.SysResource;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.feinno.framework.common.dao.jpa.EntityJpaDao;

/**
 * 系统资源表 JPA Dao
 *
 * Date: 2015-05-12 15:39:57
 *
 * @author Code Generator
 *
 */
public interface SysResourceDao extends EntityJpaDao<SysResource, Long> {

    @Modifying
    @Query(value = "update SysResource t set t.status=?2 where id=?1")
    public int updateStatusById(Long id, Integer status);

    @Modifying
    @Query(value = "update SysResource set sortNum=?2, parentId=?3 where id=?1")
    int sort(Long id, Long sortNum, Long parentId);
    
    
    @Modifying
    @Query(value = "delete from SysResource where id=?1 or parentId=?1")
    int deleteResc(Long id);
    
    @Query(value="from SysResource where parentId=?1 and title=?2")
    public List<SysResource> queryRescByPid(Long parentId,String title);
    
    @Query(value="from SysResource where parentId=?1 and title !=?2")
    public List<SysResource>  queryResc(Long parentId,String title);
    
    @Modifying
    @Query(value="update SysResource set classify=?1 where parentId=?2")
    public void  updateResByPid(Long classify,Long parentId);
    
    @Query(value="from SysResource where parentId='0' or restype='URL'")
    public List<SysResource> queryResByType();
}
