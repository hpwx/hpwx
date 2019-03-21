var vm = new Vue({
    el: '#rrapp',
    data: {
        q: {
            name: null
        },
        baseURL: "http://localhost:5000/hpwxpc",
        showList: true,
        title: null,
        role: {},
        isRequiredRadioList: [
            {"value": 1, "text": "是", "checked": true},
            {"value": 2, "text": "否", "checked": false}
        ],
        radioList: [
            {"value": 1, "text": "单选", "checked": true},
            {"value": 2, "text": "多选", "checked": false},
            {"value": 3, "text": "填空", "checked": false},
            {"value": 4, "text": "打分", "checked": false}
        ],
        ueditor: null,
        radioValue: 1,
        radioRequiredValue: 1,
        showChooseFlag: true,
        radioAndCheckBoxFlag: true,
        starShowFlag: false,
        questionnaireList: [],
        questionObj: {
            type: 1,
            questionnaireId: -1, //问卷id
            questionRadioItems: [''],
            questionCheckboxItems: [''],
            starNum: 5,
            starScore: 5,
            isRequired: 1,
            inputAnswer: "", //填空答案
            radioAnswer: "",  //单选答案
            checkBoxAnswer: [], //多选答案
            subjectName:"",     //问题名称
            subjectId:""        //问题id
        },
        answerSelect: []
    },
    mounted: function () {
        this.getQuestionnire();
        this.$nextTick(function () {
            $("#jqGrid").jqGrid({
                url: baseURL + 'sys/subject/list',
                datatype: "json",
                colModel: [
                    {label: '题目编号', name: 'objectId', index: "object_id", width: 45, key: true},
                    {label: '题目名称', name: 'name', index: "name", width: 45,formatter:function (value, options, row) {
                         var content = decodeURIComponent(value);
                         var newContent= content.replace(/<img [^>]*src=['"]([^'"]+)[^>]*>/gi,function(match,capture){
                            //capture,返回每个匹配的字符串
                             console.log(capture);
                             console.log("-------------------------------")
                             capture = "img/baidu_jgylogo3.gif";
                            var newStr='<img src="http://www.baidu.com/'+capture+'" alt="" />';
                                return newStr;
                         });
                        return newContent;
                    }},
                    {
                        label: '题目类型',
                        name: 'typeId',
                        index: "type_id",
                        width: 45,
                        formatter: function (value, options, row) {
                            if (value == 1)
                                return '<span class="label label-danger">单选题</span>';
                            if (value == 2)
                                return '<span class="label label-success">多选题</span>';
                            if (value == 3)
                                return '<span class="label label-success">填空题</span>';
                            if (value == 4)
                                return '<span class="label label-success">打分题</span>';
                        }
                    },
                    {label: '创建时间', name: 'createTime', index: "create_time", width: 80}
                ],
                viewrecords: true,
                height: 385,
                rowNum: 10,
                rowList: [10, 30, 50],
                rownumbers: true,
                rownumWidth: 25,
                autowidth: true,
                multiselect: true,
                pager: "#jqGridPager",
                jsonReader: {
                    root: "page.list",
                    page: "page.currPage",
                    total: "page.totalPage",
                    records: "page.totalCount"
                },
                prmNames: {
                    page: "page",
                    rows: "limit",
                    order: "order"
                },
                gridComplete: function () {
                    //隐藏grid底部滚动条
                    $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
                }
            });

            UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;
            UE.Editor.prototype.getActionUrl = function (action) {
                if (action == 'uploadimage' || action == 'uploadscrawl' || action == 'uploadimage') {
                    return baseURL + '/sys/subject/imgUpload';
                    //'http://localhost:8080/imgUpload';为方法imgUpload的访问地址
                } else {
                    return this._bkGetActionUrl.call(this, action);
                }
            };

            //富文本编辑器
            var ue = UE.getEditor('editor');
            ue.ready(function () {
                ue.setHeight(200);
            })


        })
    },
    methods: {
        isFocus: function (e) {
            alert(UE.getEditor('editor').isFocus());
            UE.dom.domUtils.preventDefault(e)
        },
        setblur: function (e) {
            UE.getEditor('editor').blur();
            UE.dom.domUtils.preventDefault(e)
        },
        insertHtml: function () {
            var value = prompt('插入html代码', '');
            UE.getEditor('editor').execCommand('insertHtml', value)
        },
        createEditor: function () {
            enableBtn();
            UE.getEditor('editor');
        },
        getAllHtml: function () {
            alert(UE.getEditor('editor').getAllHtml())
        },
        getContent: function () {
            var arr = [];
            arr.push("使用editor.getContent()方法可以获得编辑器的内容");
            arr.push("内容为：");
            arr.push(UE.getEditor('editor').getContent());
            alert(arr.join("\n"));
            console.log(UE.getEditor('editor').getContent());
        },
        getPlainTxt: function () {
            var arr = [];
            arr.push("使用editor.getPlainTxt()方法可以获得编辑器的带格式的纯文本内容");
            arr.push("内容为：");
            arr.push(UE.getEditor('editor').getPlainTxt());
            alert(arr.join('\n'))
        },
        setContent: function (isAppendTo) {
            // var arr = [];
            // arr.push("使用editor.setContent('欢迎使用ueditor')方法可以设置编辑器的内容");
            UE.getEditor('editor').setContent(isAppendTo);
        },
        setDisabled: function () {
            UE.getEditor('editor').setDisabled('fullscreen');
            disableBtn("enable");
        },

        setEnabled: function () {
            UE.getEditor('editor').setEnabled();
            enableBtn();
        },

        getText: function () {
            //当你点击按钮时编辑区域已经失去了焦点，如果直接用getText将不会得到内容，所以要在选回来，然后取得内容
            var range = UE.getEditor('editor').selection.getRange();
            range.select();
            var txt = UE.getEditor('editor').selection.getText();
            alert(txt)
        },

        getContentTxt: function () {
            var arr = [];
            arr.push("使用editor.getContentTxt()方法可以获得编辑器的纯文本内容");
            arr.push("编辑器的纯文本内容为：");
            arr.push(UE.getEditor('editor').getContentTxt());
            alert(arr.join("\n"));
        },
        hasContent: function () {
            var arr = [];
            arr.push("使用editor.hasContents()方法判断编辑器里是否有内容");
            arr.push("判断结果为：");
            arr.push(UE.getEditor('editor').hasContents());
            alert(arr.join("\n"));
        },
        setFocus: function () {
            UE.getEditor('editor').focus();
        },
        deleteEditor: function () {
            disableBtn();
            UE.getEditor('editor').destroy();
        },
        disableBtn: function (str) {
            var div = document.getElementById('btns');
            var btns = UE.dom.domUtils.getElementsByTagName(div, "button");
            for (var i = 0, btn; btn = btns[i++];) {
                if (btn.id == str) {
                    UE.dom.domUtils.removeAttributes(btn, ["disabled"]);
                } else {
                    btn.setAttribute("disabled", "true");
                }
            }
        }, enableBtn: function () {
            var div = document.getElementById('btns');
            var btns = UE.dom.domUtils.getElementsByTagName(div, "button");
            for (var i = 0, btn; btn = btns[i++];) {
                UE.dom.domUtils.removeAttributes(btn, ["disabled"]);
            }
        },

        getLocalData: function () {
            alert(UE.getEditor('editor').execCommand("getlocaldata"));
        },

        clearLocalData: function () {
            UE.getEditor('editor').execCommand("clearlocaldata");
            alert("已清空草稿箱")
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
        test: function () {
            console.log(this.questionObj);
        },
        isRequiredFun: function (item) {
            this.questionObj.isRequired = item.value;
        },
        questionTypeCheck: function (item) {
            console.log(item.value);
            this.starShowFlag = false;
            this.answerSelect = [];
            if (item.value == 1) {
                this.showChooseFlag = true;
                this.radioAndCheckBoxFlag = true;
                this.answerSelect = this.questionObj.questionRadioItems;
            } else if (item.value == 2) {
                this.showChooseFlag = false;
                this.radioAndCheckBoxFlag = true;
                this.answerSelect = this.questionObj.questionCheckboxItems;
            } else if (item.value == 3) {
                this.radioAndCheckBoxFlag = false;
            } else if (item.value == 4) {
                this.radioAndCheckBoxFlag = false;
                this.starShowFlag = true;
            }
            this.questionObj.type = item.value;
        },
        answerCheckBox: function (item) {
            console.log(item);
            var idIndex = this.questionObj.checkBoxAnswer.indexOf(item);
            if (idIndex != -1) {
                this.questionObj.checkBoxAnswer.splice(idIndex, 1)
            } else {
                this.questionObj.checkBoxAnswer.push(item)
            }
            console.log("=============="+idIndex);
            console.log(this.questionObj.checkBoxAnswer);

        },
        addRadioInput: function () {
            this.questionObj.questionRadioItems.push('');
            this.answerSelect = this.questionObj.questionRadioItems;
        },
        addCheckboxInput: function () {
            this.questionObj.questionCheckboxItems.push('');
        },
        delRadioInput: function () {
            this.answerSelect = this.questionObj.questionRadioItems.splice(this.questionObj.questionRadioItems.length - 1, 1);
            this.answerSelect = this.questionObj.questionRadioItems;
        },
        delCheckboxInput: function () {
            this.answerSelect = this.questionObj.questionCheckboxItems.splice(this.questionObj.questionCheckboxItems.length - 1, 1);
            this.answerSelect = this.questionObj.questionCheckboxItems;
        },
        add: function () {
            // alert(4);
            vm.showList = false;
            vm.title = "新增";
            vm.role = {};
        },
        update: function () {
            var objectId = getSelectedRow();
            if (objectId == null) {
                return;
            }

            vm.showList = false;
            vm.title = "修改";

            $.get(baseURL + "sys/subject/info/"+objectId, function(r){
                if(r.code == 0){
                    var data = r.subject;
                    console.log(data);
                    vm.setContent(decodeURIComponent(data.subjectName));
                    vm.$nextTick(function () {
                        for(var k in vm.questionObj){
                            for(var key in data){
                                if(k == key){
                                    vm.questionObj[k] = data[key];
                                }
                            }
                        }
                    });
                }else{
                    alert(r.msg);
                }


            });

        },
        del: function () {
            var ids = getSelectedRows();
            if (ids == null) {
                return;
            }

            confirm('确定要删除选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "sys/subject/delete",
                    contentType: "application/json",
                    data: JSON.stringify(ids),
                    success: function (r) {
                        if (r.code == 0) {
                            alert('操作成功', function (index) {
                                vm.reload();
                            });
                        } else {
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        saveOrUpdate: function () {
            var text = UE.getEditor('editor').getContent();
            text = text.replace("<p>","").replace("</p>","").trim("");
            vm.questionObj.subjectName = encodeURIComponent(text);
            var url = vm.questionObj.subjectId == "" ? "sys/subject/add" : "sys/subject/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.questionObj),
                success: function (r) {
                    if (r.code === 0) {
                        alert('操作成功', function () {
                            vm.reload();
                        });
                    } else {
                        alert(r.msg);
                    }
                }
            });
        },
        reload: function () {
            vm.showList = true;
            var obj = {};
            if(!((vm.q.name) == null && (vm.questionObj.questionnaireId == -1))){
                obj = {'name':encodeURIComponent(vm.q.name) ,"questionnaireId":vm.questionObj.questionnaireId}
            }
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: obj,
                page: page
            }).trigger("reloadGrid");
        }
    }
});