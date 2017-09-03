package com.controller.impl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.constants.ContextualConstantsIF;
import com.controller.inter.ContextualControllerIF;
import com.delegate.inter.ContextualDelegateIF;
import com.model.AJAXResponse;
import com.model.AdjectiveVO;
import com.model.ArticleNamesVO;
import com.model.ArticleTypesVO;
import com.model.ArticleVO;
import com.model.ArticleVOUIModel;
import com.model.CleanArticleUIModel;
import com.model.CompareArticleRVVO;
import com.model.CompareArticleVO;
import com.model.ComparisionInputModel;
import com.model.ComparisionVO;
import com.model.FeatureVO;
import com.model.FreqComputation;
import com.model.GroupingView;
import com.model.KeyPhraseModel;
import com.model.LogLikelihoodVO;
import com.model.Message;
import com.model.PhraseGModel;
import com.model.PhraseVO;
import com.model.RegisterUser;
import com.model.RegisterVerifyMsgs;
import com.model.ArticleModel;
import com.model.SearchCriteria;
import com.model.SearchObj;
import com.model.StatusInfo;
import com.model.StopWordsVO;
import com.model.StructureVO;
import com.model.ThemeModel;
import com.model.TokenVO;
import com.model.WebSiteUrlModel;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;

@Controller
public class ContextualControllerImpl implements ContextualControllerIF {

	@Autowired
	private ContextualDelegateIF contextDelegate;

	public ContextualDelegateIF getContextDelegate() {
		return contextDelegate;
	}

	public void setContextDelegate(ContextualDelegateIF contextDelegate) {
		this.contextDelegate = contextDelegate;
	}

	@Override
	@RequestMapping(value = "/storereview.do", method = { RequestMethod.POST,
			RequestMethod.GET })
	public @ResponseBody AJAXResponse storeArticle(
			@RequestBody ArticleModel reviewModel, HttpServletRequest request) {
		AJAXResponse ajaxRes = null;
		try {
			ajaxRes = new AJAXResponse();
			StatusInfo statusInfo = new StatusInfo();

			HttpSession session = request.getSession(false);
			if (session != null) {

				try {
					String userId = (String) session
							.getAttribute(ContextualConstantsIF.Keys.LOGINID_SESSION);
					reviewModel.setUserId(userId);
				} catch (Exception e) {
					System.out.println("Session Expired");
				}
			}

			statusInfo = contextDelegate.storeReviews(reviewModel);
			if (statusInfo.isStatus()) {
				ajaxRes.setStatus(true);
				ajaxRes.setMessage(ContextualConstantsIF.Message.ARTICLE_STORED_SUCESSFULLY);
				return ajaxRes;
			} else {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message msg = new Message(
						ContextualConstantsIF.Message.ARTICLE_FAILED);
				msg.setErrMessage(ContextualConstantsIF.Message.ARTICLE_FAILED);
				ebErrors.add(msg);
				ajaxRes.setEbErrors(ebErrors);
				return ajaxRes;
			}

		} catch (Exception e) {
			ajaxRes = new AJAXResponse();
			List<Message> ebErrors = new ArrayList<Message>();
			ajaxRes.setStatus(false);
			Message msg = new Message(
					ContextualConstantsIF.Message.MESSAGE_INTERNAL);
			msg.setErrMessage(ContextualConstantsIF.Message.MESSAGE_INTERNAL);
			ebErrors.add(msg);
			ajaxRes.setEbErrors(ebErrors);
			return ajaxRes;
		}
	}

	@Override
	@RequestMapping(value = "/retriveAllArticles.do", method = {
			RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse retriveArticles() {
		AJAXResponse ajaxRes = null;

		try {
			ajaxRes = new AJAXResponse();

			List<ArticleVO> articleList = contextDelegate.retriveArticles();

			if (!articleList.isEmpty() && !(null == articleList)
					&& !(articleList.size() == 0)) {
				ajaxRes.setModel(articleList);
				ajaxRes.setStatus(true);
				ajaxRes.setMessage(ContextualConstantsIF.Message.ARTICLES_RETRIVE_SUCESSFUL);
				return ajaxRes;
			} else {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message msg = new Message(
						ContextualConstantsIF.Message.NO_ARTCILES_FOUND);
				msg.setErrMessage(ContextualConstantsIF.Message.NO_ARTCILES_FOUND);
				ebErrors.add(msg);
				ajaxRes.setEbErrors(ebErrors);
				return ajaxRes;
			}

		} catch (Exception e) {
			ajaxRes = new AJAXResponse();
			List<Message> ebErrors = new ArrayList<Message>();
			ajaxRes.setStatus(false);
			Message msg = new Message(
					ContextualConstantsIF.Message.MESSAGE_INTERNAL);
			msg.setErrMessage(ContextualConstantsIF.Message.MESSAGE_INTERNAL);
			ebErrors.add(msg);
			ajaxRes.setEbErrors(ebErrors);
			return ajaxRes;
		}
	}

	@RequestMapping(value = "/webSubmission.do", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView storeWebSiteInfo(HttpServletRequest request,
			WebSiteUrlModel webSiteModel) {

		ModelAndView mv = null;
		AJAXResponse ajaxRes = null;
		try {
			ajaxRes = validateWebSiteUrl(webSiteModel);

			if (!ajaxRes.isStatus()) {
				return new ModelAndView(
						ContextualConstantsIF.Views.WEBDATA_SUBMIT_INPUT,
						ContextualConstantsIF.Keys.OBJ, ajaxRes);
			}

			HttpSession session = request.getSession(false);
			if (session != null) {

				try {
					String userId = (String) session
							.getAttribute(ContextualConstantsIF.Keys.LOGINID_SESSION);
					webSiteModel.setUserId(userId);
				} catch (Exception e) {
					System.out.println("Session Expired");
				}
			}

			boolean status = contextDelegate.storeWebSiteData(webSiteModel);
			if (!status) {
				List<Message> ebErrors = new ArrayList<Message>();

				Message webSiteUrlMsg = new Message(
						ContextualConstantsIF.Message.MESSAGE_INTERNAL);
				webSiteUrlMsg
						.setErrMessage(ContextualConstantsIF.Message.MESSAGE_INTERNAL);
				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				ajaxRes.setStatus(false);
				return new ModelAndView(
						ContextualConstantsIF.Views.WEBDATA_SUBMIT_INPUT,
						ContextualConstantsIF.Keys.OBJ, ajaxRes);
			}
			if (status) {
				mv = new ModelAndView();
				ajaxRes.setStatus(true);
				List<Message> ebErrors = new ArrayList<Message>();

				Message webSiteUrlMsg = new Message(
						ContextualConstantsIF.Message.MESSAGE_INTERNAL);
				webSiteUrlMsg
						.setErrMessage(ContextualConstantsIF.Message.STORE_WEBDATA_SUCESS);
				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(
						ContextualConstantsIF.Views.VIEW_CUSTOMER_SUCESS_PAGE,
						ContextualConstantsIF.Keys.OBJ, ajaxRes);
			}

		} catch (Exception e) {
			List<Message> ebErrors = new ArrayList<Message>();
			ajaxRes.setStatus(false);
			Message webSiteUrlMsg = new Message(
					ContextualConstantsIF.Message.MESSAGE_INTERNAL);
			webSiteUrlMsg
					.setErrMessage(ContextualConstantsIF.Message.MESSAGE_INTERNAL);

			ebErrors.add(webSiteUrlMsg);
			ajaxRes.setEbErrors(ebErrors);
			return new ModelAndView(
					ContextualConstantsIF.Views.WEBDATA_SUBMIT_INPUT,
					ContextualConstantsIF.Keys.OBJ, ajaxRes);
		}
		return mv;
	}

	private AJAXResponse validateWebSiteUrl(WebSiteUrlModel webSiteModel) {

		AJAXResponse ajax = new AJAXResponse();
		ajax.setStatus(true);
		List<Message> messageList = new ArrayList<Message>();
		if (null == webSiteModel) {

			Message webSiteUrlMsg = new Message(
					ContextualConstantsIF.Message.WEBSITE_URL + "  "
							+ ContextualConstantsIF.Message.NULL_ERROR_MSG);
			webSiteUrlMsg
					.setErrMessage(ContextualConstantsIF.Message.WEBSITE_URL
							+ "  "
							+ ContextualConstantsIF.Message.NULL_ERROR_MSG);
			messageList.add(webSiteUrlMsg);

			ajax.setEbErrors(messageList);
			ajax.setStatus(false);
			return ajax;
		}

		if (doNullCheck(webSiteModel.getWebUrl())) {

			Message webSiteUrlMsg = new Message(
					ContextualConstantsIF.Message.WEBSITE_URL + "  "
							+ ContextualConstantsIF.Message.NULL_ERROR_MSG);
			webSiteUrlMsg
					.setErrMessage(ContextualConstantsIF.Message.WEBSITE_URL
							+ "  "
							+ ContextualConstantsIF.Message.NULL_ERROR_MSG);
			messageList.add(webSiteUrlMsg);
			ajax.setEbErrors(messageList);
			ajax.setStatus(false);
			return ajax;
		}

		if (doNullCheck(webSiteModel.getArticleName())) {

			Message webSiteUrlMsg = new Message(
					ContextualConstantsIF.Message.ARTICLE_NAME_EMPTY);
			webSiteUrlMsg
					.setErrMessage(ContextualConstantsIF.Message.ARTICLE_NAME_EMPTY);
			messageList.add(webSiteUrlMsg);
			ajax.setEbErrors(messageList);
			ajax.setStatus(false);
			return ajax;
		}

		return ajax;
	}

	@Override
	@RequestMapping(value = "/addStopword.do", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView addStopWord(String stopWord) {
		ModelAndView mv = null;
		AJAXResponse ajaxRes = null;
		try {
			ajaxRes = new AJAXResponse();
			if (null == stopWord || stopWord.isEmpty()) {
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message(
						ContextualConstantsIF.Message.EMPTY_STOPWORD);
				webSiteUrlMsg
						.setErrMessage(ContextualConstantsIF.Message.EMPTY_STOPWORD);
				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(
						ContextualConstantsIF.Views.STOPWORD_INPUT,
						ContextualConstantsIF.Keys.OBJ, ajaxRes);

			}
			StatusInfo statusInfo = contextDelegate.addStopword(stopWord);
			if (!statusInfo.isStatus()) {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message(
						ContextualConstantsIF.Message.STOPWORD_ADD_FAILED);
				webSiteUrlMsg
						.setErrMessage(ContextualConstantsIF.Message.STOPWORD_ADD_FAILED);

				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(
						ContextualConstantsIF.Views.STOPWORD_INPUT,
						ContextualConstantsIF.Keys.OBJ, ajaxRes);

			} else {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message(
						ContextualConstantsIF.Message.STOPWORD_ADD_SUCESS);
				webSiteUrlMsg
						.setErrMessage(ContextualConstantsIF.Message.STOPWORD_ADD_SUCESS);

				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(
						ContextualConstantsIF.Views.VIEW_ADMIN_SUCESS_PAGE,
						ContextualConstantsIF.Keys.OBJ, ajaxRes);
			}

		} catch (Exception e) {
			ajaxRes = new AJAXResponse();
			List<Message> ebErrors = new ArrayList<Message>();
			ajaxRes.setStatus(false);
			Message webSiteUrlMsg = new Message(
					ContextualConstantsIF.Message.INTERNAL_ERROR);
			webSiteUrlMsg
					.setErrMessage(ContextualConstantsIF.Message.INTERNAL_ERROR);

			ebErrors.add(webSiteUrlMsg);
			ajaxRes.setEbErrors(ebErrors);
			return new ModelAndView(ContextualConstantsIF.Views.STOPWORD_INPUT,
					ContextualConstantsIF.Keys.OBJ, ajaxRes);
		}
	}

	@Override
	@RequestMapping(value = "/viewStopwords.do", method = { RequestMethod.POST,
			RequestMethod.GET })
	public @ResponseBody AJAXResponse viewStopWords(HttpServletRequest request) {
		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			List<StopWordsVO> keyWordList = contextDelegate.retriveStopWords();
			if (null == keyWordList) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(
						ContextualConstantsIF.Message.EMPTY_STOPWORDS);
				msg.setErrMessage(ContextualConstantsIF.Message.EMPTY_STOPWORDS);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			ajaxResponse.setModel(keyWordList);
			ajaxResponse
					.setMessage(ContextualConstantsIF.Message.STOPWORD_SUCESS);
			return ajaxResponse;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			Message msg = new Message(
					ContextualConstantsIF.Message.INTERNAL_ERROR);
			msg.setErrMessage(ContextualConstantsIF.Message.INTERNAL_ERROR);
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}
	}

	private boolean doNullCheck(String obj) {
		return (null == obj || obj.length() == 0 || obj.isEmpty()) ? true
				: false;
	}

	@Override
	@RequestMapping(value = "/removeStopword.do", method = {
			RequestMethod.POST, RequestMethod.GET })
	public ModelAndView removeStopWord(String stopWord) {
		ModelAndView mv = null;
		AJAXResponse ajaxRes = null;
		try {
			ajaxRes = new AJAXResponse();
			if (null == stopWord || stopWord.isEmpty()) {
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message(
						ContextualConstantsIF.Message.EMPTY_STOPWORD);
				webSiteUrlMsg
						.setErrMessage(ContextualConstantsIF.Message.EMPTY_STOPWORD);
				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(
						ContextualConstantsIF.Views.REMOVESTOPWORD_INPUT,
						ContextualConstantsIF.Keys.OBJ, ajaxRes);

			}
			StatusInfo statusInfo = contextDelegate.removeStopword(stopWord);
			if (!statusInfo.isStatus()) {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message(
						ContextualConstantsIF.Message.EMPTY_STOPWORD);
				webSiteUrlMsg
						.setErrMessage(ContextualConstantsIF.Message.STOPWORD_REMOVE_FAILED);

				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(
						ContextualConstantsIF.Views.REMOVESTOPWORD_INPUT,
						ContextualConstantsIF.Keys.OBJ, ajaxRes);

			} else {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message(
						ContextualConstantsIF.Message.STOPWORD_REMOVE_SUCESS);
				webSiteUrlMsg
						.setErrMessage(ContextualConstantsIF.Message.STOPWORD_REMOVE_SUCESS);

				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(
						ContextualConstantsIF.Views.VIEW_ADMIN_SUCESS_PAGE,
						ContextualConstantsIF.Keys.OBJ, ajaxRes);
			}

		} catch (Exception e) {
			ajaxRes = new AJAXResponse();
			List<Message> ebErrors = new ArrayList<Message>();
			ajaxRes.setStatus(false);
			Message webSiteUrlMsg = new Message(
					ContextualConstantsIF.Message.STOPWORD_REMOVE_SUCESS);
			webSiteUrlMsg
					.setErrMessage(ContextualConstantsIF.Message.INTERNAL_ERROR);

			ebErrors.add(webSiteUrlMsg);
			ajaxRes.setEbErrors(ebErrors);
			return new ModelAndView(
					ContextualConstantsIF.Views.REMOVESTOPWORD_INPUT,
					ContextualConstantsIF.Keys.OBJ, ajaxRes);
		}
	}

	@RequestMapping(value = "/cleandata.do", method = { RequestMethod.POST,
			RequestMethod.GET })
	@Override
	public ModelAndView doDataCleaning(HttpServletRequest request) {
		try {

			StatusInfo statusInfo = contextDelegate.doDataCleaning();

			if (!statusInfo.isStatus()) {
				AJAXResponse ajax = new AJAXResponse();
				ajax.setStatus(false);
				Message msg = new Message(statusInfo.getErrMsg());
				msg.setErrMessage(statusInfo.getErrMsg());
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajax.setEbErrors(ebErrors);
				return new ModelAndView(
						ContextualConstantsIF.Views.VIEW_ADMIN_ERROR_PAGE,
						ContextualConstantsIF.Keys.OBJ, ajax);
			}
			AJAXResponse ajax = new AJAXResponse();
			ajax.setStatus(true);
			ajax.setMessage(ContextualConstantsIF.Message.CLEANREVIEWS_SUCESS);

			return new ModelAndView(
					ContextualConstantsIF.Views.VIEW_ADMIN_SUCESS_PAGE,
					ContextualConstantsIF.Keys.OBJ, ajax);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			AJAXResponse ajax = new AJAXResponse();
			ajax.setStatus(false);
			Message msg = new Message(
					ContextualConstantsIF.Message.INTERNAL_ERROR);
			msg.setErrMessage(ContextualConstantsIF.Message.INTERNAL_ERROR);
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajax.setEbErrors(ebErrors);
			return new ModelAndView(
					ContextualConstantsIF.Views.VIEW_ADMIN_ERROR_PAGE,
					ContextualConstantsIF.Keys.OBJ, ajax);
		}
	}

	@Override
	@RequestMapping(value = "/viewCleanReviews.do", method = {
			RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse viewCleanReviews(
			HttpServletRequest request) {
		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			List<CleanArticleUIModel> reviewList = contextDelegate
					.retriveCleanReviewList();
			if (null == reviewList) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(
						ContextualConstantsIF.Message.EMPTY_REVIEWSLIST);
				msg.setErrMessage(ContextualConstantsIF.Message.EMPTY_REVIEWSLIST);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			ajaxResponse.setModel(reviewList);
			ajaxResponse
					.setMessage(ContextualConstantsIF.Message.REVIEWS_FETCH_SUCESS);
			return ajaxResponse;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			Message msg = new Message(
					ContextualConstantsIF.Message.INTERNAL_ERROR);
			msg.setErrMessage(ContextualConstantsIF.Message.INTERNAL_ERROR);
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}
	}

	@Override
	@RequestMapping(value = "/doTokens.do", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView doTokens(HttpServletRequest request) {
		try {

			StatusInfo statusInfo = contextDelegate.doTokens();

			if (!statusInfo.isStatus()) {
				AJAXResponse ajax = new AJAXResponse();
				ajax.setStatus(false);
				Message msg = new Message(statusInfo.getErrMsg());
				msg.setErrMessage(statusInfo.getErrMsg());
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajax.setEbErrors(ebErrors);
				return new ModelAndView(
						ContextualConstantsIF.Views.VIEW_ADMIN_ERROR_PAGE,
						ContextualConstantsIF.Keys.OBJ, ajax);
			}
			AJAXResponse ajax = new AJAXResponse();
			ajax.setStatus(true);
			ajax.setMessage(ContextualConstantsIF.Message.TOKENS_SUCESS);

			return new ModelAndView(
					ContextualConstantsIF.Views.VIEW_ADMIN_SUCESS_PAGE,
					ContextualConstantsIF.Keys.OBJ, ajax);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			AJAXResponse ajax = new AJAXResponse();
			ajax.setStatus(false);
			Message msg = new Message(
					ContextualConstantsIF.Message.INTERNAL_ERROR);
			msg.setErrMessage(ContextualConstantsIF.Message.INTERNAL_ERROR);
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajax.setEbErrors(ebErrors);
			return new ModelAndView(
					ContextualConstantsIF.Views.VIEW_ADMIN_ERROR_PAGE,
					ContextualConstantsIF.Keys.OBJ, ajax);
		}
	}

	@Override
	@RequestMapping(value = "/viewTokens.do", method = { RequestMethod.POST,
			RequestMethod.GET })
	public @ResponseBody AJAXResponse viewTokens(HttpServletRequest request) {
		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			List<TokenVO> tokenList = contextDelegate.retriveTokenList();
			if (null == tokenList) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(
						ContextualConstantsIF.Message.EMPTY_TOKENS);
				msg.setErrMessage(ContextualConstantsIF.Message.EMPTY_TOKENS);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			ajaxResponse.setModel(tokenList);
			ajaxResponse
					.setMessage(ContextualConstantsIF.Message.TOKENRETRIVAL_SUCESS);
			return ajaxResponse;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			Message msg = new Message(
					ContextualConstantsIF.Message.INTERNAL_ERROR);
			msg.setErrMessage(ContextualConstantsIF.Message.INTERNAL_ERROR);
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}
	}

	@Override
	@RequestMapping(value = "/viewGroupData.do", method = { RequestMethod.POST,
			RequestMethod.GET })
	public @ResponseBody AJAXResponse viewGrouping(HttpServletRequest request) {
		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			List<GroupingView> tokenList = contextDelegate.viewGrouping();
			if (null == tokenList) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(
						ContextualConstantsIF.Message.EMPTY_TOKENS);
				msg.setErrMessage(ContextualConstantsIF.Message.EMPTY_TOKENS);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			ajaxResponse.setModel(tokenList);
			ajaxResponse
					.setMessage(ContextualConstantsIF.Message.TOKENRETRIVAL_SUCESS);
			return ajaxResponse;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			Message msg = new Message(
					ContextualConstantsIF.Message.INTERNAL_ERROR);
			msg.setErrMessage(ContextualConstantsIF.Message.INTERNAL_ERROR);
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}
	}

	@Override
	@RequestMapping(value = "/addKeyPhrase.do", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView addKeyPhrase(@RequestParam String keyphrase,
			@RequestParam String type) {
		ModelAndView mv = null;
		AJAXResponse ajaxRes = null;
		try {
			ajaxRes = new AJAXResponse();
			if (null == keyphrase || keyphrase.isEmpty()) {
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message(
						ContextualConstantsIF.Message.EMPTY_KEYPHRASE);
				webSiteUrlMsg
						.setErrMessage(ContextualConstantsIF.Message.EMPTY_KEYPHRASE);
				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(
						ContextualConstantsIF.Views.KEYPHRASEADD_INPUT,
						ContextualConstantsIF.Keys.OBJ, ajaxRes);

			}
			StatusInfo statusInfo = contextDelegate.addKeyPhrase(keyphrase,
					type);
			if (!statusInfo.isStatus()) {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message(
						ContextualConstantsIF.Message.KEYPHRASE_ADD_FAILED);
				webSiteUrlMsg
						.setErrMessage(ContextualConstantsIF.Message.KEYPHRASE_ADD_FAILED);

				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(
						ContextualConstantsIF.Views.KEYPHRASEADD_INPUT,
						ContextualConstantsIF.Keys.OBJ, ajaxRes);

			} else {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message(
						ContextualConstantsIF.Message.KEYPHRASE_ADD_SUCESS);
				webSiteUrlMsg
						.setErrMessage(ContextualConstantsIF.Message.KEYPHRASE_ADD_SUCESS);

				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(
						ContextualConstantsIF.Views.VIEW_ADMIN_SUCESS_PAGE,
						ContextualConstantsIF.Keys.OBJ, ajaxRes);
			}

		} catch (Exception e) {
			ajaxRes = new AJAXResponse();
			List<Message> ebErrors = new ArrayList<Message>();
			ajaxRes.setStatus(false);
			Message webSiteUrlMsg = new Message(
					ContextualConstantsIF.Message.INTERNAL_ERROR);
			webSiteUrlMsg
					.setErrMessage(ContextualConstantsIF.Message.INTERNAL_ERROR);

			ebErrors.add(webSiteUrlMsg);
			ajaxRes.setEbErrors(ebErrors);
			return new ModelAndView(ContextualConstantsIF.Views.STOPWORD_INPUT,
					ContextualConstantsIF.Keys.OBJ, ajaxRes);
		}
	}

	@Override
	@RequestMapping(value = "/viewKeyPhrases.do", method = {
			RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse viewKeyPhrases(HttpServletRequest request) {
		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			List<KeyPhraseModel> keyWordList = contextDelegate
					.retriveKeyPhrases();
			if (null == keyWordList) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(
						ContextualConstantsIF.Message.EMPTY_KEYPHRASE);
				msg.setErrMessage(ContextualConstantsIF.Message.EMPTY_KEYPHRASE);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			ajaxResponse.setModel(keyWordList);
			ajaxResponse
					.setMessage(ContextualConstantsIF.Message.KEYPHRASE_RETRIVE_SUCESS);
			return ajaxResponse;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			Message msg = new Message(
					ContextualConstantsIF.Message.INTERNAL_ERROR);
			msg.setErrMessage(ContextualConstantsIF.Message.INTERNAL_ERROR);
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}
	}

	@Override
	@RequestMapping(value = "/removeKeyPhrase.do", method = {
			RequestMethod.POST, RequestMethod.GET })
	public ModelAndView removeKeyPhrase(@RequestParam String keyphrase,
			@RequestParam String type) {
		ModelAndView mv = null;
		AJAXResponse ajaxRes = null;
		try {
			ajaxRes = new AJAXResponse();
			if (null == keyphrase || keyphrase.isEmpty()) {
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message(
						ContextualConstantsIF.Message.EMPTY_KEYPHRASE);
				webSiteUrlMsg
						.setErrMessage(ContextualConstantsIF.Message.EMPTY_KEYPHRASE);
				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(
						ContextualConstantsIF.Views.REMOVE_KEYPHRASE_INPUT,
						ContextualConstantsIF.Keys.OBJ, ajaxRes);

			}
			StatusInfo statusInfo = contextDelegate.removeKeyPhrase(keyphrase,
					type);
			if (!statusInfo.isStatus()) {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message(
						ContextualConstantsIF.Message.KEYPHRASE_REMOVE_FAILED);
				webSiteUrlMsg
						.setErrMessage(ContextualConstantsIF.Message.KEYPHRASE_REMOVE_FAILED);

				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(
						ContextualConstantsIF.Views.REMOVE_KEYPHRASE_INPUT,
						ContextualConstantsIF.Keys.OBJ, ajaxRes);

			} else {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(true);
				Message webSiteUrlMsg = new Message(
						ContextualConstantsIF.Message.KEYPHRASE_REMOVE_SUCESS);
				webSiteUrlMsg
						.setErrMessage(ContextualConstantsIF.Message.KEYPHRASE_REMOVE_SUCESS);

				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(
						ContextualConstantsIF.Views.VIEW_ADMIN_SUCESS_PAGE,
						ContextualConstantsIF.Keys.OBJ, ajaxRes);
			}

		} catch (Exception e) {
			ajaxRes = new AJAXResponse();
			List<Message> ebErrors = new ArrayList<Message>();
			ajaxRes.setStatus(false);
			Message webSiteUrlMsg = new Message(
					ContextualConstantsIF.Message.INTERNAL_ERROR);
			webSiteUrlMsg
					.setErrMessage(ContextualConstantsIF.Message.INTERNAL_ERROR);

			ebErrors.add(webSiteUrlMsg);
			ajaxRes.setEbErrors(ebErrors);
			return new ModelAndView(
					ContextualConstantsIF.Views.REMOVE_KEYPHRASE_INPUT,
					ContextualConstantsIF.Keys.OBJ, ajaxRes);
		}
	}

	@Override
	@RequestMapping(value = "/addPhraseG.do", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView addPhraseG(@ModelAttribute PhraseGModel phraseGModel) {
		ModelAndView mv = null;
		AJAXResponse ajaxRes = null;
		try {
			ajaxRes = new AJAXResponse();
			if (null == phraseGModel.getPhraseG()
					|| phraseGModel.getPhraseG().isEmpty()) {
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message(
						ContextualConstantsIF.Message.EMPTY_PHRASEG);
				webSiteUrlMsg
						.setErrMessage(ContextualConstantsIF.Message.EMPTY_PHRASEG);
				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(
						ContextualConstantsIF.Views.PHRASEG_ADD_INPUT,
						ContextualConstantsIF.Keys.OBJ, ajaxRes);

			}

			if (phraseGModel.getNoType().equals(phraseGModel.getType())) {

				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message(
						ContextualConstantsIF.Message.NOTYPE_TYPE_CANNOT_BE_SAME);
				webSiteUrlMsg
						.setErrMessage(ContextualConstantsIF.Message.NOTYPE_TYPE_CANNOT_BE_SAME);
				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(
						ContextualConstantsIF.Views.PHRASEG_ADD_INPUT,
						ContextualConstantsIF.Keys.OBJ, ajaxRes);
			}

			StatusInfo statusInfo = contextDelegate.addPhraseG(phraseGModel);
			if (!statusInfo.isStatus()) {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message(
						ContextualConstantsIF.Message.PHRASEG_ADD_FAILED);
				webSiteUrlMsg
						.setErrMessage(ContextualConstantsIF.Message.PHRASEG_ADD_FAILED);

				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(
						ContextualConstantsIF.Views.PHRASEG_ADD_INPUT,
						ContextualConstantsIF.Keys.OBJ, ajaxRes);

			} else {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message(
						ContextualConstantsIF.Message.PHRASEG_ADD_SUCESS);
				webSiteUrlMsg
						.setErrMessage(ContextualConstantsIF.Message.PHRASEG_ADD_SUCESS);

				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(
						ContextualConstantsIF.Views.VIEW_ADMIN_SUCESS_PAGE,
						ContextualConstantsIF.Keys.OBJ, ajaxRes);
			}

		} catch (Exception e) {
			ajaxRes = new AJAXResponse();
			List<Message> ebErrors = new ArrayList<Message>();
			ajaxRes.setStatus(false);
			Message webSiteUrlMsg = new Message(
					ContextualConstantsIF.Message.INTERNAL_ERROR);
			webSiteUrlMsg
					.setErrMessage(ContextualConstantsIF.Message.INTERNAL_ERROR);

			ebErrors.add(webSiteUrlMsg);
			ajaxRes.setEbErrors(ebErrors);
			return new ModelAndView(
					ContextualConstantsIF.Views.PHRASEG_ADD_INPUT,
					ContextualConstantsIF.Keys.OBJ, ajaxRes);
		}
	}

	@Override
	@RequestMapping(value = "/removePhraseG.do", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView removePhraseG(@ModelAttribute PhraseGModel phraseGModel) {
		ModelAndView mv = null;
		AJAXResponse ajaxRes = null;
		try {
			ajaxRes = new AJAXResponse();
			if (null == phraseGModel.getPhraseG()
					|| phraseGModel.getPhraseG().isEmpty()) {
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message(
						ContextualConstantsIF.Message.EMPTY_PHRASEG);
				webSiteUrlMsg
						.setErrMessage(ContextualConstantsIF.Message.EMPTY_PHRASEG);
				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(
						ContextualConstantsIF.Views.REMOVE_PHRASEG_INPUT,
						ContextualConstantsIF.Keys.OBJ, ajaxRes);

			}
			StatusInfo statusInfo = contextDelegate.removePhraseG(phraseGModel);
			if (!statusInfo.isStatus()) {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message(
						ContextualConstantsIF.Message.PHRASEG_REMOVE_FAILED);
				webSiteUrlMsg
						.setErrMessage(ContextualConstantsIF.Message.PHRASEG_REMOVE_FAILED);

				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(
						ContextualConstantsIF.Views.REMOVE_PHRASEG_INPUT,
						ContextualConstantsIF.Keys.OBJ, ajaxRes);

			} else {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(true);
				Message webSiteUrlMsg = new Message(
						ContextualConstantsIF.Message.PHRASEG_REMOVE_SUCESS);
				webSiteUrlMsg
						.setErrMessage(ContextualConstantsIF.Message.PHRASEG_REMOVE_SUCESS);

				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(
						ContextualConstantsIF.Views.VIEW_ADMIN_SUCESS_PAGE,
						ContextualConstantsIF.Keys.OBJ, ajaxRes);
			}

		} catch (Exception e) {
			ajaxRes = new AJAXResponse();
			List<Message> ebErrors = new ArrayList<Message>();
			ajaxRes.setStatus(false);
			Message webSiteUrlMsg = new Message(
					ContextualConstantsIF.Message.INTERNAL_ERROR);
			webSiteUrlMsg
					.setErrMessage(ContextualConstantsIF.Message.INTERNAL_ERROR);

			ebErrors.add(webSiteUrlMsg);
			ajaxRes.setEbErrors(ebErrors);
			return new ModelAndView(
					ContextualConstantsIF.Views.REMOVE_PHRASEG_INPUT,
					ContextualConstantsIF.Keys.OBJ, ajaxRes);
		}
	}

	@Override
	@RequestMapping(value = "/viewPhraseG.do", method = { RequestMethod.POST,
			RequestMethod.GET })
	public @ResponseBody AJAXResponse viewPhraseG(HttpServletRequest request) {
		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			List<PhraseGModel> keyWordList = contextDelegate.retrivePhraseG();
			if (null == keyWordList) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(
						ContextualConstantsIF.Message.EMPTY_PHRASEG);
				msg.setErrMessage(ContextualConstantsIF.Message.EMPTY_PHRASEG);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			ajaxResponse.setModel(keyWordList);
			ajaxResponse
					.setMessage(ContextualConstantsIF.Message.PHRASEG_RETRIVE_SUCESS);
			return ajaxResponse;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			Message msg = new Message(
					ContextualConstantsIF.Message.INTERNAL_ERROR);
			msg.setErrMessage(ContextualConstantsIF.Message.INTERNAL_ERROR);
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}
	}

	@Override
	@RequestMapping(value = "/genPhrases.do", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView generatePhrases() {
		ModelAndView mv = null;
		AJAXResponse ajaxRes = null;
		try {
			ajaxRes = new AJAXResponse();
			StatusInfo statusInfo = contextDelegate.genPhrases();
			if (!statusInfo.isStatus()) {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message(
						ContextualConstantsIF.Message.PHRASES_GENERATION_FAILED);
				webSiteUrlMsg
						.setErrMessage(ContextualConstantsIF.Message.PHRASES_GENERATION_FAILED);
				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(
						ContextualConstantsIF.Views.FAILURE_PAGE,
						ContextualConstantsIF.Keys.OBJ, ajaxRes);

			} else {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message(
						ContextualConstantsIF.Message.PHRASES_GEN_SUCESS);
				webSiteUrlMsg
						.setErrMessage(ContextualConstantsIF.Message.PHRASES_GEN_SUCESS);

				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(
						ContextualConstantsIF.Views.VIEW_ADMIN_SUCESS_PAGE,
						ContextualConstantsIF.Keys.OBJ, ajaxRes);
			}

		} catch (Exception e) {
			ajaxRes = new AJAXResponse();
			List<Message> ebErrors = new ArrayList<Message>();
			ajaxRes.setStatus(false);
			Message webSiteUrlMsg = new Message(
					ContextualConstantsIF.Message.INTERNAL_ERROR);
			webSiteUrlMsg
					.setErrMessage(ContextualConstantsIF.Message.INTERNAL_ERROR);

			ebErrors.add(webSiteUrlMsg);
			ajaxRes.setEbErrors(ebErrors);
			return new ModelAndView(ContextualConstantsIF.Views.FAILURE_PAGE,
					ContextualConstantsIF.Keys.OBJ, ajaxRes);
		}
	}

	@Override
	@RequestMapping(value = "/viewPhrases.do", method = { RequestMethod.POST,
			RequestMethod.GET })
	public @ResponseBody AJAXResponse viewPhrases(HttpServletRequest request) {
		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			List<PhraseVO> tokenList = contextDelegate.retrivePhrasesList();
			if (null == tokenList) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(
						ContextualConstantsIF.Message.EMPTY_PHRASES);
				msg.setErrMessage(ContextualConstantsIF.Message.EMPTY_PHRASES);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			ajaxResponse.setModel(tokenList);
			ajaxResponse
					.setMessage(ContextualConstantsIF.Message.PHRASES_RETRIVAL_SUCESS);
			return ajaxResponse;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			Message msg = new Message(
					ContextualConstantsIF.Message.INTERNAL_ERROR);
			msg.setErrMessage(ContextualConstantsIF.Message.INTERNAL_ERROR);
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}
	}

	@Override
	@RequestMapping(value = "/genAdjective.do", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView generateAdjectives() {
		ModelAndView mv = null;
		AJAXResponse ajaxRes = null;
		try {
			ajaxRes = new AJAXResponse();
			StatusInfo statusInfo = contextDelegate.genAdjectives();
			if (!statusInfo.isStatus()) {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message(
						ContextualConstantsIF.Message.ADJECTIVE_GENERATION_FAILED);
				webSiteUrlMsg
						.setErrMessage(ContextualConstantsIF.Message.ADJECTIVE_GENERATION_FAILED);
				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(
						ContextualConstantsIF.Views.FAILURE_PAGE,
						ContextualConstantsIF.Keys.OBJ, ajaxRes);

			} else {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message(
						ContextualConstantsIF.Message.ADJECTIVE_GEN_SUCESS);
				webSiteUrlMsg
						.setErrMessage(ContextualConstantsIF.Message.ADJECTIVE_GEN_SUCESS);

				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(
						ContextualConstantsIF.Views.VIEW_ADMIN_SUCESS_PAGE,
						ContextualConstantsIF.Keys.OBJ, ajaxRes);
			}

		} catch (Exception e) {
			ajaxRes = new AJAXResponse();
			List<Message> ebErrors = new ArrayList<Message>();
			ajaxRes.setStatus(false);
			Message webSiteUrlMsg = new Message(
					ContextualConstantsIF.Message.INTERNAL_ERROR);
			webSiteUrlMsg
					.setErrMessage(ContextualConstantsIF.Message.INTERNAL_ERROR);

			ebErrors.add(webSiteUrlMsg);
			ajaxRes.setEbErrors(ebErrors);
			return new ModelAndView(ContextualConstantsIF.Views.FAILURE_PAGE,
					ContextualConstantsIF.Keys.OBJ, ajaxRes);
		}
	}

	@Override
	@RequestMapping(value = "/viewAdjectives.do", method = {
			RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse viewAdjectives(HttpServletRequest request) {
		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			List<AdjectiveVO> adjectiveList = contextDelegate
					.retriveAdjectives();
			if (null == adjectiveList) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(
						ContextualConstantsIF.Message.EMPTY_STOPWORDS);
				msg.setErrMessage(ContextualConstantsIF.Message.EMPTY_STOPWORDS);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			ajaxResponse.setModel(adjectiveList);
			ajaxResponse
					.setMessage(ContextualConstantsIF.Message.STOPWORD_SUCESS);
			return ajaxResponse;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			Message msg = new Message(
					ContextualConstantsIF.Message.INTERNAL_ERROR);
			msg.setErrMessage(ContextualConstantsIF.Message.INTERNAL_ERROR);
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}
	}

	@Override
	@RequestMapping(value = "/genStructure1.do", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView generateStructure1() {
		AJAXResponse ajaxRes = null;
		try {
			ajaxRes = new AJAXResponse();
			StatusInfo statusInfo = contextDelegate.genStructure1();
			if (!statusInfo.isStatus()) {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message(
						ContextualConstantsIF.Message.STRUCTURE1_GENERATION_FAILED);
				webSiteUrlMsg
						.setErrMessage(ContextualConstantsIF.Message.STRUCTURE1_GENERATION_FAILED);
				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(
						ContextualConstantsIF.Views.FAILURE_PAGE,
						ContextualConstantsIF.Keys.OBJ, ajaxRes);
			} else {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message(
						ContextualConstantsIF.Message.STRUCTURE1_GEN_SUCESS);
				webSiteUrlMsg
						.setErrMessage(ContextualConstantsIF.Message.STRUCTURE1_GEN_SUCESS);

				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(
						ContextualConstantsIF.Views.VIEW_ADMIN_SUCESS_PAGE,
						ContextualConstantsIF.Keys.OBJ, ajaxRes);
			}

		} catch (Exception e) {
			ajaxRes = new AJAXResponse();
			List<Message> ebErrors = new ArrayList<Message>();
			ajaxRes.setStatus(false);
			Message webSiteUrlMsg = new Message(
					ContextualConstantsIF.Message.INTERNAL_ERROR);
			webSiteUrlMsg
					.setErrMessage(ContextualConstantsIF.Message.INTERNAL_ERROR);

			ebErrors.add(webSiteUrlMsg);
			ajaxRes.setEbErrors(ebErrors);
			return new ModelAndView(ContextualConstantsIF.Views.FAILURE_PAGE,
					ContextualConstantsIF.Keys.OBJ, ajaxRes);
		}
	}

	@Override
	@RequestMapping(value = "/viewStructure1.do", method = {
			RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse viewStrcuture1(HttpServletRequest request) {
		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			List<StructureVO> strutureVOList = contextDelegate
					.retriveStructure1List();
			if (null == strutureVOList) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(
						ContextualConstantsIF.Message.EMPTY_STRUCTURE1);
				msg.setErrMessage(ContextualConstantsIF.Message.EMPTY_STRUCTURE1);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			ajaxResponse.setModel(strutureVOList);
			ajaxResponse
					.setMessage(ContextualConstantsIF.Message.STRUCTURE1_RETRIVAL_SUCESS);
			return ajaxResponse;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			Message msg = new Message(
					ContextualConstantsIF.Message.INTERNAL_ERROR);
			msg.setErrMessage(ContextualConstantsIF.Message.INTERNAL_ERROR);
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}
	}

	@Override
	@RequestMapping(value = "/viewStructure2.do", method = {
			RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse viewStrcuture2(HttpServletRequest request) {
		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			List<StructureVO> strutureVOList = contextDelegate
					.retriveStructure2List();
			if (null == strutureVOList) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(
						ContextualConstantsIF.Message.EMPTY_STRUCTURE2);
				msg.setErrMessage(ContextualConstantsIF.Message.EMPTY_STRUCTURE2);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			ajaxResponse.setModel(strutureVOList);
			ajaxResponse
					.setMessage(ContextualConstantsIF.Message.STRUCTURE2_RETRIVAL_SUCESS);
			return ajaxResponse;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			Message msg = new Message(
					ContextualConstantsIF.Message.INTERNAL_ERROR);
			msg.setErrMessage(ContextualConstantsIF.Message.INTERNAL_ERROR);
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}
	}

	@Override
	@RequestMapping(value = "/viewStructure3.do", method = {
			RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse viewStrcuture3(HttpServletRequest request) {
		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			List<StructureVO> strutureVOList = contextDelegate
					.retriveStructure3List();
			if (null == strutureVOList) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(
						ContextualConstantsIF.Message.EMPTY_STRUCTURE1);
				msg.setErrMessage(ContextualConstantsIF.Message.EMPTY_STRUCTURE1);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			ajaxResponse.setModel(strutureVOList);
			ajaxResponse
					.setMessage(ContextualConstantsIF.Message.STRUCTURE1_RETRIVAL_SUCESS);
			return ajaxResponse;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			Message msg = new Message(
					ContextualConstantsIF.Message.INTERNAL_ERROR);
			msg.setErrMessage(ContextualConstantsIF.Message.INTERNAL_ERROR);
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}
	}

	@Override
	@RequestMapping(value = "/genStructure2.do", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView generateStructure2() {
		AJAXResponse ajaxRes = null;
		try {
			ajaxRes = new AJAXResponse();
			StatusInfo statusInfo = contextDelegate.genStructure2();
			if (!statusInfo.isStatus()) {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message(
						ContextualConstantsIF.Message.STRUCTURE2_GENERATION_FAILED);
				webSiteUrlMsg
						.setErrMessage(ContextualConstantsIF.Message.STRUCTURE2_GENERATION_FAILED);
				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(
						ContextualConstantsIF.Views.FAILURE_PAGE,
						ContextualConstantsIF.Keys.OBJ, ajaxRes);
			} else {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message(
						ContextualConstantsIF.Message.STRUCTURE2_GEN_SUCESS);
				webSiteUrlMsg
						.setErrMessage(ContextualConstantsIF.Message.STRUCTURE2_GEN_SUCESS);

				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(
						ContextualConstantsIF.Views.VIEW_ADMIN_SUCESS_PAGE,
						ContextualConstantsIF.Keys.OBJ, ajaxRes);
			}

		} catch (Exception e) {
			ajaxRes = new AJAXResponse();
			List<Message> ebErrors = new ArrayList<Message>();
			ajaxRes.setStatus(false);
			Message webSiteUrlMsg = new Message(
					ContextualConstantsIF.Message.INTERNAL_ERROR);
			webSiteUrlMsg
					.setErrMessage(ContextualConstantsIF.Message.INTERNAL_ERROR);

			ebErrors.add(webSiteUrlMsg);
			ajaxRes.setEbErrors(ebErrors);
			return new ModelAndView(ContextualConstantsIF.Views.FAILURE_PAGE,
					ContextualConstantsIF.Keys.OBJ, ajaxRes);
		}
	}

	@Override
	@RequestMapping(value = "/genStructure3.do", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView generateStructure3() {
		AJAXResponse ajaxRes = null;
		try {
			ajaxRes = new AJAXResponse();
			StatusInfo statusInfo = contextDelegate.genStructure3();
			if (!statusInfo.isStatus()) {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message(
						ContextualConstantsIF.Message.STRUCTURE3_GENERATION_FAILED);
				webSiteUrlMsg
						.setErrMessage(ContextualConstantsIF.Message.STRUCTURE3_GENERATION_FAILED);
				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(
						ContextualConstantsIF.Views.FAILURE_PAGE,
						ContextualConstantsIF.Keys.OBJ, ajaxRes);
			} else {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message(
						ContextualConstantsIF.Message.STRUCTURE3_GEN_SUCESS);
				webSiteUrlMsg
						.setErrMessage(ContextualConstantsIF.Message.STRUCTURE3_GEN_SUCESS);

				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(
						ContextualConstantsIF.Views.VIEW_ADMIN_SUCESS_PAGE,
						ContextualConstantsIF.Keys.OBJ, ajaxRes);
			}

		} catch (Exception e) {
			ajaxRes = new AJAXResponse();
			List<Message> ebErrors = new ArrayList<Message>();
			ajaxRes.setStatus(false);
			Message webSiteUrlMsg = new Message(
					ContextualConstantsIF.Message.INTERNAL_ERROR);
			webSiteUrlMsg
					.setErrMessage(ContextualConstantsIF.Message.INTERNAL_ERROR);

			ebErrors.add(webSiteUrlMsg);
			ajaxRes.setEbErrors(ebErrors);
			return new ModelAndView(ContextualConstantsIF.Views.FAILURE_PAGE,
					ContextualConstantsIF.Keys.OBJ, ajaxRes);
		}
	}

	@Override
	@RequestMapping(value = "/genFreq.do", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView generateFreq() {
		AJAXResponse ajaxRes = null;
		try {
			ajaxRes = new AJAXResponse();
			StatusInfo statusInfo = contextDelegate.generateFreq();
			if (!statusInfo.isStatus()) {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message(statusInfo.getErrMsg());
				webSiteUrlMsg.setErrMessage(statusInfo.getErrMsg());
				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(
						ContextualConstantsIF.Views.FAILURE_PAGE,
						ContextualConstantsIF.Keys.OBJ, ajaxRes);
			} else {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message(
						ContextualConstantsIF.Message.FREQ_GEN_SUCESS);
				webSiteUrlMsg
						.setErrMessage(ContextualConstantsIF.Message.FREQ_GEN_SUCESS);

				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(
						ContextualConstantsIF.Views.VIEW_ADMIN_SUCESS_PAGE,
						ContextualConstantsIF.Keys.OBJ, ajaxRes);
			}

		} catch (Exception e) {
			ajaxRes = new AJAXResponse();
			List<Message> ebErrors = new ArrayList<Message>();
			ajaxRes.setStatus(false);
			Message webSiteUrlMsg = new Message(
					ContextualConstantsIF.Message.INTERNAL_ERROR);
			webSiteUrlMsg
					.setErrMessage(ContextualConstantsIF.Message.INTERNAL_ERROR);

			ebErrors.add(webSiteUrlMsg);
			ajaxRes.setEbErrors(ebErrors);
			return new ModelAndView(ContextualConstantsIF.Views.FAILURE_PAGE,
					ContextualConstantsIF.Keys.OBJ, ajaxRes);
		}
	}

	@Override
	@RequestMapping(value = "/viewFreq.do", method = { RequestMethod.POST,
			RequestMethod.GET })
	public @ResponseBody AJAXResponse viewFreq(HttpServletRequest request) {
		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			List<FreqComputation> strutureVOList = contextDelegate.viewFreq();
			if (null == strutureVOList) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(
						ContextualConstantsIF.Message.EMPTY_STRUCTURE1);
				msg.setErrMessage(ContextualConstantsIF.Message.EMPTY_STRUCTURE1);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			ajaxResponse.setModel(strutureVOList);
			ajaxResponse
					.setMessage(ContextualConstantsIF.Message.STRUCTURE1_RETRIVAL_SUCESS);
			return ajaxResponse;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			Message msg = new Message(
					ContextualConstantsIF.Message.INTERNAL_ERROR);
			msg.setErrMessage(ContextualConstantsIF.Message.INTERNAL_ERROR);
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}
	}

	@Override
	@RequestMapping(value = "/retriveAllArticleNames.do", method = {
			RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse retriveAllArticleNames(
			HttpServletRequest request) {
		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			List<ArticleNamesVO> articleNames = contextDelegate
					.retriveAllArticleNames();
			if (null == articleNames) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(
						ContextualConstantsIF.Message.EMPTY_ARTICLENAMES);
				msg.setErrMessage(ContextualConstantsIF.Message.EMPTY_ARTICLENAMES);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			ajaxResponse.setModel(articleNames);
			ajaxResponse
					.setMessage(ContextualConstantsIF.Message.ARTICLENAMES_SUCESS);
			return ajaxResponse;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			Message msg = new Message(
					ContextualConstantsIF.Message.INTERNAL_ERROR);
			msg.setErrMessage(ContextualConstantsIF.Message.INTERNAL_ERROR);
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}
	}

	// /typeStore.do

	@Override
	@RequestMapping(value = "/typeStore.do", method = { RequestMethod.POST,
			RequestMethod.GET })
	public @ResponseBody AJAXResponse retriveTypes(HttpServletRequest request) {
		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			List<ArticleTypesVO> articleNames = contextDelegate.retriveTypes();
			if (null == articleNames) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(
						ContextualConstantsIF.Message.EMPTY_ARTICLENAMES);
				msg.setErrMessage(ContextualConstantsIF.Message.EMPTY_ARTICLENAMES);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			ajaxResponse.setModel(articleNames);
			ajaxResponse
					.setMessage(ContextualConstantsIF.Message.ARTICLENAMES_SUCESS);
			return ajaxResponse;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			Message msg = new Message(
					ContextualConstantsIF.Message.INTERNAL_ERROR);
			msg.setErrMessage(ContextualConstantsIF.Message.INTERNAL_ERROR);
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}
	}

	@Override
	@RequestMapping(value = "/compareArticle.do", method = {
			RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse compareArticles(
			HttpServletRequest request,
			@RequestBody ComparisionInputModel comparisionModel) {
		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			if (comparisionModel.getArticleNameLeft().equals(
					comparisionModel.getArticleNameRight())) {

				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(
						ContextualConstantsIF.Message.BOTH_ARTICLENAMES_CANNOT_BE_SAME);
				msg.setErrMessage(ContextualConstantsIF.Message.BOTH_ARTICLENAMES_CANNOT_BE_SAME);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;

			}

			StatusInfo statusInfo = contextDelegate
					.compareArticles(comparisionModel);

			if (!statusInfo.isStatus()) {

				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(
						ContextualConstantsIF.Message.COULD_NOT_MEASURE_SIMILARITY);
				msg.setErrMessage(ContextualConstantsIF.Message.COULD_NOT_MEASURE_SIMILARITY);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;

			}

			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);

			CompareArticleVO compareArticlesVO = (CompareArticleVO) statusInfo
					.getObject();

			ajaxResponse.setModel(compareArticlesVO);
			ajaxResponse
					.setMessage(ContextualConstantsIF.Message.COMPARINGARTICLE_SUCESS);
			return ajaxResponse;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			Message msg = new Message(
					ContextualConstantsIF.Message.INTERNAL_ERROR);
			msg.setErrMessage(ContextualConstantsIF.Message.INTERNAL_ERROR);
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}
	}

	@Override
	@RequestMapping(value = "/viewNLPAdjectives.do", method = {
			RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse viewNLPAdjectives(
			HttpServletRequest request) {
		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			List<AdjectiveVO> adjectiveList = contextDelegate
					.retriveNLPAdjectives();
			if (null == adjectiveList) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(
						ContextualConstantsIF.Message.EMPTY_STOPWORDS);
				msg.setErrMessage(ContextualConstantsIF.Message.EMPTY_STOPWORDS);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			ajaxResponse.setModel(adjectiveList);
			ajaxResponse
					.setMessage(ContextualConstantsIF.Message.STOPWORD_SUCESS);
			return ajaxResponse;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			Message msg = new Message(
					ContextualConstantsIF.Message.INTERNAL_ERROR);
			msg.setErrMessage(ContextualConstantsIF.Message.INTERNAL_ERROR);
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}
	}

	@Override
	@RequestMapping(value = "/login.do", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView doLogin(HttpServletRequest request,
			@ModelAttribute RegisterUser registerUser) {

		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			String userId = registerUser.getUSN();

			if (null == userId || userId.isEmpty()
					|| userId.trim().length() == 0) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(
						ContextualConstantsIF.Message.USN_EMPTY);
				msg.setErrMessage(ContextualConstantsIF.Message.USN_EMPTY);

				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return new ModelAndView(
						ContextualConstantsIF.Views.VIEW_LOGIN_INPUT,
						ContextualConstantsIF.Keys.OBJ, ajaxResponse);
			}

			String password = registerUser.getPassword();

			if (null == password || password.isEmpty()
					|| password.trim().length() == 0) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(
						ContextualConstantsIF.Message.PASSWORD_EMPTY);
				msg.setErrMessage(ContextualConstantsIF.Message.PASSWORD_EMPTY);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return new ModelAndView(
						ContextualConstantsIF.Views.VIEW_LOGIN_INPUT,
						ContextualConstantsIF.Keys.OBJ, ajaxResponse);
			}

			StatusInfo statusInfo = contextDelegate.checkLogin(registerUser);

			if (!statusInfo.isStatus()) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(statusInfo.getErrMsg());
				msg.setErrMessage(statusInfo.getErrMsg());
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return new ModelAndView(
						ContextualConstantsIF.Views.VIEW_LOGIN_INPUT,
						ContextualConstantsIF.Keys.OBJ, ajaxResponse);
			} else if (statusInfo.isStatus()) {

				HttpSession session = request.getSession(true);

				session.setAttribute(
						ContextualConstantsIF.Keys.LOGINID_SESSION,
						registerUser.getUSN());
				session.setAttribute(
						ContextualConstantsIF.Keys.LOGINTYPE_SESSION,
						statusInfo.getType());

				if (statusInfo.getType() == ContextualConstantsIF.Keys.ADMIN_TYPE) {
					ajaxResponse = new AJAXResponse();
					ajaxResponse.setStatus(true);
					Message msg = new Message(
							ContextualConstantsIF.Message.USERREGISTERED_SUCESS_MSG);
					List<Message> ebErrors = new ArrayList<Message>();
					ebErrors.add(msg);
					ajaxResponse.setEbErrors(ebErrors);
					return new ModelAndView(
							ContextualConstantsIF.Views.VIEW_ADMIN_WELCOMEPAGE,
							ContextualConstantsIF.Keys.OBJ, ajaxResponse);
				} else if (statusInfo.getType() == ContextualConstantsIF.Keys.CUSTOMER_TYPE) {
					return new ModelAndView(
							ContextualConstantsIF.Views.VIEW_P_WELCOMEPAGE,
							ContextualConstantsIF.Keys.OBJ, ajaxResponse);
				} else {
					ajaxResponse = new AJAXResponse();
					ajaxResponse.setStatus(true);
					Message msg = new Message(
							ContextualConstantsIF.Message.USERREGISTERED_SUCESS_MSG);
					List<Message> ebErrors = new ArrayList<Message>();
					ebErrors.add(msg);
					ajaxResponse.setEbErrors(ebErrors);
					return new ModelAndView(
							ContextualConstantsIF.Views.VIEW_TEACH_WELCOMEPAGE,
							ContextualConstantsIF.Keys.OBJ, ajaxResponse);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			Message msg = new Message(
					ContextualConstantsIF.Message.INTERNAL_ERROR);
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return new ModelAndView(
					ContextualConstantsIF.Views.VIEW_LOGIN_INPUT,
					ContextualConstantsIF.Keys.OBJ, ajaxResponse);
		}
		return null;

	}

	@Override
	@RequestMapping(value = "/logout.do", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView doLogout(HttpServletRequest request) {
		try {

			HttpSession session = request.getSession(false);

			session.invalidate();
			return new ModelAndView(
					ContextualConstantsIF.Views.APPLICATION_WELCOME_PAGE,
					ContextualConstantsIF.Keys.OBJ, null);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			return new ModelAndView(
					ContextualConstantsIF.Views.APPLICATION_WELCOME_PAGE,
					ContextualConstantsIF.Keys.OBJ, null);
		}
	}

	@Override
	@RequestMapping(value = "/registerUser.do", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView registerUserInfo(
			@ModelAttribute RegisterUser registerUser,
			HttpServletRequest request) {
		RegisterVerifyMsgs registerVerifyMsgs = new RegisterVerifyMsgs();

		try {

			// Adding default login type as customer
			registerUser.setLoginType(ContextualConstantsIF.Keys.CUSTOMER_TYPE);

			String usn = registerUser.getUSN();
			if (null == usn || usn.isEmpty() || usn.trim().length() == 0) {

				registerVerifyMsgs
						.setSeverMessage(ContextualConstantsIF.Message.USN_EMPTY);

				return new ModelAndView(
						ContextualConstantsIF.Views.VIEW_REGISTER_INPUT,
						ContextualConstantsIF.Keys.OBJ, registerVerifyMsgs);

			}
			String password = registerUser.getPassword();
			if (null == password || password.isEmpty()
					|| password.trim().length() == 0) {

				registerVerifyMsgs
						.setSeverMessage(ContextualConstantsIF.Message.PASSWORD_EMPTY);

				return new ModelAndView(
						ContextualConstantsIF.Views.VIEW_REGISTER_INPUT,
						ContextualConstantsIF.Keys.OBJ, registerVerifyMsgs);
			}

			String email = registerUser.getEmail();

			if (null == email || email.isEmpty() || email.trim().length() == 0) {

				registerVerifyMsgs
						.setSeverMessage(ContextualConstantsIF.Message.EMAIL_EMPTY);

				return new ModelAndView(
						ContextualConstantsIF.Views.VIEW_REGISTER_INPUT,
						ContextualConstantsIF.Keys.OBJ, registerVerifyMsgs);
			}

			String gender = registerUser.getGender();

			if (null == gender || gender.isEmpty()
					|| gender.trim().length() == 0) {

				registerVerifyMsgs
						.setSeverMessage(ContextualConstantsIF.Message.GENDER_EMPTY);

				return new ModelAndView(
						ContextualConstantsIF.Views.VIEW_REGISTER_INPUT,
						ContextualConstantsIF.Keys.OBJ, registerVerifyMsgs);
			}

			String name = registerUser.getName();

			if (null == name || name.isEmpty() || name.trim().length() == 0) {

				registerVerifyMsgs
						.setSeverMessage(ContextualConstantsIF.Message.NAME_EMPTY);

				return new ModelAndView(
						ContextualConstantsIF.Views.VIEW_REGISTER_INPUT,
						ContextualConstantsIF.Keys.OBJ, registerVerifyMsgs);
			}

			String address = registerUser.getAddress();

			if (null == address || address.isEmpty()
					|| address.trim().length() == 0) {

				registerVerifyMsgs
						.setSeverMessage(ContextualConstantsIF.Message.ADDRESS_EMPTY);

				return new ModelAndView(
						ContextualConstantsIF.Views.VIEW_REGISTER_INPUT,
						ContextualConstantsIF.Keys.OBJ, registerVerifyMsgs);
			}

			String city = registerUser.getCity();

			if (null == city || city.isEmpty() || city.trim().length() == 0) {

				registerVerifyMsgs
						.setSeverMessage(ContextualConstantsIF.Message.CITY_EMPTY);

				return new ModelAndView(
						ContextualConstantsIF.Views.VIEW_REGISTER_INPUT,
						ContextualConstantsIF.Keys.OBJ, registerVerifyMsgs);
			}

			String state = registerUser.getState();

			if (null == state || state.isEmpty() || state.trim().length() == 0) {

				registerVerifyMsgs
						.setSeverMessage(ContextualConstantsIF.Message.STATE_EMPTY);

				return new ModelAndView(
						ContextualConstantsIF.Views.VIEW_REGISTER_INPUT,
						ContextualConstantsIF.Keys.OBJ, registerVerifyMsgs);
			}

			String pinCode = registerUser.getPinCode();

			if (null == pinCode || pinCode.isEmpty()
					|| pinCode.trim().length() == 0) {

				registerVerifyMsgs
						.setSeverMessage(ContextualConstantsIF.Message.PINCODE_EMPTY);

				return new ModelAndView(
						ContextualConstantsIF.Views.VIEW_REGISTER_INPUT,
						ContextualConstantsIF.Keys.OBJ, registerVerifyMsgs);
			}

			String fatherName = registerUser.getFatherName();

			if (null == fatherName || fatherName.isEmpty()
					|| fatherName.trim().length() == 0) {

				registerVerifyMsgs
						.setSeverMessage(ContextualConstantsIF.Message.FATHERNAME_EMPTY);

				return new ModelAndView(
						ContextualConstantsIF.Views.VIEW_REGISTER_INPUT,
						ContextualConstantsIF.Keys.OBJ, registerVerifyMsgs);
			}

			String fatherNumber = registerUser.getFatherNumber();

			if (null == fatherNumber || fatherNumber.isEmpty()
					|| fatherNumber.trim().length() == 0) {

				registerVerifyMsgs
						.setSeverMessage(ContextualConstantsIF.Message.FATHERNUM_EMPTY);

				return new ModelAndView(
						ContextualConstantsIF.Views.VIEW_REGISTER_INPUT,
						ContextualConstantsIF.Keys.OBJ, registerVerifyMsgs);
			}

			String fatherEmail = registerUser.getFatherEmail();

			if (null == fatherEmail || fatherEmail.isEmpty()
					|| fatherEmail.trim().length() == 0) {

				registerVerifyMsgs
						.setSeverMessage(ContextualConstantsIF.Message.FATHEREMAIL_EMPTY);

				return new ModelAndView(
						ContextualConstantsIF.Views.VIEW_REGISTER_INPUT,
						ContextualConstantsIF.Keys.OBJ, registerVerifyMsgs);
			}

			//

			String motherName = registerUser.getMotherName();

			if (null == motherName || motherName.isEmpty()
					|| motherName.trim().length() == 0) {

				registerVerifyMsgs
						.setSeverMessage(ContextualConstantsIF.Message.MOTHERNAME_EMPTY);

				return new ModelAndView(
						ContextualConstantsIF.Views.VIEW_REGISTER_INPUT,
						ContextualConstantsIF.Keys.OBJ, registerVerifyMsgs);
			}

			String motherNumber = registerUser.getMotherNumber();

			if (null == motherNumber || motherNumber.isEmpty()
					|| motherNumber.trim().length() == 0) {

				registerVerifyMsgs
						.setSeverMessage(ContextualConstantsIF.Message.MOTHERNUM_EMPTY);

				return new ModelAndView(
						ContextualConstantsIF.Views.VIEW_REGISTER_INPUT,
						ContextualConstantsIF.Keys.OBJ, registerVerifyMsgs);
			}

			String motherEmail = registerUser.getMotherEmail();

			if (null == motherEmail || motherEmail.isEmpty()
					|| motherEmail.trim().length() == 0) {

				registerVerifyMsgs
						.setSeverMessage(ContextualConstantsIF.Message.MOTHEREMAIL_EMPTY);

				return new ModelAndView(
						ContextualConstantsIF.Views.VIEW_REGISTER_INPUT,
						ContextualConstantsIF.Keys.OBJ, registerVerifyMsgs);
			}

			String guardianrName = registerUser.getLocalGuardName();

			if (null == guardianrName || guardianrName.isEmpty()
					|| guardianrName.trim().length() == 0) {

				registerVerifyMsgs
						.setSeverMessage(ContextualConstantsIF.Message.GUARDNAME_EMPTY);

				return new ModelAndView(
						ContextualConstantsIF.Views.VIEW_REGISTER_INPUT,
						ContextualConstantsIF.Keys.OBJ, registerVerifyMsgs);
			}

			String guardianNumber = registerUser.getLocalGuardNumber();

			if (null == guardianNumber || guardianNumber.isEmpty()
					|| guardianNumber.trim().length() == 0) {

				registerVerifyMsgs
						.setSeverMessage(ContextualConstantsIF.Message.GUARDNUM_EMPTY);

				return new ModelAndView(
						ContextualConstantsIF.Views.VIEW_REGISTER_INPUT,
						ContextualConstantsIF.Keys.OBJ, registerVerifyMsgs);
			}

			String guardianEmail = registerUser.getLocalGuardEmail();

			if (null == guardianEmail || guardianEmail.isEmpty()
					|| guardianEmail.trim().length() == 0) {

				registerVerifyMsgs
						.setSeverMessage(ContextualConstantsIF.Message.GUARDEMAIL_EMPTY);

				return new ModelAndView(
						ContextualConstantsIF.Views.VIEW_REGISTER_INPUT,
						ContextualConstantsIF.Keys.OBJ, registerVerifyMsgs);
			}

			String admissionType = registerUser.getAdmissionType();

			if (null == admissionType || admissionType.isEmpty()
					|| admissionType.trim().length() == 0) {

				registerVerifyMsgs
						.setSeverMessage(ContextualConstantsIF.Message.ADDTYPE_EMPTY);

				return new ModelAndView(
						ContextualConstantsIF.Views.VIEW_REGISTER_INPUT,
						ContextualConstantsIF.Keys.OBJ, registerVerifyMsgs);
			}

			String challanNumber = registerUser.getChallanNumber();

			if (null == challanNumber || challanNumber.isEmpty()
					|| challanNumber.trim().length() == 0) {

				registerVerifyMsgs
						.setSeverMessage(ContextualConstantsIF.Message.CHALLAN_NUM_EMPTY);

				return new ModelAndView(
						ContextualConstantsIF.Views.VIEW_REGISTER_INPUT,
						ContextualConstantsIF.Keys.OBJ, registerVerifyMsgs);
			}

			try {

				int challanNumberIn = new Integer(challanNumber);

			} catch (Exception e) {

				registerVerifyMsgs
						.setSeverMessage(ContextualConstantsIF.Message.CHALLANNUMBER_NUMERIC);

				return new ModelAndView(
						ContextualConstantsIF.Views.VIEW_REGISTER_INPUT,
						ContextualConstantsIF.Keys.OBJ, registerVerifyMsgs);

			}

			String feePaid = registerUser.getFeePaid();

			if (null == feePaid || feePaid.isEmpty()
					|| feePaid.trim().length() == 0) {

				registerVerifyMsgs
						.setSeverMessage(ContextualConstantsIF.Message.FEEPAID_EMPTY);

				return new ModelAndView(
						ContextualConstantsIF.Views.VIEW_REGISTER_INPUT,
						ContextualConstantsIF.Keys.OBJ, registerVerifyMsgs);
			}

			try {

				double feePaidDouble = new Double(feePaid);

			} catch (Exception e) {

				registerVerifyMsgs
						.setSeverMessage(ContextualConstantsIF.Message.FEEPAID_NUMERIC);

				return new ModelAndView(
						ContextualConstantsIF.Views.VIEW_REGISTER_INPUT,
						ContextualConstantsIF.Keys.OBJ, registerVerifyMsgs);

			}

			// From Residential Address

			String residentialAddress = registerUser.getResidentialAddress();

			if (null == residentialAddress || residentialAddress.isEmpty()
					|| residentialAddress.trim().length() == 0) {

				registerVerifyMsgs
						.setSeverMessage(ContextualConstantsIF.Message.RESIDENTIALADDRESS_EMPTY);

				return new ModelAndView(
						ContextualConstantsIF.Views.VIEW_REGISTER_INPUT,
						ContextualConstantsIF.Keys.OBJ, registerVerifyMsgs);
			}

			String residentialStatus = registerUser.getResidenceStatus();

			if (null == residentialStatus || residentialStatus.isEmpty()
					|| residentialStatus.trim().length() == 0) {

				registerVerifyMsgs
						.setSeverMessage(ContextualConstantsIF.Message.RESIDENTIALSTATUS_EMPTY);

				return new ModelAndView(
						ContextualConstantsIF.Views.VIEW_REGISTER_INPUT,
						ContextualConstantsIF.Keys.OBJ, registerVerifyMsgs);
			}

			String semMarks = registerUser.getSemesterMarks();

			if (null == semMarks || semMarks.isEmpty()
					|| semMarks.trim().length() == 0) {

				registerVerifyMsgs
						.setSeverMessage(ContextualConstantsIF.Message.SEMMARKS_EMPTY);

				return new ModelAndView(
						ContextualConstantsIF.Views.VIEW_REGISTER_INPUT,
						ContextualConstantsIF.Keys.OBJ, registerVerifyMsgs);
			}

			try {

				double semMarksDouble = new Double(semMarks);

			} catch (Exception e) {

				registerVerifyMsgs
						.setSeverMessage(ContextualConstantsIF.Message.SEMMARKS_NUMERIC);

				return new ModelAndView(
						ContextualConstantsIF.Views.VIEW_REGISTER_INPUT,
						ContextualConstantsIF.Keys.OBJ, registerVerifyMsgs);

			}

			String activity = registerUser.getActivity();

			if (null == activity || activity.isEmpty()
					|| activity.trim().length() == 0) {

				registerVerifyMsgs
						.setSeverMessage(ContextualConstantsIF.Message.ACTIVITY_EMPTY);

				return new ModelAndView(
						ContextualConstantsIF.Views.VIEW_REGISTER_INPUT,
						ContextualConstantsIF.Keys.OBJ, registerVerifyMsgs);
			}

			List<String> activityList = new ArrayList<String>();

			if (activity != null && !activity.isEmpty()) {

				String[] values = activity.split(",");

				if (values.length == 0) {

					activityList.add(activity);
				}

				for (int i = 0; i < values.length; i++) {

					activityList.add(values[i]);
				}

			}

			registerUser.setActivityList(activityList);

			StatusInfo statusInfo = contextDelegate
					.doRegistration(registerUser);

			if (!statusInfo.isStatus()) {

				registerVerifyMsgs.setSeverMessage(statusInfo.getErrMsg());

				return new ModelAndView(
						ContextualConstantsIF.Views.VIEW_REGISTER_INPUT,
						ContextualConstantsIF.Keys.OBJ, registerVerifyMsgs);
			}

			registerVerifyMsgs
					.setSucessMsg(ContextualConstantsIF.Message.USERREGISTERED_SUCESS_MSG);

			return new ModelAndView(
					ContextualConstantsIF.Views.VIEW_REGISTER_INPUT,
					ContextualConstantsIF.Keys.OBJ, registerVerifyMsgs);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());

			registerVerifyMsgs
					.setSeverMessage(ContextualConstantsIF.Message.USERREGISTERED_SUCESS_MSG);

			return new ModelAndView(
					ContextualConstantsIF.Views.VIEW_REGISTER_INPUT,
					ContextualConstantsIF.Keys.OBJ, registerVerifyMsgs);
		}

	}

	@Override
	@RequestMapping(value = "/registerTeacher.do", method = {
			RequestMethod.POST, RequestMethod.GET })
	public ModelAndView registerTeacherInfo(
			@ModelAttribute RegisterUser registerUser,
			HttpServletRequest request) {
		RegisterVerifyMsgs registerVerifyMsgs = new RegisterVerifyMsgs();

		try {

			// Adding default login type as customer
			registerUser.setLoginType(ContextualConstantsIF.Keys.TEACHER_TYPE);
			//registerUser.setLoginType(1);

			String usn = registerUser.getUSN();
			if (null == usn || usn.isEmpty() || usn.trim().length() == 0) {

				registerVerifyMsgs
						.setSeverMessage(ContextualConstantsIF.Message.USN_EMPTY);

				return new ModelAndView(
						ContextualConstantsIF.Views.VIEW_REGISTER_TEACH_INPUT,
						ContextualConstantsIF.Keys.OBJ, registerVerifyMsgs);

			}
			String password = registerUser.getPassword();
			if (null == password || password.isEmpty()
					|| password.trim().length() == 0) {

				registerVerifyMsgs
						.setSeverMessage(ContextualConstantsIF.Message.PASSWORD_EMPTY);

				return new ModelAndView(
						ContextualConstantsIF.Views.VIEW_REGISTER_TEACH_INPUT,
						ContextualConstantsIF.Keys.OBJ, registerVerifyMsgs);
			}

			String email = registerUser.getEmail();

			if (null == email || email.isEmpty() || email.trim().length() == 0) {

				registerVerifyMsgs
						.setSeverMessage(ContextualConstantsIF.Message.EMAIL_EMPTY);

				return new ModelAndView(
						ContextualConstantsIF.Views.VIEW_REGISTER_TEACH_INPUT,
						ContextualConstantsIF.Keys.OBJ, registerVerifyMsgs);
			}

			String gender = registerUser.getGender();

			if (null == gender || gender.isEmpty()
					|| gender.trim().length() == 0) {

				registerVerifyMsgs
						.setSeverMessage(ContextualConstantsIF.Message.GENDER_EMPTY);

				return new ModelAndView(
						ContextualConstantsIF.Views.VIEW_REGISTER_TEACH_INPUT,
						ContextualConstantsIF.Keys.OBJ, registerVerifyMsgs);
			}

			String name = registerUser.getName();

			if (null == name || name.isEmpty() || name.trim().length() == 0) {

				registerVerifyMsgs
						.setSeverMessage(ContextualConstantsIF.Message.NAME_EMPTY);

				return new ModelAndView(
						ContextualConstantsIF.Views.VIEW_REGISTER_TEACH_INPUT,
						ContextualConstantsIF.Keys.OBJ, registerVerifyMsgs);
			}

			String address = registerUser.getAddress();

			if (null == address || address.isEmpty()
					|| address.trim().length() == 0) {

				registerVerifyMsgs
						.setSeverMessage(ContextualConstantsIF.Message.ADDRESS_EMPTY);

				return new ModelAndView(
						ContextualConstantsIF.Views.VIEW_REGISTER_TEACH_INPUT,
						ContextualConstantsIF.Keys.OBJ, registerVerifyMsgs);
			}

			String city = registerUser.getCity();

			if (null == city || city.isEmpty() || city.trim().length() == 0) {

				registerVerifyMsgs
						.setSeverMessage(ContextualConstantsIF.Message.CITY_EMPTY);

				return new ModelAndView(
						ContextualConstantsIF.Views.VIEW_REGISTER_TEACH_INPUT,
						ContextualConstantsIF.Keys.OBJ, registerVerifyMsgs);
			}

			String state = registerUser.getState();

			if (null == state || state.isEmpty() || state.trim().length() == 0) {

				registerVerifyMsgs
						.setSeverMessage(ContextualConstantsIF.Message.STATE_EMPTY);

				return new ModelAndView(
						ContextualConstantsIF.Views.VIEW_REGISTER_TEACH_INPUT,
						ContextualConstantsIF.Keys.OBJ, registerVerifyMsgs);
			}

			String pinCode = registerUser.getPinCode();

			if (null == pinCode || pinCode.isEmpty()
					|| pinCode.trim().length() == 0) {

				registerVerifyMsgs
						.setSeverMessage(ContextualConstantsIF.Message.PINCODE_EMPTY);

				return new ModelAndView(
						ContextualConstantsIF.Views.VIEW_REGISTER_TEACH_INPUT,
						ContextualConstantsIF.Keys.OBJ, registerVerifyMsgs);
			}

			String degree = registerUser.getDegree();

			if (null == degree || degree.isEmpty()
					|| degree.trim().length() == 0) {

				registerVerifyMsgs
						.setSeverMessage(ContextualConstantsIF.Message.DEGREE_EMPTY);

				return new ModelAndView(
						ContextualConstantsIF.Views.VIEW_REGISTER_TEACH_INPUT,
						ContextualConstantsIF.Keys.OBJ, registerVerifyMsgs);
			}

			String specification = registerUser.getSpecification();

			if (null == specification || specification.isEmpty()
					|| specification.trim().length() == 0) {

				registerVerifyMsgs
						.setSeverMessage(ContextualConstantsIF.Message.SPECIFICATION_EMPTY);

				return new ModelAndView(
						ContextualConstantsIF.Views.VIEW_REGISTER_TEACH_INPUT,
						ContextualConstantsIF.Keys.OBJ, registerVerifyMsgs);
			}

			String college = registerUser.getCollege();

			if (null == college || college.isEmpty()
					|| college.trim().length() == 0) {

				registerVerifyMsgs
						.setSeverMessage(ContextualConstantsIF.Message.COLLEGE_EMPTY);

				return new ModelAndView(
						ContextualConstantsIF.Views.VIEW_REGISTER_TEACH_INPUT,
						ContextualConstantsIF.Keys.OBJ, registerVerifyMsgs);
			}

			String department = registerUser.getDepartment();

			if (null == department || department.isEmpty()
					|| department.trim().length() == 0) {

				registerVerifyMsgs
						.setSeverMessage(ContextualConstantsIF.Message.DEPART_EMPTY);

				return new ModelAndView(
						ContextualConstantsIF.Views.VIEW_REGISTER_TEACH_INPUT,
						ContextualConstantsIF.Keys.OBJ, registerVerifyMsgs);
			}

			String subjects = registerUser.getSubjects();

			if (null == subjects || subjects.isEmpty()
					|| subjects.trim().length() == 0) {

				registerVerifyMsgs
						.setSeverMessage(ContextualConstantsIF.Message.SUBJECTS_EMPTY);

				return new ModelAndView(
						ContextualConstantsIF.Views.VIEW_REGISTER_TEACH_INPUT,
						ContextualConstantsIF.Keys.OBJ, registerVerifyMsgs);
			}

			List<String> subjectList = new ArrayList<String>();

			if (subjects != null && !subjects.isEmpty()) {

				String[] values = subjects.split(",");

				if (values.length == 0) {

					subjectList.add(subjects);
				}

				for (int i = 0; i < values.length; i++) {

					subjectList.add(values[i]);
				}

			}

			registerUser.setSubjectList(subjectList);

			StatusInfo statusInfo = contextDelegate
					.doRegistrationTeacher(registerUser);

			if (!statusInfo.isStatus()) {

				registerVerifyMsgs.setSeverMessage(statusInfo.getErrMsg());

				return new ModelAndView(
						ContextualConstantsIF.Views.VIEW_REGISTER_TEACH_INPUT,
						ContextualConstantsIF.Keys.OBJ, registerVerifyMsgs);
			}

			registerVerifyMsgs
					.setSucessMsg(ContextualConstantsIF.Message.USERREGISTERED_SUCESS_MSG);

			return new ModelAndView(
					ContextualConstantsIF.Views.VIEW_REGISTER_TEACH_INPUT,
					ContextualConstantsIF.Keys.OBJ, registerVerifyMsgs);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());

			registerVerifyMsgs
					.setSeverMessage(ContextualConstantsIF.Message.USERREGISTERED_SUCESS_MSG);

			return new ModelAndView(
					ContextualConstantsIF.Views.VIEW_REGISTER_TEACH_INPUT,
					ContextualConstantsIF.Keys.OBJ, registerVerifyMsgs);
		}

	}

	@Override
	@RequestMapping(value = "/doLogLikelihood.do", method = {
			RequestMethod.POST, RequestMethod.GET })
	public ModelAndView doLikelihoodFunction(HttpServletRequest request) {
		try {

			StatusInfo statusInfo = contextDelegate.doLogLikelihood();

			if (!statusInfo.isStatus()) {
				AJAXResponse ajax = new AJAXResponse();
				ajax.setStatus(false);
				Message msg = new Message(statusInfo.getErrMsg());
				msg.setErrMessage(statusInfo.getErrMsg());
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajax.setEbErrors(ebErrors);
				return new ModelAndView(
						ContextualConstantsIF.Views.VIEW_ADMIN_ERROR_PAGE,
						ContextualConstantsIF.Keys.OBJ, ajax);
			}
			AJAXResponse ajax = new AJAXResponse();
			ajax.setStatus(true);

			ajax.setMessage(ContextualConstantsIF.Message.LIKELIHOOD_SUCESS);

			return new ModelAndView(
					ContextualConstantsIF.Views.VIEW_ADMIN_SUCESS_PAGE,
					ContextualConstantsIF.Keys.OBJ, ajax);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			AJAXResponse ajax = new AJAXResponse();
			ajax.setStatus(false);
			Message msg = new Message(
					ContextualConstantsIF.Message.INTERNAL_ERROR);
			msg.setErrMessage(ContextualConstantsIF.Message.INTERNAL_ERROR);
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajax.setEbErrors(ebErrors);
			return new ModelAndView(
					ContextualConstantsIF.Views.VIEW_ADMIN_ERROR_PAGE,
					ContextualConstantsIF.Keys.OBJ, ajax);
		}
	}

	@Override
	@RequestMapping(value = "/retriveAllLOgLikelihood.do", method = {
			RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse retriveAllLOgLikelihood(
			HttpServletRequest request) {
		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			
			List<LogLikelihoodVO> articleNames = contextDelegate
					.retriveAllLogLikelihood();
			if (null == articleNames) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(
						ContextualConstantsIF.Message.EMPTY_ARTICLENAMES);
				msg.setErrMessage(ContextualConstantsIF.Message.EMPTY_ARTICLENAMES);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			ajaxResponse.setModel(articleNames);
			ajaxResponse
					.setMessage(ContextualConstantsIF.Message.ARTICLENAMES_SUCESS);
			return ajaxResponse;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			Message msg = new Message(
					ContextualConstantsIF.Message.INTERNAL_ERROR);
			msg.setErrMessage(ContextualConstantsIF.Message.INTERNAL_ERROR);
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}
	}

	@Override
	@RequestMapping(value = "/doRVCoeffcient.do", method = {
			RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse doRVCoeffcient(
			HttpServletRequest request,
			@RequestBody ComparisionInputModel comparisionModel) {
		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			if (comparisionModel.getArticleNameLeft().equals(
					comparisionModel.getArticleNameRight())) {

				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(
						ContextualConstantsIF.Message.BOTH_ARTICLENAMES_CANNOT_BE_SAME);
				msg.setErrMessage(ContextualConstantsIF.Message.BOTH_ARTICLENAMES_CANNOT_BE_SAME);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;

			}

			StatusInfo statusInfo = contextDelegate
					.doRVCoeffcient(comparisionModel);

			if (!statusInfo.isStatus()) {

				if (statusInfo.getErrMsg() != null) {

					ajaxResponse = new AJAXResponse();
					ajaxResponse.setStatus(false);
					Message msg = new Message(statusInfo.getErrMsg());
					msg.setErrMessage(statusInfo.getErrMsg());
					List<Message> ebErrors = new ArrayList<Message>();
					ebErrors.add(msg);
					ajaxResponse.setEbErrors(ebErrors);
					return ajaxResponse;
				} else if (statusInfo.getExceptionMsg() != null) {
					ajaxResponse = new AJAXResponse();
					ajaxResponse.setStatus(false);
					Message msg = new Message(statusInfo.getExceptionMsg());
					msg.setErrMessage(statusInfo.getExceptionMsg());
					List<Message> ebErrors = new ArrayList<Message>();
					ebErrors.add(msg);
					ajaxResponse.setEbErrors(ebErrors);
					return ajaxResponse;

				} else {

					ajaxResponse = new AJAXResponse();
					ajaxResponse.setStatus(false);
					Message msg = new Message(
							ContextualConstantsIF.Message.COULD_NOT_MEASURE_SIMILARITY);
					msg.setErrMessage(ContextualConstantsIF.Message.COULD_NOT_MEASURE_SIMILARITY);
					List<Message> ebErrors = new ArrayList<Message>();
					ebErrors.add(msg);
					ajaxResponse.setEbErrors(ebErrors);
					return ajaxResponse;
				}
			}

			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);

			CompareArticleRVVO compareArticlesVO = (CompareArticleRVVO) statusInfo
					.getObject();

			ajaxResponse.setModel(compareArticlesVO);
			ajaxResponse
					.setMessage(ContextualConstantsIF.Message.COMPARINGARTICLE_SUCESS);
			return ajaxResponse;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			Message msg = new Message(
					ContextualConstantsIF.Message.INTERNAL_ERROR);
			msg.setErrMessage(ContextualConstantsIF.Message.INTERNAL_ERROR);
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}
	}

	@Override
	@RequestMapping(value = "/performCLuster.do", method = {
			RequestMethod.POST, RequestMethod.GET })
	public ModelAndView doClustering(HttpServletRequest request,
			@RequestParam String threshold) {
		try {

			StatusInfo statusInfo = contextDelegate.doClustering(Double
					.valueOf(threshold));

			if (!statusInfo.isStatus()) {
				AJAXResponse ajax = new AJAXResponse();
				ajax.setStatus(false);
				Message msg = new Message(statusInfo.getErrMsg());
				msg.setErrMessage(statusInfo.getErrMsg());
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajax.setEbErrors(ebErrors);
				return new ModelAndView(
						ContextualConstantsIF.Views.VIEW_ADMIN_ERROR_PAGE,
						ContextualConstantsIF.Keys.OBJ, ajax);
			}
			AJAXResponse ajax = new AJAXResponse();
			ajax.setStatus(true);

			ajax.setMessage(ContextualConstantsIF.Message.CLUSTERING_SUCESSS);
			return new ModelAndView(
					ContextualConstantsIF.Views.VIEW_ADMIN_SUCESS_PAGE,
					ContextualConstantsIF.Keys.OBJ, ajax);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			AJAXResponse ajax = new AJAXResponse();
			ajax.setStatus(false);
			Message msg = new Message(
					ContextualConstantsIF.Message.INTERNAL_ERROR);
			msg.setErrMessage(ContextualConstantsIF.Message.INTERNAL_ERROR);
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajax.setEbErrors(ebErrors);
			return new ModelAndView(
					ContextualConstantsIF.Views.VIEW_ADMIN_ERROR_PAGE,
					ContextualConstantsIF.Keys.OBJ, ajax);
		}
	}

	@Override
	@RequestMapping(value = "/themes.do", method = { RequestMethod.POST,
			RequestMethod.GET })
	public @ResponseBody AJAXResponse doThemes(HttpServletRequest request,
			@RequestBody ThemeModel themeModel) {
		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			StatusInfo statusInfo = contextDelegate.doThemes(themeModel);

			if (!statusInfo.isStatus()) {

				if (statusInfo.getErrMsg() != null) {

					ajaxResponse = new AJAXResponse();
					ajaxResponse.setStatus(false);
					Message msg = new Message(statusInfo.getErrMsg());
					msg.setErrMessage(statusInfo.getErrMsg());
					List<Message> ebErrors = new ArrayList<Message>();
					ebErrors.add(msg);
					ajaxResponse.setEbErrors(ebErrors);
					return ajaxResponse;

				} else if (statusInfo.getExceptionMsg() != null) {
					ajaxResponse = new AJAXResponse();
					ajaxResponse.setStatus(false);
					Message msg = new Message(statusInfo.getExceptionMsg());
					msg.setErrMessage(statusInfo.getExceptionMsg());
					List<Message> ebErrors = new ArrayList<Message>();
					ebErrors.add(msg);
					ajaxResponse.setEbErrors(ebErrors);
					return ajaxResponse;

				} else {

					ajaxResponse = new AJAXResponse();
					ajaxResponse.setStatus(false);
					Message msg = new Message(
							ContextualConstantsIF.Message.THEMES_NOT_FOUND);
					msg.setErrMessage(ContextualConstantsIF.Message.THEMES_NOT_FOUND);
					List<Message> ebErrors = new ArrayList<Message>();
					ebErrors.add(msg);
					ajaxResponse.setEbErrors(ebErrors);
					return ajaxResponse;
				}

			}

			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);

			List<LogLikelihoodVO> compareArticlesVO = (List<LogLikelihoodVO>) statusInfo
					.getObject();

			ajaxResponse.setModel(compareArticlesVO);
			ajaxResponse
					.setMessage(ContextualConstantsIF.Message.COMPARINGARTICLE_SUCESS);
			return ajaxResponse;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			Message msg = new Message(
					ContextualConstantsIF.Message.INTERNAL_ERROR);
			msg.setErrMessage(ContextualConstantsIF.Message.INTERNAL_ERROR);
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}
	}

	@RequestMapping(value = "/comparision.do", method = { RequestMethod.POST,
			RequestMethod.GET })
	public @ResponseBody AJAXResponse retriveComparison(
			HttpServletRequest request) {
		AJAXResponse ajax = new AJAXResponse();
		List<ComparisionVO> compareTrust = null;
		try {
			compareTrust = contextDelegate.retriveComparison();
			ajax.setTotalSize(compareTrust.size());
		} catch (Exception e) {
			e.printStackTrace();
			ajax.setEbErrors(Collections.singletonList(new Message(e
					.getMessage())));
			ajax.setStatus(Boolean.FALSE);
			return ajax;
		}
		ajax.setModel(compareTrust);
		ajax.setMessage("Energy Levels Obtained");
		ajax.setStatus(true);
		return ajax;
	}

	@Override
	@RequestMapping(value = "/submitArticle.do", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView submitArticle(HttpServletRequest request,
			@ModelAttribute ArticleVOUIModel articleVO,
			@RequestParam CommonsMultipartFile file) {

		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			ArticleVO articleVONew = new ArticleVO();

			HttpSession session = request.getSession(true);

			String loginId = (String) session
					.getAttribute(ContextualConstantsIF.Keys.LOGINID_SESSION);

			if (null == loginId) {

				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(
						ContextualConstantsIF.Message.SESSION_EXPIRED);
				msg.setErrMessage(ContextualConstantsIF.Message.SESSION_EXPIRED);

				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return new ModelAndView(
						ContextualConstantsIF.Views.VIEW_LOGIN_INPUT,
						ContextualConstantsIF.Keys.OBJ, ajaxResponse);

			}

			articleVO.setUSN(loginId);

			articleVONew.setUSN(loginId);

			String articleName = articleVO.getArticleName();

			if (null == articleName || articleName.isEmpty()) {

				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(
						ContextualConstantsIF.Message.ARTICLENAME_EMPTY);
				msg.setErrMessage(ContextualConstantsIF.Message.ARTICLENAME_EMPTY);

				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return new ModelAndView(
						ContextualConstantsIF.Views.VIEW_ARTICLE_SUBMISSION_INPUT,
						ContextualConstantsIF.Keys.OBJ, ajaxResponse);
			}

			articleVONew.setArticleName(articleName);

			String articleDesc = articleVO.getArticleDesc();

			if (null == articleDesc || articleDesc.isEmpty()) {

				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(
						ContextualConstantsIF.Message.ARTICLEDESC_EMPTY);
				msg.setErrMessage(ContextualConstantsIF.Message.ARTICLEDESC_EMPTY);

				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return new ModelAndView(
						ContextualConstantsIF.Views.VIEW_ARTICLE_SUBMISSION_INPUT,
						ContextualConstantsIF.Keys.OBJ, ajaxResponse);
			}

			articleVONew.setArticleDesc(articleDesc);

			String publisher = articleVO.getPublisher();

			if (null == publisher || publisher.isEmpty()) {

				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(
						ContextualConstantsIF.Message.PUBLISHER_EMPTY);
				msg.setErrMessage(ContextualConstantsIF.Message.PUBLISHER_EMPTY);

				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return new ModelAndView(
						ContextualConstantsIF.Views.VIEW_ARTICLE_SUBMISSION_INPUT,
						ContextualConstantsIF.Keys.OBJ, ajaxResponse);
			}

			articleVONew.setPublisher(publisher);

			String keywords = articleVO.getKeywords();

			if (null == keywords || keywords.isEmpty()) {

				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(
						ContextualConstantsIF.Message.KEYWORDS_EMPTY);
				msg.setErrMessage(ContextualConstantsIF.Message.KEYWORDS_EMPTY);

				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return new ModelAndView(
						ContextualConstantsIF.Views.VIEW_ARTICLE_SUBMISSION_INPUT,
						ContextualConstantsIF.Keys.OBJ, ajaxResponse);
			}

			List<String> keywordList = new ArrayList<String>();

			if (keywords != null && !keywords.isEmpty()) {

				String[] values = keywords.split(",");

				if (values.length == 0) {

					keywordList.add(keywords);
				}

				for (int i = 0; i < values.length; i++) {

					keywordList.add(values[i]);
				}

			}

			articleVONew.setKeyWordList(keywordList);

			String authors = articleVO.getAuthors();

			if (null == authors || authors.isEmpty()) {

				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(
						ContextualConstantsIF.Message.AUTHORS_EMPTY);
				msg.setErrMessage(ContextualConstantsIF.Message.AUTHORS_EMPTY);

				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return new ModelAndView(
						ContextualConstantsIF.Views.VIEW_ARTICLE_SUBMISSION_INPUT,
						ContextualConstantsIF.Keys.OBJ, ajaxResponse);
			}

			List<String> authorList = new ArrayList<String>();

			if (authors != null && !authors.isEmpty()) {

				String[] values = authors.split(",");

				if (values.length == 0) {

					authorList.add(authors);
				}

				for (int i = 0; i < values.length; i++) {

					authorList.add(values[i]);
				}

			}

			articleVONew.setAuthorList(authorList);

			MultipartFile multipartFile = file;

			if (null == multipartFile) {

				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(
						ContextualConstantsIF.Message.MULTIPARTFILE_EMPTY);
				msg.setErrMessage(ContextualConstantsIF.Message.MULTIPARTFILE_EMPTY);

				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return new ModelAndView(
						ContextualConstantsIF.Views.VIEW_ARTICLE_SUBMISSION_INPUT,
						ContextualConstantsIF.Keys.OBJ, ajaxResponse);

			}

			String contentType = multipartFile.getContentType();
			
			articleVONew.setContextType(contentType);

			String myName = multipartFile.getOriginalFilename();

			System.out.println("Name =" + myName);

			articleVONew.setFileName(myName);

			if (null == contentType) {

				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(
						ContextualConstantsIF.Message.CONTENTTYPE_EMPTY);
				msg.setErrMessage(ContextualConstantsIF.Message.CONTENTTYPE_EMPTY);

				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return new ModelAndView(
						ContextualConstantsIF.Views.VIEW_ARTICLE_SUBMISSION_INPUT,
						ContextualConstantsIF.Keys.OBJ, ajaxResponse);

			}

			byte[] fileContents = multipartFile.getBytes();
			
			InputStream fileInputStream = multipartFile.getInputStream();
			if (null == fileContents || fileContents.length == 0) {

				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(
						ContextualConstantsIF.Message.FILECONTENTS_EMPTY);
				msg.setErrMessage(ContextualConstantsIF.Message.FILECONTENTS_EMPTY);

				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return new ModelAndView(
						ContextualConstantsIF.Views.VIEW_ARTICLE_SUBMISSION_INPUT,
						ContextualConstantsIF.Keys.OBJ, ajaxResponse);

			}

			articleVONew.setFile(multipartFile);

			articleVONew.setFileContent(fileContents);

			articleVONew.setFileInputStream(fileInputStream);
			StatusInfo statusInfo = contextDelegate.storeArticle(articleVONew);

			if (!statusInfo.isStatus()) {

				if (statusInfo.getErrMsg() != null) {
					ajaxResponse = new AJAXResponse();
					ajaxResponse.setStatus(false);
					Message msg = new Message(statusInfo.getErrMsg());
					List<Message> ebErrors = new ArrayList<Message>();
					ebErrors.add(msg);
					ajaxResponse.setEbErrors(ebErrors);
					return new ModelAndView(
							ContextualConstantsIF.Views.VIEW_ARTICLE_SUBMISSION_INPUT,
							ContextualConstantsIF.Keys.OBJ, ajaxResponse);

				} else if (statusInfo.getExceptionMsg() != null) {
					ajaxResponse = new AJAXResponse();
					ajaxResponse.setStatus(false);
					Message msg = new Message(statusInfo.getExceptionMsg());
					List<Message> ebErrors = new ArrayList<Message>();
					ebErrors.add(msg);
					ajaxResponse.setEbErrors(ebErrors);
					return new ModelAndView(
							ContextualConstantsIF.Views.VIEW_ARTICLE_SUBMISSION_INPUT,
							ContextualConstantsIF.Keys.OBJ, ajaxResponse);

				} else {

					ajaxResponse = new AJAXResponse();
					ajaxResponse.setStatus(false);
					Message msg = new Message(
							ContextualConstantsIF.Message.COULD_NOT_SAVE_ARTICLE);
					List<Message> ebErrors = new ArrayList<Message>();
					ebErrors.add(msg);
					ajaxResponse.setEbErrors(ebErrors);
					return new ModelAndView(
							ContextualConstantsIF.Views.VIEW_ARTICLE_SUBMISSION_INPUT,
							ContextualConstantsIF.Keys.OBJ, ajaxResponse);
				}

			} else {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(true);
				ajaxResponse
						.setMessage(ContextualConstantsIF.Message.SAVE_ARTICLENAME);
				return new ModelAndView(
						ContextualConstantsIF.Views.VIEW_TEACHER_SUCESS_INPUT,
						ContextualConstantsIF.Keys.OBJ, ajaxResponse);
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			Message msg = new Message(e.getMessage());
			msg.setErrMessage(e.getMessage());
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return new ModelAndView(
					ContextualConstantsIF.Views.VIEW_LOGIN_INPUT,
					ContextualConstantsIF.Keys.OBJ, ajaxResponse);
		}

	}

	@Override
	@RequestMapping(value = "/retriveAllArticlesForTeacher.do", method = {
			RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse retriveArticleForCustomer(
			HttpServletRequest request) {

		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			HttpSession session = request.getSession(true);

			String loginId = (String) session
					.getAttribute(ContextualConstantsIF.Keys.LOGINID_SESSION);

			if (null == loginId) {

				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(
						ContextualConstantsIF.Message.SESSION_EXPIRED);
				msg.setErrMessage(ContextualConstantsIF.Message.SESSION_EXPIRED);

				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;

			}

			List<ArticleVO> articleList = contextDelegate
					.retriveArticlesForCustomer(loginId);

			if (!articleList.isEmpty() && !(null == articleList)
					&& !(articleList.size() == 0)) {
				ajaxResponse.setModel(articleList);
				ajaxResponse.setStatus(true);
				ajaxResponse
						.setMessage(ContextualConstantsIF.Message.ARTICLES_RETRIVE_SUCESSFUL);
				return ajaxResponse;
			} else {
				ajaxResponse = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxResponse.setStatus(false);
				Message msg = new Message(
						ContextualConstantsIF.Message.NO_ARTCILES_FOUND);
				msg.setErrMessage(ContextualConstantsIF.Message.NO_ARTCILES_FOUND);
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			Message msg = new Message(
					ContextualConstantsIF.Message.INTERNAL_ERROR);
			msg.setErrMessage(ContextualConstantsIF.Message.INTERNAL_ERROR);
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}

	}

	@Override
	@RequestMapping(value = "/doFeatureVector.do", method = {
			RequestMethod.POST, RequestMethod.GET })
	public ModelAndView doFeatureVector(HttpServletRequest request) {
		try {
			AJAXResponse ajaxRes = new AJAXResponse();

			HttpSession session = request.getSession(false);

			if (session == null) {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message(
						ContextualConstantsIF.Message.SESSION_EXPIRED);
				webSiteUrlMsg
						.setErrMessage(ContextualConstantsIF.Message.SESSION_EXPIRED);
				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(
						ContextualConstantsIF.Views.VIEW_LOGIN_INPUT,
						ContextualConstantsIF.Keys.OBJ, ajaxRes);
			}

			// Obtain User Id from Session

			String userId = (String) session
					.getAttribute(ContextualConstantsIF.Keys.LOGINID_SESSION);

			if (userId == null || userId.isEmpty()) {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message(
						ContextualConstantsIF.Message.SESSION_EXPIRED);
				webSiteUrlMsg
						.setErrMessage(ContextualConstantsIF.Message.SESSION_EXPIRED);
				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(
						ContextualConstantsIF.Views.VIEW_LOGIN_INPUT,
						ContextualConstantsIF.Keys.OBJ, ajaxRes);

			}

			StatusInfo statusInfo = contextDelegate.doFeatureVector();

			if (!statusInfo.isStatus()) {
				if (statusInfo.getErrMsg() != null) {

					AJAXResponse ajaxResponse = new AJAXResponse();
					ajaxResponse.setStatus(false);
					Message msg = new Message(statusInfo.getErrMsg());
					msg.setErrMessage(statusInfo.getErrMsg());
					List<Message> ebErrors = new ArrayList<Message>();
					ebErrors.add(msg);
					ajaxResponse.setEbErrors(ebErrors);
					ajaxResponse.setEbErrors(ebErrors);
					return new ModelAndView(
							ContextualConstantsIF.Views.VIEW_ADMIN_SUCESS_PAGE,
							ContextualConstantsIF.Keys.OBJ, ajaxResponse);
				} else if (statusInfo.getExceptionMsg() != null) {
					AJAXResponse ajaxResponse = new AJAXResponse();
					ajaxResponse.setStatus(false);
					Message msg = new Message(statusInfo.getExceptionMsg());
					msg.setErrMessage(statusInfo.getExceptionMsg());
					List<Message> ebErrors = new ArrayList<Message>();
					ebErrors.add(msg);
					ajaxResponse.setEbErrors(ebErrors);
					ajaxResponse.setEbErrors(ebErrors);
					return new ModelAndView(
							ContextualConstantsIF.Views.VIEW_ADMIN_SUCESS_PAGE,
							ContextualConstantsIF.Keys.OBJ, ajaxResponse);

				} else {

					AJAXResponse ajaxResponse = new AJAXResponse();
					ajaxResponse.setStatus(false);
					Message msg = new Message(
							ContextualConstantsIF.Message.FEATUREVECTOR_FAILED);
					msg.setErrMessage(ContextualConstantsIF.Message.FEATUREVECTOR_FAILED);
					List<Message> ebErrors = new ArrayList<Message>();
					ebErrors.add(msg);
					ajaxResponse.setEbErrors(ebErrors);
					return new ModelAndView(
							ContextualConstantsIF.Views.VIEW_ADMIN_SUCESS_PAGE,
							ContextualConstantsIF.Keys.OBJ, ajaxResponse);

				}
			}
			AJAXResponse ajax = new AJAXResponse();
			ajax.setStatus(true);
			ajax.setMessage(ContextualConstantsIF.Message.FEATUREVECTOR_MESSAGE);

			return new ModelAndView(
					ContextualConstantsIF.Views.VIEW_ADMIN_SUCESS_PAGE,
					ContextualConstantsIF.Keys.OBJ, ajax);

		} catch (Exception e) {
			e.printStackTrace();
			AJAXResponse ajax = new AJAXResponse();
			ajax.setStatus(false);
			Message msg = new Message(
					ContextualConstantsIF.Message.INTERNAL_ERROR);
			msg.setErrMessage(ContextualConstantsIF.Message.INTERNAL_ERROR);
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajax.setEbErrors(ebErrors);
			return new ModelAndView(
					ContextualConstantsIF.Views.VIEW_ADMIN_ERROR_PAGE,
					ContextualConstantsIF.Keys.OBJ, ajax);
		}
	}

	@Override
	@RequestMapping(value = "/viewFV.do", method = { RequestMethod.POST,
			RequestMethod.GET })
	public @ResponseBody AJAXResponse viewFV(HttpServletRequest request) {
		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			List<FeatureVO> featureVOList = contextDelegate.viewFV();
			if (null == featureVOList) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(
						ContextualConstantsIF.Message.EMPTY_FV);
				msg.setErrMessage(ContextualConstantsIF.Message.EMPTY_FV);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			ajaxResponse.setModel(featureVOList);
			ajaxResponse
					.setMessage(ContextualConstantsIF.Message.FV_RETRIVAL_SUCESS);
			return ajaxResponse;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			Message msg = new Message(
					ContextualConstantsIF.Message.INTERNAL_ERROR);
			msg.setErrMessage(ContextualConstantsIF.Message.INTERNAL_ERROR);
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}
	}

	@Override
	@RequestMapping(value = "/specificArticleTeacher.do", method = {
			RequestMethod.POST, RequestMethod.GET })
	public ModelAndView viewSpecificArticle(HttpServletRequest request,
			@RequestParam String articleName) {

		AJAXResponse ajaxResponse = new AJAXResponse();

		try {

			ajaxResponse.setStatus(true);

			ArticleVO articleVO = contextDelegate
					.viewSpecificArticleForArticleId(articleName);

			ajaxResponse.setModel(articleVO);

			return new ModelAndView(
					ContextualConstantsIF.Views.TEACHER_ARTICLE_DETAILS_PAGE,
					ContextualConstantsIF.Keys.OBJ, ajaxResponse);

		} catch (Exception e) {
			e.printStackTrace();
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(false);
			Message msg = new Message(
					ContextualConstantsIF.Message.INTERNAL_ERROR);
			msg.setErrMessage(ContextualConstantsIF.Message.INTERNAL_ERROR);
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return new ModelAndView(
					ContextualConstantsIF.Views.APPLICATION_WELCOME_PAGE,
					ContextualConstantsIF.Keys.OBJ, null);
		}

	}

	@RequestMapping(value = "/download.do", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView download(HttpServletRequest request,
			HttpServletResponse response, @RequestParam String articleName)
			throws Exception {

		ArticleVO articleVO = null;
		try {
			
			
			articleVO = contextDelegate
					.viewSpecificArticleForArticleId(articleName);

			response.setContentType(articleVO.getContextType());
			//response.setContentLength(articleVO.getFileContent().length);
			response.setHeader("Content-Disposition", "attachment; filename=\""
					+ articleVO.getFileName() + "\"");

			OutputStream out = response.getOutputStream();
			response.setContentType(articleVO.getContextType());
			//FileCopyUtils.copy(articleVO.getFileContent(), out);
			
			
			
			
			MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
			 
			try {
				MongoDatabase database = mongoClient.getDatabase("fileupload");
				GridFSBucket gridBucket = GridFSBuckets.create(database);
			 
				//FileOutputStream fileOutputStream = new FileOutputStream("C:/Users/Manabrata/Desktop/Manabrata/"+fileName);
				gridBucket.downloadToStream(articleVO.getFileName(), out);
				//fileOutputStream.close();
			 
			 } catch (Exception e) {
				 e.printStackTrace();
			 } finally {
				 mongoClient.close();
			 }
			
			
			
			
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;

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
	
	
	@RequestMapping(value = "/delete.do", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView delete(HttpServletRequest request,
			HttpServletResponse response, @RequestParam String articleName)
			throws Exception {
		AJAXResponse ajaxResponse = new AJAXResponse();
		
		StatusInfo statusInfo = null;
		try {
			statusInfo = contextDelegate
					.deleteSpecificArticleForArticleId(articleName);
			
			return new ModelAndView(
					ContextualConstantsIF.Views.SEARCH_INPUT_DELETE,
					ContextualConstantsIF.Keys.OBJ, ajaxResponse);


		}catch(Exception e){
			e.printStackTrace();
		}

		return null;

	}

	@Override
	@RequestMapping(value = "/specificArticleAdmin.do", method = {
			RequestMethod.POST, RequestMethod.GET })
	public ModelAndView viewSpecificArticleAdmin(HttpServletRequest request,
			@RequestParam String articleName) {
		AJAXResponse ajaxResponse = new AJAXResponse();

		try {

			ajaxResponse.setStatus(true);

			ArticleVO articleVO = contextDelegate
					.viewSpecificArticleForArticleId(articleName);

			ajaxResponse.setModel(articleVO);

			return new ModelAndView(
					ContextualConstantsIF.Views.ADMIN_ARTICLE_DETAILS_PAGE,
					ContextualConstantsIF.Keys.OBJ, ajaxResponse);

		} catch (Exception e) {
			e.printStackTrace();
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(false);
			Message msg = new Message(
					ContextualConstantsIF.Message.INTERNAL_ERROR);
			msg.setErrMessage(ContextualConstantsIF.Message.INTERNAL_ERROR);
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return new ModelAndView(
					ContextualConstantsIF.Views.APPLICATION_WELCOME_PAGE,
					ContextualConstantsIF.Keys.OBJ, null);
		}
	}

	@Override
	@RequestMapping(value = "/viewArticleStudent.do", method = {
			RequestMethod.POST, RequestMethod.GET })
	public ModelAndView viewSpecificArticleStudent(HttpServletRequest request,
			@RequestParam String articleName) {
		AJAXResponse ajaxResponse = new AJAXResponse();

		try {

			ajaxResponse.setStatus(true);

			ArticleVO articleVO = contextDelegate
					.viewSpecificArticleForArticleId(articleName);

			ajaxResponse.setModel(articleVO);

			return new ModelAndView(
					ContextualConstantsIF.Views.STUDENT_ARTICLE_DETAILS_PAGE,
					ContextualConstantsIF.Keys.OBJ, ajaxResponse);

		} catch (Exception e) {
			e.printStackTrace();
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(false);
			Message msg = new Message(
					ContextualConstantsIF.Message.INTERNAL_ERROR);
			msg.setErrMessage(ContextualConstantsIF.Message.INTERNAL_ERROR);
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return new ModelAndView(
					ContextualConstantsIF.Views.APPLICATION_WELCOME_PAGE,
					ContextualConstantsIF.Keys.OBJ, null);
		}
	}

	@Override
	@RequestMapping(value = "/doSearchArticles.do", method = {
			RequestMethod.POST, RequestMethod.GET })
	public ModelAndView doSearchContentsArticle(HttpServletRequest request,
			@ModelAttribute SearchCriteria searchCriteria) {
		AJAXResponse ajaxRes = null;
		try {

			if ((null == searchCriteria.getQuery()
					|| searchCriteria.getQuery().isEmpty()) && (null == searchCriteria.getInterestArea()
							|| searchCriteria.getInterestArea().isEmpty()) && (null == searchCriteria.getAuthors()
									|| searchCriteria.getAuthors().isEmpty()) && (null == searchCriteria.getPublisher()
									|| searchCriteria.getPublisher().isEmpty()) && (null == searchCriteria.getKeywords()
											|| searchCriteria.getKeywords().isEmpty())) {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message message = new Message(
						ContextualConstantsIF.Message.SEARCH_EMPTY);
				message.setErrMessage(ContextualConstantsIF.Message.SEARCH_EMPTY);
				ebErrors.add(message);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(
						ContextualConstantsIF.Views.SEARCH_INPUT,
						ContextualConstantsIF.Keys.OBJ, ajaxRes);

			}
			// Session

			HttpSession session = request.getSession(false);

			if (session == null) {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message message = new Message(
						ContextualConstantsIF.Message.SESSION_EXPIRED);
				message.setErrMessage(ContextualConstantsIF.Message.SESSION_EXPIRED);
				ebErrors.add(message);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(
						ContextualConstantsIF.Views.LOGIN_INPUT,
						ContextualConstantsIF.Keys.OBJ, ajaxRes);
			}

			// Obtain User Id from Session

			String userId = (String) session
					.getAttribute(ContextualConstantsIF.Keys.LOGINID_SESSION);

			if (userId == null || userId.isEmpty()) {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message(
						ContextualConstantsIF.Message.SESSION_EXPIRED);
				webSiteUrlMsg
						.setErrMessage(ContextualConstantsIF.Message.SESSION_EXPIRED);
				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(
						ContextualConstantsIF.Views.LOGIN_INPUT,
						ContextualConstantsIF.Keys.OBJ, ajaxRes);
			}

			StatusInfo statusInfo = contextDelegate.performRankSearch(
					request.getContextPath(), searchCriteria);

			if (!statusInfo.isStatus()) {

				if (statusInfo.getErrMsg() != null) {

					ajaxRes = new AJAXResponse();
					List<Message> ebErrors = new ArrayList<Message>();
					ajaxRes.setStatus(false);
					Message msg = new Message(statusInfo.getErrMsg());
					msg.setErrMessage(statusInfo.getErrMsg());
					ebErrors.add(msg);
					ajaxRes.setEbErrors(ebErrors);
					return new ModelAndView(
							ContextualConstantsIF.Views.SEARCH_INPUT,
							ContextualConstantsIF.Keys.OBJ, ajaxRes);
				} else {

					ajaxRes = new AJAXResponse();
					List<Message> ebErrors = new ArrayList<Message>();
					ajaxRes.setStatus(false);
					Message msg = new Message(
							ContextualConstantsIF.Message.COULD_NOT_FIND_RESULTS);
					msg.setErrMessage(ContextualConstantsIF.Message.COULD_NOT_FIND_RESULTS);
					ebErrors.add(msg);
					ajaxRes.setEbErrors(ebErrors);
					return new ModelAndView(
							ContextualConstantsIF.Views.SEARCH_INPUT,
							ContextualConstantsIF.Keys.OBJ, ajaxRes);

				}

			} else {

				@SuppressWarnings("unchecked")
				List<SearchObj> searchResults = (List<SearchObj>) statusInfo
						.getObject();

				if (null == searchResults || searchResults.isEmpty()) {

					ajaxRes = new AJAXResponse();
					List<Message> ebErrors = new ArrayList<Message>();
					ajaxRes.setStatus(false);
					Message msg = new Message(
							ContextualConstantsIF.Message.SEARCHRESULTS_EMPTY);
					msg.setErrMessage(ContextualConstantsIF.Message.SEARCHRESULTS_EMPTY);
					ebErrors.add(msg);
					ajaxRes.setEbErrors(ebErrors);
					return new ModelAndView(
							ContextualConstantsIF.Views.SEARCH_INPUT,
							ContextualConstantsIF.Keys.OBJ, ajaxRes);

				}

				ajaxRes = new AJAXResponse();
				ajaxRes.setStatus(true);
				ajaxRes.setModel(searchResults);
				return new ModelAndView(
						ContextualConstantsIF.Views.SEARCH_INPUT,
						ContextualConstantsIF.Keys.OBJ, ajaxRes);
			}

		} catch (Exception e) {
			ajaxRes = new AJAXResponse();
			List<Message> ebErrors = new ArrayList<Message>();
			ajaxRes.setStatus(false);
			Message webSiteUrlMsg = new Message(
					ContextualConstantsIF.Message.INTERNAL_ERROR);
			webSiteUrlMsg
					.setErrMessage(ContextualConstantsIF.Message.INTERNAL_ERROR);
			ebErrors.add(webSiteUrlMsg);
			ajaxRes.setEbErrors(ebErrors);
			return new ModelAndView(ContextualConstantsIF.Views.SEARCH_INPUT,
					ContextualConstantsIF.Keys.OBJ, ajaxRes);
		}
	}
	


}
