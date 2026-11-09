package com.crm.erp.external;

import com.crm.erp.exception.domain.ProfileNotFoundException;
import com.crm.erp.model.response.ProfileResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("profile")
public interface ProfileFeignClient {

    @GetMapping(value = "/api/v1/profile/{id}",consumes = "application/json")
    ResponseEntity<ProfileResponse> getProfileById(@PathVariable String id) throws ProfileNotFoundException;
}
