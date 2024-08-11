package clpetition.backend.gym.service;

import clpetition.backend.gym.domain.Gym;
import clpetition.backend.gym.domain.VisitsGym;
import clpetition.backend.gym.repository.VisitsGymRepository;
import clpetition.backend.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class VisitsGymService {

    private final VisitsGymRepository visitsGymRepository;

    /**
     * 암장 방문횟수 수정 (+1)
     * */
    public void increaseVisitsGym(Member member, Gym gym) {
        VisitsGym visitsGym = getVisitsGym(member, gym);
        increaseVisits(visitsGym);
    }

    /**
     * 암장 방문횟수 수정 (-1)
     * */
    public void decreaseVisitsGym(Member member, Gym gym) {
        VisitsGym visitsGym = getVisitsGym(member, gym);
        decreaseVisits(visitsGym);
    }

    /**
     * 암장 방문횟수 객체 가져오기
     * 1. 없을 경우 초기화해서 가져오기
     * */
    private VisitsGym getVisitsGym(Member member, Gym gym) {
        return visitsGymRepository.findByMemberAndGym(member, gym)
                .orElseGet(() -> VisitsGym.builder()
                        .member(member)
                        .gym(gym)
                        .visits(0L)
                        .build());
    }

    /**
     * 암장 방문횟수 +1
     * */
    private void increaseVisits(VisitsGym visitsGym) {
        visitsGymRepository.save(
                VisitsGym.builder()
                        .id(visitsGym.getId())
                        .member(visitsGym.getMember())
                        .gym(visitsGym.getGym())
                        .visits(visitsGym.getVisits() + 1)
                        .build()
        );
    }

    /**
     * 암장 방문횟수 -1
     * */
    private void decreaseVisits(VisitsGym visitsGym) {
        visitsGymRepository.save(
                VisitsGym.builder()
                        .id(visitsGym.getId())
                        .member(visitsGym.getMember())
                        .gym(visitsGym.getGym())
                        .visits(visitsGym.getVisits() - 1)
                        .build()
        );
    }
}
