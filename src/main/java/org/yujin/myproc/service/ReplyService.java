package org.yujin.myproc.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.yujin.myproc.repository.ReplyRepository;

@Service
@RequiredArgsConstructor
@Log4j2
public class ReplyService {

    private final ReplyRepository replyRepository;
}
