package com.rtgs.rtgsapi.dtos.branch;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/branches")
@RequiredArgsConstructor
public class BranchService {
    private final BranchClient branchClient;
    @GetMapping
    public List<Branch> getBranches() {
        return branchClient.getBranches();
    }

}
