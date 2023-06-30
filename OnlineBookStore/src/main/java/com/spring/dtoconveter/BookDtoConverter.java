package com.spring.dtoconveter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;

import com.spring.dtoentity.BookDTO;
import com.spring.entity.Book;

public class BookDtoConverter {
	
	public Book DtotoEntity(BookDTO dto) {
		Book b=new Book();
		if(dto!=null) {
			BeanUtils.copyProperties(dto, b);
		}
		
		return b;
	}
	
	public BookDTO EntitytoDTO(Book b) {
		BookDTO dto=new BookDTO();
		if(b!=null) {
			BeanUtils.copyProperties(b, dto);
		}
		return dto;
	}
	
	public List<BookDTO> EntitytoListDTO(List<Book> b){
		return b.stream().map(x -> EntitytoDTO(x)).collect(Collectors.toList());
	}
	
	
}
