<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>上传章节-览书网作者中心</title>
    <link rel="Shortcut Icon" href="../img/slogo.ico" >
    <link rel="stylesheet" href="../css/author.css"/>
    <link rel="stylesheet" href="../css/addChapter.css"/>
    <script src="../assets/vue.js"></script>
    <script src="../assets/axios.js"></script>
</head>
<body>
<div id="app">
<header>
    <div class="top">
        <div class="logo">
            <a href="index.jsp"><img src="../img/logo.png" alt="">
            <h1>览书网</h1></a>
            <em></em>
            <h2>作者中心</h2>
            <p>
                <a v-text="id"></a>
                <a href="javascript:void(0);" @click="exit()"><span></span>退出</a>
            </p>
        </div>
    </div>
</header>
<article class="auto">
    <div class="side-bg">
    </div>
    <div class="side">
        <ul>
            <li class="all-book current"><h4><em></em><a href="authorBook.jsp">全部作品</a></h4></li>
            <li class="incomes "><h4><em></em><a href="authorSale.jsp">我的收入</a></h4></li>
            <li class="comment "><h4><em></em><a href="authorComment.jsp">读者评论</a></h4></li>
            <li class="message "><h4><em></em><a href="authorMessage.jsp">消息中心</a></h4></li>
        </ul>
    </div>
    <div class="main">
        <div class="content">
            <div class="from-name">
                上传《{{bookName}}》章节
            </div>
            <p class="warning">
                发表作品中不得含有淫秽色情、反动、违背主流道德观等有害信息。如发现作品中含有违法违规内容，览书网将对违法违规作品进行删除处理，冻结作者账号并保留追究作者法律责任的权利。请各位作者自觉自律，切勿以身试法，共建和谐文明的网络环境。
            </p>
            <ul>
                <li>
                    <div class="item-title"><span class="red">*</span>请输入章节名：</div>
                    <div class="item-content">
                        <p>
                            <input type="text" v-model.lazy="chapterName">
                            <span v-text="chapterNameT"></span>
                        </p>
                        <p class="gray">示例如：第一章：黄山奇遇。（限20字内）</p>
                    </div>
                </li>
                <li>
                    <div class="item-title"><span class="red">*</span>章节内容：</div>
                    <div class="item-content">
                        <div class="editor">
                            <textarea v-model="chapterContent"></textarea>
                        </div>
                        <p class="gray">章节内容里请不要包含标题，字数限制为100-10000字。<span>当前字数：{{size}}</span></p>
                    </div>
                </li>
            </ul>
            <div class="submit" @click="submit">
                <div class="btn">提交</div>
            </div>
        </div>
    </div>
    <div class="clear"></div>
</article>
<footer>
    <nav>
        <a>览书首页</a>
        <a>联系我们</a>
        <a>帮助中心</a>
        <a>提交建议</a>
    </nav>
    <p>Copyright © 2017-2035 Mr.Zhou All Rights Reserved版权所有<br/>
        请所有作者发布作品时务必遵守国家互联网信息管理办法规定，我们拒绝任何色情小说，一经发现，即作删除！举报电话：18279668701<br/>
        本站所收录的作品、社区话题、用户评论、用户上传内容或图片等均属用户个人行为。如前述内容侵害您的权益，欢迎举报投诉，一经核实，立即删除，本站不承担任何责任</p>
</footer>
</div>
</body>
<script src="../js/addChapter.js"></script>
</html>