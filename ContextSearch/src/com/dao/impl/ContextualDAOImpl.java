package com.dao.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.jdbc.support.lob.DefaultLobHandler;

import com.constants.ContextualConstantsIF;
import com.dao.inter.ContextualDAOIF;
import com.model.AdjectiveVO;
import com.model.ArticleVO;
import com.model.BestFeatureVector;
import com.model.CleanArticleModel;
import com.model.CompareArticleRVVO;
import com.model.ComparisionVO;
import com.model.DuplicateBugResult;
import com.model.FeatureVO;
import com.model.FreqComputation;
import com.model.FrequencyVO;
import com.model.GroupingView;
import com.model.KeyPhraseModel;
import com.model.LogLikelihoodVO;
import com.model.PhraseGModel;
import com.model.PhraseVO;
import com.model.RegisterUser;
import com.model.ArticleModel;
import com.model.SearchObj;
import com.model.StatusInfo;
import com.model.StopWordsVO;
import com.model.StructureVO;
import com.model.TokenVO;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;
import com.sun.org.apache.bcel.internal.generic.NEW;

public class ContextualDAOImpl implements ContextualDAOIF {

	protected SimpleJdbcTemplate template;
	protected NamedParameterJdbcTemplate namedJdbcTemplate;
	private DataSource dataSource;
	@Autowired
	protected MessageSource sqlProperties;
	protected JdbcTemplate jdbcTemplate;
	
	static MongoDatabase db = null;

	/**
	 * 
	 */
	
	static {
        try {
        	
        		MongoClient mongoClient = new MongoClient("localhost",27017);
        		
        		db = mongoClient.getDatabase("ContentSearch");
            
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }
    }
	
	public void init() {
		/*template = new SimpleJdbcTemplate(dataSource);
		namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		jdbcTemplate = new JdbcTemplate(dataSource);*/
	}

	protected boolean update(String sqlKey, Map<String, Object> map) {
		/*System.out.println("Class-->RoutingDaoImpl:Method-->update");
		String sql = sqlProperties.getMessage(sqlKey, null, null);
		System.out.println("SQL" + sql);
		boolean value = false;
		try {
			namedJdbcTemplate.update(sql, map);
			value = true;
		} catch (Exception e) {
			System.out.println("Exception" + e.getMessage());
		}*/
		return false;
	}

	/**
	 * @param sqlKey
	 * @param map
	 * @return true if sucessfully updated
	 */
	protected boolean insert(String sqlKey, Map<String, Object> map) {
		/*System.out.println("Class-->RoutingDaoImpl:Method-->update");
		String sql = sqlProperties.getMessage(sqlKey, null, null);
		System.out.println("SQL" + sql);
		boolean value = false;
		try {
			namedJdbcTemplate.update(sql, map);
			value = true;
		} catch (Exception e) {
			System.out.println("Exception" + e.getMessage());
		}*/
		return false;
	}

	/**
	 * @param sqlQuery
	 * @param sqlKey
	 * @param map
	 * @return true if sucessfully updated
	 */
	protected boolean insertBasedOnQuery(String sqlQuery,
			Map<String, Object> map) {
		/*System.out.println("Class-->RoutingDaoImpl:Method-->update");
		boolean insertQuery = false;
		try {
			namedJdbcTemplate.update(sqlQuery, map);
			insertQuery = true;
		} catch (Exception e) {
			System.out.println("Exception" + e.getMessage());
		}*/
		return false;
	}

	/**
	 * @param <T>
	 * @param sqlKey
	 * @param paramMap
	 * @param rowMapper
	 * @return Object
	 */
	protected <T> T executeForObject(String sqlKey,
			Map<String, ? extends Object> paramMap, RowMapper<T> rowMapper) {
		/*String sql = sqlProperties.getMessage(sqlKey, null, null);
		return namedJdbcTemplate.queryForObject(sql, paramMap, rowMapper);*/
		return null;
	}

	protected <T> T executeForObjectUsingQuery(String sql,
			Map<String, ? extends Object> paramMap, RowMapper<T> rowMapper) {
		//return namedJdbcTemplate.queryForObject(sql, paramMap, rowMapper);
		return null;
	}

	/**
	 * @param sqlKey
	 * @param params
	 * @param whereClause
	 * @return int once the statement gets executed
	 */
	protected int executeForInt(String sqlKey, Map<String, String> params,
			String whereClause) {
		/*String sql = sqlProperties.getMessage(sqlKey, null, null);
		sql = sql.concat(whereClause);
		System.out.println(sql);

		return namedJdbcTemplate.queryForInt(sql, params);*/
		
		return 0;
	}

	/**
	 * @param sqlKey
	 * @param params
	 * @return List of String objects
	 */
	protected List<String> executeForListOfString(String sqlKey,
			Map<String, Object> params) {
		/*String sql = sqlProperties.getMessage(sqlKey, null, null);
		System.out.println(sql);
		System.out.println(params);

		return namedJdbcTemplate.queryForList(sql, params, String.class);*/
		return null;
	}

	/**
	 * @param sqlKey
	 * @param params
	 * @return List of integer values
	 */
	protected List<Integer> executeForListOfInt(String sqlKey,
			Map<String, Object> params) {
		/*String sql = sqlProperties.getMessage(sqlKey, null, null);
		System.out.println(sql);
		System.out.println(params);

		return namedJdbcTemplate.queryForList(sql, params, Integer.class);*/
		
		return null;
	}

	/**
	 * @return template
	 */
	public SimpleJdbcTemplate getTemplate() {
		return template;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	/**
	 * @param dataSource
	 */
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * @return Named Parameter JDBC Template
	 */
	public NamedParameterJdbcTemplate getNamedJdbcTemplate() {
		return namedJdbcTemplate;
	}

	/**
	 * @return the SQL properties
	 */
	public MessageSource getSqlProperties() {
		return sqlProperties;
	}

	/**
	 * @param sqlProperties
	 */
	public void setSqlProperties(MessageSource sqlProperties) {
		this.sqlProperties = sqlProperties;
	}

	@Override
	public StatusInfo insertReview(ArticleModel reviewModel) {

		Integer articleId = 1;
		StatusInfo statusInfo = new StatusInfo();
		try {
			/*String sqlKey = ContextualConstantsIF.SQLS.INSERT_ARTICLE_SQL;*/
			
			
			DBObject project = new BasicDBObject("$project", new BasicDBObject("ARTICLEID",1));
			
			DBObject sort = new BasicDBObject("$sort",new BasicDBObject("ARTICLEID",-1));
			
			DBObject limit = new BasicDBObject("$limit",1);
			
			
			List conditionPipeLine = Arrays.asList(sort, project, limit);
			
			List<String> userList = new ArrayList<String>();
			
			for(Document tempMap : db.getCollection("article").aggregate(conditionPipeLine)){
				articleId = tempMap.getInteger("ARTICLEID");
			}
			
			Map<String, Object> paramMap = new HashMap<String, Object>();
			
			paramMap.put("ARTICLEID", ++articleId);
			paramMap.put("ARTICLENAME", reviewModel.getArticleName());
			paramMap.put("ARTICLEDESC", reviewModel.getArticleDesc());
			paramMap.put("USN", reviewModel.getUserId());
			
			MongoCollection<Document> collection = db.getCollection("article");
			
			collection.insertOne(new Document(paramMap));
			
			
			/*String sql = sqlProperties.getMessage(sqlKey, null, null);
			jdbcTemplate.update(
					sql,
					new Object[] { reviewModel.getArticleName(),
							reviewModel.getArticleDesc(),
							reviewModel.getUserId() }, new int[] {
							Types.VARCHAR, Types.VARCHAR, Types.VARCHAR });*/
			statusInfo.setStatus(true);

		} catch (Exception e) {
			System.out.println("Exception is:");
			System.out.println(e.getMessage());
			e.printStackTrace();
			statusInfo
					.setErrMsg(ContextualConstantsIF.Message.MESSAGE_INTERNAL);
			statusInfo.setExceptionMsg(e.getMessage());
			statusInfo.setStatus(false);
			return statusInfo;

		}
		return statusInfo;
	}

	@Override
	public List<ArticleModel> retriveAllReviews() {

		try {

			/*String sql = sqlProperties.getMessage(
					ContextualConstantsIF.SQLS.RETRIVE_REVIEWS_SQL, null, null);*/
			
			DBObject project = new BasicDBObject("$project", new BasicDBObject("ARTICLEDESC",1).append("ARTICLENAME", 1).append("USN", 1).append("ARTICLEID", 1));
			
			List conditionPipeLine = Arrays.asList(project);
			
			List<ArticleModel> articleModelList = new ArrayList<ArticleModel>();
			
			for(Document tempMap : db.getCollection("article").aggregate(conditionPipeLine)){
				ArticleModel reviewModel = new ArticleModel();

				reviewModel.setArticleDesc(tempMap.getString("ARTICLEDESC"));
				reviewModel.setArticleName(tempMap.getString("ARTICLENAME"));
				reviewModel.setUserId(tempMap.getString("USN"));
				reviewModel.setArticleId(tempMap.getInteger("ARTICLEID"));
				
				articleModelList.add(reviewModel);
			}
			
			return articleModelList;
			
			
			//return jdbcTemplate.query(sql, new ReviewModelVOMapper());
		} catch (Exception e) {
			System.out.println("Exception" + e);
			return null;
		}
	}

	private final class ReviewModelVOMapper implements RowMapper<ArticleModel> {

		public ArticleModel mapRow(ResultSet rs, int arg1) throws SQLException {

			ArticleModel reviewModel = new ArticleModel();

			reviewModel.setArticleDesc(rs.getString("ARTICLEDESC"));
			reviewModel.setArticleName(rs.getString("ARTICLENAME"));
			reviewModel.setUserId(rs.getString("USN"));
			reviewModel.setArticleId(rs.getInt("ARTICLEID"));
			return reviewModel;

		}

	}

	@Override
	public StatusInfo insertStopWord(String stopWord) {
		StatusInfo insertStopWordStatus = null;
		try {
			insertStopWordStatus = new StatusInfo();
			/*String sql = sqlProperties.getMessage(
					ContextualConstantsIF.SQLS.INSERT_STOPWORD_SQL, null, null);*/
			
			
			Integer stopwordId=1;
			
			try{
				DBObject project = new BasicDBObject("$project", new BasicDBObject("STOPWORDID",1));
				
				DBObject sort = new BasicDBObject("$sort",new BasicDBObject("STOPWORDID",-1));
				
				DBObject limit = new BasicDBObject("$limit",1);
				
				
				List conditionPipeLine = Arrays.asList(sort, project, limit);
				
				List<String> userList = new ArrayList<String>();
				
				for(Document tempMap : db.getCollection("stopwords").aggregate(conditionPipeLine)){
					stopwordId = tempMap.getInteger("STOPWORDID");
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
			
			
			
			
			Map<String, Object> paramMap = new HashMap<String, Object>();
			
			paramMap.put("STOPWORD", stopWord);
			paramMap.put("STOPWORDID", ++stopwordId);
			
			
			MongoCollection<Document> collection = db.getCollection("stopwords");
			
			collection.insertOne(new Document(paramMap));

			
			/*System.out.println("SQL----" + sql);

			jdbcTemplate.update(sql, new Object[] { stopWord },
					new int[] { Types.VARCHAR });*/
			insertStopWordStatus.setStatus(true);
			return insertStopWordStatus;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			insertStopWordStatus = new StatusInfo();
			insertStopWordStatus.setErrMsg(e.getMessage());
			insertStopWordStatus.setStatus(false);
			return insertStopWordStatus;

		}
	}

	@Override
	public List<StopWordsVO> retriveStopWords() {
		//List<StopWordsVO> stopWordList = null;
		try {
			/*String sql = sqlProperties.getMessage(
					ContextualConstantsIF.SQLS.RETRIVE_STOPWORDS_FULL_SQL,
					null, null);*/
			
			DBObject project = new BasicDBObject("$project", new BasicDBObject("STOPWORDID",1).append("STOPWORD", 1));
			
			List conditionPipeLine = Arrays.asList(project);

			List<StopWordsVO> stopWordList = new ArrayList<StopWordsVO>();
			
			for(Document tempMap : db.getCollection("stopwords").aggregate(conditionPipeLine)){
				
				StopWordsVO stopword = new StopWordsVO();
				stopword.setStopWordId(tempMap.getInteger("STOPWORDID"));
				stopword.setStopWord(tempMap.getString("STOPWORD"));
				
				stopWordList.add(stopword);
			}
			
			return stopWordList;
			//return jdbcTemplate.query(sql, new StopWordsVOMapper());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	private final class StopWordsVOMapper implements RowMapper<StopWordsVO> {

		public StopWordsVO mapRow(ResultSet rs, int arg1) throws SQLException {
			StopWordsVO webSiteDataVO = new StopWordsVO();
			webSiteDataVO
					.setStopWordId(rs
							.getInt(ContextualConstantsIF.DatabaseColumns.STOPWORDID_COL));
			webSiteDataVO
					.setStopWord(rs
							.getString(ContextualConstantsIF.DatabaseColumns.STOPWORD_COL));
			return webSiteDataVO;

		}

	}

	@Override
	public List<String> retriveStopWordsOnly() {
		try {
			/*String sql = sqlProperties.getMessage(
					ContextualConstantsIF.SQLS.RETRIVE_STOPWORDS_SQL, null,
					null);*/
			
			DBObject project = new BasicDBObject("$project", new BasicDBObject("STOPWORD",1));
			
			List conditionPipeLine = Arrays.asList(project);
			
			List<String> stopWordList = new ArrayList<String>();
			
			for(Document tempMap : db.getCollection("stopwords").aggregate(conditionPipeLine)){
				stopWordList.add(tempMap.getString("STOPWORD"));
			}
			//return jdbcTemplate.queryForList(sql, String.class);
			return stopWordList;			
			
			//return jdbcTemplate.queryForList(sql, String.class);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	@Override
	public StatusInfo removeStopword(String stopWord) {
		StatusInfo statusInfo = null;

		try {
			statusInfo = new StatusInfo();
			String sql = sqlProperties.getMessage(
					ContextualConstantsIF.SQLS.REMOVE_STOPWORD_SQL, null, null);
			jdbcTemplate.update(sql, stopWord);
			statusInfo.setStatus(true);
			return statusInfo;
		} catch (Exception e) {
			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			System.out.println("Exception" + e);
			e.printStackTrace();
			return statusInfo;
		}
	}

	@Override
	public List<TokenVO> retriveAllTokens() {
		try {
			/*String sql = sqlProperties.getMessage(
					ContextualConstantsIF.SQLS.RETRIVE_ALLTOKENS_SQL, null,
					null);*/
			
			DBObject project = new BasicDBObject("$project", new BasicDBObject("TOKENID",1).append("TOKENNAME", 1).append("ARTICLENAME", 1));
			
			List conditionPipeLine = Arrays.asList(project);
			
			List<TokenVO> tokenList = new ArrayList<TokenVO>();
			
			for(Document tempMap : db.getCollection("tokens").aggregate(conditionPipeLine)){
				TokenVO token = new TokenVO();
				
				token.setTokenId(tempMap.getInteger("TOKENID"));
				token.setTokenName(tempMap.getString("TOKENNAME"));
				
				token.setArticleName(tempMap.getString("ARTICLENAME"));
				
				tokenList.add(token);
			}
			
			
			//return jdbcTemplate.query(sql, new TokenMapper());
			
			return tokenList;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	private final class TokenMapper implements RowMapper<TokenVO> {

		public TokenVO mapRow(ResultSet rs, int arg1) throws SQLException {
			TokenVO tokenVO = new TokenVO();

			tokenVO.setTokenId(rs
					.getInt(ContextualConstantsIF.DatabaseColumns.TOKENID_COL));
			tokenVO.setTokenName(rs
					.getString(ContextualConstantsIF.DatabaseColumns.TOKENNAME_COL));

			tokenVO.setArticleName(rs
					.getString(ContextualConstantsIF.DatabaseColumns.ARTICLENAME_COL));

			return tokenVO;
		}

	}

	@Override
	public List<CleanArticleModel> retriveCleanReviews() {
		try {
			/*String sql = sqlProperties.getMessage(
					ContextualConstantsIF.SQLS.RETRIVE_ALLCLEAN_ARTICLES_SQL,
					null, null);*/
			
			DBObject project = new BasicDBObject("$project", new BasicDBObject(ContextualConstantsIF.DatabaseColumns.CLEANID_COL,1).append(ContextualConstantsIF.DatabaseColumns.CLEANARTICLE_COL, 1).append(ContextualConstantsIF.DatabaseColumns.ARTICLENAME_COL, 1));
			
			List conditionPipeLine = Arrays.asList(project);
			
			List<CleanArticleModel> cleanArticleModelList = new ArrayList<CleanArticleModel>();
			
			for(Document tempMap : db.getCollection("cleanarticles").aggregate(conditionPipeLine)){
				CleanArticleModel cleanReviewModel = new CleanArticleModel();
				cleanReviewModel.setCleanId(tempMap.getInteger(ContextualConstantsIF.DatabaseColumns.CLEANID_COL));
				cleanReviewModel
						.setCleanArtilce(tempMap
								.getString(ContextualConstantsIF.DatabaseColumns.CLEANARTICLE_COL));

				cleanReviewModel
						.setArticleName(tempMap
								.getString(ContextualConstantsIF.DatabaseColumns.ARTICLENAME_COL));

				
				cleanArticleModelList.add(cleanReviewModel);
			}
			
			
			//return jdbcTemplate.query(sql, new TokenMapper());
			
			return cleanArticleModelList;
			
			
			//return jdbcTemplate.query(sql, new CleanReviewModelMapper());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	private final class CleanReviewModelMapper implements
			RowMapper<CleanArticleModel> {

		public CleanArticleModel mapRow(ResultSet rs, int arg1)
				throws SQLException {
			CleanArticleModel cleanReviewModel = new CleanArticleModel();
			cleanReviewModel.setCleanId(rs
					.getInt(ContextualConstantsIF.DatabaseColumns.CLEANID_COL));
			cleanReviewModel
					.setCleanArtilce(rs
							.getString(ContextualConstantsIF.DatabaseColumns.CLEANARTICLE_COL));

			cleanReviewModel
					.setArticleName(rs
							.getString(ContextualConstantsIF.DatabaseColumns.ARTICLENAME_COL));

			return cleanReviewModel;
		}

	}

	@Override
	public StatusInfo deleteAllCleanReviews() {
		StatusInfo statusInfo = null;
		try {

			statusInfo = new StatusInfo();
			/*String sql = sqlProperties.getMessage(
					ContextualConstantsIF.SQLS.DELETE_ALL_CLEAN_ARTICLES_SQL,
					null, null);*/
			
			db.getCollection("cleanarticles").drop();
			
			//jdbcTemplate.update(sql);
			statusInfo.setStatus(true);
			return statusInfo;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception " + e.getMessage());
			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			return statusInfo;
		}
	}

	@Override
	public StatusInfo insertCleanDetails(CleanArticleModel cleanReview) {
		StatusInfo cleanDetailStatus = null;
		try {
			cleanDetailStatus = new StatusInfo();
			/*String sql = sqlProperties.getMessage(
					ContextualConstantsIF.SQLS.INSERT_CLEANDETAILS_SQL, null,
					null);*/
			Integer CLEAN_ID = 1;
			
			DBObject project = new BasicDBObject("$project", new BasicDBObject("CLEANID",1));
			
			DBObject sort = new BasicDBObject("$sort",new BasicDBObject("CLEANID",-1));
			
			DBObject limit = new BasicDBObject("$limit",1);
			
			
			List conditionPipeLine = Arrays.asList(sort, project, limit);
			
			for(Document tempMap : db.getCollection("cleanarticles").aggregate(conditionPipeLine)){
				CLEAN_ID = tempMap.getInteger("CLEANID");
			}
			

			Map<String, Object> paramMap = new HashMap<String, Object>();
			
			paramMap.put("CLEANID", ++CLEAN_ID);
			
			paramMap.put(
					ContextualConstantsIF.DatabaseColumnsKeys.ARTICLENAME_KEY,
					cleanReview.getArticleName());
			paramMap.put(
					ContextualConstantsIF.DatabaseColumnsKeys.CLEANARTICLE_KEY,
					cleanReview.getCleanArtilce());
			

			MongoCollection<Document> collection = db.getCollection("cleanarticles");
			
			collection.insertOne(new Document(paramMap));


			//namedJdbcTemplate.update(sql, paramMap);

			cleanDetailStatus.setStatus(true);
			return cleanDetailStatus;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			cleanDetailStatus = new StatusInfo();
			cleanDetailStatus.setErrMsg(e.getMessage());
			cleanDetailStatus.setStatus(false);
			return cleanDetailStatus;

		}
	}

	@Override
	public StatusInfo insertToken(TokenVO tokenVO) {
		StatusInfo insertTokenStatus = null;
		try {
			insertTokenStatus = new StatusInfo();
			/*String sql = sqlProperties.getMessage(
					ContextualConstantsIF.SQLS.INSERT_TOKENS_SQL, null, null);*/
			
			
			Integer TOKEN_ID = 1;
			
			
			DBObject project = new BasicDBObject("$project", new BasicDBObject("TOKENID",1));
			
			DBObject sort = new BasicDBObject("$sort",new BasicDBObject("TOKENID",-1));
			
			DBObject limit = new BasicDBObject("$limit",1);
			
			
			List conditionPipeLine = Arrays.asList(sort, project, limit);
			
			List<String> userList = new ArrayList<String>();
			
			for(Document tempMap : db.getCollection("tokens").aggregate(conditionPipeLine)){
				TOKEN_ID = tempMap.getInteger("TOKENID");
			}
			

			Map<String, Object> paramMap = new HashMap<String, Object>();
			
			paramMap.put("TOKENID", ++TOKEN_ID);
			paramMap.put(
					ContextualConstantsIF.DatabaseColumnsKeys.ARTICLENAME_KEY,
					tokenVO.getArticleName());
			paramMap.put(
					ContextualConstantsIF.DatabaseColumnsKeys.TOKENNAME_KEY,
					tokenVO.getTokenName());
			
			
			MongoCollection<Document> collection = db.getCollection("tokens");
			
			collection.insertOne(new Document(paramMap));


			//namedJdbcTemplate.update(sql, paramMap);

			insertTokenStatus.setStatus(true);
			return insertTokenStatus;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			insertTokenStatus = new StatusInfo();
			insertTokenStatus.setErrMsg(e.getMessage());
			insertTokenStatus.setStatus(false);
			return insertTokenStatus;

		}
	}

	@Override
	public StatusInfo deleteAllTokens() {
		StatusInfo statusInfo = null;

		try {
			statusInfo = new StatusInfo();
			/*String sql = sqlProperties
					.getMessage(
							ContextualConstantsIF.SQLS.DELETE_ALLTOKENS_SQL,
							null, null);*/
			db.getCollection("tokens").drop();
			//jdbcTemplate.update(sql);
			statusInfo.setStatus(true);
			return statusInfo;
		} catch (Exception e) {
			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			System.out.println("Exception" + e);
			e.printStackTrace();
			return statusInfo;
		}
	}

	@Override
	public List<String> retriveKeyPhraseOnly(String type) {
		try {
			String sql = sqlProperties
					.getMessage(
							ContextualConstantsIF.SQLS.RETRIVE_KEYPHRASE_WHERE_TYPE_SQL,
							null, null);

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put(ContextualConstantsIF.DatabaseColumnsKeys.TYPE_KEY,
					type);

			return namedJdbcTemplate.query(sql, paramMap,
					new KeyPhraseOnlyMapper());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	private final class KeyPhraseOnlyMapper implements RowMapper<String> {

		public String mapRow(ResultSet rs, int arg1) throws SQLException {

			return rs
					.getString(ContextualConstantsIF.DatabaseColumns.KEYPHARSE_COL);

		}

	}

	@Override
	public StatusInfo insertKeyPhrase(String keyphrase, String type) {
		StatusInfo keyPhraseStatus = null;
		try {
			keyPhraseStatus = new StatusInfo();
			String sql = sqlProperties
					.getMessage(
							ContextualConstantsIF.SQLS.INSERT_KEYPHRASE_SQL,
							null, null);

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put(
					ContextualConstantsIF.DatabaseColumnsKeys.KEYPHARSE_KEY,
					keyphrase);
			paramMap.put(ContextualConstantsIF.DatabaseColumnsKeys.TYPE_KEY,
					type);

			namedJdbcTemplate.update(sql, paramMap);

			keyPhraseStatus.setStatus(true);
			return keyPhraseStatus;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			keyPhraseStatus = new StatusInfo();
			keyPhraseStatus.setErrMsg(e.getMessage());
			keyPhraseStatus.setStatus(false);
			return keyPhraseStatus;

		}
	}

	@Override
	public List<KeyPhraseModel> retriveKeyPhrases() {
		List<KeyPhraseModel> keyPhraseModelList = null;
		try {
			String sql = sqlProperties.getMessage(
					ContextualConstantsIF.SQLS.RETRIVE_KEYPHRASE_FULL_SQL,
					null, null);

			Map<String, Object> paramMap = null;

			return namedJdbcTemplate.query(sql, paramMap,
					new KeyPhraseVOMapper());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	private final class KeyPhraseVOMapper implements RowMapper<KeyPhraseModel> {

		public KeyPhraseModel mapRow(ResultSet rs, int arg1)
				throws SQLException {
			KeyPhraseModel keyPhraseModel = new KeyPhraseModel();

			keyPhraseModel
					.setKeyPhrase(rs
							.getString(ContextualConstantsIF.DatabaseColumns.KEYPHARSE_COL));

			keyPhraseModel
					.setKeyPhraseId(rs
							.getInt(ContextualConstantsIF.DatabaseColumns.KEYPHRASEID_COL));

			keyPhraseModel.setType(rs
					.getString(ContextualConstantsIF.DatabaseColumns.TYPE_COL));

			return keyPhraseModel;
		}

	}

	@Override
	public StatusInfo removeKeyPhrase(String keyphrase, String type) {
		StatusInfo statusInfo = null;

		try {
			statusInfo = new StatusInfo();
			String sql = sqlProperties
					.getMessage(
							ContextualConstantsIF.SQLS.REMOVE_KEYPHRASE_WHERE_KEYPHRASE_AND_TYPE_SQL,
							null, null);

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put(
					ContextualConstantsIF.DatabaseColumnsKeys.KEYPHARSE_KEY,
					keyphrase);
			paramMap.put(ContextualConstantsIF.DatabaseColumnsKeys.TYPE_KEY,
					type);

			namedJdbcTemplate.update(sql, paramMap);
			statusInfo.setStatus(true);
			return statusInfo;
		} catch (Exception e) {
			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			System.out.println("Exception" + e);
			e.printStackTrace();
			return statusInfo;
		}
	}

	@Override
	public StatusInfo insertPhraseG(PhraseGModel pharaseGModel) {
		StatusInfo phraseGStatus = null;
		try {
			phraseGStatus = new StatusInfo();
			String sql = sqlProperties.getMessage(
					ContextualConstantsIF.SQLS.INSERT_PHRASEG_SQL, null, null);

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put(ContextualConstantsIF.DatabaseColumnsKeys.NOTTYPE_KEY,
					pharaseGModel.getNoType());
			paramMap.put(ContextualConstantsIF.DatabaseColumnsKeys.TYPE_KEY,
					pharaseGModel.getType());
			paramMap.put(ContextualConstantsIF.DatabaseColumnsKeys.PHASEG_KEY,
					pharaseGModel.getPhraseG());

			namedJdbcTemplate.update(sql, paramMap);

			phraseGStatus.setStatus(true);
			return phraseGStatus;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			phraseGStatus = new StatusInfo();
			phraseGStatus.setErrMsg(e.getMessage());
			phraseGStatus.setStatus(false);
			return phraseGStatus;

		}
	}

	@Override
	public List<String> retrivePhraseGWhereTypeAndNoType(
			PhraseGModel pharaseGModel) {

		try {
			String sql = sqlProperties
					.getMessage(
							ContextualConstantsIF.SQLS.RETRIVE_PHRASEG_WHERE_TYPE_NOTYPE_SQL,
							null, null);

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put(ContextualConstantsIF.DatabaseColumnsKeys.TYPE_KEY,
					pharaseGModel.getType());
			paramMap.put(ContextualConstantsIF.DatabaseColumnsKeys.NOTTYPE_KEY,
					pharaseGModel.getNoType());

			return namedJdbcTemplate.query(sql, paramMap,
					new PhraseGNewOnlyMapper());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	private final class PhraseGNewOnlyMapper implements RowMapper<String> {

		public String mapRow(ResultSet rs, int arg1) throws SQLException {

			return rs
					.getString(ContextualConstantsIF.DatabaseColumns.PHASEG_COL);

		}

	}

	@Override
	public List<PhraseGModel> retrivePhraseGPhrases() {
		List<PhraseGModel> pharseGModelList = null;
		try {
			String sql = sqlProperties.getMessage(
					ContextualConstantsIF.SQLS.RETRIVE_PHRASEG_FULL_SQL, null,
					null);

			Map<String, Object> paramMap = null;

			return namedJdbcTemplate
					.query(sql, paramMap, new PhraseGVOMapper());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return pharseGModelList;
		}
	}

	private final class PhraseGVOMapper implements RowMapper<PhraseGModel> {

		public PhraseGModel mapRow(ResultSet rs, int arg1) throws SQLException {
			PhraseGModel phraseGModel = new PhraseGModel();

			phraseGModel.setType(rs
					.getString(ContextualConstantsIF.DatabaseColumns.TYPE_COL));
			phraseGModel
					.setPhraseG(rs
							.getString(ContextualConstantsIF.DatabaseColumns.PHASEG_COL));
			phraseGModel
					.setPhraseGId(rs
							.getInt(ContextualConstantsIF.DatabaseColumns.PHARSEGID_COL));
			phraseGModel
					.setNoType(rs
							.getString(ContextualConstantsIF.DatabaseColumns.NOTTYPE_COL));

			return phraseGModel;
		}

	}

	// REMOVE_PHRASEG_WHERE_PHRASEG_AND_TYPE_AND_NOTYPE_SQL
	@Override
	public StatusInfo removePhraseGForPhraseGTypeNoType(
			PhraseGModel phraseGModel) {
		StatusInfo statusInfo = null;

		try {
			statusInfo = new StatusInfo();
			String sql = sqlProperties
					.getMessage(
							ContextualConstantsIF.SQLS.REMOVE_PHRASEG_WHERE_PHRASEG_AND_TYPE_AND_NOTYPE_SQL,
							null, null);

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put(ContextualConstantsIF.DatabaseColumnsKeys.PHASEG_KEY,
					phraseGModel.getPhraseG());
			paramMap.put(ContextualConstantsIF.DatabaseColumnsKeys.TYPE_KEY,
					phraseGModel.getType());
			paramMap.put(ContextualConstantsIF.DatabaseColumnsKeys.NOTTYPE_KEY,
					phraseGModel.getNoType());

			namedJdbcTemplate.update(sql, paramMap);
			statusInfo.setStatus(true);
			return statusInfo;
		} catch (Exception e) {
			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			System.out.println("Exception" + e);
			e.printStackTrace();
			return statusInfo;
		}
	}

	@Override
	public StatusInfo deleteAllPhrases() {
		StatusInfo statusInfo = null;
		try {

			statusInfo = new StatusInfo();
			String sql = sqlProperties.getMessage(
					ContextualConstantsIF.SQLS.DELETE_ALL_PHRASES_SQL, null,
					null);

			Map<String, Object> paramMap = null;

			namedJdbcTemplate.update(sql, paramMap);
			statusInfo.setStatus(true);
			return statusInfo;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception " + e.getMessage());
			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			return statusInfo;
		}
	}

	@Override
	public List<String> retriveTokensOnly() {
		try {
			String sql = sqlProperties.getMessage(
					ContextualConstantsIF.SQLS.RETRIVE_ONLY_ALLTOKENS_SQL,
					null, null);
			return jdbcTemplate.queryForList(sql, String.class);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<String> retriveAllKeyPhraseOnly() {
		try {
			String sql = sqlProperties.getMessage(
					ContextualConstantsIF.SQLS.RETRIVE_ONLY_ALLKEYPHRASES_SQL,
					null, null);
			return jdbcTemplate.queryForList(sql, String.class);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<String> retriveAllPhraseGOnly() {
		try {
			String sql = sqlProperties.getMessage(
					ContextualConstantsIF.SQLS.RETRIVE_ONLY_ALLKEYPHRASES_SQL,
					null, null);
			return jdbcTemplate.queryForList(sql, String.class);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<String> retriveAllAdjectives() {
		try {
			String sql = sqlProperties.getMessage(
					ContextualConstantsIF.SQLS.RETRIVE_ONLY_ALL_ADJECTIVES_SQL,
					null, null);
			return jdbcTemplate.queryForList(sql, String.class);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	@Override
	public StatusInfo insertPhrasesList(Set<String> phrases) {
		StatusInfo insertStopWordStatus = null;
		try {
			insertStopWordStatus = new StatusInfo();
			String sql = sqlProperties.getMessage(
					ContextualConstantsIF.SQLS.INSERT_PHRASE_SQL, null, null);

			for (String phraString : phrases) {
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put(
						ContextualConstantsIF.DatabaseColumnsKeys.PHRASE_KEY,
						phraString);

				namedJdbcTemplate.update(sql, paramMap);

			}

			insertStopWordStatus.setStatus(true);
			return insertStopWordStatus;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			insertStopWordStatus = new StatusInfo();
			insertStopWordStatus.setErrMsg(e.getMessage());
			insertStopWordStatus.setStatus(false);
			return insertStopWordStatus;

		}
	}

	@Override
	public List<PhraseVO> retrivePhraseList() {
		try {
			String sql = sqlProperties.getMessage(
					ContextualConstantsIF.SQLS.RETRIVE_ALL_PHRASES_SQL, null,
					null);

			Map<String, Object> paramMap = null;

			return namedJdbcTemplate.query(sql, paramMap,
					new PhraseListMapper());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	private final class PhraseListMapper implements RowMapper<PhraseVO> {

		public PhraseVO mapRow(ResultSet rs, int arg1) throws SQLException {

			PhraseVO reviewModel = new PhraseVO();

			reviewModel
					.setPhrase(rs
							.getString(ContextualConstantsIF.DatabaseColumns.PHRASE_COL));
			reviewModel
					.setPhraseId(rs
							.getInt(ContextualConstantsIF.DatabaseColumns.PHRASEID_COL));

			return reviewModel;

		}

	}

	@Override
	public StatusInfo deleteAllAdjectives() {
		StatusInfo statusInfo = null;

		try {
			statusInfo = new StatusInfo();
			String sql = sqlProperties.getMessage(
					ContextualConstantsIF.SQLS.DELETE_ALL_ADJECTIVES_SQL, null,
					null);
			jdbcTemplate.update(sql);
			statusInfo.setStatus(true);
			return statusInfo;
		} catch (Exception e) {
			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			System.out.println("Exception" + e);
			e.printStackTrace();
			return statusInfo;
		}
	}

	@Override
	public StatusInfo insertAdjectives(List<String> adjectiveList) {
		StatusInfo adjectiveStatus = null;
		try {
			adjectiveStatus = new StatusInfo();
			String sql = sqlProperties
					.getMessage(
							ContextualConstantsIF.SQLS.INSERT_ADJECTIVE_SQL,
							null, null);

			if (adjectiveList != null && !adjectiveList.isEmpty()) {

				for (String adjective : adjectiveList) {
					Map<String, Object> paramMap = new HashMap<String, Object>();
					paramMap.put(
							ContextualConstantsIF.DatabaseColumnsKeys.ADJECTIVE_KEY,
							adjective);
					namedJdbcTemplate.update(sql, paramMap);
				}

			}

			adjectiveStatus.setStatus(true);
			return adjectiveStatus;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			adjectiveStatus = new StatusInfo();
			adjectiveStatus.setErrMsg(e.getMessage());
			adjectiveStatus.setStatus(false);
			return adjectiveStatus;

		}
	}

	@Override
	public List<AdjectiveVO> retriveAllFullAdjectives() {
		List<AdjectiveVO> stopWordList = null;
		try {
			String sql = sqlProperties.getMessage(
					ContextualConstantsIF.SQLS.RETRIVE_ADJECTIVES_FULL_SQL,
					null, null);

			Map<String, Object> paramMap = null;

			return namedJdbcTemplate.query(sql, paramMap,
					new AdjectiveVOMapper());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	private final class AdjectiveVOMapper implements RowMapper<AdjectiveVO> {

		public AdjectiveVO mapRow(ResultSet rs, int arg1) throws SQLException {
			AdjectiveVO adjectiveVO = new AdjectiveVO();
			adjectiveVO
					.setAdjective(rs
							.getString(ContextualConstantsIF.DatabaseColumns.ADJECTIVE_COL));
			adjectiveVO
					.setAdjectiveId((rs
							.getInt(ContextualConstantsIF.DatabaseColumns.ADJECTIVEID_COL)));
			return adjectiveVO;

		}

	}

	@Override
	public StatusInfo insertStruture1(List<String> structureList) {

		StatusInfo adjectiveStatus = null;
		try {
			adjectiveStatus = new StatusInfo();
			String sql = sqlProperties.getMessage(
					ContextualConstantsIF.SQLS.INSERT_STRUCTURE1_SQL, null,
					null);

			if (structureList != null && !structureList.isEmpty()) {

				for (String structure1 : structureList) {
					Map<String, Object> paramMap = new HashMap<String, Object>();
					paramMap.put(
							ContextualConstantsIF.DatabaseColumnsKeys.STRUCTURE_KEY,
							structure1);
					namedJdbcTemplate.update(sql, paramMap);
				}

			}

			adjectiveStatus.setStatus(true);
			return adjectiveStatus;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			adjectiveStatus = new StatusInfo();
			adjectiveStatus.setErrMsg(e.getMessage());
			adjectiveStatus.setStatus(false);
			return adjectiveStatus;

		}

	}

	@Override
	public List<StructureVO> retriveStructure1List() {
		List<StructureVO> structure1List = null;
		try {
			String sql = sqlProperties.getMessage(
					ContextualConstantsIF.SQLS.RETRIVE_STRUCTURE1_FULL_SQL,
					null, null);
			return jdbcTemplate.query(sql, new Structure1VOMapper());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	private final class Structure1VOMapper implements RowMapper<StructureVO> {

		public StructureVO mapRow(ResultSet rs, int arg1) throws SQLException {
			StructureVO strutureVO = new StructureVO();
			strutureVO
					.setStructureId(rs
							.getInt(ContextualConstantsIF.DatabaseColumns.STRUCTUREID_COL));
			strutureVO
					.setStructure(rs
							.getString(ContextualConstantsIF.DatabaseColumns.STRUCTURE_COL));
			return strutureVO;

		}

	}

	@Override
	public StatusInfo deleteAllStructure1() {
		StatusInfo statusInfo = null;

		try {
			statusInfo = new StatusInfo();
			String sql = sqlProperties.getMessage(
					ContextualConstantsIF.SQLS.DELETE_ALL_STRUCTURE1_SQL, null,
					null);
			jdbcTemplate.update(sql);
			statusInfo.setStatus(true);
			return statusInfo;
		} catch (Exception e) {
			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			System.out.println("Exception" + e);
			e.printStackTrace();
			return statusInfo;
		}
	}

	@Override
	public List<StructureVO> retriveStructure2List() {
		List<StructureVO> structure2List = null;
		try {
			String sql = sqlProperties.getMessage(
					ContextualConstantsIF.SQLS.RETRIVE_STRUCTURE2_FULL_SQL,
					null, null);
			return jdbcTemplate.query(sql, new Structure1VOMapper());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<StructureVO> retriveStructure3List() {
		List<StructureVO> structure3List = null;
		try {
			String sql = sqlProperties.getMessage(
					ContextualConstantsIF.SQLS.RETRIVE_STRUCTURE3_FULL_SQL,
					null, null);
			return jdbcTemplate.query(sql, new Structure1VOMapper());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	@Override
	public StatusInfo deleteAllStructure2() {
		StatusInfo statusInfo = null;

		try {
			statusInfo = new StatusInfo();
			String sql = sqlProperties.getMessage(
					ContextualConstantsIF.SQLS.DELETE_ALL_STRUCTURE2_SQL, null,
					null);
			jdbcTemplate.update(sql);
			statusInfo.setStatus(true);
			return statusInfo;
		} catch (Exception e) {
			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			System.out.println("Exception" + e);
			e.printStackTrace();
			return statusInfo;
		}
	}

	@Override
	public StatusInfo insertStruture2(List<String> structureList) {
		StatusInfo adjectiveStatus = null;
		try {
			adjectiveStatus = new StatusInfo();
			String sql = sqlProperties.getMessage(
					ContextualConstantsIF.SQLS.INSERT_STRUCTURE2_SQL, null,
					null);

			if (structureList != null && !structureList.isEmpty()) {

				for (String structure1 : structureList) {
					Map<String, Object> paramMap = new HashMap<String, Object>();
					paramMap.put(
							ContextualConstantsIF.DatabaseColumnsKeys.STRUCTURE_KEY,
							structure1);
					namedJdbcTemplate.update(sql, paramMap);
				}

			}

			adjectiveStatus.setStatus(true);
			return adjectiveStatus;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			adjectiveStatus = new StatusInfo();
			adjectiveStatus.setErrMsg(e.getMessage());
			adjectiveStatus.setStatus(false);
			return adjectiveStatus;

		}
	}

	@Override
	public List<String> retrivePhraseListOnly() {
		try {
			String sql = sqlProperties.getMessage(
					ContextualConstantsIF.SQLS.RETRIVE_ALL_PHRASES_ONLY_SQL,
					null, null);

			Map<String, Object> paramMap = null;

			return jdbcTemplate.queryForList(sql, String.class);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	@Override
	public StatusInfo deleteAllStructure3() {
		StatusInfo statusInfo = null;

		try {
			statusInfo = new StatusInfo();
			String sql = sqlProperties.getMessage(
					ContextualConstantsIF.SQLS.DELETE_ALL_STRUCTURE3_SQL, null,
					null);
			jdbcTemplate.update(sql);
			statusInfo.setStatus(true);
			return statusInfo;
		} catch (Exception e) {
			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			System.out.println("Exception" + e);
			e.printStackTrace();
			return statusInfo;
		}
	}

	@Override
	public StatusInfo insertStruture3(List<String> structureList) {
		StatusInfo structure3Status = null;
		try {
			structure3Status = new StatusInfo();
			String sql = sqlProperties.getMessage(
					ContextualConstantsIF.SQLS.INSERT_STRUCTURE3_SQL, null,
					null);

			if (structureList != null && !structureList.isEmpty()) {

				for (String structure1 : structureList) {
					Map<String, Object> paramMap = new HashMap<String, Object>();
					paramMap.put(
							ContextualConstantsIF.DatabaseColumnsKeys.STRUCTURE_KEY,
							structure1);
					namedJdbcTemplate.update(sql, paramMap);
				}

			}

			structure3Status.setStatus(true);
			return structure3Status;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			structure3Status = new StatusInfo();
			structure3Status.setErrMsg(e.getMessage());
			structure3Status.setStatus(false);
			return structure3Status;

		}
	}

	@Override
	public StatusInfo deleteAllFreq() {
		StatusInfo statusInfo = null;

		try {
			statusInfo = new StatusInfo();
			/*String sql = sqlProperties.getMessage(
					ContextualConstantsIF.SQLS.DELETE_ALL_FREQ_SQL, null, null);*/
			
			db.getCollection("freqcomputation").drop();
			/*jdbcTemplate.update(sql);*/
			statusInfo.setStatus(true);
			return statusInfo;
		} catch (Exception e) {
			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			System.out.println("Exception" + e);
			e.printStackTrace();
			return statusInfo;
		}
	}

	@Override
	public List<String> retriveDiffrentConcepts() {
		try {
			/*String sql = sqlProperties.getMessage(
					ContextualConstantsIF.SQLS.RETRIVE_TYPES_ONLY_SQL, null,
					null);*/
			
			DBObject project = new BasicDBObject("$project", new BasicDBObject("TYPE",1));
			
			
			List conditionPipeLine = Arrays.asList(project);
			
			List<String> TYPE_LIST = new ArrayList<String>();
			
			for(Document tempMap : db.getCollection("types").aggregate(conditionPipeLine)){
				TYPE_LIST.add(tempMap.getString("TYPE"));
			}
			
			return TYPE_LIST;
			
			//return jdbcTemplate.queryForList(sql, String.class);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<String> retriveAllKeyPhraseOnlyForConcept(String type) {
		try {
			String sql = sqlProperties
					.getMessage(
							ContextualConstantsIF.SQLS.RETRIVE_KEYPHRASE_WHERE_TYPE_SQL,
							null, null);

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put(ContextualConstantsIF.DatabaseColumnsKeys.TYPE_KEY,
					type);

			return namedJdbcTemplate.query(sql, paramMap,
					new KeyPhraseOnlyMapper());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<String> retriveAllStructure1Only() {
		try {
			String sql = sqlProperties.getMessage(
					ContextualConstantsIF.SQLS.RETRIVE_ALL_STRUCTURE1_ONLY_SQL,
					null, null);
			return jdbcTemplate.queryForList(sql, String.class);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<String> retriveAllStructure2Only() {
		try {
			String sql = sqlProperties.getMessage(
					ContextualConstantsIF.SQLS.RETRIVE_ALL_STRUCTURE2_ONLY_SQL,
					null, null);
			return jdbcTemplate.queryForList(sql, String.class);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<String> retriveAllStructure3Only() {
		try {
			String sql = sqlProperties.getMessage(
					ContextualConstantsIF.SQLS.RETRIVE_ALL_STRUCTURE3_ONLY_SQL,
					null, null);
			return jdbcTemplate.queryForList(sql, String.class);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<String> retrivePhraseGWhereTYPEISNOT(String type) {
		try {
			String sql = sqlProperties
					.getMessage(
							ContextualConstantsIF.SQLS.RETRIVE_ALL_PHARSEG_ONLY_WHERE_TYPE_SQL,
							null, null);

			Map<String, Object> map = new HashMap<String, Object>();
			map.put(ContextualConstantsIF.DatabaseColumnsKeys.TYPE_KEY, type);

			return namedJdbcTemplate.query(sql, map, new PhraseGonlyMapper());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	private final class PhraseGonlyMapper implements RowMapper {

		public String mapRow(ResultSet rs, int arg1) throws SQLException {

			return rs
					.getString(ContextualConstantsIF.DatabaseColumns.PHASEG_COL);
		}

	}

	@Override
	public StatusInfo insertFreqComputation(FreqComputation freqComputation) {
		StatusInfo cleanDetailStatus = null;
		try {
			cleanDetailStatus = new StatusInfo();
			/*String sql = sqlProperties.getMessage(
					ContextualConstantsIF.SQLS.INSERT_FREQCOMPUTATION_SQL,
					null, null);*/
			
			
			Integer FREQ_ID = 1;
			
			DBObject project = new BasicDBObject("$project", new BasicDBObject("FREQID",1));
			
			DBObject sort = new BasicDBObject("$sort",new BasicDBObject("FREQID",-1));
			
			DBObject limit = new BasicDBObject("$limit",1);
			
			
			List conditionPipeLine = Arrays.asList(sort, project, limit);
			
			for(Document tempMap : db.getCollection("article").aggregate(conditionPipeLine)){
				FREQ_ID = tempMap.getInteger("FREQID");
			}
			

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put(
					ContextualConstantsIF.DatabaseColumnsKeys.ARTICLENAME_KEY,
					freqComputation.getArticleName());
			paramMap.put("FREQ", freqComputation.getFreq());
			paramMap.put("TOKENNAME", freqComputation.getTokenName());
			paramMap.put("FREQID", ++FREQ_ID);
			
			MongoCollection<Document> collection = db.getCollection("frequency");
			
			collection.insertOne(new Document(paramMap));

			//namedJdbcTemplate.update(sql, paramMap);

			cleanDetailStatus.setStatus(true);
			return cleanDetailStatus;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			cleanDetailStatus = new StatusInfo();
			cleanDetailStatus.setErrMsg(e.getMessage());
			cleanDetailStatus.setStatus(false);
			return cleanDetailStatus;

		}
	}

	@Override
	public List<FreqComputation> viewFreq() {
		try {

			/*String sql = sqlProperties.getMessage(
					ContextualConstantsIF.SQLS.RETRIVE_FREQCOMPUTATION_SQL,
					null, null);*/
			
			DBObject project = new BasicDBObject("$project", new BasicDBObject("ARTICLENAME",1).append("FREQ", 1).append("FREQID", 1).append("TOKENNAME", 1));
			
			List conditionPipeLine = Arrays.asList(project);
			
			List<FreqComputation> freqComputationList = new ArrayList<FreqComputation>();
			
			for(Document tempMap : db.getCollection("frequency").aggregate(conditionPipeLine)){
				FreqComputation freqComputation = new FreqComputation();

				freqComputation
						.setArticleName(tempMap
								.getString(ContextualConstantsIF.DatabaseColumns.ARTICLENAME_COL));

				freqComputation.setFreq(tempMap.getInteger("FREQ"));
				freqComputation.setFreqId(tempMap.getInteger("FREQID"));
				freqComputation.setTokenName(tempMap.getString("TOKENNAME"));
				
				freqComputationList.add(freqComputation);
			}
			
			return freqComputationList;
			
			//return jdbcTemplate.query(sql, new FreqComputationVOMapper());
		} catch (Exception e) {
			System.out.println("Exception" + e);
			return null;
		}
	}

	private final class FreqComputationVOMapper implements
			RowMapper<FreqComputation> {

		public FreqComputation mapRow(ResultSet rs, int arg1)
				throws SQLException {

			FreqComputation freqComputation = new FreqComputation();

			freqComputation
					.setArticleName(rs
							.getString(ContextualConstantsIF.DatabaseColumns.ARTICLENAME_COL));

			freqComputation.setFreq(rs.getInt("FREQ"));
			freqComputation.setFreqId(rs.getInt("FREQID"));
			freqComputation.setTokenName(rs.getString("TOKENNAME"));

			return freqComputation;

		}

	}

	@Override
	public List<String> retriveDistincetTokensForArticleName(
			String articleNameLeft) {
		try {
			/*String sql = sqlProperties
					.getMessage(
							ContextualConstantsIF.SQLS.RETRIVE_DINSTINCT_TOKENS_FOR_ARTICLENAME_SQL,
							null, null);*/
			
			
			DBObject groupIdFields = new BasicDBObject("_id", "$TOKENNAME");

		    groupIdFields.put("count", new BasicDBObject("$sum", 1));
		    DBObject group = new BasicDBObject("$group", groupIdFields);

		    DBObject projectFields = new BasicDBObject("_id", 0);
		    projectFields.put("TOKENNAME", "$_id");
		    projectFields.put("count", 1);
		    DBObject project = new BasicDBObject("$project", projectFields);
		    
		    DBObject match = new BasicDBObject("$match", new BasicDBObject("ARTICLENAME",articleNameLeft));
		    
		    List conditionPipeLine = Arrays.asList(group,project);
			
			List<String> tokenName = new ArrayList<String>();

			for(Document tempMap : db.getCollection("tokens").aggregate(conditionPipeLine)){
				
				tokenName.add((String) tempMap.get("TOKENNAME"));
				
			}
			
			return tokenName;

			/*Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put(
					ContextualConstantsIF.DatabaseColumnsKeys.ARTICLENAME_KEY,
					articleNameLeft);

			return namedJdbcTemplate
					.query(sql, paramMap, new TokenOnlyMapper());*/

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	private final class TokenOnlyMapper implements RowMapper<String> {

		public String mapRow(ResultSet rs, int arg1) throws SQLException {
			return rs
					.getString(ContextualConstantsIF.DatabaseColumns.TOKENNAME_COL);
		}

	}

	@Override
	public List<String> retriveKeyPhraseOnlyForTokensAndType(
			List<String> tokenNamesLeft, String typeCombo) {
		try {
			String sql = sqlProperties
					.getMessage(
							ContextualConstantsIF.SQLS.RETRIVE_DINSTINCT_KEYPHRASES_FOR_TOKENS_AND_TYPE_SQL,
							null, null);

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put(ContextualConstantsIF.DatabaseColumnsKeys.TYPE_KEY,
					typeCombo);
			paramMap.put(
					ContextualConstantsIF.DatabaseColumnsKeys.KEYPHARSELIST_KEY,
					tokenNamesLeft);

			return namedJdbcTemplate.query(sql, paramMap,
					new KeyPharseOnlyInMapper());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	private final class KeyPharseOnlyInMapper implements RowMapper<String> {

		public String mapRow(ResultSet rs, int arg1) throws SQLException {
			return rs
					.getString(ContextualConstantsIF.DatabaseColumns.KEYPHARSE_COL);
		}

	}

	@Override
	public int retriveCountForTokenAndArticleName(String word,
			String articleNameLeft) {

		int count = 0;
		try {

			/*String sql = sqlProperties
					.getMessage(
							ContextualConstantsIF.SQLS.SELECT_COUNT_FOR_TOKENNAME_ARTICLENAME_SQL,
							null, null);*/
			
			
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put(
					ContextualConstantsIF.DatabaseColumnsKeys.TOKENNAME_KEY,
					"$"+ContextualConstantsIF.DatabaseColumnsKeys.TOKENNAME_KEY);
			paramMap.put(
					ContextualConstantsIF.DatabaseColumnsKeys.ARTICLENAME_KEY,
					"$"+ContextualConstantsIF.DatabaseColumnsKeys.ARTICLENAME_KEY);

			
			DBObject groupIdFields = new BasicDBObject("_id", paramMap);

		    groupIdFields.put("count", new BasicDBObject("$sum", 1));
		    DBObject group = new BasicDBObject("$group", groupIdFields);

		    DBObject projectFields = new BasicDBObject("_id", 0);
		    projectFields.put("count", 1);
		    DBObject project = new BasicDBObject("$project", projectFields);
		    
		    DBObject match = new BasicDBObject("$match",new BasicDBObject(ContextualConstantsIF.DatabaseColumnsKeys.TOKENNAME_KEY,word)).append(ContextualConstantsIF.DatabaseColumnsKeys.ARTICLENAME_KEY, articleNameLeft);
		    
		    List conditionPipeLine = Arrays.asList(match, group,project);
			
			List<String> tokenName = new ArrayList<String>();

			for(Document tempMap : db.getCollection("tokens").aggregate(conditionPipeLine)){
				
				count = tempMap.getInteger("count");
				
			}
			
		
			return count;
			//return namedJdbcTemplate.queryForInt(sql, paramMap);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());

		}

		return count;
	}

	@Override
	public int findNoOfTokensForArticleName(String articleNameLeft) {
		int count = 0;
		try {

			/*String sql = sqlProperties
					.getMessage(
							ContextualConstantsIF.SQLS.SELECT_COUNT_FOR_ARTICLENAME_SQL,
							null, null);*/
			
			
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put(
					ContextualConstantsIF.DatabaseColumnsKeys.ARTICLENAME_KEY,
					"$"+ContextualConstantsIF.DatabaseColumnsKeys.ARTICLENAME_KEY);

			DBObject groupIdFields = new BasicDBObject("_id", paramMap);

		    groupIdFields.put("count", new BasicDBObject("$sum", 1));
		    DBObject group = new BasicDBObject("$group", groupIdFields);

		    DBObject projectFields = new BasicDBObject("_id", 0);
		    projectFields.put("count", 1);
		    DBObject project = new BasicDBObject("$project", projectFields);
		    
		    DBObject match = new BasicDBObject("$match", new BasicDBObject(ContextualConstantsIF.DatabaseColumnsKeys.ARTICLENAME_KEY,articleNameLeft));
		    
		    List conditionPipeLine = Arrays.asList(match, group,project);
			
			List<String> tokenName = new ArrayList<String>();

			for(Document tempMap : db.getCollection("tokens").aggregate(conditionPipeLine)){
				
				count = tempMap.getInteger("count");
				
			}

			
			return count;
			
			//return namedJdbcTemplate.queryForInt(sql, paramMap);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());

		}

		return count;
	}

	@Override
	public List<FreqComputation> retriveFreqComputationForArticleName(
			String articleNameLeft) {
		try {

			/*String sql = sqlProperties
					.getMessage(
							ContextualConstantsIF.SQLS.RETRIVE_FREQCOMPUTATION_WHERE_ARTICLENAME_SQL,
							null, null);*/
			
			DBObject project = new BasicDBObject("$project", new BasicDBObject("FREQ",1).append("FREQID", 1).append("TOKENNAME", 1));
			
			DBObject match = new BasicDBObject("$match", new BasicDBObject(ContextualConstantsIF.DatabaseColumnsKeys.ARTICLENAME_KEY,articleNameLeft));
			
			List conditionPipeLine = Arrays.asList(match, project);
			
			List<FreqComputation> reqComputationList = new ArrayList<FreqComputation>();
			
			for(Document tempMap : db.getCollection("freqcomputation").aggregate(conditionPipeLine)){
				FreqComputation freqComputation = new FreqComputation();

				freqComputation
						.setArticleName(tempMap
								.getString(ContextualConstantsIF.DatabaseColumns.ARTICLENAME_COL));

				freqComputation.setFreq(tempMap.getInteger("FREQ"));
				freqComputation.setFreqId(tempMap.getInteger("FREQID"));
				freqComputation.setTokenName(tempMap.getString("TOKENNAME"));
				
				reqComputationList.add(freqComputation);

			}
			

			return reqComputationList;
			
			/*Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put(
					ContextualConstantsIF.DatabaseColumnsKeys.ARTICLENAME_KEY,
					articleNameLeft);

			return namedJdbcTemplate.query(sql, paramMap,
					new FreqComputationVOMapper());*/
		} catch (Exception e) {
			System.out.println("Exception" + e);
			return null;
		}
	}

	@Override
	public StatusInfo insertAdjectiveNLP(String adjective) {
		StatusInfo insertStopWordStatus = null;
		try {
			insertStopWordStatus = new StatusInfo();
			String sql = sqlProperties.getMessage(
					ContextualConstantsIF.SQLS.INSERT_ADJECTIVES_NLP_SQL, null,
					null);
			System.out.println("SQL----" + sql);

			jdbcTemplate.update(sql, new Object[] { adjective },
					new int[] { Types.VARCHAR });
			insertStopWordStatus.setStatus(true);
			return insertStopWordStatus;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			insertStopWordStatus = new StatusInfo();
			insertStopWordStatus.setErrMsg(e.getMessage());
			insertStopWordStatus.setStatus(false);
			return insertStopWordStatus;

		}
	}

	public List<String> retriveAdjectivesForNLP() {
		try {
			String sql = sqlProperties.getMessage(
					ContextualConstantsIF.SQLS.RETRIVE_ADJECTIVESNLP_SQL, null,
					null);
			return jdbcTemplate.queryForList(sql, String.class);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<AdjectiveVO> retriveAllFullNLPAdjectives() {
		List<AdjectiveVO> stopWordList = null;
		try {
			String sql = sqlProperties.getMessage(
					ContextualConstantsIF.SQLS.RETRIVE_ADJECTIVESNLP_FULL_SQL,
					null, null);

			Map<String, Object> paramMap = null;

			return namedJdbcTemplate.query(sql, paramMap,
					new AdjectiveVOMapper());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<FreqComputation> retriveFreqComputationForArticleNameAndType(
			String articleNameLeft, String typeCombo) {
		try {

			String sql = sqlProperties
					.getMessage(
							ContextualConstantsIF.SQLS.RETRIVE_FREQCOMPUTATION_WHERE_ARTICLENAME_AND_TYPE_SQL,
							null, null);

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put(
					ContextualConstantsIF.DatabaseColumnsKeys.ARTICLENAME_KEY,
					articleNameLeft);
			paramMap.put(ContextualConstantsIF.DatabaseColumnsKeys.TYPE_KEY,
					typeCombo);

			return namedJdbcTemplate.query(sql, paramMap,
					new FreqComputationVOMapper());
		} catch (Exception e) {
			System.out.println("Exception" + e);
			return null;
		}
	}

	@Override
	public int retriveLoginType(String userId) {
		try {
			/*String sql = sqlProperties
					.getMessage(
							ContextualConstantsIF.DATABASESQL.RETRIVE_LOGINTYPE_WHERE_USERID_SQL,
							null, null);*/
			
			DBObject match = new BasicDBObject("$match", new BasicDBObject("USN",userId));
			
			DBObject project = new BasicDBObject("$project", new BasicDBObject("LOGINTYPE",1));
			
			List conditionPipeLine = Arrays.asList(match, project);
				
			Integer loginType = null;
			
			for(Document tempMap : db.getCollection("login").aggregate(conditionPipeLine)){
				loginType = tempMap.getInteger("LOGINTYPE");
			}
			//return jdbcTemplate.queryForList(sql, String.class);
			
			return loginType;
			
			//return jdbcTemplate.queryForList(sql, Integer.class, userId).get(0);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return -1;
		}
	}

	@Override
	public List<String> retriveUserIds() {
		try {
			/*String sql = sqlProperties
					.getMessage(
							ContextualConstantsIF.DATABASESQL.RETRIVE_REGISTER_USERIDS_SQL,
							null, null);*/
			//DBObject match = new BasicDBObject("$match", new BasicDBObject("USN",map.get("USN")));
			
			DBObject project = new BasicDBObject("$project", new BasicDBObject("USN",1));
			
			List conditionPipeLine = Arrays.asList(project);
			
			List<String> userList = new ArrayList<String>();
			
			for(Document tempMap : db.getCollection("login").aggregate(conditionPipeLine)){
				userList.add(tempMap.getString("USN"));
			}
			//return jdbcTemplate.queryForList(sql, String.class);
			return userList;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	@Override
	public String retrivePassword(String userId) {
		try {
			/*String sql = sqlProperties
					.getMessage(
							ContextualConstantsIF.DATABASESQL.RETRIVE_PASSWORD_WHERE_USERID_SQL,
							null, null);*/
			
			DBObject match = new BasicDBObject("$match", new BasicDBObject("USN",userId));
			
			DBObject project = new BasicDBObject("$project", new BasicDBObject("PASSWORD",1));
			
			List conditionPipeLine = Arrays.asList(match, project);
				
			String userPassword = null;
			
			for(Document tempMap : db.getCollection("login").aggregate(conditionPipeLine)){
				userPassword = tempMap.getString("PASSWORD");
			}
			//return jdbcTemplate.queryForList(sql, String.class);
			
			return userPassword;

			//return jdbcTemplate.queryForList(sql, String.class, userId).get(0);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	// INSERT INTO
	// LOGIN(NAME,PASSWORD,EMAIL,GENDER,USN,DEGREE,SPECIFICATION,LOGINTYPE,ADDRESS,CITY,
	// STATE,PINCODE,FATHERNAME,FATHERNUMBER,MOTHERNAME,MOTHERNUMBER,FATHEREMAILID,MOTHERMAILID,
	// LOCALGUARDIANMAIL,LOCALGUARDIANNAME,LOCALGUARDIANNUMBER,ADMISSIONTYPE,CHALLANNUMBER,FEEPAID,RESIDENTIALADDRESS,RESIDENTIALSTATUS,SEMESTERMARKS)
	// VALUES(:NAME,:PASSWORD,:EMAIL,:GENDER,:USN,:DEGREE,:SPECIFICATION,:LOGINTYPE,:ADDRESS,:CITY,:STATE,:PINCODE,:FATHERNAME,
	// :FATHERNUMBER,:MOTHERNAME,:MOTHERNUMBER,:FATHEREMAILID,:MOTHERMAILID,:LOCALGUARDIANMAIL,:LOCALGUARDIANNAME,
	// :LOCALGUARDIANNUMBER,:ADMISSIONTYPE,:CHALLANNUMBER,:FEEPAID,:RESIDENTIALADDRESS,:RESIDENTIALSTATUS,:SEMESTERMARKS)

	@Override
	public StatusInfo insertLogin(RegisterUser registerUser) {
		StatusInfo insertLoginStatus = null;
		try {
			insertLoginStatus = new StatusInfo();
			/*String sql = sqlProperties.getMessage(
					ContextualConstantsIF.DATABASESQL.INSERT_LOGIN_SQL, null,
					null);*/
			
			boolean status = true;

			Map<String, Object> paramMap = populateRegister(registerUser);
			
			MongoCollection<Document> collection = db.getCollection("login");
			
			collection.insertOne(new Document(paramMap));
			
			//if(getStatus(paramMap)){
				//db.getCollection("login").insertOne(new Document(paramMap));
				
				insertLoginStatus.setStatus(true);
			//}
			
			
			//namedJdbcTemplate.update(sql, paramMap);
			//insertLoginStatus.setStatus(false);
			
			return insertLoginStatus;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			insertLoginStatus = new StatusInfo();
			insertLoginStatus.setErrMsg(e.getMessage());
			insertLoginStatus.setStatus(false);
			return insertLoginStatus;

		}
	}
	
	private boolean getStatus(Map<String, Object> map){
		
		boolean status = true;
		
		
		return status;
	}

	private Map<String, Object> populateRegister(RegisterUser registerUser) {
		Map<String, Object> paramMap = new HashMap<String, Object>();

		paramMap.put("NAME", registerUser.getName());
		paramMap.put("PASSWORD", registerUser.getPassword());
		paramMap.put("EMAIL", registerUser.getEmail());
		paramMap.put("GENDER", registerUser.getGender());
		paramMap.put("USN", registerUser.getUSN());
		paramMap.put("DEGREE", registerUser.getDegree());
		paramMap.put("SPECIFICATION", registerUser.getSpecification());
		paramMap.put("LOGINTYPE", registerUser.getLoginType());
		paramMap.put("ADDRESS", registerUser.getAddress());
		paramMap.put("CITY", registerUser.getCity());
		paramMap.put("STATE", registerUser.getState());
		paramMap.put("PINCODE", registerUser.getPinCode());
		paramMap.put("FATHERNAME", registerUser.getFatherName());
		paramMap.put("FATHERNUMBER", registerUser.getFatherNumber());
		paramMap.put("MOTHERNAME", registerUser.getMotherName());
		paramMap.put("MOTHERNUMBER", registerUser.getMotherNumber());
		paramMap.put("FATHEREMAILID", registerUser.getFatherEmail());
		paramMap.put("MOTHERMAILID", registerUser.getMotherEmail());
		paramMap.put("LOCALGUARDIANMAIL", registerUser.getLocalGuardEmail());
		paramMap.put("LOCALGUARDIANNAME", registerUser.getLocalGuardName());
		paramMap.put("LOCALGUARDIANNUMBER", registerUser.getLocalGuardNumber());
		paramMap.put("ADMISSIONTYPE", registerUser.getAdmissionType());
		paramMap.put("CHALLANNUMBER", registerUser.getChallanNumber());
		paramMap.put("FEEPAID", registerUser.getFeePaid());
		paramMap.put("RESIDENTIALADDRESS", registerUser.getResidentialAddress());
		paramMap.put("RESIDENTIALSTATUS", registerUser.getResidenceStatus());
		if (registerUser.getSemesterMarks() != null) {
			paramMap.put("SEMESTERMARKS",
					new Double(registerUser.getSemesterMarks()));
		} else {
			paramMap.put("SEMESTERMARKS", new Double(0));
		}
		paramMap.put("COLLEGE", registerUser.getCollege());
		paramMap.put("DEPARTMENT", registerUser.getDepartment());
		paramMap.put("SPECIFICATION", registerUser.getSpecification());
		return paramMap;
	}

	@Override
	public List<String> retriveAllUniqueArticleNamesFromTokenization() {
		try {
			/*String sql = sqlProperties.getMessage(
					ContextualConstantsIF.SQLS.RETRIVE_UNIQUE_ARTICLENAMES,
					null, null);*/
			
			DBObject groupIdFields = new BasicDBObject("_id", "$ARTICLENAME");

		    groupIdFields.put("count", new BasicDBObject("$sum", 1));
		    DBObject group = new BasicDBObject("$group", groupIdFields);

		    DBObject projectFields = new BasicDBObject("_id", 0);
		    projectFields.put("ARTICLENAME", "$_id");
		    projectFields.put("count", 1);
		    DBObject project = new BasicDBObject("$project", projectFields);
	
			
			List conditionPipeLine = Arrays.asList(group, project);
			
			List<String> articleName = new ArrayList<String>();

			for(Document tempMap : db.getCollection("tokens").aggregate(conditionPipeLine)){
				articleName.add(tempMap.getString("ARTICLENAME"));
			}
			
			return articleName;

			
			
			
			//return jdbcTemplate.queryForList(sql, String.class);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<String> retriveDistinctTokensForArticleName(String articleName) {
		try {
			/*String sql = sqlProperties
					.getMessage(
							ContextualConstantsIF.SQLS.RETRIVE_DISTINCTTOKENS_WHERE_ARTICLENAME_SQL,
							null, null);*/
			
			DBObject groupIdFields = new BasicDBObject("_id", "$TOKENNAME");

		    groupIdFields.put("count", new BasicDBObject("$sum", 1));
		    DBObject group = new BasicDBObject("$group", groupIdFields);

		    DBObject projectFields = new BasicDBObject("_id", 0);
		    projectFields.put("TOKENNAME", "$_id");
		    projectFields.put("count", 1);
		    DBObject project = new BasicDBObject("$project", projectFields);
		    
		    DBObject dbMatch = new BasicDBObject();
			dbMatch.put("ARTICLENAME", articleName);
			
			
			DBObject match = new BasicDBObject("$match",dbMatch);
			
			List conditionPipeLine = Arrays.asList(match, group, project);
			
			List<String> tokenName = new ArrayList<String>();

			for(Document tempMap : db.getCollection("tokens").aggregate(conditionPipeLine)){
				tokenName.add(tempMap.getString("TOKENNAME"));
			}
			
			return tokenName;
			//return jdbcTemplate.queryForList(sql, String.class, articleName);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	@Override
	public int retriveCountForTokenInArticle(String tokenTemp,
			String articleName) {
		try {
			/*String sql = sqlProperties
					.getMessage(
							ContextualConstantsIF.SQLS.RETRIVE_COUNT_WHERE_TOKENNAME_AND_ARTICLENAME_SQL,
							null, null);*/
			
			DBObject groupIdFields = new BasicDBObject("_id", "$TOKENNAME");

		    groupIdFields.put("count", new BasicDBObject("$sum", 1));
		    DBObject group = new BasicDBObject("$group", groupIdFields);

		    DBObject projectFields = new BasicDBObject("_id", 0);
		    //projectFields.put("TOKENNAME", "$_id");
		    projectFields.put("count", 1);
		    DBObject project = new BasicDBObject("$project", projectFields);
		    
		    DBObject dbMatch = new BasicDBObject();
			dbMatch.put("ARTICLENAME", articleName);
			dbMatch.put("TOKENNAME", tokenTemp);
			
			
			DBObject match = new BasicDBObject("$match",dbMatch);
			
			List conditionPipeLine = Arrays.asList(match, group, project);
			
			Integer count = 1;

			for(Document tempMap : db.getCollection("tokens").aggregate(conditionPipeLine)){
				count=tempMap.getInteger("count");
			}

			
			return count;
			
			
			//return jdbcTemplate.queryForInt(sql, tokenTemp, articleName);
		} catch (Exception e) {
			System.out.println("Exception" + e);
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public StatusInfo insertFrequency(FrequencyVO freqVO) {
		StatusInfo freqStatus = null;
		try {
			freqStatus = new StatusInfo();
			/*String sql = sqlProperties
					.getMessage(
							ContextualConstantsIF.SQLS.INSERT_FREQUENCY_SQL,
							null, null);*/
			
			Integer frequencyId=1;
			
			try{
				DBObject project = new BasicDBObject("$project", new BasicDBObject("FREQID",1));
				
				DBObject sort = new BasicDBObject("$sort",new BasicDBObject("FREQID",-1));
				
				DBObject limit = new BasicDBObject("$limit",1);
				
				
				List conditionPipeLine = Arrays.asList(sort, project, limit);
				
				List<String> userList = new ArrayList<String>();
				
				for(Document tempMap : db.getCollection("frequency").aggregate(conditionPipeLine)){
					frequencyId = tempMap.getInteger("FREQID");
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
			
			
			Map<String, Object> paramMap = new HashMap<String, Object>();
			
			paramMap.put("FREQID", ++frequencyId);
			paramMap.put("ARTICLENAME", freqVO.getArticleName());
			paramMap.put("TOKENNAME", freqVO.getTokenName());
			paramMap.put("FREQ", freqVO.getFreq());
			
			
			MongoCollection<Document> collection = db.getCollection("frequency");
			
			collection.insertOne(new Document(paramMap));
			
			

			/*jdbcTemplate.update(sql, new Object[] { freqVO.getArticleName(),
					freqVO.getTokenName(), freqVO.getFreq() }, new int[] {
					Types.VARCHAR, Types.VARCHAR, Types.INTEGER });*/
			freqStatus.setStatus(true);
			return freqStatus;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			freqStatus = new StatusInfo();
			freqStatus.setErrMsg(e.getMessage());
			freqStatus.setStatus(false);
			return freqStatus;

		}
	}

	@Override
	public List<String> retriveUniqueArticleNamesFromFrequency() {
		try {
			/*String sql = sqlProperties.getMessage(
					ContextualConstantsIF.SQLS.RETRIVE_UNIQUE_ARTICLENAMES_SQL,
					null, null);*/
			
			DBObject groupIdFields = new BasicDBObject("_id", "$ARTICLENAME");

		    groupIdFields.put("count", new BasicDBObject("$sum", 1));
		    DBObject group = new BasicDBObject("$group", groupIdFields);

		    DBObject projectFields = new BasicDBObject("_id", 0);
		    projectFields.put("ARTICLENAME", "$_id");
		    projectFields.put("count", 1);
		    DBObject project = new BasicDBObject("$project", projectFields);
		    
		    
			List conditionPipeLine = Arrays.asList(group, project);
			
			List<String> articleName = new ArrayList<String>();

			for(Document tempMap : db.getCollection("frequency").aggregate(conditionPipeLine)){
				articleName.add(tempMap.getString("ARTICLENAME"));
			}

			return articleName;
			/*return namedJdbcTemplate.query(sql, map,
					new UniqueArticleNamesMapper());*/

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	private final class UniqueArticleNamesMapper implements RowMapper {

		public String mapRow(ResultSet rs, int arg1) throws SQLException {

			return rs.getString("ARTICLENAME");
		}

	}

	@Override
	public List<String> retriveUniqueTokensFromFrequencyForArticle(
			String articleName) {
		try {
			/*String sql = sqlProperties
					.getMessage(
							ContextualConstantsIF.SQLS.RETRIVE_UNIQUE_TOKENS_FROM_FREQ_WHERE_ARTICLENAME_SQL,
							null, null);*/
			
			
			DBObject groupIdFields = new BasicDBObject("_id", "$TOKENNAME");

		    groupIdFields.put("count", new BasicDBObject("$sum", 1));
		    DBObject group = new BasicDBObject("$group", groupIdFields);

		    DBObject projectFields = new BasicDBObject("_id", 0);
		    projectFields.put("TOKENNAME", "$_id");
		    projectFields.put("count", 1);
		    DBObject project = new BasicDBObject("$project", projectFields);
		    
		    DBObject dbMatch = new BasicDBObject();
			dbMatch.put("ARTICLENAME", articleName);
			
			
			DBObject match = new BasicDBObject("$match",dbMatch);
			
			List conditionPipeLine = Arrays.asList(match, group, project);
			
			List<String> articleNameList = new ArrayList<String>();

			for(Document tempMap : db.getCollection("frequency").aggregate(conditionPipeLine)){
				articleNameList.add(tempMap.getString("TOKENNAME"));
			}

			
			return articleNameList;
			


			/*Map<String, Object> map = new HashMap<String, Object>();

			map.put("articleName", articleName);

			return namedJdbcTemplate.query(sql, map,
					new UniqueArticleNamesMapper());*/

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<FrequencyVO> retriveUniqueTokensInfoFromFrequencyForArticle(
			String articleName) {
		try {
			/*String sql = sqlProperties
					.getMessage(
							ContextualConstantsIF.SQLS.RETRIVE_UNIQUE_FREQINFO_FROM_FREQ_WHERE_ARTICLENAME_SQL,
							null, null);*/
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("TOKENNAME", "$TOKENNAME");
			map.put("FREQ", "$FREQ");
			
			DBObject groupIdFields = new BasicDBObject("_id", map);

		    groupIdFields.put("count", new BasicDBObject("$sum", 1));
		    DBObject group = new BasicDBObject("$group", groupIdFields);

		    DBObject projectFields = new BasicDBObject("_id", 0);
		    projectFields.put("TOKENNAME", "$_id.TOKENNAME");
		    projectFields.put("FREQ", "$_id.FREQ");
		    projectFields.put("count", 1);
		    DBObject project = new BasicDBObject("$project", projectFields);
		    
		    DBObject dbMatch = new BasicDBObject();
			dbMatch.put("ARTICLENAME", articleName);
			
			
			DBObject match = new BasicDBObject("$match",dbMatch);
			
			List conditionPipeLine = Arrays.asList(match, group, project);
			
			List<FrequencyVO> frequencyList = new ArrayList<FrequencyVO>();

			for(Document tempMap : db.getCollection("frequency").aggregate(conditionPipeLine)){
				FrequencyVO frquency = new FrequencyVO();
				
				frquency.setTokenName(tempMap.getString("TOKENNAME"));
				frquency.setFreq(tempMap.getInteger("FREQ"));
				
				frequencyList.add(frquency);
			}

			
			return frequencyList;

			/*Map<String, Object> map = new HashMap<String, Object>();

			map.put("articleName", articleName);

			return (List<FrequencyVO>) namedJdbcTemplate.query(sql, map,
					new FrequencyVOMapper());*/

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	private final class FrequencyVOMapper implements RowMapper<FrequencyVO> {

		public FrequencyVO mapRow(ResultSet rs, int arg1) throws SQLException {

			FrequencyVO freqVO = new FrequencyVO();

			freqVO.setFreq(rs.getInt("FREQ"));
			freqVO.setTokenName(rs.getString("TOKENNAME"));

			return freqVO;

		}

	}

	// SELECT DISTINCT SUM(FREQ) FROM FREQUENCY WHERE ARTICLENAME
	// IN(:articleName) AND TOKENNAME =:tokenName

	@Override
	public int computeFreqOtherTokensForNotTokenNameAndArticleName(
			String tokenName, String articleName) {
		
		Integer count = 0;
		
		try {
			/*String sql = sqlProperties
					.getMessage(
							ContextualConstantsIF.SQLS.RETRIVE_COUNTOTHERS_WHERE_TOKENNAME_AND_ARTICLENAME_SQL,
							null, null);*/
			
			DBObject groupIdFields = new BasicDBObject("_id", "$FREQ");

		    groupIdFields.put("count", new BasicDBObject("$sum", 1));
		    DBObject group = new BasicDBObject("$group", groupIdFields);

		    DBObject projectFields = new BasicDBObject("_id", 0);
		    projectFields.put("FREQ", 1);
		    //projectFields.put("count", 1);
		    DBObject project = new BasicDBObject("$project", projectFields);
		    
		    DBObject dbMatch = new BasicDBObject();
			dbMatch.put("ARTICLENAME", articleName);
			dbMatch.put("TOKENNAME", new BasicDBObject("$ne", tokenName));
			
			
			DBObject match = new BasicDBObject("$match",dbMatch);
			
			List conditionPipeLine = Arrays.asList(match, project);
			
			List<FrequencyVO> frequencyList = new ArrayList<FrequencyVO>();

			for(Document tempMap : db.getCollection("frequency").aggregate(conditionPipeLine)){
				count += tempMap.getInteger("FREQ");
			}

			
			return count;


			
			
			//return jdbcTemplate.queryForInt(sql, tokenName, articleName);
		} catch (Exception e) {
			System.out.println("Exception" + e);
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public int computeTotalFreq(String articleName) {
		try {
			/*String sql = sqlProperties
					.getMessage(
							ContextualConstantsIF.SQLS.RETRIVE_COUNTALL_WHERE_ARTICLENAME_SQL,
							null, null);*/
			
			
			Integer count = 1;
			
			DBObject groupIdFields = new BasicDBObject("_id", "$FREQ");

		    groupIdFields.put("count", new BasicDBObject("$sum", 1));
		    DBObject group = new BasicDBObject("$group", groupIdFields);

		    DBObject projectFields = new BasicDBObject("_id", 0);
		    projectFields.put("FREQ", 1);
		    //projectFields.put("count", 1);
		    DBObject project = new BasicDBObject("$project", projectFields);
		    
		    DBObject dbMatch = new BasicDBObject();
			dbMatch.put("ARTICLENAME", articleName);
			//dbMatch.put("TOKENNAME", new BasicDBObject("$ne", tokenName));
			
			
			DBObject match = new BasicDBObject("$match",dbMatch);
			
			List conditionPipeLine = Arrays.asList(match, project);
			
			List<FrequencyVO> frequencyList = new ArrayList<FrequencyVO>();

			for(Document tempMap : db.getCollection("frequency").aggregate(conditionPipeLine)){
				count += tempMap.getInteger("FREQ");
			}

			
			return count;


			/*return jdbcTemplate.queryForInt(sql, articleName);*/
		} catch (Exception e) {
			System.out.println("Exception" + e);
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public List<Double> computeFreqListForArticleNameAndTokenName(
			String articleName, String tokenName) {
		List<Double> frquencyList = new ArrayList<Double>();
			/*String sql = sqlProperties
					.getMessage(
							ContextualConstantsIF.SQLS.RETRIVE_FREQ_WHERE_ARTICLENAME_SQL,
							null, null);*/
			
			
			
			try{
				DBObject project = new BasicDBObject("$project", new BasicDBObject("FREQ",1));
				
				DBObject match = new BasicDBObject("$match",new BasicDBObject("ARTICLENAME",articleName).append("TOKENNAME",new BasicDBObject("$ne",tokenName)));
				
				//DBObject limit = new BasicDBObject("$limit",1);
				
				
				List conditionPipeLine = Arrays.asList(match, project);
				
				
				
				for(Document tempMap : db.getCollection("frequency").aggregate(conditionPipeLine)){
					frquencyList.add((tempMap.getInteger("FREQ")).doubleValue());
				}
			
			
			return frquencyList;
			

			/*Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("articleName", articleName);
			paramMap.put("tokenName", tokenName);

			return namedJdbcTemplate.query(sql, paramMap,
					new FreqMapperDouble());*/

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	private final class FreqMapperDouble implements RowMapper {

		public Double mapRow(ResultSet rs, int arg1) throws SQLException {

			return rs.getDouble("FREQ");
		}

	}

	// INSERT INTO
	// loglikelihood(LOGLIKELIHOODV,EXPECTEDFREQUENCY,FREQ,TOKENNAME,ARTICLENAME)
	// VALUES(:LOGLIKELIHOODV,:EXPECTEDFREQUENCY,:FREQ,:TOKENNAME,:ARTICLENAME)
	// INSERT INTO
	// loglikelihood(LOGLIKELIHOODV,EXPECTEDFREQUENCY,FREQ,TOKENNAME,ARTICLENAME)
	// VALUES(:logLikelihood,:expectedFrequency,:freq,:tokenName,:articleName)

	@Override
	public StatusInfo insertLogLikelihood(
			List<LogLikelihoodVO> logLikeliHoodVOList) {
		StatusInfo statusInfo = new StatusInfo();
		
		Integer LOG_LIKELIHOOD_ID = 1;
		
		
		DBObject project = new BasicDBObject("$project", new BasicDBObject("LOGLIKELIHOODID",1));
		
		DBObject sort = new BasicDBObject("$sort",new BasicDBObject("LOGLIKELIHOODID",-1));
		
		DBObject limit = new BasicDBObject("$limit",1);
		
		
		List conditionPipeLine = Arrays.asList(sort, project, limit);
		
		List<String> userList = new ArrayList<String>();
		
		for(Document tempMap : db.getCollection("loglikelihood").aggregate(conditionPipeLine)){
			LOG_LIKELIHOOD_ID = tempMap.getInteger("LOGLIKELIHOODID");
		}
		

		/*String sql = sqlProperties
				.getMessage(
						ContextualConstantsIF.SQLS.INSERT_LOGLIKELIHOOD_SQL,
						null, null);*/

		if (logLikeliHoodVOList != null && !logLikeliHoodVOList.isEmpty()) {

			List<Document> keyList = new ArrayList<Document>();
			
			try {
				for (LogLikelihoodVO logLikelihoodVO : logLikeliHoodVOList) {

					Map<String, Object> paramMap = new HashMap<String, Object>();

					paramMap.put("LOGLIKELIHOODID", ++LOG_LIKELIHOOD_ID);
					
					paramMap.put("ARTICLENAME",
							logLikelihoodVO.getArticleName());
					paramMap.put("LOGLIKELIHOODV",
							logLikelihoodVO.getLogLikelihood());
					paramMap.put("EXPECTEDFREQUENCY",
							logLikelihoodVO.getExpectedFrequency());
					paramMap.put("FREQ", logLikelihoodVO.getFreq());
					paramMap.put("TOKENNAME", logLikelihoodVO.getTokenName());

					keyList.add(new Document(paramMap));
					
					//namedJdbcTemplate.update(sql, paramMap);
				}
				
				MongoCollection<Document> collection = db.getCollection("loglikelihood");
				
				collection.insertMany(keyList);
			} catch (Exception e) {

				statusInfo.setStatus(false);
				statusInfo.setErrMsg(e.getMessage());
				return statusInfo;
			}

		}

		statusInfo.setStatus(true);

		return statusInfo;
	}

	@Override
	public List<LogLikelihoodVO> retriveLogLikelihoodFromDB() {
		List<LogLikelihoodVO> logLikelihoodFunction = new ArrayList<LogLikelihoodVO>();
		try {
			/*String sql = sqlProperties.getMessage(
					ContextualConstantsIF.SQLS.RETRIVE_LOGLIKELIHOOD_FULL_SQL,
					null, null);*/
			
			DBObject project = new BasicDBObject("$project", new BasicDBObject("ARTICLENAME",1).append("EXPECTEDFREQUENCY", 1).append("FREQ", 1).append("LOGLIKELIHOODV", 1).append("LOGLIKELIHOODID", 1).append("TOKENNAME", 1));
			
			List conditionPipeLine = Arrays.asList(project);
			
			
			for(Document tempMap : db.getCollection("loglikelihood").aggregate(conditionPipeLine)){
				LogLikelihoodVO logLikelihoodVO = new LogLikelihoodVO();

				logLikelihoodVO.setArticleName(tempMap.getString("ARTICLENAME"));
				logLikelihoodVO.setExpectedFrequency(tempMap
						.getDouble("EXPECTEDFREQUENCY"));
				logLikelihoodVO.setFreq(tempMap.getDouble("FREQ"));
				logLikelihoodVO.setLogLikelihood(tempMap.getDouble("LOGLIKELIHOODV"));
				logLikelihoodVO.setLogLikelihoodId(tempMap.getInteger("LOGLIKELIHOODID"));
				logLikelihoodVO.setTokenName(tempMap.getString("TOKENNAME"));
				
				logLikelihoodFunction.add(logLikelihoodVO);

			}
			
			
			return logLikelihoodFunction;
			//return jdbcTemplate.query(sql, new LogLikelihoodVOMapper());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	private final class LogLikelihoodVOMapper implements
			RowMapper<LogLikelihoodVO> {

		public LogLikelihoodVO mapRow(ResultSet rs, int arg1)
				throws SQLException {

			LogLikelihoodVO logLikelihoodVO = new LogLikelihoodVO();

			logLikelihoodVO.setArticleName(rs.getString("ARTICLENAME"));
			logLikelihoodVO.setExpectedFrequency(rs
					.getDouble("EXPECTEDFREQUENCY"));
			logLikelihoodVO.setFreq(rs.getDouble("FREQ"));
			logLikelihoodVO.setLogLikelihood(rs.getDouble("LOGLIKELIHOODV"));
			logLikelihoodVO.setLogLikelihoodId(rs.getInt("LOGLIKELIHOODID"));
			logLikelihoodVO.setTokenName(rs.getString("TOKENNAME"));

			return logLikelihoodVO;

		}

	}

	private final class LogLikelihoodTempVOMapper implements
			RowMapper<LogLikelihoodVO> {

		public LogLikelihoodVO mapRow(ResultSet rs, int arg1)
				throws SQLException {

			LogLikelihoodVO logLikelihoodVO = new LogLikelihoodVO();

			logLikelihoodVO.setArticleName(rs.getString("ARTICLENAME"));
			logLikelihoodVO.setExpectedFrequency(rs
					.getDouble("EXPECTEDFREQUENCY"));
			logLikelihoodVO.setFreq(rs.getDouble("FREQ"));
			logLikelihoodVO.setLogLikelihood(rs.getDouble("LOGLIKELIHOODV"));
			logLikelihoodVO.setTokenName(rs.getString("TOKENNAME"));

			return logLikelihoodVO;

		}

	}

	@Override
	public boolean deleteLogLikeLihood() {
		boolean logLikelihood = false;

		try {
			/*String sql = sqlProperties.getMessage(
					ContextualConstantsIF.SQLS.DELETE_LOGLIKELIHOOD_SQL, null,
					null);*/
			
			db.getCollection("loglikelihood").drop();
			
			
			//jdbcTemplate.update(sql);
			logLikelihood = true;
			return logLikelihood;
		} catch (Exception e) {
			System.out.println("Exception" + e);
			e.printStackTrace();
			return logLikelihood;
		}
	}

	@Override
	public List<String> retriveDistincetTokensForArticleNameFromLogLikelihood(
			String articleName) {
		try {
			/*String sql = sqlProperties
					.getMessage(
							ContextualConstantsIF.SQLS.RETRIVE_DINSTINCT_TOKENS_FOR_ARTICLENAME_FROM_LOGLIKELIHOOD_SQL,
							null, null);*/
			
		
				DBObject groupIdFields = new BasicDBObject("_id", "$TOKENNAME");

			    groupIdFields.put("count", new BasicDBObject("$sum", 1));
			    DBObject group = new BasicDBObject("$group", groupIdFields);

			    DBObject projectFields = new BasicDBObject("_id", 0);
			    projectFields.put("TOKENNAME", "$_id");
			    projectFields.put("count", 1);
			    DBObject project = new BasicDBObject("$project", projectFields);
			    
			    DBObject dbMatch = new BasicDBObject();
				dbMatch.put("ARTICLENAME", articleName);
				
				
				DBObject match = new BasicDBObject("$match",dbMatch);
				
				
				List conditionPipeLine = Arrays.asList(group, match, project);
				
				List<String> articleList = new ArrayList<String>();
				
				for(Document tempMap : db.getCollection("loglikelihood").aggregate(conditionPipeLine)){
					articleList.add(tempMap.getString("TOKENNAME"));
				}
				return articleList;
			
			/*Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("articleName", articleName);*/

			/*return namedJdbcTemplate
					.query(sql, paramMap, new TokenOnlyMapper());*/

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<Integer> retriveFreqValuesForArticleNameFromLogLikelihood(
			String articleName) {
		try {
			/*String sql = sqlProperties
					.getMessage(
							ContextualConstantsIF.SQLS.RETRIVE_FREQ_FROM_LOGLIKELIHOOD_WHERE_ARTICLENAME_SQL,
							null, null);*/
			
			
			DBObject project = new BasicDBObject("$project", new BasicDBObject("FREQ",1));
			
			DBObject match = new BasicDBObject("$match", new BasicDBObject("ARTICLENAME",articleName));
			
			List conditionPipeLine = Arrays.asList(match, project);
			
			List<Integer> frequencyList = new ArrayList<Integer>();
			
			
			for(Document tempMap : db.getCollection("loglikelihood").aggregate(conditionPipeLine)){
				
				frequencyList.add(tempMap.getInteger("FREQ"));
			}
			
			/*Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("articleName", articleName);

			return namedJdbcTemplate
					.query(sql, paramMap, new FreqValueMapper());*/
			
			return frequencyList;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	private final class FreqValueMapper implements RowMapper<Integer> {

		public Integer mapRow(ResultSet rs, int arg1) throws SQLException {

			Integer reviewModel = new Integer(rs.getInt("FREQ"));

			return reviewModel;

		}

	}

	@Override
	public StatusInfo storeComparison(CompareArticleRVVO compareArticleVO) {

		StatusInfo statusInfo = new StatusInfo();
		Integer COMPAREARTICLEID=1;
		try {
			//String sqlKey = ContextualConstantsIF.SQLS.INSERT_COMPAREARTICLE_SQL;
			//String sql = sqlProperties.getMessage(sqlKey, null, null);
			
			
			
			
			try{
				DBObject project = new BasicDBObject("$project", new BasicDBObject("COMPAREARTICLEID",1));
				
				DBObject sort = new BasicDBObject("$sort",new BasicDBObject("COMPAREARTICLEID",-1));
				
				DBObject limit = new BasicDBObject("$limit",1);
				
				
				List conditionPipeLine = Arrays.asList(sort, project, limit);
			
				
				for(Document tempMap : db.getCollection("comparearticle").aggregate(conditionPipeLine)){
					COMPAREARTICLEID = tempMap.getInteger("COMPAREARTICLEID");
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
			

			double accuracyOld = compareArticleVO.getAccuracyOld();

			double accuracyNew = compareArticleVO.getAccuracyProposed();

			Long timeTakenOld = compareArticleVO.getTimeTakenPrevious();

			Long timeTakenNew = compareArticleVO.getTimeTakenProposed();

			double proposedACC = 0;
			double previousACC = 0;

			if (accuracyOld > accuracyNew) {
				proposedACC = accuracyOld;
				previousACC = accuracyNew;
			} else {
				proposedACC = accuracyNew;
				previousACC = accuracyOld;
			}

			Long timeTakenOldT = 0L;
			Long timeTakenNewT = 0L;

			if (timeTakenOld > timeTakenNew) {

				timeTakenOldT = timeTakenOld;
				timeTakenNewT = timeTakenNew;
			} else {
				timeTakenOldT = timeTakenNew;
				timeTakenNewT = timeTakenOld;
			}
			
			Map<String, Object> paramMap = new HashMap<String, Object>();
			
			paramMap.put("COMPAREARTICLEID",++COMPAREARTICLEID);
			paramMap.put("ACCURARYPREVIOUS",previousACC);
			paramMap.put("ACCURACYPROPOSED",proposedACC);
			paramMap.put("TIMETAKENPREVIOUS",timeTakenOldT);
			paramMap.put("TIMETAKENPROPOSED",timeTakenNewT);
			
			
			MongoCollection<Document> collection = db.getCollection("comparearticle");
			
			collection.insertOne(new Document(paramMap));

			/*jdbcTemplate.update(sql, new Object[] { previousACC, proposedACC,
					timeTakenOldT, timeTakenNewT }, new int[] { Types.DOUBLE,
					Types.DOUBLE, Types.DOUBLE, Types.DOUBLE });*/
			statusInfo.setStatus(true);

		} catch (Exception e) {
			System.out.println("Exception is:");
			System.out.println(e.getMessage());
			e.printStackTrace();
			statusInfo
					.setErrMsg(ContextualConstantsIF.Message.MESSAGE_INTERNAL);
			statusInfo.setExceptionMsg(e.getMessage());
			statusInfo.setStatus(false);
			return statusInfo;

		}
		return statusInfo;

	}

	@Override
	public List<Integer> retriveFreqValuesForArticleNameFromLogLikelihoodAndCommonWords(
			String articleName, List<String> keyPhraseIntersection) {
		try {
			/*String sql = sqlProperties
					.getMessage(
							ContextualConstantsIF.SQLS.RETRIVE_FREQ_FROM_LOGLIKELIHOOD_WHERE_ARTICLENAME_AND_KEYPHRASE_SQL,
							null, null);*/
			
				DBObject project = new BasicDBObject("$project", new BasicDBObject("FREQ",1));
				
				DBObject match = new BasicDBObject("$match",new BasicDBObject("ARTICLENAME",articleName).append("TOKENNAME", new BasicDBObject("$in",keyPhraseIntersection)));
				
				
				List conditionPipeLine = Arrays.asList(match, project);
				
				List<Integer> frequencyList = new ArrayList<Integer>();
				
				for(Document tempMap : db.getCollection("article").aggregate(conditionPipeLine)){
					frequencyList.add(tempMap.getInteger("FREQ"));
				}
			
			/*Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("articleName", articleName);
			paramMap.put("tokenName", keyPhraseIntersection);

			return namedJdbcTemplate
					.query(sql, paramMap, new FreqValueMapper());*/
			
			return frequencyList;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	@Override
	public StatusInfo deleteDuplicateArticles() {
		StatusInfo statusInfo = null;
		try {

			statusInfo = new StatusInfo();
			/*String sql = sqlProperties.getMessage(
					ContextualConstantsIF.SQLS.DELETE_FROM_GROUPINFO_SQL, null,
					null);*/
			db.getCollection("groupinfo").drop();
			//jdbcTemplate.update(sql);
			statusInfo.setStatus(true);
			return statusInfo;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception " + e.getMessage());
			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			return statusInfo;
		}
	}

	@Override
	public List<String> retriveUniqueArticleNamesFromLogLikelihood() {
		try {
			/*String sql = sqlProperties
					.getMessage(
							ContextualConstantsIF.SQLS.RETRIVE_UNIQUE_ARTICLENAMES_FROM_LOGLIKELIHOOD_SQL,
							null, null);*/

		    DBObject groupIdFields = new BasicDBObject("_id", "$ARTICLENAME");

		    groupIdFields.put("count", new BasicDBObject("$sum", 1));
		    DBObject group = new BasicDBObject("$group", groupIdFields);

		    DBObject projectFields = new BasicDBObject("_id", 0);
		    projectFields.put("ARTICLENAME", "$_id");
		    projectFields.put("count", 1);
		    DBObject project = new BasicDBObject("$project", projectFields);
		    
		    List conditionPipeLine = Arrays.asList(group,project);
			
			List<String> articleName = new ArrayList<String>();

			for(Document tempMap : db.getCollection("loglikelihood").aggregate(conditionPipeLine)){
				
				articleName.add((String) tempMap.get("ARTICLENAME"));
				
			}
			

			return articleName;
			
			//return jdbcTemplate.queryForList(sql, String.class);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	@Override
	public int retriveGroupIdForArticleNameFromDuplicity(String articleName) {
		try {
			/*String sql = sqlProperties
					.getMessage(
							ContextualConstantsIF.SQLS.RETRIVE_GROUPID_FROM_DUPLICATEBUG_WHERE_ARTICLENAME_SQL,
							null, null);*/
			
			
			DBObject project = new BasicDBObject("$project", new BasicDBObject("GROUPID",1));
			
			DBObject match = new BasicDBObject("$match",new BasicDBObject("MAINARTICLENAME",articleName).append("GROUPID", new BasicDBObject("$gt",0)));
			
			List conditionPipeLine = Arrays.asList(match, project);
			
			Integer groupId= null;
			
			for(Document tempMap : db.getCollection("groupinfo").aggregate(conditionPipeLine)){
				groupId = tempMap.getInteger("GROUPID");
			}
			
			
			return groupId;
			
			/*Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("articleName", articleName);
			paramMap.put("groupID", 0);

			return namedJdbcTemplate.queryForObject(sql, paramMap,
					Integer.class);*/
		} catch (Exception e) {
			System.out.println("Exception" + e);
			e.printStackTrace();
			return -1;
		}
	}

	// INSERT INTO
	// groupinfo(ARTICLENAME,MAINARTICLENAME,USERID,EMAIL,RVCOEFFCIENT,THRESHOLD,SIMILARITY,GROUPID)
	// VALUES(?,?,?,?,?,?,?,?)

	@Override
	public StatusInfo insertDuplicateArticle(
			DuplicateBugResult duplicateBugResult) {
		
		/*Integer groupId = 1;
		
		DBObject project = new BasicDBObject("$project", new BasicDBObject("GROUPID",1));
		
		DBObject sort = new BasicDBObject("$sort",new BasicDBObject("GROUPID",-1));
		
		DBObject limit = new BasicDBObject("$limit",1);
		
		
		List conditionPipeLine = Arrays.asList(sort, project, limit);
		
		
		for(Document tempMap : db.getCollection("groupinfo").aggregate(conditionPipeLine)){
			groupId = tempMap.getInteger("GROUPID");
		}*/
		

		StatusInfo statusInfo = new StatusInfo();
		try {
			String sqlKey = ContextualConstantsIF.SQLS.INSERT_DUPLICATEARTICLE_SQL;
			String sql = sqlProperties.getMessage(sqlKey, null, null);

			int similarBinary = 0;
			boolean similar = duplicateBugResult.isSimilarity();
			if (similar == true) {
				similarBinary = 1;

			}
			
			Map<String, Object> paramMap = new HashMap<String, Object>();
			
			paramMap.put("ARTICLENAME", duplicateBugResult.getArticleChild());
			paramMap.put("MAINARTICLENAME", duplicateBugResult.getArticleParent());
			paramMap.put("USN", duplicateBugResult.getUserId());
			paramMap.put("EMAIL", duplicateBugResult.getEmail());
			paramMap.put("RVCOEFFCIENT", duplicateBugResult.getRvCoeeffcient());
			paramMap.put("THRESHOLD", duplicateBugResult.getThreshold());
			paramMap.put("SIMILARITY", similarBinary);
			paramMap.put("GROUPID", duplicateBugResult.getGroupId());
			paramMap.put("ARTICLEIDPARENT", duplicateBugResult.getArticleIdParent());
			paramMap.put("ARTICLEIDCHILD", duplicateBugResult.getArticleIdChild());
			
			
			MongoCollection<Document> collection = db.getCollection("groupinfo");
			
			collection.insertOne(new Document(paramMap));
			/*jdbcTemplate.update(
					sql,
					new Object[] { duplicateBugResult.getArticleChild(),
							duplicateBugResult.getArticleParent(),
							duplicateBugResult.getUserId(),
							duplicateBugResult.getEmail(),
							duplicateBugResult.getRvCoeeffcient(),
							duplicateBugResult.getThreshold(), similarBinary,
							duplicateBugResult.getGroupId(),
							duplicateBugResult.getArticleIdParent(),
							duplicateBugResult.getArticleIdChild() },
					new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
							Types.VARCHAR, Types.DOUBLE, Types.DOUBLE,
							Types.INTEGER, Types.INTEGER, Types.INTEGER,
							Types.INTEGER });*/
			statusInfo.setStatus(true);

		} catch (Exception e) {
			System.out.println("Exception is:");
			System.out.println(e.getMessage());
			e.printStackTrace();
			statusInfo
					.setErrMsg(ContextualConstantsIF.Message.MESSAGE_INTERNAL);
			statusInfo.setExceptionMsg(e.getMessage());
			statusInfo.setStatus(false);
			return statusInfo;

		}
		return statusInfo;
	}

	@Override
	public StatusInfo insertGroupedArticle(DuplicateBugResult duplicateBugResult) {
		StatusInfo statusInfo = new StatusInfo();
		try {
			String sqlKey = ContextualConstantsIF.SQLS.INSERT_DUPLICATEARTICLE_SQL;
			String sql = sqlProperties.getMessage(sqlKey, null, null);

			int similarBinary = 0;
			boolean similar = duplicateBugResult.isSimilarity();
			if (similar == true) {
				similarBinary = 1;

			}
			
			
			Map<String, Object> paramMap = new HashMap<String, Object>();
			
			paramMap.put("ARTICLENAME", duplicateBugResult.getArticleChild());
			paramMap.put("MAINARTICLENAME", duplicateBugResult.getArticleParent());
			paramMap.put("USN", duplicateBugResult.getUserId());
			paramMap.put("EMAIL", duplicateBugResult.getEmail());
			paramMap.put("RVCOEFFCIENT", duplicateBugResult.getRvCoeeffcient());
			paramMap.put("THRESHOLD", duplicateBugResult.getThreshold());
			paramMap.put("SIMILARITY", similarBinary);
			paramMap.put("GROUPID", duplicateBugResult.getGroupId());
			paramMap.put("ARTICLEIDPARENT", duplicateBugResult.getArticleIdParent());
			paramMap.put("ARTICLEIDCHILD", duplicateBugResult.getArticleIdChild());
			
			
			MongoCollection<Document> collection = db.getCollection("groupinfo");
			
			collection.insertOne(new Document(paramMap));
			
			/*jdbcTemplate.update(
					sql,
					new Object[] { duplicateBugResult.getArticleChild(),
							duplicateBugResult.getArticleParent(),
							duplicateBugResult.getUserId(),
							duplicateBugResult.getEmail(),
							duplicateBugResult.getRvCoeeffcient(),
							duplicateBugResult.getThreshold(), similarBinary,
							duplicateBugResult.getGroupId(),
							duplicateBugResult.getArticleIdChild(),
							duplicateBugResult.getArticleIdParent() },
					new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
							Types.VARCHAR, Types.DOUBLE, Types.DOUBLE,
							Types.INTEGER, Types.INTEGER, Types.INTEGER,
							Types.INTEGER });*/
			statusInfo.setStatus(true);

		} catch (Exception e) {
			System.out.println("Exception is:");
			System.out.println(e.getMessage());
			e.printStackTrace();
			statusInfo
					.setErrMsg(ContextualConstantsIF.Message.MESSAGE_INTERNAL);
			statusInfo.setExceptionMsg(e.getMessage());
			statusInfo.setStatus(false);
			return statusInfo;

		}
		return statusInfo;
	}

	@Override
	public int retriveGroupIdForArticleNameFromGroupId(String articleName1) {
		try {
			/*String sql = sqlProperties
					.getMessage(
							ContextualConstantsIF.SQLS.RETRIVE_GROUPID_FROM_DUPLICATEBUG_WHERE_ARTICLENAME_SQL,
							null, null);*/
			
			DBObject project = new BasicDBObject("$project", new BasicDBObject("GROUPID",1));
			
			DBObject match = new BasicDBObject("$match",new BasicDBObject("MAINARTICLENAME",articleName1).append("GROUPID", new BasicDBObject("$gt",0)));
			
			List conditionPipeLine = Arrays.asList(match, project);
			
			Integer groupId= null;
			
			for(Document tempMap : db.getCollection("groupinfo").aggregate(conditionPipeLine)){
				groupId = tempMap.getInteger("GROUPID");
			}
			
			
			return groupId;
			/*Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("articleName", articleName1);
			paramMap.put("groupID", 0);

			return namedJdbcTemplate.queryForObject(sql, paramMap,
					Integer.class);*/
		} catch (Exception e) {
			System.out.println("Exception" + e);
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public List<RegisterUser> retriveAllRegisterInfo() {
		try {

			/*String sql = sqlProperties
					.getMessage(
							ContextualConstantsIF.SQLS.RETRIVE_ALLLOGIN_SQL,
							null, null);*/
			
			
			DBObject project = new BasicDBObject("$project", new BasicDBObject("EMAIL",1).append("USN", 1));
			
			List conditionPipeLine = Arrays.asList(project);
			
			List<RegisterUser> registerList = new ArrayList<RegisterUser>();
			
			for(Document tempMap : db.getCollection("login").aggregate(conditionPipeLine)){
				RegisterUser registerUser = new RegisterUser();

				registerUser.setEmail(tempMap.getString("EMAIL"));
				registerUser.setUSN(tempMap.getString("USN"));
				
				registerList.add(registerUser);
			}
			
			
			return registerList;
			
			//return jdbcTemplate.query(sql, new LoginVOMapper());
		} catch (Exception e) {
			System.out.println("Exception" + e);
			return null;
		}
	}

	private final class LoginVOMapper implements RowMapper {

		public RegisterUser mapRow(ResultSet rs, int arg1) throws SQLException {

			RegisterUser registerUser = new RegisterUser();

			registerUser.setEmail(rs.getString("EMAIL"));
			registerUser.setUSN(rs.getString("USN"));

			return registerUser;
		}

	}

	@Override
	public List<GroupingView> viewGrouping() {

		try {

			/*String sql = sqlProperties.getMessage(
					ContextualConstantsIF.SQLS.RETRIVE_GROUPINGVIEW_SQL, null,
					null);*/
			
			
			DBObject project = new BasicDBObject("$project", new BasicDBObject("ARTICLENAME",1).append("MAINARTICLENAME", 1).append("EMAIL", 1).append("GROUPID", 1).append("RVCOEFFCIENT", 1).append("THRESHOLD", 1).append("USN", 1).append("ARTICLEIDCHILD", 1).append("ARTICLEIDPARENT", 1).append("SIMILARITY", 1));
			
			List conditionPipeLine = Arrays.asList(project);
			
			List<GroupingView> groupList = new ArrayList<GroupingView>();
			
			for(Document tempMap : db.getCollection("groupinfo").aggregate(conditionPipeLine)){
				GroupingView groupingView = new GroupingView();
				groupingView.setArticleName(tempMap.getString("ARTICLENAME"));
				groupingView.setArticleNameMain(tempMap.getString("MAINARTICLENAME"));
				groupingView.setEmail(tempMap.getString("EMAIL"));
				groupingView.setGroupId(tempMap.getInteger("GROUPID"));
				groupingView.setRvCoeffcient(tempMap.getDouble("RVCOEFFCIENT"));
				groupingView.setThreshold(tempMap.getDouble("THRESHOLD"));
				groupingView.setUserId(tempMap.getString("USN"));
				groupingView.setArticleIdChild(tempMap.getInteger("ARTICLEIDCHILD"));
				groupingView.setArticleIdParent(tempMap.getInteger("ARTICLEIDPARENT"));
				int similarity = tempMap.getInteger("SIMILARITY");

				if (similarity == 1) {
					groupingView.setSimilarity(true);
				} else {
					groupingView.setSimilarity(false);
				}
				
				groupList.add(groupingView);
			}
			
			
			return groupList;
			
			
			//return jdbcTemplate.query(sql, new GroupingViewMapper());
		} catch (Exception e) {
			System.out.println("Exception" + e);
			return null;
		}
	}

	private final class GroupingViewMapper implements RowMapper {

		public GroupingView mapRow(ResultSet rs, int arg1) throws SQLException {

			GroupingView groupingView = new GroupingView();
			groupingView.setArticleName(rs.getString("ARTICLENAME"));
			groupingView.setArticleNameMain(rs.getString("MAINARTICLENAME"));
			groupingView.setEmail(rs.getString("EMAIL"));
			groupingView.setGroupId(rs.getInt("GROUPID"));
			groupingView.setRvCoeffcient(rs.getDouble("RVCOEFFCIENT"));
			groupingView.setThreshold(rs.getDouble("THRESHOLD"));
			groupingView.setUserId(rs.getString("USN"));
			groupingView.setArticleIdChild(rs.getInt("ARTICLEIDCHILD"));
			groupingView.setArticleIdParent(rs.getInt("ARTICLEIDPARENT"));
			int similarity = rs.getInt("SIMILARITY");

			if (similarity == 1) {
				groupingView.setSimilarity(true);
			} else {
				groupingView.setSimilarity(false);
			}

			return groupingView;
		}

	}

	@Override
	public List<LogLikelihoodVO> retriveLogLikelihoodFromDBOrderBy(
			Double threshold) {
		List<LogLikelihoodVO> logLikelihoodFunction = null;
		try {
			/*String sql = sqlProperties
					.getMessage(
							ContextualConstantsIF.SQLS.RETRIVE_LOGLIKELIHOOD_FULL_ORDERBY_SQL,
							null, null);*/
			
			Map<String,Object> groupObject = new HashMap<String, Object>();
			groupObject.put("LOGLIKELIHOODV", "$LOGLIKELIHOODV");
			groupObject.put("EXPECTEDFREQUENCY", "$EXPECTEDFREQUENCY");
			groupObject.put("FREQ", "$FREQ");
			groupObject.put("TOKENNAME", "$TOKENNAME");
			groupObject.put("ARTICLENAME", "$ARTICLENAME");
			
			
			DBObject groupIdFields = new BasicDBObject("_id", groupObject);

		    groupIdFields.put("count", new BasicDBObject("$sum", 1));
		    DBObject group = new BasicDBObject("$group", groupIdFields);

		    DBObject projectFields = new BasicDBObject("_id", 0);
		    projectFields.put("LOGLIKELIHOODV", "$_id.LOGLIKELIHOODV");
		    projectFields.put("EXPECTEDFREQUENCY", "$_id.EXPECTEDFREQUENCY");
		    projectFields.put("FREQ", "$_id.FREQ");
		    projectFields.put("TOKENNAME", "$_id.TOKENNAME");
		    projectFields.put("ARTICLENAME", "$_id.ARTICLENAME");
		    projectFields.put("count", 1);
		    DBObject project = new BasicDBObject("$project", projectFields);
		    
		    
		    DBObject match = new BasicDBObject("$match", new BasicDBObject("LOGLIKELIHOODV",new BasicDBObject("$gt",threshold)));
		    
		    DBObject sort = new BasicDBObject("$sort", new BasicDBObject("LOGLIKELIHOODV",-1));
		    
		    
		    List conditionPipeLine = Arrays.asList(match, group, sort, project);
			
			List<LogLikelihoodVO> logLikelihoodVOList = new ArrayList<LogLikelihoodVO>();

			for(Document tempMap : db.getCollection("loglikelihood").aggregate(conditionPipeLine)){
				
				LogLikelihoodVO logLikelihoodVO = new LogLikelihoodVO();

				logLikelihoodVO.setArticleName(tempMap.getString("ARTICLENAME"));
				logLikelihoodVO.setExpectedFrequency(tempMap
						.getDouble("EXPECTEDFREQUENCY"));
				logLikelihoodVO.setFreq(tempMap.getDouble("FREQ"));
				logLikelihoodVO.setLogLikelihood(tempMap.getDouble("LOGLIKELIHOODV"));
				logLikelihoodVO.setTokenName(tempMap.getString("TOKENNAME"));
				
				logLikelihoodVOList.add(logLikelihoodVO);
				
			}
			
			return logLikelihoodVOList;
			
			/*return jdbcTemplate.query(sql, new LogLikelihoodTempVOMapper(),
					threshold);*/

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	// SELECT
	// ACCURARYPREVIOUS,ACCURACYPROPOSED,TIMETAKENPREVIOUS,TIMETAKENPROPOSED
	// FROM comparearticle
	@Override
	public List<ComparisionVO> retriveComparison() {
		List<ComparisionVO> stopWordList = null;
		try {
			/*String sql = sqlProperties.getMessage(
					ContextualConstantsIF.SQLS.RETRIVE_COMPARISION_FULL_SQL,
					null, null);*/
			
			DBObject project = new BasicDBObject("$project", new BasicDBObject("ACCURARYPREVIOUS",1).append("ACCURACYPROPOSED", 1).append("TIMETAKENPREVIOUS", 1).append("TIMETAKENPROPOSED", 1));
			
			List conditionPipeLine = Arrays.asList(project);
			
			List<ComparisionVO> comparisionVOList = new ArrayList<ComparisionVO>();
			
			for(Document tempMap : db.getCollection("comparearticle").aggregate(conditionPipeLine)){
				ComparisionVO comparisionVO = new ComparisionVO();
				comparisionVO.setPreviousAccuracy(tempMap.getDouble("ACCURARYPREVIOUS"));
				comparisionVO.setProposedAccuracy(tempMap.getDouble("ACCURACYPROPOSED"));

				comparisionVO.setPreviousTime((tempMap.getLong(("TIMETAKENPREVIOUS")).doubleValue()));
				comparisionVO.setProposedTime((tempMap.getLong("TIMETAKENPROPOSED")).doubleValue());

				comparisionVOList.add(comparisionVO);
			}
			
			return comparisionVOList;
			
			//return jdbcTemplate.query(sql, new ComparisionMapper());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	private final class ComparisionMapper implements RowMapper<ComparisionVO> {

		public ComparisionVO mapRow(ResultSet rs, int arg1) throws SQLException {

			ComparisionVO comparisionVO = new ComparisionVO();
			comparisionVO.setPreviousAccuracy(rs.getDouble("ACCURARYPREVIOUS"));
			comparisionVO.setProposedAccuracy(rs.getDouble("ACCURACYPROPOSED"));

			comparisionVO.setPreviousTime(rs.getDouble("TIMETAKENPREVIOUS"));
			comparisionVO.setProposedTime(rs.getDouble("TIMETAKENPROPOSED"));

			return comparisionVO;

		}

	}

	@Override
	public StatusInfo insertActivityList(RegisterUser registerUser) {
		StatusInfo insertLoginStatus = null;
		try {
			insertLoginStatus = new StatusInfo();
			/*String sql = sqlProperties.getMessage(
					ContextualConstantsIF.DATABASESQL.INSERT_ACTIVITY_SQL,
					null, null);*/
			
			List<Document> keyList = new ArrayList<Document>();
			

			if (registerUser.getActivityList() != null
					&& !registerUser.getActivityList().isEmpty()) {
				for (String activity : registerUser.getActivityList()) {

					Map<String, Object> paramMap = new HashMap<String, Object>();
					paramMap.put("ACTIVITYNAME", activity);
					paramMap.put("LOGINID", registerUser.getUSN());
					
					keyList.add(new Document(paramMap));

					//namedJdbcTemplate.update(sql, paramMap);
				}
				
				MongoCollection<Document> collection = db.getCollection("extraactivity");
				
				collection.insertMany(keyList);
			}

			insertLoginStatus.setStatus(true);
			return insertLoginStatus;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			insertLoginStatus = new StatusInfo();
			insertLoginStatus.setErrMsg(e.getMessage());
			insertLoginStatus.setStatus(false);
			return insertLoginStatus;

		}
	}

	@Override
	public StatusInfo insertSubjectList(RegisterUser registerUser) {
		StatusInfo insertLoginStatus = null;
		try {
			insertLoginStatus = new StatusInfo();
			/*String sql = sqlProperties.getMessage(
					ContextualConstantsIF.DATABASESQL.INSERT_SUBJECT_SQL, null,
					null);*/

			if (registerUser.getSubjectList() != null
					&& !registerUser.getSubjectList().isEmpty()) {
				
				List<Document> list = new ArrayList<Document>();
				
				for (String subject : registerUser.getSubjectList()) {

					Map<String, Object> paramMap = new HashMap<String, Object>();
					paramMap.put("SUBJECTNAME", subject);
					paramMap.put("LOGINID", registerUser.getUSN());
					
					list.add(new Document(paramMap));

					//namedJdbcTemplate.update(sql, paramMap);
				}
				
				MongoCollection<Document> collection = db.getCollection("subjects");
				collection.insertMany(list);
				
			}

			insertLoginStatus.setStatus(true);
			return insertLoginStatus;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			insertLoginStatus = new StatusInfo();
			insertLoginStatus.setErrMsg(e.getMessage());
			insertLoginStatus.setStatus(false);
			return insertLoginStatus;

		}
	}

	@Override
	public List<String> retriveArticleNames() {
		try {

			/*String sql = sqlProperties.getMessage(
					ContextualConstantsIF.SQLS.RETRIVE_ARTICLENAMES_SQL, null,
					null);*/
			DBObject project = new BasicDBObject("$project", new BasicDBObject("ARTICLENAME",1));
			
			List conditionPipeLine = Arrays.asList(project);
			
			List<String> userList = new ArrayList<String>();
			
			for(Document tempMap : db.getCollection("article").aggregate(conditionPipeLine)){
				userList.add(tempMap.getString("ARTICLENAME"));
			}
			//return jdbcTemplate.queryForList(sql, String.class);
			return userList;	
			
			
			//Map<String, Object> map = null;
			//return namedJdbcTemplate.query(sql, map, new ArticleNameMapper());
		} catch (Exception e) {
			System.out.println("Exception" + e);
			return null;
		}
	}

	private final class ArticleNameMapper implements RowMapper<String> {
		public String mapRow(ResultSet rs, int arg1) throws SQLException {
			return rs.getString("ARTICLENAME");
		}
	}

	private final class TokenNameMapper implements RowMapper<String> {
		public String mapRow(ResultSet rs, int arg1) throws SQLException {
			return rs.getString("TOKENNAME");
		}
	}

	// INSERT INTO
	// ARTICLE(ARTICLENAME,ARTICLEDESC,USN,PUBLISHER,CONTENTTYPE,ARTICLEFILE,FILENAME)
	// VALUES(:ARTICLENAME,:ARTICLEDESC,:USN,:PUBLISHER,:CONTENTTYPE,:ARTICLEFILE,:FILENAME)

	@Override
	public StatusInfo insertArticle(ArticleVO articleVO) {
		StatusInfo statusInfo = new StatusInfo();
		
		Integer articleId=1;
		
		try{
			DBObject project = new BasicDBObject("$project", new BasicDBObject("ARTICLEID",1));
			
			DBObject sort = new BasicDBObject("$sort",new BasicDBObject("ARTICLEID",-1));
			
			DBObject limit = new BasicDBObject("$limit",1);
			
			
			List conditionPipeLine = Arrays.asList(sort, project, limit);
			
			List<String> userList = new ArrayList<String>();
			
			for(Document tempMap : db.getCollection("article").aggregate(conditionPipeLine)){
				articleId = tempMap.getInteger("ARTICLEID");
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		
		try {
			/*String sql = sqlProperties.getMessage(
					ContextualConstantsIF.SQLS.INSERT_ARTICLE_SQL, null, null);

			MapSqlParameterSource parameters = new MapSqlParameterSource();
			parameters.addValue("ARTICLENAME", articleVO.getArticleName());
			parameters.addValue("ARTICLEDESC", articleVO.getArticleDesc());
			parameters.addValue("USN", articleVO.getUSN());
			parameters.addValue("PUBLISHER", articleVO.getPublisher());
			parameters.addValue("CONTENTTYPE", articleVO.getContextType());
			parameters.addValue("FILENAME", articleVO.getFileName());
			if (articleVO.getFile() != null) {

				parameters.addValue("ARTICLEFILE",
						new SqlLobValue(new ByteArrayInputStream(articleVO
								.getFile().getBytes()), articleVO.getFile()
								.getBytes().length, new DefaultLobHandler()),
						Types.BLOB);
			} else {
				parameters.addValue("ARTICLEFILE", "Test");
			}*/
			
			Map<String, Object> multipleFileMap = new HashMap<String, Object>();
			multipleFileMap.put("ARTICLENAME", articleVO.getArticleName());
			multipleFileMap.put("ARTICLEDESC", articleVO.getArticleDesc());
			multipleFileMap.put("USN", articleVO.getUSN());
			multipleFileMap.put("PUBLISHER", articleVO.getPublisher());
			multipleFileMap.put("CONTENTTYPE", articleVO.getContextType());
			multipleFileMap.put("FILENAME", articleVO.getFileName());
			multipleFileMap.put("ARTICLEID", ++articleId);
			
			
			MongoCollection<Document> collection = db.getCollection("article");
			
			collection.insertOne(new Document(multipleFileMap));

			
			Object fileId = uploadMultipleFiles(articleVO);
			
			//download(articleVO.getFileName());

			//namedJdbcTemplate.update(sql, parameters);

			statusInfo.setStatus(true);

		} catch (Exception e) {
			System.out.println("Exception is:");
			System.out.println(e.getMessage());
			e.printStackTrace();
			statusInfo
					.setErrMsg(ContextualConstantsIF.Message.MESSAGE_INTERNAL);
			statusInfo.setExceptionMsg(e.getMessage());
			statusInfo.setStatus(false);
			return statusInfo;

		}
		return statusInfo;
	}
	
	
	public ObjectId uploadMultipleFiles(ArticleVO articleVO) {
		System.out.println("Calling upload...");
  	  	MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
  	  	//DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
  	  	ObjectId fileId = null;
  	  	try {
  	  		MongoDatabase database = mongoClient.getDatabase("fileupload");
  	  		GridFSBucket gridBucket = GridFSBuckets.create(database);
  	  		//InputStream inputStream = new FileInputStream(new File(filePath));
  	  		// Create some custom options
  	  		GridFSUploadOptions uploadOptions = new GridFSUploadOptions().chunkSizeBytes(1024).metadata(new Document("type", articleVO.getContextType()).append("upload_date", new Date().toString()).append("content_type", articleVO.getContextType()).append("USN", articleVO.getUSN()));
  	  		fileId = gridBucket.uploadFromStream(articleVO.getFileName(), articleVO.getFileInputStream(), uploadOptions);
  	 
  	  	} catch (Exception e) {
  	  		e.printStackTrace();
  	  	} finally {
  	  		mongoClient.close();
  	  	}
  	 
  	  	return fileId;
	}
	
	
	public void download(String fileName) {
		System.out.println("Calling download...");
		MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
		 
		try {
			MongoDatabase database = mongoClient.getDatabase("fileupload");
			GridFSBucket gridBucket = GridFSBuckets.create(database);
		 
			FileOutputStream fileOutputStream = new FileOutputStream("C:/Users/Manabrata/Desktop/Manabrata/"+fileName);
			gridBucket.downloadToStream(fileName, fileOutputStream);
			fileOutputStream.close();
		 
		 } catch (Exception e) {
			 e.printStackTrace();
		 } finally {
			 mongoClient.close();
		 }
	}
		 


	@Override
	public StatusInfo insertKeyWordList(List<String> keyWordList,
			String artilceName) {
		StatusInfo statusInfo = new StatusInfo();
		try {
			/*String sql = sqlProperties.getMessage(
					ContextualConstantsIF.SQLS.INSERT_KEYWORDLIST_SQL, null,
					null);*/

			List<Document> keyList = new ArrayList<Document>();
			
			if (keyWordList != null && !keyWordList.isEmpty()) {
				for (String keyword : keyWordList) {
					Map<String, Object> paramMap = new HashMap<String, Object>();

					paramMap.put("ARTICLENAME", artilceName);
					paramMap.put("KEYWORD", keyword);
					
					keyList.add(new Document(paramMap));

					//namedJdbcTemplate.update(sql, paramMap);
				}
				MongoCollection<Document> collection = db.getCollection("keywordlist");
				
				collection.insertMany(keyList);

			}

			statusInfo.setStatus(true);

		} catch (Exception e) {
			System.out.println("Exception is:");
			System.out.println(e.getMessage());
			e.printStackTrace();
			statusInfo
					.setErrMsg(ContextualConstantsIF.Message.MESSAGE_INTERNAL);
			statusInfo.setExceptionMsg(e.getMessage());
			statusInfo.setStatus(false);
			return statusInfo;

		}
		return statusInfo;
	}

	// INSERT INTO
	// AUTHORLIST(ARTICLENAME,AUTHORNAME)VALUES(:ARTICLENAME,:AUTHORNAME)

	@Override
	public StatusInfo insertAuthorList(List<String> authorList,
			String articleName) {
		StatusInfo statusInfo = new StatusInfo();
		try {
			/*String sql = sqlProperties.getMessage(
					ContextualConstantsIF.SQLS.INSERT_AUTHOR_LIST_SQL, null,
					null);*/
			
			List<Document> listAuthor = new ArrayList<Document>();

			if (authorList != null && !authorList.isEmpty()) {
				for (String author : authorList) {
					Map<String, Object> paramMap = new HashMap<String, Object>();

					paramMap.put("ARTICLENAME", articleName);
					paramMap.put("AUTHORNAME", author);
					
					listAuthor.add(new Document(paramMap));

					//namedJdbcTemplate.update(sql, paramMap);
				}
				
				MongoCollection<Document> collection = db.getCollection("authorlist");
				
				collection.insertMany(listAuthor);
			}

			statusInfo.setStatus(true);

		} catch (Exception e) {
			System.out.println("Exception is:");
			System.out.println(e.getMessage());
			e.printStackTrace();
			statusInfo
					.setErrMsg(ContextualConstantsIF.Message.MESSAGE_INTERNAL);
			statusInfo.setExceptionMsg(e.getMessage());
			statusInfo.setStatus(false);
			return statusInfo;

		}
		return statusInfo;
	}

	@Override
	public List<CleanArticleModel> retriveCleanDataForArticleName(
			String articleName) {
		try {
			/*String sql = sqlProperties
					.getMessage(
							ContextualConstantsIF.SQLS.RETRIVE_ALLCLEAN_ARTICLES_FOR_ARTICLENAME_SQL,
							null, null);*/
			
			DBObject project = new BasicDBObject("$project", new BasicDBObject("CLEANID",1).append("CLEANARTICLE", 1).append("ARTICLENAME", 1));
			
			DBObject match = new BasicDBObject("$match",new BasicDBObject("ARTICLENAME",articleName));
			
			
			List conditionPipeLine = Arrays.asList(match, project);
			
			List<CleanArticleModel> cleanArticleModelList = new ArrayList<CleanArticleModel>();
			
			for(Document tempMap : db.getCollection("cleanarticles").aggregate(conditionPipeLine)){
				CleanArticleModel cleanReviewModel = new CleanArticleModel();
				cleanReviewModel.setCleanId(tempMap
						.getInteger(ContextualConstantsIF.DatabaseColumns.CLEANID_COL));
				cleanReviewModel
						.setCleanArtilce(tempMap
								.getString(ContextualConstantsIF.DatabaseColumns.CLEANARTICLE_COL));

				cleanReviewModel
						.setArticleName(tempMap
								.getString(ContextualConstantsIF.DatabaseColumns.ARTICLENAME_COL));

				cleanArticleModelList.add(cleanReviewModel);
			}
			
			
			return cleanArticleModelList;
			
			/*Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("ARTICLENAME", articleName);

			return namedJdbcTemplate.query(sql, paramMap,
					new CleanReviewModelMapper());*/

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<TokenVO> retriveAllTokensForArticleName(String articleName) {
		try {
			/*String sql = sqlProperties
					.getMessage(
							ContextualConstantsIF.SQLS.RETRIVE_ALLTOKENS_WHERE_ARTICLENAME_SQL,
							null, null);*/
			
			DBObject project = new BasicDBObject("$project", new BasicDBObject("TOKENID",1).append("TOKENNAME", 1).append("ARTICLENAME", 1));
			
			DBObject match = new BasicDBObject("$match",new BasicDBObject("ARTICLENAME",articleName));
			
			
			List conditionPipeLine = Arrays.asList(match, project);
			
			List<TokenVO> tokenVOList = new ArrayList<TokenVO>();
			
			for(Document tempMap : db.getCollection("tokens").aggregate(conditionPipeLine)){
				TokenVO tokenVO = new TokenVO();

				tokenVO.setTokenId(tempMap
						.getInteger(ContextualConstantsIF.DatabaseColumns.TOKENID_COL));
				tokenVO.setTokenName(tempMap
						.getString(ContextualConstantsIF.DatabaseColumns.TOKENNAME_COL));

				tokenVO.setArticleName(tempMap
						.getString(ContextualConstantsIF.DatabaseColumns.ARTICLENAME_COL));

				tokenVOList.add(tokenVO);
			}
			
			return tokenVOList;

			/*Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("ARTICLENAME", articleName);

			return namedJdbcTemplate.query(sql, paramMap, new TokenMapper());*/

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<ArticleVO> retriveAllArticles() {
		try {

			/*String sql = sqlProperties.getMessage(
					ContextualConstantsIF.SQLS.RETRIVE_ALL_ARTICLES_SQL, null,
					null);*/
			
			DBObject project = new BasicDBObject("$project", new BasicDBObject("ARTICLENAME",1).append("ARTICLEDESC", 1).append("USN", 1).append("PUBLISHER", 1).append("CONTENTTYPE", 1).append("ARTICLENAME",1).append("FILENAME", 1).append("ARTICLEID", 1));
			
			//DBObject match = new BasicDBObject("$match", new BasicDBObject("USN",loginId));
			
			List conditionPipeLine = Arrays.asList(project);
			
			List<ArticleVO> airticalList = new ArrayList<ArticleVO>();
			
			for(Document tempMap : db.getCollection("article").aggregate(conditionPipeLine)){
				ArticleVO article = new ArticleVO();
				
				article.setArticleName(tempMap.getString("ARTICLENAME"));
				article.setArticleDesc(tempMap.getString("ARTICLEDESC"));
				article.setUSN(tempMap.getString("USN"));
				article.setPublisher(tempMap.getString("PUBLISHER"));
				article.setContextType(tempMap.getString("CONTENTTYPE"));
				article.setArticleName(tempMap.getString("ARTICLENAME"));
				article.setFileName(tempMap.getString("FILENAME"));
				article.setArticleId(tempMap.getInteger("ARTICLEID"));
				
				airticalList.add(article);
			}


			Map<String, Object> paramMap = null;

			/*return namedJdbcTemplate.query(sql, paramMap,
					new ArticleModelVOMapper());*/
			
			return airticalList;
		} catch (Exception e) {
			System.out.println("Exception" + e);
			return null;
		}
	}

	private final class ArticleModelVOMapper implements RowMapper<ArticleVO> {

		public ArticleVO mapRow(ResultSet rs, int arg1) throws SQLException {

			ArticleVO articleVO = new ArticleVO();

			articleVO.setArticleDesc(rs.getString("ARTICLEDESC"));
			articleVO.setArticleName(rs.getString("ARTICLENAME"));
			articleVO.setUSN(rs.getString("USN"));
			articleVO.setPublisher(rs.getString("PUBLISHER"));
			articleVO.setFileName(rs.getString("FILENAME"));
			articleVO.setContextType(rs.getString("CONTENTTYPE"));
			// Need to Add Retrival Of Bytes Later
			articleVO.setFileContent(rs.getBytes("ARTICLEFILE"));
			articleVO.setArticleId(rs.getInt("ARTICLEID"));

			return articleVO;

		}

	}

	@Override
	public List<ArticleVO> retriveAllArticlesForCustomer(String loginId) {
		try {

			/*String sql = sqlProperties
					.getMessage(
							ContextualConstantsIF.SQLS.RETRIVE_ALL_ARTICLES_WHERE_USN_SQL,
							null, null);*/
			
			DBObject project = new BasicDBObject("$project", new BasicDBObject("ARTICLENAME",1).append("ARTICLEDESC", 1).append("USN", 1).append("PUBLISHER", 1).append("CONTENTTYPE", 1).append("ARTICLENAME",1).append("FILENAME", 1).append("ARTICLEID", 1));
			
			DBObject match = new BasicDBObject("$match", new BasicDBObject("USN",loginId));
			
			List conditionPipeLine = Arrays.asList(match, project);
			
			List<ArticleVO> airticalList = new ArrayList<ArticleVO>();
			
			for(Document tempMap : db.getCollection("article").aggregate(conditionPipeLine)){
				ArticleVO article = new ArticleVO();
				
				article.setArticleName(tempMap.getString("ARTICLENAME"));
				article.setArticleDesc(tempMap.getString("ARTICLEDESC"));
				article.setUSN(tempMap.getString("USN"));
				article.setPublisher(tempMap.getString("PUBLISHER"));
				article.setContextType(tempMap.getString("CONTENTTYPE"));
				article.setArticleName(tempMap.getString("ARTICLENAME"));
				article.setFileName(tempMap.getString("FILENAME"));
				article.setArticleId(tempMap.getInteger("ARTICLEID"));
				
				airticalList.add(article);
			}
			//return jdbcTemplate.queryForList(sql, String.class);
			

			/*Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("USN", loginId);

			return namedJdbcTemplate.query(sql, paramMap,
					new ArticleModelVOMapper());*/
			return airticalList;
			
		} catch (Exception e) {
			System.out.println("Exception" + e);
			return null;
		}
	}

	@Override
	public List<String> retriveDistinctArticleNamesFromFeatureVector() {
		try {
			/*String sql = sqlProperties
					.getMessage(
							ContextualConstantsIF.SQLS.RETRIVE_DINSTINCT_ARTICLENAMES_FROM_FV_SQL,
							null, null);*/
			
			
			DBObject groupIdFields = new BasicDBObject("_id", "$ARTICLENAME");

		    groupIdFields.put("count", new BasicDBObject("$sum", 1));
		    DBObject group = new BasicDBObject("$group", groupIdFields);

		    DBObject projectFields = new BasicDBObject("_id", 0);
		    projectFields.put("ARTICLENAME", "$_id");
		    projectFields.put("count", 1);
		    DBObject project = new BasicDBObject("$project", projectFields);
		    
		    List conditionPipeLine = Arrays.asList(group,project);
			
			List<String> articleName = new ArrayList<String>();

			for(Document tempMap : db.getCollection("featurevectors").aggregate(conditionPipeLine)){
				
				articleName.add((String) tempMap.get("ARTICLENAME"));
				
			}
			
			return articleName;

			/*Map<String, Object> paramMap = null;

			return namedJdbcTemplate.query(sql, paramMap,
					new ArticleNameMapper());*/

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<String> retriveDistinctArticleNamesForFreq() {
		try {
			/*String sql = sqlProperties
					.getMessage(
							ContextualConstantsIF.SQLS.RETRIVE_DINSTINCT_ARTICLENAMES_FROM_FREQ_SQL,
							null, null);*/
			
			DBObject groupIdFields = new BasicDBObject("_id", "$ARTICLENAME");

		    groupIdFields.put("count", new BasicDBObject("$sum", 1));
		    DBObject group = new BasicDBObject("$group", groupIdFields);

		    DBObject projectFields = new BasicDBObject("_id", 0);
		    projectFields.put("ARTICLENAME", "$_id");
		    projectFields.put("count", 1);
		    DBObject project = new BasicDBObject("$project", projectFields);
		    
		    List conditionPipeLine = Arrays.asList(group,project);
			
			List<String> articleName = new ArrayList<String>();

			for(Document tempMap : db.getCollection("frequency").aggregate(conditionPipeLine)){
				
				articleName.add((String) tempMap.get("ARTICLENAME"));
				
			}
			
			return articleName;


			/*Map<String, Object> paramMap = null;

			return namedJdbcTemplate.query(sql, paramMap,
					new ArticleNameMapper());*/

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<String> retriveDistinctTokensFromFreqForArticleName(
			String articleName) {
		try {
			/*String sql = sqlProperties
					.getMessage(
							ContextualConstantsIF.SQLS.RETRIVE_DINSTINCT_TOKENNAMES_FROM_FREQ_WHERE_ARTICLENAME_SQL,
							null, null);*/
			
			DBObject groupIdFields = new BasicDBObject("_id", "$TOKENNAME");

		    groupIdFields.put("count", new BasicDBObject("$sum", 1));
		    DBObject group = new BasicDBObject("$group", groupIdFields);

		    DBObject projectFields = new BasicDBObject("_id", 0);
		    projectFields.put("TOKENNAME", "$_id");
		    projectFields.put("count", 1);
		    DBObject project = new BasicDBObject("$project", projectFields);
		    
		    DBObject match = new BasicDBObject("$match", new BasicDBObject("ARTICLENAME",articleName));
		    
		    List conditionPipeLine = Arrays.asList(match, group, project);
			
			List<String> tokenName = new ArrayList<String>();

			for(Document tempMap : db.getCollection("tokens").aggregate(conditionPipeLine)){
				
				tokenName.add(tempMap.getString("TOKENNAME"));
				
			}
			
			return tokenName;

			
			/*Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("ARTICLENAME", articleName);

			return namedJdbcTemplate
					.query(sql, paramMap, new TokenNameMapper());*/

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	@Override
	public int retriveCountOfDocsInWhichTokenIsPresent(String tokenName) {
		int count = 0;
		try {

			/*String sql = sqlProperties
					.getMessage(
							ContextualConstantsIF.SQLS.RETRIVE_COUNT_FOR_TOKEN_WHERE_TOKENNAME_SQL,
							null, null);*/
			
			DBObject groupIdFields = new BasicDBObject("_id", "$ARTICLENAME");

		    groupIdFields.put("count", new BasicDBObject("$sum", 1));
		    DBObject group = new BasicDBObject("$group", groupIdFields);

		    DBObject projectFields = new BasicDBObject("_id", 0);
		    projectFields.put("ARTICLENAME", "$_id");
		    projectFields.put("count", 1);
		    DBObject project = new BasicDBObject("$project", projectFields);
		    
		    DBObject match = new BasicDBObject("$match", new BasicDBObject("TOKENNAME",tokenName));
		    
		    List conditionPipeLine = Arrays.asList(match, group, project);
			
		    List<String> articleNames = new ArrayList<String>();

			for(Document tempMap : db.getCollection("frequency").aggregate(conditionPipeLine)){
				
				articleNames.add(tempMap.getString("ARTICLENAME"));
				
			}
			
			/*Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("tokenName", tokenName);

			List<String> articleNames = namedJdbcTemplate.queryForList(sql,
					queryMap, String.class);*/

			if (null == articleNames) {
				count = 0;
			} else {
				count = articleNames.size();
			}

		} catch (Exception e)

		{
			count = -1;
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public FrequencyVO retriveFreqForArticleNameAndTokenName(
			String articleName, String tokenName) {
		try {

			/*String sql = sqlProperties
					.getMessage(
							ContextualConstantsIF.SQLS.RETRIVE_FREVO_FOR_ARTICLENAME_TOKENNAME_SQL_FROM_FREQ,
							null, null);*/
			
			DBObject project = new BasicDBObject("$project", new BasicDBObject("FREQ",1).append("TOKENNAME", 1));
			
			DBObject match = new BasicDBObject("$match",new BasicDBObject("ARTICLENAME",articleName).append("TOKENNAME", tokenName));
			
			List conditionPipeLine = Arrays.asList(match,project);
			
			FrequencyVO freqVO = new FrequencyVO();

			
			for(Document tempMap : db.getCollection("frequency").aggregate(conditionPipeLine)){
				
				freqVO.setFreq(tempMap.getInteger("FREQ"));
				freqVO.setTokenName(tempMap.getString("TOKENNAME"));
			}
			
			return freqVO;

			/*Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("tokenName", tokenName);
			paramMap.put("articleName", articleName);

			return namedJdbcTemplate.query(sql, paramMap,
					new FrequencyVOMapper()).get(0);*/

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	// INSERT INTO
	// featurevectors(FREQ,TOKENNAME,NOOFDOCS,IDFT,FEATUREVECTOR,ARTICLENAME)
	// VALUES(:FREQ,:TOKENNAME,:NOOFDOCS,:IDFT,:FEATUREVECTOR,:ARTICLENAME)

	@Override
	public StatusInfo insertFeatureVector(FeatureVO featureVO) {
		StatusInfo insertLoginStatus = null;
		try {
			insertLoginStatus = new StatusInfo();
			/*String sql = sqlProperties.getMessage(
					ContextualConstantsIF.DATABASESQL.INSERT_FEATUREVO_SQL,
					null, null);*/
			
			Integer FEATUREVECTORID = 1;
			
			
			DBObject project = new BasicDBObject("$project", new BasicDBObject("FEATUREVECTORID",1));
			
			DBObject sort = new BasicDBObject("$sort",new BasicDBObject("FEATUREVECTORID",-1));
			
			DBObject limit = new BasicDBObject("$limit",1);
			
			
			List conditionPipeLine = Arrays.asList(sort, project, limit);
			
			List<String> userList = new ArrayList<String>();
			
			for(Document tempMap : db.getCollection("featurevectors").aggregate(conditionPipeLine)){
				FEATUREVECTORID = tempMap.getInteger("FEATUREVECTORID");
			}

			Map<String, Object> paramMap = new HashMap<String, Object>();

			paramMap.put("FEATUREVECTORID", ++FEATUREVECTORID);
			paramMap.put("FREQ", featureVO.getFreq());
			paramMap.put("TOKENNAME", featureVO.getTokenName());
			paramMap.put("NOOFDOCS", featureVO.getNoOfDocs());
			paramMap.put("IDFT", featureVO.getIdft());
			paramMap.put("FEATUREVECTOR", featureVO.getFeatureVector());
			paramMap.put("ARTICLENAME", featureVO.getArticleName());
			
			MongoCollection<Document> collection = db.getCollection("featurevectors");
			
			collection.insertOne(new Document(paramMap));

			//namedJdbcTemplate.update(sql, paramMap);

			insertLoginStatus.setStatus(true);
			return insertLoginStatus;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			insertLoginStatus = new StatusInfo();
			insertLoginStatus.setErrMsg(e.getMessage());
			insertLoginStatus.setStatus(false);
			return insertLoginStatus;

		}
	}

	@Override
	public List<FeatureVO> viewFV() {
		try {

			/*String sql = sqlProperties.getMessage(
					ContextualConstantsIF.SQLS.RETRIVE_FV_COMPUTATION_SQL,
					null, null);*/
			
			DBObject project = new BasicDBObject("$project", new BasicDBObject("ARTICLENAME",1).append("FREQ", 1).append("TOKENNAME", 1).append("IDFT", 1).append("FEATUREVECTORID", 1).append("NOOFDOCS", 1).append("FEATUREVECTOR", 1));
			
			List conditionPipeLine = Arrays.asList(project);
			
			List<FeatureVO> featureVOList = new ArrayList<FeatureVO>();

			
			for(Document tempMap : db.getCollection("featurevectors").aggregate(conditionPipeLine)){
				
				FeatureVO featureVO = new FeatureVO();

				featureVO.setArticleName(tempMap.getString("ARTICLENAME"));
				featureVO.setFreq(tempMap.getInteger("FREQ"));
				featureVO.setTokenName(tempMap.getString("TOKENNAME"));
				featureVO.setIdft(tempMap.getDouble("IDFT"));
				featureVO.setFeatureId(tempMap.getInteger("FEATUREVECTORID"));
				featureVO.setNoOfDocs(tempMap.getInteger("NOOFDOCS"));
				featureVO.setFeatureVector(tempMap.getDouble("FEATUREVECTOR"));

				featureVOList.add(featureVO);
			}
			
			return featureVOList;
			
			/*Map<String, Object> paramMap = null;

			return namedJdbcTemplate.query(sql, paramMap,
					new FeatureVectorComputationVOMapper());*/
		} catch (Exception e) {
			System.out.println("Exception" + e);
			return null;
		}
	}

	// SELECT
	// FEATUREVECTORID,ARTICLENAME,FREQ,TOKENNAME,IDFT,NOOFDOCS,FEATUREVECTOR
	// FROM FEATUREVECTORS
	private final class FeatureVectorComputationVOMapper implements
			RowMapper<FeatureVO> {

		public FeatureVO mapRow(ResultSet rs, int arg1) throws SQLException {

			FeatureVO featureVO = new FeatureVO();

			featureVO.setArticleName(rs.getString("ARTICLENAME"));
			featureVO.setFreq(rs.getInt("FREQ"));
			featureVO.setTokenName(rs.getString("TOKENNAME"));
			featureVO.setIdft(rs.getDouble("IDFT"));
			featureVO.setFeatureId(rs.getInt("FEATUREVECTORID"));
			featureVO.setNoOfDocs(rs.getInt("NOOFDOCS"));
			featureVO.setFeatureVector(rs.getDouble("FEATUREVECTOR"));

			return featureVO;

		}

	}

	@Override
	public ArticleVO retriveArticleDetailForArticleName(String articleName) {
		try {

			/*String sql = sqlProperties
					.getMessage(
							ContextualConstantsIF.SQLS.RETRIVE_ARTICLE_FOR_ARTICLENAME_SQL,
							null, null);*/
			
			
			
			DBObject project = new BasicDBObject("$project", new BasicDBObject("ARTICLEDESC",1).append("ARTICLENAME", 1).append("USN", 1).append("PUBLISHER", 1).append("FILENAME", 1).append("CONTENTTYPE", 1).append("ARTICLEID", 1));
			
			DBObject match = new BasicDBObject("$match", new BasicDBObject("ARTICLENAME",articleName));
			
			List conditionPipeLine = Arrays.asList(match, project);
			
			ArticleVO articleVO = new ArticleVO();

			
			for(Document tempMap : db.getCollection("article").aggregate(conditionPipeLine)){
				
				articleVO.setArticleDesc(tempMap.getString("ARTICLEDESC"));
				articleVO.setArticleName(tempMap.getString("ARTICLENAME"));
				articleVO.setUSN(tempMap.getString("USN"));
				articleVO.setPublisher(tempMap.getString("PUBLISHER"));
				articleVO.setFileName(tempMap.getString("FILENAME"));
				articleVO.setContextType(tempMap.getString("CONTENTTYPE"));
				// Need to Add Retrival Of Bytes Later
				//articleVO.setFileContent(rs.getBytes("ARTICLEFILE"));
				articleVO.setArticleId(tempMap.getInteger("ARTICLEID"));
			}

			

			return articleVO;
			
			
			
			/*Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("ARTICLENAME", articleName);

			return namedJdbcTemplate.queryForObject(sql, paramMap,
					new ArticleModelVOMapper());*/
		} catch (Exception e) {
			System.out.println("Exception" + e);
			return null;
		}

	}

	@Override
	public List<Double> retriveFVForArticleNameAndTokenName(
			String distinctArticleName, String tokenTemp) {
		try {
			/*String sql = sqlProperties
					.getMessage(
							ContextualConstantsIF.SQLS.RETRIVE_FV_FOR_ARTICLENAME_TOKEN_SQL,
							null, null);*/
			
			DBObject project = new BasicDBObject("$project", new BasicDBObject("FEATUREVECTOR",1));
			
			DBObject match = new BasicDBObject("$match", new BasicDBObject("ARTICLENAME",distinctArticleName).append("TOKENNAME", new BasicDBObject("$regex", String.format(".*((?i)%s).*", tokenTemp))));
			
			List conditionPipeLine = Arrays.asList(match, project);
			
			
			List<Double> FEATURE_VECTOR_LIST = new ArrayList<Double>();
			
			for(Document tempMap : db.getCollection("featurevectors").aggregate(conditionPipeLine)){
				FEATURE_VECTOR_LIST.add(tempMap.getDouble("FEATUREVECTOR"));
			}
				
			return FEATURE_VECTOR_LIST;

			/*StringBuilder builder = new StringBuilder();
			builder.append("SELECT FEATUREVECTOR FROM FEATUREVECTORS WHERE ARTICLENAME=:ARTICLENAME AND TOKENNAME LIKE :TOKENNAME");

			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("ARTICLENAME", distinctArticleName);

			String value = "%" + tokenTemp + "%";

			queryMap.put("TOKENNAME", value);

			return namedJdbcTemplate.queryForList(builder.toString(), queryMap,
					Double.class);*/

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	// INSERT INTO BESTFV(FEATUREVECTOR,URL,ARTICLENAME)
	// VALUES(:FEATUREVECTOR,:URL,:ARTICLENAME)

	@Override
	public StatusInfo insertBestFV(List<BestFeatureVector> bestFV) {
		StatusInfo statusInfo = null;
		try {
			statusInfo = new StatusInfo();
			/*String sql = sqlProperties.getMessage(
					ContextualConstantsIF.SQLS.INSERT_BESTFV_SQL, null, null);*/
		
			List<Document> keyList = new ArrayList<Document>();
			
			
			for (BestFeatureVector bfv : bestFV) {
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("FEATUREVECTOR", bfv.getFeatureVector());
				paramMap.put("ARTICLENAME", bfv.getArticleName());
				
				keyList.add(new Document(paramMap));
				//namedJdbcTemplate.update(sql, paramMap);
			}
			
			MongoCollection<Document> collection = db.getCollection("bestfv");
			
			collection.insertMany(keyList);
			
			statusInfo.setStatus(true);
		} catch (Exception e) {
			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setExceptionMsg(e.getMessage());
		}
		return statusInfo;
	}

	// SELECT URL,FEATUREVECTOR,ARTICLENAME FROM BESTFV ORDER BY FEATUREVECTOR
	// DESC
	@Override
	public List<BestFeatureVector> rateArticles() {
		List<BestFeatureVector> urlList = null;
		try {

			urlList = new ArrayList<BestFeatureVector>();
			/*String sql = sqlProperties.getMessage(
					ContextualConstantsIF.SQLS.RANK_BESTFV_SQL, null, null);*/
			
			DBObject project = new BasicDBObject("$project", new BasicDBObject("FEATUREVECTOR",1).append("ARTICLENAME", 1));
			
			DBObject sort = new BasicDBObject("$sort",new BasicDBObject("FEATUREVECTOR",-1));
			
			Double ID = 0.0;
			
			DBObject match = new BasicDBObject("$match",new BasicDBObject("FEATUREVECTOR",new BasicDBObject("$gt",0)));
			
			
			List conditionPipeLine = Arrays.asList(match, sort, project);
			
			//List<String> bestFeatureVectorList = new ArrayList<String>();
			
			for(Document tempMap : db.getCollection("bestfv").aggregate(conditionPipeLine)){
				BestFeatureVector bestFeatureVector = new BestFeatureVector();

				bestFeatureVector.setFeatureVector(tempMap.getDouble("FEATUREVECTOR"));

				bestFeatureVector.setArticleName(tempMap.getString("ARTICLENAME"));

				urlList.add(bestFeatureVector);
			}
			

			/*Map<String, Object> paramMap = null;
			urlList = namedJdbcTemplate.query(sql, paramMap,
					new BestFeatureVectorMapper());*/

		} catch (Exception e) {
			e.printStackTrace();
			return null;

		}
		return urlList;
	}

	private final class BestFeatureVectorMapper implements
			RowMapper<BestFeatureVector> {

		public BestFeatureVector mapRow(ResultSet rs, int arg1)
				throws SQLException {

			BestFeatureVector bestFeatureVector = new BestFeatureVector();

			bestFeatureVector.setFeatureVector(rs.getDouble("FEATUREVECTOR"));

			bestFeatureVector.setArticleName(rs.getString("ARTICLENAME"));

			return bestFeatureVector;

		}

	}

	@Override
	public StatusInfo deleteFromBestFV() {
		StatusInfo statusInfo = null;
		try {
			statusInfo = new StatusInfo();
			/*String sql = sqlProperties.getMessage(
					ContextualConstantsIF.SQLS.DELETE_FROM_BESTFV_SQL, null,
					null);*/
			
			db.getCollection("bestfv").drop();
			
			/*Map<String, Object> paramMap = null;
			namedJdbcTemplate.update(sql, paramMap);*/
			statusInfo.setStatus(true);

		} catch (Exception e) {
			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
		}

		return statusInfo;
	}

	@Override
	public List<String> retriveKeywordsForArticles(String articleName) {
		try {
			/*String sql = sqlProperties
					.getMessage(
							ContextualConstantsIF.SQLS.RETRIVE_KEYWORDS_WHERE_ARTICLENAME_SQL,
							null, null);*/
			
			DBObject project = new BasicDBObject("$project", new BasicDBObject("KEYWORD",1).append("ARTICLENAME", 1));
			
			
			DBObject match = new BasicDBObject("$match",new BasicDBObject("ARTICLENAME",articleName));
			
			
			List conditionPipeLine = Arrays.asList(match, project);
			
			List<String> KEY_WORD_LIST = new ArrayList<String>();
			
			for(Document tempMap : db.getCollection("keywordlist").aggregate(conditionPipeLine)){
				KEY_WORD_LIST.add(tempMap.getString("KEYWORD"));
			}
			

			return KEY_WORD_LIST;
			
			/*Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("ARTICLENAME", articleName);

			return namedJdbcTemplate.query(sql, paramMap, new KeywordMapper());*/

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	private final class KeywordMapper implements RowMapper<String> {

		public String mapRow(ResultSet rs, int arg1) throws SQLException {

			return rs.getString("KEYWORD");

		}

	}

	// SELECT AUTHORNAME FROM AUTHORLIST
	@Override
	public List<String> retriveAuthorListForArticles(String articleName) {
		try {
		/*String sql = sqlProperties
					.getMessage(
							ContextualConstantsIF.SQLS.RETRIVE_AUTHORS_WHERE_ARTICLENAME_SQL,
							null, null);*/
			
			
			
			DBObject project = new BasicDBObject("$project", new BasicDBObject("AUTHORNAME",1));
			
			
			DBObject match = new BasicDBObject("$match",new BasicDBObject("ARTICLENAME",articleName));
			
			
			List conditionPipeLine = Arrays.asList(match, project);
			
			List<String> AUTHOR_NAME_LIST = new ArrayList<String>();
			
			for(Document tempMap : db.getCollection("authorlist").aggregate(conditionPipeLine)){
				AUTHOR_NAME_LIST.add(tempMap.getString("AUTHORNAME"));
			}
			

			return AUTHOR_NAME_LIST;

			

			/*Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("ARTICLENAME", articleName);

			return namedJdbcTemplate.query(sql, paramMap,
					new AuthorNameMapper());*/

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	private final class AuthorNameMapper implements RowMapper<String> {

		public String mapRow(ResultSet rs, int arg1) throws SQLException {
			return rs.getString("AUTHORNAME");
		}
	}

	@Override
	public List<Integer> retriveFreqValuesForArticleNameFromLogLikelihoodANDWordsIN(
			String articleName, List<String> keyPhraseIntersection) {
		try {
			/*String sql = sqlProperties
					.getMessage(
							ContextualConstantsIF.SQLS.RETRIVE_FREQ_FROM_LOGLIKELIHOOD_WHERE_ARTICLENAME_AND_WORDSIN_SQL,
							null, null);*/
			
			DBObject project = new BasicDBObject("$project", new BasicDBObject("FREQ",1));
			
			
			DBObject match = new BasicDBObject("$match",new BasicDBObject("ARTICLENAME",articleName).append("TOKENNAME", new BasicDBObject("$in",keyPhraseIntersection)));
			
			
			List conditionPipeLine = Arrays.asList(match, project);
			
			List<Integer> FREQ_LIST = new ArrayList<Integer>();
			
			for(Document tempMap : db.getCollection("loglikelihood").aggregate(conditionPipeLine)){
				FREQ_LIST.add(tempMap.getInteger("FREQ"));
			}
			

			return FREQ_LIST;

			/*Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("articleName", articleName);
			paramMap.put("words", keyPhraseIntersection);

			return namedJdbcTemplate
					.query(sql, paramMap, new FreqValueMapper());*/

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<String> retriveDistincetTokensForArticleNameFromFrequeny(
			String articleNameLeft) {
		try {
			/*String sql = sqlProperties
					.getMessage(
							ContextualConstantsIF.SQLS.RETRIVE_UNIQUE_TOKENS_FROM_FREQ_WHERE_ARTICLENAME_SQL,
							null, null);*/
			
			
			DBObject groupIdFields = new BasicDBObject("_id", "$TOKENNAME");

		    groupIdFields.put("count", new BasicDBObject("$sum", 1));
		    DBObject group = new BasicDBObject("$group", groupIdFields);

		    DBObject projectFields = new BasicDBObject("_id", 0);
		    projectFields.put("TOKENNAME", "$_id");
		    projectFields.put("count", 1);
		    DBObject project = new BasicDBObject("$project", projectFields);
		    
		    DBObject match = new BasicDBObject("$match",new BasicDBObject("ARTICLENAME",articleNameLeft));
		    
		    List conditionPipeLine = Arrays.asList(match, group,project);
			
			List<String> tokenName = new ArrayList<String>();

			for(Document tempMap : db.getCollection("frequency").aggregate(conditionPipeLine)){
				
				tokenName.add((String) tempMap.get("TOKENNAME"));
				
			}

			return tokenName;
			
			/*Map<String, Object> map = new HashMap<String, Object>();

			map.put("articleName", articleNameLeft);

			return namedJdbcTemplate.query(sql, map, new TokenOnlyMapper());*/

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<Integer> retriveFreqValuesForArticleNameFromFreqANDWordsIN(
			String articleName, List<String> keyPhraseIntersection) {
		try {
			/*String sql = sqlProperties
					.getMessage(
							ContextualConstantsIF.SQLS.RETRIVE_FREQ_FROM_FREQ_WHERE_ARTICLENAME_AND_TOKENNAME_SQL,
							null, null);*/
			
			DBObject project = new BasicDBObject("$project", new BasicDBObject("FREQ",1));
			
			
			DBObject match = new BasicDBObject("$match",new BasicDBObject("ARTICLENAME",articleName).append("TOKENNAME", new BasicDBObject("$in",keyPhraseIntersection)));
			
			
			List conditionPipeLine = Arrays.asList(match, project);
			
			List<Integer> FREQ_LIST = new ArrayList<Integer>();
			
			for(Document tempMap : db.getCollection("frequency").aggregate(conditionPipeLine)){
				FREQ_LIST.add(tempMap.getInteger("FREQ"));
			}
			

			return FREQ_LIST;

			/*Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("articleName", articleName);
			paramMap.put("words", keyPhraseIntersection);

			return namedJdbcTemplate
					.query(sql, paramMap, new FreqValueMapper());*/

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	@Override
	public ArticleVO retriveArticleDetailForArticleId(String articleName) {
		try {

			/*String sql = sqlProperties
					.getMessage(
							ContextualConstantsIF.SQLS.RETRIVE_ARTICLE_FOR_ARTICLEID_SQL,
							null, null);*/
			
			DBObject project = new BasicDBObject("$project", new BasicDBObject("ARTICLENAME",1).append("ARTICLEDESC", 1).append("USN", 1).append("PUBLISHER", 1).append("CONTENTTYPE", 1).append("ARTICLENAME",1).append("FILENAME", 1).append("ARTICLEID", 1));
			
			DBObject match = new BasicDBObject("$match",new BasicDBObject("ARTICLEID",Integer.parseInt(articleName)));
			
			List conditionPipeLine = Arrays.asList(match, project);
			
			ArticleVO article = new ArticleVO();
			
			for(Document tempMap : db.getCollection("article").aggregate(conditionPipeLine)){
				
				
				article.setArticleName(tempMap.getString("ARTICLENAME"));
				article.setArticleDesc(tempMap.getString("ARTICLEDESC"));
				article.setUSN(tempMap.getString("USN"));
				article.setPublisher(tempMap.getString("PUBLISHER"));
				article.setContextType(tempMap.getString("CONTENTTYPE"));
				article.setArticleName(tempMap.getString("ARTICLENAME"));
				article.setFileName(tempMap.getString("FILENAME"));
				article.setArticleId(tempMap.getInteger("ARTICLEID"));
				
			}

			/*Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("ARTICLEID", Integer.parseInt(articleName));

			return namedJdbcTemplate.queryForObject(sql, paramMap,
					new ArticleModelVOMapper());*/
			return article;
		} catch (Exception e) {
			System.out.println("Exception" + e);
			return null;
		}
	}

	@Override
	public int retriveArticleIdForArticleName(String articleName) {
		try {
			/*String sql = sqlProperties
					.getMessage(
							ContextualConstantsIF.SQLS.RETRIVE_ARTICLEID_FOR_ARTICLENAME_SQL,
							null, null);*/
			
			DBObject project = new BasicDBObject("$project", new BasicDBObject("ARTICLEID",1));
			
			DBObject match = new BasicDBObject("$match",new BasicDBObject("ARTICLENAME",articleName));
			
			List conditionPipeLine = Arrays.asList(match, project);
			
			Integer ARTICLE_ID = null;
			
			for(Document tempMap : db.getCollection("article").aggregate(conditionPipeLine)){
				ARTICLE_ID = tempMap.getInteger("ARTICLEID");
			}
			
			return ARTICLE_ID;
			
			/*Map<String, Object> map = new HashMap<String, Object>();
			map.put("ARTICLENAME", articleName);

			return namedJdbcTemplate.queryForObject(sql, map,
					new UniqueArticleIdsMapper());*/

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return -1;
		}
	}

	private final class UniqueArticleIdsMapper implements RowMapper<Integer> {

		public Integer mapRow(ResultSet rs, int arg1) throws SQLException {
			return rs.getInt("ARTICLEID");
		}
	}

	@Override
	public List<String> retriveUniqueArticleNamesFromAuthorList(
			List<String> authors) {
		try {
			/*String sql = sqlProperties
					.getMessage(
							ContextualConstantsIF.SQLS.RETRIVE_UNIQUE_ARTICLENAMES_FOR_AUTHORS_IN_SQL,
							null, null);*/
			
			
			DBObject groupIdFields = new BasicDBObject("_id", "$ARTICLENAME");

		    groupIdFields.put("count", new BasicDBObject("$sum", 1));
		    DBObject group = new BasicDBObject("$group", groupIdFields);

		    DBObject projectFields = new BasicDBObject("_id", 0);
		    projectFields.put("ARTICLENAME", "$_id");
		    projectFields.put("count", 1);
		    DBObject project = new BasicDBObject("$project", projectFields);
		    
		    DBObject match = new BasicDBObject("$match", new BasicDBObject("AUTHORNAME", new BasicDBObject("$in",authors)));
		    
		    List conditionPipeLine = Arrays.asList(match, group,project);
			
			List<String> ARTICLE_NAME_LIST = new ArrayList<String>();

			for(Document tempMap : db.getCollection("authorlist").aggregate(conditionPipeLine)){
				
				ARTICLE_NAME_LIST.add((String) tempMap.get("ARTICLENAME"));
				
			}
			
			return ARTICLE_NAME_LIST;

			/*Map<String, Object> map = new HashMap<String, Object>();
			map.put("AUTHORNAME", authors);

			return namedJdbcTemplate.query(sql, map,
					new UniqueArticleNamesMapper());*/

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<String> retriveUniqueArticleNamesFromKeywordList(List<String> keywords) {
		try {
			/*String sql = sqlProperties
					.getMessage(
							ContextualConstantsIF.SQLS.RETRIVE_UNIQUE_ARTICLENAMES_FOR_KEYWORDS_IN_SQL,
							null, null);*/
			
			
			DBObject groupIdFields = new BasicDBObject("_id", "$ARTICLENAME");

		    groupIdFields.put("count", new BasicDBObject("$sum", 1));
		    DBObject group = new BasicDBObject("$group", groupIdFields);

		    DBObject projectFields = new BasicDBObject("_id", 0);
		    projectFields.put("ARTICLENAME", "$_id");
		    projectFields.put("count", 1);
		    DBObject project = new BasicDBObject("$project", projectFields);
		    
		    DBObject match = new BasicDBObject("$match", new BasicDBObject("KEYWORD", new BasicDBObject("$in",keywords)));
		    
		    List conditionPipeLine = Arrays.asList(match, group,project);
			
			List<String> ARTICLE_NAME_LIST = new ArrayList<String>();

			for(Document tempMap : db.getCollection("keywordlist").aggregate(conditionPipeLine)){
				
				ARTICLE_NAME_LIST.add((String) tempMap.get("ARTICLENAME"));
				
			}
			
			return ARTICLE_NAME_LIST;


			/*Map<String, Object> map = new HashMap<String, Object>();
			map.put("KEYWORD", keywords);

			return namedJdbcTemplate.query(sql, map,
					new UniqueArticleNamesMapper());*/

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<String> retriveUniqueArticleNamesFromPublisherList(
			List<String> publishers) {
		try {
			/*String sql = sqlProperties
					.getMessage(
							ContextualConstantsIF.SQLS.RETRIVE_UNIQUE_ARTICLENAMES_FOR_PUBLISHERS_IN_SQL,
							null, null);*/
			
			
			DBObject groupIdFields = new BasicDBObject("_id", "$ARTICLENAME");

		    groupIdFields.put("count", new BasicDBObject("$sum", 1));
		    DBObject group = new BasicDBObject("$group", groupIdFields);

		    DBObject projectFields = new BasicDBObject("_id", 0);
		    projectFields.put("ARTICLENAME", "$_id");
		    projectFields.put("count", 1);
		    DBObject project = new BasicDBObject("$project", projectFields);
		    
		    DBObject match = new BasicDBObject("$match", new BasicDBObject("KEYWORD", new BasicDBObject("$in",publishers)));
		    
		    List conditionPipeLine = Arrays.asList(match, group,project);
			
			List<String> ARTICLE_NAME_LIST = new ArrayList<String>();

			for(Document tempMap : db.getCollection("article").aggregate(conditionPipeLine)){
				
				ARTICLE_NAME_LIST.add((String) tempMap.get("ARTICLENAME"));
				
			}
			
			return ARTICLE_NAME_LIST;

			/*Map<String, Object> map = new HashMap<String, Object>();
			map.put("PUBLISHER", publishers);

			return namedJdbcTemplate.query(sql, map,
					new UniqueArticleNamesMapper());*/

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<String> getAllInterestArea(String interestArea) {
		try {
			/*String sql = sqlProperties
					.getMessage(
							ContextualConstantsIF.SQLS.RETRIVE_FV_FOR_ARTICLENAME_TOKEN_SQL,
							null, null);*/
			
			DBObject project = new BasicDBObject("$project", new BasicDBObject("ARTICLENAME",1));
			
			DBObject match = new BasicDBObject("$match", new BasicDBObject("ARTICLENAME",new BasicDBObject("$regex", String.format(".*((?i)%s).*", interestArea))));
			
			List conditionPipeLine = Arrays.asList(match, project);
			
			
			List<String> INTEREST_AREA_LIST = new ArrayList<String>();
			
			for(Document tempMap : db.getCollection("article").aggregate(conditionPipeLine)){
				INTEREST_AREA_LIST.add(tempMap.getString("ARTICLENAME"));
			}
				
			return INTEREST_AREA_LIST;

			/*StringBuilder builder = new StringBuilder();
			builder.append("SELECT FEATUREVECTOR FROM FEATUREVECTORS WHERE ARTICLENAME=:ARTICLENAME AND TOKENNAME LIKE :TOKENNAME");

			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("ARTICLENAME", distinctArticleName);

			String value = "%" + tokenTemp + "%";

			queryMap.put("TOKENNAME", value);

			return namedJdbcTemplate.queryForList(builder.toString(), queryMap,
					Double.class);*/

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	@Override
	public int retriveCountOfDocsInWhichArticleIsPresent(String area) {
		int count = 0;
		try {

			/*String sql = sqlProperties
					.getMessage(
							ContextualConstantsIF.SQLS.RETRIVE_COUNT_FOR_TOKEN_WHERE_TOKENNAME_SQL,
							null, null);*/
			
			DBObject groupIdFields = new BasicDBObject("_id", "$TOKENNAME");

		    groupIdFields.put("count", new BasicDBObject("$sum", 1));
		    DBObject group = new BasicDBObject("$group", groupIdFields);

		    DBObject projectFields = new BasicDBObject("_id", 0);
		    projectFields.put("TOKENNAME", "$_id");
		    projectFields.put("count", 1);
		    DBObject project = new BasicDBObject("$project", projectFields);
		    
		    DBObject match = new BasicDBObject("$match", new BasicDBObject("ARTICLENAME",area));
		    
		    List conditionPipeLine = Arrays.asList(match, group, project);
			
		    List<String> articleNames = new ArrayList<String>();

			for(Document tempMap : db.getCollection("frequency").aggregate(conditionPipeLine)){
				
				articleNames.add(tempMap.getString("TOKENNAME"));
				
			}
			
			/*Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("tokenName", tokenName);

			List<String> articleNames = namedJdbcTemplate.queryForList(sql,
					queryMap, String.class);*/

			if (null == articleNames) {
				count = 0;
			} else {
				count = articleNames.size();
			}

		} catch (Exception e)

		{
			count = -1;
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public int retriveFreqForArticleName(String area) {
		int count = 0;
		try {

			/*String sql = sqlProperties
					.getMessage(
							ContextualConstantsIF.SQLS.RETRIVE_COUNT_FOR_TOKEN_WHERE_TOKENNAME_SQL,
							null, null);*/
			
			DBObject groupIdFields = new BasicDBObject("_id", "$TOKENNAME");

		    groupIdFields.put("count", new BasicDBObject("$sum", 1));
		    DBObject group = new BasicDBObject("$group", groupIdFields);

		    DBObject projectFields = new BasicDBObject("_id", 0);
		    projectFields.put("TOKENNAME", "$_id");
		    projectFields.put("count", 1);
		    DBObject project = new BasicDBObject("$project", projectFields);
		    
		    DBObject match = new BasicDBObject("$match", new BasicDBObject("ARTICLENAME",area));
		    
		    List conditionPipeLine = Arrays.asList(match, group, project);
			
		    List<String> articleNames = new ArrayList<String>();

			for(Document tempMap : db.getCollection("tokens").aggregate(conditionPipeLine)){
				
				articleNames.add(tempMap.getString("TOKENNAME"));
				
			}
			
			/*Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("tokenName", tokenName);

			List<String> articleNames = namedJdbcTemplate.queryForList(sql,
					queryMap, String.class);*/

			if (null == articleNames) {
				count = 0;
			} else {
				count = articleNames.size();
			}

		} catch (Exception e)

		{
			count = -1;
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public Set<String> getAllPublisher(String trim) {
		try {
			/*String sql = sqlProperties
					.getMessage(
							ContextualConstantsIF.SQLS.RETRIVE_FV_FOR_ARTICLENAME_TOKEN_SQL,
							null, null);*/
			
			DBObject project = new BasicDBObject("$project", new BasicDBObject("ARTICLENAME",1));
			
			DBObject match = new BasicDBObject("$match", new BasicDBObject("PUBLISHER",new BasicDBObject("$regex", String.format(".*((?i)%s).*", trim))));
			
			List conditionPipeLine = Arrays.asList(match, project);
			
			
			Set<String> PUBLISHER_LIST = new HashSet<String>();
			
			for(Document tempMap : db.getCollection("article").aggregate(conditionPipeLine)){
				PUBLISHER_LIST.add(tempMap.getString("ARTICLENAME"));
			}
				
			return PUBLISHER_LIST;

			/*StringBuilder builder = new StringBuilder();
			builder.append("SELECT FEATUREVECTOR FROM FEATUREVECTORS WHERE ARTICLENAME=:ARTICLENAME AND TOKENNAME LIKE :TOKENNAME");

			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("ARTICLENAME", distinctArticleName);

			String value = "%" + tokenTemp + "%";

			queryMap.put("TOKENNAME", value);

			return namedJdbcTemplate.queryForList(builder.toString(), queryMap,
					Double.class);*/

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	@Override
	public int retriveCountOfDocsInWhichPublisherIsPresent(String articleName, String publisherName) {
		int count = 0;
		try {

			/*String sql = sqlProperties
					.getMessage(
							ContextualConstantsIF.SQLS.RETRIVE_COUNT_FOR_TOKEN_WHERE_TOKENNAME_SQL,
							null, null);*/
			
			DBObject groupIdFields = new BasicDBObject("_id", "$ARTICLENAME");

		    groupIdFields.put("count", new BasicDBObject("$sum", 1));
		    DBObject group = new BasicDBObject("$group", groupIdFields);

		    DBObject projectFields = new BasicDBObject("_id", 0);
		    projectFields.put("ARTICLENAME", "$_id");
		    projectFields.put("count", 1);
		    DBObject project = new BasicDBObject("$project", projectFields);
		    
		    DBObject match = new BasicDBObject("$match", new BasicDBObject("PUBLISHER",new BasicDBObject("$regex", String.format(".*((?i)%s).*", publisherName))));

		    List conditionPipeLine = Arrays.asList(match, group, project);
			
		    List<String> articleNames = new ArrayList<String>();

			for(Document tempMap : db.getCollection("article").aggregate(conditionPipeLine)){
				
				articleNames.add(tempMap.getString("ARTICLENAME"));
				
			}
			
			/*Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("tokenName", tokenName);

			List<String> articleNames = namedJdbcTemplate.queryForList(sql,
					queryMap, String.class);*/

			if (null == articleNames) {
				count = 0;
			} else {
				count = articleNames.size();
			}

		} catch (Exception e)

		{
			count = -1;
			e.printStackTrace();
		}
		return count;

	}

	@Override
	public int retriveFreqForArticleNameWithPublisher(String area) {
		int count = 0;
		try {

			/*String sql = sqlProperties
					.getMessage(
							ContextualConstantsIF.SQLS.RETRIVE_COUNT_FOR_TOKEN_WHERE_TOKENNAME_SQL,
							null, null);*/
			
			DBObject groupIdFields = new BasicDBObject("_id", "$PUBLISHER");

		    groupIdFields.put("count", new BasicDBObject("$sum", 1));
		    DBObject group = new BasicDBObject("$group", groupIdFields);

		    DBObject projectFields = new BasicDBObject("_id", 0);
		    projectFields.put("PUBLISHER", "$_id");
		    projectFields.put("count", 1);
		    DBObject project = new BasicDBObject("$project", projectFields);
		    
		    DBObject match = new BasicDBObject("$match", new BasicDBObject("ARTICLENAME",area));
		    
		    List conditionPipeLine = Arrays.asList(match, group, project);
			
		    List<String> articleNames = new ArrayList<String>();

			for(Document tempMap : db.getCollection("article").aggregate(conditionPipeLine)){
				
				articleNames.add(tempMap.getString("PUBLISHER"));
				
			}
			
			/*Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("tokenName", tokenName);

			List<String> articleNames = namedJdbcTemplate.queryForList(sql,
					queryMap, String.class);*/

			if (null == articleNames) {
				count = 0;
			} else {
				count = articleNames.size();
			}

		} catch (Exception e)

		{
			count = -1;
			e.printStackTrace();
		}
		return count;
	}
	
	
	
	@Override
	public StatusInfo deleteArticleDetailForArticleId(String articleName) {
		StatusInfo statusInfo = null;
		try {
			statusInfo = new StatusInfo();
			/*String sql = sqlProperties.getMessage(
					ContextualConstantsIF.SQLS.DELETE_FROM_BESTFV_SQL, null,
					null);*/
			
			DBObject project = new BasicDBObject("$project", new BasicDBObject("ARTICLENAME",1));
			
			DBObject match = new BasicDBObject("$match", new BasicDBObject("ARTICLEID",Integer.parseInt(articleName)));
			
			List conditionPipeLine = Arrays.asList(match, project);
			
			
			String ARTICLENAME_NAME = null;
			
			for(Document tempMap : db.getCollection("article").aggregate(conditionPipeLine)){
				ARTICLENAME_NAME = tempMap.getString("ARTICLENAME");
			}
			
			Bson conditionArticle = new Document("ARTICLENAME", ARTICLENAME_NAME);
			
			db.getCollection("authorlist").deleteMany(conditionArticle);
			
			db.getCollection("cleanarticles").deleteMany(conditionArticle);
			
			db.getCollection("featurevectors").deleteMany(conditionArticle);
			
			db.getCollection("frequency").deleteMany(conditionArticle);
			
			db.getCollection("groupinfo").deleteMany(conditionArticle);
			
			db.getCollection("keywordlist").deleteMany(conditionArticle);
			
			db.getCollection("loglikelihood").deleteMany(conditionArticle);
			
			db.getCollection("tokens").deleteMany(conditionArticle);

			
			Bson condition = new Document("ARTICLEID", Integer.parseInt(articleName));
			
			db.getCollection("article").deleteOne(condition);
			
			/*Map<String, Object> paramMap = null;
			namedJdbcTemplate.update(sql, paramMap);*/
			statusInfo.setStatus(true);
		} catch (Exception e) {
			e.printStackTrace();
			statusInfo.setStatus(false);
			statusInfo.setExceptionMsg(e.getMessage());
		}
		return statusInfo;

		
	}

	

	@Override
	public Set<String> getAllAuthors(String trim) {
		try {
			/*String sql = sqlProperties
					.getMessage(
							ContextualConstantsIF.SQLS.RETRIVE_FV_FOR_ARTICLENAME_TOKEN_SQL,
							null, null);*/
			
			DBObject project = new BasicDBObject("$project", new BasicDBObject("ARTICLENAME",1));
			
			DBObject match = new BasicDBObject("$match", new BasicDBObject("AUTHORNAME",new BasicDBObject("$regex", String.format(".*((?i)%s).*", trim))));
			
			List conditionPipeLine = Arrays.asList(match, project);
			
			
			Set<String> PUBLISHER_LIST = new HashSet<String>();
			
			for(Document tempMap : db.getCollection("authorlist").aggregate(conditionPipeLine)){
				PUBLISHER_LIST.add(tempMap.getString("ARTICLENAME"));
			}
				
			return PUBLISHER_LIST;

			/*StringBuilder builder = new StringBuilder();
			builder.append("SELECT FEATUREVECTOR FROM FEATUREVECTORS WHERE ARTICLENAME=:ARTICLENAME AND TOKENNAME LIKE :TOKENNAME");

			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("ARTICLENAME", distinctArticleName);

			String value = "%" + tokenTemp + "%";

			queryMap.put("TOKENNAME", value);

			return namedJdbcTemplate.queryForList(builder.toString(), queryMap,
					Double.class);*/

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	@Override
	public int retriveCountOfDocsInWhichAuthorsIsPresent(String area,
			String authors) {
		int count = 0;
		try {

			/*String sql = sqlProperties
					.getMessage(
							ContextualConstantsIF.SQLS.RETRIVE_COUNT_FOR_TOKEN_WHERE_TOKENNAME_SQL,
							null, null);*/
			
			DBObject groupIdFields = new BasicDBObject("_id", "$ARTICLENAME");

		    groupIdFields.put("count", new BasicDBObject("$sum", 1));
		    DBObject group = new BasicDBObject("$group", groupIdFields);

		    DBObject projectFields = new BasicDBObject("_id", 0);
		    projectFields.put("ARTICLENAME", "$_id");
		    projectFields.put("count", 1);
		    DBObject project = new BasicDBObject("$project", projectFields);
		    
		    DBObject match = new BasicDBObject("$match", new BasicDBObject("AUTHORNAME",new BasicDBObject("$regex", String.format(".*((?i)%s).*", authors))));

		    List conditionPipeLine = Arrays.asList(match, group, project);
			
		    List<String> articleNames = new ArrayList<String>();

			for(Document tempMap : db.getCollection("ARTICLENAME").aggregate(conditionPipeLine)){
				
				articleNames.add(tempMap.getString("ARTICLENAME"));
				
			}
			
			/*Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("tokenName", tokenName);

			List<String> articleNames = namedJdbcTemplate.queryForList(sql,
					queryMap, String.class);*/

			if (null == articleNames) {
				count = 0;
			} else {
				count = articleNames.size();
			}

		} catch (Exception e)

		{
			count = -1;
			e.printStackTrace();
		}
		return count;

	}

	@Override
	public Set<String> getAllKeyWord(String keywords) {
		try {
			/*String sql = sqlProperties
					.getMessage(
							ContextualConstantsIF.SQLS.RETRIVE_FV_FOR_ARTICLENAME_TOKEN_SQL,
							null, null);*/
			
			DBObject project = new BasicDBObject("$project", new BasicDBObject("ARTICLENAME",1));
			
			DBObject match = new BasicDBObject("$match", new BasicDBObject("KEYWORD",new BasicDBObject("$regex", String.format(".*((?i)%s).*", keywords))));
			
			List conditionPipeLine = Arrays.asList(match, project);
			
			
			Set<String> KEYWORD_LIST = new HashSet<String>();
			
			for(Document tempMap : db.getCollection("keywordlist").aggregate(conditionPipeLine)){
				KEYWORD_LIST.add(tempMap.getString("ARTICLENAME"));
			}
				
			return KEYWORD_LIST;

			/*StringBuilder builder = new StringBuilder();
			builder.append("SELECT FEATUREVECTOR FROM FEATUREVECTORS WHERE ARTICLENAME=:ARTICLENAME AND TOKENNAME LIKE :TOKENNAME");

			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("ARTICLENAME", distinctArticleName);

			String value = "%" + tokenTemp + "%";

			queryMap.put("TOKENNAME", value);

			return namedJdbcTemplate.queryForList(builder.toString(), queryMap,
					Double.class);*/

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	@Override
	public int retriveCountOfDocsInWhichKeywordsIsPresent(String area,
			String keywords) {
		int count = 0;
		try {

			/*String sql = sqlProperties
					.getMessage(
							ContextualConstantsIF.SQLS.RETRIVE_COUNT_FOR_TOKEN_WHERE_TOKENNAME_SQL,
							null, null);*/
			
			DBObject groupIdFields = new BasicDBObject("_id", "$ARTICLENAME");

		    groupIdFields.put("count", new BasicDBObject("$sum", 1));
		    DBObject group = new BasicDBObject("$group", groupIdFields);

		    DBObject projectFields = new BasicDBObject("_id", 0);
		    projectFields.put("ARTICLENAME", "$_id");
		    projectFields.put("count", 1);
		    DBObject project = new BasicDBObject("$project", projectFields);
		    
		    DBObject match = new BasicDBObject("$match", new BasicDBObject("keywordlist",new BasicDBObject("$regex", String.format(".*((?i)%s).*", keywords))));

		    List conditionPipeLine = Arrays.asList(match, group, project);
			
		    List<String> articleNames = new ArrayList<String>();

			for(Document tempMap : db.getCollection("keywordlist").aggregate(conditionPipeLine)){
				
				articleNames.add(tempMap.getString("ARTICLENAME"));
				
			}
			
			/*Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("tokenName", tokenName);

			List<String> articleNames = namedJdbcTemplate.queryForList(sql,
					queryMap, String.class);*/

			if (null == articleNames) {
				count = 0;
			} else {
				count = articleNames.size();
			}

		} catch (Exception e)

		{
			count = -1;
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public StatusInfo insertSearchHistory(List<Map<String, Object>> searchHistoryList) {
		
		StatusInfo statusInfo = new StatusInfo();
		try{
			
			
		List<Document> historyList = new ArrayList<Document>();
		
		for(Map<String, Object> map : searchHistoryList){
			historyList.add(new Document(map));
		}
		MongoCollection<Document> collection = db.getCollection("usersearchhistory");
		
		collection.insertMany(historyList);
	
		statusInfo.setStatus(true);
	} catch (Exception e) {
		statusInfo.setStatus(false);
		statusInfo.setExceptionMsg(e.getMessage());
	}
	return statusInfo;

	}

	@Override
	public List<SearchObj> getHistoryByUSN(String USN) {
		try{
			DBObject project = new BasicDBObject("$project", new BasicDBObject("FEATUREVECTOR",1).append("ARTICLEDESC", 1).append("ARTICLENAME", 1).append("USN", 1).append("ARTICLEID", 1).append("HIDDENURL", 1).append("PUBLISHER", 1).append("KEYWORD", 1));
			
			DBObject match = new BasicDBObject("$match",new BasicDBObject("USN",USN));
			
			DBObject sort = new BasicDBObject("$sort",new BasicDBObject("FEATUREVECTOR",-1));
			
			
			List conditionPipeLine = Arrays.asList(match, project);
			
			List<SearchObj> historyList = new ArrayList<SearchObj>();
			
			for(Document tempMap : db.getCollection("usersearchhistory").aggregate(conditionPipeLine)){
				SearchObj search = new SearchObj();
				search.setFeatureVector(tempMap.getDouble("FEATUREVECTOR"));
				search.setDesc(tempMap.getString("ARTICLEDESC"));
				search.setTitle(tempMap.getString("ARTICLENAME"));
				search.setUserId(tempMap.getString("USN"));
				search.setArticleId(tempMap.getInteger("ARTICLEID"));
				search.setUrl(tempMap.getString("HIDDENURL"));
				search.setPublisher(tempMap.getString("PUBLISHER"));
				
				//List<String> keywords = (List<String>) tempMap.get("KEYWORD");

				
				//search.setKeywords(tempMap.getString("KEYWORD"));
				
				historyList.add(search);
			}
			
			return historyList;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	}
