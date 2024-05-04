package proj.petbuddy.service.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import proj.petbuddy.domain.item.WomenItem;
import proj.petbuddy.repository.SpeciesRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SpeciesService {

    private final SpeciesRepository speciesRepository;


    /**
     * 저장
     **/
    @Transactional
    public void saveSpecies(WomenItem womenItem) {
        speciesRepository.save(womenItem);
    }

    /**
     * 전체 조회
     **/
    public List<WomenItem> findAll() {
        return speciesRepository.findAll();
    }

    /**
     * 이름 조회
     **/
    public List<WomenItem> findByName(String name) {
        return speciesRepository.findByName(name);
    }

    /**
     * 수정
     * 변경감기 기능 사용 (Dirty Checking)
     */

    @Transactional
    public WomenItem updateSpecies(Long seq, WomenItem womenItem) {
        WomenItem findWomenItem = speciesRepository.findOne(seq);
        findWomenItem.setName(womenItem.getName());

        return findWomenItem;
    }
}
