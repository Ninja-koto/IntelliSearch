Ext.require(['Ext.grid.*', 'Ext.data.*', 'Ext.form.*', 'Ext.layout.container.Column', 'Ext.tab.Panel']);
Ext.Loader.setConfig({
    enabled: true
});
Ext.tip.QuickTipManager.init();



var webColumns=[
         			{
         				header : 'Article Name Main',
         				dataIndex : 'articleName',
         				sortable:true,
         				width:150
         			},
         			{
         				header : 'Compared Article Name',
         				dataIndex : 'articleNameMain',
         				sortable:true,
         				width    :150
         			},{
						header : 'User Id',
         				dataIndex : 'userId',
         				sortable:true,
         				width    :100
					},
					{
						header : 'Email',
         				dataIndex : 'email',
         				sortable:true,
         				width    :200
					},
					{
						header : 'RVCoeffcient',
         				dataIndex : 'rvCoeffcient',
         				sortable:true,
         				width    :100
					},
					{
						header : 'Threshold',
         				dataIndex : 'threshold',
         				sortable:true,
         				width    :100
					},
					{
						header : 'Similarity',
         				dataIndex : 'similarity',
         				sortable:true,
         				width    :100
					},
					{
						header : 'GroupID',
         				dataIndex : 'groupId',
         				sortable:true,
         				width    :100
					},
					{
						header : 'Article Link1',
         				dataIndex : 'articleIdParent',
         				sortable:true,
         				width    :300,
         				renderer : function(value, metadata, record, rowIndex, colIndex, store) {
         					
         					return "<a  target='_blank'" + "href=" + contextPath
 							+ "/review/download.do?articleName=" + value
 							+ ">"+record.get('articleName')+"</a>";
								
         				}
					},{
						header : 'Article Link2',
         				dataIndex : 'articleIdChild',
         				sortable:true,
         				width    :300,
         				renderer : function(value, metadata, record, rowIndex, colIndex, store) {
         					
         					return "<a  target='_blank'" + "href=" + contextPath
 							+ "/review/download.do?articleName=" + value
 							+ ">"+record.get('articleNameMain')+"</a>";
								
         				}
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
	var webSiteStore;
Ext.onReady(function () {

	var loadMask = new Ext.LoadMask(Ext.getBody(), {msg:"Loading"});
	loadMask.show();
	
	Ext.define('webModel',{
		extend : 'Ext.data.Model',
		fields : [ 
				   {name:'similarity',mapping:'similarity',type:'boolean'},
				   {name:'threshold',mapping:'threshold',type:'double'},
				   {name:'groupId', mapping:'groupId',type:'int'},
				   {name:'articleIdParent', mapping:'articleIdParent',type:'int'},
				   {name:'articleIdChild', mapping:'articleIdChild',type:'int'},
		           {name:'similarity',mapping:'similarity',type:'boolean'},
				   {name:'rvCoeffcient',mapping:'rvCoeffcient',type:'double'},
				   {name:'articleName',mapping:'articleName',type:'string'},
				   {name:'articleNameMain',mapping:'articleNameMain',type:'string'},
				   {name:'userId',mapping:'userId',type:'string'},
				   {name:'email',mapping:'email',type:'string'}
			      ]
	});

	webStore = Ext.create('Ext.data.Store', {
		id : 'webSiteStoreId',
		name : 'webSiteStoreName',
		model : 'webModel',
		proxy : {
			type : 'ajax',
			url :contextPath+'/review/viewGroupData.do',
			extraParams:{
			},
			actionMethods:{
				read:'POST'
			},
			reader : {
				type :'json',
				root:'model'
					}
		},
		listeners:
		{
			'load':function(store, records){
						
				loadMask.hide();
			}
		},
		autoLoad : true
	});
	
	
	
	
	
	var webSiteTableGrid = Ext.create('Ext.grid.Panel', {
		title:'Clustering Output',
		forceFit : true,
		id : 'webSiteGrid',
		store : webStore,
		columns : webColumns,
		width:1500,
		height:300,
		autoFit : true,
		autoscroll:true,
		stripRows:true,
		renderTo : 'webSiteContainer',
		collapsible:true,
		overflowY:'auto'
	});

});
	
	
	
