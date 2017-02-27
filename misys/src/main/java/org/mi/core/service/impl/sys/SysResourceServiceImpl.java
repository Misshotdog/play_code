package org.mi.core.service.impl.sys;



import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.mi.core.dao.sys.SysResourceDao;
import org.mi.core.domain.security.SysResource;
import org.mi.core.service.sys.SysResourceService;
import org.mi.security.cache.SecurityCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.feinno.framework.common.exception.BusinessException;
import com.feinno.framework.common.service.EntityServiceImpl;

@Service("sysResourceService")
public class SysResourceServiceImpl extends EntityServiceImpl<SysResource, SysResourceDao> implements SysResourceService {

    private Logger logger = LoggerFactory.getLogger(SysResourceServiceImpl.class);

    @Autowired
    private SecurityCache securityCache;

    @Override
    public void update(SysResource o) throws BusinessException {
        super.update(o);
        securityCache.clearAllAuthorization();
        securityCache.clearAllMenu();
    }

    @Override
    @Transactional
    public int updateStatusById(Long id, Integer status){
        return super.getEntityDao().updateStatusById(id, status);
    }

    @Override
    @Transactional
    public void sort(Long[] ids, Long[] sortNums, Long[] parentIds) {
        for(int j=0; j<ids.length; j++){
            super.getEntityDao().sort(ids[j], sortNums[j], parentIds[j]);
        }
    }

    //递归查找
    private void recursionResc(Iterator<SysResource> it, String rescType, Integer status){
        while (it.hasNext()){
            SysResource resc = it.next();

            if((StringUtils.isNotBlank(rescType) && rescType.equals(resc.getRestype())==false)
                    || (status!=null && status!=resc.getStatus())){
                it.remove();
            }else if(resc.getSubResource()!=null && resc.getSubResource().size()>=1){
                recursionResc(resc.getSubResource().iterator(), rescType, status);
            }
        }
    }

    @Override
    public List<SysResource> queryByTypeAndStatus(String parentId,String type, Integer status) {
        Map<String, Object> seachMap = new HashMap<String, Object>();
        Map<String, Boolean> sortMap = new HashMap<String, Boolean>();
//        if(StringUtils.isNotBlank(parentId)){
//            seachMap.put("EQ_parentId", parentId);
//        }

        if(StringUtils.isNotBlank(type)){
            seachMap.put("EQ_restype", type);
        }
        if(status!=null){
            seachMap.put("EQ_status", status);
        }

        sortMap.put("createDate", true);

        List<SysResource> list = super.getEntityDao().query(seachMap, sortMap);

        //递归过滤条件
        recursionResc(list.iterator(), type, status);

        return list;
    }

    @Transactional
	public int deleteResc(Long id) {
		return this.getEntityDao().deleteResc(id);
	}

	public List<SysResource> queryRescByPid(Long parentId,String title) {
		return this.getEntityDao().queryRescByPid(parentId,title);
	}
	public List<SysResource> queryResc(Long parentId, String title) {
		return this.getEntityDao().queryResc(parentId, title);
	}
	
	@Transactional
	public void updateResByPid(Long classify,Long parentId) {
		this.getEntityDao().updateResByPid(classify,parentId);
	}

	public List<SysResource> queryResByType() {
		return this.getEntityDao().queryResByType();
	}


}
