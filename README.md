# WebViewDownloadPic
Android WebView 上面的图片的两种下载方式

![gif](https://github.com/stormdzh/WebViewDownloadPic/blob/master/pre/device-2017-09-29-110253.gif)

第一种效果图：
![Paste_Image.png](https://github.com/stormdzh/WebViewDownloadPic/blob/master/pre/device-2017-09-29-111635.png)


第二种是模仿网易新闻的效果，在webview上点击图片后，先预览大图，之后可以下载。
![Paste_Image.png](https://github.com/stormdzh/WebViewDownloadPic/blob/master/pre/device-2017-09-29-111720.png)


第一种方式相对简单：
使用registerForContextMenu（view）

第二种方式是利用js注入，相对复杂点，作为一个android开发别的可能都能理解，这里就介绍下js注入获取webview图片的数组和给单张图片设置点击事件的原理吧。

我的demo上的实现是：把所有的图片地址拿到后，拼接成一个字符串返回：
  webView.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName(\"img\");" +
                "var imgs=\"\";" +
                "for(var i=0;i<objs.length;i++){" +
                "imgs=imgs+objs[i].src+\";\";" +
                "objs[i].onclick=function(){" +
                "window.imagelistener.clickImage(this.src);" +
                "}" +
                "}" +
                "window.imagelistener.getImageUrl(imgs);" +
                "})()");
				
这个地方还可以根据自己的需求，没发现一个地址，就传送给android，代码如下：
  webView.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName(\"img\");" +
                "for(var i=0;i<objs.length;i++){" +
                "window.imagelistener.getImageUrl(objs[i].src);" +
                "objs[i].onclick=function(){" +
                "window.imagelistener.clickImage(this.src);" +
                "}" +
                "}" +
                "window.imagelistener.getImageUrl(imgs);" +
                "})()");
				
当然你也可以自己随意些js。end！！！



