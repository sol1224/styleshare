package proj.petbuddy.service.store;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import proj.petbuddy.domain.map.Store;
import proj.petbuddy.repository.StoreRepository;

@RequiredArgsConstructor
@Transactional
@Service
public class StoreService {

    private final StoreRepository storeRepository;

    /**
     * 검색 서비스 (여성)
     **/
    @Transactional
    public Store search(String keyword) {
        Store list = storeRepository.findByNameOrAddress(keyword);
        return list;
    }
}
