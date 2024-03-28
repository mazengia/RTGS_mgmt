package com.rtgs.rtgsapi.rtgs;

import com.rtgs.rtgsapi.dtos.employee.EmployeeClient;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RtgsService {
    private final RtgsRepository rtgsRepository;
  private final EmployeeClient employeeService;

    public Page<Rtgs> searchByChecker(Pageable pageable, JwtAuthenticationToken token) {
        Specification<Rtgs> spec = Specification.where(
                (root, query, builder) -> builder.isNull(root.get("deletedAt"))
        );
        return search(spec, pageable);
    }

    public Page<Rtgs> searchByMaker(Pageable pageable, JwtAuthenticationToken token) {
        String employeeId = (String) token.getTokenAttributes().get("employeeID");
        var brCode =employeeService.getEmployeesByEmployeeId(employeeId).getBranch().getCode();
        Specification<Rtgs> spec = Specification.where(
                (root, query, builder) -> builder.and(
                        builder.isNull(root.get("deletedAt")),
                        builder.equal(root.get("branch").get("code"), brCode)
                )
        );
        return search(spec, pageable);
    }
    public Optional<Rtgs> getRtgsById(long id) {
        return rtgsRepository.findById(id);
    }


    public Page<Rtgs> search(Specification<Rtgs> spec, Pageable pageable) {
        return rtgsRepository.findAll(spec, pageable);
    }

    public Rtgs search(Specification<Rtgs> spec) {
        return (Rtgs) rtgsRepository.findAll(spec);
    }

//    @Transactional
    public Rtgs store(Rtgs rtgs, JwtAuthenticationToken token) {
        var employeeId = (String) token.getTokenAttributes().get("employeeID");

        System.out.println("token=");
        System.out.println(employeeId);
        var employee = employeeService.getEmployeesByEmployeeId(employeeId);
        rtgs.setBranch(employee.getBranch());
        return rtgsRepository.save(rtgs);
    }

    public void deleteById(long id) {
        rtgsRepository.deleteById(id);
    }

    public Rtgs updateRtgsById(Rtgs rtgs, long id, JwtAuthenticationToken token) {
        var employeeId = (String) token.getTokenAttributes().get("employeeID");
        var employee = employeeService.getEmployeesByEmployeeId(employeeId);
        rtgs.setBranch(employee.getBranch());
        rtgs.setId(id);
        return rtgsRepository.save(rtgs);
    }
}
