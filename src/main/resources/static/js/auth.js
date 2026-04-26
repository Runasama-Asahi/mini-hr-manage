/**
 * 认证相关JavaScript
 * 用于处理JWT令牌和AJAX拦截器
 */

// 添加AJAX拦截器，自动在请求中添加JWT令牌
$(document).ajaxSend(function(event, jqxhr, settings) {
    var token = localStorage.getItem('token') || sessionStorage.getItem('token');
    if (token && settings.url !== '/login') {
        jqxhr.setRequestHeader('Authorization', 'Bearer ' + token);
    }
});

// 添加全局AJAX错误处理
$(document).ajaxError(function(event, jqxhr, settings, error) {
    if (jqxhr.status === 401) {
        layui.use('layer', function(){
            var layer = layui.layer;
            layer.msg('登录已过期，请重新登录', {icon: 2}, function() {
                localStorage.removeItem('token');
                sessionStorage.removeItem('token');
                location.href = '/login';
            });
        });
    } else if (jqxhr.status === 403) {
        layui.use('layer', function(){
            var layer = layui.layer;
            layer.msg('权限不足', {icon: 2});
        });
    }
});
