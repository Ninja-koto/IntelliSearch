Ext.require( [ 'Ext.grid.*', 'Ext.data.*', 'Ext.form.*',
		'Ext.layout.container.Column', 'Ext.tab.Panel' ]);

Ext.Loader.setConfig( {
	enabled : true
});
var loadMask;
var hideConfirmationMsg;
var showConfirmationMsg;
var contentPanel;
var mainOutputGrid;
var tfDetailsRightArticleGrid;
var tfDetailsLeftArticleGrid;
var unionSetGrid;
var intersectionSetGrid;
var rightArticleFreqInfoListGrid;
var leftArticleFreqInfoListGrid;
var msgListGrid;

/* Hide the Confirmation Message */
hideConfirmationMsg = function() {
	var confMsgDiv = Ext.get('confirmationMessage');
	confMsgDiv.dom.innerHTML = "";
	confMsgDiv.dom.style.display = 'none';
}
/* Show Confirmation Message */
showConfirmationMsg = function(msg) {
	var confMsgDiv = Ext.get('confirmationMessage');
	confMsgDiv.dom.innerHTML = msg;
	confMsgDiv.dom.style.display = 'inline-block';
}
function generateJSONRequestForSimilarity()
{
	var reviewGen={};
	var typeCombo=Ext.getCmp('typeCombo').getValue();

	if(typeCombo!=null)
	{
		reviewGen.typeCombo=typeCombo;
	}
	return reviewGen;
}


function doJSONRequestForSimilarity(reviewGen, urlLink)
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
							var value=errorObj[i].errorCode;
							showConfirmationMsg(value);
					}
					loadMask.hide();
				}
				else
				{
					var value=JsonData.message;
					var data =
					showConfirmationMsg(value);
					loadMask.hide();
					contentPanel.hide();
					
					var dataToBeProcessed =JsonData.model;
					
					processDataAndFillGrid(dataToBeProcessed);
					
				}
			}
},
failure : function(data) {
loadMask.hide();
}
});
}

var mainColumns=[
         			
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
	
	
var mainStoreTemp=Ext.create('Ext.data.ArrayStore',{
				storeId:'mainStore',
				model:'webModel'
});



processDataAndFillGrid = function(dataToBeProcessed){
	
	var array = new Array();
	var length=dataToBeProcessed.length;
	for(k=0;k<length;k++){
	//Main Output Grid
	var articleName =dataToBeProcessed[k].articleName;
	var freq=dataToBeProcessed[k].freq;
	var tokenName=dataToBeProcessed[k].tokenName;
	var logLikelihood=dataToBeProcessed[k].logLikelihood;
	var expectedFrequency=dataToBeProcessed[k].expectedFrequency;

	
	
	var dataTemp={'articleName':articleName,
				  'freq':freq,
				  'tokenName':tokenName,
				  'logLikelihood':logLikelihood,
				  'expectedFrequency':expectedFrequency
				  };
				  array.push(dataTemp);
	}
	

	var mainStoreTemp=Ext.getCmp('mainOutputGrid').getStore();
	mainStoreTemp.loadData(array);
	
			
}




Ext
		.onReady(function() {
			
			
			
			    
			Ext.define('typeModel', {
				extend : 'Ext.data.Model',
				fields : [ 
				           {name:'typeName', mapping:'typeName',type:'string'}
						 ],
				idProperty: 'typeName'
			});
			 

			 contentPanel = Ext
					.create(
							'Ext.form.Panel',
							{
								title : 'Themes Input',
								width : 800,
								height : 100,
								autoScroll : true,
								renderTo:'inputContainer',
								collapsible:true,
								defaults : {
										padding:'0 0 0 25',
									labelAlign : 'top'
								},
								layout : {
									type : 'table',
									columns : 1
								},
								items : [
										{
											xtype : 'textfield',
											labelAlign : 'top',
											width : 150,
											fieldLabel : 'Threshold',
											id : 'typeCombo',
											name : 'typeCombo',
										},
										{
											xtype : 'button',
											text : 'Compare Similarity',
											id : 'Save',
											disabled : false,
											padding:'0 25 25 -17',
											handler : function(store, btn, args) {

												var reviewGenFormat = generateJSONRequestForSimilarity();
												urlLink = contextPath + '/review/themes.do';
												hideConfirmationMsg();
												doJSONRequestForSimilarity(reviewGenFormat,urlLink);
											}
										}]
							});
							
			
	//Now the Grid with Data 
	mainOutputGrid= Ext.create('Ext.grid.Panel', {
		title:'Themes Grid',
		forceFit : true,
		id : 'mainOutputGrid',
		store : mainStoreTemp,
		columns : mainColumns,
		width:1000,
		height:1000,
		autoFit : true,
		autoscroll:true,
		stripRows:true,
		renderTo :Ext.getBody(),
		collapsible:true
	});
		 
	
	
	
});
		
		