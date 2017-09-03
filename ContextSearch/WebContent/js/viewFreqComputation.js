Ext.require(['Ext.grid.*', 'Ext.data.*', 'Ext.form.*', 'Ext.layout.container.Column', 'Ext.tab.Panel']);
Ext.Loader.setConfig({
    enabled: true
});
Ext.tip.QuickTipManager.init();



var webColumns=[
         			{
         				header : 'Frequency ID',
         				dataIndex : 'freqId',
         				sortable:true,
         				width:100
         			},
         			{
         				header : 'Article Name',
         				dataIndex : 'articleName',
         				sortable:true,
         				width    :100
         			},
         			{
         				header : 'Token Name',
         				dataIndex : 'tokenName',
         				sortable:true,
         				width    :100
         			},
         			{
         				header : 'Frequency',
         				dataIndex : 'freq',
         				sortable:true,
         				width    :100
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
				  {name:'freqId', mapping:'freqId',type:'int'},
		          {name:'articleName', mapping:'articleName',type:'string'},
		          {name:'freq',mapping:'freq',type:'int'},
		          {name:'tokenName',mapping:'tokenName',type:'string'}
		          ]
		
	});

	webStore = Ext.create('Ext.data.Store', {
		id : 'webSiteStoreId',
		name : 'webSiteStoreName',
		model : 'webModel',
		proxy : {
			type : 'ajax',
			url :contextPath+'/review/viewFreq.do',
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
	
	rightArticleFreqInfoListGrid = Ext.create('Ext.grid.Panel', {
		title:'Articles Frequency Information',
		forceFit : true,
		id : 'rightArticleFreqInfoListGrid',
		store : webStore,
		columns : webColumns,
		width:480,
		height:500,
		autoFit : true,
		autoscroll:true,
		stripRows:true,
		renderTo : Ext.getBody(),
		collapsible:true
	});

});
	
	
	
