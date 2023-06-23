<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html>

        <head>
            <meta charset="UTF-8">
            <title>Insert title here</title>
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet"
                integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65"
                crossorigin="anonymous">
        </head>
        <style>
            .text-center,
            .row,
            .col {
                border: 1px solid black;
            }

            .title {
                font-weight: bold;
                background-color: rgb(178, 198, 235);
            }

            .content {
                padding: 20px;
            }

            .button {
                display: inline;
                float: left;
            }

            .writer {
                float: left;
            }

            .date {
                float: right;
                font-size: 12px;
                margin-left: 10px;
            }

            .replyTextarea {
                width: 87%;
                height: 100px;
                resize: none;
                vertical-align: middle;
                float: left;
            }

            .replyInput {
                width: 10%;
                height: 100px;
                vertical-align: middle;
                float: right;
            }

            .reButton {
                float: right;
                color: gray;
                text-decoration: none;
                font-size: 12px;
            }

            .replyContent {
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
                <form name="deleteForm" style="margin-right: 5px;" class="button">
                    <a href="/blog/list" class="btn btn-primary">목록</a>
                    <input type="button" class="btn btn-primary" value="삭제" onclick="deleteBlog()">
                    <input type="hidden" name="blogId" value="${blog.blogId}">
                </form>
                <form name="updateForm">
                    <input type="button" class="btn btn-primary" value="수정" onclick="updateBlog()">
                    <input type="hidden" name="blogId" value="${blog.blogId}">
                </form>
                <div style="height: 100px; margin-top: 10px; ">
                    <input type="text" name="replyWriter" id="replyWriter">
                    <textarea placeholder="댓글을 작성해주세요" name="replyContent" id="replyContent"
                        class="replyTextarea"></textarea>
                    <input type="button" value="작성" id="replySubmit" class="btn btn-primary replyInput">
                </div>
<<<<<<< HEAD
                <div class="row" style="border: none; width: 100%;">
=======
                <div class="row" style="border: none;">
>>>>>>> b5d6438566d48edc3c9eff330c6c15ae29cbbb55
                    <div id="replies"></div>
                </div>
            </div>
            <script>
                function deleteBlog() {
                    let deleteForm = document.deleteForm;
                    let check = confirm("해당 게시물을 삭제하시겠습니까?");
                    if (check) {
                        alert("삭제완료!");
                        deleteForm.method = "POST";
                        deleteForm.action = "/blog/delete";
                        deleteForm.submit();
                    }

                }

                function updateBlog() {
                    let updateForm = document.updateForm;
                    let check = confirm("해당 게시물을 수정하시겠습니까?");
                    if (check) {
                        updateForm.method = "POST"
                        updateForm.action = "/blog/update"
                        updateForm.submit();
                    }
                }

                let blogId = "${blog.blogId}";
                let $replies = document.getElementById('replies');
                function getAllReplies(blogId) {
                    fetch(`/reply/${blogId}/all`, { method: 'get' })
                        .then(res => res.json())
                        .then(data => {
                            $replies.innerHTML = "";
                            // for (let i of data) {
                            //     let date = new Date(`\${i.publishedAt}`);
                            //     // jsp에서 리터럴을 사용하기 위해서는 역슬래시를 붙여줘야한다
                            //     // js를 분리하면 안 붙여줘도 된다
                            //     $replies.innerHTML += `<hr><p><b class = 'writer'>\${i.replyWriter}</b>
                            //         <span class='date'>\${date.getFullYear()}년 \${date.getMonth()}월 \${date.getDate()}일</span>
                            //         <br>\${i.replyContent} </p>`;
                            // }

                            data.map((reply, i) => { // 첫 파라미터 : 반복대상자료, 두번째 파라미터 순번
                                let date = new Date(`\${reply.publishedAt}`);
                                $replies.innerHTML += `<hr>
                                    <p class = 'reply'>
                                        <b class = 'writer'>\${reply.replyWriter}</b>
                                        <span class='date'>\${date.getFullYear()}년 \${date.getMonth()}월 \${date.getDate()}일</span>
                                        <br><span class ='replyContent'>\${reply.replyContent}</span>
                                        <a class = 'reButton' style='margin-left:5px'>수정</a>
                                        <a class = 'reButton deleteReplyBtn' data-replyId='\${reply.replyId}'>삭제</a>
                                    </p><br>`;

                            });
                        });
                }
                getAllReplies(blogId);

                function insertReply() {
                    let url = `http://localhost:8080/reply`;
                    //내용이 없거나 띄어쓰기만 넣어놓고 작성하는것을 막는다.
                    //금칙어는 cont ban =["금칙어내용"] 변수에 담아놓고 includes(ban)로 검사한다.
                    if (document.getElementById("replyWriter").value.trim() === "") {
                        alert("작성자를 입력헤주세요");
                        return;
                    }
                    if (document.getElementById("replyContent").value.trim() === "") {
                        alert("댓글을 입력헤주세요");
                        return;
                    }
                    fetch(url, {
                        method: 'post',
                        headers: {// header에는 보내는 데이터의 자료형에 대해서 기술
                            //json 데이터를 요청과 함께 전달, @RequestBody를 입력받는 로직에 추가
                            "Content-Type": "application/json",
                        },
                        body: JSON.stringify({ // 실질적으로 요청과 보낼 json정보를 기술
                            replyWriter: document.getElementById("replyWriter").value,
                            replyContent: document.getElementById("replyContent").value,
                            blogId: "${blog.blogId}"
                        }), //insert 로직이기 때문에 reponse에 실제화면에 사용할 데이터 전송 X
                    }).then(() => {
                        document.getElementById("replyWriter").value = "";
                        document.getElementById("replyContent").value = "";
                        alert("댓글 작성 완료");
                        getAllReplies(blogId);
                    })
                }

                document.getElementById("replySubmit").addEventListener("click", insertReply);

                $replies.onclick = e =>{
                    //#replies의 자손(공백/자식은 >) 객체 중 .deleteReplyBtn인지 확인
                    if(!e.target.matches('#replies .deleteReplyBtn')){
                        return;
                    }
                    //event 객체안에 target안에 dataset안에 replyId(카멜케이스 인정 X 소문자로 되어있음)가 있다
                    const replyId = e.target.dataset.replyid;
                    if(confirm("댓글을 삭제하시겠습니까?")){
                        fetch(`/reply/\${replyId}`,{method: 'delete'})
                        .then(()=>{
                            alert("삭제완료");
                            getAllReplies(blogId);
                        });
                    }
                }
            </script>
        </body>

        </html>