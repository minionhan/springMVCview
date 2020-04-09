package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import errors.AuthenticationException;
import model.Auth;
import model.MemberInfo;

@Component
public class Authenticator {
   //bean으로 이미 설정이 되어 있다.
   @Autowired
   private MemberService memberService;
   //Component를 만들기 위해서는 default생성자 만들어야 한다.
   public Authenticator(){
      
   }
   public Authenticator(MemberService memberService){
      this.memberService = memberService;
   }
   //default생성자를 만들어 줘야 함


   public Auth authenticate(String email, String password){
      MemberInfo mi = memberService.getMemberInfoByEmail(email);
      if(mi==null)
         throw new AuthenticationException();
      if(!mi.matchPassword(password))
         throw new AuthenticationException();
      return new Auth(mi.getId(), mi.getName());
   }

}