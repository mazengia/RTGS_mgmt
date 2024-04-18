package com.rtgs.rtgsapi.rtgs;

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

@RestController
@RequestMapping("/rtgs")
@RequiredArgsConstructor
public class RtgsController {
    private final ApplicationEventPublisher eventPublisher;
    private final RtgsMapper rtgsMapper;
    private final RtgsService rtgsService;

    @GetMapping()
    private ResponseEntity<PagedModel<RtgsDto>> getAll(Pageable pageable, PagedResourcesAssembler assembler, UriComponentsBuilder uriBuilder, HttpServletResponse response) {
        eventPublisher.publishEvent(new PaginatedResultsRetrievedEvent<>(
                RtgsDto.class, uriBuilder, response, pageable.getPageNumber(), rtgsService.searchByChecker(pageable).getTotalPages(), pageable.getPageSize()));
        return new ResponseEntity<PagedModel<RtgsDto>>(assembler.toModel(rtgsService.searchByChecker(pageable).map(rtgsMapper::toRtgsDto)), HttpStatus.OK);
    }
    @GetMapping("/maker")
    private ResponseEntity<PagedModel<RtgsDto>> getAllByMakerBranch(Pageable pageable, PagedResourcesAssembler assembler, UriComponentsBuilder uriBuilder, HttpServletResponse response,JwtAuthenticationToken token) {
        var employee = rtgsService.getEmployeeFromToken(token);
        eventPublisher.publishEvent(new PaginatedResultsRetrievedEvent<>(
                RtgsDto.class, uriBuilder, response, pageable.getPageNumber(), rtgsService.getRtgsByMaker(pageable,employee).getTotalPages(), pageable.getPageSize()));
        return new ResponseEntity<PagedModel<RtgsDto>>(assembler.toModel(rtgsService.getRtgsByMaker(pageable,employee).map(rtgsMapper::toRtgsDto)), HttpStatus.OK);
    }
    @GetMapping("/approved")
    private ResponseEntity<PagedModel<RtgsDto>> getApprovedRtgs(Pageable pageable, PagedResourcesAssembler assembler, UriComponentsBuilder uriBuilder, HttpServletResponse response) {
        eventPublisher.publishEvent(new PaginatedResultsRetrievedEvent<>(
                RtgsDto.class, uriBuilder, response, pageable.getPageNumber(), rtgsService.getApprovedRtgs(pageable).getTotalPages(), pageable.getPageSize()));
        return new ResponseEntity<PagedModel<RtgsDto>>(assembler.toModel(rtgsService.getApprovedRtgs(pageable).map(rtgsMapper::toRtgsDto)), HttpStatus.OK);
    }
    @GetMapping("/district")
    private ResponseEntity<PagedModel<RtgsDto>> getRtgsByDistrict(Pageable pageable, PagedResourcesAssembler assembler, UriComponentsBuilder uriBuilder, HttpServletResponse response,JwtAuthenticationToken token) {
        var employee = rtgsService.getEmployeeFromToken(token);
        eventPublisher.publishEvent(new PaginatedResultsRetrievedEvent<>(
                RtgsDto.class, uriBuilder, response, pageable.getPageNumber(), rtgsService.getRtgsByDistrict(pageable,employee).getTotalPages(), pageable.getPageSize()));
        return new ResponseEntity<PagedModel<RtgsDto>>(assembler.toModel(rtgsService.getRtgsByDistrict(pageable,employee).map(rtgsMapper::toRtgsDto)), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    private Rtgs getRtgsById(@PathVariable long id) {
        return rtgsService.getRtgsById(id);
   }
    @PostMapping()
    public Rtgs store( @RequestBody Rtgs rtgs, JwtAuthenticationToken token) {
        var employee = rtgsService.getEmployeeFromToken(token);
        return rtgsService.store(rtgs, employee);
    }
    @DeleteMapping("/{id}")
    private void DeleteRtgsById(@PathVariable long id) {
        rtgsService.deleteById(id);
    }

    @PutMapping("/{id}")
    private Rtgs updateRtgsById(@RequestBody Rtgs rtgs, @PathVariable long id, JwtAuthenticationToken token) {
        var employee = rtgsService.getEmployeeFromToken(token);
        return rtgsService.updateRtgsById(rtgs, id,employee);
    }


}
