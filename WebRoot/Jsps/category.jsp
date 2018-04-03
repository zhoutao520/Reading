<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>全部作品-览书网</title>
    <link rel="Shortcut Icon" href="../img/slogo.ico" >
    <link rel="stylesheet" href="../css/base.css"/>
    <link rel="stylesheet" href="../css/category.css"/>
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
        <p class="first">
            <span><em><i></i><i></i><i></i></em>作品分类</span>
        </p>
        <ul>
            <li><a href="category.jsp">全部作品</a></li>
            <li><a href="category.jsp">排行</a></li>
            <li><a href="category.jsp">完本</a></li>
            <li><a href="category.jsp">免费</a></li>
            <li><a href="javascript:void(0);" @click="author()">作家专区</a></li>
        </ul>
    </nav>
</nav>
<article class="auto">
    <ul class="nav-list">
        <li class="category" :class="item.id==active&&'category-active'" v-for="item in firstTypes">
            <div class="nav-item" :style="item.id==active&&'background-color:#f4f4f4;'" @click="select(item.id)"><span class="icon trigger"></span><a :href="'category.jsp?firstId='+item.id+'&firstName='+item.name" v-text="item.name"></a></div>
            <ul>
                <li v-for="seitem in secondTypes" :style="seitem.id==seactive&&'background-color:#f4f4f4;'"><a :href="'category.jsp?firstId='+item.id+'&firstName='+item.name+'&secondId='+seitem.id+'&secondName='+seitem.name" v-text="seitem.name"></a></li>
            </ul>
        </li>
    </ul>
    <div class="content">
        <div class="sort">
            <dl>
                <dt>当前位置:</dt>
                <dd class="sort-category">
                    <ul>
                        <li><a href="">全部作品</a></li>
                        <li v-if="firstId!=''" v-text="firstName"></li>
                        <li v-if="secondId!=''" v-text="secondName"></li>
                    </ul>
                </dd>
                <dt>上架时间:</dt>
                <dd>
                    <ul>
                        <li><a class="green" href="">全部</a></li>
                        <li><a href="">七日内</a></li>
                        <li><a href="">半月内</a></li>
                        <li><a href="">一月内</a></li>
                    </ul>
                </dd>
                <dt>排序方式:</dt>
                <dd>
                    <ul>
                        <li><a class="green" href="">最热</a></li>
                        <li><a href="">最新</a></li>
                    </ul>
                </dd>
            </dl>   
        </div>
        <div class="book-content">
            <ul  v-if="size!=0">
                <li v-for="item in books" >
                    <div class="book-img">
                        <a :href="'bookInfo.jsp?bookId='+item.bookId"><img :src="item.cover" alt=""/></a>
                    </div>
                    <div class="book-info">
                        <a :href="'bookInfo.jsp?bookId='+item.bookId"><h4 v-text="item.bookName"></h4></a>
                        <p class="author">
                            <span v-text="item.authorName"></span>
                            <em>|</em>
                            <span v-text="item.firstName"></span>
                            <i>·</i>
                            <span v-text="item.secondName"></span>
                        </p>
                        <p class="intro" v-text="item.profile">
                        </p>
                        <p class="book-word"><span>{{item.wordNum>10000?item.wordNum/=10000+'万':item.wordNum}}字</span></p>
                    </div>
                </li>
            </ul>
            <div class="clear"></div>
        <div v-if="size==0" class="null">
        	<img src="../img/alert-light.png" alt="icon">
        	<h2>对不起，您所选条件没有相关作品！</h2>
        </div>
        </div>
        <div  class="page">
        	<span></span>
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
<script src='../js/category.js'></script>
</html>