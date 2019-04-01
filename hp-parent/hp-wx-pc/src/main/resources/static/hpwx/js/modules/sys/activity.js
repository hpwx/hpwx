var vm = new Vue({
	el:'#rrapp',
	data:{
		q:{
            name: null
		},
		showList: true,
		title:null,
		role:{},
        baseURL:baseURL,
        activityinfo:{
            objectId:"",    //问卷id
            name:"",       //问卷标题
            context:"",     //问卷数量
            backimage:"",     // 背景图
            activeTime:"",
            startTime:"",     //活动开始时间
            endTime:""      //活动结束时间
        }
	},
    mounted: function () {
        this.$nextTick(function () {
           // console.log(vm.questionnaire.enable);
            layui.use(['upload','laydate','jquery'], function(){
                var $ = layui.jquery
                    ,upload = layui.upload,
                    laydate = layui.laydate
                ;
                $("#jqGrid").jqGrid({
                    url: baseURL + 'sys/activity/getActivityList',
                    datatype: "json",
                    colModel: [
                        { label: '活动ID', name: 'objectId', index: "objectId", width: 45, key: true },
                        { label: '活动标题', name: 'name', index: "name", width: 45 ,search: true},
                        { label: '活动背景图', name: 'backimage', index: "backimage", width: 45 ,search: true, 
                        function(value, options, row){
                            if (value!=''){
                            return "<img src='"+value+"' width='100px' height='100px' />";
                            }else{
                                return '';
                            }

                        }},
                        { label: '活动是否启用',sortable:false, name: 'status', width: 75 ,formatter: function(value, options, row){
                                return value === 0 ?
                                    '<span class="label label-danger">否</span>' :
                                    '<span class="label label-success">是</span>';
                            }},
                        { label: '开始时间', name: 'startTime', index: "start_time", width: 80},
                        { label: '结束时间', name: 'endTime', index: "end_time", width: 80},
                        { label: '操作', sortable:false, name: 'caozuo', index: "caozuo", width: 100,formatter:function(value,options,row){
                            var  str="";
                            if (row.status == 1) {
                                str=  '<span class="label label-success tableBtnStyle" id="UnEnBtn"     style="width:72px;"   >已启用</span> ';

                            }else {
                                str=  '<span class="label label-success tableBtnStyle" id="EnBtn"   style="width:72px;"  >点击启用</span> ';
                            }
                            
                            
                            return value === undefined ?
                                      str +
                                    '<span class="label label-warning tableBtnStyle" id="editBtn"   style="width:72px;"  >编辑</span> ' +
                                    '<span class="label label-danger tableBtnStyle" id="delBtn"   style="width:72px;"  >删除</span> ' : ''
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

               

                $("#jqGrid").on("click","#delBtn",function (event) {
                    event.stopPropagation();
                    vm.del();
                    vm.reload();
                });


             

                  //启用
                $("#jqGrid").on("click", "#EnBtn", function (event) {
                    event.stopPropagation();
                    vm.isstart();
                });

                //不启用
                $("#jqGrid").on("click", "#UnEnBtn", function (event) {
                    event.stopPropagation();
                    vm.isstop();
                });


                //问卷开始时间
                laydate.render({
                    elem: '#activedate', //指定元素
                    type:'datetime',
                    format:"yyyy-MM-dd HH:mm:ss",
                    range:"~",
                    zIndex: 99999999,
                    done: function(value, date){
                        // console.log(value); //得到日期生成的值，如：2017-08-18
                        // startTime = value;
                        $("#activedate").val(value);
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

                lay('#activedate').on('click', function(e){
                    // if($('#activedate').val()==""){
                    //     alert("请先选择开始时间");
                     
                    //     return false;
                    // }
                    //问卷截止时间
                    laydate.render({
                        elem: '#activedate', //指定元素
                        type:'datetime',
                        format:"yyyy-MM-dd HH:mm:ss",
                        zIndex: 99999999,
                        show: true,
                        range:"~",
                        closeStop: '#activedate',
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

                //  背景上传  
                upload.render({
                    elem: '#imgupload',
                    url: baseURL+'sys/activity/upload',
                    before: function(obj){
                        //预读本地文件示例，不支持ie8
                        obj.preview(function(index, file, result){
                            $('#imguploadpreview1').attr('src',result); //图片链接（base64）
                            var src = $('#imguploadpreview1')[0].src;
                        });
                    },
                    done: function(res){
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
                  

           


            });

        })
    },
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
            // alert(4);
            
            debugger;
			vm.showList = false;
			vm.title = "新增";
			vm.activityinfo = {};
          
		},
		update: function() {

            debugger;
            var id = getSelectedRow();
            
            debugger;
			if(id == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            $.get(baseURL + "sys/activity/getActivityById?id="+id, function(r){
                console.info( '获取单单条数据：'+ r);
                if(r.code == 0){
                    vm.activityinfo.name=r.data.name;
                   vm .activityinfo.context=r.data.context;
                   vm.activityinfo.status=r.data.status;
                   vm.activityinfo.activedate = r.data.startTime + "~" + r.data.endTime;
                 //  vm.activityinfo.activedate.value = vm.time;
                  imguploadpreview1.src= r.data.backimage; 
                }else{
                    alert(r.msg);
                }
            });

            // vm.getMenuTree(roleId);
		},
           
        //  活动开启 
        isstart:function () {
            var roleId = getSelectedRow();
            if(roleId == null){
                return ;
            }

            confirm('确定要设置开启选中的记录？', function(){
                $.ajax({
                    type: "POST",
                    url: baseURL + "sys/activity/changeStatus?id="+roleId+"&status=1",
                    contentType: "application/json",
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
       
        // 暂停
        isstop:function(){

            var roleId = getSelectedRow();
            if(roleId == null){
                return ;
            }

            confirm('确定要设置关闭选中的记录？', function(){
                $.ajax({
                    type: "POST",
                    url: baseURL + "sys/activity/changeStatus?id="+roleId+"&status=0",
                    contentType: "application/json",
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
		del: function () {
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "sys/activity/deleteBatch",
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
        
		saveOrUpdateActivity:function() {
                  
            var reg = /[^\d]/g;
            if (!this.activityinfo.name) {
                alert("请填写活动名称！");
                return;
            }
            if (!this.activityinfo.context) {
                alert("请填写活动内容！");
                return;
            }


            var val=$('input:radio[name="status"]:checked').val();
            if(val==null){
                alert("请选择是否停止！");
                return ;
            }
              

              if ($('#activedate').val()==''){
                alert("请填写活动日期！");
                return;
              }

            this.activityinfo.objectId=null;
            this.activityinfo.status=val;
            this.activityinfo.name=$('#name').val();
            this.activityinfo.context=$('#context').val();
            this.activityinfo.startTime=$('#activedate').val().split('~')[0];           
            this.activityinfo.endTime=$('#activedate').val().split('~')[1];
            this.activityinfo.backimage= imgupload1.value;

            console.info( this.activityinfo);
            var url = (this.activityinfo.objectId == null) ? "sys/activity/save" : "sys/activity/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(this.activityinfo),
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


        // 取消上传  
        canelUpload:function(){
           
        alert('cancel upload!');
        $('#imguploadpreview1').removeAttr('src');
        $('#imguploadpreview1').attr('src','');//图片链接（base64）
        },
	    reload: function () {
	    	vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam','page');
            
           var searchtext=  vm.q.name==null?"".replace(/^\s*|\s*$/g,""): vm.q.name .replace(/^\s*|\s*$/g,"");
			$("#jqGrid").jqGrid('setGridParam',{ 
                postData:{'name': searchtext},
                page:page
            }).trigger("reloadGrid");
		},
        validator: function () {
            if(isBlank(vm.q.name)){
                alert("活动名称不能为空");
                return true;
            }
        }
	}
});