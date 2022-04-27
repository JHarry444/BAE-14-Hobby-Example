package com.qa.kittens.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import com.qa.kittens.data.Kitten;
import com.qa.kittens.data.repos.KittenRepo;

@SpringBootTest
@ActiveProfiles("test")
public class KittenServiceDBUnitTest {

	@Autowired // injects the actual service from the context
	private KittenServiceDB service;

	@MockBean // tells Spring to make a 'fake' KittenRepo that we can program
	private KittenRepo repo;

	@Test
	void testCreate() {

		Kitten newKitten = new Kitten("Jess", "Black and White", 12, 10);

		Kitten savedKitten = new Kitten(1, "Jess", "Black and White", 12, 10);

		Mockito.when(this.repo.save(newKitten)).thenReturn(savedKitten);

		assertThat(this.service.createKitten(newKitten)).isEqualTo(savedKitten);

		Mockito.verify(this.repo, Mockito.times(1)).save(newKitten);
	}

	@Test
	void testGetAll() {
		List<Kitten> testKittens = List.of(new Kitten(1, "Jess", "Black and White", 12, 10));

		Mockito.when(this.repo.findAll()).thenReturn(testKittens);

		assertThat(this.service.getAllKittens()).isEqualTo(testKittens);

		Mockito.verify(this.repo, Mockito.times(1)).findAll();
	}

	@Test
	void testGetAllByName() {

		List<Kitten> testKittens = List.of(new Kitten(1, "Jess", "Black and White", 12, 10));

		String search = "jess";
		Mockito.when(this.repo.findByNameStartsWithIgnoreCase(search)).thenReturn(testKittens);

		assertThat(this.service.getByName(search)).isEqualTo(testKittens);

		Mockito.verify(this.repo, Mockito.times(1)).findByNameStartsWithIgnoreCase(search);
	}

	@Test
	void testUpdate() {
		// GIVEN
		int id = 1;

		Kitten testKitten = new Kitten(id, "Xerxes", "Blue Persian", 8, 7); // returned by FindById
		Kitten testNewKitten = new Kitten(id, "Moggie", "Rex", 12, 8); // new cat data

		// WHEN
		Mockito.when(this.repo.findById(id)).thenReturn(Optional.of(testKitten)); // dw about this for now tbh
		Mockito.when(this.repo.save(new Kitten(id, "Moggie", "Rex", 12, 8))).thenReturn(testNewKitten);

		Kitten actual = this.service.replaceKitten(id, testNewKitten);
		// THEN
		assertThat(actual).isEqualTo(testNewKitten);

		Mockito.verify(this.repo, Mockito.times(1)).findById(id);
		Mockito.verify(this.repo, Mockito.times(1)).save(new Kitten(id, "Moggie", "Rex", 12, 8));
	}

	@Test
	void testDeleteSucceeds() {
		int id = 1;

		Mockito.when(this.repo.existsById(id)).thenReturn(false);

		assertThat(this.service.deleteKitten(id)).isEqualTo("Deleted: " + id);

		Mockito.verify(this.repo, Mockito.times(1)).existsById(id);
	}

	@Test
	void testDeleteFails() {
		int id = 1;

		Mockito.when(this.repo.existsById(id)).thenReturn(true);

		assertThat(this.service.deleteKitten(id)).isEqualTo("Not deleted: " + id);

		Mockito.verify(this.repo, Mockito.times(1)).existsById(id);
	}

}
