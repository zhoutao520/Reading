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
		  <el-col :span="6"><div class="user">管理员{{adminId}}<span @click="exit">退出</span></div></el-col>
		</el-row>
	  </el-header>
	  <el-container>
	    <el-aside width="200px">
			  <el-menu default-active="1" background-color="#eef1f6" >
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
		  <el-table :data="admins" style="width: 100%">
		    <el-table-column label="编号" prop="id" width="180">
		    </el-table-column>
		    <el-table-column label="昵称" prop="name" width="180">
		    </el-table-column>
		    <el-table-column label="电话" prop="phone" width="180">
		    </el-table-column>
		    <el-table-column label="密码" prop="password" width="180">
		    </el-table-column>
		    <el-table-column label="操作">
		      <template slot-scope="scope">
		        <el-button size="mini" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
		        <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
		      </template>
		    </el-table-column>
		  </el-table>
		  <p class="add" @click="addAdmin">新增</p>
		  <div class="addAdmin" v-if="add!=0">
		  	<p style="text-align: right"><span class="el-icon-close" @click="add=0" style="cursor:pointer"></span></p>
		  	<el-form ref="form" label-width="80px" size="mini">
			  <el-form-item label="编号">
			    <el-input v-model="id" :disabled="add==2?true:false"></el-input>
			  </el-form-item>
			  <el-form-item label="昵称">
			    <el-input v-model="name"></el-input>
			  </el-form-item>
			  <el-form-item label="密码">
			    <el-input v-model="password"></el-input>
			  </el-form-item>
			  <el-form-item label="手机号">
			    <el-input v-model="phone"></el-input>
			  </el-form-item>
			  <el-form-item>
			    <el-button type="primary" @click="onSubmit">提交</el-button>
			  </el-form-item>
			</el-form>
		  </div>
	    </el-main>
	  </el-container>
	</el-container>
</div>
  </body>
<script src="../../assets/vue.js"></script>
<script src="../../assets/elementUI.js"></script>
<script src="../../assets/axios.js"></script>
<script src="../../js/adminInfo.js"></script>
</html>
