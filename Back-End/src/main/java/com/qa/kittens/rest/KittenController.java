package com.qa.kittens.rest;

import java.util.List;
import java.util.Optional;

import javax.websocket.server.PathParam;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.qa.kittens.data.Kitten;
import com.qa.kittens.service.KittenService;

@RestController // Tells Spring to make an instance of this class
@CrossOrigin
public class KittenController {

	// dependency
	private KittenService service;

	// spring injecting it into my class
	public KittenController(KittenService service) {
		super();
		this.service = service;
	}

	@GetMapping("/") // MAPS a GET request to "/" to this method
	public String hello() {
		return "Hello, World!";
	}

	@PostMapping("/createKitten") // fancy
	public ResponseEntity<Kitten> createKitten(@RequestBody Kitten kitten) { // less fancy
		Kitten created = this.service.createKitten(kitten);
		return new ResponseEntity<>(created, HttpStatus.CREATED); // body, code
	}

	@GetMapping("/getAllKittens")
	public List<Kitten> getAllKittens() {
		return this.service.getAllKittens();
	}

	@GetMapping("/getByName/{name}")
	public List<Kitten> getByName(@PathVariable String name) {
		return this.service.getByName(name);
	}

	@GetMapping("/getKitten/{id}")
	public Kitten getKitten(@PathVariable int id) {
		return this.service.getKitten(id);
	}

	@PatchMapping("/patchKitten/{id}")
	public ResponseEntity<Kitten> patchKitten(@PathVariable int id, @PathParam("name") Optional<String> name,
			@PathParam("age") Optional<Integer> age, @PathParam("breed") Optional<String> breed,
			@PathParam("cuteness") Optional<Integer> cuteness) {

		Kitten updated = this.service.patchKitten(id, name, age, breed, cuteness);

		return new ResponseEntity<>(updated, HttpStatus.ACCEPTED);
	}

	@PutMapping("/replaceKitten/{id}")
	public ResponseEntity<Kitten> replaceKitten(@PathVariable int id, @RequestBody Kitten newKitten) {
		Kitten body = this.service.replaceKitten(id, newKitten);
		return new ResponseEntity<>(body, HttpStatus.ACCEPTED);
	}

	@DeleteMapping("/deleteKitten/{id}")
	public ResponseEntity<String> deleteKitten(@PathVariable int id) {
		String body = this.service.deleteKitten(id);
		return new ResponseEntity<>(body, HttpStatus.NO_CONTENT);
	}

}
