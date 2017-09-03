Ext.require(['Ext.grid.*', 'Ext.data.*', 'Ext.form.*', 'Ext.layout.container.Column', 'Ext.tab.Panel']);
Ext.Loader.setConfig({
    enabled: true
});
Ext.tip.QuickTipManager.init();



var webColumns=[
         			{
         				header : 'Log Likelihood ID',
         				dataIndex : 'logLikelihoodId',
         				sortable:true,
         				width:100
         			},
         			{
         				header : 'Article Name',
         				dataIndex : 'articleName',
         				sortable:true,
         				width    :200
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
         			},{
						header : 'Expected Frequency',
         				dataIndex:'expectedFrequency',
						sortable:true,
         				width    :200
         			},{
						header : 'Log Likelihood',
         				dataIndex:'logLikelihood',
						sortable:true,
         				width    :200
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
				  {name:'logLikelihoodId', mapping:'logLikelihoodId',type:'int'},
		          {name:'articleName', mapping:'articleName',type:'string'},
		          {name:'freq',mapping:'freq',type:'int'},
		          {name:'tokenName',mapping:'tokenName',type:'string'},
				  {name:'logLikelihood',mapping:'logLikelihood',type:'double'},
				  {name:'expectedFrequency',mapping:'expectedFrequency',type:'double'}
		          ]
		
	});

	webStore = Ext.create('Ext.data.Store', {
		id : 'webSiteStoreId',
		name : 'webSiteStoreName',
		model : 'webModel',
		proxy : {
			type : 'ajax',
			url :contextPath+'/review/retriveAllLOgLikelihood.do',
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
		title:'Log Likelihood Information',
		forceFit : true,
		id : 'rightArticleFreqInfoListGrid',
		store : webStore,
		columns : webColumns,
		width:980,
		height:500,
		autoFit : true,
		autoscroll:true,
		stripRows:true,
		renderTo : Ext.getBody(),
		collapsible:true
	});

});
	
	
	
