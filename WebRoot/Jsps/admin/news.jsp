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
			  <el-menu default-active="3-2" background-color="#eef1f6" >
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
	    	<el-table :data="news" style="width: 100%" border stripe>
		    <el-table-column label="管理员编号" prop="adminId" width="100">
		    </el-table-column>
		    <el-table-column label="标题" prop="title">
		    </el-table-column>
		    <el-table-column label="内容">
		    	<template slot-scope="props">
		        <a :href="props.row.content" target="_blank">{{props.row.content}}</a>
		      </template>
		    </el-table-column>
		    <el-table-column label="图片地址">
		    	<template slot-scope="props">
		        <a :href="props.row.photo" target="_blank">{{props.row.photo}}</a>
		      </template>
		    </el-table-column>
		    <el-table-column label="操作" width="100">
		      <template slot-scope="props" class="op">
		        <el-button size="mini" type="danger" @click="handleDelete(props.row.id)">删除</el-button>
		      </template>
		    </el-table-column>
		  </el-table>
		  <div class="block">
		  	 <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" layout="total, prev, pager, next" :total="size">
		    </el-pagination>
		  </div>
	    </el-main>
	  </el-container>
	</el-container>
</div>
  </body>
<script src="../../assets/vue.js"></script>
<script src="../../assets/elementUI.js"></script>
<script src="../../assets/axios.js"></script>
<script src="../../js/news.js"></script>
</html>
