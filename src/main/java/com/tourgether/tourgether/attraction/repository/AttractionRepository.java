package com.tourgether.tourgether.attraction.repository;

import com.tourgether.tourgether.attraction.entity.Attraction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttractionRepository extends JpaRepository<Attraction, Long> {

}
