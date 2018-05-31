$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'test/raffleprizeusersavevalue/list',
        datatype: "json",
        colModel: [			
			{ label: '', name: 'raffleprizeuserid', index: 'rafflePrizeUserId', width: 80 }, 			
			{ label: '', name: 'propertykeys', index: 'propertyKeys', width: 80 }, 			
			{ label: '', name: 'propertykeyvalue', index: 'propertyKeyValue', width: 80 }, 			
			{ label: '', name: 'remark', index: 'remark', width: 80 }, 			
			{ label: '', name: 'sortsid', index: 'sortsId', width: 80 }, 			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '', name: 'createdat', index: 'createdAt', width: 80 }, 			
			{ label: '', name: 'updatedat', index: 'updatedAt', width: 80 }, 			
			{ label: '', name: 'version', index: 'VERSION', width: 80 }, 			
			{ label: '', name: 'prizetype', index: 'prizeType', width: 80 }			
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
		rafflePrizeUserSaveValue: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.rafflePrizeUserSaveValue = {};
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
			var url = vm.rafflePrizeUserSaveValue.id == null ? "test/raffleprizeusersavevalue/save" : "test/raffleprizeusersavevalue/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.rafflePrizeUserSaveValue),
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
				    url: baseURL + "test/raffleprizeusersavevalue/delete",
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
			$.get(baseURL + "test/raffleprizeusersavevalue/info/"+id, function(r){
                vm.rafflePrizeUserSaveValue = r.rafflePrizeUserSaveValue;
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