<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title></title>
    <style>
        #canvas,#video {
            float: left;
            margin-right: 10px;
            background: #fff;
        }
        .box {
            overflow: hidden;
            margin-bottom: 10px;
        }
    </style>
    <script type="text/javascript" src="js/jquery.min.js"></script>
</head>
<body>
<div class="box">
    <video id="video" width="500" height="400"></video>
    <canvas id="canvas"></canvas>
</div>
<button id="live">打开摄像头</button>
<button id="snap">登录验证</button>
<script>
    var video = document.getElementById('video');
    var canvas = document.getElementById('canvas');
    var ctx = canvas.getContext('2d');
    var width = video.width;
    var height = video.height;
    canvas.width = width;
    canvas.height = height;
    function liveVideo(){
        var URL = window.URL || window.webkitURL;   // 获取到window.URL对象
        navigator.webkitGetUserMedia({
            video: true
        }, function(stream){
            video.src = URL.createObjectURL(stream);   // 将获取到的视频流对象转换为地址
            video.play();   // 播放
            //点击截图
            document.getElementById("snap").addEventListener('click', function() {
                ctx.drawImage(video, 0, 0, width, height);
                $("#canvas").hide();
                // Generate the image data（将Canvas的内容保存为图片借助toDataURL来实现） 方法返回一个包含图片展示的 data URI 。
                var Pic = canvas.toDataURL("image/jpg");
                //对其进行base64编 之后的字符串
                Pic = Pic.replace(/^data:image\/(png|jpg);base64,/,"")
                // Sending the image data to Server
                $.ajax({
                    type: 'POST',
                    url:  "faceLogin.do",
                    dataType:'json',
                    data: { "imageData" : Pic},
                    success: function (json) {
                        console.log(json);
                        // alert("人脸识别结果:"+msg);
                        //eval把字符串转换成json对象
                        /*  var json = eval("(" + msg + ")"); */
                        var code= parseInt(json.error_code);
                        if(code==0){
                            var sc=parseFloat(json.result.user_list[0].score);
                            //console.log(sc);
                            if(sc>=70){
                                alert("人脸识别成功!");
                            }else{
                                alert("人脸识别失败!");
                            }
                        }else{
                            alert("人脸识别失败!");
                        }
                    }
                });
            });
        }, function(error){
            console.log(error.name || error);
        });
    }
    document.getElementById("live").addEventListener('click',function(){
        liveVideo();
    });
</script>
</body>
</html>
