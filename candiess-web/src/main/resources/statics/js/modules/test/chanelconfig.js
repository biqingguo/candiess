$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'test/chanelconfig/list',
        datatype: "json",
        colModel: [			
			{ label: '', name: 'chanel', index: 'chanel', width: 80 }, 			
			{ label: '', name: 'status', index: 'status', width: 80 }, 			
			{ label: '', name: 'userid', index: 'userID', width: 80 }, 			
			{ label: '', name: 'remark', index: 'remark', width: 80 }, 			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '', name: 'createdat', index: 'createdAt', width: 80 }, 			
			{ label: '', name: 'updatedat', index: 'updatedAt', width: 80 }, 			
			{ label: '', name: 'version', index: 'version', width: 80 }			
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
    jQuery("#jqGrid").jqGrid('navGrid', '#jqGridPager', {
		del : false,
		add : false,
		edit : false
	}, {}, {}, {}, {
		multipleSearch : true
	})
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		chanelConfig: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.chanelConfig = {};
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(id)
		},
		saveOrUpdate: function (event) {
			var url = vm.chanelConfig.id == null ? "test/chanelconfig/save" : "test/chanelconfig/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.chanelConfig),
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功', function(index){
							vm.reload();
						});
					}else{
						alert(r.msg);
					}
				}
			});
		},
		del: function (event) {
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "test/chanelconfig/delete",
                    contentType: "application/json",
				    data: JSON.stringify(ids),
				    success: function(r){
						if(r.code == 0){
							alert('操作成功', function(index){
								$("#jqGrid").trigger("reloadGrid");
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		getInfo: function(id){
			$.get(baseURL + "test/chanelconfig/info/"+id, function(r){
                vm.chanelConfig = r.chanelConfig;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page
            }).trigger("reloadGrid");
		}
	}
});