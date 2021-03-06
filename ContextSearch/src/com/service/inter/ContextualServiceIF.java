package com.service.inter;

import java.util.List;

import com.model.AdjectiveVO;
import com.model.ArticleNamesVO;
import com.model.ArticleTypesVO;
import com.model.ArticleVO;
import com.model.CleanArticleUIModel;
import com.model.CompareArticleVO;
import com.model.ComparisionInputModel;
import com.model.ComparisionVO;
import com.model.FeatureVO;
import com.model.FreqComputation;
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
import com.model.ThemeModel;
import com.model.TokenVO;
import com.model.WebSiteUrlModel;

public interface ContextualServiceIF {

	public StatusInfo storeReviews(ArticleModel reviewModel);

	public List<ArticleDetailModel> obtainAllReviews();

	public boolean storeWebSiteData(WebSiteUrlModel webSiteModel);

	List<StopWordsVO> retriveStopWords();

	StatusInfo addStopword(String stopWord);

	public StatusInfo doDataCleaning();

	public List<CleanArticleUIModel> retriveCleanReviewList();

	public StatusInfo doTokens();

	public List<TokenVO> retriveTokenList();

	StatusInfo removeStopword(String stopWord);

	public StatusInfo addKeyPhrase(String keyphrase, String type);

	public List<KeyPhraseModel> retriveKeyPhrases();

	public StatusInfo removeKeyPhrase(String keyphrase, String type);

	public StatusInfo addPhraseG(PhraseGModel phraseGModel);

	public StatusInfo removePhraseG(PhraseGModel phraseGModel);

	public List<PhraseGModel> retrivePhraseG();

	public StatusInfo genPhrases();

	public List<PhraseVO> retrivePhrasesList();

	public StatusInfo genAdjectives();

	public List<AdjectiveVO> retriveAdjectives();

	public StatusInfo genStructure1();

	public List<StructureVO> retriveStructure1List();

	public List<StructureVO> retriveStructure2List();

	public List<StructureVO> retriveStructure3List();

	public StatusInfo genStructure2();

	public StatusInfo genStructure3();

	public StatusInfo generateFreq();

	public List<FreqComputation> viewFreq();

	public List<ArticleNamesVO> retriveAllArticleNames();

	public List<ArticleTypesVO> retriveTypes();

	public StatusInfo compareArticles(ComparisionInputModel comparisionModel);

	public void storeAdjectives(String string);

	public List<AdjectiveVO> retriveNLPAdjectives();

	public StatusInfo checkLogin(RegisterUser registerUser);

	public StatusInfo doRegistration(RegisterUser registerUser);

	public StatusInfo computeLogLikelihood();

	public List<LogLikelihoodVO> retriveLogLikelihood();

	public StatusInfo doRVCoeffcient(ComparisionInputModel comparisionModel);

	StatusInfo detectDuplicateArticles(double threshold);

	public List<GroupingView> viewGrouping();

	public StatusInfo doThemes(ThemeModel themeModel);

	public List<ComparisionVO> retriveComparison();

	public StatusInfo doRegistrationTeacher(RegisterUser registerUser);

	public StatusInfo storeArticle(ArticleVO articleVO);

	public List<ArticleVO> retriveArticles();

	public List<ArticleVO> retriveArticlesForCustomer(String loginId);

	StatusInfo doFeatureVector();

	public List<FeatureVO> viewFV();

	public ArticleVO viewSpecificArticle(String articleName);

	public ArticleVO viewSpecificArticleForArticleId(String articleName);
	
	public StatusInfo deleteSpecificArticleForArticleId(String articleName);

	public StatusInfo performRankSearch(String contextPath,
			SearchCriteria searchCriteria);

}
