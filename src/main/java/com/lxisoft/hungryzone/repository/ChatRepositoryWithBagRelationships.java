package com.lxisoft.hungryzone.repository;

import com.lxisoft.hungryzone.domain.Chat;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface ChatRepositoryWithBagRelationships {
    Optional<Chat> fetchBagRelationships(Optional<Chat> chat);

    List<Chat> fetchBagRelationships(List<Chat> chats);

    Page<Chat> fetchBagRelationships(Page<Chat> chats);
}
