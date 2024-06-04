package com.indocyber.Winterhold.services;


import com.indocyber.Winterhold.dtos.category.*;
import com.indocyber.Winterhold.models.Category;
import com.indocyber.Winterhold.models.Book;
import com.indocyber.Winterhold.repositories.BookRepository;
import com.indocyber.Winterhold.repositories.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private CategoryRepository categoryRepository;
    private BookRepository bookRepository;

    public CategoryService(CategoryRepository categoryRepository, BookRepository bookRepository) {
        this.categoryRepository = categoryRepository;
        this.bookRepository = bookRepository;
    }

    public Page<CategoryRowItemDto> getAll(CategorySearchDto dto) {
        String name = dto.getName() == null || dto.getName().isBlank() ? null : dto.getName();

        int pageNumber = dto.getPageNumber() == null ? 0 : dto.getPageNumber();
        int pageSize = dto.getPageSize() == null ? 10 : dto.getPageSize();
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Category> CategoryPage = categoryRepository.findAll(pageable, name);
        List<CategoryRowItemDto> dtos = CategoryPage.getContent()
                .stream().map(category -> CategoryRowItemDto.builder()
                        .name(category.getName())
                        .floor(category.getFloor())
                        .isla(category.getIsle())
                        .bay(category.getBay())
                        .totalBook(getTotalBook(category.getName()))
                        .build())
                .toList();
        return new PageImpl<>(dtos, pageable, CategoryPage.getTotalElements());
    }

    public Integer getTotalBook(String name) {
        return bookRepository.countBookInCategory(name);
    }


    public CategoryUpsertResponseDto save(CategoryUpsertRequestDto dto) {
        var category = Category.builder()
                        .name(dto.getName())
                        .floor(dto.getFloor())
                        .isle(dto.getIsle())
                        .bay(dto.getBay())
                        .build();

        category = categoryRepository.save(category);

        return convertResponseDto(category);
    }

    private CategoryUpsertResponseDto convertResponseDto(Category category) {
        return CategoryUpsertResponseDto.builder()
                .name(category.getName())
                .floor(category.getFloor())
                .isle(category.getIsle())
                .bay(category.getBay())
                .build();
    }

    public CategoryUpsertRequestDto getById(String name) {
        Category category = cekFindByID(name);
        return convertCategoryUpsertRequestDto(category);
    }

    public CategoryDeleteItemDto getItemByIdForDelete(String name) {
        Category category = cekFindByID(name);
        return CategoryDeleteItemDto.builder()
                .name(category.getName())
                .build();
    }

    private Category cekFindByID(String name) {
        return categoryRepository.findById(name)
                .orElseThrow(() -> new IllegalArgumentException("Nama Category tidak ditemukan"));
    }

    private CategoryUpsertRequestDto convertCategoryUpsertRequestDto(Category category) {
        return CategoryUpsertRequestDto.builder()
                .name(category.getName())
                .floor(category.getFloor())
                .isle(category.getIsle())
                .bay(category.getBay())
                .build();
    }

    public void deleteById(String name) {
        categoryRepository.deleteById(name);
    }


}
