package com.rtgs.rtgsapi.dtos.branch;

import com.rtgs.rtgsapi.config.HrClientRequestInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "branchClient", configuration = HrClientRequestInterceptor.class, url = "${client.hr.baseUrl}")
public interface BranchClient {
    @GetMapping("/branches/list")
    List<Branch> getBranches();
}
