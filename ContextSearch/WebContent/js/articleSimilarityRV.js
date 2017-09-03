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
	var articleNameLeft=Ext.getCmp('articleNameLeft').getValue();
	if(articleNameLeft)
	{
		reviewGen.articleNameLeft=articleNameLeft;
	}
	var articleNameRight=Ext.getCmp('articleNameRight').getValue();
	
	if(articleNameRight!=null)
	{
		reviewGen.articleNameRight=articleNameRight;
	}
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
     				header : 'Right Article Sum',
     				dataIndex : 'sumRightArticle',
     				sortable:true,
     				width:50
					},
					{
     				header : 'Left Article Sum',
     				dataIndex : 'sumLeftArticle',
     				sortable:true,
     				width:50
					},
					{
     				header : 'Left Article Mean',
     				dataIndex : 'meanLeftArticle',
     				sortable:true,
     				width:50
					},
					{
     				header : 'Right Article Mean',
     				dataIndex : 'meanRightArticle',
     				sortable:true,
     				width:50
					},{
     				header : 'Left Article SD',
     				dataIndex : 'standardDevLeftArticle',
     				sortable:true,
     				width:50
					},
					{
     				header : 'Right Article SD',
     				dataIndex : 'standardDevRightArtilce',
     				sortable:true,
     				width:50
					},{
     				header : 'RV Coeffcient',
     				dataIndex : 'rvCoeffcient',
     				sortable:true,
     				width:50
					},
					{
     				header : 'Similarity Status',
     				dataIndex : 'isSimilar',
     				sortable:true,
     				width:50
					}
					
];


var mainStoreTemp=Ext.create('Ext.data.ArrayStore',{
				storeId:'mainStore',
				fields: [
						{name:'sumLeftArticle', type:'double'},
						{name:'sumRightArticle',type:'double'},
						{name:'meanLeftArticle',type:'double'},
						{name:'meanRightArticle',type:'double'},
						{name:'standardDevLeftArticle', type:'double'},
						{name:'standardDevRightArtilce',type:'double'},
						{name:'rvCoeffcient',type:'double'},
						{name:'isSimilar',type:'boolean'},
			
					]
});






var tfColumns=[
					{
     				header : 'Intersection Words',
     				dataIndex : 'intersectionWords',
     				sortable:true,
     				width:50
					}
];


var intersectionWordsStore=Ext.create('Ext.data.ArrayStore',{
	storeId:'intersectionWordsStoreId',
	fields: [
			{name:'intersectionWords', type:'string'}
			]
});


var msgListColumns=[
					{
						header : 'Message',
						dataIndex : 'msg',
						sortable:true,
						width:50
					}
					];	

var msgListStore=Ext.create('Ext.data.ArrayStore',{
	storeId:'msgListStore',
	fields: [
			{name:'msg', type:'string'}
			],
	data:['']
});

processDataAndFillGrid = function(dataToBeProcessed){
	
	//Main Output Grid
	var sumLeftArticle =dataToBeProcessed.sumLeftArticle;
	var sumRightArticle=dataToBeProcessed.sumRightArticle;
	var meanRightArticle=dataToBeProcessed.meanRightArticle;
	var meanLeftArticle=dataToBeProcessed.meanLeftArticle;
	var standardDevLeftArticle=dataToBeProcessed.standardDevLeftArticle;
	var standardDevRightArtilce=dataToBeProcessed.standardDevRightArtilce;
	var rvCoeffcient=dataToBeProcessed.rvCoeffcient;
	var isSimilar=dataToBeProcessed.smilar;

	
	var array = new Array();
	var dataTemp={'sumLeftArticle':sumLeftArticle,
				  'sumRightArticle':sumRightArticle,
				  'meanRightArticle':meanRightArticle,
				  'meanLeftArticle':meanLeftArticle,
				  'standardDevLeftArticle':standardDevLeftArticle,
				  'standardDevRightArtilce':standardDevRightArtilce,
				  'rvCoeffcient':rvCoeffcient,
				  'isSimilar':isSimilar
				  };
	array.push(dataTemp);

	var mainStoreTemp=Ext.getCmp('mainOutputGrid').getStore();
	mainStoreTemp.loadData(array);
	
	
	//Message List
	var msgList =dataToBeProcessed.msgList;
	if(msgList){
	var msgListLength=msgList.length;
	
	var msgListArray = new Array();
	
	for(i=0;i<msgListLength;i++){
		var dataTemp={'msg':msgList[i]}
		msgListArray.push(dataTemp);
	}
	
	var msgListStore=Ext.getCmp('msgListGrid').getStore();
	msgListStore.loadData(array);
	
	Ext.getCmp('msgListGrid').show();
	}
	
	
					
	
	//Text Frequency Details Grid
	
	var intersectionWordsList = dataToBeProcessed.intersectionWords;
	if(intersectionWordsList){
		var lengthOfintersectionWordsList =intersectionWordsList.length;
		var tfArray = new Array();
		for(i=0;i<lengthOfintersectionWordsList;i++){
		
			var interWord=intersectionWordsList[i];
			
			var dataTemp={'intersectionWords':interWord};
			tfArray.push(dataTemp);
		}
	var intersectionWordsStore=Ext.getCmp('intersectionWordsGridId').getStore();
	intersectionWordsStore.loadData(tfArray);
	
	Ext.getCmp('intersectionWordsGridId').show();
	}
		
}




Ext
		.onReady(function() {
			
			Ext.define('articleModel', {
				extend : 'Ext.data.Model',
				fields : [ 
				           {name:'articleName', mapping:'articleName',type:'string'}
				         ],
				idProperty: 'articleName'
			});
			
			
			var articleModelStore = Ext.create('Ext.data.Store', {
				id : 'articleModelStore',
				name : 'articleModelStoreName',
				model : 'articleModel',
				proxy : {
					type : 'ajax',
					url :contextPath+'/review/retriveAllArticleNames.do',
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
				}			
				},
				autoLoad : true
			});
			articleModelStore.load();
			
			    
			Ext.define('typeModel', {
				extend : 'Ext.data.Model',
				fields : [ 
				           {name:'typeName', mapping:'typeName',type:'string'}
						 ],
				idProperty: 'typeName'
			});
			 

			var typeStore = Ext.create('Ext.data.Store', {
				id : 'typeStoreId',
				name : 'typeStoreName',
				model : 'typeModel',
				proxy : {
					type : 'ajax',
					url :contextPath+'/review/typeStore.do',
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
				}			
				},
				autoLoad : true
			});
			typeStore.load();
			
				

			 contentPanel = Ext
					.create(
							'Ext.form.Panel',
							{
								title : 'Article Similarity Input',
								width : 800,
								height : 300,
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
											xtype : 'combo',
											labelAlign : 'top',
											width : 150,
											fieldLabel : 'Left Article Name',
											id : 'articleNameLeft',
											name : 'articleNameLeft',
											queryMode : 'local',
											displayField : 'articleName',
											valueField : 'articleName',
											triggerAction : 'all',
											store : articleModelStore,
											listeners : {	
												'select' : function(combo,records) {
											
												}
												}
										},{
											xtype : 'combo',
											labelAlign : 'top',
											width : 150,
											fieldLabel : 'Right Article Name',
											id : 'articleNameRight',
											name : 'articleNameRight',
											queryMode : 'local',
											displayField : 'articleName',
											valueField : 'articleName',
											triggerAction : 'all',
											store : articleModelStore,
											listeners : {	
												'select' : function(combo,records) {
											
												}
												}
										},{
											xtype : 'combo',
											labelAlign : 'top',
											width : 150,
											fieldLabel : 'Type',
											id : 'typeCombo',
											name : 'typeCombo',
											queryMode : 'local',
											displayField : 'typeName',
											valueField : 'typeName',
											triggerAction : 'all',
											store : typeStore,
											listeners : {	
												'select' : function(combo,records) {
											
												}
												}
										},
										{
											xtype : 'button',
											text : 'Compare Similarity',
											id : 'Save',
											disabled : false,
											padding:'0 25 25 -17',
											handler : function(store, btn, args) {

												var reviewGenFormat = generateJSONRequestForSimilarity();
												urlLink = contextPath + '/review//doRVCoeffcient.do';
												hideConfirmationMsg();
												doJSONRequestForSimilarity(reviewGenFormat,urlLink);
											}
										}]
							});
							
			
	//Now the Grid with Data 
	mainOutputGrid= Ext.create('Ext.grid.Panel', {
		title:'Main Output Grid',
		forceFit : true,
		id : 'mainOutputGrid',
		store : mainStoreTemp,
		columns : mainColumns,
		width:1000,
		height:100,
		autoFit : true,
		autoscroll:true,
		stripRows:true,
		renderTo :Ext.getBody(),
		collapsible:true
	});
		 
	tfDetailsRightArticleGrid = Ext.create('Ext.grid.Panel', {
		title:'Intersection Grid',
		forceFit : true,
		id : 'intersectionWordsGridId',
		store : intersectionWordsStore,
		columns : tfColumns,
		width:1000,
		height:200,
		autoFit : true,
		autoscroll:true,
		stripRows:true,
		renderTo :Ext.getBody(),
		collapsible:true
	});
	msgListGrid = Ext.create('Ext.grid.Panel', {
		title:'Message Information List',
		forceFit : true,
		id : 'msgListGrid',
		store : msgListStore,
		columns : msgListColumns,
		width:200,
		height:200,
		autoFit : true,
		autoscroll:true,
		stripRows:true,
		renderTo : Ext.getBody(),
		collapsible:true
	});
	
});
		
		