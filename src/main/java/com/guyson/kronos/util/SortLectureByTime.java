package com.guyson.kronos.util;

import com.guyson.kronos.domain.Lecture;

import java.util.Comparator;
import java.util.List;

//Sort given list of lectures by ascending order of time of day
public class SortLectureByTime implements SortLectureStrategy {
    @Override
    public List<Lecture> sort(List<Lecture> lectures) {

        lectures.sort(Comparator.comparing(Lecture::getStartTime));

        return lectures;

    }
}
