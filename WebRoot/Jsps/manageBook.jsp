<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>管理作品-览书网作者中心</title>
    <link rel="Shortcut Icon" href="../img/slogo.ico" >
    <link rel="stylesheet" href="../css/author.css"/>
    <link rel="stylesheet" href="../css/manageBook.css"/>
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
        	<div class="name" >《{{book.bookName}}》</div>
        	<div class="status">
        		<span>[{{book.state?'新建':'待审核'}}]</span>
        		<span>分类：[{{book.secondName}}]</span>
        		<span>最新章节：[{{book.chapterId?book.chapterName:'还没有章节'}}]</span>
        		<span>总字数：{{book.wordNum}}</span>
        	</div>
        	<div class="nav">
        		<ul>
        			<li :class="sign?'current':''" @click="sign=1">
        				基本信息
        			</li>
        			<li :class="!sign?'current':''" @click="sign=0">
        				章节管理
        			</li>
        		</ul>
        		<div class="book-status">
        			<span>点击数：{{book.clickNum}}</span>
        			<span>收藏数：{{book.collectionNum}}</span>
        		</div>
        	</div>
        	<ul v-if="sign">
        		<li class="edit"><a href="javascript:void(0);" @click="edit=true">编辑</a></li>
        		<li class="book-info">
        			<div class="info-title">
                        <span class="red">*</span>作品名称：
                    </div>
                    <div class="info-content">
                        <p v-text="book.bookName" ></p>
                        <p class="remind clear">如需修改请邮件至<a href="mailto:18279668701@163.com">18279668701@163.com</a></p>
                    </div>
        		</li>
        		<li class="book-info">
        			<div class="info-title">
                        <span class="red">*</span>作品类型：
                    </div>
                    <div class="info-content">
                        <p v-text="book.secondName"></p>
                    </div>
        		</li>
        		<li class="book-info">
        			<div class="info-title">
                            作品封面：
                        </div>
                        <div class="info-content">
                           <div class="clearfix">
                               <div class="book-upload">
                                  <div class="pic">
                                      <img :src="book.cover" alt="图书封面">
                                      <div v-if="edit" class="mask">
                                          点击上传
                                      </div>
                                  </div>
                                  <input v-if="edit" type="file" class="upload" accept="image/png,image/gif,image/jpeg" @change="uploadCover($event)">
                               </div>
                           </div>
                        </div>
        		</li>
        		<li class="book-info">
        			<div class="info-title">
                        <span class="red">*</span>作品标签：
                    </div>
                    <div class="info-content">
                    	<p v-if="!edit" v-text="book.label"></p>
                        <p  v-else="edit"><input type="text" v-model="book.label"/></p>
                        <p v-else="edit" class="remind clear">1-20个字，多个标签请用逗号隔开</p>
                    </div>
        		</li>
        		<li class="book-info">
        			<div class="info-title">
                        <span class="red">*</span>作品简介：
                    </div>
                    <div class="info-content">
                    	<p v-if="!edit" v-text="book.profile"></p>
                    	<div v-else="edit">
                        <textarea  v-model="book.profile"></textarea>
                        <p class="remind clear">字数需控制在20至300字之间<span class="size" v-text="book.profile.length"></span><span class="size-tip">当前字数:</span></p>
                    	</div>
                    </div>
        		</li>
	       		<li v-if="edit" class="submit" @click="submit()">
	                <div class="btn">提交</div>
	            </li>
        	</ul>
        	<ul v-else="!sign">
        		<li class="edit"><a :href="'addChapter.jsp?bookId='+book.bookId+'&bookName='+book.bookName">新增章节</a></li>
        		<li v-for="item in chapter" class="chapter">
        			<div class="title" v-text="item.name"></div>
        			<div class="word" v-text="item.wordNum"></div>
        			<div class="op">
        				<a :href="'addChapter.jsp?bookId='+book.bookId+'&bookName='+book.bookName+'&chapterId='+item.id" >修改</a>
        				<a href="javascript:void(0);" @click="del(item.id,item.wordNum)">删除</a>
        			</div>
        		</li>
        		<p v-if="chaptersSize>20" @click="load()" class="load">加载更多...</p>
        	</ul>
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
    <script src="../js/manageBook.js"></script>
</html>
