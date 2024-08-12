package com.rtgs.rtgsapi.rtgs;

import com.rtgs.rtgsapi.dtos.employee.Employee;
import com.rtgs.rtgsapi.dtos.employee.EmployeeClient;
import com.rtgs.rtgsapi.exceptions.RuntimeExceptions;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.rtgs.rtgsapi.utils.Util.getEmployeeID;
import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class RtgsService {
    private final RtgsRepository rtgsRepository;
    private final EmployeeClient employeeClient;

    public Page<Rtgs> searchByChecker(Pageable pageable) {
        Specification<Rtgs> spec = Specification.where(
                (root, query, builder) -> {
                    var predicate = new ArrayList<>();
                    predicate.add(builder.isNull(root.get("deletedAt")));
                    return builder.and(predicate.toArray(new Predicate[0]));
                });
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "id"));
        return search(spec, pageRequest);
    }
    public Page<Rtgs> searchByStatus(Status status,Pageable pageable) {
        Specification<Rtgs> spec = Specification.where(
                (root, query, builder) -> {
                    var predicate = new ArrayList<>();
                    predicate.add(builder.isNull(root.get("deletedAt")));
                    predicate.add(builder.equal(root.get("ho"), status));
                    return builder.and(predicate.toArray(new Predicate[0]));
                });
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "id"));
        return search(spec, pageRequest);
    }
    public Page<Rtgs> getRtgsByMaker(Pageable pageable, Employee employee) {
        try {
            var brCode = employee.getBranch().getCode();
            Specification<Rtgs> spec = Specification.where(
                    (root, query, builder) -> {
                        var predicate = new ArrayList<>();
                        predicate.add(builder.equal(root.get("branch").get("code"), brCode));
                        predicate.add(builder.isNull(root.get("deletedAt")));
                        return builder.and(predicate.toArray(new Predicate[0]));
                    });
            PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "id"));
            return search(spec, pageRequest);
        } catch (Exception exception) {
            throw new RuntimeExceptions(exception.getMessage());
        }
    }

    public Page<Rtgs> getApprovedRtgs(Pageable pageable) {
        Specification<Rtgs> spec = Specification.where(
                (root, query, builder) -> {
                    var predicate = new ArrayList<>();
                    predicate.add(builder.equal(root.get("status"), Status.A));
                    predicate.add(builder.isNull(root.get("deletedAt")));
                    return builder.and(predicate.toArray(new Predicate[0]));
                });
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "id"));
        return search(spec, pageRequest);
    }

    //    public <S extends Rtgs> S getRtgsById(long id) {
//        return rtgsRepository.findById(id);
//    }
    public <S extends Rtgs> S getRtgsById(long id) {
        Optional<Rtgs> optionalRtgs = rtgsRepository.findById(id);
        return (S) optionalRtgs.orElse(null); // or handle the case when it's empty
    }

    public Page<Rtgs> getRtgsByDistrict(Pageable pageable, Employee employee) {
        String districtCode = employee.getBranch().getDistrict().getCode();
        Specification<Rtgs> spec = Specification.where(
                (root, query, builder) -> {
                    var predicate = new ArrayList<>();
                    predicate.add(builder.equal(root.get("branch").get("district").get("code"), districtCode));
                    predicate.add(builder.isNull(root.get("deletedAt")));
                    return builder.and(predicate.toArray(new Predicate[0]));
                });
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "id"));
        return search(spec, pageRequest);
    }


    public Page<Rtgs> search(Specification<Rtgs> spec, Pageable pageable) {
        return rtgsRepository.findAll(spec, pageable);
    }


    //    @Transactional
    public Rtgs store(Rtgs rtgs, Employee employee) {
        rtgs.setBranch(employee.getBranch());
        rtgs.setStatus(Status.P);
        rtgs.setHo(Status.P);
        return rtgsRepository.save(rtgs);
    }

    public Employee getEmployeeFromToken(JwtAuthenticationToken jwtAuthentication) {
        return employeeClient.getEmployeesByEmployeeId(getEmployeeID(jwtAuthentication));
    }

    public void deleteById(long id) {
        rtgsRepository.deleteById(id);
    }

    public Rtgs updateRtgsById(Rtgs rtgs, long id, Employee employee) {
        try {
            var oldData = getRtgsById(id);
            rtgs.setVersion(oldData.getVersion());
            rtgs.setId(id);

            if (rtgs.getStatus() == null) {
                rtgs.setStatus(oldData.getStatus());
            }
            List<Feedback> existingFeedback = new ArrayList<>();
            if (rtgs.getRemark()!=null&&!rtgs.getRemark().isBlank()) {
                Feedback newFeedbacks = new Feedback();
                newFeedbacks.setComment(rtgs.getRemark());
                newFeedbacks.setPostedBy(format("%s %s", employee.getFirstName(), employee.getMiddleName()));
                if (oldData.getFeedbacks() != null) {
                    existingFeedback.addAll(oldData.getFeedbacks().feedbacks());
                }
                existingFeedback.add(newFeedbacks);
                rtgs.setFeedbacks(new Feedbacks(existingFeedback));
            }
            else {

                rtgs.setFeedbacks(oldData.getFeedbacks());
            }
            rtgs.setRemark(null);

            rtgs.setBranch(oldData.getBranch());
            return rtgsRepository.save(rtgs);
        }
        catch (Exception exception){
            throw new RuntimeExceptions(exception.getMessage());
        }
    }

}
