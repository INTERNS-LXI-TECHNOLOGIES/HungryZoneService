package com.lxisoft.hungryzone.service.impl;

import com.lxisoft.hungryzone.domain.Chat;
import com.lxisoft.hungryzone.repository.ChatRepository;
import com.lxisoft.hungryzone.service.ChatService;
import com.lxisoft.hungryzone.service.dto.ChatDTO;
import com.lxisoft.hungryzone.service.mapper.ChatMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Chat}.
 */
@Service
@Transactional
public class ChatServiceImpl implements ChatService {

    private final Logger log = LoggerFactory.getLogger(ChatServiceImpl.class);

    private final ChatRepository chatRepository;

    private final ChatMapper chatMapper;

    public ChatServiceImpl(ChatRepository chatRepository, ChatMapper chatMapper) {
        this.chatRepository = chatRepository;
        this.chatMapper = chatMapper;
    }

    @Override
    public ChatDTO save(ChatDTO chatDTO) {
        log.debug("Request to save Chat : {}", chatDTO);
        Chat chat = chatMapper.toEntity(chatDTO);
        chat = chatRepository.save(chat);
        return chatMapper.toDto(chat);
    }

    @Override
    public ChatDTO update(ChatDTO chatDTO) {
        log.debug("Request to save Chat : {}", chatDTO);
        Chat chat = chatMapper.toEntity(chatDTO);
        chat = chatRepository.save(chat);
        return chatMapper.toDto(chat);
    }

    @Override
    public Optional<ChatDTO> partialUpdate(ChatDTO chatDTO) {
        log.debug("Request to partially update Chat : {}", chatDTO);

        return chatRepository
            .findById(chatDTO.getId())
            .map(existingChat -> {
                chatMapper.partialUpdate(existingChat, chatDTO);

                return existingChat;
            })
            .map(chatRepository::save)
            .map(chatMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChatDTO> findAll() {
        log.debug("Request to get all Chats");
        return chatRepository.findAll().stream().map(chatMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    public Page<ChatDTO> findAllWithEagerRelationships(Pageable pageable) {
        return chatRepository.findAllWithEagerRelationships(pageable).map(chatMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ChatDTO> findOne(Long id) {
        log.debug("Request to get Chat : {}", id);
        return chatRepository.findOneWithEagerRelationships(id).map(chatMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Chat : {}", id);
        chatRepository.deleteById(id);
    }
}
