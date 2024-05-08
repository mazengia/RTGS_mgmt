package com.rtgs.rtgsapi.dtos.employee;

import com.rtgs.rtgsapi.config.HrClientRequestInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "employeeClient", configuration = HrClientRequestInterceptor.class, url = "${client.hr.baseUrl}")
public interface EmployeeClient {

    @GetMapping("/employees/by-employeeId/{employeeId}")
    Employee getEmployeesByEmployeeId(@PathVariable String employeeId);
}
