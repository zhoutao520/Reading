<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>管理员-览书网</title>
    <link rel="Shortcut Icon" href="../../img/slogo.ico" >
    <link rel="stylesheet" href="../../assets/elementUI.css"/>
    <link rel="stylesheet" href="../../css/admin.css"/>
</head>
<body>
<div id="app">
	<el-container>
	  <el-header>
	  	<el-row type="flex" justify="space-between">
		  <el-col :span="3"><div class="logo">BOOKREADINGADMIN</div></el-col>
		  <el-col :span="6"><div class="user">管理员{{id}}<span @click="exit">退出</span></div></el-col>
		</el-row>
	  </el-header>
	  <el-container>
	    <el-aside width="200px">
			  <el-menu default-active="3-1" background-color="#eef1f6" >
		      <a href="adminInfo.jsp">
		      <el-menu-item index="1">
		        <i class="el-icon-info"></i>
		        <span slot="title">信息管理</span>
		      </el-menu-item>
		      </a>
		      <el-submenu index="2">
		        <template slot="title">
		          <i class="el-icon-menu"></i>
		          <span>书籍管理</span>
		        </template>
		        <a href="admin.jsp">
		        <el-menu-item-group>
		          <el-menu-item index="2-1">审核书籍</el-menu-item>
		        </el-menu-item-group>
		        </a>
		        <a href="recommendBook.jsp">
		        <el-menu-item-group>
		          <el-menu-item index="2-2">推荐书籍</el-menu-item>
		        </el-menu-item-group>
		        </a>
		      </el-submenu>
		      <el-submenu index="3">
		        <template slot="title">
		          <i class="el-icon-tickets"></i>
		          <span>资讯管理</span>
		        </template>
		        <a href="addNews.jsp">
		        <el-menu-item-group>
		          <el-menu-item index="3-1">上传资讯</el-menu-item>
		        </el-menu-item-group>
		        </a>
		        <a href="news.jsp">
		        <el-menu-item-group>
		          <el-menu-item index="3-2">删除资讯</el-menu-item>
		        </el-menu-item-group>
		        </a>
		      </el-submenu>
		    </el-menu>
	    </el-aside>
	    <el-main>
	    	<el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
			  <el-form-item label="标题" prop="title">
			    <el-input v-model="ruleForm.title"></el-input>
			  </el-form-item>
			  <el-form-item label="活动类型" prop="region">
			    <el-select v-model="ruleForm.region" placeholder="请选择活动区域">
			      <el-option label="图书推荐" value="shanghai"></el-option>
			      <el-option label="活动" value="beijing"></el-option>
			    </el-select>
			  </el-form-item>
			  <el-form-item label="图片" prop="imageUrl">
				  <el-upload action="http://upload.qiniu.com" drag :on-success="handleAvatarSuccess" :data="postData">
				    <img v-if="ruleForm.imageUrl" :src="ruleForm.imageUrl" width="360" height="180">
				    <i class="el-icon-upload"></i>
				    <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em>      </div>
				</el-upload>
			  </el-form-item>
			  <el-form-item label="内容" prop="content">
			    <el-input type="textarea" v-model="ruleForm.content"></el-input>
			  </el-form-item>
			  <el-form-item>
			    <el-button type="primary" @click="submitForm('ruleForm')">立即创建</el-button>
			    <el-button @click="resetForm('ruleForm')">重置</el-button>
			  </el-form-item>
			</el-form>
	    </el-main>
	  </el-container>
	</el-container>
</div>
  </body>
<script src="../../assets/vue.js"></script>
<script src="../../assets/elementUI.js"></script>
<script src="../../assets/axios.js"></script>
<script src="../../js/addNews.js"></script>
</html>
