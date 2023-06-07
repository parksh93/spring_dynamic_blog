package com.spring.blog.exception;

public class NotFoundBlogIdException extends RuntimeException{  //Exception(checkedException)이 아닌 unCheckedException으로 만들어야 한다
    //생성자에서 에러 사유를 전달할 수 있도록 메세지를 작성
    public NotFoundBlogIdException(String message){
        super(message);
    }
}
