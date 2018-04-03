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
			  <el-menu default-active="2-2" background-color="#eef1f6" >
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
		    <el-row>
			  <el-col :span="24"><el-input v-model="search" placeholder="书名"></el-input><el-button type="primary" size="medium" @click="searchBook()">查询</el-button>
			  <el-dropdown @command="handleCommand">
				  <el-button type="primary" size="medium">
				      排序方式<i class="el-icon-arrow-down el-icon--right"></i>
				  </el-button>
				  <el-dropdown-menu slot="dropdown">
				    <el-dropdown-item command="1">按用户收藏数</el-dropdown-item>
				    <el-dropdown-item command="2">按用户点击数</el-dropdown-item>
				    <el-dropdown-item command="3">按用户推荐数</el-dropdown-item>
				  </el-dropdown-menu>
				</el-dropdown>
			  </el-col>
			</el-row>
	    	<el-table :data="books" style="width: 100%" border stripe>
		    <el-table-column type="expand">
		      <template slot-scope="props">
		        <el-form label-position="left" inline class="demo-table-expand">
		          <el-form-item label="书籍名称">
		            <span>{{ props.row.bookName}}</span>
		          </el-form-item>
		          <el-form-item label="书籍封面" style="float:right">
		            <span><img :src="props.row.cover" width="100"/></span>
		          </el-form-item>
		          <el-form-item label="所属类型">
		            <span>{{ props.row.firstName+'/'+props.row.secondName }}</span>
		          </el-form-item>
		          <el-form-item label="书籍标签">
		            <span>{{ props.row.label }}</span>
		          </el-form-item>
		          <el-form-item label="收藏数">
		            <span>{{ props.row.collectionNum }}</span>
		          </el-form-item>
		          <el-form-item label="点击数">
		            <span>{{ props.row.clickNum }}</span>
		          </el-form-item>
		          <el-form-item label="推荐数">
		            <span>{{ props.row.recommendNum }}</span>
		          </el-form-item>
		          <el-form-item label="书籍简介">
		            <span class="pro">{{ props.row.profile }}</span>
		          </el-form-item>
		        </el-form>
		      </template>
		    </el-table-column>
		    <el-table-column label="书籍编号" prop="bookId" width="200">
		    </el-table-column>
		    <el-table-column label="书籍名称" prop="bookName">
		    </el-table-column>
		    <el-table-column label="书籍简介" prop="profile">
		    </el-table-column>
		    <el-table-column label="操作" width="200">
		      <template slot-scope="props" class="op">
		        <el-button v-if="props.row.isRecommend" type="danger" size="mini" @click="recommend(props.row.bookId,0)">取消推荐</el-button>
		        <el-button v-else="!props.row.isRecommend" size="mini" @click="recommend(props.row.bookId,1)">推荐</el-button>
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
<script src="../../js/recommendBook.js"></script>
</html>
