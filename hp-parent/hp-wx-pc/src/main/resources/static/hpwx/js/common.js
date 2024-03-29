//jqGrid的配置信息
$.jgrid.defaults.width = 1000;
$.jgrid.defaults.responsive = true;
$.jgrid.defaults.styleUI = 'Bootstrap';


//var baseURL = "https://120.27.147.104:8010/hpwxapitest/";
//var baseURL = "http://localhost:8001/hpwxpc/";
//工具集合Tools
window.T = {};

// 获取请求参数
// 使用示例
// location.href = http://localhost/index.html?id=123
// T.p('id') --> 123;
var url = function(name) {
	var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if(r!=null)return  unescape(r[2]); return null;
};
T.p = url;

//请求前缀
 //var baseURL = "/renren-fast/";
 //var baseURL = "http://localhost:8010/renren-fast/";

//登录token
var token = localStorage.getItem("token");
if(token == 'null'){
	
	console.info('重定向。。。。。');
    //parent.location.href = baseURL+'hpwx/login.html';
	console.info ('登录页：'+  baseURL+'hpwx/login.html');
	window.location.href = baseURL+'hpwx/login.html';
	
	
}

//jquery全局配置
$.ajaxSetup({
	dataType: "json",
	cache: false,
    headers: {
        "token": token
    },
    xhrFields: {
	    withCredentials: true
    },
    complete: function(xhr) {
        //token过期，则跳转到登录页面
        if(xhr.responseJSON == null || xhr.responseJSON.code == 401){
        	
//            parent.location.href = 'login.html';
        	
        	console.info('重定向3333。。。。。');
            //parent.location.href = baseURL+'hpwx/login.html';
        	
        	console.info ('登录页：'+  baseURL+'hpwx/login.html');
        	window.location.href = baseURL+'hpwx/login.html';
        }
    }
});

//jqgrid全局配置
$.extend($.jgrid.defaults, {
    ajaxGridOptions : {
        headers: {
            "token": token
        }
    }
});

//权限判断
function hasPermission(permission) {
    if (window.parent.permissions.indexOf(permission) > -1) {
        return true;
    } else {
        return false;
    }
}
/**
 * 是否去除所有空格
 * @param str
 * @param is_global 如果为g或者G去除所有的
 * @returns
 */
String.prototype.trim = function (is_global) {
    var result;
    result = this.replace(/(^\s+)|(\s+$)/g,"");
    if(is_global == "g")
    {
        result = result.replace(/\s/g,"");
    }
    return result;

};
//重写alert
window.alert = function(msg, callback){
	parent.layer.alert(msg, function(index){
		parent.layer.close(index);
		if(typeof(callback) === "function"){
			callback("ok");
		}
	});
}
//重写confirm式样框
window.confirm = function(msg, callback){
	parent.layer.confirm(msg, {btn: ['确定','取消']},
	function(){//确定事件
		if(typeof(callback) === "function"){
			callback("ok");
		}
	});
}
//选择一条记录
function getSelectedRow() {
    var grid = $("#jqGrid");
    var rowKey = grid.getGridParam("selrow");
    if(!rowKey){
    	alert("请选择一条记录");
    	return ;
    }
    
    var selectedIDs = grid.getGridParam("selarrrow");
    if(selectedIDs.length > 1){
    	alert("只能选择一条记录");
    	return ;
    }
    
    return selectedIDs[0];
}

//选择多条记录
function getSelectedRows() {
    var grid = $("#jqGrid");
    var rowKey = grid.getGridParam("selrow");
    if(!rowKey){
    	alert("请选择一条记录");
    	return ;
    }
    
    return grid.getGridParam("selarrrow");
}

//判断是否为空
function isBlank(value) {
    return !value || !/\S/.test(value)
}

function getUUID() {
	var s = [];
	var hexDigits = "0123456789abcdef";
	for (var i = 0; i < 36; i++) {
		s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
	}
	s[14] = "4"; 
	s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1); 
														
	s[8] = s[13] = s[18] = s[23] = "-";

	var uuid = s.join("");
	return uuid;
}