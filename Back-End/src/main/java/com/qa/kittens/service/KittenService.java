package com.qa.kittens.service;

import java.util.List;
import java.util.Optional;

import com.qa.kittens.data.Kitten;

public interface KittenService {

	public Kitten createKitten(Kitten kitten);

	public List<Kitten> getAllKittens();

	public Kitten getKitten(int id);

	public Kitten patchKitten(int id, Optional<String> name, Optional<Integer> age, Optional<String> breed, Optional<Integer> cuteness);

	public Kitten replaceKitten(int id, Kitten newKitten);

	public String deleteKitten(int id);

	List<Kitten> getByName(String name);

}
