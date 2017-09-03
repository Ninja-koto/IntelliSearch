package com.delegate.impl;

import java.text.ParseException;
import java.util.List;

import com.delegate.inter.ContextualDelegateIF;
import com.model.AdjectiveVO;
import com.model.ArticleNamesVO;
import com.model.ArticleTypesVO;
import com.model.ArticleVO;
import com.model.CleanArticleUIModel;
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
import com.model.ArticleModel;
import com.model.SearchCriteria;
import com.model.SearchObj;
import com.model.StatusInfo;
import com.model.StopWordsVO;
import com.model.StructureVO;
import com.model.ThemeModel;
import com.model.TokenVO;
import com.model.WebSiteUrlModel;
import com.service.inter.ContextualServiceIF;

public class ContextualDelegateImpl implements ContextualDelegateIF {

	private ContextualServiceIF contextualService;

	public ContextualServiceIF getContextualService() {
		return contextualService;
	}

	public void setContextualService(ContextualServiceIF contextualService) {
		this.contextualService = contextualService;
	}

	@Override
	public StatusInfo storeReviews(ArticleModel reviewModel) {
		return contextualService.storeReviews(reviewModel);
	}

	@Override
	public boolean storeWebSiteData(WebSiteUrlModel webSiteModel) {
		return contextualService.storeWebSiteData(webSiteModel);
	}

	@Override
	public StatusInfo addStopword(String stopWord) {
		return contextualService.addStopword(stopWord);
	}

	@Override
	public List<StopWordsVO> retriveStopWords() {
		return contextualService.retriveStopWords();
	}

	@Override
	public StatusInfo doDataCleaning() {
		return contextualService.doDataCleaning();
	}

	@Override
	public List<CleanArticleUIModel> retriveCleanReviewList() {
		return contextualService.retriveCleanReviewList();
	}

	@Override
	public StatusInfo doTokens() {
		return contextualService.doTokens();
	}

	@Override
	public List<TokenVO> retriveTokenList() {
		return contextualService.retriveTokenList();
	}

	@Override
	public StatusInfo removeStopword(String stopWord) {
		return contextualService.removeStopword(stopWord);
	}

	@Override
	public StatusInfo addKeyPhrase(String keyphrase, String type) {
		return contextualService.addKeyPhrase(keyphrase, type);
	}

	@Override
	public List<KeyPhraseModel> retriveKeyPhrases() {
		return contextualService.retriveKeyPhrases();
	}

	@Override
	public StatusInfo removeKeyPhrase(String keyphrase, String type) {
		return contextualService.removeKeyPhrase(keyphrase, type);
	}

	@Override
	public StatusInfo addPhraseG(PhraseGModel phraseGModel) {
		return contextualService.addPhraseG(phraseGModel);
	}

	@Override
	public StatusInfo removePhraseG(PhraseGModel phraseGModel) {
		return contextualService.removePhraseG(phraseGModel);
	}

	@Override
	public List<PhraseGModel> retrivePhraseG() {
		return contextualService.retrivePhraseG();
	}

	@Override
	public StatusInfo genPhrases() {
		return contextualService.genPhrases();
	}

	@Override
	public List<PhraseVO> retrivePhrasesList() {
		return contextualService.retrivePhrasesList();
	}

	@Override
	public StatusInfo genAdjectives() {
		return contextualService.genAdjectives();
	}

	@Override
	public List<AdjectiveVO> retriveAdjectives() {
		return contextualService.retriveAdjectives();
	}

	@Override
	public StatusInfo genStructure1() {
		return contextualService.genStructure1();
	}

	@Override
	public List<StructureVO> retriveStructure1List() {
		return contextualService.retriveStructure1List();
	}

	@Override
	public List<StructureVO> retriveStructure2List() {
		return contextualService.retriveStructure2List();
	}

	@Override
	public List<StructureVO> retriveStructure3List() {
		return contextualService.retriveStructure3List();
	}

	@Override
	public StatusInfo genStructure2() {
		return contextualService.genStructure2();
	}

	@Override
	public StatusInfo genStructure3() {
		return contextualService.genStructure3();
	}

	@Override
	public StatusInfo generateFreq() {
		return contextualService.generateFreq();
	}

	@Override
	public List<FreqComputation> viewFreq() {
		return contextualService.viewFreq();
	}

	@Override
	public List<ArticleNamesVO> retriveAllArticleNames() {
		return contextualService.retriveAllArticleNames();
	}

	@Override
	public List<ArticleTypesVO> retriveTypes() {
		return contextualService.retriveTypes();
	}

	@Override
	public StatusInfo compareArticles(ComparisionInputModel comparisionModel) {
		return contextualService.compareArticles(comparisionModel);
	}

	@Override
	public List<AdjectiveVO> retriveNLPAdjectives() {
		return contextualService.retriveNLPAdjectives();
	}

	@Override
	public StatusInfo checkLogin(RegisterUser registerUser) {
		return contextualService.checkLogin(registerUser);
	}

	@Override
	public StatusInfo doRegistration(RegisterUser registerUser) {
		return contextualService.doRegistration(registerUser);
	}

	@Override
	public StatusInfo doLogLikelihood() {
		return contextualService.computeLogLikelihood();
	}

	@Override
	public List<LogLikelihoodVO> retriveAllLogLikelihood() {
		return contextualService.retriveLogLikelihood();
	}

	@Override
	public StatusInfo doRVCoeffcient(ComparisionInputModel comparisionInputModel) {
		return contextualService.doRVCoeffcient(comparisionInputModel);
	}

	@Override
	public StatusInfo doClustering(double threshold) {
		return contextualService.detectDuplicateArticles(threshold);
	}

	@Override
	public List<GroupingView> viewGrouping() {
		return contextualService.viewGrouping();
	}

	@Override
	public StatusInfo doThemes(ThemeModel themeModel) {
		return contextualService.doThemes(themeModel);
	}

	@Override
	public List<ComparisionVO> retriveComparison() {
		return contextualService.retriveComparison();
	}

	@Override
	public StatusInfo doRegistrationTeacher(RegisterUser registerUser) {
		return contextualService.doRegistrationTeacher(registerUser);
	}

	@Override
	public StatusInfo storeArticle(ArticleVO articleVO) throws ParseException,
			Exception {
		return contextualService.storeArticle(articleVO);
	}

	@Override
	public List<ArticleVO> retriveArticles() {
		return contextualService.retriveArticles();
	}

	@Override
	public List<ArticleVO> retriveArticlesForCustomer(String loginId) {
		return contextualService.retriveArticlesForCustomer(loginId);
	}

	@Override
	public StatusInfo doFeatureVector() {
		return contextualService.doFeatureVector();
	}

	@Override
	public List<FeatureVO> viewFV() {
		return contextualService.viewFV();
	}

	@Override
	public ArticleVO viewSpecificArticle(String articleName) {
		return contextualService.viewSpecificArticle(articleName);
	}

	@Override
	public ArticleVO viewSpecificArticleForArticleId(String articleName) {
		return contextualService.viewSpecificArticleForArticleId(articleName);
	}
	
	@Override
	public StatusInfo deleteSpecificArticleForArticleId(String articleName) {
		return contextualService.deleteSpecificArticleForArticleId(articleName);
	}

	@Override
	public StatusInfo performRankSearch(String contextPath,
			SearchCriteria searchCriteria) {
		return contextualService.performRankSearch(contextPath, searchCriteria);
	}

}
