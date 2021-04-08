package com.guyson.kronos.util;

import com.guyson.kronos.domain.Lecture;

import java.util.List;

//Strategy design pattern to sort lectures in given method
public class SortLectureModule {

    private SortLectureStrategy sortLectureStrategy;

    //Set the sorting strategy
    public void setSortLectureStrategy(SortLectureStrategy sortLectureStrategy) {
        this.sortLectureStrategy = sortLectureStrategy;
    }

    //Sort lectures using selected strategy
    public List<Lecture> sortLectures(List<Lecture> lectures) {
        return sortLectureStrategy.sort(lectures);
    }

}
