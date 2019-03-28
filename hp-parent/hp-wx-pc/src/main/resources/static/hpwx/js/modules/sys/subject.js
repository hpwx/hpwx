var vm = new Vue({
    el: '#rrapp',
    data: {
        q: {
            name: null
        },
        baseURL: baseURL,
        showList: true,
        title: null,
        EditFalse:false,
        EditCheckBox:[],
        role: {},
        isRequiredRadioList: [
            {"value": 0, "text": "否", "checked": true},
            {"value": 1, "text": "是", "checked": false}
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
        answerSelect: [],
        checkboxSelect: []

    },
    mounted: function () {
        this.getQuestionnire();
        this.$nextTick(function () {

        layui.use(['upload', 'laydate', 'jquery'], function () {
            var $ = layui.jquery
                , upload = layui.upload,
                laydate = layui.laydate;

            //题目图片
            upload.render({
                elem: '#imgupload',
                url: baseURL + 'sys/questionnaire/upload',
                before: function (obj) {
                    //预读本地文件示例，不支持ie8
                    obj.preview(function (index, file, result) {
                        $("#editImguploadpreview1").hide();
                        $('#imguploadpreview1').attr('src', result).show(); //图片链接（base64）
                        var src = $('#imguploadpreview1')[0].src;
                    });
                },
                done: function (res) {
                    // vm.from.src = res.url;
                    // alert(res.url)
                    // $('#backuploadpreview').attr('src', res.url);
                    //如果上传失败
                    if (res.code > 0) {
                        return layer.msg('上传失败');
                    }
                    $("#imgupload1").val(res.url);
                    //上传成功
                },
                error: function () {
                    //演示失败状态，并实现重传
                    var demoText = $('#demoText');
                    demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
                    demoText.find('.demo-reload').on('click', function () {
                        uploadInst.upload();
                    });
                }
            });

            $("#jqGrid").jqGrid({
                url: baseURL + 'sys/subject/list',
                datatype: "json",
                colModel: [
                    {label: '题目编号', name: 'objectId', index: "object_id", width: 45, key: true},
                    {label: '题目名称', name: 'name', index: "name", width: 45,formatter:function (value, options, row) {
                            var content = decodeURIComponent(value);
                            var newContent= content.replace(/<img [^>]*src=['"]([^'"]+)[^>]*>/gi,function(match,capture){
                                //capture,返回每个匹配的字符串
                                //  console.log(capture);
                                console.log("-------------------------------")
                                // capture = "img/baidu_jgylogo3.gif";
                                var newStr='<img src="'+capture+'" alt="" style="width:200px;height: auto" />';
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

/*            UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;
            UE.Editor.prototype.getActionUrl = function (action) {
                if (action == 'uploadimage' || action == 'uploadscrawl' || action == 'uploadimage') {
                    return baseURL + '/sys/subject/imgUpload';
                    //'http://localhost:8080/imgUpload';为方法imgUpload的访问地址
                } else {
                    return this._bkGetActionUrl.call(this, action);
                }
            };

            //富文本编辑器
            var ue = UE.getEditor('editor',{
                initialFrameWidth :800,//设置编辑器宽度
                initialFrameHeight:250,//设置编辑器高度
                scaleEnabled:true//设置不自动调整高度
            });
            ue.ready(function () {
                ue.setHeight(200);
            })*/

        });

        })
    },
    methods: {

/*        isFocus: function (e) {
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
            // alert("已清空草稿箱")
        },*/

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
            console.log(vm.checkboxSelect);
            for(var i = 0;i<vm.checkboxSelect.length;i++){
                if(vm.checkboxSelect[i].item == item.item){
                    vm.checkboxSelect[i].state = item.state;
                }
            }
        },
        checkboxBlur: function(){
            if(vm.isRepeat(vm.questionObj.questionCheckboxItems)){
                alert("亲，多选题不能有重复答案吧!");
                return;
            }else{
                vm.checkboxSelect = [];
                for(var i = 0;i<this.questionObj.questionCheckboxItems.length;i++){
                    if(this.questionObj.questionCheckboxItems[i] != ""){
                        var obj = {};
                        obj.item = this.questionObj.questionCheckboxItems[i];
                        obj.state = false;
                        vm.checkboxSelect.push(obj);
                    }
                }
            }
        },
        editCheckBox: function (item) {
            console.log("==================editCheckBox===================");
            console.log(item.state);
            console.log("==================editCheckBox===================");
            console.log(vm.EditCheckBox);
        },
        addRadioInput: function () {
            this.questionObj.questionRadioItems.push('');
            this.answerSelect = this.questionObj.questionRadioItems;
        },
        addCheckboxInput: function () {
            if(vm.isRepeat(vm.questionObj.questionCheckboxItems)){
                alert("亲，多选题不能有重复答案吧!");
                return;
            }else{
                vm.checkboxSelect = [];
                this.questionObj.questionCheckboxItems.push('');
                for(var i = 0;i<this.questionObj.questionCheckboxItems.length;i++){
                    if(this.questionObj.questionCheckboxItems[i] != ""){
                        var obj = {};
                        obj.item = this.questionObj.questionCheckboxItems[i];
                        obj.state = false;
                        vm.checkboxSelect.push(obj);
                    }
                }
            }
        },
        delRadioInput: function () {
            this.answerSelect = this.questionObj.questionRadioItems.splice(this.questionObj.questionRadioItems.length - 1, 1);
            this.answerSelect = this.questionObj.questionRadioItems;
        },
        delCheckboxInput: function () {

            var delItem = this.questionObj.questionCheckboxItems.splice(this.questionObj.questionCheckboxItems.length - 1, 1);

            for(var i = 0;i<vm.checkboxSelect.length;i++){
                if(vm.checkboxSelect[i].item == delItem){
                    vm.checkboxSelect.splice(i,i)
                }
            }
        },
        add: function () {
            // alert(4);
            vm.showList = false;
            vm.EditFalse = false;
            vm.title = "新增";
            vm.role = {};
            vm.radioValue=1;
                vm.radioRequiredValue= 1;
                vm.showChooseFlag = true;
                vm.radioAndCheckBoxFlag = true;
                vm.starShowFlag = false;
                vm.questionObj = {
                    type: 1,
                    questionnaireId: -1, //问卷id
                    questionRadioItems: [''],
                    questionCheckboxItems: [''],
                    starNum: 5,
                    starScore: 1,
                    isRequired: 1,
                    inputAnswer: "", //填空答案
                    radioAnswer: "",  //单选答案
                    checkBoxAnswer: [], //多选答案
                    subjectName:"",     //问题名称
                    subjectId:""        //问题id
            };
            vm.answerSelect = [];
            // vm.EditCheckBox = [];
            vm.checkboxSelect = [];

            imguploadpreview1.style.display = "block";
            editImguploadpreview1.style.display = "none";

            // vm.setContent("");
        },
        update: function () {
            var objectId = getSelectedRow();
            if (objectId == null) {
                return;
            }
            vm.showList = false;
            vm.title = "新增";
            vm.role = {};
            vm.radioValue=1;
            vm.radioRequiredValue= 1;
            vm.showChooseFlag = true;
            vm.radioAndCheckBoxFlag = true;
            vm.starShowFlag = false;
            vm.questionObj = {
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
                subjectId:"",        //问题id
                nameImage:""         //题目图片
            };
            vm.answerSelect = [];
            vm.EditCheckBox = [];
            vm.showList = false;
            vm.title = "修改";

            $.get(baseURL + "sys/subject/info/"+objectId, function(r){
                if(r.code == 0){
                    var data = r.subject;
                    console.log("============题目===========")
                    console.log(data);
                    console.log("=============%%%%%==============")
                    vm.questionObj.isRequired = data.mustAnswer;
                    imgupload1.value = data.nameImage;
                    if(data.type == 3){
                        vm.radioAndCheckBoxFlag = false;
                    }else if(data.type == 4){
                        vm.radioAndCheckBoxFlag = false;
                        vm.starShowFlag = true;
                    }else if(data.type == 1){
                        vm.showChooseFlag = true;
                        vm.radioAndCheckBoxFlag = true;
                        vm.answerSelect = vm.questionObj.questionRadioItems;
                    }else if(data.type == 2){
                        vm.showChooseFlag = false;
                        vm.radioAndCheckBoxFlag = true;
                        vm.answerSelect = vm.questionObj.questionCheckboxItems;

                    }
                    vm.radioValue = data.type;

                    // vm.setContent(decodeURIComponent(data.subjectName));
                    imguploadpreview1.style.display = "none";
                    editImguploadpreview1.style.display = "block";
                    vm.questionObj.subjectName = data.subjectName;
                    vm.questionObj.nameImage = data.nameImage;
                    editImguploadpreview1.src = vm.questionObj.nameImage;


                    vm.$nextTick(function () {
                        for(var k in vm.questionObj){
                            for(var key in data){
                                if(k == key){
                                    if(key == "type" && (data[key] == 1)){
                                        vm.questionObj.questionCheckboxItems = data.questionCheckboxItems = [''];
                                        vm.questionObj.checkBoxAnswer = data.checkBoxAnswer = [];
                                        vm.answerSelect = data.questionRadioItems;
                                        vm.questionObj.type = data.type;
                                        vm.questionObj.inputAnswer = data.inputAnswer;
                                    }else if(key == "type" && (data[key] == 3 || data[key] == 4)){
                                        vm.questionObj.checkBoxAnswer = data.checkBoxAnswer = [];
                                        vm.questionObj.inputAnswer = data.inputAnswer = "";
                                        vm.questionObj.questionCheckboxItems = data.questionCheckboxItems = [''];
                                        vm.questionObj.questionRadioItems = data.questionRadioItems = [''];
                                        vm.questionObj.type = data.type;
                                    }else if(key == "type" && (data[key] == 2)){
                                        vm.questionObj.inputAnswer = data.inputAnswer = "";
                                        vm.questionObj.questionRadioItems = data.questionRadioItems = [''];
                                        vm.checkboxSelect = [];
                                        for(var j = 0;j < data.questionCheckboxItems.length;j++){
                                            var obj = {};
                                            obj.item = data.questionCheckboxItems[j];
                                            obj.state = false;
                                            vm.checkboxSelect.push(obj);
                                        }
                                        for(var i = 0;i<data.checkBoxAnswer.length;i++){
                                            for(var j = 0;j < vm.checkboxSelect.length;j++){
                                               if(data.checkBoxAnswer[i] == vm.checkboxSelect[j].item){
                                                   vm.checkboxSelect[j].state = true;
                                               }
                                            }
                                        }
                                        console.log("==================");
                                        vm.questionObj.type = data.type;
                                    }else{
                                        if(k != "checkBoxAnswer"){
                                            vm.questionObj[k] = data[key];
                                        }
                                    }
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
        isRepeat: function(arr){
        var hash = {};
        for(var i in arr) {
            if(hash[arr[i]])
                return true;
            hash[arr[i]] = true;

        }
        return false;
        },
        getByteLen: function (val) {
            var len = 0;
            for (var i = 0; i < val.length; i++) {
                if (val[i].match(/[^\x00-\xff]/ig) != null) //全角
                    len += 1;
                else
                    len += 1;
            }
            return len;
        },
        saveOrUpdate: function () {
            // var text = UE.getEditor('editor').getContent();
            if(vm.questionObj.subjectName == "" || vm.questionObj.subjectName == null){
                alert("请添加题目标题");
                return;
            }
            if(vm.getByteLen(vm.questionObj.subjectName) > 100){
                alert("题目标题字数超过100");
                return;
            }
            if(vm.questionObj.type == 4){
                if(!vm.questionObj.starNum){
                    alert("星星的数量填个呗！");
                    return;
                }
                if(vm.questionObj.starNum > 5){
                    alert("星星的数量太多啦！");
                    return;
                }
                if(vm.questionObj.starNum <= 0){
                    alert("星星个数不能小于1！");
                    return;
                }
                if(!vm.questionObj.starScore){
                    alert("星值填个呗！");
                    return;
                }

            }
            if(vm.questionObj.type == 1 && vm.isRepeat(vm.questionObj.questionRadioItems)){
                alert("亲，单选题不能有重复答案吧!");
                return;
            }
            if(vm.questionObj.type == 1 && vm.questionObj.radioAnswer == ""){
                alert("亲，至少得有个选择的答案吧!");
                return;
            }
            if(vm.questionObj.type == 2){
                if(vm.checkboxSelect.length == 0){
                    alert("亲，至少得有个选择的答案吧!");
                    return;
                }
                if(vm.isRepeat(vm.questionObj.questionCheckboxItems)){
                    alert("亲，不能有重复答案吧!");
                    return;
                }
                for(var i = 0;i<vm.questionObj.questionCheckboxItems.length;i++){
                    if(vm.questionObj.questionCheckboxItems[i]==''||vm.questionObj.questionCheckboxItems[i]==null||typeof(vm.questionObj.questionCheckboxItems[i])==undefined){
                        vm.questionObj.questionCheckboxItems.splice(i,1);
                        i=i-1;
                    }
                }
                var count = false;
                for(var i = 0;i<vm.checkboxSelect.length;i++){
                    if(vm.checkboxSelect[i].state){
                        count = true;
                        vm.questionObj.checkBoxAnswer.push(vm.checkboxSelect[i].item)
                    }
                }
                if(!count){
                    alert("亲，至少得有个选择的答案吧!");
                    return;
                }
            }

            // text = text.replace("<p>","").replace("</p>","").trim("");

            // vm.questionObj.subjectName = encodeURIComponent(text);

            vm.questionObj.nameImage = imgupload1.value;

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