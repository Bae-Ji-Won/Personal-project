package com.example.singleproject.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

@Service
public class PagenationService {
    private static final int BAR_LENGTH = 5;

    // currentPageNumber = 현재 페이지 번호
    public List<Integer> getPagenationBarNumbers(int currentPageNumber, int totalPages){
        int startNum = Math.max(currentPageNumber - (BAR_LENGTH/2),0);  // 페이지 번호가 0보다 작아지는것을 방지
        int endNum = Math.min(startNum + BAR_LENGTH,totalPages);        // 페이지가 끝번호보다 커지는 것을 방지

        return IntStream.range(startNum,endNum).boxed().toList();   // 시작번호와 마지막 번호를 List형식으로 반환
    }

    public int currentBarLength(){
        return BAR_LENGTH;
    }
}
