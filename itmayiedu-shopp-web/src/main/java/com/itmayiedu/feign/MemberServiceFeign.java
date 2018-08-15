package com.itmayiedu.feign;

import com.itmayiedu.service.MemberService;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * Created by Mr.PanYang on 2018/8/14.
 */
@FeignClient("member")
@Component
public interface MemberServiceFeign extends MemberService {
}
