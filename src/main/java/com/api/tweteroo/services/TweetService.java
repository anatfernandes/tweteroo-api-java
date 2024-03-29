package com.api.tweteroo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.api.tweteroo.dto.TweetDTO;
import com.api.tweteroo.models.Tweet;
import com.api.tweteroo.models.User;
import com.api.tweteroo.repositories.TweetRepository;

@Service
public class TweetService {
    @Autowired
    private TweetRepository repository;

    @Autowired
    private UserService userService;

    public void create(TweetDTO user) throws NotFoundException {
        List<User> users = userService.findByUsernameEquals(user.username());

        if (users.isEmpty()) {
            throw new NotFoundException();
        }

        Tweet newTweet = new Tweet(user, users.get(0).getUsername());
        repository.save(newTweet);
    }

    public List<Tweet> findByUsernameEquals(String username) {
        return repository.findByUsernameEquals(username);
    }

    public Page<Tweet> findAll(int page) {
        PageRequest pageRequest = PageRequest.of(page, 5, Sort.Direction.DESC, "id");
        return repository.findAll(pageRequest);
    }

    public Page<Tweet> findAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.DESC, "id");
        return repository.findAll(pageRequest);
    }
}
