package com.guyson.kronos.util;

import com.guyson.kronos.domain.Lecture;

import java.util.Comparator;
import java.util.List;

//Alphabetically sort lectures
public class SortLectureByModule implements SortLectureStrategy {
    @Override
    public List<Lecture> sort(List<Lecture> lectures) {

        // Compare lectures by module name and sort in ascending order
        lectures.sort(Comparator.comparing(a -> a.getModule().getName()));

        return lectures;
    }
}

