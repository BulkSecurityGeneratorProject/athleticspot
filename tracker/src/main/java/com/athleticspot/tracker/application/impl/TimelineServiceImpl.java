package com.athleticspot.tracker.application.impl;

import com.athleticspot.tracker.application.ApplicationEvents;
import com.athleticspot.tracker.application.TimelineService;
import com.athleticspot.tracker.domain.model.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author Tomasz Kasprzycki
 */
@Service
@Transactional
public class TimelineServiceImpl implements TimelineService {

    private final TimelineRepository timelineRepository;
    private final UserRepository userRepository;
    private final SportActivityRepository sportActivityRepository;
    private final ApplicationEvents applicationEvents;

    public TimelineServiceImpl(TimelineRepository timelineRepository,
                               UserRepository userRepository,
                               SportActivityRepository sportActivityRepository,
                               ApplicationEvents applicationEvents) {
        this.timelineRepository = timelineRepository;
        this.userRepository = userRepository;
        this.sportActivityRepository = sportActivityRepository;
        this.applicationEvents = applicationEvents;
    }

    @Override
    public String createTimeline() {
        final Timeline timeline = Timeline.create(timelineRepository.nextTimelineId());
        timelineRepository.store(timeline);
        applicationEvents.timelineWasCreated(timeline);
        return timeline.timelineIdentifier();
    }

    @Override
    public void createTimelineWithActivities() {

    }

    @Override
    public void addActivity(SportActivityDetails sportActivityDetails, String activitySource) {
        final String timelineIdentifier = userRepository.getTimelineIdentifier();
        final String sportActivityIdentifier = sportActivityRepository.nextSportActivityId();
        SportActivity sportActivity =
            SportActivity.create(
                sportActivityIdentifier,
                activitySource,
                sportActivityDetails

            );
        //TODO: if timeline identifier is null then we need to assign it back to user
        Optional<Timeline> timelineOptional = timelineRepository.find(timelineIdentifier);

        Timeline timeline;
        if (!timelineOptional.isPresent()) {
            final String availableTimelineId = timelineRepository.nextTimelineId();
            timeline = Timeline.create(availableTimelineId);
            sportActivity.assignToTimeline(timeline.timelineIdentifier());
            userRepository.addTimelineIdentifier("user id", availableTimelineId);
        } else {
            timeline = timelineOptional.get();
            sportActivity.assignToTimeline(timelineIdentifier);
        }
        timelineRepository.store(timeline);
        //TODO: do we need below line? We can just simply save sport activity.
        timeline.addTimelineEvent(sportActivity);
        sportActivityRepository.store(sportActivity);
        applicationEvents.sportActivityAdded(sportActivity);
    }

    @Override
    public void addActivities() {
        //TODO: future state
    }

    @Override
    public void removeActivity(SportActivity sportActivity) {
        final String timelineIdentifier = userRepository.getTimelineIdentifier();
        Optional<Timeline> timeline = timelineRepository.find(timelineIdentifier);
        if (!timeline.isPresent()) {
            throw new IllegalStateException("Cannot remove sport activity when timeline doesn't exists");
        }
        timeline.get().removeTimelineEvent(sportActivity.identifier());
        sportActivityRepository.delete(sportActivity.identifier());
        timelineRepository.store(timeline.get());
    }

    @Override
    public void removeActivities() {
        //TODO: future state
    }
}
