package com.example;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SpringBootApplication
@RestController
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @GetMapping("/blog/{id}")
    public Blog get(
            @PathVariable("id") long id
    ) {
        return new Blog(id);
    }

    @PostMapping("/blog")
    public Blog post(
            @RequestBody Blog blog
    ) {
        blog.setId(RandomUtils.nextLong());
        return blog;
    }

    @PutMapping("/blog/{id}")
    public Blog post(
            @PathVariable("id") long id,
            @RequestBody Blog blog
    ) {
        blog.setId(id);
        return blog;
    }

    @DeleteMapping("/blog/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void post(
            @PathVariable("id") long id
    ) {
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Blog {
        private long id;

        @NotEmpty
        @Length(max = 255)
        private String title;

        @NotEmpty
        @Length(max = 255)
        private String author;

        public Blog(long id) {
            this.id = id;
            title = RandomStringUtils.randomAlphanumeric(5);
            author = RandomStringUtils.randomAlphanumeric(5);
        }
    }
}
