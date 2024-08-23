package clpetition.backend.record.repository;

import clpetition.backend.member.domain.Member;
import clpetition.backend.record.domain.Record;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecordRepository extends JpaRepository<Record, Long>, RecordQueryRepository {
    Long countByMember(Member member);
    List<Record> findByMemberOrderByDateDesc(Member member);
}
