<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Insert title here</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
    </head>
    <style>
        .text-center, .row, .col {
            border: 1px solid black;
        }
        .title{
            font-weight: bold;
            background-color: rgb(178, 198, 235);
        }
        .content{
            padding: 20px;
        }
        form{
            display: inline;
            float: left;
        }
    </style>
    <body>
        <div class="container text-center">
            <div class="row first-row">
                <div class="col-1 title">
                    글번호             
                </div>
                <div class="col-1">
                    ${blog.blogId}
                </div>
                <div class="col-2 title">
                    글제목
                </div>
                <div class="col-6">
                    ${blog.blogTitle}
                </div>
                <div class="col-1 title">
                    글쓴이
                </div>
                <div class="col-1">
                    ${blog.writer}
                </div>
            </div>
            <div class="row second-row">
                <div class="col-1 title">
                    작성일
                </div>
                <div class="col-4">
                    ${blog.publishedAt}
                </div>
                <div class="col-1 title">
                    수정일
                </div>
                <div class="col-4">
                    ${blog.updatedAt}
                </div>
                <div class="col-1 title">
                    조회수
                </div>
                <div class="col-1">
                    ${blog.blogCount}
                </div>
            </div>
            <div class="row" style="height: 500px;">
                <div class="col content">
                    ${blog.blogContent}
                </div>
            </div>
        </div>
        <br>
        <div class="container">
            <form name="deleteForm" style="margin-right: 5px;">
                <a href = "/blog/list" class = "btn btn-primary">목록</a>
                <input type="button" class = "btn btn-primary" value="삭제" onclick="deleteBlog()">
                <input type="hidden" name = "blogId" value="${blog.blogId}">
            </form> 
            <form name = "updateForm">
                <input type="button" class = "btn btn-primary" value="수정" onclick="updateBlog()">
                <input type="hidden" name = "blogId" value="${blog.blogId}">
            </form>
        </div>
        <script>
            function deleteBlog(){
                let deleteForm = document.deleteForm;
                let check = confirm("해당 게시물을 삭제하시겠습니까?");
                if(check){
                    alert("삭제완료!");
                    deleteForm.method = "POST";
                    deleteForm.action = "/blog/delete";
                    deleteForm.submit();
                }
       
            }
            
            function updateBlog(){
                let updateForm = document.updateForm;
                let check = confirm("해당 게시물을 수정하시겠습니까?");
                if(check){
                    updateForm.method = "POST"
                    updateForm.action = "/blog/update"
                    updateForm.submit();
                }
            }
            </script>
    </body>
</html>