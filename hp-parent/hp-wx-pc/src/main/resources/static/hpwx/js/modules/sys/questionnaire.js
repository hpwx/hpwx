$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/questionnaire/list',
        datatype: "json",
        colModel: [
            { label: '问卷编号', name: 'objectId', index: "objectId", width: 45, key: true },
			{ label: '问卷标题', name: 'title', index: "title", width: 45 },
			{ label: '问卷是否重复作答', name: 'repeatedAnswer', width: 75 ,formatter: function(value, options, row){
                    return value === 0 ?
                        '<span class="label label-danger">否</span>' :
                        '<span class="label label-success">是</span>';
                }},
			{ label: '问卷是否转发', name: 'forward', index: "forward", width: 75,formatter: function(value, options, row){
                    return value === 0 ?
                        '<span class="label label-danger">否</span>' :
                        '<span class="label label-success">是</span>';
                } },
			{ label: '问卷是否置顶', name: 'top', index: "top", width: 75,formatter: function(value, options, row){
                    return value === 0 ?
                        '<span class="label label-danger">否</span>' :
                        '<span class="label label-success">是</span>';
                } },
			{ label: '创建时间', name: 'createTime', index: "create_time", width: 80},
            { label: '截止时间', name: 'endTime', index: "create_time", width: 80},
            { label: '操作', name: 'caozuo', index: "caozuo", width: 100,formatter:function(value,options,row){
                    return value === undefined ?
                        '<span class="label label-warning tableBtnStyle" id="editBtn">编辑</span> ' +
                        '<span class="label label-success tableBtnStyle">修改</span> ' +
                        '<span class="label label-success tableBtnStyle">置顶</span> ' +
                        '<span class="label label-danger tableBtnStyle">删除</span> ' : ''
            }
            }
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
    $("#jqGrid").on('click',"#editBtn",function(){
        alert("sssss")
    });

    var startTime,endTime;

    layui.use(['upload','laydate'], function(){
        var $ = layui.jquery
            ,upload = layui.upload,
            laydate = layui.laydate
        ;

        //问卷开始时间
        laydate.render({
            elem: '#startTime', //指定元素
            type:'datetime',
            format:"yyyy-MM-dd HH:mm:ss",
            range:"-",
            zIndex: 99999999,
            done: function(value, date){
                console.log(value); //得到日期生成的值，如：2017-08-18
                startTime = value;
                console.log(date); //得到日期时间对象：{year: 2017, month: 8, date: 18, hours: 0, minutes: 0, seconds: 0}
            },
            change: function(value, date, endDate){
                startTime = value;
            console.log(value); //得到日期生成的值，如：2017-08-18
            console.log(date); //得到日期时间对象：{year: 2017, month: 8, date: 18, hours: 0, minutes: 0, seconds: 0}

        }
        });

        lay('#endTime').on('click', function(e){
            if(!startTime){
                alert("请先选择开始时间");
                return false;
            }
            //问卷截止时间
            laydate.render({
                elem: '#endTime', //指定元素
                type:'datetime',
                format:"yyyy-MM-dd HH:mm:ss",
                zIndex: 99999999,
                show: true,
                min:startTime,
                closeStop: '#endTime',
                done: function(value, date){
                    endTime = value;
                    console.log(value); //得到日期生成的值，如：2017-08-18
                    console.log(date); //得到日期时间对象：{year: 2017, month: 8, date: 18, hours: 0, minutes: 0, seconds: 0}
                },
                change: function(value, date, endDate){
                    endTime = value;
                    console.log(value); //得到日期生成的值，如：2017-08-18
                    console.log(date); //得到日期时间对象：{year: 2017, month: 8, date: 18, hours: 0, minutes: 0, seconds: 0}
                    console.log(endDate); //得结束的日期时间对象，开启范围选择（range: true）才会返回。对象成员同上。
                }
            });
        });

        //问卷背景
        upload.render({
            elem: '#backupload',
            url: baseURL+'sys/questionnaire/upload',
            before: function(obj){
                //预读本地文件示例，不支持ie8
                obj.preview(function(index, file, result){
                    $('#backuploadpreview').attr('src',result); //图片链接（base64）
                    var src = $('#backuploadpreview')[0].src;
                    console.log(src);
                });
            },
            done: function(res){
                // vm.from.src = res.url;
                // alert(res.url)
                // $('#backuploadpreview').attr('src', res.url);
                //如果上传失败
                if(res.code > 0){
                    return layer.msg('上传失败');
                }
                //上传成功
            },
            error: function(){
                //演示失败状态，并实现重传
                var demoText = $('#demoText');
                demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
                demoText.find('.demo-reload').on('click', function(){
                    uploadInst.upload();
                });
            }
        });
        //问卷封面
        upload.render({
            elem: '#imgupload',
            url: baseURL+'sys/questionnaire/upload',
            before: function(obj){
                //预读本地文件示例，不支持ie8
                obj.preview(function(index, file, result){
                    $('#imguploadpreview').attr('src', result); //图片链接（base64）
                    var src_img = $('#imguploadpreview')[0].src;
                });
            },
            done: function(res){
                //如果上传失败
                if(res.code > 0){
                    return layer.msg('上传失败');
                }
                //上传成功
            },
            error: function(){
                //演示失败状态，并实现重传
            }
        });

        //题目背景
        upload.render({
            elem: '#questionImgUpload',
            url: baseURL+'sys/questionnaire/upload',
            before: function(obj){
                //预读本地文件示例，不支持ie8
                obj.preview(function(index, file, result){
                    $('#imguploadpreview').attr('src',result); //图片链接（base64）
                    var src = $('#imguploadpreview')[0].src;
                    console.log(src);
                });
            },
            done: function(res){
                // vm.from.src = res.url;
                // alert(res.url)
                // $('#backuploadpreview').attr('src', res.url);
                //如果上传失败
                if(res.code > 0){
                    return layer.msg('上传失败');
                }
                //上传成功
            },
            error: function(){
                //演示失败状态，并实现重传
                var demoText = $('#demoText');
                demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
                demoText.find('.demo-reload').on('click', function(){
                    uploadInst.upload();
                });
            }
        });

        var obj = new Object();
        obj.title = $('#biaoti').text();
        obj.content = $('#content').val();
        obj.names = $('input[name="names"]:checked').val();
        obj.support = $('input[name="support"]:checked').val();
        obj.prohibit = $('input[name="prohibit"]:checked').val();
        obj.src = $('#backuploadpreview')[0].src;
        obj.src_img = $('#imguploadpreview')[0].src;
        $('#addConfig').click(function () {
            layer.open({
                type: 1,
                skin: 'layui-layer-molv',
                title: "新建问卷",
                area: ['700px', '388px'],
                shadeClose: false,
                content: jQuery("#addquest"),
                btn: ['保存','取消'],
                btn1: function (index) {
                    console.log(obj);
                    $.ajax({
                        type: "POST",
                        url: baseURL + "sys/questionnaire/add",
                        dataType: "json",
                        contentType: "application/json",
                        data: JSON.stringify(obj),
                        success: function(r){
                            if(r.code == 0){
                                layer.close(index);
                                layer.alert('修改成功', function(){
                                    location.reload();
                                });
                            }else{
                                layer.alert(r.msg);
                            }
                        }
                    });
                }
            });
        })


    });
});

var setting = {
	data: {
		simpleData: {
			enable: true,
			idKey: "menuId",
			pIdKey: "parentId",
			rootPId: -1
		},
		key: {
			url:"nourl"
		}
	},
	check:{
		enable:true,
		nocheckInherit:true
	}
};
var ztree;
	
var vm = new Vue({
	el:'#rrapp',
	data:{
		q:{
			roleName: null
		},
		showList: true,
		title:null,
		role:{}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			// alert(4);
			vm.showList = false;
			vm.title = "新增";
			vm.role = {};
			vm.getMenuTree(null);
		},
		update: function () {
			var roleId = getSelectedRow();
			if(roleId == null){
				return ;
			}
			
			vm.showList = false;
            vm.title = "修改";
            vm.getMenuTree(roleId);
		},
		del: function () {
			var roleIds = getSelectedRows();
			if(roleIds == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "sys/questionnaire/delete",
                    contentType: "application/json",
				    data: JSON.stringify(roleIds),
				    success: function(r){
						if(r.code == 0){
							alert('操作成功', function(index){
								vm.reload();
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		getRole: function(roleId){
            $.get(baseURL + "sys/role/info/"+roleId, function(r){
            	vm.role = r.role;
                
                //勾选角色所拥有的菜单
    			var menuIds = vm.role.menuIdList;
    			for(var i=0; i<menuIds.length; i++) {
    				var node = ztree.getNodeByParam("menuId", menuIds[i]);
    				ztree.checkNode(node, true, false);
    			}
    		});
		},
		saveOrUpdate: function () {
            if(vm.validator()){
                return ;
            }

			//获取选择的菜单
			var nodes = ztree.getCheckedNodes(true);
			var menuIdList = new Array();
			for(var i=0; i<nodes.length; i++) {
				menuIdList.push(nodes[i].menuId);
			}
			vm.role.menuIdList = menuIdList;
			
			var url = vm.role.roleId == null ? "sys/role/save" : "sys/role/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.role),
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功', function(){
							vm.reload();
						});
					}else{
						alert(r.msg);
					}
				}
			});
		},
		getMenuTree: function(roleId) {
			//加载菜单树
			$.get(baseURL + "sys/menu/list", function(r){
				ztree = $.fn.zTree.init($("#menuTree"), setting, r);
				//展开所有节点
				ztree.expandAll(true);
				
				if(roleId != null){
					vm.getRole(roleId);
				}
			});
	    },
	    reload: function () {
	    	vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                postData:{'roleName': vm.q.roleName},
                page:page
            }).trigger("reloadGrid");
		},
        validator: function () {
            if(isBlank(vm.role.roleName)){
                alert("角色名不能为空");
                return true;
            }
        }
	}
});