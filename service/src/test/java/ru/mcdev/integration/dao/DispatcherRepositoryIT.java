package ru.mcdev.integration.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.mcdev.dao.queryDslDao.DispatcherRepository;
import ru.mcdev.entity.Dispatcher;
import ru.mcdev.integration.testUtils.EntityUtil;

import static org.assertj.core.api.Assertions.assertThat;

class DispatcherRepositoryIT extends BaseIntegrationTest {

    private DispatcherRepository dispatcherRepository;

    @BeforeEach
    void init() {
        dispatcherRepository = new DispatcherRepository(session);
    }

    @Test
    void findById() {

        var actualResult = dispatcherRepository.findById(7L);

        assertThat(actualResult).isPresent();
        assertThat(actualResult.get().getEmail()).isEqualTo("email7@mail.com");
    }

    @Test
    void save() {

        var dispatcher = EntityUtil.createDispatcher();
        dispatcherRepository.save(dispatcher);
        dispatcherRepository.getSession().flush();
        dispatcherRepository.getSession().clear();

        var actualResult = dispatcherRepository.findById(dispatcher.getId());

        assertThat(actualResult).isPresent();
        assertThat(actualResult.get()).isEqualTo(dispatcher);
    }

    @Test
    void update() {

        var dispatcher = dispatcherRepository.findById(7L).get();
        dispatcher.setEmail("new");
        dispatcherRepository.update(dispatcher);
        dispatcherRepository.getSession().flush();
        dispatcherRepository.getSession().clear();

        var actualResult = dispatcherRepository.findById(7L);

        assertThat(actualResult).isPresent();
        assertThat(actualResult.get().getEmail()).isEqualTo("new");
    }

    @Test
    void delete() {

        var dispatcher = dispatcherRepository.findById(7L);
        dispatcherRepository.delete(dispatcher.get().getId());

        var actualResult = dispatcherRepository.findById(7L);

        assertThat(actualResult).isEmpty();
    }

    @Test
    void findAll() {

        var actualResult = dispatcherRepository.findAll();

        assertThat(actualResult.stream().map(Dispatcher::getEmail).toList())
                .containsExactlyInAnyOrder("email6@mail.com", "email7@mail.com");
    }

}