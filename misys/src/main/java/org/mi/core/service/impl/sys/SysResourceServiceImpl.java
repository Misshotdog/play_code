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
	
}
