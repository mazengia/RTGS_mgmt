package com.rtgs.rtgsapi.otherBranch;

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
public class OtherBranchService {
    private final OtherBranchRepository otherBranchRepository;

    EmployeeClient employeeService;
    public Page<OtherBank> getAll(Pageable pageable) {
        Specification<OtherBank> spec = Specification.where(
                (root, query, builder) -> {
                    var predicate = new ArrayList<>();
                    predicate.add(builder.isNull(root.get("deletedAt")));
                    return builder.and(predicate.toArray(new Predicate[0]));
                });
        return search(spec, pageable);
    }

    public Optional<OtherBank> getOtherBranchById(long id) {
        return otherBranchRepository.findById(id);
    }
    public OtherBank create(OtherBank customer, JwtAuthenticationToken token) {
        return otherBranchRepository.save(customer);
    }

    public Page<OtherBank> search(Specification<OtherBank> spec, Pageable pageable) {
        return otherBranchRepository.findAll(spec, pageable);
    }
    @Transactional
    public OtherBank store(OtherBank otherBank) {
        return otherBranchRepository.save(otherBank);
    }


    public void deleteById(long id) {
        otherBranchRepository.deleteById(id);
    }

    public OtherBank updateById(OtherBank otherBank, long id, JwtAuthenticationToken token) {
        var oldData=getOtherBranchById(id);
        otherBank.setId(id);
        otherBank.setVersion(oldData.get().getVersion());
        return otherBranchRepository.save(otherBank);
    }
}
