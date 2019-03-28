var star = Vue.component("star",{
	props:{
		starNum:Number
	},
    template: "#el"
});
var vm = new Vue({
	el:'#rrapp',
	data:{
		q:{
			roleName: null
		},
		showList: true,
		title:null,
		role:{},
        questionnaireList:[],
        singleQuestion:[],
        moreQuestion:[],
        completionQuestion:[],
        starQuestion:[],
		arrEnglish:[
			"A","B","C","D","E","F","G","H","I","J","K",
			"M","L","N","O","P","Q","R","S",
			"T","U","V","W","X","Y","Z"
		],
        questionObj:{
            questionnaireId:-1
		},
		test:[
			{id:"",title:"你一天不用手机你难受吗?",list:[
					{
						starNum:5,poll:200,totalPoll:1500
					},{
                        starNum:4,poll:300,totalPoll:1500
                    },{
                        starNum:3,poll:200,totalPoll:1500
                    },{
                        starNum:2,poll:400,totalPoll:1500
                    },{
                        starNum:1,poll:200,totalPoll:1500
                    }
			]},
            {id:"",title:"你对林志玲打几分?",list:[
                    {
                        starNum:5,poll:200,totalPoll:1500
                    },{
                        starNum:4,poll:300,totalPoll:1500
                    },{
                        starNum:3,poll:200,totalPoll:1500
                    },{
                        starNum:2,poll:400,totalPoll:1500
                    },{
                        starNum:1,poll:200,totalPoll:1500
                    }
                ]},
		]
	},
    components:{
		star:star
	},
	created:function(){
        this.getQuestionnire();
	},
    filters: {
        //保留2位小数点过滤器 不四舍五入
        number:function(value) {
        	console.log(value);
            var realVal;
        	if(Number.isNaN(value)){
                realVal = 0.00;
                return  realVal;
            }
            var toFixedNum = Number(value).toFixed(3);
            realVal = toFixedNum.substring(0, toFixedNum.toString().length - 1);
            return realVal;
        }
    },
    mounted:function(){
		this.$nextTick(function(){
            layui.use(['form','element'], function(){
                var form = layui.form,
                    element = layui.element;
                form.render();
                element.on('tab(filter)', function(data){
                    // console.log(this); //当前Tab标题所在的原始DOM元素
                    console.log(data.index); //得到当前Tab的所在下标
                    // console.log(data.elem); //得到当前的Tab大容器
					if(data.index == 1){
						vm.getMutipQuestionStatics();
					}else if(data.index == 2){
						vm.getCompletionQuestion();
					}else if(data.index == 3){
						vm.getStarQuestion();
                    }
                });


            });
		})

	},
	methods: {
        questionnaireSelect:function(){

            $.get(baseURL + "questionnaireStatistics/getSingleQuestionStatics?questionNaireId="+this.questionObj.questionnaireId+"&typeid=1", function (r) {
                if (r.code == 0000) {
                    console.log("我是单选题");
                	console.log(r.page.list);
					vm.singleQuestion = r.page.list;
                } else {
                    alert(r.msg);
                }
            });

        	console.log(this.questionObj.questionnaireId);
		},
        getMutipQuestionStatics:function(){

            $.get(baseURL + "questionnaireStatistics/getMutipQuestionStatics?questionNaireId="+this.questionObj.questionnaireId+"&typeid=2", function (r) {
                if (r.code == 0000) {
                    console.log("我是多选题");
                    console.log(r.page.list);
					vm.moreQuestion = r.page.list;
                } else {

                    alert(r.msg);
                }
            });

            console.log(this.questionObj.questionnaireId);
        },
        getCompletionQuestion:function(){

            $.get(baseURL + "questionnaireStatistics/getCompletionQuestionStatics?questionNaireId="+this.questionObj.questionnaireId+"&typeid=3", function (r) {
                if (r.code == 0000) {
                    console.log("我是填空题");
                    console.log(r.page.list);
                    vm.completionQuestion = r.page.list
                } else {

                    alert(r.msg);
                }
            });

            console.log(this.questionObj.questionnaireId);
        },
        getStarQuestion:function(){

            $.get(baseURL + "questionnaireStatistics/getStarQuestionStatics?questionNaireId="+this.questionObj.questionnaireId+"&typeid=4", function (r) {
                if (r.code == 0000) {
                    console.log("我是打分题");
                    console.log(r.page.list);
                    vm.starQuestion = r.page.list;
                } else {

                    alert(r.msg);
                }
            });

            console.log(this.questionObj.questionnaireId);
        },
        getQuestionnire: function () {
            $.get(baseURL + "sys/questionnaire/list/", function (r) {
                if (r.code == 0) {
                    r.page.list.push({"title":"请选择","objectId":-1});
                    vm.questionnaireList = r.page.list;
                } else {

                    alert(r.msg);
                }
            });
        },
		query: function () {
			vm.reload();
		},
		add: function(){
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
				    url: baseURL + "sys/role/delete",
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