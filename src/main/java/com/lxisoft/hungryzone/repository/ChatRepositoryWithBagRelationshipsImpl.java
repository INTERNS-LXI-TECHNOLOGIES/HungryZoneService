package com.lxisoft.hungryzone.repository;

import com.lxisoft.hungryzone.domain.Chat;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class ChatRepositoryWithBagRelationshipsImpl implements ChatRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Chat> fetchBagRelationships(Optional<Chat> chat) {
        return chat.map(this::fetchUsers);
    }

    @Override
    public Page<Chat> fetchBagRelationships(Page<Chat> chats) {
        return new PageImpl<>(fetchBagRelationships(chats.getContent()), chats.getPageable(), chats.getTotalElements());
    }

    @Override
    public List<Chat> fetchBagRelationships(List<Chat> chats) {
        return Optional.of(chats).map(this::fetchUsers).orElse(Collections.emptyList());
    }

    Chat fetchUsers(Chat result) {
        return entityManager
            .createQuery("select chat from Chat chat left join fetch chat.users where chat is :chat", Chat.class)
            .setParameter("chat", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Chat> fetchUsers(List<Chat> chats) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, chats.size()).forEach(index -> order.put(chats.get(index).getId(), index));
        List<Chat> result = entityManager
            .createQuery("select distinct chat from Chat chat left join fetch chat.users where chat in :chats", Chat.class)
            .setParameter("chats", chats)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
