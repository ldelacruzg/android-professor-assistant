package com.smartclassroom.Models;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter @Setter
@RequiredArgsConstructor
public class Classroom {
    private final int id;
    private final String name;
    private String faculty;
    private int capacity;
    private List<Subject> subjects;
    private List<ClassroomAccess> accessHistory;
}
