package com.project.service.rentalservice;

import com.project.entity.BrandEntity;
import com.project.entity.CarEntity;
import com.project.entity.UserEntity;
import com.project.repository.UserRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class UserMockRepository {

    public static UserRepository getUserMockRepository(){
        return new UserRepository() {

            public List<UserEntity> users = new ArrayList<>();

            @Override
            public Boolean existsByUsername(final String username) {
                for (UserEntity x: users
                ) {
                    if(x.getUsername().equals(username))
                        return true;
                }
                return false;
            }

            @Override
            public UserEntity findByUsername(final String username) {
                for (UserEntity x: users
                ) {
                    if(x.getUsername().equals(username))
                       return x;
                }
               return null;
            }

            @Override
            public List<UserEntity> findAll() {
                return null;
            }

            @Override
            public List<UserEntity> findAll(final Sort sort) {
                return null;
            }

            @Override
            public List<UserEntity> findAllById(final Iterable<Long> iterable) {
                return null;
            }

            @Override
            public <S extends UserEntity> List<S> saveAll(final Iterable<S> iterable) {
                return null;
            }

            @Override
            public void flush() {

            }

            @Override
            public <S extends UserEntity> S saveAndFlush(final S s) {
                return null;
            }

            @Override
            public void deleteInBatch(final Iterable<UserEntity> iterable) {

            }

            @Override
            public void deleteAllInBatch() {

            }

            @Override
            public UserEntity getOne(final Long aLong) {
                return null;
            }

            @Override
            public <S extends UserEntity> List<S> findAll(final Example<S> example) {
                return null;
            }

            @Override
            public <S extends UserEntity> List<S> findAll(final Example<S> example, final Sort sort) {
                return null;
            }

            @Override
            public Page<UserEntity> findAll(final Pageable pageable) {
                return null;
            }

            @Override
            public <S extends UserEntity> S save(final S s) {
                users.removeIf(x -> x.getId().equals(s.getId()));
                users.add(s);
                return s;
            }

            @Override
            public Optional<UserEntity> findById(final Long aLong) {
                return Optional.empty();
            }

            @Override
            public boolean existsById(final Long aLong) {
                return false;
            }

            @Override
            public long count() {
                return 0;
            }

            @Override
            public void deleteById(final Long aLong) {

            }

            @Override
            public void delete(final UserEntity userEntity) {

            }

            @Override
            public void deleteAll(final Iterable<? extends UserEntity> iterable) {

            }

            @Override
            public void deleteAll() {

            }

            @Override
            public <S extends UserEntity> Optional<S> findOne(final Example<S> example) {
                return Optional.empty();
            }

            @Override
            public <S extends UserEntity> Page<S> findAll(final Example<S> example, final Pageable pageable) {
                return null;
            }

            @Override
            public <S extends UserEntity> long count(final Example<S> example) {
                return 0;
            }

            @Override
            public <S extends UserEntity> boolean exists(final Example<S> example) {
                return false;
            }
        };
    }
}
