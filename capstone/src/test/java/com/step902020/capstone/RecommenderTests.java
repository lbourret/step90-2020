package com.step902020.capstone;

import org.junit.Test;

import java.net.URISyntaxException;
import java.util.*;

import static org.junit.Assert.assertEquals;

public class RecommenderTests {

  private Recommender recommender = new Recommender();

  public Set<String> getSetOfItems(String user) {
    Set<String> result = new TreeSet<String>();
    if (user.equals("A")) {
      result.add("event 1");
      result.add("event 3");
    } else if (user.equals("B")) {
      result.add("event 1");
      result.add("event 2");
    } else if (user.equals("C")) {
      result.add("event 2");
      result.add("event 4");
    }
    return result;
  }

  @Test
  public void testRecommendWithEmptyUserListCase() throws URISyntaxException {
    List<String> list = new ArrayList<String>();
    List<String> result = recommender.recommend("A", list, p  -> getSetOfItems(p), 1);
    List<String> expected = new ArrayList<>();
    assertEquals("Wrong list returned", expected, result);
  }

  @Test
  public void testRecommendTargetUserWithNoSavedEventsCase() throws URISyntaxException {
    List<String> list = new ArrayList<String>();
    list.add("B");
    List<String> result = recommender.recommend("D", list, p  -> getSetOfItems(p), 2);
    List<String> expected = new ArrayList<>();
    expected.add("event 1");
    expected.add("event 2");
    assertEquals("Wrong list returned", expected, result);
  }

  @Test
  public void testRecommendTargetUserWithNoSavedEventsCaseWithEventLimit() throws URISyntaxException {
    List<String> list = new ArrayList<String>();
    list.add("B");
    List<String> result = recommender.recommend("D", list, p  -> getSetOfItems(p), 1);
    List<String> expected = new ArrayList<>();
    expected.add("event 1");
    assertEquals("Wrong list returned", expected, result);
  }

  @Test
  public void testRecommendWithTwoPeopleCase() throws URISyntaxException {
    List<String> list = new ArrayList<String>();
    list.add("B");
    List<String> result = recommender.recommend("A", list, p  -> getSetOfItems(p), 1);
    List<String> expected = new ArrayList<>();
    expected.add("event 2");
    assertEquals("Wrong list returned", expected, result);
  }

  @Test
  public void testRecommendWithThreePeopleCase() throws URISyntaxException {
    List<String> list = new ArrayList<String>();
    list.add("B");
    list.add("C");
    List<String> result = recommender.recommend("A", list, p  -> getSetOfItems(p), 2);
    List<String> expected = new ArrayList<>();
    expected.add("event 2");
    expected.add("event 4");
    assertEquals("Wrong list returned", expected, result);
  }

  @Test
  public void testRecommendWithTwoPeopleCaseWithRepeat() throws URISyntaxException {
    List<String> list = new ArrayList<String>();
    list.add("A");
    list.add("B");
    List<String> result = recommender.recommend("A", list, p  -> getSetOfItems(p), 1);
    List<String> expected = new ArrayList<>();
    expected.add("event 2");
    assertEquals("Wrong list returned", expected, result);
  }

  @Test
  public void testRecommendWithFourPeopleCase() throws URISyntaxException {
    List<String> list = new ArrayList<String>();
    list.add("A");
    list.add("B");
    list.add("C");
    List<String> result = recommender.recommend("D", list, p  -> getSetOfItems(p), 10);
    List<String> expected = new ArrayList<>();
    expected.add("event 1");
    expected.add("event 3");
    expected.add("event 2");
    expected.add("event 4");
    assertEquals("Wrong list returned", expected, result);
  }
}