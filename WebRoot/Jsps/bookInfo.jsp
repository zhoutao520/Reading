<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>作品信息-览书网</title>
    <link rel="Shortcut Icon" href="../img/slogo.ico" >
    <link rel="stylesheet" href="../css/base.css"/>
    <link rel="stylesheet" href="../css/bookInfo.css"/>
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
<div class="bg">
	<div class="breadcrumbs">
		当前位置：<a :href="'category.jsp?firstId='+book.firstId+'&firstName='+book.firstName" v-text="book.firstName"></a>
	<span class="sep">&gt;</span>
	<a :href="'category.jsp?firstId='+book.firstId+'&firstName='+book.firstName+'&secondId='+book.secondId+'&secondName='+book.secondName" v-text="book.secondName"></a>
	<span class="sep">&gt;</span>
	{{book.bookName}}
	</div>
	<div class="bg-right">
		<div class="book-status">
			<i class="angle"></i>
			<i class="angle-inner"></i>
			<ul>
				<li>字数：{{book.wordNum}}</li>
				<li>点击：{{book.clickNum}}</li>
				<li>分类：<a :href="'category.jsp?firstId='+book.firstId+'&firstName='+book.firstName+'&secondId='+book.secondId+'&secondName='+book.secondName" v-text="book.secondName"></a></li>
				<li><div>标签：</div>
					<div class="tag"><span v-for="item in label" v-text="item"></span></div>
				</li>
			</ul>
		</div>
	</div>
	<div class="bg-left">
		<div class="book-info">
			<span class="cover">
				<img :src="book.cover" width="172" height="237">
			</span>
			<div class="info">
				<h3 :title="book.bookName">{{book.bookName}}<span>{{book.authorName}} 著</span></h3>
				<p v-text="book.profile"></p>
			</div>
			<div v-if="chapters.length!=0" class="ops">
				<a :href="'bookRead.jsp?bookId='+book.bookId+'&chapterId='+chapters[0].id" target="_blank" class="read">立即阅读</a>
				<a v-if="!shelf" href="javascript:void(0);" @click="addSub()" class="addSub">加入书架</a>
				<a v-else="shelf" href="javascript:void(0);" @click="Recommend()" class="addSub">投推荐票</a>
			</div>
		</div>
		<div class="chapters">
			<div><p class="list">目录<span>（{{chaptersSize}}章）</span></p></div>
			<div class="content">
				<ul>
					<li v-for="(item,key) in chapters"  @click="bookRead(item.id)"><span v-text="item.name"></span></li>
				</ul>
				<p v-if="chaptersSize>20&&chaptersSize!=chapters.length" @click="load()" class="load">加载更多...</p>
			</div>
		</div>
		<div class="commnet">
			<h3>评论<span>（{{commentSize}}条）</span></h3>
			<div class="content">
				<div class="head">
					<img src="../img/tx.png">
				</div>
				<div @click="(id=='')&&(login=true)" class="box">
					<div class="reply">
						<div class="contentwrap">
					        <i class="angle-l"></i><i class="angle-inner-l"></i>
					        <textarea name="content" :placeholder="(id=='')?'请登录后评论':'评论：《'+book.bookName+'》'" v-model="comContent"></textarea>
	    				</div>
	    				<div class="bottombar">
					        <p><a href="javascript:void(0);" @click="comment()">评 论</a></p>
					        <p style="color: rgb(102, 102, 102);">还能输入<span v-text="500-comContent.length"></span>字</p>
					    </div>
					</div>
				</div>
				<ul>
					<li v-if="commentSize>0" v-for="item in comments">
						<div class="head">
							<img :src="item.head">
						</div>
						<div class="info" v-text="item.name==undefined?item.reviewerId:item.name"></div>
						<blockquote v-text="item.content"></blockquote>
						<div class="opt">
							<span class="time" v-text="item.time.replace('.0','')"></span>
							<a v-show="id==item.reviewerId" href="javascript:void(0);" @click="delComent(item.id)" class="del">删除</a>
							<span class="sep">|</span>
							<a href="javascript:void(0);" @click="likeComent(item.id)" class="like"><i></i>{{item.praiseNum==0?'点赞':'('+item.praiseNum+')'}}</a>
						</div>
					</li>
				</ul>
				<p v-if="commentSize>10&&commentSize!=comments.length" @click="loadCom()" class="load">加载更多...</p>
			</div>
		</div>
	</div>
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
  <script src="../js/bookInfo.js"></script>
</html>
