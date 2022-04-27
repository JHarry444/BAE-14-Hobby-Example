package com.qa.kittens.data.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qa.kittens.data.Kitten;

@Repository
public interface KittenRepo extends JpaRepository<Kitten, Integer> {

	List<Kitten> findByNameStartsWithIgnoreCase(String name);

}
