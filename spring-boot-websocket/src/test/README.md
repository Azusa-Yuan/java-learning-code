# 测试
## 使用浏览器测试websocket
1. 启动项目
2. 打开浏览器开发者控制台
3. 执行以下代码 (根据需要自行选择要执行的语句)
```javascript
// 创建连接
var socket = new WebSocket("ws://localhost:8182/ws/");
// 连接成功
socket.onopen = function () {
    console.log("连接成功");
    socket.send("hello");
};
// 收到消息
socket.onmessage = function (msg) {
    console.log("收到消息：" + msg.data);
};
// 连接关闭
socket.onclose = function () {
    console.log("连接关闭");
};
```