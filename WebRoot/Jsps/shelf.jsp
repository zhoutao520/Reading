<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>我的书架-览书网</title>
    <link rel="Shortcut Icon" href="../img/slogo.ico" >
    <link rel="stylesheet" href="../css/base.css"/>
    <link rel="stylesheet" href="../css/search.css"/>
    <script src="../assets/vue.js"></script>
    <script src="../assets/axios.js"></script>
</head>
<body>
<div id="app">
<header>
    <div class="top">
        <nav class="auto">
        	<a href="index.jsp">览书网</a>
        	<div v-if="id==''" class="login-box">
        		<a href="userLogin.jsp">登录</a>
        		<a href="userRegister.jsp">注册</a>
        	</div>
        	<div v-else="id!=''" class="login-box">
        		<a href="personal.jsp" v-text="id"></a>
        		<a @click="exit()">登出</a>
        	</div>
        </nav>
    </div>
    <div class="box-center">
    	<p>
    	<a  href="index.jsp"><img alt="logo" src="../img/logo.png"><span>览书网</span></a>
    	</p>
    	<div class="search-wrap">
    		<p>
    			<input class="search-box" type="text" v-model="search">
    		</p>
    		<a :href="'search.jsp?search='+search"><span><img alt="search" src="../img/search.png"/></span></a>
    	</div>
    	<div class="book-shelf">
    		<a :href="id!=''&&'shelf.jsp'"><span>我的书架</span></a>
    	</div>
    </div>
    
</header>
<nav class="top-nav">
    <nav class="auto">
        <ul>
            <li><a href="category.jsp">全部作品</a></li>
            <li><a href="category.jsp">排行</a></li>
            <li><a href="category.jsp">完本</a></li>
            <li><a href="category.jsp">免费</a></li>
            <li><a href="javascript:void(0);" @click="author()">作家专区</a></li>
        </ul>
    </nav>
</nav>
<div class="box auto shlef-box">
	<p class="shlef">我的书架</p>
	<ul>
		<li v-for="item in books">
			<div class="book-img">
				<a :href="'bookInfo.jsp?bookId='+item.bookId"><img :src="item.cover" ></a>
			</div>
			<div class="info">
				<h4 ><a :href="'bookInfo.jsp?bookId='+item.bookId" v-text="item.bookName"></a></h4>
				<p class="author">
					<span v-text="item.authorName"></span>
					<em>|</em>
					<span v-text="item.firstName"></span>
					<em>|</em>
					<span v-text="item.secondName"></span>
					<em>|</em>
					<span v-text="item.state==1&&'连载中'"></span>
				</p>
				<p class="intro" v-text="item.profile">
				</p>
				<p class="update" >
					最近更新：<span v-text="item.chapterName"></span>
				</p>
			</div>
			<div class="info-right">
				<div class="total">
					<p>总字数：<span v-text="item.wordNum"></span></p>
					<p>总推荐：<span v-text="item.collectionNum"></span></p>
					<p>总点击：<span v-text="item.clickNum"></span></p>
				</div>
				<div style="margin-top:60px;margin-left:20px;color:#ff1919;cursor:pointer;" @click="delSub(item.id)">删除</div>
			</div>
		</li>
	</ul>
</div>
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
  <script src='../js/shelf.js'></script>
</html>
