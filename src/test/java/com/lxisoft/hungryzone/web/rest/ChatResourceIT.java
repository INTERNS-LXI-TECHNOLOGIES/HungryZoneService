package com.lxisoft.hungryzone.web.rest;

import static com.lxisoft.hungryzone.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.lxisoft.hungryzone.IntegrationTest;
import com.lxisoft.hungryzone.domain.Chat;
import com.lxisoft.hungryzone.repository.ChatRepository;
import com.lxisoft.hungryzone.service.ChatService;
import com.lxisoft.hungryzone.service.dto.ChatDTO;
import com.lxisoft.hungryzone.service.mapper.ChatMapper;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ChatResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ChatResourceIT {

    private static final ZonedDateTime DEFAULT_CHAT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CHAT_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_TEXT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_TEXT_MESSAGE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/chats";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ChatRepository chatRepository;

    @Mock
    private ChatRepository chatRepositoryMock;

    @Autowired
    private ChatMapper chatMapper;

    @Mock
    private ChatService chatServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restChatMockMvc;

    private Chat chat;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Chat createEntity(EntityManager em) {
        Chat chat = new Chat().chatDate(DEFAULT_CHAT_DATE).textMessage(DEFAULT_TEXT_MESSAGE);
        return chat;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Chat createUpdatedEntity(EntityManager em) {
        Chat chat = new Chat().chatDate(UPDATED_CHAT_DATE).textMessage(UPDATED_TEXT_MESSAGE);
        return chat;
    }

    @BeforeEach
    public void initTest() {
        chat = createEntity(em);
    }

    @Test
    @Transactional
    void createChat() throws Exception {
        int databaseSizeBeforeCreate = chatRepository.findAll().size();
        // Create the Chat
        ChatDTO chatDTO = chatMapper.toDto(chat);
        restChatMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(chatDTO)))
            .andExpect(status().isCreated());

        // Validate the Chat in the database
        List<Chat> chatList = chatRepository.findAll();
        assertThat(chatList).hasSize(databaseSizeBeforeCreate + 1);
        Chat testChat = chatList.get(chatList.size() - 1);
        assertThat(testChat.getChatDate()).isEqualTo(DEFAULT_CHAT_DATE);
        assertThat(testChat.getTextMessage()).isEqualTo(DEFAULT_TEXT_MESSAGE);
    }

    @Test
    @Transactional
    void createChatWithExistingId() throws Exception {
        // Create the Chat with an existing ID
        chat.setId(1L);
        ChatDTO chatDTO = chatMapper.toDto(chat);

        int databaseSizeBeforeCreate = chatRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restChatMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(chatDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Chat in the database
        List<Chat> chatList = chatRepository.findAll();
        assertThat(chatList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkChatDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = chatRepository.findAll().size();
        // set the field null
        chat.setChatDate(null);

        // Create the Chat, which fails.
        ChatDTO chatDTO = chatMapper.toDto(chat);

        restChatMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(chatDTO)))
            .andExpect(status().isBadRequest());

        List<Chat> chatList = chatRepository.findAll();
        assertThat(chatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTextMessageIsRequired() throws Exception {
        int databaseSizeBeforeTest = chatRepository.findAll().size();
        // set the field null
        chat.setTextMessage(null);

        // Create the Chat, which fails.
        ChatDTO chatDTO = chatMapper.toDto(chat);

        restChatMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(chatDTO)))
            .andExpect(status().isBadRequest());

        List<Chat> chatList = chatRepository.findAll();
        assertThat(chatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllChats() throws Exception {
        // Initialize the database
        chatRepository.saveAndFlush(chat);

        // Get all the chatList
        restChatMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chat.getId().intValue())))
            .andExpect(jsonPath("$.[*].chatDate").value(hasItem(sameInstant(DEFAULT_CHAT_DATE))))
            .andExpect(jsonPath("$.[*].textMessage").value(hasItem(DEFAULT_TEXT_MESSAGE)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllChatsWithEagerRelationshipsIsEnabled() throws Exception {
        when(chatServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restChatMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(chatServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllChatsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(chatServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restChatMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(chatRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getChat() throws Exception {
        // Initialize the database
        chatRepository.saveAndFlush(chat);

        // Get the chat
        restChatMockMvc
            .perform(get(ENTITY_API_URL_ID, chat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(chat.getId().intValue()))
            .andExpect(jsonPath("$.chatDate").value(sameInstant(DEFAULT_CHAT_DATE)))
            .andExpect(jsonPath("$.textMessage").value(DEFAULT_TEXT_MESSAGE));
    }

    @Test
    @Transactional
    void getNonExistingChat() throws Exception {
        // Get the chat
        restChatMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewChat() throws Exception {
        // Initialize the database
        chatRepository.saveAndFlush(chat);

        int databaseSizeBeforeUpdate = chatRepository.findAll().size();

        // Update the chat
        Chat updatedChat = chatRepository.findById(chat.getId()).get();
        // Disconnect from session so that the updates on updatedChat are not directly saved in db
        em.detach(updatedChat);
        updatedChat.chatDate(UPDATED_CHAT_DATE).textMessage(UPDATED_TEXT_MESSAGE);
        ChatDTO chatDTO = chatMapper.toDto(updatedChat);

        restChatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, chatDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(chatDTO))
            )
            .andExpect(status().isOk());

        // Validate the Chat in the database
        List<Chat> chatList = chatRepository.findAll();
        assertThat(chatList).hasSize(databaseSizeBeforeUpdate);
        Chat testChat = chatList.get(chatList.size() - 1);
        assertThat(testChat.getChatDate()).isEqualTo(UPDATED_CHAT_DATE);
        assertThat(testChat.getTextMessage()).isEqualTo(UPDATED_TEXT_MESSAGE);
    }

    @Test
    @Transactional
    void putNonExistingChat() throws Exception {
        int databaseSizeBeforeUpdate = chatRepository.findAll().size();
        chat.setId(count.incrementAndGet());

        // Create the Chat
        ChatDTO chatDTO = chatMapper.toDto(chat);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, chatDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(chatDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Chat in the database
        List<Chat> chatList = chatRepository.findAll();
        assertThat(chatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchChat() throws Exception {
        int databaseSizeBeforeUpdate = chatRepository.findAll().size();
        chat.setId(count.incrementAndGet());

        // Create the Chat
        ChatDTO chatDTO = chatMapper.toDto(chat);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(chatDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Chat in the database
        List<Chat> chatList = chatRepository.findAll();
        assertThat(chatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamChat() throws Exception {
        int databaseSizeBeforeUpdate = chatRepository.findAll().size();
        chat.setId(count.incrementAndGet());

        // Create the Chat
        ChatDTO chatDTO = chatMapper.toDto(chat);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChatMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(chatDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Chat in the database
        List<Chat> chatList = chatRepository.findAll();
        assertThat(chatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateChatWithPatch() throws Exception {
        // Initialize the database
        chatRepository.saveAndFlush(chat);

        int databaseSizeBeforeUpdate = chatRepository.findAll().size();

        // Update the chat using partial update
        Chat partialUpdatedChat = new Chat();
        partialUpdatedChat.setId(chat.getId());

        partialUpdatedChat.chatDate(UPDATED_CHAT_DATE);

        restChatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedChat.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedChat))
            )
            .andExpect(status().isOk());

        // Validate the Chat in the database
        List<Chat> chatList = chatRepository.findAll();
        assertThat(chatList).hasSize(databaseSizeBeforeUpdate);
        Chat testChat = chatList.get(chatList.size() - 1);
        assertThat(testChat.getChatDate()).isEqualTo(UPDATED_CHAT_DATE);
        assertThat(testChat.getTextMessage()).isEqualTo(DEFAULT_TEXT_MESSAGE);
    }

    @Test
    @Transactional
    void fullUpdateChatWithPatch() throws Exception {
        // Initialize the database
        chatRepository.saveAndFlush(chat);

        int databaseSizeBeforeUpdate = chatRepository.findAll().size();

        // Update the chat using partial update
        Chat partialUpdatedChat = new Chat();
        partialUpdatedChat.setId(chat.getId());

        partialUpdatedChat.chatDate(UPDATED_CHAT_DATE).textMessage(UPDATED_TEXT_MESSAGE);

        restChatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedChat.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedChat))
            )
            .andExpect(status().isOk());

        // Validate the Chat in the database
        List<Chat> chatList = chatRepository.findAll();
        assertThat(chatList).hasSize(databaseSizeBeforeUpdate);
        Chat testChat = chatList.get(chatList.size() - 1);
        assertThat(testChat.getChatDate()).isEqualTo(UPDATED_CHAT_DATE);
        assertThat(testChat.getTextMessage()).isEqualTo(UPDATED_TEXT_MESSAGE);
    }

    @Test
    @Transactional
    void patchNonExistingChat() throws Exception {
        int databaseSizeBeforeUpdate = chatRepository.findAll().size();
        chat.setId(count.incrementAndGet());

        // Create the Chat
        ChatDTO chatDTO = chatMapper.toDto(chat);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, chatDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(chatDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Chat in the database
        List<Chat> chatList = chatRepository.findAll();
        assertThat(chatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchChat() throws Exception {
        int databaseSizeBeforeUpdate = chatRepository.findAll().size();
        chat.setId(count.incrementAndGet());

        // Create the Chat
        ChatDTO chatDTO = chatMapper.toDto(chat);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(chatDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Chat in the database
        List<Chat> chatList = chatRepository.findAll();
        assertThat(chatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamChat() throws Exception {
        int databaseSizeBeforeUpdate = chatRepository.findAll().size();
        chat.setId(count.incrementAndGet());

        // Create the Chat
        ChatDTO chatDTO = chatMapper.toDto(chat);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChatMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(chatDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Chat in the database
        List<Chat> chatList = chatRepository.findAll();
        assertThat(chatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteChat() throws Exception {
        // Initialize the database
        chatRepository.saveAndFlush(chat);

        int databaseSizeBeforeDelete = chatRepository.findAll().size();

        // Delete the chat
        restChatMockMvc
            .perform(delete(ENTITY_API_URL_ID, chat.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Chat> chatList = chatRepository.findAll();
        assertThat(chatList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
