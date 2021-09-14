package ru.netology.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.netology.model.Post;
import ru.netology.service.PostService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;

// прием запросов и подготовка ответов
@Controller
@RequestMapping("/api/posts")
public class PostController {
    public static final String APPLICATION_JSON = "application/json";
    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }
@GetMapping
    public void all(HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);// записывается в JSON формате ответ
        final var gson = new Gson();//создание Jsona
        final var data = service.all();//получение всех записей репозитория через слой Service
        response.getWriter().print(gson.toJson(data));// отправка обратно ответа в виде Jsona
    }
@GetMapping("/{id}")
    public void getById(long id, HttpServletResponse response) throws Exception {
        response.setContentType(APPLICATION_JSON);
        final var gson = new Gson();
        final var data = service.getById(id);
        response.getWriter().print(gson.toJson(data));
    }
@PostMapping
    public void save(Reader body, HttpServletResponse response) throws Exception {
        response.setContentType(APPLICATION_JSON);
        final var gson = new Gson();
        final var post = gson.fromJson(body, Post.class);
        final var data = service.save(post);
        response.getWriter().print(gson.toJson(data));
    }
@DeleteMapping
    public void removeById(long id, HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);
        final var gson = new Gson();
        final var post = gson.fromJson("ресурс удален", Post.class);
        service.removeById(id);
        response.getWriter().print(gson.toJson(post));
    }
}