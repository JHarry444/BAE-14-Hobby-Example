package com.qa.kittens.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.qa.kittens.data.Kitten;

@Service
public class KittenServiceList implements KittenService {
	private List<Kitten> kittens = new ArrayList<>();

	@Override
	public Kitten createKitten(Kitten kitten) { // less fancy
		System.out.println(kitten);
		this.kittens.add(kitten);
		return this.kittens.get(this.kittens.size() - 1);
	}

	@Override
	public List<Kitten> getAllKittens() {
		return this.kittens;
	}

	@Override
	public Kitten getKitten(int id) {
		return this.kittens.get(id);
	}

	@Override
	public Kitten replaceKitten(int id, Kitten newKitten) {
		return this.kittens.set(id, newKitten); // replace the kitten at index id
	}

	@Override
	public String deleteKitten(int id) {
		this.kittens.remove(id);

		return "Deleted kitten at index: " + id;
	}

	@Override
	public List<Kitten> getByName(String name) {
		return Collections.emptyList();
	}

	@Override
	public Kitten patchKitten(int id, Optional<String> name, Optional<Integer> age, Optional<String> breed, Optional<Integer> cuteness) {
		return null;
	}

}
