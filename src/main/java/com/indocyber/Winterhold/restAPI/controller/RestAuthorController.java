package com.indocyber.Winterhold.restAPI.controller;

import com.indocyber.Winterhold.dtos.author.AuthorSearchDto;
import com.indocyber.Winterhold.dtos.author.AuthorUpsertRequestDto;
import com.indocyber.Winterhold.restAPI.dto.RestAuthorRowItemDto;
import com.indocyber.Winterhold.services.AuthorService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/authors")
public class RestAuthorController {
    private final AuthorService authorService;

    public RestAuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("")
    public ResponseEntity<Page<RestAuthorRowItemDto>> gelAll(@RequestParam("name") String name,
                                                             AuthorSearchDto dto) {

        return ResponseEntity.ok(authorService.getAllRest(dto));
    }

    @GetMapping("{id}")
    public ResponseEntity<RestAuthorRowItemDto> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(authorService.getByIdRest(id));
    }

    @PostMapping("")
    public ResponseEntity<RestAuthorRowItemDto> insert(@Valid @RequestBody AuthorUpsertRequestDto dto) {
        return ResponseEntity.ok(authorService.insert(dto));
    }

    @PutMapping("{id}")
    public ResponseEntity<RestAuthorRowItemDto> update(@Valid @RequestBody AuthorUpsertRequestDto dto, @PathVariable Integer id ){
        return  ResponseEntity.ok(authorService.insert(dto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<RestAuthorRowItemDto> delete(@PathVariable Integer id){
        return ResponseEntity.ok(authorService.delete(id));
    }



}
