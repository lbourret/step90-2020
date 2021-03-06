
package com.step902020.capstone;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;

public class Recommender {

  /**
   * creates a list of recommended items based on a user-based collaborative filtering algorithm.
   * Calculates the distance between users and add the users' items based on the distance
   * between the users.
   * @param targetUser the user that we are trying to recommend to
   * @param users all the users in the database that go to the same college as the target user
   * @param getItemList function that returns a list of objects based on the user
   * @param numNeeded the number of objects that need to be returned
   * @param <E> expects Individual
   * @param <U> expects Event or Organization
   * @return list of objects that are recommended for the target user
   */
  public <E, U> List<E> recommend (U targetUser, List<U> users, Function<U, Set<E>> getItemList, int numNeeded) {
    Map<U, Integer> userToScore= new HashMap<U, Integer>();
    Set<E> targetUserEvents = new HashSet(getItemList.apply(targetUser));
    // for each user, calculate the total number of differences between the current user and the target user
    // store this result as the distance
    for (U user: users) {
      if (user.equals(targetUser)) {
        continue;
      }
      Set<E> userEvents = new HashSet(getItemList.apply(user));
      int dist = calculateDistance(targetUserEvents, userEvents);

      userToScore.put(user, dist);
    }
    // sort the resulting list of individuals by increasing distance
    // Create a list from elements of HashMap
    List<Map.Entry<U, Integer>> sortedUsers =
            new LinkedList<Map.Entry<U, Integer> >(userToScore.entrySet());

    // Sort the list
    Collections.sort(sortedUsers, (o1, o2) -> (o1.getValue()).compareTo(o2.getValue()));

    // put data from sorted user list to ordered event list
    LocalDateTime now = LocalDateTime.now();
    List<E> sorted = new ArrayList<>();
    int count = 0;
    for (Map.Entry<U, Integer> entry : sortedUsers) {
      Set<E> currUserEvents = getItemList.apply(entry.getKey());
      for (E e : currUserEvents) {
        if (!targetUserEvents.contains(e) && !sorted.contains(e) && count < numNeeded) {
          sorted.add(e);
          count++;
        }
      }
    }
    return sorted;
  }
  public <E> int calculateDistance(Set<E> targetUserEvents, Set<E> userEvents) {
    int dist = 0;
    for (E e : targetUserEvents) {
      if (!userEvents.contains(e)) {
        dist++;
      }
    }
    for (E e : userEvents) {
      if (!targetUserEvents.contains(e)) {
        dist++;
      }
    }
    return dist;
  }
}