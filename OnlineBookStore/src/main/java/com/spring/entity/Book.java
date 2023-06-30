package com.spring.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Book")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
	@Id
	@NotNull
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long bid;
	
	@Pattern(regexp = "^[A-Za-z0-9\\s]+$")
	@NotNull
	private String bname;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JsonBackReference
	private Author author;
	
	@Pattern(regexp = "\\d+")
	private String price;
}
