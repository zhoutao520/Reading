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
        <title>作者注册-览书网作者中心</title>
        <link rel="Shortcut Icon" href="../img/slogo.ico" >
        <link rel="stylesheet" href="../css/register.css"/>
        <script src="../assets/vue.js"></script>
        <script src="../assets/axios.js"></script>
    </head>
    <body>
        <header>
            <div class="center">
                <div class="logo">
                    <img src="../img/logo.png" alt="">
                    <span class="logo-info"><a href="index.jsp">览书网</a></span>
                    <em></em>
                    <span class="lang">作者注册</span>
                </div>
            </div>
        </header>
        <div class="content">
            <div id="reg">
                <div class="reg-step">
                    <span :class="act?'act':''"><i>1</i>填写注册信息</span>
                    <span :class="act?'':'act'"><i>2</i>注册成功</span>
                </div>
                <div class="reg">
                    <div class="reg-form" v-if="act">
                        <dl>
                            <dd class="phone">
                                <em>手机号</em>
                                <input type="text" v-model.lazy="phone" placeholder="输入手机号码" :class="error[0].flag?'error':''"><span v-text="error[0].phone"></span>
                            </dd>
                            <dd> 
                                <em>短信验证</em>
                                <input type="text" v-model.lazy="code" class="code" placeholder="输入验证码" :class="error[1].flag?'error':''">
                                <a href="javascript:;" class="get-code" @click="codeFlag && getCode()" v-text="getCodeMsg"></a>
                                <span v-text="error[1].code"></span>
                            </dd>
                            <dd>
                                <em>密码</em>
                                <input type="password" v-model.lazy="pwd" placeholder="6-18位大小写字母、符号或数字" :class="error[2].flag?'error':''"><span v-text="error[2].pwd"></span>    
                            </dd>
                            <dd>
                                <em>确认密码</em>
                                <input type="password" v-model.lazy="repwd" placeholder="再次输入密码" :class="error[3].flag?'error':''"><span v-text="error[3].repwd"></span>
                            </dd>
                        </dl>
                        <button class="submit" @click="submit()">立即注册</button>
                    </div>
                    <div class="reg-success" v-else="!act">
                        <p>尊敬的用户，您好！</p>
                        <p>您的账号为</p>
                        <p v-text="phone" class="author"></p>
                        <p class="jump"><span v-text="sec"></span>秒后即将跳转至个人中心</p>
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
    <script src="../js/register.js"></script>
</html>