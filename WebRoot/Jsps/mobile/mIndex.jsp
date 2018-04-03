<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1,user-scalable=no">
    <title>览书网</title>
    <script src="../../js/changeFontSize.js"></script>
    <link rel="stylesheet" href="../../css/mIndex.css"/> 
    <script src="../../assets/vue.js"></script>
</head>
<body>
<header>
    <a href="">
        <h1>览书网</h1>
    </a>
    <div class="header-operate">
        <a href="" class="search">搜索</a>
        <i></i>
        <a href="">
            <svg>
                <use xlink:href="#icon-person">
                    <svg id="icon-person" viewBox="0 0 16 16" width="100%" height="100%"><g><path d="M12 5a4 4 0 1 0-8 0 4 4 0 0 0 8 0zM3 5a5 5 0 1 1 10 0A5 5 0 0 1 3 5z"></path><path d="M8 9c-4.397 0-8 2.883-8 6.5a.5.5 0 1 0 1 0C1 12.49 4.113 10 8 10s7 2.49 7 5.5a.5.5 0 1 0 1 0C16 11.883 12.397 9 8 9z"></path></g></svg>
                </use>
            </svg>
        </a>
        <a href="">
            <svg>
                <use xlink:href="#icon-book">
                    <svg id="icon-book" viewBox="0 0 17 18" height="100%" width="100%"><g><path d="M1.01 1h1.98C3.549 1 4 1.445 4 2v14a1 1 0 0 1-1.01 1H1.01C.451 17 0 16.555 0 16V2a1 1 0 0 1 1.01-1zM1 16h1.99c.012 0 .01.002.01 0V2H1.01C.997 2 1 1.998 1 2v14zM6.01 1h1.98C8.549 1 9 1.445 9 2v14a1 1 0 0 1-1.01 1H6.01C5.451 17 5 16.555 5 16V2a1 1 0 0 1 1.01-1zM6 16h1.99c.012 0 .01.002.01 0V2H6.01C5.997 2 6 1.998 6 2v14zM10.77 1.253l1.957-.31a1.003 1.003 0 0 1 1.153.831L16.07 15.6a1 1 0 0 1-.84 1.147l-1.957.31a1.003 1.003 0 0 1-1.153-.831L9.93 2.4a1 1 0 0 1 .84-1.147zm2.337 14.816l1.966-.31c.012-.002-2.18-13.828-2.18-13.828l-1.966.31c-.012.002 2.18 13.828 2.18 13.828z"></path></g></svg>
                </use>
            </svg>
        </a>
    </div>
</header>
<div id="app">
    <nav class="home-nav">
        <a href="">
            <i class="sort"></i>
            <h4>分类</h4>
        </a>
        <a href="">
            <i class="rank"></i>
            <h4>排行</h4>
        </a>
        <a href="">
            <i class="free"></i>
            <h4>免费</h4>
        </a>
        <a href="">
            <i class="end"></i>
            <h4>完本</h4>
        </a>
    </nav>
    <div class="module">
        <div class="module-header">
            <div class="module-header-l">
                <h3>热门图书</h3>
                <span>编辑推荐</span>
            </div>
        </div>
        <div class="module-slide">
            <ol>
                <li v-for="item in hotBook">
                    <a :href="item.href">
                        <img :src="item.src" alt=""/>
                        <figcaption v-text="item.name"></figcaption>
                        <p v-text="item.author"></p>
                    </a>
                </li>
            </ol>
        </div>
    </div>
    <div class="module">
        <div class="module-header">
            <div class="module-header-l">
                <h3>排行榜</h3>
            </div>
            <div class="module-header-r">
                    <a href="">更多＞</a>
            </div>
        </div>
        <div class="module-tab">
            <nav>
                <h3 v-for="(item,index) in group" @click="change(index)">
                    <a href="javascript:" class="group" :class="{active:index===isActive}" v-text="item.name"></a>
                </h3>
            </nav>
        </div>
        <div class="module-slide">
            <ol v-for="(items,index) in group" :class="{active:index===isActive}">
                <li v-for="item in items.book">
                    <a :href="item.href">
                        <img :src="item.src" alt=""/>
                        <figcaption v-text="item.name"></figcaption>
                        <p v-text="item.author"></p>
                    </a>
                </li>
            </ol>
        </div>
    </div>
    <div class="module">
        <div class="module-header">
            <div class="module-header-l">
                <h3>新书上架</h3>
                <span>一周畅销新书</span>
            </div>
            <div class="module-header-r">
                    <a href="">更多＞</a>
            </div>
        </div>
        <ol>
            <li v-for="item in newBook" class="book-li">
                <a :href="item.href" class="book-lay">
                    <img :src="item.src" alt=""/>
                    <div class="info">
                        <h4 v-text="item.name"></h4>
                        <p v-text="item.profile"></p>
                        <div class="meta">
                            <div class="meta-l" v-text="item.author"></div>
                            <div class="meta-r" v-text="item.type"></div>
                        </div>
                    </div>
                </a>
            </li>
        </ol>
    </div>
    <div class="module">
        <div class="module-header">
            <div class="module-header-l">
                <h3>畅销完本</h3>
                <span>一周热销完本</span>
            </div>
            <div class="module-header-r">
                    <a href="">更多＞</a>
            </div>
        </div>
        <ol>
            <li v-for="item in endBook" class="book-li">
                <a :href="item.href" class="book-lay">
                    <img :src="item.src" alt=""/>
                    <div class="info">
                        <h4 v-text="item.name"></h4>
                        <p v-text="item.profile"></p>
                        <div class="meta">
                            <div class="meta-l" v-text="item.author"></div>
                            <div class="meta-r" v-text="item.type"></div>
                        </div>
                    </div>
                </a>
            </li>
        </ol>
    </div>
</div>
<footer>
        览书网copyright © 2017-2018   
</footer>
</body>
<script src="../../js/mIndex.js"></script>
</html>
