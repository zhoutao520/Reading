<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>个人中心-览书网</title>
    <link rel="Shortcut Icon" href="../img/slogo.ico" >
    <link rel="stylesheet" href="../css/base.css"/>
    <link rel="stylesheet" href="../css/personal.css"/>
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
<div class="main">
	<nav>
		<ul>
			<li :class="type==1&&'active'">
				<a href="javascript:;" @click="type=1">
				<svg class="icon" aria-hidden="true">
				    <use xlink:href="#icon-gerenxinxi1"></use>
				</svg>
				<span>个人信息</span></a>
			</li>
			<li :class="type==2&&'active'">
				<a href="javascript:;" @click="type=2">
				<svg class="icon" aria-hidden="true">
				    <use xlink:href="#icon-xiugaimima"></use>
				</svg>
				<span>修改密码</span></a>
			</li>
			<li :class="type==3&&'active'">
				<a href="javascript:;" @click="type=3">
				<svg class="icon" aria-hidden="true">
				    <use xlink:href="#icon-shuping"></use>
				</svg>
				<span>我的书评</span></a>
			</li>
		</ul>
	</nav>
	<div class="mainbox">
		<div v-show="type==1">
			<dl>
				<dt>账号</dt>
				<dd v-text="id"></dd>
			</dl>
			<dl>
				<dt>昵称</dt>
				<dd><input type="text" v-model="name" /></dd>
			</dl>
			<dl>
				<dt>头像</dt>
				<dd>
					<div class="head-upload">
						<div class="pic">
		                  <img :src="head" alt="图书封面">
		                  <div class="mask">编辑头像</div>
		                </div>
	                    <input type="file" class="upload" accept="image/png,image/gif,image/jpeg" @change="uploadHead($event)">
					</div>
				</dd>
			</dl>
			<dl>
				<dt>性别</dt>
				<dd>
					<input type="radio" id="radioSex1" v-model="sex" name="sex" required value="0">
					<label for="radioSex1">男</label>
					<input type="radio" id="radioSex2" v-model="sex" name="sex" required value="1">
					<label for="radioSex2">女</label>
				</dd>
			</dl>
			<dl>
				<dt>实名认证</dt>
				<dd><a href="javascript:;" @click="check=!check" v-text="IDCard!=''?'查看实名认证信息':'未实名认证'"></a></dd>
			</dl>
			<div v-show="check">
				<dl>
					<dt>姓名</dt>
					<dd><input type="text" v-if="!certification" v-model="realName" /><span v-text="certification?realName:''"></span></dd>
				</dl>
				<dl>
					<dt>身份证号</dt>
					<dd><input type="text" v-if="!certification" v-model="IDCard" /><span v-text="certification?IDCard:''"></span></dd>
				</dl>
			</div>
			<dl>
				<dt>简介</dt>
				<dd>
					<div class="profile">
						<textarea id="profile" v-model="profile" placeholder="可以简单的描述自己"></textarea>
						<div class="profile-border"></div>
						<label for="profile" class="count"><span v-text="profile.length"></span>/200</label>
					</div>
				</dd>
			</dl>
			<dl>
				<dt></dt>
				<dd class="submit" @click="submit">保存</dd>
			</dl>
		</div>
		<div v-show="type==2">
			<dl>
				<dt>当前密码</dt>
				<dd>
					<input type="password" v-model.lazy="oldpassword" placeholder="请填写当前密码"/>
					<p v-text="passwordCheck==1?'密码错误':''"></p>
				</dd>
			</dl>
			<dl>
				<dt>新密码</dt>
				<dd>
					<input type="password" v-model.lazy="newpassword" placeholder="6-18位大小写字母、符号或数字"/>
					<p v-text="passwordCheck==2?'长度为6-18个字符，且不能是纯数字或纯字母':''"></p>
				</dd>
			</dl>
			<dl>
				<dt>确认新密码</dt>
				<dd>
					<input type="password" v-model.lazy="renewpassword" placeholder="请再次输入密码"/>
					<p v-text="passwordCheck==3?'密码错误':''"></p>
				</dd>
			</dl>
			<dl>
				<dt></dt>
				<dd class="submit" @click="submitPwd">保存</dd>
			</dl>
		</div>
		<div v-show="type==3">
			<div></div>
			你还未发表评论哦~
			
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
  <script src='//at.alicdn.com/t/font_474916_srmjn6u9pkuzncdi.js'></script>
  <script src='../js/personal.js'></script>
</html>