package org.example.springsecurity.exam.service;

import lombok.RequiredArgsConstructor;
import org.example.springsecurity.exam.annotation.Retry;
import org.example.springsecurity.exam.annotation.Trace;
import org.example.springsecurity.exam.repository.ExamRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExamService {

    private final ExamRepository examRepository;

    //@Trace가 붙으면 부가기능이 적용되는거임
    @Trace
    public void request(String itemId){
        examRepository.save(itemId);
    }
}
