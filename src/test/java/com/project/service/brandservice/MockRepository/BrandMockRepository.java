package com.project.service.brandservice.MockRepository;

import com.project.entity.BrandEntity;
import com.project.repository.BrandRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BrandMockRepository {

    public static BrandRepository getMockBrandRepository(){
        return new BrandRepository() {
            public List<BrandEntity> brands = new ArrayList<>();

            @Override
            public List<BrandEntity> findAll() {
                return brands;
            }

            @Override
            public List<BrandEntity> findAll(final Sort sort) {
                return null;
            }

            @Override
            public List<BrandEntity> findAllById(final Iterable<Long> iterable) {
                return null;
            }

            @Override
            public <S extends BrandEntity> List<S> saveAll(final Iterable<S> iterable) {
                return null;
            }

            @Override
            public void flush() {

            }

            @Override
            public <S extends BrandEntity> S saveAndFlush(final S s) {
                return null;
            }

            @Override
            public void deleteInBatch(final Iterable<BrandEntity> iterable) {

            }

            @Override
            public void deleteAllInBatch() {

            }

            @Override
            public BrandEntity getOne(final Long aLong) {
                for (BrandEntity x: brands
                     ) {
                    if(x.getId().equals(aLong))
                        return x;
                }
                return null;
            }

            @Override
            public <S extends BrandEntity> List<S> findAll(final Example<S> example) {
                return null;
            }

            @Override
            public <S extends BrandEntity> List<S> findAll(final Example<S> example, final Sort sort) {
                return null;
            }

            @Override
            public Page<BrandEntity> findAll(final Pageable pageable) {
                return null;
            }

            @Override
            public <S extends BrandEntity> S save(final S s) {
                brands.removeIf(x -> x.getId().equals(s.getId()));
               brands.add(s);
               return s;

            }

            @Override
            public Optional<BrandEntity> findById(final Long aLong) {
                for (BrandEntity x: brands
                ) {
                    if(x.getId().equals(aLong))
                        return Optional.of(x);
                }
                return Optional.empty();
            }

            @Override
            public boolean existsById(final Long aLong) {
                for (BrandEntity x: brands
                ) {
                    if(x.getId().equals(aLong))
                        return true;
                }
                return false;
            }

            @Override
            public long count() {
                return brands.size();
            }

            @Override
            public void deleteById(final Long aLong) {
                brands.removeIf(x -> x.getId().equals(aLong));

            }

            @Override
            public void delete(final BrandEntity brandEntity) {
                brands.removeIf(x -> x.equals(brandEntity));
            }

            @Override
            public void deleteAll(final Iterable<? extends BrandEntity> iterable) {
            }

            @Override
            public void deleteAll() {

            }

            @Override
            public <S extends BrandEntity> Optional<S> findOne(final Example<S> example) {
                return Optional.empty();
            }

            @Override
            public <S extends BrandEntity> Page<S> findAll(final Example<S> example, final Pageable pageable) {
                return null;
            }

            @Override
            public <S extends BrandEntity> long count(final Example<S> example) {
                return brands.size();
            }

            @Override
            public <S extends BrandEntity> boolean exists(final Example<S> example) {
                return false;
            }
        };
    }
}
