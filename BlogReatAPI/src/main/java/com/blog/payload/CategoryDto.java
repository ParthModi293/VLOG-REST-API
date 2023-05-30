package com.blog.payload;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDto {

	private int categoryId;
	
	@NotBlank(message = "name should not be null")
	private String categoryTitle;
	
	@NotBlank
	private String categoryDescription;
	
}
