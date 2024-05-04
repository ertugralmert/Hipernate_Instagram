package com.mert.repository;

import com.mert.entity.Follow;
import com.mert.utility.Icrud;

import java.util.List;
import java.util.Optional;

public class FollowRepository implements Icrud<Follow, Long> {
    @Override
    public Follow save(Follow entity) {
        return null;
    }

    @Override
    public Iterable<Follow> saveAll(Iterable<Follow> entities) {
        return null;
    }

    @Override
    public Optional<Follow> findyById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public List<Follow> findAll() {
        return null;
    }

    @Override
    public List<Follow> findByColumnAndValue(String columnName, Object value) {
        return null;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public List<Follow> findAllByEntity(Follow entity) {
        return null;
    }
}
