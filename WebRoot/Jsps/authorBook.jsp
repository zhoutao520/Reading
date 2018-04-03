<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>全部作品-览书网作者中心</title>
    <link rel="Shortcut Icon" href="../img/slogo.ico" >
    <link rel="stylesheet" href="../css/author.css"/>
    <link rel="stylesheet" href="../css/authorBook.css"/>
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
            <div class="content-name">
                <h2>作品列表<span>共 {{size}} 部作品</span></h2>
                <p><a href="createBook.jsp">发表作品</a></p>
            </div>
            <table cellpadding="0" cellspacing="0">
                <thead>
                    <tr>
                        <th>作品名称</th>
                        <th>最新发布章节</th>
                        <th>作品分组</th>
                        <th>作品状态</th>
                        <th>作品定价</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="item in books">
                        <td>
                            <div class="book-info">
                                <div class="pic"><img :src="item.cover" alt=""/></div>
                                <p v-text="item.bookName"></p>
                                <p class="word">总字数：{{item.wordNum}}</p>
                                <p class="word">点击数：{{item.clickNum}}</p>
                                <p class="word">收藏数：{{item.collectionNum}}</p>
                                <p class="ops"><a :href="'manageBook.jsp?bookId='+item.bookId+'&sign=1'">修改作品</a></p>
                            </div>
                        </td>
                        <td>
                            <p class="intro">您的作品尚未通过审核、章节尚未发布。如有章节已提交，我们会尽快审核上线</p>
                            <p class="time"></p>
                        </td>
                        <td>{{item.firstName}}</td>
                        <td v-text="item.state?'新建':'待审核'"></td>
                        <td>-</td>
                        <td>
                            <p><a :href="'addChapter.jsp?bookId='+item.bookId+'&bookName='+item.bookName">新增章节</a></p>
                            <p><a :href="'manageBook.jsp?bookId='+item.bookId+'&sign=0'">管理章节</a></p>
                        </td>
                    </tr>
                </tbody>
            </table>
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
    <script src="../js/authorBook.js"></script>
</html>