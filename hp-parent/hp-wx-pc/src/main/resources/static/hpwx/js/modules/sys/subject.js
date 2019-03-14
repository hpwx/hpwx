$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/subject/list',
        datatype: "json",
        colModel: [
            { label: '题目编号', name: 'objectId', index: "object_id", width: 45, key: true },
			{ label: '题目名称', name: 'name', index: "name", width: 45 },
            { label: '题目类型', name: 'typeId', index: "type_id", width: 45,formatter: function(value, options, row){
                    if(value == 1)
                       return '<span class="label label-danger">单选题</span>';
                    if(value == 2)
                       return '<span class="label label-success">多选题</span>';
                    if(value == 3)
                       return  '<span class="label label-success">填空题</span>';
                    if(value == 4)
                       return '<span class="label label-success">问答题</span>';
                } },
			{ label: '创建时间', name: 'createTime', index: "create_time", width: 80}
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

    //富文本编辑器
    var ue = UE.getEditor('editor');


    function isFocus(e){
        alert(UE.getEditor('editor').isFocus());
        UE.dom.domUtils.preventDefault(e)
    }
    function setblur(e){
        UE.getEditor('editor').blur();
        UE.dom.domUtils.preventDefault(e)
    }
    function insertHtml() {
        var value = prompt('插入html代码', '');
        UE.getEditor('editor').execCommand('insertHtml', value)
    }
    function createEditor() {
        enableBtn();
        UE.getEditor('editor');
    }
    function getAllHtml() {
        alert(UE.getEditor('editor').getAllHtml())
    }
    function getContent() {
        var arr = [];
        arr.push("使用editor.getContent()方法可以获得编辑器的内容");
        arr.push("内容为：");
        arr.push(UE.getEditor('editor').getContent());
        alert(arr.join("\n"));
    }
    function getPlainTxt() {
        var arr = [];
        arr.push("使用editor.getPlainTxt()方法可以获得编辑器的带格式的纯文本内容");
        arr.push("内容为：");
        arr.push(UE.getEditor('editor').getPlainTxt());
        alert(arr.join('\n'))
    }
    function setContent(isAppendTo) {
        var arr = [];
        arr.push("使用editor.setContent('欢迎使用ueditor')方法可以设置编辑器的内容");
        UE.getEditor('editor').setContent('欢迎使用ueditor', isAppendTo);
        alert(arr.join("\n"));
    }
    function setDisabled() {
        UE.getEditor('editor').setDisabled('fullscreen');
        disableBtn("enable");
    }

    function setEnabled() {
        UE.getEditor('editor').setEnabled();
        enableBtn();
    }

    function getText() {
        //当你点击按钮时编辑区域已经失去了焦点，如果直接用getText将不会得到内容，所以要在选回来，然后取得内容
        var range = UE.getEditor('editor').selection.getRange();
        range.select();
        var txt = UE.getEditor('editor').selection.getText();
        alert(txt)
    }

    function getContentTxt() {
        var arr = [];
        arr.push("使用editor.getContentTxt()方法可以获得编辑器的纯文本内容");
        arr.push("编辑器的纯文本内容为：");
        arr.push(UE.getEditor('editor').getContentTxt());
        alert(arr.join("\n"));
    }
    function hasContent() {
        var arr = [];
        arr.push("使用editor.hasContents()方法判断编辑器里是否有内容");
        arr.push("判断结果为：");
        arr.push(UE.getEditor('editor').hasContents());
        alert(arr.join("\n"));
    }
    function setFocus() {
        UE.getEditor('editor').focus();
    }
    function deleteEditor() {
        disableBtn();
        UE.getEditor('editor').destroy();
    }
    function disableBtn(str) {
        var div = document.getElementById('btns');
        var btns = UE.dom.domUtils.getElementsByTagName(div, "button");
        for (var i = 0, btn; btn = btns[i++];) {
            if (btn.id == str) {
                UE.dom.domUtils.removeAttributes(btn, ["disabled"]);
            } else {
                btn.setAttribute("disabled", "true");
            }
        }
    }
    function enableBtn() {
        var div = document.getElementById('btns');
        var btns = UE.dom.domUtils.getElementsByTagName(div, "button");
        for (var i = 0, btn; btn = btns[i++];) {
            UE.dom.domUtils.removeAttributes(btn, ["disabled"]);
        }
    }

    function getLocalData () {
        alert(UE.getEditor('editor').execCommand( "getlocaldata" ));
    }

    function clearLocalData () {
        UE.getEditor('editor').execCommand( "clearlocaldata" );
        alert("已清空草稿箱")
    }

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
		role:{},
        isRequiredRadioList: [
            {"value":1,"text":"是","checked":true},
            {"value":2,"text":"否","checked":false}
        ],
        radioList: [
            {"value":1,"text":"单选","checked":true},
            {"value":2,"text":"多选","checked":false},
            {"value":3,"text":"填空","checked":false},
            {"value":4,"text":"打分","checked":false}
        ],
        radioValue: 1,
        radioRequiredValue: 1,
        showChooseFlag:true,
        radioAndCheckBoxFlag: true,
        starShowFlag: false,
        questionObj: {
		    type:1,
            questionnaire:-1, //问卷id
            questionRadioItems:[''],
            questionCheckboxItems:[''],
            starNum: 5,
            starScore:5,
            isRequired:1,
            inputAnswer: "", //填空答案
            radioAnswer: "",  //单选答案
            checkBoxAnswer:[] //多选答案
        },
        answerSelect: []
	},
	methods: {
		query: function () {
			vm.reload();
		},
        test:function () {
            console.log(this.questionObj);
        },
        isRequiredFun:function (item) {
            this.questionObj.isRequired = item.value;
        },
        questionTypeCheck: function (item) {
		    console.log(item.value);
            this.starShowFlag = false;
            this.answerSelect = [];
		    if(item.value == 1){
                this.showChooseFlag = true;
                this.radioAndCheckBoxFlag = true;
                this.answerSelect  = this.questionObj.questionRadioItems;
            }else if(item.value == 2){
                this.showChooseFlag = false;
                this.radioAndCheckBoxFlag = true;
                this.answerSelect  = this.questionObj.questionCheckboxItems;
            }else if(item.value == 3){
		        this.radioAndCheckBoxFlag = false;
            }else if(item.value == 4){
                this.radioAndCheckBoxFlag = false;
                this.starShowFlag = true;
            }
            this.questionObj.type = item.value;
        },
        answerCheckBox: function (item) {
            console.log(item);
        },
        addRadioInput:function () {
            this.questionObj.questionRadioItems.push('');
            this.answerSelect  = this.questionObj.questionRadioItems;
        },
        addCheckboxInput:function () {
            this.questionObj.questionCheckboxItems.push('');
        },
        delRadioInput:function () {
            this.answerSelect = this.questionObj.questionRadioItems.splice(this.questionObj.questionRadioItems.length -1,1);
            this.answerSelect  = this.questionObj.questionRadioItems;
        },
        delCheckboxInput:function () {
            this.answerSelect = this.questionObj.questionCheckboxItems.splice(this.questionObj.questionCheckboxItems.length -1,1);
            this.answerSelect  = this.questionObj.questionCheckboxItems;
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