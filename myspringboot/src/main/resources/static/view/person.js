$(function () {

    initTable();

})




function initTable() {

    $('#table1')
        .bootstrapTable(
            {
                method : 'post',// 请求方式（*）
                url : '/user/list',// 请求后台的URL（*）
                cache : false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
                sortable : false, // 是否启用排序
                contentType: 'application/x-www-form-urlencoded',
                pagination : true,// 是否显示分页（*）
                queryParamsType: "limit",//查询参数组织方式
                pageSize : 10, // 每页的记录行数（*）
                pageList : [ 5, 10, 15 ], // 可供选择的每页的行数（*）
                search:true,//搜索框
                pageNumber:1,
                striped : true, // 表格显示条纹
                silent: true,
                strictSearch:true,//
                showColumns:true,// 是否显示所有的列
                showRefresh:true,//刷新
                showToggle : true, // 是否显示详细视图和列表视图的切换按钮
                cardView : false, // 是否显示详细视图
                detailView : false, // 是否显示父子表
                toolbar : '#toolbar',
                clickToSelect : true,
                sidePagination : 'server',
                queryParams : function(params){
                    console.info(params.offset);
                    return params;
                },
                columns : [
                    {field : 'stat',
                        width : 2,
                        checkbox : true
                    },
                    {
                        field : 'id',
                        title : 'id',
                        align : 'left',
                        valign : 'middle',
                        width : 200,
                        sortable : true,
                        visible:false
                    }
                    ,
                    {
                        field : 'username',
                        title : '姓名',
                        align : 'left',
                        valign : 'middle',
                        width : 200,
                        sortable : true
                    }
                    , {
                        field : 'password',
                        title : '密码',
                        align : 'left',
                        valign : 'middle',
                        width : 50,
                        sortable : true
                    }],
                onClickRow : function(row, $element){
                    //console.info(row);
                },
                onDblClickRow : function(row, $element){//双击
                    //console.info(row);
                },
                onLoadSuccess: function(data){  //加载成功时执行
                    console.info(JSON.stringify(data));
                    console.info("加载成功");
                },
                onLoadError: function(){  //加载失败时执行
                    console.info("加载数据失败");
                }
            })

}


//刷新事件
function refreshTable(){
    $('#table1').bootstrapTable('refresh');
}

//使用bootstrap model模块add
function submitData(){

    var data = {};
    var dataArray = $('#addForm').serializeArray();
    if(dataArray.length>0){
        for(i=0;i<dataArray.length;i++){
            var input = dataArray[i];
            var ele = $("input[name='"+input.name+"']");
            if(ele.prop("disabled")!=true){
                data[input.name] = input.value;
            }
        }
    }
    $.ajax({
        type : 'post',
        url : '/user/save',
        dataType:'json',
        data : data,
        success : function(result) {
            console.info(result);
            $('#myModal').modal('hide');
        }
    })
}

//删除
function del(ids) {
    if (!ids) {
        var ids='';
        var selected = $('#table1').bootstrapTable('getSelections');
        if(selected.length>0){
            for (i = 0; i < selected.length; i++) {
                ids=ids+selected[i].id+',';
            }
        }
    }
    confirmDelete(ids)
}
function confirmDelete(ids){
    $.ajax({
        type : 'post',
        url : '/user/delte?ids='+ids,
        success : function(result) {
            $('#delModel').modal('hide');
            refreshTable();
        }
    })
}

//给Model获取值
function getModelData() {
    var selected = $('#table1').bootstrapTable('getSelections');
    var id=selected[0].id;
    $.ajax({
        type : 'post',
        url : '/user/userById?id='+id,
        dataType:'json',

        success : function(result) {
            // console.info(result);
            // console.info(result.username)
            $("#upid").val(result.id);
            $("#upusername").val(result.username);
            $("#uppassword").val(result.password);
        }
    })
}






//使用bootstrap model模块update
function updateUser(){

    var data = {};
    var dataArray = $('#updateForm').serializeArray();
    if(dataArray.length>0){
        for(i=0;i<dataArray.length;i++){
            var input = dataArray[i];
            var ele = $("input[name='"+input.name+"']");
            if(ele.prop("disabled")!=true){
                data[input.name] = input.value;
            }
        }
    }
    $.ajax({
        type : 'post',
        url : '/user/update',
        dataType:'json',
        data : data,
        success : function(result) {
            console.info(result);
            $('#updateModel').modal('hide');
            refreshTable();
        }
    })
}