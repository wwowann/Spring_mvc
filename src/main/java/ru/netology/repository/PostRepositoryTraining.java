package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class PostRepositoryTraining implements PostRepository {
    ConcurrentHashMap<Long, Post> repositoryMap = new ConcurrentHashMap<>();
    static long counter = 0;

    public List<Post> all() {
        List<Post> posts = new ArrayList<>();
        for (ConcurrentHashMap.Entry<Long, Post> entry : repositoryMap.entrySet()) {
            if (!entry.getValue().getFlag().equals("remove")) {
                posts.add(entry.getValue());
            }
        }
        return posts;
//           return List.of(new Post(11, "Привет!!"), new Post(12, "Пока!"));

    }

    public Post getById(long id) {
        if (repositoryMap.get(id).getFlag().equals("remove")) {
            throw new NotFoundException();
        }
        return repositoryMap.get(id);
    }

    public Post save(Post post) {
        if (post.getId() == 0 /*//&& !post.getFlag().equals("remove")*/) {//да, здесь ошибка,
            // т.к. если поста нет, то флага не может быть т.е. = null
            counter++;
            repositoryMap.put(counter, new Post(post.getId(), post.getContent()));
        }

        if (post.getId() != 0) {
            if (repositoryMap.containsKey(post.getId())) {
                repositoryMap.put(post.getId(), new Post(post.getId(), post.getContent()));
            } else
                throw new NotFoundException();
        }

        return post;
    }

    public void removeById(long id) {
        if (repositoryMap.containsKey(id)) {
            repositoryMap.get(id).setFlag("remove");
//            repositoryMap.remove(id, repositoryMap.get(id));
        }
    }
}
