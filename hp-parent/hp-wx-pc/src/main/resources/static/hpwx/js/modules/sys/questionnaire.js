var vm = new Vue({
	el:'#rrapp',
	data:{
		q:{
            title: null
		},
		showList: true,
		title:null,
		role:{},
        baseURL:baseURL,
        questionnaire:{
            objectId:"",    //问卷id
            title:"",       //问卷标题
            answerCount:"",         //问卷数量
            questionnaireDesc:"", //封面描述
            endImageDesc:"",      //尾页描述
            forward:1,  //是否转发,
            repeatedAnswer:0,   //是否重复答题
            questionnaireIsTop:0,     //是否置顶
            questionnaireStyleStatus:0, //题目翻转样式
            isPublic:1,   //是否公开
            anonymous:0,  //是否匿名
            endIsShow:1, //尾页是否显示
            coverIsShow:1, //封面是否显示
            enable:0,   //问卷是否启用  0否1是
            questionnaireTime:"",         //活动时间
            icon:"",               //问卷图片
            cover:"",               //问卷封面图片
            backColor:"",                //题目背景图片,
            endImage:"",            //尾页图片
            startTime:"",           //活动开始时间
            endTime:""              //活动结束时间
        }
	},
    mounted: function () {
        this.$nextTick(function () {
            console.log(vm.questionnaire.enable);
            console.log(vm.questionnaire.endIsShow);
            layui.use(['upload','laydate','jquery'], function(){
                var $ = layui.jquery
                    ,upload = layui.upload,
                    laydate = layui.laydate
                ;

                $("#jqGrid").jqGrid({
                    url: baseURL + 'sys/questionnaire/list',
                    datatype: "json",
                    colModel: [
                        { label: '问卷编号', name: 'objectId', index: "objectId", width: 45, key: true },
                        { label: '问卷标题', name: 'title', index: "title", width: 45 ,search: true},
                        { label: '问卷是否重复作答',sortable:false, name: 'repeatedAnswer', width: 45 ,formatter: function(value, options, row){
                                return value === 0 ?
                                    '<span class="label label-danger">否</span>' :
                                    '<span class="label label-success">是</span>';
                            }},
                        { label: '问卷是否转发',sortable:false, name: 'forward', index: "forward", width: 45,formatter: function(value, options, row){
                                return value === 0 ?
                                    '<span class="label label-danger">否</span>' :
                                    '<span class="label label-success">是</span>';
                            } },
                        { label: '问卷是否置顶',sortable:false, name: 'questionnaireIsTop', index: "questionnaireIsTop", width: 45,formatter: function(value, options, row){
                                return value === 0 ?
                                    '<span class="label label-danger">是</span>' :
                                    '<span class="label label-success">否</span>';
                            } },
                        { label: '创建时间', name: 'createTime', index: "create_time", width: 80},
                        { label: '开始时间', name: 'startTime', index: "start_time", width: 80},
                        { label: '截止时间', name: 'endTime', index: "end_time", width: 80},
                        { label: '操作', sortable:false, name: 'caozuo', index: "caozuo", width: 140,formatter:function(value,options,row){
                                if(row.enable == 1){
                                    if(row.questionnaireIsTop == 1){
                                        return value === undefined ?
                                            '<span class="label label-warning tableBtnStyle" id="editBtn">编辑</span> ' +
                                            '<span class="label label-success tableBtnStyle" id="DownBtn">取消置顶</span> ' +
                                            '<span class="label label-success tableBtnStyle" id="EnBtn">启用</span> ' +
                                            '<span class="label label-danger tableBtnStyle" id="delBtn">删除</span> ' : ''
                                    }else{
                                        return value === undefined ?
                                            '<span class="label label-warning tableBtnStyle" id="editBtn">编辑</span> ' +
                                            '<span class="label label-success tableBtnStyle" id="TopBtn">置顶</span> ' +
                                            '<span class="label label-success tableBtnStyle" id="EnBtn">启用</span> ' +
                                            '<span class="label label-danger tableBtnStyle" id="delBtn">删除</span> ' : ''
                                    }
                                }else if(row.enable == 0){
                                    if(row.questionnaireIsTop == 1){
                                        return value === undefined ?
                                            '<span class="label label-warning tableBtnStyle" id="editBtn">编辑</span> ' +
                                            '<span class="label label-success tableBtnStyle" id="DownBtn">取消置顶</span> ' +
                                            '<span class="label label-info tableBtnStyle" id="UnEnBtn">未启用</span> ' +
                                            '<span class="label label-danger tableBtnStyle" id="delBtn">删除</span> ' : ''
                                    }else{
                                        return value === undefined ?
                                            '<span class="label label-warning tableBtnStyle" id="editBtn">编辑</span> ' +
                                            '<span class="label label-success tableBtnStyle" id="TopBtn">置顶</span> ' +
                                            '<span class="label label-info tableBtnStyle" id="UnEnBtn">未启用</span> ' +
                                            '<span class="label label-danger tableBtnStyle" id="delBtn">删除</span> ' : ''
                                    }
                                }

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
                $("#jqGrid").on('click',"#editBtn",function(event){
                    event.stopPropagation();
                    vm.update();
                });

                $("#jqGrid").on("click","#TopBtn",function (event) {
                    event.stopPropagation();
                    vm.isTop();
                });

                $("#jqGrid").on("click","#DownBtn",function (event) {
                    event.stopPropagation();
                    vm.isDown();
                });

                $("#jqGrid").on("click","#EnBtn",function (event) {
                    event.stopPropagation();
                    vm.enable();
                });

                $("#jqGrid").on("click","#UnEnBtn",function (event) {
                    event.stopPropagation();
                    vm.UnEnable();
                });

                $("#jqGrid").on("click","#delBtn",function (event) {
                    event.stopPropagation();
                    vm.del();
                });
                //问卷开始时间
                laydate.render({
                    elem: '#startTime', //指定元素
                    type:'datetime',
                    format:"yyyy-MM-dd HH:mm:ss",
                    range:"~",
                    zIndex: 99999999,
                    done: function(value, date){
                        // console.log(value); //得到日期生成的值，如：2017-08-18
                        // startTime = value;
                        $("#startTime").val(value);
                        // console.log("==========="+startTime);
                        // console.log(typeof startTime);
                        // console.log(date); //得到日期时间对象：{year: 2017, month: 8, date: 18, hours: 0, minutes: 0, seconds: 0}
                    },
                    change: function(value, date, endDate){
                        // startTime = value;
                        // console.log(value); //得到日期生成的值，如：2017-08-18
                        // console.log(date); //得到日期时间对象：{year: 2017, month: 8, date: 18, hours: 0, minutes: 0, seconds: 0}

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
                    elem: '#imgupload',
                    url: baseURL+'sys/questionnaire/upload',
                    before: function(obj){
                        //预读本地文件示例，不支持ie8
                        obj.preview(function(index, file, result){
                            $('#imguploadpreview1').attr('src',result); //图片链接（base64）
                            var src = $('#imguploadpreview1')[0].src;
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
                        $("#imgupload1").val(res.url);
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
                    elem: '#backupload',
                    url: baseURL+'sys/questionnaire/upload',
                    before: function(obj){
                        //预读本地文件示例，不支持ie8
                        obj.preview(function(index, file, result){
                            $('#imguploadpreview2').attr('src', result); //图片链接（base64）
                            var src_img = $('#imguploadpreview2')[0].src;
                        });
                    },
                    done: function(res){
                        //如果上传失败
                        if(res.code > 0){
                            return layer.msg('上传失败');
                        }
                        $("#imgupload2").val(res.url);
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
                            $('#imguploadpreview3').attr('src',result); //图片链接（base64）
                            var src = $('#imguploadpreview3')[0].src;
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
                        $("#imgupload3").val(res.url);
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

                //尾页图片
                upload.render({
                    elem: '#endImgUpload',
                    url: baseURL+'sys/questionnaire/upload',
                    before: function(obj){
                        //预读本地文件示例，不支持ie8
                        obj.preview(function(index, file, result){
                            $('#imguploadpreview4').attr('src',result); //图片链接（base64）
                            var src = $('#imguploadpreview4')[0].src;
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
                        $("#imgupload4").val(res.url);
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

            });

        })
    },
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			// alert(4);
			vm.showList = false;
			vm.title = "新增";
			vm.questionnaire = {
                objectId:"",    //问卷id
                    title:"",       //问卷标题
                    answerCount:"",         //问卷数量
                    questionnaireDesc:"", //问卷描述
                    endImageDesc:"",      //尾页描述
                    forward:1,  //是否转发,
                    repeatedAnswer:0,   //是否重复答题
                    questionnaireIsTop:0,     //是否置顶
                    questionnaireStyleStatus:0, //题目翻转样式
                    isPublic:1,   //是否公开
                    anonymous:0,  //是否匿名
                    endIsShow:0, //尾页是否显示
                    coverIsShow:0, //封面是否显示
                    enable:0,   //问卷是否启用  0否1是
                    questionnaireTime:"",         //活动时间
                    icon:"",               //问卷图片
                    cover:"",               //问卷封面图片
                    backColor:"",                //题目背景图片,
                    endImage:"",
                    startTime:"",           //活动开始时间
                    endTime:""              //活动结束时间
            };
            // imguploadpreview1.src = "";
            // imguploadpreview2.src = "";
            // imguploadpreview3.src = "";
		},
		update: function () {
			var roleId = getSelectedRow();
			if(roleId == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";

            $.get(baseURL + "sys/questionnaire/info/"+roleId, function(r){

                if(r.code == 0){
                    var data = r.menu;
                    console.log(data);
                    console.log(vm.questionnaire);
                    for(var k in vm.questionnaire){
                        for(var key in data){
                            if(k == key){
                                vm.questionnaire[k] = data[key];
                            }
                        }
                    }
                    if(!(vm.questionnaire.startTime== "" && vm.questionnaire.endTime == "")){
                        vm.time = vm.questionnaire.startTime + "~" + vm.questionnaire.endTime;
                    }else{
                        vm.time = "";
                    }
                    startTime.value = vm.time;
                    imguploadpreview1.src = vm.baseURL + vm.questionnaire.icon;
                    imguploadpreview2.src = vm.baseURL + vm.questionnaire.cover;
                    imguploadpreview3.src = vm.baseURL + vm.questionnaire.backColor;
                    imguploadpreview4.src = vm.baseURL + vm.questionnaire.endImage;
                }else{

                    alert(r.msg);
                }


            });

            // vm.getMenuTree(roleId);
		},

        isTop:function () {
            var roleId = getSelectedRow();
            if(roleId == null){
                return ;
            }
            confirm('确定要置顶选中的记录？', function(){
                $.ajax({
                    type: "POST",
                    url: baseURL + "sys/questionnaire/top",
                    contentType: "application/json",
                    data: JSON.stringify(roleId),
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
        isDown:function () {
            var roleId = getSelectedRow();
            if(roleId == null){
                return ;
            }
            confirm('确定要取消置顶选中的记录？', function(){
                $.ajax({
                    type: "POST",
                    url: baseURL + "sys/questionnaire/down",
                    contentType: "application/json",
                    data: JSON.stringify(roleId),
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
        enable:function(){
            var id = getSelectedRow();
            if(id == null){
                return ;
            }
            var url = "sys/questionnaire/enableOrUnEnable";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/x-www-form-urlencoded",
                data:  {
                    questionnaireId: id,
                    enable:"down"
                },
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
        UnEnable:function(){
            var id = getSelectedRow();
            if(id == null){
                return ;
            }
            var obj = {
                questionnaireId: id,
                enable:"up"
            };
            var url = "sys/questionnaire/enableOrUnEnable";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/x-www-form-urlencoded",
                data: obj,
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
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "sys/questionnaire/del",
                    contentType: "application/json",
				    data: JSON.stringify(ids),
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
        saveOrUpdateQuestionnaire:function () {
            this.questionnaire.questionnaireTime = startTime.value;
            this.questionnaire.icon = imgupload1.value;
            this.questionnaire.cover = imgupload2.value;
            this.questionnaire.backColor = imgupload3.value;
            this.questionnaire.endImage = imgupload4.value;
            for(var key in this.questionnaire){
                if(this.questionnaire[key] && (typeof this.questionnaire[key] == 'string') && key != "questionnaireTime"){
                    this.questionnaire[key] = this.questionnaire[key].trim("g");
                }
            }
            var arr = this.questionnaire.questionnaireTime.split("~");
            this.questionnaire.startTime = arr[0];
            this.questionnaire.endTime = arr[1];
            var url = (this.questionnaire.objectId == "") ? "sys/questionnaire/add" : "sys/questionnaire/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(this.questionnaire),
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
		    console.log(JSON.stringify(this.questionnaire))
        },
	    reload: function () {
	    	vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                postData:{'title': vm.q.title},
                page:page
            }).trigger("reloadGrid");
		},
        validator: function () {
            if(isBlank(vm.q.title)){
                alert("标题不能为空");
                return true;
            }
        }
	}
});