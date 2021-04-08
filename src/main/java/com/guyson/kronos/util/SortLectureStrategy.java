package com.guyson.kronos.util;

import com.guyson.kronos.domain.Lecture;

import java.util.List;

//Strategy design pattern to sort lectures
public interface SortLectureStrategy {

    public List<Lecture> sort(List<Lecture> lectures);

}
