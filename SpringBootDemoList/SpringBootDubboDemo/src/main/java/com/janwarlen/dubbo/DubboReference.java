package com.janwarlen.dubbo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.deppon.data.platform.common.service.ResultDTO;
import com.deppon.data.platform.demo.api.facade.dto.DemoDTO;
import com.deppon.data.platform.demo.api.facade.service.DemoDubboServiceFacade;
import io.shulie.center.user.api.model.entity.Org;
import io.shulie.center.user.api.service.OrgService;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName: DubboReference
 * @author: wangjian
 * @Date: 2019/8/21 15:06
 * @Description:
 */
@Component
public class DubboReference {

    public void query() {

    }
}
