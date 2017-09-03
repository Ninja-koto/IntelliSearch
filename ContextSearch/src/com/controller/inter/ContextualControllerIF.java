package com.controller.inter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.model.AJAXResponse;
import com.model.ArticleVO;
import com.model.ArticleVOUIModel;
import com.model.ComparisionInputModel;
import com.model.PhraseGModel;
import com.model.RegisterUser;
import com.model.ArticleModel;
import com.model.SearchCriteria;
import com.model.ThemeModel;

public interface ContextualControllerIF {

	ModelAndView addStopWord(String stopWord);

	AJAXResponse viewStopWords(HttpServletRequest request);

	ModelAndView removeStopWord(String stopWord);

	AJAXResponse viewTokens(HttpServletRequest request);

	ModelAndView doTokens(HttpServletRequest request);

	AJAXResponse viewCleanReviews(HttpServletRequest request);

	ModelAndView doDataCleaning(HttpServletRequest request);

	ModelAndView addKeyPhrase(String stopWord, String type);

	AJAXResponse viewKeyPhrases(HttpServletRequest request);

	ModelAndView removeKeyPhrase(String keyphrase, String type);

	ModelAndView addPhraseG(PhraseGModel phraseGModel);

	AJAXResponse viewPhraseG(HttpServletRequest request);

	ModelAndView removePhraseG(PhraseGModel phraseGModel);

	ModelAndView generatePhrases();

	AJAXResponse viewPhrases(HttpServletRequest request);

	ModelAndView generateAdjectives();

	AJAXResponse viewAdjectives(HttpServletRequest request);

	ModelAndView generateStructure1();

	AJAXResponse viewStrcuture1(HttpServletRequest request);

	AJAXResponse viewStrcuture2(HttpServletRequest request);

	AJAXResponse viewStrcuture3(HttpServletRequest request);

	ModelAndView generateStructure2();

	ModelAndView generateStructure3();

	ModelAndView generateFreq();

	AJAXResponse viewFreq(HttpServletRequest request);

	AJAXResponse retriveAllArticleNames(HttpServletRequest request);

	AJAXResponse retriveTypes(HttpServletRequest request);

	AJAXResponse compareArticles(HttpServletRequest request,
			@RequestBody ComparisionInputModel comparisionInputModel);

	AJAXResponse viewNLPAdjectives(HttpServletRequest request);

	ModelAndView registerUserInfo(RegisterUser registerUser,
			HttpServletRequest request);

	ModelAndView doLogin(HttpServletRequest request, RegisterUser registerUser);

	ModelAndView doLogout(HttpServletRequest request);

	AJAXResponse storeArticle(ArticleModel reviewModel,
			HttpServletRequest request);

	ModelAndView doLikelihoodFunction(HttpServletRequest request);

	AJAXResponse retriveAllLOgLikelihood(HttpServletRequest request);

	AJAXResponse doRVCoeffcient(HttpServletRequest request,
			ComparisionInputModel com);

	ModelAndView doClustering(HttpServletRequest request, String threshold);

	AJAXResponse viewGrouping(HttpServletRequest request);

	AJAXResponse doThemes(HttpServletRequest request,
			ThemeModel comparisionModel);

	AJAXResponse retriveComparison(HttpServletRequest request);

	ModelAndView registerTeacherInfo(RegisterUser registerUser,
			HttpServletRequest request);

	AJAXResponse retriveArticles();

	AJAXResponse retriveArticleForCustomer(HttpServletRequest request);

	ModelAndView doFeatureVector(HttpServletRequest request);

	AJAXResponse viewFV(HttpServletRequest request);

	ModelAndView viewSpecificArticle(HttpServletRequest request,
			String articleName);

	ModelAndView viewSpecificArticleAdmin(HttpServletRequest request,
			String articleName);



	ModelAndView viewSpecificArticleStudent(HttpServletRequest request,
			String articleName);

	ModelAndView submitArticle(HttpServletRequest request,
			ArticleVOUIModel articleVO, CommonsMultipartFile file);

	
	ModelAndView doSearchContentsArticle(HttpServletRequest request,
			SearchCriteria searchCriteria);

}
