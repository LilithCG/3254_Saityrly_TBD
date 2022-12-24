package com.delivery_service.service.cafe;

import com.delivery_service.postgres.repository.CafeRepository;
import com.delivery_service.dto.request.CafeRequest;
import com.delivery_service.postgres.entity.Cafe;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CafeServiceImpl implements CafeService{
    private final CafeRepository cafeRepository;

    private Cafe toCafe(CafeRequest cafeRequest) {
        return Cafe.builder()
                .id(cafeRequest.getCafeId())
                .name(cafeRequest.getName())
                .build();
    }
    @Override
    public void saveCafe(CafeRequest cafeRequest) {
        var cafe = toCafe(cafeRequest);
        cafeRepository.save(cafe);
    }

    @Override
    public List<Cafe> fetchCafeList() {
        return cafeRepository.findAll();
    }

    @Override
    public Cafe fetchCafe(long id) throws NotFoundException {
        return Optional.ofNullable(cafeRepository.findById(id)).orElseThrow(NotFoundException::new);
    }

    @Override
    public Optional<Cafe> fetchCafe(String name) throws NotFoundException {
        return Optional.ofNullable(cafeRepository.findByName(name)).orElseThrow(NotFoundException::new);
    }

    @Override
    public boolean exist(long id) {
        return cafeRepository.existsById(id);
    }
}
