package com.qa.kittens.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
// tells Spring that this class represents a table in the db
public class Kitten {

	@Id // Primary Key
	@GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO_INCREMENT
	private int id;

	@Column(name = "fullName", unique = true)
	private String name;
	private String breed;

	private int age;
	private int cuteness;

	public Kitten(int id, String name, String breed, int age, int cuteness) {
		super();
		this.id = id;
		this.name = name;
		this.breed = breed;
		this.age = age;
		this.cuteness = cuteness;
	}

	public Kitten(String name, String breed, int age, int cuteness) {
		super();
		this.name = name;
		this.breed = breed;
		this.age = age;
		this.cuteness = cuteness;
	}

	public Kitten() {
		// REQUIRED
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + age;
		result = prime * result + ((breed == null) ? 0 : breed.hashCode());
		result = prime * result + cuteness;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Kitten other = (Kitten) obj;
		if (age != other.age)
			return false;
		if (breed == null) {
			if (other.breed != null)
				return false;
		} else if (!breed.equals(other.breed))
			return false;
		if (cuteness != other.cuteness)
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	// REQUIRED
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBreed() {
		return breed;
	}

	public void setBreed(String breed) {
		this.breed = breed;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getCuteness() {
		return cuteness;
	}

	public void setCuteness(int cuteness) {
		this.cuteness = cuteness;
	}

	@Override
	public String toString() {
		return "Kitten [name=" + name + ", breed=" + breed + ", age=" + age + ", cuteness=" + cuteness + "]";
	}

}
