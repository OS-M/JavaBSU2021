package com.quackable.photo.repositories;

import com.quackable.photo.model.PhotoSet;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoSetRepository extends CrudRepository<PhotoSet, Integer> { }
