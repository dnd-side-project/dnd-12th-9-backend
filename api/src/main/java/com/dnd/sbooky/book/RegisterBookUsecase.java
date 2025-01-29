package com.dnd.sbooky.book;

import com.dnd.sbooky.book.request.RegisterBookRequest;
import com.dnd.sbooky.member.MemberEntity;
import com.dnd.sbooky.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RegisterBookUsecase {
    private final MemberBookRepository memberBookRepository;
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;

    public void registerBook(RegisterBookRequest request, Long memberId) {
        MemberEntity memberEntity = memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException()); //todo 커스텀한 예외로 수정
        BookEntity bookEntity = bookRepository.save(
                BookEntity.newInstance(request.author(), request.title(), request.publishedAt()));
        memberBookRepository.save(MemberBookEntity.newInstance(memberEntity, bookEntity, ReadStatus.valueOf(request.readStatus())));
    }
}
