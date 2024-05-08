package com.rtgs.rtgsapi.reasons;

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
@RequestMapping("/reasons")
@RequiredArgsConstructor
public class ReasonsController {
    private final ApplicationEventPublisher eventPublisher;
    private final ReasonsMapper reasonsMapper;
    private final ReasonsService reasonsService;

    @GetMapping()
    private ResponseEntity<PagedModel<ReasonsDto>> getAll(Pageable pageable, PagedResourcesAssembler assembler, UriComponentsBuilder uriBuilder, HttpServletResponse response) {
        eventPublisher.publishEvent(new PaginatedResultsRetrievedEvent<>(
                ReasonsDto.class, uriBuilder, response, pageable.getPageNumber(), reasonsService.getAll(pageable).getTotalPages(), pageable.getPageSize()));
        return new ResponseEntity<PagedModel<ReasonsDto>>(assembler.toModel(reasonsService.getAll(pageable).map(reasonsMapper::toReasonsDto)), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    private Optional<Reasons> getReasonsById(@PathVariable long id) {
        return reasonsService.getOtherBranchById(id);
    }
    @PostMapping()
    public Reasons store(@RequestBody Reasons reasons) {
        return reasonsService.store(reasons);
    }
    @DeleteMapping("/{id}")
    private void DeleteById(@PathVariable long id) {
        reasonsService.deleteById(id);
    }

    @PutMapping("/{id}")
    private Reasons updateById(@RequestBody Reasons reasons, @PathVariable long id, JwtAuthenticationToken token) {
        return reasonsService.updateById(reasons, id,token);
    }
}
