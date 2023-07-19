package com.spring.blog.controller;

import com.spring.blog.entity.Blog;
import com.spring.blog.exception.NotFoundBlogIdException;
import com.spring.blog.service.BlogService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.List;
import java.util.Properties;

@Controller //빈 컨테이너 등록(Component)과 url을 분석할 수 있는 로직(RequestMapping)이 추가되어 있다
@RequestMapping("/blog")
@Log4j2 // sout이 아닌 로깅을 통한 디버깅을 위해 선언
public class BlogController {
    private BlogService blogService;
    static final int PAGE_BTN_NUM = 10; // 한페이지에 보여야 하는 페이징 버튼 그룹의 개수

    @Autowired
    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @RequestMapping(value = {"/list/{pageNumber}", "/list"})
    public String findAll(Model model, @PathVariable(required = false) Long pageNumber){
        // List<Blog> blogList = blogService.findAll().toList();
        Page<Blog> pageInfo = blogService.findAll(pageNumber);

        // 현재 조회중인 페이지 번호(0부터 시작)
        int currentPageNum = pageInfo.getNumber() + 1;
        // 현재 조회중인 페이지 그룹의 끝 번호
        long endPageNum = (long)Math.ceil(currentPageNum / (double)PAGE_BTN_NUM) * PAGE_BTN_NUM;

        // 마지막 그룹번호 보정
        endPageNum = endPageNum > pageInfo.getTotalPages() ? pageInfo.getTotalPages() : endPageNum;

        // 현재 조회중인 페이지 그룹의 시작번호
        long startPageNum = endPageNum - PAGE_BTN_NUM + 1;

        if(startPageNum <= 0){
            startPageNum = 1;
        }
//        // 이전 페이지 버튼
//        boolean prevBtn = startPageNum != 1;s

        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("currentPageNum", currentPageNum);
        model.addAttribute("endPageNum", endPageNum);
        model.addAttribute("startPageNum", startPageNum);
        return "blog/list";
    }

    @RequestMapping("/detail/{blogId}")
    public String detail(@PathVariable long blogId, Model model, Principal principal){
        Blog blog = blogService.findById(blogId);
        if (blog == null){
            try{
                throw new NotFoundBlogIdException("없는 blogId 조회 조회번호 : " + blogId);
            }catch (NotFoundBlogIdException e){
//                System.out.println(e.getMessage());
                e.printStackTrace();
                return "blog/NotFoundBlogIdExceptionResultPage";
            }
        }else{
            model.addAttribute("blog", blog);
            model.addAttribute("username", principal.getName());
        }
        return "blog/detail";
    }

    // 홈페이지와 실제 등록 url은 같은 url을 쓰도록 한다
    //대신 폼 페이지는 GET방식으로 접속했을때 연결
    // 폼에서 작성완료된 내용을 POST방시그로 제출해 저장하도록 만든다
    @RequestMapping(value = "/insert", method = RequestMethod.GET)
    public String insert(Model model, Principal principal){
      //SecurityContext, Principal는 로그인 정보룰 담고 있다.
       //Principal는 jsp파일에서 사용자 정보를 사용할 수 있다.
//        System.out.println(securityContext);
//        System.out.println(principal);
        model.addAttribute("username", principal.getName()); // 현재 로그인한 유저의 아이디를 리턴
        return "blog/blog-form";
    }

    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    public String insert(Blog blog){

        blogService.save(blog);
        return "redirect:/blog/list";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String delete(long blogId){
        log.info(blogId);   // sout은 로그파일에 안찍히지만 log.info는 찍힌다.
                            // log4j에 보안적인 취약점이 있었다
        blogService.deleteById(blogId);
        return "redirect:/blog/list";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(Model model, int blogId){
        Blog blog = blogService.findById(blogId);
        model.addAttribute("blog",blog);
        return "blog/update-form";
    }
    @RequestMapping(value = "/updateOk", method = RequestMethod.POST)
    public String update(Blog blog){
        blogService.update(blog);
        return "redirect:/blog/detail/" + blog.getBlogId();
    }
}
