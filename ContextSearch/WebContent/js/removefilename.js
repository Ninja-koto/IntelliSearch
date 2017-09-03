Ext.require(['Ext.grid.*', 'Ext.data.*', 'Ext.form.*', 'Ext.layout.container.Column', 'Ext.tab.Panel']);
Ext.Loader.setConfig({
    enabled: true
});
Ext.tip.QuickTipManager.init();



var webColumns=[
         			{
         				header : 'File ID',
         				dataIndex : 'fileId',
         				sortable:true,
         				width:50
         			},
         			{
         				header : 'File Name',
         				dataIndex : 'fileName',
         				sortable:true,
         				width    :150
         			},
         			{
         				header : 'Elective Name',
         				dataIndex : 'electiveName',
         				sortable:true,
         				width    :150
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
		          	{name:'fileId', mapping:'fileId',type:'int'},
		          	{name:'fileName', mapping:'fileName',type:'string'},
		           {name:'electiveName',mapping:'electiveName',type:'string'}
		          ]
		
	});

	webStore = Ext.create('Ext.data.Store', {
		id : 'webSiteStoreId',
		name : 'webSiteStoreName',
		model : 'webModel',
		proxy : {
			type : 'ajax',
			url :contextPath+'/elective/viewFilenames.do',
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
	
	
	
	contentPanel = Ext
	.create(
			'Ext.form.Panel',
			{
				title : 'View File Names',
				collapsible:true,
				width : 1000,
				height : 300,
				autoScroll : true,
				renderTo:'container',
				defaults : {
					padding : '15 100 15 100',
					labelAlign : 'top'
				},
				layout : {
					type : 'table',
					columns : 2
				},
				items : [
						{
							xtype : 'combo',
							fieldLabel : 'Select File Name:',
							id : 'fileName',
							name : 'fileName',
							labelAlign : 'top',
							width : 150,
							queryMode : 'local',
							displayField : 'fileName',
							valueField : 'fileName',
							triggerAction : 'all',
							store : webStore	,
							hidden:true
						},
						{
							xtype : 'button',
							text : 'Store File Name',
							id : 'Save',
							disabled : false,
							handler : function(store, btn, args) {

								var reviewGenFormat = generateJSONRequestForAddingReview();
								urlLink = contextPath + '/review/saveFileName.do';
								hideConfirmationMsg();
								doReviewGenerationRequest(reviewGenFormat,urlLink);
							}
						} ]
			});	
	


});
	
function generateJSONRequestForAddingReview()
{
	var reviewGen={};
	var fileNameValue=Ext.getCmp('fileName').getValue();
	if(fileNameValue!=null)
	{
		reviewGen.fileName=fileNameValue;
	}
	return reviewGen;
}


function doReviewGenerationRequest(reviewGen, urlLink)
{
loadMask = new Ext.LoadMask(Ext.getBody(), {msg:"Loading"});
loadMask.show();
Ext.Ajax.request({	
method: 'POST',
processData: false,
contentType:'application/json',
jsonData: Ext.encode(reviewGen),
url:urlLink, 
success: function(response) {
var data;
if (response){
			 
			var JsonData = Ext.decode(response.responseText);
				if(JsonData.ebErrors != null){
					var errorObj=JsonData.ebErrors;
					for(i=0;i<errorObj.length;i++)
					{
							var value=errorObj[i].errMessage;
							showConfirmationMsg(value);
					}
					loadMask.hide();
				}
				else
				{
					var value=JsonData.message;
					showConfirmationMsg(value);
					contentPanel.hide();
					loadMask.hide();
				}
			}
},
failure : function(data) {
loadMask.hide();
}
});
}	
	
