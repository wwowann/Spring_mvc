package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class PostRepositoryTraining implements PostRepository {
    ConcurrentHashMap<Long, Post> repositoryMap = new ConcurrentHashMap<>();
    static long counter = 0;

    public List<Post> all() {
        return List.of(new Post(11, "Привет!!"), new Post(12, "Пока!"));
        // new ArrayList<>(repositoryMap.values());
    }

    public Post getById(long id) {
        return repositoryMap.get(id);
    }

    public Post save(Post post){
        if (post.getId() == 0) {
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
            repositoryMap.remove(id, repositoryMap.get(id));
        }
    }
}
