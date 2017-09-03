Ext.require(['Ext.grid.*', 'Ext.data.*', 'Ext.form.*', 'Ext.layout.container.Column', 'Ext.tab.Panel']);
Ext.Loader.setConfig({
    enabled: true
});

var dataminingColumns1=[
         			{
         				header : 'Iteration Number',
         				dataIndex : 'iterationNo',
         				sortable:false,
         				width:265
         			},
         			 {
         				header : 'Time Algo1',
         				dataIndex : 'previousTime',
         				sortable:true,
         				width    :258
         			},
					{
         				header : 'Time Algo2',
         				dataIndex : 'proposedTime',
         				sortable:true,
         				width    :258
         			}
         	    		];


var hideConfirmationMsg;
var showConfirmationMsg;
/* Hide the Confirmation Message */
	hideConfirmationMsg = function() {
		var confMsgDiv = Ext.get('confirmationMessage');
		confMsgDiv.dom.innerHTML = "";
		confMsgDiv.dom.style.display = 'none';
	}
	/* Show Confirmation Message */
	showConfirmationMsg = function(msg) {
		var confMsgDiv = Ext.get('confirmationMessage');
		confMsgDiv.dom.innerHTML =  msg;
		confMsgDiv.dom.style.display = 'inline-block';		
	}
	
Ext.onReady(function () {
   Ext.define('dataminingCompareModel', {
				extend : 'Ext.data.Model',
				fields : [ 
				           {name:'iterationNo', mapping:'iterationNo',type:'int'},
				           {name:'previousTime', mapping:'previousTime',type:'double'},
						   {name:'proposedTime', mapping:'proposedTime',type:'double'}
						  ],
				idProperty: 'iterationNo'
			});
			
	  
	
	var dataminingCompareStoreAlgo1Store = Ext.create('Ext.data.Store', {
				id : 'dataminingCompareStoreId1',
				name : 'dataminingCompareStoreName1',
				model : 'dataminingCompareModel',
				proxy : {
					type : 'ajax',
					url :contextPath+'/review/comparision.do',
					actionMethods:{
						read:'POST'
					},
					reader : {
						type :'json',
						root:'model',
						totalProperty:'totalSize'
					}
				},
				listeners:
				{
					'load':function(store, records){
				}			
				},
				autoLoad : true
			});
	dataminingCompareStoreAlgo1Store.load();
	
	
	
	

var dataminingGridPanelAlgo2 = Ext.create('Ext.grid.Panel', {
collapsible:true,
title:'Time Comparison in ms',
forceFit : true,
id : 'topologyGrid2',
store : dataminingCompareStoreAlgo1Store,
columns :dataminingColumns1,
maxHeight : 300,
width : 400,
autoFit : true,
stripRows:true,
renderTo : 'dataminingGridPanelAlgo2Container'
});

});
	
	
	
