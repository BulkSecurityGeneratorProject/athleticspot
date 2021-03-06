package com.athleticspot.tracker.domain.model;

import com.athleticspot.tracker.domain.graph.Athlete;
import com.google.common.collect.Lists;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.UUID;

/**
 * @author Tomasz Kasprzycki
 */
public class AthleteTest {


    @Test
    public void whenUnfallowThenRelationshipIsGone(){
        //given
        Athlete tomek = new Athlete("tomkasp", UUID.randomUUID().toString(), "Tom Kasp");
        Athlete olek = new Athlete("olek", UUID.randomUUID().toString(), "Olo Kasp");
        ReflectionTestUtils.setField(tomek, "id", 1L);
        ReflectionTestUtils.setField(olek, "id", 2L);

        tomek.fallow(olek);
        Assertions.assertThat(tomek.getFriends()).contains(olek);

        //when
        tomek.unfallow(olek.getId());


        //then
        Assertions.assertThat(tomek.getFriends()).hasSize(0);
    }

    @Test
    public void checkIfFollow() {
        Athlete tomek = new Athlete("tomkasp", UUID.randomUUID().toString(), "Tom Kasp");
        Athlete olek = new Athlete("olek", UUID.randomUUID().toString(), "Olo Kasp");
        ReflectionTestUtils.setField(tomek, "id", 1L);
        ReflectionTestUtils.setField(olek, "id", 2L);

        tomek.fallow(olek);

        Assertions.assertThat(tomek.checkIfFollow(Lists.newArrayList(2L, 5L)))
            .hasSize(1)
            .containsExactly(2L);
    }


    @Test
    public void checkIfFollowUser() {
        Athlete tomek = new Athlete("tomkasp", UUID.randomUUID().toString(), "Tom Kasp");
        Athlete olek = new Athlete("olek", UUID.randomUUID().toString(), "Olo Kasp");
        ReflectionTestUtils.setField(tomek, "id", 1L);
        ReflectionTestUtils.setField(olek, "id", 2L);

        tomek.fallow(olek);

        Assertions.assertThat(tomek.checkIfFollowUser(2L))
            .isTrue();
    }
}
