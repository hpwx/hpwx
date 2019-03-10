$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/oss/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', width: 20, key: true },
            { label: 'URL地址', name: 'url', width: 160 },
			{ label: '创建时间', name: 'createDate', width: 40 }
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

    layui.use('upload', function(){
        var $ = layui.jquery
        ,upload = layui.upload;
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

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
        config: {},
        // form:{
        //     title:'',
        //     questionnaireDesc:'',
        //     anonymous:'',
        //     forward:'',
        //     repeatedAnswer:'',
        //     cover:'',
        //     src:'',
        //     imgs:''
        // },
	},
    created: function(){
        this.getConfig();
    },
	methods: {
		query: function () {
			vm.reload();
		},
		getConfig: function () {
            $.getJSON(baseURL + "sys/oss/config", function(r){
				vm.config = r.config;
            });
        },
		// addConfig: function(){
            // layer.open({
			// 	type: 1,
			// 	skin: 'layui-layer-molv',
			// 	title: "新建问卷",
			// 	area: ['700px', '388px'],
			// 	shadeClose: false,
			// 	content: jQuery("#addquest"),
			// 	btn: ['保存','取消'],
			// 	btn1: function (index) {
			// 		$.ajax({
			// 			type: "POST",
			// 		    url: baseURL + "sys/questionnaire/add",
			// 		    dataType: "json",
             //            contentType: "application/json",
             //            data: JSON.stringify(vm.form),
			// 		    success: function(r){
			// 				if(r.code == 0){
			// 					layer.close(index);
			// 					layer.alert('修改成功', function(){
			// 						location.reload();
			// 					});
			// 				}else{
			// 					layer.alert(r.msg);
			// 				}
			// 			}
			// 		});
	         //    }
			// });
		// },
		saveOrUpdate: function () {
			var url = baseURL + "sys/oss/saveConfig";
			$.ajax({
				type: "POST",
			    url: url,
                contentType: "application/json",
			    data: JSON.stringify(vm.config),
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
        del: function () {
            var ossIds = getSelectedRows();
            if(ossIds == null){
                return ;
            }

            confirm('确定要删除选中的记录？', function(){
                $.ajax({
                    type: "POST",
                    url: baseURL + "sys/oss/delete",
                    contentType: "application/json",
                    data: JSON.stringify(ossIds),
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
            });
        },
		reload: function () {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page
            }).trigger("reloadGrid");
		}
	}
});