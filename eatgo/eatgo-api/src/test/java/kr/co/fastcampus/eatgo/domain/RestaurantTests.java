package kr.co.fastcampus.eatgo.domain;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTests {
    @Test
    public void creation(){
        Restaurant restaurant = new Restaurant(1004L,"Bob zip","Seoul");

        assertThat(restaurant.getId(), is(1004L));
        assertThat(restaurant.getName(), is("Bob zip")); //RED -> GREEN -> Refactoring
        assertThat(restaurant.getAddress(), is("Seoul"));

    }

    @Test
    public void information(){
        Restaurant restaurant = new Restaurant(1004L, "Bob zip", "Seoul");

        assertThat(restaurant.getInformation(),is( "Bob zip in Seoul"));
    }
}