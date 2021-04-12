package me.hoonti06.springbeginner;

import me.hoonti06.springbeginner.repository.MemberRepository;
import me.hoonti06.springbeginner.repository.MemoryMemberRepository;
import me.hoonti06.springbeginner.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

}
