package com.rtgs.rtgsapi.otherBranch;

import com.rtgs.rtgsapi.utils.PaginatedResultsRetrievedEvent;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@RestController
@RequestMapping("/other-branch")
@RequiredArgsConstructor
public class OtherBranchController {
    private final ApplicationEventPublisher eventPublisher;
    private final OtherBranchMapper otherBranchMapper;
    private final OtherBranchService otherBranchService;

    @GetMapping()
    private ResponseEntity<PagedModel<OtherBranchDto>> getAll(Pageable pageable, PagedResourcesAssembler assembler, UriComponentsBuilder uriBuilder, HttpServletResponse response) {
        eventPublisher.publishEvent(new PaginatedResultsRetrievedEvent<>(
                OtherBranchDto.class, uriBuilder, response, pageable.getPageNumber(), otherBranchService.getAll(pageable).getTotalPages(), pageable.getPageSize()));
        return new ResponseEntity<PagedModel<OtherBranchDto>>(assembler.toModel(otherBranchService.getAll(pageable).map(otherBranchMapper::toOtherBranchDto)), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    private Optional<OtherBank> getRtgsById(@PathVariable long id) {
        return otherBranchService.getOtherBranchById(id);
    }
    @PostMapping()
    public OtherBank store(@RequestBody OtherBank otherBank) {
        return otherBranchService.store(otherBank);
    }
    @DeleteMapping("/{id}")
    private void DeleteById(@PathVariable long id) {
        otherBranchService.deleteById(id);
    }

    @PutMapping("/{id}")
    private OtherBank updateById(@RequestBody OtherBank otherBank, @PathVariable long id, JwtAuthenticationToken token) {
        return otherBranchService.updateById(otherBank, id,token);
    }
}
