package com.athleticspot.tracker.application.impl;

import com.athleticspot.common.SecurityUtils;
import com.athleticspot.common.domain.event.AthleteDeletedEvent;
import com.athleticspot.common.domain.event.UserCreatedEvent;
import com.athleticspot.common.domain.event.UserUpdatedEvent;
import com.athleticspot.common.infrastracture.dto.AthleteDeletedEventDto;
import com.athleticspot.common.infrastracture.dto.UserUpdatedEventDto;
import com.athleticspot.tracker.application.AthleteApplicationService;
import com.athleticspot.tracker.domain.graph.Athlete;
import com.athleticspot.tracker.domain.graph.GraphAthleteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

/**
 * @author Tomasz Kasprzycki
 */
@Service
@Transactional(transactionManager = "graphTransactionManager")
public class AthleteApplicationServiceImpl implements AthleteApplicationService {

    private static final int DEPTH = 1;
    private final GraphAthleteRepository graphAthleteRepository;

    @Autowired
    public AthleteApplicationServiceImpl(GraphAthleteRepository graphAthleteRepository) {
        this.graphAthleteRepository = graphAthleteRepository;
    }

    @Override
    public void follow(Long athleteIdToFallow) {
        final String currentUserLogin = SecurityUtils.getCurrentUserLogin();
        final Athlete athleteToFallow =
            graphAthleteRepository
                .findById(athleteIdToFallow)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Athlete with give athleteId: %s doesn't exists", athleteIdToFallow)));
        final Optional<Athlete> athleteOptional = graphAthleteRepository.findByName(currentUserLogin);
        final Athlete athlete = athleteOptional.orElseThrow(() -> new IllegalStateException(String.format("Athlete with name: %s doesn't exist", currentUserLogin)));
        athlete.fallow(athleteToFallow);
        graphAthleteRepository.save(athlete);
    }

    @Override
    public void unfollow(Long athleteIdToUnfallow) {
        Athlete athlete = findCurrentAthlete();
        athlete.unfallow(athleteIdToUnfallow);
        graphAthleteRepository.save(athlete);
    }

    public Athlete findCurrentAthlete() {
        final Optional<Athlete> athleteOptional = graphAthleteRepository.findByName(SecurityUtils.getCurrentUserLogin());
        return athleteOptional.orElseThrow(() -> new IllegalStateException(String.format("Athlete with name: %s doesn't exist", SecurityUtils.getCurrentUserLogin())));
    }

    @Override
    public Page<Athlete> findAllFallowedAthletesPaged(String athleteUUID, PageRequest pageRequest) {
        return graphAthleteRepository.findAllFallowedAthletesPaged(athleteUUID, pageRequest);
    }

    @Override
    public List<Athlete> findAllFallowedAthletes(String athleteUuid) {
        return graphAthleteRepository.findAllFallowedAthletes(athleteUuid);
    }

    @Override
    public void createAthlete(String username, String uuid, String firstAndLastName) {
        Athlete athlete = new Athlete(username, uuid, firstAndLastName);
        graphAthleteRepository.save(athlete);
    }

    @Override
    public void updateAthlete(UserUpdatedEventDto userUpdatedEventDto) {
        final Athlete athlete = graphAthleteRepository.findByName(userUpdatedEventDto.getName())
            .orElseGet(() ->
                new Athlete(userUpdatedEventDto.getName(),
                    userUpdatedEventDto.getUuid(),
                    userUpdatedEventDto.getFirstName() + userUpdatedEventDto.getLastName())
            );
        athlete.updateFirstAndLastName(
            userUpdatedEventDto.getFirstName(),
            userUpdatedEventDto.getLastName()
        );
        graphAthleteRepository.save(athlete);
    }

    @Override
    public List<Long> checkIfFollow(List<Long> followedAthleteIdsToCheck, Long followingAthleteId) {
        Assert.notNull(followedAthleteIdsToCheck, "Followed athletes to check cannot be null");
        Assert.notNull(followingAthleteId, "Following athlete id cannot be null");
        final Athlete athlete =
            graphAthleteRepository.findById(followingAthleteId, DEPTH)
                .orElseThrow(() -> new IllegalArgumentException("There is no Athlete with id: " + followingAthleteId));

        return athlete.checkIfFollow(followedAthleteIdsToCheck);

    }

    private void deleteAthlete(AthleteDeletedEventDto content) {
        graphAthleteRepository.deleteAthlete(content.getLogin());
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleAthleteCreatedEvent(UserCreatedEvent userCreatedEvent) {
        createAthlete(
            userCreatedEvent.getContent().getName(),
            userCreatedEvent.getContent().getUuid(),
            userCreatedEvent.getContent().getFirstAndLastName()
        );
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleAthleteUpdatedEvent(UserUpdatedEvent userUpdatedEvent) {
        updateAthlete(userUpdatedEvent.getContent());
    }


    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleAthleteDeletedEvent(AthleteDeletedEvent athleteDeletedEvent) {
        deleteAthlete(athleteDeletedEvent.getContent());
    }

}
