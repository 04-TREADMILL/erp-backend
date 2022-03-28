package com.nju.edu.erp.web.controller;

import com.nju.edu.erp.service.CategoryService;
import com.nju.edu.erp.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/category")
public class CategoryController {

    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/create")
    public Response createCategory(@RequestParam(value = "parentId") int parentId,
                               @RequestParam(value = "name") String name) {
        return Response.buildSuccess(categoryService.createCategory(parentId, name));
    }

    @GetMapping("/queryAll")
    public Response queryAllCategory() {
        return Response.buildSuccess(categoryService.queryAllCategory());
    }

    @GetMapping("/update")
    public Response updateCategory(@RequestParam(value = "id") int id,
                                   @RequestParam(value = "name") String name) {
        return Response.buildSuccess(categoryService.updateCategory(id, name));
    }

    @GetMapping("/delete")
    public Response deleteCategory(@RequestParam(value = "id") int id) {
        categoryService.deleteCategory(id);
        return Response.buildSuccess();
    }
}
