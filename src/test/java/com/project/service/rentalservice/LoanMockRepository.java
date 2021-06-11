package com.project.service.rentalservice;

import com.project.entity.CarEntity;
import com.project.entity.LoanEntity;
import com.project.repository.LoanRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class LoanMockRepository {

    static LoanRepository getLoanMockRepository(){
        return new LoanRepository(){
            public List<LoanEntity> loans = new ArrayList<>();
            @Override
            public List<LoanEntity> findAll() {
                return loans;
            }

            @Override
            public List<LoanEntity> findAll(final Sort sort) {
                return null;
            }

            @Override
            public Page<LoanEntity> findAll(final Pageable pageable) {
                return null;
            }

            @Override
            public List<LoanEntity> findAllById(final Iterable<Long> iterable) {
                return null;
            }

            @Override
            public long count() {
                return 0;
            }

            @Override
            public void deleteById(final Long aLong) {

            }

            @Override
            public void delete(final LoanEntity loanEntity) {

            }

            @Override
            public void deleteAll(final Iterable<? extends LoanEntity> iterable) {

            }

            @Override
            public void deleteAll() {

            }

            @Override
            public <S extends LoanEntity> S save(final S s) {
                loans.removeIf(x -> x.getId().equals(s.getId()));
                loans.add(s);
                return s;
            }

            @Override
            public <S extends LoanEntity> List<S> saveAll(final Iterable<S> iterable) {
                return null;
            }

            @Override
            public Optional<LoanEntity> findById(final Long aLong) {
                return Optional.empty();
            }

            @Override
            public boolean existsById(final Long aLong) {
                return false;
            }

            @Override
            public void flush() {

            }

            @Override
            public <S extends LoanEntity> S saveAndFlush(final S s) {
                return null;
            }

            @Override
            public void deleteInBatch(final Iterable<LoanEntity> iterable) {

            }

            @Override
            public void deleteAllInBatch() {

            }

            @Override
            public LoanEntity getOne(final Long aLong) {
                return null;
            }

            @Override
            public <S extends LoanEntity> Optional<S> findOne(final Example<S> example) {
                return Optional.empty();
            }

            @Override
            public <S extends LoanEntity> List<S> findAll(final Example<S> example) {
                return null;
            }

            @Override
            public <S extends LoanEntity> List<S> findAll(final Example<S> example, final Sort sort) {
                return null;
            }

            @Override
            public <S extends LoanEntity> Page<S> findAll(final Example<S> example, final Pageable pageable) {
                return null;
            }

            @Override
            public <S extends LoanEntity> long count(final Example<S> example) {
                return 0;
            }

            @Override
            public <S extends LoanEntity> boolean exists(final Example<S> example) {
                return false;
            }

            @Override
            public List<LoanEntity> findAllByCar(final CarEntity carEntity) {
                return null;
            }
        };
    }
}
