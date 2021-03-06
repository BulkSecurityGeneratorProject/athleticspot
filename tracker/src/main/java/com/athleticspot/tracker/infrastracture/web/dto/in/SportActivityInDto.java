package com.athleticspot.tracker.infrastracture.web.dto.in;

import com.athleticspot.tracker.domain.model.SportActivityType;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Tomasz Kasprzycki
 */

@JsonDeserialize
@JsonAutoDetect(
    fieldVisibility = JsonAutoDetect.Visibility.ANY,
    getterVisibility = JsonAutoDetect.Visibility.NONE,
    setterVisibility = JsonAutoDetect.Visibility.NONE)
public class SportActivityInDto implements Serializable {

    private String id;

    @NotNull
    private String trackerSource;

    @NotNull
    private SportActivityType sportActivityType;

    @NotNull
    private String title;

    private String description;

    @NotNull
    private Integer duration;

    private Float distance;

    private String units;

    private Float maxSpeed;

    private Float averageSpeed;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime startDate;

    public String getId() {
        return id;
    }

    public String getTrackerSource() {
        return trackerSource;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public SportActivityType getSportActivityType() {
        return sportActivityType;
    }

    public Integer getDuration() {
        return duration;
    }

    public Float getDistance() {
        return distance;
    }

    public String getUnits() {
        return units;
    }

    public Float getMaxSpeed() {
        return maxSpeed;
    }

    public Float getAverageSpeed() {
        return averageSpeed;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    @Override
    public String toString() {
        return "SportActivityInDto{" +
            "id='" + id + '\'' +
            ", trackerSource='" + trackerSource + '\'' +
            ", description='" + description + '\'' +
            ", title='" + title + '\'' +
            ", sportActivityType='" + sportActivityType + '\'' +
            ", duration='" + duration + '\'' +
            ", distance='" + distance + '\'' +
            ", units='" + units + '\'' +
            ", maxSpeed='" + maxSpeed + '\'' +
            ", averageSpeed='" + averageSpeed + '\'' +
            ", startDate=" + startDate +
            '}';
    }
}
