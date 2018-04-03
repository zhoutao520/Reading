<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html >
<head lang="en">
    <meta charset="UTF-8">
    <title>首页-览书网</title>
    <link rel="Shortcut Icon" href="../img/slogo.ico" >
    <link rel="stylesheet" href="../css/base.css"/>
    <link rel="stylesheet" href="../css/index.css"/>
    
    <script src="../assets/vue.js"></script>
    <script src="../assets/axios.js"></script>
    <!-- <script>
        if ((navigator.userAgent.match(/(phone|pad|pod|iPhone|iPod|ios|iPad|Android|Mobile|BlackBerry|IEMobile|MQQBrowser|JUC|Fennec|wOSBrowser|BrowserNG|WebOS|Symbian|Windows Phone)/i))) {
            window.location.href = "../pages/mobile/mIndex.html";
        }
    </script> -->
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
<div class="top-nav">
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
</div>
<article class="auto nav-center">
    <nav>
        <dl>
            <dd class="null" @click="cata(1,'小说作品')">
            	<svg class="icon" aria-hidden="true">
				    <use xlink:href="#icon-xuanhuan"></use>
				</svg>
				<span>小说作品</span>
			</dd>
            <dd class="null" @click="cata(2,'人文社科')">
            	<svg class="icon" aria-hidden="true">
				    <use xlink:href="#icon-lishirenwen"></use>
				</svg>
				<span>人文社科</span>
			</dd>
            <dd class="even"  @click="cata(3,'品质生活')">
            	<svg class="icon" aria-hidden="true">
				    <use xlink:href="#icon-iconfontpinzhishenghuo2"></use>
				</svg>
				<span>品质生活</span>
			</dd>
            <dd class="even" @click="cata(4,'成功励志')">
            	<svg class="icon" aria-hidden="true">
				    <use xlink:href="#icon-lizhi"></use>
				</svg>
				<span>成功励志</span>
			</dd>
            <dd @click="cata(5,'经济管理')">
            	<svg class="icon" aria-hidden="true">
				    <use xlink:href="#icon-jingjiguanli"></use>
				</svg>
				<span>经济管理</span>
			</dd>
            <dd @click="cata(6,'文学作品')">
            	<svg class="icon" aria-hidden="true">
				    <use xlink:href="#icon-wenxue"></use>
				</svg>
				<span>文学作品</span>
			</dd>
            <dd class="even" @click="cata(7,'自然科学')">
            	<svg class="icon" aria-hidden="true">
				    <use xlink:href="#icon-xuekeshezhi"></use>
				</svg>
				<span>自然科学</span>
			</dd>
            <dd class="even" @click="cata(8,'计算机')">
            	<svg class="icon" aria-hidden="true">
				    <use xlink:href="#icon-jisuanjiyubiancheng"></use>
				</svg>
				<span>计算机</span>
			</dd>
            <dd @click="cata(9,'培训考试')">
            	<svg class="icon" aria-hidden="true">
				    <use xlink:href="#icon-peixunkaoshi"></use>
				</svg>
				<span>培训考试</span>
			</dd>
            <dd @click="cata(10,'亲子少儿')">
            	<svg class="icon" aria-hidden="true">
				    <use xlink:href="#icon-qinzi"></use>
				</svg>
				<span>亲子少儿</span>
			</dd>
        </dl>
    </nav>
    <div class="slide" v-on:mouseover="stop()" v-on:mouseout="move()">  
		<transition-group tag="ul" class="slide-ul" name="list">  
			 <li v-for="(item, index) in imgArray"  v-show="index===mark" :key="index">  
			   <a :title="item.title" target="_blank" :href="item.content">  
			     <img :src='item.photo'>  
			   </a>  
			 </li>  
		</transition-group>
	    <div class="bar">  
	      <span v-for="(item, index) in imgArray" :class="{ 'active':index===mark }" @click="change(index)" :key="index"></span>  
	    </div>  
  	</div>  
</article>
<article class="auto recommend">
    <div class="left-list">
        <h3>主编推荐</h3>
        <div class="line1"></div>
        <div class="line2"></div>
        <ul>
        	<li v-for="item in editbooks">
        		<div class="book-img">
        			<a target="_blank" :href="'bookInfo.jsp?bookId='+item.bookId"><img :src="item.cover"></a>
        		</div>
        		<div class="book-info">
        			<h3><a target="_blank" :href="'bookInfo.jsp?bookId='+item.bookId" v-text="item.bookName"></a></h3>
        			<p v-text="item.profile"></p>
        			<div class="state">
        				<i>{{item.wordNum}}字</i>
        				<i v-text="item.firstName"></i>
        				<span v-text="item.authorName"></span>
        			</div>
        		</div>
        	</li>
        </ul>
    </div>
    <div class="right-list">
        <h3>本周强推</h3>
        <ul>
            <li :class="!key&&'unfold'" v-for="(item,key) in userbooks">
            	<div v-if="!key" class="book-wrap">
            		<div class="book-info">
            			<h3 v-text="item.firstName"></h3>
            			<h4><a target="_blank" :href="'bookInfo.jsp?bookId='+item.bookId" v-text="item.bookName"></a></h4>
            			<p v-text="item.authorName"></p>
            		</div>
            		<div class="book-cover">
            			<a target="_blank" :href="'bookInfo.jsp?bookId='+item.bookId"><img :src="item.cover"></a>
            			<span></span>
            		</div>
            	</div>
            	<a v-if="key" target="_blank" :href="'bookInfo.jsp?bookId='+item.bookId" v-text="item.bookName" ></a>
            	<i v-if="key">{{item.wordNum}}字</i>
            </li>
        </ul>
    </div>
</article>
<article class="auto recommend">
    <div class="left-list">
        <h3>热门图书</h3>
        <div class="line1"></div>
        <div class="line2"></div>
        <ul>
        	<li v-for="item in hotbooks">
        		<div class="book-img">
        			<a target="_blank" :href="'bookInfo.jsp?bookId='+item.bookId"><img :src="item.cover"></a>
        		</div>
        		<div class="book-info">
        			<h3><a target="_blank" :href="'bookInfo.jsp?bookId='+item.bookId" v-text="item.bookName"></a></h3>
        			<p v-text="item.profile"></p>
        			<div class="state">
        				<i>{{item.wordNum}}字</i>
        				<i v-text="item.firstName"></i>
        				<span v-text="item.authorName"></span>
        			</div>
        		</div>
        	</li>
        </ul>
    </div>
    <div class="right-list">
        <h3>总点击榜</h3>
        <ul>
            <li :class="!key&&'unfold'" v-for="(item,key) in clickbooks">
            	<div v-if="!key" class="book-wrap">
            		<div class="book-info">
            			<h3 v-text="item.firstName"></h3>
            			<h4><a target="_blank" :href="'bookInfo.jsp?bookId='+item.bookId" v-text="item.bookName"></a></h4>
            			<p v-text="item.authorName"></p>
            		</div>
            		<div class="book-cover">
            			<a target="_blank" :href="'bookInfo.jsp?bookId='+item.bookId"><img :src="item.cover"></a>
            			<span></span>
            		</div>
            	</div>
            	<a v-if="key" target="_blank" :href="'bookInfo.jsp?bookId='+item.bookId" v-text="item.bookName" ></a>
            	<i v-if="key">{{item.wordNum}}字</i>
            </li>
        </ul>
    </div>
</article>
<article class="auto recommend">
    <div class="left-list">
        <h3>随机推荐<a href="javascript:void(0);" @click="rand()"><svg class="icon" aria-hidden="true">
				    <use xlink:href="#icon-f14"></use>
				</svg>换一批</a></h3>
        <div class="line1"></div>
        <div class="line2"></div>
        <ul>
        	<li v-for="item in randbooks">
        		<div class="book-img">
        			<a target="_blank" :href="'bookInfo.jsp?bookId='+item.bookId"><img :src="item.cover"></a>
        		</div>
        		<div class="book-info">
        			<h3><a target="_blank" :href="'bookInfo.jsp?bookId='+item.bookId" v-text="item.bookName"></a></h3>
        			<p v-text="item.profile"></p>
        			<div class="state">
        				<i>{{item.wordNum}}字</i>
        				<i v-text="item.firstName"></i>
        				<span v-text="item.authorName"></span>
        			</div>
        		</div>
        	</li>
        </ul>
    </div>
    <div class="right-list">
        <h3>新书上架</h3>
        <ul>
            <li :class="!key&&'unfold'" v-for="(item,key) in newbooks">
            	<div v-if="!key" class="book-wrap">
            		<div class="book-info">
            			<h3 v-text="item.firstName"></h3>
            			<h4><a target="_blank" :href="'bookInfo.jsp?bookId='+item.bookId" v-text="item.bookName"></a></h4>
            			<p v-text="item.authorName"></p>
            		</div>
            		<div class="book-cover">
            			<a target="_blank" :href="'bookInfo.jsp?bookId='+item.bookId"><img :src="item.cover"></a>
            			<span></span>
            		</div>
            	</div>
            	<a v-if="key" target="_blank" :href="'bookInfo.jsp?bookId='+item.bookId" v-text="item.bookName" ></a>
            	<i v-if="key">{{item.wordNum}}字</i>
            </li>
        </ul>
    </div>
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
<script src='//at.alicdn.com/t/font_474916_srmjn6u9pkuzncdi.js'></script>
<script  src='../js/index.js'></script>
</html>