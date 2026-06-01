package com.example.productjpa.controller;

import com.example.productjpa.entity.Product;
import com.example.productjpa.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String homeRedirect() {
        return "redirect:/products/list";
    }

    @GetMapping("/list")
    public String listProducts(Model model) {
        model.addAttribute("products", productService.findAll());
        return "product/list";
    }

    @GetMapping("/search")
    public String searchProducts(@RequestParam(required = false) String keyword, Model model) {
        if (keyword != null && !keyword.isEmpty()) {
            model.addAttribute("products", productService.searchByName(keyword));
            model.addAttribute("keyword", keyword);
        } else {
            model.addAttribute("products", productService.findAll());
        }
        return "product/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("pageTitle", "Add Product");
        return "product/form";
    }

    @PostMapping("/save")
    public String saveProduct(@ModelAttribute Product product,
                              @RequestParam("imageFile") MultipartFile imageFile,
                              RedirectAttributes redirectAttributes) {
        productService.addProduct(product, imageFile);
        redirectAttributes.addFlashAttribute("message", "Product added successfully!");
        return "redirect:/products/list";
    }

    @GetMapping("/view/{id}")
    public String viewProduct(@PathVariable Long id,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        try {
            model.addAttribute("product", productService.getById(id));
            return "product/view";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/products/list";
        }
    }

    @GetMapping("/edit/{id}")
    public String editProduct(@PathVariable Long id,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        try {
            model.addAttribute("product", productService.getById(id));
            model.addAttribute("pageTitle", "Edit Product");
            return "product/form";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/products/list";
        }
    }

    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable Long id,
                                @ModelAttribute Product product,
                                @RequestParam("imageFile") MultipartFile imageFile,
                                RedirectAttributes redirectAttributes) {
        product.setId(id);
        productService.updateProduct(product, imageFile);
        redirectAttributes.addFlashAttribute("message", "Product updated successfully!");
        return "redirect:/products/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id,
                                RedirectAttributes redirectAttributes) {
        try {
            productService.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "Product deleted successfully!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/products/list";
    }
}
