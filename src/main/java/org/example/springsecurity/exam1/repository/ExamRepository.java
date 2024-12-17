package org.example.springsecurity.exam1.repository;

import org.example.springsecurity.exam1.annotation.Retry;
import org.example.springsecurity.exam1.annotation.Trace;
import org.springframework.stereotype.Repository;

@Repository
public class ExamRepository {

    private static int seq = 0;

    /**
     * 5번에 1번은 실패되는 로직
     */
    @Trace
    @Retry  //기본 값 사용 안한다면 @Retry(value = 4) 이렇게 주면 된다
    public String save(String itemId) {
        seq++;
        if (seq % 5 == 0) {
            throw new IllegalStateException("예외 발생");
        }
        return "ok";

    }
}
