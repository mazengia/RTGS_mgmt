package com.rtgs.rtgsapi.reasons;

import com.rtgs.rtgsapi.dtos.employee.EmployeeClient;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReasonsService {
    private final ReasonsRepository reasonsRepository;

    EmployeeClient employeeService;
    public Page<Reasons> getAll(Pageable pageable) {
        Specification<Reasons> spec = Specification.where(
                (root, query, builder) -> {
                    var predicate = new ArrayList<>();
                    predicate.add(builder.isNull(root.get("deletedAt")));
                    return builder.and(predicate.toArray(new Predicate[0]));
                });
        return search(spec, pageable);
    }

    public Optional<Reasons> getOtherBranchById(long id) {
        return reasonsRepository.findById(id);
    }
    public Reasons create(Reasons customer, JwtAuthenticationToken token) {
        return reasonsRepository.save(customer);
    }

    public Page<Reasons> search(Specification<Reasons> spec, Pageable pageable) {
        return reasonsRepository.findAll(spec, pageable);
    }
    @Transactional
    public Reasons store(Reasons reasons) {
        return reasonsRepository.save(reasons);
    }


    public void deleteById(long id) {
        reasonsRepository.deleteById(id);
    }

    public Reasons updateById(Reasons reasons, long id, JwtAuthenticationToken token) {
        var oldData=getOtherBranchById(id);
        reasons.setId(id);
        reasons.setVersion(oldData.get().getVersion());
        return reasonsRepository.save(reasons);
    }
}
