package com.service.impl;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.constants.ContextualConstantsIF;
import com.dao.inter.ContextualDAOIF;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.AdjectiveVO;
import com.model.ArticleNamesVO;
import com.model.ArticleTypesVO;
import com.model.ArticleVO;
import com.model.BestFeatureVector;
import com.model.BinaryCompareObject;
import com.model.CleanArticleModel;
import com.model.CleanArticleUIModel;
import com.model.CompareArticleRVVO;
import com.model.CompareArticleVO;
import com.model.ComparisionInputModel;
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
import com.model.ArticleDetailModel;
import com.model.ArticleModel;
import com.model.SearchCriteria;
import com.model.SearchObj;
import com.model.StatusInfo;
import com.model.StopWordsVO;
import com.model.StructureVO;
import com.model.TextFreqComputeVO;
import com.model.ThemeModel;
import com.model.TokenVO;
import com.model.WebSiteDataVO;
import com.model.WebSiteUrlModel;
import com.service.inter.ContextualServiceIF;
import com.util.FormulaUtil;

public class ContextualServiceImpl implements ContextualServiceIF {

	@Autowired
	private ContextualDAOIF contextualDao;

	public ContextualDAOIF getContextualDao() {
		return contextualDao;
	}

	public void setContextualDao(ContextualDAOIF contextualDao) {
		this.contextualDao = contextualDao;
	}

	@Override
	public StatusInfo storeReviews(ArticleModel reviewModel) {

		StatusInfo statusInfo = null;
		try {

			statusInfo = contextualDao.insertReview(reviewModel);

		} catch (Exception e) {
			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setExceptionMsg(e.getMessage());
			return statusInfo;
		}
		return statusInfo;
	}

	/*
	 * public static void main(String s[]) { try {
	 * ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
	 * "review.xml"); ReviewServiceIF reviewServiceIF = (ReviewServiceIF) ctx
	 * .getBean("reviewServiceBean");
	 * 
	 * List<PolarityModel> rerviewModel=reviewServiceIF.retrivePolarity(1);
	 * 
	 * for(PolarityModel rerviewModelTemp:rerviewModel) {
	 * System.out.println("VALUE"+rerviewModelTemp.getNegativeRating()); }
	 * 
	 * 
	 * } catch (Exception e) { e.printStackTrace();
	 * System.out.println("EXCEPTION--->" + e); } }
	 */

	@Override
	public List<ArticleDetailModel> obtainAllReviews() {

		List<ArticleDetailModel> reviewModelList = null;
		try {

			List<ArticleModel> reviewModelListTemp = contextualDao
					.retriveAllReviews();
			if (null == reviewModelListTemp || reviewModelListTemp.isEmpty()
					|| reviewModelListTemp.size() == 0) {

				return null;

			}
			reviewModelList = new ArrayList<ArticleDetailModel>();
			for (ArticleModel reviewModelTemp : reviewModelListTemp) {

				ArticleDetailModel reviewDetailModel = new ArticleDetailModel();

				reviewDetailModel.setArticleDesc(reviewModelTemp
						.getArticleDesc());
				reviewDetailModel.setArticleName(reviewModelTemp
						.getArticleName());

				reviewModelList.add(reviewDetailModel);

			}

			return reviewModelList;

		} catch (Exception e) {
			System.out.println("Exception e" + e.getMessage());

		}
		return reviewModelList;

	}

	private WebSiteDataVO readWebSiteData(String webUrl) {

		WebSiteDataVO webSiteVO = null;

		try {
			String webSiteData = getUrlDataAsString(webUrl);
			if (!doNullCheck(webSiteData)) {
				webSiteVO = new WebSiteDataVO();
				webSiteVO.setWebSiteData(webSiteData);
				webSiteVO.setWebUrl(webUrl);
			}
		} catch (Exception e) {
			return null;
		}
		return webSiteVO;

	}

	private boolean doNullCheck(String obj) {
		return (null == obj || obj.length() == 0 || obj.isEmpty()) ? true
				: false;
	}

	private static final String SPACE = " ";

	private String getDataFromHtml(String locationOfHtml) {
		FileReader reader;

		String dataFromHtml = null;

		try {
			reader = new FileReader(locationOfHtml);
			dataFromHtml = extractText(reader);
		} catch (FileNotFoundException e) {
			System.out.println("File is Not Found:" + e);
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IO Exception" + e);
			e.printStackTrace();
		}

		return dataFromHtml;
	}

	private List<String> getHtmlElementsId(String locationOfHtml) {

		return null;

	}

	private String extractText(Reader reader) throws IOException {
		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(reader);
		String line;
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
		String textOnly = Jsoup.parse(sb.toString()).text();
		return textOnly;
	}

	private List<String> getUrls(String urlToConnect) {

		List<String> urls = null;

		if (urlToConnect != null) {
			urls = new ArrayList<String>();

			urls = extractLinks(urlToConnect);
		}
		return urls;
	}

	private List<String> extractLinks(String url) {
		final ArrayList<String> result = new ArrayList<String>();

		try {

			Document doc;
			doc = Jsoup.connect(url).get();
			// doc = Jsoup.parse(url);
			Elements links = doc.select("a");

			System.out.println("LINKS-->" + doc);

			// Elements imports = doc.select("link[href]");

			System.out.println("LINKS INFO--->" + links);

			// href ...
			for (Element link : links) {

				String linkHref = links.attr("href"); // "http://example.com/"
				String linkText = link.text();

				System.out.println("linkText" + linkText);

				result.add(linkText);
			}

			/*
			 * // js, css, ... for (Element link : imports) {
			 * result.add(link.attr("abs:href")); }
			 */

		} catch (Exception e) {
			System.out.println("EXCEPTION--->" + e);
		}
		return result;
	}

	public static String getTextDivData(String url) {

		StringBuilder stringBuilder = new StringBuilder();
		try {
			Document doc;
			doc = Jsoup.connect(url).get();
			Elements links = doc.select("div");
			for (Element link : links) {

				String linkText = link.text();
				stringBuilder.append(linkText);
				stringBuilder.append(SPACE);
			}
		} catch (Exception e) {
			System.out.println("EXCEPTION--->" + e);
		}
		return stringBuilder.toString();

	}

	public static String getTextTagData(String url) {
		final ArrayList<String> result = new ArrayList<String>();

		StringBuilder stringBuilder = new StringBuilder();
		try {
			Document doc;
			doc = Jsoup.connect(url).get();
			Elements links = doc.select("text");
			for (Element link : links) {

				String linkText = link.text();
				stringBuilder.append(linkText);
				stringBuilder.append(SPACE);
			}
		} catch (Exception e) {
			System.out.println("EXCEPTION--->" + e);
		}
		return stringBuilder.toString();

	}

	public static String getUrlDataAsString(String url) {
		StringBuilder stringBuilder = new StringBuilder();

		try {

			Document doc;
			doc = Jsoup.connect(url).get();
			// doc = Jsoup.parse(url);
			Elements links = doc.select("a");

			System.out.println("LINKS-->" + doc);

			// Elements imports = doc.select("link[href]");
			// href ...
			for (Element link : links) {

				String linkText = link.text();

				stringBuilder.append(SPACE);

				stringBuilder.append(linkText);

				stringBuilder.append(SPACE);

			}

			// Elements
			String divData = getTextDivData(url);

			if (divData != null && !divData.isEmpty()) {
				stringBuilder.append(divData);

				stringBuilder.append(SPACE);
			}

			// TET DATA

			/*
			 * String textData=getTextTagData(url);
			 * 
			 * if(textData!=null && !textData.isEmpty()) {
			 * 
			 * stringBuilder.append(textData);
			 * 
			 * stringBuilder.append(SPACE);
			 * 
			 * }
			 */

		} catch (Exception e) {
			System.out.println("EXCEPTION--->" + e);
		}

		System.out.println("USER" + stringBuilder.toString());

		return stringBuilder.toString();

	}

	public static String getUrlDataAsStringTest(String url) {
		StringBuilder stringBuilder = new StringBuilder();

		try {

			Document doc;
			doc = Jsoup.connect(url).get();
			// doc = Jsoup.parse(url);
			Elements links = doc.select("a");

			// Elements imports = doc.select("link[href]");
			// href ...
			for (Element link : links) {

				String linkText = link.text();

				stringBuilder.append(SPACE);

				stringBuilder.append(linkText);

				stringBuilder.append(SPACE);

			}

			// Elements
			String divData = getTextDivData(url);

			System.out.println("DIV =" + divData);

			stringBuilder.append(divData);

			stringBuilder.append(SPACE);

			String textData = getTextTagData(url);

			stringBuilder.append(textData);

			stringBuilder.append(SPACE);

		} catch (Exception e) {
			System.out.println("EXCEPTION--->" + e);
		}

		System.out.println("USER" + stringBuilder.toString());

		return stringBuilder.toString();

	}

	private boolean checkWordPresent(String word, String document) {

		boolean flag = false;
		String completeData = getDataFromHtml(document);

		String[] dataChunks = completeData.split(" ");

		List<String> wordList = new ArrayList<String>();

		for (String wordTemp : dataChunks) {
			wordList.add(wordTemp);
		}

		if (wordList.contains(word)) {
			flag = true;
			return flag;
		}

		return flag;
	}

	@Override
	public boolean storeWebSiteData(WebSiteUrlModel webSiteModel) {
		boolean webDataStorage = false;
		try

		{
			WebSiteDataVO webSiteDataVO = null;

			/* Reading the Web Site Data from Url1 */
			webSiteDataVO = readWebSiteData(webSiteModel.getWebUrl());
			if (null == webSiteDataVO) {
				return false;
			}

			ArticleModel reviewModel = new ArticleModel();

			if (webSiteDataVO.getWebSiteData() != null
					&& !webSiteDataVO.getWebSiteData().isEmpty()) {

				int webDataVONewLen = webSiteDataVO.getWebSiteData().length();

				String webSiteData = webSiteDataVO.getWebSiteData();
				if (webDataVONewLen > 2000) {

					webSiteData = webSiteData.substring(1, 2000);

				}

				reviewModel.setArticleDesc(webSiteData);
				reviewModel.setArticleName(webSiteModel.getArticleName());
				reviewModel.setUserId(webSiteModel.getUserId());

				StatusInfo webDataStorage1 = contextualDao
						.insertReview(reviewModel);

				if (!webDataStorage1.isStatus()) {
					return false;
				} else {
					return true;
				}
			} else {
				return false;
			}

		} catch (Exception e) {
			System.out.println("Exception" + e);
			return false;
		}

	}

	@Override
	public StatusInfo addStopword(String stopWord) {
		StatusInfo statusInfo = null;
		try {
			statusInfo = new StatusInfo();

			List<String> keyWordList = contextualDao.retriveStopWordsOnly();

			if (null == keyWordList || keyWordList.isEmpty()) {
				statusInfo = contextualDao.insertStopWord(stopWord);
				statusInfo.setStatus(true);
				return statusInfo;
			}

			if (keyWordList.contains(stopWord)) {
				statusInfo
						.setErrMsg(ContextualConstantsIF.Message.STOPWORD_EXIST);
				statusInfo.setStatus(false);
				return statusInfo;
			} else {
				statusInfo = contextualDao.insertStopWord(stopWord);
				statusInfo.setStatus(true);
				return statusInfo;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			statusInfo = new StatusInfo();
			statusInfo.setErrMsg(e.getMessage());
			statusInfo.setStatus(false);
			return statusInfo;
		}
	}

	@Override
	public List<StopWordsVO> retriveStopWords() {
		List<StopWordsVO> stopWordList = null;
		try {
			stopWordList = contextualDao.retriveStopWords();
			if (null == stopWordList) {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			return null;
		}
		return stopWordList;
	}

	@Override
	public StatusInfo doDataCleaning() {
		StatusInfo statusInfo = null;
		try {

			statusInfo = new StatusInfo();

			// Remove All Clean Reviews
			statusInfo = contextualDao.deleteAllCleanReviews();
			if (!statusInfo.isStatus()) {
				return statusInfo;
			}

			List<ArticleDetailModel> reviewList = obtainAllReviews();

			List<String> stopWordsList = contextualDao.retriveStopWordsOnly();

			for (ArticleDetailModel reviewModelObj : reviewList) {

				String reviewDetails = reviewModelObj.getArticleDesc();
				String str = reviewDetails.replaceAll("\\s", " ");

				String webSiteDataCleanTemp = str.replaceAll(
						ContextualConstantsIF.Keys.STOPWORDS_SYMBOL,
						ContextualConstantsIF.Keys.SPACE);

				StringTokenizer tok1 = new StringTokenizer(webSiteDataCleanTemp);
				StringBuilder buffer = new StringBuilder();
				String str1 = null;
				while (tok1.hasMoreTokens()) {
					str1 = (String) tok1.nextElement();
					str1 = str1.toLowerCase();

					if (null == str1 || str1.isEmpty() || str1.length() <= 0
							|| str1.trim().length() == 0) {
						continue;
					}
					if (str1 != null) {
						str1 = str1.trim();
					}
					if (stopWordsList.contains(str1)) {
						continue;
					} else {
						str1 = str1.replaceAll(
								ContextualConstantsIF.Keys.STOPWORDS_SYMBOL,
								ContextualConstantsIF.Keys.SPACE);
						str1 = str1.trim();
						if (str1.length() > 0) {
							buffer.append(ContextualConstantsIF.Keys.SPACE);
							buffer.append(str1);
						}
						System.out.println("BUFFER" + buffer);
					}
				}

				// Now Create an Object of Clean Review

				CleanArticleModel cleanReview = new CleanArticleModel();
				cleanReview.setArticleName(reviewModelObj.getArticleName());
				// Blob cleanData = covertFromStringToBlob(buffer.toString());
				cleanReview.setCleanArtilce(buffer.toString());

				statusInfo = contextualDao.insertCleanDetails(cleanReview);

				if (!statusInfo.isStatus()) {
					statusInfo.setStatus(false);
					statusInfo
							.setErrMsg(ContextualConstantsIF.Message.CLEANMODEL_FAILED);
					return statusInfo;
				}

			}
			statusInfo.setStatus(true);
			return statusInfo;
			// end of for Loop

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception " + e.getMessage());
			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			return statusInfo;

		}
	}

	private Blob covertFromStringToBlob(String reviewDetails) {

		byte[] byteContent = reviewDetails.getBytes();
		Blob blob = null;
		try {
			blob = new SerialBlob(byteContent);
		} catch (SerialException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return blob;
	}

	@Override
	public List<CleanArticleUIModel> retriveCleanReviewList() {
		List<CleanArticleUIModel> cleanReviewUIList = null;
		try {

			List<CleanArticleModel> cleanReviewModelList = contextualDao
					.retriveCleanReviews();

			if (null == cleanReviewModelList) {
				return null;
			}

			cleanReviewUIList = new ArrayList<CleanArticleUIModel>();

			for (CleanArticleModel cleanReviewModelTemp : cleanReviewModelList) {
				CleanArticleUIModel cleanReviewUIModel = new CleanArticleUIModel();
				cleanReviewUIModel
						.setCleanId(cleanReviewModelTemp.getCleanId());
				/*
				 * cleanReviewUIModel
				 * .setCleanArticle(convertFromBlobToString(cleanReviewModelTemp
				 * .getCleanArtilce()));
				 */
				cleanReviewUIModel.setCleanArticle(cleanReviewModelTemp
						.getCleanArtilce());

				cleanReviewUIModel.setArticleName(cleanReviewModelTemp
						.getArticleName());
				cleanReviewUIList.add(cleanReviewUIModel);
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			return null;
		}
		return cleanReviewUIList;
	}

	@Override
	public StatusInfo doTokens() {
		StatusInfo statusInfo = null;
		try {

			statusInfo = new StatusInfo();

			// Remove All Tokens
			statusInfo = contextualDao.deleteAllTokens();
			if (!statusInfo.isStatus()) {
				return statusInfo;
			}

			List<CleanArticleUIModel> cleanReviewUIModelList = retriveCleanReviewList();

			if (null == cleanReviewUIModelList
					|| cleanReviewUIModelList.isEmpty()) {
				statusInfo.setStatus(false);
				statusInfo
						.setErrMsg(ContextualConstantsIF.Message.CLEANREVIEWS_EMPTY);
				return statusInfo;
			}

			for (CleanArticleUIModel reviewModelObj : cleanReviewUIModelList) {

				StringTokenizer tok1 = new StringTokenizer(
						reviewModelObj.getCleanArticle(),
						ContextualConstantsIF.Keys.SPACE);
				String tokenName;
				while (tok1.hasMoreTokens()) {
					tokenName = tok1.nextToken();
					tokenName = tokenName.toLowerCase();
					TokenVO tokenVO = new TokenVO();
					tokenVO.setTokenName(tokenName);
					tokenVO.setArticleName(reviewModelObj.getArticleName());
					statusInfo = contextualDao.insertToken(tokenVO);
					if (!statusInfo.isStatus()) {
						statusInfo.setStatus(false);
						statusInfo
								.setErrMsg(ContextualConstantsIF.Message.INSERT_TOKENS_FAILED);
						return statusInfo;
					}

				}
				// end of for Loop
			}
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
	public List<TokenVO> retriveTokenList() {
		List<TokenVO> tokenVOList = null;
		try {
			tokenVOList = contextualDao.retriveAllTokens();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception " + e.getMessage());
			return null;
		}
		return tokenVOList;
	}

	@Override
	public StatusInfo removeStopword(String stopWord) {

		StatusInfo statusInfo = null;
		try {
			statusInfo = new StatusInfo();

			List<String> keyWordList = contextualDao.retriveStopWordsOnly();

			if (!keyWordList.contains(stopWord)) {
				statusInfo
						.setErrMsg(ContextualConstantsIF.Message.NO_STOPWORD_EXIST);
				statusInfo.setStatus(false);
				return statusInfo;
			} else {
				statusInfo = contextualDao.removeStopword(stopWord);
				statusInfo.setStatus(true);
				return statusInfo;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			statusInfo = new StatusInfo();
			statusInfo.setErrMsg(e.getMessage());
			statusInfo.setStatus(false);
			return statusInfo;
		}

	}

	private String convertFromBlobToString(Blob blob) {
		System.out.println("BLOB---" + blob);

		byte[] bdata = null;
		try {
			bdata = blob.getBytes(1, (int) blob.length());

			System.out.println("BDATA" + bdata);

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
		}
		String text = new String(bdata);
		System.out.println("TEXT---" + text);
		return text;
	}

	@Override
	public StatusInfo addKeyPhrase(String keyphrase, String type) {
		StatusInfo statusInfo = null;
		try {
			statusInfo = new StatusInfo();

			List<String> keyWordList = contextualDao.retriveKeyPhraseOnly(type);

			if (null == keyWordList || keyWordList.isEmpty()) {
				statusInfo = contextualDao.insertKeyPhrase(keyphrase, type);
				statusInfo.setStatus(true);
				return statusInfo;
			}

			if (keyWordList.contains(keyphrase)) {
				statusInfo
						.setErrMsg(ContextualConstantsIF.Message.KEYPHRASE_EXIST);
				statusInfo.setStatus(false);
				return statusInfo;
			} else {
				statusInfo = contextualDao.insertKeyPhrase(keyphrase, type);
				statusInfo.setStatus(true);
				return statusInfo;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			statusInfo = new StatusInfo();
			statusInfo.setErrMsg(e.getMessage());
			statusInfo.setStatus(false);
			return statusInfo;
		}
	}

	@Override
	public List<KeyPhraseModel> retriveKeyPhrases() {
		List<KeyPhraseModel> keyPhraseList = null;
		try {
			keyPhraseList = contextualDao.retriveKeyPhrases();
			if (null == keyPhraseList) {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			return null;
		}
		return keyPhraseList;

	}

	@Override
	public StatusInfo removeKeyPhrase(String keyphrase, String type) {

		StatusInfo statusInfo = null;
		try {
			statusInfo = new StatusInfo();

			List<String> keyWordList = contextualDao.retriveKeyPhraseOnly(type);

			if (!keyWordList.contains(keyphrase)) {
				statusInfo
						.setErrMsg(ContextualConstantsIF.Message.KEYPHRASE_NOT_EXIST);
				statusInfo.setStatus(false);
				return statusInfo;
			} else {
				statusInfo = contextualDao.removeKeyPhrase(keyphrase, type);
				statusInfo.setStatus(true);
				return statusInfo;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			statusInfo = new StatusInfo();
			statusInfo.setErrMsg(e.getMessage());
			statusInfo.setStatus(false);
			return statusInfo;
		}
	}

	@Override
	public StatusInfo addPhraseG(PhraseGModel phraseGModel) {
		StatusInfo statusInfo = null;
		try {
			statusInfo = new StatusInfo();

			List<String> keyWordList = contextualDao
					.retrivePhraseGWhereTypeAndNoType(phraseGModel);

			if (null == keyWordList || keyWordList.isEmpty()) {
				statusInfo = contextualDao.insertPhraseG(phraseGModel);
				statusInfo.setStatus(true);
				return statusInfo;
			}

			if (keyWordList.contains(phraseGModel.getPhraseG())) {
				statusInfo
						.setErrMsg(ContextualConstantsIF.Message.PHRASEG_EXIST);
				statusInfo.setStatus(false);
				return statusInfo;
			} else {
				statusInfo = contextualDao.insertPhraseG(phraseGModel);
				statusInfo.setStatus(true);
				return statusInfo;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			statusInfo = new StatusInfo();
			statusInfo.setErrMsg(e.getMessage());
			statusInfo.setStatus(false);
			return statusInfo;
		}
	}

	@Override
	public StatusInfo removePhraseG(PhraseGModel phraseGModel) {
		StatusInfo statusInfo = null;
		try {
			statusInfo = new StatusInfo();

			List<String> keyWordList = contextualDao
					.retrivePhraseGWhereTypeAndNoType(phraseGModel);

			if (!keyWordList.contains(phraseGModel.getPhraseG())) {
				statusInfo
						.setErrMsg(ContextualConstantsIF.Message.PHRASEG_NOT_EXIST);
				statusInfo.setStatus(false);
				return statusInfo;
			} else {
				statusInfo = contextualDao
						.removePhraseGForPhraseGTypeNoType(phraseGModel);
				statusInfo.setStatus(true);
				return statusInfo;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			statusInfo = new StatusInfo();
			statusInfo.setErrMsg(e.getMessage());
			statusInfo.setStatus(false);
			return statusInfo;
		}
	}

	@Override
	public List<PhraseGModel> retrivePhraseG() {
		List<PhraseGModel> phraseGList = null;
		try {
			phraseGList = contextualDao.retrivePhraseGPhrases();
			if (null == phraseGList) {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			return null;
		}
		return phraseGList;
	}

	@Override
	public StatusInfo genPhrases() {
		StatusInfo statusInfo = null;
		try {

			statusInfo = new StatusInfo();

			// Remove All Phrases
			statusInfo = contextualDao.deleteAllPhrases();
			if (!statusInfo.isStatus()) {
				statusInfo.setStatus(false);
				statusInfo
						.setErrMsg(ContextualConstantsIF.Message.COULD_NOT_REMOVE_PHRASES);
				return statusInfo;
			}

			// Obtain the List of Tokens

			List<String> tokens = contextualDao.retriveTokensOnly();

			if (null == tokens || tokens.isEmpty()) {

				statusInfo.setStatus(false);
				statusInfo
						.setErrMsg(ContextualConstantsIF.Message.TOKENS_EMPTY);
				return statusInfo;
			}

			// Obtain the List of Stopwords

			List<String> stopwords = contextualDao.retriveStopWordsOnly();

			if (null == stopwords || stopwords.isEmpty()) {
				statusInfo.setStatus(false);
				statusInfo
						.setErrMsg(ContextualConstantsIF.Message.STOPWORDS_EMPTY);
				return statusInfo;
			}

			// Obtain the List of Key Phrases

			List<String> keyPhrasesList = contextualDao
					.retriveAllKeyPhraseOnly();

			if (null == keyPhrasesList || keyPhrasesList.isEmpty()) {
				statusInfo.setStatus(false);
				statusInfo
						.setErrMsg(ContextualConstantsIF.Message.KEYPHRASES_EMPTY);
				return statusInfo;
			}

			// Obtain the List of PhraseG

			List<String> phraseGList = contextualDao.retriveAllPhraseGOnly();

			if (null == phraseGList || phraseGList.isEmpty()) {
				statusInfo.setStatus(false);
				statusInfo
						.setErrMsg(ContextualConstantsIF.Message.PHRASEG_EMPTY);
				return statusInfo;
			}

			// List of Phrases

			Set<String> phrases = new HashSet<String>();

			// Obtain the List of Adjectives

			List<String> adjectiveList = contextualDao.retriveAllAdjectives();

			for (String token : tokens) {

				if (!stopwords.contains(token) && !phraseGList.contains(token)
						&& !keyPhrasesList.contains(token)) {

					if (adjectiveList != null) {

						if (!adjectiveList.contains(token)) {
							phrases.add(token);
						}

					} else {
						phrases.add(token);
					}
				}

			}

			if (null == phrases || phrases.isEmpty()) {
				statusInfo.setStatus(false);
				statusInfo
						.setErrMsg(ContextualConstantsIF.Message.NO_PHRASES_ARE_FOUND);
				return statusInfo;
			}

			statusInfo = contextualDao.insertPhrasesList(phrases);

			if (!statusInfo.isStatus()) {
				statusInfo.setStatus(false);
				statusInfo
						.setErrMsg(ContextualConstantsIF.Message.COULD_NOT_INSERT_PHRASES);
				return statusInfo;
			}

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
	public List<PhraseVO> retrivePhrasesList() {
		List<PhraseVO> phraseList = null;
		try {
			phraseList = contextualDao.retrivePhraseList();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception " + e.getMessage());
			return null;
		}
		return phraseList;
	}

	@Override
	public StatusInfo genAdjectives() {

		StatusInfo statusInfo = new StatusInfo();

		try {
			List<ArticleDetailModel> reviewList = obtainAllReviews();

			List<String> adjectiveList = new ArrayList<String>();

			statusInfo = contextualDao.deleteAllAdjectives();

			if (reviewList != null && !reviewList.isEmpty()) {

				for (ArticleDetailModel reviewDetailModel : reviewList) {

					StringTokenizer tok1 = new StringTokenizer(
							reviewDetailModel.getArticleDesc(),
							ContextualConstantsIF.Keys.SPACE);
					String tokenName;
					while (tok1.hasMoreTokens()) {
						tokenName = tok1.nextToken();
						tokenName = tokenName.toLowerCase();

						// Check Token is Adjective using NLP
						List<String> adjectiveTemp = obtainAdjectivesUsingNLP(tokenName);
						if (adjectiveTemp != null && !adjectiveTemp.isEmpty()) {
							adjectiveList.addAll(adjectiveTemp);
						}
					}
				}
			} else {
				statusInfo = new StatusInfo();
				statusInfo.setStatus(false);
				statusInfo
						.setErrMsg(ContextualConstantsIF.Message.REVIEW_LIST_EMPTY);
				return statusInfo;

			}
			if (adjectiveList != null && !adjectiveList.isEmpty()) {

				statusInfo = contextualDao.insertAdjectives(adjectiveList);

				if (!statusInfo.isStatus()) {

					statusInfo = new StatusInfo();
					statusInfo.setStatus(false);
					statusInfo
							.setErrMsg(ContextualConstantsIF.Message.INSERTING_ADJECTIVES_FAILED);
					return statusInfo;
				} else {
					statusInfo = new StatusInfo();
					statusInfo.setStatus(true);
					return statusInfo;
				}

			}

		} catch (Exception e) {

			e.printStackTrace();
			System.out.println("Exception " + e.getMessage());
			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			return statusInfo;

		}
		return statusInfo;

	}

	private List<String> obtainAdjectivesUsingNLP(String tokenName) {

		List<String> nlpList = new ArrayList<String>();
		try {

			List<String> adjectiveList = contextualDao
					.retriveAdjectivesForNLP();

			nlpList = new ArrayList<String>();

			String lowerCaseWord = tokenName.toLowerCase();

			if (adjectiveList.contains(lowerCaseWord)) {

				nlpList.add(lowerCaseWord);
			}

		} catch (Exception e) {
			System.out.println("Exception " + e.getMessage());
		}

		return nlpList;

	}

	@Override
	public List<AdjectiveVO> retriveAdjectives() {

		List<AdjectiveVO> adjectiveVOList = null;
		try {
			adjectiveVOList = contextualDao.retriveAllFullAdjectives();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception " + e.getMessage());
			return null;
		}
		return adjectiveVOList;

	}

	@Override
	public StatusInfo genStructure1() {
		StatusInfo statusInfo = new StatusInfo();

		try {

			statusInfo = contextualDao.deleteAllStructure1();

			if (!statusInfo.isStatus()) {
				statusInfo = new StatusInfo();
				statusInfo.setStatus(false);
				statusInfo
						.setErrMsg(ContextualConstantsIF.Message.DELETE_STRUCTURE3_FAILED);
				return statusInfo;

			}

			List<String> structureList = new ArrayList<String>();

			List<String> keyPhrasesOnly = contextualDao
					.retriveAllKeyPhraseOnly();

			if (null == keyPhrasesOnly || keyPhrasesOnly.isEmpty()) {

				statusInfo = new StatusInfo();
				statusInfo.setStatus(false);
				statusInfo
						.setErrMsg(ContextualConstantsIF.Message.NO_KEYPHRASES_FOUND);
				return statusInfo;
			} else {

				for (String keyPhrase : keyPhrasesOnly) {
					for (String keyPhrase2 : keyPhrasesOnly) {
						StringBuilder sb = new StringBuilder(keyPhrase);
						sb.append(ContextualConstantsIF.CONSTANTS.SPACE);
						sb.append(keyPhrase2);

						structureList.add(sb.toString());
					}
				}

			}

			if (structureList != null && !structureList.isEmpty()) {

				// Structure1 List
				statusInfo = contextualDao.insertStruture1(structureList);

				if (!statusInfo.isStatus()) {

					statusInfo = new StatusInfo();
					statusInfo.setStatus(false);
					statusInfo
							.setErrMsg(ContextualConstantsIF.Message.NO_INSERTION_STRUCTURE1);
					return statusInfo;
				}

			} else {

				statusInfo = new StatusInfo();
				statusInfo.setStatus(false);
				statusInfo
						.setErrMsg(ContextualConstantsIF.Message.NO_STRUCTURE1_FOUND);
				return statusInfo;

			}

		} catch (Exception e) {

			e.printStackTrace();
			System.out.println("Exception " + e.getMessage());
			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			return statusInfo;

		}
		return statusInfo;
	}

	@Override
	public List<StructureVO> retriveStructure1List() {
		List<StructureVO> structure1List = null;
		try {
			structure1List = contextualDao.retriveStructure1List();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception " + e.getMessage());
			return null;
		}
		return structure1List;
	}

	@Override
	public List<StructureVO> retriveStructure2List() {
		List<StructureVO> structure2List = null;
		try {
			structure2List = contextualDao.retriveStructure2List();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception " + e.getMessage());
			return null;
		}
		return structure2List;
	}

	@Override
	public List<StructureVO> retriveStructure3List() {
		List<StructureVO> structure3List = null;
		try {
			structure3List = contextualDao.retriveStructure3List();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception " + e.getMessage());
			return null;
		}
		return structure3List;
	}

	@Override
	public StatusInfo genStructure2() {
		StatusInfo statusInfo = new StatusInfo();

		try {

			statusInfo = contextualDao.deleteAllStructure2();

			if (!statusInfo.isStatus()) {
				statusInfo = new StatusInfo();
				statusInfo.setStatus(false);
				statusInfo
						.setErrMsg(ContextualConstantsIF.Message.DELETE_STRUCTURE3_FAILED);
				return statusInfo;

			}

			List<String> structureList = new ArrayList<String>();

			List<String> adjectivesOnly = contextualDao.retriveAllAdjectives();

			List<String> keyPhrasesOnly = contextualDao
					.retriveAllKeyPhraseOnly();

			if (null == keyPhrasesOnly || keyPhrasesOnly.isEmpty()) {

				statusInfo = new StatusInfo();
				statusInfo.setStatus(false);
				statusInfo
						.setErrMsg(ContextualConstantsIF.Message.NO_KEYPHRASES_FOUND);
				return statusInfo;
			}

			if (null == adjectivesOnly || adjectivesOnly.isEmpty()) {

				statusInfo = new StatusInfo();
				statusInfo.setStatus(false);
				statusInfo
						.setErrMsg(ContextualConstantsIF.Message.NO_ADJECTIVES_FOUND);
				return statusInfo;
			}

			for (String keyPhrase : keyPhrasesOnly) {
				for (String adjectives : adjectivesOnly) {
					StringBuilder sb = new StringBuilder(keyPhrase);
					sb.append(ContextualConstantsIF.CONSTANTS.SPACE);
					sb.append(adjectives);

					structureList.add(sb.toString());
				}
			}

			if (structureList != null && !structureList.isEmpty()) {

				// Structure1 List
				statusInfo = contextualDao.insertStruture2(structureList);

				if (!statusInfo.isStatus()) {

					statusInfo = new StatusInfo();
					statusInfo.setStatus(false);
					statusInfo
							.setErrMsg(ContextualConstantsIF.Message.NO_INSERTION_STRUCTURE2);
					return statusInfo;
				}

			} else {

				statusInfo = new StatusInfo();
				statusInfo.setStatus(false);
				statusInfo
						.setErrMsg(ContextualConstantsIF.Message.NO_STRUCTURE2_FOUND);
				return statusInfo;

			}

		} catch (Exception e) {

			e.printStackTrace();
			System.out.println("Exception " + e.getMessage());
			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			return statusInfo;

		}
		return statusInfo;
	}

	@Override
	public StatusInfo genStructure3() {
		StatusInfo statusInfo = new StatusInfo();

		try {

			statusInfo = contextualDao.deleteAllStructure3();

			if (!statusInfo.isStatus()) {
				statusInfo = new StatusInfo();
				statusInfo.setStatus(false);
				statusInfo
						.setErrMsg(ContextualConstantsIF.Message.DELETE_STRUCTURE3_FAILED);
				return statusInfo;

			}

			List<String> structureList = new ArrayList<String>();

			List<String> phraseListOnly = contextualDao.retrivePhraseListOnly();

			if (null == phraseListOnly || phraseListOnly.isEmpty()) {

				statusInfo = new StatusInfo();
				statusInfo.setStatus(false);
				statusInfo
						.setErrMsg(ContextualConstantsIF.Message.NO_PHRASELIST_FOUND);
				return statusInfo;
			}

			List<String> keyPhrasesOnly = contextualDao
					.retriveAllKeyPhraseOnly();

			if (null == keyPhrasesOnly || keyPhrasesOnly.isEmpty()) {

				statusInfo = new StatusInfo();
				statusInfo.setStatus(false);
				statusInfo
						.setErrMsg(ContextualConstantsIF.Message.NO_KEYPHRASES_FOUND);
				return statusInfo;
			}

			for (String keyPhrase : keyPhrasesOnly) {
				for (String phrase : phraseListOnly) {
					StringBuilder sb = new StringBuilder(keyPhrase);
					sb.append(ContextualConstantsIF.CONSTANTS.SPACE);
					sb.append(phrase);
					structureList.add(sb.toString());
				}
			}

			if (structureList != null && !structureList.isEmpty()) {

				// Structure1 List
				statusInfo = contextualDao.insertStruture3(structureList);

				if (!statusInfo.isStatus()) {

					statusInfo = new StatusInfo();
					statusInfo.setStatus(false);
					statusInfo
							.setErrMsg(ContextualConstantsIF.Message.NO_INSERTION_STRUCTURE3);
					return statusInfo;
				}

			} else {

				statusInfo = new StatusInfo();
				statusInfo.setStatus(false);
				statusInfo
						.setErrMsg(ContextualConstantsIF.Message.NO_STRUCTURE3_FOUND);
				return statusInfo;

			}

		} catch (Exception e) {

			e.printStackTrace();
			System.out.println("Exception " + e.getMessage());
			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			return statusInfo;

		}
		return statusInfo;
	}

	@Override
	public StatusInfo generateFreq() {
		StatusInfo statusInfo = null;
		try {
			// obtain a list of data from tokens
			List<TokenVO> tokensList = contextualDao.retriveAllTokens();

			if (null == tokensList || tokensList.size() == 0) {

				statusInfo = new StatusInfo();
				statusInfo.setStatus(false);
				statusInfo
						.setErrMsg(ContextualConstantsIF.Message.TOKENS_EMPTY);
				return statusInfo;

			} else {

				// Now get List of Reviews From Token Table
				/*
				 * Remove existing data
				 */
				statusInfo = contextualDao.deleteAllFreq();

				if (!statusInfo.isStatus()) {
					statusInfo = new StatusInfo();
					statusInfo.setStatus(false);
					statusInfo
							.setErrMsg(ContextualConstantsIF.Message.COULDNOT_DELETE_FREQUENCY);
					return statusInfo;
				} else {
					// Now get the distinct review Ids
					List<String> distinctArticleNamess = contextualDao
							.retriveAllUniqueArticleNamesFromTokenization();

					System.out.println("DISTINCT R=" + distinctArticleNamess);
					if (null == distinctArticleNamess
							|| distinctArticleNamess.isEmpty()
							|| distinctArticleNamess.size() == 0) {
						statusInfo = new StatusInfo();
						statusInfo.setStatus(false);
						statusInfo
								.setErrMsg(ContextualConstantsIF.Message.COULDNOT_FIND_ARTICLES);
						return statusInfo;
					} else {

						for (String articleName : distinctArticleNamess) {
							// Obtain distinct tokens for each Review
							List<String> distinctTokens = contextualDao
									.retriveDistinctTokensForArticleName(articleName);

							if (null == distinctTokens
									|| distinctTokens.isEmpty()
									|| distinctTokens.size() == 0) {

								statusInfo = new StatusInfo();
								statusInfo.setStatus(false);
								statusInfo
										.setErrMsg(ContextualConstantsIF.Message.COULDNOT_FIND_TOKENS);
								return statusInfo;
							} else {
								for (String tokenTemp : distinctTokens) {

									// count of tokens
									int count = contextualDao
											.retriveCountForTokenInArticle(
													tokenTemp, articleName);

									FrequencyVO freqVO = new FrequencyVO();

									freqVO.setArticleName(articleName);
									freqVO.setTokenName(tokenTemp);
									freqVO.setFreq(count);

									// Insert into Frequency Table

									statusInfo = contextualDao
											.insertFrequency(freqVO);
									if (!statusInfo.isStatus()) {
										statusInfo = new StatusInfo();
										statusInfo.setStatus(false);
										statusInfo
												.setErrMsg(ContextualConstantsIF.Message.COULDNOT_INSERT_FREQUENCY);
										return statusInfo;
									}
								}
							}

						}//

					}

				}
			}// End

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("MESSAGE EXCEPTION" + e.getMessage());
			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			return statusInfo;
		}
		statusInfo = new StatusInfo();
		statusInfo.setStatus(true);
		statusInfo
				.setErrMsg(ContextualConstantsIF.Message.FREQ_COMPUTATION_SUCESS);
		return statusInfo;

	}

	private int countNoOfWords(String str1, List<String> words) {
		int count = 0;
		if (words != null && !words.isEmpty()) {
			for (String word : words) {
				if (str1.contains(word)) {
					count = count + 1;
				}
			}
		}
		return count;
	}

	@Override
	public List<FreqComputation> viewFreq() {
		List<FreqComputation> freqComputationList = null;
		try {
			freqComputationList = contextualDao.viewFreq();
			if (null == freqComputationList) {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			return null;
		}
		return freqComputationList;
	}

	@Override
	public List<ArticleNamesVO> retriveAllArticleNames() {
		List<ArticleNamesVO> articleNamesVOList = null;
		try {
			List<ArticleModel> reviewModelList = contextualDao
					.retriveAllReviews();

			if (null == reviewModelList || reviewModelList.isEmpty()) {
				return null;
			}

			articleNamesVOList = new ArrayList<ArticleNamesVO>();

			for (ArticleModel reviewModel : reviewModelList) {

				ArticleNamesVO articleNamesVO = new ArticleNamesVO();

				articleNamesVO.setArticleName(reviewModel.getArticleName());
				articleNamesVOList.add(articleNamesVO);
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception " + e.getMessage());
			return null;
		}
		return articleNamesVOList;
	}

	@Override
	public List<ArticleTypesVO> retriveTypes() {
		List<ArticleTypesVO> typeVOList = null;
		try {
			List<String> concepts = contextualDao.retriveDiffrentConcepts();

			if (null == concepts || concepts.isEmpty()) {
				return null;
			}

			typeVOList = new ArrayList<ArticleTypesVO>();

			for (String concept : concepts) {

				ArticleTypesVO typesVo = new ArticleTypesVO();

				typesVo.setTypeName(concept);

				typeVOList.add(typesVo);
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception " + e.getMessage());
			return null;
		}
		return typeVOList;
	}

	@Override
	public StatusInfo compareArticles(ComparisionInputModel comparisionModel) {

		StatusInfo statusInfo = new StatusInfo();

		List<CompareArticleVO> compareArticleList = null;

		try {

			CompareArticleVO compareArticleVO = new CompareArticleVO();
			List<String> tokenNamesLeft = contextualDao
					.retriveDistincetTokensForArticleName(comparisionModel
							.getArticleNameLeft());

			if (null == tokenNamesLeft || tokenNamesLeft.isEmpty()) {
				statusInfo.setStatus(false);
				statusInfo
						.setErrMsg(ContextualConstantsIF.Message.NO_TOKENS_FOR_ARTICLE_LEFT
								+ comparisionModel.getArticleNameLeft());
				return statusInfo;
			}

			List<String> tokenNamesRight = contextualDao
					.retriveDistincetTokensForArticleName(comparisionModel
							.getArticleNameRight());

			if (null == tokenNamesRight || tokenNamesRight.isEmpty()) {
				statusInfo.setStatus(false);
				statusInfo
						.setErrMsg(ContextualConstantsIF.Message.NO_TOKENS_FOR_ARTICLE_RIGHT
								+ comparisionModel.getArticleNameRight());
				return statusInfo;
			}

			// Now Find the Key Phrase for Article

			List<String> keyPhrasesForLeftArticle = contextualDao
					.retriveKeyPhraseOnlyForTokensAndType(tokenNamesLeft,
							comparisionModel.getTypeCombo());

			compareArticleVO.setLeftArticleKeyPhrases(keyPhrasesForLeftArticle);

			if (null == keyPhrasesForLeftArticle
					|| keyPhrasesForLeftArticle.isEmpty()) {
				statusInfo.setStatus(false);
				statusInfo
						.setErrMsg(ContextualConstantsIF.Message.NO_KEYPHRASES_FOR_LEFT_ARTICLE
								+ comparisionModel.getArticleNameLeft());
				return statusInfo;
			}

			// Now Find the Key Phrase for Article

			List<String> keyPhrasesForRightArticle = contextualDao
					.retriveKeyPhraseOnlyForTokensAndType(tokenNamesRight,
							comparisionModel.getTypeCombo());

			compareArticleVO
					.setRightArticleKeyPhrases(keyPhrasesForRightArticle);

			if (null == keyPhrasesForRightArticle
					|| keyPhrasesForRightArticle.isEmpty()) {
				statusInfo.setStatus(false);
				statusInfo
						.setErrMsg(ContextualConstantsIF.Message.NO_KEYPHRASES_FOR_RIGHT_ARTICLE
								+ comparisionModel.getArticleNameRight());
				return statusInfo;
			}

			//

			// Now Find the Intersection between the two sets
			List<String> rightArticleKeyPhrase = new ArrayList<String>();
			rightArticleKeyPhrase.addAll(keyPhrasesForRightArticle);

			rightArticleKeyPhrase.retainAll(keyPhrasesForLeftArticle);

			// Find the Union Between teh Two Articles

			List<String> unionList = new ArrayList<String>();

			unionList.addAll(keyPhrasesForRightArticle);
			unionList.addAll(keyPhrasesForLeftArticle);

			Set<String> keyPhraseUnionSet = new HashSet<String>();

			for (String keyPhraseUnion : unionList) {
				keyPhraseUnionSet.add(keyPhraseUnion);
			}

			// Now Store in the Key Phrase Union List

			List<String> keyPhraseUnion = new ArrayList<String>();

			for (String keyPhrase : keyPhraseUnionSet) {

				keyPhraseUnion.add(keyPhrase);

			}

			// Adding the Key Phrase Union Set

			compareArticleVO.setUnionSet(keyPhraseUnion);

			Set<String> keyPhraseInterSet = new HashSet<String>();

			for (String word : rightArticleKeyPhrase) {

				keyPhraseInterSet.add(word);
			}

			// Now Store in the Key Phrase Intersection List

			List<String> keyPhraseIntersection = new ArrayList<String>();

			for (String keyPhrase : keyPhraseInterSet) {
				keyPhraseIntersection.add(keyPhrase);
			}

			compareArticleVO.setInstersectionSet(keyPhraseIntersection);

			List<String> msgList = new ArrayList<String>();

			if (null == keyPhraseIntersection
					|| keyPhraseIntersection.isEmpty()) {
				msgList.add(ContextualConstantsIF.Message.KEY_PHRASE_INTERSECTION_SET_EMPTY);
			}

			if (null == keyPhraseUnion || keyPhraseUnion.isEmpty()) {
				msgList.add(ContextualConstantsIF.Message.KEY_PHRASE_UNION_SET_EMPTY);
			}

			//

			List<TextFreqComputeVO> leftArticleTextFreq = new ArrayList<TextFreqComputeVO>();

			List<TextFreqComputeVO> rightArticleTextFreq = new ArrayList<TextFreqComputeVO>();

			double unionSum = 0;

			for (String word : keyPhraseUnion) {

				TextFreqComputeVO textComputeVO = new TextFreqComputeVO();

				int countLeft = contextualDao
						.retriveCountForTokenAndArticleName(word,
								comparisionModel.getArticleNameLeft());

				int denomLeftArticle = contextualDao
						.findNoOfTokensForArticleName(comparisionModel
								.getArticleNameLeft());

				textComputeVO.setKeyPhraseName(word);
				textComputeVO.setFreq(countLeft);
				textComputeVO.setCount(denomLeftArticle);

				double textFreqLeft = (double) ((double) countLeft / (double) denomLeftArticle);
				textComputeVO.setTextFreq(textFreqLeft);

				leftArticleTextFreq.add(textComputeVO);

				TextFreqComputeVO textComputeVO1 = new TextFreqComputeVO();

				textComputeVO1.setKeyPhraseName(word);

				int countRight = contextualDao
						.retriveCountForTokenAndArticleName(word,
								comparisionModel.getArticleNameRight());
				textComputeVO1.setFreq(countRight);

				int denomRightArticle = contextualDao
						.findNoOfTokensForArticleName(comparisionModel
								.getArticleNameRight());

				textComputeVO1.setCount(denomRightArticle);

				double textFreqRight = (double) ((double) countRight / (double) denomRightArticle);
				textComputeVO1.setTextFreq(textFreqRight);

				rightArticleTextFreq.add(textComputeVO1);

				if (textFreqLeft >= textFreqRight) {

					unionSum = unionSum + textFreqLeft;

				} else {
					unionSum = unionSum + textFreqRight;
				}

			}

			compareArticleVO.setUnionSum(unionSum);

			// Find the Intersection Sum
			double intersectionSum = 0;

			for (String word : keyPhraseIntersection) {

				TextFreqComputeVO textComputeVO = new TextFreqComputeVO();

				int countLeft = contextualDao
						.retriveCountForTokenAndArticleName(word,
								comparisionModel.getArticleNameLeft());

				int denomLeftArticle = contextualDao
						.findNoOfTokensForArticleName(comparisionModel
								.getArticleNameLeft());

				textComputeVO.setKeyPhraseName(word);
				textComputeVO.setFreq(countLeft);
				textComputeVO.setCount(denomLeftArticle);

				double textFreqLeft = (double) ((double) countLeft / (double) denomLeftArticle);
				textComputeVO.setTextFreq(textFreqLeft);

				leftArticleTextFreq.add(textComputeVO);

				TextFreqComputeVO textComputeVO1 = new TextFreqComputeVO();

				textComputeVO1.setKeyPhraseName(word);

				int countRight = contextualDao
						.retriveCountForTokenAndArticleName(word,
								comparisionModel.getArticleNameRight());
				textComputeVO1.setFreq(countRight);

				int denomRightArticle = contextualDao
						.findNoOfTokensForArticleName(comparisionModel
								.getArticleNameRight());

				textComputeVO1.setCount(denomRightArticle);

				double textFreqRight = (double) ((double) countRight / (double) denomRightArticle);
				textComputeVO1.setTextFreq(textFreqRight);

				rightArticleTextFreq.add(textComputeVO1);

				if (textFreqLeft <= textFreqRight) {

					intersectionSum = intersectionSum + textFreqLeft;

				} else {
					intersectionSum = intersectionSum + textFreqRight;
				}

			}
			compareArticleVO.setIntersectionSum(intersectionSum);

			compareArticleVO.setTextFreqRArticle(rightArticleTextFreq);

			compareArticleVO.setTextFreqLeftArticle(leftArticleTextFreq);

			double similarityMeasure = (double) ((double) intersectionSum / (double) unionSum);

			compareArticleVO.setSimilarityMeasure(similarityMeasure);

			if (similarityMeasure >= 0.5) {
				compareArticleVO.setSimilarityStatus(true);
				compareArticleVO
						.setSimilarityMeasureMsg(ContextualConstantsIF.Message.BOTH_ARTICLENAMES_ARE_SIMILAR);
			} else {
				compareArticleVO.setSimilarityStatus(false);
				compareArticleVO
						.setSimilarityMeasureMsg(ContextualConstantsIF.Message.BOTH_ARTICLENAMES_ARE_NOTSIMILAR);
			}

			// Now getting All the Content Information

			List<FreqComputation> leftArticleFreqInfo = contextualDao
					.retriveFreqComputationForArticleNameAndType(
							comparisionModel.getArticleNameLeft(),
							comparisionModel.getTypeCombo());

			compareArticleVO.setLeftArticleFreqInfo(leftArticleFreqInfo);

			List<FreqComputation> rightArticleFreqInfo = contextualDao
					.retriveFreqComputationForArticleNameAndType(
							comparisionModel.getArticleNameRight(),
							comparisionModel.getTypeCombo());

			compareArticleVO.setRightArticleFreqInfo(rightArticleFreqInfo);

			statusInfo.setObject(compareArticleVO);

			statusInfo.setStatus(true);

		} catch (Exception e) {
			e.printStackTrace();
			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			return statusInfo;
		}
		return statusInfo;

	}

	public void storeAdjectives(String fileLocation) {

		boolean intial = false;
		BufferedReader reader1;
		try {
			reader1 = new BufferedReader(new InputStreamReader(
					new FileInputStream(fileLocation)));
			String line1;
			line1 = reader1.readLine();
			while (line1 != null) {
				System.out.println("Line1" + line1);
				StringTokenizer tok1 = new StringTokenizer(line1, " ");
				String str1;

				while (tok1.hasMoreTokens()) {
					str1 = tok1.nextToken();
					contextualDao.insertAdjectiveNLP(str1);
				}
				line1 = reader1.readLine();

				reader1.close();
			}
			intial = false;
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public static void main(String s[]) {

		try {
			ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
					"review.xml");
			ContextualServiceIF reviewServiceIF = (ContextualServiceIF) ctx
					.getBean("reviewServiceBean");

			reviewServiceIF
					.storeAdjectives("F:\\ARTICLE_SIMILARITY\\CODE\\ArticleSimilarPro\\src\\adjectives.txt");

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION--->" + e);
		}

	}

	@Override
	public List<AdjectiveVO> retriveNLPAdjectives() {
		List<AdjectiveVO> adjectiveVOList = null;
		try {
			adjectiveVOList = contextualDao.retriveAllFullNLPAdjectives();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception " + e.getMessage());
			return null;
		}
		return adjectiveVOList;
	}

	private boolean checkUserInformation(String userId) {
		try {
			List<String> userNameList = contextualDao.retriveUserIds();

			System.out.println("LIST" + userNameList);
			if (null == userNameList || userNameList.isEmpty()
					|| userNameList.size() == 0) {
				return false;
			}
			if (userNameList.contains(userId)) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			return true;
		}
	}

	@Override
	public StatusInfo checkLogin(RegisterUser registerUser) {
		StatusInfo statusInfo = null;

		try {
			statusInfo = new StatusInfo();
			boolean status = checkUserInformation(registerUser.getUSN());
			if (!status) {
				statusInfo
						.setErrMsg(ContextualConstantsIF.Message.INVALID_USER_PASSWORD);
				statusInfo.setStatus(false);
				return statusInfo;
			} else {
				String password = contextualDao.retrivePassword(registerUser
						.getUSN());

				if (null == password || password.isEmpty()) {
					statusInfo
							.setErrMsg(ContextualConstantsIF.Message.INVALID_USER_PASSWORD);
					statusInfo.setStatus(false);
					return statusInfo;
				}
				if (!password.equals(registerUser.getPassword())) {
					statusInfo
							.setErrMsg(ContextualConstantsIF.Message.INVALID_USER_PASSWORD);
					statusInfo.setStatus(false);
					return statusInfo;
				}
				if (password.equals(registerUser.getPassword())) {
					statusInfo
							.setErrMsg(ContextualConstantsIF.Message.USER_LOGIN_SUCESS);
					statusInfo.setStatus(true);
					// Now retrieve the login type
					int loginTypeDB = contextualDao
							.retriveLoginType(registerUser.getUSN());
					statusInfo.setType(loginTypeDB);
					return statusInfo;
				}
			}
		} catch (Exception e) {
			statusInfo = new StatusInfo();
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			return statusInfo;

		}
		return statusInfo;

	}

	@Override
	public StatusInfo doRegistration(RegisterUser registerUser) {
		StatusInfo statusInfo = null;
		try {
			statusInfo = new StatusInfo();

			List<String> userIdList = contextualDao.retriveUserIds();
			if (null == userIdList || userIdList.isEmpty()
					|| userIdList.size() == 0) {
				statusInfo = contextualDao.insertLogin(registerUser);

				if (!statusInfo.isStatus()) {
					statusInfo
							.setErrMsg(ContextualConstantsIF.Message.INTERNAL_ERROR);
					statusInfo.setStatus(false);
					return statusInfo;
				} else {

					if (registerUser.getActivityList() != null
							&& registerUser.getActivityList().isEmpty()) {

						statusInfo = contextualDao
								.insertActivityList(registerUser);

						if (!statusInfo.isStatus()) {
							statusInfo
									.setErrMsg(ContextualConstantsIF.Message.INTERNAL_ERROR);
							statusInfo.setStatus(false);
							return statusInfo;
						} else {
							statusInfo.setStatus(true);
							return statusInfo;

						}

					}

					statusInfo.setStatus(true);

					return statusInfo;
				}

			}

			if (userIdList.contains(registerUser.getUSN())) {
				statusInfo
						.setErrMsg(ContextualConstantsIF.Message.USERALREADY_EXIST);
				statusInfo.setStatus(false);
				return statusInfo;
			} else {
				statusInfo = contextualDao.insertLogin(registerUser);

				if (registerUser.getActivityList() != null
						&& !registerUser.getActivityList().isEmpty()) {

					statusInfo = contextualDao.insertActivityList(registerUser);

					if (!statusInfo.isStatus()) {
						statusInfo
								.setErrMsg(ContextualConstantsIF.Message.INTERNAL_ERROR);
						statusInfo.setStatus(false);
						return statusInfo;
					} else {
						statusInfo.setStatus(true);
						return statusInfo;

					}

				}

				if (!statusInfo.isStatus()) {
					statusInfo
							.setErrMsg(ContextualConstantsIF.Message.INTERNAL_ERROR);
					statusInfo.setStatus(false);
					return statusInfo;
				} else {
					return statusInfo;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			statusInfo = new StatusInfo();
			statusInfo.setErrMsg(ContextualConstantsIF.Message.INTERNAL_ERROR);
			statusInfo.setStatus(false);
			return statusInfo;

		}
	}

	@Override
	public StatusInfo computeLogLikelihood() {
		StatusInfo statusInfo = null;
		try {

			statusInfo = new StatusInfo();

			List<String> articleNamesFromFreq = contextualDao
					.retriveDistinctArticleNamesForFreq();

			if (null == articleNamesFromFreq || articleNamesFromFreq.isEmpty()) {

				statusInfo.setStatus(false);
				statusInfo
						.setErrMsg(ContextualConstantsIF.Message.PLEASE_DO_FREQUENCY);

				return statusInfo;

			}

			List<String> featureVectorLogLikelihood = contextualDao
					.retriveUniqueArticleNamesFromLogLikelihood();

			List<String> articleNameNotComputed = new ArrayList<String>();

			if (featureVectorLogLikelihood != null
					&& !featureVectorLogLikelihood.isEmpty()) {
				articleNameNotComputed.addAll(articleNamesFromFreq);
				articleNameNotComputed.removeAll(featureVectorLogLikelihood);
			} else {
				articleNameNotComputed.addAll(articleNamesFromFreq);
			}

			List<LogLikelihoodVO> logLikeliHoodVOList = new ArrayList<LogLikelihoodVO>();

			boolean statusV = contextualDao.deleteLogLikeLihood();

			if (statusV == false) {

				statusInfo
						.setErrMsg("Could Not Remove Old Data From Log LikeLihood");
				statusInfo.setStatus(false);
				return statusInfo;
			}

			for (String articleName : articleNameNotComputed) {

				List<FrequencyVO> freqNames = contextualDao
						.retriveUniqueTokensInfoFromFrequencyForArticle(articleName);

				if (null == freqNames
						|| (freqNames != null && freqNames.isEmpty())) {

					statusInfo = new StatusInfo();
					statusInfo.setStatus(false);
					statusInfo.setErrMsg("Tokens are not found on the Article"
							+ articleName);
					return statusInfo;

				}

				int totalFreq = contextualDao.computeTotalFreq(articleName);

				for (FrequencyVO frequencyVO : freqNames) {

					String tokenName = frequencyVO.getTokenName();

					int freq = frequencyVO.getFreq();

					int otherFreq = contextualDao
							.computeFreqOtherTokensForNotTokenNameAndArticleName(
									tokenName, articleName);

					double expectedFreq = FormulaUtil.computeExpectedFreq(freq,
							otherFreq, totalFreq);

					List<Double> otherFreqValues = contextualDao
							.computeFreqListForArticleNameAndTokenName(
									articleName, tokenName);

					LogLikelihoodVO logLikelihoodVO2 = new LogLikelihoodVO();

					logLikelihoodVO2.setArticleName(articleName);
					logLikelihoodVO2.setExpectedFrequency(expectedFreq);
					logLikelihoodVO2.setFreq(freq);
					double logLikelihood = FormulaUtil
							.computeLogLikelihoodValue(otherFreqValues,
									expectedFreq);
					logLikelihoodVO2.setLogLikelihood(logLikelihood);
					logLikelihoodVO2.setTokenName(tokenName);

					logLikeliHoodVOList.add(logLikelihoodVO2);

				}

				// Insert of Log Likelihood List

				statusInfo = contextualDao
						.insertLogLikelihood(logLikeliHoodVOList);

				if (!statusInfo.isStatus()) {

					statusInfo.setStatus(false);
					statusInfo
							.setErrMsg(ContextualConstantsIF.Message.INSERT_LOGLIKELIHOOD);

					return statusInfo;
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(ContextualConstantsIF.Message.INTERNAL_ERROR);
			return statusInfo;
		}

		statusInfo.setStatus(true);

		return statusInfo;
	}

	@Override
	public List<LogLikelihoodVO> retriveLogLikelihood() {
		List<LogLikelihoodVO> logLikelihood = null;
		try {
			logLikelihood = contextualDao.retriveLogLikelihoodFromDB();
			if (null == logLikelihood) {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			return null;
		}
		return logLikelihood;
	}

	@Override
	public StatusInfo doRVCoeffcient(ComparisionInputModel comparisionModel) {
		StatusInfo statusInfo = new StatusInfo();

		CompareArticleRVVO compareArticleVO = new CompareArticleRVVO();

		try {

			List<String> tokenNamesLeft = contextualDao
					.retriveDistincetTokensForArticleNameFromFrequeny(comparisionModel
							.getArticleNameLeft());

			if (null == tokenNamesLeft || tokenNamesLeft.isEmpty()) {
				statusInfo.setStatus(false);
				statusInfo
						.setErrMsg(ContextualConstantsIF.Message.NO_TOKENS_FOR_ARTICLE_LEFT
								+ comparisionModel.getArticleNameLeft());
				return statusInfo;
			}

			List<String> tokenNamesRight = contextualDao
					.retriveDistincetTokensForArticleNameFromFrequeny(comparisionModel
							.getArticleNameRight());

			if (null == tokenNamesRight || tokenNamesRight.isEmpty()) {
				statusInfo.setStatus(false);
				statusInfo
						.setErrMsg(ContextualConstantsIF.Message.NO_TOKENS_FOR_ARTICLE_RIGHT
								+ comparisionModel.getArticleNameRight());
				return statusInfo;
			}

			List<String> tokenNamesAll = new ArrayList<String>();
			tokenNamesAll.addAll(tokenNamesLeft);

			tokenNamesAll.retainAll(tokenNamesRight);

			// Intersection Set Looping

			Set<String> keyPhraseInterSet = new HashSet<String>();

			for (String word : tokenNamesAll) {

				keyPhraseInterSet.add(word);
			}

			if (null == keyPhraseInterSet
					|| (keyPhraseInterSet != null && keyPhraseInterSet
							.isEmpty())) {

				statusInfo.setStatus(false);

				compareArticleVO.setRvCoeffcient(0);
				compareArticleVO.setSmilar(false);
				return statusInfo;
			}

			// Now Store in the Key Phrase Intersection List

			List<String> keyPhraseIntersection = new ArrayList<String>();

			for (String keyPhrase : keyPhraseInterSet) {
				keyPhraseIntersection.add(keyPhrase);
			}

			compareArticleVO.setIntersectionWords(keyPhraseIntersection);

			List<String> msgList = new ArrayList<String>();

			if (null == keyPhraseIntersection
					|| keyPhraseIntersection.isEmpty()) {
				msgList.add(ContextualConstantsIF.Message.KEY_INTERSECTION_SET_EMPTY);
				statusInfo.setStatus(false);
				return statusInfo;
			}

			// Compute Mean of Words in Article1
			List<Integer> freqLeft = contextualDao
					.retriveFreqValuesForArticleNameFromFreqANDWordsIN(
							comparisionModel.getArticleNameLeft(),
							keyPhraseIntersection);

			int sumLeftArticle = computeSum(freqLeft);

			double meanLeft = computeMean(freqLeft, sumLeftArticle);

			compareArticleVO.setMeanLeftArticle(meanLeft);
			compareArticleVO.setSumLeftArticle(sumLeftArticle);

			List<Integer> freqRight = contextualDao
					.retriveFreqValuesForArticleNameFromFreqANDWordsIN(
							comparisionModel.getArticleNameRight(),
							keyPhraseIntersection);

			// Compute Mean of Words in Article2
			int sumRightArticle = computeSum(freqRight);

			double meanRight = computeMean(freqRight, sumRightArticle);

			compareArticleVO.setMeanRightArticle(meanRight);
			compareArticleVO.setSumRightArticle(sumRightArticle);

			// Compute the Standard Deviation for Article
			double standardDLeft = computeSD(freqLeft, meanLeft);
			compareArticleVO.setStandardDevLeftArticle(standardDLeft);

			// Compute the Standard Deviation for Article
			double standardDRight = computeSD(freqRight, meanRight);
			compareArticleVO.setStandardDevRightArtilce(standardDRight);

			Long timeStart = System.currentTimeMillis();
			// Compute Rv Coeffcient
			FormulaUtil formulaUtil = new FormulaUtil();
			double rvCoeffcient = formulaUtil.computeRVCoefficent(freqRight,
					freqLeft, meanRight, meanLeft, standardDLeft,
					standardDRight);

			compareArticleVO.setRvCoeffcient(rvCoeffcient);

			double threshold = new Double(comparisionModel.getTypeCombo());

			if (rvCoeffcient >= threshold) {

				compareArticleVO.setSmilar(true);
			}

			Long timeStop = System.currentTimeMillis();

			Long timeTaken = timeStop - timeStart;

			compareArticleVO.setTimeTakenProposed(timeTaken);

			double accuracy = 0;
			if (rvCoeffcient == 0) {
				accuracy = 100;
			} else {
				accuracy = rvCoeffcient * 100;
			}
			compareArticleVO.setAccuracyProposed(accuracy);

			// Now Find the Article Comparison with respect to Binary

			List<String> distinctTokenNamesLeft = contextualDao
					.retriveDistincetTokensForArticleName(comparisionModel
							.getArticleNameLeft());

			compareArticleVO.setTokenNamesLeft(distinctTokenNamesLeft);

			// Now Find the Article Comparison with respect to Binary

			List<String> distinctTokenNamesRight = contextualDao
					.retriveDistincetTokensForArticleName(comparisionModel
							.getArticleNameRight());
			compareArticleVO.setTokenNamesRight(distinctTokenNamesRight);

			// Now Do a Binary Comparision of Resemblems Technique
			BinaryCompareObject binarySameObj = formulaUtil
					.computeBinarySimilar(distinctTokenNamesLeft,
							distinctTokenNamesRight);

			compareArticleVO.setTimeTakenPrevious(binarySameObj
					.getTimeTakenBinary() + timeTaken);

			compareArticleVO.setAccuracyOld(binarySameObj.getAccuracy());

			compareArticleVO.setBinaryCompareObject(binarySameObj);

			statusInfo = contextualDao.storeComparison(compareArticleVO);

			statusInfo.setObject(compareArticleVO);

			statusInfo.setStatus(true);

		} catch (Exception e) {
			e.printStackTrace();
			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			return statusInfo;
		}
		return statusInfo;
	}

	private double computeSD(List<Integer> freqLeft, double meanLeft) {
		double standardDeviationBeforeRoot = 0;

		for (Integer freq : freqLeft) {

			double minus = freq - meanLeft;
			double temp = Math.pow(minus, 2);

			standardDeviationBeforeRoot = standardDeviationBeforeRoot + temp;
		}

		double standardDev = Math.sqrt(standardDeviationBeforeRoot);

		return standardDev;
	}

	private double computeMean(List<Integer> freqLeft, double sumLeftArticle) {
		double meanLeft;
		meanLeft = ((double) sumLeftArticle / (double) freqLeft.size());
		return meanLeft;
	}

	private int computeSum(List<Integer> freqLeft) {
		int sumLeftArticle = 0;
		for (Integer freq : freqLeft) {
			sumLeftArticle = sumLeftArticle + freq;
		}
		return sumLeftArticle;
	}

	@Override
	public StatusInfo detectDuplicateArticles(double threshold) {
		StatusInfo statusInfo = new StatusInfo();
		try {

			statusInfo = contextualDao.deleteDuplicateArticles();

			if (!statusInfo.isStatus()) {

				statusInfo.setStatus(false);
				statusInfo.setErrMsg("Could not Delete Grouping Information");
				return statusInfo;
			}

			List<String> articleNames = contextualDao
					.retriveUniqueArticleNamesFromFrequency();

			if (articleNames == null || articleNames.isEmpty()) {

				statusInfo.setStatus(false);
				statusInfo
						.setErrMsg("Please Perform Frequency Before Grouping");
				return statusInfo;

			}

			Map<String, Integer> mapArticleIdAndArticleName = new HashMap<String, Integer>();

			for (String articleName : articleNames) {

				int articleId = contextualDao
						.retriveArticleIdForArticleName(articleName);

				mapArticleIdAndArticleName.put(articleName, articleId);

			}

			List<ArticleModel> reviewModelListTemp = contextualDao
					.retriveAllReviews();

			Map<String, String> mapArticleNameUserId = new HashMap<String, String>();

			List<RegisterUser> userNameList = contextualDao
					.retriveAllRegisterInfo();

			if (reviewModelListTemp != null && !reviewModelListTemp.isEmpty()) {

				for (ArticleModel rev : reviewModelListTemp) {

					mapArticleNameUserId.put(rev.getArticleName(),
							rev.getUserId());
				}

			}

			Map<String, String> mapUserIdEmail = new HashMap<String, String>();

			if (userNameList != null && !userNameList.isEmpty()) {

				for (RegisterUser registerUser : userNameList) {

					mapUserIdEmail.put(registerUser.getUSN(),
							registerUser.getEmail());

				}
			}

			//
			int groupId = 1;
			for (String articleName : articleNames) {

				int groupIdFromDB = contextualDao
						.retriveGroupIdForArticleNameFromDuplicity(articleName);

				if (groupIdFromDB <= 0) {

					statusInfo = new StatusInfo();

					DuplicateBugResult duplicateBugResult = new DuplicateBugResult();
					duplicateBugResult.setArticleParent(articleName);
					duplicateBugResult
							.setArticleIdParent(mapArticleIdAndArticleName
									.get(articleName));
					duplicateBugResult.setArticleChild(articleName);
					duplicateBugResult
							.setArticleIdChild(mapArticleIdAndArticleName
									.get(articleName));
					duplicateBugResult.setGroupId(groupId);
					duplicateBugResult.setThreshold(threshold);
					duplicateBugResult.setRvCoeeffcient(0);
					duplicateBugResult.setSimilarity(true);
					String userId = mapArticleNameUserId.get(articleName);
					duplicateBugResult.setUserId(userId);
					String email = mapUserIdEmail.get(userId);
					duplicateBugResult.setEmail(email);

					statusInfo = contextualDao
							.insertDuplicateArticle(duplicateBugResult);

					if (!statusInfo.isStatus()) {
						statusInfo.setStatus(false);
						statusInfo
								.setErrMsg("Initial Assigment for Resources has Failed");
						return statusInfo;
					}

					for (String articleName1 : articleNames) {

						groupIdFromDB = contextualDao
								.retriveGroupIdForArticleNameFromGroupId(articleName1);

						if (groupIdFromDB <= 0) {

							if (articleName1 != articleName) {

								ComparisionInputModel comparisionModel = new ComparisionInputModel();
								comparisionModel
										.setArticleNameLeft(articleName1);
								comparisionModel
										.setArticleNameRight(articleName);
								comparisionModel.setTypeCombo(Double
										.toString(threshold));

								boolean assignedGroup = false;
								StatusInfo statusInfoTemp = doRVCoeffcient(comparisionModel);

								CompareArticleRVVO compareArticleRVVO = null;
								if (statusInfoTemp != null
										&& statusInfoTemp.isStatus() == true) {

									compareArticleRVVO = (CompareArticleRVVO) statusInfoTemp
											.getObject();

									if (compareArticleRVVO.isSmilar()) {
										assignedGroup = true;
									}

								}

								duplicateBugResult = new DuplicateBugResult();
								duplicateBugResult
										.setArticleParent(articleName1);

								duplicateBugResult
										.setArticleIdParent(mapArticleIdAndArticleName
												.get(articleName1));
								duplicateBugResult.setArticleChild(articleName);
								duplicateBugResult
										.setArticleIdChild(mapArticleIdAndArticleName
												.get(articleName));

								duplicateBugResult.setArticleChild(articleName);
								if (assignedGroup == true) {
									duplicateBugResult.setGroupId(groupId);
								}
								duplicateBugResult.setThreshold(threshold);
								userId = mapArticleNameUserId.get(articleName);
								duplicateBugResult.setUserId(userId);
								email = mapUserIdEmail.get(userId);
								duplicateBugResult.setEmail(email);
								if (compareArticleRVVO != null) {
									duplicateBugResult
											.setRvCoeeffcient(compareArticleRVVO
													.getRvCoeffcient());
									duplicateBugResult
											.setSimilarity(compareArticleRVVO
													.isSmilar());
								}

								statusInfo = contextualDao
										.insertGroupedArticle(duplicateBugResult);

								if (!statusInfo.isStatus()) {

									statusInfo.setStatus(false);
									statusInfo
											.setErrMsg("Could not Group Articles");

								}

							}
						}

					}
					groupId = groupId + 1;

				}
			}

		} catch (Exception e) {

			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			return statusInfo;
		}

		statusInfo.setStatus(true);

		return statusInfo;
	}

	@Override
	public List<GroupingView> viewGrouping() {
		List<GroupingView> groupingViewList = null;
		try {
			groupingViewList = contextualDao.viewGrouping();
			if (null == groupingViewList) {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			return null;
		}
		return groupingViewList;
	}

	@Override
	public StatusInfo doThemes(ThemeModel themeModel) {

		List<LogLikelihoodVO> logLikeLihoodVONew = new ArrayList<LogLikelihoodVO>();

		StatusInfo statusInfo = new StatusInfo();
		try {

			Double thresholdTemp = Double.valueOf(themeModel.getTypeCombo());
			logLikeLihoodVONew = contextualDao
					.retriveLogLikelihoodFromDBOrderBy(thresholdTemp);

			if (logLikeLihoodVONew == null || logLikeLihoodVONew.isEmpty()) {

				statusInfo.setStatus(false);
				statusInfo
						.setErrMsg("Log Likelihood Function is Not Performed By Admin");
				return statusInfo;
			}

			statusInfo.setStatus(true);
			statusInfo.setObject(logLikeLihoodVONew);

		} catch (Exception e) {

			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			return statusInfo;
		}

		return statusInfo;

	}

	@Override
	public List<ComparisionVO> retriveComparison() {
		List<ComparisionVO> comparisionVOList = null;

		List<ComparisionVO> comparisionVOListNew = null;
		try {
			comparisionVOList = contextualDao.retriveComparison();

			comparisionVOListNew = new ArrayList<ComparisionVO>();

			if (comparisionVOList != null && !comparisionVOList.isEmpty()) {

				int iterationNo = 1;
				for (ComparisionVO comparisionVO : comparisionVOList) {

					ComparisionVO comparisionVO2 = new ComparisionVO();

					if (comparisionVO.getProposedTime() == 0) {
						comparisionVO.setProposedTime(0.01);
					}

					comparisionVO2.setIterationNo(iterationNo);
					comparisionVO2.setPreviousAccuracy(comparisionVO
							.getPreviousAccuracy());
					if (comparisionVO.getPreviousTime() == 0) {
						comparisionVO.setPreviousTime(0.001);
					}

					comparisionVO2.setPreviousTime(comparisionVO
							.getPreviousTime());

					comparisionVO2.setProposedAccuracy(comparisionVO
							.getProposedAccuracy());
					comparisionVO2.setProposedTime(comparisionVO
							.getProposedTime());

					comparisionVOListNew.add(comparisionVO2);

					iterationNo = iterationNo + 1;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			return null;
		}
		return comparisionVOListNew;
	}

	@Override
	public StatusInfo doRegistrationTeacher(RegisterUser registerUser) {
		StatusInfo statusInfo = null;
		try {
			statusInfo = new StatusInfo();

			List<String> userIdList = contextualDao.retriveUserIds();
			if (null == userIdList || userIdList.isEmpty()
					|| userIdList.size() == 0) {
				statusInfo = contextualDao.insertLogin(registerUser);

				if (!statusInfo.isStatus()) {
					statusInfo
							.setErrMsg(ContextualConstantsIF.Message.INTERNAL_ERROR);
					statusInfo.setStatus(false);
					return statusInfo;
				} else {

					System.out.println("outside SUbject List");
					if (registerUser.getSubjectList() != null
							&& !registerUser.getSubjectList().isEmpty()) {

						System.out.println("SUbject List");
						statusInfo = contextualDao
								.insertSubjectList(registerUser);

						if (!statusInfo.isStatus()) {
							statusInfo
									.setErrMsg(ContextualConstantsIF.Message.INTERNAL_ERROR);
							statusInfo.setStatus(false);
							return statusInfo;
						} else {
							statusInfo.setStatus(true);
							return statusInfo;

						}

					}

					statusInfo.setStatus(true);

					return statusInfo;
				}

			}

			if (userIdList.contains(registerUser.getUSN())) {
				statusInfo
						.setErrMsg(ContextualConstantsIF.Message.USERALREADY_EXIST);
				statusInfo.setStatus(false);
				return statusInfo;
			} else {
				statusInfo = contextualDao.insertLogin(registerUser);

				System.out.println("SUB OUT");
				if (registerUser.getSubjectList() != null
						&& !registerUser.getSubjectList().isEmpty()) {

					System.out.println("SUB IN");

					statusInfo = contextualDao.insertSubjectList(registerUser);

					if (!statusInfo.isStatus()) {
						statusInfo
								.setErrMsg(ContextualConstantsIF.Message.INTERNAL_ERROR);
						statusInfo.setStatus(false);
						return statusInfo;
					} else {
						statusInfo.setStatus(true);
						return statusInfo;

					}

				}

				if (!statusInfo.isStatus()) {
					statusInfo
							.setErrMsg(ContextualConstantsIF.Message.INTERNAL_ERROR);
					statusInfo.setStatus(false);
					return statusInfo;
				} else {
					return statusInfo;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			statusInfo = new StatusInfo();
			statusInfo.setErrMsg(ContextualConstantsIF.Message.INTERNAL_ERROR);
			statusInfo.setStatus(false);
			return statusInfo;

		}
	}

	@Override
	public StatusInfo storeArticle(ArticleVO articleVO) {

		StatusInfo statusInfo = new StatusInfo();
		try {

			List<String> articleNames = contextualDao.retriveArticleNames();

			if (null == articleNames || articleNames.isEmpty()) {

				statusInfo = contextualDao.insertArticle(articleVO);
				if (!statusInfo.isStatus()) {

					statusInfo = new StatusInfo();
					statusInfo
							.setErrMsg(ContextualConstantsIF.Message.INSERT_ARTICLE_FAILED);
					statusInfo.setStatus(false);
					return statusInfo;
				}

				statusInfo = contextualDao.insertKeyWordList(
						articleVO.getKeyWordList(), articleVO.getArticleName());

				if (!statusInfo.isStatus()) {

					statusInfo = new StatusInfo();
					statusInfo
							.setErrMsg(ContextualConstantsIF.Message.INSERT_KEYWORDLIST_FAILED);
					statusInfo.setStatus(false);
					return statusInfo;

				}

				statusInfo = contextualDao.insertAuthorList(
						articleVO.getAuthorList(), articleVO.getArticleName());

				if (!statusInfo.isStatus()) {

					statusInfo = new StatusInfo();
					statusInfo
							.setErrMsg(ContextualConstantsIF.Message.INSERT_AUTHOR_LIST_FAILED);
					statusInfo.setStatus(false);
					return statusInfo;

				}

				// Perform Data Cleaning
				statusInfo = performCleaningDuringUpload(articleVO);
				if (!statusInfo.isStatus()) {
					return statusInfo;
				}

				// Perform Tokenization
				statusInfo = performTokenizationDuringUpload(articleVO);
				if (!statusInfo.isStatus()) {
					return statusInfo;
				}

				// Perform Frequency
				statusInfo = performFreqDuringUpload(articleVO);
				if (!statusInfo.isStatus()) {
					return statusInfo;
				}

				statusInfo.setStatus(true);

			} else {

				if (articleNames.contains(articleVO.getArticleName())) {
					statusInfo = new StatusInfo();
					statusInfo
							.setErrMsg(ContextualConstantsIF.Message.DUPLICATE_ARTICLENAME);
					statusInfo.setStatus(false);
					return statusInfo;

				} else {
					statusInfo = contextualDao.insertArticle(articleVO);
					if (!statusInfo.isStatus()) {

						statusInfo = new StatusInfo();
						statusInfo
								.setErrMsg(ContextualConstantsIF.Message.INSERT_ARTICLE_FAILED);
						statusInfo.setStatus(false);
						return statusInfo;
					}

					statusInfo = contextualDao.insertKeyWordList(
							articleVO.getKeyWordList(),
							articleVO.getArticleName());

					if (!statusInfo.isStatus()) {

						statusInfo = new StatusInfo();
						statusInfo
								.setErrMsg(ContextualConstantsIF.Message.INSERT_KEYWORDLIST_FAILED);
						statusInfo.setStatus(false);
						return statusInfo;

					}

					statusInfo = contextualDao.insertAuthorList(
							articleVO.getAuthorList(),
							articleVO.getArticleName());

					if (!statusInfo.isStatus()) {

						statusInfo = new StatusInfo();
						statusInfo
								.setErrMsg(ContextualConstantsIF.Message.INSERT_AUTHOR_LIST_FAILED);
						statusInfo.setStatus(false);
						return statusInfo;

					}

					// Perform Data Cleaning
					statusInfo = performCleaningDuringUpload(articleVO);
					if (!statusInfo.isStatus()) {
						return statusInfo;
					}

					// Perform Tokenization
					statusInfo = performTokenizationDuringUpload(articleVO);
					if (!statusInfo.isStatus()) {
						return statusInfo;
					}

					// Perform Frequency
					statusInfo = performFreqDuringUpload(articleVO);
					if (!statusInfo.isStatus()) {
						return statusInfo;
					}

					statusInfo.setStatus(true);

				}

			}

		} catch (Exception e) {

			e.printStackTrace();
			statusInfo = new StatusInfo();
			statusInfo.setErrMsg(ContextualConstantsIF.Message.INTERNAL_ERROR);
			statusInfo.setStatus(false);
			return statusInfo;

		}
		statusInfo.setStatus(true);
		return statusInfo;

	}

	private StatusInfo performFreqDuringUpload(ArticleVO articleVO) {
		StatusInfo statusInfo = new StatusInfo();
		String articleName = articleVO.getArticleName();

		List<TokenVO> tokensList = contextualDao
				.retriveAllTokensForArticleName(articleName);

		if (null == tokensList || tokensList.size() == 0) {

			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(ContextualConstantsIF.Message.TOKENS_EMPTY);
			return statusInfo;

		}

		List<String> distinctTokens = contextualDao
				.retriveDistinctTokensForArticleName(articleName);

		if (null == distinctTokens || distinctTokens.isEmpty()
				|| distinctTokens.size() == 0) {

			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo
					.setErrMsg(ContextualConstantsIF.Message.COULDNOT_FIND_TOKENS);
			return statusInfo;
		}

		for (String tokenTemp : distinctTokens) {

			// count of tokens
			int count = contextualDao.retriveCountForTokenInArticle(tokenTemp,
					articleName);

			FrequencyVO freqVO = new FrequencyVO();

			freqVO.setArticleName(articleName);
			freqVO.setTokenName(tokenTemp);
			freqVO.setFreq(count);

			// Insert into Frequency Table

			statusInfo = contextualDao.insertFrequency(freqVO);
			if (!statusInfo.isStatus()) {
				statusInfo = new StatusInfo();
				statusInfo.setStatus(false);
				statusInfo
						.setErrMsg(ContextualConstantsIF.Message.COULDNOT_INSERT_FREQUENCY);
				return statusInfo;
			}
		}

		statusInfo.setStatus(true);

		return statusInfo;
	}

	private StatusInfo performTokenizationDuringUpload(ArticleVO articleVO) {

		StatusInfo statusInfo = new StatusInfo();
		List<CleanArticleModel> cleanArticleModelList = contextualDao
				.retriveCleanDataForArticleName(articleVO.getArticleName());

		if (null == cleanArticleModelList || cleanArticleModelList.isEmpty()) {
			statusInfo.setStatus(false);
			statusInfo
					.setErrMsg(ContextualConstantsIF.Message.CLEAN_ARTICLE_EMPTY);
			return statusInfo;
		}

		String cleanArticle = cleanArticleModelList.get(0).getCleanArtilce();

		StringTokenizer tok1 = new StringTokenizer(cleanArticle,
				ContextualConstantsIF.Keys.SPACE);
		String tokenName;
		while (tok1.hasMoreTokens()) {
			tokenName = tok1.nextToken();
			tokenName = tokenName.toLowerCase();
			TokenVO tokenVO = new TokenVO();
			tokenVO.setTokenName(tokenName);
			tokenVO.setArticleName(articleVO.getArticleName());
			statusInfo = contextualDao.insertToken(tokenVO);
			if (!statusInfo.isStatus()) {
				statusInfo.setStatus(false);
				statusInfo
						.setErrMsg(ContextualConstantsIF.Message.INSERT_TOKENS_FAILED);
				return statusInfo;
			}

			// end of for Loop
		}
		statusInfo.setStatus(true);
		return statusInfo;
	}

	private StatusInfo performCleaningDuringUpload(ArticleVO articleVO) {
		StatusInfo statusInfo = new StatusInfo();
		List<String> stopWordsList = contextualDao.retriveStopWordsOnly();

		String str = articleVO.getArticleDesc().replaceAll("\\s", " ");

		String webSiteDataCleanTemp = str.replaceAll(
				ContextualConstantsIF.Keys.STOPWORDS_SYMBOL,
				ContextualConstantsIF.Keys.SPACE);

		StringTokenizer tok1 = new StringTokenizer(webSiteDataCleanTemp);
		StringBuilder buffer = new StringBuilder();
		String str1 = null;
		while (tok1.hasMoreTokens()) {
			str1 = (String) tok1.nextElement();
			str1 = str1.toLowerCase();

			if (null == str1 || str1.isEmpty() || str1.length() <= 0
					|| str1.trim().length() == 0) {
				continue;
			}
			if (str1 != null) {
				str1 = str1.trim();
			}
			if (stopWordsList.contains(str1)) {
				continue;
			} else {
				str1 = str1.replaceAll(
						ContextualConstantsIF.Keys.STOPWORDS_SYMBOL,
						ContextualConstantsIF.Keys.SPACE);
				str1 = str1.trim();
				if (str1.length() > 0) {
					buffer.append(ContextualConstantsIF.Keys.SPACE);
					buffer.append(str1);
				}
			}
		}

		CleanArticleModel cleanReview = new CleanArticleModel();
		cleanReview.setArticleName(articleVO.getArticleName());
		// Blob cleanData = covertFromStringToBlob(buffer.toString());
		cleanReview.setCleanArtilce(buffer.toString());

		statusInfo = contextualDao.insertCleanDetails(cleanReview);

		if (!statusInfo.isStatus()) {
			statusInfo.setStatus(false);
			statusInfo
					.setErrMsg(ContextualConstantsIF.Message.CLEANMODEL_FAILED);
			return statusInfo;
		}
		statusInfo.setStatus(true);
		return statusInfo;
	}

	@Override
	public List<ArticleVO> retriveArticles() {
		try {

			List<ArticleVO> reviewModelListTemp = contextualDao
					.retriveAllArticles();

			return reviewModelListTemp;

		} catch (Exception e) {
			System.out.println("Exception e" + e.getMessage());

		}
		return null;
	}

	@Override
	public List<ArticleVO> retriveArticlesForCustomer(String loginId) {
		try {

			List<ArticleVO> articleList = contextualDao
					.retriveAllArticlesForCustomer(loginId);

			return articleList;

		} catch (Exception e) {
			System.out.println("Exception e" + e.getMessage());

		}
		return null;
	}

	@Override
	public StatusInfo doFeatureVector() {
		StatusInfo statusInfo = null;
		try {
			statusInfo = new StatusInfo();

			List<String> articleNamesFromFreq = contextualDao
					.retriveDistinctArticleNamesForFreq();

			if (null == articleNamesFromFreq || articleNamesFromFreq.isEmpty()) {

				statusInfo.setStatus(false);
				statusInfo
						.setErrMsg(ContextualConstantsIF.Message.PLEASE_DO_FREQUENCY);

				return statusInfo;

			}

			List<String> featureVectorArticleNames = contextualDao
					.retriveDistinctArticleNamesFromFeatureVector();

			List<String> articleNameNotComputed = new ArrayList<String>();

			if (featureVectorArticleNames != null
					&& !featureVectorArticleNames.isEmpty()) {
				articleNameNotComputed.addAll(articleNamesFromFreq);
				articleNameNotComputed.removeAll(featureVectorArticleNames);
			} else {
				articleNameNotComputed.addAll(articleNamesFromFreq);
			}

			for (String articleName : articleNameNotComputed) {

				List<String> tokenNames = contextualDao
						.retriveDistinctTokensFromFreqForArticleName(articleName);

				for (String tokenName : tokenNames) {

					FeatureVO featureVO = new FeatureVO();

					// Count of URLS
					int countOfUrls = contextualDao
							.retriveCountOfDocsInWhichTokenIsPresent(tokenName);

					FrequencyVO frequencyVO = contextualDao
							.retriveFreqForArticleNameAndTokenName(articleName,
									tokenName);

					int freq = frequencyVO.getFreq();

					if (countOfUrls <= 0) {
						countOfUrls = 1;
					}

					double idftTemp = ((double) freq / (double) countOfUrls);

					double idft = 10 * Math.log(idftTemp);

					if (idft < 0) {
						idft = idft * -1;
					}

					double featureVector = idft * freq;

					if (featureVector == 0.0) {
						featureVector = freq;
					}

					featureVO.setFreq(frequencyVO.getFreq());
					featureVO.setNoOfDocs(countOfUrls);
					featureVO.setTokenName(frequencyVO.getTokenName());
					featureVO.setIdft(idft);
					featureVO.setFeatureVector(featureVector);
					featureVO.setArticleName(articleName);

					statusInfo = contextualDao.insertFeatureVector(featureVO);
					if (!statusInfo.isStatus()) {
						statusInfo.setStatus(false);
						statusInfo
								.setErrMsg(ContextualConstantsIF.Message.FEATURE_VECTOR_EMPTY);
						return statusInfo;
					}
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("MESSAGE EXCEPTION" + e.getMessage());
			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			return statusInfo;
		}

		statusInfo.setStatus(true);

		return statusInfo;
	}

	@Override
	public List<FeatureVO> viewFV() {
		List<FeatureVO> featureVOList = null;
		try {
			featureVOList = contextualDao.viewFV();
			if (null == featureVOList) {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			return null;
		}
		return featureVOList;
	}

	@Override
	public ArticleVO viewSpecificArticle(String articleName) {
		try {

			return contextualDao
					.retriveArticleDetailForArticleName(articleName);

		} catch (Exception e) {
			System.out.println("Exception e" + e.getMessage());

		}
		return null;
	}

	@Override
	public StatusInfo performRankSearch(String contextPath,
			SearchCriteria searchCriteria) {

		StatusInfo statusInfo = new StatusInfo();

		List<SearchObj> searchResultsList = null;
		try {
			searchResultsList = new ArrayList<SearchObj>();

			String query = searchCriteria.getQuery();

			/*if (null == query || query.isEmpty() || query.trim().isEmpty() || searchCriteria.getAuthors().trim().isEmpty() || searchCriteria.getPublisher().trim().isEmpty() || searchCriteria.getKeywords().trim().isEmpty() || searchCriteria.getInterestArea().trim().isEmpty()) {

				statusInfo.setStatus(false);
				statusInfo.setErrMsg(ContextualConstantsIF.Message.QUERY_EMPTY);

				return statusInfo;
			}*/
			
			
			if(!searchCriteria.getQuery().trim().isEmpty()){

				String tokens[] = query.split(" ");

				List<String> distinctArticleNamesList = new ArrayList<String>();

				if (null == searchCriteria.getKeywords()
						&& null == searchCriteria.getPublisher()
						&& null == searchCriteria.getAuthors()) {
					distinctArticleNamesList = contextualDao
							.retriveDistinctArticleNamesForFreq();
				} else if (searchCriteria.getAuthors().isEmpty()
						&& searchCriteria.getPublisher().isEmpty()
						&& searchCriteria.getKeywords().isEmpty()) {
					distinctArticleNamesList = contextualDao
							.retriveDistinctArticleNamesForFreq();
				} else {
					Set<String> articleNameList = new HashSet<String>();

					String authorWithComma = searchCriteria.getAuthors();

					Set<String> articleNameListFromAuthors = obtainArticlesBasedonAuthorList(authorWithComma);

					if (articleNameListFromAuthors != null
							&& !articleNameListFromAuthors.isEmpty()) {

						articleNameList.addAll(articleNameListFromAuthors);
					}

					Set<String> articleNameListFromKeywords = obtainArticlesBasedonKeywordList(searchCriteria
							.getKeywords());

					if (articleNameListFromKeywords != null
						&& !articleNameListFromKeywords.isEmpty()) {

						articleNameList.addAll(articleNameListFromKeywords);
					}

					Set<String> articleNameListFromPublisher = obtainArticlesBasedonPubisherList(searchCriteria
						.getPublisher());

					if (articleNameListFromPublisher != null
							&& !articleNameListFromPublisher.isEmpty()) {

						articleNameList.addAll(articleNameListFromPublisher);
					}

					if (null == articleNameList || articleNameList.isEmpty()) {

						statusInfo = new StatusInfo();
						statusInfo.setStatus(false);
						statusInfo
							.setErrMsg(ContextualConstantsIF.Message.NO_ARTICLES_FOUND_WITH_ADDITIONALCRITERIA);

						return statusInfo;

					}

					distinctArticleNamesList.addAll(articleNameList);

				}

				List<BestFeatureVector> bestFV = new ArrayList<BestFeatureVector>();
				for (String distinctArticleName : distinctArticleNamesList) {

					double totalFV = 0;
					for (String tokenTemp : tokens) {
						double fvdouble = 0.0;
						List<Double> featureVector = contextualDao
								.retriveFVForArticleNameAndTokenName(
										distinctArticleName, tokenTemp);

						if (null == featureVector) {
							fvdouble = 0;
						} else {
							for (Double fv1 : featureVector) {
								fvdouble = fvdouble + fv1;
							}
						}	

						totalFV = totalFV + fvdouble;
					}

					BestFeatureVector bfv = new BestFeatureVector();
					bfv.setFeatureVector(totalFV);
					bfv.setArticleName(distinctArticleName);
					bestFV.add(bfv);
				}

				// Now Insert the Best FV
				StatusInfo st = contextualDao.deleteFromBestFV();
				if (!st.isStatus()) {
					return null;
				}

				// Now Insert Into Best FV
				statusInfo = contextualDao.insertBestFV(bestFV);

				if (!statusInfo.isStatus()) {
					return null;
				}

				List<BestFeatureVector> bestFeatureVectorList = contextualDao
						.rateArticles();

				if (null == bestFeatureVectorList) {
					return null;
				}

				//Search History
				
				String USN = null;
				
				List<Map<String, Object>> searchHistoryList = new ArrayList<Map<String, Object>>();
				
				
				for (BestFeatureVector bestFeatureVector : bestFeatureVectorList) {
					
					Map<String, Object> map = new HashMap<String,Object>();

					SearchObj searchObj = new SearchObj();

					ArticleVO articleVO = contextualDao
							.retriveArticleDetailForArticleName(bestFeatureVector
								.getArticleName());
					searchObj
						.setFeatureVector(bestFeatureVector.getFeatureVector());
					searchObj.setDesc(articleVO.getArticleDesc());
					searchObj.setTitle(articleVO.getArticleName());
					searchObj.setUserId(articleVO.getUSN());
					searchObj.setArticleId(articleVO.getArticleId());
					searchObj.setUrl(contextPath
							+ "/review/viewArticleStudent.do?articleName="
							+ articleVO.getArticleId());
					searchObj.setPublisher(articleVO.getPublisher());

					List<String> keywords = contextualDao
							.retriveKeywordsForArticles(articleVO.getArticleName());

					searchObj.setKeywords(keywords);

					List<String> authorList = contextualDao
							.retriveAuthorListForArticles(articleVO
									.getArticleName());

					searchObj.setAuthorList(authorList);

					searchResultsList.add(searchObj);
					
					map.put("FEATUREVECTOR", bestFeatureVector.getFeatureVector());
					map.put("ARTICLENAME", articleVO.getArticleName());
					map.put("ARTICLEDESC", articleVO.getArticleDesc());
					map.put("USN", articleVO.getUSN());
					
					map.put("ARTICLEID", articleVO.getArticleId());
					
					map.put("HIDDENURL", contextPath
							+ "/review/viewArticleStudent.do?articleName="
							+ articleVO.getArticleId());
					
					map.put("PUBLISHER", articleVO.getPublisher());
					map.put("KEYWORD", keywords);
					USN = articleVO.getUSN();

					searchHistoryList.add(map);
				}
				
				List<SearchObj> listSearch = new ArrayList<SearchObj>();
				
				statusInfo = contextualDao.insertSearchHistory(searchHistoryList);
				
				listSearch = contextualDao.getHistoryByUSN(USN);

				

			}
			
			
			else if(!searchCriteria.getAuthors().trim().isEmpty()){
				Set<String> distinctInterestAreaList = new HashSet<String>();
				
				distinctInterestAreaList = contextualDao.getAllAuthors(searchCriteria.getAuthors().trim());
				
				
				Map<String, Double> featureVectorList = new HashMap<String, Double>();
				
				for(String area : distinctInterestAreaList){
					int countOfUrls = contextualDao
							.retriveCountOfDocsInWhichAuthorsIsPresent(area, searchCriteria.getAuthors().trim());
					
					int frequency = contextualDao
							.retriveFreqForArticleName(area);
					
					int freq = frequency;

					if (countOfUrls <= 0) {
						countOfUrls = 1;
					}

					double idftTemp = ((double) freq / (double) countOfUrls);

					double idft = 10 * Math.log(idftTemp);

					if (idft < 0) {
						idft = idft * -1;
					}

					double featureVector = idft * freq;
					
					if (featureVector == 0.0) {
						featureVector = freq;
					}
					
					featureVectorList.put(area, featureVector);
					
				}
				
				List<BestFeatureVector> bestFV = new ArrayList<BestFeatureVector>();
				
				for(String area : featureVectorList.keySet()){
					double totalFV = 0;
					double fvdouble = 0.0;
					if (null == featureVectorList.get(area)) {
						fvdouble = 0;
					} else {
							fvdouble = fvdouble + featureVectorList.get(area);
					}	

					totalFV = totalFV + fvdouble;
					
					BestFeatureVector bfv = new BestFeatureVector();
					bfv.setFeatureVector(totalFV);
					bfv.setArticleName(area);
					bestFV.add(bfv);
				}
				
				
				// Now Insert the Best FV
				StatusInfo st = contextualDao.deleteFromBestFV();
				if (!st.isStatus()) {
					return null;
				}

				// Now Insert Into Best FV
				statusInfo = contextualDao.insertBestFV(bestFV);

				if (!statusInfo.isStatus()) {
					return null;
				}

				List<BestFeatureVector> bestFeatureVectorList = contextualDao
						.rateArticles();

				if (null == bestFeatureVectorList) {
					return null;
				}

				for (BestFeatureVector bestFeatureVector : bestFeatureVectorList) {

					SearchObj searchObj = new SearchObj();

					ArticleVO articleVO = contextualDao
							.retriveArticleDetailForArticleName(bestFeatureVector
								.getArticleName());
					searchObj
						.setFeatureVector(bestFeatureVector.getFeatureVector());
					searchObj.setDesc(articleVO.getArticleDesc());
					searchObj.setTitle(articleVO.getArticleName());
					searchObj.setUserId(articleVO.getUSN());
					searchObj.setArticleId(articleVO.getArticleId());
					searchObj.setUrl(contextPath
							+ "/review/viewArticleStudent.do?articleName="
							+ articleVO.getArticleId());
					searchObj.setPublisher(articleVO.getPublisher());

					List<String> keywords = contextualDao
							.retriveKeywordsForArticles(articleVO.getArticleName());

					searchObj.setKeywords(keywords);

					List<String> authorList = contextualDao
							.retriveAuthorListForArticles(articleVO
									.getArticleName());

					searchObj.setAuthorList(authorList);

					searchResultsList.add(searchObj);

				}

			}

			
			else if(!searchCriteria.getPublisher().trim().isEmpty()){
				Set<String> distinctInterestAreaList = new HashSet<String>();
				
				distinctInterestAreaList = contextualDao.getAllPublisher(searchCriteria.getPublisher().trim());
				
				
				Map<String, Double> featureVectorList = new HashMap<String, Double>();
				
				for(String area : distinctInterestAreaList){
					int countOfUrls = contextualDao
							.retriveCountOfDocsInWhichPublisherIsPresent(area, searchCriteria.getPublisher().trim());
					
					int frequency = contextualDao
							.retriveFreqForArticleName(area);
					
					int freq = frequency;

					if (countOfUrls <= 0) {
						countOfUrls = 1;
					}

					double idftTemp = ((double) freq / (double) countOfUrls);

					double idft = 10 * Math.log(idftTemp);

					if (idft < 0) {
						idft = idft * -1;
					}

					double featureVector = idft * freq;
					
					if (featureVector == 0.0) {
						featureVector = freq;
					}
					
					featureVectorList.put(area, featureVector);
					
				}
				
				List<BestFeatureVector> bestFV = new ArrayList<BestFeatureVector>();
				
				for(String area : featureVectorList.keySet()){
					double totalFV = 0;
					double fvdouble = 0.0;
					if (null == featureVectorList.get(area)) {
						fvdouble = 0;
					} else {
							fvdouble = fvdouble + featureVectorList.get(area);
					}	

					totalFV = totalFV + fvdouble;
					
					BestFeatureVector bfv = new BestFeatureVector();
					bfv.setFeatureVector(totalFV);
					bfv.setArticleName(area);
					bestFV.add(bfv);
				}
				
				
				// Now Insert the Best FV
				StatusInfo st = contextualDao.deleteFromBestFV();
				if (!st.isStatus()) {
					return null;
				}

				// Now Insert Into Best FV
				statusInfo = contextualDao.insertBestFV(bestFV);

				if (!statusInfo.isStatus()) {
					return null;
				}

				List<BestFeatureVector> bestFeatureVectorList = contextualDao
						.rateArticles();

				if (null == bestFeatureVectorList) {
					return null;
				}

				for (BestFeatureVector bestFeatureVector : bestFeatureVectorList) {

					SearchObj searchObj = new SearchObj();

					ArticleVO articleVO = contextualDao
							.retriveArticleDetailForArticleName(bestFeatureVector
								.getArticleName());
					searchObj
						.setFeatureVector(bestFeatureVector.getFeatureVector());
					searchObj.setDesc(articleVO.getArticleDesc());
					searchObj.setTitle(articleVO.getArticleName());
					searchObj.setUserId(articleVO.getUSN());
					searchObj.setArticleId(articleVO.getArticleId());
					searchObj.setUrl(contextPath
							+ "/review/viewArticleStudent.do?articleName="
							+ articleVO.getArticleId());
					searchObj.setPublisher(articleVO.getPublisher());

					List<String> keywords = contextualDao
							.retriveKeywordsForArticles(articleVO.getArticleName());

					searchObj.setKeywords(keywords);

					List<String> authorList = contextualDao
							.retriveAuthorListForArticles(articleVO
									.getArticleName());

					searchObj.setAuthorList(authorList);

					searchResultsList.add(searchObj);

				}

			}

			
			else if(!searchCriteria.getKeywords().trim().isEmpty()){
				Set<String> distinctInterestAreaList = new HashSet<String>();
				
				distinctInterestAreaList = contextualDao.getAllKeyWord(searchCriteria.getKeywords());
				
				
				Map<String, Double> featureVectorList = new HashMap<String, Double>();
				
				for(String area : distinctInterestAreaList){
					int countOfUrls = contextualDao
							.retriveCountOfDocsInWhichKeywordsIsPresent(area, searchCriteria.getKeywords());
					
					int frequency = contextualDao
							.retriveFreqForArticleName(area);
					
					int freq = frequency;

					if (countOfUrls <= 0) {
						countOfUrls = 1;
					}

					double idftTemp = ((double) freq / (double) countOfUrls);

					double idft = 10 * Math.log(idftTemp);

					if (idft < 0) {
						idft = idft * -1;
					}

					double featureVector = idft * freq;
					
					if (featureVector == 0.0) {
						featureVector = freq;
					}
					
					featureVectorList.put(area, featureVector);
					
				}
				
				List<BestFeatureVector> bestFV = new ArrayList<BestFeatureVector>();
				
				for(String area : featureVectorList.keySet()){
					double totalFV = 0;
					double fvdouble = 0.0;
					if (null == featureVectorList.get(area)) {
						fvdouble = 0;
					} else {
							fvdouble = fvdouble + featureVectorList.get(area);
					}	

					totalFV = totalFV + fvdouble;
					
					BestFeatureVector bfv = new BestFeatureVector();
					bfv.setFeatureVector(totalFV);
					bfv.setArticleName(area);
					bestFV.add(bfv);
				}
				
				
				// Now Insert the Best FV
				StatusInfo st = contextualDao.deleteFromBestFV();
				if (!st.isStatus()) {
					return null;
				}

				// Now Insert Into Best FV
				statusInfo = contextualDao.insertBestFV(bestFV);

				if (!statusInfo.isStatus()) {
					return null;
				}

				List<BestFeatureVector> bestFeatureVectorList = contextualDao
						.rateArticles();

				if (null == bestFeatureVectorList) {
					return null;
				}
				
				
				
				for (BestFeatureVector bestFeatureVector : bestFeatureVectorList) {

					SearchObj searchObj = new SearchObj();
					
					Map<String, Object> map = new HashMap<String, Object>();

					ArticleVO articleVO = contextualDao
							.retriveArticleDetailForArticleName(bestFeatureVector
								.getArticleName());
					searchObj
						.setFeatureVector(bestFeatureVector.getFeatureVector());
					
					
					
					searchObj.setDesc(articleVO.getArticleDesc());
					
					searchObj.setTitle(articleVO.getArticleName());
					
					searchObj.setUserId(articleVO.getUSN());
					
					
					
					searchObj.setArticleId(articleVO.getArticleId());
					
					searchObj.setUrl(contextPath
							+ "/review/viewArticleStudent.do?articleName="
							+ articleVO.getArticleId());
					
					searchObj.setPublisher(articleVO.getPublisher());
					map.put("PUBLISHER", articleVO.getPublisher());

					List<String> keywords = contextualDao
							.retriveKeywordsForArticles(articleVO.getArticleName());

					searchObj.setKeywords(keywords);
					map.put("KEYWORD", keywords);

					List<String> authorList = contextualDao
							.retriveAuthorListForArticles(articleVO
									.getArticleName());

					searchObj.setAuthorList(authorList);
					
					searchResultsList.add(searchObj);
					
					//searchHistoryList.add(map);

				}
				
			}

			
			
			
			else if(!searchCriteria.getInterestArea().trim().isEmpty()){
				
				List<String> distinctInterestAreaList = new ArrayList<String>();
			
				distinctInterestAreaList = contextualDao.getAllInterestArea(searchCriteria.getInterestArea().trim());
				
				Map<String, Double> featureVectorList = new HashMap<String, Double>();
				
				for(String area : distinctInterestAreaList){
					int countOfUrls = contextualDao
							.retriveCountOfDocsInWhichArticleIsPresent(area);
					
					int frequency = contextualDao
							.retriveFreqForArticleName(area);
					
					int freq = frequency;

					if (countOfUrls <= 0) {
						countOfUrls = 1;
					}

					double idftTemp = ((double) freq / (double) countOfUrls);

					double idft = 10 * Math.log(idftTemp);

					if (idft < 0) {
						idft = idft * -1;
					}

					double featureVector = idft * freq;
					
					if (featureVector == 0.0) {
						featureVector = freq;
					}
					
					featureVectorList.put(area, featureVector);
					
				}
				
				List<BestFeatureVector> bestFV = new ArrayList<BestFeatureVector>();
				
				for(String area : featureVectorList.keySet()){
					double totalFV = 0;
					double fvdouble = 0.0;
					if (null == featureVectorList.get(area)) {
						fvdouble = 0;
					} else {
							fvdouble = fvdouble + featureVectorList.get(area);
					}	

					totalFV = totalFV + fvdouble;
					
					BestFeatureVector bfv = new BestFeatureVector();
					bfv.setFeatureVector(totalFV);
					bfv.setArticleName(area);
					bestFV.add(bfv);
				}
				
				
				// Now Insert the Best FV
				StatusInfo st = contextualDao.deleteFromBestFV();
				if (!st.isStatus()) {
					return null;
				}

				// Now Insert Into Best FV
				statusInfo = contextualDao.insertBestFV(bestFV);

				if (!statusInfo.isStatus()) {
					return null;
				}

				List<BestFeatureVector> bestFeatureVectorList = contextualDao
						.rateArticles();

				if (null == bestFeatureVectorList) {
					return null;
				}

				for (BestFeatureVector bestFeatureVector : bestFeatureVectorList) {

					SearchObj searchObj = new SearchObj();

					ArticleVO articleVO = contextualDao
							.retriveArticleDetailForArticleName(bestFeatureVector
								.getArticleName());
					searchObj
						.setFeatureVector(bestFeatureVector.getFeatureVector());
					searchObj.setDesc(articleVO.getArticleDesc());
					searchObj.setTitle(articleVO.getArticleName());
					searchObj.setUserId(articleVO.getUSN());
					searchObj.setArticleId(articleVO.getArticleId());
					searchObj.setUrl(contextPath
							+ "/review/viewArticleStudent.do?articleName="
							+ articleVO.getArticleId());
					searchObj.setPublisher(articleVO.getPublisher());

					List<String> keywords = contextualDao
							.retriveKeywordsForArticles(articleVO.getArticleName());

					searchObj.setKeywords(keywords);

					List<String> authorList = contextualDao
							.retriveAuthorListForArticles(articleVO
									.getArticleName());

					searchObj.setAuthorList(authorList);

					searchResultsList.add(searchObj);

				}

			}
			
			
		} catch (Exception e) {
				System.out.println("Exception " + e.getMessage());
				e.printStackTrace();
				statusInfo = new StatusInfo();
				statusInfo.setStatus(false);
				statusInfo.setErrMsg(e.getMessage());

				return statusInfo;
			}
	
		statusInfo = new StatusInfo();
		statusInfo.setStatus(true);
		statusInfo.setObject(searchResultsList);

		return statusInfo;
	}

	private Set<String> obtainArticlesBasedonPubisherList(String pubishers) {
		Set<String> articleListTemp = new HashSet<String>();
		List<String> publisherList = new ArrayList<String>(); 

		if (pubishers != null && !pubishers.isEmpty()) {
			String publisher[] = pubishers.split(",");

			if (null == publisher || publisher.length == 0) {

				publisherList.add(publisher[0]);

			} else {
				publisherList.addAll(Arrays.asList(publisher));
			}

			List<String> articleNames = contextualDao
					.retriveUniqueArticleNamesFromPublisherList(publisherList);

			if (articleNames != null && !articleNames.isEmpty()) {

				articleListTemp.addAll(articleNames);

			}
		}

		return articleListTemp;
	}

	private Set<String> obtainArticlesBasedonKeywordList(String keywords) {
		Set<String> articleListTemp = new HashSet<String>();
		List<String> keywordArrayList = new ArrayList<String>(); 

		if (keywords != null && !keywords.isEmpty()) {
			String keywordList[] = keywords.split(",");

			if (null == keywordList || keywordList.length == 0) {

				keywordArrayList.add(keywordList[0]);

			} else {
				keywordArrayList.addAll(Arrays.asList(keywordList));
			}

			List<String> articleNames = contextualDao
					.retriveUniqueArticleNamesFromKeywordList(keywordArrayList);

			if (articleNames != null && !articleNames.isEmpty()) {

				articleListTemp.addAll(articleNames);

			}
		}

		return articleListTemp;
	}

	private Set<String> obtainArticlesBasedonAuthorList(String authorWithComma) {

		Set<String> articleListTemp = new HashSet<String>();
		List<String> authors = new ArrayList<String>();

		if (authorWithComma != null && !authorWithComma.isEmpty()) {
			String authorList[] = authorWithComma.split(",");

			if (null == authorList || authorList.length == 0) {

				authors.add(authorList[0]);

			} else {
				authors.addAll(Arrays.asList(authorList));
			}

			List<String> articleNames = contextualDao
					.retriveUniqueArticleNamesFromAuthorList(authors);

			if (articleNames != null && !articleNames.isEmpty()) {

				articleListTemp.addAll(articleNames);

			}
		}

		return articleListTemp;
	}

	@Override
	public ArticleVO viewSpecificArticleForArticleId(String articleName) {
		try {

			return contextualDao.retriveArticleDetailForArticleId(articleName);

		} catch (Exception e) {
			System.out.println("Exception e" + e.getMessage());

		}
		return null;
	}
	
	@Override
	public StatusInfo deleteSpecificArticleForArticleId(String articleName) {
		try {

			return contextualDao.deleteArticleDetailForArticleId(articleName);

		} catch (Exception e) {
			System.out.println("Exception e" + e.getMessage());

		}
		return null;
	}
}
