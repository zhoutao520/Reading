<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html lang="zh-CN">
        <head>
            <meta charset="utf-8">
            <meta name="author" content="周焘">
            <title>作者中心-览书网作者中心</title>
            <link rel="Shortcut Icon" href="../img/slogo.ico" >
            <link rel="stylesheet" href="../css/login.css"/>
            <script src="../assets/vue.js"></script>
            <script src="../assets/axios.js"></script>
        </head>
    <body>
        <header>
            <div class="logo">
                <img src="../img/logo.png" alt="">
                <h1>览书网</h1>
                <em></em>
                <h2>作者中心</h2>
            </div>
            <div class="register">
                第一次使用作者中心？
                <a href="auRegister.jsp">立即注册</a>
            </div>
        </header>
        <div class="main" id="app">
            <div class="login-bg">
                <div class="login">
                    <div class="login-head">
                        <div class="title">
                        </div>
                    </div>
                    <div class="login-content">
                        <div class="content">
                            <div class="box">
                                <div class="u-logo">
                                    <div class="img"></div>
                                </div>
                                <div class="u-input">
                                    <label :style="{display:phoneL}" for="phone">手机号</label>
                                    <input type="text" id = "phone" v-model="phone">
                                </div>
                            </div>
                            <div class="box">
                                <div class="u-logo">
                                    <div class="pwd-img"></div>
                                </div>
                                <div class="u-input">
                                    <label :style="{display:pwdL}" for="pwd">6-16位密码，区分大小写</label>
                                    <input type="password" id = "pwd" @keyup.enter="login()" v-model="pwd">
                                </div>
                            </div>
                            <div class="error" :style="{display:errorT}">
                                <div class="ico"></div>
                                <div class="info" v-text="error"></div>
                            </div>
                            <div class="login-btn">
                                <a href="javascript:void(0);" class="btn"  @click="login()" v-text="btn"></a>
                            </div>
                        </div>
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
    </body>
    <script src="../js/login.js"></script>
</html>